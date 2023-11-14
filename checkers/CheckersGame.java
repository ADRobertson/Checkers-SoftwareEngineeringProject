package checkers;

import ocsf.server.ConnectionToClient;

public class CheckersGame {
	//these will be used to identify which player is sending information
	private ConnectionToClient playerOne = null;
	private ConnectionToClient playerTwo = null;
	//these will be used to verify who's turn it should be
	private boolean playerOneTurn = false;
	private boolean playerTwoTurn = false;
	
	//will store rules and boardCells
	private GameBoard gameBoard;
	
	public void setPlayer(ConnectionToClient player) {
		if (playerOne == null) {
			playerOne = player;
		}
		else if (playerTwo == null) {
			playerTwo = player;
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
	public CheckersGame() {
		
	}
}
