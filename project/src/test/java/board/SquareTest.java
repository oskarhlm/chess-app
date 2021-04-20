package board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Board.GameType;
import utils.Color;

public class SquareTest {
	
	private Board board;
	
	@BeforeEach
	public void setup() {
		board = new Board(GameType.CLASSIC_SETUP);
	}
	
	@Test
	public void testMovingPieceToSquare() {
		Square d4 = board.getSquare(Board.algNotToPosition("d4"));
		Assertions.assertNull(d4.getPiece());
		// Moving pawn to d4
		board.move("d4");
		Assertions.assertNotNull(d4.getPiece());
	}
	
	@Test
	public void testCaptures() {
		Square d5 = board.getSquare(Board.algNotToPosition("d5"));
		board.move("e4");
		Assertions.assertNull(d5.getPiece());
		board.move("d5");
		Assertions.assertEquals(Color.BLACK, d5.getPiece().getColor());
		board.move("exd5");
		Assertions.assertEquals(Color.WHITE, d5.getPiece().getColor());
	}

}
