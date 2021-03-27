package game;

import board.*;
import board.Board.GameType;
import player.*;
import pieces.*;
import utils.Color;
import utils.Position;
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
			if (!(piece instanceof King) && piece.getLegalMoves(board).size() > 0) {
				hasLegalPieceMoves = true;
			}
		}
		
		King playerKing = playerToMove.getKing();
		
		if (King.isCheckMate(board, playerKing)) {
			System.out.println("Check mate");
			return true;
		} else if (!hasLegalPieceMoves && playerKing.getLegalMoves(board).size() == 0) {
			System.out.println("Patt");
			return true;
		} return false;
	}
	
	private void move(String algNot) {
		board.move(algNot);
	}
}
