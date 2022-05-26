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
import fr.ign.cogit.geographlab.visu.ArcGraphique;

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
import java.util.Collections;
import java.util.Set;

public class PanelEdges2 extends ColorDockable {
	
	private static final long serialVersionUID = 1L;
	
	boolean DEBUG = false;
	
	static JTable table;
	
	MainWindow parent;
	
	public static Object saveValueBeforeBeingChanged;
	
	Object[][] data;
	
	public PanelEdges2(MainWindow parent) {
		this( parent, "Liste des arcs", Color.WHITE, 1.0f);
	}
	
	public PanelEdges2(MainWindow parent, String nom, Color couleur, float luminosite) {
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
				
				PanelEdges2.this.parent.panelActif.carte.getVueDuGraphe().unselectAll();
				
				for (int i = 0; i < tabSelectedRows.length; i++) {
					PanelEdges2.this.parent.panelActif.carte.getVueDuGraphe().selectionObjet(table.getValueAt(tabSelectedRows[i], 0).toString());
				}
				PanelEdges2.this.parent.panelActif.repaint();
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
						
						int search = Collections.binarySearch(PanelEdges2.this.parent.panelActif.carte.getGraphe().getNomDesArcs(), nouveauNom);
						
						int i = 0;
						if (search < 0) {
							// si pas de nom identique dans la collection alors
							// on change de noeuds
							PanelEdges2.this.parent.panelActif.carte.getGraphe().getArc(PanelEdges.saveValueBeforeBeingChanged.toString()).setNom(nouveauNom);
						}
						
					}
				}
				if (e.getColumn() == 1 && table.isRowSelected(e.getFirstRow()) && saveValueBeforeBeingChanged != null) {
					// Changement du poids d'un arc
					nouvelleValeur = (Double) data;
					
					// si pas de nom identique dans la collection alors on
					// change de noeuds
					PanelEdges2.this.parent.panelActif.carte.getGraphe().setPoidsArc(PanelEdges2.this.parent.panelActif.carte.getGraphe().getArc(table.getValueAt(table.getSelectedRow(), 0).toString()), nouvelleValeur.doubleValue());
				}
				// Modification de la couleur du nom
				if (e.getColumn() == 2 && table.isRowSelected(e.getFirstRow()) && saveValueBeforeBeingChanged != null) {
					
					System.out.println((Color) data);
					
					System.out.println("Avant " + PanelEdges2.this.parent.panelActif.carte.getVueDuGraphe().getArcGraphique(model.getValueAt(row, 0).toString()) + " " + PanelEdges2.this.parent.panelActif.carte.getVueDuGraphe().getArcGraphique(
							model.getValueAt(row, 0).toString()).getColor());
					
					PanelEdges2.this.parent.panelActif.carte.getVueDuGraphe().getArcGraphique(model.getValueAt(row, 0).toString()).setColor((Color) data);
					
					System.out.println("Apres " + PanelEdges2.this.parent.panelActif.carte.getVueDuGraphe().getArcGraphique(model.getValueAt(row, 0).toString()) + " " + PanelEdges2.this.parent.panelActif.carte.getVueDuGraphe().getArcGraphique(
							model.getValueAt(row, 0).toString()).getColor());
					
					PanelEdges2.this.parent.panelActif.carte.getGraphe().setGrapheChange();
					PanelEdges2.table.repaint();
					
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
		
//		JPanel localPanelTable = new JPanel();
		
		// Add the scroll pane to this panel.
		// setLayout(new FlowLayout(FlowLayout.LEFT));
		add(scrollPane);
		// add(localPanelButton);
	}
	
	public void updateEdgesFromModel() {
		
		Set<ArcGraphique> tousLesArcs = this.parent.panelActif.carte.getVueDuGraphe().getArcsGraphiques();
		int nbrArcs = tousLesArcs.size();
		
		int i = 0;
		PanelEdges2.setModelDataSize(nbrArcs);
		if (nbrArcs != 0) {
			for (ArcGraphique iterArc : tousLesArcs) {
				PanelEdges2.getJTable().setValueAt(iterArc.getNom(), i, 0);
//				if(iterArc.getArcTopologique().getPoids() == 1.0 )
//					System.out.println("break in update panel " + iterArc.getNom());
//				PanelEdges2.getJTable().setValueAt(new Double(iterArc.getArcTopologique().getPoids()), i, 1);
				PanelEdges2.getJTable().setValueAt(iterArc.getArcTopologique().getValeurPourIndicateur(this.parent.panelActif.carte.getNomIndicateurCourant()), i, 1);
				PanelEdges2.getJTable().setValueAt(iterArc.getColor(), i, 2);
				i++;
			}
		}
		PanelEdges2.table.repaint();
		
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
		
		private String[] columnNames = { "Nom des arcs", "Poids", "Couleurs" };
		
		// public final Object[] longValues = {new Color(255,255,255),
		// "Nom d'un arc qui est long et meme plus long que ca", "1000000"};
		
		public void initValues() {
			
			if (PanelEdges2.this.parent.panelActif != null) {
				updateEdgesFromModel();
			}
			
		}
		
		public int getColumnCount() {
			return this.columnNames.length;
		}
		
		public int getRowCount() {
			return PanelEdges2.this.data.length;
		}
		
		@Override
		public String getColumnName(int col) {
			return this.columnNames[col];
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Class getColumnClass(int c) {
			Object value=this.getValueAt(0,c);  
			return (value==null)?Object.class:value.getClass();
		}
		
		public Object getValueAt(int row, int col) {
			return PanelEdges2.this.data[row][col];
		}
		
		public void setDataSize(int taille) {
			PanelEdges2.this.data = new Object[taille][this.columnNames.length];
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
			if (PanelEdges2.this.DEBUG) {
				System.out.println("Setting value at " + row + "," + col + " to " + value + " (an instance of " + value.getClass() + ")");
			}
			
			saveValueBeforeBeingChanged = table.getValueAt(row, col);
			
			// if( saveValueBeforeBeingChanged == null){
			// saveValueBeforeBeingChanged = new String("");
			// }
			
			PanelEdges2.this.data[row][col] = value;
			fireTableCellUpdated(row, col);
			
			if (PanelEdges2.this.DEBUG) {
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
					System.out.print("  " + PanelEdges2.this.data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}
}