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
import java.util.HashSet;


import fr.ign.cogit.geographlab.cheminements.APSP;
import fr.ign.cogit.geographlab.cheminements.APSPDijkstraODMultiThreads;
import fr.ign.cogit.geographlab.cheminements.Chemin;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.exploration.Espace;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.lang.Messages;
import fr.ign.cogit.geographlab.util.Timer;
import fr.ign.cogit.geographlab.visu.ArcGraphique;

public class CentraliteIntermediaire extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche;
	private Espace espace;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	private HashSet<Arc> arcsNonUtilises;
	
	public Thread thread;
	
	public CentraliteIntermediaire(Carte carte) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, Messages.getString("CentraliteIntermediaire.0"), Messages.getString("CentraliteIntermediaire.1") + new Date().getTime()); //$NON-NLS-1$ //$NON-NLS-2$
		this.thread = new Thread(this);
		// Insertion dans le gestionnaire de couches
		this.nouvelleCartePourNouvelleCouche.setColorLayer(Color.RED);
		// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
		
		this.arcsNonUtilises = new HashSet<Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs());
		this.espace = this.nouvelleCartePourNouvelleCouche.getEspace();
		
	}
	
	// public ValeursVue getValeurs(){
	//
	// // if( this.carte.getTousLesPCC().isEmpty() ){
	// //Si pas de pcc calcules alors on va les calculer
	//
	// this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setMax(this.max);
	// this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setMin(this.min);
	// System.out.println("min = " + this.min);
	// System.out.println("max = " + this.max);
	//
	// return this.valeursCentralite;
	//
	// }
	
	@Override
	public void run() {
		
		if (this.espace == null || this.nouvelleCartePourNouvelleCouche.getTousLesPCC().size() == 0) {
			System.out.println(Messages.getString("CentraliteIntermediaire.2")); //$NON-NLS-1$
			if(ConstantesApplication.filterHasBeenActivated) {
				APSPDijkstraODMultiThreads allShortestPath = new APSPDijkstraODMultiThreads(this.nouvelleCartePourNouvelleCouche);
				allShortestPath.run();
			}else{
				APSP allShortestPath = new APSP(this.nouvelleCartePourNouvelleCouche);
				allShortestPath.run();
			}
			
		}
		
		this.espace = this.nouvelleCartePourNouvelleCouche.getEspace();
		
		int i = 0;
		
		int nbOperation = this.espace.getEspaceDeDef().size();
		
		Double valeurTemp = new Double(0.0);
		
		Timer.tic();
		
		Collection<OD> espaceSet = this.espace.getEspaceDeDef().values();
		
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), 0.0);
		}
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), 0.0);
		}
		
		for (OD iterOD : espaceSet) {
			
			//			PCC localPCC = new PCC(this.nouvelleCartePourNouvelleCouche.getGraphe(), iterOD, 1, Constantes.typePCC);
			
			Chemin localPCC = this.nouvelleCartePourNouvelleCouche.getTousLesPCC().get(iterOD.hashCode());
			
			if( localPCC != null ) {
				
				// localPCC.setSelected(true);
				// this.carte.parent.repaint();
				// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.
				// System.out.println(localPCC.toString());
				if (localPCC.getEdgeList() != null) {
					for (Arc iterArc : localPCC.getEdgeList()) {
						// iterArc.setValeurVue(iterArc.getValeurVue() + 1.0);
						
						//					double poidsNoeudOrigine = localPCC.origine.getValeurPourIndicateur(this.carteParente.getNomIndicateurCourant());
						//					double poidsNoeudDestination = localPCC.destination.getValeurPourIndicateur(this.carteParente.getNomIndicateurCourant());
						//					
						//					double p1 = (poidsNoeudOrigine * poidsNoeudDestination) / (1-poidsNoeudOrigine);
						//					double p2 = (poidsNoeudDestination * poidsNoeudOrigine) / (1-poidsNoeudDestination);
						//
						//					double ponderation = p1 + p2;
						//					iterOD.setPonderation(ponderation);
						
						valeurTemp = iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) + iterOD.getPonderation();
						//					valeurTemp = valeurTemp/espaceSet.size();
						iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTemp.doubleValue());
						
						this.arcsNonUtilises.remove(iterArc);
						
						this.max = Math.max(valeurTemp.doubleValue(), this.max);
						this.min = Math.min(valeurTemp.doubleValue(), this.min);
					}
				}
				if (localPCC.getVerticesList() != null) {
					for (Noeud iterNoeud : localPCC.getVerticesList()) {
						if (iterNoeud != localPCC.getStartVertex() & iterNoeud != localPCC.getEndVertex()) {
							// iterNoeud.setValeurVue(iterNoeud.getValeurVue()+1);
							valeurTemp = iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) + iterOD.getPonderation();
						} else {
							if (iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) == 0)
								valeurTemp = new Double(0.0);
							else
								valeurTemp = iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant());
						}
						//					valeurTemp = valeurTemp/espaceSet.size();
						iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTemp.doubleValue());
						this.max = Math.max(valeurTemp.doubleValue(), this.max);
						this.min = Math.min(valeurTemp.doubleValue(), this.min);
					}
				}
			}else{
				System.out.println("null OD");
			}
			
			// Mise a jour de la progress bar
			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(i++, nbOperation);
			
		}
		
		// Affichage pour debug
//		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
//			System.out.println("Centralite Intermediaire Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
//		}
//		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
//			System.out.println("Centralite Intermediaire Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
//		}
		
		//		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().min = this.min;
		//		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().max = this.max;
		System.out.println(this.min + Messages.getString("CentraliteIntermediaire.3") + this.max); //$NON-NLS-1$
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
		System.out.println(Messages.getString("CentraliteIntermediaire.4") + Timer.tac()); //$NON-NLS-1$
		
		// Selection des arcs non utilises
		for (ArcGraphique iterArcGraphique : this.nouvelleCartePourNouvelleCouche.getVueDuGraphe().getArcsGraphiques()) {
			for (Arc iterArcsNonUtilises : this.arcsNonUtilises) {
				if (iterArcGraphique.getNom().compareTo(iterArcsNonUtilises.getNom()) == 0) {
					iterArcGraphique.setColor(Color.RED);
					System.out.println(iterArcGraphique.toString());
				}
			}
		}
		
		// Mise a jour de la legende et de liens couleurs - legende
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setEchelle(this.min, this.max, this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().getIntervalles());
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
		this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String(Messages.getString("CentraliteIntermediaire.5")); //$NON-NLS-1$
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		 this.nouvelleCartePourNouvelleCouche.parent.repaint();
	}
}