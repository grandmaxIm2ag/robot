package main;

import java.io.IOException;

import controler.RobotControler;
import controler.TestRobotControler;

public class MainTestControler {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		RobotControler r = new TestRobotControler();
		r.start();
	}

}
