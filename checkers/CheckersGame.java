package checkers;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;

import ocsf.server.ConnectionToClient;

public class CheckersGame {
	//these will be used to color the cells of the board (don't know if this matters for the server yet)
	private List<Integer> oddStartCells = Arrays.asList(new Integer[] {1,3,5,7});
	private List<Integer> evenStartCells = Arrays.asList(new Integer[] {0,2,4,6});
	//these will be used to identify which player is sending information
	private ConnectionToClient playerOne = null;
	private ConnectionToClient playerTwo = null;
	//these will be used to verify who's turn it should be
	private boolean playerOneTurn = false;
	private boolean playerTwoTurn = false;
	
	//will store boardcells and also will have row and columns
	private BoardCell[][] cells;
	private int rows = 8;
	private int columns = 8;
	
	//will be changed to true when a second player joins
	private boolean started = false;
	
	public void processClick() {
		
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
			started = true;
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
	public CheckersGame() {
		buildBoard();
	}
}
