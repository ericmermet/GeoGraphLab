/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.graphe;

import java.util.Hashtable;

public class ObjetTopologique {
	
	private String nom;
	private Hashtable<String, Double> indicateursValeurs = new Hashtable<String, Double>();
	private boolean isSelected = false;
	private int iD;
	
	public ObjetTopologique() {
		setIndicateurValeur("none", 0.0);
	}
	
	public ObjetTopologique(String nom) {
		this.setNom(nom);
		setIndicateurValeur("none", 0.0);
	}
	
	public ObjetTopologique(String nom, String nomIndicateur) {
		this.nom = nom;
		this.setIndicateurValeur(nomIndicateur, 0.0);
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public void setIndicateurValeur(String nom, double valeur) {
		this.indicateursValeurs.put(nom, new Double(valeur));
	}
	
	public Double getValeurPourIndicateur(String nomIndicateur) {
		return this.indicateursValeurs.get(nomIndicateur);
	}

	public void setID(int iD) {
		this.iD = iD;
	}
	
	public int getID() {
		return this.iD;
	}
	
	/**
	 * @param n
	 * @return
	 */
	public boolean equals(ObjetTopologique o) {
		if( this.iD == o.iD )
			return true;
		return false;
	}
}
