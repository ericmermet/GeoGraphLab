/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm;

import fr.ign.cogit.geographlab.ihm.bars.MenuBar;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.ihm.listes.PanelEdges2;
import fr.ign.cogit.geographlab.ihm.listes.PanelNodes2;
import fr.ign.cogit.geographlab.lang.Messages;
import fr.ign.cogit.geographlab.test.Debug;
import fr.ign.cogit.geographlab.visu.ArcGraphique;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class PopUpClicDroitPanelPrincipal extends JPopupMenu {
	
	ObjetGraphique objetSelectionne;
	PanelMainDraw panel;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PopUpClicDroitPanelPrincipal(PanelMainDraw panel, MouseEvent me) {
		super();
		
		this.panel = panel;
		
		createPopUp();
		
		// Add listener to the text area so the popup menu can come up.
		MouseListener popupListener = new PopupListener(this);
		panel.addMouseListener(popupListener);
		
	}
	
	protected void createPopUp() {
		
		JCheckBox menuItemCheckBox = new JCheckBox();
		JMenuItem menuItemRename = new JMenuItem();
		JMenuItem menuItemDelete = new JMenuItem();
		JMenuItem menuItemOpenBackGround = new JMenuItem();
		JMenuItem menuItemChangeColor = new JMenuItem();
		JMenuItem menuItemSetPoids = new JMenuItem();
		
		PopUpClicDroitPanelPrincipal.this.removeAll();
		
		if (PopUpClicDroitPanelPrincipal.this.panel.listOfSelectedObjects.size() == 0) {
			menuItemOpenBackGround = new JMenuItem(Messages.getString("PopUpClicDroitPanelPrincipal.0")); //$NON-NLS-1$
			PopUpClicDroitPanelPrincipal.this.add(menuItemOpenBackGround);
		}
		
		PopUpClicDroitPanelPrincipal.this.objetSelectionne = new ObjetGraphique();
		
		for (ObjetGraphique iterObj : PopUpClicDroitPanelPrincipal.this.panel.listOfSelectedObjects) {
			PopUpClicDroitPanelPrincipal.this.objetSelectionne = iterObj;
			// Un seul objet
		}
		
		if (PopUpClicDroitPanelPrincipal.this.panel.listOfSelectedObjects.size() == 1) {
			
			if (PopUpClicDroitPanelPrincipal.this.objetSelectionne.getType() == ConstantesApplication.typeVertex) {
				
				menuItemChangeColor = new JMenuItem(Messages.getString("PopUpClicDroitPanelPrincipal.1")); //$NON-NLS-1$
				PopUpClicDroitPanelPrincipal.this.add(menuItemChangeColor);
				menuItemRename = new JMenuItem(Messages.getString("PopUpClicDroitPanelPrincipal.2")); //$NON-NLS-1$
				PopUpClicDroitPanelPrincipal.this.add(menuItemRename);
				menuItemDelete = new JMenuItem(Messages.getString("PopUpClicDroitPanelPrincipal.3")); //$NON-NLS-1$
				PopUpClicDroitPanelPrincipal.this.add(menuItemDelete);
				menuItemCheckBox = new JCheckBox(Messages.getString("PopUpClicDroitPanelPrincipal.4")); //$NON-NLS-1$
				menuItemCheckBox.setSelected(PopUpClicDroitPanelPrincipal.this.objetSelectionne.isVisible());
				PopUpClicDroitPanelPrincipal.this.add(menuItemCheckBox);
				menuItemSetPoids = new JMenuItem(Messages.getString("PopUpClicDroitPanelPrincipal.5")); //$NON-NLS-1$
				PopUpClicDroitPanelPrincipal.this.add(menuItemSetPoids);
			} else if (PopUpClicDroitPanelPrincipal.this.objetSelectionne.getType() == ConstantesApplication.typeEdge) {
				menuItemChangeColor = new JMenuItem(Messages.getString("PopUpClicDroitPanelPrincipal.6")); //$NON-NLS-1$
				PopUpClicDroitPanelPrincipal.this.add(menuItemChangeColor);
				menuItemRename = new JMenuItem(Messages.getString("PopUpClicDroitPanelPrincipal.7")); //$NON-NLS-1$
				PopUpClicDroitPanelPrincipal.this.add(menuItemRename);
				menuItemDelete = new JMenuItem(Messages.getString("PopUpClicDroitPanelPrincipal.8")); //$NON-NLS-1$
				PopUpClicDroitPanelPrincipal.this.add(menuItemDelete);
				menuItemCheckBox = new JCheckBox(Messages.getString("PopUpClicDroitPanelPrincipal.9")); //$NON-NLS-1$
				menuItemCheckBox.setSelected(PopUpClicDroitPanelPrincipal.this.objetSelectionne.isVisible());
				PopUpClicDroitPanelPrincipal.this.add(menuItemCheckBox);
				menuItemSetPoids = new JMenuItem(Messages.getString("PopUpClicDroitPanelPrincipal.10")); //$NON-NLS-1$
				PopUpClicDroitPanelPrincipal.this.add(menuItemSetPoids);
			} else if (PopUpClicDroitPanelPrincipal.this.objetSelectionne.getType() == ConstantesApplication.typeMetaVertex) {
				// TODO
			}
		} else {
			menuItemChangeColor = new JMenuItem(Messages.getString("PopUpClicDroitPanelPrincipal.11")); //$NON-NLS-1$
			PopUpClicDroitPanelPrincipal.this.add(menuItemChangeColor);
		}
		
		menuItemChangeColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String texte = new String(Messages.getString("PopUpClicDroitPanelPrincipal.12")); //$NON-NLS-1$
				
				Color newColor = JColorChooser.showDialog(PopUpClicDroitPanelPrincipal.this, texte, PopUpClicDroitPanelPrincipal.this.objetSelectionne.getColor());
				
				for (ObjetGraphique iterObjGraphique : PopUpClicDroitPanelPrincipal.this.panel.listOfSelectedObjects) {
					iterObjGraphique.setColor(newColor);
				}
				
				// PopUpClicDroitPanelPrincipal.this.objetSelectionne.setColor(newColor);
				
				PopUpClicDroitPanelPrincipal.this.panel.carte.getGraphe().setGrapheChange();
			}
			
		});
		
		menuItemRename.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				final JDialog dialoglSetNom = new JDialog();
				
				dialoglSetNom.setName(Messages.getString("PopUpClicDroitPanelPrincipal.13")); //$NON-NLS-1$
				dialoglSetNom.setModal(true);
				
				Point mainPanelLocation = PopUpClicDroitPanelPrincipal.this.panel.getLocationOnScreen();
				int xDialogLocation = mainPanelLocation.x / 2;
				int yDialogLocation = mainPanelLocation.y / 2;
				
				dialoglSetNom.setLocation(xDialogLocation, yDialogLocation);
				dialoglSetNom.setSize(250, 100);
				dialoglSetNom.setResizable(false);
				
				JPanel panelTexte = new JPanel();
				final JTextField nouveauNom = new JTextField(PopUpClicDroitPanelPrincipal.this.objetSelectionne.getNom());
				nouveauNom.setSize(200, 25);
				panelTexte.setLayout(new FlowLayout(FlowLayout.CENTER));
				panelTexte.add(nouveauNom);
				
				JPanel panelBoutons = new JPanel();
				JButton bOK = new JButton(Messages.getString("PopUpClicDroitPanelPrincipal.14")); //$NON-NLS-1$
				bOK.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent aebOk) {
						if (PopUpClicDroitPanelPrincipal.this.objetSelectionne.getType() == ConstantesApplication.typeVertex) {
							((NoeudGraphique) PopUpClicDroitPanelPrincipal.this.objetSelectionne).getNoeudTopologique().setNom(nouveauNom.getText());
						} else if (PopUpClicDroitPanelPrincipal.this.objetSelectionne.getType() == ConstantesApplication.typeEdge) {
							((ArcGraphique) PopUpClicDroitPanelPrincipal.this.objetSelectionne).getArcTopologique().setNom(nouveauNom.getText());
						}
						
						PopUpClicDroitPanelPrincipal.this.panel.carte.getGraphe().setGrapheChange();
						dialoglSetNom.setVisible(false);
					}
				});
				JButton bCancel = new JButton(Messages.getString("PopUpClicDroitPanelPrincipal.15")); //$NON-NLS-1$
				bCancel.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent aebCancel) {
						dialoglSetNom.setVisible(false);
					}
				});
				panelBoutons.setLayout(new FlowLayout(FlowLayout.CENTER));
				panelBoutons.add(bOK);
				panelBoutons.add(bCancel);
				
				dialoglSetNom.setLayout(new FlowLayout(FlowLayout.CENTER));
				dialoglSetNom.add(panelTexte);
				dialoglSetNom.add(panelBoutons);
				
				dialoglSetNom.setVisible(true);
				
			}
		});
		
		menuItemSetPoids.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				final JDialog dialoglSetNom = new JDialog();
				
				dialoglSetNom.setName(Messages.getString("PopUpClicDroitPanelPrincipal.16")); //$NON-NLS-1$
				dialoglSetNom.setModal(true);
				
				Point mainPanelLocation = PopUpClicDroitPanelPrincipal.this.panel.getLocationOnScreen();
				int xDialogLocation = mainPanelLocation.x / 2;
				int yDialogLocation = mainPanelLocation.y / 2;
				
				dialoglSetNom.setLocation(xDialogLocation, yDialogLocation);
				dialoglSetNom.setSize(250, 100);
				dialoglSetNom.setResizable(false);
				
				JPanel panelTexte = new JPanel();
				final JTextField nouveauPoids = new JTextField( Messages.getString("PopUpClicDroitPanelPrincipal.17") + ((ArcGraphique)PopUpClicDroitPanelPrincipal.this.objetSelectionne).getArcTopologique().getPoids()); //$NON-NLS-1$
				nouveauPoids.setSize(200, 25);
				panelTexte.setLayout(new FlowLayout(FlowLayout.CENTER));
				panelTexte.add(nouveauPoids);
				
				JPanel panelBoutons = new JPanel();
				JButton bOK = new JButton(Messages.getString("PopUpClicDroitPanelPrincipal.18")); //$NON-NLS-1$
				bOK.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent aebOk) {
						if (PopUpClicDroitPanelPrincipal.this.objetSelectionne.getType() == ConstantesApplication.typeEdge) {
							
							Double valeur = new Double(nouveauPoids.getText());
							
							((ArcGraphique)PopUpClicDroitPanelPrincipal.this.objetSelectionne).getArcTopologique().setPoidsDistance(valeur);
//							((ArcGraphique)PopUpClicDroitPanelPrincipal.this.objetSelectionne).getArcTopologique().setPoidsTemps(valeur);
							(((ArcGraphique)PopUpClicDroitPanelPrincipal.this.objetSelectionne).getArcTopologique()).setIndicateurValeur(PopUpClicDroitPanelPrincipal.this.panel.carte.getNomIndicateurCourant(), valeur);
							PopUpClicDroitPanelPrincipal.this.panel.carte.getGraphe().setPoidsArc(((ArcGraphique)PopUpClicDroitPanelPrincipal.this.objetSelectionne).getArcTopologique(), valeur);

						}
						
						PopUpClicDroitPanelPrincipal.this.panel.carte.getGraphe().setGrapheChange();
						dialoglSetNom.setVisible(false);
					}
				});
				JButton bCancel = new JButton(Messages.getString("PopUpClicDroitPanelPrincipal.19")); //$NON-NLS-1$
				bCancel.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent aebCancel) {
						dialoglSetNom.setVisible(false);
					}
				});
				panelBoutons.setLayout(new FlowLayout(FlowLayout.CENTER));
				panelBoutons.add(bOK);
				panelBoutons.add(bCancel);
				
				dialoglSetNom.setLayout(new FlowLayout(FlowLayout.CENTER));
				dialoglSetNom.add(panelTexte);
				dialoglSetNom.add(panelBoutons);
				
				dialoglSetNom.setVisible(true);
				
			}
			
		});
		
		menuItemDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch (PopUpClicDroitPanelPrincipal.this.objetSelectionne.getType()) {
					case ConstantesApplication.typeVertex:
						PopUpClicDroitPanelPrincipal.this.panel.carte.getVueDuGraphe().removeNoeudGraphique(PopUpClicDroitPanelPrincipal.this.objetSelectionne.getNom());
						PopUpClicDroitPanelPrincipal.this.panel.carte.getGraphe().delNoeud(PopUpClicDroitPanelPrincipal.this.objetSelectionne.getNom());
						break;
					case ConstantesApplication.typeEdge:
						PopUpClicDroitPanelPrincipal.this.panel.carte.getVueDuGraphe().removeArcGraphique(PopUpClicDroitPanelPrincipal.this.objetSelectionne.getNom());
						PopUpClicDroitPanelPrincipal.this.panel.carte.getGraphe().delArc(PopUpClicDroitPanelPrincipal.this.objetSelectionne.getNom());
						break;
					default:
						break;
				}
				
				PopUpClicDroitPanelPrincipal.this.panel.carte.getGraphe().setGrapheChange();
				PopUpClicDroitPanelPrincipal.this.panel.repaint();
				PanelNodes2.getJTable().repaint();
				PanelEdges2.getJTable().repaint();
			}
		});
		
		menuItemOpenBackGround.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser inFileDlg = new JFileChooser(System.getProperty(Messages.getString("PopUpClicDroitPanelPrincipal.20"))); //$NON-NLS-1$
				int returnVal = inFileDlg.showOpenDialog(PopUpClicDroitPanelPrincipal.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String browseDir = inFileDlg.getCurrentDirectory().toString();
					Debug.printDebug(browseDir);
					String inFile = null;
					inFile = inFileDlg.getSelectedFile().toString();
					
					PopUpClicDroitPanelPrincipal.this.panel.backGroundImage = new ImageIcon(inFile).getImage();
					
					ConstantesApplication.afficheImageDeFond = true;
					
					PopUpClicDroitPanelPrincipal.this.panel.parent.panelTools.cbAfficheImageFond.setSelected(true);
				}
			}
			
		});
		
	}
	
	class PopupListener extends MouseAdapter {
		JPopupMenu popup;
		
		PopupListener(JPopupMenu popupMenu) {
			this.popup = popupMenu;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			createPopUp();
			maybeShowPopup(e);
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}
		
		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				this.popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}
	
}
