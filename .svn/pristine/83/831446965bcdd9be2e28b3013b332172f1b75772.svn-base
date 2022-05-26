/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.visu;

import java.awt.Color;
import java.awt.Shape;
import java.util.Hashtable;

import javax.swing.JPanel;

import fr.ign.cogit.geographlab.ihm.PanelMainDraw;

public class ObjetGraphique extends JPanel {//implements MouseListener, MouseMotionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private String nom;
	private boolean isSelected = false, visible = true;
	private Color color;
	private int stimuli = 0;
	private PanelMainDraw parentPanel;
	
	private Hashtable<String, Color> indicateursCouleurs = new Hashtable<String, Color>();
	
	public ObjetGraphique() {
		setNom("No name Graphic Object");
		setVisibility(true);
	}
	
	public ObjetGraphique(String nom, int type) {
		setNom(nom);
		setType(type);
		setVisibility(true);
	}
	
	public ObjetGraphique(String name, int type, PanelMainDraw p) {
		this(name, type);
		this.setParentPanel(p);
	}
	
	public ObjetGraphique(String name, int coordXS, int coordYS, int coordXE, int coordYE, int type, Shape shape) {
		this(name, type);
		setVisibility(true);
	}
	
	public void setNom(String name) {
		this.nom = name;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public int getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		String s = new String(getNom() + " " + getType());
		return s;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public void setVisibility(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void initStimuli() {
		this.stimuli = 0;
	}
	
	public void incrStimuli() {
		this.stimuli++;
	}
	
	public int getStimuli() {
		return this.stimuli;
	}
	
	public void setIndicateurCouleur(String nom, Color color) {
		this.indicateursCouleurs.put(nom, color);
	}
	
	public Color getCouleurPourIndicateur(String nomIndicateur) {
		return this.indicateursCouleurs.get(nomIndicateur);
	}
	
	/**
	 * @return the parentPanel
	 */
	public PanelMainDraw getParentPanel() {
		return this.parentPanel;
	}

	/**
	 * @param parentPanel the parentPanel to set
	 */
	public void setParentPanel(PanelMainDraw parentPanel) {
		this.parentPanel = parentPanel;
	}

//	@Override
//	public void mouseClicked(MouseEvent e) {
//		System.out.println("Mouse Listener from ObjetGraphique");
//		
//	}
//	
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		System.out.println("Mouse Listener from ObjetGraphique");
//	}
//	
//	@Override
//	public void mouseExited(MouseEvent e) {
//		System.out.println("Mouse Listener from ObjetGraphique");
//		
//	}
//	
//	@Override
//	public void mousePressed(MouseEvent e) {
//		System.out.println("Mouse Listener from ObjetGraphique");
//		
//	}
//	
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		System.out.println("Mouse Listener from ObjetGraphique");
//		
//	}
//	
//	@Override
//	public void mouseDragged(MouseEvent arg0) {
//		
//	}
//	
//	@Override
//	public void mouseMoved(MouseEvent arg0) {
//		
//	}
	
}