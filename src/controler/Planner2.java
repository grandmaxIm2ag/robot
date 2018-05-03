package controler;

import java.util.ArrayList;
import java.util.List;

import utils.Deliver;
import utils.Instruction;
import utils.Move;
import utils.Palet;
import utils.Pick;
import utils.Point;
import exception.EmptyArenaException;

public class Planner2 extends Planner {

	public static List<Instruction> getPlan(List<Palet> palets, Point point,
			boolean isSouth)throws EmptyArenaException{
		
		if(palets.isEmpty())
			throw new exception.EmptyArenaException();
		List<Instruction> l = new ArrayList<Instruction>();
		
		Point closest = new Point(0,0);
		double dist = Double.MAX_VALUE;
		for(Palet pal : palets){
			if(point.distance((Point)pal.getP())<=dist){
				dist = point.distance((Point)pal.getP());
				closest.setX(((Point)pal.getP()).getX());
				closest.setY(((Point)pal.getP()).getY());
			}	
		}
		
		l.add(new Move(point, closest));
		l.add(new Pick(new Palet(closest, true), closest));
		l.add(new Move(closest, utils.PointCalculator.getWhiteLinePoint(isSouth,
				closest.getX())));
		l.add(new Deliver(new Palet(closest, true)));
		return l;
	}
}
