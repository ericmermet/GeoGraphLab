/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2011
 *
 * Distances.java in algo.maths
 * 
 */
package fr.ign.cogit.geographlab.algo.maths;

import com.vividsolutions.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;

import fr.ign.cogit.geographlab.cheminements.Chemin;
import fr.ign.cogit.geographlab.graphe.Noeud;

/**
 * @author mermet
 *
 */
public class Distances {
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double deuxPoints(Point a, Point b){
		
		return a.distance(b);
	}
	
	/**
	 * 
	 * @param listNoeud nodes list 
	 * @param p a point with a geometry
	 * @return the nearest node of the point p
	 */
	public static Noeud plusProcheVoisin(Set<Noeud> listNoeud, Point p) {
		
		Noeud temp = null;
		double minDistance = Double.MAX_VALUE, tempDistance;
//		Coordinate pTemp;
		
		for (Noeud noeud : listNoeud) {
//			pTemp = new Coordinate(noeud.getXPosition(), noeud.getYPosition());
//			tempDistance = p.getCoordinate().distance(pTemp);
			tempDistance = deuxPoints(noeud.getPosition(), p );
			
			if( tempDistance < minDistance ) {
				temp = noeud;
				minDistance = tempDistance;
//				System.out.println(" dans distance " + p.getX() + " " + p.getY());
//				System.out.println("   noeud coord (" + noeud.getXPosition() + ", " + noeud.getYPosition() +") "+ temp.getNom() + " distance = " + tempDistance);
			}
		}
		return temp;
	}
	
	/**
	 * Computes the euclidian length a linestring specified by a sequence of points, but taking only start and end.
	 *
	 * @param pts the points specifying the linestring
	 * @return the length of the linestring
	 */
	public static double lengthEuclidian(CoordinateSequence pts) {
		// optimized for processing CoordinateSequences
		int n = pts.size();
		if (n <= 1) return 0.0;
		
		Coordinate cStart = pts.getCoordinate(0);
		Coordinate cEnd = pts.getCoordinate(n-1);

		double len = Math.sqrt(	Math.pow(cEnd.y - cStart.y, 2.0) +
								Math.pow(cEnd.x - cStart.x, 2.0)
				);
				
		return len;
	}
	
	
	
	/**
	 * Computes the length in 3D of a linestring specified by a sequence of points.
	 *
	 * @param pts the points specifying the linestring
	 * @return the length of the linestring
	 */
	@SuppressWarnings("boxing")
	public static double length3D(CoordinateSequence pts) {
		// optimized for processing CoordinateSequences
		int n = pts.size();
		if (n <= 1) return 0.0;
		
		double len = 0.0;
		
		for (int i = 0 ; i < n - 1 ; i++) {
			Coordinate c0 = pts.getCoordinate(i);
			Coordinate c1 = pts.getCoordinate(i + 1);
			Double dx = new Double(c0.x - c1.x);
			Double dy = new Double(c0.y - c1.y);
			Double dz = new Double(c0.z - c1.z);
			
			if( !Double.isNaN(dz) )
				len += Math.sqrt(dx * dx + dy * dy + dz * dz);
			else
				len += Math.sqrt(dx * dx + dy * dy);
		}
		return len;
	}
	
	public static double brunetAB(Chemin cheminA, Chemin cheminB) {
		
		double nA = cheminA.getListeNoeuds().size();
		if( nA == 0)
			return 0.0;
		
		List<Noeud> listeA = cheminA.getListeNoeuds();
		listeA.retainAll(cheminB.getListeNoeuds());
		
		double n = listeA.size();
		
		return (nA-n)/nA;
	}
	
	public static double brunetBA(Chemin cheminA, Chemin cheminB) {
		
		double nB = cheminB.getListeNoeuds().size();
		if( nB == 0)
			return 0.0;
		
		List<Noeud> listeB = cheminB.getListeNoeuds();
		listeB.retainAll(cheminA.getListeNoeuds());
		
		double n = listeB.size();
		
		return new Double((nB-n)/nB).doubleValue();
	}
	
	public static double brunet(Chemin cheminA, Chemin cheminB) {
		
		return brunetAB(cheminA, cheminB) + brunetBA(cheminA, cheminB);
	}
	
}