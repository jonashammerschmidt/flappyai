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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hebe.flappyai.hud.HUD;
import com.hebe.flappyai.hud.components.HUDText;
import com.hebe.flappyai.logic.entities.Bird;
import com.hebe.flappyai.logic.entities.Pipe;
import com.hebe.flappyai.logic.nn.Genom;
import com.hebe.flappyai.logic.nn.NNComponent;
import com.hebe.flappyai.logic.nn.NeuralNetwork;

public class World {

	private Random rand;
	
	private float camX, camY;

	private List<Genom> genoms;

	private HUDText activation;
	private HUDText fitness;
	private HUDText generation;
	private NNComponent nnComponent;

	private int generationCount = 1;

	private List<Pipe> pipes;

	private Texture background;

	public World(HUD hud) {
		this.pipes = new ArrayList<Pipe>();
		this.genoms = new ArrayList<Genom>();

		this.activation = new HUDText(0, 512, "Activation: ");
		this.fitness = new HUDText(0, 492, "Score: ");
		this.generation = new HUDText(0, 472, "Generation: ");

		this.nnComponent = new NNComponent(10, -20);

		hud.add(this.activation);
		hud.add(this.fitness);
		hud.add(this.generation);
		hud.add(this.nnComponent);

		this.background = new Texture("background-day.png");

		this.rand = new Random();
		
		for (int i = 0; i < 60; i++) {
			this.genoms.add(new Genom(new Bird(0, 256)));
		}
		
		reset();
	}

	public void reset(){
		this.camX = 0;
		this.camY = 256;
		this.pipes.clear();
		for (int i = 0; i < 5; i++) {
			this.pipes.add(new Pipe(180f + i * 180, this.rand.nextFloat() * 312f + 100f));
		}
	}
	
	public void update(float delta) {
		Pipe nextPipe = null;
		if(this.pipes.get(0).getX() < this.camX - 244){
			this.pipes.remove(0);
			this.pipes.add(new Pipe(this.pipes.get(this.pipes.size() - 1).getX() + 180, this.rand.nextFloat() * 312f + 100f));
		}
		for (Pipe pipe : this.pipes) {
			if (pipe.getX() + Pipe.WIDTH > this.camX){
				if(nextPipe == null || nextPipe.getX() > pipe.getX()) {
					nextPipe = pipe;
				}
			}
		}
		
		if(nextPipe == null){
			nextPipe = new Pipe(this.pipes.get(this.pipes.size() - 1).getX() + 180f, this.rand.nextFloat() * 312f + 100f);
			this.pipes.add(nextPipe);
		}
		
		for (Genom genom : this.genoms) {
			genom.update(delta, nextPipe.getX() - this.camX, genom.getBird().getY() - (nextPipe.getY() - Pipe.GAP), (nextPipe.getY() + Pipe.GAP) - genom.getBird().getY());

			if (genom.getBird().getY() < 0 || genom.getBird().getY() > 512) {
				genom.getBird().ripIt();
			}
			for (Pipe pipe : this.pipes) {
				if (pipe.collision(genom.getBird().getX(), genom.getBird().getY())) {
					genom.getBird().ripIt();
				}
			}
		}

		this.camX += 100 * delta;

		Genom maxGenom = null;
		boolean allDead = true;
		for (Genom genom : this.genoms) {
			if (maxGenom == null || genom.getFitness() > maxGenom.getFitness()) {
				maxGenom = genom;
			}
			if(!maxGenom.getBird().isRip()){
				allDead = false;
			}
		}
		if (allDead) {
			this.generationCount++;
			nextGen(maxGenom);
			reset();
		}

		if (maxGenom != null) {
			this.fitness.setText("Score: " + ((int)((this.camX-180f)/180)+1)); 
			if(this.generationCount == maxGenom.getInitalGeneration()) {
				this.generation.setText("Generation: " + this.generationCount);
			} else {
				this.generation.setText("Generation: " + this.generationCount + " from " + maxGenom.getInitalGeneration());
			}
			
			double[] inputs = {nextPipe.getX() - this.camX, maxGenom.getBird().getY() - (nextPipe.getY() - Pipe.GAP), (nextPipe.getY() + Pipe.GAP) - maxGenom.getBird().getY()}; 
			this.nnComponent.setNn(maxGenom.getNeuralNetwork(), inputs);
	
			if (Gdx.input.isKeyJustPressed(Keys.P)) {
				exportNN(maxGenom);
			}
		}

		if (Gdx.input.isKeyJustPressed(Keys.I))

		{
			importNN();
			reset();
		}
	}

	private void nextGen(Genom genom) {
		this.genoms.clear();
		Genom bestNeuralNetwork = new Genom(new Bird(0, 256), genom.getNeuralNetwork());
		bestNeuralNetwork.setInitalGeneration(genom.getInitalGeneration());
		this.genoms.add(bestNeuralNetwork);
		for (int i = 0; i < 9; i++) {
			NeuralNetwork bestNetwork = genom.getNeuralNetwork().deepCopy();
			bestNetwork.mutate(1 - genom.getFitness());
			Genom newGenom = new Genom(new Bird(0, 256), bestNetwork);
			newGenom.setInitalGeneration(this.generationCount);
			this.genoms.add(newGenom);
		}
		for (int i = 9; i < 20; i++) {
			Genom newGenom = new Genom(new Bird(0, 256));
			newGenom.setInitalGeneration(this.generationCount);
			this.genoms.add(newGenom);
		}
	}

	public void render(SpriteBatch batch, ShapeRenderer shape, BitmapFont font) {
		batch.begin();
		batch.draw(this.background, this.camX - (this.camX % 288) - 144, 0);
		batch.draw(this.background, this.camX - (this.camX % 288) + 144, 0);
		batch.end();

		for (Pipe pipe : this.pipes) {
			pipe.render(batch);
		}

		for (Genom genom : this.genoms) {
			genom.getBird().render(batch);
		}
	}

	private void importNN() {
		try {
			FileHandle file = Gdx.files.local("nn.dat");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.file()));
			NeuralNetwork imported = (NeuralNetwork) ois.readObject();
			ois.close();

			this.generationCount = 1;
			this.camX = 0;

			Genom genom = new Genom(new Bird(0, 256), imported);
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
