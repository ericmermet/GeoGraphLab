/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.interpreteur;

import java.lang.Runtime;

import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.console.HelpConsole;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.ihm.constantes.PatternsConstants;

import java.util.HashSet;
import java.util.regex.Pattern;

public class Parseur {
	
	MainWindow mainWindow;
	
	HashSet<Pattern> patterns = new HashSet<Pattern>();
	
	PatternsConstants.NumeralCommands commands;
	
	public Parseur(MainWindow mw) {
		this.mainWindow = mw;
	}
	
	public boolean parseInput(String input) {
		
		try {
			String[] items = PatternsConstants.splitPattern.split(input);
			
			if (items[0].contains("layer")) {
				OperationsMesure op = new OperationsMesure();
				if (!op.parseOperations(this.mainWindow, items)) {
					this.mainWindow.console.addNewLine("Synthax Error in Compute");
				} else {
					return true;
				}
			} else {
				
				this.commands = PatternsConstants.NumeralCommands.valueOf(items[0].toUpperCase());
				
				switch (this.commands) {
					case EXIT:
						System.exit(0);
						break;
					case HELP:
						this.mainWindow.console.addNewLine(HelpConsole.textMainHelp);
						break;
					case CLEAR:
						this.mainWindow.console.setTexte("");
						this.mainWindow.console.setToutLeTexte("");
						break;
					case INFO:
						this.mainWindow.console.addNewLine(ConstantesApplication.textSystem);
						break;
					case SYSTEM:
						String textSystem = "Utilisation memoire : " + Runtime.getRuntime().freeMemory() / 1000000 + " Mo /" + Runtime.getRuntime().totalMemory() / 1000000 + " Mo";
						this.mainWindow.console.addNewLine(textSystem);
						break;
					case FREEMEM:
						long pre = Runtime.getRuntime().freeMemory() / 1000000;
						System.gc();
						long post = Runtime.getRuntime().freeMemory() / 1000000;
						long gain = pre - post;
						
						String textFree = "Free " + gain + " Mo";
						this.mainWindow.console.addNewLine(textFree);
						break;
					case CALCUL:
						if (!Compute.parseOperations(this.mainWindow, items)) {
							this.mainWindow.console.addNewLine("Synthax Error in Compute");
						}
						break;
					case SELECT:
						if (!Select.parseOperations(this.mainWindow, items)) {
							this.mainWindow.console.addNewLine("Synthax Error in Select");
						}
						break;
					case FILTRE:
						if (!Filtre.parseOperations(this.mainWindow, items)) {
							this.mainWindow.console.addNewLine("Synthax Error in Filtre");
						}
						break;
					case ESPACE:
						this.mainWindow.console.addNewLine("Taille de l'espace sur carte " + this.mainWindow.panelActif.carte.getNom() + " : " + this.mainWindow.panelActif.carte.getEspace().getToutesLesOD().size() + " ODs");
						break;
					default:
						// Operations sur les valeurs d'indicateurs
						this.mainWindow.console.addNewLine("Commande inconnue");
						break;
				}
			}
			
		} catch (Exception e) {
			this.mainWindow.console.addNewLine("Exception : Commande inconnue");
			return false;
		}
		return true;
	}

}