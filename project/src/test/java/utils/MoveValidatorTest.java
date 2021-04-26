package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Board;
import board.Board.GameType;
import game.Game;
import pieces.*;

public class MoveValidatorTest {
	
	Game game;
	Board board;
	
	@BeforeEach
	public void setup() {
		game = new Game(GameType.CLASSIC_SETUP);
		board = game.getBoard();
	}
	
	@Test
	public void testKnightMoves() {
		IPiece knight = board.getPiece(Board.algNotToPosition("g1"));
		Assertions.assertEquals(2, knight.getLegalMoves(board).size());
		
		game.move("Nf3");
		Assertions.assertEquals(5, knight.getLegalMoves(board).size());
		Assertions.assertTrue(knight.getLegalMoves(board).contains(Board.algNotToPosition("e5")));
		Assertions.assertFalse(knight.getLegalMoves(board).contains(Board.algNotToPosition("d2")));
	}
	
	@Test
	public void testPawnMoves() {
		IPiece pawn = board.getPiece(Board.algNotToPosition("c2"));
		Assertions.assertEquals(2, pawn.getLegalMoves(board).size());
		game.move("c4");
		Assertions.assertEquals(1, pawn.getLegalMoves(board).size());
		game.move("b5");
		// Checking for capture on pawn on b5
		Assertions.assertTrue(pawn.getLegalMoves(board).contains(Board.algNotToPosition("b5")));
		game.move("c5");
		game.move("d5");
		// Checking if pawn on d5 can be captured en passent
		Assertions.assertEquals(board.getPiece(Board.algNotToPosition("d5")), board.getEnPassentPiece());
		// Checking if white pawn on c5 can capture black pawn on d5 by going to d6
		Assertions.assertTrue(pawn.getLegalMoves(board).contains(Board.algNotToPosition("d6")));
		game.move("Nf3");
		// Checking if pawn on d5 no longer can be captured en passent
		Assertions.assertFalse(board.getPiece(Board.algNotToPosition("d5")) == board.getEnPassentPiece());
		game.move("e6");
		// Checking that en passent capture is no longer possible
		Assertions.assertFalse(pawn.getLegalMoves(board).contains(Board.algNotToPosition("d6")));
		// Checking that pawn on e6 no longer can move two squares ahead
		IPiece pe6 = board.getPiece(Board.algNotToPosition("e6"));
		Assertions.assertFalse(pe6.getLegalMoves(board).contains(Board.algNotToPosition("e4")));
	}
	
	@Test
	public void testBishopMoves() {
		IPiece bishop = board.getPiece(Board.algNotToPosition("c1"));
		Assertions.assertEquals(0, bishop.getLegalMoves(board).size());
		
		board.move("d4");
		Assertions.assertEquals(5, bishop.getLegalMoves(board).size());
		Assertions.assertTrue(bishop.getLegalMoves(board).contains(Board.algNotToPosition("h6")));
		Assertions.assertFalse(bishop.getLegalMoves(board).contains(Board.algNotToPosition("b2")));
	}
	
	@Test 
	public void testQueenMoves() {
		IPiece queen = board.getPiece(Board.algNotToPosition("d1"));
		Assertions.assertEquals(0, queen.getLegalMoves(board).size());
		
		board.move("d4");
		board.move("d5");
		board.move("c3");
		Assertions.assertEquals(5, queen.getLegalMoves(board).size());
		Assertions.assertTrue(queen.getLegalMoves(board).contains(Board.algNotToPosition("d3")));
		Assertions.assertTrue(queen.getLegalMoves(board).contains(Board.algNotToPosition("a4")));
		Assertions.assertFalse(queen.getLegalMoves(board).contains(Board.algNotToPosition("d4")));
	}
	
	private void setupCastling() {
		board.move("e4");
		board.move("e5");
		board.move("Bc4");
		board.move("d6");
		board.move("Nf3");
		board.move("c6");
	}
	
