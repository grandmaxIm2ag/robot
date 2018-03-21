package main;

import fr.uga.pddl4j.encoding.CodedProblem;
import java.util.List;
import java.util.Properties;
import fr.uga.pddl4j.planners.hsp.HSP;

public class TestHSP {
	final static int traceLevel = 1;
	public static void main(String[] args) {
		final String domain = "ressource/pddl/domain.pddl";
        final String problem = "ressource/pddl/table.pddl";

        String[] arg = {"-o",domain,"-f",problem,"-i","0"};
        // Creates the planner
        final Properties arguments = HSP.parseArguments(arg);
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
