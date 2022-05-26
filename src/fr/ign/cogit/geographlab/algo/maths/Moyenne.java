/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2011
 *
 * Moyenne.java in algo.maths
 * 
 */
package fr.ign.cogit.geographlab.algo.maths;

import java.util.List;

/**
 * @author eric
 *
 */
public class Moyenne {
	
	public static Double arithmetique(Double a, Double b) {
		return (a+b)/2.0;
	}
	
	public static Double arithmetique(List<Double> liste) {
		
		double sommetemp = 0;
		
		for (Double temp : liste) {
			sommetemp += temp.doubleValue();
		}
		
		return new Double(sommetemp/liste.size());
		
	}
	
	public static Double arithmetiquePondere(Double a, Double poidsA, Double b, Double poidsB) {
		return (a*poidsA + b*poidsB)/(poidsA+poidsB);
	}
	
	public static Double medianne(Double[] tabDouble) {
		//TO DO		
		return 0.0;
	}
	
	public static void geometrique() {
		//TO DO
	}
	
	public static void harmonique() {
		//TO DO
	}
}
