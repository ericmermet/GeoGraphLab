/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.cheminements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;

public class CheminLogit extends Thread {
	
	private Carte carte;
	
	private OD od;
	private Chemin pccCourant;
	private Double teta;
	
	public Thread thread;
	
	public CheminLogit(Carte carte, OD od, Double teta) {
		this.carte = carte;
		this.thread = new Thread(this);
		
		this.od = od;
		this.teta = teta;
		
		this.pccCourant = carte.getTousLesPCC().get(new Integer(this.od.getOrigine().hashCode() + this.od.getDestination().hashCode()));
		
		// Insertion dans le gestionnaire de couches
		// this.nouvelleCartePourNouvelleCouche.setColorLayer(new
		// Color(200,0,100));
		// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
		
		System.out.println("Chemin Logit pour :" + od.getOrigine().getNom() + " -> " + od.getDestination().getNom());
		
	}
	
	@Override
	public void run() {
		int i = 0;
		
		int nbOperation = this.carte.getGraphe().getNoeuds().size();
		
		HashMap<Integer, Arc> arcValides = new HashMap<Integer, Arc>();
		HashMap<Arc, Double> vraisemblanceDesArcs = new HashMap<Arc, Double>();
		// TreeMap<Double, Noeud> noeudOrdreCroissantEloignementAOrigine = new
		// TreeMap<Double, Noeud>();
		HashMap<Noeud, Double> noeudOrdreCroissantEloignementAOrigine = new HashMap<Noeud, Double>();
		HashMap<Noeud, Double> noeudOrdreCroissantEloignementADestination = new HashMap<Noeud, Double>();
		
		// Initialisation
		for (Arc iterArc : this.carte.getGraphe().getArcs()) {
			
			Noeud noeudDepart = null, noeudArrivee = null;
			OD odDepartArcAOrigine = null, odArriveeArcAOrigine = null, odDepartArcADestination = null, odArriveeArcADestination = null;
			double l1 = 0, l2 = 0, longueurCheminDepartArcAO = 0.0, longueurCheminArriveArcAO = 0.0, longueurCheminDepartArcAD = 0.0, longueurCheminArriveArcAD = 0.0;
			
			// Tests du noeud le plus proche de l'origine de l'od en terme de
			// distance sur le reseau
			try {
				
				// Si le noeud source de l'arc n'est pas le noeud origine de
				// l'od
				if (this.od.getOrigine() != iterArc.getSource()) {
					odDepartArcAOrigine = this.carte.getEspace().espaceDeDef.get(new Integer(this.od.getOrigine().hashCode() + iterArc.getSource().hashCode()));
					l1 = this.carte.getTousLesPCC().get(new Integer(odDepartArcAOrigine.hashCode())).getLongueur();
					// }else{ //sinon le depart est la
					// noeudDepart = this.od.getOrigine();
					// noeudArrivee = iterArc.getNoeudCible();
				}
				
				// Si le noeud cible de l'arc n'est pas le noeud de l'origine de
				// l'od
				if (this.od.getOrigine() != iterArc.getTarget()) {
					odArriveeArcAOrigine = this.carte.getEspace().espaceDeDef.get(new Integer(this.od.getOrigine().hashCode() + iterArc.getTarget().hashCode()));
					l2 = this.carte.getTousLesPCC().get(new Integer(odArriveeArcAOrigine.hashCode())).getLongueur();
					// }else{ //sinon le depart est l'origine de l'od et
					// l'arrivee est la cible de l'arc
					// noeudDepart = this.od.getOrigine();
					// noeudArrivee = iterArc.getNoeudCible();
				}
				
				// Affection des noeuds de depart et d'arrivee relativement %
				// distance reseau
				if (l1 < l2) {
					noeudDepart = iterArc.getSource();
					noeudArrivee = iterArc.getTarget();
				} else {
					noeudDepart = iterArc.getTarget();
					noeudArrivee = iterArc.getSource();
				}
				
				// Si le noeud source de l'arc est le noeud origine de l'od
				if (this.od.getOrigine() == iterArc.getSource()) {
					noeudDepart = iterArc.getSource();
					noeudArrivee = iterArc.getTarget();
				}
				
				// Si le noeud cible de l'od est le noeud origine de l'od
				if (this.od.getOrigine() == iterArc.getTarget()) {
					noeudDepart = iterArc.getTarget();
					noeudArrivee = iterArc.getSource();
				}
				
				// Si le noeud source de l'arc est le noeud destination de l'od
				if (this.od.getDestination() == iterArc.getSource()) {
					noeudDepart = iterArc.getTarget();
					noeudArrivee = iterArc.getSource();
				}
				
				// Si le noeud cible de l'arc est le noeud destination de l'od
				if (this.od.getDestination() == iterArc.getTarget()) {
					noeudDepart = iterArc.getSource();
					noeudArrivee = iterArc.getTarget();
				}
				
				// Affectation des ods
				if (this.od.getOrigine() != noeudDepart) {
					odDepartArcAOrigine = this.carte.getEspace().espaceDeDef.get(new Integer(this.od.getOrigine().hashCode() + noeudDepart.hashCode()));
					odArriveeArcAOrigine = this.carte.getEspace().espaceDeDef.get(new Integer(this.od.getOrigine().hashCode() + noeudArrivee.hashCode()));
				} else {
					arcValides.put(new Integer(iterArc.hashCode()), iterArc);
					iterArc.setSelected(true);
					continue;
				}
				
				// if( this.od.getDestination() != noeudArrivee){
				// odDepartArcADestination =
				// this.carte.getEspace().espaceDeDef.get(new
				// Integer(noeudDepart.hashCode() +
				// this.od.getDestination().hashCode()));
				// odArriveeArcADestination =
				// this.carte.getEspace().espaceDeDef.get(new
				// Integer(noeudArrivee.hashCode() +
				// this.od.getDestination().hashCode()));
				// }else{
				// arcValides.add(iterArc);
				// iterArc.setSelected(true);
				// continue;
				// }
				
				// 4 longueurs de chemins a calculer
				
				longueurCheminDepartArcAO = this.carte.getTousLesPCC().get(new Integer(odDepartArcAOrigine.hashCode())).getLongueur();
				longueurCheminArriveArcAO = this.carte.getTousLesPCC().get(new Integer(odArriveeArcAOrigine.hashCode())).getLongueur();
				
				// longueurCheminDepartArcAD =
				// this.carte.getTousLesPCC().get(new
				// Integer(odDepartArcADestination.hashCode())).getLongueur();
				// longueurCheminArriveArcAD =
				// this.carte.getTousLesPCC().get(new
				// Integer(odArriveeArcADestination.hashCode())).getLongueur();
				
				if (longueurCheminArriveArcAO >= longueurCheminDepartArcAO) {// &
					// longueurCheminDepartArcAD
					// >
					// longueurCheminArriveArcAD){
					arcValides.put(new Integer(iterArc.hashCode()), iterArc);
					
					iterArc.setNoeudDepartPourCheminLogit(noeudDepart);
					iterArc.setNoeudArriveePourCheminLogit(noeudArrivee);
					
					// Vraisemblance des arcs
					double vraisemblanceDeLarc = Math.exp(-this.teta.doubleValue() * (longueurCheminArriveArcAO - longueurCheminDepartArcAO - iterArc.getPoids()));
					vraisemblanceDesArcs.put(iterArc, new Double(vraisemblanceDeLarc));
					
					// Stockage des noeuds dans un treemap classe
					// automatiquement a la distance a l'origine
					noeudOrdreCroissantEloignementAOrigine.put(noeudDepart, new Double(longueurCheminDepartArcAO));
					noeudOrdreCroissantEloignementADestination.put(noeudArrivee, new Double(longueurCheminArriveArcAD));
					
					iterArc.setSelected(true);
				}
			} catch (Exception e) {
				System.out
						.println("Debug Init Logit " + " arc : " + iterArc.getNom() + " Source Arc -> Origine " + odDepartArcAOrigine + " Cible Arc -> Origine" + odArriveeArcAOrigine + " Source Arc -> Destination " + odDepartArcADestination + " Cible Arc -> Destination " + odArriveeArcADestination

						);
			}
		}
		
		// Tri des noeuds dans les Hashmaps
		// Ajout des entrees de la map a une liste pour le hashMap noeud a
		// l'origine
		final List<Map.Entry<Noeud, Double>> noeudPlusProchesDeOrigine = new ArrayList<Map.Entry<Noeud, Double>>(noeudOrdreCroissantEloignementAOrigine.entrySet());
		
		// Utilisation du sort de collections et redefinition de la methode
		// compare
		Collections.sort(noeudPlusProchesDeOrigine, new Comparator<Map.Entry<Noeud, Double>>() {
			
			@Override
			public int compare(Entry<Noeud, Double> e1, Entry<Noeud, Double> e2) {
				return e1.getValue().compareTo(e2.getValue());
			}
		});
		
		// Ajout des entrees de la map a une liste pour le hashMap noeud a la
		// destination
		final List<Map.Entry<Noeud, Double>> noeudPlusProchesDeDestination = new ArrayList<Map.Entry<Noeud, Double>>(noeudOrdreCroissantEloignementADestination.entrySet());
		
		Collections.sort(noeudPlusProchesDeDestination, new Comparator<Map.Entry<Noeud, Double>>() {
			
			@Override
			public int compare(Entry<Noeud, Double> e1, Entry<Noeud, Double> e2) {
				return e1.getValue().compareTo(e2.getValue());
			}
		});
		
		// Forward pass
		for (Entry<Noeud, Double> iterNoeud : noeudPlusProchesDeOrigine) {
			
			List<Noeud> listeDesVoisins = null, listeDesSuccesseurs = null, listeDesPredecesseurs = null;
			
			// if( this.carte.getGraphe().getClass() == UndirectedGraph.class){
			// listeDesPredecesseurs =
			// Graphs.predecessorListOf(this.carte.getGraphe(),
			// iterNoeud.getKey());
			// listeDesSuccesseurs =
			// Graphs.successorListOf(this.carte.getGraphe(),
			// iterNoeud.getKey());
			// }
			if (this.carte.getGraphe().getClass() == DirectedGraph.class) {
				listeDesVoisins = Graphs.neighborListOf(this.carte.getGraphe(), iterNoeud.getKey());
				
				// On recupere les voisins du noeud considere et donc les arcs
				for (Noeud noeud : listeDesVoisins) {
					Arc arcCourant = this.carte.getGraphe().getEdge(iterNoeud.getKey(), noeud);
					
					if (arcValides.containsKey(arcCourant)) {
						
						double w = vraisemblanceDesArcs.get(arcCourant).doubleValue();
						
						// l'arc est un arc valide
						if (iterNoeud.getKey() == arcCourant.getNoeudDepartPourCheminLogit()) {
							arcCourant.setPoidsLogit(w);
						} else {
							
						}
					} else {
						// l'arc n'est pas valide
						arcCourant.setPoidsLogit(0.0);
					}
					
				}
			}
			
		}
		
		// Backward pass
		
	}
}