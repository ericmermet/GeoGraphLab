/**
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.outils;

import fr.ign.cogit.geographlab.algo.geom.RegionsDeVoronoi;
import fr.ign.cogit.geographlab.algo.indicateurs.Chevelu;
import fr.ign.cogit.geographlab.cheminements.APSP;
import fr.ign.cogit.geographlab.cheminements.APSPDijkstra;
import fr.ign.cogit.geographlab.cheminements.APSPDijkstraODMultiThreads;
import fr.ign.cogit.geographlab.cheminements.APSPFloydWarshallTest;
import fr.ign.cogit.geographlab.cheminements.CheminLogit;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.cheminements.PCC;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.ihm.customdockingframes.ColorDockable;
import fr.ign.cogit.geographlab.lang.Messages;
import fr.ign.cogit.geographlab.test.Debug;
import fr.ign.cogit.geographlab.util.Timer;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;
import fr.ign.cogit.geographlab.visu.ZoneAgregee;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PanelTools extends ColorDockable {
	
	MainWindow parent;
	
	private JButton bPCC;
	private JButton bCheminLogit;
	private JButton bChevelu;
	
	public JComboBox<String> viewList;
	private JButton bOKViewList;
	
	private JButton bAgregate;
	private JButton bDesagregate;
	private JButton bAllShortestPaths;
	private JButton bAllShortestPathsDijkstra;
	private JButton bAllShortestPathOD;
	private JButton bAllShortestPathFloydWarshall;
	
	public JTextArea tGeometry;
	public JComboBox<String> cbGeometry;
	
	public JCheckBox cbAfficheGraphe;
	public JCheckBox cbAfficheGrapheDelaunay;
	public JCheckBox cbAfficheVoronoi;
	public JCheckBox cbAfficheGrapheNonDirige;
	
	//	public JSlider slTailleNoeuds;
	//	public JSlider slEpaisseurArcs;
	//	public JSlider slBufferZonesAgregees;
	
	public JCheckBox cbAfficheAgregation;
	public JCheckBox cbAfficheSousReseau;
	
	public JCheckBox cbAfficheImageFond;
	
	public JCheckBox cbAfficheLignesCentre;
	
	public JCheckBox cbAfficheEnveloppeConvexe;
	
	public JCheckBox cbColorieVoronoi;
	
	JSlider slZoom = new JSlider(1,200,70);
	JTextArea tZoomInfo = new JTextArea("Zoom Level :");
	JTextField tZoom = new JTextField(ConstantesApplication.zoomValue.toString(), 3);
	
	public PanelTools(MainWindow parent) {
		this( parent, Messages.getString("PanelTools.txtTools"), Color.WHITE, 1.0f); //$NON-NLS-1$
	}
	
	public PanelTools(MainWindow parent, String nom, Color couleur, float luminosite) {
		super( nom, couleur, luminosite );
		this.parent = parent;
		initialize();
	}
	
	private void initialize() {
		
		this.bPCC = new JButton(Messages.getString("PanelTools.bShortestPath")); //$NON-NLS-1$
		this.bPCC.addActionListener(new ActionListener() { /* Declenche sur PCC */
			public void actionPerformed(ActionEvent e) {
				
				if (PanelTools.this.parent.getPanelMainDrawActif().listOfSelectedObjects.size() == 2) {
					
					NoeudGraphique[] noeudSel = new NoeudGraphique[2];
					
					int i = 0;
					for (ObjetGraphique objGraphique : PanelTools.this.parent.getPanelMainDrawActif().listOfSelectedObjects) {
						if (objGraphique.getType() == ConstantesApplication.typeVertex)
							noeudSel[i++] = (NoeudGraphique) objGraphique;
					}
					
					Timer.tic();
					
					PCC lePCC = new PCC(PanelTools.this.parent.getPanelMainDrawActif().carte.getGraphe(),
							new OD(noeudSel[0].getNoeudTopologique(), noeudSel[1].getNoeudTopologique()),
							1, 0, PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheGrapheNonDirige);
					
					System.out.println(lePCC.toString());
					System.out.println(Messages.getString("PanelTools.txtInfo1") + Timer.tac()); //$NON-NLS-1$
					
					
					lePCC.setSelected(true);
					
					PanelTools.this.parent.getPanelMainDrawActif().repaint();
				} else {
					return;
				}
			}
		});
		
		//		this.bSpanningTree = new JButton(Messages.getString("PanelTools.BSpanTree")); //$NON-NLS-1$
		//		this.bSpanningTree.addActionListener(new ActionListener() {
		//			
		//			@Override
		//			public void actionPerformed(ActionEvent e) {
		//				Debug.printDebug(Messages.getString("PanelTools.txtInfoSpanTree")); //$NON-NLS-1$
		////				SpanningTree sTree = new SpanningTree(PanelTools.this.parent.getPanelMainDrawActif().carte);
		////				SpanningTreeCentrality sTree = new SpanningTreeCentrality(PanelTools.this.parent.getPanelMainDrawActif().carte);
		//				SpanningTreeCentralityMultiThreads sTree = new SpanningTreeCentralityMultiThreads(PanelTools.this.parent.getPanelMainDrawActif().carte);
		//				sTree.thread.start();
		//			}
		//		});
		
		this.bCheminLogit = new JButton(Messages.getString("PanelTools.bLogitPath")); //$NON-NLS-1$
		this.bCheminLogit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (PanelTools.this.parent.getPanelMainDrawActif().listOfSelectedObjects.size() == 2) {
					
					NoeudGraphique[] noeudSel = new NoeudGraphique[2];
					
					int i = 0;
					for (ObjetGraphique objGraphique : PanelTools.this.parent.getPanelMainDrawActif().listOfSelectedObjects) {
						if (objGraphique.getType() == ConstantesApplication.typeVertex)
							noeudSel[i++] = (NoeudGraphique) objGraphique;
					}
					
					CheminLogit cheminLogit = new CheminLogit(PanelTools.this.parent.getPanelMainDrawActif().carte, new OD(noeudSel[0].getNoeudTopologique(), noeudSel[1].getNoeudTopologique()), new Double(0.5));
					// Parametre teta pour le modele Logit
					cheminLogit.thread.start();
					
					PanelTools.this.parent.getPanelMainDrawActif().repaint();
					
				}
			}
		});
		
		this.bChevelu = new JButton(Messages.getString("PanelTools.bChevelu")); //$NON-NLS-1$
		this.bChevelu.addActionListener(new ActionListener() { /*
		 * Declenche sur
		 * Chemin
		 */
			public void actionPerformed(ActionEvent arg0) {
				Chevelu ericGrosso = new Chevelu(PanelTools.this.parent.getPanelMainDrawActif().carte);
				ericGrosso.thread.start();
			}
		});
		
		String[] viewStrings = { Messages.getString("PanelTools.bNormalView"), Messages.getString("PanelTools.bIndicatorView") }; //$NON-NLS-1$ //$NON-NLS-2$
		
		this.viewList = new JComboBox<String>(viewStrings);
		this.viewList.setSelectedIndex(0);
		
		this.bOKViewList = new JButton(Messages.getString("PanelTools.bOk")); //$NON-NLS-1$
		this.bOKViewList.addActionListener(new ActionListener() { /*
		 * Declenche sur
		 * Vue Normale
		 */
			public void actionPerformed(ActionEvent arg0) {
				switch (PanelTools.this.viewList.getSelectedIndex()) {
					case 0: // / VUE NORMALE
						PanelTools.this.parent.panelActif.carte.variablesDeCarte.affichageEnCours = new String(Messages.getString("PanelTools.txtInfoVarNormalView")); //$NON-NLS-1$
						break;
					case 1: // / VUE INDICATEUR
						PanelTools.this.parent.panelActif.carte.variablesDeCarte.affichageEnCours = new String(Messages.getString("PanelTools.txtInfoVArIndicatorView")); //$NON-NLS-1$
						break;
					default:
						PanelTools.this.parent.panelActif.carte.variablesDeCarte.affichageEnCours = new String(Messages.getString("PanelTools.12")); //$NON-NLS-1$
						break;
				}
				PanelTools.this.parent.panelLegend.updateLegendFromModel();
				PanelTools.this.parent.getPanelMainDrawActif().repaint();
			}
		});
		
		//		this.bSuppressSelected = new JButton("Sup ??l??ments s??lectionn??s");
		
		
		this.bAgregate = new JButton(Messages.getString("PanelTools.bAgregate")); //$NON-NLS-1$
		this.bAgregate.addActionListener(new ActionListener() {
			// Declenche sur Agreger
			public void actionPerformed(ActionEvent arg0) {
				
				ZoneAgregee nouvelleZoneAgregee = new ZoneAgregee(PanelTools.this.parent.getPanelMainDrawActif(), PanelTools.this.parent.getPanelMainDrawActif().listOfSelectedObjects);
				
				PanelTools.this.parent.getPanelMainDrawActif().carte.getVueDuGraphe().addZoneAgregee(nouvelleZoneAgregee);
				PanelTools.this.parent.getPanelMainDrawActif().carte.getGraphe().setGrapheChange();
				
				PanelTools.this.parent.getPanelMainDrawActif().repaint();
			}
		});
		
		this.bDesagregate = new JButton(Messages.getString("PanelTools.bDesagregate")); //$NON-NLS-1$
		this.bDesagregate.addActionListener(new ActionListener() {
			// Declenche sur Desagreger
			public void actionPerformed(ActionEvent arg0) {
				for (ZoneAgregee zoneAgrege : PanelTools.this.parent.getPanelMainDrawActif().carte.getVueDuGraphe().getZonesAgregees()) {
					
					for (NoeudGraphique noeud : zoneAgrege.getNoeuds()) {
						
						if( PanelTools.this.parent.getPanelMainDrawActif().listOfSelectedObjects.contains(noeud) ) {
							PanelTools.this.parent.getPanelMainDrawActif().carte.getVueDuGraphe().removeZoneAgregee(zoneAgrege);
						}
					}
					
				}
				PanelTools.this.parent.getPanelMainDrawActif().carte.getGraphe().setGrapheChange();
				PanelTools.this.parent.getPanelMainDrawActif().repaint();
			}
		});
		
		this.bAllShortestPaths = new JButton(Messages.getString("PanelTools.bAllSP")); //$NON-NLS-1$
		this.bAllShortestPaths.addActionListener(new ActionListener() { /*
		 * Declenche sur APSP */
			public void actionPerformed(ActionEvent arg0) {
				APSP allShortestPath = new APSP(PanelTools.this.parent.panelActif.carte);
				allShortestPath.thread.start();
			}
		});
		
		this.bAllShortestPathsDijkstra = new JButton(Messages.getString("PanelTools.bAllSPDij")); //$NON-NLS-1$
		this.bAllShortestPathsDijkstra.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent arg0) {
				APSPDijkstra allShortestPath2 = new APSPDijkstra(PanelTools.this.parent.panelActif.carte);
				allShortestPath2.thread.start();
			}
		});
		
		this.bAllShortestPathOD = new JButton(Messages.getString("PanelTools.bAllSPOD")); //$NON-NLS-1$
		this.bAllShortestPathOD.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent arg0) {
				//				APSPDijkstraOD allShortestPathOD = new APSPDijkstraOD(PanelTools.this.parent.panelActif.carte);
				//				allShortestPathOD.thread.start();
				PanelTools.this.parent.statusBar.mainProgressBar.setIndeterminate(true);
				APSPDijkstraODMultiThreads allShortestPathOD = new APSPDijkstraODMultiThreads(PanelTools.this.parent.panelActif.carte);
				allShortestPathOD.mainWorkingThread.start();
			}
		});
		
		this.bAllShortestPathFloydWarshall = new JButton(Messages.getString("PanelTools.bAllSPFW")); //$NON-NLS-1$
		this.bAllShortestPathFloydWarshall.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent arg0) {
				PanelTools.this.parent.statusBar.mainProgressBar.setIndeterminate(true);
				APSPFloydWarshallTest allShortestPathOD = new APSPFloydWarshallTest(PanelTools.this.parent.panelActif.carte);
				allShortestPathOD.thread.start();
			}
		});
		
		this.cbAfficheGraphe = new JCheckBox(Messages.getString("PanelTools.bCheckNetwork")); //$NON-NLS-1$
		this.cbAfficheGraphe.setSelected(ConstantesApplication.afficheGraphe);
		
		this.cbAfficheGrapheDelaunay = new JCheckBox(Messages.getString("PanelTools.bCheckDelaunay")); //$NON-NLS-1$
		this.cbAfficheGrapheDelaunay.setSelected(ConstantesApplication.afficheGrapheDelaunay);
		
		this.cbAfficheVoronoi = new JCheckBox(Messages.getString("PanelTools.bCheckVoronoi")); //$NON-NLS-1$
		this.cbAfficheVoronoi.setSelected(ConstantesApplication.afficheVoronoi);
		
		this.cbAfficheGrapheNonDirige = new JCheckBox(Messages.getString("PanelTools.bCheckUndirected")); //$NON-NLS-1$
		this.cbAfficheGrapheNonDirige.setSelected(ConstantesApplication.afficheGrapheNonDirige);
		
		this.tGeometry = new JTextArea(Messages.getString("PanelTools.tGeometrie")); //$NON-NLS-1$
		String[] viewStringsGeometry = { Messages.getString("PanelTools.tGeomSimple"), Messages.getString("PanelTools.tGeomShape") }; //$NON-NLS-1$ //$NON-NLS-2$
		this.cbGeometry = new JComboBox<String>(viewStringsGeometry);
		this.cbGeometry.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Selection de l'affichage geometrique des arcs (LineString = Simple ou MultiLineString = Reelle Shape )
				switch (PanelTools.this.cbGeometry.getSelectedIndex()) {
					case 0:
						PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheGeometrie = 0;
						break;
					case 1:
						PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheGeometrie = 1;
						break;
					default:
						PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheGeometrie = 0;
						break;
				}
				PanelTools.this.parent.panelActif.repaint();
			}
		});
		
		this.cbAfficheAgregation = new JCheckBox(Messages.getString("PanelTools.bCheckAgregateAreas")); //$NON-NLS-1$
		this.cbAfficheAgregation.setSelected(true);
		
		this.cbAfficheSousReseau = new JCheckBox(Messages.getString("PanelTools.bCheckSubNetwork")); //$NON-NLS-1$
		this.cbAfficheSousReseau.setSelected(true);
		
		this.cbAfficheImageFond = new JCheckBox(Messages.getString("PanelTools.bCheckBackground")); //$NON-NLS-1$
		this.cbAfficheImageFond.setSelected(false);
		
		this.cbAfficheLignesCentre = new JCheckBox(Messages.getString("PanelTools.bCheckCenterLines")); //$NON-NLS-1$
		this.cbAfficheLignesCentre.setSelected(false);
		
		this.cbAfficheEnveloppeConvexe = new JCheckBox(Messages.getString("PanelTools.bCheckConvexHull")); //$NON-NLS-1$
		this.cbAfficheEnveloppeConvexe.setSelected(false);
		
		this.cbColorieVoronoi = new JCheckBox(Messages.getString("PanelTools.bCheckColoreAreas")); //$NON-NLS-1$
		this.cbColorieVoronoi.setEnabled(false);
		
		this.cbAfficheGraphe.addItemListener(new CheckBoxesListener());
		this.cbAfficheGrapheDelaunay.addItemListener(new CheckBoxesListener());
		this.cbAfficheVoronoi.addItemListener(new CheckBoxesListener());
		
		this.cbAfficheVoronoi.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					
					PanelTools.this.cbColorieVoronoi.setEnabled(true);
					Debug.printDebug(Messages.getString("PanelTools.bCheckComputeVoronoi")); //$NON-NLS-1$
					RegionsDeVoronoi surfVoronoi = new RegionsDeVoronoi(PanelTools.this.parent.getPanelMainDrawActif().carte.getGraphe());
					surfVoronoi.run();
				}else{
					PanelTools.this.cbColorieVoronoi.setEnabled(false);
				}
			}});
		
		
