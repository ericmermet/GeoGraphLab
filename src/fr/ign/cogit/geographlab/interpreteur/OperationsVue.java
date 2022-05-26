/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2011
 *
 * OperationsVue.java in interpreteur
 * 
 */
package fr.ign.cogit.geographlab.interpreteur;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.test.Debug;
import fr.ign.cogit.geographlab.visu.ZoneAgregee;

import java.awt.Color;
import java.util.Date;
import java.util.Set;


public class OperationsVue {
	
	double min;
	double max;
	boolean valReturn;
	
	public OperationsVue() {
		this.min = Double.MAX_VALUE;
		this.max = Double.MIN_VALUE;
		this.valReturn = false;
	}
	
	public boolean parseOperations(MainWindow mainWindow, String[] items) {
		
		String operateur;
		
		String numCoucheA = items[0].substring("layer".length(), items[0].length());
		String numCoucheB = items[2].substring("layer".length(), items[0].length());
		
		int numIntCoucheA = Integer.parseInt(numCoucheA);
		int numIntCoucheB = Integer.parseInt(numCoucheB);
		
		Carte carteA = mainWindow.panelActif.couchesDeCartes.getCarte(numIntCoucheA);
		Carte carteB = mainWindow.panelActif.couchesDeCartes.getCarte(numIntCoucheB);
		
		operateur = items[1];
		
		String nomIndicateurPourNouvelleCarte = items[0] + operateur + items[2] + new Date().getTime();
		Carte nouvelleCartePourNouvelleCouche = new Carte(carteA.parent, carteA.getNom() + " " + operateur + " " + carteB.getNom(), nomIndicateurPourNouvelleCarte);
		
		int newColor = carteA.getColorLayer().getRGB() + carteB.getColorLayer().getRGB();
		nouvelleCartePourNouvelleCouche.setColorLayer(new Color(newColor));
		
		this.valReturn = operations(carteA, carteB, operateur, nouvelleCartePourNouvelleCouche);
		
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
	
	public boolean operations(Carte carteA, Carte carteB, String operateur, Carte nouvelleCarte) {
		
		String nomIndicateurA = carteA.getNomIndicateurCourant();
		String nomIndicateurB = carteB.getNomIndicateurCourant();
		
		nouvelleCarte.setNomIndicateurCourant(nomIndicateurA + operateur + nomIndicateurB );
		
		String nomIndicateurPourNouvelleCarte = nouvelleCarte.getNomIndicateurCourant();
		
		if (operateur.compareTo("unionVue") == 0) {
			

			
			return (true);
		}
		
		if (operateur.compareTo("intersectionVue") == 0) {
			


			return (true);
		}
		
		if (operateur.compareTo("exclusionVue") == 0) {
			

			
			return (true);
		}
		
		if (operateur.compareTo("unionConsVue") == 0) {
			
			Set<ZoneAgregee> zonesAgregeesCarteA = carteA.getVueDuGraphe().getZonesAgregees();
			Set<ZoneAgregee> zonesAgregeesCarteB = carteB.getVueDuGraphe().getZonesAgregees();
			
			nouvelleCarte.getVueDuGraphe().addZonesAgregees(zonesAgregeesCarteA);
			nouvelleCarte.getVueDuGraphe().addZonesAgregees(zonesAgregeesCarteB);
			
			return (true);
		}
		
		return (false);
	}
	
}