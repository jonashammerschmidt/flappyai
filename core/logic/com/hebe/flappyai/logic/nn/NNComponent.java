package com.hebe.flappyai.logic.nn;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.hebe.flappyai.hud.components.HUDComponent;

public class NNComponent extends HUDComponent {

	private NeuralNetwork nn;
	private double[][] activation;

	public NNComponent(int x, int y) {
		super(x, y);
	}

	public void setNn(NeuralNetwork nn) {
		this.nn = nn;
	}

	public void setNn(NeuralNetwork nn, double[] inputs) {
		this.nn = nn;
		this.activation = nn.showProcessing(inputs);
	}

	@Override
	public void draw(SpriteBatch batch, ShapeRenderer shape, BitmapFont font) {
		int height = 0;
		for (int i = 0; i < this.nn.topology.length; i++) {
			if (this.nn.topology[i] > height) {
				height = this.nn.topology[i];
			}
		}

		double[] highestWeight = new double[this.nn.topology.length - 1];
		for (int i = 0; i < this.nn.topology.length - 1; i++) {
			for (int j = 0; j < this.nn.topology[i]; j++) {
				for (int k = 0; k < this.nn.topology[i + 1]; k++) {
					double weight = Math.abs(this.nn.layers[i].weights[j][k]);
					if (highestWeight[i] < weight) {
						highestWeight[i] = weight;
					}
				}
			}
		}

		shape.begin(ShapeType.Filled);
		shape.setColor(Color.WHITE);
		for (int i = 0; i < this.nn.topology.length - 1; i++) {
			for (int j = 0; j < this.nn.topology[i]; j++) {
				for (int k = 0; k < this.nn.topology[i + 1]; k++) {
					double weight = Math.abs(this.nn.layers[i].weights[j][k]);
					shape.setColor(1f, 1f - (float) (weight / highestWeight[i]), 1f - (float) (weight / highestWeight[i]), 1f);
					shape.rectLine(this.x + 20 + i * 85, this.y + 50 + (height - this.nn.topology[i]) * 30 + j * 60, this.x + 20 + (i + 1) * 85, this.y + 50 + (height - this.nn.topology[i + 1]) * 30 + k * 60,
							1f + (float) (weight / highestWeight[i]) * 5f);
				}
			}
		}
		shape.end();


		double highestActivation = 0.0;
		if(this.activation != null){
			for (int i = 0; i < this.activation.length; i++) {
				for (int j = 0; j < this.activation[i].length; j++) {
					if (highestActivation < Math.abs(this.activation[i][j])) {
						highestActivation = Math.abs(this.activation[i][j]);
					}
				}
			}
		}
		
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.WHITE);
		for (int i = 0; i < this.nn.topology.length; i++) {
			for (int j = 0; j < this.nn.topology[i]; j++) {
				if(this.activation != null){
					shape.setColor(1f, 1f - (float) (Math.abs(this.activation[i][j]) / highestActivation), 1f - (float) (Math.abs(this.activation[i][j]) / highestActivation), 1f);
				}
				shape.circle(this.x + 20 + i * 85, this.y + 50 + (height - this.nn.topology[i]) * 30 + j * 60, 20);
			}
		}
		shape.end();
	}

}
