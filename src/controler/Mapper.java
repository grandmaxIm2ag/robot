package controler;

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
	/**
	 * 
	 */
	private final boolean south;
	
	/**
	 * 
	 * @param fromPlanner
	 * @param south
	 */
	public Mapper(boolean fromPlanner, boolean south) {
		super();
		this.fromPlanner = fromPlanner;
		this.south = south;
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
	 * @param c
	 * @return
	 */
	public Point nodeToPoint(Node n){
		throw new java.lang.UnsupportedOperationException("Not supported yet");
	}
	/**
	 * 
	 * @param c
	 * @return
	 */
	public Coord pointToNode(Point p){
		final int END = 30;
		final int NODE_WIDTH = 50;
		final int NODE_HEIGHT = 60;
		final int MAP_WIDTH = 200;
		final int MAP_HEIGHT = 300;
		
		float xIn = p.getX();
		float yIn = p.getY();
		float xOut, yOut;
		
		/*if (yIn <= END) {
			yOut = 0;
		}
		else if (yIn >= MAP_HEIGHT - END) {
			yOut = MAP_HEIGHT - END;
		}
		else {
			yOut = yIn / NODE_HEIGHT;
		}
		xOut = (xIn + NODE_WIDTH/2)/NODE_WIDTH;*/
		
		xOut = (xIn - NODE_WIDTH/2)/NODE_WIDTH + 2;
		yOut = yIn / NODE_HEIGHT + 1;
		
		int nbX = MAP_WIDTH / NODE_WIDTH + 2;
		int nbY = (MAP_HEIGHT - 2*END) / NODE_HEIGHT + 2; 
		System.out.println("nbX = " + nbX + " et nbY = " + nbY);
		
		if (!south) {
			yOut = nbY - (int) yOut;
			xOut = nbX - (int) xOut;
		}
			
		System.out.println("xOut = " + xOut + " et yOut = " + yOut);

				
		return new Node((int)xOut, (int)yOut);
	}
}
