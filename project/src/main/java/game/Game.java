package game;

import board.*;
import board.Board.GameType;
import gui.ChessBoardGUI;
import player.*;
import utils.Color;
import pieces.*;
import java.util.*;

public class Game {
	
	public enum GameState {
		NOT_STARTED,
		ONGOING,
		DRAWN,
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
	
	public static void main(String[] args) {
		Game game = new Game(GameType.CLASSIC_SETUP);
		
		Scanner sc = new Scanner(System.in);
		String moveInput = sc.nextLine();
		
		while (!moveInput.equals("quit")) {
			game.move(moveInput);
//			if (game.hasEnded()) break;
			moveInput = sc.nextLine();
		}

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
				gameState = (playerToMove.getColor() == Color.WHITE)
						? GameState.BLACK_VICTORIOUS : GameState.WHITE_VICTORIOUS;
			} else System.out.println("Check!");
		} else if (!hasLegalPieceMoves) {
			System.out.println("Stale mate!");
			gameState = GameState.DRAWN;
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
	}
}
