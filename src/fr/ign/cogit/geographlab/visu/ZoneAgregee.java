/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.visu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.Subgraph;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.PanelMainDraw;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.ihm.outils.legende.PanelLegend;

public class ZoneAgregee extends ObjetGraphique {
	
	private static final long serialVersionUID = 1L;
	
	private PanelMainDraw panel;
	
	private HashSet<ObjetGraphique> objetsAgreges;
	
	private Polygon polygonShape;
	
	private Double valeurGraphique;
	
	private Subgraph<Noeud, Arc, Graphe> sousGrapheDeLaZone;
	
	double x1, y1, x2, y2;
	double[][] coordBissectrices;
	double[][] coordDroites;
	
	private int bufferZoneAgregee;
	
	public ZoneAgregee(PanelMainDraw panel, List<ObjetGraphique> objetsAAgreger) {
		
		this.panel = panel;
		
		String nom = "";
		for (ObjetGraphique objetGraphique : objetsAAgreger) {
			if (objetGraphique.getType() == ConstantesApplication.typeVertex) {
				String str = objetGraphique.getNom() + "-";
				nom = nom + str;
			}
		}
		
		Set<Noeud> vertexSet = new HashSet<Noeud>();
		Set<NoeudGraphique> graphicVertexSet = new HashSet<NoeudGraphique>();
		Set<Arc> edgeSet = new HashSet<Arc>();
		Set<ArcGraphique> graphicEdgeSet = new HashSet<ArcGraphique>();
		
		// On met les noeuds a agreger dans la collection vertexSet
		for (ObjetGraphique objetGraphique : objetsAAgreger) {
			if (objetGraphique.getType() == ConstantesApplication.typeVertex) {
				vertexSet.add(((NoeudGraphique) objetGraphique).getNoeudTopologique());
				graphicVertexSet.add((NoeudGraphique) objetGraphique);
				// valeur +=
				// ((NoeudGraphique)objetGraphique).getNoeudTopologique().getValeurPourIndicateur(panel.carte.getNomIndicateurCourant());
				// nbValeur++;
			}
		}
		
		// On met les arcs issus des noeuds a agreger dans la collection edgeSet
		for (Noeud iterNoeud1 : vertexSet) {
			for (Noeud iterNoeud2 : vertexSet) {
				Set<Arc> tempArcs = panel.carte.getGraphe().getAllEdges(iterNoeud1, iterNoeud2);
				if (tempArcs.size() != 0) {
					edgeSet.addAll(tempArcs);
					for (Arc iterArcAAjouter : tempArcs)
						graphicEdgeSet.add(iterArcAAjouter.getArcGraphique());
					// valeur +=
					// iterArcAAjouter.getArcGraphique().getNoeudTopologique().getValeurPourIndicateur(nomIndicateur);
					// nbValeur++;
				}
			}
		}
		
		objetsAAgreger.clear();
		objetsAAgreger.addAll(graphicVertexSet);
		objetsAAgreger.addAll(graphicEdgeSet);
		
		this.setSousGrapheDeLaZone(new Subgraph<Noeud, Arc, Graphe>(panel.carte.getGraphe(), vertexSet, edgeSet));
		
		setType(ConstantesApplication.typeMetaVertex);
		
		setNom(nom);
		
		setColor(ConstantesApplication.unselectedColorMetarVertex);
		
		System.out.println(getNom());
		
		this.objetsAgreges = new HashSet<ObjetGraphique>();
		this.setObjetsAgreges(objetsAAgreger);
		
		setValeursGraphiquesMoyenne();
		
	}
	
	public void addObjet(ObjetGraphique objetGraphique) {
		this.objetsAgreges.add(objetGraphique);
	}
	
	public void removeObjet(ObjetGraphique objetGraphique) {
		this.objetsAgreges.remove(objetGraphique);
	}
	
	public void setObjetsAgreges(List<ObjetGraphique> objetsAAgreger) {
		this.objetsAgreges.addAll(objetsAAgreger);
	}
	
	public HashSet<ObjetGraphique> getObjetsAgreges() {
		return this.objetsAgreges;
	}
	
