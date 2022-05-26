/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.exploration;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import javax.swing.event.EventListenerList;

import org.jgrapht.graph.AsUndirectedGraph;

import fr.ign.cogit.geographlab.cheminements.Chemin;
import fr.ign.cogit.geographlab.exploration.events.CarteChangeEvent;
import fr.ign.cogit.geographlab.exploration.events.CarteListener;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.ArcFactory;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.PanelMainDraw;
import fr.ign.cogit.geographlab.ihm.exploration.BlocCarte;
import fr.ign.cogit.geographlab.util.TimerClignotant;
import megamu.mesh.MPolygon;

public class Carte implements Cloneable {
	
	public MainWindow fenetrePrincipale;
	
	private String nom = new String("");
	private String nomIndicateurCourant = new String("Normal");
	public int niveau;
	
	private String commentairesUtilisateur = new String();
	private boolean selected = true;
	private Color colorLayer = Color.GREEN;
	
	public PanelMainDraw parent;
	
	public ArcFactory edgeFactory = new ArcFactory();
	
	private Graphe g;//, gR;
	
	AsUndirectedGraph<Noeud, Arc> gNonDirige;
	private MPolygon[] polygonesDeVoronoi;
	
	private EventListenerList listeners;
	
	// Classe pour l'Exploration
	Espace espace;
	Indicateur indicateur;
	VueDuGraphe vueDuGraphe;
	VueDuGraphe vueDuGrapheDelaunay;
	Legende legendeDeLaCarte;
	
	public Carte carteMere = null;
	private boolean isParent = false;
//	private boolean isResultante = false;
	boolean conservationValeurAlaCopie = false;
	public BlocCarte carteExplo;
	
	public VariablesDeCarte variablesDeCarte;
	
	private HashMap<Integer, Chemin> tousLesPCC = new HashMap<Integer, Chemin>();
	
	public TimerClignotant timerClignotantObjetSelectionnes;
	
	public Carte(PanelMainDraw parent) {
		this.parent = parent;
		
		this.fenetrePrincipale = parent.parent;
		setNom(this.parent.getNom());
		
		this.variablesDeCarte = new VariablesDeCarte();
		
		razCarte();
		
		this.isParent = true;
		this.niveau = this.parent.couchesDeCartes.getNombreDeCouchesParentes()-1;
		this.carteMere = this;
		this.setNomIndicateurCourant(this.nom);
		
		this.carteExplo = new BlocCarte(this, true);
		//		 this.carteExplo.init();
		
		this.parent.couchesDeCartes.ajouterUneCoucheCarte(this);
		
		this.colorLayer = new Color(0, new Random().nextInt(256), 0);
		
	}
	
//	public Carte(PanelMainDraw parent, String nom, String nomIndicateur, boolean isResultante) {
//		this.isResultante = isResultante;
//		//		this(parent,nom,nomIndicateur);
//	}
	
	public Carte(PanelMainDraw parent, String nom, String nomIndicateur) {
		
		this.parent = parent;
		
		this.fenetrePrincipale = parent.parent;
		setNom(nom);
		this.variablesDeCarte = new VariablesDeCarte();
		
		razCarte();
		this.g = parent.carte.getGraphe().clone();
//		this.gR = parent.carte.getGrapheDeReference().clone();
		
		this.vueDuGraphe = new VueDuGraphe(this.fenetrePrincipale, this, this.nom, this.g);
		
		this.espace = parent.carte.getEspace().clone();
		
		this.legendeDeLaCarte = new Legende(this.fenetrePrincipale, this, parent.carte.getLegendeDeLaCarte().getTypeLegend(), parent.carte.getLegendeDeLaCarte().getIntervalles(), parent.carte.getLegendeDeLaCarte().getColors(), parent.carte
				.getLegendeDeLaCarte().min, parent.carte.getLegendeDeLaCarte().max);
		
		this.setNomIndicateurCourant(nomIndicateur);
		
		this.carteExplo = new BlocCarte(this, false);
		
		this.parent.couchesDeCartes.ajouterUneCoucheCarte(this);
		
		fireCarteChanged();
		
		this.parent.repaint();
	}
	
	public Carte(Carte carteACopier, String nom, String nomIndicateur) {// ,
		// boolean
		// conservationValeurAlaCopie){
		this.parent = carteACopier.parent;
		
		this.fenetrePrincipale = carteACopier.parent.parent;
		
		if(nom.compareTo("Nouvelle_Couche_Copie") == 0)
			setNom("cp_" + carteACopier.getNom() );
		else
			setNom(nom);
		
		this.variablesDeCarte = new VariablesDeCarte();
		//		this.nomIndicateurCourant = getNom();//carteACopier.getNomIndicateurCourant();
		setNomIndicateurCourant(nomIndicateur);
		
		// this.conservationValeurAlaCopie = conservationValeurAlaCopie;
		copierLaCarte(carteACopier, nom);
		
		this.carteExplo = new BlocCarte(this, false);
		// this.carteExplo.init();
		
		this.parent.couchesDeCartes.ajouterUneCoucheCarte(this);
	}
	
