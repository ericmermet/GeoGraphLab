/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm;

import java.util.HashSet;
import java.util.List;
import org.jgrapht.Graphs;

import fr.ign.cogit.geographlab.algo.traverse.SpanningTreeSelect;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

public class OutilsSelections {
	
	public static void selectMore(Carte carteActive) {
		
		HashSet<ObjetGraphique> tempSelected = new HashSet<ObjetGraphique>();
		tempSelected.clear();
		
		for (ObjetGraphique objetGraphique : carteActive.parent.listOfSelectedObjects) {
			if (objetGraphique.getType() == ConstantesApplication.typeVertex) {
				// Si l'objet est un noeud alors on selectionne tout ses voisins
				List<Noeud> temp = Graphs.neighborListOf(carteActive.getGraphe(), ((NoeudGraphique) objetGraphique).getNoeudTopologique());
				// Qu'on ajoute dans la liste d'objet selectionne
				for (Noeud noeud : temp) {
					noeud.setSelected(true);
					tempSelected.add(noeud.getNoeudGraphique());
				}
			}
		}
		
		// Add all objects
		carteActive.parent.listOfSelectedObjects.addAll(tempSelected);
		
		System.out.println(carteActive.parent.listOfSelectedObjects);
		carteActive.parent.parent.console.addNewLine(carteActive.parent.listOfSelectedObjects.toString());
		
		carteActive.parent.repaint();
		
	}
	
	public static void selectSpanningTree(Carte carteActive) {
		SpanningTreeSelect sTree = new SpanningTreeSelect(carteActive);
		sTree.thread.start();
	}
	
}
