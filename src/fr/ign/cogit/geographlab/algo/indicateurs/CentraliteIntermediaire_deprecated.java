/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.indicateurs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;

import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.exploration.Espace;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.util.Timer;
import fr.ign.cogit.geographlab.visu.ArcGraphique;

public class CentraliteIntermediaire_deprecated extends Thread {
	
	private Carte carteParente, nouvelleCartePourNouvelleCouche;
	private Espace espace;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	private HashSet<Arc> arcsNonUtilises;
	
	public Thread thread;
	
	public CentraliteIntermediaire_deprecated(Carte carte) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "Centralite_Intermediaire", "CentraliteIntermediaire" + new Date().getTime());
		this.thread = new Thread(this);
//		this.espace = this.nouvelleCartePourNouvelleCouche.getEspace();
		this.carteParente = carte;
		
		// Insertion dans le gestionnaire de couches
		this.nouvelleCartePourNouvelleCouche.setColorLayer(Color.RED);
		// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
		
		this.arcsNonUtilises = new HashSet<Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs());
		
	}
	
	@Override
	public void run() {
		
		this.espace = this.nouvelleCartePourNouvelleCouche.getEspace();
		
		int i = 0;
		Double valeurTemp = new Double(0.0);
		
		Timer.tic();
		
		Collection<OD> espaceSet = this.espace.getEspaceDeDef().values();
		int nbOperation = (int) Math.pow(this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds().size(), 2.0);
		
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), 0.0);
		}
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), 0.0);
		}
		
		for (Noeud iterNoeud1 : this.carteParente.getGraphe().getNoeuds()) {
			
			for (Noeud iterNoeud2 : this.carteParente.getGraphe().getNoeuds()) {
			
				if(iterNoeud1 != iterNoeud2 ) {
					
					DijkstraShortestPath<Noeud, Arc> dj = new DijkstraShortestPath<Noeud, Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe().asUndirectedView(), iterNoeud1, iterNoeud2);
					
					List<Arc> lArcs = dj.getPathEdgeList();
					
					double poidsNoeudOrigine = iterNoeud1.getValeurPourIndicateur(this.carteParente.getNomIndicateurCourant()).doubleValue();
					double poidsNoeudDestination = iterNoeud2.getValeurPourIndicateur(this.carteParente.getNomIndicateurCourant()).doubleValue();
					
					double p1 = Math.abs((poidsNoeudOrigine * poidsNoeudDestination) / (1-poidsNoeudOrigine));
					double p2 = Math.abs((poidsNoeudDestination * poidsNoeudOrigine) / (1-poidsNoeudDestination));
					
					double ponderation = p1 + p2;
					
					//Comptage des arcs du chemin
					for (Arc iterArc : lArcs) {
						
						valeurTemp = iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) + ponderation/nbOperation;
						
						iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(),valeurTemp);
						this.max = Math.max(valeurTemp.doubleValue(), this.max);
						this.min = Math.min(valeurTemp.doubleValue(), this.min);
					}
					
					//Comptage des noeuds du chemin
					for (Noeud iterNoeud : getPathVertexList(lArcs)) {
						
						valeurTemp = iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) + ponderation/nbOperation;
						
						iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(),valeurTemp);
						this.max = Math.max(valeurTemp.doubleValue(), this.max);
						this.min = Math.min(valeurTemp.doubleValue(), this.min);
					}
				}
				this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(i++, nbOperation);
			}
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
		System.out.println(this.min + " " + this.max);
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);

		System.out.println("Temps d'execution (ms):" + Timer.tac());
		
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
		this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		// this.nouvelleCartePourNouvelleCouche.parent.repaint();
	}
	
	public List<Noeud> getPathVertexList(List<Arc> listeArcs) {
		
		LinkedHashSet<Noeud> listeConstruite = new LinkedHashSet<Noeud>();
		
		Noeud noeudOrigine = listeArcs.get(0).getSource();
		if (listeArcs.size() > 1) {
			if (noeudOrigine == listeArcs.get(1).getTarget() | noeudOrigine == listeArcs.get(1).getSource())
				noeudOrigine = listeArcs.get(0).getTarget();
		}
		
		for (Iterator<Arc> i = listeArcs.iterator(); i.hasNext();) {
			Arc e = i.next();
			listeConstruite.add(e.getSource());
			listeConstruite.add(e.getTarget());
		}

		List<Noeud> vertexList = new ArrayList<Noeud>(listeConstruite);
		Noeud noeudDestination = vertexList.get(vertexList.size() - 1);
		
		vertexList.remove(noeudOrigine);
		vertexList.remove(noeudDestination);
		
		return vertexList;
	}
}