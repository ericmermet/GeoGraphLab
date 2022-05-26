/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.topologie;

import fr.ign.cogit.geographlab.graphe.Noeud;

public class Triangle {
	
	Noeud noeud1, noeud2, noeud3;
	String nomDutriangle;
	
	public Triangle(Noeud n1, Noeud n2, Noeud n3) {
		this.noeud1 = n1;
		this.noeud2 = n2;
		this.noeud3 = n3;
		this.nomDutriangle = this.noeud1.getNom() + " " + this.noeud2.getNom() + " " + this.noeud3.getNom();
	}
	
	public String getNomDutriangle() {
		return this.nomDutriangle;
	}
	
	public void setNomDutriangle(String nomDutriangle) {
		this.nomDutriangle = nomDutriangle;
	}
	
	@Override
	public String toString() {
		return this.nomDutriangle;
		
	}
	
	public int hashcode() {
		return 10 * this.noeud1.hashCode() + this.noeud2.hashCode() + this.noeud3.hashCode();
	}
	
}