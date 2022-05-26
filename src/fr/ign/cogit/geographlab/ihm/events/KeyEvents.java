/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2011
 *
 * KeyEvents.java in ihm.events
 * 
 */
package fr.ign.cogit.geographlab.ihm.events;

import fr.ign.cogit.geographlab.ihm.PanelMainDraw;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * @author Rikless
 *
 */
public class KeyEvents extends KeyAdapter{
	
	private PanelMainDraw panel;
	
	public KeyEvents(PanelMainDraw panel) {
		super();
		this.panel = panel;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyAdapter#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyTyped(e);
		
//		System.out.println("pass key typed panel main draw");
		
		if( e.getKeyCode() == KeyEvent.VK_DELETE ){
			
			for (ObjetGraphique iterObjGraphique : this.panel.listOfSelectedObjects) {
				
				switch (iterObjGraphique.getType()) {
					case ConstantesApplication.typeVertex:
						this.panel.carte.getVueDuGraphe().removeNoeudGraphique(iterObjGraphique.getNom());
						this.panel.carte.getGraphe().delNoeud(iterObjGraphique.getNom());
						break;
					case ConstantesApplication.typeEdge:
						this.panel.carte.getVueDuGraphe().removeArcGraphique(iterObjGraphique.getNom());
						this.panel.carte.getGraphe().delArc(iterObjGraphique.getNom());
						break;
					default:
						break;
				}
				
			}
			this.panel.repaint();
		}
		
	}
	
}
