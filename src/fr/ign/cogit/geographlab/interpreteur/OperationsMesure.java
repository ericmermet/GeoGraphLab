/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.interpreteur;

import java.awt.Color;
import java.util.Date;


import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.test.Debug;

public class OperationsMesure {
	
	protected double min;
	protected double max;
	boolean valReturn;
	
	public OperationsMesure() {
		this.min = Double.MAX_VALUE;
		this.max = Double.MIN_VALUE;
		this.valReturn = false;
	}
	
	public boolean parseOperations(MainWindow mainWindow, String[] items) {
		
		String operateur;
		
		String numCoucheA = items[0].substring("layer".length(), items[0].length());
		String numCoucheB = items[2].substring("layer".length(), items[0].length());
		String numCoucheC = items[4].substring("layer".length(), items[0].length());
		
		int numIntCoucheA = Integer.parseInt(numCoucheA);
		int numIntCoucheB = Integer.parseInt(numCoucheB);
		int numIntCoucheC = Integer.parseInt(numCoucheC);
		
		Carte carteA = mainWindow.panelActif.couchesDeCartes.getCarte(numIntCoucheA);
		Carte carteB = mainWindow.panelActif.couchesDeCartes.getCarte(numIntCoucheB);
		Carte carteC = mainWindow.panelActif.couchesDeCartes.getCarte(numIntCoucheC);
		
		operateur = items[1];
		
		String nomIndicateurPourNouvelleCarte = items[0] + operateur + items[2] + new Date().getTime();
		Carte nouvelleCartePourNouvelleCouche = new Carte(carteA.parent, carteA.getNom() + " " + operateur + " " + carteB.getNom(), nomIndicateurPourNouvelleCarte);
		
		int newColor = carteA.getColorLayer().getRGB() + carteB.getColorLayer().getRGB();
		nouvelleCartePourNouvelleCouche.setColorLayer(new Color(newColor));
		
		this.valReturn = operations(carteA, carteB, carteC, operateur, nouvelleCartePourNouvelleCouche);
		
		// Affichage pour debug
		for (Arc iterArc : nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			Debug.printDebug("Operation pour Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		for (Noeud iterNoeud : nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			Debug.printDebug("Operation pour Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		System.out.println(this.min + " " + this.max);
		
		// mainWindow.panelActif.couchesDeCartes.ajouterUneCoucheCarte(nouvelleCartePourNouvelleCouche);
		nouvelleCartePourNouvelleCouche.getGraphe().setGrapheChange();
		
		// Mise a jour de la legende et de liens couleurs - legende
		nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setEchelle(this.min, this.max, nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().getIntervalles());
		nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
		nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
		
		// nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation,nbOperation);
		
		nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		mainWindow.console.addNewLine("Une nouvelle carte a ete creee : " + nouvelleCartePourNouvelleCouche.getNom());
		
		if (this.valReturn) {
			return true;
		}
		return false;
		
	}
	
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
		
		if (operateur.compareTo("+") == 0) {
			
			for (Arc iterArc : carteA.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0, valeurArcB = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurArcB = iterArc.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				double sommeArc = valeurArcA + valeurArcB;
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(sommeArc));
				this.max = Math.max(sommeArc, this.max);
				this.min = Math.min(sommeArc, this.min);
			}
			for (Noeud iterNoeud : carteA.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0, valeurNoeudB = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurNoeudB = iterNoeud.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				double sommeNoeud = valeurNoeudA + valeurNoeudB;
				
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(sommeNoeud));
				this.max = Math.max(sommeNoeud, this.max);
				this.min = Math.min(sommeNoeud, this.min);
			}
			
			return (true);
		}
		
