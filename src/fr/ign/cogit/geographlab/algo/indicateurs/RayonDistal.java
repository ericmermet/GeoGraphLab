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
import java.util.HashSet;
import java.util.Set;

import fr.ign.cogit.geographlab.cheminements.APSP;
import fr.ign.cogit.geographlab.cheminements.Chemin;
import fr.ign.cogit.geographlab.cheminements.Constantes;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.cheminements.PCC;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.test.Debug;

public class RayonDistal extends Thread {
	
	private Carte carteMere, nouvelleCartePourNouvelleCoucheDistal;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	public Thread thread;
	
	public RayonDistal(Carte carte) {
		this.carteMere = carte;
		this.nouvelleCartePourNouvelleCoucheDistal = new Carte(carte, "Rayon_Distal", "RayonDistal" + new Date().getTime());
		this.thread = new Thread(this);
		
		// Insertion dans le gestionnaire de couches
		this.nouvelleCartePourNouvelleCoucheDistal.setColorLayer(Color.PINK);
		// this.nouvelleCartePourNouvelleCoucheDistal.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCoucheDistal);
		
	}
	
	@Override
	public void run() {
		
		int i = 0;
		
		// Prendre les PCC dans la carte mere?
		// Attention, il ne faut pas que la carte mere soit differente des
		// cartes filles...
		if (this.nouvelleCartePourNouvelleCoucheDistal.getTousLesPCC().size() == 0) {
			System.out.println("Calcul de tous les PCC d'abord");
			APSP allShortestPath = new APSP(this.nouvelleCartePourNouvelleCoucheDistal);
			allShortestPath.run();
		}
		
		int nbOperation = this.nouvelleCartePourNouvelleCoucheDistal.getTousLesPCC().size();
		
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCoucheDistal.getGraphe().getNoeuds()) {
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCoucheDistal.getNomIndicateurCourant(), new Double(0.0));
		}
		for (Arc iterArc : this.nouvelleCartePourNouvelleCoucheDistal.getGraphe().getArcs()) {
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCoucheDistal.getNomIndicateurCourant(), new Double(0.0));
		}
		
		Calendar c1 = Calendar.getInstance();
		long debut = c1.getTimeInMillis();
		
		PCC pccInterneOrigine = null, pccInterneDestination = null;
		Set<Arc> arcIntermediaire = new HashSet<Arc>();
		double distanceMax = 0, distanceOrigine, distanceDestination;
		
		// Pour tous les PCC du reseau
		for (Chemin iterPCC : this.nouvelleCartePourNouvelleCoucheDistal.getTousLesPCC().values()) {
			
			// Pour tous les noeuds du chemin
			for (Noeud iterNoeud : iterPCC.getListeNoeuds()) {
				
				distanceOrigine = 0;
				distanceDestination = 0;
				
				pccInterneOrigine = null;
				pccInterneDestination = null;
				
				// On cherche l'extremite la plus ??loign??e
				if (iterNoeud != iterPCC.origine) {
					pccInterneOrigine = new PCC(this.nouvelleCartePourNouvelleCoucheDistal.getGraphe(), new OD(iterNoeud, iterPCC.origine), 1, Constantes.typePCC);
					distanceOrigine = pccInterneOrigine.getLongueur();
				}
				if (iterNoeud != iterPCC.destination) {
					pccInterneDestination = new PCC(this.nouvelleCartePourNouvelleCoucheDistal.getGraphe(), new OD(iterNoeud, iterPCC.destination), 1, Constantes.typePCC);
					distanceDestination = pccInterneDestination.getLongueur();
				}
				
				distanceMax = Math.max(distanceOrigine, distanceDestination);
				
				// if( iterPCC.origine != iterPCC.destination){
				// arcIntermediaire =
				// this.nouvelleCartePourNouvelleCoucheDistal.getGraphe().getAllEdges(iterPCC.origine,
				// iterPCC.destination);
				// for (Arc arc : arcIntermediaire) {
				// arc.getArcGraphique().incrStimuli();
				// arc.setValeurVue((distanceOrigine+distanceDestination)/2);
				// }
				// }
				
				// iterNoeud.setValeurVue(iterNoeud.getValeurVue()+distanceMax);
				double valeurTemp = iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCoucheDistal.getNomIndicateurCourant()) + distanceMax;
				iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCoucheDistal.getNomIndicateurCourant(), valeurTemp);
				iterNoeud.getNoeudGraphique().incrStimuli();
				
				this.max = Math.max(valeurTemp, this.max);
				this.min = Math.min(valeurTemp, this.min);
				
			}
			// Mise a jour de la progress bar
			this.nouvelleCartePourNouvelleCoucheDistal.parent.parent.statusBar.changeProgressBar(i++, nbOperation);
		}
		
		// //Mise en moyenne par rapport aux nombres de fois ou les composantes
		// sont traversees
		// for(Noeud iterNoeud :
		// this.nouvelleCartePourNouvelleCoucheDistal.getGraphe().getNoeuds())
		// iterNoeud.setValeurVue(iterNoeud.getValeurVue()/iterNoeud.getNoeudGraphique().getStimuli());
		// for(Arc iterArc :
		// this.nouvelleCartePourNouvelleCoucheDistal.getGraphe().getArcs())
		// iterArc.setValeurVue(iterArc.getValeurVue()/iterArc.getArcGraphique().getStimuli());
		
		// Affichage pour debug
		for (Arc iterArc : this.nouvelleCartePourNouvelleCoucheDistal.getGraphe().getArcs()) {
			Debug.printDebug("Rayon Distal Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCoucheDistal.getNomIndicateurCourant()));
		}
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCoucheDistal.getGraphe().getNoeuds()) {
			Debug.printDebug("Rayon Distal Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCoucheDistal.getNomIndicateurCourant()));
		}
		System.out.println(this.min + " " + this.max);
		
		Calendar c2 = Calendar.getInstance();
		long tempsExecution = c2.getTimeInMillis() - debut;
		;
		System.out.println("Temps d'execution (ms):" + tempsExecution);
		
		// Mise a jour des couleurs par rapport aux valeurs
		this.nouvelleCartePourNouvelleCoucheDistal.getLegendeDeLaCarte().setEchelle(this.min, this.max, this.nouvelleCartePourNouvelleCoucheDistal.getLegendeDeLaCarte().getIntervalles());
		this.nouvelleCartePourNouvelleCoucheDistal.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		this.nouvelleCartePourNouvelleCoucheDistal.parent.parent.panelTools.viewList.setSelectedIndex(1);
		this.nouvelleCartePourNouvelleCoucheDistal.variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
		
		this.nouvelleCartePourNouvelleCoucheDistal.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		// Finalidation de la progress bar a 100pc
		this.nouvelleCartePourNouvelleCoucheDistal.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
		// this.nouvelleCartePourNouvelleCoucheDistal.parent.repaint();
	}
}