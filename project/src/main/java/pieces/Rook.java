package pieces;

import java.util.List;

import board.Board;
import utils.*;

public class Rook extends Piece {

	public Rook(Position position, Color color) {
		super(position, color);
	}
	
	@Override
	public String toString() {
		return "|" + colorPrefix + "R|";
	}
	
	public static void main(String[] args) {
//		Rook test = new Rook(1, 2, Color.WHITE);
//		System.out.println(test.getColor());
	}

	@Override
	public List<Position> getLegalMoves(Board board) {
		MoveValidator validator = new MoveValidator(board, this);
		validator.addStraigths();
		return validator.getLegalDestinations();
	}

	
	 
}
