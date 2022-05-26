/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
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


public class APSPDijkstra extends Thread {
	
	public Carte carte;
	
	public Espace espace;
	
	public Thread thread;
	
	public APSPDijkstra(Carte carte) {
		
		this.carte = carte;
		this.espace = carte.getEspace();
		this.thread = new Thread(this);
		
	}
	
	@Override
	public void run() {
		
		ConnectivityInspector<Noeud, Arc> testDeConnexite = new ConnectivityInspector<Noeud, Arc>(carte.getGraphe());
		Double nbrCompConnex = new Double(testDeConnexite.connectedSets().size());
		
		if( nbrCompConnex.doubleValue() > 1 ) {
			//Affichage conseil filtrage des composantes connexes
			JOptionPane.showMessageDialog(carte.fenetrePrincipale, "Trop de composantes connexes dans le graphe !\n Utilisez l'outil de pré-traitement approprié.");
			System.out.println("Fin APSP");
			return;
		}
		
		int nbOdsCalculeesTotales = 0;
		
		System.out.println("Debut APSP by Dijkstra");
		
		//Clear espace & populate OD collection 
		System.out.println("Populate OD Space");
		
		this.espace.populate(this.carte.variablesDeCarte.afficheGrapheNonDirige);
		
		this.carte.getTousLesPCC().clear();
		
		Timer.tic();
		
		int nbOperation;
		if( this.carte.variablesDeCarte.afficheGrapheNonDirige )
			nbOperation = this.espace.espaceDeDef.size();
		else
			nbOperation = this.espace.espaceDeDef.size()*2;
		
		for (OD iterOd : this.espace.getToutesLesOD()) {
			
			PCC pccDirect = new PCC(this.carte.getGraphe(), iterOd, 1.0, 0, this.carte.variablesDeCarte.afficheGrapheNonDirige);
			this.carte.getTousLesPCC().put(new Integer(pccDirect.hashCode()), pccDirect);
			
			this.carte.parent.parent.statusBar.changeProgressBar(nbOdsCalculeesTotales++, nbOperation);
			
			if( !this.carte.variablesDeCarte.afficheGrapheNonDirige ) {
				
				PCC pccReverse = new PCC(this.carte.getGraphe(), iterOd.getReverse(), 1.0, 0, this.carte.variablesDeCarte.afficheGrapheNonDirige);
				this.carte.getTousLesPCC().put(new Integer(pccReverse.hashCode()), pccReverse);
				
				this.carte.parent.parent.statusBar.changeProgressBar(nbOdsCalculeesTotales++, nbOperation);
				
			}
		}
		
		this.carte.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		System.out.println("Temps d'execution (ms):" + Timer.tac());
		
	}
}