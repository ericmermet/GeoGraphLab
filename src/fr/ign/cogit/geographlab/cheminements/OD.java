/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.cheminements;

import org.jgrapht.util.VertexPair;

import fr.ign.cogit.geographlab.factories.GestionnaireODs;
import fr.ign.cogit.geographlab.graphe.Noeud;

public class OD extends VertexPair<Noeud> implements Comparable<OD> {
	
	private String nom;
	private Noeud origine;
	private Noeud destination;
	private int id;
	private double ponderation = 1;
	private double ponderationOrigineDestination = 1;
	private double ponderationDestinationOrigine = 1;
	private int typeDeCheminement;
	private boolean pccCalcule;
	private boolean dirige;		//true = dirige, 2 sens distincts OD != DO; false = non-dirige, OD = DO
	
	public OD(Noeud origine, Noeud destination) {
		
		super(origine, destination);
		setOrigine(origine);
		setDestination(destination);
		setNom(new String(origine.getNom() + " - " + destination.getNom()));
		
		if( origine.getPonderation() > 1 & destination.getPonderation() > 1 ) {
			
			setPonderationOrigineDestination(Math.abs((origine.getPonderation() * destination.getPonderation()) / (1-origine.getPonderation()) ));
			setPonderationDestinationOrigine(Math.abs((destination.getPonderation() * origine.getPonderation()) / (1-destination.getPonderation())) );
			
			setPonderation(getPonderationOrigineDestination() + getPonderationDestinationOrigine());
		}else{
			setPonderationOrigineDestination(1.0);
			setPonderationDestinationOrigine(1.0);
			setPonderation(1.0);
		}
		
		setTypeDeCheminement(Constantes.typePCC);
		setPccCalcule(false);
		this.id = GestionnaireODs.getIDUnique();
		//Par defaut OD est non dirige
		setDirige(false);
	}
	
	public OD(Noeud origine, Noeud destination, boolean dirige) {
		this(origine, destination);
		setDirige(dirige);
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setPonderationOrigineDestination(double ponderation) {
		this.ponderationOrigineDestination = ponderation;
	}
	
	public double getPonderationOrigineDestination() {
		return this.ponderationOrigineDestination;
	}
	
	public void setPonderationDestinationOrigine(double ponderation) {
		this.ponderationDestinationOrigine = ponderation;
	}
	
	public double getPonderationDestinationOrigine() {
		return this.ponderationDestinationOrigine;
	}
	
	public void setPonderation(double ponderation) {
		this.ponderation = ponderation;
	}
	
	public double getPonderation() {
		return this.ponderation;
	}
	
	public void setTypeDeCheminement(int typeDeCheminement) {
		this.typeDeCheminement = typeDeCheminement;
	}
	
	public int getTypeDeCheminement() {
		return this.typeDeCheminement;
	}
	
	
	public void setOrigine(Noeud origine) {
		this.origine = origine;
	}
	
	public Noeud getOrigine() {
		return this.origine;
	}
	
	public void setDestination(Noeud destination) {
		this.destination = destination;
	}
	
	public Noeud getDestination() {
		return this.destination;
	}
	
	public void setPccCalcule(boolean pccCalcule) {
		this.pccCalcule = pccCalcule;
	}
	
	public boolean isPccCalcule() {
		return this.pccCalcule;
	}
	
	/**
	 * @return the directed parameters
	 */
	public boolean isDirige() {
		return dirige;
	}
	
	/**
	 * @param dirige the dirige to set
	 */
	public void setDirige(boolean dirige) {
		this.dirige = dirige;
	}
	
	/**
	 * @return the reverse OD
	 */
	public OD getReverse() {
		OD reverseOD = new OD(this.destination, this.origine);
		reverseOD.setPonderation(this.getPonderation());
		reverseOD.id = -this.id;
		return reverseOD;
	}
	
	@Override
	public boolean equals(Object arg0) {
		// OD odAComparer = (OD) arg0;
		
		if (arg0.hashCode() == this.hashCode())
			return true;
		
		int a = arg0.hashCode() / 100000;
		int b = arg0.hashCode() - a * 100000;
		
		int hashCodeInverse = b * 100000 + a;
		
		if (hashCodeInverse == this.hashCode())
			return true;
		
		return false;
	}
	
	@Override
	public int compareTo(OD od) {
		if (od.hashCode() == this.hashCode())
			return 0;
		return -1;
	}
	
	@Override
	public int hashCode() {
		//		return this.origine.hashCode() + this.destination.hashCode();
		
		return this.id;
	}
	
	public int hasCodeSpecial() {
		return this.id;
	}
	
}