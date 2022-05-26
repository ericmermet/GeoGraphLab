/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.exploration.events;

import java.util.EventListener;

public interface CarteListener extends EventListener {
	public void carteChanged(CarteChangeEvent e);
}