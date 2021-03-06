/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.exploration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Graphe;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.graphe.event.GrapheEvent;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.visu.ArcGraphique;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;
import fr.ign.cogit.geographlab.visu.ZoneAgregee;

public class VueDuGraphe {
	
	MainWindow fenetrePrincipale;
	
	private String nom = new String();
	
	public boolean locked = false;
	
	Graphe grapheDeLaVue = null;
	
	public Set<NoeudGraphique> noeudsGraphiques;
	public GeometryCollection collectionPoints;
	public Set<ArcGraphique> arcsGraphiques;
	public Set<ObjetGraphique> objetsGraphiques;
	public Set<ZoneAgregee> zonesAgregees;
	
	private GrapheEvent event(Graphe g) {
		GrapheEvent evt = new GrapheEvent(this.fenetrePrincipale, this, g);
		
		// System.out.println("pouet");
		if (evt.grapheChange()) {
			// System.out.println("youpi");
		}
		return evt;
	}
	
	public VueDuGraphe(MainWindow fenetrePrincipale, Carte carte, String nom, Graphe g) {
		this.fenetrePrincipale = fenetrePrincipale;
		setNom(nom);
		this.grapheDeLaVue = g;
		
		this.objetsGraphiques = new HashSet<ObjetGraphique>();
		this.noeudsGraphiques = new HashSet<NoeudGraphique>();
		this.arcsGraphiques = new HashSet<ArcGraphique>();
		this.zonesAgregees = new HashSet<ZoneAgregee>();
		
		// Construction des noeuds et arcs graphiques a partir du graphe
		// topologique
		setNoeudsGraphiques(this.grapheDeLaVue);
		setArcsGraphiques(this.grapheDeLaVue);
		g.addGrapheListener(this.event(g));
		g.addPoidsArcChange(this.event(g));
	}
	
	public void effacerObjetsGraphiques() {
		this.noeudsGraphiques = new HashSet<NoeudGraphique>();
		this.arcsGraphiques = new HashSet<ArcGraphique>();
	}
	
	public Graphe getGrapheDeLaVue() {
		return this.grapheDeLaVue;
	}
	
