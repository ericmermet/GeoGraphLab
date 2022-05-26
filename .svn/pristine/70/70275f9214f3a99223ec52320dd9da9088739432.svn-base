/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2014
 *
 * Consumer.java in fr.ign.cogit.geographlab.thread
 * 
 */
package fr.ign.cogit.geographlab.thread;

/**
 * Consumer class is Consumer Thread Pool, who will fetch shared <code>String</code> objects from 
 * SharedPlace class and consume them.
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

public class Consumer {

	private final int COUNT;
	SharedPlace sharedPlace;
	int workDone = 0;
	
	StatusBar sb;
	int totalCptTree = 0;
	int nbOperation;
	String nomIndicateurCarte;
	
	public Consumer(SharedPlace sharedObj, int count, StatusBar sb, String nomIndicateur) {
		this.sharedPlace = sharedObj;
		this.COUNT = count;
		this.sb = sb;
		this.nomIndicateurCarte = nomIndicateur;
	}

	public void startConsumption() {
		ExecutorService executorService = Executors.newFixedThreadPool(ConstantesApplication.availableCores);
		
		for (int i = 0; i < this.COUNT; i++) {
			executorService.execute(new WorkerConsumer());
		}
		executorService.shutdown();
	}
	
	public void startConsumptionSpanningTrees(Object parent, Set<Noeud> setNoeuds) {
		ExecutorService executorService = Executors.newFixedThreadPool(ConstantesApplication.availableCores);
		this.nbOperation = setNoeuds.size();
		for (int i = 0; i < setNoeuds.size(); i++) {
			executorService.execute(new WorkerConsumer());
		}
		executorService.shutdown();
//		parent.notifyAll();
	}

	private class WorkerConsumer implements Runnable {

		private List<Arc> edgeList;
//		HashSet<Arc> listeArcsPourUnArbreSansDoublon;
		
		public void run() {

			synchronized (Consumer.this.sharedPlace) {

				while (Consumer.this.sharedPlace.isEmpty()) {
					try {
						Consumer.this.sharedPlace.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				System.out.println("consum tree");
//				this.listeArcsPourUnArbreSansDoublon = new HashSet<Arc>();
				while (Consumer.this.sharedPlace.getSpanningTree().hasNext()) {
					Noeud noeudTemp = Consumer.this.sharedPlace.getSpanningTree().next();
					
					createEdgeList(Consumer.this.sharedPlace.getCarte(), Consumer.this.sharedPlace.getSpanningTree(), noeudTemp);
					Consumer.this.sharedPlace.getListeArcsAvecDoublon().addAll(this.edgeList);
					Consumer.this.sharedPlace.listeArcsPourUnArbreSansDoublon.addAll(this.edgeList);
					
				}
				
				Consumer.this.sharedPlace.remove();
				Consumer.this.workDone++;
				System.out.println("WORK DONE=" + Consumer.this.workDone + " ");
				Consumer.this.sharedPlace.notifyAll();
				
				Consumer.this.sb.changeProgressBar(Consumer.this.totalCptTree++, Consumer.this.nbOperation);
				System.out.println(Consumer.this.totalCptTree + " / " + Consumer.this.nbOperation);
				if(Consumer.this.totalCptTree >= Consumer.this.nbOperation) {
					synchronized (Consumer.this.sharedPlace.getMainThread()) {
						Consumer.this.sb.changeProgressBar(Consumer.this.nbOperation, Consumer.this.nbOperation);
						Consumer.this.sharedPlace.setPoolFinish(true);
						Consumer.this.sharedPlace.getMainThread().notifyAll();
					}
				}
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