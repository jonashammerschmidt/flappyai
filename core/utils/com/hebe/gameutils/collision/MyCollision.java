package com.hebe.gameutils.collision;

import java.util.LinkedList;
import java.util.List;

public class MyCollision {

	public List<MyLine> lines;

	public MyCollision() {
		this.lines = new LinkedList<MyLine>();
	}

	public boolean doesCollides(MyLine lineToCheck) {
		for (MyLine line : this.lines) {
			if (line.collides(lineToCheck) != null) {
				return true;
			}
		}
		return false;
	}

	public List<MyVector> getCollidingVectors(MyLine lineToCheck) {
		List<MyVector> vectors = new LinkedList<MyVector>();

		for (MyLine line : this.lines) {
			MyVector colVector = line.collides(lineToCheck);
			if (colVector != null) {
				vectors.add(colVector);
			}
		}

		return vectors;
	}

	public void makeEfficent() {
		for (int i = 0; i < this.lines.size(); i++) {
			for (int j = 0; j < this.lines.size(); j++) {
				if (i != j && this.lines.get(i).equals(this.lines.get(j))) {
					MyLine line1 = this.lines.get(i);
					MyLine line2 = this.lines.get(j);
					this.lines.remove(line1);
					this.lines.remove(line2);
					i--;
					j--;
					if (i < j) {
						j--;
					} else {
						i--;
					}
				}
			}
		}
/*
		MyLine line1 = null;
		MyLine line2 = null;
		for (int i = 0; i < this.lines.size(); i++) {
			for (int j = 0; j < this.lines.size(); j++) {
				if (i != j) {
					line1 = this.lines.get(i);
					line2 = this.lines.get(j);
					if (line1.toVector().unit().equals(line2.toVector().unit())
							|| line1.toVector().unit().equals(line2.toVector().scl(-1).unit())) {
						if (line1.x1 == line2.x1 && line1.y1 == line2.y1 
								|| line2.x2 == line2.x1 && line1.y2 == line2.y1
								|| line1.x1 == line2.x2 && line1.y1 == line2.y2
								|| line1.x2 == line2.x2 && line1.y2 == line2.y2) {
							
						}
					}
				}
			}
		}*/
	}

	public void add(MyRectangle myRectangle) {
		for(MyLine line : myRectangle.getEdges()){
			this.lines.add(line);
		}
	}
}
