/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.indicateurs;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.geonames.utils.Distance;
import org.jgrapht.Graphs;

import fr.ign.cogit.geographlab.algo.maths.Distances;
import fr.ign.cogit.geographlab.cheminements.Chemin;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.test.Debug;

public class BuildDependencies extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche, carteParente;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	public Thread thread;
	
	public BuildDependencies(Carte carte) {
		this.carteParente = carte;
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "RelationalDependencies", "RelationalDependencies");
		this.thread = new Thread(this);
		
		// Insertion dans le gestionnaire de couches
		this.nouvelleCartePourNouvelleCouche.setColorLayer(new Color(200, 0, 100));
		
	}
	
	@Override
	public void run() {
		int i = 0;
		
		int nbOperation = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds().size();
		
//		HashMap<Integer, Chemin> tousChemins = this.nouvelleCartePourNouvelleCouche.getTousLesPCC();
		
		Collection<OD> espaceSet = this.carteParente.getEspace().getEspaceDeDef().values();
		
		for (OD iterODA : espaceSet) {
			
			Chemin cheminA = this.nouvelleCartePourNouvelleCouche.getTousLesPCC().get(iterODA.hashCode());
			
			if( cheminA != null ) {
				
				for (OD iterODB : espaceSet) {
					
					Chemin cheminB = this.nouvelleCartePourNouvelleCouche.getTousLesPCC().get(iterODB.hashCode());
					
					if( cheminB != null ) {
						
						double Dab = Distances.brunetAB(cheminA, cheminB);
						double Dba = Distances.brunetBA(cheminA, cheminB);
						double DAB = Dab + Dba;
						
						System.out.println("DBrunet_AB = " + Dab + " DBrunet_BA = " + Dba + " DBrunet = " + DAB);
						
					}
				}
			}
			
		}
		
		//		for (Noeud noeudA : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
		//			
		//			//dependance de B -> A si valeur de A > B
		//			
		//			double minDiff = Double.MAX_VALUE;
		//			Noeud noeudAConnecter = noeudA;
		//			for(Noeud noeudB : Graphs.neighborListOf(this.nouvelleCartePourNouvelleCouche.getGraphe(), noeudA)) {
		//				String indicateurParent = this.carteParente.getNomIndicateurCourant();
		//				double diff = Math.abs(noeudA.getValeurPourIndicateur(indicateurParent).doubleValue() - noeudB.getValeurPourIndicateur(indicateurParent).doubleValue());
		//				
		//				if( diff < minDiff ) {
		//					noeudAConnecter = noeudB;
		//					minDiff = diff;
		//				}
		//				
		//				noeudA.setSelected(true);
		//				noeudAConnecter.setSelected(true);
		//				if( this.nouvelleCartePourNouvelleCouche.getGraphe().getArc(noeudA, noeudAConnecter) != null )
		//					this.nouvelleCartePourNouvelleCouche.getGraphe().getArc(noeudA, noeudAConnecter).setSelected(true);
		//				else
		//					this.nouvelleCartePourNouvelleCouche.getGraphe().getArc(noeudAConnecter, noeudA).setSelected(true);
		//				
		//			}
		//			
		//		}
		//		
		//		// Affichage pour debug
		//		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
		//			Debug.printDebug("Centralite Degre Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		//		}
		//		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
		//			Debug.printDebug("Centralite Degre Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		//		}
		//		System.out.println(this.min + " " + this.max);
		
		// Mise a jour de la legende et de liens couleurs - legende
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setEchelle(this.min, this.max, this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().getIntervalles());
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
		this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		this.nouvelleCartePourNouvelleCouche.parent.repaint();
	}
}