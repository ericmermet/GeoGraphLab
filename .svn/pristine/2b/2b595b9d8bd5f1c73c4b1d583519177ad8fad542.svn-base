/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.cheminements;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.jgrapht.GraphPath;


public class Chemin implements GraphPath<Noeud, Arc> {
	
	private String nom;
	protected OD od;
	protected Graphe g;
	protected boolean dirige = false;
	private double ponderation = 1.0;
	private double longueurChemin;
	private boolean selected = false;;
	private List<Noeud> listeNoeuds;
	private List<Arc> listeArcs;
	
	private int typeDeChemin;
	
	public Noeud origine, destination;
	
	public Chemin(Graphe g, double ponderation, int type) {
		this.g = g;
		this.ponderation = ponderation;
		setTypeDeChemin(type);
		this.listeNoeuds = new ArrayList<Noeud>();
		this.listeArcs = new ArrayList<Arc>();
	}
	
	public Chemin(Graphe g, double ponderation, double longueurChemin, int type) {
		this(g, ponderation, type);
		this.longueurChemin = longueurChemin;
	}
	
	public Chemin(Graphe g, OD od, double ponderation, int type) {
		this(g, ponderation, type);
		setNom(new String(od.getNom()));
		this.od = od;
	}
	
	public Chemin(Graphe g, OD od, double ponderation, double longueur, int type) {
		this(g, od, ponderation, type);
		setNom(new String(od.getNom()));
		this.od = od;
		this.longueurChemin = longueur;
	}
	
	public Chemin(Graphe g, GraphPath<Noeud, Arc> path, OD od) {
		this.g = g;
		this.od = od;
		this.listeArcs = path.getEdgeList();
		this.listeNoeuds = getPathVertexList();
		this.ponderation = 1.0;
		this.longueurChemin = path.getWeight();
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setOd(OD od) {
		this.od = od;
		this.origine = od.getOrigine();
		this.destination = od.getDestination();
		setNom(new String(od.getNom()));
	}
	
	public OD getOd() {
		return this.od;
	}
	
	public void setTypeDeChemin(int typeDeChemin) {
		this.typeDeChemin = typeDeChemin;
	}
	
	public int getTypeDeChemin() {
		return this.typeDeChemin;
	}
	
	public Noeud getStartVertex() {
		return this.od.getOrigine();
	}
	
	public Noeud getEndVertex() {
		return this.od.getDestination();
	}
	
	public Graphe getGraph() {
		return this.g;
	}
	
	public List<Arc> getEdgeList() {
		return getListeArcs();
	}
	
	public void setEdgeList(List<Arc> l) {
		setListeArcs(l);
	}
	
	public List<Noeud> getVerticesList() {
		return getListeNoeuds();
	}
	
	public void setVerticesList(List<Noeud> l) {
		setListeNoeuds(l);
	}
	
	public double getWeight() {
		return this.ponderation;
	}
	
	public void setLongueur(double longueur) {
		this.longueurChemin = longueur;
	}
	
	public double getLongueur() {
		return this.longueurChemin;
	}
	
	private void setListeArcs(List<Arc> l) {
		if( l != null)
			this.listeArcs.addAll(l);
	}
	
	private List<Arc> getListeArcs() {
		return this.listeArcs;
	}
	
	private void setListeNoeuds(List<Noeud> l) {
		this.listeNoeuds.addAll(l);
	}
	
	public List<Noeud> getListeNoeuds() {
		return this.listeNoeuds;
	}
	
	public List<Noeud> getPathVertexList() {
		
		LinkedHashSet<Noeud> listeConstruite = new LinkedHashSet<Noeud>();
		
		Noeud noeudOrigine = this.listeArcs.get(0).getSource();
		if (this.listeArcs.size() > 1) {
			if (noeudOrigine == this.listeArcs.get(1).getTarget() | noeudOrigine == this.listeArcs.get(1).getSource())
				noeudOrigine = this.listeArcs.get(0).getTarget();
		}
		
		listeConstruite.add(noeudOrigine);
		this.origine = noeudOrigine;
		for (Iterator<Arc> i = this.listeArcs.iterator(); i.hasNext();) {
			Arc e = i.next();
			listeConstruite.add(e.getSource());
			listeConstruite.add(e.getTarget());
		}
		
		List<Noeud> vertexList = new ArrayList<Noeud>(listeConstruite);
		this.destination = vertexList.get(vertexList.size() - 1);
		this.listeNoeuds = vertexList;
		
		return vertexList;
	}
	
	public boolean estPresente(ObjetGraphique objetGraphiqueSelectionne) {
		
		for (Arc iterArc : getEdgeList()) {
			if (iterArc.getArcGraphique() == objetGraphiqueSelectionne) {
				return true;
			}
		}
		
		for (Noeud iterNoeud : getListeNoeuds()) {
			if (iterNoeud.getNoeudGraphique() == objetGraphiqueSelectionne)
				return true;
		}
		
		return false;
	}
	
	public void setSelected(boolean selected) {
		
		for (Noeud iterNoeud : getListeNoeuds()) {
			iterNoeud.setSelected(selected);
		}
		
		for (Arc iterArc : getEdgeList()) {
			iterArc.setSelected(selected);
		}
		
		this.od.getOrigine().getNoeudGraphique().setSelectedOrigine(true);
		this.od.getDestination().getNoeudGraphique().setSelectedDestination(true);
		
		this.selected = selected;
	}
	
	public boolean getSelected() {
		return this.selected;
	}
	
	public void incrStimuli(String indicateur) {
		for (Noeud iterNoeud : getListeNoeuds()) {
			int tempValeurNoeud = iterNoeud.getValeurPourIndicateur(indicateur).intValue() + 1;
			iterNoeud.setIndicateurValeur(indicateur, tempValeurNoeud);
		}
		
		for (Arc iterArc : getListeArcs()) {
			int tempValeurArc = iterArc.getValeurPourIndicateur(indicateur).intValue() + 1;
			iterArc.setIndicateurValeur(indicateur, tempValeurArc);
		}
	}
	
	@Override
	public String toString() {
		String returnString = new String("");
		for (Noeud iterNoeud : getListeNoeuds())
			returnString += iterNoeud.getNom() + " ";
		
		return returnString;
	}
	
	@Override
	public int hashCode() {
		// return 10000*this.listeArcs.hashCode() +
		// this.listeNoeuds.hashCode()+(int)getWeight();
//		return this.origine.hashCode() + this.destination.hashCode();
		return this.od.hashCode();
	}
	
}