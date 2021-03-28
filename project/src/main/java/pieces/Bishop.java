package pieces;

import java.util.List;

import board.Board;
import utils.*;

public class Bishop extends Piece {
	
	public Bishop(Position position, Color color) {
		super(position, color, 'B');
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
	
}
