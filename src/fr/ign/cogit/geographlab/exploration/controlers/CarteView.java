/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.exploration.controlers;

public abstract class CarteView {
	private CarteControleur controleur = null;
	
	public CarteView(CarteControleur controller) {
		super();
		
		this.controleur = controller;
	}
	
	public final CarteControleur getController() {
		return this.controleur;
	}
	
	public abstract void display();
	
	public abstract void close();
}
