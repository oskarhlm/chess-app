package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import board.Board;
import board.Square;
import pieces.*;

public class MoveValidator {
	
	private Position position;
	private Board board;
	private List<Position> legalDestinations = new ArrayList<Position>();
	private boolean isAttacked = false;
	private IPiece piece;
	private Integer multiplier[];
	private Integer dir[] = new Integer[] { -1, 1 };
	
	public MoveValidator(Board board, IPiece piece) {
		this.board = board;
		this.piece = piece;
		this.position = piece.getPosition();
		this.multiplier = new Integer[] { 1, 2, 3, 4, 5, 6, 7 };
	}
	
	public boolean isAttacked() {
		return isAttacked;
	}
	
	public void addStraigths() {
		for (int i : dir) {
			for (int mult : multiplier) {
				int row = position.row + i * mult;
				int col = position.col;
				Position squarePosition = new Position(row, col);
				
				if (row < 0 || row > 7 || col < 0 || col > 7) {
					break;
				} else if (board.getPiece(squarePosition) == null) {
					if (!(piece instanceof King && mult > 1)) legalDestinations.add(squarePosition);
				} else if (board.getPiece(squarePosition).getColor() == piece.getColor()) {
					break;
				} else {
					if (!(piece instanceof King && mult > 1)) legalDestinations.add(squarePosition);
					IPiece pieceOnSquare = board.getPiece(squarePosition);
					
					if (pieceOnSquare instanceof Queen || pieceOnSquare instanceof Rook) {
						isAttacked = true;
					}
					
					break;
				}
			}
		}
		
		for (int i : dir) {
			for (int mult : multiplier) {
				int row = position.row;
				int col = position.col + i * mult;
				Position squarePosition = new Position(row, col);
				
				if (row < 0 || row > 7 || col < 0 || col > 7) {
					break;
				} else if (board.getPiece(squarePosition) == null) {
					if (!(piece instanceof King && mult > 1)) legalDestinations.add(squarePosition);
				} else if (board.getPiece(squarePosition).getColor() == piece.getColor()) {
					break;
				} else {
					if (!(piece instanceof King && mult > 1)) legalDestinations.add(squarePosition);
					IPiece pieceOnSquare = board.getPiece(squarePosition);
					
					if (pieceOnSquare instanceof Queen || pieceOnSquare instanceof Rook) {
						isAttacked = true;
					}
					
					break;
				}
			}
		}
	}
	
	public void addDiagonals() {
		int pawnAttackRow = (piece.getColor() == Color.WHITE) ?  position.row - 1 : position.row + 1;
		
		for (int i : dir) {
			for (int j : dir) {
				for (int mult : multiplier) {
					
					int row = position.row + i * mult;
					int col = position.col + j * mult;
					Position squarePosition = new Position(row, col);
					
					if (row < 0 || row > 7 || col < 0 || col > 7) {
						break;
					} else if (board.getPiece(squarePosition) == null) {
						if (!(piece instanceof King && mult > 1)) legalDestinations.add(squarePosition);
					} else if (board.getPiece(squarePosition).getColor() == piece.getColor()) {
						break;
					} else {
						if (!(piece instanceof King && mult > 1)) legalDestinations.add(squarePosition);
						IPiece pieceOnSquare = board.getPiece(squarePosition);
						
						if (pieceOnSquare instanceof Queen || pieceOnSquare instanceof Bishop
								|| (pieceOnSquare instanceof Pawn && row == pawnAttackRow)) {
							isAttacked = true;
						}
						
						break;
					}
				}
			}
		}
	}
	
	public void addKnightJump() {
		for (int i : Arrays.asList(1, 2)) {
			int j = (i == 1) ? 2 : 1;
			for (int k : dir) {
				for (int l : dir) {
					
					int row = position.row + i * k;
					int col = position.col + j * l;
					Position squarePosition = new Position(row, col);
					
					if (! (row < 0 || row > 7 || col < 0 || col > 7)) {
						if (board.getPiece(squarePosition) == null) {
							legalDestinations.add(squarePosition);
						} else if (board.getPiece(squarePosition).getColor() != piece.getColor()) {
							legalDestinations.add(squarePosition);
							IPiece pieceOnSquare = board.getPiece(squarePosition);
							
							if (pieceOnSquare instanceof Knight) {
								isAttacked = true;
							}
						}
					}
				}
			}
		}
	}

