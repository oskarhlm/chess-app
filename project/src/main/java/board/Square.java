package board;

import gui.ChessBoardGUI;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.*;
import utils.Position;

public class Square extends Rectangle {
	/* Class for a square on a board */
	
	private IPiece piece;
	private Position position;
	private int SIZE = ChessBoardGUI.SQUARE_SIZE;
	private Board board;
	
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
		if (board.getGame() != null && board.getGame().getChessBoardGUI() != null) {
			board.getGame().getChessBoardGUI().getPieceGroup().getChildren().remove(piece.getImage());
		}
		
		// Remove piece from the player who had it
		piece.getPlayer().getPieces().remove(piece);
		
		// Removal from "backend board" is done from the removePiece() method
	}
	
	@Override
	public String toString() {
		return (this.getPiece() == null) ? "|__|" : piece.toString();
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Board getBoard() {
		return this.board;
	}
}
