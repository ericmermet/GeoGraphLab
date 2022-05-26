/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2011
 *
 * BlocOperationLegende.java in ihm.exploration
 * 
 */
package fr.ign.cogit.geographlab.ihm.exploration;

import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesImages;
import fr.ign.cogit.geographlab.ihm.events.MouseEventsExplo;

import javax.swing.ImageIcon;

/**
 * @author mermet
 *
 */
public class BlocOperationLegende extends BlocOperation {
	
public int operation;
	
	public BlocOperationLegende(PanelExplo panelPere, int operation) {
		
		super(panelPere, operation);
		
		this.couleur = ConstantesExploration.couleurLegende;
		
		this.levierMisEnJeu = ConstantesExploration.blocOperationLegende;
		
		this.setType(operation);
		
		switch (operation) {
			case ConstantesExploration.typeOperationCrsmtLegendeCouleurCouleur:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgCrsmtCouleurCouleurLegende)).getImage());
				break;
			case ConstantesExploration.typeOperationCrsmtLegendeCouleurTaille:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgCrsmtCouleurTailleLegende)).getImage());
				break;
			default:
				break;
		}
		
	}
	
	@Override
	public void run() {
//		OperationsVue op = new OperationsVue();
//		System.out.println("Compute !");
//				switch (this.operation) {
//					case ConstantesExploration.typeOperationUnionVue:
//						op.operations( ((CarteExplo)this.connecteurEntreeGauche).getCarte(), ((CarteExplo)this.connecteurEntreeHaut).getCarte(), "unionVue", ((CarteExplo)this.connecteurSortie).getCarte());
//						break;
//					case ConstantesExploration.typeOperationIntersectionVue:
//						op.operations( ((CarteExplo)this.connecteurEntreeGauche).getCarte(), ((CarteExplo)this.connecteurEntreeHaut).getCarte(), "intersectionVue", ((CarteExplo)this.connecteurSortie).getCarte());
//						break;
//					case ConstantesExploration.typeOperationExclusionVue:
//						op.operations( ((CarteExplo)this.connecteurEntreeGauche).getCarte(), ((CarteExplo)this.connecteurEntreeHaut).getCarte(), "exclusionVue", ((CarteExplo)this.connecteurSortie).getCarte());
//						break;
//					case ConstantesExploration.typeOperationUnionAvecConservationVue:
//						op.operations( ((CarteExplo)this.connecteurEntreeGauche).getCarte(), ((CarteExplo)this.connecteurEntreeHaut).getCarte(), "unionConsVue", ((CarteExplo)this.connecteurSortie).getCarte());
//						break;
//				}
//				((CarteExplo)this.connecteurSortie).getCarte().getGraphe().setGrapheChange();
//				((CarteExplo)this.connecteurSortie).getCarte().getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
//				((CarteExplo)this.connecteurSortie).getCarte().parent.parent.panelTools.viewList.setSelectedIndex(1);
//				((CarteExplo)this.connecteurSortie).getCarte().variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
//				
//				((CarteExplo)this.connecteurSortie).getCarte().parent.parent.panelLayer.updateLayersFromLayerControler();
	}
	
	public boolean setConnecteur(BlocGraphique connecteurEntree, int idConnecteurReleaseOnThisBloc){
		// Le bloc connecteur Entree est celui a l'oppose de l'objet que l'on considere (this)
		// Ici on relache toujours sur un bloc operation
		switch (connecteurEntree.typeBloc) {
			case ConstantesExploration.blocOperation:
				// Pour mettre plusieurs operations en cascade
				
				break;
			case ConstantesExploration.blocCarte:
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationGauche && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idLegendeSortie){
					setConnecteurEntreeGauche(connecteurEntree);
					return true;
				}
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationHaut && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idLegendeSortie ){
					setConnecteurEntreeHaut(connecteurEntree);
					return true;
				}
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationEgal && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idLegendeEntree ){
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