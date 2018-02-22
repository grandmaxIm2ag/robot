package vue;

import lejos.hardware.Button;

public class InputHandler {

	private Screen screen;

	public InputHandler(Screen screen) {
		this.screen = screen;
	}
	
	public boolean waitOkEscape(int id){
		screen.printQuit();
		screen.printOk();
		return (Button.waitForAnyPress() & id) != 0;
	}

	/**
	 * Attends une touche pressée
	 * @return la touche pressée
	 */
	public int waitAny(){
		screen.printOk();
		return Button.waitForAnyPress();
	}
	
	/**
	 * regarde si le bouton passé en paramètre est celui attendu
	 * @param bitmap le masque
	 * @param id le bouton
	 * @return vrai si c'est le cas
	 */
	public boolean isThisButtonPressed(int bitmap, int id){
		return (bitmap & id) != 0;
	}

	/**
	 * Renvoie vrai si le bouton echap est enfoncé
	 * @return
	 */
	public boolean escapePressed() {
		return Button.ESCAPE.isDown();
	}

	/**
	 * 
	 * @return vrai si le bouton ok est enfoncé
	 */
	public boolean enterPressed() {
		return Button.ENTER.isDown();
	}
}
