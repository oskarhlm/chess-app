package gui;

import java.net.URL;
import java.util.ResourceBundle;

import game.Game;
import game.Game.GameState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
		boardGUI.setController(this);
	}
	
	public GameController(Game game) {
		boardGUI = new ChessBoardGUI(game);
		boardGUI.setController(this);
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
		displayDeclareWinnerBox();
	}
	
	private void displayDeclareWinnerBox() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Declare winner");
		window.setMinWidth(250);
		
		Label label = new Label("Declare the winner of the game:");
		
		Button whiteButton = new Button("White");
		whiteButton.setOnAction(e -> {
			game.setGameState(GameState.WHITE_VICTORIOUS);
			declareWinnerButton.setDisable(true);
			drawButton.setDisable(true);
			window.close();
		});
		
		Button blackButton = new Button("Black");
		blackButton.setOnAction(e -> {
			game.setGameState(GameState.BLACK_VICTORIOUS);
			declareWinnerButton.setDisable(true);
			drawButton.setDisable(true);
			window.close();
		});
		
		HBox buttons = new HBox();
		buttons.getChildren().addAll(whiteButton, blackButton);
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(10);
		buttons.setMinHeight(50);
		
		VBox layout = new VBox();
		layout.getChildren().addAll(label, buttons);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
	public void handleDrawButtonClicked() {
		displayDrawAgreementBox();
	}
	
	private void displayDrawAgreementBox() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Agree to draw");
		window.setMinWidth(250);
		
		Label label = new Label("Are you sure you want to agree to a draw?");
		
		Button yesButton = new Button("Yes");
		yesButton.setOnAction(e -> {
			game.setGameState(GameState.DRAW_BY_AGREEMENT);
			declareWinnerButton.setDisable(true);
			drawButton.setDisable(true);
			window.close();
		});
		
		Button noButton = new Button("No");
		noButton.setOnAction(e -> window.close());
		
		HBox buttons = new HBox();
		buttons.getChildren().addAll(yesButton, noButton);
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(10);
		buttons.setMinHeight(50);
		
		VBox layout = new VBox();
		layout.getChildren().addAll(label, buttons);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
	public void gameStateChanged(GameState gameState) {
		switch (gameState) {
			case DRAW_BY_AGREEMENT:
				gameInfoLabel.setText("Game was drawn.");
				break;
			case STALE_MATE: 
				gameInfoLabel.setText("Stale mate!");
				break;
			case WHITE_VICTORIOUS:
				gameInfoLabel.setText("White has won!");
				break;
			case BLACK_VICTORIOUS:
				gameInfoLabel.setText("Black has won!");
				break;
			default:
				break;
		}
		
		if (gameState != GameState.ONGOING) {
			declareWinnerButton.setDisable(true);
			drawButton.setDisable(true);
		}
	}
}
