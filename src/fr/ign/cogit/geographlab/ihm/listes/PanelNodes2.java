/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.listes;

import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.customdockingframes.ColorDockable;
import fr.ign.cogit.geographlab.ihm.outils.legende.ColorEditor;
import fr.ign.cogit.geographlab.ihm.outils.legende.ColorRenderer;
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
import java.util.Collections;
import java.util.Set;

/**
 * TableRenderDemo is just like TableDemo, except that it explicitly initializes
 * column sizes and it uses a combo box as an editor for the Sport column.
 */
public class PanelNodes2 extends ColorDockable {
	
	private static final long serialVersionUID = 1L;
	
	boolean DEBUG = false;
	
	static JTable table;
	
	MainWindow parent;
	
	public static Object saveValueBeforeBeingChanged;
	
	Object[][] data;
	
	public PanelNodes2(MainWindow parent) {
		this( parent, "Liste des noeuds", Color.WHITE, 1.0f);
	}
	
	public PanelNodes2(MainWindow parent, String nom, Color couleur, float luminosite) {
		super( nom, couleur, luminosite );
		//Si bug voir constructeur avec super(new GridLayout(1, 0)); sur un JPanel
		
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
				int[] tabSelectedRows = table.getSelectedRows();
				
				PanelNodes2.this.parent.panelActif.carte.getVueDuGraphe().unselectAll();
				
				for (int i = 0; i < tabSelectedRows.length; i++) {
					PanelNodes2.this.parent.panelActif.carte.getVueDuGraphe().selectionObjet(table.getValueAt(tabSelectedRows[i], 0).toString());
				}
				PanelNodes2.this.parent.panelActif.repaint();
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
				
				// Modification du nom du noeud
				if (e.getColumn() == 0 && table.isRowSelected(e.getFirstRow()) && saveValueBeforeBeingChanged != null) {
					// Changement du nom d'un noeud
					nouveauNom = (String) dataM;
					
					if (nouveauNom.compareTo(saveValueBeforeBeingChanged.toString()) != 0) {
						
						int search = Collections.binarySearch(PanelNodes2.this.parent.panelActif.carte.getGraphe().getNomDesNoeuds(), nouveauNom);
						
						int i = 0;
						if (search < 0) {
							// si pas de nom identique dans la collection alors
							// on change de noeuds
							
							
							
							PanelNodes2.this.parent.panelActif.carte.getGraphe().getNoeud((String)PanelNodes2.saveValueBeforeBeingChanged).setNom(nouveauNom);
							PanelNodes2.this.parent.panelActif.carte.getGraphe().setGrapheChange();
							PanelNodes2.table.repaint();
						}
						
					}
					return;
				}
				
				// Modification de la couleur du noeud
				if (e.getColumn() == 3 && table.isRowSelected(e.getFirstRow()) && saveValueBeforeBeingChanged != null) {
					
					System.out.println((Color) dataM);
					
					System.out.println("Avant " + PanelNodes2.this.parent.panelActif.carte.getVueDuGraphe().getNoeudGraphique(model.getValueAt(row, 0).toString()) + " " + PanelNodes2.this.parent.panelActif.carte.getVueDuGraphe().getNoeudGraphique(
							model.getValueAt(row, 0).toString()).getColor());
					
					PanelNodes2.this.parent.panelActif.carte.getVueDuGraphe().getNoeudGraphique(model.getValueAt(row, 0).toString()).setColor((Color) dataM);
					
					System.out.println("Apres " + PanelNodes2.this.parent.panelActif.carte.getVueDuGraphe().getNoeudGraphique(model.getValueAt(row, 0).toString()) + " " + PanelNodes2.this.parent.panelActif.carte.getVueDuGraphe().getNoeudGraphique(
							model.getValueAt(row, 0).toString()).getColor());
					
					PanelNodes2.this.parent.panelActif.carte.getGraphe().setGrapheChange();
					PanelNodes2.table.repaint();
					
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
		
		JPanel localPanelTable = new JPanel();
		
		// Add the scroll pane to this panel.
		// setLayout(new FlowLayout(FlowLayout.LEFT));
		add(scrollPane);
		// add(localPanelButton);
	}
	
	public void updateNodesFromModel() {
		
		Set<NoeudGraphique> tousLesNoeuds = this.parent.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques();
		int nbrSommets = this.parent.panelActif.carte.getGraphe().getNoeuds().size();
		
		int i = 0;
		PanelNodes2.setModelDataSize(nbrSommets);
		if (nbrSommets != 0) {
			for (NoeudGraphique iterNoeud : tousLesNoeuds) {
				if( this.parent.panelActif.carte.getGraphe().containsVertex(iterNoeud.getNoeudTopologique()) ) {
					PanelNodes2.getJTable().setValueAt(iterNoeud.getNom(), i, 0);
					//				PanelNodes2.getJTable().setValueAt(iterNoeud.getNoeudTopologique().getValeurPourIndicateur(this.parent.panelActif.carte.getNom()), i, 1);
					PanelNodes2.getJTable().setValueAt(iterNoeud.getNoeudTopologique().getValeurPourIndicateur(this.parent.panelActif.carte.getNomIndicateurCourant()), i, 1);
					PanelNodes2.getJTable().setValueAt(new Double(Graphs.neighborListOf(this.parent.panelActif.carte.getGraphe(), iterNoeud.getNoeudTopologique()).size()), i, 2);
					PanelNodes2.getJTable().setValueAt(iterNoeud.getColor(), i, 3);
					i++;
				}
			}
		}
		PanelNodes2.table.repaint();
		
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
		
		private String[] columnNames = { "Nom des noeuds", "Alea", "Degre", "Couleurs" };
		
		// public final Object[] longValues = {new Color(255,255,255),
		// "Nom d'un arc qui est long et meme plus long que ca", "1000000"};
		
		public void initValues() {
			
			if (PanelNodes2.this.parent.panelActif != null) {
				updateNodesFromModel();
			}
			
		}
		
		public int getColumnCount() {
			return this.columnNames.length;
		}
		
		public int getRowCount() {
			return PanelNodes2.this.data.length;
		}
		
		@Override
		public String getColumnName(int col) {
			return this.columnNames[col];
		}
		
		@Override
		public Class getColumnClass(int c) {
			Object value=this.getValueAt(0,c);  
			return (value==null)?Object.class:value.getClass();
		}
		
		public Object getValueAt(int row, int col) {
			return PanelNodes2.this.data[row][col];
		}
		
		public void setDataSize(int taille) {
			PanelNodes2.this.data = new Object[taille][this.columnNames.length];
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
			if (PanelNodes2.this.DEBUG) {
				System.out.println("Setting value at " + row + "," + col + " to " + value + " (an instance of " + value.getClass() + ")");
			}
			
			saveValueBeforeBeingChanged = table.getValueAt(row, col);
			
			// if( saveValueBeforeBeingChanged == null){
			// saveValueBeforeBeingChanged = new String("");
			// }
			
			PanelNodes2.this.data[row][col] = value;
			fireTableCellUpdated(row, col);
			
			if (PanelNodes2.this.DEBUG) {
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
					System.out.print("  " + PanelNodes2.this.data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}
}