/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.maths;

import java.awt.Point;
import java.util.Set;

import fr.ign.cogit.geographlab.visu.NoeudGraphique;


public class Barycentre {
	
	Point barycentre = new Point(0, 0);
	
	public Barycentre(Set<NoeudGraphique> mesNoeuds) {
		
		int moyX = 0, moyY = 0;
		
		if (mesNoeuds.size() > 0) {
			
			for (NoeudGraphique noeudGraphique : mesNoeuds) {
				moyX += noeudGraphique.getXPosition();
				moyY += noeudGraphique.getYPosition();
			}
			
			moyX /= mesNoeuds.size();
			moyY /= mesNoeuds.size();
			
			this.barycentre.x = moyX;
			this.barycentre.y = moyY;
			
		}
		
	}
	
	public Point getBarycentre() {
		return this.barycentre;
	}
}
