package com.hebe.flappyai.logic.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bird {

	private float x;
	private float y;

	private float velY;

	private static Texture bird = new Texture("yellowbird-midflap.png");
	
	private boolean rip;

	public Bird(float x, float y) {
		this.x = x;
		this.y = y;
		this.rip = false;
	}

	public void update(float delta) {
		if(!this.rip){
			this.velY -= 700 * delta;
			this.x += delta * 100f;
			this.y += this.velY * delta;
		}
	}

	public void jump(){
		this.velY = 300;
	}
	
	public void render(SpriteBatch batch) {
		batch.begin();
	
		float rotation = 360*this.velY/4000f;
		if(rotation > 45) rotation = 45;
		if(rotation < -45) rotation = -45;
		
		batch.draw(bird, this.x - bird.getWidth() / 2f, this.y - bird.getHeight() / 2f, bird.getWidth() / 2f, bird.getHeight() / 2f, bird.getWidth(), bird.getHeight(), 1f, 1f, rotation, 0, 0, bird.getWidth(), bird.getHeight(), false, false);
		batch.end();
	}
	
	public void ripIt() {
		this.rip = true;
	}
	
	public boolean isRip() {
		return this.rip;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}

}
