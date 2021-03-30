package board;

import gui.ChessApp;
import gui.ChessBoard;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.*;
import utils.Position;

public class Square extends Rectangle {
	
	private IPiece piece;
	private Position position;
	private int SIZE = ChessBoard.SQUARE_SIZE;
	
	public Square(int row, int col) {
		this.position = new Position(row, col);
		
		setHeight(SIZE);
		setWidth(SIZE);
		relocate(row * SIZE, col * SIZE);
		
		boolean dark = (row + col) % 2 != 0; 
		setFill(dark ? Color.BROWN : Color.BEIGE);
	}
	
	public void placePiece(IPiece piece) {
		this.piece = piece;
	}
	
	public void removePiece() {
		piece = null;
	}
	
	public IPiece getPiece() {
		return piece;
	}
	
	public void capturePieceOnSquare() {
		piece.getPlayer().getPieces().remove(piece);
	}
	
	@Override
	public String toString() {
		return (this.getPiece() == null) ? "|__|" : piece.toString();
	}
	
	public Position getPosition() {
		return position;
	}
}
