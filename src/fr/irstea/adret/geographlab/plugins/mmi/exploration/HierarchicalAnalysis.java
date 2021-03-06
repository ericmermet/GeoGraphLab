/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * HierarchicalAnalysis.java in fr.irstea.adret.geographlab.plugins.ihm.exploration
 * 
 */
package fr.irstea.adret.geographlab.plugins.mmi.exploration;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.ihm.PanelMainDraw;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;
import fr.ign.cogit.geographlab.ihm.events.MouseEventsExplo;
import fr.ign.cogit.geographlab.ihm.exploration.ConnecteurBlocs;
import fr.ign.cogit.geographlab.ihm.exploration.PanelExplo;
import fr.irstea.adret.geographlab.plugins.mmi.constants.ConstantesExplorationIrstea;
import fr.irstea.adret.geographlab.plugins.mmi.exploration.BlocOperationMesure_Irstea;
import fr.irstea.adret.geographlab.plugins.model.Indicator;
import fr.irstea.adret.geographlab.plugins.model.Operation;
import fr.irstea.adret.geographlab.plugins.interpreter.OperationsMesureSommePonderee;

/**
 * @author eric
 *
 */
public class HierarchicalAnalysis {
	
	PanelMainDraw mainActivePanel;
	ArrayList<Indicator> listIndicators;
	ArrayList<Operation> listOperations;
	HashSet<String> listThemes = new HashSet<String>();
	HashMap<String, Carte> listMaps = new HashMap<String, Carte>();
	
	public HierarchicalAnalysis(PanelMainDraw pmd, ArrayList<Indicator> listIndicators, ArrayList<Operation> listOperations) {
		this.mainActivePanel = pmd;
		this.listIndicators = listIndicators;
		this.listOperations = listOperations;
		
		//Retrieve all themes
		for (Indicator indicator : listIndicators) {
			this.listThemes.add(indicator.getTheme());
		}	
		
	}
	
	public void createIndicators(List<String> mapsNames) {
		
		//Creation d'un nombre de cartes en consequence -1 (une carte est deja creee par defaut)
		//Mais d'abord on enleve les cartes parentes du gestionnaire de couches
		this.mainActivePanel.couchesDeCartes.retirerToutesLesChouchesParentes();
		
		for (String theme : this.listThemes) {
			for (Indicator indicator: this.listIndicators) {
				
				//If same theme
				if( indicator.getTheme().compareTo(theme) == 0) {
					
					//Add map if same name as indicator
					for (String mapName : mapsNames) {
						if( indicator.getAttribute_name().compareTo(mapName) == 0) {
							Carte carteTemp = new Carte(this.mainActivePanel);
							carteTemp.setNom(mapName);
							carteTemp.setNomIndicateurCourant(carteTemp.getNom());
							this.listMaps.put(mapName, carteTemp);
							//Add here code to highlight theme
							//TODO
						}
					}
				}
				
			}
		}
		
		//		for (int i = 0; i < mapsNames.size(); i++) {
		//			Carte carteTemp = new Carte(this.mainActivePanel);
		//			carteTemp.setNom(mapsNames.get(i));
		//			carteTemp.setNomIndicateurCourant(carteTemp.getNom());
		//			//L'ajout de cartes dans le gestionnaire est implicite et est effectue a la creation de la carte
		//			//				this.parent.couchesDeCartes.ajouterUneCoucheCarte(carteTemp);
		//		}
		
	}
	
	public void createOperations(ArrayList<Operation> listOperations) {
		
		for (Operation operation : listOperations) {
			
			ArrayList<Carte> listCartes = new ArrayList<Carte>();
			int moyPosY = 0;
			int posX = 200;
			int i = 0;
			boolean testIfOperandsExists = true;
			
			for (String operande: operation.getOperands()) {
				
				Carte carteTemp = this.listMaps.get(operande);
				if( carteTemp == null ) {
					testIfOperandsExists = false;
					break;
				}
				Point p = carteTemp.carteExplo.getpXY();
				posX = p.x;
				moyPosY += p.y;
				i++;
				
				listCartes.add(carteTemp);
				
				//				carteTemp.carteExplo.getConnecteurEntreeMesure();
				
			}
			
			if( testIfOperandsExists ) {
				moyPosY /= i;
				
				BlocOperationMesure_Irstea blockWeightedSum = new BlocOperationMesure_Irstea(this.mainActivePanel.panelExplo.getJPanel(), 
						ConstantesExplorationIrstea.typeOperationSommePonderee, 
						listCartes,
						operation.getWeight(),
						operation.getResult());
				
				//Add the new measure block in graphical language panel
				this.mainActivePanel.panelExplo.getJPanel().operations.ajouterUneOperation(blockWeightedSum);
				blockWeightedSum.setpXY(new Point(posX + 200, moyPosY));
				blockWeightedSum.setRectanglesGenericLayer(blockWeightedSum.getpXY());
				
				//Add connections
				for (Carte carte : listCartes) {
					//Link block-maps with operation
					//				MouseEventsExplo.link(carte.carteExplo, blockWeightedSum, Color.GREEN, 100000);
					ConnecteurBlocs connecteur = new ConnecteurBlocs(carte.carteExplo, blockWeightedSum);
					connecteur.setLinesMeasureBlockToBlock();
					carte.carteExplo.addConnecteurFilaires(connecteur);
					blockWeightedSum.addConnecteurFilaires(connecteur);
					PanelExplo.connecteurs.add(connecteur);
					
				}
				
				Carte carteResultante = new Carte(listCartes.get(0),  operation.getResult(), operation.getResult());
				carteResultante.carteExplo.setParentMap(false);
				carteResultante.carteMere = null;
				carteResultante.carteExplo.setpXY(new Point(posX + 300,moyPosY+5));
				carteResultante.carteExplo.setRectanglesGenericLayer(carteResultante.carteExplo.getpXY());
				
				ConnecteurBlocs connecteur2 = new ConnecteurBlocs(blockWeightedSum, carteResultante.carteExplo );
				connecteur2.setLinesMeasureBlockToBlock();
				blockWeightedSum.addConnecteurFilaires(connecteur2);
				carteResultante.carteExplo.addConnecteurFilaires(connecteur2);
				PanelExplo.connecteurs.add(connecteur2);
				
				OperationsMesureSommePonderee.operations(listCartes, "sommePonderee", operation.getWeight(), carteResultante, operation.getResult());
				
			}
		}
	}
	
}