/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2012
 *
 * ParallelKPCCSuperposes.java in algo.indicateurs
 * 
 */

package fr.ign.cogit.geographlab.algo.indicateurs;


import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.cheminements.ParallelKPCC;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.test.Debug;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

public class ParallelKPCCSuperposes extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche;
	
	private double min = Double.MAX_VALUE;
	private double max = 0;
	int k = 2;
	
	public Thread thread;
	
	public ParallelKPCCSuperposes(Carte carte) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "Flux_PCC", "Flux_PCC" + new Date().getTime());
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
		int nbOperation = this.k;
		Calendar c1 = Calendar.getInstance();
		
		long debut = c1.getTimeInMillis();
		
		// Algo ici
		
		if (this.nouvelleCartePourNouvelleCouche.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects.size() == 2) {
			
			// Insertion dans le gestionnaire de couches apres test reussi des deux objets
			this.nouvelleCartePourNouvelleCouche.setColorLayer(new Color(200, 0, 100));
			// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
			
			NoeudGraphique[] noeudSel = new NoeudGraphique[2];
			
			for (ObjetGraphique objGraphique : this.nouvelleCartePourNouvelleCouche.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects) {
				if (objGraphique.getType() == ConstantesApplication.typeVertex)
					noeudSel[i++] = (NoeudGraphique) objGraphique;
			}
			
			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.mainProgressBar.setIndeterminate(true);

			boolean dirige = this.nouvelleCartePourNouvelleCouche.variablesDeCarte.afficheGrapheNonDirige;
				
			ParallelKPCC fluxPCC = new ParallelKPCC(this.nouvelleCartePourNouvelleCouche.getGraphe(), new OD(noeudSel[0].getNoeudTopologique(), noeudSel[1].getNoeudTopologique()), 0, 1, dirige);;
			
			for (Arc iterArc: fluxPCC.getArcs()) {
				
				int temp = iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()).intValue() + 1;
				iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(temp));
			}
			
			
		} else {
			JOptionPane.showMessageDialog(this.nouvelleCartePourNouvelleCouche.fenetrePrincipale, "Il faut selectionner deux noeuds comme Origine et Destination");
			return;
		}
		
		// Affichage pour debug
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			Debug.printDebug("Kppc Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			Debug.printDebug("Kpcc Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		System.out.println(this.min + " " + this.max);
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.mainProgressBar.setIndeterminate(false);
		
		Calendar c2 = Calendar.getInstance();
		long tempsExecution = c2.getTimeInMillis() - debut;

		System.out.println("Temps d'execution (ms):" + tempsExecution);
		
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
	
}