/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.geom;

import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;
import megamu.mesh.MPolygon;
import megamu.mesh.Voronoi;
import fr.ign.cogit.geographlab.visu.CelluleDeVoronoi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.CoordinateSequences;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.triangulate.VoronoiDiagramBuilder;
import com.vividsolutions.jts.triangulate.quadedge.QuadEdgeSubdivision;

public class RegionsDeVoronoi extends Thread {
	
	private Graphe grapheInitial;
	private ArrayList<MPolygon[]> regionsDeVoronoi;
	private ArrayList<float[][]> arretesDeVoronoi;
	private ArrayList<CelluleDeVoronoi> celluleDeVoronois;
	
	VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();
	
	public Thread thread;
	
	public RegionsDeVoronoi(Graphe g) {
		this.thread = new Thread(this);
		this.grapheInitial = g;
	}
	
	@Override
	public void run() {
		
		VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();
		
		ArrayList<Coordinate> mesNoeuds = new ArrayList<Coordinate>();
		
		for (Noeud iterNoeud : this.grapheInitial.getNoeuds()) {
			mesNoeuds.add(iterNoeud.getPosition().getCoordinate());
		}
		
		vdb.setSites(mesNoeuds);
		
		List<Polygon> listPolygon = vdb.getSubdivision().getVoronoiCellPolygons(new GeometryFactory());
		
		for (Polygon polygone : listPolygon) {
			
			CelluleDeVoronoi cell = new CelluleDeVoronoi(polygone.getCoordinates());
			
			for (Noeud iterNoeud : this.grapheInitial.getNoeuds()) {
				if( polygone.intersects(iterNoeud.getPosition())) {
					cell.setNom("VoronoiCell_" + iterNoeud);
					cell.noeudAssocie = iterNoeud.getNoeudGraphique();
					iterNoeud.getNoeudGraphique().setCelluleDeVoronoi(cell);
					break;
				}
			}
		}
		
//		this.celluleDeVoronois = new ArrayList<CelluleDeVoronoi>();
//		
//		this.regionsDeVoronoi = new ArrayList<MPolygon[]>();
//		this.arretesDeVoronoi = new ArrayList<float[][]>();
//		
//		float[][] mesPointsEnFloat = new float[this.grapheInitial.getNoeuds().size()][2];
//		Coordinate[] coords = null;
//		
//		int i = 0;
//		for (Noeud iterNoeud : this.grapheInitial.getNoeuds()) {
//			mesPointsEnFloat[i][0] = (float) iterNoeud.getXPosition();
//			mesPointsEnFloat[i][1] = (float) iterNoeud.getYPosition();
//			i++;
//		}
//		
//		Voronoi voronoiUnique = new Voronoi(mesPointsEnFloat);
//		
//		this.regionsDeVoronoi.add(voronoiUnique.getRegions());
//		this.arretesDeVoronoi.add(voronoiUnique.getEdges());
//		
//		for (MPolygon[] mPolygone : getRegionsDeVoronoi()) {
//			
//			for (int p = 0; p < mPolygone.length; p++) {
//				
//				float[][] pointsDuPolygone = mPolygone[p].getCoords();
//				coords = new Coordinate[pointsDuPolygone.length+1];
//				
//				for (int j = 0; j < pointsDuPolygone.length; j++) {
//					coords[j] = new Coordinate(pointsDuPolygone[j][0], pointsDuPolygone[j][1]);
//				}
//				coords[coords.length-1] = new Coordinate(pointsDuPolygone[0][0], pointsDuPolygone[0][1]);
//				
//				//On crée la cellule a partir des coordonnees
//				CelluleDeVoronoi cell = new CelluleDeVoronoi(coords);
//				
//				//Association noeud cellule
//				for (Noeud iterNoeud : this.grapheInitial.getNoeuds()) {
//					if( cell.polygone.intersects(iterNoeud.getPosition())) {
//						cell.setNom("VoronoiCell_" + iterNoeud);
//						cell.noeudAssocie = iterNoeud.getNoeudGraphique();
//						iterNoeud.getNoeudGraphique().setCelluleDeVoronoi(cell);
//						break;
//					}
//				}
//				
//				this.celluleDeVoronois.add(cell);
//				
//			}
//		}
		
	}
	
	public ArrayList<MPolygon[]> getRegionsDeVoronoi() {
		return this.regionsDeVoronoi;
	}
	
	public ArrayList<float[][]> getArreteDeVoronoi() {
		return this.arretesDeVoronoi;
	}
	
	public ArrayList<CelluleDeVoronoi> getRegionsDeVoronoiPolygone() {
		return this.celluleDeVoronois;
	}
	
	public void pondereNoeudAleaRegion() {
		
		ArrayList<CelluleDeVoronoi> cellulesDeVoronoi = getRegionsDeVoronoiPolygone();
		
		for (CelluleDeVoronoi cell : cellulesDeVoronoi) {
			//			for (Noeud iterNoeud : this.grapheInitial.getNoeuds()) {
			
			//				if (cell.polygone.contains( iterNoeud.getPosition() ) ) {
			cell.noeudAssocie.getNoeudTopologique().setPonderation(cell.getSurfacePolygone());
			cell.noeudAssocie.getNoeudTopologique().getNoeudGraphique().setCelluleDeVoronoi(cell);
			//					break;
			//				}
			//			}
		}
		this.grapheInitial.setGrapheChange();
	}
	
	//	public void attribCouleurSurfaces() {
	//		
	//		HashSet<CelluleDeVoronoi> cellulesDeVoronoi = getRegionsDeVoronoiPolygone();
	//		
	//		for (CelluleDeVoronoi cell : cellulesDeVoronoi) {
	//			for (Noeud iterNoeud : this.grapheInitial.getNoeuds()) {
	//				
	//				if (cell.polygone.contains(iterNoeud.getPosition())) {
	//
	//				}
	//			}
	//		}
	//		this.grapheInitial.setGrapheChange();
	//		
	//	}
	
}