package player;

import java.util.ArrayList;
import java.util.List;

import pieces.*;
import utils.Color;

public class Player {
	
	private Color color;
	private List<IPiece> pieces = new ArrayList<IPiece>();
	private King king;
	
	public Player(Color color) {
		this.color = color;
	}
	
	public List<IPiece> getPieces() {
		return pieces;
	}
	
	public void addPiece(IPiece piece) {
		pieces.add(piece);
		
		if (piece instanceof King) {
			king = (King) piece;
		}
	}
	
	public King getKing() {
		return king;
	}
}
