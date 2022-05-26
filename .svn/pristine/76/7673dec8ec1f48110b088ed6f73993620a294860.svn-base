/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.indicateurs;


import java.awt.Color;
import java.util.Date;
import java.util.HashSet;

import javax.swing.JOptionPane;

import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.cheminements.PCC;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.test.Debug;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

public class KClosestFirstTreeTEst extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche;
	private String nomIndicateur = new String("KClosestFirstTree" + new Date().getTime());
	
	private double min = 0;
	private double max = Double.MIN_VALUE;
	
	public Thread thread;
	
	public KClosestFirstTreeTEst(Carte carte) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "NomCarte", this.nomIndicateur);
		this.thread = new Thread(this);
		
		// Insertion dans le gestionnaire de couches
//		this.nouvelleCartePourNouvelleCouche.setColorLayer(new Color(10, 60, 100));
		// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
		
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		}
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		}
		
	}
	
	@Override
	public void run() {
		
		int nbOperation = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds().size();
		
		int nbElementsSelectionnes = this.nouvelleCartePourNouvelleCouche.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects.size();
		
		if (nbElementsSelectionnes > 0) {
			// Insertion dans le gestionnaire de couches apres test reussi des
			// deux objets
			this.nouvelleCartePourNouvelleCouche.setColorLayer(new Color(200, 0, 100));
			
			HashSet<Noeud> noeudsSel = new HashSet<Noeud>();
			
			for (ObjetGraphique objGraphique : this.nouvelleCartePourNouvelleCouche.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects) {
				if (objGraphique.getType() == ConstantesApplication.typeVertex){
					noeudsSel.add(((NoeudGraphique) objGraphique).getNoeudTopologique());
//					System.out.println("S�lection de " + noeudsSel.[i].getNom());
//					if(this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeud(noeudSel[i].getNom()) != null)
//						System.out.println("Pr�sent dans le graphe ");
//					i++;
					((NoeudGraphique) objGraphique).setColor(Color.BLUE);
					this.nouvelleCartePourNouvelleCouche.timerClignotantObjetSelectionnes.addObjetClignotant(((NoeudGraphique) objGraphique));
				}
			}
			this.max = noeudsSel.size();
			
//			Noeud[] noeudSel = new Noeud[2];
//			int test=0;
//			
//			for( Noeud iterNoeud : noeudsSel){
//				System.out.println("S�lection de " + iterNoeud.getNom());
//				noeudSel[test++] = iterNoeud;
//				if(this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeud(iterNoeud.getNom()) != null)
//					System.out.println("Pr�sent dans le graphe ");
//			}
//			
//			PCC pcc = new PCC(this.nouvelleCartePourNouvelleCouche.getGraphe(), new OD(noeudSel[0], noeudSel[1]), 1.0, 0);
//			System.out.println(pcc.toString());
			
			for( Noeud noeudOrigine : noeudsSel){
//				ClosestFirstIterator<Noeud, Arc> spanningTree = new ClosestFirstIterator<Noeud, Arc>( this.nouvelleCartePourNouvelleCouche.getGraphe(), noeudOrigine );
				
//				while(spanningTree.hasNext()){
//					
//					Noeud noeudDestinationTemp = spanningTree.next();
//					int tempValeurNoeud = noeudDestinationTemp.getValeurPourIndicateur(this.nomIndicateur).intValue() + 1;
//					
//					if( noeudDestinationTemp != noeudOrigine ){
//						
//					}
//					
//				}
				
				for( Noeud noeudDestination : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()){
					if( noeudDestination != noeudOrigine ){
						
						int tempValeurNoeud = noeudDestination.getValeurPourIndicateur(this.nomIndicateur).intValue() + 1;
						noeudDestination.setIndicateurValeur(this.nomIndicateur, new Double(tempValeurNoeud));
						
						PCC lePCC = new PCC(this.nouvelleCartePourNouvelleCouche.getGraphe(), new OD(noeudOrigine, noeudDestination), 1.0, 0);
						for( Arc arcChemin : lePCC.getEdgeList()){
							int tempValeurArc = arcChemin.getValeurPourIndicateur(this.nomIndicateur).intValue() + 1;
							arcChemin.setIndicateurValeur(this.nomIndicateur, new Double(tempValeurArc));
						}
					}
				}
			}
			
		}else{
			JOptionPane.showMessageDialog(this.nouvelleCartePourNouvelleCouche.fenetrePrincipale, "Il faut selectionner au moins un noeud");
			return;
		}
		
//		 Affichage pour debug
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			Debug.printDebug("Centralite Degre Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			Debug.printDebug("Centralite Degre Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		System.out.println(this.min + " " + this.max);
		
		// Mise a jour de la legende et de liens couleurs - legende
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setEchelle(this.min, this.max, this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().getIntervalles());
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
		this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		// this.nouvelleCartePourNouvelleCouche.parent.repaint();
	}
}