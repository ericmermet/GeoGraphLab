/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2014
 *
 * SpanningTreeCentrality.java in fr.ign.cogit.geographlab.algo.traverse
 * 
 */

package fr.ign.cogit.geographlab.algo.traverse;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.ArcFactory;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.lang.Messages;
import fr.ign.cogit.geographlab.thread.Consumer;
import fr.ign.cogit.geographlab.thread.Producer;
import fr.ign.cogit.geographlab.thread.SharedPlace;
import fr.ign.cogit.geographlab.util.Timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.ClosestFirstIterator;


/**
 * @author eric
 *
 */
public class SpanningTreeCentralityMultiThreads extends Thread {
	
	private Carte carte;
	private Carte nouvelleCartePourNouvelleCouche;
	
	private List<Arc> edgeList;
	
	public Thread thread;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	SimpleGraph<Noeud, Arc> testGraphe = new SimpleGraph<Noeud, Arc>(new ArcFactory());
	
	/**
	 * 
	 */
	public SpanningTreeCentralityMultiThreads(Carte carte) {
		this.carte = carte;
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "SpanningTreeCentrality", "SpanningTreeCentrality_" + new Date().getTime()); //$NON-NLS-1$ //$NON-NLS-2$
		this.thread = new Thread(this);	
		
		//Raz arc
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), 0.0);
		}
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), 0.0);
		}
	}
	
	@Override
	public void run() {
		
		Timer.tic();
		
		//		int nbOperation = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds().size();
		//		int cpt = 0;
		
		//		ThreadPool threadsPool = new ThreadPool(ConstantesApplication.availableCores);
		
		SharedPlace sharedObj = new SharedPlace(this.thread);
		Producer producer = new Producer(sharedObj, 666, this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar);
		Consumer consumer = new Consumer(sharedObj, 666, this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar, this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant());
		producer.startProductionSpanningTrees(this.nouvelleCartePourNouvelleCouche, this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds());
		consumer.startConsumptionSpanningTrees(this, this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds());
		
		while( !sharedObj.isPoolFinish() ){
			//			System.out.println("waiting...");
//			System.out.println(sharedObj.isPoolFinish());
			synchronized (this.thread) {
				try {
					this.thread.wait();
					break;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		producer = null;
		consumer = null;
		System.gc();
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.mainProgressBar.setIndeterminate(true);
		
		int nbNoeuds = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds().size();
		int maxPaths = (nbNoeuds * (nbNoeuds -1));
		
		for (Arc arc : sharedObj.getListeArcsAvecDoublon()) {
			double valeurTempArc = arc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()).doubleValue() + 1.0;
			arc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTempArc);
			
			double valeurTempNoeudSource = arc.getSource().getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()).doubleValue() + 1.0;
			arc.getSource().setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTempNoeudSource/maxPaths);
			double valeurTempNoeudTarget = arc.getTarget().getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()).doubleValue() + 1.0;
			arc.getTarget().setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTempNoeudTarget);
			
			this.max = Math.max(valeurTempArc, this.max);
			this.max = Math.max(valeurTempNoeudSource, this.max);
			this.max = Math.max(valeurTempNoeudTarget, this.max);
			this.min = Math.min(valeurTempArc, 0);
			this.min = Math.min(valeurTempNoeudSource, 0);
			this.min = Math.min(valeurTempNoeudTarget, 0);
			
//			if( j%1000 == 0 )
//				System.out.println(j+"/"+nbArcs);
			
		}
		
		
		sharedObj = null;
		System.gc();
		
		// Mise a jour de la legende et de liens couleurs - legende
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setEchelle(this.min, this.max, this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().getIntervalles());
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
		this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String(Messages.getString("CentraliteIntermediaire.5")); //$NON-NLS-1$
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		// this.carte.repaint();
		System.out.println(Messages.getString("CentraliteIntermediaire.4") + Timer.tac()); //$NON-NLS-1$
		System.out.println("Finish Spanning tree");
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.mainProgressBar.setIndeterminate(false);
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.mainProgressBar.setString("Ok");
		this.carte.repaint();
		
	}
	
	private void createEdgeList(ClosestFirstIterator<Noeud, Arc> iter, Noeud noeudDestination) {
		this.edgeList = new ArrayList<Arc>();
		this.edgeList.clear();
		
		while (true) {
			Arc edge = iter.getSpanningTreeEdge(noeudDestination);
			
			if (edge == null) {
				break;
			}
			this.testGraphe.addVertex(edge.getSource());
			this.testGraphe.addVertex(edge.getTarget());
			this.testGraphe.addEdge(edge.getSource(), edge.getTarget());
			
			this.edgeList.add(edge);
			noeudDestination = Graphs.getOppositeVertex(this.carte.getGraphe(), edge, noeudDestination);
		}
		Collections.reverse(this.edgeList);
	}

}