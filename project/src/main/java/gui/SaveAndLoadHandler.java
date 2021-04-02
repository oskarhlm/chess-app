package gui;

import board.Board;
import game.Game;
import pieces.Bishop;
import pieces.IPiece;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
import utils.Color;
import utils.Position;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SaveAndLoadHandler {
	
	public static void save(Game game, String fileName) throws IOException {
		Board board = game.getBoard();
		
		String playerToMoveOutput = (game.getBoard().getPlayerToMove().getColor() == Color.WHITE) 
				? "white to move" : "black to move";
		saveToFile(fileName, playerToMoveOutput, false);
		
		List<IPiece> allPieces = new ArrayList<>();
		allPieces.addAll(board.getWhitePlayer().getPieces());
		allPieces.addAll(board.getBlackPlayer().getPieces());
		
		for (IPiece piece : allPieces) {
			String outputText = piece.getColorPrefix() + piece.getPieceLetter() + piece.getPosition();
			saveToFile(fileName, outputText, true);
		}
	}
	
	private static void saveToFile(String fileName, String text, boolean append) throws IOException {
		File file = new File("src\\main\\resources\\saved_games\\" + fileName);
		FileWriter fw = new FileWriter(file, append);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(text);
		pw.close();
	}
	
	public static Game load(String fileName) throws IOException {
		String path = "src\\main\\resources\\saved_games\\" + fileName;
		String line = "";
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		Color playerToMoveColor = Color.WHITE;
		List<IPiece> pieces = new ArrayList<>();
		
		while ((line = br.readLine()) != null) {
			if (line.equals("white to move")) {
				playerToMoveColor = Color.WHITE;
			} else if (line.equals("black to move")) {
				playerToMoveColor = Color.BLACK;
			} else {
				char colorPrefix = line.charAt(0);
				Color pieceColor = (colorPrefix == 'w') ? Color.WHITE : Color.BLACK;
				
				char pieceLetter = line.charAt(1);
				Position piecePosition = Board.algNotToPosition(line.substring(2));
				
				if (pieceLetter == 'R') pieces.add(new Rook(piecePosition, pieceColor));
				else if (pieceLetter == 'N') pieces.add(new Knight(piecePosition, pieceColor));
				else if (pieceLetter == 'B') pieces.add(new Bishop(piecePosition, pieceColor));
				else if (pieceLetter == 'Q') pieces.add(new Queen(piecePosition, pieceColor));
				else if (pieceLetter == 'K') pieces.add(new King(piecePosition, pieceColor));
				else if (pieceLetter == 'p') pieces.add(new Pawn(piecePosition, pieceColor));
			}
		}
		
		Board board = new Board(pieces, playerToMoveColor);
		
		return new Game(board);
	}
	
	
	
	
	
	
	
	
	
}
