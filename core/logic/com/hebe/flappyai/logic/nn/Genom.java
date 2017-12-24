package com.hebe.flappyai.logic.nn;

public class Genom {

	private NeuralNetwork neuralNetwork;
	private int initalGeneration = -1;
	
	public Genom() {
		this.neuralNetwork = new NeuralNetwork(new int[] {3, 4, 3, 1 });
		this.neuralNetwork.setRandomWeights(-30, 30);
	}

	public Genom(NeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}

	public void update(float delta) {

		double[] inputs = new double[5];
		for (int i = 0; i < 5; i++) {
			inputs[i] = 0; // sensor value
		}

		double[] outputs = this.neuralNetwork.processInputs(inputs);
		//use outputs
		//this.car.setTurn((float) outputs[0]);
		//this.car.setEngine((float) outputs[0]);		
	}
	
	public NeuralNetwork getNeuralNetwork() {
		return neuralNetwork;
	}
	
	public void setInitalGeneration(int initalGeneration) {
		this.initalGeneration = initalGeneration;
	}
	
	public int getInitalGeneration() {
		return initalGeneration;
	}
	
	public float getFitness(){
		return 0f;
	}
	
}
