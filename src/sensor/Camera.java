package sensor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import exception.MovingPaletException;
import utils.Palet;
import utils.Point;
/**
 * Classe permettant la communication avec le serveur de la caméra
 *
 */
public class Camera {
	/**
	 * La socker pour la communication
	 */
	private static DatagramSocket dsocket;

	private static List<Point> theoric_points;
	/**
	 * initialise la caméra
	 * @throws SocketException problème de comunication
	 */
	public static void init_camera() throws SocketException{
		dsocket = new DatagramSocket(utils.R2D2Constants.PORT_CAMERA);
		theoric_points = new ArrayList<Point>();
		theoric_points.add(new Point(50,15));
		theoric_points.add(new Point(100,15));
		theoric_points.add(new Point(150,15));
		theoric_points.add(new Point(50,90));
		theoric_points.add(new Point(100,90));
		theoric_points.add(new Point(150,90));
		theoric_points.add(new Point(50,150));
		theoric_points.add(new Point(100,150));
		theoric_points.add(new Point(150,150));
		theoric_points.add(new Point(50,210));
		theoric_points.add(new Point(100,210));
		theoric_points.add(new Point(150,210));
		theoric_points.add(new Point(50,285));
		theoric_points.add(new Point(100,285));
		theoric_points.add(new Point(150,285));
	}
	/**
	 * Renvoie la liste des palets sur la table
	 * 
	 * @return les palets
	 * @throws IOException problème de communication
	 */
	public static List<Palet> getPalet() throws IOException {
		
		byte[]buffer=new byte[2048];
		DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
		dsocket.receive(packet);
		String msg=new String(buffer,0,packet.getLength());
		String []trames=msg.split("\n");
		
		List<Point> points = new ArrayList<Point>();
		for(String s:trames) {
			String []pl=s.split(";");
			points.add(correction(new Point(Integer.parseInt(pl[1]), 
					Integer.parseInt(pl[2]))));
		}
		List<Palet> palets = new ArrayList<Palet>();
		for(Point p : points){
			palets.add(new Palet(p, p.getY() > utils.R2D2Constants.Y_SOUTH &&
					p.getY() < utils.R2D2Constants.Y_NORTH));
		}
		return palets;
	}
	
	public static Point update(Point p_old) throws IOException, MovingPaletException{
		List<Palet> pls = getPalet();
		for(Palet p:pls)
			if(p_old.distance((Point) p.getP())<5)
				return (Point) p.getP();
		
		throw new MovingPaletException();
	}

	/**
	 * Corrige la distorsion de Barrel en un point
	 * @param p le point à corriger
	 * @return le point corrigé
	 */
	public static Point correction(Point p){
		float halfwidth = utils.R2D2Constants.WIDTH_ARENA / 2f;
		float halflength = utils.R2D2Constants.LENGTH_ARENA / 2f;
		
		float correction_radius = (float)Math.sqrt(Math.pow(utils.R2D2Constants.WIDTH_ARENA, 2)
				+Math.pow(utils.R2D2Constants.LENGTH_ARENA, 2))
				/utils.R2D2Constants.STRENGTH_BARREL_CORRECTION;
		
		float newX = p.getX() - halfwidth;
		float newY = p.getY() - halflength;
		
		float distance = (float)Math.sqrt(Math.pow(newX, 2)+Math.pow(newY, 2));
		float r = distance / correction_radius;
		
		float theta;
		if(r == 0)
			theta = 1;
		else
			theta = (float)Math.atan(r)/r;
		
		float sourceX = halfwidth + theta * newX;
		float sourceY = halflength+ theta * newY;
		
		Point res = new Point(sourceX, sourceY);
		
		for(Point tmp: theoric_points) {
			if(tmp.distance(res) < 20) {
				res = new Point(tmp.getX(), tmp.getY());
				break;
			}			
		}
		
		return res;
	}
}
