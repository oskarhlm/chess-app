package pieces;

import java.util.List;

import board.*;
import gui.ChessBoardGUI;
import javafx.scene.image.ImageView;
import player.Player;
import utils.*;

abstract class Piece implements IPiece {
	/* Abstract class for all the different pieces.
	 * Contains, for instance, instructions for what to do when a the image of a piece 
	 * is clicked and moved, as well as a move(Board board, Position newPosition) method
	 * that moves the piece to a new square in the "backend" */
	
	private Color color;
	private Position position;
	private boolean hasMoved = false;
	String colorPrefix;
	List<IPiece> attackingPieces;
	final public char pieceLetter;
	private Player player;
	int squareSize = ChessBoardGUI.SQUARE_SIZE;
	double pieceX; 
	double pieceY; 
	ImageView image;
	Board board;
	
	Piece(Position position, Color color, char pieceLetter) {
		this.position = position;
		this.color = color;
		colorPrefix = (color == Color.WHITE) ? "w" : "b";
		this.pieceLetter = pieceLetter;
		
		pieceX = squareSize * (position.col + 0.1); 
		pieceY = squareSize * (position.row + 0.1);
		
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
	
	void mouseEventHandler(ImageView image) {
		image.setOnMousePressed(e -> {
			// TODO: Show legal moves?
			image.relocate(e.getSceneX() - squareSize*0.35, e.getSceneY() - squareSize*0.35);
		});
		
		image.setOnMouseDragged(e -> {
			image.relocate(e.getSceneX() - squareSize*0.35, e.getSceneY() - squareSize*0.35);
		});
		
		image.setOnMouseReleased(e -> {
			int newCol = (int) (e.getSceneX() / squareSize);
			int newRow = (int) (e.getSceneY() / squareSize);
			double newPieceX = squareSize * (newCol + 0.1);
			double newPieceY = squareSize * (newRow + 0.1);
			image.relocate(newPieceX, newPieceY);
			
			if (!board.tryMove(this, new Position(newRow, newCol))) {
				image.relocate(pieceX, pieceY);
			} else {
				pieceX = newPieceX;
				pieceY = newPieceY;
			}
		});
	}
	
	public void relocatePiece(Position position) {
		pieceX = squareSize * (position.col + 0.1);
		pieceY = squareSize * (position.row + 0.1);
		image.relocate(pieceX, pieceY);
	}
	
	public void move(Board board, Position newPosition) throws IllegalArgumentException {
		if (! this.getLegalMoves(board).contains(newPosition)) {
			throw new IllegalArgumentException(String.format("Error board:\n%s\nIllegal move! %s", board, newPosition));
		}
		
		board.getSquare(position).removePiece();
		
		if (board.getSquare(newPosition).getPiece() != null) {
			board.getSquare(newPosition).capturePieceOnSquare();
			System.out.println(String.format("Captured piece on %s", newPosition));
		}
	
		// Check if en passent capture
		if (this instanceof Pawn && position.col != newPosition.col && board.getSquare(newPosition).getPiece() == null) {
			Position enPassentPiecePosition = new Position(position.row, newPosition.col);
			System.out.println("en passent: " + enPassentPiecePosition);
			board.getSquare(enPassentPiecePosition).capturePieceOnSquare();
			board.getSquare(enPassentPiecePosition).removePiece();
			System.out.println(String.format("Captured piece on %s en passent", enPassentPiecePosition));
		}
		
		board.getSquare(newPosition).placePiece(this);
		if (this instanceof Pawn) this.setEnPassentPiece(board, position, newPosition);
		else board.setEnPassentPiece(null);
		position = newPosition;
		if (!this.hasMoved) this.hasMoved = true;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getColorPrefix() {
		return colorPrefix;
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
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void setHasMoved(Boolean bool) {
		hasMoved = bool;
	}

	public void setEnPassentPiece(Board board, Position oldPosition, Position newPosition) {};
	
	public void setBoard(Board board) {
		this.board = board;
	}
}
