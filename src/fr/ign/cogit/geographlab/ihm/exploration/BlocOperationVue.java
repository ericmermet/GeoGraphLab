/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2011
 *
 * BlocOperationVue.java in ihm.exploration
 * 
 */
package fr.ign.cogit.geographlab.ihm.exploration;

import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesImages;
import fr.ign.cogit.geographlab.ihm.events.MouseEventsExplo;
import fr.ign.cogit.geographlab.interpreteur.OperationsVue;

import javax.swing.ImageIcon;

/**
 * @author mermet
 *
 */
public class BlocOperationVue extends BlocOperation {
	
	public int operation;
	
	public BlocOperationVue(PanelExplo panelPere, int operation) {
		
		super(panelPere, operation);
		
		this.couleur = ConstantesExploration.couleurVue;
		
		this.levierMisEnJeu = ConstantesExploration.blocOperationVue;
		
		this.setType(operation);
		
		switch (operation) {
			case ConstantesExploration.typeOperationUnionVue:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgUnionVue)).getImage());
				break;
			case ConstantesExploration.typeOperationIntersectionVue:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgIntersectionVue)).getImage());
				break;
			case ConstantesExploration.typeOperationExclusionVue:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgExclusionVue)).getImage());
				break;
			case ConstantesExploration.typeOperationUnionAvecConservationVue:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgUnionAvecConservationVue)).getImage());
				break;
			default:
				break;
		}
		
	}
	
	@Override
	public void run() {
		OperationsVue op = new OperationsVue();
		System.out.println("Compute !");
				switch (this.operation) {
					case ConstantesExploration.typeOperationUnionVue:
						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), ((BlocCarte)this.connecteurEntreeHaut).getCarte(), "unionVue", ((BlocCarte)this.connecteurSortie).getCarte());
						break;
					case ConstantesExploration.typeOperationIntersectionVue:
						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), ((BlocCarte)this.connecteurEntreeHaut).getCarte(), "intersectionVue", ((BlocCarte)this.connecteurSortie).getCarte());
						break;
					case ConstantesExploration.typeOperationExclusionVue:
						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), ((BlocCarte)this.connecteurEntreeHaut).getCarte(), "exclusionVue", ((BlocCarte)this.connecteurSortie).getCarte());
						break;
					case ConstantesExploration.typeOperationUnionAvecConservationVue:
						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), ((BlocCarte)this.connecteurEntreeHaut).getCarte(), "unionConsVue", ((BlocCarte)this.connecteurSortie).getCarte());
						break;
				}
				((BlocCarte)this.connecteurSortie).getCarte().getGraphe().setGrapheChange();
				((BlocCarte)this.connecteurSortie).getCarte().getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
				((BlocCarte)this.connecteurSortie).getCarte().parent.parent.panelTools.viewList.setSelectedIndex(1);
				((BlocCarte)this.connecteurSortie).getCarte().variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
				
				((BlocCarte)this.connecteurSortie).getCarte().parent.parent.panelLayer.updateLayersFromLayerControler();
	}
	
	public boolean setConnecteur(BlocGraphique connecteurEntree, int idConnecteurReleaseOnThisBloc){
		// Le bloc connecteur Entree est celui a l'oppose de l'objet que l'on considere (this)
		// Ici on relache toujours sur un bloc operation
		switch (connecteurEntree.typeBloc) {
			case ConstantesExploration.blocOperation:
				// Pour mettre plusieurs operations en cascade
				
				break;
			case ConstantesExploration.blocCarte:
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationGauche && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idVueSortie){
					setConnecteurEntreeGauche(connecteurEntree);
					return true;
				}
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationHaut && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idVueSortie ){
					setConnecteurEntreeHaut(connecteurEntree);
					return true;
				}
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationEgal && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idVueEntree ){
					setConnecteurSortie(connecteurEntree);
					return true;
				}
				break;
			default:
				break;
		}
		return false;
	}
	
	public void setType(int type) {
		this.operation = type;
	}
	
	public int getType() {
		return this.operation;
	}
	
}