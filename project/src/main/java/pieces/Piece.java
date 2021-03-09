package pieces;

import java.util.List;

import board.*;
import utils.*;

abstract class Piece implements IPiece {
	
	private Color color;
	private Position position;
	private boolean hasMoved = false;
	String colorPrefix;
	List<IPiece> attackingPieces;
	
	Piece(Position position, Color color) {
		this.position = position;
		this.color = color;
		colorPrefix = (color == Color.WHITE) ? "w" : "b";
	}
	
	public void move(Board board, Position newPosition) throws IllegalArgumentException {
		if (! this.getLegalMoves(board).contains(newPosition)) {
			throw new IllegalArgumentException("Illegal move!");
		}
		board.getSquare(position).removePiece();
		board.getSquare(newPosition).placePiece(this);
		if (this instanceof Pawn) this.setEnPassentPiece(board, position, newPosition);
		else board.setEnPassentPiece(null);
		position = newPosition;
		if (!this.hasMoved) this.hasMoved = true;
	}
	
	public Color getColor() {
		return color;
	}

	public Position getPosition() {
		return position;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}

	public void setEnPassentPiece(Board board, Position oldPosition, Position newPosition) {};
	
}
