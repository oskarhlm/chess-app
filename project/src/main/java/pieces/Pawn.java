package pieces;

import java.util.List;

import board.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.*;

public class Pawn extends Piece {
	
	public Pawn(Position position, Color color) {
		super(position, color, 'p');
	}
	
	public void setImage() {
		String imagePath = String.format("/piece_sprites/%s_pawn_png_128px.png", colorPrefix);
		this.image = new ImageView(new Image(getClass().getResourceAsStream(imagePath), squareSize*0.8, squareSize*0.8, true, true));
		image.relocate(pieceX, pieceY);
		mouseEventHandler(image);
	}
	
	@Override
	public String toString() {
		return "|" + colorPrefix + "p|";
	}

	@Override
	public List<Position> getLegalMoves(Board board) {
		MoveValidator validator = new MoveValidator(board, this);
		validator.addPawnMoves();
		return validator.getLegalDestinations();
	}
	
	@Override
	public void setEnPassentPiece(Board board, Position oldPosition, Position newPosition) {
		if (Math.abs(oldPosition.row - newPosition.row) == 2) {
			board.setEnPassentPiece(this);
		} else board.setEnPassentPiece(null);
	}
	
	@Override
	public ImageView getImage() {
		return image;
	}
	
}