	public Graphe getGraphe() {
		return this.g;
	}
	
	public void setGraphe(Graphe g) {
		this.g = g;
		//		this.gNonDirige = new AsUndirectedGraph(g);
	}
	
//	public Graphe getGrapheDeReference() {
//		return this.gR;
//	}
//	
//	public void setGrapheDeReference(Graphe gR) {
//		this.gR = gR;
//	}
	
	public void setPolygonesDeVoronoi(MPolygon[] polygonesDeVoronoi) {
		this.polygonesDeVoronoi = polygonesDeVoronoi;
	}
	
	public MPolygon[] getPolygonesDeVoronoi() {
		return this.polygonesDeVoronoi;
	}
	
	public void razCarte() {
		
		System.gc();
		
		this.niveau = 0;
		
		// this.parent.parent.panelExplo.nbCartesParNiveau.set(this.niveau, new
		// Integer(this.parent.parent.panelExplo.nbCartesParNiveau.get(this.niveau)
		// + 1));
		
		this.g = new Graphe(this.edgeFactory, this.nom);
//		this.gR = new Graphe(this.edgeFactory, this.nom.concat("_Delaunay"));
		this.polygonesDeVoronoi = null;
		System.out.println(this.g.getNom());
		this.espace = new Espace(this.fenetrePrincipale, this.g.getToutesLesOD());
		// this.espace.addEspaceListener(new EspaceEvent(){
		// //Mettre a jour tableau OD
		// });
		
		this.vueDuGraphe = new VueDuGraphe(this.fenetrePrincipale, this, this.nom, this.g);
//		this.vueDuGrapheDelaunay = new VueDuGraphe(this.fenetrePrincipale, this, this.nom.concat("_Delaunay"), this.gR);
		this.legendeDeLaCarte = new Legende(this.fenetrePrincipale, this );
		this.indicateur = new Indicateur();
		// this.valeurs = new ValeursVue(this.g);
		this.setNomIndicateurCourant("none");
		
		this.timerClignotantObjetSelectionnes = new TimerClignotant(this);
		
		this.g.setGrapheChange();
		
		this.listeners = new EventListenerList();
		
		fireCarteChanged();
		
		this.parent.repaint();
	}
	
	public void copierLaCarte(Carte carteACopier, String nomCarte) {
		
		System.gc();
		
		this.carteMere = carteACopier;
		
		this.niveau = carteACopier.niveau + 1;
		
		this.parent.panelExplo.getJPanel().nbCartesParNiveau[this.niveau]++;
		
		// Clonage des graphes
		this.g = carteACopier.getGraphe().clone();
//		this.gR = carteACopier.getGrapheDeReference().clone();
		
		for (Arc iterArc : this.g.getArcs()) {
			if( iterArc.getValeurPourIndicateur(carteACopier.getNomIndicateurCourant()) != null ) {
				iterArc.setIndicateurValeur(getNomIndicateurCourant(), iterArc.getValeurPourIndicateur(carteACopier.getNomIndicateurCourant()).doubleValue());
			}else{
				iterArc.setIndicateurValeur(getNomIndicateurCourant(), 1.0);
			}
		}
		
		for (Noeud iterNoeud : this.g.getNoeuds()) {
			if( iterNoeud.getValeurPourIndicateur(carteACopier.getNomIndicateurCourant()) != null ) 
				iterNoeud.setIndicateurValeur(getNomIndicateurCourant(), iterNoeud.getValeurPourIndicateur(carteACopier.getNomIndicateurCourant()).doubleValue());
			else
				iterNoeud.setIndicateurValeur(getNomIndicateurCourant(), 1.0);
		}
		
		// this.g = new Graphe(new ArcFactory(), nom);
		// Graphs.addAllVertices(carteACopier.getGraphe(),
		// carteACopier.getGraphe().getNoeuds());
		// Graphs.addAllEdges(this.g, carteACopier.getGraphe(),
		// carteACopier.getGraphe().getArcs());
		
		// if (this.conservationValeurAlaCopie) {
		// for
		// }
		
		this.polygonesDeVoronoi = null;
		System.out.println(this.g.getNom());
		this.espace = carteACopier.getEspace().clone(); // new
		// Espace(this.fenetrePrincipale,
		// this.g.getToutesLesOD());
		// this.espace.addEspaceListener(new EspaceEvent(){
		// //Mettre a jour tableau OD
		// });
		this.vueDuGraphe = new VueDuGraphe(this.fenetrePrincipale, this, this.nom, this.g);
//		this.vueDuGrapheDelaunay = new VueDuGraphe(this.fenetrePrincipale, this, this.nom.concat("_Delaunay"), this.gR);
		this.legendeDeLaCarte = new Legende(this.fenetrePrincipale, this, carteACopier.getLegendeDeLaCarte().getTypeLegend(), carteACopier.getLegendeDeLaCarte().getIntervalles(), carteACopier.getLegendeDeLaCarte().getColors(), carteACopier
				.getLegendeDeLaCarte().min, carteACopier.getLegendeDeLaCarte().max);
		// clonage a revoir
		this.indicateur = (Indicateur) carteACopier.getIndicateur().clone();
		// this.valeurs = new ValeursVue(this.g);
		
		this.setTousLesPCC(carteACopier.getTousLesPCC());
		
		this.timerClignotantObjetSelectionnes = new TimerClignotant(this);
		
		this.g.setGrapheChange();
		
		this.listeners = new EventListenerList();
		
		fireCarteChanged();
		
		this.parent.repaint();
	}
	
