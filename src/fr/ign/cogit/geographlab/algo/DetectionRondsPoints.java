/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;

import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;

public class DetectionRondsPoints {
	
	Carte carte;
	Graphe graphe;
	
	public DetectionRondsPoints(Carte carte) {
		this.carte = carte;
		this.graphe = this.carte.getGraphe();
	}
	
	public Set<Noeud> detecteCyclePourLeNoeud(Noeud noeudADetecter) {
		// public java.util.Set<V> findCyclesContainingVertex(V v)
		
		CycleDetector<Noeud, Arc> detecteurDeCycle;// = new
		// CycleDetector(this.graphe);
		detecteurDeCycle = new CycleDetector<Noeud, Arc>((DirectedGraph<Noeud, Arc>) this.graphe);
		
//		System.out.println("pouet");
		
		return detecteurDeCycle.findCycles();
	}
	
}