	public void addPawnMoves() {
		int steps = (piece.hasMoved()) ? 1 : 2;
		int stepDir = (piece.getColor() == Color.BLACK) ? 1 : -1;
		
		int i = 1;
		while (i <= steps) {
			int row = piece.getPosition().row + i * stepDir;
			int col = piece.getPosition().col;
			Position squarePosition = new Position(row, col);
			
			if (board.getPiece(squarePosition) == null) {
				legalDestinations.add(squarePosition);
			} else break;
			
			i++;
		}
		
		// Captures
		for (int j : dir) {
			int row = piece.getPosition().row + stepDir;
			int col = piece.getPosition().col + j;
			Position squarePosition = new Position(row, col);
			
			if (! (row < 0 || row > 7 || col < 0 || col > 7)) {
				if (board.getPiece(squarePosition) != null) {
					if (board.getPiece(squarePosition).getColor() != piece.getColor()) {
						legalDestinations.add(squarePosition);
					}
				}
			}
			
			// TODO: Does not capture on right square
			Position enPassentSquare = new Position(piece.getPosition().row, col);
			
			if (! (row < 0 || row > 7 || col < 0 || col > 7)) {
				if (board.getPiece(enPassentSquare) != null) {
					IPiece pieceOnSquare = board.getPiece(enPassentSquare);
					if (pieceOnSquare.getColor() != piece.getColor() && pieceOnSquare == board.getEnPassentPiece()) {
						legalDestinations.add(new Position(pieceOnSquare.getPosition().row + stepDir, pieceOnSquare.getPosition().col));
					}
				}
			}
		}
	}
	
	public void addCastling() {
		if (!(this.piece instanceof King) || piece.hasMoved()) return;
		
		for (int direction : dir) {
			Board boardCopy = new Board(board);
			King king = (piece.getColor() == Color.WHITE) 
					? boardCopy.getWhiteKing() : boardCopy.getBlackKing();
			if (King.isCheck(boardCopy, king)) return;
			int steps = 0;
			
			while (steps != 2) {
				Position castlingStep = new Position(king.getPosition().row, king.getPosition().col + direction);
				if (! king.getLegalMoves(boardCopy).contains(castlingStep)) {
					break;
				} else {
					king.move(boardCopy, castlingStep);
					steps++;
				}
				
				if (steps == 2) {
					int rookColAdd = (direction == 1) ? 1 : -2;
					IPiece rook = board.getPiece(new Position(king.getPosition().row, king.getPosition().col + rookColAdd));
					if (rook instanceof Rook && !rook.hasMoved()) {
						legalDestinations.add(castlingStep);
					}
				}
			}
			
			boardCopy = new Board(board);
			king = (piece.getColor() == Color.WHITE) 
					? boardCopy.getWhiteKing() : boardCopy.getBlackKing();
		}
	}

	private void accountForChecks() {
		List<Position> destinationsToRemove = new ArrayList<>();
		
		for (Position destination : legalDestinations) {
			Board boardCopy = new Board(board);
			
			Square pieceSquare = boardCopy.getSquare(piece.getPosition());
			Square pieceDestination = boardCopy.getSquare(destination);
			
			pieceSquare.removePiece();
			pieceDestination.placePiece(piece);
			
			if (piece instanceof King) {
				if (piece.getColor() == Color.WHITE) {
					boardCopy.setWhiteKing(new King(destination, Color.WHITE));
				}  else {
					boardCopy.setBlackKing(new King(destination, Color.BLACK));
				}
			}
			
			King playerKing = (piece.getColor() == Color.WHITE) ? boardCopy.getWhiteKing() : boardCopy.getBlackKing();
			
			if (King.isCheck(boardCopy, playerKing)) {
				destinationsToRemove.add(destination);
			}
		}
		
		legalDestinations.removeAll(destinationsToRemove);
 	}
	
	public List<Position> getLegalDestinations() {
		accountForChecks();
		return legalDestinations;
	}
	
}
