/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.interpreteur;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;

import fr.ign.cogit.geographlab.algo.filtres.ODFilter;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.console.HelpConsole;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.ihm.constantes.PatternsConstants;
import fr.ign.cogit.geographlab.ihm.constantes.PatternsConstants.NumeralCommandesFiltre;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;


public class Filtre {
	
	private static enum NumeralSousCommandesFiltre {
		HELP, CLEAR, TOTAL, NORDSUD, ESTOUEST, N, N_SELECTED, SELECTED_AS_O, SELECTED_AS_D;
	}
	
	public static boolean parseOperations(MainWindow mainWindow, String[] items) {
		
		PatternsConstants.NumeralCommandesFiltre commandesFiltre = NumeralCommandesFiltre.HELP;
		NumeralSousCommandesFiltre sousCommandesFiltre = NumeralSousCommandesFiltre.HELP;
		
		
		if( items.length == 2 ){
			commandesFiltre = PatternsConstants.NumeralCommandesFiltre.valueOf(items[1].toUpperCase());
		}
		if(items.length >= 3 ){
			commandesFiltre = PatternsConstants.NumeralCommandesFiltre.valueOf(items[1].toUpperCase());
			sousCommandesFiltre = NumeralSousCommandesFiltre.valueOf(items[2].toUpperCase());
		}
			
		try {
			
//			Collection<OD> e = mainWindow.panelActif.carte.getEspace().getToutesLesOD();
			
			HashMap<Integer, OD> nouvelEspace = new HashMap<Integer, OD>();
			
			switch (commandesFiltre) {
				
				case HELP:
					mainWindow.console.addNewLine(HelpConsole.textHelpFiltre);
					break;
				
				case ESPACE:

					mainWindow.panelActif.carte.getEspace().clearEspaceDeDef();
					
					switch (sousCommandesFiltre) {
						
						case HELP:
							
							mainWindow.console.addNewLine(HelpConsole.textHelpFiltreEspace);
							break;
							
						case CLEAR:
							
							ODFilter.clearFilters(mainWindow, nouvelEspace);
							break;
						
						case TOTAL:
							
							mainWindow.panelActif.carte.getEspace().setToutesLesOD(mainWindow.panelActif.carte.getGraphe().getNoeuds());
							for (NoeudGraphique noeud : mainWindow.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques()) {
								noeud.setColor(ConstantesApplication.drawingColorVertex);
							}
							ConstantesApplication.filterHasBeenActivated = false;
							break;
						
						case NORDSUD:

							int cptNordSud = 0;
//							noeudsNord = 0,
//							noeudsSud = 0;
							double seuilHorizontal = mainWindow.panelActif.lignePartageNordSudHorizontal.y1;
							
							for (NoeudGraphique noeud1 : mainWindow.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques()) {
								for (NoeudGraphique noeud2 : mainWindow.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques()) {
									if (noeud1.getYPosition() <= seuilHorizontal & noeud2.getYPosition() >= seuilHorizontal) {
										OD nouvelOD = new OD(noeud1.getNoeudTopologique(), noeud2.getNoeudTopologique());
										nouvelEspace.put(new Integer(nouvelOD.hashCode()), nouvelOD);
										nouvelOD.getOrigine().getNoeudGraphique().setColor(Color.GREEN);
										nouvelOD.getDestination().getNoeudGraphique().setColor(Color.RED);
										cptNordSud++;
									}
								}
							}
							
							mainWindow.panelActif.carte.getEspace().setEspaceDeDef(nouvelEspace);
							mainWindow.console.addNewLine("Selection de " + cptNordSud + " relations OD Nord - Sud");
							
							break;
						
						case ESTOUEST:

							int cptEstOuest = 0;
//							noeudsEst = 0,
//							noeudsOuest = 0;
							double seuilVertical = mainWindow.panelActif.lignePartageEstOuestVertical.x1;
							
							for (NoeudGraphique noeud1 : mainWindow.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques()) {
								for (NoeudGraphique noeud2 : mainWindow.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques()) {
									if (noeud1.getXPosition() <= seuilVertical & noeud2.getXPosition() >= seuilVertical) {
										OD nouvelOD = new OD(noeud1.getNoeudTopologique(), noeud2.getNoeudTopologique());
										nouvelEspace.put(new Integer(nouvelOD.hashCode()), nouvelOD);
										nouvelOD.getOrigine().getNoeudGraphique().setColor(Color.GREEN);
										nouvelOD.getDestination().getNoeudGraphique().setColor(Color.RED);
										cptEstOuest++;
									}
								}
							}
							
							mainWindow.panelActif.carte.getEspace().setEspaceDeDef(nouvelEspace);
							mainWindow.console.addNewLine("Selection de " + cptEstOuest + " relations OD Est - Ouest");
							
							break;
						
						case N:

							int cptSelODNoeud = 0;
							// recuperer les chaines apres N
							String[] objASelectionner = new String[items.length - 3];
							for (int i = 3; i < items.length; i++) {
								objASelectionner[i - 3] = items[i];
							}
							
							for (String objSelect : objASelectionner) {
								NoeudGraphique noeud1 = mainWindow.panelActif.carte.getVueDuGraphe().getNoeudGraphique(objSelect);
								for (NoeudGraphique noeud2 : mainWindow.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques()) {
									if (noeud2 != noeud1) {
										OD nouvelOD = null;
										try {
											nouvelOD = new OD(noeud1.getNoeudTopologique(), noeud2.getNoeudTopologique());
										} catch (Exception e2) {
											mainWindow.console.addNewLine("Le noeud decrit n'existe pas ...");
											break;
										}
										nouvelEspace.put(new Integer(nouvelOD.hashCode()), nouvelOD);
										noeud2.setColor(Color.RED);
										// nouvelOD.getDestination().getNoeudGraphique().setColor(Color.RED);
										cptSelODNoeud++;
									}
								}
							}
							
							// Mise en couleur des noeuds selectionnes
							for (String objSelect : objASelectionner) {
								NoeudGraphique noeud1 = mainWindow.panelActif.carte.getVueDuGraphe().getNoeudGraphique(objSelect);
								noeud1.setColor(Color.GREEN);
							}
							
							mainWindow.panelActif.carte.getEspace().setEspaceDeDef(nouvelEspace);
							mainWindow.panelActif.carte.getTousLesPCC().clear();
							
							// mainWindow.panelActif.carte.carteMere.getEspace().setEspaceDeDef(nouvelEspace);
							mainWindow.console.addNewLine("Selection de " + cptSelODNoeud + " ODs a partir du(des) noeud(s) selectionnes");
							
							ConstantesApplication.filterHasBeenActivated = true;
							
							break;
						
						case N_SELECTED:
							
							ODFilter.nSelected(mainWindow, nouvelEspace);
							
							break;
							
						case SELECTED_AS_O:
							
							ODFilter.selectedAsOrigins(mainWindow, nouvelEspace);
							
							break;
							
						case SELECTED_AS_D:
							
							ODFilter.selectedAsDestinations(mainWindow, nouvelEspace);
							
							break;
							
					}
					
					mainWindow.panelActif.repaint();
					break;
			}
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}