	@Test
	public void testLegalCastling() {
		IPiece king = board.getPiece(Board.algNotToPosition("e1"));
		Assertions.assertEquals(0, king.getLegalMoves(board).size());
		
		setupCastling();
		Assertions.assertEquals(3, king.getLegalMoves(board).size());
		
		// Checking castling
		IPiece rook = board.getPiece(Board.algNotToPosition("h1"));
		board.move("Kg1");
		Assertions.assertEquals(king, board.getPiece(Board.algNotToPosition("g1")));
		Assertions.assertEquals(rook, board.getPiece(Board.algNotToPosition("f1")));
	}
	
	@Test 
	public void testIllegalCastlingDueToCheck() {
		IPiece king = board.getPiece(Board.algNotToPosition("e1"));
		IPiece rook = board.getPiece(Board.algNotToPosition("h1"));
		
		setupCastling();
		board.move("g4");
		board.move("Qg5");
		board.move("c3");
		board.move("Qxg4");
		board.move("Kg1");

		// Checking that the king did not castle (because it would have castled into check)
		Assertions.assertEquals(king, board.getPiece(Board.algNotToPosition("e1")));
		Assertions.assertEquals(rook, board.getPiece(Board.algNotToPosition("h1")));
	}
	
	@Test
	public void testIllegalCastlingDueToKingAllreadyMoved() {
		IPiece king = board.getPiece(Board.algNotToPosition("e1"));
		IPiece rook = board.getPiece(Board.algNotToPosition("h1"));
		
		setupCastling();
		board.move("Ke2");
		board.move("Be7");
		board.move("Ke1");
		board.move("Nf6");
		board.move("Kg1");
		
		// Checking that the king did not castle 
		Assertions.assertEquals(king, board.getPiece(Board.algNotToPosition("e1")));
		Assertions.assertEquals(rook, board.getPiece(Board.algNotToPosition("h1")));
	}
	
	@Test
	public void testIllegalCastlingDueToRookAllreadyMoved() {
		game = new Game(GameType.PROMOTION);
		board = game.getBoard();
		IPiece whiteKing = game.getBoard().getPiece(Board.algNotToPosition("f6"));
		IPiece blackKing = game.getBoard().getPiece(Board.algNotToPosition("c5"));
		
		// Checking that the kings are where they are supposed to be
		Assertions.assertEquals(whiteKing, board.getPiece(Board.algNotToPosition("f6")));
		Assertions.assertEquals(blackKing, board.getPiece(Board.algNotToPosition("c5")));
		
		board.move("Ke5");
		board.move("Kd5");
		// Last move should not be legal because the black king is on e5
		
		// Checking that the white black is on e5 and that the white king hasn't moved form c5
		Assertions.assertEquals(whiteKing, board.getPiece(Board.algNotToPosition("e5")));
		Assertions.assertEquals(blackKing, board.getPiece(Board.algNotToPosition("c5")));
	}
	
	@Test
	public void testKingsCannotStandNextToEachother() {
		IPiece king = board.getPiece(Board.algNotToPosition("e1"));
		IPiece rook = board.getPiece(Board.algNotToPosition("h1"));
		
		setupCastling();
		board.move("Rf1");
		board.move("Be7");
		board.move("Rh1");
		board.move("Nf6");
		board.move("Kg1");
		
		// Checking that the king did not castle 
		Assertions.assertEquals(king, board.getPiece(Board.algNotToPosition("e1")));
		Assertions.assertEquals(rook, board.getPiece(Board.algNotToPosition("h1")));
	}
	
	@Test
	public void testRookMoves() {
		IPiece rook = board.getPiece(Board.algNotToPosition("h1"));

		board.move("h4");
		board.move("d5");
		Assertions.assertEquals(2, rook.getLegalMoves(board).size());
		board.move("Rh3");
		board.move("Nf6");
		Assertions.assertEquals(9, rook.getLegalMoves(board).size());
		board.move("Rb3");
		Assertions.assertEquals(rook, board.getPiece(Board.algNotToPosition("b3")));

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
