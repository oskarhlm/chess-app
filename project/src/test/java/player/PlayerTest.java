package player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import board.Board.GameType;
import game.Game;
import utils.Color;

public class PlayerTest {
	
	@Test
	public void testPlayerColor() {
		Game game = new Game(GameType.CLASSIC_SETUP);
		Assertions.assertEquals(Color.WHITE, game.getBoard().getWhitePlayer().getColor());
		Assertions.assertEquals(Color.BLACK, game.getBoard().getBlackPlayer().getColor());
	}
	
	@Test
	public void testPlayerKing() {
		Game game = new Game(GameType.CLASSIC_SETUP);
		Player white = game.getBoard().getWhitePlayer();
		Player black = game.getBoard().getBlackPlayer();
		Assertions.assertEquals(white.getKing(), game.getBoard().getWhiteKing());
		Assertions.assertEquals(black.getKing(), game.getBoard().getBlackKing());
	}
	
	@Test 
	public void testCorrectAmountOfPieces() {
		Game game = new Game(GameType.CLASSIC_SETUP);
		Player white = game.getBoard().getWhitePlayer();
		Player black = game.getBoard().getBlackPlayer();
		Assertions.assertEquals(16, white.getPieces().size());
		Assertions.assertEquals(16, black.getPieces().size());
		
		game.move("e4");
		game.move("d5");
		game.move("exd5");
		Assertions.assertEquals(16, white.getPieces().size());
		Assertions.assertEquals(15, black.getPieces().size());
	}
	
}
