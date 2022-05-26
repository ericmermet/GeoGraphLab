/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * ColorDockable.java in fr.ign.cogit.geographlab.ihm.exemples
 * 
 */
package fr.ign.cogit.geographlab.ihm.customdockingframes;

import java.awt.Color;
import bibliothek.gui.dock.DefaultDockable;

public class ColorDockable extends DefaultDockable{
	
	public ColorDockable( String title, Color color, float brightness ){
		setTitleText( title );
		
		if( brightness != 1.0 ){
			float[] hsb = Color.RGBtoHSB( color.getRed(), color.getGreen(), color.getBlue(), null );
			
			hsb[1] = Math.min( 1.0f, hsb[1] / brightness );
			hsb[2] = Math.min( 1.0f, hsb[2] * brightness );
			
			color = Color.getHSBColor( hsb[0], hsb[1], hsb[2] );
		}
		setTitleIcon( new ColorIcon( color ) );
	}
	
}