	public HashSet<NoeudGraphique> getNoeuds() {
		
		HashSet<NoeudGraphique> noeudsGraphiques = new HashSet<NoeudGraphique>();
		
		for (ObjetGraphique iterObjGra : this.objetsAgreges) {
			if (iterObjGra.getType() == ConstantesApplication.typeVertex)
				noeudsGraphiques.add((NoeudGraphique) iterObjGra);
		}
		
		return noeudsGraphiques;
	}
	
	public HashSet<ArcGraphique> getArcs() {
		
		HashSet<ArcGraphique> noeudsGraphiques = new HashSet<ArcGraphique>();
		
		for (ObjetGraphique iterObjGra : this.objetsAgreges) {
			if (iterObjGra.getType() == ConstantesApplication.typeEdge)
				noeudsGraphiques.add((ArcGraphique) iterObjGra);
		}
		
		return noeudsGraphiques;
	}
	
	/**
	 * @return the sousGrapheDeLaZone
	 */
	public Subgraph<Noeud, Arc, Graphe> getSousGrapheDeLaZone() {
		return this.sousGrapheDeLaZone;
	}

	/**
	 * @param sousGrapheDeLaZone the sousGrapheDeLaZone to set
	 */
	public void setSousGrapheDeLaZone(Subgraph<Noeud, Arc, Graphe> sousGrapheDeLaZone) {
		this.sousGrapheDeLaZone = sousGrapheDeLaZone;
	}

	public Double setValeursGraphiquesMoyenne() {
		Double temp = new Double(0.0);
		
		for (ObjetGraphique iterObjetZoneAgregee : this.objetsAgreges) {
			if (iterObjetZoneAgregee.getType() == ConstantesApplication.typeVertex) {
				temp += ((NoeudGraphique) iterObjetZoneAgregee).getNoeudTopologique().getValeurPourIndicateur(this.panel.carte.getNomIndicateurCourant());
			}
			if (iterObjetZoneAgregee.getType() == ConstantesApplication.typeEdge) {
				temp += ((ArcGraphique) iterObjetZoneAgregee).getArcTopologique().getValeurPourIndicateur(this.panel.carte.getNomIndicateurCourant());
			}
		}
		
		this.valeurGraphique = new Double(temp.doubleValue() / this.objetsAgreges.size());
		
		return this.valeurGraphique;
	}
	
	public void setValeursGraphiquesMedianne() {
		
	}
	
	public void setValeursGraphiquesMinimum() {
		
	}
	
	public void setValeursGraphiquesMaximum() {
		
	}
	
	public Double getValeurGraphique() {
		return this.valeurGraphique;
	}
	
	public Polygon getShape() {
		
		Coordinate tabCoord[] = new Coordinate[getNoeuds().size()];
		
		int i = 0;
		for (NoeudGraphique iterNoeud : getNoeuds()) {
			
			Coordinate coord = new  Coordinate((float)iterNoeud.getXPosition(), (float)iterNoeud.getYPosition());
			tabCoord[i] = coord;
			i++;
			
		}
		
		//First the buffer % points
		Geometry geom = new GeometryFactory().createMultiPoint(tabCoord);
		
		Geometry geomEnvelop = geom.convexHull().buffer(PanelLegend.parent.panelActif.cst.bufferZoneAgregee);
		
		Coordinate[] coordBuffer = geomEnvelop.getCoordinates();
		int[] coordXPolygone = new int[coordBuffer.length];
		int[] coordYPolygone = new int[coordBuffer.length];
		
		for (int j = 0; j < coordBuffer.length; j++) {
			coordXPolygone[j] = new Double(coordBuffer[j].x).intValue();
			coordYPolygone[j] = new Double(coordBuffer[j].y).intValue();
		}
		
		this.polygonShape = new Polygon(coordXPolygone, coordYPolygone, coordBuffer.length);
		
		return this.polygonShape;
	}
	
	public void paintComponent(Graphics g, int tailleBuffer) {
		
		this.bufferZoneAgregee = tailleBuffer;
		
		((Graphics2D) g).fill(getShape());
		
		if (!this.panel.carte.variablesDeCarte.afficheSousReseau) {
			((Graphics2D) g).setColor(Color.BLACK);
			((Graphics2D) g).draw(getShape());
		}
		
	}
	
}