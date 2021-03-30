package pieces;

import java.util.List;

import board.Board;
import gui.ChessApp;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.*;

public class Bishop extends Piece {
	
	public Bishop(Position position, Color color) {
		super(position, color, 'B');
		
		String imagePath = String.format("/piece_sprites/%s_bishop_png_128px.png", colorPrefix);
		this.image = new ImageView(new Image(getClass().getResourceAsStream(imagePath), squareSize*0.8, squareSize*0.8, true, true));
		image.relocate(pieceX, pieceY);
		mouseEventHandler(image);
	}
	
	@Override
	public String toString() {
		return "|" + colorPrefix + "B|";
	}

	@Override
	public List<Position> getLegalMoves(Board board) {
		MoveValidator validator = new MoveValidator(board, this);
		validator.addDiagonals();
		return validator.getLegalDestinations();
	}
	
	public ImageView getImage() {
		return image;
	}
	
}
