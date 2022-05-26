/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * JPanelColorDockable.java in fr.ign.cogit.geographlab.ihm.customdockingframes
 * 
 */
package fr.ign.cogit.geographlab.ihm.customdockingframes;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.PanelMainDraw;

public class JPanelMainDrawColorDockable extends ColorDockable {
	
	private PanelMainDraw panel;
	private MainWindow parent;
	
	FocusListener listener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
			JPanelMainDrawColorDockable.this.parent.panelActif = panel;
			JPanelMainDrawColorDockable.this.parent.panelActif.carte.getGraphe().setGrapheChange();
		}
		@Override
		public void focusLost(FocusEvent e) {
		}
		
	};
	
	public JPanelMainDrawColorDockable( MainWindow mainWindow, String title, int id){
		super( title, Color.WHITE, 1.0f );
		this.parent = mainWindow;
		this.panel = new PanelMainDraw(mainWindow, title, 0);
		add(panel);
		this.panel.addFocusListener(listener);
	}
	
	public JPanelMainDrawColorDockable( MainWindow mainWindow, String title, Color color, float brightness ){
		super( title, color, brightness );
		this.parent = mainWindow;
		this.panel = new PanelMainDraw(mainWindow, title, 0);
		add(panel);
		this.panel.addFocusListener(listener);
	}
	
	public PanelMainDraw getJPanel() {
		return this.panel;
	}
}