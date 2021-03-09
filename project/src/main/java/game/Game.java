package game;

import board.*;
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
		IPiece n = board.getPiece(new Position(7, 1));
		game.move(n, new Position(5, 2));
		game.move(n, new Position(3, 1));
		IPiece wp = board.getPiece(new Position(1, 2));
		game.move(wp, new Position(3, 2));
		game.move(wp, new Position(4, 2));
		IPiece bp = board.getPiece(new Position(6, 1));
		game.move(bp, new Position(4, 1));
//		game.move(n, new Position(2, 3));
		System.out.println(wp.getLegalMoves(board));
	}
	
	private void move(IPiece piece, Position position) {
		piece.move(board, position);
		System.out.println("\n" + board.toString());
		System.out.println(board.getEnPassentPiece());
	}
	
}
