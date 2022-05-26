/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.exploration;

import javax.swing.ImageIcon;

import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesImages;
import fr.ign.cogit.geographlab.ihm.events.MouseEventsExplo;
import fr.ign.cogit.geographlab.interpreteur.OperationsMesure;

public class BlocOperationMesure extends BlocOperation {
	
	public BlocOperationMesure(PanelExplo panelPere, int operation) {
		
		super(panelPere, operation);
		
		this.couleur = ConstantesExploration.couleurMesure;
		
		this.levierMisEnJeu = ConstantesExploration.blocOperationMesure;
		
		this.setType(operation);
		
		switch (operation) {
			case ConstantesExploration.typeOperationAdditionMesure:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgAddition)).getImage());
				break;
			case ConstantesExploration.typeOperationAddition3Mesure:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgAddition3)).getImage());
				break;
			case ConstantesExploration.typeOperationSoustractionMesure:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgSoustration)).getImage());
				break;
			case ConstantesExploration.typeOperationMutliplicationMesure:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgMultiplication)).getImage());
				break;
			case ConstantesExploration.typeOperationMutliplication3Mesure:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgMultiplication3)).getImage());
				break;
			case ConstantesExploration.typeOperationDivisionMesure:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgDivision)).getImage());
				break;
			case ConstantesExploration.typeOperationFonctionUnaireMesure:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgFonctionUnaire)).getImage());
				break;
			case ConstantesExploration.typeOperationFonctionBinaireMesure:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgFonctionBinaire)).getImage());
				break;
			default:
				break;
		}
		
	}
	
	@Override
	public void run() {
		OperationsMesure op = new OperationsMesure();
		System.out.println("Compute !");
			String operationString = "";
				switch (this.getOperation()) {
					case ConstantesExploration.typeOperationAdditionMesure:
						operationString = "+";
						break;
					case ConstantesExploration.typeOperationAddition3Mesure:
						operationString = "++";
						break;
					case ConstantesExploration.typeOperationSoustractionMesure:
						operationString = "-";
						break;
					case ConstantesExploration.typeOperationMutliplicationMesure:
						operationString = "*";
						break;
					case ConstantesExploration.typeOperationMutliplication3Mesure:
						operationString = "**";
						break;
					case ConstantesExploration.typeOperationDivisionMesure:
						operationString = "/";
						break;
					case ConstantesExploration.typeOperationFonctionUnaireMesure:
						operationString = "fonctionUnaire";
						break;
					case ConstantesExploration.typeOperationFonctionBinaireMesure:
						operationString = "fonctionBinaire";
						break;
					default:
						break;
				}
				
				//Test sur le nombre de connexions entrantes sur l'operateur
				switch (this.getOperation()) {
					case ConstantesExploration.typeOperationFonctionUnaireMesure:
						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), null, null, operationString, ((BlocCarte)this.connecteurSortie).getCarte());
						break;
					case ConstantesExploration.typeOperationAddition3Mesure:
					case ConstantesExploration.typeOperationMutliplication3Mesure:
						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), ((BlocCarte)this.connecteurEntreeHaut).getCarte(), ((BlocCarte)this.connecteurEntreeBas).getCarte(), operationString, ((BlocCarte)this.connecteurSortie).getCarte());
						break;
					default:
						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), ((BlocCarte)this.connecteurEntreeHaut).getCarte(), null, operationString, ((BlocCarte)this.connecteurSortie).getCarte());
						break;
				}
				
				((BlocCarte)this.connecteurSortie).getCarte().setNom(((BlocCarte)this.connecteurEntreeHaut).getCarte().getNom() + " " + operationString + " " + ((BlocCarte)this.connecteurEntreeGauche).getCarte().getNom());
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
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationGauche && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idMesureSortie ){
					setConnecteurEntreeGauche(connecteurEntree);
					return true;
				}
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationHaut && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idMesureSortie ){
					setConnecteurEntreeHaut(connecteurEntree);
					return true;
				}
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationEgal && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idMesureEntree ){
					setConnecteurSortie(connecteurEntree);
					return true;
				}
				if( idConnecteurReleaseOnThisBloc == ConstantesExploration.idOperationBas && MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idMesureSortie ){
					setConnecteurEntreeBas(connecteurEntree);
					return true;
				}
				break;
			default:
				break;
		}
		return false;
	}
	
	public void setType(int type) {
		 this.setOperation(type);
	}
	
	public int getType() {
		return this.getOperation();
	}
	
}