/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.graphe.event;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;



import javax.swing.JTable;

import org.jgrapht.alg.ConnectivityInspector;


import fr.ign.cogit.geographlab.cheminements.Constantes;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.VueDuGraphe;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.graphe.listeners.GrapheListener;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.listes.PanelOD;
import fr.ign.cogit.geographlab.ihm.listes.PanelTopologicalIndicators;
import fr.ign.cogit.geographlab.lang.Messages;

public class GrapheEvent implements GrapheListener {
	
	MainWindow fenetrePrincipale;
	VueDuGraphe vue = null;
	Graphe g = null;
	
	public GrapheEvent() {
		//		
	}
	
	public GrapheEvent(MainWindow fenetrePrincipale, VueDuGraphe a, Graphe g) {
		this.fenetrePrincipale = fenetrePrincipale;
		this.vue = a;
		this.g = g;
	}
	
	public boolean grapheChange() {
		
		// Maj de la table des indicateurs topologiques
		updateInfosTopos();
		// Maj de la table des infos des noeuds
		updateNoeuds();
		// Maj de la table des infos des arcs
		updateArcs();
		// Maj de la table ODs
		if (Math.pow(this.g.getNoeuds().size(), 2.0) < 1000000)
			updateODs();

		// Maj de l'arbre des zones agregees
		updateAgregs();
		
		// Maj de la legende
//		updateLegende();
		// Maj des couches de cartes
		updateLayers();
		
		return true;
	}
	
	private void updateInfosTopos() {
		Object[][] dataInfosTopos = new Object[8][2];
		Object[] columnNames = { Messages.getString("GrapheEvent.0"), Messages.getString("GrapheEvent.1") }; //$NON-NLS-1$ //$NON-NLS-2$
		Double nbrSommets = new Double(this.g.getNombreNoeuds());
		Double nbrAretes = new Double(this.g.getNombreArcs());
		
		ConnectivityInspector<Noeud, Arc> testDeConnexite = new ConnectivityInspector<Noeud, Arc>(this.g);
		Double nbrCompConnex = new Double(testDeConnexite.connectedSets().size());
		
		dataInfosTopos[0][0] = Messages.getString("GrapheEvent.2"); //$NON-NLS-1$
		dataInfosTopos[0][1] = nbrSommets;
		dataInfosTopos[1][0] = Messages.getString("GrapheEvent.3"); //$NON-NLS-1$
		dataInfosTopos[1][1] = nbrAretes;
		dataInfosTopos[2][0] = Messages.getString("GrapheEvent.4"); //$NON-NLS-1$
		dataInfosTopos[2][1] = nbrCompConnex;
		dataInfosTopos[3][0] = Messages.getString("GrapheEvent.5"); //$NON-NLS-1$
		dataInfosTopos[3][1] = Constantes.diametre; //MAJ2
		dataInfosTopos[4][0] = Messages.getString("GrapheEvent.6"); //$NON-NLS-1$
		NumberFormat formatter = new DecimalFormat(Messages.getString("GrapheEvent.7")); //$NON-NLS-1$
		dataInfosTopos[4][1] = formatter.format(new Double(nbrAretes.doubleValue() / nbrSommets.doubleValue()));
		dataInfosTopos[5][0] = Messages.getString("GrapheEvent.8"); //$NON-NLS-1$
		dataInfosTopos[5][1] = formatter.format(new Double((nbrAretes.doubleValue() / ((nbrSommets.doubleValue() * (nbrSommets.doubleValue() - 1)) / 2)) * 100));
		dataInfosTopos[6][0] = Messages.getString("GrapheEvent.9"); //$NON-NLS-1$
		Double double1 = new Double(nbrAretes.doubleValue() - nbrSommets.doubleValue() + nbrCompConnex.doubleValue());
		Double double2 = new Double((nbrAretes.doubleValue() * (nbrAretes.doubleValue() - 1) / 2) - nbrAretes.doubleValue() + 1);
		dataInfosTopos[6][1] = formatter.format(new Double(double1.doubleValue() / double2.doubleValue()));
		
		dataInfosTopos[7][0] = Messages.getString("GrapheEvent.10"); //$NON-NLS-1$
		if (Math.pow(this.g.getNoeuds().size(), 2.0) < 1000000 && (this.fenetrePrincipale.getPanelMainDrawActif() != null) )
			if(this.fenetrePrincipale.getPanelMainDrawActif().carte.variablesDeCarte.afficheGrapheNonDirige)
				dataInfosTopos[7][1] = this.g.getNoeuds().size()*(this.g.getNoeuds().size()-1)/2;
			else
				dataInfosTopos[7][1] = this.g.getNoeuds().size()*(this.g.getNoeuds().size()-1);
//			dataInfosTopos[7][1] = new Double(this.g.getToutesLesOD().size());
		else
			dataInfosTopos[7][1] = Messages.getString("GrapheEvent.11"); //$NON-NLS-1$
		
		JTable localTable = new JTable(dataInfosTopos, columnNames);
		PanelTopologicalIndicators.getTInfosTopogiques().setModel(localTable.getModel());
	}
	
