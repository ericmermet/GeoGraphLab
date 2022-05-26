/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2014
 *
 * SharedPlace.java in fr.ign.cogit.geographlab.thread
 * 
 */
package fr.ign.cogit.geographlab.thread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.jgrapht.traverse.ClosestFirstIterator;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;

/**
 * SharedPlace class is common shared place where Producer Thread Pool places its outputs
 * and Consumer Thread Pool fetch those outputs and consume them.
 *
 */

public class SharedPlace {

	private Thread mainThread;
	
	private String text;
	
	private ClosestFirstIterator<Noeud, Arc> spanningTree;
	private List<Arc> listeArcAvecDoublon = new ArrayList<Arc>();
	HashSet<Arc> listeArcsPourUnArbreSansDoublon = new HashSet<Arc>();
//	private List<Arc> listeNoeudsAvecDoublon = new ArrayList<Arc>();
//	HashSet<Arc> listeNoeudsPourUnArbreSansDoublon = new HashSet<Arc>();
	private Carte carte;
	
	/**
	 * 
	 */
	public SharedPlace(Thread t) {
		this.mainThread = t;
	}
	
	private boolean isPoolFinish = false;

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void remove() {
		this.text = null;
		this.spanningTree = null;
		this.listeArcsPourUnArbreSansDoublon.clear();
	}

	public boolean isEmpty() {
		return (this.spanningTree == null) ? true : false;
	}

	/**
	 * @return the spanningTree
	 */
	public ClosestFirstIterator<Noeud, Arc> getSpanningTree() {
		return this.spanningTree;
	}

	/**
	 * @param spanningTree the spanningTree to set
	 */
	public void setSpanningTree(ClosestFirstIterator<Noeud, Arc> spanningTree) {
		this.spanningTree = spanningTree;
	}

	/**
	 * @return the listeAvecDoublon
	 */
	public List<Arc> getListeArcsAvecDoublon() {
		return this.listeArcAvecDoublon;
	}

	/**
	 * @param listeAvecDoublon the listeAvecDoublon to set
	 */
	public void setListeArcsAvecDoublon(List<Arc> listeAvecDoublon) {
		this.listeArcAvecDoublon = listeAvecDoublon;
	}

	/**
	 * @return the carte
	 */
	public Carte getCarte() {
		return this.carte;
	}

	/**
	 * @param carte the carte to set
	 */
	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	/**
	 * @return the isPoolFinish
	 */
	public boolean isPoolFinish() {
		return this.isPoolFinish;
	}

	/**
	 * @param isPoolFinish the isPoolFinish to set
	 */
	public void setPoolFinish(boolean isPoolFinish) {
		this.isPoolFinish = isPoolFinish;
	}

	/**
	 * @return the mainThread
	 */
	public Thread getMainThread() {
		return this.mainThread;
	}

	/**
	 * @param mainThread the mainThread to set
	 */
	public void setMainThread(Thread mainThread) {
		this.mainThread = mainThread;
	}
	
}