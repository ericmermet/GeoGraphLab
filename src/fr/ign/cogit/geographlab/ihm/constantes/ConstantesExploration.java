/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.constantes;

import java.awt.Color;

public class ConstantesExploration {
	
	public static final int widthBloc = 100;
	public static final int heightBloc = 51;
	
	public static final int widthOperationBloc = 50;
	public static final int heightOperationBloc = 42;
	
	//Type de bloc graphique
	public static final int blocCarte = 110;
	public static final int blocOperation = 220;
	
	public static final int blocOperationEspace= 221;
	public static final int blocOperationMesure = 222;
	public static final int blocOperationVue = 223;
	public static final int blocOperationLegende = 224;
	
	// Modes d'outils selectionne pour la toolbar exploration
	public static final int selection = 0;
	public static final int drawMapLayer = 1;
	public static final int drag = 2;
	public static boolean dragEnabled = false;
	public static final int moveBloc = 3;
	
	public static final int unionEspaceBloc = 11;
	public static final int soustractionEspaceBloc = 12;
	public static final int intersectionEspaceBloc = 13;
	public static final int exclusionEspaceBloc = 14;
	public static final int complementEspaceBloc = 15;
	
	public static final int additionBloc = 21;
	public static final int addition3Bloc = 27;
	public static final int soustractionBloc = 22;
	public static final int multiplicationBloc = 23;
	public static final int multiplication3Bloc = 28;
	public static final int divisionBloc = 24;
	public static final int fonctionUnaire = 25;
	public static final int fonctionBinaire = 26;
	public static final int sommePonderee = 29;
	
	public static final int unionVueBloc = 31;
	public static final int intersectionVueBloc = 32;
	public static final int exclusionVueBloc = 33;
	public static final int unionAvecConservationVueBloc = 34;
	
	public static final int crsmtCouleurCouleurLegendeBloc = 41;
	public static final int crsmtCouleurTailleLegendeBloc = 42;
	
	public static final int link = 99;
	
	// Couleur des leviers de l'exploration
	public static Color couleurEspace = Color.BLUE;
	public static Color couleurMesure = Color.GREEN;
	public static Color couleurVue = new Color(212,85,0);
	public static Color couleurLegende = Color.RED;
	
	// Les types d'operations ensemblistes sur l'espace
	public static final int typeOperationUnionEspace = 111;
	public static final int typeOperationIntersectionEspace = 112;
	public static final int typeOperationSoustractionEspace = 113;
	public static final int typeOperationExclusionEspace = 114;
	public static final int typeOperationComplementEspace = 115;
	
	// Les types d'operations sur les indicateurs
	public static final int typeOperationAdditionMesure = 121;
	public static final int typeOperationSoustractionMesure = 122;
	public static final int typeOperationMutliplicationMesure = 123;
	public static final int typeOperationDivisionMesure = 124;
	public static final int typeOperationFonctionUnaireMesure = 125;
	public static final int typeOperationFonctionBinaireMesure = 126;
	public static final int typeOperationAddition3Mesure = 127;
	public static final int typeOperationMutliplication3Mesure = 128;
	
	public static final int typeOperationSommePonderee = 129;
	
	
	
	// Les types d'operations ensemblistes sur la vue
	public static final int typeOperationUnionVue = 131;
	public static final int typeOperationIntersectionVue = 132;
	public static final int typeOperationExclusionVue = 133;
	public static final int typeOperationUnionAvecConservationVue = 134;
	
	// Les types d'operations sur les legendes
	public static final int typeOperationCrsmtLegendeCouleurCouleur = 141;
	public static final int typeOperationCrsmtLegendeCouleurTaille = 142;
	
	// ID connecteurs
	public static final int idEspaceEntree = 100;
	public static final int idMesureEntree = 101;
	public static final int idVueEntree = 102;
	public static final int idLegendeEntree = 103;
	public static final int idEspaceSortie = -idEspaceEntree;
	public static final int idMesureSortie = -idMesureEntree;
	public static final int idVueSortie = -idVueEntree;
	public static final int idLegendeSortie = -idLegendeEntree;
	
	// Type des connecteurs
	public static final int typeEspace = 1000;
	public static final int typeMesure = 1001;
	public static final int typeVue= 1002;
	public static final int typeLegende= 1003;
	public static final int typeConnecteurHaut = 10001;
	public static final int typeConnecteurGauche = 10002;
	public static final int typeConnecteurBas = 10003;
	public static final int typeConnecteurSortie = 10004;
	
	public static final int idOperationGauche = 200;
	public static final int idOperationHaut = 201;
	public static final int idOperationEgal = 222;
	public static final int idOperationBas = 202;
	
}