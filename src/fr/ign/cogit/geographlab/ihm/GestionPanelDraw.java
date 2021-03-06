/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm;

import java.util.HashSet;

import bibliothek.gui.Dockable;

import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.ihm.customdockingframes.JPanelMainDrawColorDockable;
import fr.ign.cogit.geographlab.lang.Messages;

public class GestionPanelDraw {
	private HashSet<PanelMainDraw> listePanelMainDraw;
	private MainWindow parent;
	
	public GestionPanelDraw(MainWindow parent) {
		this.parent = parent;
		this.listePanelMainDraw = new HashSet<PanelMainDraw>();
		
		// Creation du premier panel
		addNewPanel();
	}
	
	public void setListePanelMainDraw(HashSet<PanelMainDraw> panelMainDraw) {
		this.listePanelMainDraw = panelMainDraw;
	}
	
	public HashSet<PanelMainDraw> getListePanelMainDraw() {
		return this.listePanelMainDraw;
	}
	
	public void addPanel(PanelMainDraw panel) {
		this.listePanelMainDraw.add(panel);
	}
	
	public PanelMainDraw addNewPanel() {
		return addNewPanel(Messages.getString("GestionPanelDraw.NewView") + getSize()); //$NON-NLS-1$
	}
	
	public PanelMainDraw addNewPanel(String nom) {
		JPanelMainDrawColorDockable newPanel = new JPanelMainDrawColorDockable(this.parent, nom, getSize() );
		addPanel(newPanel.getJPanel());
		// Le rendre actif pour le dessin
		this.parent.setPanelMainDrawActif(newPanel.getJPanel());
		
		// Ajout de la vue dans le viewMap
		this.parent.viewMap.put(newPanel.getJPanel().getName(), newPanel.getJPanel());
		// Ajout de la vue dans le SplitWindow principal
		this.parent.frontend.addDockable(nom, newPanel);
		this.parent.frontend.setHideable(newPanel, true);
		
		this.parent.grid.addDockable( 30*ConstantesApplication.tailleFenetreX/100, 0, 
				70*ConstantesApplication.tailleFenetreX/100, 80*ConstantesApplication.tailleFenetreY/100,
				newPanel );
		this.parent.grid.setSelected( 30*ConstantesApplication.tailleFenetreX/100, 0, 
				70*ConstantesApplication.tailleFenetreX/100, 80*ConstantesApplication.tailleFenetreY/100,
				newPanel );
		this.parent.station.dropTree( this.parent.grid.toTree() );
		
		return newPanel.getJPanel();
	}
	
	public void removePanel(PanelMainDraw panel, Dockable d) {
		
		panel.setVisible(false);
		
		this.parent.frontend.remove(d);
		
		this.listePanelMainDraw.remove(panel);
		this.parent.viewMap.remove(panel);
		this.parent.station.removeDockable(d);
		
		this.parent.station.dropTree( this.parent.grid.toTree() );
		
		//R??cup??ration m??moire suite ?? fermeture panelDraw
		System.gc();
		
	}
	
	public PanelMainDraw getFirstPanel() {
		
		PanelMainDraw firstPanelToReturned = getPanel(0);
		
		return firstPanelToReturned;
	}
	
	public PanelMainDraw getPanel(int id) {
		for (PanelMainDraw iterPanel : this.listePanelMainDraw) {
			if (iterPanel.getID() == id)
				return iterPanel;
		}
		
		return null;
	}
	
	public PanelMainDraw getPanel(String nom) {
		for (PanelMainDraw iterPanel : this.listePanelMainDraw) {
			if (iterPanel.getNom().compareTo(nom) == 0)
				return iterPanel;
		}
		
		return null;
	}
	
	public int getSize() {
		return this.listePanelMainDraw.size();
	}
}