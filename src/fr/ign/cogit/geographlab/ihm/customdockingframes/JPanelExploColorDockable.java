/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * JPanelExploColorDockable.java in fr.ign.cogit.geographlab.ihm.customdockingframes
 * 
 */
package fr.ign.cogit.geographlab.ihm.customdockingframes;

/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * JPanelColorDockable.java in fr.ign.cogit.geographlab.ihm.customdockingframes
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;

import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.exploration.PanelExplo;

/**
 * @author eric
 *
 */
public class JPanelExploColorDockable extends ColorDockable {

	private PanelExplo panel;
	
	public JPanelExploColorDockable( MainWindow mainWindow, String title, Color color, float brightness ){
		super( title, Color.WHITE, 1.0f );
		this.panel = new PanelExplo(mainWindow, title);
		add(this.panel.toolBarExplo, BorderLayout.BEFORE_FIRST_LINE);
		add(panel);
	}
	
	public PanelExplo getJPanel() {
		return this.panel;
	}
	
}
