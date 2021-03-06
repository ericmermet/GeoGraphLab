/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2010
 *
 * GrapheGenerique.java in graphe
 * 
 */
package fr.ign.cogit.geographlab.graphe;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.vividsolutions.jts.geom.Point;

import fr.ign.cogit.geographlab.cheminements.OD;

/**
 * @author mermet
 *
 */
public interface GrapheGenerique {
	
	public  String nom = new String();
	
	public void setNom(String nom);
	public String getNom();
	public void addNoeud(Noeud noeud);
	public boolean delNoeud(String nomDuNoeud);
	public void setPonderationNoeud(Noeud noeud, double poids);
	public double getPoidsNoeud(Noeud n);
	public int getNombreNoeuds();
	public Noeud getNoeud(String nomDuNoeud);
	public Noeud getNoeud(Point position);
	public Noeud getNoeud(int iD);
	public Set<Noeud> getNoeuds();
	public Point[] getNoeudsArray();
	public List<String> getNomDesNoeuds();
	public boolean addArc(Arc a);
	public boolean addArcPondere(Arc a, double poids);
	public boolean delArc(String nomDeLArc);
	public void setPoidsArc(Arc a, double poids);
	public double getPoidsArc(Arc a);
	public int getNombreArcs();
	public Arc getArc(String nomDeLArc);
	public Set<Arc> getArcs();
	public List<String> getNomDesArcs();
	public HashMap<Integer, OD> getToutesLesOD();
	public void setGrapheChange();
	
}