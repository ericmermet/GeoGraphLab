/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2010
 *
 * OperationsEspace.java in interpreteur
 * 
 */
package fr.ign.cogit.geographlab.interpreteur;

/**
 * @author mermet
 *
 */

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;



import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.test.Debug;

public class OperationsEspace {
	
	double min;
	double max;
	boolean valReturn;
	
	public OperationsEspace() {
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
		
		if (operateur.compareTo("union") == 0) {
			
			HashMap<Integer, OD> hashTempUnion = new HashMap<Integer, OD>();
			
			hashTempUnion.putAll(carteA.getEspace().getEspaceDeDef());
			hashTempUnion.putAll(carteB.getEspace().getEspaceDeDef());
			
			nouvelleCarte.getEspace().setEspaceDeDef( hashTempUnion );
			
			return (true);
		}
		
		if (operateur.compareTo("soustraction") == 0) {
			
			// On realise ici l'operation de soustration des objets de l'espace de la carte B a ceux de la carte A
			// Espace( carteA ) - Espace ( carteB )
			
			// Construction d'une nouvelle hashSet
			HashSet<OD> hashtempSoustraction = new HashSet<OD>();
			// Ajout de tous les elements de l'espace de la carte A
			hashtempSoustraction.addAll(carteA.getEspace().getEspaceDeDef().values());
			// Retrait de tous les elements de l'espace de la carte B
			hashtempSoustraction.removeAll(carteB.getEspace().getEspaceDeDef().values());
			
			nouvelleCarte.getEspace().setEspaceDeDef(hashtempSoustraction);

			return (true);
		}
		
		if (operateur.compareTo("intersection") == 0) {
			
			// On realise ici l'operation d'intersection des objets de l'espace de la carte B a ceux de la carte A
			// Espace( carteA ) [inter] Espace ( carteB )
			
			// Construction d'une nouvelle hashSet
			HashSet<OD> hashtempIntersection = new HashSet<OD>();
			// Ajout de tous les elements de l'espace de la carte A
			hashtempIntersection.addAll(carteA.getEspace().getEspaceDeDef().values());
			// Retient tous les elements en commun avec l'espace de la carte B
			hashtempIntersection.retainAll(carteB.getEspace().getEspaceDeDef().values());
			
			nouvelleCarte.getEspace().setEspaceDeDef(hashtempIntersection);
			
			return (true);
		}
		
		if (operateur.compareTo("exclusion") == 0) {
			
			// On realise ici l'operation d'exclusion des objets de l'espace de la carte B a ceux de la carte A
			// Espace( carteA ) [exclu] Espace ( carteB ) = (A union B) - (A inter B ) 
			
			// Construction d'une nouvelle hashSet formee pour l'instant de l'union de A et de B
			HashSet<OD> hashtempExclusion = new HashSet<OD>();
			// Ajout de tous les elements de l'espace de la carte A
			hashtempExclusion.addAll(carteA.getEspace().getEspaceDeDef().values());
			// Ajout de tous les elements de l'espace de la carte B
			hashtempExclusion.addAll(carteB.getEspace().getEspaceDeDef().values());
			
			// Construction d'une nouvelle hashSet formee de l'intersection des deux ensembles
			HashSet<OD> hashtempIntersection = new HashSet<OD>();
			// Ajout de tous les elements de l'espace de la carte A
			hashtempIntersection.addAll(carteA.getEspace().getEspaceDeDef().values());
			// Retrait de tous les elements de l'espace de la carte B
			hashtempIntersection.retainAll(carteB.getEspace().getEspaceDeDef().values());
			
			hashtempExclusion.retainAll(hashtempIntersection);
			
			nouvelleCarte.getEspace().setEspaceDeDef(hashtempExclusion);
			
			return (true);
		}
		
		if (operateur.compareTo("complement") == 0) {
			
			HashSet<OD> hashtempEspaceTotal = new HashSet<OD>();
			HashSet<OD> hashtempEspaceRetain1 = new HashSet<OD>();
			HashSet<OD> hashtempEspaceRetain2 = new HashSet<OD>();
			
			// Espace total
			hashtempEspaceTotal = (HashSet<OD>) carteA.getGraphe().getToutesLesOD().values();
			
			// On enleve l'espace r de la carte A de l'espace t = retain1
			hashtempEspaceRetain1 = hashtempEspaceTotal;
			hashtempEspaceRetain1.retainAll(carteA.getEspace().getEspaceDeDef().values());
			
			// On enleve retain1 de l'espace t = retain 2 = complement de l'espace de def de la carte A
			hashtempEspaceRetain2 = hashtempEspaceTotal;
			hashtempEspaceRetain2.retainAll(hashtempEspaceRetain1);
	
			nouvelleCarte.getEspace().setEspaceDeDef(hashtempEspaceRetain2);
			
			return (true);
		}
		
		return (false);
	}
	
}