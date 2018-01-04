package com.hebe.flappyai.logic.nn;

import com.hebe.flappyai.logic.entities.Bird;

public class Genom {

	private NeuralNetwork neuralNetwork;
	private int initalGeneration = -1;
	
	private Bird bird;
	
	public Genom(Bird bird) {
		this.bird = bird;
		this.neuralNetwork = new NeuralNetwork(new int[] {3, 2, 1 });
		this.neuralNetwork.setRandomWeights(-30, 30);
	}

	public Genom(Bird bird, NeuralNetwork neuralNetwork) {
		this.bird = bird;
		this.neuralNetwork = neuralNetwork;
	}

	public void update(float delta, float d1, float d2, float d3) {
		this.bird.update(delta);
		double[] inputs = new double[3];
		inputs[0] = d1;
		inputs[1] = d2;
		inputs[2] = d3;

		double[] outputs = this.neuralNetwork.processInputs(inputs);
		if(outputs[0] > 0.5){
			this.bird.jump();
		}
		//this.car.setTurn((float) outputs[0]);
		//this.car.setEngine((float) outputs[0]);		
	}
	
	public Bird getBird() {
		return this.bird;
	}
	
	public NeuralNetwork getNeuralNetwork() {
		return this.neuralNetwork;
	}
	
	public void setInitalGeneration(int initalGeneration) {
		this.initalGeneration = initalGeneration;
	}
	
	public int getInitalGeneration() {
		return this.initalGeneration;
	}
	
	public float getFitness(){
		if(this.bird.getX() < 250){
			return (this.bird.getX()) / (1 + Math.abs(this.bird.getY() - 256) / 100) * 0.01f; 
		}else{
			return this.bird.getX();
		}
	}
	
}
