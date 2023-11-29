package checkers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;

import ocsf.server.ConnectionToClient;

public class CheckersGame {
	private CheckersServer server;
	//these will be used to color the cells of the board (don't know if this matters for the server yet)
	private List<Integer> oddStartCells = Arrays.asList(new Integer[] {1,3,5,7});
	private List<Integer> evenStartCells = Arrays.asList(new Integer[] {0,2,4,6});
	//these will be used to identify which player is sending information
	private ConnectionToClient playerOne = null;
	private ConnectionToClient playerTwo = null;
	//these will be used to verify who's turn it should be
	private boolean playerOneTurn = false;
	private boolean playerTwoTurn = false;
	private ConnectionToClient winningPlayer = null;
	private List<BoardCell> possibleMoves = new ArrayList<BoardCell>();

	//will store boardcells and also will have row and columns
	private BoardCell[][] cells;
	private int rows = 8;
	private int columns = 8;

	//will be used to store selected from piece
	private BoardCell from;

	//will be changed to true when a second player joins
	private boolean started = false;
	//used to mirror moves to the other side of the board (will need to be done for all player two input...)
	private Map<Integer, Integer> dictionary = new HashMap<>();
	
	private boolean jumpMove = false;
	private List<BoardCell> captureMoves = new ArrayList<BoardCell>();

	public boolean getPlayerOneTurn() {
		return playerOneTurn;
	}

	public boolean getPlayerTwoTurn() {
		return playerTwoTurn;
	}

	public void flipTurns() {
		playerOneTurn = !playerOneTurn;
		playerTwoTurn = !playerTwoTurn;
	}

	public void fillTranslationDictionary() {
		dictionary.put(7,0);
		dictionary.put(6,1);
		dictionary.put(5, 2);
		dictionary.put(4, 3);
		dictionary.put(3, 4);
		dictionary.put(2, 5);
		dictionary.put(1, 6);
		dictionary.put(0, 7);
	}
	
	public void removePiece(BoardCell pieceRemoved) {
		
		//if piece is tan and player is tan -> remove piece, do nothing additional
		//if piece is green and player is tan -> remove piece, call pieceTaken() on GameSidePanel
		//same stuff above but if the player is green.
		pieceRemoved.setIcon(null);
		pieceRemoved.setPiece(false);
		pieceRemoved.setKing(false);
		
		
	}
	
	public void movePiece(BoardCell to) {
		if (from.getPieceColor() == 0) {
			from.setIcon(null);
			from.setPiece(false);
			to.setPieceColor(0);
			to.setPiece(true);
			to.setKing(from.isKing());
			if (from.isKing()) {
				from.setKing(false);
			}
			from = null;
			to = null;
			captureMoves.clear();
			possibleMoves.clear();
		}
		else if (from.getPieceColor()==1) {
			from.setIcon(null);
			from.setPiece(false);
			to.setPieceColor(1);
			to.setPiece(true);
			to.setKing(from.isKing());
			if (from.isKing()) {
				from.setKing(false);
			}
			from = null;
			to = null;
			captureMoves.clear();
			possibleMoves.clear();
		}
		
	}
	
	
	public void capture(BoardCell toCapture) {
		toCapture.setIcon(null);
		toCapture.setPiece(false);
		toCapture.setKing(false);
		System.out.println("Captured: " + toCapture.toString());
	}
	
