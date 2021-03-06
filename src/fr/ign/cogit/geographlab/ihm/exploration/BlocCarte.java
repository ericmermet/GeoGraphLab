/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.exploration;


import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesImages;
import fr.ign.cogit.geographlab.ihm.events.MouseEventsExplo;

public class BlocCarte extends BlocGraphique{
	
	private Carte carte;
	
	private Image imageCarte;
	
	private boolean isParentMap;
	
	//	private Point pXY;

	private Color couleur;
	
	private BlocGraphique connecteurEntreeEspace, connecteurEntreeMesure, connecteurEntreeVue, connecteurEntreeLegende;
	
	public Rectangle rectEspaceEntree = new Rectangle(), rectMesureEntree = new Rectangle(), rectVueEntree = new Rectangle(), rectLegendeEntree = new Rectangle();
	public Rectangle rectEspaceSortie = new Rectangle(), rectMesureSortie = new Rectangle(), rectVueSortie = new Rectangle(), rectLegendeSortie = new Rectangle();
	public Rectangle[] tabRectanglesEntreeSortie = new Rectangle[8];
	
	int widthCarteParente, heightCarteParente;
	Double rapportHauteur, rapportLargeur;
	
	public Color[] cstColorConnecteur = {ConstantesExploration.couleurEspace, ConstantesExploration.couleurMesure, ConstantesExploration.couleurVue, ConstantesExploration.couleurLegende };
	public int[] tabIdConnecteurs = {	ConstantesExploration.idEspaceEntree, ConstantesExploration.idMesureEntree, ConstantesExploration.idVueEntree, ConstantesExploration.idLegendeEntree,
			ConstantesExploration.idEspaceSortie, ConstantesExploration.idMesureSortie, ConstantesExploration.idVueSortie, ConstantesExploration.idLegendeSortie };
	
	public List<ConnecteurBlocs> connecteursFilaires = new ArrayList<ConnecteurBlocs>();
	
	Image imgCarteParente = new ImageIcon(getClass().getResource(ConstantesImages.imgCarteParente)).getImage();
	Image imgCarte = new ImageIcon(getClass().getResource(ConstantesImages.imgCarte)).getImage();
	
	public BlocCarte(Carte carte, boolean isParent) {
		
		super ();
		
		this.carte = carte;
		this.isParentMap = isParent;
		this.typeBloc = ConstantesExploration.blocCarte;
		
		if (this.isParentMap) {
			this.setImageCarte(this.imgCarteParente);
		} else {
			this.setImageCarte(this.imgCarte);
		}
		
		this.couleur = this.carte.getColorLayer();
		
		init();
		
	}
	
	@SuppressWarnings("boxing")
	public void init() {
		
		if (this.isParentMap) {
			setpXY(new Point(20, 150 + 90 *this.carte.niveau));
			
			this.widthCarteParente = this.imgCarteParente.getWidth(this.carte.parent.panelExplo.getJPanel());
			this.heightCarteParente = this.imgCarteParente.getHeight(this.carte.parent.panelExplo.getJPanel());
			
			this.rapportHauteur = new Double(ConstantesExploration.widthBloc) / new Double(this.widthCarteParente);
			this.rapportLargeur = new Double(ConstantesExploration.heightBloc) / new Double(this.heightCarteParente);
			
			setRectanglesGenericLayer(getpXY());
			
		} else {
			
			//Astuce de programmation sur l'inversion width/height ?
			this.widthCarteParente = this.imgCarte.getHeight(this.carte.parent.panelExplo.getJPanel());
			this.heightCarteParente = this.imgCarte.getWidth(this.carte.parent.panelExplo.getJPanel());
			
			this.rapportHauteur = new Double(ConstantesExploration.widthBloc) / new Double(this.heightCarteParente);
			this.rapportLargeur = new Double(ConstantesExploration.heightBloc) / new Double(this.widthCarteParente);
			
			if( this.carte.carteMere != null) {
				setpXY( new Point(this.carte.carteMere.carteExplo.getpXY().x+200, this.carte.carteMere.carteExplo.getpXY().y) );
				setRectanglesGenericLayer(getpXY());
			}
		}
		
	}
	
