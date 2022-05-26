/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.exploration;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesImages;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

public class ToolBarExplo extends JToolBar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	PanelExplo parent;
	
	// Selection
	JButton bSelect, bMoveBlock, bDrag;
	Icon imgSelect, imgMove, imgDrag;
	
	// Ajout d'une carte
	JButton bAddMapLayer, bRemoveMapLayer;
	Icon imgAddMapLayer, imgRemoveMapLayer;
	
	// Ajout d'une operation sur l'espace
	JComboBox bEspace;
	Icon imgEspace, imgUnionEspace, imgSoustractionEspace, imgIntersectionEspace, imgExclusionEspace, imgComplementEspace;
	JButton bAddUnionEspace, bAddSoustractionEspace, bAddIntersectionEspace, bAddExclusionEspace, bAddComplementEspace;
	
	// Ajout d'une operation sur l'indicateur
	JComboBox bMesure;
	Icon imgMesure, imgAddition, imgAddition3, imgSoustraction, imgMultiplication, imgMultiplication3, imgDivision, imgFonctionUnaire, imgFonctionBinaire;
	JButton bAddAddition, bAddAddition3, bAddSoustraction, bAddMultiplication, bAddMultiplication3, bAddDivision, bAddFonctionUnaire, bAddFonctionBinaire;

	//Ajout d'une operation sur la vue
	JComboBox bVue;
	Icon imgVue, imgUnionVue, imgIntersectionVue, imgExclusionVue, imgUnionAvecConservationVue;
	JButton	bAddUnionVue, bAddIntersectionVue, bAddExclusionVue, bAddUnionAvecConservationVue;
	
	//Ajout d'une operation sur la legende
	JComboBox bLegende;
	Icon imgLegende, imgCrsmtCouleurCouleur, imgCrsmtCouleurTaille;
	JButton	bAddCrsmtCouleurCouleur, bAddCrsmtCouleurTaille;
	
	// Lien entre les blocs
	JButton bLink;
	Icon imgLink;
	
	public ToolBarExplo(PanelExplo parent) {
		super();
		this.parent = parent;
		initialize();
//		setBounds(10, 10, 100, 100);
//		setSize(10, 10);
	}
	
	private void initialize() {
		this.imgSelect = new ImageIcon(getClass().getResource(ConstantesImages.imgSelect));
		this.bSelect = new JButton(this.imgSelect);
		this.bSelect.setMaximumSize(new Dimension(36, 36));
		
		this.imgMove = new ImageIcon(getClass().getResource(ConstantesImages.imgMoveNode));
		this.bMoveBlock = new JButton(this.imgMove);
		this.bMoveBlock.setMaximumSize(new Dimension(36, 36));
		
		this.imgDrag = new ImageIcon(getClass().getResource(ConstantesImages.imgDrag));
		this.bDrag = new JButton(this.imgDrag);
		this.bDrag.setMaximumSize(new Dimension(36, 36));
		
		this.imgLink = new ImageIcon(getClass().getResource("/fr/ign/cogit/geographlab/images/lienBlocs.png"));
		this.bLink = new JButton(this.imgLink);
		this.bLink.setMaximumSize(new Dimension(36, 36));
		
		this.imgAddMapLayer = new ImageIcon(getClass().getResource("/fr/ign/cogit/geographlab/images/iconeBlocGraphique.png"));
		this.bAddMapLayer = new JButton(this.imgAddMapLayer);
		this.bAddMapLayer.setMaximumSize(new Dimension(36, 36));
		
		this.imgAddMapLayer = new ImageIcon(getClass().getResource("/fr/ign/cogit/geographlab/images/iconeBlocGraphique.png"));
		this.bAddMapLayer = new JButton(this.imgAddMapLayer);
		this.bAddMapLayer.setMaximumSize(new Dimension(36, 36));
		
		this.imgRemoveMapLayer = new ImageIcon(getClass().getResource("/fr/ign/cogit/geographlab/images/iconeRemoveBlocGraphique.png"));
		this.bRemoveMapLayer = new JButton(this.imgRemoveMapLayer);
		this.bRemoveMapLayer.setMaximumSize(new Dimension(36, 36));
		
		//Espace
		this.imgEspace = new ImageIcon();
		this.bEspace = new JComboBox();
		
		this.imgUnionEspace = new ImageIcon(getClass().getResource(ConstantesImages.imgUnionEspaceIcone));
		this.bAddUnionEspace = new JButton(this.imgUnionEspace);
		this.bAddUnionEspace.setMaximumSize(new Dimension(36, 36));

		this.imgSoustractionEspace = new ImageIcon(getClass().getResource(ConstantesImages.imgSoustractionEspaceIcone));
		this.bAddSoustractionEspace = new JButton(this.imgSoustractionEspace);
		this.bAddSoustractionEspace.setMaximumSize(new Dimension(36, 36));
		
		this.imgIntersectionEspace = new ImageIcon(getClass().getResource(ConstantesImages.imgIntersectionEspaceIcone));
		this.bAddIntersectionEspace = new JButton(this.imgIntersectionEspace);
		this.bAddIntersectionEspace.setMaximumSize(new Dimension(36, 36));
		
		this.imgExclusionEspace = new ImageIcon(getClass().getResource(ConstantesImages.imgExclusionEspaceIcone));
		this.bAddExclusionEspace = new JButton(this.imgExclusionEspace);
		this.bAddExclusionEspace.setMaximumSize(new Dimension(36, 36));
		
		this.imgComplementEspace = new ImageIcon(getClass().getResource(ConstantesImages.imgComplementEspaceIcone));
		this.bAddComplementEspace = new JButton(this.imgComplementEspace);
		this.bAddComplementEspace.setMaximumSize(new Dimension(36, 36));
		
		//Mesure
		this.imgMesure = new ImageIcon();
		this.bMesure = new JComboBox();
		
		this.imgAddition = new ImageIcon(getClass().getResource(ConstantesImages.imgAdditionIcone));
		this.bAddAddition = new JButton(this.imgAddition);
		this.bAddAddition.setMaximumSize(new Dimension(36, 36));
		
		this.imgAddition3 = new ImageIcon(getClass().getResource(ConstantesImages.imgAddition3Icone));
		this.bAddAddition3 = new JButton(this.imgAddition3);
		this.bAddAddition3.setMaximumSize(new Dimension(36, 36));
		
		this.imgSoustraction = new ImageIcon(getClass().getResource(ConstantesImages.imgSoustractionIcone));
		this.bAddSoustraction = new JButton(this.imgSoustraction);
		this.bAddSoustraction.setMaximumSize(new Dimension(36, 36));
		
		this.imgMultiplication = new ImageIcon(getClass().getResource(ConstantesImages.imgMultiplicationIcone));
		this.bAddMultiplication = new JButton(this.imgMultiplication);
		this.bAddMultiplication.setMaximumSize(new Dimension(36, 36));
		
		this.imgMultiplication3 = new ImageIcon(getClass().getResource(ConstantesImages.imgMultiplication3Icone));
		this.bAddMultiplication3 = new JButton(this.imgMultiplication3);
		this.bAddMultiplication3.setMaximumSize(new Dimension(36, 36));
		
		this.imgDivision = new ImageIcon(getClass().getResource(ConstantesImages.imgDivisionIcone));
		this.bAddDivision = new JButton(this.imgDivision);
		this.bAddDivision.setMaximumSize(new Dimension(36, 36));
		
		this.imgFonctionUnaire = new ImageIcon(getClass().getResource(ConstantesImages.imgFonctionUnaireIcone));
		this.bAddFonctionUnaire = new JButton(this.imgFonctionUnaire);
		this.bAddFonctionUnaire.setMaximumSize(new Dimension(36, 36));
		
		this.imgFonctionBinaire = new ImageIcon(getClass().getResource(ConstantesImages.imgFonctionBinaireIcone));
		this.bAddFonctionBinaire = new JButton(this.imgFonctionBinaire);
		this.bAddFonctionBinaire.setMaximumSize(new Dimension(36, 36));
		
		//Vue
		this.imgVue = new ImageIcon();
		this.bMesure = new JComboBox();
		
		this.imgUnionVue = new ImageIcon(getClass().getResource(ConstantesImages.imgUnionVueIcone));
		this.bAddUnionVue = new JButton(this.imgUnionVue);
		this.bAddUnionVue.setEnabled(false);
		this.bAddUnionVue.setMaximumSize(new Dimension(36, 36));
		
		this.imgIntersectionVue = new ImageIcon(getClass().getResource(ConstantesImages.imgIntersectionVueIcone));
		this.bAddIntersectionVue = new JButton(this.imgIntersectionVue);
		this.bAddIntersectionVue.setEnabled(false);
		this.bAddIntersectionVue.setMaximumSize(new Dimension(36, 36));
		
		this.imgExclusionVue = new ImageIcon(getClass().getResource(ConstantesImages.imgExclusionVueIcone));
		this.bAddExclusionVue = new JButton(this.imgExclusionVue);
		this.bAddExclusionVue.setEnabled(false);
		this.bAddExclusionVue.setMaximumSize(new Dimension(36, 36));
		
		this.imgUnionAvecConservationVue = new ImageIcon(getClass().getResource(ConstantesImages.imgUnionAvecConservationVueIcone));
		this.bAddUnionAvecConservationVue = new JButton(this.imgUnionAvecConservationVue);
		this.bAddUnionAvecConservationVue.setEnabled(false);
		this.bAddUnionAvecConservationVue.setMaximumSize(new Dimension(36, 36));
		
		//Legende
		this.imgLegende = new ImageIcon();
		this.bLegende = new JComboBox();
		
		this.imgCrsmtCouleurCouleur = new ImageIcon(getClass().getResource(ConstantesImages.imgCrsmtCouleurCouleurLegendeIcone));
		this.bAddCrsmtCouleurCouleur = new JButton(this.imgCrsmtCouleurCouleur);
		this.bAddCrsmtCouleurCouleur.setEnabled(false);
		this.bAddCrsmtCouleurCouleur.setMaximumSize(new Dimension(36, 36));
		
		this.imgCrsmtCouleurTaille = new ImageIcon(getClass().getResource(ConstantesImages.imgCrsmtCouleurTailleLegendeIcone));
		this.bAddCrsmtCouleurTaille = new JButton(this.imgCrsmtCouleurTaille);
		this.bAddCrsmtCouleurTaille.setEnabled(false);
		this.bAddCrsmtCouleurTaille.setMaximumSize(new Dimension(36, 36));
		
		
		this.bSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ToolBarExplo.this.parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.selection;
			}
		});
		
		this.bMoveBlock.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.moveBloc;
			}
		});
		
		this.bDrag.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ToolBarExplo.this.parent.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.drag;
			}
		});
		
		this.bLink.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.link;
			}
		});
		
		this.bAddMapLayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.drawMapLayer;
			}
		});
		
		this.bRemoveMapLayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (Carte iterBloc : ToolBarExplo.this.parent.parent.panelActif.couchesDeCartes.getCouches()) {
					if( iterBloc.isSelected() ) {
						iterBloc.setSelected(false);
						iterBloc.carteMere.setSelected(true);
						
//						ToolBarExplo.this.parent.parent.panelActif.panelExplo.getJPanel().operations.retirerUneOperation((BlocOperation) iterBloc.carteExplo.getConnecteurEntreeEspace());
//						ToolBarExplo.this.parent.parent.panelActif.panelExplo.getJPanel().operations.retirerUneOperation((BlocOperation) iterBloc.carteExplo.getConnecteurEntreeMesure());
//						ToolBarExplo.this.parent.parent.panelActif.panelExplo.getJPanel().operations.retirerUneOperation((BlocOperation) iterBloc.carteExplo.getConnecteurEntreeVue());
//						ToolBarExplo.this.parent.parent.panelActif.panelExplo.getJPanel().operations.retirerUneOperation((BlocOperation) iterBloc.carteExplo.getConnecteurEntreeLegende());
						
						ArrayList<BlocOperation> list = new ArrayList<BlocOperation>();
						list.addAll(ToolBarExplo.this.parent.parent.panelActif.panelExplo.getJPanel().operations.getOperations());
						
						for (BlocOperation iterOp : list) {
							
							for (BlocGraphique iterBlocsConnectes : iterOp.getToutesLesConnexionsBlocs()) {
								if( iterBlocsConnectes == iterBloc.carteExplo ) {
									ToolBarExplo.this.parent.parent.panelActif.panelExplo.getJPanel().operations.retirerUneOperation(iterOp);
									ToolBarExplo.this.parent.parent.panelActif.panelExplo.getJPanel();
									PanelExplo.connecteurs.removeAll(iterOp.connecteursFilaires);
									iterOp.connecteursFilaires.clear();
								}
							}
							
						}
						
						ToolBarExplo.this.parent.parent.panelLayer.updateLayersFromLayerControler();
						ToolBarExplo.this.parent.parent.panelActif.carte = iterBloc.carteMere;
						ToolBarExplo.this.parent.parent.panelActif.carte.getGraphe().setGrapheChange();
						ToolBarExplo.this.parent.parent.panelActif.couchesDeCartes.effacerUneCoucheCarte(iterBloc);
						break;
					}
					
				}
				
				ToolBarExplo.this.parent.parent.panelActif.repaint();
				ToolBarExplo.this.parent.parent.panelActif.panelExplo.getJPanel().repaint();
			}
		});
		
		// Espace
		this.bAddUnionEspace.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.unionEspaceBloc;
			}
		});
		
		this.bAddSoustractionEspace.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.soustractionEspaceBloc;
			}
		});

		this.bAddIntersectionEspace.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.intersectionEspaceBloc;
			}
		});

		this.bAddExclusionEspace.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.exclusionEspaceBloc;
			}
		});

		this.bAddComplementEspace.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.complementEspaceBloc;
			}
		});
		
		// Mesure
		this.bAddAddition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.additionBloc;
			}
		});
		
		this.bAddAddition3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.addition3Bloc;
			}
		});
		
		this.bAddSoustraction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.soustractionBloc;
			}
		});
		
		this.bAddMultiplication.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.multiplicationBloc;
			}
		});
		
		this.bAddMultiplication3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.multiplication3Bloc;
			}
		});
		
		this.bAddDivision.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.divisionBloc;
			}
		});
		
		this.bAddFonctionUnaire.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.fonctionUnaire;
			}
		});
		
		this.bAddFonctionBinaire.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.fonctionBinaire;
			}
		});
		
		this.bAddUnionVue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.unionVueBloc;
			}
		});
		
		this.bAddIntersectionVue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.intersectionVueBloc;
			}
		});
		
		this.bAddExclusionVue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.exclusionVueBloc;
			}
		});
		
		this.bAddUnionAvecConservationVue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.unionAvecConservationVueBloc;
			}
		});
		
		this.bAddCrsmtCouleurCouleur.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.crsmtCouleurCouleurLegendeBloc;
			}
		});
		
		this.bAddCrsmtCouleurTaille.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolBarExplo.this.parent.outilsActifs = ConstantesExploration.crsmtCouleurTailleLegendeBloc;
			}
		});
		
		add(this.bSelect);
		add(this.bMoveBlock);
		add(this.bDrag);
		
//		add(new JSeparator());
		
		add(this.bLink);
		add(this.bAddMapLayer);
		add(this.bRemoveMapLayer);
		
//		add(new JSeparator());
		
		add(this.bAddUnionEspace);
		add(this.bAddSoustractionEspace);
		add(this.bAddIntersectionEspace);
		add(this.bAddExclusionEspace);
		add(this.bAddComplementEspace);
		
//		add(new JSeparator());
		
//		add(this.bMesure);
		add(this.bAddAddition);
		add(this.bAddAddition3);
		add(this.bAddSoustraction);
		add(this.bAddMultiplication);
		add(this.bAddMultiplication3);
		add(this.bAddDivision);
		add(this.bAddFonctionUnaire);
		add(this.bAddFonctionBinaire);
		
//		add(new JSeparator());
		
		add(this.bAddUnionVue);
		add(this.bAddIntersectionVue);
		add(this.bAddExclusionVue);
		add(this.bAddUnionAvecConservationVue);
		
//		add(new JSeparator());
		
		add(this.bAddCrsmtCouleurCouleur);
		add(this.bAddCrsmtCouleurTaille);
		
	}
	
}