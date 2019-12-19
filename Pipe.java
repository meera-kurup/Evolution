package Evolution;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;

/**
 * Pipe Class:
 * 
 * Handles all the information about the pipes, including creating a Pipe
 * which is a composite shape made out of two rectangles. Methods include 
 * generate and getting various values of pipe like X and Y location 
 * of top and bottom and heights.
 * 
 */
public class Pipe {
	
	//Instance variables for the rectangles that make the top and bottom pipe
	private Rectangle _topPipe; 
	private Rectangle _bottomPipe; 
	
	//Instance variable for pipe's Pane for graphical view 
	private Pane _pipePane; 
	
	//Instance variables for the height of the top and bottom pipes
	private int _topPipeHeight;
	private int _bottomPipeHeight; 
	
	/*
	 * This is the Pipe constructor where the pipes are created on a Pane and the 
	 * generatePipe method is called to graphically generate a pipe. 
	 */
	public Pipe(Pane pipePane) {
		
		//setting the instance variable for the Pipe's pane to the Pane parameter the constructor takes in 
		_pipePane = new Pane();
		_pipePane = pipePane; 
		
		//calls on method to generate pipe
		this.generatePipe();
	}
	
	/*
	 * This method generates each individual pipe with the top and bottom and the height and widths
	 * and then adding the pipes to the Pane and settings the initial x and y locations. 
	 */
	
	//
	public void generatePipe() 
	{
		//Random interger created to set the height of the top pipe
		//random interger is between 100 and 300 so there is ample space for the bird to move between two pipes
		int rand = Constants.MIN_TOP_PIPE_HEIGHT + (int)(Math.random() * ((Constants.MAX_TOP_PIPE_HEIGHT - Constants.MIN_TOP_PIPE_HEIGHT) + 1));
		_topPipeHeight = rand; 
		//bottom pipe's height is the rest of the distance after the gap height
		_bottomPipeHeight = Constants.PANEL_HEIGHT - Constants.SIDE_BAR_HEIGHT - Constants.GAP_HEIGHT - _topPipeHeight; 
		
		//set the top and bottom pipe as rectangles with widths and heights and setting fill
		_topPipe = new Rectangle(Constants.PIPE_WIDTH, _topPipeHeight); 
		_bottomPipe = new Rectangle(Constants.PIPE_WIDTH, _bottomPipeHeight);

		//Bird's image is set to the image saved in the DoodleJump project
		_topPipe.setFill(new ImagePattern(Constants.TOP_PIPE_IMAGE));
		_bottomPipe.setFill(new ImagePattern(Constants.BOTTOM_PIPE_IMAGE));

		//adding rectangles to the Pane
		_pipePane.getChildren().addAll(_topPipe, _bottomPipe); 
		
		//Setting the first pipe's X and Y location
		this.setXLoc(400);
        this.setYLoc(0);
	}
	
	/* 
	 * This method sets the x location of the pipe by taking in a double as a parameter.
	 */
	public void setXLoc(double x) {
		_topPipe.setX(x);
		_bottomPipe.setX(x);
	}
	
	/* 
	 * This method sets the y location of the pipe by taking in a double as a parameter.
	 */
	public void setYLoc(double y) {
		_topPipe.setY(y);
		_bottomPipe.setY(y + _topPipeHeight + Constants.GAP_HEIGHT);
	}
	
	/* 
	 * This method gets the x location of the pipe
	 */
	public double getXLoc() {
		return _topPipe.getX();
	}
	
	/* 
	 * This method gets the y location of the pipe
	 */
	public double getYLoc() {
		return _topPipe.getY();
	}
	
	/*
	 * This method returns the top Pipe of type Rectangle.
	 * Used to graphically remove pipes. 
	 */
	public Rectangle getTopPipe() {
		return _topPipe; 
	}
	
	/*
	 * This method returns the bottom Pipe of type Rectangle.
	 * Used to graphically remove pipes. 
	 */
	public Rectangle getBottomPipe() {
		return _bottomPipe; 
	}
	
	/*
	 * This method returns the bottom pipe's Y location since it is different than the top Pipe's Y Location
	 */
	public double getBottomPipeYLoc(){
		return _bottomPipe.getY();
	}
	
	/*
	 * This method returns the height of the top pipe. 
	 */
	public double getTopPipeHeight() {
		return _topPipe.getHeight();
	}
	/*
	 * This method returns the width of the top pipe. 
	 */
	public double getTopPipeWidth() {
		return _topPipe.getWidth();
	}
	/*
	 * This method returns the height of the bottom pipe. 
	 */
	public double getBottomPipeHeight() {
		return _bottomPipe.getHeight();
	}
	/*
	 * This method returns the width of the bottom pipe. 
	 */
	public double getBottomPipeWidth() {
		return _bottomPipe.getWidth();
	}
}
