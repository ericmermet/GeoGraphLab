/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2010
 *
 * APSPFloydWarshall.java in algo.indicateurs
 * 
 */
package fr.ign.cogit.geographlab.cheminements;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;

import org.jgrapht.Graph;
import org.jgrapht.alg.FloydWarshallShortestPaths;

/**
 * @author mermet
 *
 */
public class APSPFloydWarshall extends FloydWarshallShortestPaths<Noeud, Arc>{

	Graphe g;
	
	/**
	 * @param arg0
	 */
	public APSPFloydWarshall(Graphe arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		
		this.g = arg0;
		
	}
	
	public void printAllPaths(){
		
//		for (Noeud iterNoeud : ) {
//			
//		}
		
	}
	
}
