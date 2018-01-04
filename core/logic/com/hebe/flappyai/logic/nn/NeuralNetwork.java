package com.hebe.flappyai.logic.nn;

import java.io.Serializable;

public class NeuralNetwork implements Serializable{

	private static final long serialVersionUID = 3844991511850413117L;

	public NeuralLayer[] layers;

	public int[] topology;

	public int weightCount;
	
	public NeuralNetwork(int[] topology) {
		this.topology = topology;

		// Calculate overall weight count
		this.weightCount = 0;
		for (int i = 0; i < topology.length - 1; i++)
			this.weightCount += (topology[i] + 1) * topology[i + 1]; // + 1 for bias node

		// Initialise layers
		this.layers = new NeuralLayer[topology.length - 1];
		for (int i = 0; i < this.layers.length; i++) {
			this.layers[i] = new NeuralLayer(topology[i], topology[i + 1]);
		}
	}

	public double[] processInputs(double[] inputs) {
		if (inputs.length != this.layers[0].neuronCount)
			System.err.println("Given inputs do not match network input amount.");

		double[] outputs = inputs;
		for (NeuralLayer layer : this.layers) {
			outputs = layer.processInputs(outputs);
		}

		return outputs;
	}
	


	public double[][] showProcessing(double[] inputs) {
		if (inputs.length != this.layers[0].neuronCount)
			System.err.println("Given inputs do not match network input amount.");

		double[][] activations = new double[this.layers.length][];
		double[] outputs = inputs;
		for (int i = 0; i < this.layers.length; i++) {
			outputs = this.layers[i].processInputs(outputs);
			activations[i] = outputs;
		}

		return activations;
	}
	
	public void setRandomWeights(double minValue, double maxValue) {
		if (this.layers != null) {
			for (NeuralLayer layer : this.layers) {
				layer.setRandomWeights(minValue, maxValue);
			}
		}
	}

	public NeuralNetwork getTopologyCopy() {
		return new NeuralNetwork(this.topology);
	}

	public NeuralNetwork deepCopy() {
		NeuralNetwork newNet = new NeuralNetwork(this.topology);
		for (int i = 0; i < this.layers.length; i++)
			newNet.layers[i] = this.layers[i].deepCopy();

		return newNet;
	}

	@Override
	public String toString() {
		String output = "";

		for (int i = 0; i < this.layers.length; i++)
			output += "Layer " + i + ":\n" + this.layers[i].toString();

		return output;
	}

	public void mutate(float value) {
		for(NeuralLayer layer : this.layers) {
			layer.mutate(value);
		}
	}
}
