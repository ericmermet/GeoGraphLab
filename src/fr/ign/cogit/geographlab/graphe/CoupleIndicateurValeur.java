/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.graphe;

public class CoupleIndicateurValeur {
	
	private String nomIndicateur;
	private Double valeur;
	
	public CoupleIndicateurValeur(String nom, Double valeur) {
		this.setNomIndicateur(nom);
		this.setValeur(valeur);
	}
	
	public void setNomIndicateur(String nomIndicateur) {
		this.nomIndicateur = nomIndicateur;
	}
	
	public String getNomIndicateur() {
		return this.nomIndicateur;
	}
	
	public void setValeur(Double valeur) {
		this.valeur = valeur;
	}
	
	public Double getValeur() {
		return this.valeur;
	}
	
}
