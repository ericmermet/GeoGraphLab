/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.Timer;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;


public class TimerClignotant extends Thread {
	
	Carte carteMere;
	private Timer timerClignotant;
	HashSet<ObjetGraphique> objetsClignotants = new HashSet<ObjetGraphique>();
	boolean clignotant = false;
	Color nouvelleCouleur;
	
	public TimerClignotant(Carte carte) {
		this.carteMere = carte;
		int delay = 1000; // milliseconds
		
		ActionListener clignote = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if( TimerClignotant.this.objetsClignotants.size() > 0 ) {
					for (ObjetGraphique iterObjetGraphique : TimerClignotant.this.objetsClignotants) {
						if (TimerClignotant.this.clignotant) {
							TimerClignotant.this.nouvelleCouleur = Color.BLACK;
						} else {
							TimerClignotant.this.nouvelleCouleur = Color.GREEN;
						}
						iterObjetGraphique.setIndicateurCouleur(TimerClignotant.this.carteMere.getNomIndicateurCourant(), TimerClignotant.this.nouvelleCouleur);
						TimerClignotant.this.clignotant = !TimerClignotant.this.clignotant;
					}
					TimerClignotant.this.carteMere.parent.repaint();
				}
			}
		};
		
		this.timerClignotant = new Timer(delay, clignote);
		this.timerClignotant.start();
		
	}
	
	public void addObjetClignotant(ObjetGraphique obj) {
		this.objetsClignotants.add(obj);
	}
	
	public void removeObjetClignotant(ObjetGraphique obj) {
		this.objetsClignotants.remove(obj);
	}
	
	public void clearObjetsClignotants() {
		this.objetsClignotants.clear();
	}
}