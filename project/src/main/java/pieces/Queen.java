package pieces;

import java.util.List;

import board.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.*;

public class Queen extends Piece {
	
	public Queen(Position position, Color color) {
		super(position, color, 'Q');
	}
	
	public void setImage() {
		String imagePath = String.format("/piece_sprites/%s_queen_png_128px.png", colorPrefix);
		this.image = new ImageView(new Image(getClass().getResourceAsStream(imagePath), squareSize*0.8, squareSize*0.8, true, true));
		image.relocate(pieceX, pieceY);
		mouseEventHandler(image);
	}
	
	@Override
	public String toString() {
		return "|" + colorPrefix + "Q|";
	}

	@Override
	public List<Position> getLegalMoves(Board board) {
		MoveValidator validator = new MoveValidator(board, this);
		validator.addDiagonals();
		validator.addStraigths();
		return validator.getLegalDestinations();
	}
	
	@Override
	public ImageView getImage() {
		return image;
	}
	
}
