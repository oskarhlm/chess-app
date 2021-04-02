package gui;

import java.io.IOException;

import board.Board.GameType;
import game.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class MenuController {
	
	@FXML AnchorPane menu;
	@FXML Button newGameButton;
	@FXML Button loadMenuButton;
	
	public void newGameButtonClicked() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
		loader.setController(new GameController());
		Parent root = loader.load();
		Stage window = (Stage) newGameButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}
	
	public void loadButtonClicked() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("LoadMenu.fxml"));
		Stage window = (Stage) loadMenuButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}
	
//	public void loadButtonClicked() throws Exception {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
//		Game newGame = SaveAndLoadHandler.load("test.txt");
//		loader.setController(new GameController(newGame));
//		Parent root = loader.load();
//		Stage window = (Stage) newGameButton.getScene().getWindow();
//		window.setScene(new Scene(root));
//	}
}












