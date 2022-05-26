/**
 * GeoGraphLab - eric - Cogit / IGN - Graph-it
 * 2007 - 2014
 *
 * EloignementMoyenTest.java in fr.ign.cogit.geographlab.algo.indicateurs
 * 
 */

package fr.ign.cogit.geographlab.algo.indicateurs;


import java.awt.Color;

import java.util.Collection;
import java.util.Date;

import org.jgrapht.traverse.ClosestFirstIterator;

import fr.ign.cogit.geographlab.cheminements.APSPFloydWarshallTest;
import fr.ign.cogit.geographlab.cheminements.Chemin;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.exploration.Espace;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.lang.Messages;
import fr.ign.cogit.geographlab.util.Timer;

public class EloignementMoyenTest extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche;
	
	private Espace espace;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	public Thread thread;
	
	public EloignementMoyenTest(Carte carte, int typeChemin) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, Messages.getString("EloignementMoyen.0"), Messages.getString("EloignementMoyen.1") + new Date().getTime()); //$NON-NLS-1$ //$NON-NLS-2$
		this.thread = new Thread(this);
		
		// Insertion dans le gestionnaire de couches
		this.nouvelleCartePourNouvelleCouche.setColorLayer(Color.YELLOW);
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
		
		if (this.nouvelleCartePourNouvelleCouche.getTousLesPCC().size() == 0) {
			System.out.println(Messages.getString("EloignementMoyen.2")); //$NON-NLS-1$
			APSPFloydWarshallTest allShortestPath = new APSPFloydWarshallTest(this.nouvelleCartePourNouvelleCouche);
			allShortestPath.run();
		}
		
		this.espace = this.nouvelleCartePourNouvelleCouche.getEspace();
		
		int i = 0;
		
		//		Set<Noeud> tousLesNoeuds = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds();
		
		Timer.tic();
		
		Collection<OD> espaceSet = this.espace.getEspaceDeDef().values();
		int nbOperation = espaceSet.size();
		
		for (OD iterOD : espaceSet) {
			double eloignementMoyenDuNoeud = 0.0;
			
			Chemin localPCC = this.nouvelleCartePourNouvelleCouche.getTousLesPCC().get(iterOD.hashCode());
			
			eloignementMoyenDuNoeud = 	localPCC.getStartVertex().getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) +
					localPCC.getLongueur()/(this.espace.espaceDeDef.size()*iterOD.getPonderation());
			
			localPCC.getStartVertex().setIndicateurValeur(	this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(),
					eloignementMoyenDuNoeud);
			
			this.max = Math.max(eloignementMoyenDuNoeud, this.max);
			this.min = Math.min(eloignementMoyenDuNoeud, this.min);
			
			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(i++, nbOperation);
		}
		
		//		for (OD iterOD : espaceSet) {
		//			double eloignementMoyenDuNoeud = 0.0,eloignementMoyenDuNoeud2 = 0.0;
		//			
		//			ClosestFirstIterator<Noeud, Arc> spanningTree = new ClosestFirstIterator<Noeud, Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe().asUndirectedView(), iterOD.getOrigine());
		//			ClosestFirstIterator<Noeud, Arc> spanningTree2 = new ClosestFirstIterator<Noeud, Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe().asUndirectedView(), iterOD.getDestination());
		//			
		//			eloignementMoyenDuNoeud = spanningTree.getShortestPathLength(iterOD.getDestination())/espaceSet.size();
		//			eloignementMoyenDuNoeud2 = spanningTree2.getShortestPathLength(iterOD.getOrigine())/espaceSet.size();
		//			
		//			eloignementMoyenDuNoeud = iterOD.getOrigine().getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) + eloignementMoyenDuNoeud;
		//			
		//			iterOD.getOrigine().setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), eloignementMoyenDuNoeud);
		//			
		//			this.max = Math.max(eloignementMoyenDuNoeud, this.max);
		//			this.min = Math.min(eloignementMoyenDuNoeud, this.min);
		//			
		//			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(i++, nbOperation);
		//		}
		
		//		for (Noeud iterNoeudOrigine : tousLesNoeuds) {
		//			double somme = 0.0;
		//			double eloignementMoyenDuNoeud = 0.0;
		//			
		////			ClosestFirstIterator<Noeud, Arc> treeNode1 = new ClosestFirstIterator<Noeud, Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe().getUndirectedView(), iterNoeudOrigine, Double.POSITIVE_INFINITY);
		//			BellmanFordShortestPath<Noeud, Arc> bfsp = new BellmanFordShortestPath<Noeud, Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe().asUndirectedView(), iterNoeudOrigine);
		//			
		//			for (Noeud iterNoeudDestination : tousLesNoeuds) {
		//				
		//				if(iterNoeudOrigine != iterNoeudDestination){
		//				
		////					DijkstraShortestPath<Noeud, Arc> dj = new DijkstraShortestPath<Noeud, Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe().getUndirectedView(), iterNoeudOrigine, iterNoeudDestination);
		//					somme += bfsp.getCost(iterNoeudDestination);
		//				
		//				}
		//			
		//			}
		//			
		////			for (Noeud iterNoeudDestination : tousLesNoeuds) {
		////				if (iterNoeudOrigine != iterNoeudDestination) {				
		////					PCC plusCourtChemin = new PCC(this.nouvelleCartePourNouvelleCouche.getGraphe(), new OD(iterNoeudOrigine, iterNoeudDestination), 1, Constantes.typePCC,
		////							this.nouvelleCartePourNouvelleCouche.parent.carte.variablesDeCarte.afficheGrapheNonDirige);
		////					somme += plusCourtChemin.getLongueur();
		////				}
		////			}
		//			eloignementMoyenDuNoeud = somme / tousLesNoeuds.size();
		//			this.max = Math.max(eloignementMoyenDuNoeud, this.max);
		//			this.min = Math.min(eloignementMoyenDuNoeud, this.min);
		//			
		//			iterNoeudOrigine.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(eloignementMoyenDuNoeud));
		//			
		//			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(i++, nbOperation);
		//		}
		
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs())
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		
		// Afichage pour debug
		//		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
		//			Debug.printDebug("Eloignement Moyen Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		//		}
		//		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
		//			Debug.printDebug("Eloignement Moyen Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		//		}
		System.out.println(this.min + Messages.getString("EloignementMoyen.3") + this.max); //$NON-NLS-1$
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
		System.out.println(Messages.getString("EloignementMoyen.4") + Timer.tac()); //$NON-NLS-1$
		
		// Mise a jour de la legende et de liens couleurs - legende
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setEchelle(this.min, this.max, this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().getIntervalles());
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
		this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String(Messages.getString("EloignementMoyen.5")); //$NON-NLS-1$
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		
	}
}