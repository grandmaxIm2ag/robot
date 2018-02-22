package utils;

/**
 * Cette classe représente un noeud dans un graphe, identifier par une abcisse 
 * i et une ordonné j
 *
 */
public class Node extends Coord{
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
}
