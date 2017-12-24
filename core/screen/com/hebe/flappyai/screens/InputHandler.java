package com.hebe.flappyai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InputHandler {

	private OrthographicCamera cam;
	private Viewport viewport;

	public InputHandler(OrthographicCamera cam, Viewport viewport) {
		this.cam = cam;
		this.viewport = viewport;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public OrthographicCamera getCamera() {
		return cam;
	}

	public int getUnprojectedX() {
		return (int) viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x;
	}

	public int getUnprojectedX(int i) {
		return (int) viewport.unproject(new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0)).x;
	}

	public int getUnprojectedY() {
		return (int) viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y;
	}

	public int getUnprojectedY(int i) {
		return (int) viewport.unproject(new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0)).y;
	}

}
