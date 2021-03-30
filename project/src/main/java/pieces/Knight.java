package pieces;

import java.util.List;

import board.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.*;

public class Knight extends Piece {
	
	public Knight(Position position, Color color) {
		super(position, color, 'N');
		
		String imagePath = String.format("/piece_sprites/%s_knight_png_128px.png", colorPrefix);
		this.image = new ImageView(new Image(getClass().getResourceAsStream(imagePath), squareSize*0.8, squareSize*0.8, true, true));
		image.relocate(pieceX, pieceY);
		mouseEventHandler(image);
	}
	
	@Override
	public String toString() {
		return "|" + colorPrefix + "N|";
	}

	@Override
	public List<Position> getLegalMoves(Board board) {
		MoveValidator validator = new MoveValidator(board, this);
		validator.addKnightJump();
		return validator.getLegalDestinations();
	}
	
	@Override
	public ImageView getImage() {
		return image;
	}
	
}
