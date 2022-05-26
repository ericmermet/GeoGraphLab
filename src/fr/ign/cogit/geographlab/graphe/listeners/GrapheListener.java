/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.graphe.listeners;

import java.util.EventListener;

public interface GrapheListener extends EventListener {
	// void grapheChange();
	
	boolean grapheChange();
	
	boolean poidsArcsChange();
}