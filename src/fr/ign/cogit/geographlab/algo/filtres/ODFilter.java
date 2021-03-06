/**
 * GeoGraphLab - eric - Graph-it
 * 2007 - 2014
 *
 * ODFilter.java in fr.ign.cogit.geographlab.algo.filtres
 * 
 */
package fr.ign.cogit.geographlab.algo.filtres;

import java.util.ArrayList;
import java.util.HashMap;

import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

/**
 * @author eric
 *
 */
public class ODFilter {
	
	public static void nSelected(MainWindow mainWindow, HashMap<Integer, OD> nouvelEspace) {
		
		int cptSelODFromNSelected = 0;
		for( ObjetGraphique noeudSel : mainWindow.panelActif.listOfSelectedObjects) {
			if( noeudSel.getType() == ConstantesApplication.typeVertex) {
				for (NoeudGraphique noeud2 : mainWindow.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques()) {
					if( noeudSel != noeud2 && !mainWindow.panelActif.listOfSelectedObjects.contains(noeud2)) {
						
						//unSelect and color for selected node
						((NoeudGraphique)noeudSel).setSelected(false);
						((NoeudGraphique)noeudSel).setColor(mainWindow.panelActif.carte.variablesDeCarte.selectedColorVertexOrigine);
						
						//unSelect and color for destination node
						noeud2.setSelected(false);
						noeud2.setColor(mainWindow.panelActif.carte.variablesDeCarte.selectedColorVertexDestination);
						
						OD nouvelOD = null;
						nouvelOD = new OD(((NoeudGraphique)noeudSel).getNoeudTopologique(), noeud2.getNoeudTopologique());
						nouvelEspace.put(new Integer(nouvelOD.hashCode()), nouvelOD);
						cptSelODFromNSelected++;
					}
				}
			}
		}
		
		mainWindow.panelActif.carte.getEspace().setEspaceDeDef(nouvelEspace);
		mainWindow.panelActif.carte.getTousLesPCC().clear();
		
		// mainWindow.panelActif.carte.carteMere.getEspace().setEspaceDeDef(nouvelEspace);
		mainWindow.console.addNewLine("Selection de " + cptSelODFromNSelected + " ODs a partir du(des) noeud(s) selectionnes");
		
		ConstantesApplication.filterHasBeenActivated = true;
	
		mainWindow.panelActif.repaint();
	}
	
	public static void selectedAsOrigins(MainWindow mainWindow, HashMap<Integer, OD> nouvelEspace) {
		for( ObjetGraphique o : mainWindow.panelActif.listOfSelectedObjects) {
			if( o.getType() == ConstantesApplication.typeVertex) {
				
				mainWindow.panelActif.listeOrigines.add((NoeudGraphique)o);
				// Mise en couleur des noeuds selectionnes
				((NoeudGraphique)o).setSelected(false);
				((NoeudGraphique)o).setColor(mainWindow.panelActif.carte.variablesDeCarte.selectedColorVertexOrigine);
			}
		}
		mainWindow.panelActif.listOfSelectedObjects.clear();
		mainWindow.panelActif.repaint();
	}
	
	public static void selectedAsDestinations(MainWindow mainWindow, HashMap<Integer, OD> nouvelEspace) {
		for( ObjetGraphique o : mainWindow.panelActif.listOfSelectedObjects) {
			if( o.getType() == ConstantesApplication.typeVertex) {
				
				mainWindow.panelActif.listeDestinations.add((NoeudGraphique)o);
				((NoeudGraphique)o).setSelected(false);
				((NoeudGraphique)o).setColor(mainWindow.panelActif.carte.variablesDeCarte.selectedColorVertexDestination);
				
			}
		}
		mainWindow.panelActif.listOfSelectedObjects.clear();
		
		int cptSelectedOandD= 0;
		for (NoeudGraphique origine : mainWindow.panelActif.listeOrigines) {
			origine.setSelectedOrigine(true);
			for(NoeudGraphique destination : mainWindow.panelActif.listeDestinations ) {
					
				OD nouvelOD = null;
				if( origine != destination ) {
					nouvelOD = new OD(origine.getNoeudTopologique(), destination.getNoeudTopologique());
					nouvelEspace.put(new Integer(nouvelOD.hashCode()), nouvelOD);
					cptSelectedOandD++;
				}
				
			}
		}
		
		mainWindow.panelActif.carte.getEspace().setEspaceDeDef(nouvelEspace);
		mainWindow.panelActif.carte.getTousLesPCC().clear();
		
		// mainWindow.panelActif.carte.carteMere.getEspace().setEspaceDeDef(nouvelEspace);
		mainWindow.console.addNewLine("Selection de " + cptSelectedOandD + " ODs a partir du(des) noeud(s) selectionnes");
		
		ConstantesApplication.filterHasBeenActivated = true;
		mainWindow.panelActif.repaint();
	}
	
	public static void clearFilters(MainWindow mainWindow, HashMap<Integer, OD> nouvelEspace) {
		mainWindow.panelActif.carte.getEspace().clearEspaceDeDef();
		mainWindow.panelActif.listeOrigines.clear();
		mainWindow.panelActif.listeDestinations.clear();
		for (NoeudGraphique noeud : mainWindow.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques()) {
			noeud.setColor(ConstantesApplication.drawingColorVertex);
		}
		ConstantesApplication.filterHasBeenActivated = false;
		mainWindow.panelActif.repaint();
	}
	
	public static void inverseFilters(MainWindow mainWindow, HashMap<Integer, OD> nouvelEspace) {
		
//		mainWindow.panelActif.carte.getEspace().clearEspaceDeDef();
//		mainWindow.panelActif.listeOrigines.clear();
//		mainWindow.panelActif.listeDestinations.clear();
//		for (NoeudGraphique noeud : mainWindow.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques()) {
//			noeud.setColor(ConstantesApplication.drawingColorVertex);
//		}
		
		for(OD od : mainWindow.panelActif.carte.getEspace().getEspaceDeDef().values()) {
			OD nouvelOD = new OD(od.getDestination(), od.getOrigine());
			nouvelOD.getOrigine().getNoeudGraphique().setColor(mainWindow.panelActif.carte.variablesDeCarte.selectedColorVertexOrigine);
			nouvelOD.getDestination().getNoeudGraphique().setColor(mainWindow.panelActif.carte.variablesDeCarte.selectedColorVertexDestination);
			nouvelEspace.put(new Integer(nouvelOD.hashCode()), nouvelOD);
		}
		
		mainWindow.panelActif.carte.getEspace().setEspaceDeDef(nouvelEspace);
		mainWindow.panelActif.carte.getTousLesPCC().clear();
		
		// mainWindow.panelActif.carte.carteMere.getEspace().setEspaceDeDef(nouvelEspace);
		mainWindow.console.addNewLine("Selection de " + mainWindow.panelActif.carte.getEspace().getEspaceDeDef().values().size() + " ODs a partir du(des) noeud(s) selectionnes");
		
		ConstantesApplication.filterHasBeenActivated = true;
		mainWindow.panelActif.repaint();
	}
	
}