package utils;
/**
 * 
 * @author maxence
 *
 */
public class Palet {
	/**
	 * 
	 */
	private Coord p;
	/**
	 * 
	 */
	private boolean in_game;
	
	/**
	 * 
	 * @param p
	 * @param b
	 */
	public Palet(Coord p, boolean b){
		this.p=p;
		this.in_game=b;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Palet [p=" + p + ", in_game=" + in_game + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (in_game ? 1231 : 1237);
		result = prime * result + ((p == null) ? 0 : p.hashCode());
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
		if (!(obj instanceof Palet)) {
			return false;
		}
		Palet other = (Palet) obj;
		if (in_game != other.in_game) {
			return false;
		}
		if (p == null) {
			if (other.p != null) {
				return false;
			}
		} else if (!p.equals(other.p)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isIn_game() {
		return in_game;
	}
	/**
	 * 
	 * @param in_game
	 */
	public void setIn_game(boolean in_game) {
		this.in_game = in_game;
	}
	/**
	 * 
	 * @return
	 */
	public Coord getP() {
		return p;
	}
	/**
	 * 
	 * @param p
	 */
	public void setP(Coord p) {
		this.p = p;
	}
}
