package controler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import exception.EmptyArenaException;
import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.planners.hsp.HSP;
import utils.Palet;
import utils.FactoryInstruction;
import utils.Instruction;
import utils.Node;
import utils.Point;
import utils.PointCalculator;
import utils.Visitor;

public class Planner {
	private final static String dom = "(define (domain Robot)\n" + 
			"  (:requirements :strips :typing)\n" + 
			"  (:types pallet node)\n" + 
			"  (:constants dock - node)\n" + 
			"  (:predicates (at ?x - pallet ?y - node)\n" + 
			"	       (at-robby ?y - node)\n" + 
			"	       (at-dock ?y - pallet)\n" + 
			"	       (connected ?x ?y - node) \n" + 
			"          (finished)\n" + 
			"	       (gripperempty)\n" + 
			"	       (holding ?x - pallet)\n" + 
			"	       )\n" + 
			"   \n" + 
			"   (:action move\n" + 
			"       :parameters  (?from ?to - node)\n" + 
			"       :precondition (and (at-robby ?from) (connected ?from ?to))\n" + 
			"       :effect (and  (at-robby ?to)\n" + 
			"		     (not (at-robby ?from))))\n" + 
			"   \n" + 
			"   (:action pick\n" + 
			"       :parameters (?pl - pallet ?pos - node)\n" + 
			"       :precondition  (and  (at ?pl ?pos) (at-robby ?pos) (gripperempty))\n" + 
			"       :effect (and (holding ?pl)\n" + 
			"		    (not (at ?pl ?pos))\n" + 
			"		    (not (gripperempty))))\n" + 
			"\n" + 
			"   (:action deliver\n" + 
			"       :parameters  (?pl - pallet)\n" + 
			"       :precondition  (and  (holding ?pl) (at-robby dock))\n" + 
			"       :effect (and (at-dock ?pl)\n" + 
			"		    (gripperempty)\n" + 
			"           (finished)\n" + 
			"		    (not (holding ?pl)))))";
	private final static String intro = "(define (problem CoreGame)\n" + 
			"(:domain Robot)\n" + 
			"(:objects \n" + 
			"	a55 a45 a35 a25 a15 - node\n" + 
			"	a54 a44 a34 a24 a14 - node\n" + 
			"	a53 a43 a33 a23 a13 - node\n" + 
			"	a52 a42 a32 a22 a12 - node\n" + 
			"	a51 a41 a31 a21 a11 - node\n";	
	private final static String north = "	(connected dock a15)\n" + 
			"	(connected dock a25)\n" + 
			"	(connected dock a35)\n" + 
			"	(connected dock a45)\n" + 
			"	(connected dock a55)\n" + 
			"	(connected a15 dock)\n" + 
			"	(connected a25 dock)\n" + 
			"	(connected a35 dock)\n" +
			"	(connected a45 dock)\n" + 
			"	(connected a55 dock)\n";
	private final static String south = "	(connected dock a51)\n" + 
			"	(connected dock a41)\n" + 
			"	(connected dock a31)\n" + 
			"	(connected dock a21)\n" + 
			"	(connected dock a11)\n" + 
			"	(connected a51 dock)\n" + 
			"	(connected a41 dock)\n" + 
			"	(connected a31 dock)\n" +
			"	(connected a21 dock)\n" + 
			"	(connected a11 dock)\n";	
	private final static String last = 
			"	(connected a11 a12)\n" + 
			"	(connected a11 a21)\n" + 
			"	(connected a12 a11)\n" + 
			"	(connected a12 a22)\n" + 
			"	(connected a12 a13)\n" + 
			"	(connected a13 a12)\n" + 
			"	(connected a13 a23)\n" + 
			"	(connected a13 a14)\n" + 
			"	(connected a14 a13)\n" + 
			"	(connected a14 a24)\n" + 
			"	(connected a14 a15)\n" + 
			"	(connected a15 a14)\n" + 
			"	(connected a15 a25)\n" + 
			"	(connected a21 a11)\n" + 
			"	(connected a21 a31)\n" + 
			"	(connected a21 a22)\n" + 
			"	(connected a22 a21)\n" + 
			"	(connected a22 a12)\n" + 
			"	(connected a22 a32)\n" + 
			"	(connected a22 a23)\n" + 
			"	(connected a23 a22)\n" + 
			"	(connected a23 a13)\n" + 
			"	(connected a23 a33)\n" + 
			"	(connected a23 a24)\n" + 
			"	(connected a24 a23)\n" + 
			"	(connected a24 a14)\n" + 
			"	(connected a24 a34)\n" + 
			"	(connected a24 a25)\n" + 
			"	(connected a25 a24)\n" + 
			"	(connected a25 a15)\n" + 
			"	(connected a25 a35)\n" + 
			"	(connected a31 a21)\n" + 
			"	(connected a31 a41)\n" + 
			"	(connected a31 a32)\n" + 
			"	(connected a32 a31)\n" + 
			"	(connected a32 a22)\n" + 
			"	(connected a32 a42)\n" + 
			"	(connected a32 a33)\n" + 
			"	(connected a33 a32)\n" + 
			"	(connected a33 a23)\n" + 
			"	(connected a33 a43)\n" + 
			"	(connected a33 a34)\n" + 
			"	(connected a34 a33)\n" + 
			"	(connected a34 a24)\n" + 
			"	(connected a34 a44)\n" + 
			"	(connected a34 a35)\n" + 
			"	(connected a35 a34)\n" + 
			"	(connected a35 a25)\n" + 
			"	(connected a35 a45)\n" + 
			"	(connected a41 a31)\n" + 
			"	(connected a41 a51)\n" + 
			"	(connected a41 a42)\n" + 
			"	(connected a42 a41)\n" + 
			"	(connected a42 a32)\n" + 
			"	(connected a42 a52)\n" + 
			"	(connected a42 a43)\n" + 
			"	(connected a43 a42)\n" + 
			"	(connected a43 a33)\n" + 
			"	(connected a43 a53)\n" + 
			"	(connected a43 a44)\n" + 
			"	(connected a44 a43)\n" + 
			"	(connected a44 a34)\n" + 
			"	(connected a44 a54)\n" + 
			"	(connected a44 a45)\n" + 
			"	(connected a45 a44)\n" + 
			"	(connected a45 a35)\n" + 
			"	(connected a45 a55)\n" + 
			"	(connected a51 a41)\n" + 
			"	(connected a51 a52)\n" + 			
			"	(connected a52 a51)\n" + 
			"	(connected a52 a42)\n" + 
			"	(connected a52 a53)\n" + 
			"	(connected a53 a52)\n" + 
			"	(connected a53 a43)\n" + 
			"	(connected a53 a54)\n" + 
			"	(connected a54 a53)\n" + 
			"	(connected a54 a44)\n" + 
			"	(connected a54 a55)\n" + 
			"	(connected a55 a54)\n" + 
			"	(connected a55 a45)\n" + 
			")\n" + 
			"\n" + 
			"(:goal (finished)\n" + 
			"))";
	/**
	 * 
	 */
	private static Mapper mapper;
	private static String fin;
	private static File tempd;
	private static File tempt;
	private static Properties arguments;
	
