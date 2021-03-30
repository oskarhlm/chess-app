package gui;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class Controller {
	
	@FXML AnchorPane ap_main;
	@FXML AnchorPane menu;
	
	private void loadChessGame() {
		VBox vBox = new VBox();
		Pane chessBoard = new ChessBoard().getBoard();
		Button drawButton = new Button("Declare draw");
		
		vBox.getChildren().addAll(chessBoard, drawButton);
		vBox.setPrefSize(600, 800);
		
		AnchorPane pane = new AnchorPane();
	    pane.prefHeightProperty().bind(vBox.prefHeightProperty());
	    pane.prefWidthProperty().bind(vBox.prefWidthProperty());

	    pane.getChildren().setAll(vBox);
	    ap_main.getChildren().setAll(pane);
	}
	
	public void newGameButtonClicked() {
		loadChessGame();
	}

}
