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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
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
}