	public static void init(Mapper m) {
		mapper = m;
		arguments = HSP.getDefaultArguments();
		arguments.put(HSP.Argument.TRACE_LEVEL, 0);
		if(m.getDirecttion())
			fin = south+last;
		else {
			System.out.println("Test");
			fin = north+last;
		}
			
		try {
			tempd = File.createTempFile("dom", ".tmp"); 
			BufferedWriter bw = new BufferedWriter(new FileWriter(tempd));
	 	    bw.write(dom);
	 	    bw.close();
	 	    tempd.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		arguments.put(HSP.Argument.DOMAIN, tempd.getAbsolutePath());
	}
	
	private static List<String> cleanPlan(List<String> plan){
		List<String> res = new ArrayList<>();
		for(int i=0;i<plan.size();i++) {
			if(plan.get(i).contains("move") && i+1<plan.size() && plan.get(i+1).contains("move")){
				int j;
				for(j=i+1;j<plan.size() && plan.get(j).contains("move");j++);
				j--;
				String []ps = plan.get(i).split(" ");
				String []pd = plan.get(j).split(" ");
				res.add("move "+ps[1]+" "+pd[2]);
				i = j;
			}
			else
				res.add(plan.get(i));
		}
		return res;
	}

	/**
	 * Convertie les coordonnées pour l'arène
	 * 
	 * @param l le plan
	 * @return le plan mappé au terrain
	 * 
	 * @throws Exception 
	 */
	public static List<Instruction> accept(List<Instruction> l, 
			Visitor<Instruction> v) throws Exception{
		List<Instruction> res = new ArrayList<Instruction>();
		Iterator<Instruction >it = l.iterator();
		while(it.hasNext()){
			res.add(it.next().accept(v));
		}
		return res;
	}
	
	/**
	 * 
	 * @param palets List des palets enjeux
	 * @param point Position du robot
	 *
	 * @return le plan
	 * @throws EmptyArenaException
	 */
	public static List<Instruction> getPlan(List<Palet> palets, Point point,
			boolean isSOuth)throws EmptyArenaException{
		
		Map<String, Node> map = new HashMap<String, Node>();
		
		if(palets.isEmpty()){
			throw new EmptyArenaException();
		}
		List<String> res = new ArrayList<>();
		List<Node> pallets = new ArrayList<>();
		for(Palet p:palets)
			if(p.isIn_game())
				pallets.add((Node) mapper.pointToNode((Point)p.getP()));
		Node pos = (Node) mapper.pointToNode(point);
		
		BufferedWriter bw1;
		try {
			tempt = File.createTempFile("tab", ".tmp");
			bw1 = new BufferedWriter(new FileWriter(tempt));
			bw1.write(intro);
		    bw1.write("	");
		    for(int i=0;i<pallets.size();i++){
		    	bw1.write("pl"+i+" ");
		    	map.put("pl"+i, pallets.get(i));
		    }
		    bw1.write("- Pallet)\n(:init\n"+"	(gripperempty)\n");
		    for(int i=0;i<pallets.size();i++)
		    	bw1.write("	(at pl"+i+" a"+pallets.get(i).getI()+pallets.get(i).getJ()+")\n");
		    bw1.write("	(at-robby a"+pos.getI()+pos.getJ()+")\n");
		    bw1.write(fin);
		    bw1.close();
		    
		    // Creates the planner
		    arguments.put(HSP.Argument.PROBLEM, tempt.getAbsolutePath());
			final HSP planner = new HSP(arguments);
			final CodedProblem codproblem = planner.parseAndEncode();

			if (codproblem.isSolvable()) {;
				res = cleanPlan(planner.aStarSearch(codproblem));
			}
			
			for(int i=0; i<7; i++)
				for(int j=0; j<7; j++)
					map.put("a"+i+""+j, new Node(i,j));
			FactoryInstruction.init_map(map);
			FactoryInstruction.init_south(isSOuth);
			List<Instruction> plan1 = new ArrayList<Instruction>();
			for(String str : res) {
				plan1.add(FactoryInstruction.create(str));
			}
				
			System.out.println(plan1);
			List<Instruction> final_plan = accept(plan1, mapper);
			return final_plan;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