	public void addNoeud(Noeud n) {
		this.g.addNoeud(n);
	}
	
	public void delNoeud(Noeud n) {
		this.g.removeVertex(n);
	}
	
	public void addArc(Arc a) {
		this.g.addArc(a);
	}
	
	public void delArc(Arc a) {
		this.g.removeEdge(a);
	}
	
	public Espace getEspace() {
		return this.espace;
	}
	
	public void setEspace(Espace espace) {
		this.espace = espace;
	}
	
	public Indicateur getIndicateur() {
		return this.indicateur;
	}
	
	public void setIndicateur(Indicateur indicateur) {
		this.indicateur = indicateur;
	}
	
	public VueDuGraphe getVueDuGraphe() {
		return this.vueDuGraphe;
	}
	
	public void setVueDuGraphe(VueDuGraphe vueDuGraphe) {
		this.vueDuGraphe = vueDuGraphe;
		fireCarteChanged();
	}
	
	public VueDuGraphe getVueDuGrapheDeReference() {
		return this.vueDuGrapheDelaunay;
	}
	
	public void setVueDuGrapheDeReference(VueDuGraphe vueDuGrapheDelaunay) {
		this.vueDuGrapheDelaunay = vueDuGrapheDelaunay;
		fireCarteChanged();
	}
	
	public Legende getLegendeDeLaCarte() {
		return this.legendeDeLaCarte;
	}
	
	public void setLegendeDeLaCarte(Legende legendeDeLaCarte) {
		this.legendeDeLaCarte = legendeDeLaCarte;
		this.legendeDeLaCarte.setLegendesNoeudsArcsZonePourValeurs();
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNomIndicateurCourant(String nomIndicateurCourant) {
		this.nomIndicateurCourant = nomIndicateurCourant;
	}
	
	public String getNomIndicateurCourant() {
		return this.nomIndicateurCourant;
	}
	
	public boolean isSelected() {
		return this.selected;
	}
	
	public boolean isParent() {
		return this.isParent;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public Color getColorLayer() {
		return this.colorLayer;
	}
	
	public void setColorLayer(Color colorLayer) {
		this.colorLayer = colorLayer;
	}
	
	public void setCommentairesUtilisateur(String commentairesUtilisateur) {
		this.commentairesUtilisateur = commentairesUtilisateur;
	}
	
	public String getCommentairesUtilisateur() {
		return this.commentairesUtilisateur;
	}
	
	public void setTousLesPCC(HashMap<Integer, Chemin> tousLesPCC) {
		this.tousLesPCC = tousLesPCC;
	}
	
	public HashMap<Integer, Chemin> getTousLesPCC() {
		return this.tousLesPCC;
	}
	
	@Override
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("MyObject can't clone");
		}
		return o;
	}
	
	public void prepareEffaceCarte() {
		
		this.fenetrePrincipale = null;
		
		this.nom = null;
		this.nomIndicateurCourant = null;
		
		this.commentairesUtilisateur = null;
		this.colorLayer = null;
		
		this.parent = null;
		
		this.edgeFactory = null;
		
		this.g = null;
		
		this.gNonDirige = null;
		this.polygonesDeVoronoi = null;
		
		this.listeners = null;
		
		this.espace = null;
		this.indicateur = null;
		this.vueDuGraphe = null;
		this.vueDuGrapheDelaunay = null;
		this.legendeDeLaCarte = null;
		
		this.carteMere = null;
		this.carteExplo = null;
		
		this.variablesDeCarte = null;
		
		this.tousLesPCC = null;
		
		this.timerClignotantObjetSelectionnes = null;
	}
	
	public void addCarteListener(CarteListener l) {
		this.listeners.add(CarteListener.class, l);
	}
	
	public void removeCarteListener(CarteListener l) {
		this.listeners.remove(CarteListener.class, l);
	}
	
	public void fireCarteChanged() {
		CarteListener[] listenerList = this.listeners.getListeners(CarteListener.class);
		
		for (CarteListener listener : listenerList) {
			listener.carteChanged((new CarteChangeEvent(this)));
		}
	}
	
	public void repaint(){
		this.fenetrePrincipale.panelActif.repaint();
	}
	
}