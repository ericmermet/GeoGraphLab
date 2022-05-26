/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2014
 *
 * Producer.java in fr.ign.cogit.geographlab.thread
 * 
 */
package fr.ign.cogit.geographlab.thread;

/**
 * Producer class is actually Producer Thread Pool, which would produce some output
 * <code>String </code> and place this <code>String</code> to a Shared Place.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jgrapht.Graphs;
import org.jgrapht.traverse.ClosestFirstIterator;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.bars.StatusBar;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;

public class Producer {

	SharedPlace sharedPlace;
	private final int COUNT;
	StatusBar sb;
	int totalCptTree = 0;
	int nbOperation;

	public Producer(SharedPlace sharedObj, int count, StatusBar sb) {
//		this.sharedPlace.setMainThread(t);
		this.sharedPlace = sharedObj;
		this.COUNT = count;
		this.sb = sb;
	}

	public void startProduction() {

		ExecutorService executorService = Executors.newFixedThreadPool(100);
		for (int i = 1; i <= this.COUNT; i++) {
			executorService.execute(new Task(i));
		}
		executorService.shutdown();
	}
	
	public void startProductionSpanningTrees(Carte carte, Set<Noeud> setNoeuds) {

//		List<Noeud> list = new ArrayList<Noeud>(setNoeuds);
		
		this.sharedPlace.setCarte(carte);
		
		ExecutorService executorService = Executors.newFixedThreadPool(ConstantesApplication.availableCores);
		this.nbOperation = setNoeuds.size();
		int i = 0;
		for (Noeud noeud : setNoeuds) {
			executorService.execute(new Task(i++, carte, noeud));
		}
		
		executorService.shutdown();
	}

	private class Task implements Runnable {

		private final int TASKNUM;
		private final Noeud n;
		private final Carte carte;
		
		List<Arc> edgeList;

		public Task(int taskNum) {
			this.TASKNUM = taskNum;
			this.n = null;
			this.carte = null;
		}
		
		public Task(int taskNum, Carte carte, Noeud n) {
			this.TASKNUM = taskNum;
			this.n = n;
			this.carte = carte;
		}

		public void run() {

			synchronized (Producer.this.sharedPlace) {

				while (!Producer.this.sharedPlace.isEmpty()) {
					try {
						Producer.this.sharedPlace.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				ClosestFirstIterator<Noeud, Arc> spanningTree = null;
				
				if( this.carte.variablesDeCarte.afficheGrapheNonDirige == false ){
					spanningTree = new ClosestFirstIterator<Noeud, Arc>(this.carte.getGraphe(), this.n, Double.POSITIVE_INFINITY);
				}else{
					spanningTree = new ClosestFirstIterator<Noeud, Arc>(this.carte.getGraphe().asUndirectedView(), this.n, Double.POSITIVE_INFINITY);
				}
				System.out.println("produce tree "+this.n.getNom());
				
				Producer.this.sharedPlace.setSpanningTree(spanningTree);
				
//				System.out.println("consum tree "+this.n.getNom());
//				while (sharedPlace.getSpanningTree().hasNext()) {
//					Noeud noeudTemp = sharedPlace.getSpanningTree().next();
//					
//					createEdgeList(sharedPlace.getCarte(), sharedPlace.getSpanningTree(), noeudTemp);
//					sharedPlace.getListeAvecDoublon().addAll(this.edgeList);
//				}
				
				
//				sharedPlace.remove();
//				Consumer.this.workDone++;
//				System.out.println("WORK DONE=" + Consumer.this.workDone + " ");
				
//				System.out.println("done with " +this.n.getNom());

				sharedPlace.notifyAll();
			}

		}
		
		private void createEdgeList(Carte carte, ClosestFirstIterator<Noeud, Arc> iter, Noeud noeudDestination) {
			this.edgeList = new ArrayList<Arc>();
			this.edgeList.clear();
			
			while (true) {
				Arc edge = iter.getSpanningTreeEdge(noeudDestination);
				
				if (edge == null) {
					break;
				}
//				this.testGraphe.addVertex(edge.getSource());
//				this.testGraphe.addVertex(edge.getTarget());
//				this.testGraphe.addEdge(edge.getSource(), edge.getTarget());
				
				this.edgeList.add(edge);
				noeudDestination = Graphs.getOppositeVertex(carte.getGraphe(), edge, noeudDestination);
			}
			Collections.reverse(this.edgeList);
		}

	}

}