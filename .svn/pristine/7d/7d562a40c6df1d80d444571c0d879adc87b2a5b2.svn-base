/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2010
 *
 * SpanningTree.java in algo.traverse
 * 
 */
package fr.ign.cogit.geographlab.algo.traverse;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.ArcFactory;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.lang.Messages;
import fr.ign.cogit.geographlab.util.Timer;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;

import org.jgrapht.Graphs;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.ClosestFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;


/**
 * @author eric
 *
 */
public class SpanningTreeSelect extends Thread {
	
	private Carte carte;
//	private Carte nouvelleCartePourNouvelleCouche;
	
	private List<Arc> edgeList;
	
	public Thread thread;
	
//	SimpleGraph<Noeud, Arc> testGraphe = new SimpleGraph<Noeud, Arc>(new ArcFactory());
	
	/**
	 * 
	 */
	public SpanningTreeSelect(Carte carte) {
		this.carte = carte;
		this.thread = new Thread(this);	
		
	}
	
	@Override
	public void run() {
		
		int nbElementsSelectionnes = this.carte.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects.size();
		
		if (nbElementsSelectionnes == 1) {
			
			NoeudGraphique noeudSel = null;
			
			for (ObjetGraphique objGraphique : this.carte.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects) {
				if (objGraphique.getType() == ConstantesApplication.typeVertex)
					noeudSel = (NoeudGraphique) objGraphique;
			}
			
			if(noeudSel != null ){
				
				Timer.tic();
				
				ClosestFirstIterator<Noeud, Arc> spanningTree = null;
				
				if( this.carte.variablesDeCarte.afficheGrapheNonDirige == false ){
					spanningTree = new ClosestFirstIterator<Noeud, Arc>(this.carte.getGraphe(), noeudSel.getNoeudTopologique(), Double.POSITIVE_INFINITY);
				}else{
					spanningTree = new ClosestFirstIterator<Noeud, Arc>(this.carte.getGraphe().asUndirectedView(), noeudSel.getNoeudTopologique(), Double.POSITIVE_INFINITY);
				}
				
				HashSet<Arc> listeArcsPourUnArbreSansDoublon = new HashSet<Arc>();
				
				while (spanningTree.hasNext()) {
					Noeud noeudTemp = spanningTree.next();
					
					createEdgeList(spanningTree, noeudTemp);
					listeArcsPourUnArbreSansDoublon.addAll(this.edgeList);
				}
				
				
				for (Arc iterArc : listeArcsPourUnArbreSansDoublon) {
					iterArc.getArcGraphique().setSelected(true);
				}
				
				this.carte.repaint();
				System.out.println(Messages.getString("CentraliteIntermediaire.4") + Timer.tac()); //$NON-NLS-1$
				System.out.println("Finish Spanning tree");
				
			}
			
		}else{
			JOptionPane.showMessageDialog(this.carte.fenetrePrincipale, "Il faut selectionner au moins un noeud");
			return;
		}
		
	}
	
	private void createEdgeList(ClosestFirstIterator<Noeud, Arc> iter, Noeud noeudDestination) {
		this.edgeList = new ArrayList<Arc>();
		this.edgeList.clear();
		
		while (true) {
			Arc edge = iter.getSpanningTreeEdge(noeudDestination);

			if (edge == null) {
				break;
			}
//			this.testGraphe.addVertex(edge.getSource());
//			this.testGraphe.addVertex(edge.getTarget());
//			this.testGraphe.addEdge(edge.getSource(), edge.getTarget());
			
			this.edgeList.add(edge);
			noeudDestination = Graphs.getOppositeVertex(this.carte.getGraphe(), edge, noeudDestination);
		}
		Collections.reverse(this.edgeList);
	}
	
}
