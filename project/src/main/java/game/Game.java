package game;

import board.*;
import player.*;
import pieces.*;
import utils.Position;
import java.util.*;

public class Game {
	
	private static final String List = null;
	private Board board;
	
	public Game() {
		board = new Board();
		System.out.println(board.toString());
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		Board board = game.board;
		board.move(board.getPiece(new Position(1, 3)), new Position(3, 3));
		board.move(board.getPiece(new Position(6, 2)), new Position(5, 2));
		board.move(board.getPiece(new Position(6, 1)), new Position(4, 1));
		System.out.println(board.getBlackKing().isInCheck(board));
		board.move(board.getPiece(new Position(0, 4)), new Position(4, 0));
		System.out.println(board.getBlackKing().isCheckMate(board));
		System.out.println(board);
	}
	
}