	//dictionary of boardCell that sends a specific message if we move to a certain piece that is a jump
	//might need to return object here.
	public Object processClick(int i, int j, ConnectionToClient sender) {
		//if it's the senders turn
		if (sender == playerOne && playerOneTurn == true) {
			//if selected cell has piece and is the color of the sender
			if (cells[i][j].hasPiece() && cells[i][j].getPieceColor() == 0) {
				//select that cell by making the from board cell equal to that
				from = cells[i][j];
				//send back determine potential moves so that the user can make the next choice.
				possibleMoves = determinePotentialMoves();
				
				//if there are moves available, send them to the player
				if (!possibleMoves.isEmpty()) {
					//server.sendMessageToClient("POSSIBLE:" + possibleMoves.get(0).toString() + ";" + possibleMoves.get(1).toString(), sender);
					if (possibleMoves.size() == 2) {
						server.sendMessageToClient("POSSIBLE:" + possibleMoves.get(0).toString() + ";" + possibleMoves.get(1).toString(), sender);
					}
					else if (possibleMoves.size() == 1) {
						server.sendMessageToClient("POSSIBLE:" + possibleMoves.get(0).toString(), sender);
					}
				}
				//if there are no possible moves, send not possible
				else if (possibleMoves.isEmpty()) {
					server.sendMessageToClient("NOT POSSIBLE", sender);
				}
			}
			//if they have a selected piece and they are sending a cells that is in possible moves
			//MOVE ENCODING
			if (from != null && possibleMoves.contains(cells[i][j])) {
				//server.sendMoveToClients(sender, from, cells[i][j], true);
				//from = null;
				//since this move is playerOne, we check if this move is in capture moves
				if (captureMoves.contains(cells[i][j])) {
					if (from.getColumn() - j > 0) {
						//this is a right capture
						server.sendMessageToClient("CAPTURE:"+cells[from.getRow()-1][from.getColumn()-1].toString(), playerOne);
						
						//need to flip this 
						BoardCell flippedCapture = cells[dictionary.get(from.getRow()-1)][dictionary.get(from.getColumn()-1)];
						server.sendMessageToClient("CAPTURE:"+flippedCapture.toString(), playerTwo);
						capture(cells[from.getRow()-1][from.getColumn()-1]);
					}
					if (from.getColumn() - j < 0) {
						//this is a left capture
						server.sendMessageToClient("CAPTURE:"+cells[from.getRow()-1][from.getColumn()+1].toString(), playerOne);
						
						BoardCell flippedCapture = cells[dictionary.get(from.getRow()-1)][dictionary.get(from.getColumn()+1)];
						
						server.sendMessageToClient("CAPTURE:"+flippedCapture.toString(),playerTwo);
						capture(cells[from.getRow()-1][from.getColumn()+1]);
					}
					
				}
				
				
				
				//send the exact move to playerOne
				server.sendMessageToClient("MOVE:" + from.toString() + ";" + cells[i][j].toString(), playerOne);
				//otherwise mirror move and send to playerTwo
				BoardCell mirroredFrom = cells[dictionary.get(from.getRow())][dictionary.get(from.getColumn())];
				BoardCell mirroredTo = cells[dictionary.get(i)][dictionary.get(j)];
				
				server.sendMessageToClient("MOVE:" + mirroredFrom.toString() + ";" + mirroredTo.toString(), playerTwo);
				movePiece(cells[i][j]);
				
				
				//if piece was on first row
				if (i == 0) {
					cells[i][j].setKing(true);
					//send message to King piece on playerOne's gui
					server.sendMessageToClient("KING:" + cells[i][j].toString(), playerOne);
					//send mirrored version of piece that is being kinged to playerTwo
					server.sendMessageToClient("KING:"+ mirroredTo.toString(), playerTwo);
				}
				
				flipTurns();
				
				server.sendMessageToClient("END TURN", playerOne);
				server.sendMessageToClient("YOUR TURN", playerTwo);
			}

		}
		else if (sender == playerTwo && playerTwoTurn == true) {
			//need to mirror the move for player two so that the server can keep track of things...
			Integer twoI = dictionary.get(i);
			Integer twoJ = dictionary.get(j);
			if (cells[twoI][twoJ].hasPiece() && cells[twoI][twoJ].getPieceColor() == 1) {
				from = cells[twoI][twoJ];

				//send back determine potential moves so that the user can make the next choice
				possibleMoves = determinePotentialMoves();
				
				//if there are moves available, send them to the player
				if (!possibleMoves.isEmpty()) {
					
					//will have to flip potential moves before sending back to player two? since they are seeing a non-flipped version of their gamestate???
					if (possibleMoves.size() == 2) {
						BoardCell flippedPossibleMove = cells[dictionary.get(possibleMoves.get(0).getRow())][dictionary.get(possibleMoves.get(0).getColumn())];
						BoardCell flippedPossibleMove2 = cells[dictionary.get(possibleMoves.get(1).getRow())][dictionary.get(possibleMoves.get(1).getColumn())];
						server.sendMessageToClient("POSSIBLE:" + flippedPossibleMove.toString() + ";" + flippedPossibleMove2.toString(), sender);
					}
					else if (possibleMoves.size() == 1) {
						BoardCell flippedPossibleMove = cells[dictionary.get(possibleMoves.get(0).getRow())][dictionary.get(possibleMoves.get(0).getColumn())];
						server.sendMessageToClient("POSSIBLE:" + flippedPossibleMove.toString(), sender);
					}
					//server.sendMessageToClient("POSSIBLE:" + possibleMoves.get(0).toString() + ";" + possibleMoves.get(1).toString(), sender);
				}
				//if there are no possible moves, send not possible
				else if (possibleMoves.isEmpty()) {
					server.sendMessageToClient("NOT POSSIBLE", sender);
				}
			}
			// if from is selected for playerTwo and possible moves contains the flipped version of their selection then process.
			if (from != null && possibleMoves.contains(cells[twoI][twoJ])) {
				//server.sendMoveToClients(sender, from, cells[dictionary.get(i)][dictionary.get(j)], true);
				//from = null;
				//send flipped version of move to playerOne
				if (captureMoves.contains(cells[twoI][twoJ])) {
					if (from.getColumn() - twoJ > 0) {
						//this is a left capture
						server.sendMessageToClient("CAPTURE:"+cells[from.getRow()+1][from.getColumn()+1].toString(), playerOne);
						
						//need to flip this 
						BoardCell flippedCapture = cells[dictionary.get(from.getRow()+1)][dictionary.get(from.getColumn()+1)];
						server.sendMessageToClient("CAPTURE:"+flippedCapture.toString(), playerTwo);
					}
					if (from.getColumn() - twoJ < 0) {
						//this is a right capture
						server.sendMessageToClient("CAPTURE:"+cells[from.getRow()+1][from.getColumn()-1].toString(), playerOne);
						
						BoardCell flippedCapture = cells[dictionary.get(from.getRow()+1)][dictionary.get(from.getColumn()-1)];
						
						server.sendMessageToClient("CAPTURE:"+flippedCapture.toString(),playerTwo);
					}
					
				}
				
				server.sendMessageToClient("MOVE:" + from.toString() + ";" + cells[twoI][twoJ].toString(), playerOne);

				//unflip the from variable to send to playerTwo
				BoardCell mirroredFrom = cells[dictionary.get(from.getRow())][dictionary.get(from.getColumn())];

				//send unflipped version of move to playerTwo
				server.sendMessageToClient("MOVE:" + mirroredFrom.toString() + ";" + cells[i][j].toString(), playerTwo);
				movePiece(cells[twoI][twoJ]);
				
				//if the move is on the last row of playerOne's side, king p2 piece
				if (twoI == 7) {
					cells[twoI][twoJ].setKing(true);
					
					//send unflipped version of king message to playerTwo
					server.sendMessageToClient("KING:" + cells[i][j].toString(), playerTwo);
					//send flipped version of king message to playerOne (this is also what will be referenced server side)
					server.sendMessageToClient("KING:" + cells[twoI][twoJ], playerOne);
				}
				
				
				flipTurns();
				server.sendMessageToClient("END TURN", playerTwo);
				server.sendMessageToClient("YOUR TURN", playerOne);
				
			}
		}
		return null;
	}

