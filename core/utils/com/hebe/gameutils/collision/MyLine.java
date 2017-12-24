package com.hebe.gameutils.collision;

public class MyLine {

	public float x1, y1, x2, y2;

	public MyLine() {
	}

	public MyLine(float x1, float y1, float x2, float y2) {
		if(x1<=x2){
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
		}else{
			this.x1 = x2;
			this.x2 = x1;
			this.y1 = y2;
			this.y2 = y1;
		}
	}
	
	public void set(float x1, float y1, float x2, float y2) {
		this.x1 = Math.min(x1, x2);
		this.x2 = Math.max(x1, x2);
		this.y1 = Math.min(y1, y2);
		this.y2 = Math.max(y1, y2);
	}

	public MyVector collides(MyLine line) {
		MyVector p = new MyVector(this.x1, this.y1);
		MyVector p2 = new MyVector(this.x2, this.y2);
		MyVector q = new MyVector(line.x1, line.y1);
		MyVector q2 = new MyVector(line.x2, line.y2);
		
		boolean considerCollinearOverlapAsIntersect = false;
		MyVector intersection = new MyVector();

		MyVector r = p2.sub(p);
		MyVector s = q2.sub(q);
		float rxs = r.cross(s);
		float qpxr = q.sub(p).cross(r);

		// If r x s = 0 and (q - p) x r = 0, then the two lines are collinear.
		if (rxs == 0 && qpxr == 0) {
			// 1. If either 0 <= (q - p) * r <= r * r or 0 <= (p - q) * s <= * s
			// then the two lines are overlapping,
			if (considerCollinearOverlapAsIntersect) {
				if ((0 <= q.sub(p).mult(r) && q.sub(p).mult(r) <= r.mult(r))
						|| (0 <= p.sub(q).mult(s) && p.sub(q).mult(s) <= s.mult(s))) {
					return intersection;
				}
			}

			// 2. If neither 0 <= (q - p) * r = r * r nor 0 <= (p - q) * s <= s
			// * s
			// then the two lines are collinear but disjoint.
			// No need to implement this expression, as it follows from the
			// expression above.
			return null;
		}

		// 3. If r x s = 0 and (q - p) x r != 0, then the two lines are parallel
		// and non-intersecting.
		if (rxs == 0 && qpxr != 0) {
			return null;
		}

		// t = (q - p) x s / (r x s)
		float t = q.sub(p).cross(s) / rxs;

		// u = (q - p) x r / (r x s)

		float u = q.sub(p).cross(r) / rxs;

		// 4. If r x s != 0 and 0 <= t <= 1 and 0 <= u <= 1
		// the two line segments meet at the point p + t r = q + u s.
		if (rxs != 0 && (0 <= t && t <= 1) && (0 <= u && u <= 1)) {
			// We can calculate the intersection point using either t or u.
			intersection = p.add(r.scl(t));

			// An intersection was found.
			return intersection;
		}

		// 5. Otherwise, the two line segments are not parallel but do not
		// intersect.
		return null;
	}
	
	public boolean isHorizontal(){
		return this.y1 == this.y2;
	}
	
	public boolean isVertical(){
		return this.x1 == this.x2;
	}
	
	public MyVector toVector() {
		return new MyVector(this.x2-this.x1, this.y2-this.y1);
	}
	
	@Override
	public String toString() {
		return "Line: " + this.x1 + "/" + this.y1 + " " + this.x2 + "/" + this.y2;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			MyLine line = (MyLine) obj;
			return (line.x1 == this.x1 && line.x2 == this.x2 && line.y1 == this.y1 && line.y2 == this.y2)
					|| (line.x2 == this.x1 && line.x1 == this.x2 && line.y2 == this.y1 && line.y1 == this.y2);
		} catch (ClassCastException cce) {
			return false;
		}
	}

}
