package board;

import pieces.*;
import utils.*;
import player.*;
import java.util.*;


public class Board {
	
	Square[][] squares = new Square[8][8];
	private Pawn enPassentPiece;
	private Player whitePlayer = new Player(Color.WHITE);
	private Player blackPlayer = new Player(Color.BLACK);
	private Player playerToMove = whitePlayer;
	
	public Board(String gameType) {
		if (gameType.equals("Classic setup")) newGame();
		else if (gameType.equals("Staircase mate")) newBackRankMate();
	}
	
	public Board(Board board) {
//		this.whitePlayer = new Player(Color.WHITE);
//		this.blackPlayer = new Player(Color.BLACK);
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j] = new Square();
			}
		}
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				IPiece piece = board.getPiece(new Position(i, j));
				if (piece != null) {
					Color color = (piece.getColor() == Color.WHITE) ? Color.WHITE : Color.BLACK;
					Position pos = piece.getPosition();
					if (piece instanceof Rook) squares[pos.row][pos.col].placePiece(new Rook(pos, color));
					else if (piece instanceof Knight) squares[pos.row][pos.col].placePiece(new Knight(pos, color));
					else if (piece instanceof Bishop) squares[pos.row][pos.col].placePiece(new Bishop(pos, color));
					else if (piece instanceof Queen) squares[pos.row][pos.col].placePiece(new Queen(pos, color));
					else if (piece instanceof King) squares[pos.row][pos.col].placePiece(new King(pos, color));
					else if (piece instanceof Pawn) squares[pos.row][pos.col].placePiece(new Pawn(pos, color));
				}
			}
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
	
	private void initializeSquares() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j] = new Square();
			}
		}
	}
	
	private void addPiecesToPlayer(Player whitePlayer, Player blackPlayer) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				IPiece piece = this.getPiece(new Position(i, j));
				if (piece != null) {
					if (piece.getColor() == Color.WHITE) {
						whitePlayer.addPiece(piece);
						piece.setPlayer(whitePlayer);
					} else {
						blackPlayer.addPiece(piece);
						piece.setPlayer(blackPlayer);
					}
				}
			}
		}
	}
	
	public void newGame() {
//		whitePlayer = new Player(Color.WHITE);
//		blackPlayer = new Player(Color.BLACK);
		
		initializeSquares();
		
		// Black pieces
		squares[0][0].placePiece(new Rook(new Position(0, 0), Color.BLACK));
		squares[0][7].placePiece(new Rook(new Position(0, 7), Color.BLACK));
		squares[0][1].placePiece(new Knight(new Position(0, 1), Color.BLACK));
		squares[0][6].placePiece(new Knight(new Position(0, 6), Color.BLACK));
		squares[0][2].placePiece(new Bishop(new Position(0, 2), Color.BLACK));
		squares[0][5].placePiece(new Bishop(new Position(0, 5), Color.BLACK));
		squares[0][3].placePiece(new Queen(new Position(0, 3), Color.BLACK));
		squares[0][4].placePiece(new King(new Position(0, 4), Color.BLACK));
		
		for (int i = 0; i < 8; i++) {
			squares[1][i].placePiece(new Pawn(new Position(1, i), Color.BLACK));
		}
		
		// White pieces
		squares[7][0].placePiece(new Rook(new Position(7, 0), Color.WHITE));
		squares[7][7].placePiece(new Rook(new Position(7, 7), Color.WHITE));
		squares[7][1].placePiece(new Knight(new Position(7, 1), Color.WHITE));
		squares[7][6].placePiece(new Knight(new Position(7, 6), Color.WHITE));
		squares[7][2].placePiece(new Bishop(new Position(7, 2), Color.WHITE));
		squares[7][5].placePiece(new Bishop(new Position(7, 5), Color.WHITE));
		squares[7][3].placePiece(new Queen(new Position(7, 3), Color.WHITE));
		squares[7][4].placePiece(new King(new Position(7, 4), Color.WHITE));
		
		for (int i = 0; i < 8; i++) {
			squares[6][i].placePiece(new Pawn(new Position(6, i), Color.WHITE));
		}
		
		addPiecesToPlayer(whitePlayer, blackPlayer);
		playerToMove = whitePlayer;
	}
	
	public void newBackRankMate() {
//		whitePlayer = new Player(Color.WHITE);
//		blackPlayer = new Player(Color.BLACK);
		
		initializeSquares();
		
		// Black pieces
		squares[0][3].placePiece(new King(new Position(0, 3), Color.BLACK));
				
		// White pieces
		squares[2][1].placePiece(new Rook(new Position(2, 1), Color.WHITE));
		squares[1][0].placePiece(new Rook(new Position(1, 0), Color.WHITE));
		squares[7][1].placePiece(new King(new Position(7, 1), Color.WHITE));

		addPiecesToPlayer(whitePlayer, blackPlayer);	
		playerToMove = blackPlayer;
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
	
	public void move(IPiece piece, Position position) {
		piece.move(this, position);
		System.out.println("\n" + this.toString());
	}
	
	public void move(String algNot) {
		Position newPosition = algNotToPosition(algNot);
		char pieceLetter = algNot.charAt(0);
		Color pieceColor = (playerToMove == whitePlayer) ? Color.WHITE : Color.BLACK;
		List<IPiece> sameColoredPieces = (pieceColor == Color.WHITE) ? getWhitePlayer().getPieces() : getBlackPlayer().getPieces();
		
		for (IPiece piece : sameColoredPieces) {
			if (piece.getLegalMoves(this).contains(newPosition) && pieceLetter == piece.getPieceLetter()) {
				Position oldPosition = piece.getPosition();
				piece.move(this, newPosition);
				
				// Castling
				if (piece instanceof King && Math.abs(newPosition.col - oldPosition.col) == 2) {
					int rookCol = (newPosition.col - oldPosition.col < 0) ? 0 : 7;
					int rookRow = (piece.getColor() == Color.WHITE) ? 7 : 0;
					int rookColJump = (newPosition.col - oldPosition.col < 0) ? 3 : -2;
					IPiece rook = this.getPiece(new Position(rookRow, rookCol));
					System.out.println(rook);
					Position oldRookPos = new Position(rookRow, rookCol);
					Position newRookPos = new Position(rookRow, rookCol + rookColJump);
					rook.setPosition(newRookPos);
					this.getSquare(oldRookPos).removePiece();
					this.getSquare(newRookPos).placePiece(rook);
				}
				
				// TODO: Må fikses for situasjoner der to brikker av samme type og farge kan flytte til samme felt
				System.out.println("\n" + this.toString());
				playerToMove = (playerToMove == whitePlayer) ? blackPlayer : whitePlayer;
				System.out.println(playerToMove);
			} 
		}
		
		
	}
	
	public Position algNotToPosition(String algNot) {
		int row = -1;
		int col = -1;
		for (int i = 0; i < algNot.length(); i++) {
			if ("abcdefgh".indexOf(algNot.charAt(i)) != -1) {
				row = 8 - Character.getNumericValue(algNot.charAt(i + 1));
				col = "abcdefgh".indexOf(algNot.charAt(i));
			}
		}
		
		return new Position(row, col);
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
	
	public Player getPlayerToMove() {
		return playerToMove;
	}
	
	public void setPlayerToMove(Player playerToMove) {
		this.playerToMove = playerToMove;
	}
	
	
}
