/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.factories;

import fr.ign.cogit.geographlab.graphe.Noeud;

import java.util.HashMap;

public class GestionnaireDeNoeuds {
	
	private static int nbNoeuds = 0;
	private static HashMap<Integer, Noeud> idsNoeuds = new HashMap<Integer, Noeud>();
	
	public static int getID(Noeud n) {
		idsNoeuds.put(new Integer(nbNoeuds), n);
		nbNoeuds++;
		return nbNoeuds;
	}
	
	public static HashMap<Integer, Noeud> getIdsNoeuds() {
		return idsNoeuds;
	}
	
}
