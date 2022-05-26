/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.maths;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.ArcFactory;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;

import megamu.mesh.Delaunay;

public class TriangulationDelaunay2 extends Thread {
        
        Graphe grapheInitial;
        Graphe grapheDeReference = new Graphe(new ArcFactory(), "GrapheDeDelaunay");
        Point[] mesPointsDansLespace;
        
        public Thread thread;
        
        public TriangulationDelaunay2(Graphe g) {
                this.thread = new Thread(this);
                this.grapheInitial = g;
        }
        
        public Graphe getGrapheDelaunay() {
                return this.grapheDeReference;
        }
        
        @Override
        public void run() {
                
                this.mesPointsDansLespace = this.grapheInitial.getNoeudsArray();
                
                float[][] mesPointsEnFloat = new float[this.mesPointsDansLespace.length][2];
                
                for (int i = 0; i < mesPointsEnFloat.length; i++) {
                        mesPointsEnFloat[i][0] = (float) this.mesPointsDansLespace[i].getX();
                        mesPointsEnFloat[i][1] = (float) this.mesPointsDansLespace[i].getY();
                }
                
                Delaunay monDelaunay = new Delaunay(mesPointsEnFloat);
                
                float[][] mesNoeudsDeDelaunay = monDelaunay.getEdges();
                
                for (int i = 0; i < mesNoeudsDeDelaunay.length; i++) {
                        float posXNoeudDepart = mesNoeudsDeDelaunay[i][0];
                        float posYNoeudDepart = mesNoeudsDeDelaunay[i][1];
                        float posXNoeudArrive = mesNoeudsDeDelaunay[i][2];
                        float posYNoeudArrive = mesNoeudsDeDelaunay[i][3];
                        
                        Noeud noeudDepart = this.grapheInitial.getNoeud(new Point(new Coordinate(posXNoeudDepart, posYNoeudDepart), new PrecisionModel(), 0));
                        Noeud noeudArrive = this.grapheInitial.getNoeud(new Point(new Coordinate(posXNoeudArrive, posYNoeudArrive), new PrecisionModel(), 0));
                        
                        this.grapheDeReference.addNoeud(noeudDepart);
                        this.grapheDeReference.addNoeud(noeudArrive);
                        
                        Arc nouvelArc = new Arc(noeudDepart, noeudArrive);
                        
                        this.grapheDeReference.addArc(nouvelArc);
                        
                }
                
                System.out.println("Noeuds = " + this.grapheDeReference.getNombreNoeuds() + " Arcs = " + this.grapheDeReference.getNombreArcs());
                // this.grapheDeReference.setGrapheChange();
        }
        
}