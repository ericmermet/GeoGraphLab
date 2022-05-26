/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.visu;

import fr.ign.cogit.geographlab.cheminements.Chemin;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.graphe.Graphe;

public class CheminGraphique {
	
	public String nom;
	public OD od;
	public Chemin cheminTopologique;
	public Graphe g;
	public double ponderation;
	public int typeDeChemin;
	
	public CheminGraphique(Chemin cheminTopologique) {
		this.cheminTopologique = cheminTopologique;
		this.nom = cheminTopologique.getNom();
		this.od = cheminTopologique.getOd();
		this.g = cheminTopologique.getGraph();
		this.ponderation = cheminTopologique.getWeight();
		this.typeDeChemin = cheminTopologique.getTypeDeChemin();
	}
	
	public CheminGraphique(OD od) {
		this.nom = od.getNom();
		this.od = od;
	}
	
}