	@Override
	@SuppressWarnings("boxing")
	public void setRectanglesGenericLayer(Point p) {
		
		if (this.isParentMap) {
			setRectangles(
					new Rectangle(),
					new Rectangle(),
					new Rectangle(),
					new Rectangle(),
					new Rectangle(new Double(675 * this.rapportHauteur).intValue() + getpXY().x, new Double(51 * this.rapportLargeur).intValue() + getpXY().y,
							new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue()),
					new Rectangle(new Double(675 * this.rapportHauteur).intValue() + getpXY().x, new Double(159 * this.rapportLargeur).intValue() + getpXY().y, 
						new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue()),
					new Rectangle(new Double(675 * this.rapportHauteur).intValue() + getpXY().x, new Double(267 * this.rapportLargeur).intValue() + getpXY().y,
							new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue()),
					new Rectangle(new Double(675 * this.rapportHauteur).intValue() + getpXY().x, new Double(375 * this.rapportLargeur).intValue() + getpXY().y,
							new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue())
			);
			
		}else{
			setRectangles(
					new Rectangle(this.getpXY().x, new Double(47 * this.rapportLargeur).intValue() + this.getpXY().y, new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue()),
					new Rectangle(this.getpXY().x, new Double(155 * this.rapportLargeur).intValue() + this.getpXY().y, new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue()),
					new Rectangle(this.getpXY().x, new Double(264 * this.rapportLargeur).intValue() + this.getpXY().y, new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue()),
					new Rectangle(this.getpXY().x, new Double(371 * this.rapportLargeur).intValue() + this.getpXY().y, new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue()),
					
					new Rectangle(new Double(754 * this.rapportHauteur).intValue() + this.getpXY().x, new Double(47 * this.rapportLargeur).intValue() + this.getpXY().y,
							new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue()),
					new Rectangle(new Double(754 * this.rapportHauteur).intValue() + this.getpXY().x, new Double(155 * this.rapportLargeur).intValue() + this.getpXY().y,
							new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue()),
					new Rectangle(new Double(754 * this.rapportHauteur).intValue() + this.getpXY().x, new Double(264 * this.rapportLargeur).intValue() + this.getpXY().y,
							new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue()),
					new Rectangle(new Double(754 * this.rapportHauteur).intValue() + this.getpXY().x, new Double(371 * this.rapportLargeur).intValue() + this.getpXY().y,
							new Double(81 * this.rapportHauteur + 1).intValue(), new Double(15 * this.rapportLargeur + 1).intValue())
			);
		}
		
		//Mise a jour des connecteurs potentiels
		for (ConnecteurBlocs iterConnecteurs : this.connecteursFilaires) {
			iterConnecteurs.setLines();
		}
		
	}
	
	private void setRectangles(Rectangle rEE, Rectangle rME, Rectangle rVE, Rectangle rLE, Rectangle rES, Rectangle rMS, Rectangle rVS, Rectangle rLS){
		
		this.rectEspaceEntree.setLocation(rEE.getLocation());
		this.rectEspaceEntree.setSize(rEE.getSize());
		
		this.rectMesureEntree.setLocation(rME.getLocation());
		this.rectMesureEntree.setSize(rME.getSize());
		
		this.rectVueEntree.setLocation(rVE.getLocation());
		this.rectVueEntree.setSize(rVE.getSize());
		
		this.rectLegendeEntree.setLocation(rLE.getLocation());
		this.rectLegendeEntree.setSize(rLE.getSize());
		
		this.rectEspaceSortie.setLocation(rES.getLocation());
		this.rectEspaceSortie.setSize(rES.getSize());
		
		this.rectMesureSortie.setLocation(rMS.getLocation());
		this.rectMesureSortie.setSize(rMS.getSize());
		
		this.rectVueSortie.setLocation(rVS.getLocation());
		this.rectVueSortie.setSize(rVS.getSize());
		
		this.rectLegendeSortie.setLocation(rLS.getLocation());
		this.rectLegendeSortie.setSize(rLS.getSize());
		
		this.tabRectanglesEntreeSortie[0] = this.rectEspaceEntree;	
		this.tabRectanglesEntreeSortie[1] = this.rectMesureEntree;
		this.tabRectanglesEntreeSortie[2] = this.rectVueEntree;
		this.tabRectanglesEntreeSortie[3] = this.rectLegendeEntree;
		this.tabRectanglesEntreeSortie[4] = this.rectEspaceSortie;
		this.tabRectanglesEntreeSortie[5] = this.rectMesureSortie;
		this.tabRectanglesEntreeSortie[6] = this.rectVueSortie;
		this.tabRectanglesEntreeSortie[7] = this.rectLegendeSortie;
		
	}
	
	/**
	 * @return the isParentMap
	 */
	public boolean isParentMap() {
		return this.isParentMap;
	}

	/**
	 * @param isParentMap the isParentMap to set
	 */
	public void setParentMap(boolean isParentMap) {
		this.isParentMap = isParentMap;
	}
	
