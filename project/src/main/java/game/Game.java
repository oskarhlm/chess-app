package game;

import board.*;
import board.Board.GameType;
import player.*;
import pieces.*;
import java.util.*;

public class Game {
	
	private Board board;
	
	public Game(GameType gameType) {
		board = new Board(gameType);
		System.out.println(board.toString());
	}
	
	public static void main(String[] args) {
		Game game = new Game(GameType.CLASSIC_SETUP);
		
		Scanner sc = new Scanner(System.in);
		String moveInput = sc.nextLine();
		
		while (!moveInput.equals("quit")) {
			game.move(moveInput);
			System.out.println("White king legal moves: " + game.board.getWhiteKing().getLegalMoves(game.board));
			System.out.println("Black king legal moves: " + game.board.getBlackKing().getLegalMoves(game.board));
			System.out.println(game.board.getEnPassentPiece());
			if (game.hasEnded()) break;
			moveInput = sc.nextLine();
		}

		sc.close();
	}
	
	private boolean hasEnded() {
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
				return true;
			} else System.out.println("Check!");
		} else if (!hasLegalPieceMoves) {
			System.out.println("Stale mate!");
			return true;
		} 
		
		return false;
	}
	
	private void move(String algNot) {
		board.move(algNot);
	}
}
