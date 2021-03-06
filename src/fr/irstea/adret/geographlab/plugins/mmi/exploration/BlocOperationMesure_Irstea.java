/**
* GeoGrapheLab ( http://geographlab.free.fr/ ) - This file is part of GeoGrapheLab
* Copyright (C) 2007-2012 - IGN  - Eric Mermet & IRSTEA - Jean-Marc Tacnet (2011)
*
* This file must be used under the terms of the CeCILL-B.
* This source file is licensed as described in the file COPYING, which
* you should have received as part of this distribution.  The terms
* are also available at
* http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
*
*/

package fr.irstea.adret.geographlab.plugins.mmi.exploration;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;
import fr.irstea.adret.geographlab.plugins.interpreter.OperationsMesureSommePonderee;
import fr.irstea.adret.geographlab.plugins.mmi.constants.ConstantesExplorationIrstea;
import fr.irstea.adret.geographlab.plugins.mmi.constants.ConstantesImagesIrstea;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesImages;
import fr.ign.cogit.geographlab.ihm.exploration.BlocCarte;
import fr.ign.cogit.geographlab.ihm.exploration.PanelExplo;
import fr.ign.cogit.geographlab.interpreteur.OperationsMesure;


public class BlocOperationMesure_Irstea extends fr.ign.cogit.geographlab.ihm.exploration.BlocOperationMesure {
	
	ArrayList<Carte> listCartes;
	ArrayList<Double> listPoids;
	String nomCarteResultante;
	
	public BlocOperationMesure_Irstea(PanelExplo panelPere, int operation, ArrayList<Carte> listcartes, ArrayList<Double> argWeights, String nomCarteResultante) {
		
		super(panelPere, operation);
		
		this.couleur = ConstantesExploration.couleurMesure;
		
		this.levierMisEnJeu = ConstantesExploration.blocOperationMesure;
		
		this.listCartes = listcartes;
		this.listPoids = argWeights;
		this.nomCarteResultante = nomCarteResultante;
		
		this.setType(operation);
		
		switch (operation) {
//			case ConstantesExplorationIrstea.typeOperationAddition3Mesure:
//				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgAddition3)).getImage());
//				break;
//			case ConstantesExplorationIrstea.typeOperationMutliplication3Mesure:
//				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgMultiplication3)).getImage());
//				break;
//			case ConstantesExplorationIrstea.typeOperationFonctionUnaireMesure:
//				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgFonctionUnaire)).getImage());
//				break;
//			case ConstantesExplorationIrstea.typeOperationFonctionBinaireMesure:
//				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImages.imgFonctionBinaire)).getImage());
//				break;
			case ConstantesExplorationIrstea.typeOperationSommePonderee:
				setImageOperation(new ImageIcon(getClass().getResource(ConstantesImagesIrstea.imgSommePonderee)).getImage());
				break;
			default:
				break;
		}
		
	}
	
	@Override
	public void run() {
		OperationsMesureSommePonderee op = new OperationsMesureSommePonderee();
		System.out.println("Compute !");
			String operationString = "";
				switch (this.getOperation()) {
//					case ConstantesExploration.typeOperationAddition3Mesure:
//						operationString = "++";
//						break;
//					case ConstantesExplorationIrstea.typeOperationMutliplication3Mesure:
//						operationString = "**";
//						break;
//					case ConstantesExplorationIrstea.typeOperationFonctionUnaireMesure:
//						operationString = "fonctionUnaire";
//						break;
//					case ConstantesExplorationIrstea.typeOperationFonctionBinaireMesure:
//						operationString = "fonctionBinaire";
//						break;
					case ConstantesExplorationIrstea.typeOperationSommePonderee:
						operationString = "sommePonderee";
					default:
						break;
				}
				
				//Test sur le nombre de connexions entrantes sur l'operateur
				switch (this.getOperation()) {
//					case ConstantesExplorationIrstea.typeOperationFonctionUnaireMesure:
//						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), null, null, operationString, ((BlocCarte)this.connecteurSortie).getCarte());
//						break;
//					case ConstantesExplorationIrstea.typeOperationAddition3Mesure:
//					case ConstantesExplorationIrstea.typeOperationMutliplication3Mesure:
//						op.operations( ((BlocCarte)this.connecteurEntreeGauche).getCarte(), ((BlocCarte)this.connecteurEntreeHaut).getCarte(), ((BlocCarte)this.connecteurEntreeBas).getCarte(), operationString, ((BlocCarte)this.connecteurSortie).getCarte());
//						break;
					case ConstantesExplorationIrstea.typeOperationSommePonderee:
						OperationsMesureSommePonderee.operations(this.listCartes, operationString, this.listPoids, ((BlocCarte)this.connecteurSortie).getCarte(), this.nomCarteResultante);
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
	
}