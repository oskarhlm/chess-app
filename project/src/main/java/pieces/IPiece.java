package pieces;

import java.util.List;

import board.Board;
import utils.*;

public interface IPiece {
	void move(Board board, Position newPosition);
	Color getColor();
	Position getPosition();
	boolean hasMoved();
	List<Position> getLegalMoves(Board board);
	void setEnPassentPiece(Board board, Position oldPosition, Position newPosition);
}
