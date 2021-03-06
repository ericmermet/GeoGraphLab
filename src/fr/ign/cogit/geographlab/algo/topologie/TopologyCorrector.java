/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * TopologyCorrecter.java in fr.ign.cogit.geographlab.algo.topologie
 * 
 */
package fr.ign.cogit.geographlab.algo.topologie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Noeud;

/**
 * @author eric
 *
 */
public class TopologyCorrector extends Thread {
	
	private Carte carte;
	public Thread thread;
	
	int distance;
	
	List<Noeud> listNoeuds = new ArrayList<Noeud>();
	List<Noeud> listNoeudsAEffacer = new ArrayList<Noeud>();
	
	public TopologyCorrector(Carte carte, int distance) {
		this.thread = new Thread(this);
		this.carte = carte;
		this.distance = distance;
	}
	
	@Override
	public void run() {
		
		//Creation de grappes de noeuds proches
		
		System.out.println("_______");
		
		ArrayList<Noeud> noeudsReunis = new ArrayList<Noeud>();
		
		int i = 0;
		for (Noeud noeudCourant : this.carte.getGraphe().getNoeuds()) {
			System.out.print(".");
			i++;
			for (Noeud autreNoeud : this.carte.getGraphe().getNoeuds()) {
				
				if( !noeudCourant.equals(autreNoeud) & !noeudsReunis.contains(autreNoeud) & !noeudsReunis.contains(noeudCourant)) {
					
					this.listNoeuds.clear();
					
//					System.out.println("compare " + noeudCourant.getNom() + " -> " + autreNoeud.getNom());
					
					//Si la distance du noeud courant a un autre noeud est < param distance
					if( noeudCourant.getNoeudGraphique().getGeom().distance(autreNoeud.getNoeudGraphique().getGeom()) < this.distance ) {
						//Alors on reunit
						this.listNoeuds.add(noeudCourant);
						this.listNoeuds.add(autreNoeud);
//						System.out.print(" réunion des deux noeuds");
						//Les arcs sont effaces, les noeuds sont recuperes et effaces apres la boucle
						this.carte.getVueDuGraphe().reunirNoeuds(this.listNoeuds, false); 
						
						noeudsReunis.add(autreNoeud);
//						iterNoeuds2.remove();
						System.out.print("_"+ i +"\n");
					}
					
				}
			}
			// Mise a jour de la progress bar
			this.carte.parent.parent.statusBar.changeProgressBar(i, this.carte.getGraphe().getNoeuds().size());
		}
		
		for (Noeud noeud : noeudsReunis) {
			this.carte.getVueDuGraphe().removeNoeudGraphique(noeud.getNoeudGraphique());
			this.carte.getGraphe().delNoeud(noeud);
		}
		this.carte.parent.parent.statusBar.changeProgressBar(this.carte.getGraphe().getNoeuds().size(), this.carte.getGraphe().getNoeuds().size());
		this.carte.getGraphe().setGrapheChange();
		this.carte.repaint();
		
	}
	
}
