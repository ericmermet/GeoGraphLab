/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.listes;

import java.awt.Color;
import java.awt.GridLayout;

import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.customdockingframes.ColorDockable;
import fr.ign.cogit.geographlab.visu.ArcGraphique;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ZoneAgregee;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;


public class PanelAgregation extends ColorDockable {
	private static final long serialVersionUID = 1L;
	
	boolean DEBUG = false;
	
	public static JTree tree;
	public DefaultMutableTreeNode top;
	
	MainWindow parent;
	
	public static Object saveValueBeforeBeingChanged;
	
	public PanelAgregation(MainWindow parent) {
		this(parent, "Légende", Color.WHITE, 1.0f);
	}
	
	public PanelAgregation(MainWindow parent, String nom, Color couleur, float luminosite) {
		super( nom, couleur, luminosite );
		
		this.setLayout(new GridLayout(1, 0));
		this.parent = parent;
		
		this.top = new DefaultMutableTreeNode("Zones Agregees");
		
		// createNodes();
		tree = new JTree(this.top);
		
		JScrollPane treeView = new JScrollPane(tree);
		
		add(treeView);
	}
	
	public void createNodes() {
		DefaultMutableTreeNode zoneAgregee = null;
		DefaultMutableTreeNode noeuds = null;
		DefaultMutableTreeNode arcs = null;
		
		if (this.parent.panelActif != null) {
			for (ZoneAgregee iterZone : this.parent.panelActif.carte.getVueDuGraphe().getZonesAgregees()) {
				zoneAgregee = new DefaultMutableTreeNode(iterZone.getNom());
				this.top.add(zoneAgregee);
				for (NoeudGraphique iterNoeud : iterZone.getNoeuds()) {
					noeuds = new DefaultMutableTreeNode(iterNoeud.getNom());
					zoneAgregee.add(noeuds);
				}
				for (ArcGraphique iterArc : iterZone.getArcs()) {
					arcs = new DefaultMutableTreeNode(iterArc.getNom());
					zoneAgregee.add(arcs);
				}
			}
		}
	}
	
}