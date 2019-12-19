package Evolution;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;

/**
 * Bird Class:
 * Class for the bird in Game and Evolution
 * Bird is an ellipse filled with an image of the FlappyBird
 * Has 3 constructor, 1 for Game, and 2 (one overloaded) for 
 * Evolution 
 * Methods in the class include methods used in both Game and Evolution and
 * Evolution specific 
 * Evolution specific: kill, getinputNodes, getFitness, mutate, getNN
 */

public class Bird {
	
	//instance variable for the ellipse that makes up the Pane
	private Ellipse _bird;
	
	//Instance variable for the bird's fitness
	private int _fitness; 
	
	//Instance variable for the bird's neural network
	private NeuralNetwork _neuralNetwork; 
	private double[] _inputNodes; //double array for input nodes 
	 
	//Instance variable to associate Evolution to Bird
	private Evolution _evolution; 
	
	//Instance variables for current and updated velocity used in the updateDoodle method and score  
	private double _currentVelocity; 
	private double _updatedVelocity; 
	
	
	/* 
	 * This is the first bird Constructor used in the regular FlappyBird Game
	 * Takes in a parameter of type Pane for the bird to have its own Pane
	 * Sets size and image of the Bird, sets velocity, adds ellipse to birdPane and sets initial location
	 */
	public Bird(Pane birdPane) {
	
		//Bird's shape is set to a rectangle with constants created for its width and height 
		_bird = new Ellipse(10,10); 

		//setting the fill of the rectangle to the Image
		_bird.setFill(new ImagePattern(Constants.BIRD_IMAGE));
		
		//Sets current velocity to Rebound Velocity (inital jump velocity) and updated Velocity to 0 (updated in a method)
    	_currentVelocity = Constants.REBOUND_VELOCITY;
    	_updatedVelocity = 0.0;
    	
		birdPane.getChildren().add(_bird); 
		
		//setting initial location of the Bird on the Scene
		this.setXLoc(Constants.START_BIRD_X);
		this.setYLoc(Constants.START_BIRD_Y);
	}
	
	/*
	 * This is the second bird Constructor used in Evolution
	 * Parameter is evolution in order to associate both classes
	 * Sets size and image of the Bird, sets velocity, adds ellipse to birdPane and sets initial location
	 * Also creates NN for the bird and sets the initial fitness value
	 */
	
	public Bird(Evolution evolution) {
		
		//associating both classes
		_evolution = evolution;
		
		//Bird's shape is set to a ellipse with constants created for its width and height 
		_bird = new Ellipse(10,10); 

		//setting the fill of the ellipse to the Image
		_bird.setFill(new ImagePattern(Constants.BIRD_IMAGE));
		
		//NeuralNetwork is created for the bird
		_neuralNetwork =  new NeuralNetwork(); 
		
		//Setting the bird's fitness
		_fitness = 0; 
		
		//Sets current velocity to Rebound Velocity (inital jump velocity) and updated Velocity to 0 (updated in a method)
    	_currentVelocity = Constants.REBOUND_VELOCITY;
    	_updatedVelocity = 0.0;
		
		//setting initial location of the Bird on the Scene
		this.setXLoc(Constants.START_BIRD_X);
		this.setYLoc(Constants.START_BIRD_Y);
	}
	
	
	/*
	 * This is the overloaded Bird constructor for Evolution
	 * Another parameter of type NN in order to store the previous bird's NN for the next generated Bird
	 * Rest of the constructor is the same as the previous one. 
	 */
	public Bird(Evolution evolution, NeuralNetwork neuralNetwork)
	{
		_evolution = evolution;
		
		//Bird's shape is set to a ellipse with constants created for its width and height 
		_bird = new Ellipse(10,10); 
		
		//setting the fill of the ellipse to the Image
		_bird.setFill(new ImagePattern(Constants.BIRD_IMAGE));
		
		//NeuralNetwork is copied from the previous bird, uses NN class's overloaded constructor 
		_neuralNetwork = new NeuralNetwork(neuralNetwork);
		
		//Setting the bird's fitness
		_fitness = 0; 
		
		//Sets current velocity to Rebound Velocity (inital jump velocity) and updated Velocity to 0 (updated in a method)
    	_currentVelocity = Constants.REBOUND_VELOCITY;
    	_updatedVelocity = 0.0;
		
		//setting initial location of the Bird on the Scene
		this.setXLoc(Constants.START_BIRD_X);
		this.setYLoc(Constants.START_BIRD_Y);
	}
	
