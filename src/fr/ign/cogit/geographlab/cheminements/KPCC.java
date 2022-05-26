/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.cheminements;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.KShortestPaths;

public class KPCC extends Chemin {
	
	private KShortestPaths<Noeud, Arc> kpcc;
	List<GraphPath<Noeud, Arc>> chemins;
	
	public KPCC(Graphe g, OD od, double ponderation, int type, int k, boolean dirige) {
		super(g, od, ponderation, type);
		if( dirige )
			calcPcc(g.asUndirectedView(), od.getOrigine(), od.getDestination(), k);
		else
			calcPcc(g, od.getOrigine(), od.getDestination(), k);
		for (GraphPath<Noeud, Arc> graphPath : this.chemins) {
			setEdgeList(graphPath.getEdgeList());
			setVerticesList(Graphs.getPathVertexList(graphPath));
		}
	}
	
	private void calcPcc(Graph<Noeud, Arc> g, Noeud n1, Noeud n2, int k) {
		this.kpcc = new KShortestPaths<Noeud, Arc>(g, n1, k);
		this.chemins = this.kpcc.getPaths(n2);
		// for (GraphPath<Noeud,Arc> graphPath : this.chemins) {
		// System.out.println("Il passe par:");
		// for (Arc arc : graphPath.getEdgeList()) {
		// System.out.println(arc.getNom() + " " + arc.getPoids());
		// }
		// }
	}
	
	public List<GraphPath<Noeud, Arc>> getChemins() {
		return this.chemins;
	}
}
