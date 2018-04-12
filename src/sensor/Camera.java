package sensor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import exception.MovingPaletException;
import utils.Palet;
import utils.Point;
/**
 * Classe permettant la communication avec le serveur de la camera
 *
 */
public class Camera {
	/**
	 * La socket pour la communication
	 */
	private static DatagramSocket dsocket;

	private static List<Point> theoreticalPoints, artificialPoints, correctedPoints;
	private static float distortedCenterX, distortedCenterY;
	/**
	 * Initialise la camera
	 * @throws SocketException probleme de comunication
	 */
	public static void init_camera() throws SocketException{
		theoreticalPoints = new ArrayList<Point>();
		theoreticalPoints.add(new Point(50,15));
		theoreticalPoints.add(new Point(100,15));
		theoreticalPoints.add(new Point(150,15));
		theoreticalPoints.add(new Point(50,90));
		theoreticalPoints.add(new Point(100,90));
		theoreticalPoints.add(new Point(150,90));
		theoreticalPoints.add(new Point(50,150));
		theoreticalPoints.add(new Point(100,150));
		theoreticalPoints.add(new Point(150,150));
		theoreticalPoints.add(new Point(50,210));
		theoreticalPoints.add(new Point(100,210));
		theoreticalPoints.add(new Point(150,210));
		theoreticalPoints.add(new Point(50,285));
		theoreticalPoints.add(new Point(100,285));
		theoreticalPoints.add(new Point(150,285));

		distortedCenterX = utils.R2D2Constants.WIDTH_ARENA / 2f;
		distortedCenterY = utils.R2D2Constants.LENGTH_ARENA / 2f;
	}
	/**
	 * Initialise la camera a partir des coordonnees donnees
	 */
	public static void initArtificialCamera(){
		theoreticalPoints = new ArrayList<Point>();

		theoreticalPoints.add(new Point(200,270));
		theoreticalPoints.add(new Point(200,210));
		theoreticalPoints.add(new Point(200,150));
		theoreticalPoints.add(new Point(200,90));
		theoreticalPoints.add(new Point(200,30));
		
		theoreticalPoints.add(new Point(100,270));
		theoreticalPoints.add(new Point(100,210));
		theoreticalPoints.add(new Point(100,150));
		theoreticalPoints.add(new Point(100,90));
		theoreticalPoints.add(new Point(100,30));

		theoreticalPoints.add(new Point(0,270));
		theoreticalPoints.add(new Point(0,210));
		theoreticalPoints.add(new Point(0,150));
		theoreticalPoints.add(new Point(0,90));
		theoreticalPoints.add(new Point(0,30));
		
		theoreticalPoints.add(new Point(150,270));
		theoreticalPoints.add(new Point(50,270));
		theoreticalPoints.add(new Point(150,30));
		theoreticalPoints.add(new Point(50,30));

		artificialPoints = new ArrayList<Point>();

		artificialPoints.add(new Point(208f,278f));
		artificialPoints.add(new Point(208f,213f));
		artificialPoints.add(new Point(206f,145f));
		artificialPoints.add(new Point(202f,85f));
		artificialPoints.add(new Point(198f,24f));
		
		artificialPoints.add(new Point(99f,282f));
		artificialPoints.add(new Point(99f,213f));
		artificialPoints.add(new Point(97f,147f));
		artificialPoints.add(new Point(97f,83f));
		artificialPoints.add(new Point(95f,24f));
		
		artificialPoints.add(new Point(-9f,280f));
		artificialPoints.add(new Point(-9f,213f));
		artificialPoints.add(new Point(-7f,149f));
		artificialPoints.add(new Point(-7f,85f));
		artificialPoints.add(new Point(-5f,26f));
		
		artificialPoints.add(new Point(157f,280f));
		artificialPoints.add(new Point(42f,282f));
		artificialPoints.add(new Point(149f,24f));
		artificialPoints.add(new Point(44f,24f));
		
		calculateDistortedCenter();
	}
	
