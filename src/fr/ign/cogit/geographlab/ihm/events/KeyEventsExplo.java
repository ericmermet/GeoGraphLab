/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2011
 *
 * KeyEventsExplo.java in ihm.events
 * 
 */
package fr.ign.cogit.geographlab.ihm.events;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.ihm.exploration.PanelExplo;


/**
 * @author Eric Mermet
 *
 */
public class KeyEventsExplo extends KeyAdapter{
	
	private PanelExplo panel;
	
	public KeyEventsExplo(PanelExplo panel) {
		super();
		this.panel = panel;
	}
	
/* (non-Javadoc)
	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		super.keyPressed(arg0);
		
		System.out.println("test");
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyTyped(e);
		System.out.println("pass key typed - panel explo");
		
		if( e.getKeyCode() == KeyEvent.VK_DELETE ){
			
			//Parcours de toutes les cartes
			for (Carte iterCarte: this.panel.parent.panelActif.couchesDeCartes.getCouches()) {
				
				//Suppression du bloc carte selectionne
				if( iterCarte.isSelected() ) {
					this.panel.parent.panelActif.couchesDeCartes.retirerUneCoucheCarte(iterCarte);
				}
			}
			this.panel.repaint();
		}
		
	}
	
}
