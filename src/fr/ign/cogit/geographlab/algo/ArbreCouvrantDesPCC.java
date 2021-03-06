/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2011
 *
 * ArbreCouvrantPoidsMinimum.java in algo
 * 
 */
package fr.ign.cogit.geographlab.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.traverse.ClosestFirstIterator;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;


/**
 * @author mermet
 *
 */
public class ArbreCouvrantDesPCC extends Thread {
	
	public Thread thread;
	
	ClosestFirstIterator<Noeud,Arc> iterArbreCouvrantDesPCC;
	Carte carte;
	
	Noeud noeudDepart;
	private List<Arc> edgeList;
	
	public ArbreCouvrantDesPCC(Carte carte, Noeud noeudDepart) {
		this.thread = new Thread(this);
		this.carte = carte;
		this.noeudDepart = noeudDepart;
	}
	
	
	@Override
	public void run() {
		
		if( this.carte.variablesDeCarte.afficheGrapheNonDirige == false ){
			this.iterArbreCouvrantDesPCC = new ClosestFirstIterator<Noeud, Arc>(this.carte.getGraphe(), this.noeudDepart, Double.POSITIVE_INFINITY);
		}else{
			this.iterArbreCouvrantDesPCC = new ClosestFirstIterator<Noeud, Arc>(this.carte.getGraphe().asUndirectedView(), this.noeudDepart, Double.POSITIVE_INFINITY);
		}
		
		HashSet<Arc> listeArcsPourUnArbreSansDoublon = new HashSet<Arc>();
		
		while (this.iterArbreCouvrantDesPCC.hasNext()) {
			Noeud noeudTemp = this.iterArbreCouvrantDesPCC.next();
			
			selectEdgeList(this.iterArbreCouvrantDesPCC, noeudTemp);
			listeArcsPourUnArbreSansDoublon.addAll(this.edgeList);
		}
		
		for (Arc iterArc : listeArcsPourUnArbreSansDoublon) {
			iterArc.getArcGraphique().setSelected(true);
		}
		
		selectEdgeList(this.iterArbreCouvrantDesPCC, this.noeudDepart);
		this.carte.repaint();
		
	}
	
	private void selectEdgeList(ClosestFirstIterator<Noeud, Arc> iter, Noeud noeudDestination) {
		this.edgeList = new ArrayList<Arc>();
		this.edgeList.clear();
		
		while (true) {
			Arc edge = iter.getSpanningTreeEdge(noeudDestination);
			
			if (edge == null) {
				break;
			}
			this.edgeList.add(edge);
			edge.getArcGraphique().setSelected(true);
			noeudDestination = Graphs.getOppositeVertex(this.carte.getGraphe(), edge, noeudDestination);
		}
		Collections.reverse(this.edgeList);
	}
	
}
