/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.maths;


//import java.util.Collection;

//Import Geotools a revoir depuis la nouvelle version

//import com.vividsolutions.jts.geomgraph.Edge;
//import org.geotools.graph.structure.Graph;
//import org.geotools.graph.util.delaunay.DelaunayNode;
//import org.geotools.graph.util.delaunay.DelaunayTriangulator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.triangulate.DelaunayTriangulationBuilder;

import fr.ign.cogit.geographlab.graphe.ArcFactory;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;

public class TriangulationDelaunay extends Thread {
	
	Graphe grapheInitial;
	Graphe grapheDeReference = new Graphe(new ArcFactory(), "GrapheDeDelaunay");
//	DelaunayTriangulator maTriangulationDeDelauney;
	DelaunayTriangulationBuilder dtBuilder;
	
	public TriangulationDelaunay(Graphe g) {
		this.grapheInitial = g;
//		this.maTriangulationDeDelauney = new DelaunayTriangulator();
		dtBuilder = new DelaunayTriangulationBuilder();
	}
	
	public Graphe getGrapheDelaunay() {
		return this.grapheDeReference;
	}
	
	private int[] getTroisPointsEnglobants() {
		
		int[] mes3PointsEnglobants = new int[3];
		
		double xMin = Double.MAX_VALUE;
		double yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE;
		double yMax = Double.MIN_VALUE;
		
//		DelaunayNode[] tabDeNoeuds = new DelaunayNode[this.grapheInitial.getNoeuds().size()];
		
//		int i = 0;
		for (Noeud iterNoeud : this.grapheInitial.getNoeuds()) {
//			DelaunayNode newDelaunayNode = new DelaunayNode();
//			newDelaunayNode.setCoordinate(new Coordinate(iterNoeud.getXPosition(), iterNoeud.getYPosition()));
//			tabDeNoeuds[i++] = newDelaunayNode;
			if (iterNoeud.getXPosition() > xMax)
				xMax = iterNoeud.getXPosition();
			if (iterNoeud.getYPosition() > yMax)
				yMax = iterNoeud.getYPosition();
			if (iterNoeud.getXPosition() < xMin)
				xMin = iterNoeud.getXPosition();
			if (iterNoeud.getYPosition() < yMin)
				yMin = iterNoeud.getYPosition();
		}
		
		Point[] returnPoint = new Point[2];
		returnPoint[0] = new Point(new Coordinate(xMin, yMin), new PrecisionModel(), 0);
		returnPoint[1] = new Point(new Coordinate(xMax, yMax), new PrecisionModel(), 0);
		
//		this.maTriangulationDeDelauney.setNodeArray(tabDeNoeuds);
		
		return mes3PointsEnglobants;
	}
	
	@Override
	public void run() {
//		int[] troisPointsEnglobants = getTroisPointsEnglobants();
		
//		Graph grapheTrianguleGeoTools = this.maTriangulationDeDelauney.getTriangulation();
		
//		for (Object unNoeudDuGrapheGeoTools : grapheTrianguleGeoTools.getNodes()) {
//			Point p = new Point(new Double(((DelaunayNode) unNoeudDuGrapheGeoTools).getCoordinate().x).intValue(), new Double(((DelaunayNode) unNoeudDuGrapheGeoTools).getCoordinate().y).intValue());
			
//			Noeud nouveauNoeud = new Noeud(unNoeudDuGrapheGeoTools.toString(), p);
			
//			this.grapheDeReference.addNoeud(nouveauNoeud);
//		}
		
//		for (Object unArcDuGrapheGeoTools : grapheTrianguleGeoTools.getEdges()) {
			
//			Noeud n1 = this.grapheDeReference.getNoeud(((Edge) unArcDuGrapheGeoTools).getNodeA().toString());
//			Noeud n2 = this.grapheDeReference.getNoeud(((Edge) unArcDuGrapheGeoTools).getNodeB().toString());
			
//			Arc nouvelArc = new Arc(n1, n2);
			
//			this.grapheDeReference.addArc(nouvelArc);
//		}
		
		System.out.println("Noeuds = " + this.grapheDeReference.getNombreNoeuds() + " Arcs = " + this.grapheDeReference.getNombreArcs());
		// this.grapheDeReference.setGrapheChange();
	}
	
}
