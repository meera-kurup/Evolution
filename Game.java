package Evolution;

import java.util.LinkedList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/** 
 * Game class: 
 * For the user to manually play FlappyBird with a space bar
 * Handles all Flappy Bird logic from space bar to generate pipes and restarting the game
 * Logic includes multiple helper methods in the TimeHandlers 
 * Also includes a KeyHandler for the space bar
 */
public class Game {
	
	//Game Pane instance variable
	private BorderPane _gamePane;
	
	//Instance variable for the Doodle's Pane and the doodle of type Doodle 
	private Bird _bird; 
	private Pane _birdPane; 
		
	//Instance variable for timeline
	private Timeline _timeline; 
		
	//Instance variables for Pipes, including LinkedList of Pipes, the Pane, and one pipe itself 
	private LinkedList<Pipe> _pipeList; 
	private Pane _pipePane;
	private Pipe _pipe; 
	
	//for the closest pipe 
	private Pipe _closestPipe; 
	
	//instance variable for the sideBar 
	private BorderPane _sideBarPane; 
	
	//Instance variable for background
	private ImageView _backgroundImage; 
	
	//instance variables for button Pane and buttons
	private HBox _buttonPane; 
	private Button _1xSpeedButton;
	private Button _2xSpeedButton; 
	private Button _3xSpeedButton;
	
	/* 
	 * This Constructor sets instances for the Game Pane, Bird Pane, Pipe Pane, Timeline, background, and buttons. 
	 * Calls method generatePipes so pipes are initially added to the pane. 
	 */
	
	public Game() {
		
		//sets instance of the Game's Pane to a BorderPane
		_gamePane = new BorderPane();
		_gamePane.setStyle("-fx-background-color: #79ced9;");
		_gamePane.setPrefSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
				
		//creating a Bird Pane for the Bird 
		_birdPane = new Pane(); 
		_bird = new Bird(_birdPane);
	
		//creates an LinkedList of type Pipe 
		_pipeList = new LinkedList<Pipe>();
		_pipePane = new Pane();
			
		this.generatePipes(); 		//generates more pipes
		
		//Instances of sideBarPane created
		_sideBarPane = new BorderPane();
		_sideBarPane.setStyle("-fx-background-color: #e6df95;");
		_sideBarPane.setPrefSize(Constants.SIDE_BAR_WIDTH,Constants.SIDE_BAR_HEIGHT);
		
		//Buttons instantiated
		_1xSpeedButton = new Button("1x");
		_2xSpeedButton = new Button("2x");
		_3xSpeedButton = new Button("3x");
		
		//Instance of ButtonPane created
		_buttonPane = new HBox(); 
		
		//Settings the location of buttons on Pane
		_sideBarPane.setRight(_buttonPane);
		_buttonPane.getChildren().addAll(_1xSpeedButton,_2xSpeedButton, _3xSpeedButton);
		_1xSpeedButton.setFocusTraversable(false);
		_2xSpeedButton.setFocusTraversable(false);
		_3xSpeedButton.setFocusTraversable(false);
		
		//setting ClickHandlers for all Buttons
		_1xSpeedButton.setOnAction(new ClickHandler_1x());
		_2xSpeedButton.setOnAction(new ClickHandler_2x());
		_3xSpeedButton.setOnAction(new ClickHandler_3x());
	
		//Setting the background image of the game
		_backgroundImage = new ImageView(); 
		_backgroundImage.setImage(Constants.BACKGROUND_IMAGE); 
		_backgroundImage.setFitWidth(Constants.PANEL_WIDTH);
		_backgroundImage.setFitHeight(Constants.PANEL_HEIGHT);
		
		//adding pipePane, birdPane, and background image to EvolutionPane
        _gamePane.getChildren().addAll( _pipePane, _birdPane, _backgroundImage);
        _backgroundImage.toBack();
        _gamePane.setBottom(_sideBarPane);
        
      //EventHandler - KeyHandler for the space key and setting the focus for the spacebar
        _gamePane.setOnKeyPressed(new KeyHandler());		
		_gamePane.setFocusTraversable(true);
		_gamePane.requestFocus();   

        //Creates and sets up the timeline
      	_timeline = new Timeline(); 
		this.setupTimeline(1);
		
	}
	
