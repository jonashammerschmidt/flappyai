package com.hebe.gameutils.collision;

public class MyCircle {

	public float x, y, r;	
	
	public MyCircle(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public boolean doesCollide(MyLine line) {		
		MyVector vO = line.toVector().orth().scl(r);
		MyLine l1 = new MyLine(x, y, x+vO.x, y+vO.y);
		MyLine l2 = new MyLine(x, y, x-vO.x, y-vO.y);
		return l1.collides(line) != null || l2.collides(line) != null || new MyVector(x-line.x1, y-line.y1).len() < r || new MyVector(x-line.x2, y-line.y2).len() < r;		
	}
	
}
