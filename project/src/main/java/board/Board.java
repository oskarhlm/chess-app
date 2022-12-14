package board;

import pieces.*;
import utils.*;
import player.*;

import java.util.*;

import game.Game;
import game.Game.GameState;


public class Board {
	
	private Game game;
	Square[][] squares = new Square[8][8];
	private Pawn enPassentPiece;
	private Player whitePlayer = new Player(Color.WHITE);
	private Player blackPlayer = new Player(Color.BLACK);
	private Player playerToMove = whitePlayer;
	
	public enum GameType {
		CLASSIC_SETUP,
		STAIRCASE_MATE,
		PROMOTION,
		STALE_MATE,
		NOTATION_TEST
	}
	
	public Board(GameType gameType) {
		/* Creates a new board with specified setup. All types other than "CLASSIC_SETUP"
		 * are solely used in development for testing purposes */
		
		switch (gameType) {
			case CLASSIC_SETUP: 
				newGame();
				break;
			case STAIRCASE_MATE:
				stairCaseMate();
				break;
			case PROMOTION:
				promotionPosition();
				break;
			case STALE_MATE:
				staleMate();
				break;
			case NOTATION_TEST:
				notationTest();
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + gameType);
		}
	}
	
	public Board(Board board) {
		/* Used to make a copy of a board. Needed when looking at potential positions, for instance when 
		 * deciding if a certain move puts your king in check, and thus needs to be disallowed */
		
		initializeSquares();
		
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
	
	public Board(List<IPiece> pieces, Color playerToMoveColor) {
		/* Constructor used when loading a saved game. A list of pieces is created based on information from
		 * the save file, and a board is formed based on this list and knowledge of which player has the next move */
		
		initializeSquares();
		
		for (IPiece piece : pieces) {
			Color color = (piece.getColor() == Color.WHITE) ? Color.WHITE : Color.BLACK;
			Position pos = piece.getPosition();
			if (piece instanceof Rook) squares[pos.row][pos.col].placePiece(new Rook(pos, color));
			else if (piece instanceof Knight) squares[pos.row][pos.col].placePiece(new Knight(pos, color));
			else if (piece instanceof Bishop) squares[pos.row][pos.col].placePiece(new Bishop(pos, color));
			else if (piece instanceof Queen) squares[pos.row][pos.col].placePiece(new Queen(pos, color));
			else if (piece instanceof King) squares[pos.row][pos.col].placePiece(new King(pos, color));
			else if (piece instanceof Pawn) squares[pos.row][pos.col].placePiece(new Pawn(pos, color));
		}
		
		addPiecesToBoardAndPlayer(whitePlayer, blackPlayer);
		this.playerToMove = (playerToMoveColor == Color.WHITE) ? whitePlayer : blackPlayer;
	}
	
	private void initializeSquares() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j] = new Square(i, j);
				squares[i][j].setBoard(this);
			}
		}
	}
	
	private void addPiecesToBoardAndPlayer(Player whitePlayer, Player blackPlayer) {
		/* Connects each piece to its respective "owner" */
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				IPiece piece = this.getPiece(new Position(i, j));
				
				if (piece != null) {
					piece.setBoard(this);
					piece.setImage();

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
	
	private void newGame() {
		/* Initalizes a board with the normal piece setup */
		
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
		
		addPiecesToBoardAndPlayer(whitePlayer, blackPlayer);
		playerToMove = whitePlayer;
	}
	
	private void stairCaseMate() {
		/* Specific setup used for testing purposes */
		
		initializeSquares();
		
		// Black pieces
		squares[0][3].placePiece(new King(new Position(0, 3), Color.BLACK));
				
		// White pieces
		squares[2][1].placePiece(new Rook(new Position(2, 1), Color.WHITE));
		squares[1][0].placePiece(new Rook(new Position(1, 0), Color.WHITE));
		squares[7][1].placePiece(new King(new Position(7, 1), Color.WHITE));

		addPiecesToBoardAndPlayer(whitePlayer, blackPlayer);	
		playerToMove = blackPlayer;
		getWhiteKing().setHasMoved(true);
		getBlackKing().setHasMoved(true);
	}
	
	private void promotionPosition() {
		/* Specific setup used for testing purposes */
		
		initializeSquares();
		
		// White pieces
		squares[3][2].placePiece(new King(new Position(3, 2), Color.WHITE));

		// Black pieces
		squares[2][5].placePiece(new King(new Position(2, 5), Color.BLACK));
		squares[6][5].placePiece(new Pawn(new Position(6, 5), Color.BLACK));
		
		addPiecesToBoardAndPlayer(whitePlayer, blackPlayer);
		playerToMove = blackPlayer;
		getWhiteKing().setHasMoved(true);
		getBlackKing().setHasMoved(true);
	}
	
	private void staleMate() {
		/* Specific setup used for testing purposes */
		
		initializeSquares();
		
		// White pieces
		squares[1][2].placePiece(new King(new Position(1, 2), Color.WHITE));
		squares[7][1].placePiece(new Rook(new Position(7, 1), Color.WHITE));
		squares[3][3].placePiece(new Knight(new Position(3, 3), Color.WHITE));
		
		// Black pieces
		squares[0][0].placePiece(new King(new Position(0, 0), Color.BLACK));
		squares[0][2].placePiece(new Knight(new Position(0, 2), Color.BLACK));
		
		addPiecesToBoardAndPlayer(whitePlayer, blackPlayer);
		playerToMove = whitePlayer;
		getWhiteKing().setHasMoved(true);
		getBlackKing().setHasMoved(true);
	}
	
	private void notationTest() {
		/* Specific setup used for testing purposes */
		
		initializeSquares();
		
		// White pieces
		squares[1][2].placePiece(new King(new Position(1, 2), Color.WHITE));
		squares[7][7].placePiece(new Knight(new Position(7, 7), Color.WHITE));
		squares[3][7].placePiece(new Knight(new Position(3, 7), Color.WHITE));
		squares[3][5].placePiece(new Knight(new Position(3, 5), Color.WHITE));
		squares[7][5].placePiece(new Knight(new Position(7, 5), Color.WHITE));
		
		// Black pieces
		squares[0][0].placePiece(new King(new Position(0, 0), Color.BLACK));
		
		addPiecesToBoardAndPlayer(whitePlayer, blackPlayer);
		playerToMove = whitePlayer;
		getWhiteKing().setHasMoved(true);
		getBlackKing().setHasMoved(true);
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
		/* Moves a piece to a position on the board */
		
		piece.move(this, position);
		playerToMove = (playerToMove == whitePlayer) ? blackPlayer : whitePlayer;
		
		if (this.getGame() != null && this.getGame().getGameState() == GameState.NOT_STARTED) {
			this.getGame().setGameState(GameState.ONGOING);
		}
	}
	
	public boolean tryMove(IPiece piece, Position newPosition) {
		/* Method that tries to move a piece to a position. 
		 * Differs from move(IPiece piece, Position position) in that it checks if it is even a legal
		 * position for the piece to move to. Returns false if this is not the case, true if it is. */
		
		if (getGame().getGameState() != GameState.NOT_STARTED && getGame().getGameState() != GameState.ONGOING) {
			return false;
		}
		
		Position oldPosition = piece.getPosition();
		Color pieceColor = (playerToMove == whitePlayer) ? Color.WHITE : Color.BLACK;
		boolean promotion = false;
		
		if (piece.getColor() == playerToMove.getColor() && piece.getLegalMoves(this).contains(newPosition)) {
			move(piece, newPosition);
			
			// Castling
			if (piece instanceof King && Math.abs(newPosition.col - oldPosition.col) == 2) {
				int rookCol = (newPosition.col - oldPosition.col < 0) ? 0 : 7;
				int rookRow = (piece.getColor() == Color.WHITE) ? 7 : 0;
				int rookColJump = (newPosition.col - oldPosition.col < 0) ? 3 : -2;
				IPiece rook = this.getPiece(new Position(rookRow, rookCol));
				Position oldRookPos = new Position(rookRow, rookCol);
				Position newRookPos = new Position(rookRow, rookCol + rookColJump);
				rook.setPosition(newRookPos);
				this.getSquare(oldRookPos).removePiece();
				this.getSquare(newRookPos).placePiece(rook);
				rook.relocatePiece(newRookPos);
			}
			
			// Promotion
			int promotionRow = (pieceColor == Color.WHITE) ? 0 : 7;
			
			if (piece instanceof Pawn && newPosition.row == promotionRow) {
				promotion = true;
			}
			
			if (promotion) {
				this.getSquare(newPosition).capturePieceOnSquare();
				Queen newQueen = new Queen(new Position(newPosition.row, newPosition.col), pieceColor);
				newQueen.setBoard(this);
				newQueen.setImage();
				newQueen.setPlayer(playerToMove);
				Player player = (pieceColor == Color.WHITE) ? whitePlayer : blackPlayer;
				player.addPiece(newQueen);
				Square newSquare = squares[newPosition.row][newPosition.col];
				newSquare.placePiece(newQueen);
				this.getGame().getChessBoardGUI().getPieceGroup().getChildren().add(newQueen.getImage());
			}
			
			this.getGame().updateGameState();
			System.out.println("\n" + this.toString());
			System.out.println(this.getGame().getGameState());
			
			return true;
		}
			
		return false; 
	}
	
	public void move(String algNot) {
		/* Takes user input in the form of algebraic chess notation, analyses it
		 * and moves the specified piece according to the input if it is a legal move.
		 * Not used in the final version, but needed for testing purposes. */
		
		Position newPosition = algNotToPosition(algNot);
		char pieceLetter;
		
		// Check what piece is trying to move
		if ("RNBQK".indexOf(algNot.charAt(0)) != -1) {
			pieceLetter = algNot.charAt(0);
		} else if (algNot.equals("O-O") || algNot.equals("O-O-O")) {
			pieceLetter = 'K';
		} else {
			pieceLetter = 'p';
		}
		
		// Accounting for case where to pieces of the same type can move to the same square
		int pieceRow = -1;
		int pieceCol = -1;
		
		if (pieceLetter == 'p' && algNot.length() >= 3) {
			pieceCol = "abcdefgh".indexOf(algNot.charAt(0));
		} else if (algNot.length() >= 4) {
			
			if ("abcdefgh".indexOf(algNot.charAt(1)) != -1) {
				pieceCol = "abcdefgh".indexOf(algNot.charAt(1)); 
			} 
			
			if (Character.isDigit(algNot.charAt(1))) {
				pieceRow = 8 - Character.getNumericValue(algNot.charAt(1));
			} else if (Character.isDigit(algNot.charAt(2))) {
				pieceRow = 8 - Character.getNumericValue(algNot.charAt(2));
			}
		}
		
		Color pieceColor = (playerToMove == whitePlayer) ? Color.WHITE : Color.BLACK;
		List<IPiece> sameColoredPieces = (pieceColor == Color.WHITE) ? getWhitePlayer().getPieces() : getBlackPlayer().getPieces();
		boolean promotion = false;
		
		// Castling
		if (algNot.equals("O-O") || algNot.equals("O-O-O")) {
			System.out.println("hei");
			int row = (pieceColor == Color.WHITE) ? 7 : 0;
			newPosition = (algNot.equals("O-O"))
					? new Position(row, 6) : new Position(row, 2);
			System.out.println(newPosition);
		}
		
		for (IPiece piece : sameColoredPieces) {
			Position piecePos = piece.getPosition();
			
			if (piece.getLegalMoves(this).contains(newPosition) && pieceLetter == piece.getPieceLetter()
					&& ((pieceRow == -1 && pieceCol == -1) 
					|| (piecePos.row == pieceRow && pieceCol == -1) 
					|| (piecePos.col == pieceCol && pieceRow == -1)
					|| (piecePos.row == pieceRow && piecePos.col == pieceCol)
					)) {
				
				Position oldPosition = piece.getPosition();
				move(piece, newPosition);
				if (piece.getImage() != null) piece.relocatePiece(newPosition);
				
				// Castling
				if (piece instanceof King && Math.abs(newPosition.col - oldPosition.col) == 2) {
					int rookCol = (newPosition.col - oldPosition.col < 0) ? 0 : 7;
					int rookRow = (piece.getColor() == Color.WHITE) ? 7 : 0;
					int rookColJump = (newPosition.col - oldPosition.col < 0) ? 3 : -2;
					IPiece rook = this.getPiece(new Position(rookRow, rookCol));
					Position oldRookPos = new Position(rookRow, rookCol);
					Position newRookPos = new Position(rookRow, rookCol + rookColJump);
					rook.setPosition(newRookPos);
					this.getSquare(oldRookPos).removePiece();
					this.getSquare(newRookPos).placePiece(rook);
				}
				
				// Promotion
				int promotionRow = (pieceColor == Color.WHITE) ? 0 : 7;
				
				if (piece instanceof Pawn && newPosition.row == promotionRow) {
					promotion = true;
				}
			} 
		}
		
		if (promotion) {
			this.getSquare(newPosition).capturePieceOnSquare();
			Queen newQueen = new Queen(new Position(newPosition.row, newPosition.col), pieceColor);
			Player player = (pieceColor == Color.WHITE) ? whitePlayer : blackPlayer;
			player.addPiece(newQueen);
			squares[newPosition.row][newPosition.col].placePiece(newQueen);
		}
		
		System.out.println("\n" + this.toString());
		System.out.println("Player to move: " + playerToMove);
	}
	
	public static Position algNotToPosition(String algNot) {
		/* Extracts a destination based on algebraic input */
		
		int row = 8 - Character.getNumericValue(algNot.charAt(algNot.length() - 1));
		int col = "abcdefgh".indexOf(algNot.charAt(algNot.length() - 2));
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
	
	public void setWhiteKing(King king) {
		whitePlayer.setKing(king);
	}
	
	public void setBlackKing(King king) {
		blackPlayer.setKing(king);
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
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	
	
}
