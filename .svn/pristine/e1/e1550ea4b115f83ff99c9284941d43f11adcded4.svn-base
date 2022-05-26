/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.indicateurs;

import java.awt.Color;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


import fr.ign.cogit.geographlab.cheminements.APSP;
import fr.ign.cogit.geographlab.cheminements.Chemin;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.exploration.Espace;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;

public class Empan extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche;
	private Espace espace;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	public Thread thread;
	
	public Empan(Carte carte) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "Empan", "Empan" + new Date().getTime());
		this.thread = new Thread(this);
		
		// Insertion dans le gestionnaire de couches
		this.nouvelleCartePourNouvelleCouche.setColorLayer(new Color(200, 50, 100));
		// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
		
	}
	
	@Override
	public void run() {
		
		if (this.nouvelleCartePourNouvelleCouche.getTousLesPCC().size() == 0) {
			System.out.println("Calcul de tous les PCC d'abord");
			APSP allShortestPath = new APSP(this.nouvelleCartePourNouvelleCouche);
			allShortestPath.run();
		}
		
		this.espace = this.nouvelleCartePourNouvelleCouche.getEspace();
		
		int i = 0;
		
		int nbOperation = this.espace.getEspaceDeDef().size();
		
		Double valeurTemp = new Double(0.0);
		
		Calendar c1 = Calendar.getInstance();
		
		long debut = c1.getTimeInMillis();
		
		Collection<OD> espaceSet = this.espace.getEspaceDeDef().values();
		
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		}
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		}
		
		Double longueurTotale = new Double(0);
		
		for (OD iterOD : espaceSet) {
			
			Chemin localPCC = this.nouvelleCartePourNouvelleCouche.getTousLesPCC().get(iterOD.hashCode());
			
			longueurTotale += localPCC.getLongueur();
			
			if (localPCC.getEdgeList() != null) {
				for (Arc iterArc : localPCC.getEdgeList()) {
					// iterArc.setValeurVue(iterArc.getValeurVue() + 1.0);
					valeurTemp = iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) + iterOD.getPonderation()*localPCC.getLongueur();
					iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTemp);
					
					
					this.max = Math.max(valeurTemp.doubleValue(), this.max);
					this.min = Math.min(valeurTemp.doubleValue(), this.min);
				}
			}
			if (localPCC.getVerticesList() != null) {
				for (Noeud iterNoeud : localPCC.getVerticesList()) {
//					if (iterNoeud != localPCC.getStartVertex() & iterNoeud != localPCC.getEndVertex()) {
						// iterNoeud.setValeurVue(iterNoeud.getValeurVue()+1);
						valeurTemp = iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) + iterOD.getPonderation()*localPCC.getLongueur();
//					} else {
//						if (iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) == 0)
//							valeurTemp = new Double(0.0);
//						else
//							valeurTemp = iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant());
//					}
					iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTemp);
					this.max = Math.max(valeurTemp.doubleValue(), this.max);
					this.min = Math.min(valeurTemp.doubleValue(), this.min);
				}
			}
			
			// Mise a jour de la progress bar
			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(i++, nbOperation);
			
		}
		
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			valeurTemp = iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) / longueurTotale;
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTemp);
		}
		
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			valeurTemp = iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) / longueurTotale;
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTemp);
		}
		
		// Affichage pour debug
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			System.out.println("Centralite Intermediaire Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			System.out.println("Centralite Intermediaire Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		
		System.out.println(this.min + " " + this.max);
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
		Calendar c2 = Calendar.getInstance();
		long tempsExecution = c2.getTimeInMillis() - debut;
		;
		System.out.println("Temps d'execution (ms):" + tempsExecution);
		
//		// Selection des arcs non utilises
//		for (ArcGraphique iterArcGraphique : this.nouvelleCartePourNouvelleCouche.getVueDuGraphe().getArcsGraphiques()) {
//			for (Arc iterArcsNonUtilises : this.arcsNonUtilises) {
//				if (iterArcGraphique.getNom().compareTo(iterArcsNonUtilises.getNom()) == 0) {
//					iterArcGraphique.setColor(Color.RED);
//					System.out.println(iterArcGraphique.toString());
//				}
//			}
//		}
		
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