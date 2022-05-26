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



import fr.ign.cogit.geographlab.cheminements.APSP;
import fr.ign.cogit.geographlab.cheminements.Chemin;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.test.Debug;

public class Circuite extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	public Thread thread;
	
	public Circuite(Carte carte) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "Circuite", "Circuite" + new Date().getTime());
		this.thread = new Thread(this);
		
		// Insertion dans le gestionnaire de couches
		this.nouvelleCartePourNouvelleCouche.setColorLayer(new Color(200, 0, 100));
		// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
		
	}
	
	@Override
	public void run() {
		
		if (this.nouvelleCartePourNouvelleCouche.getTousLesPCC().size() == 0) {
			System.out.println("Calcul de tous les PCC d'abord");
			APSP allShortestPath = new APSP(this.nouvelleCartePourNouvelleCouche);
			allShortestPath.run();
		}
		
		int i = 0;
		
		Set<Noeud> tousLesNoeuds = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds();
		int nbOperation = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds().size();
		
		Calendar c1 = Calendar.getInstance();
		long debut = c1.getTimeInMillis();
		
		for (Noeud iterNoeudOrigine : tousLesNoeuds) {
			double longueurPCC = 0.0;
			double longueurVolDoiseau = 0.0;
			double circuite = 0.0;
			for (Noeud iterNoeudDestination : tousLesNoeuds) {
				if (iterNoeudOrigine != iterNoeudDestination) {
					
					Chemin localPCC = this.nouvelleCartePourNouvelleCouche.getTousLesPCC().get(new OD(iterNoeudOrigine,iterNoeudDestination));
					longueurPCC = localPCC.getLongueur();
					
					longueurVolDoiseau = Math.sqrt(
							Math.pow(iterNoeudDestination.getPosition().getY()-iterNoeudOrigine.getPosition().getY(), 2.0) + 
							Math.pow(iterNoeudDestination.getPosition().getX()-iterNoeudOrigine.getPosition().getX(), 2.0)
							);
					
					circuite += longueurPCC + longueurVolDoiseau;
					
				}
			}
			circuite = longueurPCC / tousLesNoeuds.size();
			this.max = Math.max(circuite, this.max);
			this.min = Math.min(circuite, this.min);
			
			iterNoeudOrigine.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(circuite));
			
			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(i++, nbOperation);
		}
		
		// Affichage pour debug
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			Debug.printDebug("Centralite Degre Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			Debug.printDebug("Centralite Degre Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
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
}