	private void updateNoeuds() {
		
		// Set<NoeudGraphique> tousLesNoeuds = this.vue.getNoeudsGraphiques();
		// int nbrSommets = tousLesNoeuds.size();
		//
		// int i= 0;
		// PanelNodes2.setModelDataSize(nbrSommets);
		// for (NoeudGraphique iterNoeud : tousLesNoeuds) {
		// PanelNodes2.getJTable().setValueAt(iterNoeud.getNom(), i, 0);
		// PanelNodes2.getJTable().setValueAt(new
		// Double(iterNoeud.getNoeudTopologique().getPonderation()), i, 1);
		// PanelNodes2.getJTable().setValueAt(new
		// Double(Graphs.neighborListOf(this.g,
		// iterNoeud.getNoeudTopologique()).size()), i, 2);
		// PanelNodes2.getJTable().setValueAt(iterNoeud.getColor(), i, 3);
		// i++;
		// }
		
		if (this.fenetrePrincipale.panelLegend != null) {
			this.fenetrePrincipale.tabNodes.updateNodesFromModel();
		}
		
	}
	
	private void updateArcs() {
		
		// Set<ArcGraphique> tousLesArcs = this.vue.getArcsGraphiques();
		// int nbrArcs = tousLesArcs.size();
		//		
		// int i = 0;
		// PanelEdges.setModelDataSize(nbrArcs);
		//		
		// PanelEdges.getJTable().setAutoCreateRowSorter(false);
		//		
		// for(ArcGraphique iterArc : tousLesArcs ){
		// PanelEdges.getJTable().setValueAt(iterArc.getNom(), i, 0);
		// PanelEdges.getJTable().setValueAt(new
		// Double(this.g.getEdgeWeight(iterArc.getArcTopologique())), i, 1);
		// PanelEdges.getJTable().setValueAt(iterArc.getColor(), i, 2);
		// i++;
		// }
		// PanelEdges.getJTable().setAutoCreateRowSorter(false);
		
		if (this.fenetrePrincipale.panelLegend != null) {
			this.fenetrePrincipale.tabEdges.updateEdgesFromModel();
		}
		
	}
	
	private void updateODs() {
		if (this.fenetrePrincipale.panelLegend != null) {
			HashMap<Integer, OD> toutesLesODs = this.fenetrePrincipale.panelActif.carte.getEspace().getEspaceDeDef();
			int nbOds = toutesLesODs.size();
			
			int i = 0;
			PanelOD.setModelDataSize(nbOds);
			if (PanelOD.parent.panelActif != null) {
				PanelOD.parent.panelActif.carte.getEspace().setEspaceDeDef(toutesLesODs);
			}
			
			for (OD iterOD : toutesLesODs.values()) {
				PanelOD.getJTable().setValueAt(iterOD.getNom(), i, 0);
				PanelOD.getJTable().setValueAt(new Double(iterOD.getPonderation()), i, 1);
				PanelOD.getJTable().setValueAt(new String(Messages.getString("GrapheEvent.12")), i, 2); //$NON-NLS-1$
				PanelOD.getJTable().setValueAt(new Boolean(false), i, 3);
				i++;
			}
		}
	}
	
	private void updateAgregs() {
		this.fenetrePrincipale.panelAgreg.createNodes();
	}
	
	private void updateLegende() {
		if (this.fenetrePrincipale.panelLegend != null) {
			this.fenetrePrincipale.panelLegend.updateLegendFromModel();
		}
	}
	
	private void updateLayers() {
		if (this.fenetrePrincipale.panelLayer != null) {
			this.fenetrePrincipale.panelLayer.updateLayersFromLayerControler();
		}
	}
	
	@Override
	public boolean poidsArcsChange() {
		System.out.println(Messages.getString("GrapheEvent.13")); //$NON-NLS-1$
		return false;
	}
}