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
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;

import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.graphe.event.NoeudEvent;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.visu.CelluleDeVoronoi;

public class NoeudGraphique extends ObjetGraphique {
	
	private static final long serialVersionUID = 1L;
	
	private Noeud noeudTopologique;
	
	private int rayon;
	private Ellipse2D.Double vertexShape;
	
	private Point p;
	
	private CelluleDeVoronoi celluleDeVoronoi = null;
	
//	private boolean isSelectedOrigine =false , isSelectedDestination= false;
	
//	private static NoeudEvent event(Noeud n, NoeudGraphique ng) {
//		NoeudEvent evt = new NoeudEvent(n, ng);
//		
//		// System.out.println("pouet");
//		if (evt.selectionChange()) {
//			// System.out.println("youpi");
//		}
//		return evt;
//		
//	}
	
	public NoeudGraphique(Noeud n, Point p) {
		super(n.getNom(), ConstantesApplication.typeVertex);
		this.setNoeudTopologique(n);
//		n.addSelectionNoeudListener(NoeudGraphique.event(n, this));
//		n.addNomNoeudListener(NoeudGraphique.event(n, this));
//		n.addPositionNoeudListener(this.event(n, this));
		setPosition(p);
		setRayon(ConstantesApplication.vertexRadius);
		setColor(ConstantesApplication.unselectedColorVertex);
	}
	
	public void setNoeudTopologique(Noeud noeudTopologique) {
		this.noeudTopologique = noeudTopologique;
	}
	
	public Noeud getNoeudTopologique() {
		return this.noeudTopologique;
	}
	
	public Point getPosition() {
		return this.p;
	}
	
	public double getXPosition() {
		return this.p.getX();
	}
	
	public double getYPosition() {
		return this.p.getY();
	}
	
	public void setPosition(Point position) {
		this.p = position;
		autoSetShape();
	}
	
	public void setPosition(double x, double y) {
		this.p = new GeometryFactory().createPoint(new Coordinate(x, y));
		autoSetShape();
	}
	
	public void setXPosition(int x) {
		setPosition(x, getYPosition());
		autoSetShape();
	}
	
	public void setYPosition(int y) {
		setPosition(getXPosition(), y);
		autoSetShape();
	}
	
	public void setGeom(Geometry geom) {
		
		if (geom instanceof MultiPoint) {
			MultiPoint geomMultiPoint = (MultiPoint) geom;
			this.p = (Point)geomMultiPoint.getGeometryN(0);
		} else if (geom instanceof Point) {
			this.p = (Point)geom;
		} else {
			System.out.println("ATTENTION : Geometrie differente de Point ou MultiPoint");
			this.p = null;
			return;
		}
		
	}
	
	public Point getGeom() {
		return this.p;
	}
	
	public int getRayon() {
		return this.rayon;
	}
	
	public void setRayon(int r) {
		this.rayon = r;
	}
	
	public void autoSetShape() {
		this.vertexShape = new Ellipse2D.Double(getXPosition() - getRayon(), getYPosition() - getRayon(), 3 * getRayon(), 3 * getRayon());
	}
	
	public void changeRayon(int nouveauRayon) {
		setRayon(nouveauRayon);
		this.vertexShape.x = getXPosition() - getRayon();
		this.vertexShape.y = getYPosition() - getRayon();
		this.vertexShape.height = 3 * getRayon();
		this.vertexShape.width = 3 * getRayon();
	}
	
	public Shape getShape() {
		return this.vertexShape;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		((Graphics2D) g).fill(this.vertexShape);
		((Graphics2D) g).setColor(Color.BLACK);
		((Graphics2D) g).draw(getShape());
		
	}
	
	public void paintComponent(Graphics g, int vertexRadius) {
		
		this.changeRayon(vertexRadius);
		paintComponent(g);
		
	}
	
	public void setCelluleDeVoronoi(CelluleDeVoronoi cell) {
		this.celluleDeVoronoi = cell;
	}
	
	public CelluleDeVoronoi getCelluleDeVoronoi() {
		return this.celluleDeVoronoi;
	}

	@Override
	public void setSelected(boolean isSelected){
		super.setSelected(isSelected);
		if(isSelected == false ){
			setSelectedOrigine(false);
			setSelectedDestination(false);
		}
	}

	public void setSelectedOrigine(boolean isSelectedOrigine) {
		this.getNoeudTopologique().setSelectedOrigine(isSelectedOrigine);
	}

	public boolean isSelectedOrigine() {
		return this.getNoeudTopologique().isSelectedOrigine();
	}

	public void setSelectedDestination(boolean isSelectedDestination) {
		this.getNoeudTopologique().setSelectedDestination(isSelectedDestination);
	}

	public boolean isSelectedDestination() {
		return this.getNoeudTopologique().isSelectedDestination();
	}

}