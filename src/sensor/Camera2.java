package sensor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import utils.Palet;
import utils.Point;

public class Camera2 {

	/**
	 * La socket pour la communication
	 */
	private static DatagramSocket dsocket;

	private static List<Point> theoricalPoints, init_point;
	private static Map<Point, Point> coeff;
	
	/**
	 * Initialise la camera a partir des coordonnees donnees
	 * @throws IOException 
	 */
	public static void init_camera() throws IOException{
		init_point = new ArrayList<Point>();
		coeff = new HashMap<Point, Point>();
		
		theoricalPoints = new ArrayList<Point>();

		theoricalPoints.add(new Point(200,270));
		theoricalPoints.add(new Point(200,210));
		theoricalPoints.add(new Point(200,150));
		theoricalPoints.add(new Point(200,90));
		theoricalPoints.add(new Point(200,30));
		
		theoricalPoints.add(new Point(100,270));
		theoricalPoints.add(new Point(100,210));
		theoricalPoints.add(new Point(100,150));
		theoricalPoints.add(new Point(100,90));
		theoricalPoints.add(new Point(100,30));

		theoricalPoints.add(new Point(0,270));
		theoricalPoints.add(new Point(0,210));
		theoricalPoints.add(new Point(0,150));
		theoricalPoints.add(new Point(0,90));
		theoricalPoints.add(new Point(0,30));
		
		theoricalPoints.add(new Point(150,270));
		theoricalPoints.add(new Point(50,270));
		theoricalPoints.add(new Point(150,30));
		theoricalPoints.add(new Point(50,30));

		init_coeff();
	}
	private static  void init_coeff() throws IOException {
		dsocket = new DatagramSocket(utils.R2D2Constants.PORT_CAMERA);
		byte[]buffer=new byte[2048];
		DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
		dsocket.receive(packet);
		String msg=new String(buffer,0,packet.getLength());
		String []trames=msg.split("\n");
		System.out.println(msg);
		for(String s:trames) {
			String []pl=s.split(";");
			Point tmp = new Point(Integer.parseInt(pl[0]), Integer.parseInt(pl[0]));
			Point init = tmp.n_closest(1, theoricalPoints).get(0);
			Point c = tmp.compute_coeff(init);
			init_point.add(tmp);
			coeff.put(init, c);
		}
		dsocket.close();
	}
	
	private static Point correction(Point p) {
		
		List<Point> lp = p.n_closest(3, init_point);
		List<Point> c = new ArrayList<Point>();
		for(Point key : lp) {
			c.add(coeff.get(key));
		}
		System.out.println(lp);
		p.applay_mult_coeff(c, lp);
		return p;
	}
	
	/**
	 * Renvoie la liste des palets sur la table a partir des vrais coordonnes
	 * 
	 * @return les palets
	 * @throws IOException problème de communication
	 */
	public static List<Palet> getPalets() throws IOException {
		dsocket = new DatagramSocket(utils.R2D2Constants.PORT_CAMERA);
		byte[]buffer=new byte[2048];
		DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
		dsocket.receive(packet);
		String msg=new String(buffer,0,packet.getLength());
		String []trames=msg.split("\n");
		
		List<Point> lp = new ArrayList<Point>();
		for(String s:trames) {
			String []pl=s.split(";");
			Point p = correction(new Point(Integer.parseInt(pl[0]),Integer.parseInt(pl[0])));
			lp.add(p);
		}
		List<Palet> palets = new ArrayList<Palet>();
		for(Point p : lp){
			palets.add(new Palet(p, p.getY() > utils.R2D2Constants.Y_SOUTH+10 &&
					p.getY() < utils.R2D2Constants.Y_NORTH-10));
		}
		
		Iterator<Palet> it = palets.iterator();
		while(it.hasNext())
			if(!((Palet)it.next()).isIn_game())
				it.remove();
		dsocket.close();
		return palets;
	}
}
