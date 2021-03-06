/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;


import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.visu.ArcGraphique;

public class DetectionImpasses extends Thread {
	
	private Carte carte;
	private Graphe graphe;
	
	private List<ArcGraphique> listeImpasses;
	
	int nbPassDetectionSimple = 20;
	
	public Thread thread;
	
	public DetectionImpasses(Carte carte) {
		this.thread = new Thread(this);
		this.carte = carte;
		this.graphe = this.carte.getGraphe();
	}
	
	@Override
	public void run() {
		
		System.out.println("Détection simple");
		for (int i = 0; i < nbPassDetectionSimple; i++) {
			System.out.println("Pass "+ i);
			detectionSimple();
		}
		
//		System.out.println("Détection par composantes connexes");
//		detectionComposantesConnexes();
		
	}
	
	public List<ArcGraphique> detectionSimple() {
		
		// Methode de detection des noeuds de degre 1
		this.carte.parent.listOfSelectedObjects.clear();
		this.listeImpasses = new ArrayList<ArcGraphique>();
		
		int cpt=0;
		for (Noeud iterNoeud : this.graphe.getNoeuds()) {

			if (Graphs.neighborListOf(this.graphe, iterNoeud).size() == 1) {

				cpt++;
				Noeud noeudVoisin = Graphs.neighborListOf(this.graphe, iterNoeud).get(0);
				
				if( this.graphe.getEdge(iterNoeud, noeudVoisin) != null) {
					this.listeImpasses.add(this.graphe.getEdge(iterNoeud, noeudVoisin).getArcGraphique());
				}
				if( this.graphe.getEdge(noeudVoisin, iterNoeud ) != null) {
					this.listeImpasses.add(this.graphe.getEdge(noeudVoisin, iterNoeud ).getArcGraphique());
				}
				
				iterNoeud.getNoeudGraphique().setSelected(true);

				this.carte.parent.listOfSelectedObjects.add(iterNoeud.getNoeudGraphique());
			}
			
		}
		
			for (ArcGraphique arc : this.listeImpasses) {
					arc.setSelected(true);
//					System.out.println("select " + arc.getNom());
					this.carte.parent.listOfSelectedObjects.add(arc);
				}
		
		System.out.println("nb impasses = " + cpt);
		
		this.carte.getVueDuGraphe().effaceSelection();
		return this.listeImpasses;
	}
	
	public List<ArcGraphique> detectionComposantesConnexes() {
		
		// Methode par augmentation +1 de composantes connexes
		
		Calendar c1 = Calendar.getInstance();
		
		long debut = c1.getTimeInMillis();
		
		ConnectivityInspector<Noeud, Arc> connexiteInitiale;
		connexiteInitiale = new ConnectivityInspector<Noeud, Arc>(this.graphe);
		int nbComposantesConnexes = connexiteInitiale.connectedSets().size();
		
		System.out.println("nb composantes connexes initiales = " + nbComposantesConnexes);
		
		Graphe copieGraphe = this.graphe.clone();
		ConnectivityInspector<Noeud, Arc> testDeConnexite;
		
		int nbArcs = this.graphe.getNombreArcs();
		int i = 0;
		
		for (Arc arc : this.graphe.getArcs()) {
			
			copieGraphe.removeEdge(arc);
			
			testDeConnexite = new ConnectivityInspector<Noeud, Arc>(copieGraphe);
			
			int nbComposantesConnexesApresSuppressionArc = testDeConnexite.connectedSets().size();
			
			// Si le reseau est deconnecte, le nombre de composantes connexes
			// augmente
			if (nbComposantesConnexesApresSuppressionArc == nbComposantesConnexes + 1) {
				
				Noeud noeudSource = arc.getSource();
				Noeud noeudCible = arc.getTarget();
				
				// On recupere les noeuds des deux composantes connexes de part
				// et d'autre de l'arc supprime
				Set<Noeud> listeDesNoeudsComposanteConnexeSource = testDeConnexite.connectedSetOf(noeudSource);
				Set<Noeud> listeDesNoeudsComposanteConnexeCible = testDeConnexite.connectedSetOf(noeudCible);
				
				Set<Noeud> NoeudComposanteConnexeMinimal = null;
				
				// Heuristique
				if (listeDesNoeudsComposanteConnexeSource.size() > listeDesNoeudsComposanteConnexeCible.size()) {
					NoeudComposanteConnexeMinimal = listeDesNoeudsComposanteConnexeCible;
				} else {
					NoeudComposanteConnexeMinimal = listeDesNoeudsComposanteConnexeSource;
				}
				
				//Selection des noeuds de la composante connexe minimal selectionnee
				for (Noeud noeud : NoeudComposanteConnexeMinimal) {
					noeud.setSelected(true);
					this.carte.parent.listOfSelectedObjects.add(noeud.getNoeudGraphique());
				}
				
				// Selection des arcs graphiques incidents aux noeuds selectionnes
				// List<Arc> listeArc = new ArrayList<Arc>();
				for (Arc arcSel : this.graphe.getArcs()) {
					for (Noeud noeud : NoeudComposanteConnexeMinimal) {
						if (Graphs.testIncidence(this.graphe, arcSel, noeud)) {
							// Alors selectionner l'arc
							// this.listeImpasses.add(arcSel);
							arcSel.setSelected(true);
							this.carte.parent.listOfSelectedObjects.add(arcSel.getArcGraphique());
						}
					}
				}
				
			}
			
			copieGraphe.addArc(arc);
			System.gc();
			
			if (i++ % 100 == 0)
				System.out.println(i + " / " + nbArcs);
			
		}
		
		Calendar c2 = Calendar.getInstance();
		
		long tempsExecution = c2.getTimeInMillis() - debut;
		;
		System.out.println("Temps d'execution (ms):" + tempsExecution);
		
		// Insertion dans le gestionnaire de couches
		Carte nouvelleCartePourNouvelleCouche = (Carte) this.carte.clone();
		nouvelleCartePourNouvelleCouche.setNom(nouvelleCartePourNouvelleCouche.getNom() + "_Impasses");
		nouvelleCartePourNouvelleCouche.setColorLayer(Color.MAGENTA);
		// this.carte.parent.couchesDeCartes.ajouterUneCoucheCarte(this.carte);
		this.carte.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		return this.listeImpasses;
	}
	
}
