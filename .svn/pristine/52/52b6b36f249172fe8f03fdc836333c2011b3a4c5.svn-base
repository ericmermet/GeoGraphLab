/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.indicateurs;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.jgrapht.alg.FloydWarshallShortestPaths;


import fr.ign.cogit.geographlab.cheminements.Constantes;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.cheminements.PCC;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.test.Debug;

public class EloignementMax extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	public Thread thread;
	
	public EloignementMax(Carte carte, int typeChemin) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "Eloignement_Max", "EloignementMax" + new Date().getTime());
		this.thread = new Thread(this);
		
		// Insertion dans le gestionnaire de couches
		this.nouvelleCartePourNouvelleCouche.setColorLayer(Color.ORANGE);
		// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
	}
	
	@Override
	public void run() {
		int i = 0;
		
		Set<Noeud> tousLesNoeuds = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds();
		int nbOperation = tousLesNoeuds.size();
		
		Calendar c1 = Calendar.getInstance();
		long debut = c1.getTimeInMillis();
		
//		FloydWarshallShortestPaths<Noeud, Arc> fwAPSP;
//		if( this.nouvelleCartePourNouvelleCouche.variablesDeCarte.afficheGrapheNonDirige == false ){
//			fwAPSP = new FloydWarshallShortestPaths<Noeud, Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe());
//		}else{
//			fwAPSP = new FloydWarshallShortestPaths<Noeud, Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe().asUndirectedView());
//		}
		
		for (Noeud iterNoeudOrigine : tousLesNoeuds) {
//			double somme = 0.0;
			double eloignementMaxDuNoeud = 0.0;
			for (Noeud iterNoeudDestination : tousLesNoeuds) {
				if (iterNoeudOrigine != iterNoeudDestination) {
					PCC plusCourtChemin = new PCC(this.nouvelleCartePourNouvelleCouche.getGraphe(), new OD(iterNoeudOrigine, iterNoeudDestination), 1, Constantes.typePCC,
							this.nouvelleCartePourNouvelleCouche.parent.carte.variablesDeCarte.afficheGrapheNonDirige);
//					fwAPSP.getShortestPath(iterNoeudOrigine, iterNoeudDestination).getWeight();
					eloignementMaxDuNoeud = Math.max(eloignementMaxDuNoeud, plusCourtChemin.getLongueur());
				}
			}
			this.max = Math.max(eloignementMaxDuNoeud, this.max);
			this.min = Math.min(eloignementMaxDuNoeud, this.min);
			
			iterNoeudOrigine.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(eloignementMaxDuNoeud));
			
			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(i++, nbOperation);
		}
		
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs())
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		
		// Afichage pour debug
//		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
//			Debug.printDebug("Eloignement Max Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
//		}
//		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
//			Debug.printDebug("Eloignement Max Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
//		}
		System.out.println(this.min + " " + this.max);
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
		Calendar c2 = Calendar.getInstance();
		long tempsExecution = c2.getTimeInMillis() - debut;
		;
		System.out.println("Temps d'execution (ms):" + tempsExecution);
		
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