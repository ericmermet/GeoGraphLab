/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2011
 *
 * FWSP.java in cheminements
 * 
 */
package fr.ign.cogit.geographlab.cheminements;

import java.util.Calendar;
import java.util.List;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.FloydWarshallShortestPaths;

/**
 * @author Eric Mermet
 *
 */
public class FWSP extends Thread {
	
	public Thread thread;
	
	private Carte carte;
	
	private FloydWarshallShortestPaths<Noeud, Arc> chemins;
	
	private List<GraphPath<Noeud,Arc>> listeDeChemins;
	
	private int nbOperation;
	
	public FWSP(Carte carte){
		
		this.carte = carte;
		
		this.carte.getTousLesPCC().clear();
		
		this.thread = new Thread(this);
		
	}
	
	@Override
	public void run() {
		
		System.out.println("Debut FWSP");
		
		this.nbOperation = this.carte.getGraphe().getNombreNoeuds()*(this.carte.getGraphe().getNombreNoeuds()-1);
		
		Double valeurTemp = new Double(0.0);
		
		Calendar c1 = Calendar.getInstance();
		long debut = c1.getTimeInMillis();
		
		this.carte.parent.parent.statusBar.mainProgressBar.setIndeterminate(true);
		this.chemins = new FloydWarshallShortestPaths<Noeud, Arc>(this.carte.getGraphe().asUndirectedView());
		
		decompileChemins();
		
		System.out.println("Nombre de chemins : " + this.chemins.getShortestPathsCount());
		
		Calendar c2 = Calendar.getInstance();
		
		long tempsExecution = c2.getTimeInMillis() - debut;
		System.out.println("Temps d'execution (ms):" + tempsExecution);
		
		System.out.println("Fin FWSP");
		
//		System.out.println(this.carte.getTousLesPCC().toString());
		
		System.gc();
		
		this.carte.getGraphe().setGrapheChange();
	}
	
	private void decompileChemins() {
		
		Chemin nouveauCheminLocal;
		GraphPath<Noeud, Arc> unChemin;
		int nbNoeudCalcules = 0;
		
		this.carte.parent.parent.statusBar.mainProgressBar.setIndeterminate(false);
		
		for (Noeud iterNoeud : this.carte.getGraphe().getNoeuds()) {
			for (Noeud iterNoeud2 : this.carte.getGraphe().getNoeuds() ) {
				
				if( iterNoeud != iterNoeud2 ) {
					
					unChemin = this.chemins.getShortestPath(iterNoeud, iterNoeud2);
					
//					for (GraphPath<Noeud, Arc> iterChemin: this.listeDeChemins) {
						nouveauCheminLocal = new Chemin(this.carte.getGraphe(), 1.0, unChemin.getWeight(), Constantes.typePCC);
						nouveauCheminLocal.setEdgeList(unChemin.getEdgeList());
						OD nouvelleOD = new OD(unChemin.getStartVertex(), unChemin.getEndVertex());
						nouveauCheminLocal.setOd(nouvelleOD);
						
						System.out.println(nbNoeudCalcules + " -  " + unChemin.getStartVertex().getNom() + " -> " + unChemin.getEndVertex().getNom() + " : " + unChemin.getWeight()); //+ " | " + unChemin.getEdgeList().toString() );
						
						this.carte.getTousLesPCC().put(new Integer(nouveauCheminLocal.hashCode()), nouveauCheminLocal);
						this.carte.getEspace().getEspaceDeDef().put(nouvelleOD.hashCode(), nouvelleOD);
//					}
					this.carte.parent.parent.statusBar.changeProgressBar(++nbNoeudCalcules, this.nbOperation);
					
				}
			}
		}
		this.carte.getGraphe().setGrapheChange();
	}
	
}
