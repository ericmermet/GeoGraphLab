/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.visu;

import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
//import java.awt.Polygon;
import java.awt.geom.Area;
import java.util.ArrayList;

import org.geotools.geometry.jts.LiteShape;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

public class CelluleDeVoronoi extends ObjetGraphique {
	
	private static final long serialVersionUID = 1L;
	
	public Polygon polygone;
	public Area area;
	public NoeudGraphique noeudAssocie;
	
	public CelluleDeVoronoi(Noeud n) {
		super("Cell_" + n.getNom(), ConstantesApplication.typeVoronoiCell );
		n.getNoeudGraphique().setCelluleDeVoronoi(this);
	}
	
	public CelluleDeVoronoi(String nom) {
		super(nom, ConstantesApplication.typeVoronoiCell);
	}
	
	public CelluleDeVoronoi(String nom, Polygon p, Area a) {
		this(nom);
		this.polygone = p;
		this.area = a;
	}
	
	public CelluleDeVoronoi(String nom, Coordinate[] coords) {
		this(nom);
		GeometryFactory geomFac = new GeometryFactory();
		LinearRing lr = geomFac.createLinearRing(coords);
		if( coords.length >= 4)
			this.polygone = geomFac.createPolygon(lr);
	}
	
	public CelluleDeVoronoi(Coordinate[] coords) {
		this("");
		GeometryFactory geomFac = new GeometryFactory();
		LinearRing lr = geomFac.createLinearRing(coords);
		if( coords.length >= 4)
			this.polygone = geomFac.createPolygon(lr);
	}
	
	public CelluleDeVoronoi(Polygon polygone) {
		this("");
		this.polygone = polygone;
	}
	
	public CelluleDeVoronoi(Noeud n, String nom, Coordinate[] coords) {
		this(nom, coords);
		n.getNoeudGraphique().setCelluleDeVoronoi(this);
		this.noeudAssocie = n.getNoeudGraphique();
	}

	public double getSurfacePolygone() {
		
		return this.polygone.getArea();
	}
	
	public void paintComponent(Graphics g, boolean colorierSurface) {
				
		LineString ls = this.polygone.getExteriorRing();
		paintLineString(g, ls.getCoordinateSequence());
		if(colorierSurface)
			paintSurface(g, this.polygone);
		
	}
	
	public static void paintSurface(Graphics g, Polygon p) {
		
		Coordinate coords[] = p.getCoordinates();
		GeneralPath path = new GeneralPath();
        path.moveTo((float)coords[0].x, (float)coords[0].y);
                
        for (int i=1; i < coords.length; i++) { 
            path.lineTo((float)coords[i].x, (float)coords[i].y); 
        }
		
		((Graphics2D) g).fill(path);
		
	}
	
	public static void paintLineString(Graphics g, CoordinateSequence pts) {
		// optimized for processing CoordinateSequences
		int n = pts.size();
		if (n <= 1) return;
		
		for (int i = 0 ; i < n - 1 ; i++) {
			Coordinate c0 = pts.getCoordinate(i);
			Coordinate c1 = pts.getCoordinate(i + 1);
			
			Line2D.Double tempLine = new Line2D.Double();
			tempLine = new Line2D.Double(c0.x, c0.y, c1.x, c1.y);
			((Graphics2D) g).setStroke(new BasicStroke(2.0f));//, 
//					BasicStroke.CAP_ROUND,
//                    BasicStroke.JOIN_ROUND, 10.0f));
			((Graphics2D) g).draw(tempLine);
			
		}
	}
	
}