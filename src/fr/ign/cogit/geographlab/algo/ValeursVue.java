/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo;

// Cette classe n'est plus utilisee

import org.jgrapht.Graphs;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.ArcFactory;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;

public class ValeursVue {
	
	private Graphe grapheAvecValeursDuCalcul;
	
	public ValeursVue(Graphe g) {
		ArcFactory edgeFactory = new ArcFactory();
		this.grapheAvecValeursDuCalcul = new Graphe(edgeFactory, g.getNom() + "_ValeursVue");
		Graphs.addGraph(this.grapheAvecValeursDuCalcul, g);
		// Mise a zero de tous les poids arcs et noeuds
		for (Arc iterArc : this.grapheAvecValeursDuCalcul.getArcs()) {
			setValeurArc(iterArc, 0);
		}
		for (Noeud iterNoeud : this.grapheAvecValeursDuCalcul.getNoeuds()) {
			setValeurNoeud(iterNoeud, 0);
		}
	}
	
	public void setValeurNoeud(Noeud n, double valeur) {
		this.grapheAvecValeursDuCalcul.setPonderationNoeud(n, valeur);
	}
	
	public double getValeurNoeud(Noeud n) {
		return this.grapheAvecValeursDuCalcul.getPoidsNoeud(n);
	}
	
	public double getValeurArc(Arc a) {
		return this.grapheAvecValeursDuCalcul.getPoidsArc(a);
	}
	
	public void setValeurArc(Arc a, double valeur) {
		this.grapheAvecValeursDuCalcul.setPoidsArc(a, valeur);
	}
	
	public Graphe getGrapheValeursDeLaVue() {
		return this.grapheAvecValeursDuCalcul;
	}
	
	public Object[][] getValeursDesNoeudsDeLaVueArray() {
		
		Object[][] tableauDesValeursDesNoeuds = new Object[this.grapheAvecValeursDuCalcul.getNoeuds().size()][2];
		
		int i = 0;
		for (Noeud iterNoeud : this.grapheAvecValeursDuCalcul.getNoeuds()) {
			tableauDesValeursDesNoeuds[i][0] = iterNoeud.getNom();
			tableauDesValeursDesNoeuds[i][1] = new Double(iterNoeud.getPonderation());
			i++;
		}
		
		return tableauDesValeursDesNoeuds;
	}
	
	public Object[][] getValeursDesArcsDeLaVueArray() {
		
		Object[][] tableauDesArcs = new Object[this.grapheAvecValeursDuCalcul.getArcs().size()][2];
		
		int i = 0;
		for (Arc iterArc : this.grapheAvecValeursDuCalcul.getArcs()) {
			tableauDesArcs[i][0] = iterArc.getNom();
			tableauDesArcs[i][1] = new Double(iterArc.getPoids());
			i++;
		}
		
		return tableauDesArcs;
	}
	
	public double[] getValeursDesNoeudsDeLaVueArraySansLesNoms() {
		double[] tableauDesValeursDesNoeuds = new double[this.grapheAvecValeursDuCalcul.getNoeuds().size()];
		
		int i = 0;
		for (Noeud iterNoeud : this.grapheAvecValeursDuCalcul.getNoeuds()) {
			tableauDesValeursDesNoeuds[i] = iterNoeud.getPonderation();
			i++;
		}
		
		return tableauDesValeursDesNoeuds;
	}
	
	public double[] getValeursDesArcsDeLaVueArraySansLesNoms() {
		double[] tableauDesValeursDesArcs = new double[this.grapheAvecValeursDuCalcul.getArcs().size()];
		
		int i = 0;
		for (Noeud iterNoeud : this.grapheAvecValeursDuCalcul.getNoeuds()) {
			tableauDesValeursDesArcs[i] = iterNoeud.getPonderation();
			i++;
		}
		
		return tableauDesValeursDesArcs;
	}
	
}
