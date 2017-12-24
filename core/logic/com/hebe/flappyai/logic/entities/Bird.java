package com.hebe.flappyai.logic.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bird {

	private float x;
	private float y;

	private float velY;

	private Texture bird;

	public Bird(float x, float y) {
		this.x = x;
		this.y = y;
		this.bird = new Texture("yellowbird-midflap.png");
	}

	public void update(float delta) {
		if(Gdx.input.isKeyJustPressed(Keys.W)) {
			velY = 300;
		}
		velY -= 700 * delta;
		x += delta * 100f;
		y += velY * delta;
	}

	public void render(SpriteBatch batch) {
		batch.begin();
		batch.draw(bird, this.x - this.bird.getWidth() / 2, this.y - this.bird.getHeight() / 2);
		batch.end();
	}

}
