/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.events;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;
import fr.ign.cogit.geographlab.ihm.exploration.BlocGraphique;
import fr.ign.cogit.geographlab.ihm.exploration.BlocOperation;
import fr.ign.cogit.geographlab.ihm.exploration.BlocOperationEspace;
import fr.ign.cogit.geographlab.ihm.exploration.BlocOperationLegende;
import fr.ign.cogit.geographlab.ihm.exploration.BlocOperationMesure;
import fr.ign.cogit.geographlab.ihm.exploration.BlocOperationVue;
import fr.ign.cogit.geographlab.ihm.exploration.ConnecteurBlocs;
import fr.ign.cogit.geographlab.ihm.exploration.PanelExplo;

public class MouseEventsExplo extends MouseAdapter {
	
	private PanelExplo panel;
	
	private static boolean flagClick = false;
	
	Rectangle2D.Double mySelection;
	private int widthSelection = 8, heightSelection = 8;
	
	private static int xClickStart, yClickStart;
	private int currentClickedTrX, currentClickedTrY;
	private Point centerToPoint = new Point();
	
	private static int xTempConnect = 0, yTempConnect = 0;
	private static int idConnecteurOppose;
	private static BlocGraphique blocOpposeAConnecter;
	
	public MouseEventsExplo(PanelExplo panel) {
		super();
		this.panel = panel;
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		
		Carte nouvelleCarte;
		BlocOperation operation;
		
		clicksPointedTransform(me.getX(), me.getY());
		
		switch (me.getButton()) {
			case MouseEvent.BUTTON1:
				switch (this.panel.outilsActifs) {
					case ConstantesExploration.selection:
						
						Carte carteSelectionne = this.panel.parent.panelActif.carte;
						
						//Parcours des blocs cartes
						for (Carte iterBloc : this.panel.parent.panelActif.couchesDeCartes.getCouches()) {
							iterBloc.setSelected(false);
							if( iterBloc.carteExplo.getEmprise().contains(this.currentClickedTrX, this.currentClickedTrY) ) {
								iterBloc.setSelected(true);
								carteSelectionne = iterBloc;
								for (BlocOperation iterBlocOperation : this.panel.parent.panelActif.panelExplo.getJPanel().operations.getOperations()) {
									iterBlocOperation.setSelected(false);
								}
							}
						}
						
						//Parcours des blocs cartes
						for (BlocOperation iterBlocOperation : this.panel.parent.panelActif.panelExplo.getJPanel().operations.getOperations()) {
							iterBlocOperation.setSelected(false);
							if( iterBlocOperation.getEmprise().contains(this.currentClickedTrX, this.currentClickedTrY) ) {
								iterBlocOperation.setSelected(true);
								for (Carte iterBloc : this.panel.parent.panelActif.couchesDeCartes.getCouches()) {
									iterBloc.setSelected(false);
								}
							}
							
						}
						
						this.panel.parent.panelLayer.updateLayersFromLayerControler();
						this.panel.parent.panelActif.carte = carteSelectionne;
						this.panel.parent.panelActif.carte.getGraphe().setGrapheChange();
						this.panel.parent.panelActif.repaint();
						this.panel.parent.panelActif.panelExplo.getJPanel().repaint();
							
						break;
					case ConstantesExploration.link:
						// link(me);
						break;
					case ConstantesExploration.drawMapLayer:
						// Figer le bloc au clic
						this.panel.drawBloc = false;
						nouvelleCarte = new Carte(this.panel.parent.getPanelMainDrawActif().carte, "Nouvelle_Couche_Copie", "Normal");
						nouvelleCarte.setColorLayer(new Color(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
						nouvelleCarte.carteExplo.setpXY(new Point(this.currentClickedTrX-ConstantesExploration.widthBloc/2, this.currentClickedTrY-ConstantesExploration.heightBloc/2));
						nouvelleCarte.carteExplo.setRectanglesGenericLayer(nouvelleCarte.carteExplo.getpXY());
						this.panel.parent.panelLayer.updateLayersFromLayerControler();
						break;
					case ConstantesExploration.additionBloc:
						operation = new BlocOperationMesure(this.panel, ConstantesExploration.typeOperationAdditionMesure);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.addition3Bloc:
						operation = new BlocOperationMesure(this.panel, ConstantesExploration.typeOperationAddition3Mesure);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.soustractionBloc:
						operation = new BlocOperationMesure(this.panel, ConstantesExploration.typeOperationSoustractionMesure);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.multiplicationBloc:
						operation = new BlocOperationMesure(this.panel, ConstantesExploration.typeOperationMutliplicationMesure);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.multiplication3Bloc:
						operation = new BlocOperationMesure(this.panel, ConstantesExploration.typeOperationMutliplication3Mesure);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.divisionBloc:
						operation = new BlocOperationMesure(this.panel, ConstantesExploration.typeOperationDivisionMesure);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.fonctionUnaire:
						operation = new BlocOperationMesure(this.panel, ConstantesExploration.typeOperationFonctionUnaireMesure);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.fonctionBinaire:
						operation = new BlocOperationMesure(this.panel, ConstantesExploration.typeOperationFonctionBinaireMesure);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.sommePonderee:
						operation = new BlocOperationMesure(this.panel, ConstantesExploration.typeOperationFonctionBinaireMesure);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.unionEspaceBloc:
						operation = new BlocOperationEspace(this.panel, ConstantesExploration.typeOperationUnionEspace);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.soustractionEspaceBloc:
						operation = new BlocOperationEspace(this.panel, ConstantesExploration.typeOperationSoustractionEspace);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.intersectionEspaceBloc:
						operation = new BlocOperationEspace(this.panel, ConstantesExploration.typeOperationIntersectionEspace);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.exclusionEspaceBloc:
						operation = new BlocOperationEspace(this.panel, ConstantesExploration.typeOperationExclusionEspace);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.complementEspaceBloc:
						operation = new BlocOperationEspace(this.panel, ConstantesExploration.typeOperationComplementEspace);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.unionVueBloc:
						operation = new BlocOperationVue(this.panel, ConstantesExploration.typeOperationUnionVue);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.intersectionVueBloc:
						operation = new BlocOperationVue(this.panel, ConstantesExploration.typeOperationIntersectionVue);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.exclusionVueBloc:
						operation = new BlocOperationVue(this.panel, ConstantesExploration.typeOperationExclusionVue);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.unionAvecConservationVueBloc:
						operation = new BlocOperationVue(this.panel, ConstantesExploration.typeOperationUnionAvecConservationVue);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.crsmtCouleurCouleurLegendeBloc:
						operation = new BlocOperationLegende(this.panel, ConstantesExploration.typeOperationCrsmtLegendeCouleurCouleur);
						finaliseAjoutBloc(me, operation);
						break;
					case ConstantesExploration.crsmtCouleurTailleLegendeBloc:
						operation = new BlocOperationLegende(this.panel, ConstantesExploration.typeOperationCrsmtLegendeCouleurTaille);
						finaliseAjoutBloc(me, operation);
						break;
					default:
						break;
				}
				break;
			case MouseEvent.BUTTON2:

				break;
			
			case MouseEvent.BUTTON3:

				break;
		}
		
	}
	
	private void finaliseAjoutBloc(MouseEvent me, BlocOperation op) {
		this.panel.drawBloc = false;
		op.setpXY(new Point(this.currentClickedTrX-ConstantesExploration.widthOperationBloc/2, this.currentClickedTrY-ConstantesExploration.heightOperationBloc/2));
		op.setRectanglesGenericLayer(op.getpXY());
		this.panel.parent.panelActif.panelExplo.getJPanel().operations.ajouterUneOperation(op);
	}
	
	@Override
	public void mousePressed(MouseEvent me) {
		
		MouseEventsExplo.xClickStart = me.getX();
		MouseEventsExplo.yClickStart = me.getY();
//		MouseEventsExplo.xClickEnd = MouseEventsExplo.xClickStart;
//		MouseEventsExplo.yClickEnd = MouseEventsExplo.yClickStart;
		
		switch (this.panel.outilsActifs) {
			
			case ConstantesExploration.link:

				clicksPointedTransform(me.getX(), me.getY());
				
				this.mySelection = new Rectangle2D.Double(this.currentClickedTrX - this.widthSelection, this.currentClickedTrY - this.heightSelection, this.widthSelection, this.heightSelection);
				
				// Premiere connexion
				
				// Pour les connexions blocs
				for (Carte iterCarte : this.panel.parent.panelActif.couchesDeCartes.getCouches()) {
					iterCarte.carteExplo.premiereConnexion(this.mySelection, iterCarte.carteExplo);
				}
				
				// Pour les operations sur les operations
				for (BlocOperation iterOperation : this.panel.parent.panelActif.panelExplo.getJPanel().operations.getOperations() ) {
					iterOperation.premiereConnexion(this.mySelection, iterOperation);
				}
				
				break;
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent me) {
		
		switch (this.panel.outilsActifs) {
			
			case ConstantesExploration.link:

				clicksPointedTransform(me.getX(), me.getY());
				
				this.mySelection = new Rectangle2D.Double(this.currentClickedTrX - this.widthSelection, this.currentClickedTrY - this.heightSelection, this.widthSelection, this.heightSelection);
				
				// Seconde connexion
				MouseEventsExplo.flagClick = false;
				
				// Pour les connexions entre cartes
				for (Carte iterCarte : this.panel.parent.panelActif.couchesDeCartes.getCouches()) {
					iterCarte.carteExplo.secondeConnexion(this.mySelection, MouseEventsExplo.blocOpposeAConnecter);
				}
				
				// Pour les connexions contenant des operateurs
				for (BlocOperation iterOperation : this.panel.parent.panelActif.panelExplo.getJPanel().operations.getOperations() ) {
					iterOperation.secondeConnexion(this.mySelection, MouseEventsExplo.blocOpposeAConnecter );
				}
				
				break;
			case ConstantesExploration.drag:
				// un coup de repaint avec antialiasing apres le deplacement
				// c'est plus joli
				ConstantesExploration.dragEnabled = false;
				this.panel.repaint();
				break;
				
			default:

				break;
		}
		
		PanelExplo.connecteurEntreDeuxBlocs[0] = new Line2D.Double();
		PanelExplo.connecteurEntreDeuxBlocs[1] = new Line2D.Double();
		PanelExplo.connecteurEntreDeuxBlocs[2] = new Line2D.Double();
		
		this.panel.repaint();
		
	}
	
	@Override
	public void mouseDragged(MouseEvent me) {
		
		int pointDragX = me.getX();
		int pointDragY = me.getY();
		
//		System.out.println(me.getX() + "," + me.getY());

		switch (this.panel.outilsActifs) {
			
			case ConstantesExploration.link:
				
				clicksPointedTransform(me.getX(), me.getY());
				
//				System.out.println(this.currentClickedTrX + "," + this.currentClickedTrY);
				
				if (MouseEventsExplo.flagClick) {
					// System.out.println("drag ( " +
					// MouseEventsExplo.xTempConnect + " , " +
					// MouseEventsExplo.YTempConnect + " ) -> ( " + me.getX() +
					// " , " + me.getY() + " )");
					
					double moyX = (MouseEventsExplo.xTempConnect + this.currentClickedTrX) / 2;
					
					PanelExplo.connecteurEntreDeuxBlocs[0] = new Line2D.Double(MouseEventsExplo.xTempConnect, MouseEventsExplo.yTempConnect, moyX, MouseEventsExplo.yTempConnect);
					PanelExplo.connecteurEntreDeuxBlocs[1] = new Line2D.Double(moyX, MouseEventsExplo.yTempConnect, moyX, this.currentClickedTrY);
					PanelExplo.connecteurEntreDeuxBlocs[2] = new Line2D.Double(moyX, this.currentClickedTrY, this.currentClickedTrX, this.currentClickedTrY);
				}
				break;
				
			case ConstantesExploration.drag:
				
				ConstantesApplication.dragEnabled = true;				
				
				this.centerToPoint.setLocation(-(MouseEventsExplo.xClickStart - pointDragX), -(MouseEventsExplo.yClickStart - pointDragY));
	
				this.panel.centreVueSurUnPoint(this.centerToPoint);
				
				xClickStart = pointDragX;
				yClickStart = pointDragY;
				
				break;
				
			case ConstantesExploration.moveBloc:
				
				clicksPointedTransform(me.getX(), me.getY());
				
				BlocGraphique blocSelectionne = null;
				
				//Selection du bloc pointe
				
				//Parcours des blocs cartes
				for (Carte iterBloc : this.panel.parent.panelActif.couchesDeCartes.getCouches()) {
					iterBloc.setSelected(false);
					if( iterBloc.carteExplo.getEmprise().contains(this.currentClickedTrX, this.currentClickedTrY) ) {
						iterBloc.setSelected(true);
						blocSelectionne = iterBloc.carteExplo;
						for (BlocOperation iterBlocOperation : this.panel.parent.panelActif.panelExplo.getJPanel().operations.getOperations()) {
							iterBlocOperation.setSelected(false);
						}
					}
				}
				
				//Parcours des blocs operations
				for (BlocOperation iterBlocOperation : this.panel.parent.panelActif.panelExplo.getJPanel().operations.getOperations()) {
					iterBlocOperation.setSelected(false);
					if( iterBlocOperation.getEmprise().contains(this.currentClickedTrX, this.currentClickedTrY) ) {
						iterBlocOperation.setSelected(true);
						blocSelectionne = iterBlocOperation;
						for (Carte iterBloc : this.panel.parent.panelActif.couchesDeCartes.getCouches()) {
							iterBloc.setSelected(false);
						}
					}
					
				}

				if( blocSelectionne != null ) {
				//La on modifie le point pXY
					switch (blocSelectionne.typeBloc) {
						case ConstantesExploration.blocCarte:
							blocSelectionne.setpXY(new Point(this.currentClickedTrX-ConstantesExploration.widthBloc/2, this.currentClickedTrY-ConstantesExploration.heightBloc/2));
							break;
						case ConstantesExploration.blocOperation:
							blocSelectionne.setpXY(new Point(this.currentClickedTrX-ConstantesExploration.widthOperationBloc/2, this.currentClickedTrY-ConstantesExploration.heightOperationBloc/2));
							break;
					}
					blocSelectionne.setRectanglesGenericLayer(blocSelectionne.getpXY());
				}
				
				this.panel.parent.panelLayer.updateLayersFromLayerControler();
				break;

		}
		
		this.panel.repaint();
		this.panel.parent.panelActif.panelExplo.getJPanel().repaint();
		
	}
	
	@Override
	public void mouseMoved(MouseEvent me) {
		
//		System.out.println(me.getX() + "," + me.getY());
		
		clicksPointedTransform(me.getX(), me.getY());
		
//		System.out.println(this.currentClickedTrX + "," + this.currentClickedTrY);
		
		switch (this.panel.outilsActifs) {
			
			case ConstantesExploration.selection:
				//Affichage infobulle correspondante
					
				break;
			
			case ConstantesExploration.link:
				//Affichage infobulle correspondante
				
				break;
			
			case ConstantesExploration.drawMapLayer:
			case ConstantesExploration.unionEspaceBloc:
			case ConstantesExploration.soustractionEspaceBloc:
			case ConstantesExploration.intersectionEspaceBloc:
			case ConstantesExploration.exclusionEspaceBloc:
			case ConstantesExploration.complementEspaceBloc:
			case ConstantesExploration.additionBloc:
			case ConstantesExploration.addition3Bloc:
			case ConstantesExploration.soustractionBloc:
			case ConstantesExploration.multiplicationBloc:
			case ConstantesExploration.multiplication3Bloc:
			case ConstantesExploration.divisionBloc:
			case ConstantesExploration.fonctionUnaire:
			case ConstantesExploration.fonctionBinaire:
			case ConstantesExploration.unionVueBloc:
			case ConstantesExploration.intersectionVueBloc:
			case ConstantesExploration.exclusionVueBloc:
			case ConstantesExploration.unionAvecConservationVueBloc:
			case ConstantesExploration.crsmtCouleurCouleurLegendeBloc:
			case ConstantesExploration.crsmtCouleurTailleLegendeBloc:

				// Deplacer le bloc avec la souris lors de la creation
				this.panel.drawBloc = true;
				this.panel.posMouseX = this.currentClickedTrX;
				this.panel.posMouseY = this.currentClickedTrY;
				this.panel.repaint();
				
				break;
		}
	}
	
	/**
	 * Set currentClick X & Y mouse to transformed values (values screen -> values model)
	 * @param clickX : clicked screen position X
	 * @param clickY : clicked screen position Y
	 */
	private void clicksPointedTransform(int clickX, int clickY) {
		this.currentClickedTrX = new Float(((-this.panel.getAt().getTranslateX() + clickX) )).intValue();
		this.currentClickedTrY = new Float(((-this.panel.getAt().getTranslateY() + clickY) )).intValue();
//		this.currentClickedTrX = new Float(((-this.panel.getGC2D().getTransform().getTranslateX() + clickX) )).intValue();
//		this.currentClickedTrY = new Float(((-this.panel.getGC2D().getTransform().getTranslateY() + clickY) )).intValue();
		// System.out.println(this.panel.getAt().getScaleX() + " " +
		// this.panel.getAt().getScaleY());
		// System.out.println(this.widthSelection + " " + this.heightSelection);
	}
	
	public void selection(MouseEvent me) {
		System.out.println();
	}
	
	public static ConnecteurBlocs link(BlocGraphique bloc1, BlocGraphique bloc2, Color c, int typeConnecteur) {
		
		ArrayList<Rectangle> listRectConnect = new ArrayList<Rectangle>();
		
		for (Rectangle iterRectangleBloc1: bloc1.getRectangles()) {
			if(iterRectangleBloc1.intersectsLine(PanelExplo.connecteurEntreDeuxBlocs[0]) | iterRectangleBloc1.intersectsLine(PanelExplo.connecteurEntreDeuxBlocs[2] )){
				listRectConnect.add(iterRectangleBloc1);
				break;
			}
		}
		
		for (Rectangle iterRectangleBloc2: bloc2.getRectangles()) {
			if(iterRectangleBloc2.intersectsLine(PanelExplo.connecteurEntreDeuxBlocs[2]) | iterRectangleBloc2.intersectsLine(PanelExplo.connecteurEntreDeuxBlocs[0] )) {
				listRectConnect.add(iterRectangleBloc2);
				break;
			}
		}
		
		ConnecteurBlocs p = new ConnecteurBlocs(listRectConnect, typeConnecteur);
//		p.addLine(PanelExplo.connecteurEntreDeuxBlocs[0]);
//		p.addLine(PanelExplo.connecteurEntreDeuxBlocs[1]);
//		p.addLine(PanelExplo.connecteurEntreDeuxBlocs[2]);
		
		bloc1.addConnecteurFilaires(p);
		bloc2.addConnecteurFilaires(p);
		
		p.setCouleur(c);
		
		p.setTypeConnecteur(typeConnecteur);
		
		PanelExplo.connecteurs.add(p);
		
		return p;
		
	}

	public static boolean isFlagClick() {
		return flagClick;
	}

	public static void setFlagClick(boolean flagClick) {
		MouseEventsExplo.flagClick = flagClick;
	}
	
	public static void setXYTempConnect(int x, int y){
		MouseEventsExplo.xTempConnect = x;
		MouseEventsExplo.yTempConnect = y;
	}
	
	public static int getXTempConnect(){
		return MouseEventsExplo.xTempConnect;
	}
	
	public static int getYTempConnect(){
		return MouseEventsExplo.yTempConnect;
	}

	public static int getIdConnecteurOppose() {
		return idConnecteurOppose;
	}

	public static void setIdConnecteurOppose(int idConnecteurOppose) {
		MouseEventsExplo.idConnecteurOppose = idConnecteurOppose;
	}

	public static void setBlocOpposeAConnecter(BlocGraphique carteOpposeeAConnecter) {
		MouseEventsExplo.blocOpposeAConnecter = carteOpposeeAConnecter;
	}
	
	public static BlocGraphique getBlocOpposeAConnecter() {
		return blocOpposeAConnecter;
	}
	
}