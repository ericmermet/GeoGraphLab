/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.factories;

import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.graphe.Noeud;

import java.util.HashMap;


public class GestionnaireODs {
	
	private static int nbODs = 1;
	private static HashMap<Integer, OD> idsODs = new HashMap<Integer, OD>();
	
	private static HashMap<Noeud, HashMap<Noeud, OD>> doubleOD = new HashMap<Noeud, HashMap<Noeud,OD>>();
	
//	public static int getID(OD od) {
//		// nbODs++;
//		// Integer i = new Integer(od.getOrigine().getID()*100000 +
//		// od.getDestination().getID());
//		// idsODs.put(i, od);
//		// return i.intValue();
//		
//		int i = 0;
//		int j = 0;
//		
//		if (od.getOrigine().getID() > od.getDestination().getID()) { // i<j
//			i = od.getOrigine().getID();
//			j = od.getDestination().getID();
//		} else { // j<i
//			j = od.getDestination().getID();
//			i = od.getOrigine().getID();
//		}
//		
//		int id = i * (i + 1) / 2 + j;
//		idsODs.put(new Integer(id), od);
//		
//		return id;
//		
//	}
	
	public static void add(OD od) {
		
		OD reverseOD = new OD(od.getDestination(), od.getOrigine());
		reverseOD.setPonderation(od.getPonderation());
		
		idsODs.put(new Integer(od.hashCode()), od);
		idsODs.put(new Integer(-od.hashCode()), reverseOD);
		
		HashMap<Noeud, OD> destination = new HashMap<Noeud, OD>();
		destination.put(od.getDestination(), od);
		
		doubleOD.put(od.getOrigine(), destination );
		
		doubleOD.get(od.getOrigine()).get(od.getDestination());
		
	}
	
	public static OD get(Integer hashCode) {
		return idsODs.get(hashCode);
	}
	
	public static int getIDUnique() {		
		
		return nbODs++;
	}
	
}