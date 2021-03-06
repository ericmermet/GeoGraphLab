/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.exploration.controlers;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.PanelMainDraw;

public class CarteControleur {
	
	private Carte modeleCarte = null;
	
	public PanelMainDraw mainPanel = null;
	
	public CarteControleur(MainWindow parent, Carte modeleCarte) {
		this.modeleCarte = modeleCarte;
		
		this.mainPanel = new PanelMainDraw(parent, "", 0);
	}
}
