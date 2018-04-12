package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 */
public class Point extends Coord{
	/**
	 * 
	 */
	private float x;
	/**
	 * 
	 */
	private float y;
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Point (float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getX() {
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	/**
	 * 
	 * return
	 */
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	/**
	 * return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Point)) {
			return false;
		}
		Point other = (Point) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public float distance (Point p) {
		return (float)Math.sqrt(Math.pow(p.getX()-x, 2)+Math.pow(p.getY()-y, 2));
	}
	/**
	 * 
	 * @param point
	 * @return
	 */
	public float angle (Point point) {
		Point tmp = new Point(x, point.getY());
		float hyp = this.distance(point);
		float adj = this.distance(tmp);
		double r = Math.acos((adj/hyp));
		if (x < point.x)
			if(y>point.y)
				return 180+(float)Math.toDegrees(r);
			else
				return (float)-Math.toDegrees(r);
		else
			if(y>point.y)
				return 180-(float)Math.toDegrees(r);
			else
				return (float)Math.toDegrees(r);
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void apply_coeff(Point coeff) {
		x *= coeff.getX();
		y *= coeff.getY();
	}
	
	public void applay_mult_coeff(List<Point> coeff, List<Point> p) {
		float D = 0, R=0;
		float dist[] = new float[coeff.size()];
 		for(int i=0; i<coeff.size(); i++) {
			D+=distance(p.get(i));
			dist[i] = distance(p.get(i));
 		}
 		
 		for(int i=0; i<coeff.size();i++){
 			dist[i] = 1- (dist[i]-D);
 			R+=dist[i];
 		}
		
		float P[] = new float[coeff.size()];
		
		for(int i=0; i<coeff.size(); i++)
			P[i] = (dist[i]/R);
		
		float alpha=0, beta=0;
		for(int i=0; i<coeff.size(); i++) {
			alpha += P[i]*coeff.get(i).getX();
			beta += P[i]*coeff.get(i).getY();
		}
		
		apply_coeff(new Point(alpha, beta));
	}
	
	public Point compute_coeff(Point p) {
		return new Point((float)p.getX()/x, (float)p.getY()/y);
	}
	
	public List<Point> n_closest(int n, List<Point> p) {
		if(n >= p.size())
			throw new IllegalArgumentException("n plus grand que lee tableau");
		List<Point > res = new ArrayList<Point>();
		List<Point> close = new ArrayList<Point>();
		for(int j = 0; j<n; j++) {
			float max = 0;
			int i_max = -1;
			for(int k =0; k<p.size(); k++) {
				if(!close.contains(p.get(k)))
					if(max <= distance(p.get(k))) {
						i_max = k;
						max = distance(p.get(i_max));
					}
			}
			res.add(p.get(i_max));
			close.add(p.get(i_max));
		}
		return res;
	}
}
