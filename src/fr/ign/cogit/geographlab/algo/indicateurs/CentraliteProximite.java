/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.indicateurs;

import java.awt.Color;
import java.util.Date;



import fr.ign.cogit.geographlab.cheminements.Constantes;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.cheminements.PCC;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.test.Debug;


public class CentraliteProximite extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	public Thread thread;
	
	public CentraliteProximite(Carte carte) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "Centralite_Proximite", "CentraliteDeProximite" + new Date().getTime());
		
		this.thread = new Thread(this);
		
		// Insertion dans le gestionnaire de couches
		this.nouvelleCartePourNouvelleCouche.setColorLayer(Color.ORANGE);
		// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
	}
	
	@Override
	public void run() {
		int i = 0;
		
		int nbNoeuds = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds().size();
		int nbOperation = nbNoeuds;
		double sommeDistance;
		
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		}
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		}
		
		// En partant d'un noeud, on calcule la distance a tous les autres
		// noeuds
		for (Noeud iterNoeudO : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			sommeDistance = 0.0;
			for (Noeud iterNoeudD : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
				if (iterNoeudO != iterNoeudD) {
					PCC localPCC = new PCC(this.nouvelleCartePourNouvelleCouche.getGraphe(), new OD(iterNoeudO, iterNoeudD), 1, Constantes.typePCC);
					sommeDistance += localPCC.getLongueur();
				}
			}
			double valeur = 100 * (nbNoeuds - 1) / sommeDistance;
			
			iterNoeudO.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(valeur));
			
			this.max = Math.max(valeur, this.max);
			this.min = Math.min(valeur, this.min);
			
			// Mise a jour de la progress bar
			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(i++, nbOperation);
		}
		
		// Affichage pour debug
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			Debug.printDebug("Centralite Proximite Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			Debug.printDebug("Centralite Proximite Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		System.out.println(this.min + " " + this.max);
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
		// Mise a jour de la legende et de liens couleurs - legende
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setEchelle(this.min, this.max, this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().getIntervalles());
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
		this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		// this.nouvelleCartePourNouvelleCouche.parent.repaint();
	}
}