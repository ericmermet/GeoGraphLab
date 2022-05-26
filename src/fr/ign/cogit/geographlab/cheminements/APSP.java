/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.cheminements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.jgrapht.Graphs;


import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.exploration.Espace;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;


import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.traverse.ClosestFirstIterator;

public class APSP extends Thread {
	
	private Carte carteEntrante, nouvelleCartePourNouvelleCouche;
	private Espace espace;
	
	private List<Arc> edgeList;
	
	public Thread thread;
	
	public APSP(Carte carte) {
		
		ConnectivityInspector<Noeud, Arc> testDeConnexite = new ConnectivityInspector<Noeud, Arc>(carte.getGraphe());
		Double nbrCompConnex = new Double(testDeConnexite.connectedSets().size());
		
		if( nbrCompConnex.doubleValue() > 1 ) {
			//Affichage conseil filtrage des composantes connexes
			JOptionPane.showMessageDialog(carte.fenetrePrincipale, "Trop de composantes connexes dans le graphe !\n Utilisez l'outil de pré-traitement approprié.");
			System.out.println("Fin APSP");
			return;
		}
		
		//Clear espace & populate OD collection 
		if( carte.getEspace().espaceDeDef.size() == 0 ) {
			System.out.println("Populate OD Space");
			carte.getEspace().populate(carte.variablesDeCarte.afficheGrapheNonDirige);
		}else{
			System.out.println("OD Space already exist");
		}
		
		this.carteEntrante = carte;
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "Centralite_Empan", "CentraliteEmpan" + new Date().getTime());
		
		this.thread = new Thread(this);
		
		this.espace = carte.getEspace();
		this.nouvelleCartePourNouvelleCouche.setEspace(this.espace);
		//FIXME Attention au pointeur vers un espace deja defini lors de la reduction d'espace relationnel !
		//FIXME Ici ca ne marchera pas
		
