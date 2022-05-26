/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.exploration;

import java.util.ArrayList;

public class GestionnaireOperations {
	
	private ArrayList<BlocOperation> mesOperations;
	
	public GestionnaireOperations() {
		this.mesOperations = new ArrayList<BlocOperation>();
	}
	
	public void ajouterUneOperation(BlocOperation operation) {
		this.mesOperations.add(operation);
	}
	
	public void retirerUneOperation(BlocOperation operation) {
		this.mesOperations.remove(operation);
	}
	
	public ArrayList<BlocOperation> getOperations() {
		return this.mesOperations;
	}
	
}