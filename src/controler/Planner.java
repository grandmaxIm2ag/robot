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
import utils.Visitor;


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
	 * @throws Exception 
	 */
	public static List<Instruction> accept(List<Instruction> l, 
			Visitor<Instruction> v) throws Exception{
		List<Instruction> res = new ArrayList<Instruction>();
		Iterator<Instruction >it = res.iterator();
		while(it.hasNext()){
			res.add(it.next().accept(v));
		}
		return res;
	}
	
	/**
	 * 
	 * @param palets List des palets enjeux
	 * @param point Position du robot
	 * @param isSouth Indique le robot doit déposer les palets "au sud"
	 * @return
	 * @throws EmptyArenaException
	 */
	public static List<Instruction> getPlan(List<Palet> palets, Point point,
			boolean isSOuth) throws EmptyArenaException{
		
		
		
		return null;
	}
}
