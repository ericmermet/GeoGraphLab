/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * SommePonderee.java in fr.ign.cogit.geographlab.algo.maths
 * 
 */
package fr.ign.cogit.geographlab.algo.maths;

import java.util.ArrayList;

/**
 * @author eric
 *
 */
public class SommePonderee {
	
	public static double get(ArrayList<Double> listPoids, ArrayList<Double> listValeurs) {
		
		if (listPoids.size() != listValeurs.size() ) {
			return Double.MIN_VALUE;
		}
		
		double returnValue = 0;
		
		for (int i = 0; i < listPoids.size(); i++) {
			returnValue += listPoids.get(i) * listValeurs.get(i);
		}
		
		return returnValue;
		
	}
	
}
