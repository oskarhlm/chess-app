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
	final public char pieceLetter;
	
	Piece(Position position, Color color, char pieceLetter) {
		this.position = position;
		this.color = color;
		colorPrefix = (color == Color.WHITE) ? "w" : "b";
		this.pieceLetter = pieceLetter;
		
		if (this instanceof Pawn) {
			if (color == Color.BLACK && position.row > 1) {
				hasMoved = true;
			} else if (color == Color.WHITE && position.row < 6) {
				hasMoved = true;
			}
		}
	}
	
	Piece(Piece piece) {
		this.position = piece.getPosition();
		this.color = piece.getColor();
		colorPrefix = (piece.getColor() == Color.WHITE) ? "w" : "b";
		this.pieceLetter = piece.getPieceLetter();
	}
	
	public void move(Board board, Position newPosition) throws IllegalArgumentException {
		if (! this.getLegalMoves(board).contains(newPosition)) {
			throw new IllegalArgumentException(String.format("Error board:\n%s\nIllegal move! %s", board, newPosition));
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
	
	public char getPieceLetter() {
		return pieceLetter;
	}

	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void setHasMoved(Boolean bool) {
		hasMoved = bool;
	}

	public void setEnPassentPiece(Board board, Position oldPosition, Position newPosition) {};
	
}
