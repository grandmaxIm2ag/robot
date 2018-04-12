package main;

import fr.uga.pddl4j.encoding.CodedProblem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import controler.Mapper;
import controler.Planner;
import exception.EmptyArenaException;
import fr.uga.pddl4j.planners.hsp.HSP;
import utils.Node;
import utils.Palet;
import utils.Point;

public class TestHSP {
	
	
	public static void main(String[] args) throws IOException, EmptyArenaException {
		
		Mapper m = new Mapper(true, false);
		Planner.init(m);
		List<Palet> palets = new ArrayList<Palet>();
		palets.add(new Palet(new Point(50,210), true));
		palets.add(new Palet(new Point(100,210), true));
		palets.add(new Palet(new Point(150,210), true));
		palets.add(new Palet(new Point(50,150), true));
		palets.add(new Palet(new Point(100,150), true));
		palets.add(new Palet(new Point(150,150), true));
		palets.add(new Palet(new Point(50,90), true));
		palets.add(new Palet(new Point(100,90), true));
		palets.add(new Palet(new Point(150,90), true));
		System.out.println(Planner.getPlan(palets, new Point(200,90), false));
		/*System.out.println(m.pointToNode(new Point(50,90)));
		System.out.println(m.pointToNode(new Point(100,90)));
		System.out.println(m.pointToNode(new Point(150,90)));
		System.out.println(m.pointToNode(new Point(100,150)));
		System.out.println(m.pointToNode(new Point(50,150)));
		System.out.println(m.pointToNode(new Point(150,150)));
		System.out.println(m.pointToNode(new Point(100,210)));
		System.out.println(m.pointToNode(new Point(50,210)));
		System.out.println(m.pointToNode(new Point(150,210)));*/
	}

}
