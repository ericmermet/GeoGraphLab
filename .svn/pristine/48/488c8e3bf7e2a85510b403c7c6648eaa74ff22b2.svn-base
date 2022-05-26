/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2014
 *
 * DisplayForcedShape.java in fr.ign.cogit.geographlab.testing
 * 
 */
package fr.ign.cogit.geographlab.testing;

/**
 * @author eric
 *
 */
import org.geotools.data.DataUtilities;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import org.geotools.feature.FeatureIterator;

import com.vividsolutions.jts.geom.Geometry;

import org.geotools.data.FeatureSource;
import org.geotools.map.DefaultMapContext;
import org.geotools.map.FeatureLayer;
import org.geotools.map.MapContent;
import org.geotools.map.MapContext;
import org.geotools.styling.SLD;
import org.geotools.swing.JMapFrame;

public class DisplayForcedShape {
	public static void main(String[] args) throws Exception {
		final SimpleFeatureType TYPE = 
				DataUtilities.createType("Location","location:Point,name:String"); // see 
//		createFeatureType();
		DefaultFeatureCollection collection = new DefaultFeatureCollection("internal",TYPE);
		double lon[] = {123.31, 0, 47.66};
		double lat[] = {48.4, 52, 63.15};
		String name[] = { "Point1", "Point2", "Point3" };
		GeometryFactory factory = JTSFactoryFinder.getGeometryFactory(null);    
		for(int i=0;i<=2;i++){
			Point point = factory.createPoint( new Coordinate(lon[i],lat[i]));
			SimpleFeature feature = SimpleFeatureBuilder.build( 
					TYPE, new Object[]{point, name[i]}, null );
			collection.add( feature );
//			collection.
		}
		
		FeatureIterator iterator = collection.features();
		try {
			while (iterator.hasNext()) {
				SimpleFeature feature = (SimpleFeature)iterator.next();
				
				Geometry geometry = (Geometry) feature.getDefaultGeometry();
				Coordinate[] coords = geometry.getCoordinates();
				for( int i = 0; i < coords.length; i++ ) {
					System.out.println(coords[i]);
				}
			}
		}
		finally {
			if( iterator != null ){
				// YOU MUST CLOSE THE ITERATOR!
				iterator.close();
			}
		}
		
		FeatureSource<?, ?> featureSource = DataUtilities.source(collection);
		
		MapContent mapContent = new MapContent();
		mapContent.setTitle("Title");
		FeatureLayer layer = new FeatureLayer(featureSource, SLD.createSimpleStyle(TYPE));
        mapContent.addLayer(layer);
		
        JMapFrame.showMap(mapContent);
		
	}
}