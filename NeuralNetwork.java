package Evolution;

/**
 * NeuralNetwork Class:
 * 
 * This class handles all the "machine learning" of the population of birds
 * A NeuralNetwork (NN) is made of up 3 layers: input (4), hidden(5), and output(1)
 * each with weights (syn0 and syn1) that change the value of these layers 
 * The final output node decides whether the bird jumps or does not
 * The main methods include forwardPro, mutation, and sigmoid activation
 * The constructor is overloaded so the next bird can know the previous bird's NN
 * in order to learn 
 */

public class NeuralNetwork {
	
	//Instance variables
	
	//syn0 and syn1
	private double[][] syn0; 
	private double[][] syn1; 
	
	//hidden layer
	private double[] _hiddenLayer; 
	
	//output node (only a single value) 
	private double _outputNode; 
	
	/* 
	 * NeuralNetwork Main Constructor: 
	 * called when the first set of birds are created and need to have a NeuralNetwork
	 * sets weights in syn0 and syn1 mainly
	 */
	public NeuralNetwork() {

		//creating double array for syn0 (5 by 4 since 5 hidden layer nodes and 4 input nodes)
		syn0 = new double[5][4];
		for(int i = 0; i < syn0.length; i++)
		{
			for(int j = 0; j < syn0[i].length; j++)
			{
				//setting the weight to a random num between -1 and 1
				syn0[i][j] = (Math.random() * (1 + 1)) + (-1);
			}
		}
		//creating a double array for syn1 (1 by 5 since 5 hidden layer nodes and 1 output node)
		syn1 = new double[1][5];
		for(int i = 0; i < syn1[0].length; i++)
		{
			//setting the weight to a random num between -1 and 1
			syn1[0][i] = (Math.random() * (1 + 1)) + (-1); 
		}
		
		//creating the hiddenLayer array
		_hiddenLayer = new double[5];
		//setting output node to 0
		_outputNode = 0; 
		
	}
	
	
	/*
	 * This is NeuralNetwork Constructor overloaded.
	 * Overloaded in order to call on the previous bird's NN 
	 * when creating the next generation to learn
	 * Parameter: NN for learning 
	 */
	public NeuralNetwork(NeuralNetwork neuralNetwork) {
		
		
		syn0 = new double[5][4];
		//copying syn0 of the parameter's NN
		syn0 = neuralNetwork.copySyn0();
		
		syn1 = new double[1][5];
		//copying syn1 of the parameter's NN
		syn1 = neuralNetwork.copySyn1();
		
		//mutating the NN
		neuralNetwork.mutate();
		
		//creating the hiddenLayer array
		_hiddenLayer = new double[5];
		//setting output node to 0
		_outputNode = 0; 
	}
	
	/*
	 * This is the forwardPropagation method used to calculate the 
	 * output of the NeuralNetwork based on input values. 
	 * This is called for every new Population that is updated
	 * Parameters: the input nodes
	 * Returns: single output node (double type) 
	 */
	
	public double forwardPropagation(double[] inputNodes) {
				
		//set the hidden layer to the dot product of syn0 and inputNodes
		_hiddenLayer = this.dotProduct(syn0, inputNodes);
		
		//activating the Hidden Layer by calling the sigmoid function on the layer
		for(int i = 0; i < _hiddenLayer.length; i++)
		{
			this.sigmoidFunction(_hiddenLayer[i]);
		}
		
		//set the output node to the dot product of syn1 and hiddenLayer
		double[] outputNode = this.dotProduct(syn1, _hiddenLayer); 

		
		//since only one value is stored in first index of array, calling on that one index
		//and activating the OutputNode by calling the sigmoid function on the layer
		_outputNode = this.sigmoidFunction(outputNode[0]);
		
		//returning the single outputNode
		return _outputNode; 
	}


