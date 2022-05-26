/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.graphe.event;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.listeners.ArcListener;
import fr.ign.cogit.geographlab.visu.ArcGraphique;

public class ArcEvent implements ArcListener {
	
	Arc a = null;
	ArcGraphique ag = null;
	
	public ArcEvent(Arc a, ArcGraphique ag) {
		this.a = a;
		this.ag = ag;
	}
	
	public boolean selectionChange() {
		this.ag.setSelected(this.a.isSelected());
		// System.out.println("pass");
		return true;
	}
	
	@Override
	public void positionChange() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void nomChange() {
		this.ag.setNom(this.a.getNom());
	}
}
