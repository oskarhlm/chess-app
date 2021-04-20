package utils;

import java.io.IOException;

import game.Game;

public interface ISaveAndLoadHandler {
	public void save(Game game, String filename) throws IOException;
	public Game load(String filename) throws IOException;
}
