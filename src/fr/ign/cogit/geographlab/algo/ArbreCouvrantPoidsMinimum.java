/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2011
 *
 * ArbreCouvrantPoidsMinimum.java in algo
 * 
 */
package fr.ign.cogit.geographlab.algo;

import org.jgrapht.alg.KruskalMinimumSpanningTree;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;


/**
 * @author mermet
 *
 */
public class ArbreCouvrantPoidsMinimum extends Thread {
	
	public Thread thread;
	
	KruskalMinimumSpanningTree<Noeud,Arc> arbreCouvrantPoidsMinimum;
	Carte carte;
	
	/**
	 * 
	 */
	public ArbreCouvrantPoidsMinimum(Carte carte) {
		this.thread = new Thread(this);
		this.carte = carte;

	}
	
	
	@Override
	public void run() {
		this.arbreCouvrantPoidsMinimum = new KruskalMinimumSpanningTree<Noeud,Arc>(this.carte.getGraphe());
		
		for(Arc iterArc : this.arbreCouvrantPoidsMinimum.getMinimumSpanningTreeEdgeSet()){
			iterArc.getArcGraphique().setSelected(true);
		}
		
		System.out.println("Le poids de l'arbre est de :" + this.arbreCouvrantPoidsMinimum.getMinimumSpanningTreeTotalWeight());
		
	}
}