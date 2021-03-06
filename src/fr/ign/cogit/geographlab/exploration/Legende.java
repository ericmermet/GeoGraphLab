/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.exploration;

import fr.ign.cogit.geographlab.algo.legende.Classifieur1D;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesLegende;
import fr.ign.cogit.geographlab.lang.Messages;
import fr.ign.cogit.geographlab.utils.Utils;
import fr.ign.cogit.geographlab.visu.ZoneAgregee;

import java.awt.Color;

import org.math.array.DoubleArray;

public class Legende implements Cloneable {
	
	MainWindow fenetrePrincipale;
	Carte carte;
	
	public boolean locked = false;
	
	private double defaultmin = 0;
	private double defaultmax = 100;
	private int typeDeLegendeParDefaut = ConstantesLegende.legendeIntervallesEgaux;
	private int natureDeLegendeParDefaut = ConstantesLegende.natureLegendeSequentielle;
	private int intervallesDefaut = 6;
	private Color[] defaultColor = { new Color(13, 13, 13), new Color(168, 35, 56), new Color(216, 111, 81), new Color(255, 171, 98), new Color(255, 225, 155), new Color(255, 255, 154) };
	private double[] echelleParDefaut = { this.defaultmin, 17, 33, 50, 66, 83, this.defaultmax };
	
	public int typeDeLegende = this.typeDeLegendeParDefaut;
	public int natureDeLegende = this.natureDeLegendeParDefaut;
	public int intervalles = this.intervallesDefaut;
	public double min = this.defaultmin;
	public double max = this.defaultmax;
	public Color[] couleurs = this.defaultColor;
	public double[] limites = this.echelleParDefaut;
	public double[] data;
	
	//	private Color[] defaultColor = {new Color(255, 255, 154), new Color(255, 225, 155), new Color(255, 171, 98), new Color(216, 111, 81), new Color(168, 35, 56), new Color(13, 13, 13) };
	
	public Legende(MainWindow fenetrePrincipale, Carte carte) {
		
		this.fenetrePrincipale = fenetrePrincipale;
		this.carte = carte;
		this.intervalles = this.intervallesDefaut;
		this.couleurs = this.defaultColor;
		this.limites = this.echelleParDefaut;
		
	}
	
	public Legende(MainWindow fenetrePrincipale, Carte carte, int typeDeLegende, int intervalles, Color[] couleurs, double min, double max) {
		this(fenetrePrincipale, carte);
		this.typeDeLegende = typeDeLegende;
		this.intervalles = intervalles;
		this.min = min;
		this.max = max;
		this.couleurs = couleurs;
	}
	
	public Legende(MainWindow fenetrePrincipale, Carte carte, int typeDeLegende, int intervalles, Color[] couleurs, double min, double max, double[] valeurs) {
		// Constructeur pour effectif egaux
		this(fenetrePrincipale, carte, typeDeLegende, intervalles, couleurs, min, max);
		this.data = valeurs;
		new Double(this.data.length / intervalles).intValue();
	}
	
	public void setColors(Color[] colors) {
		this.couleurs = new Color[colors.length];
		this.couleurs = colors;
		setLegendesNoeudsArcsZonePourValeurs();
	}
	
	public void setColors(int nbCaissons) {
		this.couleurs = new Color[nbCaissons];
		for (int i = 0; i < this.couleurs.length; i++) {
			this.couleurs[i] = this.defaultColor[i];
		}
	}
	
	public double[] getEchelle() {
		return this.limites;
	}
	
	public void setEchelle(double[] echelle) {
		this.limites = echelle;
	}
	
	public void setEchelle(double min, double max, int intervalles) {
		this.min = min;
		this.max = max;
		this.intervalles = intervalles;
//		if( max-min < intervalles)
//			this.intervalles = (int) (Math.round(max) - Math.round(min));
		
		double[] data;
		data =  this.carte.getGraphe().getValeursNoeudsArcs(this.carte.getNomIndicateurCourant());
			
			switch (this.typeDeLegende) {
				case ConstantesLegende.legendeEffectifsEgaux:
					this.limites = Classifieur1D.classificationEffectifsEgaux(data, this.intervalles);
					break;
				case ConstantesLegende.legendeIntervallesEgaux:
					this.limites = Classifieur1D.classificationIntervallesEgaux(this.min, this.max, this.intervalles);
					break;
				default:
					this.limites = Classifieur1D.classificationIntervallesEgaux(this.min, this.max, this.intervalles);
					break;
			
		}
		
		this.limites = Utils.inverseTableau(this.limites);
		
	}
	
