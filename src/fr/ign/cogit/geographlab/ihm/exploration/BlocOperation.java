/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2010
 *
 * OperationBloc.java in ihm.exploration
 * 
 */
package fr.ign.cogit.geographlab.ihm.exploration;

/**
 * @author Eric
 *
 */

import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;
import fr.ign.cogit.geographlab.ihm.events.MouseEventsExplo;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class BlocOperation extends BlocGraphique {
	
	private Image imageCarte;
	private PanelExplo panelPere;
	
	public Color couleur;
	
	public int levierMisEnJeu;
	
	public int nbConnections = 0;
	
	public Rectangle rectEntreeHaut = new Rectangle(), rectEntreeGauche = new Rectangle(), rectSortie = new Rectangle(), rectEntreeBas = new Rectangle();
	public Rectangle[] tabRectanglesEntreeSortie = new Rectangle[4];
	public int[] tabIdConnecteurs = { ConstantesExploration.idOperationGauche, ConstantesExploration.idOperationHaut, ConstantesExploration.idOperationBas, ConstantesExploration.idOperationEgal };
	
	public BlocGraphique connecteurEntreeHaut, connecteurEntreeGauche, connecteurEntreeBas, connecteurSortie;
	public List<ConnecteurBlocs> connecteursFilaires = new ArrayList<ConnecteurBlocs>();
	
	public BlocOperation(PanelExplo panelPere, int operation) {
		
		super();
		
		this.panelPere = panelPere;
		this.typeBloc = ConstantesExploration.blocOperation;
		this.operation = operation;
		
	}
	
	public boolean premiereConnexion(Rectangle2D.Double selection, BlocGraphique b) {
		
		//Pour tous les rectangles du bloc Operation
		for (int i = 0; i < this.tabRectanglesEntreeSortie.length; i++) {
			if( this.tabRectanglesEntreeSortie[i].intersects(selection)) {
				MouseEventsExplo.setFlagClick(true);
				MouseEventsExplo.setXYTempConnect(this.tabRectanglesEntreeSortie[i].x  + this.tabRectanglesEntreeSortie[i].width, this.tabRectanglesEntreeSortie[i].y + this.tabRectanglesEntreeSortie[i].height/2);
				MouseEventsExplo.setIdConnecteurOppose(this.tabIdConnecteurs[i]);
				MouseEventsExplo.setBlocOpposeAConnecter(b);
			}
		}
		
		return false;
	}
	
	public boolean secondeConnexion(Rectangle2D.Double selection, BlocGraphique blocAConnecter) {
		
		//La seconde connexion intervient sur un bloc operation
		
		MouseEventsExplo.setFlagClick(false);
		
		// Pour tous les rectangles du bloc operation
		for (int i = 0; i < this.tabRectanglesEntreeSortie.length; i++) {
			// Si la selection du relachement d'un clic intersecte un des rectangles
			if( this.tabRectanglesEntreeSortie[i].intersects(selection) ) {
				// Alors on tente de connecter si la connexion est coherente
				if( blocAConnecter!= this & setConnecteurSelonLevier( blocAConnecter, this.tabIdConnecteurs[i] ) ){ // (bloc a connecter en face, id du connecteur sur lequel on fait un mouse release )
					// Si on n'essaie pas de connecter le bloc a lui meme et si on arrive a realiser une connexion coherente alors on relie graphiquement
					MouseEventsExplo.setXYTempConnect(	this.tabRectanglesEntreeSortie[i].x  + this.tabRectanglesEntreeSortie[i].width,
							this.tabRectanglesEntreeSortie[i].y + this.tabRectanglesEntreeSortie[i].height/2);
					int typeConnecteur = 10000 + i;
					MouseEventsExplo.link(this, blocAConnecter, this.couleur, typeConnecteur);
				}
			}
		}
		
		return false;
	}
	
	public boolean setConnecteurSelonLevier(BlocGraphique connecteurEntree, int idConnecteurReleaseOnThisBloc){
		switch (this.levierMisEnJeu) {
			case ConstantesExploration.blocOperationEspace:
				return ((BlocOperationEspace)this).setConnecteur(connecteurEntree, idConnecteurReleaseOnThisBloc);
			case ConstantesExploration.blocOperationMesure:
				return ((BlocOperationMesure)this).setConnecteur(connecteurEntree, idConnecteurReleaseOnThisBloc);
			case ConstantesExploration.blocOperationVue:
				return ((BlocOperationVue)this).setConnecteur(connecteurEntree, idConnecteurReleaseOnThisBloc);
			case ConstantesExploration.blocOperationLegende:
				return ((BlocOperationLegende)this).setConnecteur(connecteurEntree, idConnecteurReleaseOnThisBloc);
		}
		return false;
	}
	
	public void setConnecteurEntreeGauche(BlocGraphique connecteurEntreeGauche) {
		this.connecteurEntreeGauche = connecteurEntreeGauche;
		doWeCompute();
	}
	
	public BlocGraphique getConnecteurEntreeGauche() {
		return this.connecteurEntreeGauche;
	}
	
	public void setConnecteurEntreeHaut(BlocGraphique connecteurEntreeHaut) {
		this.connecteurEntreeHaut = connecteurEntreeHaut;
		doWeCompute();
	}
	
	public BlocGraphique getConnecteurEntreeHaut() {
		return this.connecteurEntreeHaut;
	}
	
	public void setConnecteurEntreeBas(BlocGraphique connecteurEntreeBas) {
		this.connecteurEntreeBas = connecteurEntreeBas;
		doWeCompute();
	}
	
	public BlocGraphique getConnecteurEntreeBas() {
		return this.connecteurEntreeBas;
	}
	
	public void setConnecteurSortie(BlocGraphique connecteurSortie) {
		this.connecteurSortie = connecteurSortie;
		doWeCompute();
	}
	
	public BlocGraphique getConnecteurSortie() {
		return this.connecteurSortie;
	}
	
	public ArrayList<BlocGraphique> getToutesLesConnexionsBlocs() {
		
		ArrayList<BlocGraphique> listBlocs = new ArrayList<BlocGraphique>();
		listBlocs.add(getConnecteurEntreeBas());
		listBlocs.add(getConnecteurEntreeGauche());
		listBlocs.add(getConnecteurEntreeHaut());
		listBlocs.add(getConnecteurSortie());
		
		return listBlocs;
		
	}
	
	private void doWeCompute(){
		//Les operateurs unaires
		if( this.operation == ConstantesExploration.typeOperationComplementEspace |
				this.operation == ConstantesExploration.typeOperationFonctionUnaireMesure | 
				this.operation == ConstantesExploration.typeOperationSommePonderee) {
			if( this.connecteurEntreeGauche != null & this.connecteurSortie != null ) {
				this.thread.run();
			}
		}else{
			//Les operateurs ternaires
			if( this.operation == ConstantesExploration.typeOperationAddition3Mesure | this.operation == ConstantesExploration.typeOperationMutliplication3Mesure ) {
				if( this.connecteurEntreeGauche != null & this.connecteurEntreeHaut != null & this.connecteurSortie != null & this.connecteurEntreeBas != null) {
					this.thread.run();
				}
			}else{
				//Les operateurs binaires
				if( this.connecteurEntreeGauche != null & this.connecteurEntreeHaut != null & this.connecteurSortie != null ) {
					this.thread.run();
				}
			}
		}
	}
	
	@Override
	@SuppressWarnings("boxing")
	public void setRectanglesGenericLayer(Point p) {
		int heightCarteParente = getImageOperation().getWidth(this.panelPere);
		int widthCarteParente = getImageOperation().getHeight(this.panelPere);
		
		Double rapportHauteur = new Double(ConstantesExploration.widthOperationBloc) / new Double(heightCarteParente);
		Double rapportLargeur = new Double(ConstantesExploration.heightOperationBloc) / new Double(widthCarteParente);
		
		this.rectEntreeGauche.setLocation(getpXY().x, new Double(182 * rapportLargeur).intValue() + getpXY().y);
		this.rectEntreeGauche.setSize(new Double(45 * rapportHauteur + 1).intValue(), new Double(14 * rapportLargeur + 1).intValue());
		
		this.rectEntreeHaut.setLocation(new Double(183 * rapportHauteur).intValue() + getpXY().x, getpXY().y);
		this.rectEntreeHaut.setSize(new Double(14 * rapportHauteur + 1).intValue(), new Double(45 * rapportLargeur + 1).intValue());
		
		this.rectEntreeBas.setLocation(new Double(184 * rapportHauteur).intValue() + getpXY().x, new Double(290 * rapportHauteur).intValue() + getpXY().y);
		this.rectEntreeBas.setLocation(new Double(14 * rapportHauteur + 1).intValue(), new Double(45 * rapportLargeur + 1).intValue());
		
		this.rectSortie.setLocation(new Double(335 * rapportHauteur).intValue() + getpXY().x, new Double(182 * rapportLargeur).intValue() + getpXY().y);
		this.rectSortie.setSize(new Double(45 * rapportHauteur + 1).intValue(), new Double(14 * rapportLargeur + 1).intValue());
		
		this.tabRectanglesEntreeSortie[0] = this.rectEntreeGauche;
		this.tabRectanglesEntreeSortie[1] = this.rectEntreeHaut;
		this.tabRectanglesEntreeSortie[2] = this.rectEntreeBas;
		this.tabRectanglesEntreeSortie[3] = this.rectSortie;
		
		//Mise a jour des connecteurs potentiels
		for (ConnecteurBlocs iterConnecteurs : this.connecteursFilaires) {
			iterConnecteurs.setLines();
		}
		
	}
	
	@Override
	public Rectangle[] getRectangles(){
		return this.tabRectanglesEntreeSortie;
	}
	
	public void setImageOperation(Image imageCarte) {
		this.imageCarte = imageCarte;
	}
	
	public Image getImageOperation() {
		return this.imageCarte;
	}
	
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	
	public Color getCouleur() {
		return this.couleur;
	}

	@Override
	public void addConnecteurFilaires(ConnecteurBlocs connecteur) {
		this.connecteursFilaires.add(connecteur);
	}
	
}