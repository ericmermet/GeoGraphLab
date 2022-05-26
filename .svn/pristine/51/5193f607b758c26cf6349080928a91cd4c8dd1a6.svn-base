/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2010
 *
 * OperationEspaceBloc.java in ihm.exploration
 * 
 */
package fr.ign.cogit.geographlab.ihm.exploration;

/**
 * @author mermet
 *
 */


import javax.swing.ImageIcon;

import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesImages;
import fr.ign.cogit.geographlab.ihm.events.MouseEventsExplo;
import fr.ign.cogit.geographlab.interpreteur.OperationsEspace;


public class BlocOperationEspace extends BlocOperation {
	
	public BlocOperationEspace(PanelExplo panelPere, int operation) {
		
		super(panelPere, operation);
		
		this.couleur = ConstantesExploration.couleurEspace;
		
		this.levierMisEnJeu = ConstantesExploration.blocOperationEspace;
		
		this.setType(operation);
		
		switch (operation) {
			case ConstantesExploration.typeOperationUnionEspace:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgUnionEspace)).getImage());
				break;
			case ConstantesExploration.typeOperationSoustractionEspace:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgSoustractionespace)).getImage());
				break;
			case ConstantesExploration.typeOperationIntersectionEspace:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgIntersectionEspace)).getImage());
				break;
			case ConstantesExploration.typeOperationExclusionEspace:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgExclusionEspace)).getImage());
				break;
			case ConstantesExploration.typeOperationComplementEspace:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgComplementEspace)).getImage());
				break;
			default:
				break;
		}
		
	}
	
	@Override
	public void run() {
		OperationsEspace op = new OperationsEspace();
		System.out.println("Compute !");
				switch (this.operation) {
					case ConstantesExploration.typeOperationUnionEspace:
						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), ((BlocCarte)this.connecteurEntreeHaut).getCarte(), "union", ((BlocCarte)this.connecteurSortie).getCarte());
						break;
					case ConstantesExploration.typeOperationSoustractionEspace:
						op.operations( ((BlocCarte)this.connecteurEntreeHaut).getCarte(), ((BlocCarte)this.connecteurEntreeGauche).getCarte(), "soustraction", ((BlocCarte)this.connecteurSortie).getCarte());
						break;
					case ConstantesExploration.typeOperationIntersectionEspace:
						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), ((BlocCarte)this.connecteurEntreeHaut).getCarte(), "intersection", ((BlocCarte)this.connecteurSortie).getCarte());
						break;
					case ConstantesExploration.typeOperationExclusionEspace:
						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), ((BlocCarte)this.connecteurEntreeHaut).getCarte(), "exclusion", ((BlocCarte)this.connecteurSortie).getCarte());
						break;
					case ConstantesExploration.typeOperationComplementEspace:
						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), ((BlocCarte)this.connecteurEntreeHaut).getCarte(), "complement", ((BlocCarte)this.connecteurSortie).getCarte());
						break;
				}
				((BlocCarte)this.connecteurSortie).getCarte().getGraphe().setGrapheChange();
//				((CarteExplo)this.connecteurSortie).getCarte().getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
//				((CarteExplo)this.connecteurSortie).getCarte().parent.parent.panelTools.viewList.setSelectedIndex(1);
//				((CarteExplo)this.connecteurSortie).getCarte().variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
				
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
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationGauche && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idEspaceSortie ){
					setConnecteurEntreeGauche(connecteurEntree);
					return true;
				}
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationHaut && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idEspaceSortie ){
					setConnecteurEntreeHaut(connecteurEntree);
					return true;
				}
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationEgal && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idEspaceEntree ){
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