package utils;

/**
 * 
 *
 */
public class Point extends Coord{
	/**
	 * 
	 */
	private final float x;
	/**
	 * 
	 */
	private final float y;
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Point (float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getX() {
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	/**
	 * 
	 * return
	 */
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	/**
	 * return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Point)) {
			return false;
		}
		Point other = (Point) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public float distance (Point p) {
		return (float)Math.sqrt(Math.pow(p.getX()-x, 2)+Math.pow(p.getY()-y, 2));
	}
	/**
	 * 
	 * @param point
	 * @return
	 */
	public float angle (Point point) {
		Point tmp = new Point(x, point.getY());
		float hyp = this.distance(point);
		float adj = this.distance(tmp);
		double r = Math.acos((adj/hyp));
		if (x < point.x)
			if(y>point.y)
				return 180+(float)Math.toDegrees(r);
			else
				return (float)Math.toDegrees(r);
		else
			if(y>point.y)
				return 180-(float)Math.toDegrees(r);
			else
				return (float)-Math.toDegrees(r);
	}
}
