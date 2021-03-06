/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.indicateurs;

import java.awt.Color;
import java.util.Calendar;

import javax.swing.JOptionPane;


import fr.ign.cogit.geographlab.cheminements.APSP;
import fr.ign.cogit.geographlab.cheminements.Chemin;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.test.Debug;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

public class Chevelu extends Thread {
	
	private Carte carteMere, nouvelleCartePourNouvelleCouche;
	
	private boolean calculAutorise = false;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	ObjetGraphique objetGraphiqueSelectionne;
	
	public Thread thread;
	
	public Chevelu(Carte carte) {
		
		this.carteMere = carte;
		this.thread = new Thread(this);
		
		if (carte.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects.size() == 1) {
			for (ObjetGraphique objGraphique : carte.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects) {
				this.objetGraphiqueSelectionne = objGraphique;
				this.calculAutorise = true;
				
				this.nouvelleCartePourNouvelleCouche = new Carte(carte, "Chevelu", new String("Chevelu" + "_" + this.objetGraphiqueSelectionne.getNom()));
				
				// Insertion dans le gestionnaire de couches
				this.nouvelleCartePourNouvelleCouche.setColorLayer(Color.DARK_GRAY);
				// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
			}
		} else {
			if (carte.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects.size() < 1)
				JOptionPane.showMessageDialog(carte.fenetrePrincipale, "Pas de selection d'objet pour en effectuer le chevelu");
			else
				JOptionPane.showMessageDialog(carte.fenetrePrincipale, "Il faut selectionner un seul noeud ou un seul arc pour effectuer un chevelu");
			this.calculAutorise = false;
		}
		
	}
	
	@Override
	public void run() {
		int i = 0;
		
		if (this.calculAutorise) {
			if (this.nouvelleCartePourNouvelleCouche.getTousLesPCC().size() == 0) {
				System.out.println("Calcul de tous les PCC d'abord");
				APSP allShortestPath = new APSP(this.nouvelleCartePourNouvelleCouche);
				allShortestPath.run();
			}
			
			int nbOperation = this.nouvelleCartePourNouvelleCouche.getTousLesPCC().size();
			
			for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
				iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0).doubleValue());
			}
			for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
				iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0).doubleValue());
			}
			
			Calendar c1 = Calendar.getInstance();
			long debut = c1.getTimeInMillis();
			
			// ALGO
			for (Chemin iterPCC : this.nouvelleCartePourNouvelleCouche.getTousLesPCC().values()) {
				
				if (iterPCC.estPresente(this.objetGraphiqueSelectionne)) {
					iterPCC.incrStimuli(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant());
					iterPCC.setSelected(true);
				}
			}
			
			this.objetGraphiqueSelectionne.setColor(Color.BLUE);
			this.nouvelleCartePourNouvelleCouche.timerClignotantObjetSelectionnes.addObjetClignotant(this.objetGraphiqueSelectionne);
			
			// Get min et max
			for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
				double tempArc = iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()).doubleValue();
				this.min = Math.min(this.min, tempArc);
				this.max = Math.max(this.max, tempArc);
			}
			for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
				double tempNoeud = iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()).doubleValue();
				this.min = Math.min(this.min, tempNoeud);
				this.max = Math.max(this.max, tempNoeud);
			}
			
			// Affichage pour debug
			for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
				Debug.printDebug("Chevelu Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
			}
			for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
				Debug.printDebug("Chevelu Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
			}
			System.out.println(this.min + " " + this.max);
			
			Calendar c2 = Calendar.getInstance();
			long tempsExecution = c2.getTimeInMillis() - debut;
			;
			System.out.println("Temps d'execution (ms):" + tempsExecution);
			
			// Mise a jour des couleurs par rapport aux valeurs
			this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setEchelle(this.min, this.max, this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().getIntervalles());
			this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
			
			// Activation de la vue indicateur
			this.nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
			this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
			
			this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
			
			// Finalidation de la progress bar a 100pc
			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
			
		}
	}
	
}