	public Ellipse getBird() {
		return _bird; 
	}

	/* 
	 * This method sets the x location of the Bird by taking in a double as a parameter.
	 */
	public void setXLoc(double x) {
		_bird.setCenterX(x);
	}
	
	/* 
	 * This method sets they location of the Bird by taking in a double as a parameter.
	 */
	public void setYLoc(double y) {
		_bird.setCenterY(y);
	}
	
	/* 
	 * This method gets the x location of the Bird
	 */
	public double getXLoc() {
		return _bird.getCenterX();
	}
	
	/* 
	 * This method gets the y location of the Bird
	 */
	public double getYLoc() {
		return _bird.getCenterY();
	}
	
	/* 
	 * This method allows the bird to fall according to gravity.
	 * The two variables, updateVelocity and currentVelocity are updated
	 * in the TimeHandler. 
	 * 	 
	*/
	public void updateVelocity() {
		double deltaVelocity = Constants.GRAVITY * Constants.DURATION;
		_updatedVelocity = _currentVelocity + deltaVelocity; 
		_currentVelocity = _updatedVelocity; 
		double deltaPosition = _updatedVelocity  * Constants.DURATION;
		this.setYLoc(this.getYLoc() + deltaPosition); 	
	} 
	
	/* 
	 * This method sets the currentVelocity of the bird
	 * Parameter is a double to set the currentVelocity
	 * to a new value. 
	 * Setter method
	 */
	public void setCurrentVelocity(double currentVelocity)
	{
		_currentVelocity = currentVelocity; 
	}
	
	/*
	 * This method returns a NN, getter method
	 */
	public NeuralNetwork getNeuralNetwork() {
		return _neuralNetwork; 
	}
	
	/*
	 * This method sets the NN, setter method
	 * Parameter is of type NN
	 */
	public void setNeuralNetwork(NeuralNetwork nn) {
		_neuralNetwork = nn; 
	}
	
	/*
	 * This method checks if a bird should jump or not 
	 * depending on what the output Node of the bird is. 
	 * Used in Evolution.
	 */
	public void jump() {
		if (this.getOutputNode() < 0.5) //if value of the output Node is above .5, then the bird jumps
		{
			_currentVelocity = Constants.REBOUND_VELOCITY; //tells the bird to "jump" by setting the _currentVelocity to rebound
		}
	}
	
	/* 
	 * This method sets and returns the input Nodes in order to run them throug the forward Propagation method.
	 * There are 4 input does, each described below, for an individual bird
	 */
	public double[] getInputNodes() {
		_inputNodes = new double[4]; //Array of inputNodes
		_inputNodes[0] = Constants.PANEL_HEIGHT - this.getYLoc(); //distance from the bottom of screen
		_inputNodes[1] = _evolution.getClosestPipeX() - this.getXLoc();  //distance from closest pipe
		_inputNodes[2] = _evolution.getBottomPipeY() - this.getYLoc(); //distance from bottom pipe to bird
		_inputNodes[3] = _evolution.getTopPipeY() - this.getYLoc(); //distance from top pipe to bird
		
		return _inputNodes; 
	}
	
	/* 
	 * This method returns the output node after forwardProp of the input Nodes
	 * getter method, returns double
	 */
	public double getOutputNode() {
		return _neuralNetwork.forwardPropagation(this.getInputNodes());
	}
	
	/*
	 * This method mutates the bird, calls on the mutate method in NN
	 */
	public void mutate() {
		_neuralNetwork.mutate(); 
	}
	
	/*
	 * This method kills a bird graphically by removing it from the Pane
	 */
	public void kill() {
		_evolution.getEvolutionPane().getChildren().remove(_bird);
	}
	
	/* 
	 * This method increases the fitness value when called. 
	 */
	public void increaseFitness() {
		 _fitness++; 
	}
	
	/* 
	 * This method returns the fitness value, getter method
	 */
	public int getFitness(){
		return _fitness;
	}
	
	/* 
	 * This method checks the intersection of the Bird and Pipe. Uses java method intersects(). 
	 * If-statement used to check if intersects method is true, then returns true. If not, returns false. 
	 */
	public boolean intersects(double pipeXLoc, double pipeYLoc, double pipeWidth, double pipeHeight) {
		if (_bird.intersects(pipeXLoc, pipeYLoc, pipeWidth, pipeHeight) == true)
			return true;
		else 
			return false; 
	}

}
