package main;

import java.io.*;

import lejos.hardware.Button;
import lejos.hardware.Sound;

public class FileWriteTest {
	  public static void main(String[] args) { 
	    FileOutputStream out = null; // declare outside the try block
	    File data = new File("colors.dat");

	    try {
	      out = new FileOutputStream(data);
	    } catch(IOException e) {
	    	System.err.println("Failed to create output stream");
	    	Button.waitForAnyEvent();
	    	System.exit(1);
	    }

	    DataOutputStream dataOut = new DataOutputStream(out);

	    float x = 1f;
	    int length = 8;

	    try { // write
	      for(int i = 0 ; i<length; i++ ) {
	        dataOut.writeFloat(x);
	        x = x*-2.2f; 
	      }
	      out.close(); // flush the buffer and write the file
	    } catch (IOException e) {
	      System.err.println("Failed to write to output stream");
	    }
	    Sound.beepSequence();
	    Button.waitForAnyPress();
	  }
	}
