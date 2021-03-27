package pieces;

import java.util.Arrays;
import java.util.List;
import pieces.*;
import board.Board;
import utils.*;

public class King extends Piece {
	
	public King(Position position, Color color) {
		super(position, color, 'K');
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
		
		if (board.getPlayerToMove() == this.getPlayer()) {
			validator.addCastling();
		}
		
		List<Position> legalDestinations = validator.getLegalDestinations();
		accountForOpponentKingVicinity(board, legalDestinations);
		
		return validator.getLegalDestinations();
	}
	
	private void accountForOpponentKingVicinity(Board board, List<Position> destinations) {
		List<Integer> dirs = Arrays.asList(-1, 0, 1);
		Position opponentKingPosition = (this.getColor() == Color.WHITE) 
				? board.getBlackKing().getPosition() : board.getWhiteKing().getPosition();
		
		for (int i : dirs) {
			for (int j : dirs) {
				Position illegalDestination = new Position(opponentKingPosition.row + i, opponentKingPosition.col + j);
				
				if (destinations.contains(illegalDestination)) {
					destinations.remove(illegalDestination);
				}
			}
		}
	}
	
	private List<Position> removeIllegalMoves(Board board, List<Position> destinations) {
		Board boardCopy = new Board(board);
		
		for (Position destination : destinations) {
			boardCopy.getSquare(this.getPosition()).removePiece();
			boardCopy.getSquare(destination).placePiece(this);
			
			if (this.isInCheck(boardCopy)) {
				destinations.remove(destination);
			}
			
			boardCopy = new Board(board);
		}
		
		return destinations;
	}
	
	public boolean isCheck(Board board, Position kingPosition) {
		List<IPiece> opponentPieces = (this.getColor() == Color.WHITE) 
				? board.getBlackPlayer().getPieces() 
				: board.getWhitePlayer().getPieces();
		
		for (IPiece piece : opponentPieces) {
			if (piece instanceof King) continue;
			
			if (piece.getLegalMoves(board).contains(kingPosition)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isInCheck(Board board) {
		return isCheck(board, this.getPosition());
	}
	
	public boolean isCheckMate(Board board) {
		if (!isInCheck(board)) return false;
		
		for (Position possiblePosition : this.getLegalMoves(board)) {		
			if (!isCheck(board, possiblePosition)) return false;
		}
		
		Board boardCopy = new Board(board);
		
		List<IPiece> checkedPlayersPieces = (this.getColor() == Color.BLACK) 
				? boardCopy.getBlackPlayer().getPieces() 
				: boardCopy.getWhitePlayer().getPieces();
		
		for (IPiece piece : checkedPlayersPieces) {
			System.out.println(piece.getLegalMoves(boardCopy));

			for (Position move : piece.getLegalMoves(boardCopy)) {
				Position oldPosition = piece.getPosition();
				System.out.println(piece);
				System.out.println(piece.getLegalMoves(boardCopy));
				Boolean pieceHadNotMoved = piece.hasMoved();
				boardCopy.move(piece, move);
				piece.setHasMoved(pieceHadNotMoved);
				
				System.out.println("Copy: \n" + boardCopy);
				
				if (!isCheck(boardCopy, boardCopy.getBlackKing().getPosition()) && !(piece instanceof King)) {
					return false;
				} 
				
				System.out.println("Moving back");
				boardCopy.getSquare(piece.getPosition()).removePiece();
				piece.setPosition(oldPosition);
				boardCopy.getSquare(oldPosition).placePiece(piece);
				System.out.println("Copy: \n" + boardCopy);
			}
			
		}
		
		return true;
	}

}
