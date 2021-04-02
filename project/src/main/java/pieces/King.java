package pieces;

import java.util.Arrays;
import java.util.List;
import board.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.*;

public class King extends Piece {
	
	public King(Position position, Color color) {
		super(position, color, 'K');
	}
	
	public void setImage() {
		String imagePath = String.format("/piece_sprites/%s_king_png_128px.png", colorPrefix);
		this.image = new ImageView(new Image(getClass().getResourceAsStream(imagePath), squareSize*0.8, squareSize*0.8, true, true));
		image.relocate(pieceX, pieceY);
		mouseEventHandler(image);
	}
	
	@Override
	public String toString() {
		return "|" + colorPrefix + "K|";
	}
	
	@Override
	public List<Position> getLegalMoves(Board board) {
		MoveValidator validator = new MoveValidator(board, this);
		validator.addDiagonals();
		validator.addStraigths();
		
		if (board.getPlayerToMove() == this.getPlayer()) {
			validator.addCastling();
		}
		
		List<Position> legalDestinations = validator.getLegalDestinations();
		accountForOpponentKingVicinity(board, legalDestinations);
		
		return legalDestinations;
	}
	
	private void accountForOpponentKingVicinity(Board board, List<Position> destinations) {
		List<Integer> dirs = Arrays.asList(-1, 0, 1);
		Position opponentKingPosition = (this.getColor() == Color.WHITE) 
				? board.getBlackKing().getPosition() : board.getWhiteKing().getPosition();
		
		for (int i : dirs) {
			for (int j : dirs) {
				Position illegalDestination = new Position(opponentKingPosition.row + i, opponentKingPosition.col + j);
				
				if (destinations.contains(illegalDestination)) {
					destinations.remove(illegalDestination);
				}
			}
		}
	}
	
	public static boolean isCheck(Board board, King king) {
		MoveValidator validator = new MoveValidator(board, king);
		validator.addDiagonals();
		validator.addKnightJump();
		validator.addStraigths();
		
		return validator.isAttacked();
	}

	@Override
	public ImageView getImage() {
		return image;
	}

}
