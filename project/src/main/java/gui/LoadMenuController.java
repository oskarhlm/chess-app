package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import game.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ISaveAndLoadHandler;
import utils.SaveAndLoadHandler;

public class LoadMenuController implements Initializable {
	/* Controller for the load menu */
	
	@FXML VBox container;
	@FXML ListView<String> gamesList;
	
	ObservableList<String> fileNames = FXCollections.observableArrayList();
	
	ISaveAndLoadHandler saveAndLoadHandler = new SaveAndLoadHandler();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File folder = new File("src\\main\\resources\\saved_games");
		System.out.println(folder.listFiles().length);
		
		for (File fileEntry : folder.listFiles()) {
			fileNames.add(fileEntry.getName());
		}
		
		gamesList = new ListView<>(fileNames);
		
		gamesList.setOnMouseClicked(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	        	try {
	        		FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
		        	String fileName = gamesList.getSelectionModel().getSelectedItem();
		    		Game game = saveAndLoadHandler.load(fileName);
		    		loader.setController(new GameController(game, fileName));
		    		Parent root = loader.load();
		    		Stage window = (Stage) gamesList.getScene().getWindow();
		    		window.setScene(new Scene(root));
	        	} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    });
		
		Button backToMenuButton = new Button("Back to main menu"); 
		
		backToMenuButton.setOnAction(e -> {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
				Stage window = (Stage) backToMenuButton.getScene().getWindow();
				window.setScene(new Scene(root));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		container.getChildren().addAll(gamesList, backToMenuButton);
	}
	
	

}
