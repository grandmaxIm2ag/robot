package utils;

import java.util.Comparator;

/**
 * 
 *
 */
public class Checker implements Comparator<Palet>{
	/**
	 * 
	 */
	Point p;
	public Checker(Point p){
		this.p = p;
	}
	/**
	 * 
	 */
	@Override
	public int compare(Palet arg0, Palet arg1) {
		if(p.distance((Point)arg0.getP()) > p.distance((Point)arg0.getP()) ){
			return 1;
		}else if(p.distance((Point)arg0.getP()) < p.distance((Point)arg0.getP()) ){
			return -1;
		}else{
			return 0;
		}
	}
}
