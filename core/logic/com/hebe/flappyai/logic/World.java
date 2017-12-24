package com.hebe.flappyai.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hebe.flappyai.hud.HUD;
import com.hebe.flappyai.hud.components.HUDText;
import com.hebe.flappyai.logic.entities.Bird;
import com.hebe.flappyai.logic.nn.Genom;
import com.hebe.flappyai.logic.nn.NNComponent;
import com.hebe.flappyai.logic.nn.NeuralNetwork;
import com.hebe.gameutils.collision.MyVector;

public class World {

	private float camX, camY;

	private List<Genom> genoms;

	private HUDText activation;
	private HUDText fitness;
	private HUDText generation;
	private NNComponent nnComponent;

	private int generationCount = 1;
	
	private List<MyVector> pipes;
	
	private Texture background;
	
	private Texture pipe;

	private Bird bird;
	
	public World(HUD hud) {
		this.pipes = new ArrayList<MyVector>();
		this.genoms = new ArrayList<Genom>();
		
		this.activation = new HUDText(0, 1080, "Activation: ");
		this.fitness = new HUDText(0, 1060, "Fitness: ");
		this.generation = new HUDText(0, 1040, "Generation: ");
		
		this.nnComponent = new NNComponent(0, 0);

		hud.add(this.activation);
		hud.add(this.fitness);
		hud.add(this.generation);
		//hud.add(this.nnComponent);

		this.camX = 0;
		this.camY = 256;

		this.background = new Texture("background-day.png");
		this.pipe = new Texture("pipe-green.png");
		
		Random rand = new Random();
		
		for(int i = 0; i < 16; i++) {
			this.pipes.add(new MyVector(250f + i * 180f, rand.nextFloat() * 312f + 100f));
		}
		
		bird = new Bird(0, 256);
		
	}
	
	public void update(float delta) {
		for (Genom genom : this.genoms) {
			genom.update(delta);
		}
		
		bird.update(delta);

		this.camX += 100 * delta;
		
		/*Genom maxGenom = null;
		boolean allDead = true;
		for (Genom genom : this.genoms) {
			if (maxGenom == null || genom.getFitness() > maxGenom.getFitness()) {
				maxGenom = genom;
			}
		}
		if (allDead) {
			this.generationCount++;
			nextGen(maxGenom);
		}

		if (maxGenom != null) {
			//setCam(maxGenom.getCar().getX(), maxGenom.getCar().getY());

			//this.activation.setText("Turn: " + maxGenom.getCar().getTurn());
			this.fitness.setText("Fitness: " + maxGenom.getFitness());
			if(this.generationCount == maxGenom.getInitalGeneration()) {
				this.generation.setText("Generation: " + this.generationCount);
			}else {
				this.generation.setText("Generation: " + this.generationCount + " from " + maxGenom.getInitalGeneration());
			}
			
			this.nnComponent.setNn(maxGenom.getNeuralNetwork());
			
			if(Gdx.input.isKeyJustPressed(Keys.P)) {
				exportNN(maxGenom);
			}
		}

		if(Gdx.input.isKeyJustPressed(Keys.I)) {
			importNN();
		}*/
	}

	private void nextGen(Genom genom) {
		this.genoms.clear();
		Genom bestNeuralNetwork = new Genom(genom.getNeuralNetwork());
		bestNeuralNetwork.setInitalGeneration(genom.getInitalGeneration());
		this.genoms.add(bestNeuralNetwork);
		for (int i = 0; i < 9; i++) {
			NeuralNetwork bestNetwork = genom.getNeuralNetwork().deepCopy();
			bestNetwork.mutate(1 - genom.getFitness());
			Genom newGenom = new Genom(bestNetwork);
			newGenom.setInitalGeneration(this.generationCount);
			this.genoms.add(newGenom);
		}
		for (int i = 9; i < 20; i++) {
			Genom newGenom = new Genom();
			newGenom.setInitalGeneration(this.generationCount);
			this.genoms.add(newGenom);
		}
	}

	private void calcFitness() {
		
	}

	public void render(SpriteBatch batch, ShapeRenderer shape) {
		for (Genom genom : this.genoms) {
			//genom.getCar().render(shape);
		}
		batch.begin();
		batch.draw(background, camX - (camX % 288) - 144, 0);
		batch.draw(background, camX - (camX % 288) + 144, 0);
		for(MyVector pipePos : pipes) {
			batch.draw(this.pipe, pipePos.x, pipePos.y - 50 - this.pipe.getHeight());

			batch.draw(this.pipe, pipePos.x, pipePos.y + 50f, this.pipe.getWidth(), this.pipe.getHeight(), 0, 0, this.pipe.getWidth(), this.pipe.getHeight(), false, true);
		}
		
		batch.end();
		

		bird.render(batch);
	}
	
	private void importNN() {
		try {
			FileHandle file = Gdx.files.local("nn.dat");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.file()));
			NeuralNetwork imported = (NeuralNetwork) ois.readObject();
			ois.close();
			
			this.generationCount = 1;
			
			Genom genom = new Genom(imported);
			genom.setInitalGeneration(1);

			this.genoms.clear();
			this.genoms.add(genom);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void exportNN(Genom genom) {
		try {
			FileHandle file = Gdx.files.local("nn.dat");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.file()));
			oos.writeObject(genom.getNeuralNetwork());
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setCam(float x, float y) {
		this.camX = x;
		this.camY = y;
	}

	public float getCamX() {
		return this.camX;
	}

	public float getCamY() {
		return this.camY;
	}
}
