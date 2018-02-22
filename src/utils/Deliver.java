package utils;

public class Deliver extends Instruction {
	/**
	 * 
	 */
	Palet palet;
	/**
	 * 
	 * @param palet
	 */
	public Deliver(Palet palet) {
		super();
		this.palet = palet;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (!(obj instanceof Deliver)) {
			return false;
		}
		Deliver other = (Deliver) obj;
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
		return "Deliver [palet=" + palet + "]";
	}
	
	
}
