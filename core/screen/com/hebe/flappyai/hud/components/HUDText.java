package com.hebe.flappyai.hud.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HUDText extends HUDComponent{
	
	private String text;
	
	public HUDText(int x, int y, String text) {
		super(x, y);
		this.text = text;
	}
	
	@Override
	public void draw(SpriteBatch batch, ShapeRenderer shape, BitmapFont font) {
		batch.begin();
		font.draw(batch, this.text, this.x, this.y);
		batch.end();
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
}
