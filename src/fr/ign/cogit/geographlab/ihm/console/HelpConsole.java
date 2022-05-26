/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.console;

public class HelpConsole {
	
	public static String textMainHelp = new String(
			"info - affiche les infortmations sur l'outil\n" + "system - affiche les informations sur l'etat du systeme\n" + 
			"exit - ferme GeoGraphLab\n" + "freemem - libere de la memoire\n" + 
			"compute - execute un algorithme sur la carte courante\n" + 
			"layer iD - affiche la couche correspondante a l'iD\n" + "select {composante1, composante2, ...} - selectionne des composantes du reseau\n" + 
			"espace - donne la taille de l'espace OD\n" + 
			"filtre - sélection les OD\n" +
			"open file - ouvre un fichier\n");
	
	public static String textHelpCalcul = new String(
			"CI ou CENTRALITE_INTERMEDIAIRE\n" + "CD ou CENTRALITE_DEGRE\n" + "CP ou CENTRALITE_PROXIMITE\n" + "EM ou ELOIGNEMENT_MOYEN\n" + "EMI ou ELOIGNEMENT_MINIMUM\n" + "EMA ou ELOIGNEMENT_MAXIMUM\n" + "RD ou RAYONDISTAL\n" + "RP ou RAYONPROXIMAL\n");
	
	public static String textHelpSelect = new String(
			"Selectionne une ou des composantes noeuds ou arcs du reseau\n" + "NOEUD nomCompNoeud1 nomCompNoeud2 nomCompNoeud3 ...\n" + "ARC nomCompArc1 nomCompArc2  nomComp3 ...\n" + "ZONE nomZone1 nomZone2 nomZone3 ...");
	
	public static String textHelpFiltre = new String("only command espace is permetted"	);
	
	public static String textHelpFiltreEspace = new String(	"Total : revenir a l'espace total\n" +
															"NordSud : selection des deplacements nord-sud si le centre de gravite du reseau a ete calcule\n" + 
															"EstOuest : selection des deplacements est-ouest si le centre de gravite du reseau a ete caclule\n"	+
															"N + liste du(des) noeud(s) origines\n" + 
															"Exemples: 	filtre espace n Auber\n" +
															"			filtre espace n Daumesnil Rome\n");
}