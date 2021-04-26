package game;

import board.*;
import board.Board.GameType;
import gui.ChessBoardGUI;
import player.*;
import utils.Color;
import pieces.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Game {
	/* Connects the GUI to the Board class and implements a GameState */
	
	public enum GameState {
		NOT_STARTED,
		ONGOING,
		DRAW_BY_AGREEMENT,
		STALE_MATE,
		WHITE_VICTORIOUS,
		BLACK_VICTORIOUS
	}
	
	private Board board;
	private ChessBoardGUI boardGUI;
	private GameState gameState;
	
	public Game(Board board, GameState gameState) {
		this.board = board;
		board.setGame(this);
		this.gameState = gameState;
		System.out.println(board);
	}
	
	public Game(GameType gameType) {
		board = new Board(gameType);
		board.setGame(this);
		this.gameState = GameState.NOT_STARTED;
		System.out.println(board);
	}
	
	public void updateGameState() {
		/* Updates the gameState. Is called whenever a change is made to the position */
		
		Player playerToMove = board.getPlayerToMove();
		List<IPiece> playerToMovePieces = playerToMove.getPieces();
		boolean hasLegalPieceMoves = false;
		
		for (IPiece piece : playerToMovePieces) {
			if (piece.getLegalMoves(board).size() > 0) {
				hasLegalPieceMoves = true;
			}
		}
		
		King playerKing = playerToMove.getKing();
		
		if (King.isCheck(board, playerKing)) {
			if (!hasLegalPieceMoves) {
				System.out.println("Check mate!");
				GameState newGameState = (playerToMove.getColor() == Color.WHITE)
						? GameState.BLACK_VICTORIOUS : GameState.WHITE_VICTORIOUS;
				setGameState(newGameState);
			} else System.out.println("Check!");
		} else if (!hasLegalPieceMoves) {
			System.out.println("Stale mate!");
			setGameState(GameState.STALE_MATE);
		} 
	}
	
	public void move(String algNot) {
		board.move(algNot);
		updateGameState();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setChessBoardGUI(ChessBoardGUI boardGUI) {
		this.boardGUI = boardGUI;
	}
	
	public ChessBoardGUI getChessBoardGUI() {
		return boardGUI;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameState(GameState gameState) {
		/* Sets the gameState and tells the GUI to update the gameState message that is 
		 * visible to the player ("Black has won!", "Stalemate!" etc.) */
		
		this.gameState = gameState;
		
		if (boardGUI != null) {
			boardGUI.getGameController().gameStateChanged(gameState);
		}
	}
}
