/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.outils;

import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.customdockingframes.ColorDockable;
import fr.ign.cogit.geographlab.ihm.outils.legende.ColorEditor;
import fr.ign.cogit.geographlab.ihm.outils.legende.ColorRenderer;
import fr.ign.cogit.geographlab.lang.Messages;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * TableRenderDemo is just like TableDemo, except that it explicitly initializes
 * column sizes and it uses a combo box as an editor for the Sport column.
 */
public class PanelLayer extends ColorDockable {
	
	boolean DEBUG = false;
	
	static JTable table;
	
	MainWindow parent;
	
	public static Object saveValueBeforeBeingChanged;
	
	Object[][] data;
	
	public PanelLayer(MainWindow parent) {
		this(parent, Messages.getString("PanelLayer.tListEdges"), Color.WHITE, 1.0f); //$NON-NLS-1$
	}
	
	public PanelLayer(MainWindow parent, String nom, Color couleur, float luminosite) {
		super( nom, couleur, luminosite );
		
//		this.setLayout(new GridLayout(0,1));	??? si bug ?
		this.parent = parent;
		
		saveValueBeforeBeingChanged = new String();
		
		MyTableModel model = new MyTableModel();
		table = new JTable(model);
		model.initValues();
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (event.getValueIsAdjusting())
					return;
			}
		});
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				JTable tableInEvent = (JTable) e.getSource();
				Point p = e.getPoint();
				if (e.getClickCount() == 2) {
					// System.out.println("table.isEditing = " +
					// tableInEvent.isEditing());
					int row = tableInEvent.rowAtPoint(p);
					int col = tableInEvent.columnAtPoint(p);
					
					PanelLayer.this.parent.panelActif.carte = PanelLayer.this.parent.panelActif.couchesDeCartes.getCarte((String) tableInEvent.getValueAt(row, 1));
					PanelLayer.this.parent.panelActif.couchesDeCartes.setVisible(PanelLayer.this.parent.panelActif.carte);
					PanelLayer.this.parent.panelActif.carte.getGraphe().setGrapheChange();
					PanelLayer.this.parent.panelActif.repaint();
						
					PanelLayer.this.parent.panelActif.panelExplo.getJPanel().repaint();
					
				}
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		table.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				String nouveauNom = null;
				
				int row = e.getFirstRow();
				int column = e.getColumn();
				MyTableModel model = (MyTableModel) e.getSource();
				String columnName = model.getColumnName(column);
				Object dataM = model.getValueAt(row, column);
				
				// Modification du nom de la couche
				if (e.getColumn() == 1 && table.isRowSelected(e.getFirstRow()) && saveValueBeforeBeingChanged != null) {
					// Changement du nom d'une couche
					nouveauNom = (String) dataM;
					
					if (nouveauNom.compareTo(saveValueBeforeBeingChanged.toString()) != 0) {
						
						// TODO CHOPPER LE NOM DE LA COUCHE AVANT DE L'EFFACER
						// ET RETROUVER LA CARTE
						
						Carte carteActive = PanelLayer.this.parent.panelActif.couchesDeCartes.getCarte(saveValueBeforeBeingChanged.toString());
						carteActive.setNom(nouveauNom);
						PanelLayer.this.parent.panelActif.panelExplo.getJPanel().repaint();
					}
					return;
				}
				
				// Modification de la couleur de la carte
				if (e.getColumn() == 3 && table.isRowSelected(e.getFirstRow()) && saveValueBeforeBeingChanged != null) {
					
					// System.out.println((Color)dataM);
					
					PanelLayer.this.parent.panelActif.carte.setColorLayer((Color) dataM);
					
					PanelLayer.this.parent.panelActif.carte.getGraphe().setGrapheChange();
					PanelLayer.table.repaint();
					
					PanelLayer.this.parent.panelActif.panelExplo.getJPanel().repaint();
					
					PanelLayer.this.parent.panelActif.repaint();
					
					return;
				}
			}
		});
		
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		
		// table.setAutoCreateRowSorter(true);
		
		// Set up column sizes.
		// initColumnSizes(table);
		
		table.setDefaultRenderer(Color.class, new ColorRenderer(true));
		table.setDefaultEditor(Color.class, new ColorEditor());
		
		// JPanel localPanelTable = new JPanel();
		
		// Add the scroll pane to this panel.
		// setLayout(new FlowLayout(FlowLayout.LEFT));
		add(scrollPane);
		// add(localPanelButton);
	}
	
	public void updateLayersFromLayerControler() {
		
		ArrayList<Carte> mesCartes = this.parent.panelActif.couchesDeCartes.getCouches();
		int nbrCouches = mesCartes.size();
		
		PanelLayer.setModelDataSize(nbrCouches);
		if (nbrCouches != 0) {
			for (int j = 0; j < nbrCouches; j++) {
				PanelLayer.getJTable().setValueAt(j, j, 0);
				PanelLayer.getJTable().setValueAt(mesCartes.get(j).getNom(), j, 1);
				PanelLayer.getJTable().setValueAt(new Boolean(mesCartes.get(j).isSelected()), j, 2);
				PanelLayer.getJTable().setValueAt(mesCartes.get(j).getColorLayer(), j, 3);
			}
		}
		PanelLayer.getJTable().repaint();
		
		this.parent.panelActif.panelExplo.getJPanel().repaint();
		
	}
	
	public static JTable getJTable() {
		return table;
	}
	
	public static void setJTable(JTable tNomODs) {
		table = tNomODs;
	}
	
	public static void setModelDataSize(int taille) {
		((MyTableModel) table.getModel()).setDataSize(taille);
	}
	
	class MyTableModel extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;
		
		private String[] columnNames = { Messages.getString("PanelLayer.1"), Messages.getString("PanelLayer.tName"), Messages.getString("PanelLayer.tVisible"), Messages.getString("PanelLayer.tColors") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
		// public final Object[] longValues = {new Color(255,255,255),
		// "Nom d'un arc qui est long et meme plus long que ca", "1000000"};
		
		public void initValues() {
			
			if (PanelLayer.this.parent.panelActif != null) {
				updateLayersFromLayerControler();
			}
			
		}
		
		public int getColumnCount() {
			return this.columnNames.length;
		}
		
		public int getRowCount() {
			return PanelLayer.this.data.length;
		}
		
		@Override
		public String getColumnName(int col) {
			return this.columnNames[col];
		}
		
		@Override
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}
		
		public Object getValueAt(int row, int col) {
			return PanelLayer.this.data[row][col];
		}
		
		public void setDataSize(int taille) {
			PanelLayer.this.data = new Object[taille][this.columnNames.length];
		}
		
		// @Override
		// public Class getColumnClass(int c) {
		// return getValueAt(0, c).getClass();
		// }
		
		@Override
		public boolean isCellEditable(int row, int col) {
			if (col == 0)
				return false;
			if (col == 2)
				return false;
			return true;
		}
		
		@Override
		public void setValueAt(Object value, int row, int col) {
			if (PanelLayer.this.DEBUG) {
				System.out.println(Messages.getString("PanelLayer.tInfo11") + row + Messages.getString("PanelLayer.6") + col + Messages.getString("PanelLayer.7") + value + Messages.getString("PanelLayer.8") + value.getClass() + Messages.getString("PanelLayer.9")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			}
			
			saveValueBeforeBeingChanged = table.getValueAt(row, col);
			
			PanelLayer.this.data[row][col] = value;
			fireTableCellUpdated(row, col);
			
			if (PanelLayer.this.DEBUG) {
				System.out.println(Messages.getString("PanelLayer.10")); //$NON-NLS-1$
				printDebugData();
			}
		}
		
		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();
			
			for (int i = 0; i < numRows; i++) {
				System.out.print(Messages.getString("PanelLayer.11") + i + Messages.getString("PanelLayer.12")); //$NON-NLS-1$ //$NON-NLS-2$
				for (int j = 0; j < numCols; j++) {
					System.out.print(Messages.getString("PanelLayer.13") + PanelLayer.this.data[i][j]); //$NON-NLS-1$
				}
				System.out.println();
			}
			System.out.println(Messages.getString("PanelLayer.14")); //$NON-NLS-1$
		}
	}
}