		this.nouvelleCartePourNouvelleCouche.setColorLayer(Color.MAGENTA);
		// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
	}
	
	@Override
	public void run() {
		
		int nbOdsCalculeesTotales = 0, nbOdsCalculeesParArbreCouvrant = 0;
		
		System.out.println("Debut APSP");
		
		//Test de connexité du graphe
		ConnectivityInspector<Noeud, Arc> testDeConnexite = new ConnectivityInspector<Noeud, Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe());
		Double nbrCompConnex = new Double(testDeConnexite.connectedSets().size());
		if( nbrCompConnex.doubleValue() > 1 ) {
			//Affichage conseil filtrage des composantes connexes
			JOptionPane.showMessageDialog(this.nouvelleCartePourNouvelleCouche.fenetrePrincipale, "Trop de composantes connexes dans le graphe !\n Utilisez l'outil de pré-traitement approprié.");
			System.out.println("Fin APSP");
			return;
		}
		
		this.espace.clearEspaceDeDef();
		this.nouvelleCartePourNouvelleCouche.getTousLesPCC().clear();
		
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), 0.0);
		}
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			this.nouvelleCartePourNouvelleCouche.getGraphe().setPoidsArc(iterArc, iterArc.getValeurPourIndicateur(this.carteEntrante.getNomIndicateurCourant()).doubleValue());
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), 0.0);
		}
		
		Calendar c1 = Calendar.getInstance();
		long debut = c1.getTimeInMillis();
		
		int nbNoeud = this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds().size();
		
		int i = 0, cpt = 0;
		int nbArbreCouvrant = 0, nbArbresCouvrantsUtiles = 0;
		int nbOperation = nbNoeud * (nbNoeud - 1) / 2;
		
		// Pour chaque noeud on cree un arbre couvrant
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			
			cpt++;
			// System.out.println("Noeud n° " + cpt);
			System.out.print(iterNoeud.getNom());
			
			// System.out.println("OD en cours = " + localOD.getNom());
			
			// if( !localOD.isPccCalcule()){
			
			// Jusqu'a ce qu'on obtienne le nombre suffisant d'OD et PCC
			// calcules
			if (this.nouvelleCartePourNouvelleCouche.getTousLesPCC().size() >= nbOperation) {
				System.out.println("nd Chemins atteint: " + this.nouvelleCartePourNouvelleCouche.getTousLesPCC().size());
				break;
			}
			
			// System.out.println("On part de " + iterNoeud.getNom() +
			// "nom n°" + i++ + " / " + nbNoeud);
			// System.out.println("nombre d'od calculees " +
			// this.espace.getEspaceDeDef().size());
			nbArbreCouvrant++;
			
			ClosestFirstIterator<Noeud, Arc> spanningTree = null;
			
			if( this.nouvelleCartePourNouvelleCouche.variablesDeCarte.afficheGrapheNonDirige == false ){
				spanningTree = new ClosestFirstIterator<Noeud, Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe(), iterNoeud, Double.POSITIVE_INFINITY);
			}else{
				spanningTree = new ClosestFirstIterator<Noeud, Arc>(this.nouvelleCartePourNouvelleCouche.getGraphe().asUndirectedView(), iterNoeud, Double.POSITIVE_INFINITY);
			}
			
			// System.out.println("arbre couvrant n°" + nbArbreCouvrant);
			
			// Inversion de la liste des noeuds de l'arbre couvrant
			ArrayList<Noeud> listeNoeudsInversees = new ArrayList<Noeud>();
			while (spanningTree.hasNext()) {
				listeNoeudsInversees.add(spanningTree.next());
			}
			
			Collections.reverse(listeNoeudsInversees);
			listeNoeudsInversees.remove(listeNoeudsInversees.size() - 1);
			
			// Un noeud dans le vide car identique au premier
			// Collections.reverse(spanningTree);
			// Noeud unNoeudDestinattion = spanningTree.next();
			
			Noeud unNoeudDestination;
			
			// while ( spanningTree.hasNext()) {
			nbOdsCalculeesParArbreCouvrant = 0;
			for (Noeud iterNoeudDeLarbreCouvrant : listeNoeudsInversees) {
				
				// unNoeudDestinattion = spanningTree.next();
				
				unNoeudDestination = iterNoeudDeLarbreCouvrant;
				
				OD nouvelleOD = new OD(iterNoeud, unNoeudDestination);
				
				// OD retrouveOD = this.espace.getOD(
				// localOD.getOrigine().getNom() ,
				// unNoeudDestinattion.getNom());
				
				// On ajoute l'od, si elle n'est pas presente (true) dans la
				// hashset alors on fait le calcul sinon l'od est deja
				// calcule
				// if( !this.espace.getEspaceDeDef().contains(nouvelleOD) ){
				
				// int straightHashCode = nouvelleOD.hasCodeSpecial();
				// int a = nouvelleOD.hasCodeSpecial() / 100000;
				// int b = nouvelleOD.hasCodeSpecial() - a*100000;
				//				
				// int reversedHashCodeInverse = b*100000 + a;
				
				// if( !this.espace.getEspaceDeDef().containsKey(new
				// Integer(straightHashCode)) |
				// !this.espace.getEspaceDeDef().containsKey(new
				// Integer(reversedHashCodeInverse))){
				
				// if( !GestionnaireODs.idsODs.containsKey(new
				// Integer(straightHashCode)) |
				// !GestionnaireODs.idsODs.containsKey(new
				// Integer(reversedHashCodeInverse)) ){
				
				// String hashCodeStringStraight = nouvelleOD.hasCodeSpecial();
				// String[] OetD = nouvelleOD.hasCodeSpecial().split("!");
				// String hashCodeStringreverse = OetD[1] + "!" + OetD[0];
				
				// int hashCodeStringStraight = nouvelleOD.hashCode();
				
				// if(
				// !this.espace.getEspaceDeDef().containsKey(hashCodeStringStraight)
				// |
				// !this.espace.getEspaceDeDef().containsKey(hashCodeStringreverse)){
				
				if (!this.espace.getEspaceDeDef().containsKey(new Integer(nouvelleOD.hashCode()))) {
					
					createEdgeList(this.nouvelleCartePourNouvelleCouche.getGraphe(), spanningTree, unNoeudDestination);
					
					Set<Chemin> odExtraitesInBetween = extractBetweenPaths(nouvelleOD, this.edgeList);
					
					nbOdsCalculeesParArbreCouvrant += odExtraitesInBetween.size();
					
//					nbOdsCalculeesTotales += nbOdsCalculeesParArbreCouvrant;
					
					// System.out.print(" nb extraites dans cet arbre = " +
					// odExtraitesInBetween.size());
					
					// System.out.println(nbOdCalcule + " / " +
					// nbNoeud*(nbNoeud-1)/2 );
					
				}// else{
				// System.out.println(iterNoeud.getNom() + " " +
				// unNoeudDestinattion.getNom() + " est deja calcule ! ;)");
				// }
			}
			// System.out.println("TOTAL NB OD Calcule dans cette Boucle: " +
			// nbODCalculeDansCetteBoucle);
			// nbODCalculeDansCetteBoucle=0;
			// }
			// System.out.println("		Nombre d'ods extraites pour ce noeud : " +
			// nbOdsCalculeesParNoeud);
			// System.out.println("		Nombre d'ods totales: " +
			// nbOdsCalculeesTotales);
			System.out.print(", odCalc = " + nbOdsCalculeesParArbreCouvrant);
			nbOdsCalculeesTotales += nbOdsCalculeesParArbreCouvrant;
			System.out.print(", odTot = " + nbOdsCalculeesTotales);
			System.out.println();
			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOdsCalculeesTotales, nbOperation);
		}
		
		Calendar c2 = Calendar.getInstance();
		
		long tempsExecution = c2.getTimeInMillis() - debut;
		System.out.println("Temps d'execution (ms):" + tempsExecution);
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
		// Stockage des PCC dans la carte mere
		this.nouvelleCartePourNouvelleCouche.setTousLesPCC(this.nouvelleCartePourNouvelleCouche.getTousLesPCC());
		int nbCalcule = 0;
		int nbNonCalcule = 0;
		
		// /////////////
		// // TESTS ////
		// /////////////
		
		// System.out.println("OD manquantes");
		// Noeud[] tabNoeuds = new
		// Noeud[this.carte.getGraphe().getNoeuds().size()];
		// int indexNoeud=0;
		// for (Noeud iterNoeud: this.carte.getGraphe().getNoeuds()){
		// tabNoeuds[indexNoeud++]=iterNoeud;
		// }
		//
		// for (int j = 0; j < tabNoeuds.length; j++) {
		// for (int k = j+1; k < tabNoeuds.length; k++) {
		// OD localOD = new OD(tabNoeuds[j],tabNoeuds[k]);
		// OD localODinverse = new OD(tabNoeuds[k],tabNoeuds[j]);
		// if( this.espace.getEspaceDeDef().containsKey(new
		// Integer(localOD.hashCode())) ||
		// this.espace.getEspaceDeDef().containsKey(new
		// Integer(localODinverse.hashCode())) ){
		// // System.out.println(b++ + "  " + tabNoeuds[j].getNom() + " - " +
		// tabNoeuds[k].getNom() + " ok ");
		// // System.out.println();
		// nbCalcule++;
		// }else{
		// nbNonCalcule++;
		// System.out.println(tabNoeuds[j].getNom() + " - " +
		// tabNoeuds[k].getNom() + " n'est pas calculee ");
		// }
		// }
		// }
		//
		// System.out.println("OD identiques ???");
		// int nbIdentique = 1;
		// OD[] tabOD = new OD[this.espace.getEspaceDeDef().size()];
		// int indexOD=0;
		// for (OD iterOD: this.espace.getEspaceDeDef().values()){
		// tabOD[indexOD++]=iterOD;
		// }
		// for (int j = 0; j < tabOD.length; j++) {
		// for (int k = 0; k < tabOD.length; k++) {
		// if( tabOD[j].hashCode() == tabOD[k].hashCode() && j!=k)
		// System.out.println("OD identique :" + tabOD[j].getNom() +
		// " / hashcode = " + tabOD[j].hashCode() + " et "
		// + tabOD[k].getNom() + " / hashcode = " + tabOD[k].hashCode() +
		// " num " + nbIdentique++);
		// }
		// }
		//
		// System.out.println("Verif 2: OD identiques ?");
		// for( Noeud iterNoeud1: this.carte.getGraphe().getNoeuds()){
		// for( Noeud iterNoeud2: this.carte.getGraphe().getNoeuds()){
		// if( iterNoeud1.getNom().compareTo(iterNoeud2.getNom()) != 0) {
		// if( this.espace.getOD(iterNoeud1, iterNoeud2) != null ){
		// // System.out.println("Existe dans l'espace : " + iterNoeud1.getNom()
		// + " - " + iterNoeud2.getNom() + " ou " + iterNoeud2.getNom() + " - "
		// + iterNoeud1.getNom());
		// }else{
		// System.out.println("N'existe pas dans l'espace : " +
		// iterNoeud1.getNom() + " - " + iterNoeud2.getNom() + " ou " +
		// iterNoeud2.getNom() + " - " + iterNoeud1.getNom());
		// }
		// }
		
		//
		// }
		// }
		
		// System.out.println("Chemins identiques ???");
		// for (Chemin iterPCC1 : this.carte.getTousLesPCC().values()) {
		// for (Chemin iterPCC2 : this.carte.getTousLesPCC().values()) {
		// if( iterPCC1.equals(iterPCC2))
		// System.out.println("Chemin identique :" + iterPCC1.getNom() +
		// " / hashcode = " + iterPCC1.hashCode() + " et " + iterPCC2.getNom() +
		// " / hashcode = " + iterPCC2.hashCode());
		// }
		// }
		
		System.out.println("nb OD calculee " + nbCalcule + ", nb non calculee " + nbNonCalcule);
		System.out.println("nb arbres couvrants :" + nbArbreCouvrant);
		System.out.println("taille de tabod " + this.espace.getEspaceDeDef().size());
		System.out.println("Taille de tabChemin " + this.nouvelleCartePourNouvelleCouche.getTousLesPCC().size());
		
		System.out.println("Fin APSP");
		System.gc();
		
		this.nouvelleCartePourNouvelleCouche.getGraphe().setGrapheChange();
		
		// Mise a jour des couleurs par rapport aux valeurs
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		// if( !local ){
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
		this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		// }
		
	}
	
	private Set<Chemin> extractBetweenPaths(OD odDeDepart, List<Arc> listeArcs) {
		
		Set<Chemin> cheminsCalculesImplicitement = new HashSet<Chemin>();
		
		int nombreArcs = this.edgeList.size();
		
		List<Arc> listeArcsDynamique;
		
		// System.out.println("On travaille sur " +
		// odDeDepart.getOrigine().getNom() + " -> " +
		// odDeDepart.getDestination().getNom());
		// System.out.println("Il y a " +
		// (listeArcs.size()*listeArcs.size()-1)/2 +
		// " chemins potentiels a calculer dans cette OD");
		
		for (int indiceDebut = 0; indiceDebut < listeArcs.size(); indiceDebut++) {
			listeArcsDynamique = new ArrayList<Arc>();
			for (int indiceFin = indiceDebut; indiceFin < listeArcs.size(); indiceFin++) {
				double longueurChemin = 0.0;
				for (int index = indiceDebut; index < indiceFin + 1; index++) {
					
					listeArcsDynamique.add(listeArcs.get(index));
					longueurChemin += listeArcs.get(index).getPoids();
					
				}
				
				// for( Arc iterArcs : listeArcsDynamique)
				// System.out.print(iterArcs.getNom() + " -> ");
				// System.out.println("");
				
				// On cree le chemin avant
				Chemin nouveauCheminImplicite = new Chemin(this.nouvelleCartePourNouvelleCouche.getGraphe(), 1.0, longueurChemin, Constantes.typePCC);
				nouveauCheminImplicite.setEdgeList(listeArcsDynamique);
				
				// calcul de la centralite pour les arcs
				// if( !this.local){
				for (Arc iterArc : listeArcsDynamique) {
					// iterArc.setValeurVue(iterArc.getValeurVue()+1);
					
					double valeurTemp = iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) + 1;
					iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTemp);
					
					// }
				}
				
				// On retrouve l'ordre des noeuds dans le chemin
				List<Noeud> mesNoeudsDansLordre = nouveauCheminImplicite.getPathVertexList();
				nouveauCheminImplicite.setVerticesList(mesNoeudsDansLordre);
				
				// calcul de la centralite pour les noeuds
				// if( !this.local){
				for (Noeud iterNoeud : mesNoeudsDansLordre) {
					// iterNoeud.setValeurVue(iterNoeud.getValeurVue()+1);
					double valeurTemp = iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()) + 1;
					iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), valeurTemp);
					// }
				}
				
				// On en deduit les noeuds origine et destination
				Noeud origine = mesNoeudsDansLordre.get(0);
				Noeud destination = mesNoeudsDansLordre.get(mesNoeudsDansLordre.size() - 1);
				
				// A ajouter plutot qu'a rechercher
				OD localOD = new OD(origine, destination);
				
				// //Pour comparaisons chemins
				// PCC localPCCPourVerif = new PCC(this.carte.getGraphe(),
				// localOD, 1, Constantes.typePCC);
				//				
				// for( int indexDansListe = 0; indexDansListe <
				// localPCCPourVerif.getEdgeList().size(); indexDansListe++){
				// if( localPCCPourVerif.getEdgeList().get(indexDansListe) !=
				// nouveauCheminImplicite.getEdgeList().get(indexDansListe))
				// System.out.println("Perdu...");
				// }
				
				// if( !this.espace.getEspaceDeDef().contains(localOD) ){
				
				int straightHashCode = localOD.hasCodeSpecial();
				int a = localOD.hasCodeSpecial() / 100000;
				int b = localOD.hasCodeSpecial() - a * 100000;
				
				int reversedHashCodeInverse = b * 100000 + a;
				
				// if( !GestionnaireODs.idsODs.containsKey(new
				// Integer(straightHashCode)) |
				// !GestionnaireODs.idsODs.containsKey(new
				// Integer(reversedHashCodeInverse)) ){
				
				// if( !this.espace.getEspaceDeDef().containsKey(new
				// Integer(straightHashCode)) |
				// !this.espace.getEspaceDeDef().containsKey(new
				// Integer(reversedHashCodeInverse))){
				
				// String hashCodeStringStraight = localOD.hasCodeSpecial();
				// String[] OetD = localOD.hasCodeSpecial().split("!");
				// String hashCodeStringreverse = OetD[1] + "!" + OetD[0];
				
				// if(
				// !this.espace.getEspaceDeDef().containsKey(hashCodeStringStraight)
				// |
				// !this.espace.getEspaceDeDef().containsKey(hashCodeStringreverse)){
				
				if (!this.espace.getEspaceDeDef().containsKey(new Integer(localOD.hashCode()))) {
					
					// for (OD iterOD : this.espace.getEspaceDeDef()) {
					// if( iterOD.hashCode() == localOD.hashCode())
					// System.out.println("problem!!!!");
					// }
					
					// this.espace.getEspaceDeDef().add(localOD);
					this.espace.getEspaceDeDef().put(new Integer(localOD.hashCode()), localOD);
					
					nouveauCheminImplicite.setOd(localOD);
					
					cheminsCalculesImplicitement.add(nouveauCheminImplicite);
					
					if (nouveauCheminImplicite.getEdgeList().size() == 0)
						System.out.println("pas d'arcs dans le chemin...");
					
					Constantes.diametre = Math.max(Constantes.diametre, new Double(nouveauCheminImplicite.getListeNoeuds().size()).intValue());
					
					this.nouvelleCartePourNouvelleCouche.getTousLesPCC().put(new Integer(nouveauCheminImplicite.hashCode()), nouveauCheminImplicite);
				} else {
					// System.out.println("od deja presente!");
					localOD = null;
				}
				listeArcsDynamique.clear();
			}
		}
		
		return cheminsCalculesImplicitement;
	}
	
	private void createEdgeList(Graphe g, ClosestFirstIterator<Noeud, Arc> iter, Noeud noeudDestination) {
		this.edgeList = new ArrayList<Arc>();
		
		while (true) {
			Arc edge = iter.getSpanningTreeEdge(noeudDestination);
			
			if (edge == null) {
				break;
			}
			this.edgeList.add(edge);
			noeudDestination = Graphs.getOppositeVertex(g, edge, noeudDestination);
		}
		Collections.reverse(this.edgeList);
	}
	
}