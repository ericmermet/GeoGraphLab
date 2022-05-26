/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.factories;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.ihm.PanelMainDraw;

import java.util.ArrayList;

public class GestionnaireDeCouches {
	
	private ArrayList<Carte> mesCouches;
	private PanelMainDraw panel;
	
	public GestionnaireDeCouches(PanelMainDraw panelDraw) {
		this.mesCouches = new ArrayList<Carte>();
		this.panel = panelDraw;
	}
	
	public void ajouterUneCoucheCarte(Carte coucheCarte) {
		
		coucheCarte.setNom(coucheCarte.getNom() + "_" + new Integer(this.mesCouches.size() + 1).toString());
		
		this.mesCouches.add(coucheCarte);
		
		// Activation de la carte en visualisation
		this.panel.carte = coucheCarte;
		for (Carte iterCarte : this.mesCouches) {
			iterCarte.setSelected(false);
		}
		this.panel.carte.setSelected(true);
	}
	
	public void retirerUneCoucheCarte(Carte coucheCarte) {
//		coucheCarte.prepareEffaceCarte();
		this.mesCouches.remove(coucheCarte);
		System.gc();
	}
	
	public void effacerUneCoucheCarte(Carte coucheCarte) {
		coucheCarte.prepareEffaceCarte();
		this.mesCouches.remove(coucheCarte);
		System.gc();
	}
	
	public void retirerToutesLesCouches() {
		this.mesCouches.clear();
	}
	
	public void retirerToutesLesChouchesParentes() {
		for (int i = 0; i < this.mesCouches.size(); i++) {
			if( this.mesCouches.get(i).isParent() )
				retirerUneCoucheCarte(this.mesCouches.get(i));
		}
	}
	
	public ArrayList<Carte> getCouches() {
		return this.mesCouches;
	}
	
	public Carte getCarte(int i) {
		return this.mesCouches.get(i);
	}
	
	public int getNombreDeCouches() {
		return this.mesCouches.size();
	}
	
	public int getNombreDeCouchesParentes() {
		int cpt=0;
		for (int i = 0; i < this.mesCouches.size(); i++) {
			if( this.mesCouches.get(i).isParent() )
				cpt++;
		}
		return cpt;
	}
	
	public Carte getCarte(String nomDeLaCarte) {
		
		for (Carte iterCarte : this.mesCouches) {
			if (iterCarte.getNom().compareTo(nomDeLaCarte) == 0)
				return iterCarte;
		}
		
		return null;
	}
	
	public void setVisible(Carte uneCarte) {
		for (Carte iterCarte : this.mesCouches) {
			if (iterCarte == uneCarte) {
				iterCarte.setSelected(true);
			} else {
				iterCarte.setSelected(false);
			}
		}
	}
	
}