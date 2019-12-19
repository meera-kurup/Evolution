package Evolution;

import java.util.LinkedList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


/**
 * Evolution Class: 
 * For the user to allow the program to play Evolution using 
 * Machine Learning. Includes some logic for the population of birds
 * but mainly handles logic for restarting the game, and for the 
 * pipes. Also has ClickHandlers for buttons to increase and decrease the 
 * speed and multiple labels to keep track of data of the population
 *
 */
public class Evolution {
	
	//Evolution Pane instance variable
	private BorderPane _evolutionPane;

	//Instance variable for population to call on it 
	private Population _population; 
	
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
	
	//Instance variables for Panes to hold button and buttons themselves
	private HBox _buttonPane; 
	private VBox _labelPane;
	private Button _1xSpeedButton;
	private Button _2xSpeedButton; 
	private Button _5xSpeedButton;
	private Button _MaxSpeedButton; 
	
	//Instance variables for labels
	private Label _generationsLabel; 
	private int generations; 
	private Label _fitnessLabel;
	private Label _aliveBirdsLabel; 
	private Label _bestBirdLabel;
	private Label _bestBirdEverLabel; 
	
	//Instance variable for background
	private ImageView _backgroundImage;
	
	/*
	 * Evolution Constructor where the Pane is set, population and pipes are created,
	 * labels, buttons, background, and timeline are set
	 */
			
	public Evolution() {
		
		//sets instance of the Game's Pane to a BorderPane with a size 400x550 and background color
		_evolutionPane = new BorderPane();
		_evolutionPane.setStyle("-fx-background-color: #79ced9;");
		_evolutionPane.setPrefSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
				
		//Population of Birds from the population class
		_population = new Population(this); 
	
		//creates an LinkedList of type Pipe 
		_pipeList = new LinkedList<Pipe>();
		_pipePane = new Pane();		
				
		//generates more pipes
		 this.generatePipes();
		
		 //Pane for the sidebar with size and color
		_sideBarPane = new BorderPane();
		_sideBarPane.setStyle("-fx-background-color: #e6df95;");
		_sideBarPane.setPrefSize(Constants.SIDE_BAR_WIDTH,Constants.SIDE_BAR_HEIGHT);
		
		//Buttons instantiated
		_1xSpeedButton = new Button("1x");
		_2xSpeedButton = new Button("2x");
		_5xSpeedButton = new Button("5x");
		_MaxSpeedButton = new Button("Max");
		//generation int is set to 1 
		generations = 1; 
		
		//Labels instantiated
		_generationsLabel = new Label("Generation: " + generations);
		_fitnessLabel = new Label("Avg Fitness Last Gen: " + 0); 
		_aliveBirdsLabel = new Label("Alive Birds: " + _population.getAliveBirdsTotal());
		_bestBirdLabel = new Label("Best Fitness Last Gen: " + 0);
		_bestBirdEverLabel = new Label("Best Fitness All Time: " + 0);

		//Instances of ButtonPane and Label created
		_buttonPane = new HBox(); 
		_labelPane = new VBox(); 
		
		//Settings the location of buttons/labels on Panes
		_sideBarPane.setRight(_buttonPane);
		_sideBarPane.setLeft(_labelPane);
		_buttonPane.getChildren().addAll(_1xSpeedButton,_2xSpeedButton, _5xSpeedButton, _MaxSpeedButton);
		_labelPane.getChildren().addAll(_aliveBirdsLabel, _generationsLabel,_fitnessLabel, _bestBirdLabel, _bestBirdEverLabel);
		
		//setting ClickHandlers for all Buttons
		_1xSpeedButton.setOnAction(new ClickHandler_1x());
		_2xSpeedButton.setOnAction(new ClickHandler_2x());
		_5xSpeedButton.setOnAction(new ClickHandler_5x());
		_MaxSpeedButton.setOnAction(new ClickHandler_Max());
		
		//Setting the background image of the game
		_backgroundImage = new ImageView(); 
		_backgroundImage.setImage(Constants.BACKGROUND_IMAGE); 
		_backgroundImage.setFitWidth(Constants.PANEL_WIDTH);
		_backgroundImage.setFitHeight(Constants.PANEL_HEIGHT);
		
		//adding pipePane and background image to EvolutionPane
		_evolutionPane.getChildren().addAll(_pipePane, _backgroundImage);
		_backgroundImage.toBack();
		_evolutionPane.setBottom(_sideBarPane);
		
        //Creates and sets up the timeline
      	_timeline = new Timeline(); 
		this.setupTimeline(1);
		
	}
	