	/*
	 * This method returns the BorderPane for the Game to call in menu Pane in Menu
	 */
	public Pane getGamePane() {
		return _gamePane; 		
	}
	
	/*
	 * This method generates the new Pipes. It does this by checking where the previous
	 * pipe is located and setting a new pipe a certain distance away from the previous one
	 * 
	 */
	public void generatePipes() {
		
		//First checks to see if the pipeList is empty and if so, adds the first pipe to it
		if(_pipeList.isEmpty() == true)
		{
			_pipe = new Pipe(_pipePane);
			_pipeList.add(_pipe); 
			_closestPipe = _pipeList.get(0); //returns the closestPipe as the first pipe of the Pipelist 
		}
		
		//creates a double that stores the y location of the last pipe in the list
		double lastPipeXLoc = _pipeList.get(_pipeList.size() - 1).getXLoc();

		//checks to make sure the y location of the last pipe of the list is less than the PanelWidth
    	while (lastPipeXLoc < Constants.PANEL_WIDTH) {
    		//creates a new Pipe
    		Pipe newPipe = new Pipe(_pipePane);
    		//sets the new pipe's location a certain distance away from the previous one
			newPipe.setXLoc(lastPipeXLoc + Constants.PIPE_DISTANCE); 
    		//adds pipe to list
    		_pipeList.addLast(newPipe);
    		//resets the value of the last pipe
    		lastPipeXLoc = _pipeList.get(_pipeList.size() - 1).getXLoc(); 
       	}

	}
	
	/* 
	 * Getter method: returns the x location of the closest pipe
	 */
	public double getClosestPipeX() {
		return _closestPipe.getXLoc(); 
	}
	
	/* 
	 * Getter method: returns the y location of the top closest pipe
	 */
	public double getTopPipeY() {
		return _closestPipe.getYLoc(); 
	}
	
	/* 
	 * Getter method: returns the top closest pipe height
	 */
	public double getTopPipeHeight() {
		return _closestPipe.getTopPipeHeight();
	}
	
	/* 
	 * Getter method: returns the y location of the bottom closest pipe
	 */
	
	public double getBottomPipeY() {
		return _closestPipe.getBottomPipeYLoc();
	}
	
	/* 
	 * Getter method: returns the bottom closest pipe height
	 */
	
	public double getBottomPipeHeight() {
		return _closestPipe.getBottomPipeHeight();
	}
	
