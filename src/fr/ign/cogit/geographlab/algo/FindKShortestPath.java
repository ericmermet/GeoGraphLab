/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo;

import java.util.Arrays;

import org.jgrapht.GraphPath;

import fr.ign.cogit.geographlab.cheminements.Constantes;
import fr.ign.cogit.geographlab.cheminements.KPCC;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.exploration.Espace;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;

public class FindKShortestPath extends Thread {
	
	private Carte carte;
	private Espace espace;
	
	public Thread thread;
	
	public FindKShortestPath(Carte carte) {
		this.thread = new Thread(this);
		this.carte = carte;
		this.espace = carte.getEspace();
	}
	
	@Override
	public void run() {
		int i = 0;
		int nbOperation = this.espace.getEspaceDeDef().size();
		double valeurArc = 0;
		double valeurNoeud = 0;
		
		for (OD iterOD : this.espace.getEspaceDeDef().values()) {
			if (i % 100 == 0)
				System.out.println(i + " / " + nbOperation);
			KPCC localPCC = new KPCC(this.carte.getGraphe(), iterOD, 1, Constantes.typePCC, 3, false);
			
			double[] longueursDesChemins = new double[localPCC.getChemins().size()];
			int j = 0;
			for (GraphPath<Noeud, Arc> unChemin : localPCC.getChemins()) {
				longueursDesChemins[j++] = unChemin.getWeight();
			}
			
			Arrays.sort(longueursDesChemins);
			
			double premierElementDuTableau = longueursDesChemins[0];
			int nbCheminsDeMmLongueur = 0;
			
			for (int l = 1; l < longueursDesChemins.length; l++) {
				if (longueursDesChemins[l] == premierElementDuTableau) {
					nbCheminsDeMmLongueur++;
					System.out.println("Plusieurs plus courts chemins existants sur le reseau de longueur " + premierElementDuTableau);
				}
			}
			
			if (nbCheminsDeMmLongueur > 0) {
				for (GraphPath<Noeud, Arc> unChemin : localPCC.getChemins()) {
					if (unChemin.getWeight() == premierElementDuTableau) {
						System.out.println(unChemin.getStartVertex().getNom() + " -> " + unChemin.getEndVertex().getNom() + " [ ");
						for (Arc iterArc : unChemin.getEdgeList()) {
							System.out.print(iterArc.getNom());
						}
						System.out.print(" ].");
						
					}
				}
			}
			
			this.carte.parent.parent.statusBar.changeProgressBar(i++, nbOperation);
		}
		
	}
	
}
