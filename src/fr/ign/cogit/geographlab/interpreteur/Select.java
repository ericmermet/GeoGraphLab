/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.interpreteur;

import java.util.Set;


import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.console.HelpConsole;
import fr.ign.cogit.geographlab.ihm.constantes.PatternsConstants;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;
import fr.ign.cogit.geographlab.visu.ZoneAgregee;

public class Select {
	
	public static boolean parseOperations(MainWindow mainWindow, String[] items) {
		
		PatternsConstants.NumeralCommandesSelect commandesSelect;
		
		commandesSelect = PatternsConstants.NumeralCommandesSelect.valueOf(items[1].toUpperCase());
		
		try {
			
			String[] objASelectionner = new String[items.length - 2];
			for (int i = 2; i < items.length; i++) {
				objASelectionner[i - 2] = items[i];
			}
			
			switch (commandesSelect) {
				case ARC:

					Set<Arc> lesArcs = mainWindow.panelActif.carte.getGraphe().getArcs();
					
					for (String objSelect : objASelectionner) {
						System.out.println(objSelect);
						for (Arc arc : lesArcs) {
							if (arc.getNom().compareTo(objSelect) == 0) {
								arc.setSelected(true);
								mainWindow.panelActif.listOfSelectedObjects.add(arc.getArcGraphique());
								System.out.println("select ok");
//								mainWindow.console.addNewLine("selection arc " + arc.getNom());
							}
						}
					}
					break;
				
				case NOEUD:

					Set<Noeud> lesNoeuds = mainWindow.panelActif.carte.getGraphe().getNoeuds();
					
					for (String objSelect : objASelectionner) {
						for (Noeud noeud : lesNoeuds) {
							if (noeud.getNom().compareTo(objSelect) == 0) {
								noeud.setSelected(true);
								mainWindow.panelActif.listOfSelectedObjects.add(noeud.getNoeudGraphique());
								System.out.println("select ok");
//								mainWindow.console.addNewLine("selection noeud " + noeud.getNom());
							}
						}
					}
					
					break;
				
				case ZONE:

					Set<ZoneAgregee> lesZones = mainWindow.panelActif.carte.getVueDuGraphe().getZonesAgregees();
					
					for (String objSelect : objASelectionner) {
						System.out.println(objSelect);
						for (ZoneAgregee zone : lesZones) {
							if (zone.getNom().compareTo(objSelect) == 0) {
								zone.setSelected(true);
								mainWindow.panelActif.listOfSelectedObjects.add(zone);
//								mainWindow.console.addNewLine("selection zone " + zone.getNom());
							}
						}
					}
					
					break;
				case HELP:
					mainWindow.console.addNewLine(HelpConsole.textHelpSelect);
					break;
			}
			mainWindow.panelActif.repaint();
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
