/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * APSPDijkstraOD.java in fr.ign.cogit.geographlab.cheminements
 * 
 */

package fr.ign.cogit.geographlab.cheminements;

import javax.swing.JOptionPane;

import org.jgrapht.alg.ConnectivityInspector;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.exploration.Espace;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.util.Timer;


public class APSPDijkstraOD extends Thread implements Runnable {
	
	public Carte carte;
	
	public Espace espace;
	
	public Thread thread;
	
	public APSPDijkstraOD(Carte carte) {
		
		this.carte = carte;
		this.espace = carte.getEspace();
		this.thread = new Thread(this);
		
	}
	
	@Override
	public void run() {
		
		ConnectivityInspector<Noeud, Arc> testDeConnexite = new ConnectivityInspector<Noeud, Arc>(this.carte.getGraphe());
		Double nbrCompConnex = new Double(testDeConnexite.connectedSets().size());
		
		if( nbrCompConnex.doubleValue() > 1 ) {
			//Affichage conseil filtrage des composantes connexes
			JOptionPane.showMessageDialog(this.carte.fenetrePrincipale, "Trop de composantes connexes dans le graphe !\n Utilisez l'outil de pré-traitement approprié.");
			System.out.println("Fin APSP");
			return;
		}
		
		int nbOdsCalculeesTotales = 0;
		
		System.out.println("Debut APSP by Dijkstra - calcul des ODs chargés");
		
		this.carte.getTousLesPCC().clear();
		
		Timer.tic();
		
		int nbOperation = this.espace.getToutesLesOD().size();
		
		for (OD iterOD : this.espace.getToutesLesOD()) {
			System.out.println(iterOD.getOrigine().getNom() + " -> " + iterOD.getDestination().getNom());
			
			PCC pcc = new PCC(this.carte.getGraphe(), iterOD, iterOD.getPonderation(), 0, !iterOD.isDirige());
			
			this.carte.getTousLesPCC().put(iterOD.hashCode(), pcc);
			
			this.carte.parent.parent.statusBar.changeProgressBar(nbOdsCalculeesTotales++, nbOperation);
			
		}
		
		System.out.println("Temps d'execution (ms):" + Timer.tac());
		this.carte.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
	}
}