/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2010
 *
 * GrapheNonDirige.java in graphe
 * 
 */
package fr.ign.cogit.geographlab.graphe;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.vividsolutions.jts.geom.Point;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.WeightedMultigraph;

import fr.ign.cogit.geographlab.cheminements.OD;

/**
 * @author mermet
 *
 */
public class GrapheNonDirige extends WeightedMultigraph<Noeud, Arc> implements GrapheGenerique {

	private static final long serialVersionUID = 1L;
	
	public GrapheNonDirige(EdgeFactory<Noeud, Arc> arg, String nom) {
		super(arg);
		setNom(nom);
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#addArc(graphe.Arc)
	 */
	@Override
	public boolean addArc(Arc a) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#addArcPondere(graphe.Arc, java.lang.Double)
	 */
	@Override
	public boolean addArcPondere(Arc a, double poids) {
		// TODO Auto-generated method stub
		
		return true;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#addNoeud(graphe.Noeud)
	 */
	@Override
	public void addNoeud(Noeud noeud) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#delArc(java.lang.String)
	 */
	@Override
	public boolean delArc(String nomDeLArc) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#delNoeud(java.lang.String)
	 */
	@Override
	public boolean delNoeud(String nomDuNoeud) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getArc(java.lang.String)
	 */
	@Override
	public Arc getArc(String nomDeLArc) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getArcs()
	 */
	@Override
	public Set<Arc> getArcs() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getNoeud(java.lang.String)
	 */
	@Override
	public Noeud getNoeud(String nomDuNoeud) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getNoeud(java.awt.Point)
	 */
	@Override
	public Noeud getNoeud(Point position) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getNoeud(int)
	 */
	@Override
	public Noeud getNoeud(int iD) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getNoeuds()
	 */
	@Override
	public Set<Noeud> getNoeuds() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getNoeudsArray()
	 */
	@Override
	public Point[] getNoeudsArray() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getNom()
	 */
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getNomDesArcs()
	 */
	@Override
	public List<String> getNomDesArcs() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getNomDesNoeuds()
	 */
	@Override
	public List<String> getNomDesNoeuds() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getNombreArcs()
	 */
	@Override
	public int getNombreArcs() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getNombreNoeuds()
	 */
	@Override
	public int getNombreNoeuds() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getPoidsArc(graphe.Arc)
	 */
	@Override
	public double getPoidsArc(Arc a) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getPoidsNoeud(graphe.Noeud)
	 */
	@Override
	public double getPoidsNoeud(Noeud n) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#getToutesLesOD()
	 */
	@Override
	public HashMap<Integer, OD> getToutesLesOD() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#setGrapheChange()
	 */
	@Override
	public void setGrapheChange() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#setNom(java.lang.String)
	 */
	@Override
	public void setNom(String nom) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#setPoidsArc(graphe.Arc, double)
	 */
	@Override
	public void setPoidsArc(Arc a, double poids) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see graphe.GrapheGenerique#setPonderationNoeud(graphe.Noeud, double)
	 */
	@Override
	public void setPonderationNoeud(Noeud noeud, double poids) {
		// TODO Auto-generated method stub
		
	}

}