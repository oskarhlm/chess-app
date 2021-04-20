package game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import board.Board;
import board.Board.GameType;
import game.Game.GameState;
import player.Player;
import utils.Color;

public class GameTest {
	
	@Test
	public void testCheckmate() {
		Game game = new Game(GameType.CLASSIC_SETUP);
		Assertions.assertEquals(GameState.NOT_STARTED, game.getGameState());
		game.move("f3");
		Assertions.assertEquals(GameState.ONGOING, game.getGameState());
		game.move("e5");
		game.move("g4");
		game.move("Qh4");
		Assertions.assertEquals(GameState.BLACK_VICTORIOUS, game.getGameState());
	}
	
	@Test 
	public void testStaleMate() {
		Game game = new Game(GameType.STALE_MATE);
		game.move("Ra1");
		Assertions.assertEquals(GameState.ONGOING, game.getGameState());
		game.move("Na7");
		game.move("Ra2");
		Assertions.assertEquals(GameState.STALE_MATE, game.getGameState());
	}
	
	@Test 
	public void testPlayerTurn() {
		Game game = new Game(GameType.CLASSIC_SETUP);
		Assertions.assertEquals(game.getBoard().getWhitePlayer() ,game.getBoard().getPlayerToMove());
		game.move("e4");
		Assertions.assertEquals(game.getBoard().getBlackPlayer() ,game.getBoard().getPlayerToMove());
		game.move("e5");
		Assertions.assertEquals(game.getBoard().getWhitePlayer() ,game.getBoard().getPlayerToMove());
	}
	
	@Test
	public void testCaptures() {
		Game game = new Game(GameType.CLASSIC_SETUP);
		Player blackPlayer = game.getBoard().getBlackPlayer();
		
		game.move("d4");
		game.move("e5");
		Assertions.assertEquals(Color.BLACK, game.getBoard().getPiece(Board.algNotToPosition("e5")).getColor());
		Assertions.assertEquals(16, blackPlayer.getPieces().size());
		game.move("de5");
		Assertions.assertEquals(Color.WHITE, game.getBoard().getPiece(Board.algNotToPosition("e5")).getColor());
		// Checking that the black players piece count decremented
		Assertions.assertEquals(15, blackPlayer.getPieces().size());
	}
	
}
