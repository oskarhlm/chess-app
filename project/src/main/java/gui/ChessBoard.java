package gui;

import board.Square;
import board.Board.GameType;
import game.Game;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class ChessBoard {
	
	Pane board;
	public static final int SQUARE_SIZE = 70;

	private Group squareGroup = new Group();
	private Group pieceGroup = new Group();
	
	public ChessBoard() {
		board = new Pane();
		board.setPrefSize(SQUARE_SIZE * 8, SQUARE_SIZE * 8);
		board.getChildren().addAll(squareGroup, pieceGroup);
		
		Game newGame = new Game(GameType.CLASSIC_SETUP);
		Square[][] squares = newGame.getBoard().getSquares();
		
		for (Square[] row : squares) {
			for (Square square : row) {
				squareGroup.getChildren().add(square);
				if (square.getPiece() != null) {
					pieceGroup.getChildren().add(square.getPiece().getImage());
				}
			}
		}
	}
	
	public Pane getBoard() { 
		return board;
	}
	
}
