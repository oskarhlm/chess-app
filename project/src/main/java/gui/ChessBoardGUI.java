package gui;

import board.Square;
import board.Board.GameType;
import game.Game;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class ChessBoardGUI {
	/* Creates a pane that contains the actual chess board. */
	
	Pane board;
	public static final int SQUARE_SIZE = 70;

	private Group squareGroup = new Group();
	private Group pieceGroup = new Group();
	
	Game game;
	GameController controller;
	
	public ChessBoardGUI() {
		initialize(new Game(GameType.CLASSIC_SETUP));
	}
	
	public ChessBoardGUI(Game game) {
		initialize(game);
	}
	
	private void initialize(Game game) {
		board = new Pane();
		board.setPrefSize(SQUARE_SIZE * 8, SQUARE_SIZE * 8);
		board.getChildren().addAll(squareGroup, pieceGroup);
		
		this.game = game;
		game.setChessBoardGUI(this);
		
		Square[][] squares = game.getBoard().getSquares();
		
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
	
	public Group getPieceGroup() {
		return pieceGroup;
	}
	
	public Game getGame() {
		return game;
	}
	
	public GameController getGameController() {
		return controller;
	}
	
	public void setController(GameController controller) {
		this.controller = controller;
	}
}