	public List<BoardCell> determinePotentialMoves() {
		int i = from.getRow();
		int j = from.getColumn();
		boolean leftIsPossible = false;
		boolean rightIsPossible = false;
		boolean backLeftIsPossible = false;
		boolean backRightIsPossible = false;
		boolean jumpRight = false;
		boolean jumpLeft = false;
		boolean jumpBackLeft = false;
		boolean jumpBackRight = false;
		List<BoardCell> potentialMoves = new ArrayList<BoardCell>();
		BoardCell rightMove = null;
		BoardCell leftMove = null;
		BoardCell backLeftMove = null;
		BoardCell backRightMove = null;
		


		//if tan (playerOne) piece
		if (from.getPieceColor() == 0) {
			try {
				leftMove = cells[i-1][j-1];
			} catch(Exception e) {		
				System.out.println("couldn't get left move");
			}
			try {
				rightMove = cells[i-1][j+1];
			} catch (Exception e) {
				System.out.println("couldn't get right move");

			}
			//if the piece is a king
			if (from.isKing()) {

			}
			//if the piece is a normal piece
			else if (!from.isKing()) {
				// if there could be a left move, but there'sd a piece in the way
				if (leftMove != null && leftMove.hasPiece()) {
					//if the piece is the same color as player, can't move
					if (leftMove.getPieceColor() == 0) {
						leftIsPossible = false;
					}
					//otherwise, we need to try and go up one more space diagnollly for left and right move
					else {
						
						try {
							leftMove = cells[leftMove.getRow()-1][leftMove.getColumn()-1];
							if (!leftMove.hasPiece()) {
								leftIsPossible = true;
								jumpLeft = true;
							}
						} catch (Exception e) {
							System.out.println("Jump on left not possible");
						}
						
					} 
					
				}
				else if (leftMove!=null){
					leftIsPossible = true;
				}
				if (rightMove != null && rightMove.hasPiece()) {
					if (rightMove.getPieceColor() == 0) {
						rightIsPossible = false;
					}
					else {
						
						try {
							rightMove = cells[rightMove.getRow()-1][rightMove.getColumn()+1];
							if (!rightMove.hasPiece()) {
								rightIsPossible = true;
								jumpRight = true;
							}
						} catch (Exception e) {
							System.out.println("Jump on right not possible");
						}
						
					} 
				}
				else if (rightMove !=null) {
					rightIsPossible = true;
				}
			}
		}
		//if it's a green piece (playerTwo)
		else if (from.getPieceColor() == 1) {
			try {
				leftMove = cells[i+1][j+1];
			} catch(Exception e) {		
				System.out.println("couldn't get left move");
			}
			try {
				rightMove = cells[i+1][j-1];
			} catch (Exception e) {
				System.out.println("couldn't get right move");

			}
			
			if (from.isKing()) {
				
			}
			else if (!from.isKing()) {
				if (leftMove != null && leftMove.hasPiece()) {
					if (leftMove.getPieceColor() == 1) {
						leftIsPossible = false;
					}
					else {
						try {
							leftMove = cells[leftMove.getRow()+1][leftMove.getColumn()+1];
							if (!leftMove.hasPiece()) {
								leftIsPossible = true;
								jumpLeft = true;
							}
							
						} catch (Exception e) {
							System.out.println("Jump on left Not Possible");
						}
						
					}
				}
				else if (leftMove != null) {
					leftIsPossible = true;
				}
				
				if (rightMove != null && rightMove.hasPiece()) {
					if (rightMove.getPieceColor() == 1) {
						rightIsPossible = false;
					}
					else {
						try {
							rightMove = cells[rightMove.getRow()+1][rightMove.getColumn()-1];
							if (!rightMove.hasPiece()) {
								rightIsPossible = true;
								jumpRight = true;
							}
						} catch (Exception e) {
							System.out.println("Jump On Right Not Possible");
						}
					}
				}
				else if (rightMove != null) {
					rightIsPossible = true;
				}
			}

		}
		if (rightIsPossible) {
			potentialMoves.add(rightMove);
			if (jumpRight) {
				captureMoves.add(rightMove);
			}
		}
		if (leftIsPossible) {
			potentialMoves.add(leftMove);
			if (jumpLeft) {
				captureMoves.add(leftMove);
			}
		}
		if (backRightIsPossible) {
			potentialMoves.add(backRightMove);
			if (jumpBackRight) {
				captureMoves.add(backRightMove);
			}
		}
		if (backLeftIsPossible) {
			potentialMoves.add(backLeftMove);
			if (jumpBackLeft) {
				captureMoves.add(backLeftMove);
			}
		}

		//for (BoardCell boardCell : potentialMoves) {
		//boardCell.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		//boardCell.setBackground(highLightColor);
		//}
		return potentialMoves;
	}

