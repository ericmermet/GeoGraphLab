/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.listes;

import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.outils.legende.ColorEditor;
import fr.ign.cogit.geographlab.ihm.outils.legende.ColorRenderer;
import fr.ign.cogit.geographlab.ihm.outils.legende.PanelLegend;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import org.jgrapht.Graphs;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.Set;

/**
 * TableRenderDemo is just like TableDemo, except that it explicitly initializes
 * column sizes and it uses a combo box as an editor for the Sport column.
 */
public class PanelNodes extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	boolean DEBUG = false;
	
	static JTable table;
	
	MainWindow parent;
	
	Object[][] data;
	
	public static Object saveValueBeforeBeingChanged;
	
	public final static int nbColonnes = 4;
	
	public PanelNodes(MainWindow mainWindow) {
		super(new GridLayout(1, 0));
		
		this.parent = mainWindow;
		
		saveValueBeforeBeingChanged = new String();
		
		MyTableModel model = new MyTableModel();
		table = new JTable(model);
		model.initValues();
		
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		// table.setAutoCreateRowSorter(true);
		
		table.setDefaultRenderer(Color.class, new ColorRenderer(true));
		table.setDefaultEditor(Color.class, new ColorEditor());
		
		// Selection dans la table et appel de la methode de selection d'un
		// objet graphique
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (event.getValueIsAdjusting())
					return;
				int[] tabSelectedRows = table.getSelectedRows();
				
				PanelNodes.this.parent.panelActif.carte.getVueDuGraphe().unselectAll();
				
				for (int i = 0; i < tabSelectedRows.length; i++) {
					PanelNodes.this.parent.panelActif.carte.getVueDuGraphe().selectionObjet(table.getValueAt(tabSelectedRows[i], 0).toString());
				}
				PanelNodes.this.parent.panelActif.repaint();
			}
		});
		
		// Modification dans la table des proprietes d'un objet
		table.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				String nouveauNom = null;
				
				int row = e.getFirstRow();
				int column = e.getColumn();
				MyTableModel model = (MyTableModel) e.getSource();
				String columnName = model.getColumnName(column);
				Object dataM = model.getValueAt(row, column);
				
				// Modification du nom du noeud
				if (e.getColumn() == 0 && table.isRowSelected(e.getFirstRow()) && saveValueBeforeBeingChanged != null) {
					// Changement du nom d'un noeud
					nouveauNom = (String) dataM;
					
					if (nouveauNom.compareTo(saveValueBeforeBeingChanged.toString()) != 0) {
						
						int search = Collections.binarySearch(PanelNodes.this.parent.panelActif.carte.getGraphe().getNomDesNoeuds(), nouveauNom);
						
						int i = 0;
						if (search < 0) {
							// si pas de nom identique dans la collection alors
							// on change de noeuds
							PanelNodes.this.parent.panelActif.carte.getGraphe().getNoeud(PanelNodes.saveValueBeforeBeingChanged.toString()).setNom(nouveauNom);
							PanelNodes.this.parent.panelActif.carte.getGraphe().setGrapheChange();
							PanelNodes.table.repaint();
						}
						
					}
					return;
				}
				// Modification de la couleur du nom
				if (e.getColumn() == 3 && table.isRowSelected(e.getFirstRow()) && saveValueBeforeBeingChanged != null) {
					
					PanelNodes.this.parent.panelActif.carte.getVueDuGraphe().getNoeudGraphique(model.getValueAt(row, 0).toString()).setColor((Color) dataM);
					PanelNodes.this.parent.panelActif.carte.getGraphe().setGrapheChange();
					PanelNodes.table.repaint();
					
					return;
				}
			}
		});
		
		table.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 127) {
					// Suppression d'un noeud et des arcs inferents
					int[] tabSelectedRows = table.getSelectedRows();
					for (int i = 0; i < tabSelectedRows.length; i++) {
						PanelNodes.this.parent.panelActif.carte.getVueDuGraphe().removeNoeudGraphique(table.getValueAt(tabSelectedRows[i], 0).toString());
						PanelNodes.this.parent.panelActif.carte.getGraphe().delNoeud(table.getValueAt(tabSelectedRows[i], 0).toString());
					}
					PanelNodes.this.parent.panelActif.carte.getGraphe().setGrapheChange();
					PanelNodes.this.parent.panelActif.repaint();
					PanelNodes.table.repaint();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		
		// table.setAutoCreateRowSorter(true);
		
		// Set up column sizes.
		// initColumnSizes(table);
		
		// Add the scroll pane to this panel.
		add(scrollPane);
	}
	
	public void updateNodesFromGraph() {
		
		Set<NoeudGraphique> tousLesNoeuds = this.parent.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques();
		int nbrSommets = tousLesNoeuds.size();
		
		// ((MyTableModel)table.getModel()).setDataSize(nbrSommets);
		
		int i = 0;
		PanelNodes.setModelDataSize(nbrSommets);
		for (NoeudGraphique iterNoeud : tousLesNoeuds) {
			PanelNodes.getJTable().setValueAt(iterNoeud.getNom(), i, 0);
			PanelNodes.getJTable().setValueAt(new Double(iterNoeud.getNoeudTopologique().getPonderation()), i, 1);
			PanelNodes.getJTable().setValueAt(new Double(Graphs.neighborListOf(this.parent.panelActif.carte.getGraphe(), iterNoeud.getNoeudTopologique()).size()), i, 2);
			PanelNodes.getJTable().setValueAt(new Color(150, 51, 31), i, 3);
			i++;
		}
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
	
	// private void initColumnSizes(JTable laTable) {
	// MyTableModel model = (MyTableModel)laTable.getModel();
	// TableColumn column = null;
	// Component comp = null;
	// int headerWidth = 0;
	// int cellWidth = 0;
	// Object[] longValues = model.longValues;
	// TableCellRenderer headerRenderer =
	// laTable.getTableHeader().getDefaultRenderer();
	//
	// for (int i = 0; i < nbColonnes; i++) {
	// column = laTable.getColumnModel().getColumn(i);
	//
	// comp = headerRenderer.getTableCellRendererComponent(
	// null, column.getHeaderValue(),
	// false, false, 0, 0);
	// headerWidth = comp.getPreferredSize().width;
	//
	// comp = laTable.getDefaultRenderer(model.getColumnClass(i)).
	// getTableCellRendererComponent(
	// laTable, longValues[i],
	// false, false, 0, i);
	// cellWidth = comp.getPreferredSize().width;
	//
	// column.setPreferredWidth(Math.max(headerWidth, cellWidth));
	// }
	// }
	
	class MyTableModel extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;
		
		private String[] columnNames = { "Nom des noeuds", "Alea", "Degre", "Couleurs" };
		
		// private Object[][] data = { {"		", "		", "		" , "		"} };
		
		// public final Object[] longValues =
		// {"Nom d'un noeud qui est long et meme plus long que ca", "1000000",
		// "1000000", new Color(255,255,255)};
		
		public void initValues() {
			
			setDataSize(0);
			
			if (PanelLegend.parent != null) {
				updateNodesFromGraph();
			}
			
			// PanelNodes.this.data = new Object[1][4];
			// PanelNodes.this.data[0][0] = new String("");
			// PanelNodes.this.data[0][1] = new String("");
			// PanelNodes.this.data[0][2] = new String("");
			// PanelNodes.this.data[0][3] = Color.WHITE;
		}
		
		public int getColumnCount() {
			return this.columnNames.length;
		}
		
		public int getRowCount() {
			return PanelNodes.this.data.length;
		}
		
		@Override
		public String getColumnName(int col) {
			return this.columnNames[col];
		}
		
		public Object getValueAt(int row, int col) {
			return PanelNodes.this.data[row][col];
		}
		
		public void setDataSize(int taille) {
			PanelNodes.this.data = new Object[taille][nbColonnes];
		}
		
		@Override
		public boolean isCellEditable(int row, int col) {
			switch (col) {
				case 0:
				case 1:
				case 3:
					return true;
				case 2:
				default:
					return false;
			}
		}
		
		@Override
		public void setValueAt(Object value, int row, int col) {
			if (PanelNodes.this.DEBUG) {
				System.out.println("Setttttting value at " + row + "," + col + " to " + value + " (an instance of " + value.getClass() + ")");
			}
			
			saveValueBeforeBeingChanged = table.getValueAt(row, col);
			
			PanelNodes.this.data[row][col] = value;
			fireTableCellUpdated(row, col);
			
			if (PanelNodes.this.DEBUG) {
				System.out.println("New value of data:");
				printDebugData();
			}
		}
		
		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();
			
			for (int i = 0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j = 0; j < numCols; j++) {
					System.out.print("  " + PanelNodes.this.data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}
}