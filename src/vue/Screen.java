package vue;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;

public class Screen {

	private static int maxLignes = 8;
	private GraphicsLCD g;
	private int y_quit     = 100;
	private int width_quit = 45;
	private int height_quit = width_quit/2;
	

	public Screen(){
		g = LocalEV3.get().getGraphicsLCD();
	}
	
	/**
	 * imprimer un titre et 8 lignes max à l'écran
	 * @param title
	 * @param lignes
	 * @return le key code renvoyé par l'action si il s'agit d'enter
	 * renverra le key code escape sinon
	 */
	public void drawText(String title, String ... lignes){
		g.clear();
		g.setFont(Font.getDefaultFont());
		g.drawString(title, 5, 0, 0);
		g.setFont(Font.getSmallFont());
		
		for(int i = 0; i<lignes.length && i<maxLignes; i++){
			g.drawString(lignes[i], 2, 20+(10*i), 0);
		}
	}

	protected void printQuit(){
		int arc_diam = 6;
		g.drawString("QUIT", 9, y_quit+7, 0);
		g.drawLine(0, y_quit,  45, y_quit); // top line
		g.drawLine(0, y_quit,  0, y_quit+height_quit-arc_diam/2); // left line
		g.drawLine(width_quit, y_quit,  width_quit, y_quit+height_quit/2); // right line
		g.drawLine(0+arc_diam/2, y_quit+height_quit,  width_quit-10, y_quit+height_quit); // bottom line
		g.drawLine(width_quit-10, y_quit+height_quit, width_quit, y_quit+height_quit/2); // diagonal
		g.drawArc(0, y_quit+height_quit-arc_diam, arc_diam, arc_diam, 180, 90);
	}

	/**
	 * Dessine le bouton OK
	 */
	protected void printOk(){
		// Enter GUI button:
		g.fillRect(width_quit+10, y_quit, height_quit, height_quit);
		g.drawString("GO", width_quit+15, y_quit+7, 0,true);
	}

	/**
	 * efface l'écran
	 */
	public void clearDraw() {
		g.clear();
	}

	/**
	 * efface l'écran
	 */
	public void clearPrintln() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}

	/**
	 * dessine une courbe
	 * @param raw les données de la courbe
	 */
	public void drawGraphic(float[] raw) {
		g.clear();
		for(int i=0; i<raw.length; i++){
			g.drawLine(i, 0, i+70, (int) raw[i]);
		}
	}
}
