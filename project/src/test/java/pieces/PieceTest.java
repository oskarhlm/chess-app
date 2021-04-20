package pieces;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Board;
import board.Board.GameType;
import game.Game;
import utils.Color;

public class PieceTest {
	
	Game game;
	
	@BeforeEach
	private void setup() {
		game = new Game(GameType.CLASSIC_SETUP);
	}
	
	@Test
	public void testHasMoved() {
		List<IPiece> pieces = new ArrayList<>();
		pieces.addAll(game.getBoard().getWhitePlayer().getPieces());
		pieces.addAll(game.getBoard().getBlackPlayer().getPieces());
		
		// Looping through all pieces to see that no piece has moved
		for (IPiece piece : pieces) {
			Assertions.assertFalse(piece.hasMoved());
		}
		
		// Moving a piece (for instance the knight on g1) and seeing that it now says it has been moved
		IPiece knight = game.getBoard().getPiece(Board.algNotToPosition("g1"));
		game.move("Nf3");
		Assertions.assertTrue(knight.hasMoved());
	}
	
	@Test
	public void testPieceColor() {
		// Checking color of some pieces
		Assertions.assertEquals(Color.WHITE, game.getBoard().getPiece(Board.algNotToPosition("c1")).getColor());
		Assertions.assertEquals(Color.WHITE, game.getBoard().getPiece(Board.algNotToPosition("d2")).getColor());
		Assertions.assertEquals(Color.BLACK, game.getBoard().getPiece(Board.algNotToPosition("h8")).getColor());
		Assertions.assertEquals(Color.BLACK, game.getBoard().getPiece(Board.algNotToPosition("f7")).getColor());
	}
	
	@Test
	public void testPieceType() {
		// Checking type of some pieces, and simultaneously their position on the board
		Assertions.assertEquals(Bishop.class, game.getBoard().getPiece(Board.algNotToPosition("c1")).getClass());
		Assertions.assertEquals(King.class, game.getBoard().getPiece(Board.algNotToPosition("e8")).getClass());
		Assertions.assertEquals(Pawn.class, game.getBoard().getPiece(Board.algNotToPosition("h7")).getClass());
		Assertions.assertEquals(Queen.class, game.getBoard().getPiece(Board.algNotToPosition("d1")).getClass());
		Assertions.assertEquals(Rook.class, game.getBoard().getPiece(Board.algNotToPosition("a1")).getClass());
	}
	
	@Test
	public void testPiecePosition() {
		// Looking at pawn on e2
		IPiece pawn = game.getBoard().getPiece(Board.algNotToPosition("e2"));
		Assertions.assertEquals(Board.algNotToPosition("e2"), pawn.getPosition());
		
		// Moving pawn and checking that its position updates
		game.move("e4");
		Assertions.assertEquals(Board.algNotToPosition("e4"), pawn.getPosition());
	}
	

}


















