/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.topologie;

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.Graphs;


import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.test.Debug;

public class Triangles extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	private String nomIndicateur = "NombreDetriangles" + new Date().getTime();
	
	public Thread thread;
	
	public Triangles(Carte carte) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "Nombre_Triangles", this.nomIndicateur);
		this.thread = new Thread(this);
		
		// Insertion dans le gestionnaire de couches
		this.nouvelleCartePourNouvelleCouche.setColorLayer(new Color(150, 50, 50));
		// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
		
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
		
		int nbOperation = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds().size();
		
		// Algo ici
		
		HashMap<Integer, Triangle> mesTriangles = new HashMap<Integer, Triangle>();
		
		List<Noeud> listeDesVoisins;
		List<Noeud> listeDesVoisinsDuVoisin;
		List<Noeud> listeDesVoisinsDuVoisinPriveeDuVoisin;
		
		Triangle triangleLocal = null;
		double valeurTemp = 0;
		
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			listeDesVoisins = Graphs.neighborListOf(this.nouvelleCartePourNouvelleCouche.getGraphe(), iterNoeud);
			
			for (Noeud iterNoeudVoisin : listeDesVoisins) {
				listeDesVoisinsDuVoisin = Graphs.neighborListOf(this.nouvelleCartePourNouvelleCouche.getGraphe(), iterNoeudVoisin);
				listeDesVoisinsDuVoisin.remove(iterNoeud);
				
				// listeDesVoisinsDuVoisinPriveeDuVoisin =
				// listeDesVoisinsDuVoisin;
				// listeDesVoisinsDuVoisinPriveeDuVoisin.remove(iterNoeudVoisin);
				
				// OperationsEnsemblistes.intersection(listeDesVoisins,
				// listeDesVoisinsDuVoisin);
				
				// Intersection
				listeDesVoisinsDuVoisin.containsAll(listeDesVoisins);
				
				for (Noeud autreNoeud : listeDesVoisinsDuVoisin) {
					
					triangleLocal = new Triangle(iterNoeud, iterNoeudVoisin, autreNoeud);
					Integer hashCodeTriangle = new Integer(triangleLocal.hashcode());
					
					if (!mesTriangles.containsKey(hashCodeTriangle)) {
						mesTriangles.put(hashCodeTriangle, triangleLocal);
						valeurTemp = iterNoeud.getValeurPourIndicateur(this.nomIndicateur) + 1;
						iterNoeud.setIndicateurValeur(this.nomIndicateur, new Double(valeurTemp));
						System.out.println(triangleLocal.toString());
						this.max = Math.max(valeurTemp, this.max);
						this.min = Math.min(valeurTemp, this.min);
					}
					
				}
			}
			
		}
		
		// Affichage pour debug
		// for(Arc iterArc:
		// this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()){
		// Debug.printDebug("Centralite Degre Arc " + iterArc.getNom() + " = " +
		// iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		// }
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			Debug.printDebug("Nombre de triangles Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
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