package com.hebe.flappyai.hud.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class HUDComponent {

	protected int x;
	protected int y;
	
	public HUDComponent(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void draw(SpriteBatch batch, ShapeRenderer shape, BitmapFont font);
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
}
