package Evolution;

import javafx.scene.image.Image;

/**
 * Constants Class:
 * Holds all necessary constants in the game. 
 * Images, ints, and doubles
 */

public class Constants {
	
	//BIRD Constants
	public static final int GRAVITY = 1000; // acceleration constant (UNITS: pixels/s^2)
    public static final int REBOUND_VELOCITY = -300; // initial jump velocity (UNITS: pixels/s)
    public static final double DURATION = 0.016; // // KeyFrame duration (UNITS: s)
    
    //Panel Constants
    public static final int PANEL_WIDTH = 400;  
    public static final int PANEL_HEIGHT = 550;  
    public static final int SIDE_BAR_WIDTH = 400;  
    public static final int SIDE_BAR_HEIGHT = 100;  

    //PIPE Constants    
    public static final int PIPE_WIDTH = 40; 
    public static final int MIN_TOP_PIPE_HEIGHT = 100; 
    public static final int MAX_TOP_PIPE_HEIGHT = 300; 
    public static final int GAP_HEIGHT = 110; 
    public static final int PIPE_DISTANCE = 175; 
    public static final int CLOSEST_PIPE = 60; 


    
    //PIPE start position
    public static final int TOP_PIPE_START_X = 200;
    public static final int TOP_PIPE_START_Y = 0;  
    public static final int BOTTOM_PIPE_START_X = 200;  
    public static final int BOTTOM_PIPE_START_Y = 300;  
    
    //BIRD start position  
    public static final int START_BIRD_X = 100;  
    public static final int START_BIRD_Y = 50;    
    
    //POPULATION
    public static final int POPULATION = 50; 
	public static final int WIN_FITNESS = 4000; //
	
	//BIRD and PIPE Image
	public static final Image BIRD_IMAGE = new Image("file:./Evolution/flappy_bird.png");
	public static final Image TOP_PIPE_IMAGE = new Image("file:./Evolution/top_pipe.png");
	public static final Image BOTTOM_PIPE_IMAGE = new Image("file:./Evolution/bottom_pipe.png");
	public static final Image BACKGROUND_IMAGE = new Image("file:./Evolution/background.png");

}
