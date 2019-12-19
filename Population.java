package Evolution;

import java.util.ArrayList;
import java.util.*; 
import java.util.Stack; 
import java.util.Collections;

/**
 * Population Class: 
 * 
 * Handles the entire population of birds created in one generation
 * Stores all birds in an arraylist, pops them onto a stack
 * and stores the best birds in an arraylist
 * Returns important information like fitness and alive birds through getters
 * Selection method is here to select best birds and birds are all updated. 
 * Also checks if the Evolution Game is over.
 */

public class Population {

	//Instance variable for Alive Population (stores all 50 birds and then removes them logically and graphically)
	private ArrayList<Bird> _alivePopulation; 
	//Instance variable for Stack of Dead Population (stores all 50 birds as they die, in order)
	private Stack<Bird> _deadPopulation;
	//Instance variable for the ArrayList of Best Birds (store stop 3 best birds of all time) 
	private ArrayList<Bird> _bestBirds; 
		 
	//instance variable of type evolution for association
	private Evolution _evolution; 
	
	/* 
	 * This is the population Constructor
	 * It is associated with Evolution (game logic is held)
	 * therefore the parameter is of type evolution
	 */
	public Population(Evolution evolution) {
		
		//Association the parameter and type Evolution
		_evolution = evolution;
		
		//Setting the ArrayLists and stacks
		_alivePopulation = new ArrayList<Bird>(); 
		_deadPopulation = new Stack<Bird>();
		_bestBirds = new ArrayList<Bird>(); 
		
		//Populating the alive population arraylist with birds
		for(int i = 0; i < Constants.POPULATION; i++)
		{
			Bird bird = new Bird(evolution);
			_alivePopulation.add(bird);
			evolution.getEvolutionPane().getChildren().add(bird.getBird()); //adding to evolution's pane
		}
		
	}
	
	/* 
	 * Method to get/return Alive Population's size (getter)
	 */
	public int getAliveBirdsTotal() {
		return _alivePopulation.size();
	}
	
	/*
	 * This method returns the average fitness of all the birds in a generation.
	 * The method loops through the dead population arrayList to receive each bird's
	 * fitness, then adds them all up and divides by total number of birds in 
	 * deadPopulation
	 * 
	 */
	public int getAverageFitness() {
		int totalFitness = 0; 
		
		for (int i = 0; i < _deadPopulation.size(); i++)
		{
			//adding up all the deadPopulation birds' fitness
			totalFitness += _deadPopulation.get(i).getFitness();
		}
		
		return totalFitness/_deadPopulation.size(); //returning the average
	}

	
	/*
	 * Getter Method to get/return the Best bird's fitness of all time 
	 * Retrieve from _bestBirds since it holds the best bird of all time
	 */
	public int getBestFitnessAllTime() {
		return _bestBirds.get(0).getFitness();
	}
	
	/*
	 * Getter Method to get/return the Best bird's fitness of the current
	 * generation. Retrieve from _deadPopulation since it holds the best bird 
	 * in the current generation
	 */
	public int getBestFitnessCurrentGeneration() {
		return _deadPopulation.pop().getFitness(); 
	}
	
	/*
	 * This method updates the Evolution game's birds for every timestep, here are the steps: 
	 * First, the bird's velocities are updated 
	 * Second, their fitness value is increases 
	 * Third, forwardPropagation is called on the NN
	 * Fourth, the birds are told to jump depending on output Node
	 * Fifth, check if the birds have died or not through if statements
	 * Sixth, check if game over
	 */
	public void updateEvolution() {
		for(int i = _alivePopulation.size() - 1; i >= 0; i--)
		{	
			_alivePopulation.get(i).updateVelocity(); //updates velocity
			_alivePopulation.get(i).increaseFitness();  // increases bird's fitness
			_alivePopulation.get(i).getNeuralNetwork().forwardPropagation(_alivePopulation.get(i).getInputNodes()); //call forwardProp on NN
			_alivePopulation.get(i).jump(); //tells the bird to jump or not
			
			//If statements to check if the birds die or not 
			if(_alivePopulation.get(i).getYLoc() < 0) //if the bird hits the ceiling
			{
				_alivePopulation.get(i).kill();  //birds are removed graphically by Bird's kill method
				_deadPopulation.push(_alivePopulation.remove(i)); //bird is then pushed onto stack and removes bird logically
				
			}
			else if(_alivePopulation.get(i).getYLoc() > (Constants.PANEL_HEIGHT - Constants.SIDE_BAR_HEIGHT)) //if the bird hits the floor
			{
				_alivePopulation.get(i).kill(); 
				_deadPopulation.push(_alivePopulation.remove(i)); 
			}
			//if the bird intersects with the top pipe
			else if(_alivePopulation.get(i).intersects(_evolution.getClosestPipeX(),_evolution.getTopPipeY(), Constants.PIPE_WIDTH, _evolution.getTopPipeHeight()))
			{
				_alivePopulation.get(i).kill(); 
				_deadPopulation.push(_alivePopulation.remove(i)); 
			}
			//if the bird intersects with the bottom pipe
			else if(_alivePopulation.get(i).intersects(_evolution.getClosestPipeX(),_evolution.getBottomPipeY(), Constants.PIPE_WIDTH, _evolution.getBottomPipeHeight()))
			{
				_alivePopulation.get(i).kill(); 
				_deadPopulation.push(_alivePopulation.remove(i)); 

			} 
			//if the bird has been alive for too long (aka has a fitness of over 4000)
			else if(_alivePopulation.get(i).getFitness() > Constants.WIN_FITNESS) 
			{
				_alivePopulation.get(i).kill(); 
				_deadPopulation.push(_alivePopulation.remove(i)); 
			}
			//calls on method to check if the game is over
			this.checkEvolutionOver(); 
		}
	}
	