	/* 
	 * This method is to set up the Timeline and call the MoveHandler. A KeyFrame is instantiated and the Animation continues
	 * to play. The Duration is the time before the event occurs. 
	 */
	public void setupTimeline(int speed){
		KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION / speed), new GameHandler());	    
		_timeline = new Timeline(kf);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();  
	}
	
	/*
	 * This method is the GameHandler. This method is called when the Timeline is setup. 
	 * This has multiple helper methods that are called, including bird's updateVelocity, 
	 * updateBird, scrollPipes, and removePipes. 
	 */
	private class GameHandler implements EventHandler<ActionEvent> {

		public void handle (ActionEvent event) {
			_bird.updateVelocity();
			this.updateBird();
			this.scrollPipes();
			this.removePipes();
        }
		
		/* 
		 * This method updates the bird to check if it intersects with a pipe, the top or bottom of the panel
		 * If so, restart method is called
		 */
		private void updateBird() {
			if(_bird.getYLoc() < 0) //if the bird hits the ceiling
			{
				this.restartGame();		
			}
			else if(_bird.getYLoc() > (Constants.PANEL_HEIGHT - Constants.SIDE_BAR_HEIGHT)) //if the bird hits the floor
			{
				this.restartGame();		
			}
			//if bird hit the top pipe
			else if(_bird.intersects(Game.this.getClosestPipeX(), Game.this.getTopPipeY(), Constants.PIPE_WIDTH, Game.this.getTopPipeHeight()))
			{
				this.restartGame();		
			}
			//if bird hits the bottom pipe
			else if(_bird.intersects(Game.this.getClosestPipeX(),Game.this.getBottomPipeY(), Constants.PIPE_WIDTH, Game.this.getBottomPipeHeight()))
			{
				this.restartGame();		
			}
		}

		private void scrollPipes() {
			//loops through pipeList
			for (int i = 0; i < _pipeList.size(); i++) {
				//sets the location of the pipe 
				_pipeList.get(i).setXLoc(_pipeList.get(i).getXLoc() - 2);
			}
			//checks if the closest pipe has moved past the location of the bird
			if(_closestPipe.getXLoc() < Constants.CLOSEST_PIPE)
			{
				_closestPipe = _pipeList.get(1); //if so, the closest pipe is now the next pipe
			}
			Game.this.generatePipes(); //generates more pipes
		}
		
		/*
		 * Remove Platforms Method removes all the platforms as they leave the panel
		 */
		private void removePipes() {
			//loops through pipeLIst
			for (int i = 0; i < _pipeList.size(); i++) {
				if (_pipeList.get(i).getXLoc() < (0 - Constants.PIPE_WIDTH)) //checks if the pipe's location is past the panel
				{
					_pipePane.getChildren().remove(_pipeList.get(i).getTopPipe()); //removes top pipe graphically
					_pipePane.getChildren().remove(_pipeList.get(i).getBottomPipe()); //removes bottom pipe graphically
					_pipeList.removeFirst(); //removes pipe logically
				}
			}
		}			
				
		/* 
		 * This method restarts the game by clearing the birds and pipes and then adding them again
		 */
		private void restartGame() {
			//clearing pipes and bird logically and graphically
			_pipeList.clear(); 
			_pipePane.getChildren().clear();
			_birdPane.getChildren().clear();  
				
				
			//creates an LinkedList of type Pipe
			_pipeList = new LinkedList<Pipe>();	
			
			//creating a new Bird that takes in a BirdPane
			_birdPane = new Pane(); 
			_bird = new Bird(_birdPane); 					
			
			//Adding birds to gamePane
			_gamePane.getChildren().addAll(_birdPane);
				
			Game.this.generatePipes(); //generates more pipes
		}
	}
	
	/*
	 * This is the KeyHandler to allow the bird to jump when the space bar is pressed, resetting its velocity
	 */

	private class KeyHandler implements EventHandler<KeyEvent> {
		public void handle(KeyEvent e) {
			
			//variable created for the keyboard letters to use in if statements 
			KeyCode keyPressed = e.getCode();
	
			//if the spacekey is pressed
			if (keyPressed == KeyCode.SPACE) {
				_bird.setCurrentVelocity(Constants.REBOUND_VELOCITY); //set velocity to rebound
				e.consume(); 
			}
		}
	}
	
	/* 
	 * Getter: gets the Timeline, for ClickHandlers
	 */
	public Timeline getTimeline() {
		// TODO Auto-generated method stub
		return _timeline;
	}
	
	/* 
	 * ClickHandlers for the 1x button, sets the rate of the Evolution to 1
	 */
	private class ClickHandler_1x implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Game.this.getTimeline().setRate(1);
			event.consume(); //Consume the event here
		}
	}
	
	/* 
	 * ClickHandlers for the 2x button, sets the rate of the Evolution to 2 (double speed)
	 */
	private class ClickHandler_2x implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Game.this.getTimeline().setRate(2);
			event.consume(); //Consume the event here
		}
	}
	
	/* 
	 * ClickHandlers for the 1x button, sets the rate of the Evolution to 5 
	 */
	
	private class ClickHandler_3x implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Game.this.getTimeline().setRate(3); 
			event.consume(); //Consume the event here
		}
	}


}
