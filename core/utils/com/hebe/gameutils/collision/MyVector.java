package com.hebe.gameutils.collision;

public class MyVector {

	public float x = 0;
	public float y = 0;

	public MyVector() {}

	public MyVector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public MyVector(MyVector fsm) {
		this.x = fsm.x;
		this.y = fsm.y;
	}

	public MyVector add(MyVector vec) {
		return new MyVector(this.x + vec.x, this.y + vec.y);
	}

	public MyVector sub(MyVector vec) {
		return new MyVector(this.x - vec.x, this.y - vec.y);
	}

	public MyVector scl(float scala) {
		return new MyVector(this.x * scala, this.y * scala);
	}

	public float mult(MyVector w) {
		return this.x * w.x + this.y * w.y;
	}

	public MyVector unit() {
		float length = len();
		if(length==0){
			return new MyVector(0, 0);
		}else{
			return new MyVector(this.x / len(), this.y / len());
		}
	}

	public float dst(MyVector vec) {
		return vec.sub(this).len();
	}

	public MyVector orth() {
		double angle = getAngle() + Math.PI / 2;
		return new MyVector((float) Math.cos(angle), (float) Math.sin(angle)).unit();
	}

	public float len() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public float cross(MyVector v) {
		return this.x * v.y - this.y * v.x;
	}

	@Override
	public boolean equals(Object obj) {
		MyVector v = (MyVector) obj;
		return this.x - v.x == 0 && this.y - v.y == 0;
	}

	public double getAngle() {
		return Math.atan2(this.y, this.x);
	}

	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}

	public String toIString() {
		return "[" + (int) this.x + ", " + (int) this.y + "]";
	}

}
