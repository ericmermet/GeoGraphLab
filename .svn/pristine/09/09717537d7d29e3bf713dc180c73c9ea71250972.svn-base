/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.interpreteur;

import fr.ign.cogit.geographlab.algo.indicateurs.CentraliteDeDegre;
import fr.ign.cogit.geographlab.algo.indicateurs.CentraliteIntermediaire;
import fr.ign.cogit.geographlab.algo.indicateurs.CentraliteProximite;
import fr.ign.cogit.geographlab.algo.indicateurs.EloignementMax;
import fr.ign.cogit.geographlab.algo.indicateurs.EloignementMin;
import fr.ign.cogit.geographlab.algo.indicateurs.EloignementMoyen;
import fr.ign.cogit.geographlab.algo.indicateurs.RayonDistal;
import fr.ign.cogit.geographlab.algo.indicateurs.RayonProximal;
import fr.ign.cogit.geographlab.cheminements.APSP;
import fr.ign.cogit.geographlab.cheminements.Constantes;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.console.HelpConsole;
import fr.ign.cogit.geographlab.ihm.constantes.PatternsConstants;

public class Compute {
	
	public static boolean parseOperations(MainWindow mainWindow, String[] items) {
		
		PatternsConstants.NumeralCommandesCompute commandsCompute;
		
		commandsCompute = PatternsConstants.NumeralCommandesCompute.valueOf(items[1].toUpperCase());
		
		try {
			
			// Verifier qu'il y a bien une carte active
			
			switch (commandsCompute) {
				case HELP:
					mainWindow.console.addNewLine(HelpConsole.textHelpCalcul);
					break;
				case CI:
				case CENTRALITE_INTERMEDIAIRE:
					CentraliteIntermediaire indCentraliteIntermediaire = new CentraliteIntermediaire(mainWindow.getPanelMainDrawActif().carte);
					indCentraliteIntermediaire.thread.start();
					break;
				case CD:
				case CENTRALITE_DEGRE:
					CentraliteDeDegre indCentraliteDeDegre = new CentraliteDeDegre(mainWindow.getPanelMainDrawActif().carte);
					indCentraliteDeDegre.thread.start();
					break;
				case CP:
				case CENTRALITE_PROXIMITE:
					CentraliteProximite indCentraliteDeProximite = new CentraliteProximite(mainWindow.getPanelMainDrawActif().carte);
					indCentraliteDeProximite.thread.start();
					break;
				case EM:
				case ELOIGNEMENT_MOYEN:
					EloignementMoyen eloignementMoyen = new EloignementMoyen(mainWindow.getPanelMainDrawActif().carte, Constantes.typePCC);
					eloignementMoyen.thread.start();
					break;
				case EMA:
				case ELOIGNEMENT_MAXIMUM:
					EloignementMax eloignementMax = new EloignementMax(mainWindow.getPanelMainDrawActif().carte, Constantes.typePCC);
					eloignementMax.thread.start();
					break;
				case EMI:
				case ELOIGNEMENT_MINIMUM:
					EloignementMin eloignementMin = new EloignementMin(mainWindow.getPanelMainDrawActif().carte, Constantes.typePCC);
					eloignementMin.thread.start();
					break;
				case RD:
				case RAYON_DISTAL:
					RayonDistal rayonDistal = new RayonDistal(mainWindow.getPanelMainDrawActif().carte);
					rayonDistal.thread.start();
					break;
				case RP:
				case RAYON_PROXIMAL:
					RayonProximal rayonProximal = new RayonProximal(mainWindow.getPanelMainDrawActif().carte);
					rayonProximal.thread.start();
					break;
				case APSP:
					APSP allShortestPath = new APSP(mainWindow.getPanelMainDrawActif().carte);
					allShortestPath.thread.start();
					break;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
