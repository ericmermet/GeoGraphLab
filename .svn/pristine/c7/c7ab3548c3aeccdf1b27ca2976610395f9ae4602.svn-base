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
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.PanelMainDraw;

import org.jgrapht.alg.EulerianCircuit;
import org.jgrapht.graph.AsUndirectedGraph;

/**
 * @author mermet
 *
 */
public class CheminEulerien extends Chemin {
	
	PanelMainDraw p;
	List<Noeud> noeudsDuChemin;
	
	public CheminEulerien(PanelMainDraw p) {
		
		super(p.carte.getGraphe(), 1.0, 1);
		
		this.p = p;
		
		AsUndirectedGraph<Noeud, Arc> gUndirected = new AsUndirectedGraph<Noeud, Arc>(this.g);
		
		if(EulerianCircuit.isEulerian(gUndirected) ) {
			this.noeudsDuChemin = EulerianCircuit.getEulerianCircuitVertices(gUndirected);
			if( this.noeudsDuChemin != null )
				setSelected();
		}
	}
	
	public void setSelected() {
		
		Noeud noeudPrecedent = null;
		
		for (Noeud iterNoeud : this.noeudsDuChemin) {
			iterNoeud.setSelected(true);
			System.out.println("select noeud " + iterNoeud.getNom());
			Arc a = this.g.getEdge(noeudPrecedent, iterNoeud);
			Arc b = this.g.getEdge(iterNoeud, noeudPrecedent);
			if( a != null) {
				a.setSelected(true); 
				System.out.println("select arc " + a.getNom());
			}
			if( b != null) {
				b.setSelected(true); 
				System.out.println("select arc " + b.getNom());
			}
			noeudPrecedent = iterNoeud;
		}
		this.p.repaint();
	}
	
}