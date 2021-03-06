/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * Graphe.java in fr.ign.cogit.geogaphlab.graphe
 * 
 */

package fr.ign.cogit.geographlab.graphe;

import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.graphe.listeners.GrapheListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.event.EventListenerList;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graphs;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.AsWeightedGraph;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class Graphe extends DirectedWeightedPseudograph<Noeud, Arc>  implements GrapheGenerique {
	
	private static final long serialVersionUID = -4859350121319378867L;
	
	private String nom = new String();
	
	GeometryFactory geomFac = new GeometryFactory();
	
	private final EventListenerList listenersGraphe = new EventListenerList();
	private final EventListenerList listenersPoidsArc = new EventListenerList();
	
	private EdgeFactory<Noeud, Arc> ef;
	
	//	public ArrayList<Point> coordNoeuds = new ArrayList<Point>();
	//	public ArrayList<Noeud> tabNoeuds = new ArrayList<Noeud>();
	
	//	private HashMap<Integer, Noeud> mapNoeud = new HashMap<Integer, Noeud>();
	
	public Graphe(EdgeFactory<Noeud, Arc> arg, String nom) {
		// Using DefaultDirectedWeightedGraph(EdgeFactory<V,E> ef) constructor
		super(arg);
		this.ef = arg;
		setNom(nom);
	}
	
	public AsUndirectedGraph<Noeud, Arc> asUndirectedView(){
		
		AsUndirectedGraph<Noeud, Arc> gUndirected = new AsUndirectedGraph<Noeud, Arc>(this);
		
		return gUndirected;
	}
	
	public AsWeightedGraph<Noeud, Arc> asSimpleWeightedView(){
		
		AsWeightedGraph<Noeud, Arc> gSimpleWeighted = new AsWeightedGraph<Noeud, Arc>(this, null);
		
		return gSimpleWeighted;
	}
	
	public SimpleWeightedGraph<Noeud, Arc> asSimpleWeightedGraph(){
		
		SimpleWeightedGraph<Noeud, Arc> gSW = new SimpleWeightedGraph<Noeud, Arc>(this.ef);
		
		for (Noeud iterNoeud : getNoeuds()) {
			gSW.addVertex(iterNoeud);
		}
		
		for (Arc iterArc : getArcs()) {
			gSW.addEdge(iterArc.getSource(), iterArc.getTarget(), iterArc);
		}
		
		return gSW;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void addNoeud(Noeud noeud) {
		addVertex(noeud);
	}
	
	public boolean delNoeud(Noeud n){
		
		if( this.containsVertex(n) ) {
			
			Set<Arc> arcsVoisins = this.edgesOf(n);
			
			for (Arc iterArc : arcsVoisins) {
				this.removeEdge(iterArc);
			}
			
			//		n.getNoeudGraphique().
			boolean returnValue = removeVertex(n);
			//		fireGrapheChange();
			return returnValue;
		}
		return false;
	}
	
	public boolean delNoeud(String nomDuNoeud) {
		
		Noeud noeudAEffacer = getNoeud(nomDuNoeud);
		if (noeudAEffacer != null) {
			delNoeud(noeudAEffacer);
			//			fireGrapheChange();
			return true;
		}
		return false;
	}
	
//	public List<Noeud> reunirNoeuds(List<Noeud> noeuds) {
//		
//		if( noeuds.size() > 1 ) {
//			
//			Noeud noeudConserve = noeuds.get(0);
//			noeuds.remove(noeudConserve);
//			
//			//Pour tous les noeuds
//			for (Noeud iterNoeud : noeuds) {
//				
//				//On recupere tous les arcs du noeud courant
//				for (Arc arcIssusDuNoeud : this.edgesOf(iterNoeud)) {
//					
//					
//					Arc nouvelArc;
//					
//					//Soit on change la source
//					if( iterNoeud.equals(arcIssusDuNoeud.getSource()) ) {
//						//						arcIssusDuNoeud.changeNoeudSource(noeudConserve);
//						nouvelArc = new Arc(noeudConserve, iterNoeud);
//						
//					} else {
//						
//						//Soit on change la cible
//						//						arcIssusDuNoeud.changeNoeudCible(noeudConserve);
//						nouvelArc = new Arc(iterNoeud, noeudConserve);
//					}
//					
//					//copy geometry
//					nouvelArc.getArcGraphique().setGeom(arcIssusDuNoeud.getArcGraphique().getGeometryLineString());
//					//copy indicateurs
//					nouvelArc.setIndicateurs(arcIssusDuNoeud.getIndicateurs());
//					//add edge and copy weight
//					this.addArcPondere(nouvelArc, arcIssusDuNoeud.getPoids());
//					
//					//remove old edge
//					this.removeEdge(arcIssusDuNoeud);
//					arcIssusDuNoeud = null;
//					
//				}
//				
//			}
//			
//			//On efface les noeuds sauf celui conserve
//			return noeuds;
//		}
//		
//		return null;
//	}
	
	public void setPonderationNoeud(Noeud noeud, double poids) {
		noeud.setPonderation(poids);
	}
	
	public double getPoidsNoeud(Noeud n) {
		
		double valeur = 0;
		
		// Recherche dans les noeuds
		for (Noeud iterNoeud : this.getNoeuds()) {
			if (iterNoeud.equals(n)) {
				valeur = iterNoeud.getPonderation();
				break;
			}
		}
		
		return valeur;
	}
	
	public int getNombreNoeuds() {
		return vertexSet().size();
	}
	
	public Noeud getNoeud(String nomDuNoeud) {
		
		Set<Noeud> mesNoeuds = getNoeuds();
		
		for (Noeud iterNoeud : mesNoeuds) {
			if (iterNoeud.getNom().compareTo(nomDuNoeud) == 0) {
				return iterNoeud;
			}
		}
		return null;
		
	}
	
	public Noeud getNoeud(Point position) {
		
		Set<Noeud> mesNoeuds = getNoeuds();
		
		for (Noeud iterNoeud : mesNoeuds) {
			if (iterNoeud.getNoeudGraphique().getPosition().equals(position)) {
				return iterNoeud;
			}
		}
		return null;
	}
	
	public Noeud getNoeud(int iD) {
		
		Set<Noeud> mesNoeuds = getNoeuds();
		
		for (Noeud iterNoeud : mesNoeuds) {
			if (iterNoeud.getID() == iD) {
				return iterNoeud;
			}
		}
		return null;
	}
	
	public Set<Noeud> getNoeuds() {
		return vertexSet();
	}
	
	public Noeud[] getNoeudsListe() {
		return (Noeud[]) vertexSet().toArray();
	}
	
	public Point[] getNoeudsArray() {
		Point[] mesNoeudsEnTableau = new Point[vertexSet().size()];
		
		int i = 0;
		for (Noeud iterNoeud : vertexSet()) {
			Coordinate coordinate = new Coordinate(iterNoeud.getXPosition(), iterNoeud.getYPosition());
			mesNoeudsEnTableau[i++] = this.geomFac.createPoint(coordinate);
		}
		
		return mesNoeudsEnTableau;
	}
	
	public List<String> getNomDesNoeuds() {
		List<String> nomsDesNoeuds = new ArrayList<String>();
		
		for (Noeud iterNoeud : getNoeuds()) {
			nomsDesNoeuds.add(iterNoeud.getNom());
		}
		
		return nomsDesNoeuds;
	}
	
	public double[] getValeursNoeuds(String nomIndicateur) {
		
		double[] returnTab = new double[getNoeuds().size()];
		int i=0;
		
		for (Noeud iterNoeud : getNoeuds()) {
			returnTab[i++] = iterNoeud.getValeurPourIndicateur(nomIndicateur);
		}
		
		return returnTab;
		
	}
	
	public boolean addArc(Arc a) {
		// fireGrapheChange();
		return addEdge(a.getSource(), a.getTarget(), a);
	}
	
	public boolean addArcPondere(Arc a, double poids) {
		boolean returnValue = addArc(a);
		setEdgeWeight(getEdge(a.getSource(), a.getTarget()), poids);
		return returnValue;
	}
	
	public boolean delArc(String nomDeLArc) {
		
		Arc arcAEffacer = getArc(nomDeLArc);
		if (arcAEffacer != null) {
			removeEdge(arcAEffacer);
			//			fireGrapheChange();
			return true;
		}
		return false;
	}
	
	public boolean delArc(Arc arcAEffacer) {
		
		if (arcAEffacer != null) {
			removeEdge(arcAEffacer);
			//			fireGrapheChange();
			return true;
		}
		return false;
	}
	
	public void setPoidsArc(Arc a, double poids) {
		setEdgeWeight(getEdge(a.getSource(), a.getTarget()), poids);
		// firePoidsArcChange();
	}
	
	public double getPoidsArc(Arc a) {
		return this.getEdgeWeight(a);
	}
	
	public int getNombreArcs() {
		return edgeSet().size();
	}
	
	public Arc getArc(String nomDeLArc) {
		
		Set<Arc> mesArcs = getArcs();
		
		for (Arc iterNoeud : mesArcs) {
			if (iterNoeud.getNom().compareTo(nomDeLArc) == 0) {
				return iterNoeud;
			}
		}
		return null;
	}
	
	public Arc getArc(Noeud nS, Noeud nC) {
		
		return getEdge(nS, nC);
	}
	
	public Set<Arc> getArcs() {
		return edgeSet();
	}
	
	public List<String> getNomDesArcs() {
		List<String> nomsDesArcs = new ArrayList<String>();
		
		for (Arc iterArc : getArcs()) {
			nomsDesArcs.add(iterArc.getNom());
		}
		return nomsDesArcs;
	}
	
	public double[] getValeursArcs(String nomIndicateur) {
		
		double[] returnTab = new double[getArcs().size()];
		int i=0;
		
		for (Arc iterArc : getArcs()) {
			returnTab[i++] = iterArc.getValeurPourIndicateur(nomIndicateur);
		}
		
		return returnTab;
		
	}
	
	public double[] getValeursNoeudsArcs(String nomIndicateur) {
		
		double[] returnTab = new double[getNoeuds().size() + getArcs().size()];
		int i=0;
		
		for (Noeud iterNoeud : getNoeuds()) {
			returnTab[i++] = iterNoeud.getValeurPourIndicateur(nomIndicateur);
		}
		
		for (Arc iterArc : getArcs()) {
			returnTab[i++] = iterArc.getValeurPourIndicateur(nomIndicateur);
		}
		
		return returnTab;
		
	}
	
	public HashMap<Integer, OD> getToutesLesOD() {
		
		//		Collection<Noeud> collec = getNoeuds();
		HashMap<Integer, OD> returnToutesLesOD = new HashMap<Integer, OD>();
		
		Object[] mesNoeudsEnTableau = getNoeuds().toArray();
		
		//		this.carte.variablesDeCarte.afficheGrapheNonDirige == false
		
		for (int i = 0; i < mesNoeudsEnTableau.length; i++) {
			for (int j = i + 1; j < mesNoeudsEnTableau.length; j++) {
				
				OD locaOd = new OD((Noeud) mesNoeudsEnTableau[i], (Noeud) mesNoeudsEnTableau[j]);
				
				returnToutesLesOD.put(new Integer(locaOd.hashCode()), locaOd);
				// System.out.println(((Noeud)mesNoeudsEnTableau[i]).getNom() +
				// " - " + ((Noeud)mesNoeudsEnTableau[j]).getNom());
			}
		}
		return returnToutesLesOD;
	}
	
	public HashMap<Integer, OD> getToutesLesODUDirected() {
		
		//		Collection<Noeud> collec = getNoeuds();
		HashMap<Integer, OD> returnToutesLesOD = new HashMap<Integer, OD>();
		
		Object[] mesNoeudsEnTableau = getNoeuds().toArray();
		
		//		this.carte.variablesDeCarte.afficheGrapheNonDirige == false
		
		for (int i = 0; i < mesNoeudsEnTableau.length; i++) {
			for (int j = 0; j < mesNoeudsEnTableau.length; j++) {
				
				OD locaOd = new OD((Noeud) mesNoeudsEnTableau[i], (Noeud) mesNoeudsEnTableau[j]);
				
				returnToutesLesOD.put(new Integer(locaOd.hashCode()), locaOd);
				// System.out.println(((Noeud)mesNoeudsEnTableau[i]).getNom() +
				// " - " + ((Noeud)mesNoeudsEnTableau[j]).getNom());
			}
		}
		return returnToutesLesOD;
	}
	
	public void setGrapheChange() {
		fireGrapheChange();
	}
	
	public void addPoidsArcChange(GrapheListener listener) {
		this.listenersPoidsArc.add(GrapheListener.class, listener);
	}
	
	public void removePoidsArcChange(GrapheListener listener) {
		this.listenersPoidsArc.remove(GrapheListener.class, listener);
	}
	
	public GrapheListener[] getPoidsArcChangeListener() {
		return this.listenersPoidsArc.getListeners(GrapheListener.class);
	}
	
	protected void firePoidsArcChange() {
		for (GrapheListener listener : getPoidsArcChangeListener()) {
			listener.poidsArcsChange();
		}
	}
	
	public void addGrapheListener(GrapheListener listener) {
		this.listenersGraphe.add(GrapheListener.class, listener);
	}
	
	public void removeGrapheListener(GrapheListener listener) {
		this.listenersGraphe.remove(GrapheListener.class, listener);
	}
	
	public GrapheListener[] getGrapheListeners() {
		return this.listenersGraphe.getListeners(GrapheListener.class);
	}
	
	public void fireGrapheChange() {
		for (GrapheListener listener : getGrapheListeners()) {
			listener.grapheChange();
		}
	}
	
	@Override
	public Graphe clone() {
		
		Graphe leGrapheACloner = (Graphe) super.clone();
		
		Graphe nouveauGraphe = new Graphe(new ArcFactory(), this.nom);
		
		Graphs.addAllVertices(nouveauGraphe, leGrapheACloner.getNoeuds());
		Graphs.addAllEdges(nouveauGraphe, leGrapheACloner, leGrapheACloner.getArcs());
		
		return nouveauGraphe;
	}
	
	public Graphe deepClone() {
		// Clonage en profondeur d'un graphe
		Graphe nouveauGraphe = new Graphe(new ArcFactory(), this.nom);
		
		for (Noeud iterNoeud : this.vertexSet()) {
			nouveauGraphe.addNoeud(iterNoeud.clone());
		}
		
		for (Arc iterArc : this.edgeSet()) {
			
			Noeud n1 = nouveauGraphe.getNoeud(iterArc.getSource().getNom());
			Noeud n2 = nouveauGraphe.getNoeud(iterArc.getTarget().getNom());
			
			Arc a = new Arc(n1, n2);
			a.setPoidsDistance(iterArc.getPoids());
			a.setPoidsTemps(iterArc.getPoids());
			
			nouveauGraphe.addArcPondere(a, iterArc.getPoids());
		}
		
		return nouveauGraphe;
	}
	
}