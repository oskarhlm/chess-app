package game;

import board.*;
import player.*;
import pieces.*;
import utils.Position;

public class Game {
	
	private Board board;
	
	public Game() {
		board = new Board();
		System.out.println(board.toString());
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		Board board = game.board;
		game.move(board.getPiece(new Position(1, 3)), new Position(3, 3));
		game.move(board.getPiece(new Position(6, 2)), new Position(5, 2));
		System.out.println(board.getBlackKing().isInCheck(board));
		game.move(board.getPiece(new Position(0, 4)), new Position(4, 0));
		System.out.println(board.getBlackKing().isInCheck(board));
	}
	
	private void move(IPiece piece, Position position) {
		piece.move(board, position);
		System.out.println("\n" + board.toString());
		System.out.println(board.getEnPassentPiece());
	}
	
}