	@Override
	public Rectangle[] getRectangles(){
		return this.tabRectanglesEntreeSortie;
	}
	
	public Carte getCarte(){
		return this.carte;
	}
	
	public void setImageCarte(Image imageCarte) {
		this.imageCarte = imageCarte;
	}
	
	public Image getImageCarte() {
		return this.imageCarte;
	}
	
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	
	public Color getCouleur() {
		return this.couleur;
	}
	
	public boolean premiereConnexion(Rectangle2D.Double selection, BlocGraphique b) {
		
		// Pour tous les rectangles du bloc carte
		for (int i = 0; i < this.tabRectanglesEntreeSortie.length; i++) {
			// Si la selection intersecte un des rectangles
			if( this.tabRectanglesEntreeSortie[i].intersects(selection)) {
				// Alors on effectue un debut de connexion pour affichage du lien
				MouseEventsExplo.setFlagClick(true);
				MouseEventsExplo.setXYTempConnect(this.tabRectanglesEntreeSortie[i].x  + this.tabRectanglesEntreeSortie[i].width, this.tabRectanglesEntreeSortie[i].y + this.tabRectanglesEntreeSortie[i].height/2);
				MouseEventsExplo.setIdConnecteurOppose(this.tabIdConnecteurs[i]);
				MouseEventsExplo.setBlocOpposeAConnecter(b);
				return true;
			}
		}	
		return false;
	}
	
	public boolean secondeConnexion(Rectangle2D.Double selection, BlocGraphique blocAConnecter){
		
		//La seconde connection intervient sur un bloc carte
		
		MouseEventsExplo.setFlagClick(false);
		
		// Pour tous les rectangles du bloc carte
		for (int i = 0; i < this.tabRectanglesEntreeSortie.length; i++) {
			// Si la selection du relachement d'un clic intersecte un des rectangles
			if( this.tabRectanglesEntreeSortie[i].intersects(selection) ) {
				// Alors on tente de connecter si la connexion est coherente
				if( blocAConnecter!= this & setConnecteur( blocAConnecter, this.tabIdConnecteurs[i] ) ){
					// Si on n'essaie pas de connecter le bloc a lui meme et si on arrive a realiser une connexion coherente alors on relie graphiquement
					MouseEventsExplo.setXYTempConnect(this.tabRectanglesEntreeSortie[i].x  + this.tabRectanglesEntreeSortie[i].width, this.tabRectanglesEntreeSortie[i].y + this.tabRectanglesEntreeSortie[i].height/2);
					int typeConnecteur = 1000 + i;
					MouseEventsExplo.link(this, blocAConnecter, this.cstColorConnecteur[i%4], typeConnecteur);
				}
				return true;
			}
		}
		
		return false;
	}
	
