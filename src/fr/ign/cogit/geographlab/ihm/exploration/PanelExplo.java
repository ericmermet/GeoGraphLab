/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.exploration;


import java.awt.Color;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesImages;
import fr.ign.cogit.geographlab.ihm.events.MouseEventsExplo;

public class PanelExplo extends JPanel implements KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainWindow parent;
	
	public ToolBarExplo toolBarExplo;
	
	private Graphics2D gC2D;
	
	private Color defaultColor = Color.WHITE;
	
	public GestionnaireOperations operations;
	
	public int outilsActifs = 0;
	
	private AffineTransform at = new AffineTransform();
	
	public int posMouseX, posMouseY;
	public boolean drawBloc = false;
	
	public int nbCartesParNiveau[] = new int[15];
	
	public static Line2D connecteurEntreDeuxBlocs[] = new Line2D[3];
	
	public static ArrayList<ConnecteurBlocs> connecteurs = new ArrayList<ConnecteurBlocs>();
	
	private ImageIcon imgCarte;
	private ImageIcon imgAddition, imgAddition3, imgSoustration, imgMultiplication, imgMultiplication3, imgDivision, imgFonctionUnaire, imgFonctionBinaire;
	private ImageIcon imgUnionEspace, imgIntersectionEspace, imgSoustractionespace, imgExclusionEspace, imgComplementEspace;
	private ImageIcon imgUnionVue, imgIntersectionVue, imgExclusionVue, imgUnionAvecConservationVue;
	private ImageIcon imgCrsmtCouleurCouleur, imgCrsmtCouleurTaille;

	public PanelExplo(MainWindow parent, String nom) {
		
		this.parent = parent;
		this.setName(new String("Exploration par langage graphique"));
		
		this.setOpaque(true);
		this.setFocusable(true);
		
		this.setBackground(this.defaultColor);
		this.setForeground(this.defaultColor);
		
		// Ajout de la toolBar dediee
		this.toolBarExplo = new ToolBarExplo(this);
		
		//Mouse Listeners
		this.addMouseListener(new MouseEventsExplo(this));
		this.addMouseMotionListener(new MouseEventsExplo(this));
		this.addMouseWheelListener(new MouseEventsExplo(this));
		
		//KeyBoard Listener

//		this.addKeyListener(new KeyEventsExplo(this));
		
//		FocusListener listener = new FocusListener() {
//			public void focusGained(FocusEvent e) {
//				dumpInfo(e);
//				PanelExplo.this.requestFocusInWindow();
//				repaint();
//			}
//			
//			public void focusLost(FocusEvent e) {
//				dumpInfo(e);
//			}
//			
//			private void dumpInfo(FocusEvent e) {
//				System.out.println("PanelExplo");
//				System.out.println("Source  : " + name(e.getComponent()));
//				System.out.println("Opposite : "
//						+ name(e.getOppositeComponent()));
//				System.out.println("Temporary: " + e.isTemporary());
//			}
//			
//			private String name(Component c) {
//				return (c == null) ? null : c.getName();
//			}
//		};
//		this.addFocusListener(listener);
	

		
		for (int i = 0; i < this.nbCartesParNiveau.length; i++) {
			this.nbCartesParNiveau[0] = 0;
		}
		
		this.operations = new GestionnaireOperations();
		
		PanelExplo.connecteurEntreDeuxBlocs[0] = PanelExplo.connecteurEntreDeuxBlocs[1] = PanelExplo.connecteurEntreDeuxBlocs[2] = new Line2D.Double(0, 0, 0, 0);
		
		initImages();

	}
	
	private void initImages(){
		//		this.imgCarteParente = new ImageIcon(getClass().getResource(ConstantesImages.imgCarteParente));
		this.imgCarte = new ImageIcon(getClass().getResource(ConstantesImages.imgCarte));
		
		this.imgAddition = new ImageIcon(getClass().getResource(ConstantesImages.imgAddition));
		this.imgAddition3 = new ImageIcon(getClass().getResource(ConstantesImages.imgAddition3));
		this.imgSoustration = new ImageIcon(getClass().getResource(ConstantesImages.imgSoustration));
		this.imgMultiplication = new ImageIcon(getClass().getResource(ConstantesImages.imgMultiplication));
		this.imgMultiplication3 = new ImageIcon(getClass().getResource(ConstantesImages.imgMultiplication3));
		this.imgDivision = new ImageIcon(getClass().getResource(ConstantesImages.imgDivision));
		this.imgFonctionUnaire = new ImageIcon(getClass().getResource(ConstantesImages.imgFonctionUnaire));
		this.imgFonctionBinaire = new ImageIcon(getClass().getResource(ConstantesImages.imgFonctionBinaire));
		
		this.imgUnionEspace = new ImageIcon(getClass().getResource(ConstantesImages.imgUnionEspace));
		this.imgIntersectionEspace = new ImageIcon(getClass().getResource(ConstantesImages.imgIntersectionEspace));
		this.imgSoustractionespace = new ImageIcon(getClass().getResource(ConstantesImages.imgSoustractionespace));
		this.imgExclusionEspace = new ImageIcon(getClass().getResource(ConstantesImages.imgExclusionEspace));
		this.imgComplementEspace = new ImageIcon(getClass().getResource(ConstantesImages.imgComplementEspace));
		
		this.imgUnionVue = new ImageIcon(getClass().getResource(ConstantesImages.imgUnionVue));
		this.imgIntersectionVue = new ImageIcon(getClass().getResource(ConstantesImages.imgIntersectionVue));
		this.imgExclusionVue = new ImageIcon(getClass().getResource(ConstantesImages.imgExclusionVue));
		this.imgUnionAvecConservationVue = new ImageIcon(getClass().getResource(ConstantesImages.imgUnionAvecConservationVue));
		
		this.imgCrsmtCouleurCouleur = new ImageIcon(getClass().getResource(ConstantesImages.imgCrsmtCouleurCouleurLegende));
		this.imgCrsmtCouleurTaille  = new ImageIcon(getClass().getResource(ConstantesImages.imgCrsmtCouleurTailleLegende));
		
	}
	
	public void setAt(AffineTransform at) {
		this.at = at;
	}
	
	public AffineTransform getAt() {
		return this.at;
	}
	
	public void centreVueSurUnPoint(Point p1) {
		this.getAt().translate(p1.getX(), p1.getY());
		
		this.revalidate();
		this.repaint();
	}
	
	protected void paintComponent(Graphics g) {
		super.paint(g);
	}
	
	public void repaint(){
		paintImmediately(0, 0, this.getWidth(), this.getHeight());
	}
	
	@Override
	public void paint(Graphics gC) {
		
		setGC2D((Graphics2D) gC);
		
		this.gC2D.setColor(this.defaultColor);
		this.gC2D.fillRect(0, 0, getWidth(), getHeight());
		
		//Anti-aliasing
		if (!ConstantesApplication.dragEnabled) {
			this.gC2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.gC2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			this.gC2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		
		// AffineTransform
		AffineTransform transform = this.gC2D.getTransform();
		// Concatener les transformations pour eviter le probleme du decalage !!!
		transform.concatenate(this.getAt());
		this.gC2D.setTransform(transform);
		
		this.gC2D.setColor(this.defaultColor);
		
		ImageIcon imageActive = null;
		
		for (Carte iterCarte : this.parent.panelActif.couchesDeCartes.getCouches()) {
			
			//Affichage de liens avec la carte parente
			this.gC2D.setColor(Color.LIGHT_GRAY);
			this.gC2D.setStroke(ConstantesApplication.dashed);
			if( iterCarte.carteMere != null )
				this.gC2D.drawLine(iterCarte.carteExplo.getpXY().x+ConstantesExploration.widthBloc / 2, iterCarte.carteExplo.getpXY().y + ConstantesExploration.heightBloc/ 2,
						iterCarte.carteMere.carteExplo.getpXY().x + ConstantesExploration.widthBloc / 2, iterCarte.carteMere.carteExplo.getpXY().y + ConstantesExploration.heightBloc/ 2);
			this.gC2D.setStroke( ConstantesApplication.normalStroke);
			
		}
		
		// Affichage Actif
		if (this.drawBloc && this.outilsActifs != ConstantesExploration.link && this.outilsActifs != ConstantesExploration.selection ) {
			int width = ConstantesExploration.widthOperationBloc;
			int height = ConstantesExploration.heightOperationBloc;
			switch (this.outilsActifs) {
				case ConstantesExploration.drawMapLayer:
					imageActive = this.imgCarte;
					width = ConstantesExploration.widthBloc;
					height = ConstantesExploration.heightBloc;
					break;
				case ConstantesExploration.additionBloc:
					imageActive = this.imgAddition;
					break;
				case ConstantesExploration.addition3Bloc:
					imageActive = this.imgAddition3;
					break;
				case ConstantesExploration.soustractionBloc:
					imageActive = this.imgSoustration;
					break;
				case ConstantesExploration.multiplicationBloc:
					imageActive = this.imgMultiplication;
					break;
				case ConstantesExploration.multiplication3Bloc:
					imageActive = this.imgMultiplication3;
					break;
				case ConstantesExploration.divisionBloc:
					imageActive = this.imgDivision;
					break;
				case ConstantesExploration.fonctionUnaire:
					imageActive = this.imgFonctionUnaire;
					break;
				case ConstantesExploration.fonctionBinaire:
					imageActive = this.imgFonctionBinaire;
					break;
				case ConstantesExploration.unionEspaceBloc:
					imageActive = this.imgUnionEspace;
					break;
				case ConstantesExploration.soustractionEspaceBloc:
					imageActive = this.imgSoustractionespace;
					break;
				case ConstantesExploration.intersectionEspaceBloc:
					imageActive = this.imgIntersectionEspace;
					break;
				case ConstantesExploration.exclusionEspaceBloc:
					imageActive = this.imgExclusionEspace;
					break;
				case ConstantesExploration.complementEspaceBloc:
					imageActive = this.imgComplementEspace;
					break;
				case ConstantesExploration.unionVueBloc:
					imageActive = this.imgUnionVue;
					break;
				case ConstantesExploration.intersectionVueBloc:
					imageActive = this.imgIntersectionVue;
					break;
				case ConstantesExploration.exclusionVueBloc:
					imageActive = this.imgExclusionVue;
					break;
				case ConstantesExploration.unionAvecConservationVueBloc:
					imageActive = this.imgUnionAvecConservationVue;
					break;
				case ConstantesExploration.crsmtCouleurCouleurLegendeBloc:
					imageActive = this.imgCrsmtCouleurCouleur;
					break;
				case ConstantesExploration.crsmtCouleurTailleLegendeBloc:
					imageActive = this.imgCrsmtCouleurTaille;
					break;
				default:
					break;
			}
			//			System.out.println(imageActive.getImage());
			if( imageActive != null ) 
				if(this.outilsActifs != ConstantesExploration.drag & this.outilsActifs != ConstantesExploration.moveBloc)
					this.gC2D.drawImage(imageActive.getImage(), this.posMouseX-width/2, this.posMouseY-height/2, width, height, this);
		}
		
		// Affichage des cartes
		for (Carte iterCarte : this.parent.panelActif.couchesDeCartes.getCouches()) {
			
			if( iterCarte.isSelected() ) {
				//Affiche le rectangle rouge signalant la carte active
				this.gC2D.setColor(Color.RED);
				this.gC2D.drawRect((int)iterCarte.carteExplo.getEmprise().getX(), (int)iterCarte.carteExplo.getEmprise().getY(), (int)iterCarte.carteExplo.getEmprise().getWidth(), (int)iterCarte.carteExplo.getEmprise().getHeight());
			}
			
			// Dessine l'image de la carte
			this.gC2D.drawImage(iterCarte.carteExplo.getImageCarte(), iterCarte.carteExplo.getpXY().x, iterCarte.carteExplo.getpXY().y, ConstantesExploration.widthBloc, ConstantesExploration.heightBloc, this);
			// Afichage d'un petit carre de couleur au centre
			this.gC2D.setColor(iterCarte.getColorLayer());
			this.gC2D.fillRect(iterCarte.carteExplo.getpXY().x + ConstantesExploration.widthBloc / 2 - 4, iterCarte.carteExplo.getpXY().y + ConstantesExploration.heightBloc / 2 - 4, 8, 8);
			
			//Texte descriptif du bloc
			this.gC2D.drawString(iterCarte.getNom(), iterCarte.carteExplo.getpXY().x, iterCarte.carteExplo.getpXY().y + ConstantesExploration.heightBloc + 20);
			
			// Affichage des connecteurs -> petits rectangles de couleur lies
			// aux parametres d'une carte
			this.gC2D.setColor(ConstantesExploration.couleurEspace);
			this.gC2D.drawRect(iterCarte.carteExplo.rectEspaceEntree.x, iterCarte.carteExplo.rectEspaceEntree.y, iterCarte.carteExplo.rectEspaceEntree.width, iterCarte.carteExplo.rectEspaceEntree.height);
			this.gC2D.drawRect(iterCarte.carteExplo.rectEspaceSortie.x, iterCarte.carteExplo.rectEspaceSortie.y, iterCarte.carteExplo.rectEspaceSortie.width, iterCarte.carteExplo.rectEspaceSortie.height);
			
			this.gC2D.setColor(ConstantesExploration.couleurMesure);
			this.gC2D.drawRect(iterCarte.carteExplo.rectMesureEntree.x, iterCarte.carteExplo.rectMesureEntree.y, iterCarte.carteExplo.rectMesureEntree.width, iterCarte.carteExplo.rectMesureEntree.height);
			this.gC2D.drawRect(iterCarte.carteExplo.rectMesureSortie.x, iterCarte.carteExplo.rectMesureSortie.y, iterCarte.carteExplo.rectMesureSortie.width, iterCarte.carteExplo.rectMesureSortie.height);
			
			this.gC2D.setColor(ConstantesExploration.couleurVue);
			this.gC2D.drawRect(iterCarte.carteExplo.rectVueEntree.x, iterCarte.carteExplo.rectVueEntree.y, iterCarte.carteExplo.rectVueEntree.width, iterCarte.carteExplo.rectVueEntree.height);
			this.gC2D.drawRect(iterCarte.carteExplo.rectVueSortie.x, iterCarte.carteExplo.rectVueSortie.y, iterCarte.carteExplo.rectVueSortie.width, iterCarte.carteExplo.rectVueSortie.height);
			
			this.gC2D.setColor(ConstantesExploration.couleurLegende);
			this.gC2D.drawRect(iterCarte.carteExplo.rectLegendeSortie.x, iterCarte.carteExplo.rectLegendeSortie.y, iterCarte.carteExplo.rectLegendeSortie.width, iterCarte.carteExplo.rectLegendeSortie.height);
			this.gC2D.drawRect(iterCarte.carteExplo.rectLegendeEntree.x, iterCarte.carteExplo.rectLegendeEntree.y, iterCarte.carteExplo.rectLegendeEntree.width, iterCarte.carteExplo.rectLegendeEntree.height);
			
		}
		
		// Affichage des operateurs
		for (BlocOperation iterOperation : this.parent.panelActif.panelExplo.getJPanel().operations.getOperations()) {
			this.gC2D.drawImage(iterOperation.getImageOperation(), iterOperation.getpXY().x, iterOperation.getpXY().y, ConstantesExploration.widthOperationBloc, ConstantesExploration.heightOperationBloc, this);
			
			if( iterOperation.isSelected() ) {
				this.gC2D.setColor(Color.RED);
				this.gC2D.drawRect((int)iterOperation.getEmprise().getX(), (int)iterOperation.getEmprise().getY(), (int)iterOperation.getEmprise().getWidth(), (int)iterOperation.getEmprise().getHeight());
			}
			
			// Affichage des rectangles connecteurs du bloc
			
			// Selection de la couleur en fontion du type de bloc
			switch (iterOperation.levierMisEnJeu) {
				case ConstantesExploration.blocOperationEspace:
					this.gC2D.setColor(ConstantesExploration.couleurEspace);
					break;
				case ConstantesExploration.blocOperationMesure:
					this.gC2D.setColor(ConstantesExploration.couleurMesure);
					break;
				case ConstantesExploration.blocOperationVue:
					this.gC2D.setColor(ConstantesExploration.couleurVue);
					break;
				case ConstantesExploration.blocOperationLegende:
					this.gC2D.setColor(ConstantesExploration.couleurLegende);
					break;
				default:
					break;
			}
			
			//Ajout des blocs d'operations unaires - pas d'entree en haut sur les blocs unaires
			if( iterOperation.operation != ConstantesExploration.typeOperationComplementEspace &
					iterOperation.operation != ConstantesExploration.typeOperationFonctionUnaireMesure &
					iterOperation.operation != ConstantesExploration.typeOperationSommePonderee)
				this.gC2D.drawRect(iterOperation.rectEntreeHaut.x, iterOperation.rectEntreeHaut.y, iterOperation.rectEntreeHaut.width, iterOperation.rectEntreeHaut.height);
			
			//Pour tous les autres blocs operations
			this.gC2D.drawRect(iterOperation.rectEntreeGauche.x, iterOperation.rectEntreeGauche.y, iterOperation.rectEntreeGauche.width, iterOperation.rectEntreeGauche.height);
			this.gC2D.drawRect(iterOperation.rectSortie.x, iterOperation.rectSortie.y, iterOperation.rectSortie.width, iterOperation.rectSortie.height);
			
			//Pour les operateurs ternaires - entree du bas ajoutee
			if( iterOperation.operation == ConstantesExploration.typeOperationAddition3Mesure |
					iterOperation.operation == ConstantesExploration.typeOperationMutliplication3Mesure )
				this.gC2D.drawRect(iterOperation.rectEntreeBas.x, iterOperation.rectEntreeBas.y, iterOperation.rectEntreeBas.width, iterOperation.rectEntreeBas.height);
		}
		
		// Affichage des connecteurs
		this.gC2D.setStroke(new BasicStroke(2.0f));
		for (ConnecteurBlocs iterP : PanelExplo.connecteurs) {
			this.gC2D.setColor(iterP.getCouleur());	
			for (Line2D ligne : iterP.getLines()) {
				this.gC2D.drawLine(	new Double(ligne.getP1().getX()).intValue(), new Double(ligne.getP1().getY()).intValue(),
									new Double(ligne.getP2().getX()).intValue(), new Double(ligne.getP2().getY()).intValue());
			}
		}
		
		// Affichage du connecteur en cours de dessin
		for (int i = 0; i < PanelExplo.connecteurEntreDeuxBlocs.length; i++) {
			this.gC2D.setColor(Color.BLACK);
			this.gC2D.drawLine(
					new Double(PanelExplo.connecteurEntreDeuxBlocs[i].getX1()).intValue(), new Double(PanelExplo.connecteurEntreDeuxBlocs[i].getY1()).intValue(),
					new Double(PanelExplo.connecteurEntreDeuxBlocs[i].getX2()).intValue(), new Double(PanelExplo.connecteurEntreDeuxBlocs[i].getY2()).intValue());
		}
		
	}
	
	public void setGC2D(Graphics2D gC2D) {
		this.gC2D = gC2D;
	}
	
	public Graphics2D getGC2D() {
		return this.gC2D;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Key Pressed on PanelExplo");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Key Released on PanelExplo");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Key Typed on PanelExplo");
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return this;
	}
	
}