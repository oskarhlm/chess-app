package game;

import board.*;
import board.Board.GameType;
import gui.ChessBoardGUI;
import player.*;
import utils.Color;
import pieces.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Game implements Serializable {
	
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
	
	public Game(GameType gameType) {
		board = new Board(gameType);
		board.setGame(this);
		this.gameState = GameState.NOT_STARTED;
		System.out.println(board.toString());
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Game game = new Game(GameType.CLASSIC_SETUP);
		
		Scanner sc = new Scanner(System.in);
		String moveInput = sc.nextLine();
		
		while (!moveInput.equals("quit")) {
			game.move(moveInput);
			game.updateGameState();
			moveInput = sc.nextLine();
		}
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game.txt"));
		out.writeObject(game);
		out.close();

		sc.close();
	}
	
	public void updateGameState() {
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
	
	private void move(String algNot) {
		board.move(algNot);
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
		this.gameState = gameState;
		boardGUI.getGameController().gameStateChanged(gameState);
	}
}
