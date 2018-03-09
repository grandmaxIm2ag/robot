package controler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import exception.EmptyArenaException;
import utils.Checker;
import utils.Palet;
import utils.Instruction;
import utils.Point;
import utils.PointCalculator;


public class Planner {
	
	
	/**
	 * 
	 */
	private static Mapper mapper;
	
	/**
	 * 
	 * @param m
	 */
	public static void init(Mapper m) {
		mapper = m;
	}

	/**
	 * 
	 * @param l
	 * @return
	 */
	public static List<Instruction> accept(List<Instruction> l){
		List<Instruction> res = new ArrayList<Instruction>();
		Iterator<Instruction >it = res.iterator();
		while(it.hasNext()){
			res.add(it.next());
		}
		return res;
	}
	
	/**
	 * Renvoie un plan
	 * 
	 * @return
	 * @throws EmptyArenaException 
	 */
	public static List<Instruction> getPlan(List<Palet> palets, Point point, boolean isSOuth) throws EmptyArenaException{
		if(palets.size() == 0){
			throw new EmptyArenaException();
		}
		PriorityQueue<Palet> pq = new PriorityQueue<Palet>(palets.size(), new Checker(point) );
		for(Palet p : palets){
			pq.offer(p);
		}
		List<Instruction> plan = new ArrayList<Instruction>();
		Point init = point;
		while(! pq.isEmpty()){
			Palet p = pq.remove();
			plan.add(new utils.Move(init, (Point)p.getP()));
			plan.add(new utils.Pick(p, (Point)p.getP()));
			plan.add(new utils.Deliver(p));
			init = PointCalculator.getWhiteLinePoint(true, ((Point)p.getP()).getX());
		}
		return plan;
	}
}
