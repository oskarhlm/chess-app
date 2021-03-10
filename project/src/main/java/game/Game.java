package game;

import board.*;
import player.*;
import pieces.*;
import utils.Position;
import java.util.*;

public class Game {
	
	private Board board;
	
	public Game(String gameType) {
		board = new Board(gameType);
		System.out.println(board.toString());
	}
	
	public static void main(String[] args) {
		Game game = new Game("Classic setup");
		Board board = game.board;
		game.move("pe4");
		game.move("pd4");
		game.move("pd3");
		
		
//		board.move(board.getPiece(new Position(1, 3)), new Position(3, 3));
//		board.move(board.getPiece(new Position(6, 2)), new Position(5, 2));
//		board.move(board.getPiece(new Position(6, 1)), new Position(4, 1));
//		System.out.println(board.getBlackKing().isInCheck(board));
//		board.move(board.getPiece(new Position(0, 4)), new Position(4, 0));
//		System.out.println(board.getBlackKing().isCheckMate(board));
//		System.out.println(board);
//		
//		System.out.println(board.getWhiteKing().isCheckMate(board));
//		board.move(board.getPiece(new Position(2, 1)), new Position(0, 1));
//		System.out.println(board.getWhiteKing().isCheckMate(board));
	}
	
	private void move(String algNot) {
		board.move(algNot);
	}
}
