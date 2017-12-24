package com.hebe.gameutils.fps;

import java.util.LinkedList;
import java.util.List;

public class FPSCounter {

	private float frequency;
	private float currentTime;
	private List<Integer> fpsList; 
	private int fps;
	
	public FPSCounter(float frequency) {
		this.frequency = frequency;
		this.fps = 0;
		this.currentTime = 0f;
		this.fpsList = new LinkedList<Integer>();
	}
	
	public void add(float delta){
		currentTime+=delta;
		if(currentTime>frequency){
			currentTime=0;
			fps=0;
			for(Integer singleFps : fpsList){
				fps+=singleFps;
			}
			if(!fpsList.isEmpty()){
				fps/=fpsList.size();
				fpsList.clear();
			}
		}
		fpsList.add((int)(1f/delta));
	}
	
	public int getFPS(){
		return fps;
	}
	
	
	
}
