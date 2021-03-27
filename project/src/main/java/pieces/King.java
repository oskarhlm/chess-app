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
		
		return legalDestinations;
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
	
	public static boolean isCheck(Board board, King king) {
		MoveValidator validator = new MoveValidator(board, king);
		validator.addDiagonals();
		validator.addKnightJump();
		validator.addStraigths();
		
		return validator.isAttacked();
	}
	
	public static boolean isCheckMate(Board board, King king) {
		if (!isCheck(board, king) || king.getLegalMoves(board).size() != 0) return false;
		
		List<IPiece> checkedPlayersPieces = (king.getColor() == Color.BLACK) 
				? board.getBlackPlayer().getPieces() 
				: board.getWhitePlayer().getPieces();
		
		for (IPiece piece : checkedPlayersPieces) {
			if (piece.getLegalMoves(board).size() != 0) {
				return false;
			}
		}
		
		return true;
	}
	
//	public boolean isCheckMate(Board board) {
//		King king = board.getPlayerToMove().getKing();
//		if (!isCheck(board, king) || king.getLegalMoves(board).size() != 0) return false;
//		
//		Board boardCopy = new Board(board);
//		List<IPiece> checkedPlayersPieces = (this.getColor() == Color.BLACK) 
//				? boardCopy.getBlackPlayer().getPieces() 
//				: boardCopy.getWhitePlayer().getPieces();
//		
//		for (IPiece piece : checkedPlayersPieces) {
//			Position oldPosition = piece.getPosition();
//
//			for (Position destination : piece.getLegalMoves(boardCopy)) {
//				boardCopy.getSquare(oldPosition).removePiece();
//				boardCopy.getSquare(destination).placePiece(piece);
//				
//				if (!isCheck(boardCopy, king)) {
//					return false;
//				} 
//				
//				boardCopy = new Board(board);
//			}
//		}
//		
//		return true;
//	}

}
