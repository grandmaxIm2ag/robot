package utils;

public class Pick extends Instruction {
	/**
	 * 
	 */
	private Palet palet;
	/**
	 * 
	 */
	private Coord coord;
	/**
	 * 
	 * @param palet
	 * @param coord
	 */
	public Pick(Palet palet, Coord coord) {
		super();
		this.palet = palet;
		this.coord = coord;
	}
	/**
	 * @return the palet
	 */
	public Palet getPalet() {
		return palet;
	}
	/**
	 * @param palet the palet to set
	 */
	public void setPalet(Palet palet) {
		this.palet = palet;
	}
	/**
	 * @return the coord
	 */
	public Coord getCoord() {
		return coord;
	}
	/**
	 * @param coord the coord to set
	 */
	public void setCoord(Coord coord) {
		this.coord = coord;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coord == null) ? 0 : coord.hashCode());
		result = prime * result + ((palet == null) ? 0 : palet.hashCode());
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
		if (!(obj instanceof Pick)) {
			return false;
		}
		Pick other = (Pick) obj;
		if (coord == null) {
			if (other.coord != null) {
				return false;
			}
		} else if (!coord.equals(other.coord)) {
			return false;
		}
		if (palet == null) {
			if (other.palet != null) {
				return false;
			}
		} else if (!palet.equals(other.palet)) {
			return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pick [palet=" + palet + ", coord=" + coord + "]";
	}
	
	
	

}