	/*
	 * This method carries out the dot-product. 
	 * Used in order to multiple weights but nodes (input and hidden layer)
	 * Parameters are for weights and inputNodes/hiddenNodes
	 * returns double array
	 */
	public double[] dotProduct(double[][] weights, double[] inputNodes)
	{
		//double array that is returned
		double[] layer = new double[weights.length];
		
		for(int i = 0; i < weights.length; i++)
		{
			int value = 0; 
			for(int j = 0; j < weights[0].length; j++)
			{
				//stores the multiplication of the nodes and adds them up
				value += weights[i][j] * inputNodes[j];
			}
			//sets one index of the array to the value calculated
			layer[i] = value; 
		}
		return layer; 
	}
	
	/*
	 * This is the sigmoid activation function. 
	 * This function takes in an x and runs it in the function to return a number between 0 and 1.
	 * Domain: (0 to 1)
	 * This method is used in the NN (explained in write-up)
	 */
	public double sigmoidFunction(double x) {
		return 1/(1 + Math.pow(Math.E,(-1*x)));
	}
	
	/*
	 * This is the tanh activation function. 
	 * This function takes in an x and runs it in the function to return a number between -1 and 1.
	 * Domain: (-1 to 1)
	 * This method is not used, but was tested with and proved
	 * to not be effective (explained in write-up)
	 */
	public double tanhFunction(double x) {
		return (2/(1 + Math.pow(Math.E,(-2*x)))) - 1;
	}
	
	/*
	 * This is the ReLU activation function. 
	 * This function takes in an x and returns either the x value or 0, 
	 * depending on if the x is greater than 0.
	 * Domain: (0 to infinity)
	 * This method is not used, but was tested with and proved
	 * to not be effective (explained in write-up)
	 */
	public double ReLUFunction(double x) {
		if(x >= 0)
		{
			return x;
		}
		else {
			return 0; 
		}
	}
	
	
	/* 
	 * This method copies the syn0 values and creates a new array to store the values.
	 * Returns the copy array. 
	 * Called in constructor when mutating. 
	 */
	public double[][] copySyn0() {
		
		double[][] syn0Copy = new double[5][4];
		for(int i = 0; i < syn0Copy.length; i++)
		{
			for(int j = 0; j < syn0Copy[0].length; j++)
			{
				syn0Copy[i][j] = syn0[i][j]; 
			}
		}
		return syn0Copy; 
	}
	
	/* 
	 * This method copies the syn1 values and creates a new array to store the values.
	 * Returns the copy array. 
	 * 	 * Called in constructor when mutating. 

	 */
	public double[][] copySyn1() {
		
		double[][] syn1Copy = new double[1][5];
		for(int k = 0; k < syn1.length; k++)
		{
			syn1Copy[0][k] = syn1[0][k] ; 
		}
		
		return syn1Copy; 
	}
	
	/*
	 * This is the mutation method that mutates the Birds when called. 
	 * The syn0 and syn1 values are the ones being mutated. 
	 * The mutation rate is 2% (0.02) and if the syn0 or syn1 value is to be mutated, 
	 * 0.1 is added to the value to "mutate"
	 */
	public void mutate() {
		for(int i = 0; i < syn0.length; i++)
		{
			for(int j = 0; j < syn0[0].length; j++)
			{
				if(Math.random() < 0.05)
				{
					syn0[i][j] = syn0[i][j] + 0.15;
					
					//if the mutated value is not between -1 and 1, set the value to a random num

					if(syn0[i][j] > 1 || syn0[i][j] < -1 )
					{
						syn0[i][j] = (Math.random() * (1 + 1)) + (-1);
					}
				}
			}
		}
		for(int k = 0; k < syn1.length; k++)
		{
			if(Math.random() < 0.05)
			{
				syn1[0][k] = syn0[0][k] + 0.15;
				
				//if the mutated value is not between -1 and 1, set the value to a random num
				if(syn1[0][k] > 1 || syn1[0][k] < -1)
				{
					syn1[0][k] = (Math.random() * (1 + 1)) + (-1);
				}
				
			}
			
		}		
	}
}
