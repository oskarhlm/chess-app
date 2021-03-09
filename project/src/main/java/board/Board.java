package board;

import pieces.*;
import utils.*;
import player.*;


public class Board {
	
	Square[][] squares = new Square[8][8];
	private Pawn enPassentPiece;
	private Player whitePlayer;
	private Player blackPlayer;
	
	public Board() {
		this.newGame();
	}
	
	public void newGame() {
		whitePlayer = new Player(Color.WHITE);
		blackPlayer = new Player(Color.BLACK);
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j] = new Square();
			}
		}
		
		// Adding white pieces
		squares[0][0].placePiece(new Rook(new Position(0, 0), Color.WHITE));
		squares[0][7].placePiece(new Rook(new Position(0, 7), Color.WHITE));
		squares[0][1].placePiece(new Knight(new Position(0, 1), Color.WHITE));
		squares[0][6].placePiece(new Knight(new Position(0, 6), Color.WHITE));
		squares[0][2].placePiece(new Bishop(new Position(0, 2), Color.WHITE));
		squares[0][5].placePiece(new Bishop(new Position(0, 5), Color.WHITE));
		squares[0][4].placePiece(new Queen(new Position(0, 4), Color.WHITE));
		squares[0][3].placePiece(new King(new Position(0, 3), Color.WHITE));
		
		for (int i = 0; i < 8; i++) {
			squares[1][i].placePiece(new Pawn(new Position(1, i), Color.WHITE));
		}
		
		// Adding black pieces
		squares[7][0].placePiece(new Rook(new Position(7, 0), Color.BLACK));
		squares[7][7].placePiece(new Rook(new Position(7, 7), Color.BLACK));
		squares[7][1].placePiece(new Knight(new Position(7, 1), Color.BLACK));
		squares[7][6].placePiece(new Knight(new Position(7, 6), Color.BLACK));
		squares[7][2].placePiece(new Bishop(new Position(7, 2), Color.BLACK));
		squares[7][5].placePiece(new Bishop(new Position(7, 5), Color.BLACK));
		squares[7][4].placePiece(new Queen(new Position(7, 4), Color.BLACK));
		squares[7][3].placePiece(new King(new Position(7, 3), Color.BLACK));
		
		for (int i = 0; i < 8; i++) {
			squares[6][i].placePiece(new Pawn(new Position(6, i), Color.BLACK));
		}
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				IPiece piece = this.getPiece(new Position(i, j));
				if (piece != null) {
					if (piece.getColor() == Color.WHITE) {
						whitePlayer.addPiece(piece);
					} else blackPlayer.addPiece(piece);
				}
			}
		}
		
	}
	
	@Override
	public String toString() {
		String output = "";
		for (Square[] row : squares) {
			for (Square square : row) {
				output += square.toString();
			}
			output += "\n";
		}
		return output;
	}
	
	public Square[][] getSquares() {
		return squares;
	}
	
	public Square getSquare(Position position) {
		return squares[position.row][position.col];
	}
	
	public IPiece getPiece(Position position) {
		return squares[position.row][position.col].getPiece();
	}
	
	public void setEnPassentPiece(Pawn pawn) {
		enPassentPiece = pawn;
	}
	
	public Pawn getEnPassentPiece() {
		return enPassentPiece;
	}
	
	public Player getWhitePlayer() {
		return whitePlayer;
	}
	
	public Player getBlackPlayer() {
		return blackPlayer;
	}
	
	public King getWhiteKing() {
		return getWhitePlayer().getKing();
	}
	
	public King getBlackKing() {
		return getBlackPlayer().getKing();
	}
	
	
}
