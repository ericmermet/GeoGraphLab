/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2010
 *
 * CheminEulerien.java in cheminements
 * 
 */
package fr.ign.cogit.geographlab.cheminements;

import java.util.List;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.PanelMainDraw;

import org.jgrapht.alg.EulerianCircuit;
import org.jgrapht.alg.HamiltonianCycle;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.AsWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * @author mermet
 *
 */
public class CheminHamiltonien extends Chemin{
	
	PanelMainDraw p;
	List<Noeud> noeudsDuChemin;
	Graphe g;
	
	public CheminHamiltonien(PanelMainDraw p)  {
		
		super(p.carte.getGraphe(), 1.0, 1);
		
		this.p = p;
		
		this.g = p.carte.getGraphe();
		
		this.noeudsDuChemin = HamiltonianCycle.getApproximateOptimalForCompleteGraph(g.asSimpleWeightedGraph());
		if( this.noeudsDuChemin != null)
			setSelected();
		else
			System.out.println("Le graphe n'est pas hamiltonien");
			
	}
	
	public void setSelected() {
		
		Noeud noeudPrecedent = null;
		
		for (Noeud iterNoeud : this.noeudsDuChemin) {
			iterNoeud.setSelected(true);
			Arc a = this.g.getEdge(noeudPrecedent, iterNoeud);
			if( a != null)
				a.setSelected(true);
			noeudPrecedent = iterNoeud;
		}
		this.p.repaint();
	}
	
}