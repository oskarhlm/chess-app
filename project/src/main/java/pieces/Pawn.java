package pieces;

import java.util.List;

import board.Board;
import utils.*;

public class Pawn extends Piece {
	
	public Pawn(Position position, Color color) {
		super(position, color, 'p');
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
	
}
