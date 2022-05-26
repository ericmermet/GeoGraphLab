/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2014
 *
 * GeoNamesWebServices.java in fr.ign.cogit.geographlab.fichier
 * 
 */
package fr.ign.cogit.geographlab.fichier;

import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class GeoNamesWebService {

	public static ToponymGeom getCoordForToponym(String text) {

		//Build geom with JTS and Geotools
		SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();

		//set the name
		b.setName( "PointFeature" );

		//add a geometry property
		b.setCRS( DefaultGeographicCRS.WGS84 ); // set crs first
		b.add( "location", Point.class ); // then add geometry

		//build the type
		final SimpleFeatureType TYPE = b.buildFeatureType();
		
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
		GeometryFactory geometryFactory = new GeometryFactory();
		
		SimpleFeature feature = featureBuilder.buildFeature( "fid.1" ); // build the 1st feature
		
		//Prepare connection to geonames webservice 
		WebService.setUserName("puck"); // puck user has been created on geonames.org
		
		ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
		searchCriteria.setQ(text);
		ToponymSearchResult searchResult = null;
		try {
			searchResult = WebService.search(searchCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Toponym iterToponym : searchResult.getToponyms()) {
			System.out.println(iterToponym.getName()+" "+ iterToponym.getCountryName());
			
			Coordinate pointCoord = new Coordinate(iterToponym.getLongitude(), iterToponym.getLatitude());
			Point point = geometryFactory.createPoint(pointCoord);
			
			featureBuilder.add(point);

			ToponymGeom tpGeom = new ToponymGeom(iterToponym, point);
			
			return tpGeom;
		}
		return null;

	}
	
	public static void main(final String[] args) {
		
		ToponymGeom topo = getCoordForToponym("Cagli");
		
		System.out.println(topo.getToponym().getName() + " " + topo.getToponym().getLatitude() + " " + topo.getToponym().getLongitude() + " " + topo.getPoint().getX() + " " + topo.getPoint().getY());
		
	}

}