	public boolean setConnecteur(BlocGraphique connecteurEntree, int idConnecteur){
		// Le bloc connecteur Entree est celui a l'oppose de l'objet que l'on considere (this)
		// Ici on relache toujours sur un bloc carte
		switch (connecteurEntree.typeBloc) {
			case ConstantesExploration.blocOperation:
				// Si on relache un clic sur un bloc operation
				//Mesure
				if( idConnecteur == ConstantesExploration.idMesureSortie & MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationGauche ) {
					((BlocOperationMesure)connecteurEntree).setConnecteurEntreeGauche(this);
					return true;
				}
				if( idConnecteur == ConstantesExploration.idMesureSortie & MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationHaut ) {
					((BlocOperationMesure)connecteurEntree).setConnecteurEntreeHaut(this);
					return true;
				}
				if( idConnecteur == ConstantesExploration.idMesureSortie & MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationBas ) {
					((BlocOperationMesure)connecteurEntree).setConnecteurEntreeBas(this);
					return true;
				}
				if( idConnecteur == ConstantesExploration.idMesureEntree & MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationEgal ) {
					((BlocOperationMesure)connecteurEntree).setConnecteurSortie(this);
					return true;
				}
				//Espace
				if( idConnecteur == ConstantesExploration.idEspaceSortie& MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationGauche ) {
					((BlocOperationEspace)connecteurEntree).setConnecteurEntreeGauche(this);
					return true;
				}
				if( idConnecteur == ConstantesExploration.idEspaceSortie & MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationHaut ) {
					((BlocOperationEspace)connecteurEntree).setConnecteurEntreeHaut(this);
					return true;
				}
				if( idConnecteur == ConstantesExploration.idEspaceEntree & MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationEgal ) {
					((BlocOperationEspace)connecteurEntree).setConnecteurSortie(this);
					return true;
				}
				//Vue
				if( idConnecteur == ConstantesExploration.idVueSortie& MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationGauche ) {
					((BlocOperationVue)connecteurEntree).setConnecteurEntreeGauche(this);
					return true;
				}
				if( idConnecteur == ConstantesExploration.idVueSortie & MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationHaut ) {
					((BlocOperationVue)connecteurEntree).setConnecteurEntreeHaut(this);
					return true;
				}
				if( idConnecteur == ConstantesExploration.idVueEntree & MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationEgal ) {
					((BlocOperationVue)connecteurEntree).setConnecteurSortie(this);
					return true;
				}
				//Legende
				if( idConnecteur == ConstantesExploration.idLegendeSortie& MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationGauche ) {
					((BlocOperationLegende)connecteurEntree).setConnecteurEntreeGauche(this);
					return true;
				}
				if( idConnecteur == ConstantesExploration.idLegendeSortie & MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationHaut ) {
					((BlocOperationLegende)connecteurEntree).setConnecteurEntreeHaut(this);
					return true;
				}
				if( idConnecteur == ConstantesExploration.idLegendeEntree & MouseEventsExplo.getIdConnecteurOppose() == ConstantesExploration.idOperationEgal ) {
					((BlocOperationLegende)connecteurEntree).setConnecteurSortie(this);
					return true;
				}
				break;
			case ConstantesExploration.blocCarte:
				// Si on relache un clic sur un bloc carte
				if( Math.abs(idConnecteur) == ConstantesExploration.idEspaceEntree & idConnecteur == -MouseEventsExplo.getIdConnecteurOppose() ){
					//test sur la valeur absolue selon le sens de connexion (entree vers sortie ou sortie vers entree et si on est bien sur le mm parametre (valeur oppose)
					setConnecteurEntreeEspace(connecteurEntree);
					return true;
				}
				if( Math.abs(idConnecteur) == ConstantesExploration.idMesureEntree & idConnecteur == -MouseEventsExplo.getIdConnecteurOppose() ){
					setConnecteurEntreeMesure(connecteurEntree);
					return true;
				}
				if( Math.abs(idConnecteur) == ConstantesExploration.idVueEntree & idConnecteur == -MouseEventsExplo.getIdConnecteurOppose() ){
					setConnecteurEntreeVue(connecteurEntree);
					return true;
				}
				if( Math.abs(idConnecteur) == ConstantesExploration.idLegendeEntree & idConnecteur == -MouseEventsExplo.getIdConnecteurOppose() ){
					setConnecteurEntreeLegende(connecteurEntree);
					return true;
				}
				break;
			default:
				break;
		}
		return false;
	}
	
	public void setConnecteurEntreeEspace(BlocGraphique connecteurEntreeEspace) {
		this.connecteurEntreeEspace = connecteurEntreeEspace;
		this.carte.setEspace( ((BlocCarte)connecteurEntreeEspace).carte.getEspace());
	}
	
	public BlocGraphique getConnecteurEntreeEspace() {
		return this.connecteurEntreeEspace;
	}
	
	public void setConnecteurEntreeMesure(BlocGraphique connecteurEntreeMesure) {
		this.connecteurEntreeMesure = connecteurEntreeMesure;
		this.carte.setNomIndicateurCourant(((BlocCarte)connecteurEntreeMesure).carte.getNomIndicateurCourant());
	}
	
	public BlocGraphique getConnecteurEntreeMesure() {
		return this.connecteurEntreeMesure;
	}
	
	public void setConnecteurEntreeVue(BlocGraphique connecteurEntreeVue) {
		this.connecteurEntreeVue = connecteurEntreeVue;
		this.carte.setVueDuGraphe(((BlocCarte)connecteurEntreeVue).carte.getVueDuGraphe());
	}
	
	public BlocGraphique getConnecteurEntreeVue() {
		return this.connecteurEntreeVue;
	}
	
	public void setConnecteurEntreeLegende(BlocGraphique connecteurEntreeLegende) {
		this.connecteurEntreeLegende = connecteurEntreeLegende;
		this.carte.setLegendeDeLaCarte(((BlocCarte)connecteurEntreeLegende).carte.getLegendeDeLaCarte());
		this.carte.parent.parent.panelLegend.updateLegendFromModel();
		this.carte.repaint();
	}
	
	public BlocGraphique getConnecteurEntreeLegende() {
		return this.connecteurEntreeLegende;
	}

	@Override
	public void addConnecteurFilaires(ConnecteurBlocs connecteur) {
		this.connecteursFilaires.add(connecteur);
	}
	
	
	
}