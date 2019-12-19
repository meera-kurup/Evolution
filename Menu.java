package Evolution;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** 
 * Menu Class: 
 * 
 * Handles the two buttons to allow the user to play either flappy bird 
 * or Evolution. Made in place of the Pane Organizer. 
 * Buttons have their own clickHandlers to set the scene
 * for either gamepane or evolutionpane. 
 *
 */

public class Menu {
	
	//Instance Variable for the menu BorderPane
	private BorderPane _menu; 
	
	//Instance Variable for the stage from App, for association 
	private Stage _stage; 
	
	//Instance Variable for buttonPane that holds all Buttons
	private VBox _buttonPane; 
	
	//Instance Variable for Evolution (ML) and FlappyBird (FB) buttons
	private Button _buttomML;
	private Button _buttonFB;
	
	//Instance variable for the instructions label 
	private Label _choseGame; 

	/*
	 * This is the menu constructor that takes in a stage as a parameter
	 * in order to set the stage when a certain button is pressed 
	 * for the type of game the user wants to play. 
	 */
	public Menu(Stage stage)
	{
		//associating the stage from App to show the certain stage when a button is pressed
		_stage = stage;
		
		//Menu Pane created
		_menu = new BorderPane();

		//Button Pane created for the Buttons and label
		_buttonPane = new VBox();
		_buttonPane.setStyle("-fx-background-color: white;");
		
		//creating the instructions Label
		_choseGame = new Label("Chose a Game! Either FlappyBird or Evolution(ML)");
						
		//Quit button created, set to bottom of root BorderPane and center of ButtonPane, and calls ClickHandler to quit game
		_buttomML = new Button("Evolution");
		//setting focus of button to false in order to make sure the keys pressing does not focus on the button
		_buttomML.setFocusTraversable(false);
		//Quit button created, set to bottom of root BorderPane and center of ButtonPane, and calls ClickHandler to quit game
		_buttonFB = new Button("Flappy Bird");
		//setting focus of button to false in order to make sure the keys pressing does not focus on the button
		_buttonFB.setFocusTraversable(false);
		
		//Adds both buttons to the Menu Pane and setting it to Center of the pane
		_buttonPane.getChildren().addAll(_choseGame, _buttonFB, _buttomML); 
		_buttonPane.setSpacing(30);
		_buttonPane.setAlignment(Pos.CENTER);
			
		//adding ButtonPane to the menu's Pane
		_menu.setCenter(_buttonPane);
		
		//Calling ClickHandlers on each Button
		_buttomML.setOnAction(new ClickHandler_ML());
		_buttonFB.setOnAction(new ClickHandler_FB());
				
		
	}
	
	//This method returns the BorderPane to call in Scene object created in App.java
		public BorderPane getMenu() {
			return _menu; 
	}
	
	/*
	 * This method is the ClickHandler for the Evolution Button
	 * The Evolution instance is created and called and the scene is set accordingly
	 */
	private class ClickHandler_ML implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
	    	
			//Instance of Evolution Created
			Evolution Evolution = new Evolution();
			_menu.setCenter(Evolution.getEvolutionPane());
			
	    	//Stage is set and shown
			_stage.setScene(_menu.getScene());
	    	_stage.show();
	    	
			event.consume(); //Consume the event here
		}
	}
	
	/*
	 * This method is the ClickHandler for the Flappy Bird Button
	 * The Game instance is created and called and the scene is set accordingly
	 */
	private class ClickHandler_FB implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			
			//Instance of Game Created
			Game game = new Game();
			_menu.setCenter(game.getGamePane());
			
	    	//Stage is set and shown
			_stage.setScene(_menu.getScene());
	    	_stage.show();
	    	
			event.consume(); //Consume the event here
		}
	}

}
