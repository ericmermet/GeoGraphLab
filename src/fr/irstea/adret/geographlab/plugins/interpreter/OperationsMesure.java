/**
* GeoGrapheLab ( http://geographlab.free.fr/ ) - This file is part of GeoGrapheLab
* Copyright (C) 2007-2012 - IGN  - Eric Mermet & IRSTEA - Jean-Marc Tacnet (2011)
*
* This file must be used under the terms of the CeCILL-B.
* This source file is licensed as described in the file COPYING, which
* you should have received as part of this distribution.  The terms
* are also available at
* http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
*
*/

package fr.irstea.adret.geographlab.plugins.interpreter;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;

public class OperationsMesure extends fr.ign.cogit.geographlab.interpreteur.OperationsMesure {
	
public boolean operations(Carte carteA, Carte carteB, Carte carteC, String operateur, Carte nouvelleCarte) {
		
		String nomIndicateurA = carteA.getNomIndicateurCourant();
		String nomIndicateurB = "";
		String nomIndicateurC = "";
		if( carteB != null )
			nomIndicateurB = carteB.getNomIndicateurCourant();
		if( carteC != null )
			nomIndicateurC = carteC.getNomIndicateurCourant();
		
		nouvelleCarte.setNomIndicateurCourant(nomIndicateurA + operateur + nomIndicateurB );
		
		String nomIndicateurPourNouvelleCarte = nouvelleCarte.getNomIndicateurCourant();
		
		if (operateur.compareTo("++") == 0) {
			
			for (Arc iterArc : carteA.carteMere.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0, valeurArcB = 0.0, valeurArcC = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurArcB = iterArc.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				valeurArcC = iterArc.getValeurPourIndicateur(nomIndicateurC).doubleValue();
				
				double sommeArc = valeurArcA + valeurArcB + valeurArcC;
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, sommeArc);
				this.max = Math.max(sommeArc, this.max);
				this.min = Math.min(sommeArc, this.min);
			}
			for (Noeud iterNoeud : carteA.carteMere.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0, valeurNoeudB = 0.0, valeurNoeudC = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurNoeudB = iterNoeud.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				valeurNoeudC = iterNoeud.getValeurPourIndicateur(nomIndicateurC).doubleValue();
				
				double sommeNoeud = valeurNoeudA + valeurNoeudB + valeurNoeudC;
				
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, sommeNoeud);
				this.max = Math.max(sommeNoeud, this.max);
				this.min = Math.min(sommeNoeud, this.min);
			}
			
			return (true);
		}
		
		if (operateur.compareTo("**") == 0) {
			
			for (Arc iterArc : carteA.carteMere.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0, valeurArcB = 0.0, valeurArcC = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurArcB = iterArc.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				valeurArcC = iterArc.getValeurPourIndicateur(nomIndicateurC).doubleValue();
				
				double sommeArc = valeurArcA * valeurArcB * valeurArcC;
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, sommeArc);
				this.max = Math.max(sommeArc, this.max);
				this.min = Math.min(sommeArc, this.min);
			}
			for (Noeud iterNoeud : carteA.carteMere.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0, valeurNoeudB = 0.0, valeurNoeudC = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurNoeudB = iterNoeud.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				valeurNoeudC = iterNoeud.getValeurPourIndicateur(nomIndicateurC).doubleValue();
				
				double sommeNoeud = valeurNoeudA * valeurNoeudB * valeurNoeudC;
				
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, sommeNoeud);
				this.max = Math.max(sommeNoeud, this.max);
				this.min = Math.min(sommeNoeud, this.min);
			}
			
			return (true);
		}
		
		if (operateur.compareTo("fonctionBinaire") == 0) {
			
			for (Arc iterArc : carteA.carteMere.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0, valeurArcB = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurArcB = iterArc.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				double resultatFonctionArete = fonctionBinaireArete(valeurArcA,valeurArcB);
				
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, resultatFonctionArete);
				this.max = Math.max(resultatFonctionArete, this.max);
				this.min = Math.min(resultatFonctionArete, this.min);
			}
			for (Noeud iterNoeud : carteA.carteMere.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0, valeurNoeudB = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurNoeudB = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				double resultatFonctionNoeud = fonctionBinaireNoeud(valeurNoeudA, valeurNoeudB);
				
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, resultatFonctionNoeud);
				this.max = Math.max(resultatFonctionNoeud, this.max);
				this.min = Math.min(resultatFonctionNoeud, this.min);
			}
			
			return (true);
		}
		
		if (operateur.compareTo("fonctionUnaire") == 0) {
			
			for (Arc iterArc : carteA.carteMere.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				double resultatFonctionArete = fonctionUnaireArete(valeurArcA);
				
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, resultatFonctionArete);
				this.max = Math.max(resultatFonctionArete, this.max);
				this.min = Math.min(resultatFonctionArete, this.min);
			}
			for (Noeud iterNoeud : carteA.carteMere.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				double resultatFonctionNoeud = fonctionUnaireNoeud(valeurNoeudA);
				
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, resultatFonctionNoeud);
				this.max = Math.max(resultatFonctionNoeud, this.max);
				this.min = Math.min(resultatFonctionNoeud, this.min);
			}
			
			return (true);
		}
		
		
		return (false);
	}
	
	public static double fonctionBinaireNoeud(double valeur1, double valeur2) {
		//TODO implémenter ici la fonction binaire pour les noeuds
		//Par exemple ici on renvoie a^b
		double coeff1 = 0.5, coeff2 = 0.5;
		return (coeff1*valeur1 + coeff2*valeur2)/2;
	}
	
	public static double fonctionBinaireArete(double valeur1, double valeur2) {
		//TODO implémenter ici la fonction binaire pour les aretes (si algo différent des noeuds)
		//Par exemple ici on renvoie a^b
		double coeff1 = 0.5, coeff2 = 0.5;
		return (coeff1*valeur1 + coeff2*valeur2)/2;
	}
	
	public static double fonctionUnaireNoeud(double valeur) {
		//TODO implémenter ici la fonction unaire pour les noeuds
		//Par exemple ici on renvoie 1/a
		return Math.pow(valeur, -1);
	}
	
	public static double fonctionUnaireArete(double valeur) {
		//TODO implémenter ici la fonction unaire pour les noeuds
		//Par exemple ici on renvoie 1/a
		return Math.pow(valeur, -1);
	}
	
}