/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2012
 *
 * ParallelKPCC.java in cheminements
 * 
 */
package fr.ign.cogit.geographlab.cheminements;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.traverse.ClosestFirstIterator;

/**
 * @author Eric Mermet
 *
 */
public class ParallelKPCC extends Chemin {
	
	private KShortestPaths<Noeud, Arc> kpcc;
	List<GraphPath<Noeud, Arc>> chemins;
	
	private ArrayList<Arc> edgeListConfusion;
	
	public ParallelKPCC(Graphe g, OD od, double ponderation, int type, boolean dirige) {
		super(g, od, ponderation, type);
		
		calc(g, dirige, od.getOrigine(), od.getDestination());

		
		for (GraphPath<Noeud, Arc> graphPath : this.chemins) {
			setEdgeList(graphPath.getEdgeList());
			setVerticesList(Graphs.getPathVertexList(graphPath));
		}
	}
	
	private void calc(Graphe g, boolean dirige, Noeud n1, Noeud n2) {
		ParallelThreadSpanningTree t1 = new ParallelThreadSpanningTree(g, dirige, n1);
		ParallelThreadSpanningTree t2 = new ParallelThreadSpanningTree(g, dirige, n2);
//		Thread tConfusion = new ParallelThreadConfusion();
		
		//Lancement des threads
		t1.start(); t2.start(); //tConfusion.start();
		
		while (t1.isAlive() & t2.isAlive() ){
			System.out.println("Compute Threads");
		}
		
		System.out.println("Confusion des listes");
//		// Construction d'une nouvelle hashSet
//		ArrayList<Arc> listArcsTemp = new ArrayList<Arc>();
		// Ajout de tous les arcs du thread 1
		this.edgeListConfusion.addAll(t1.getArcs());
		// Retient tous les arcs en commun avec les arcs calculés par thread 2
		this.edgeListConfusion.retainAll(t2.getArcs());
		
	}
	
	public ArrayList<Arc> getArcs() {
		return this.edgeListConfusion;
	}
	
	private class ParallelThreadSpanningTree extends Thread{
		
		Graphe g;
		boolean dirige;
		Noeud n;
		ClosestFirstIterator<Noeud, Arc> spanningTree;
		private ArrayList<Arc> edgeList;
		
		public ParallelThreadSpanningTree(Graphe g, boolean dirige, Noeud n) {
			this.g = g;
			this.dirige = dirige;
			this.n = n;
		}
		
		@Override
		public void run() {
			if( this.dirige == false ){
				this.spanningTree = new ClosestFirstIterator<Noeud, Arc>(this.g, this.n, Double.POSITIVE_INFINITY);
			}else{
				this.spanningTree = new ClosestFirstIterator<Noeud, Arc>(this.g.asUndirectedView(), this.n, Double.POSITIVE_INFINITY);
			}
			
			HashSet<Arc> listeArcsPourUnArbreSansDoublon = new HashSet<Arc>();
			
			while (this.spanningTree.hasNext()) {
				Noeud noeudTemp = this.spanningTree.next();
				
				selectEdgeList(this.spanningTree, noeudTemp);
				listeArcsPourUnArbreSansDoublon.addAll(this.edgeList);
			}
			
			for (Arc iterArc : listeArcsPourUnArbreSansDoublon) {
				iterArc.getArcGraphique().setSelected(true);
			}
			
			selectEdgeList(this.spanningTree, this.n);
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
				noeudDestination = Graphs.getOppositeVertex(this.g, edge, noeudDestination);
			}
			Collections.reverse(this.edgeList);
		}
		
		public ArrayList<Arc> getArcs() {
			return this.edgeList;
		}
		
	}
	
	private class ParallelThreadConfusion extends Thread{
		
	}
	
}