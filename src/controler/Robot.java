package controler;

import utils.Point;

public class Robot {
	/**
	 * 
	 */
	Point p;
	/**
	 * 
	 */
	boolean south;
	/**
	 * @param p
	 * @param south
	 */
	public Robot(Point p, boolean south) {
		super();
		this.p = p;
		this.south = south;
	}
	/**
	 * @return the p
	 */
	public Point getP() {
		return p;
	}
	/**
	 * @param p the p to set
	 */
	public void setP(Point p) {
		this.p = p;
	}
	/**
	 * @return the south
	 */
	public boolean isSouth() {
		return south;
	}
	/**
	 * @param south the south to set
	 */
	public void setSouth(boolean south) {
		this.south = south;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		result = prime * result + (south ? 1231 : 1237);
		return result;
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
		if (!(obj instanceof Robot)) {
			return false;
		}
		Robot other = (Robot) obj;
		if (p == null) {
			if (other.p != null) {
				return false;
			}
		} else if (!p.equals(other.p)) {
			return false;
		}
		if (south != other.south) {
			return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Robot [p=" + p + ", south=" + south + "]";
	}
	
	
}
