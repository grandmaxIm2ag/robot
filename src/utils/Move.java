package utils;

public class Move extends Instruction {
	/**
	 * 
	 */
	private Coord previous;
	/**
	 * 
	 */
	private Coord next;
	/**
	 * 
	 * @param previous
	 * @param next
	 */
	public Move(Coord previous, Coord next) {
		super();
		this.previous = previous;
		this.next = next;
	}
	
	/**
	 * @return the previous
	 */
	public Coord getPrevious() {
		return previous;
	}
	/**
	 * @param previous the previous to set
	 */
	public void setPrevious(Coord previous) {
		this.previous = previous;
	}
	/**
	 * @return the next
	 */
	public Coord getNext() {
		return next;
	}
	
	/**
	 * @param next the next to set
	 */
	public void setNext(Coord next) {
		this.next = next;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result
				+ ((previous == null) ? 0 : previous.hashCode());
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
		if (!(obj instanceof Move)) {
			return false;
		}
		Move other = (Move) obj;
		if (next == null) {
			if (other.next != null) {
				return false;
			}
		} else if (!next.equals(other.next)) {
			return false;
		}
		if (previous == null) {
			if (other.previous != null) {
				return false;
			}
		} else if (!previous.equals(other.previous)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Move [previous=" + previous + ", next=" + next + "]";
	}
	
	
}
