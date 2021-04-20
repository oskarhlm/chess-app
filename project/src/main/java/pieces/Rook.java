package pieces;

import java.util.List;

import board.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.*;

public class Rook extends Piece {
	
	public Rook(Position position, Color color) {
		super(position, color, 'R');
	}
	
	public void setImage() {
		String imagePath = String.format("/piece_sprites/%s_rook_png_128px.png", colorPrefix);
		this.image = new ImageView(new Image(getClass().getResourceAsStream(imagePath), squareSize*0.8, squareSize*0.8, true, true));
		image.relocate(pieceX, pieceY);
		mouseEventHandler(image);
	}
	
	@Override
	public String toString() {
		return "|" + colorPrefix + "R|";
	}
	
	@Override
	public List<Position> getLegalMoves(Board board) {
		MoveValidator validator = new MoveValidator(board, this);
		validator.addStraigths();
		return validator.getLegalDestinations();
	}

	@Override
	public ImageView getImage() {
		return image;
	}
	
}
