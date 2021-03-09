package pieces;

import java.util.List;
import pieces.*;
import board.Board;
import utils.*;

public class King extends Piece {

	public King(Position position, Color color) {
		super(position, color);
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
		return validator.getLegalDestinations();
	}
	
	public boolean isInCheck(Board board) {
		List<IPiece> opponentPieces = (this.getColor() == Color.WHITE) 
									? board.getBlackPlayer().getPieces() 
									: board.getWhitePlayer().getPieces();
		
		for (IPiece piece : opponentPieces) {
			if (piece.getLegalMoves(board).contains(this.getPosition())) {
				return true;
			}
		}
		
		return false;
	}
	
}