	/**
	 * Calcule "le centre" de table avec des points donnees
	 * @throws SocketException probleme de comunication
	 */
	private static void calculateDistortedCenter(){
		float xTotal = 0;
		float yTotal = 0;
		float tTotal = 0;
		
		for(Point pFix : artificialPoints){
			xTotal += pFix.getX();
			yTotal += pFix.getY();
			tTotal += 1;
		}
		distortedCenterX = (utils.R2D2Constants.WIDTH_ARENA-xTotal/tTotal);
		// + utils.R2D2Constants.WIDTH_ARENA / 2f;
		distortedCenterY = (utils.R2D2Constants.LENGTH_ARENA-yTotal/tTotal);
		// + utils.R2D2Constants.LENGTH_ARENA / 2f;
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
		
		correctedPoints = new ArrayList<Point>();
		for(String s:trames) {
			String []pl=s.split(";");
			Point p = new Point(Integer.parseInt(pl[1]), 
					Integer.parseInt(pl[2]));
			correctedPoints.add(utils.R2D2Constants.ADVANCED_CORRECTION ? 
					advancedCorrection(p):simpleCorrection(p));
		}
		List<Palet> palets = new ArrayList<Palet>();
		for(Point p : correctedPoints){
			palets.add(new Palet(p, p.getY() > utils.R2D2Constants.Y_SOUTH+10 &&
					p.getY() < utils.R2D2Constants.Y_NORTH-10));
		}
		
		//On supprime les palets hors jeu
		Iterator<Palet> it = palets.iterator();
		while(it.hasNext())
			if(!((Palet)it.next()).isIn_game())
				it.remove();
		
//		//On afiche les palets
//		it = palets.iterator();
//		while(it.hasNext())
//			System.out.println(it.next());
//		System.out.println("###################################");
		
		dsocket.close();
		return palets;
	}
	/**
	 * Renvoie la liste des palets sur la table a partir des faux coordonnes
	 * 
	 * @return les palets
	 * @throws IOException problème de communication
	 */
	public static List<Palet> getArtificialPalets(){
		correctedPoints = new ArrayList<Point>();
		for(Point p : artificialPoints){
			correctedPoints.add(utils.R2D2Constants.ADVANCED_CORRECTION ? 
					advancedCorrection(p):simpleCorrection(p));
		}

		List<Palet> palets = new ArrayList<Palet>();
		for(Point p : correctedPoints){
			palets.add(new Palet(p, p.getY() > utils.R2D2Constants.Y_SOUTH+10 &&
					p.getY() < utils.R2D2Constants.Y_NORTH-10));
		}
		
		//On supprime les palets hors jeu
		Iterator<Palet> it = palets.iterator();
		while(it.hasNext())
			if(!((Palet)it.next()).isIn_game())
				it.remove();
		
		return palets;
	}	

	public static Point update(Point p_old) throws IOException, MovingPaletException{
		List<Palet> pls = getPalets();
		for(Palet p:pls)
			if(p_old.distance((Point) p.getP())<5)
				return (Point) p.getP();
		
		throw new MovingPaletException();
	}

	public static List<Point> getTheoreticalPoints(){
		return theoreticalPoints;
	}
	public static List<Point> getArtificialPoints(){
		return artificialPoints;
	}
	public static List<Point> getCorrectedPoints(){
		return correctedPoints;
	}

	/**
	 * Corrige la distorsion de type Barrel en un point
	 * @param p le point a corriger
	 * @return le point corrige
	 */	
	private static Point simpleCorrection(Point p){
		float halfwidth = distortedCenterX;//utils.R2D2Constants.WIDTH_ARENA / 2f;
		float halflength = distortedCenterY;//utils.R2D2Constants.LENGTH_ARENA / 2f;
		
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

		for(Point tmp: theoreticalPoints) {
			if(tmp.distance(res) <= 25) {
				res = new Point(tmp.getX(), tmp.getY());
				break;
			}			
		}
		
		return res;
	}

	/**
	 * Corrige la distorsion de type Barrel en un point
	 * @param p le point a corriger
	 * @return le point corrige
	 */	
	private static Point advancedCorrection(Point p){
		float width = utils.R2D2Constants.WIDTH_ARENA;
		float height = utils.R2D2Constants.LENGTH_ARENA;
		float x = p.getX();
		float y = p.getY();
		
		// parameters for correction
		float paramA = 0.01f; // affects only the outermost pixels of the image
		float paramB = 0.02f; // most cases only require b optimization
		float paramC = 0.001f; // most uniform correction
		float paramD = 1.0f - paramA - paramB - paramC; // describes the linear scaling of the image
		
		
		float d = Math.min(height,width) / 2;

		
//		float distortedCenterX = (width - 1)  / 2.0f;
//		float distortedCenterY = (height - 1) / 2.0f;

		float deltaX = (x - distortedCenterX) / d;
		float deltaY = (y - distortedCenterY) / d;
		
		float dstR = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		float srcR = (paramA * dstR * dstR * dstR + paramB * dstR * dstR + paramC * dstR + paramD) * dstR;
		
		float factor = Math.abs(dstR / srcR);

        float srcX = distortedCenterX + (deltaX * factor * d);
        float srcY = distortedCenterY + (deltaY * factor * d);
        
        Point res = new Point(srcX,srcY);
		for(Point tmp: theoreticalPoints) {
			if(tmp.distance(res) <= 15) {
				res = new Point(tmp.getX(), tmp.getY());
				break;
			}
		}
        
        return res;
	}
}
