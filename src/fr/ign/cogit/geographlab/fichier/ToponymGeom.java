package fr.ign.cogit.geographlab.fichier;

import org.geonames.Toponym;

import com.vividsolutions.jts.geom.Point;

public class ToponymGeom {

	private Toponym toponym;
	private Point point;
	
	public ToponymGeom(Toponym toponym, Point point) {
		this.toponym = toponym;
		this.point = point;
	}

	public Toponym getToponym() {
		return toponym;
	}

	public void setToponym(Toponym toponym) {
		this.toponym = toponym;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
}