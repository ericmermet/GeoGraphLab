/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.events;

import fr.ign.cogit.geographlab.algo.geom.RegionsDeVoronoi;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.PanelMainDraw;
import fr.ign.cogit.geographlab.ihm.PopUpClicDroitPanelPrincipal;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.visu.ArcGraphique;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class MouseEvents extends MouseAdapter {
	
	private PanelMainDraw panel;
	
	private int widthSelection = 4, heightSelection = 4;
	private static int xClickStart, yClickStart;
	private Point centerToPoint;
	
	private int currentClickedTrX, currentClickedTrY;
	private static int xClickStartTempDrawEdge, yClickStartTempDrawEdge;
	private static Noeud noeudDebut, noeudFin;
	
	// Definition du rectangle de selection
	Rectangle2D.Double mySelection;
	private String selectionMode = "Simple";
	private ObjetGraphique currentSelectedObject;
	private boolean selectedObject, unselectedObject;
	
	private final static int MOUSE_BUTTON_3_DRAG = 2048;
	
	PopUpClicDroitPanelPrincipal menuClicDroit;
	
	GeometryFactory geomFac = new GeometryFactory();
	
	public MouseEvents(PanelMainDraw panel) {
		super();
		this.panel = panel;
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		// System.out.println("click");
		
		// this.currentClickedTrX = me.getX();
		// this.currentClickedTrY = me.getY();
		
		clicksPointedTransformOnPanel(me.getX(), me.getY());
		
		this.mySelection = new Rectangle2D.Double(this.currentClickedTrX - this.widthSelection, this.currentClickedTrY - this.heightSelection, this.widthSelection, this.heightSelection);
		
		switch (me.getButton()) {
			case MouseEvent.BUTTON1:
				switch (this.panel.getOutilsSelectionne()) {
					case ConstantesApplication.selectionMode:
						selection(me);
						break;
					case ConstantesApplication.drawingNodesMode:
						selection(me);
						drawNode(me);
						break;
					case ConstantesApplication.zoomInMode:
						//						this.panel.getAt().setToScale(1.414 * this.panel.getAt().getScaleX(), 1.414 * this.panel.getAt().getScaleY());
						//						this.panel.centreVue(new Point(this.currentClickedTrX, this.currentClickedTrY), new Double(1.1));
						this.panel.repaint();
						break;
					case ConstantesApplication.zoomOutMode:
						//						this.panel.getAt().setToScale(0.707 * this.panel.getAt().getScaleX(), 0.707 * this.panel.getAt().getScaleY());
						//						this.panel.centreVue(new Point(this.currentClickedTrX, this.currentClickedTrY), new Double(0.9));
						this.panel.repaint();
						break;
					default:
						break;
				}
				break;
			case MouseEvent.BUTTON2:
				
				break;
			case MouseEvent.BUTTON3:
				selection(me);
				
				this.menuClicDroit = new PopUpClicDroitPanelPrincipal(this.panel, me);
				// this.menuClicDroit.setVisible(true);
				// this.menuClicDroit.setLocation(me.getX(), me.getY());
				// this.menuClicDroit = null;
				
				break;
			default:
				break;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent me) {
		MouseEvents.xClickStart = me.getX();
		MouseEvents.yClickStart = me.getY();
		switch (me.getButton()) {
			case MouseEvent.BUTTON1:
				switch (this.panel.getOutilsSelectionne()) {
					case ConstantesApplication.drawingEdgesMode:
						selection(me);
						initiateDrawEdge(me);
						break;
					default:
						break;
				}
				break;
			case MouseEvent.BUTTON2:
				
				break;
			case MouseEvent.BUTTON3:
				
				break;
			default:
				break;
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent me) {

		ConstantesApplication.dragEnabled = false;
		
		switch (me.getModifiersEx()) {
			
			default:
				
				switch (this.panel.getOutilsSelectionne()) {
					case ConstantesApplication.drawingEdgesMode:
						
						finaliseDrawEdge(me);
						
						break;
					case ConstantesApplication.dragMode:
						// un coup de repaint avec antialiasing apres le deplacement
						// c'est plus joli
						ConstantesApplication.dragEnabled = false;
						
						break;
					case ConstantesApplication.selectionModePolygon:
						
						this.panel.listOfSelectedObjects.clear();
						
						for (NoeudGraphique iterNG : this.panel.carte.getVueDuGraphe().getNoeudsGraphiques()) {
							if( this.panel.polygonSelect.intersects(iterNG.getShape().getBounds())) {
								this.currentSelectedObject = iterNG;
								this.panel.listOfSelectedObjects.add(this.currentSelectedObject);
								System.out.println(iterNG.getNom());
								iterNG.setSelected(true);
							}
						}
						
						this.panel.polygonSelect = new Polygon();
						
						break;
					default:
						break;
				}
				
				break;
				
			case MOUSE_BUTTON_3_DRAG:
				ConstantesApplication.dragEnabled = false;
		}
		this.panel.repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent me) {
		
		this.panel.getGraphics().setXORMode(Color.WHITE);
		
		int pointDragX = me.getX();
		int pointDragY = me.getY();
		
		switch (me.getModifiersEx()) {
			
			case MOUSE_BUTTON_3_DRAG:
				
				ConstantesApplication.dragEnabled = true;				
				
				Coordinate coord = new Coordinate(	-(MouseEvents.xClickStart - pointDragX) / this.panel.getAt().getScaleX(),
													-(MouseEvents.yClickStart - pointDragY) / this.panel.getAt().getScaleY());
				
				this.centerToPoint = this.geomFac.createPoint(coord);
					
				this.panel.centreVueSurUnPoint(this.centerToPoint);
				
				xClickStart = pointDragX;
				yClickStart = pointDragY;
				
				break;
				
			default:
				
				switch (this.panel.getOutilsSelectionne()) {
					case ConstantesApplication.moveNodeMode:
						for (ObjetGraphique objGraphique : this.panel.listOfSelectedObjects)
							this.currentSelectedObject = objGraphique;
						
							clicksPointedTransformOnPanel(me.getX(), me.getY());
						
							if (this.currentSelectedObject.getType() == ConstantesApplication.typeVertex) {
								((NoeudGraphique) this.currentSelectedObject).getNoeudTopologique().setPosition(this.currentClickedTrX, this.currentClickedTrY);
							}
						break;
					case ConstantesApplication.dragMode:
						ConstantesApplication.dragEnabled = true;				
						
						Coordinate coord2 = new Coordinate(	-(MouseEvents.xClickStart - pointDragX) / this.panel.getAt().getScaleX(),
															-(MouseEvents.yClickStart - pointDragY) / this.panel.getAt().getScaleY());

						this.centerToPoint = this.geomFac.createPoint(coord2);
						
						this.panel.centreVueSurUnPoint(this.centerToPoint);
						
						xClickStart = pointDragX;
						yClickStart = pointDragY;
						
						break;
					case ConstantesApplication.drawingEdgesMode:
						this.panel.getGC2D().setColor(Color.GREEN);
						// this.panel.getGC2D().setStroke(new BasicStroke( 3.0f ));
						
						clicksPointedTransformOnPanel(me.getX(), me.getY());
						
						int pointClickedX = this.currentClickedTrX;
						int pointClickedY = this.currentClickedTrY;
						
						// System.out.println(this.panel.getGC2D().hashCode());
						
						this.panel.ligneEntreDeuxNoeuds = new Line2D.Double(MouseEvents.xClickStartTempDrawEdge, MouseEvents.yClickStartTempDrawEdge, pointClickedX, pointClickedY);
						
						// this.panel.getGC2D().drawLine(MouseEvents.xClickStartTempDrawEdge,
						// MouseEvents.yClickStartTempDrawEdge, pointClickedX,
						// pointClickedY);
						break;
					case ConstantesApplication.selectionModePolygon:
						
						for (ObjetGraphique objG : this.panel.carte.getVueDuGraphe().getNoeudsGraphiques()) {
							objG.setSelected(false);
						}
						
						this.panel.getGC2D().setColor(Color.GREEN);
						// this.panel.getGC2D().setStroke(new BasicStroke( 3.0f ));
						
						clicksPointedTransformOnPanel(me.getX(), me.getY());
						
						int pointClickedPolygonX = this.currentClickedTrX;
						int pointClickedPolygonY = this.currentClickedTrY;
						
						this.panel.polygonSelect.addPoint(pointClickedPolygonX, pointClickedPolygonY);
						
						break;						
					default:
						break;
				}
				
		}
		
		this.panel.repaint();
	}
	
	@Override
	public void mouseMoved(MouseEvent me) {
		
		//For keyListner
		//		this.panel.requestFocus();
		
		clicksPointedTransformOnPanel(me.getX(), me.getY());
		
		this.panel.parent.statusBar.changePositionSouris(this.currentClickedTrX, this.currentClickedTrY);
		
		//Desactivation temporaire du tooltip
//		if (this.panel.getOutilsSelectionne() == ConstantesApplication.selectionMode) {
//			
//			this.mySelection = new Rectangle2D.Double(this.currentClickedTrX - this.widthSelection, this.currentClickedTrY - this.heightSelection, this.widthSelection, this.heightSelection);
//			
//			ToolTipManager.sharedInstance().setEnabled(true);
//			ToolTipManager.sharedInstance().setDismissDelay(3000);
//			ToolTipManager.sharedInstance().setInitialDelay(10);
//			
//			for (ArcGraphique iterAG : this.panel.carte.getVueDuGraphe().getArcsGraphiques()) {
//				// iterNG.setSelected(false);
//				if (iterAG.getShape().intersects(this.mySelection) && iterAG.isVisible()) {
//					// Et si la forme du noeud croise la selection
//					
//					this.panel.setToolTipText("<html>" +
//							"Arc :" + iterAG.getNom() + "<br/>" +
//							"Poids :" + iterAG.getArcTopologique().getPoids() + "<br/>" +
//							this.panel.carte.getNomIndicateurCourant() + " :" + iterAG.getArcTopologique().getValeurPourIndicateur(this.panel.carte.getNomIndicateurCourant()) +
//							"</html>");
//					
//					break;
//					
//				}
//			}
//			
//			for (NoeudGraphique iterNG : this.panel.carte.getVueDuGraphe().getNoeudsGraphiques()) {
//				// iterNG.setSelected(false);
//				if (iterNG.getShape().intersects(this.mySelection) && iterNG.isVisible()) {
//					// Et si la forme du noeud croise la selection
//					
//					this.panel.setToolTipText("<html>" +
//							"Noeud :" + iterNG.getNom() + "<br/>" + 
//							"Poids " + this.panel.carte.getNom() + " : " + iterNG.getNoeudTopologique().getValeurPourIndicateur(this.panel.carte.getNomIndicateurCourant()) + "<br/>" + 
//							this.panel.carte.getNomIndicateurCourant() + " : " + iterNG.getNoeudTopologique().getValeurPourIndicateur(this.panel.carte.getNomIndicateurCourant()) +
//							"</html>");
//					//FIXME Nom de l'indicateur diff??rent de nom carte lors d'un chargement de donn??es type noeuds et donc plus d'affichage tooltip
//					
//					break;
//				}
//			}
//			
//			for (ZoneAgregee iterZA : this.panel.carte.getVueDuGraphe().getZonesAgregees()) {
//				// iterNG.setSelected(false);
//				if (iterZA.getShape().intersects(this.mySelection) && iterZA.isVisible() && this.panel.carte.variablesDeCarte.afficheAgregation) {
//					// Et si la forme du noeud croise la selection
//					
//					this.panel.setToolTipText("<html>" + iterZA.getNom() + "<br/>" + iterZA.getNoeuds() + " Noeuds" + "<br/>" + iterZA.getArcs() + " Arcs" + "<br/>" + this.panel.carte.getNomIndicateurCourant() + " :" + iterZA.getValeurGraphique() + "</html>");
//					
//					break;
//				}
//			}
//			
//			this.panel.repaint();
//		} else {
//			ToolTipManager.sharedInstance().setEnabled(false);
//		}
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		this.panel.scaleFactor -= (ConstantesApplication.zoomValue * e.getWheelRotation());
		
		clicksPointedTransform(e.getX(), e.getY());
		
		Coordinate coordinate = new Coordinate(this.currentClickedTrX, this.currentClickedTrY);
		Point pCentreVue = this.geomFac.createPoint(coordinate);
		
		this.panel.centreVue(pCentreVue);
		this.panel.repaint();
		
	}
	
	public void selection(MouseEvent me) {
		
		boolean noeudDejaSelectionne = false;
		
		// Pour les selections multiples avec CTRL
		
		//16 BUTTON_1 ONLY
		//17 + SHIFT
		//18 + CTRL
		//24 + ALT
		
		if ( me.getModifiers() ==  16 ){
			//Effacement de la selection precedente si pas CTRL ou SHIT ou ALT
			for (ObjetGraphique objG : this.panel.carte.getVueDuGraphe().getNoeudsGraphiques()) {
				objG.setSelected(false);
			}
			this.panel.listOfSelectedObjects.clear();
		}
		
		for (NoeudGraphique iterNG : this.panel.carte.getVueDuGraphe().getNoeudsGraphiques()) {
			// iterNG.setSelected(false);
			if (iterNG.getShape().intersects(this.mySelection) && iterNG.isVisible()) {
				// Et si la forme du noeud croise la selection
				if (!iterNG.isSelected()) {
					this.currentSelectedObject = iterNG;
					this.panel.listOfSelectedObjects.add(this.currentSelectedObject);
					// this.panel.carte.timerClignotantObjetSelectionnes.addObjetClignotant(this.currentSelectedObject);
					noeudDejaSelectionne = true;
					System.out.println(iterNG.getNom());
					iterNG.setSelected(true);
					break;
				}
				iterNG.setSelected(false);
				iterNG.setSelectedDestination(false);
				iterNG.setSelectedOrigine(false);
				this.currentSelectedObject = null;
				this.panel.listOfSelectedObjects.remove(iterNG);
				// this.panel.carte.timerClignotantObjetSelectionnes.removeObjetClignotant(this.currentSelectedObject);
			}
		}
		
		for (ArcGraphique iterAG : this.panel.carte.getVueDuGraphe().getArcsGraphiques()) {
			iterAG.setSelected(false);
			if (!noeudDejaSelectionne && iterAG.getShape().intersects(this.mySelection) && iterAG.isVisible()) {
				// Et si la forme du noeud croise la selection
				if (!iterAG.isSelected()) {
					this.currentSelectedObject = iterAG;
					this.panel.listOfSelectedObjects.add(this.currentSelectedObject);
					// this.panel.carte.timerClignotantObjetSelectionnes.addObjetClignotant(this.currentSelectedObject);
					System.out.println(iterAG.getNom() + " poids = " + iterAG.getArcTopologique().getPoids());
					iterAG.setSelected(true);
					break;
				}
				iterAG.setSelected(false);
				this.currentSelectedObject = null;
				this.panel.listOfSelectedObjects.remove(iterAG);
				// this.panel.carte.timerClignotantObjetSelectionnes.removeObjetClignotant(this.currentSelectedObject);
			}
		}
		
		if (this.panel.listOfSelectedObjects.size() > 0) {
			System.out.println(this.panel.listOfSelectedObjects);
			this.panel.parent.console.addNewLine("Selection(s) de : " + this.panel.listOfSelectedObjects.toString());
			if( me.getModifiers() == 24 ){
				this.panel.parent.console.addQueueTexteInConsole(this.currentSelectedObject.getNom());
			}
		}
		
		// switch (me.getModifiers()) {
		// case 4: //Slection simple puis properties
		// case 16: //& (InputEvent.BUTTON1_MASK | InputEvent.CTRL_DOWN_MASK ))
		// != 0 & intersect == true)
		// //Pour une slection simple
		// if( this.intersect == true &&
		// this.selectionMode.compareTo("WindowedMultiple") != 0){
		// this.selectionMode = "Simple";
		// selectObjects(this.mySelection, this.selectionMode);
		// }
		// break;
		// case 18: //& (InputEvent.BUTTON1_MASK )) != 0 && intersect == true)
		// if( this.intersect == true &&
		// this.selectionMode.compareTo("WindowedMultiple") != 0){
		// this.selectionMode = "Multiple";
		// selectObjects(this.mySelection, this.selectionMode);
		// }
		// break;
		// }
		
		this.panel.repaint();
	}
	
	private void drawNode(MouseEvent me) {
		
		double pX = new Float(((-this.panel.getAt().getTranslateX() + me.getX()) / this.panel.getAt().getScaleX())).intValue();
		double pY = new Float(((-this.panel.getAt().getTranslateY() + me.getY()) / this.panel.getAt().getScaleY())).intValue();
		
		Coordinate coord = new Coordinate(pX, pY);
		Point p = this.geomFac.createPoint(coord);
		
		Noeud nouveauNoeudTopologique = new Noeud(p,this.panel.carte.getNomIndicateurCourant());
		
		//Operations de s??lection du noeud en creation
		eraseSelectedElements();
		nouveauNoeudTopologique.getNoeudGraphique().setSelected(true);
		this.panel.listOfSelectedObjects.add(nouveauNoeudTopologique.getNoeudGraphique());
		System.out.println(this.panel.listOfSelectedObjects);
		this.panel.parent.console.addNewLine("Selection(s) de : " + this.panel.listOfSelectedObjects.toString());
		
		this.panel.carte.getGraphe().addNoeud(nouveauNoeudTopologique);
		this.panel.carte.getVueDuGraphe().addNoeud(nouveauNoeudTopologique.getNoeudGraphique());
		this.panel.carte.getGraphe().setGrapheChange();
		
		if (this.panel.carte.variablesDeCarte.afficheVoronoi) {
			RegionsDeVoronoi surfVoronoi = new RegionsDeVoronoi(this.panel.carte.getGraphe());
			surfVoronoi.run();
//			surfVoronoi.pondereNoeudAleaRegion();
//			this.panel.carte.setPolygonesDeVoronoi(surfVoronoi.getRegionsDeVoronoi());
			this.panel.repaint();
		}
	}
	
	private void moveNode(MouseEvent me) {
		
	}
	
	private void initiateDrawEdge(MouseEvent me) {
		
		this.mySelection = new Rectangle2D.Double(this.currentClickedTrX - this.widthSelection, this.currentClickedTrY - this.heightSelection, this.widthSelection, this.heightSelection);
		
		clicksPointedTransformOnPanel(me.getX(), me.getY());
		
		MouseEvents.xClickStartTempDrawEdge = this.currentClickedTrX;
		MouseEvents.yClickStartTempDrawEdge = this.currentClickedTrY;
		
		Rectangle2D.Double selectionDepart = new Rectangle2D.Double(this.currentClickedTrX - this.widthSelection, this.currentClickedTrY - this.heightSelection, this.widthSelection, this.heightSelection);
		
		for (NoeudGraphique itNG : this.panel.carte.getVueDuGraphe().getNoeudsGraphiques()) {
			if (itNG.getShape().intersects(selectionDepart)) {
				;
				noeudDebut = itNG.getNoeudTopologique();
				eraseSelectedElements();
				itNG.setSelected(true);
			}
		}
	}
	
	private void finaliseDrawEdge(MouseEvent me) {
		clicksPointedTransformOnPanel(me.getX(), me.getY());
		
		Rectangle2D.Double selelectionFin = new Rectangle2D.Double(this.currentClickedTrX - this.widthSelection, this.currentClickedTrY - this.heightSelection, this.widthSelection, this.heightSelection);
		
		for (NoeudGraphique itNG : this.panel.carte.getVueDuGraphe().getNoeudsGraphiques()) {
			if (itNG.getShape().intersects(selelectionFin)) {
				new Float(((-this.panel.getAt().getTranslateX() + me.getX()) / this.panel.getAt().getScaleX())).intValue();
				new Float(((-this.panel.getAt().getTranslateY() + me.getY()) / this.panel.getAt().getScaleY())).intValue();
				noeudFin = itNG.getNoeudTopologique();
				eraseSelectedElements();
				itNG.setSelected(true);
				
				Arc nouvelArc = new Arc(noeudDebut, noeudFin, this.panel.carte.getNomIndicateurCourant());
				// ArcGraphique nouvelArcGraphique = new
				// ArcGraphique(nouvelArc);
				
				double poidsParDefaut = 2.0;
				
				this.panel.carte.getGraphe().addArcPondere(nouvelArc, poidsParDefaut);
				this.panel.carte.getVueDuGraphe().addArc(nouvelArc.getArcGraphique());
				
				this.panel.carte.getGraphe().setGrapheChange();
			}
		}
		
		this.panel.repaint();
	}
	
	/**
	 * Set currentClick X & Y mouse to transformed values (values screen -> values model)
	 * @param clickX : clicked screen position X
	 * @param clickY : clicked screen position Y
	 */
	private void clicksPointedTransform(int clickX, int clickY) {
		this.currentClickedTrX = new Float(((-this.panel.getGC2D().getTransform().getTranslateX() + clickX) / this.panel.getGC2D().getTransform().getScaleX())).intValue();
		this.currentClickedTrY = new Float(((-this.panel.getGC2D().getTransform().getTranslateY() + clickY) / this.panel.getGC2D().getTransform().getScaleY())).intValue();
	}
	
	private void clicksPointedTransformOnPanel(int clickX, int clickY) {
		this.currentClickedTrX = new Float(((-this.panel.getAt().getTranslateX() + clickX) / this.panel.getAt().getScaleX())).intValue();
		this.currentClickedTrY = new Float(((-this.panel.getAt().getTranslateY() + clickY) / this.panel.getAt().getScaleY())).intValue();
	}
	
//	public void selectObjects(Shape myShapeSelection, String selectionType) {
//		
//		if (this.selectionMode.compareTo("WindowedMultiple") != 0) {
//			this.selectedObject = this.currentSelectedObject.isSelected();
//			if (!this.selectedObject) { // Si non selectionne, on le selectionne
//				if (selectionType.compareTo("Simple") == 0) {
//					eraseSelectedElements();
//					for (ObjetGraphique itOG : this.panel.carte.getVueDuGraphe().getObjetsGraphiques()) {
//						itOG.setSelected(false);
//					}
//				}
//				this.selectedObject = !this.selectedObject;
//				this.currentSelectedObject.setSelected(this.selectedObject);
//				this.panel.carte.timerClignotantObjetSelectionnes.addObjetClignotant(this.currentSelectedObject);
//				this.panel.listOfSelectedObjects.add(this.currentSelectedObject);
//			} else { // Sinon il est selectionne et on le deselectionne
//				this.unselectedObject = this.selectedObject; // UnselectedObject
//				// passe true
//				this.currentSelectedObject.setSelected(!this.unselectedObject);
//				this.panel.listOfSelectedObjects.remove(this.currentSelectedObject);
//				this.panel.carte.timerClignotantObjetSelectionnes.removeObjetClignotant(this.currentSelectedObject);
//				// Voir s'il y a plusieurs objets selectionnes
//			}
//		} else {
//			eraseSelectedElements();
//			for (ObjetGraphique itGO : this.panel.listOfSelectedObjects) {
//				itGO.setSelected(true);
//				this.panel.listOfSelectedObjects.add(itGO);
//			}
//			this.selectionMode = "Simple";
//		}
//	}
	
	public void eraseSelectedElements() {
		for (ObjetGraphique gO : this.panel.listOfSelectedObjects) {
			gO.setSelected(false);
		}
		this.panel.listOfSelectedObjects.clear();
		this.panel.carte.timerClignotantObjetSelectionnes.clearObjetsClignotants();
	}
}