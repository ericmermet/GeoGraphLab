/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.exploration;

import java.awt.Color;

import fr.ign.cogit.geographlab.lang.Messages;

public class VariablesDeCarte {
	
	public Color defaultColor = Color.BLACK;
	public Color selectedColorGraphicObject = Color.RED;
	public Color selectedColorVertex = Color.GREEN;// /Color.decode("#7D42C1");
	public Color selectedColorVertexOrigine = Color.decode("#008c00");
	public Color selectedColorVertexDestination = Color.RED;
	public Color unselectedColorVertex = Color.BLUE;
	public Color boundColorVertex = Color.BLACK;
	public Color selectedColorMetaVertex = Color.RED;
	public Color unselectedColorMetarVertex = Color.ORANGE;
	public Color boundColorMetaVertex = Color.BLACK;
	public Color selectedColorEdge = Color.GREEN;
	public Color unselectedColorEdge = Color.DARK_GRAY;
	public Color vertexDelaunayColor = Color.CYAN;
	public Color edgeDelaunayColor = Color.CYAN;
	public Color voronoiCellColor = Color.LIGHT_GRAY;
	public Color agregationColor = Color.ORANGE;
	public Color convexHullColor = Color.LIGHT_GRAY;
	public Color drawingColorEdge = this.unselectedColorEdge;
	public Color drawingColorVertex = this.unselectedColorVertex;
	public Color drawingColorZone = this.unselectedColorMetarVertex;
	
	public int vertexRadius = 4;
	public int agregateVertexRadius = 6;
	public int vertexRadiusMaxIndicatorView = 10;
	public int nbMarques = 6;
	public int edgewidth = 3;
	
	public boolean dragEnabled = false;
	
//	public String affichageEnCours = new String("Vue Normale");
	public String affichageEnCours = Messages.getString("ConstantesApplication.normalView");
	public int afficheGeometrie = 0;		//0 => Affichage Simple = LineString, 1 => Affichage Shape = MultiLineString, 2 => Autre ? Interpolation ? A faire.
	public boolean afficheGraphe = true;
	public boolean afficheGrapheDelaunay = false;
	public boolean afficheVoronoi = false;
	public boolean afficheGrapheNonDirige = true;
	public boolean afficheAgregation = true;
	public boolean afficheSousReseau = true;
	public boolean afficheImageDeFond = false;
	public boolean afficheLigneCentre = false;
	public boolean afficheEnveloppeConvexe = false;
	public boolean afficheCouleurVoronoi = false;
	
	public VariablesDeCarte() {
		
	}
	
}
