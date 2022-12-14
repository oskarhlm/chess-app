package pieces;

import java.util.List;

import board.Board;
import javafx.scene.image.ImageView;
import player.Player;
import utils.*;

public interface IPiece {
	void move(Board board, Position newPosition);
	Color getColor();
	String getColorPrefix();
	Position getPosition();
	void setPosition(Position position);
	boolean hasMoved();
	void setHasMoved(Boolean bool);
	List<Position> getLegalMoves(Board board);
	void setEnPassentPiece(Board board, Position oldPosition, Position newPosition);
	char getPieceLetter();
	Player getPlayer();
	void setPlayer(Player player);
	ImageView getImage();
	void setBoard(Board board);
	void relocatePiece(Position position);
	void setImage();
}
