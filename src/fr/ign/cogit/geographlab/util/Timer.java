/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * Timer.java in fr.ign.cogit.geographlab.util
 * 
 */
package fr.ign.cogit.geographlab.util;

import java.util.Calendar;

/**
 * @author eric
 *
 */
public class Timer {
	
	static Calendar c1, c2;
	static long debut;
	
	public static void tic(){
		c1 = Calendar.getInstance();
		debut = c1.getTimeInMillis();
	}
	
	public static long tac(){
		c2 = Calendar.getInstance();
		return c2.getTimeInMillis() - debut;
	}
	
}