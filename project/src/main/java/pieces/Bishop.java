package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import javafx.geometry.Pos;
import utils.*;

public class Bishop extends Piece {
	
	public Bishop(Position position, Color color) {
		super(position, color, 'B');
	}
	
	@Override
	public String toString() {
		return "|" + colorPrefix + "B|";
	}

	@Override
	public List<Position> getLegalMoves(Board board) {
		MoveValidator validator = new MoveValidator(board, this);
		validator.addDiagonals();
		return validator.getLegalDestinations();
	}
	
	
	
	//Validering
	
	private boolean isValid(String field) {
		//Sjekker om foreslått felt er et lovlig flytt
		return true;
	}
	
	//Vurdere å sette inn separate checks for lovlig lengde på flytt, sjakk-kontroll 
	
	private boolean isCheck(String field) {
		//Sjekker om ønsket felt setter egen konge i sjakk 
		return true;
	}
	
	private boolean validMove(String field) {
		//sjekker om flyttet til field er godkjent
		return true;
	}
	
}