	/*
	 * This method returns the BorderPane for the Game to call in menu Pane in Menu
	 */
	public Pane getEvolutionPane() {
		return _evolutionPane; 		
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
	 * Takes in an int speed in order to change the duration of the timehandler for the different buttons
	 */
	public void setupTimeline(int speed){
		KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION / speed), new EvolutionHandler());	    
		_timeline = new Timeline(kf);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();  
	}
	
	/*
	 * This method is the EvolutionHandler. This method is called when the Timeline is setup. 
	 * This has multiple helper methods that are called, including updatingEvolution, 
	 * scrollPipes, removePipes, and restartEvolution. 
	 */
	private class EvolutionHandler implements EventHandler<ActionEvent> {

		public void handle (ActionEvent event) {
			//calling on update method of birds
			_population.updateEvolution();
			//updating # of alive birds in the game
			_aliveBirdsLabel.setText("Alive Birds: " + _population.getAliveBirdsTotal());
			//calling all helper methods
			this.scrollPipes();
			this.removePipes();
			this.restartEvolution();
        }
		
		/* 
		 * This method "scrolls" the pipes to ensure they move towards the left at a constant rate.
		 */
		
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
			Evolution.this.generatePipes(); //generates more pipes
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
		 * This method restart Evolution if the checkEvolutionOver method from Population returns true
		 * If so, selection is done, the labels change, population and pipes are removed and then added again. 
		 */
		private void restartEvolution() {
			if(_population.checkEvolutionOver() == true) { //checks if Evolution Game is over
				
				_population.selection(); //method from Population
				//updating all the labels with the correct new information by calling on getter methods in Population
				_bestBirdLabel.setText("Best Fitness Last Gen: " + _population.getBestFitnessCurrentGeneration());
				_fitnessLabel.setText("Avg Fitness Last Gen: " + _population.getAverageFitness()); 
				_bestBirdEverLabel.setText("Best Fitness All Time: " + _population.getBestFitnessAllTime());
				
				//clearing deadPopulation arraylist
				_population.clearDeadBirds();
				
				//clearing pipes logically and graphically
				_pipeList.clear(); 
				_pipePane.getChildren().clear();
				
				//creates an LinkedList of type Pipe
				_pipeList = new LinkedList<Pipe>();			
				
				Evolution.this.generatePipes(); //generate more pipes
				 
				//update generations label
				 generations++; 
				 _generationsLabel.setText("Generation: " + generations);		
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
			Evolution.this.getTimeline().setRate(1);
			event.consume(); //Consume the event here
		}
	}
	
	/* 
	 * ClickHandlers for the 2x button, sets the rate of the Evolution to 2 (double speed)
	 */
	private class ClickHandler_2x implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Evolution.this.getTimeline().setRate(2);
			event.consume(); //Consume the event here
		}
	}
	
	/* 
	 * ClickHandlers for the 1x button, sets the rate of the Evolution to 5 
	 */
	
	private class ClickHandler_5x implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Evolution.this.getTimeline().setRate(5); 
			event.consume(); //Consume the event here
		}
	}
	
	/* 
	 * ClickHandlers for the 1x button, sets the rate of the Evolution to 20 (max speed)
	 */
	private class ClickHandler_Max implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Evolution.this.getTimeline().setRate(20);
			event.consume(); //Consume the event here
		}
	}
}
