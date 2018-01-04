package com.hebe.flappyai.logic.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pipe {

	private static Texture pipe = new Texture("pipe-green.png");

	private float x;
	private float y;

	public static final int GAP = 70;
	public static final int WIDTH = 52;
	
	public Pipe(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void render(SpriteBatch batch) {
		batch.begin();
		batch.draw(pipe, this.x, this.y - GAP - pipe.getHeight());
		batch.draw(pipe, this.x, this.y + GAP, pipe.getWidth(), pipe.getHeight(), 0, 0, pipe.getWidth(), pipe.getHeight(), false, true);
		batch.end();
	}

	public boolean collision(float x, float y) {
		return (this.x < x && x < this.x + WIDTH && this.y - GAP - pipe.getHeight() < y && y < this.y - GAP) || 
				(this.x < x && x < this.x + WIDTH && this.y + GAP < y && y < this.y + GAP + pipe.getHeight());
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

}