//		@Override
//		public void stateChanged(ChangeEvent e) {
//			if( e.PanelTools.this.cbAfficheVoronoi.isSelected() ){
//				PanelTools.this.cbColorieVoronoi.setEnabled(true);
//				
//				Debug.printDebug(Messages.getString("PanelTools.bCheckComputeVoronoi")); //$NON-NLS-1$
//				RegionsDeVoronoi surfVoronoi = new RegionsDeVoronoi(PanelTools.this.parent.getPanelMainDrawActif().carte.getGraphe());
//				surfVoronoi.run();
//				//					surfVoronoi.pondereNoeudAleaRegion();
//				//					PanelTools.this.parent.panelActif.carte.setPolygonesDeVoronoi(surfVoronoi.getRegionsDeVoronoi());
//			} else
//				PanelTools.this.cbColorieVoronoi.setEnabled(false);
//		}
//		});
		
		this.cbAfficheGrapheNonDirige.addItemListener(new CheckBoxesListener());
		this.cbAfficheAgregation.addItemListener(new CheckBoxesListener());
		this.cbAfficheSousReseau.addItemListener(new CheckBoxesListener());
		this.cbAfficheImageFond.addItemListener(new CheckBoxesListener());
		this.cbAfficheLignesCentre.addItemListener(new CheckBoxesListener());
		this.cbAfficheEnveloppeConvexe.addItemListener(new CheckBoxesListener());
		this.cbColorieVoronoi.addItemListener(new CheckBoxesListener());
		
		this.cbColorieVoronoi.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if( PanelTools.this.cbColorieVoronoi.isSelected() ){
					RegionsDeVoronoi surfVoronoi = new RegionsDeVoronoi(PanelTools.this.parent.getPanelMainDrawActif().carte.getGraphe());
					surfVoronoi.run();
					//					surfVoronoi.pondereNoeudAleaRegion();
					//					PanelTools.this.parent.panelActif.carte.setPolygonesDeVoronoi(surfVoronoi.getRegionsDeVoronoi());
				}
			}
		});
		
		this.slZoom.setPaintTicks(true);
		this.slZoom.setPaintLabels(true);
		this.slZoom.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				
				double value = (double) source.getValue()/100.0;
				
				PanelTools.this.tZoom.setText(String.valueOf(value));
				ConstantesApplication.zoomValue = value;
				
			}
		});
		
		this.tZoom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int value = new Double(Double.valueOf(PanelTools.this.tZoom.getText())*100).intValue();
				PanelTools.this.slZoom.setValue(value);
				ConstantesApplication.zoomValue = Double.valueOf(PanelTools.this.tZoom.getText());
				
			}
		});
		
		JPanel localPanelPCC = new JPanel();
		JPanel localPanelVues = new JPanel();
		JPanel localPanelGeometry = new JPanel();
		JPanel localPanelOperations = new JPanel();
		JPanel localPanelCheckBoxes = new JPanel();
		JPanel localPanelAgregation = new JPanel();
		JPanel localPanelImageDeFond = new JPanel();
		JPanel localPanelEspace = new JPanel();
		JPanel localPanelEnveloppe = new JPanel();
		JPanel localPanelZoomLevel = new JPanel();
		
		localPanelPCC.setLayout(new FlowLayout(FlowLayout.LEFT));
		localPanelPCC.add(this.bPCC);
		localPanelPCC.add(this.bAllShortestPathFloydWarshall);
		localPanelPCC.add(this.bAllShortestPathOD);
		//		localPanelPCC.add(this.bSpanningTree);
		//		localPanelPCC.add(this.bCheminLogit);
		//		localPanelPCC.add(this.bChevelu);
		localPanelVues.setLayout(new FlowLayout(FlowLayout.LEFT));
		localPanelVues.add(this.viewList);
		localPanelVues.add(this.bOKViewList);
		localPanelGeometry.add(this.tGeometry);
		localPanelGeometry.add(this.cbGeometry);
		localPanelOperations.setLayout(new FlowLayout(FlowLayout.LEFT));
		localPanelOperations.add(this.bAgregate);
		localPanelOperations.add(this.bDesagregate);
		//		localPanelOperations.add(this.bAllShortestPaths);
		//		localPanelOperations.add(this.bAllShortestPathsDijkstra);
		//		localPanelOperations.add(this.bAllShortestPathOD);
		//		localPanelOperations.add(this.bAllShortestPathFloydWarshall);
		localPanelCheckBoxes.add(this.cbAfficheGraphe);
		localPanelCheckBoxes.setLayout(new FlowLayout(FlowLayout.LEFT));
		localPanelCheckBoxes.add(this.cbAfficheGraphe);
		//		localPanelCheckBoxes.add(this.cbAfficheGrapheDelaunay);
		localPanelCheckBoxes.add(this.cbAfficheVoronoi);
		localPanelCheckBoxes.add(this.cbAfficheGrapheNonDirige);
		localPanelAgregation.add(this.cbAfficheAgregation);
		localPanelAgregation.add(this.cbAfficheSousReseau);
		localPanelImageDeFond.add(this.cbAfficheImageFond);
		localPanelEspace.add(this.cbAfficheLignesCentre);
		localPanelEnveloppe.add(this.cbAfficheEnveloppeConvexe);
		localPanelEnveloppe.add(this.cbColorieVoronoi);
		localPanelZoomLevel.add(this.tZoomInfo);
		localPanelZoomLevel.add(this.tZoom);
		localPanelZoomLevel.add(this.slZoom);
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(localPanelPCC);
		add(localPanelVues);
		add(localPanelGeometry);
		add(localPanelOperations);
		add(localPanelCheckBoxes);
		add(localPanelAgregation);
		add(localPanelImageDeFond);
		add(localPanelEspace);
		add(localPanelEnveloppe);
		add(localPanelZoomLevel);
	}
	
	class CheckBoxesListener implements ItemListener {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			Object source = e.getItemSelectable();
			
			if (source == PanelTools.this.cbAfficheGraphe) {
				PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheGraphe = PanelTools.this.cbAfficheGraphe.isSelected();
			} else if (source == PanelTools.this.cbAfficheGrapheDelaunay) {
				PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheGrapheDelaunay = PanelTools.this.cbAfficheGrapheDelaunay.isSelected();
			} else if (source == PanelTools.this.cbAfficheVoronoi) {
				PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheVoronoi = PanelTools.this.cbAfficheVoronoi.isSelected();
			} else if (source == PanelTools.this.cbAfficheGrapheNonDirige) {
				PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheGrapheNonDirige = PanelTools.this.cbAfficheGrapheNonDirige.isSelected();
				PanelTools.this.parent.panelActif.carte.getGraphe().setGrapheChange();
			} else if (source == PanelTools.this.cbAfficheAgregation) {
				PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheAgregation = PanelTools.this.cbAfficheAgregation.isSelected();
			} else if (source == PanelTools.this.cbAfficheSousReseau) {
				PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheSousReseau = PanelTools.this.cbAfficheSousReseau.isSelected();
			} else if (source == PanelTools.this.cbAfficheImageFond) {
				PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheImageDeFond = PanelTools.this.cbAfficheImageFond.isSelected();
			} else if (source == PanelTools.this.cbAfficheLignesCentre) {
				PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheLigneCentre = PanelTools.this.cbAfficheLignesCentre.isSelected();
			} else if (source == PanelTools.this.cbAfficheEnveloppeConvexe) {
				PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheEnveloppeConvexe = PanelTools.this.cbAfficheEnveloppeConvexe.isSelected();
			} else if (source == PanelTools.this.cbColorieVoronoi) {
				PanelTools.this.parent.panelActif.carte.variablesDeCarte.afficheCouleurVoronoi = PanelTools.this.cbColorieVoronoi.isSelected();
			}
			PanelTools.this.parent.panelActif.repaint();
		}
	}
	
}