package com.hebe.flappyai.logic.nn;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.hebe.flappyai.hud.components.HUDComponent;

public class NNComponent extends HUDComponent {

	private NeuralNetwork nn;

	public NNComponent(int x, int y) {
		super(x, y);
	}

	public void setNn(NeuralNetwork nn) {
		this.nn = nn;
	}

	@Override
	public void draw(SpriteBatch batch, ShapeRenderer shape, BitmapFont font) {
		int height = 0;
		for (int i = 0; i < nn.topology.length; i++) {
			if (nn.topology[i] > height) {
				height = nn.topology[i];
			}
		}

		double[] highestWeight = new double[nn.topology.length - 1];
		for (int i = 0; i < nn.topology.length - 1; i++) {
			for (int j = 0; j < nn.topology[i]; j++) {
				for (int k = 0; k < nn.topology[i + 1]; k++) {
					double weight = Math.abs(nn.layers[i].weights[j][k]);
					if (highestWeight[i] < weight) {
						highestWeight[i] = weight;
					}
				}
			}
		}

		shape.begin(ShapeType.Filled);
		shape.setColor(Color.WHITE);
		for (int i = 0; i < nn.topology.length - 1; i++) {
			for (int j = 0; j < nn.topology[i]; j++) {
				for (int k = 0; k < nn.topology[i + 1]; k++) {
					double weight = Math.abs(nn.layers[i].weights[j][k]);
					shape.setColor(1f, 1f - (float)(weight / highestWeight[i]), 1f - (float)(weight / highestWeight[i]), 1f);
					shape.rectLine(100 + i * 85, 50 + (height - nn.topology[i]) * 30 + j * 60, 100 + (i + 1) * 85,
							50 + (height - nn.topology[i + 1]) * 30 + k * 60, 1f + (float)(weight / highestWeight[i])*5f);
				}
			}
		}
		shape.end();

		shape.begin(ShapeType.Filled);
		shape.setColor(Color.WHITE);
		for (int i = 0; i < nn.topology.length; i++) {
			for (int j = 0; j < nn.topology[i]; j++) {
				shape.circle(100 + i * 85, 50 + (height - nn.topology[i]) * 30 + j * 60, 20);
			}
		}
		shape.end();
	}

}
