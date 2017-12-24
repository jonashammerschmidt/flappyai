package com.hebe.gameutils.collision;

public class MyRectangle {

	public float x, y, width, height;
	private MyLine[] edges;

	public MyRectangle() {
		this.edges = new MyLine[4];
		this.edges[0] = new MyLine();
		this.edges[1] = new MyLine();
		this.edges[2] = new MyLine();
		this.edges[3] = new MyLine();
	}

	public MyRectangle(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.edges = new MyLine[4];
		this.edges[0] = new MyLine();
		this.edges[1] = new MyLine();
		this.edges[2] = new MyLine();
		this.edges[3] = new MyLine();
		calcEdges();
	}

	private void calcEdges() {
		this.edges[0].set(this.x, this.y, this.x, this.y + this.height);
		this.edges[1].set(this.x + this.width, this.y, this.x + this.width, this.y + this.height);
		this.edges[2].set(this.x, this.y, this.x + this.width, this.y);
		this.edges[3].set(this.x, this.y + this.height, this.x + this.width, this.y + this.height);
	}

	public boolean collides(MyLine line) {
		for (MyLine edge : this.edges) {
			MyVector colVector = line.collides(edge);
			if (colVector != null) {
				return true;
			}
		}
		return false;
	}

	public void set(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		calcEdges();
	}

	public float getCenterX() {
		return this.x + this.width / 2;
	}

	public float getCenterY() {
		return this.y + this.height / 2;
	}

	public MyLine[] getEdges() {
		return this.edges;
	}

	public boolean contains(int x, int y) {
		return this.x < x && this.y < y && x < this.x + this.width && y < this.y + this.height;
	}

	public boolean collision(MyRectangle col) {
		return x < col.x + col.width && x + width > col.x && y < col.y + col.height && height + y > col.y;
	}

}
