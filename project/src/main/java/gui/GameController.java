package gui;

import java.net.URL;
import java.util.ResourceBundle;

import game.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameController implements Initializable  {

	@FXML Pane gamePane = new Pane();
	@FXML Button exitButton;
	@FXML Button declareWinnerButton;
	@FXML Button drawButton;
	@FXML Label gameInfoLabel;
	
	Game game;
	ChessBoardGUI boardGUI;
	
	public GameController() {
		boardGUI = new ChessBoardGUI();
	}
	
	public GameController(Game game) {
		boardGUI = new ChessBoardGUI(game);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gamePane.getChildren().add(boardGUI.getBoard());
		game = boardGUI.getGame();
	}
	
	public void handleExitButtonClicked() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Stage window = (Stage) exitButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}
	
	public void handleDeclareWinnerButtonClicked() {
		
	}
	
	public void handleDrawButtonClicked() {
		gameInfoLabel.setText("Game was drawn.");
		declareWinnerButton.setDisable(true);
		drawButton.setDisable(true);
	}
	
	private class ConfirmDrawBox {
		
		private void display() {
			System.out.println("ya");
		}
	}

}
