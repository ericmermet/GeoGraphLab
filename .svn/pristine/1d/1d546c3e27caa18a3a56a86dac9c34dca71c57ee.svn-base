/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * ColorIcon.java in fr.ign.cogit.geographlab.ihm.exemples
 * 
 */
package fr.ign.cogit.geographlab.ihm.customdockingframes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class ColorIcon implements Icon{
	private Color color;
	
	public ColorIcon( Color color ){
		this.color = color;
	}
	
	public int getIconHeight(){
		return 16;
	}
	public int getIconWidth(){
		return 16;
	}
	
	public void paintIcon( Component c, Graphics g, int x, int y ){
		g.setColor( color );
		g.fillOval( x+3, y+3, 10, 10 );
	}
}