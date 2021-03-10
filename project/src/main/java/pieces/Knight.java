package pieces;

import java.util.List;

import board.Board;
import utils.*;

public class Knight extends Piece {
	
	public Knight(Position position, Color color) {
		super(position, color, 'N');
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
	
}
