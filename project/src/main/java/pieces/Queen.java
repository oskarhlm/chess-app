package pieces;

import java.util.List;

import board.Board;
import utils.*;

public class Queen extends Piece {

	public Queen(Position position, Color color) {
		super(position, color);
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
	
}