	public void setGrapheDeLaVue(Graphe grapheDeLaVue) {
		this.grapheDeLaVue = grapheDeLaVue;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNomGraphe() {
		return this.grapheDeLaVue.getNom();
	}
	
	public void addNoeud(NoeudGraphique n) {
		this.noeudsGraphiques.add(n);
		this.objetsGraphiques.add(n);
	}
	
	public void removeNoeudGraphique(NoeudGraphique noeudG) {
		
		if( this.grapheDeLaVue.containsVertex(noeudG.getNoeudTopologique()) ) {
			
			Set<Arc> arcsVoisins = this.grapheDeLaVue.edgesOf(noeudG.getNoeudTopologique());
			
			//On retire les arcs lies au noeud que l'on efface
			if( !arcsVoisins.isEmpty() ) {
				for (Arc iterArc : arcsVoisins) {
					this.arcsGraphiques.remove(iterArc.getArcGraphique());
					this.objetsGraphiques.remove(iterArc.getArcGraphique());
				}
			}
			
			//Puis le noeud
			this.noeudsGraphiques.remove(noeudG);
			this.objetsGraphiques.remove(noeudG);
			
		}
		
	}
	
	public void removeNoeudGraphique(String nomDuNoeudTopologique) {
		
		Noeud n = getNoeudGraphique(nomDuNoeudTopologique).getNoeudTopologique();
		
		if( this.grapheDeLaVue.containsVertex(n) ) {
			
			Set<Arc> arcsVoisins = this.grapheDeLaVue.edgesOf(n);
			
			//On retire les arcs lies au noeud que l'on efface
			if( !arcsVoisins.isEmpty() ) {
				for (Arc iterArc : arcsVoisins) {
					this.arcsGraphiques.remove(iterArc.getArcGraphique());
					this.objetsGraphiques.remove(iterArc.getArcGraphique());
				}
			}
			
			//Puis le noeud
			this.noeudsGraphiques.remove(getNoeudGraphique(nomDuNoeudTopologique));
			this.objetsGraphiques.remove(getNoeudGraphique(nomDuNoeudTopologique));
			
		}
	}
	
	public void setNoeudsGraphiques(Set<NoeudGraphique> noeudsGraphique) {
		this.noeudsGraphiques = noeudsGraphique;
		this.objetsGraphiques.addAll(noeudsGraphique);
	}
	
	public void setNoeudsGraphiques(Graphe g) {
		
		for (Noeud iterNoeud : g.getNoeuds()) {
			// NoeudGraphique nG = new NoeudGraphique(iterNoeud);
			NoeudGraphique nG = iterNoeud.getNoeudGraphique();
			nG.setPosition(iterNoeud.getPosition());
			addNoeud(nG);
		}
	}
	
	public void addArc(ArcGraphique a) {
		this.arcsGraphiques.add(a);
		this.objetsGraphiques.add(a);
	}
	
	public void removeArcGraphique(String nomDeLArcTopologique) {
		this.arcsGraphiques.remove(getArcGraphique(nomDeLArcTopologique));
		this.objetsGraphiques.remove(getArcGraphique(nomDeLArcTopologique));
	}
	
	public void removeArcGraphique(ArcGraphique arcG) {
		this.arcsGraphiques.remove(arcG);
		this.objetsGraphiques.remove(arcG);
	}
	
	public void setArcsGraphiques(Graphe g) {
		
		for (Arc iterArc : g.getArcs()) {
			// ArcGraphique aG = new ArcGraphique(iterArc);
			ArcGraphique aG = iterArc.getArcGraphique();
			addArc(aG);
		}
	}
	
	public Set<ObjetGraphique> getObjetsGraphiques() {
		return this.objetsGraphiques;
	}
	
	public Set<NoeudGraphique> getNoeudsGraphiques() {
		return this.noeudsGraphiques;
	}
	
	//	public boolean delNoeud(NoeudGraphique n) {
	//		Set<Arc> arcsVoisins = this.grapheDeLaVue.edgesOf(n.getNoeudTopologique());
	//		
	//		for (Arc iterArc : arcsVoisins) {
	//			this.delArc(iterArc.getArcGraphique());
	//		}
	//		
	//		boolean returnValue = this.getNoeudsGraphiques().remove(n);
	//		return returnValue;
	//	}
	
	public boolean delArc(ArcGraphique arcAEffacer) {
		if (arcAEffacer != null) {
			this.removeArcGraphique(arcAEffacer);
			return true;
		}
		return false;
	}
	
	public NoeudGraphique getNoeudGraphique(String nomDuNoeud) {
		
		Set<NoeudGraphique> mesNoeuds = getNoeudsGraphiques();
		
		for (NoeudGraphique iterNoeud : mesNoeuds) {
			if (iterNoeud.getNom().compareTo(nomDuNoeud) == 0) {
				return iterNoeud;
			}
		}
		return null;
	}
	
	public void setArcsGraphiques(Set<ArcGraphique> arcsGraphiques) {
		this.arcsGraphiques = arcsGraphiques;
		this.objetsGraphiques.addAll(arcsGraphiques);
	}
	
	public Set<ArcGraphique> getArcsGraphiques() {
		return this.arcsGraphiques;
	}
	
	public ArcGraphique getArcGraphique(String nomDeLarc) {
		
		Set<ArcGraphique> mesArcs = getArcsGraphiques();
		
		for (ArcGraphique iterNoeud : mesArcs) {
			if (iterNoeud.getNom().compareTo(nomDeLarc) == 0) {
				return iterNoeud;
			}
		}
		return null;
	}
	
	public void addZoneAgregee(ZoneAgregee zoneAgregee) {
		this.zonesAgregees.add(zoneAgregee);
		this.objetsGraphiques.add(zoneAgregee);
	}
	
	public void addZonesAgregees(Set<ZoneAgregee> zonesAgregeesInput ) {
		this.zonesAgregees.addAll(zonesAgregeesInput);
		this.objetsGraphiques.addAll(zonesAgregeesInput);
	}
	
	public void removeZoneAgregee(ZoneAgregee zoneAgregee) {
		this.zonesAgregees.remove(zoneAgregee);
		this.objetsGraphiques.remove(zoneAgregee);
	}
	
	public Set<ZoneAgregee> getZonesAgregees() {
		return this.zonesAgregees;
	}
	
	public ZoneAgregee getZoneAgregee(String nomDeLaZone) {
		
		Set<ZoneAgregee> mesZones = getZonesAgregees();
		
		for (ZoneAgregee iterZone : mesZones) {
			if (iterZone.getNom().compareTo(nomDeLaZone) == 0) {
				return iterZone;
			}
		}
		return null;
	}
	
	public void unselectAll() {
		Set<NoeudGraphique> mesNoeudsGraphiques = getNoeudsGraphiques();
		for (NoeudGraphique iterNoeudGraphique : mesNoeudsGraphiques) {
			iterNoeudGraphique.setSelected(false);
		}
		
		Set<ArcGraphique> mesArcssGraphiques = getArcsGraphiques();
		for (ArcGraphique iterArcGraphique : mesArcssGraphiques) {
			iterArcGraphique.setSelected(false);
		}
	}
	
	public void selectionObjet(String nomDeLobj) {
		
		Set<NoeudGraphique> mesNoeudsGraphiques = getNoeudsGraphiques();
		for (NoeudGraphique iterNoeudGraphique : mesNoeudsGraphiques) {
			if (iterNoeudGraphique.getNom().compareTo(nomDeLobj) == 0) {
				iterNoeudGraphique.setSelected(true);
				return;
			}
		}
		
		Set<ArcGraphique> mesArcssGraphiques = getArcsGraphiques();
		for (ArcGraphique iterArcGraphique : mesArcssGraphiques) {
			if (iterArcGraphique.getNom().compareTo(nomDeLobj) == 0) {
				iterArcGraphique.setSelected(true);
				return;
			}
		}
		
	}
	
	public void selectionObjet(ObjetGraphique objGraph) {
		
		for (ObjetGraphique iterObjetGraphique : getObjetsGraphiques()) {
			iterObjetGraphique.setSelected(false);
		}
		objGraph.setSelected(true);
	}
	
	public void effaceSelection() {
		
		System.out.println("Delete : en cours ");
		//		int i=0;
		for (ObjetGraphique itGO : this.fenetrePrincipale.panelActif.listOfSelectedObjects ) {
			try {
				
				switch (itGO.getType()) {
					case ConstantesApplication.typeVertex:
						//						System.out.println("suppression noeud " + itGO.getNom());
						this.fenetrePrincipale.panelActif.carte.getVueDuGraphe().removeNoeudGraphique((NoeudGraphique)itGO);
						this.fenetrePrincipale.panelActif.carte.getGraphe().delNoeud(((NoeudGraphique)itGO).getNoeudTopologique());
						break;
					case ConstantesApplication.typeEdge:
						//						System.out.println("suppression arc " + itGO.getNom());
						this.fenetrePrincipale.panelActif.carte.getVueDuGraphe().getArcsGraphiques().remove(itGO);
						this.fenetrePrincipale.panelActif.carte.getGraphe().delArc(((ArcGraphique)itGO).getArcTopologique());
						break;
					default:
						break;
				}
				
				//				System.out.println(++i +"/"+this.fenetrePrincipale.panelActif.listOfSelectedObjects.size());
			} catch (Exception e) {
				// TODO: handle exception
				//				System.out.println("suppression d??j?? effectu??e");
			}
			
		}
		//		System.out.println("fire graphChange!");
		this.fenetrePrincipale.panelActif.carte.getGraphe().fireGrapheChange();
		
	}
	
	public void reunirNoeudsSurSelection() {
		
		System.out.println("Reunion de noeuds sur selection");
		
		List<Noeud> listNoeuds = new ArrayList<Noeud>();
		
		for (ObjetGraphique itGO : this.fenetrePrincipale.panelActif.listOfSelectedObjects ) {
			
			switch (itGO.getType()) {
				case ConstantesApplication.typeVertex:
					listNoeuds.add(((NoeudGraphique)itGO).getNoeudTopologique());
					break;
				case ConstantesApplication.typeEdge:
					JOptionPane.showMessageDialog(this.fenetrePrincipale, "Seul la s??lection de noeuds est autoris??e");
					return;
				default:
					break;	
			}
		}
		
		reunirNoeuds(listNoeuds, true);
		
	}
	
	public List<Noeud> reunirNoeuds(List<Noeud> listNoeuds, boolean removeNodes) {
//		System.out.println("Reunion de noeuds ");
		
		//On efface les noeuds renvoyes
		if( listNoeuds.size() > 1 ) {
			
			Noeud noeudConserve = listNoeuds.get(0);
			listNoeuds.remove(noeudConserve);
			
			//Pour tous les noeuds
			for (Noeud iterNoeud : listNoeuds) {
				
				//On recupere tous les arcs du noeud courant
				for (Arc arcIssusDuNoeud : this.grapheDeLaVue.edgesOf(iterNoeud)) {
					
					Arc nouvelArc;
					
					//Soit on change la source
					if( iterNoeud.equals(arcIssusDuNoeud.getSource()) ) {
						//						arcIssusDuNoeud.changeNoeudSource(noeudConserve);
						nouvelArc = new Arc(noeudConserve, arcIssusDuNoeud.getTarget());
						
					} else {
						
						//Soit on change la cible
						//						arcIssusDuNoeud.changeNoeudCible(noeudConserve);
						nouvelArc = new Arc(arcIssusDuNoeud.getSource(), noeudConserve);
					}
					
					//copy geometry
					nouvelArc.getArcGraphique().setGeom(arcIssusDuNoeud.getArcGraphique().getGeomClone());
					//copy indicateurs
					nouvelArc.setIndicateurs(arcIssusDuNoeud.getIndicateursClone());
					//add edge and copy weight
					this.grapheDeLaVue.addArcPondere(nouvelArc, arcIssusDuNoeud.getPoids());
					this.addArc(nouvelArc.getArcGraphique());
					
					//remove old edge
					this.fenetrePrincipale.panelActif.carte.getVueDuGraphe().getArcsGraphiques().remove(arcIssusDuNoeud.getArcGraphique());
					this.fenetrePrincipale.panelActif.carte.getGraphe().delArc(arcIssusDuNoeud);
					
				}
				
			}
			
			if( removeNodes ){
				if( listNoeuds.size() > 0)
					for (Noeud noeud : listNoeuds) {
						this.fenetrePrincipale.panelActif.carte.getVueDuGraphe().removeNoeudGraphique(noeud.getNoeudGraphique());
						this.fenetrePrincipale.panelActif.carte.getGraphe().delNoeud(noeud);
					}
				this.fenetrePrincipale.panelActif.carte.getGraphe().setGrapheChange();
				return null;
			}
			
		}
		return listNoeuds;
		
	}
	
	public Point[] getDimensionVue() {
		
		double xMin = Double.MAX_VALUE;
		double yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE;
		double yMax = Double.MIN_VALUE;
		
		boolean flagNeg = false;
		
		for (NoeudGraphique iterNoeudGraphique : this.noeudsGraphiques) {
			xMin = Math.min(xMin, iterNoeudGraphique.getXPosition());
			xMax = Math.max(xMax, iterNoeudGraphique.getXPosition());
			
			yMin = Math.min(yMin, (iterNoeudGraphique.getYPosition() > 0) ? iterNoeudGraphique.getYPosition() : -iterNoeudGraphique.getYPosition());
			yMax = Math.max(yMax, (iterNoeudGraphique.getYPosition() > 0) ? iterNoeudGraphique.getYPosition() : -iterNoeudGraphique.getYPosition());
			
			if( iterNoeudGraphique.getYPosition() < 0)
				flagNeg = true;
		}
		
		if(flagNeg) {
			yMin = -yMin;
			yMax = -yMax;
		}
		
		Point[] returnPoint = new Point[2];
		
		returnPoint[0] = new GeometryFactory().createPoint(new Coordinate(xMin, yMin));
		returnPoint[1] = new GeometryFactory().createPoint(new Coordinate(xMax, yMax));
		
		return returnPoint;
	}
}