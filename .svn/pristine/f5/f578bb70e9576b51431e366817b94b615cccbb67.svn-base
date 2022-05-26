/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.listes;

import fr.ign.cogit.geographlab.ihm.MainWindow;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class PanelODbak extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainWindow parent;
	
	static Object[][] dataODssNames = new Object[0][0];
	static Object[] columnNamesTODsVertices = { "Nom des OD", "Poids", "Cheminement" };
	private static JTable tNamesODs;// = new JTable(new
	
	// MyTableModel());//JTable(dataODssNames,
	// columnNamesTODsVertices);
	
	public static JTable getTNodes() {
		return tNamesODs;
	}
	
	public static void setTNodes(JTable tNomODs) {
		tNamesODs = tNomODs;
	}
	
	public PanelODbak(MainWindow parent) {
		super(new GridLayout(1, 0));
		this.parent = parent;
		
		tNamesODs = new JTable(new MyTableModel());
		
		initColumnSizes(tNamesODs);
		
		TableColumn colonneCheminement = tNamesODs.getColumnModel().getColumn(2);
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("PCC");
		comboBox.addItem("Utilisateur");
		comboBox.addItem("None");
		
		colonneCheminement.setCellEditor(new DefaultCellEditor(comboBox));
		
		// colonneCheminement.setCellRenderer(new
		// MyComboBoxRenderer(valuesCombo));
		
		// colonneCheminement.get
		
		// tNamesODs.setColumnModel(columnModel);
		
		// DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		// renderer.setToolTipText("Click");
		// colonneCheminement.setCellRenderer(renderer);
		
		tNamesODs.setAutoCreateRowSorter(true);
		
		JScrollPane localPanelTableau = new JScrollPane(tNamesODs);
		add(localPanelTableau);
	}
	
	private void initColumnSizes(JTable table) {
		MyTableModel model = (MyTableModel) table.getModel();
		TableColumn column = null;
		Component comp = null;
		int headerWidth = 0;
		int cellWidth = 0;
		Object[] longValues = model.longValues;
		TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
		
		for (int i = 0; i < 3; i++) {
			column = table.getColumnModel().getColumn(i);
			
			comp = headerRenderer.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
			headerWidth = comp.getPreferredSize().width;
			
			comp = table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(table, longValues[i], false, false, 0, i);
			cellWidth = comp.getPreferredSize().width;
			
			// if (DEBUG) {
			// System.out.println("Initializing width of column "
			// + i + ". "
			// + "headerWidth = " + headerWidth
			// + "; cellWidth = " + cellWidth);
			// }
			
			column.setPreferredWidth(Math.max(headerWidth, cellWidth));
		}
	}
	
	class MyTableModel extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;
		
		private String[] columnNamesTODsVertices = { "Nom des OD", "Poids", "Cheminement" };
		
		private Object[][] dataODssNames = new Object[0][3];
		
		public final Object[] longValues = { "Sharon", "Campione", "None of the above", new Integer(20), Boolean.TRUE };
		
		@Override
		public int getColumnCount() {
			return this.columnNamesTODsVertices.length;
		}
		
		@Override
		public int getRowCount() {
			return this.dataODssNames.length;
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return this.dataODssNames[rowIndex][columnIndex];
		}
		
		@Override
		public void setValueAt(Object value, int row, int col) {
			// if (DEBUG) {
			// System.out.println("Setting value at " + row + "," + col
			// + " to " + value
			// + " (an instance of "
			// + value.getClass() + ")");
			// }
			
			this.dataODssNames[row][col] = value;
			fireTableCellUpdated(row, col);
			
			// if (DEBUG) {
			// System.out.println("New value of data:");
			// printDebugData();
			// }
		}
	}
}