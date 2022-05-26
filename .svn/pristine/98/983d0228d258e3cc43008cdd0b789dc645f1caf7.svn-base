/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.listes;

import fr.ign.cogit.geographlab.ihm.MainWindow;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;

/**
 * TableRenderDemo is just like TableDemo, except that it explicitly initializes
 * column sizes and it uses a combo box as an editor for the Sport column.
 */
public class PanelEdges extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	boolean DEBUG = false;
	
	static JTable table;
	
	MainWindow parent;
	
	public static Object saveValueBeforeBeingChanged;
	
	public final static int nbColonnes = 3;
	
	public PanelEdges(MainWindow mainWindow) {
		super(new GridLayout(1, 0));
		
		this.parent = mainWindow;
		
		saveValueBeforeBeingChanged = new String();
		
		table = new JTable(new MyTableModel());
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (event.getValueIsAdjusting())
					return;
				int[] tabSelectedRows = table.getSelectedRows();
				
				PanelEdges.this.parent.panelActif.carte.getVueDuGraphe().unselectAll();
				
				for (int i = 0; i < tabSelectedRows.length; i++) {
					PanelEdges.this.parent.panelActif.carte.getVueDuGraphe().selectionObjet(table.getValueAt(tabSelectedRows[i], 0).toString());
				}
				PanelEdges.this.parent.panelActif.repaint();
				PanelEdges.table.repaint();
			}
		});
		
		table.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				String nouveauNom = null;
				Double nouvelleValeur = new Double(0.0);
				
				int row = e.getFirstRow();
				int column = e.getColumn();
				MyTableModel model = (MyTableModel) e.getSource();
				String columnName = model.getColumnName(column);
				Object data = model.getValueAt(row, column);
				
				if (e.getColumn() == 0 && table.isRowSelected(e.getFirstRow()) && saveValueBeforeBeingChanged != null) {
					// Changement du nom d'un arc
					nouveauNom = (String) data;
					
					if (nouveauNom.compareTo(saveValueBeforeBeingChanged.toString()) != 0) {
						
						int search = Collections.binarySearch(PanelEdges.this.parent.panelActif.carte.getGraphe().getNomDesArcs(), nouveauNom);
						
						int i = 0;
						if (search < 0) {
							// si pas de nom identique dans la collection alors
							// on change de noeuds
							PanelEdges.this.parent.panelActif.carte.getGraphe().getArc(PanelEdges.saveValueBeforeBeingChanged.toString()).setNom(nouveauNom);
						}
						
					}
				}
				if (e.getColumn() == 1 && table.isRowSelected(e.getFirstRow()) && saveValueBeforeBeingChanged != null) {
					// Changement du poids d'un noeud
					nouvelleValeur = new Double(Double.parseDouble((String) data));
					
					// si pas de nom identique dans la collection alors on
					// change de noeuds
					PanelEdges.this.parent.panelActif.carte.getGraphe().setPoidsArc(PanelEdges.this.parent.panelActif.carte.getGraphe().getArc(table.getValueAt(table.getSelectedRow(), 0).toString()), nouvelleValeur.doubleValue());
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
						PanelEdges.this.parent.panelActif.carte.getVueDuGraphe().removeNoeudGraphique(table.getValueAt(tabSelectedRows[i], 0).toString());
						PanelEdges.this.parent.panelActif.carte.getGraphe().delArc(table.getValueAt(tabSelectedRows[i], 0).toString());
					}
					PanelEdges.this.parent.panelActif.repaint();
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
		// table.setRowSorter(new TableRowSorter<TableModel>(table.getModel()));
		
		// Set up column sizes.
		initColumnSizes(table);
		
		// Add the scroll pane to this panel.
		add(scrollPane);
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
	
	private void initColumnSizes(JTable laTable) {
		MyTableModel model = (MyTableModel) table.getModel();
		TableColumn column = null;
		Component comp = null;
		int headerWidth = 0;
		int cellWidth = 0;
		Object[] longValues = model.longValues;
		TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
		
		for (int i = 0; i < nbColonnes; i++) {
			column = table.getColumnModel().getColumn(i);
			
			comp = headerRenderer.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
			headerWidth = comp.getPreferredSize().width;
			
			comp = table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(table, longValues[i], false, false, 0, i);
			cellWidth = comp.getPreferredSize().width;
			
			column.setPreferredWidth(Math.max(headerWidth, cellWidth));
		}
	}
	
	class MyTableModel extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;
		
		private String[] columnNames = { "Nom des aretes", "Poids", "Couleurs" };
		
		private Object[][] data = { { "		", "		", "		" } };
		
		public final Object[] longValues = { "Nom d'un arc qui est long et meme plus long que ca", "1000000", new Color(255, 255, 255) };
		
		public int getColumnCount() {
			return this.columnNames.length;
		}
		
		public int getRowCount() {
			return this.data.length;
		}
		
		@Override
		public String getColumnName(int col) {
			return this.columnNames[col];
		}
		
		public Object getValueAt(int row, int col) {
			return this.data[row][col];
		}
		
		public void setDataSize(int taille) {
			this.data = new Object[taille][nbColonnes];
		}
		
		// @Override
		// public Class getColumnClass(int c) {
		// return getValueAt(0, c).getClass();
		// }
		
		@Override
		public boolean isCellEditable(int row, int col) {
			return true;
		}
		
		@Override
		public void setValueAt(Object value, int row, int col) {
			if (PanelEdges.this.DEBUG) {
				System.out.println("Setttttting value at " + row + "," + col + " to " + value + " (an instance of " + value.getClass() + ")");
			}
			
			saveValueBeforeBeingChanged = table.getValueAt(row, col);
			
			// if( saveValueBeforeBeingChanged == null){
			// saveValueBeforeBeingChanged = new String("");
			// }
			
			this.data[row][col] = value;
			fireTableCellUpdated(row, col);
			
			if (PanelEdges.this.DEBUG) {
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
					System.out.print("  " + this.data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}
}