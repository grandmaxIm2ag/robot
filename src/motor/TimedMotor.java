package motor;

public abstract class TimedMotor {
	/**
	 * Dans le cas d'un lancement du moteur pour un temps donné,
	 * vérifie si le temps est passé et que le moteur doit être arrêté
	 * 
	 * Vérifie si les moteurs sont coincés.
	 * Dans ce cas, les arrête. Par sécurité.
	 */
	public void checkState(){
		if(isStall() || isTimeRunElapsed()){
			stopMoving();
		}
	}
	/**
	 * 
	 * @return vrai si le robot est coincé
	 */
	public abstract boolean isStall();
	/**
	 * stop le robot
	 */
	public abstract void stopMoving();
	
	/**
	 * lance le robot indéfiniment
	 * @param forward vrai si en avant
	 */
	public abstract void run(boolean forward);
	/**
	 * lance le robot pour X millisenconde
	 * @param millis temps de trajet
	 * @param forward vrai en avant
	 */
	public abstract void runFor(int millis, boolean forward);
	/**
	 * 
	 * @return vrai si le temps de voyage est terminé
	 */
	public abstract boolean isTimeRunElapsed();
}