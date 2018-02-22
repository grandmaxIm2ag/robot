package controler;

import exception.InstructionException;
import utils.*;
/**
 * 
 * @author maxence
 *
 */
public class Mapper implements Visitor{
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
		throw new java.lang.UnsupportedOperationException("Not supported yet");
	}
}
