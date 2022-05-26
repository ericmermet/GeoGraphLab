/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2014
 *
 * APSPFloydWarshallTest.java in fr.ign.cogit.geographlab.cheminements
 * 
 */

package fr.ign.cogit.geographlab.cheminements;

import javax.swing.JOptionPane;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.traverse.ClosestFirstIterator;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.exploration.Espace;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.util.Timer;


public class APSPFloydWarshallTest extends Thread implements Runnable {
	
	public Carte carte;
	
	public Espace espace;
	
	public Thread thread;
	
	public APSPFloydWarshallTest(Carte carte) {
		
		this.carte = carte;
		this.espace = carte.getEspace();
		this.thread = new Thread(this);
		
	}
	
	@Override
	public void run() {
		
		this.carte.parent.parent.statusBar.mainProgressBar.setIndeterminate(true);
		
		ConnectivityInspector<Noeud, Arc> testDeConnexite = new ConnectivityInspector<Noeud, Arc>(this.carte.getGraphe());
		Double nbrCompConnex = new Double(testDeConnexite.connectedSets().size());
		
		if( nbrCompConnex.doubleValue() > 1 ) {
			//Affichage conseil filtrage des composantes connexes
			JOptionPane.showMessageDialog(this.carte.fenetrePrincipale, "Trop de composantes connexes dans le graphe !\n Utilisez l'outil de pré-traitement approprié.");
			System.out.println("Fin APSP");
			this.carte.parent.parent.statusBar.mainProgressBar.setIndeterminate(false);
			return;
		}
		
		int nbOdsCalculeesTotales = 0;
		
		System.out.println("Debut APSP by Floyd-Warhall - process all");
		
		this.carte.getTousLesPCC().clear();
		this.carte.getEspace().clearEspaceDeDef();
		
		Timer.tic();
		
		int nbOperation = this.carte.getGraphe().getNoeuds().size();
		
		FloydWarshallShortestPaths<Noeud, Arc> fwAPSP;
		if( this.carte.variablesDeCarte.afficheGrapheNonDirige == false ){
			fwAPSP = new FloydWarshallShortestPaths<Noeud, Arc>(this.carte.getGraphe());
		}else{
			fwAPSP = new FloydWarshallShortestPaths<Noeud, Arc>(this.carte.getGraphe().asUndirectedView());
		}
		
//		FloydWarshallShortestPaths<Noeud, Arc> fwAPSP = new FloydWarshallShortestPaths<Noeud, Arc>(this.carte.getGraphe());
		
		this.carte.parent.parent.statusBar.mainProgressBar.setIndeterminate(false);
		for (Noeud noeud : this.carte.getGraphe().getNoeuds()) {
			
			System.out.print(nbOdsCalculeesTotales++);
			
			for (GraphPath<Noeud,Arc> path : fwAPSP.getShortestPaths(noeud)) {
				
//				System.out.print(".");
				
				OD tempOD = new OD(path.getStartVertex(), path.getEndVertex());
				this.carte.getEspace().espaceDeDef.put(tempOD.hashCode(), tempOD);
				
				Chemin myPath = new Chemin(this.carte.getGraphe(), path, tempOD);
				
				this.carte.getTousLesPCC().put(new Integer(myPath.hashCode()), myPath);
				
			}
			System.out.println("\n");
			
			this.carte.parent.parent.statusBar.changeProgressBar(nbOdsCalculeesTotales, nbOperation);
		}
		
//		for (GraphPath<Noeud,Arc> path : fwAPSP.getShortestPaths()) {
//			
//			System.out.println(nbOdsCalculeesTotales++);
//			
//			OD tempOD = new OD(path.getStartVertex(), path.getEndVertex());
//			this.carte.getEspace().espaceDeDef.put(tempOD.hashCode(), tempOD);
//			
//			Chemin myPath = new Chemin(this.carte.getGraphe(), path, tempOD);
//			
//			this.carte.getTousLesPCC().put(new Integer(path.hashCode()), myPath);
//			
//		}
		
		System.out.println("Temps d'execution (ms):" + Timer.tac());
		this.carte.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
	}
}