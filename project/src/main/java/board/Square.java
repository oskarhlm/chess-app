package board;

import pieces.*;

public class Square {
	
	private IPiece piece;
	
	public void placePiece(IPiece piece) {
		this.piece = piece;
	}
	
	public void removePiece() {
		piece = null;
	}
	
	public IPiece getPiece() {
		return piece;
	}
	
	@Override
	public String toString() {
		return (this.getPiece() == null) ? "|__|" : piece.toString();
	}
}