	public void buildBoard() {
		cells = new BoardCell[rows][columns];
		boolean red = false;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new BoardCell(i,j); 
				cells[i][j].setGreenCell(false);
				if (red) {
					cells[i][j].setGreenCell(true);
				}
				cells[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE));
				red = !red;
				//if we are on last swap the color so that it stays the same...
				if (j == columns-1) {
					red = !red;
				}
				if (i == 0 || i == 2) {
					if (oddStartCells.contains(j)) {
						cells[i][j].setPieceColor(1);
						cells[i][j].setPiece(true);
					}
				}
				if (i==1) {
					if (evenStartCells.contains(j)) {
						cells[i][j].setPieceColor(1);
						cells[i][j].setPiece(true);
					}
				}
				if (i == 5 || i == 7) {
					if (evenStartCells.contains(j)) {
						cells[i][j].setPieceColor(0);
						cells[i][j].setPiece(true);
					}
				}
				if (i == 6) {
					if (oddStartCells.contains(j)) {
						cells[i][j].setPieceColor(0);
						cells[i][j].setPiece(true);
					}
				}
			}
		}

	}

	public boolean isStarted() {
		return started;
	}

	public boolean isHosted() {
		if (playerOne == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public void setPlayer(ConnectionToClient player) {
		if (playerOne == null) {
			playerOne = player;
			return;
		}
		else if (playerTwo == null) {
			playerTwo = player;
			//the below code actually starts the game by flipping playerOneTurn to true and sending out a message to both clients
			started = true;
			playerOneTurn = true;
			server.sendMessageToClient("YOUR TURN", playerOne);
			server.sendMessageToClient("END TURN", playerTwo);
		}
	}

	public boolean isPlayerOneTurn() {
		return playerOneTurn;
	}

	public boolean isPlayerTwoTurn() {
		return playerTwoTurn;
	}

	public void setPlayerOneTurn(boolean turn) {
		playerOneTurn = turn;
	}

	public void setPlayerTwoTurn(boolean turn) {
		playerTwoTurn = turn;
	}

	public ConnectionToClient getPlayerOne() {
		return playerOne;
	}

	public ConnectionToClient getPlayerTwo() {
		return playerTwo;
	}
	public CheckersGame(CheckersServer server) {
		this.server = server;
		fillTranslationDictionary();
		buildBoard();
	}
}
