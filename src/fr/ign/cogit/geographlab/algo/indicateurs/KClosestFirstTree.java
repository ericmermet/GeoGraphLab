/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.indicateurs;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;

import org.jgrapht.Graphs;
import org.jgrapht.traverse.ClosestFirstIterator;


import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.test.Debug;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

public class KClosestFirstTree extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche;
	private String nomIndicateur = new String("KClosestFirstTree" + new Date().getTime());
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	private List<Arc> edgeList;
	
	public Thread thread;
	
	public KClosestFirstTree(Carte carte) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "K Closest First Tree", this.nomIndicateur);
		this.thread = new Thread(this);
		
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		}
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		}
		
	}
	
	@Override
	public void run() {
		int i = 0;
		
		int nbOperation = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds().size();
		
		int nbElementsSelectionnes = this.nouvelleCartePourNouvelleCouche.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects.size();
		
		if (nbElementsSelectionnes > 0) {
			
			// Insertion dans le gestionnaire de couches apres test reussi des
			// deux objets
			this.nouvelleCartePourNouvelleCouche.setColorLayer(new Color(200, 0, 100));
			// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
			
			NoeudGraphique[] noeudSel = new NoeudGraphique[nbElementsSelectionnes];
			
			for (ObjetGraphique objGraphique : this.nouvelleCartePourNouvelleCouche.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects) {
				if (objGraphique.getType() == ConstantesApplication.typeVertex)
					noeudSel[i++] = (NoeudGraphique) objGraphique;
			}
			
//			List<HashSet<Noeud>> listCollectionNoeuds = new ArrayList<HashSet<Noeud>>();
			
			for (int j = 0; j < noeudSel.length; j++) {
				ClosestFirstIterator<Noeud, Arc> spanningTree = new ClosestFirstIterator<Noeud, Arc>(	this.nouvelleCartePourNouvelleCouche.getGraphe().asUndirectedView(),
																										noeudSel[j].getNoeudTopologique());
				
				noeudSel[j].setColor(Color.BLUE);
				this.nouvelleCartePourNouvelleCouche.timerClignotantObjetSelectionnes.addObjetClignotant(noeudSel[j]);
				
				// HashSet<Noeud> collectionTempo = new HashSet<Noeud>();
				HashSet<Arc> listeArcsPourUnArbreSansDoublon = new HashSet<Arc>();
				
				while(spanningTree.hasNext()) {
					
					// Pour les noeuds mais inutiles puisque l'arbre couvre tous
					// les noeuds....
					Noeud noeudTemp = spanningTree.next();
					int tempValeurNoeud = noeudTemp.getValeurPourIndicateur(this.nomIndicateur).intValue() + 1;
					
					this.max = Math.max(tempValeurNoeud, this.max);
					this.min = Math.min(tempValeurNoeud, this.min);
					
					noeudTemp.setIndicateurValeur(this.nomIndicateur, new Double(tempValeurNoeud));
					// collectionTempo.add(noeudTemp);
					
					// Pour les arcs
					createEdgeList(spanningTree, noeudTemp);
					listeArcsPourUnArbreSansDoublon.addAll(this.edgeList);
				}
				
				// listCollectionNoeuds.add(collectionTempo);
				
				// On depile les arcs pour un arbre, normalement pas de doublon
				for (Arc iterArc : listeArcsPourUnArbreSansDoublon) {
					int tempValeurArc = iterArc.getValeurPourIndicateur(this.nomIndicateur).intValue() + 1;
					this.max = Math.max(tempValeurArc, this.max);
					this.min = Math.min(tempValeurArc, this.min);
					iterArc.setIndicateurValeur(this.nomIndicateur, new Double(tempValeurArc));
				}
				
			}
			
		} else {
			JOptionPane.showMessageDialog(this.nouvelleCartePourNouvelleCouche.fenetrePrincipale, "Il faut selectionner au moins un noeud");
			return;
		}
		
		// Affichage pour debug
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			Debug.printDebug("Nombre d'arbres traversant cet Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			Debug.printDebug("Nombre d'arbre traversant ce Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		System.out.println(this.min + " " + this.max);
		
		// Mise a jour de la legende et de liens couleurs - legende
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setEchelle(this.min, this.max, this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().getIntervalles());
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
		this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		// this.nouvelleCartePourNouvelleCouche.parent.repaint();
	}
	
	private void createEdgeList(ClosestFirstIterator<Noeud, Arc> iter, Noeud noeudDestination) {
		this.edgeList = new ArrayList<Arc>();
		this.edgeList.clear();
		
		while (true) {
			Arc edge = iter.getSpanningTreeEdge(noeudDestination);
			
			if (edge == null) {
				break;
			}
			this.edgeList.add(edge);
			noeudDestination = Graphs.getOppositeVertex(this.nouvelleCartePourNouvelleCouche.getGraphe(), edge, noeudDestination);
		}
		Collections.reverse(this.edgeList);
	}
}