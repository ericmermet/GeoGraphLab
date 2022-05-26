/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.exploration;

import fr.ign.cogit.geographlab.algo.MyHashSet;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.listeners.EspaceListener;
import fr.ign.cogit.geographlab.graphe.Noeud;

import java.util.HashMap;
import java.util.Set;

import javax.swing.event.EventListenerList;


public class CopyOfEspace {
	
	private MyHashSet<OD> espaceDeDef;
	public HashMap<Integer, OD> espaceDeDefEnMap = new HashMap<Integer, OD>();
	
	private final EventListenerList listenersEspace = new EventListenerList();
	
	public CopyOfEspace(MyHashSet<OD> odEspace) {
		setEspaceDeDef(odEspace);
	}
	
	public void setEspaceDeDef(MyHashSet<OD> espaceDeDef) {
		this.espaceDeDef = espaceDeDef;
	}
	
	public void setToutesLesOD(Set<Noeud> tousLesNoeuds) {
		
		// Collection<Noeud> collec = getNoeuds();
		MyHashSet<OD> returnToutesLesOD = new MyHashSet<OD>();
		
		Object[] mesNoeudsEnTableau = tousLesNoeuds.toArray();
		
		for (int i = 0; i < mesNoeudsEnTableau.length; i++) {
			for (int j = i + 1; j < mesNoeudsEnTableau.length; j++) {
				returnToutesLesOD.add(new OD((Noeud) mesNoeudsEnTableau[i], (Noeud) mesNoeudsEnTableau[j]));
			}
		}
		setEspaceDeDef(returnToutesLesOD);
	}
	
	public MyHashSet<OD> getEspaceDeDef() {
		return this.espaceDeDef;
	}
	
	public void clearEspaceDeDef() {
		this.espaceDeDef.clear();
	}
	
	public OD getOD(String nomOD) {
		// HashSet<OD> mesODs = getEspaceDeDef();
		
		// TODO revoir la recherche dans la collection et redfinir equals
		// Collections.binarySearch(this.espaceDeDef, nomDeLOD)
		
		for (OD iterOD : this.espaceDeDef) {
			if (iterOD.getNom().compareTo(nomOD) == 0) {
				return iterOD;
			}
		}
		return null;
	}
	
	public OD getOD(String nomOrigine, String nomDestination) {
		
		// TODO revoir la recherche dans la collection et redfinir equals ou
		// compareTo
		// Collections.binarySearch(this.espaceDeDef, nomOrigine + " " +
		// nomDestination);
		
		// this.espaceDeDef.
		
		// a.
		
		// Collections.
		
		for (OD iterOD : this.espaceDeDef) {
			if (iterOD.getNom().compareTo(nomOrigine + " - " + nomDestination) == 0 | iterOD.getNom().compareTo(nomDestination + " - " + nomOrigine) == 0) {
				return iterOD;
			}
		}
		return null;
	}
	
	public OD getOD(Noeud origine, Noeud destination) {
		
		int hashcodeOD = origine.hashCode() + destination.hashCode();
		
		return this.espaceDeDef.get(hashcodeOD);
	}
	
	public OD getOD(OD od) {
		return this.espaceDeDef.get(od);
	}
	
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("MyObject can't clone");
		}
		return o;
	}
	
	public void addEspaceListener(EspaceListener listener) {
		this.listenersEspace.add(EspaceListener.class, listener);
	}
	
	public void removeEspaceListener(EspaceListener listener) {
		this.listenersEspace.remove(EspaceListener.class, listener);
	}
	
	public EspaceListener[] getEspaceListeners() {
		return this.listenersEspace.getListeners(EspaceListener.class);
	}
	
	protected void fireGrapheChange() {
		for (EspaceListener listener : getEspaceListeners()) {
			listener.espaceChange();
		}
	}
}
