package board;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import board.Board.GameType;
import pieces.Pawn;
import pieces.Queen;
import player.Player;
import utils.Position;

public class BoardTest {
	
	@Test
	public void testMovingWithAlgebraicNotation() {
		Board board = new Board(GameType.CLASSIC_SETUP);
		// Checking that there is no piece on e4
		Assertions.assertNull(board.getPiece(new Position(4, 4)));
		// Checking that there is a piece on e2
		Assertions.assertNotNull(board.getPiece(new Position(6, 4)));
		// Moving pawn from e2 to e4
		board.move("e4");
		// Checking that there is no piece on e2
		Assertions.assertNotNull(board.getPiece(new Position(4, 4)));
		// Checking that there is a piece on e4
		Assertions.assertNull(board.getPiece(new Position(6, 4)));
	}
	
	@Test
	public void testAlgebraicNotation() {
		/* Testing that move notation gives right destination square */
		Assertions.assertEquals(new Position(4, 4), Board.algNotToPosition("e4"));
		Assertions.assertEquals(new Position(5, 2), Board.algNotToPosition("Nc3"));
		Assertions.assertEquals(new Position(3, 3), Board.algNotToPosition("exd5"));
	}
	
	@Test
	public void testPromotion() {
		Board board = new Board(GameType.PROMOTION);
		
		// Check that black player has one pawn, but no queen
		Player black = board.getBlackPlayer();
		Assertions.assertEquals(0, black.getPieces().stream()
				.filter(piece -> piece.getClass().equals(Queen.class))
				.collect(Collectors.toList())
				.size());
		Assertions.assertEquals(1, black.getPieces().stream()
				.filter(piece -> piece.getClass().equals(Pawn.class))
				.collect(Collectors.toList())
				.size());
		
		// Promoting black pawn on f2 to a new queen
		board.move("pf1");
		
		// Check that black player now has one queen, but no pawn
		Assertions.assertEquals(1, black.getPieces().stream()
				.filter(piece -> piece.getClass().equals(Queen.class))
				.collect(Collectors.toList())
				.size());
		Assertions.assertEquals(0, black.getPieces().stream()
				.filter(piece -> piece.getClass().equals(Pawn.class))
				.collect(Collectors.toList())
				.size());
	}

}
