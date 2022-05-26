/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * OperationsMesureSommePonderee.java in fr.irstea.adret.geographlab.plugins.interpreteur
 * 
 */
package fr.irstea.adret.geographlab.plugins.interpreter;

import java.util.ArrayList;

import fr.ign.cogit.geographlab.algo.maths.SommePonderee;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;

/**
 * @author eric
 *
 */
public class OperationsMesureSommePonderee extends OperationsMesure {
	
	public static boolean operations(ArrayList<Carte> listCarte, String operateur, ArrayList<Double> poids, Carte nouvelleCarte, String nomCarteResultante) {
		
		nouvelleCarte.setNomIndicateurCourant(nomCarteResultante);
		
		String nomIndicateurPourNouvelleCarte = nouvelleCarte.getNomIndicateurCourant();
		
		if (operateur.compareTo("sommePonderee") == 0) {

			for (Arc iterArc : listCarte.get(0).carteMere.getGraphe().getArcs()) {
				ArrayList<Double> listValeursArcs = new ArrayList<Double>();
				for (Carte iterCarte : listCarte) {
					listValeursArcs.add(iterArc.getValeurPourIndicateur(iterCarte.getNomIndicateurCourant()));
				}
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, 
						SommePonderee.get(poids, listValeursArcs));
			}
			for (Noeud iterNoeud : listCarte.get(0).carteMere.getGraphe().getNoeuds()) {
				ArrayList<Double> listValeursNoeuds = new ArrayList<Double>();
				for (Carte iterCarte : listCarte) {
					listValeursNoeuds.add(iterNoeud.getValeurPourIndicateur(iterCarte.getNomIndicateurCourant()));
				}
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, 
						SommePonderee.get(poids, listValeursNoeuds));
			}
			
			return (true);
		}
		
		return false;
	}
	
}
