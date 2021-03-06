/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.geom;

import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.ihm.outils.legende.PanelLegend;
import megamu.mesh.Hull;
import megamu.mesh.MPolygon;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class EnveloppeConvexe extends ObjetGraphique {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private Point[] mesPointsDansLespace;
//	
//	private Hull enveloppeConvexe;
	
	private Geometry enveloppeConvexeGeom;
	
	private Polygon polygonShape;
//	private int[] extrema;
	
	public EnveloppeConvexe(String nom, Graphe g, int buffer) {
		super(nom, ConstantesApplication.typeVoronoiCell);
		
		Coordinate tabCoord[] = new Coordinate[g.getNoeudsArray().length];
		
		int i = 0;
		for (Noeud iterNoeud : g.getNoeuds()) {
			
			Coordinate coord = new  Coordinate((float)iterNoeud.getNoeudGraphique().getXPosition(), (float)iterNoeud.getNoeudGraphique().getYPosition());
			tabCoord[i] = coord;
			i++;
			
		}
		
		Geometry geom = new GeometryFactory().createMultiPoint(tabCoord);
		this.enveloppeConvexeGeom = geom.convexHull().buffer(buffer);

		Coordinate[] coordBuffer = this.enveloppeConvexeGeom.getCoordinates();
		int[] coordXPolygone = new int[coordBuffer.length];
		int[] coordYPolygone = new int[coordBuffer.length];
		
		for (int j = 0; j < coordBuffer.length; j++) {
			coordXPolygone[j] = new Double(coordBuffer[j].x).intValue();
			coordYPolygone[j] = new Double(coordBuffer[j].y).intValue();
		}
		
		this.polygonShape = new Polygon(coordXPolygone, coordYPolygone, coordBuffer.length);
		
//		this.mesPointsDansLespace = g.getNoeudsArray();
//		float[][] mesPointsEnFloat = new float[this.mesPointsDansLespace.length][2];
//		
//		for (int i = 0; i < mesPointsEnFloat.length; i++) {
//			mesPointsEnFloat[i][0] = (float) this.mesPointsDansLespace[i].getX();
//			mesPointsEnFloat[i][1] = (float) this.mesPointsDansLespace[i].getY();
//		}
		
//		this.enveloppeConvexe = new Hull(mesPointsEnFloat);
		
//		this.region = this.enveloppeConvexe.getRegion();
//		
//		this.extrema = this.enveloppeConvexeGeom.;
	}
	
	public Polygon getEnveloppePolygone() {
		
		return this.polygonShape;
		
	}
	
//	public Polygon getEnveloppePolygone() {
//		
//		float[][] pointsDuPolygone = this.region.getCoords();
//		int nbPoints = pointsDuPolygone.length;
//		
//		int[] coordXPolygone = new int[nbPoints];
//		int[] coordYPolygone = new int[nbPoints];
//		
//		for (int i = 0; i < pointsDuPolygone.length; i++) {
//			coordXPolygone[i] = (int) pointsDuPolygone[i][0];
//			coordYPolygone[i] = (int) pointsDuPolygone[i][1];
//		}
//		
//		Polygon polygoneReturn = new Polygon(coordXPolygone, coordYPolygone, nbPoints);
//		
//		return polygoneReturn;
//	}
	
	
	
//	public Polygon getEnveloppePolygone(String buffer) {
//		Polygon polygoneSansBuffer = getEnveloppePolygone();
//		
//		AffineTransform at = new AffineTransform();
//		at.scale(1.15, 1.15);
//		
//		Polygon polygoneIntermediaireBuffer = new Polygon();
//		
//		for(int i=0;i<polygoneSansBuffer.xpoints.length; i++) {
//			Point2D ptOut = at.transform(new Point2D.Double(polygoneSansBuffer.xpoints[i], polygoneSansBuffer.ypoints[i]), null);
//			polygoneIntermediaireBuffer.addPoint((int) ptOut.getX(), (int) ptOut.getY()); 
//		}
//		
//		int xMilieuPSansBuffer = (int) polygoneSansBuffer.getBounds2D().getCenterX();
//		int yMilieuPSansBuffer = (int) polygoneSansBuffer.getBounds2D().getCenterY();
//		
//		int xMilieuPAvecBuffer = (int) polygoneIntermediaireBuffer.getBounds2D().getCenterX();
//		int yMilieuPAvecBuffer = (int) polygoneIntermediaireBuffer.getBounds2D().getCenterY();
//		
//		int decalX, decalY;
//		
//		if( xMilieuPAvecBuffer > xMilieuPSansBuffer )
//			decalX = xMilieuPSansBuffer - xMilieuPAvecBuffer;
//		else
//			decalX = xMilieuPAvecBuffer - xMilieuPSansBuffer;
//		
//		if( yMilieuPAvecBuffer > yMilieuPSansBuffer )
//			decalY = yMilieuPSansBuffer - yMilieuPAvecBuffer;
//		else
//			decalY = yMilieuPAvecBuffer - yMilieuPSansBuffer;
//		
//		Polygon polygoneAvecBuffer = new Polygon();
//		
//		for(int i=0;i<polygoneSansBuffer.xpoints.length; i++) {
//			Point2D ptOut = new Point2D.Double(polygoneIntermediaireBuffer.xpoints[i]+decalX, polygoneIntermediaireBuffer.ypoints[i]+decalY);
//			polygoneAvecBuffer.addPoint((int) ptOut.getX(), (int) ptOut.getY()); 
//		}
//		
//		return polygoneAvecBuffer;
//		
//	}
//	
//	public int[] getExtrema() {
//		return this.extrema;
//	}
//	
//	public Point[] getPointsEnveloppe() {
//		Point[] pointsreturn = new Point[getExtrema().length];
//		int i = 0;
//		for (int point : getExtrema()) {
//			pointsreturn[i++] = this.mesPointsDansLespace[point];
//		}
//		return pointsreturn;
//	}
	
}