		if (operateur.compareTo("++") == 0) {
			
			for (Arc iterArc : carteA.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0, valeurArcB = 0.0, valeurArcC = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurArcB = iterArc.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				valeurArcC = iterArc.getValeurPourIndicateur(nomIndicateurC).doubleValue();
				
				double sommeArc = valeurArcA + valeurArcB + valeurArcC;
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(sommeArc));
				this.max = Math.max(sommeArc, this.max);
				this.min = Math.min(sommeArc, this.min);
			}
			for (Noeud iterNoeud : carteA.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0, valeurNoeudB = 0.0, valeurNoeudC = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurNoeudB = iterNoeud.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				valeurNoeudC = iterNoeud.getValeurPourIndicateur(nomIndicateurC).doubleValue();
				
				double sommeNoeud = valeurNoeudA + valeurNoeudB + valeurNoeudC;
				
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(sommeNoeud));
				this.max = Math.max(sommeNoeud, this.max);
				this.min = Math.min(sommeNoeud, this.min);
			}
			
			return (true);
		}
		
		if (operateur.compareTo("-") == 0) {
			
			for (Arc iterArc : carteA.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0, valeurArcB = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurArcB = iterArc.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				double diffArc = valeurArcA - valeurArcB;
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(diffArc));
				this.max = Math.max(diffArc, this.max);
				this.min = Math.min(diffArc, this.min);
			}
			for (Noeud iterNoeud : carteA.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0, valeurNoeudB = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurNoeudB = iterNoeud.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				double diffNoeud = valeurNoeudA - valeurNoeudB;
				
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(diffNoeud));
				this.max = Math.max(diffNoeud, this.max);
				this.min = Math.min(diffNoeud, this.min);
			}
			
			return (true);
		}
		
		if (operateur.compareTo("*") == 0) {
			
			for (Arc iterArc : carteA.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0, valeurArcB = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurArcB = iterArc.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				double produitArc = valeurArcA * valeurArcB;
				
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(produitArc));
				this.max = Math.max(produitArc, this.max);
				this.min = Math.min(produitArc, this.min);
			}
			for (Noeud iterNoeud : carteA.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0, valeurNoeudB = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurNoeudB = iterNoeud.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				double produitNoeud = valeurNoeudA * valeurNoeudB;
				
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(produitNoeud));
				this.max = Math.max(produitNoeud, this.max);
				this.min = Math.min(produitNoeud, this.min);
			}
			
			return (true);
		}
		
		if (operateur.compareTo("**") == 0) {
			
			for (Arc iterArc : carteA.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0, valeurArcB = 0.0, valeurArcC = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurArcB = iterArc.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				valeurArcC = iterArc.getValeurPourIndicateur(nomIndicateurC).doubleValue();
				
				double sommeArc = valeurArcA * valeurArcB * valeurArcC;
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(sommeArc));
				this.max = Math.max(sommeArc, this.max);
				this.min = Math.min(sommeArc, this.min);
			}
			for (Noeud iterNoeud : carteA.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0, valeurNoeudB = 0.0, valeurNoeudC = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurNoeudB = iterNoeud.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				valeurNoeudC = iterNoeud.getValeurPourIndicateur(nomIndicateurC).doubleValue();
				
				double sommeNoeud = valeurNoeudA * valeurNoeudB * valeurNoeudC;
				
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(sommeNoeud));
				this.max = Math.max(sommeNoeud, this.max);
				this.min = Math.min(sommeNoeud, this.min);
			}
			
			return (true);
		}
		
		if (operateur.compareTo("/") == 0) {
			
			for (Arc iterArc : carteA.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0, valeurArcB = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurArcB = iterArc.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				double diviseur = valeurArcA;
				
				if (diviseur != 0) {
					double divArc = valeurArcB / diviseur;
					iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(divArc));
					this.max = Math.max(divArc, this.max);
					this.min = Math.min(divArc, this.min);
				} else {
					iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(0.0));
				}
			}
			for (Noeud iterNoeud : carteA.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0, valeurNoeudB = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurNoeudB = iterNoeud.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				double diviseur = valeurNoeudA;
				
				if (diviseur != 0) {
					double divNoeud = valeurNoeudB / diviseur;
					iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(divNoeud));
					this.max = Math.max(divNoeud, this.max);
					this.min = Math.min(divNoeud, this.min);
				} else {
					iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(0.0));
				}
				
			}
			
			return (true);
		}
		
		if (operateur.compareTo("fonctionBinaire") == 0) {
			
			for (Arc iterArc : carteA.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0, valeurArcB = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurArcB = iterArc.getValeurPourIndicateur(nomIndicateurB).doubleValue();
				
				double resultatFonctionArete = fonctionBinaireArete(valeurArcA,valeurArcB);
				
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(resultatFonctionArete));
				this.max = Math.max(resultatFonctionArete, this.max);
				this.min = Math.min(resultatFonctionArete, this.min);
			}
			for (Noeud iterNoeud : carteA.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0, valeurNoeudB = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				valeurNoeudB = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				double resultatFonctionNoeud = fonctionBinaireNoeud(valeurNoeudA, valeurNoeudB);
				
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(resultatFonctionNoeud));
				this.max = Math.max(resultatFonctionNoeud, this.max);
				this.min = Math.min(resultatFonctionNoeud, this.min);
			}
			
			return (true);
		}
		
		if (operateur.compareTo("fonctionUnaire") == 0) {
			
			for (Arc iterArc : carteA.getGraphe().getArcs()) {
				
				double valeurArcA = 0.0;
				
				valeurArcA = iterArc.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				double resultatFonctionArete = fonctionUnaireArete(valeurArcA);
				
				iterArc.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(resultatFonctionArete));
				this.max = Math.max(resultatFonctionArete, this.max);
				this.min = Math.min(resultatFonctionArete, this.min);
			}
			for (Noeud iterNoeud : carteA.getGraphe().getNoeuds()) {
				
				double valeurNoeudA = 0.0;
				
				valeurNoeudA = iterNoeud.getValeurPourIndicateur(nomIndicateurA).doubleValue();
				
				double resultatFonctionNoeud = fonctionUnaireNoeud(valeurNoeudA);
				
				iterNoeud.setIndicateurValeur(nomIndicateurPourNouvelleCarte, new Double(resultatFonctionNoeud));
				this.max = Math.max(resultatFonctionNoeud, this.max);
				this.min = Math.min(resultatFonctionNoeud, this.min);
			}
			
			return (true);
		}
		
		
		return (false);
	}
	
	double fonctionBinaireNoeud(double valeur1, double valeur2) {
		//TODO impl??menter ici la fonction binaire pour les noeuds
		//Par exemple ici on renvoie a^b
		double coeff1 = 0.5, coeff2 = 0.5;
		return (coeff1*valeur1 + coeff2*valeur2)/2;
	}
	
	double fonctionBinaireArete(double valeur1, double valeur2) {
		//TODO impl??menter ici la fonction binaire pour les aretes (si algo diff??rent des noeuds)
		//Par exemple ici on renvoie a^b
		double coeff1 = 0.5, coeff2 = 0.5;
		return (coeff1*valeur1 + coeff2*valeur2)/2;
	}
	
	double fonctionUnaireNoeud(double valeur) {
		//TODO impl??menter ici la fonction unaire pour les noeuds
		//Par exemple ici on renvoie 1/a
		return Math.pow(valeur, -1);
	}
	
	double fonctionUnaireArete(double valeur) {
		//TODO impl??menter ici la fonction unaire pour les noeuds
		//Par exemple ici on renvoie 1/a
		return Math.pow(valeur, -1);
	}
	
}