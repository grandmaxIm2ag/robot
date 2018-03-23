package controler;

import java.util.TreeMap;

import exception.InstructionException;
import utils.*;
/**
 * 
 *
 */
public class Mapper implements Visitor<Instruction>{
	/**
	 * 
	 */
	private final boolean fromPlanner;
	private final int END = 30;
	private final int NODE_WIDTH = 50;
	private final int NODE_HEIGHT = 60;
	private final int MAP_WIDTH = 200;
	private final int MAP_HEIGHT = 300;
	private final int nbX = MAP_WIDTH / NODE_WIDTH + 2;
	private final int nbY = (MAP_HEIGHT - 2*END) / NODE_HEIGHT + 2; 
	/**
	 * 
	 */
	private final boolean south;
	
	private TreeMap<Node, Point> nodesToPoints;
	private TreeMap<Node, Point> defaultPoints;
	/**
	 * 
	 * @param fromPlanner
	 * @param south
	 */
	public Mapper(boolean fromPlanner, boolean south) {
		super();
		this.fromPlanner = fromPlanner;
		this.south = south;
		this.nodesToPoints = new TreeMap<Node,Point>();
		this.defaultPoints = new TreeMap<Node,Point>();
		System.out.println(1);
		for (int i = 1; i < nbX; i++) {
			for (int j = 1; j < nbY; j++) {
				defaultPoints.put(new Node(i, j), new Point(NODE_WIDTH*(i-1), NODE_HEIGHT*(j-1) + NODE_HEIGHT/2));
			}
		}
	}
	/**
	 * 
	 * @param i
	 * @return
	 * @throws InstructionException 
	 */
	public Instruction visit (Instruction i) throws InstructionException{
		throw new exception.InstructionException("Cette instruction n'a" +
				" pas encore été implantée");
	}
	/**
	 * 
	 * @param m
	 * @return
	 */
	public Instruction visit(Move m){
		if(fromPlanner)
			return new Move(nodeToPoint((Node)m.getPrevious()),
					nodeToPoint((Node)m.getPrevious()));
		else
			return new Move(pointToNode((Point)m.getPrevious()),
					pointToNode((Point)m.getPrevious()));
		
	}
	/**
	 * 
	 * @param d
	 * @return
	 */
	public Instruction visit(Deliver d){
		if(fromPlanner){
			return new Deliver(new Palet(nodeToPoint((Node)d.getPalet().getP()),
					d.getPalet().isIn_game()));
		}else{
			return new Deliver(new Palet(pointToNode((Point)d.getPalet().getP()),
					d.getPalet().isIn_game()));
		}
	}
	/**
	 * 
	 * @param p
	 * @return
	 */
	public Instruction visit(Pick p){
		if(fromPlanner){
			return new Pick(new Palet(nodeToPoint((Node)p.getPalet().getP()),
					p.getPalet().isIn_game()), nodeToPoint((Node)p.getCoord()));
		}else{
			return new Pick(new Palet(pointToNode((Point)p.getPalet().getP()),
					p.getPalet().isIn_game()), pointToNode((Point)p.getCoord()));
		}
	}
	/**
	 * 
	 * @param n : un noeud
	 * @return : le point associé à ce noeud (par défaut si ce point n'est pas connu)
	 */
	public Point nodeToPoint(Node n) {
		
		//System.out.println(nodesToPoints);
		if (nodesToPoints.containsKey(n)) {
			return nodesToPoints.get(n);
		}
		else if (defaultPoints.containsKey(n)) {
			return defaultPoints.get(n);
		}
		else {
			return null;
		}

	}
	/**
	 * 
	 * @param p : Point
	 * @return : les coordonnées du noeud associé à ce point
	 */
	public Coord pointToNode(Point p){
		
		float xIn = p.getX();
		float yIn = p.getY();
		float xOut, yOut;
		
		xOut = (xIn - NODE_WIDTH/2)/NODE_WIDTH + 2;
		yOut = yIn / NODE_HEIGHT + 1;
		
		
		/* Inverse les noeuds en fonction des camps
		if (!south) {
			yOut = nbY - (int) yOut;
			xOut = nbX - (int) xOut;
		}*/
		Node n = new Node((int)xOut, (int)yOut);

		nodesToPoints.put(n, p);
		
		return n;
	}
}
