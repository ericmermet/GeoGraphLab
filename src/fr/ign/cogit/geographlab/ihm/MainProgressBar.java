/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm;

import java.awt.Component;

import javax.swing.JProgressBar;

public class MainProgressBar extends JProgressBar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MainProgressBar(Component parent) {
		super(HORIZONTAL, 0, 100);
//		initialize();
	}
	
	private void initialize() {
//		int i;
	}
	
	public void setValue(int compteur, int nbOp) {
		int val = compteur * 100 / nbOp;
		setValue(val);
		setStringPainted(true);
		setString(val + " %");
	}
}
