/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.cheminements;

import java.util.ArrayList;
import java.util.List;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;

import org.jgrapht.alg.BellmanFordShortestPath;

public class BFPCC extends Chemin {
	
	private BellmanFordShortestPath<Noeud, Arc> chemin;
	
	public BFPCC(Graphe g, OD od, double ponderation, int type) {
		super(g, od, ponderation, type);
		// for (Noeud noeud : g.getNoeuds()) {
		// System.out.println("vNom = " + noeud.getNom()
		// +" - Position des noeuds = ( " + noeud.getXPosition() + " , " +
		// noeud.getYPosition() + " , " + noeud.hashCode() + " )");
		// }
		// for (Arc arc : g.getArcs()){
		// System.out.println("Nom arcs = " + arc.getNom() + " relie " +
		// arc.getNoeudSource().getNom() + " a " + arc.getNoeudCible().getNom()
		// +" - Position des arcs = ( " + arc.getPointSource().x + " , " +
		// arc.getPointSource().y + " ) " +
		// "-> ( " + arc.getPointCible().x + " , " + arc.getPointCible().y +
		// " ), " +
		// "temps = " + arc.getPoids());
		// }
		calcPcc(od.getOrigine(), od.getDestination());
		setEdgeList(this.chemin.getPathEdgeList(od.getDestination()));
		// setVerticesList(Graphs.getPathVertexList(this));
		setVerticesList(getPathVertexList(this.chemin.getPathEdgeList(od.getDestination())));
	}
	
	private void calcPcc(Noeud n1, Noeud n2) {
		this.chemin = new BellmanFordShortestPath<Noeud, Arc>(getGraph(), n1, 100);
		// System.out.println("Chemin de " + n1.getNom() + " a " + n2.getNom());
		// System.out.println("Longueur du chemin = " +
		// this.chemin.getCost(n2));
		// System.out.println("Il passe par:");
		// for (Arc arc : this.chemin.getPathEdgeList(n2)) {
		// System.out.println(arc.getNom() + " " + arc.getPoids());
		// }
		setChemin(this.chemin);
	}
	
	private void setChemin(BellmanFordShortestPath<Noeud, Arc> chemin) {
		this.chemin = chemin;
	}
	
	private List<Noeud> getPathVertexList(List<Arc> listeArcs) {
		
		List<Noeud> listeRetour = new ArrayList<Noeud>();
		
		for (Arc iterArc : listeArcs) {
			if (!listeRetour.contains(iterArc.getSource()))
				listeRetour.add(iterArc.getSource());
			if (!listeRetour.contains(iterArc.getTarget()))
				listeRetour.add(iterArc.getTarget());
		}
		
		return listeRetour;
	}
}