	public void setTypeLegend(String typeLegend) {
		if (typeLegend.compareTo(Messages.getString("PanelLegend.cEqualEffective")) == 0) {
			this.typeDeLegende = ConstantesLegende.legendeEffectifsEgaux;
		} else {
			if (typeLegend.compareTo(Messages.getString("PanelLegend.cEqualInterval")) == 0) {
				this.typeDeLegende = ConstantesLegende.legendeIntervallesEgaux;
			}
		}
	}
	
	public int getTypeLegend() {
		return this.typeDeLegende;
	}
	
	public void setNatureLegend(int natureLegend) {
		this.natureDeLegende = natureLegend;
	}
	
	public int getNatureLegend() {
		return this.natureDeLegende;
	}
	
	//	public void setLegendePourValeurs(double[] valeur) {
	//		
	//		this.min = Double.MAX_VALUE;
	//		this.max = Double.MIN_VALUE;
	//		this.limites = new double[this.intervalles + 1];
	//		
	//		switch (getTypeLegend()) {
	//			case ConstantesLegende.legendeIntervallesEgaux:
	//				// Determination des min et max
	//				setMinMax(valeur);
	//				
	//				// Determination des intervalles
	//				for (int k = 0; k < this.intervalles; k++) {
	//					this.limites[k] = (this.max - this.min) * k * this.scaleFactor + this.min;
	//				}
	//				break;
	//			case ConstantesLegende.legendeEffectifsEgaux:
	//				int nbEffectifParClasse = (int) Math.ceil(valeur.length / getIntervallesDefaut());
	//				int resteDivisionEffectifParClasse = valeur.length % getIntervallesDefaut();
	//				
	//				for (int k = 0; k < this.intervalles - 1; k++) {
	//					this.limites[k] = valeur[k * nbEffectifParClasse];
	//				}
	//				this.limites[getIntervallesDefaut()] = this.max;
	//				break;
	//			default:
	//				break;
	//		}
	//		
	//	}
	
	public void setIntervallesDefaut(int nbIntervalles) {
		this.intervalles = nbIntervalles;
		
		setEchelle(this.min, this.max, nbIntervalles);
	}
	
	public int getIntervallesDefaut() {
		return this.intervallesDefaut;
	}
	
	public int getIntervalles() {
		return this.intervalles;
	}
	
	//	public Color getCouleurPourValeur(double valeur) {
	//		
	//		Color returnColor = Color.BLACK;
	//		
	//		for( int i = 0; i<this.limites.length; i++ ) {
	//			if( valeur < this.limites[i] )
	//				return this.couleurs[i-1];
	//		}
	//		
	//		return returnColor;
	//		
	//	}
	
	//	public Color getColorLegendeIntervallesEgaux(double valeur) {
	//		Color returnColor = Color.BLACK;
	//		
	//		for (int i = 0; i < this.limites.length - 1; i++) {
	//			if (valeur >= this.limites[i] && valeur <= this.limites[i + 1]) {
	//				returnColor = this.couleurs[this.couleurs.length - 1 - i];
	//				break;
	//			}
	//		}
	//		
	//		return returnColor;
	//	}
	
