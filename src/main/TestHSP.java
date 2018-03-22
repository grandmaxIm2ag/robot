package main;

import fr.uga.pddl4j.encoding.CodedProblem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import controler.Mapper;
import fr.uga.pddl4j.planners.hsp.HSP;
import utils.Node;
import utils.Point;

public class TestHSP {
	private static DatagramSocket dsocket;

	public static void main(String[] args) throws IOException {
		final String domain = "ressource/pddl/domain.pddl";
		final String problem = "ressource/pddl/table.pddl";

		/*dsocket = new DatagramSocket(8888);
		byte[]buffer=new byte[2048];
		DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
		dsocket.receive(packet);
		String msg=new String(buffer,0,packet.getLength());
		String msg = "0;198;24\n" + 
				"1;202;85\n" + 
				"2;206;145\n" + 
				"3;208;213\n" + 
				"4;208;278";
		String []trames=msg.split("\n");

		boolean south = false;
		List<Node> pallets = new ArrayList<>();
		Mapper m = new Mapper(true, south);
		Point pt;
		for(String s:trames) {
			String []pl=s.split(";");
			pt = new Point(Integer.parseInt(pl[1]), Integer.parseInt(pl[2]));
			System.out.println(pt);
			pallets.add((Node) m.pointToNode(pt));
		}

		for(Node n : pallets)
			System.out.println(n);*/

		

		// Creates the planner
		final Properties arguments = HSP.getDefaultArguments();
		arguments.put(HSP.Argument.TRACE_LEVEL, 0);
		arguments.put(HSP.Argument.DOMAIN, domain);
		
		
		
		arguments.put(HSP.Argument.PROBLEM, problem);
		
		final HSP planner = new HSP(arguments);
		final CodedProblem codproblem = planner.parseAndEncode();
		List<String> plan = null;
		if (codproblem.isSolvable()) {
			plan = planner.aStarSearch(codproblem);
		}
		for(String id:plan)
			System.out.println(id);
	}

}
