/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2014
 *
 * SpanningTreeCentrality.java in fr.ign.cogit.geographlab.algo.traverse
 * 
 */

package fr.ign.cogit.geographlab.algo.traverse;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.ArcFactory;
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

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.ClosestFirstIterator;


/**
 * @author eric
 *
 */
public class SpanningTreeCentrality extends Thread {
	
	private Carte carte;
	private Carte nouvelleCartePourNouvelleCouche;
	
	private List<Arc> edgeList;
	private List<Noeud> nodeList;
	
	public Thread thread;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
//	SimpleGraph<Noeud, Arc> testGraphe = new SimpleGraph<Noeud, Arc>(new ArcFactory());
	
	/**
	 * 
	 */
	public SpanningTreeCentrality(Carte carte) {
		this.carte = carte;
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "SpanningTreeCentrality_Node", "SpanningTreeCentrality_Node_" + new Date().getTime()); //$NON-NLS-1$ //$NON-NLS-2$
		this.thread = new Thread(this);	
		
		//Raz arc
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), 0.0);
		}
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), 0.0);
		}
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
				
				int nbOperation = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds().size();
				int cpt = 0;
					
					ClosestFirstIterator<Noeud, Arc> spanningTree = null;
					
					if( this.carte.variablesDeCarte.afficheGrapheNonDirige == false ){
						spanningTree = new ClosestFirstIterator<Noeud, Arc>(this.carte.getGraphe(), noeudSel.getNoeudTopologique(), Double.POSITIVE_INFINITY);
					}else{
						spanningTree = new ClosestFirstIterator<Noeud, Arc>(this.carte.getGraphe().asUndirectedView(), noeudSel.getNoeudTopologique(), Double.POSITIVE_INFINITY);
					}
					
//					HashSet<Arc> listeArcsPourUnArbreSansDoublon = new HashSet<Arc>();
					List<Arc> listeArcsAvecDoublon = new ArrayList<Arc>();
					List<Noeud> listeNoeudsAvecDoublon = new ArrayList<Noeud>();
					
					while (spanningTree.hasNext()) {
						Noeud noeudTemp = spanningTree.next();
						
						createEdgeList(spanningTree, noeudTemp);
//						listeArcsPourUnArbreSansDoublon.addAll(this.edgeList);
						listeArcsAvecDoublon.addAll(this.edgeList);
						listeNoeudsAvecDoublon.addAll(this.nodeList);
					}
					
					for (Arc arc : listeArcsAvecDoublon) {
						double valeurTempArc = arc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()).doubleValue() + 1.0;
						arc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTempArc);
						
						this.max = Math.max(valeurTempArc, this.max);
						this.min = Math.min(valeurTempArc, 0);
						
					}
					
					for (Noeud node : listeNoeudsAvecDoublon) {
						double valeurTempNoeud = node.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()).doubleValue() + 1.0;
//						int degre = this.nouvelleCartePourNouvelleCouche.getGraphe().degreeOf(node);
						node.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTempNoeud);
						
						this.max = Math.max(valeurTempNoeud, this.max);
						this.min = Math.min(valeurTempNoeud, 0);
						
					}
					
					// Mise a jour de la progress bar
					this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(cpt++, nbOperation);
				
			}
		
		}
		
		// Mise a jour de la legende et de liens couleurs - legende
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setEchelle(this.min, this.max, this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().getIntervalles());
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
		this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String(Messages.getString("CentraliteIntermediaire.5")); //$NON-NLS-1$
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		//				this.carte.repaint();
		System.out.println(Messages.getString("CentraliteIntermediaire.4") + Timer.tac()); //$NON-NLS-1$
		System.out.println("Finish Spanning tree");
		
	}
	
	private void createEdgeList(ClosestFirstIterator<Noeud, Arc> iter, Noeud noeudDestination) {
		this.edgeList = new ArrayList<Arc>();
		this.edgeList.clear();
		this.nodeList = new ArrayList<Noeud>();
		this.nodeList.clear();
		
		while (true) {
			Arc edge = iter.getSpanningTreeEdge(noeudDestination);
			
			if (edge == null) {
				break;
			}
			
			this.edgeList.add(edge);
			this.nodeList.add(edge.getSource());
			this.nodeList.add(edge.getTarget());
			noeudDestination = Graphs.getOppositeVertex(this.carte.getGraphe(), edge, noeudDestination);
		}
		Collections.reverse(this.edgeList);
	}
	
}