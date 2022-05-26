/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.graphe.event;

import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.graphe.listeners.NoeudListener;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;

public class NoeudEvent implements NoeudListener {
	
	Graphe g = null;
	Noeud n = null;
	NoeudGraphique ng = null;
	
	public NoeudEvent(Noeud nt, NoeudGraphique ng) {
		this.n = nt;
		this.ng = ng;
	}
	
	public void positionChange() {
		this.ng.setPosition(this.n.getPosition());
	}
	
	public boolean selectionChange() {
		this.ng.setSelected(this.n.isSelected());
		if( this.n.isSelected() == false ){
			this.ng.setSelectedOrigine(false);
			this.ng.setSelectedDestination(false);
		}
		return true;
	}
	
	public void nomChange() {
		this.ng.setNom(this.n.getNom());
	}
	
}
