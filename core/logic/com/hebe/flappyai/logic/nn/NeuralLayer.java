package com.hebe.flappyai.logic.nn;

import java.io.Serializable;
import java.util.Random;

public class NeuralLayer implements Serializable{

	private static final long serialVersionUID = -5025152069198362955L;

	private static Random randomizer = new Random();

	public int neuronCount;

	public int outputCount;

	public double[][] weights;

	public NeuralLayer(int nodeCount, int outputCount) {
		this.neuronCount = nodeCount;
		this.outputCount = outputCount;

		this.weights = new double[nodeCount + 1][outputCount]; // + 1 for bias node
	}
	
	public void setWeights(double[] weights) {
		// Check arguments
		if (weights.length != this.weights.length)
			System.err.println("Input weights do not match layer weight count.");

		// Copy weights from given value array
		int k = 0;
		for (int i = 0; i < this.weights.length; i++)
			for (int j = 0; j < this.weights[i].length; j++)
				this.weights[i][j] = weights[k++];
	}

	public double[] processInputs(double[] inputs) {
		// Check arguments
		if (inputs.length != this.neuronCount)
			System.err.println("Given xValues do not match layer input count.");

		// Calculate sum for each neuron from weighted inputs and bias
		double[] sums = new double[this.outputCount];
		// Add bias (always on) neuron to inputs
		double[] biasedInputs = new double[this.neuronCount + 1];
		for (int i = 0; i < inputs.length; i++) {
			biasedInputs[i] = inputs[i];
		}
		biasedInputs[inputs.length] = 1.0;

		for (int i = 0; i < this.weights.length; i++)
			for (int j = 0; j < this.weights[i].length; j++)
				sums[j] += biasedInputs[i] * this.weights[i][j];

		for (int i = 0; i < sums.length; i++)
			sums[i] = sigmoid(sums[i]);

		return sums;
	}

	public NeuralLayer deepCopy() {
		// Copy weights
		double[][] copiedWeights = new double[this.weights.length][];
		for (int i = 0; i < this.weights.length; i++) {
			copiedWeights[i] = new double[this.weights[i].length];
			for (int j = 0; j < this.weights[i].length; j++)
				copiedWeights[i][j] = this.weights[i][j];
		}

		// Create copy
		NeuralLayer newLayer = new NeuralLayer(this.neuronCount, this.outputCount);
		newLayer.weights = copiedWeights;

		return newLayer;
	}

	public void setRandomWeights(double minValue, double maxValue) {
		double range = Math.abs(minValue - maxValue);
		for (int i = 0; i < this.weights.length; i++)
			for (int j = 0; j < this.weights[i].length; j++)
				this.weights[i][j] = minValue + (randomizer.nextDouble() * range); // random double between minValue and maxValue
	}

	@Override
	public String toString() {
		String output = "";

		for (int i = 0; i < this.weights.length; i++) {
			for (int j = 0; j < this.weights[i].length; j++) {
				output += "[" + i + "," + j + "]: " + this.weights[i][j];
			}
			output += "\n";
		}

		return output;
	}

	public double sigmoid(double value) {
		if (value > 10) return 1.0;
        else if (value < -10) return 0.0;
        else return 1.0 / (1.0 + Math.exp(-value));
	}
	
	public void mutate(float value) {
		for (int i = 0; i < this.weights.length; i++) {
			for (int j = 0; j < this.weights[i].length; j++) {
				if(randomizer.nextDouble() < this.weights[i][j]) {
					this.weights[i][j] += Math.max(this.weights[i][j] - value, 0) * randomizer.nextDouble();
				}else {
					this.weights[i][j] += Math.min(this.weights[i][j] + value, 1) * randomizer.nextDouble();
				}
			}
		}
	}
}