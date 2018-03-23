package utils;

import java.lang.Math;

/**
 * Cette classe représente un noeud dans un graphe, identifier par une abscisse 
 * i et une ordonné j
 *
 */
public class Node extends Coord implements Comparable {
	/**
	 * L'abcisse du noeud
	 */
	private final int i;
	/**
	 * L'ordonné du noeud
	 */
	private final int j;
	/**
	 * Contrusteur de la classe Node
	 * @param i l'absice
	 * @param j l'ordonné
	 */
	public Node(int i, int j) {
		super();
		this.j = j;
		this.i = i;
	}
	/**
	 * Getteur de l'attribu i
	 * @return la valeur de i
	 */
	public int getI() {
		return this.i;
	}
	/**
	 * Getteur de l'attribu j
	 * @return la valeur de j
	 */
	public int getJ() {
		return this.j;
	}
	
	
	/**
	 * 
	 * 
	 * return
	 */
	@Override
	public boolean equals(Object o) {
		if (o==null || !(o instanceof Node) ) return false;
		Node n = (Node) o;
		return (n.getI() == this.i && n.getJ() == this.j);		
	}
	
	@Override
	public int compareTo(Object o) {
		Node n = (Node) o;
		double thisDist = Math.sqrt(i*i + j*j);
		double oDist = Math.sqrt(n.getI()*n.getI() + n.getJ()*n.getJ());
		if (oDist - thisDist > 0)
			return 1;
		else if (oDist == thisDist)
			return 0;		
		else
			return -1;
	}
	

	public String a() {
		return "a"+i+j;
	}
	
	/**
	 * 
	 * return
	 */
	@Override
	public String toString() {
		return "Node[i = " + i + ", j = " + j + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		return result;
	}
}