	/* 
	 * This method checks if Evolution is over by checking if the alivePopulation arraylist is empty (at 0)
	 * if so, returns true and if true, Evolution is restarted (restart handled in Evolution class) 
	 */
	public boolean checkEvolutionOver() {
		return _alivePopulation.size() == 0;
	}

	/* 
	 * This method clears the deadPopulation ArrayList after each generation 
	 * Ensures the deadPopulation doesn't keep on increasing in size after the game has ended 
	 * clears the deadBird by clearing the population list. 
	 * 
	 */
	public void clearDeadBirds() {
		_deadPopulation.clear();
	}
	
	/* 
	 * Implemented class called BirdCompare that implements a comparator to call on Collections.sort to sort the bestBirds ArrayList
	 * Has only one method called compare
	 */
	public class BirdCompare implements Comparator<Bird> {
		
		/* 
		 * This method takes takes in two birds and returns the bird with the higher fitness by comparing them
		 * and returning the higher fitness of the bird. 
		 */
		public int compare(Bird a, Bird b)
		{
			return b.getFitness() - a.getFitness(); 
		}
	}
	
	/* 
	 * This is the selection method. This method selects the best birds from a population and repopulates the 
	 * alivePopulation ArrayList for the next generation.  
	 */
	public void selection() {

		//Pops off the first three birds of deadPopulation (these are the best birds of the previous generation) 
		for(int i = 0; i < 3; i++) {
			Bird bird = _deadPopulation.pop();
			_bestBirds.add(bird); //adds bird to the _bestBirds arraylist 
		} 
		
		//From the second generation onwards, _bestBirds will have 6 birds
		//It must be cut down to keep the size of the ArrayList small
		//and only hold the three best birds
			
		//calls on BirdCompare to sort the BestBirds arraylist so the best birds are the first elements of the list
		Collections.sort(_bestBirds, new BirdCompare());
				
		//Removes the worst 3 birds that are at the end of the arraylist
		for(int i = _bestBirds.size() - 1; i > 2; i--)
		{
			_bestBirds.remove(i);
		}
		
		//Populates the aliveBirds ArrayList with birds from the bestBirds ArrayList
		for(int i = 0; i < Constants.POPULATION; i++)
		{
			if(i < Constants.POPULATION/2) //For the first 25 birds (half the population)
			{
				//create new bird with NN of best bird (at index 0 in bestBirds) 
				Bird newBird = new Bird(_evolution, _bestBirds.get(0).getNeuralNetwork());
				//mutates bird
				newBird.mutate();
				//adds bird to alive population logically
				_alivePopulation.add(newBird);
				//adds graphically
				_evolution.getEvolutionPane().getChildren().add(newBird.getBird()); 
			}
			else if(i < Constants.POPULATION - 10) //for the next 15 birds
			{
				//create new bird with NN of second Best bird (at index 1 in bestBirds) 
				Bird newSecondBird = new Bird(_evolution, _bestBirds.get(1).getNeuralNetwork());
				//mutates bird
				newSecondBird.mutate();
				//adds bird to alive population logically
				_alivePopulation.add(newSecondBird);
				//adds graphically
				_evolution.getEvolutionPane().getChildren().add(newSecondBird.getBird()); 
			}
			else //for the rest of the birds (10 of them)
			{
				//create new bird with NN of third best bird (at index 2 in bestBirds) 
				Bird newThirdBird = new Bird(_evolution, _bestBirds.get(2).getNeuralNetwork());
				//mutates bird
				newThirdBird.mutate();
				//adds bird to alive population logically
				_alivePopulation.add(newThirdBird);
				//adds graphically
				_evolution.getEvolutionPane().getChildren().add(newThirdBird.getBird()); 
			}	
		}	
	}
}