	//	public Color getColorLegendeEffectifsEgaux(double valeur) {
	//		Color returnColor = Color.BLACK;
	//		
	//		// FAIRE LA RECUPERATION DES VALEURS DANS UNE AUTRE METHODE ET FAIRE UN
	//		// TEST SI LES NOUVELLES VALEURS SONT DEJA OBTENUES
	//		
	//		// On recupere les valeurs des noeuds et arcs
	//		this.nbDeValeurs = this.fenetrePrincipale.panelActif.carte.getGraphe().getNoeuds().size() + this.fenetrePrincipale.panelActif.carte.getGraphe().getArcs().size();
	//		this.data = new double[this.nbDeValeurs];
	//		
	//		int j = 0;
	//		for (Noeud iterNoeud : this.fenetrePrincipale.panelActif.carte.getGraphe().getNoeuds()) {
	//			// this.valeurs[j++] = iterNoeud.getValeurVue().doubleValue();
	//			this.data[j++] = iterNoeud.getValeurPourIndicateur(this.fenetrePrincipale.panelActif.carte.getNomIndicateurCourant()).doubleValue();
	//		}
	//		for (Arc iterArc : this.fenetrePrincipale.panelActif.carte.getGraphe().getArcs()) {
	//			this.data[j++] = iterArc.getValeurPourIndicateur(this.fenetrePrincipale.panelActif.carte.getNomIndicateurCourant()).doubleValue();
	//		}
	//		
	//		// Tri du tableau
	//		Arrays.sort(this.data);
	//		
	//		this.limites = new double[this.intervalles + 1];
	//		int nbValeursParClasse = new Double(Math.rint(this.nbDeValeurs / (this.limites.length - 1))).intValue();
	//		
	//		// Trouver les frontieres
	//		for (int i = 0; i < this.limites.length - 1; i++) {
	//			this.limites[i] = this.data[nbValeursParClasse * i];
	//		}
	//		this.limites[this.limites.length - 1] = this.data[this.data.length - 1];
	//		
	//		for (int i = 0; i < this.limites.length - 1; i++) {
	//			if (valeur >= this.limites[i] && valeur <= this.limites[i + 1]) {
	//				returnColor = this.couleurs[this.couleurs.length - 1 - i];
	//				break;
	//			}
	//		}
	//		
	//		return returnColor;
	//	}
	
	public Color[] getDefaultColors() {
		return this.defaultColor;
	}
	
	public Color[] getColors() {
		return this.couleurs;
	}
	
	public double[] getDefaultScale() {
		return this.echelleParDefaut;
	}
	
	public void setMin(double min) {
		this.min = min;
	}
	
	public double getMin() {
		return this.min;
	}
	
	public double getMax() {
		return this.max;
	}
	
	public void setMax(double max) {
		this.max = max;
	}
	
	public void setMinMax(double valeurs[]) {
		this.min = DoubleArray.min(valeurs);
		this.max = DoubleArray.max(valeurs);
	}
	
	public void setLegendesNoeudsArcsZonePourValeurs() {
		
		System.out.println("setLegendesNoeudsArcsZonePourValeurs");
		
		double[] data =  this.carte.getGraphe().getValeursNoeudsArcs(this.carte.getNomIndicateurCourant());
		double[] limites = null;
		
		if( data.length > 0 ) {
			switch (this.typeDeLegende) {
				default:
				case ConstantesLegende.legendeEffectifsEgaux:
					limites = Classifieur1D.classificationEffectifsEgaux(data, this.intervalles);
					break;
				case ConstantesLegende.legendeIntervallesEgaux:
					limites = Classifieur1D.classificationIntervallesEgaux(this.min, this.max, this.intervalles);
					break;
			}
			
			// Attribution des couleurs des arcs en fonction des valeurs
			for (Arc iterArc : this.carte.getGraphe().getArcs()) {
				iterArc.getArcGraphique().setIndicateurCouleur(this.carte.getNomIndicateurCourant(), 
						getColorValeur(iterArc.getValeurPourIndicateur(this.carte.getNomIndicateurCourant()).doubleValue(), limites));
			}
			
			// Attribution des couleurs des noeuds en fonction des valeurs
			for (Noeud iterNoeud : this.carte.getGraphe().getNoeuds()) {
				iterNoeud.getNoeudGraphique().setIndicateurCouleur(this.carte.getNomIndicateurCourant(), 
						getColorValeur(iterNoeud.getValeurPourIndicateur(this.carte.getNomIndicateurCourant()).doubleValue(), limites));
			}
			
			// Attribution des couleurs des zones en fonction des valeurs
			
			// Un repaint pour mettre a jour la carte
			this.fenetrePrincipale.panelActif.repaint();
		}
	}
	
	public Color getColorValeur(double valeur, double[] limites) {
		
		//D'abord les bornes min et max
		if( valeur >= this.min & valeur < limites[1] )
			return this.couleurs[this.couleurs.length-1];		//Derni??re couleur du tableau de couleur
		if( valeur >= limites[limites.length-1] )
			return this.couleurs[0];	
		
		// Sinon parcours du tableau
		for (int i = 0; i < limites.length-1; i++) {
			if( valeur >= limites [i] & valeur < limites[i+1] )
				return this.couleurs[this.couleurs.length-2-i];
		}
		return Color.LIGHT_GRAY;
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
	
}