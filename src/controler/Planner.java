package controler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import utils.Palet;
import utils.Instruction;


public class Planner {
	/**
	 * 
	 */
	Mapper m;
	
	/**
	 * 
	 * @param m
	 */
	public Planner(Mapper m) {
		super();
		this.m = m;
	}

	/**
	 * @return the m
	 */
	public Mapper getM() {
		return m;
	}

	/**
	 * @param m the m to set
	 */
	public void setM(Mapper m) {
		this.m = m;
	}

	public List<Instruction> plan(PriorityQueue<Palet> pq){
		throw new java.lang.UnsupportedOperationException("Not supported yet");
	}
	
	public List<Instruction> accept(List<Instruction> l){
		List<Instruction> res = new ArrayList<Instruction>();
		Iterator<Instruction >it = res.iterator();
		while(it.hasNext()){
			res.add(it.next());
		}
		return res;
	}
}
