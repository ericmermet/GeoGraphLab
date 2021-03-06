/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.cheminements;

import java.util.List;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;

import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.AsUndirectedGraph;

public class PCC extends Chemin implements Runnable {
	
	private DijkstraShortestPath<Noeud, Arc> chemin;
	
	public PCC(Graphe g, OD od, double ponderation, int type) {
		super(g, od, ponderation, type);
		
		calcPcc(g, od.getOrigine(), od.getDestination());
		od.setTypeDeCheminement(Constantes.typePCC);
		setEdgeList(this.chemin.getPathEdgeList());
		
		getPathVertexList();
		
	}
	
	public PCC(Graphe g, OD od, double ponderation, int type, boolean dirige) {
		super(g, od, ponderation, type);
		
		this.run();
		
	}
	
	@Override
	public void run() {
//		if( !this.dirige )
			calcPcc(this.g, this.od.getOrigine(), this.od.getDestination());
//		else
//			calcPcc(this.g.asUndirectedView(), this.od.getOrigine(), this.od.getDestination());
		
		this.od.setTypeDeCheminement(Constantes.typePCC);
		setEdgeList(this.chemin.getPathEdgeList());
		
//		System.out.println(this.getEdgeList().toString());
		
		getPathVertexList();
	}
	
	private void calcPcc(Graphe g, Noeud n1, Noeud n2) {
		if( g.containsVertex(n1) & g.containsVertex(n2) ) {
			if( this.dirige )
				this.chemin = new DijkstraShortestPath<Noeud, Arc>(g, n1, n2);
			else
				this.chemin = new DijkstraShortestPath<Noeud, Arc>(this.g.asUndirectedView(), n1, n2);

			setChemin(this.chemin);
		}
		
	}
	
//	private void calcPcc(AsUndirectedGraph<Noeud, Arc> g, Noeud n1, Noeud n2) {
//		if( g.containsVertex(n1) & g.containsVertex(n2) ) {
//			this.chemin = new DijkstraShortestPath<Noeud, Arc>(g, n1, n2);
//			
//			//		List<Arc> listeArcs = this.chemin.getPathEdgeList();
//			//		listeArcs = DijkstraShortestPath.findPathBetween(g, n1, n2);
//			
//			setChemin(this.chemin);
//		}
//		
//	}
	
	private void setChemin(DijkstraShortestPath<Noeud, Arc> chemin) {
		this.chemin = chemin;
	}
	
	@Override
	public double getLongueur() {
		return this.chemin.getPathLength();
	}
	
	@Override
	public String toString() {
		String returnString = new String("Liste des arcs : ");
		
		Double longueurPcc = 0.0;
		
		for (Arc iterArc : getEdgeList()) {
			returnString += "[ " + iterArc.getNom() + "( " + iterArc.getPoidsDistance() + " )" + "] - ";
			longueurPcc += iterArc.getPoidsDistance();
		}
		
		returnString += "\n Liste des noeuds : ";
		
		for (Noeud iterNoeud : getListeNoeuds()) {
			returnString += iterNoeud.getNom() + " - ";
		}
		
		returnString += "\n Longueur : ";
		
		setLongueur(longueurPcc);
		
		returnString += longueurPcc;
		
		return returnString;
	}

}