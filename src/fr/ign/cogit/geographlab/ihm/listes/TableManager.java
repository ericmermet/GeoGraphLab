/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.listes;

import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableManager extends JTable {
	/**
     * 
     */
	private static final long serialVersionUID = -7147894423903333181L;
	
	// public static Object[][] data = new Object[0][0];
	// public static Object[] columnNames = {"Nom des noeuds", "Alea", "Degre",
	// "Centralite"};
	// private static JTable tNamesVertices;
	
	public TableManager(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		this.add(new JScrollBar(JScrollBar.VERTICAL));
		this.setAutoscrolls(true);
		this.getSelectionModel().addListSelectionListener(new RowListener());
	}
	
	// public void addElement(Object elem){
	//    	
	// }
	
	public void addNewRow(int line, Object[][] data) {
		int addLine = getRowCount();
		if (line != -1)
			addLine = line + 1;
	}
	
	private class RowListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (event.getValueIsAdjusting()) {
				return;
			}
			// System.out.println(event.getFirstIndex() + " " +
			// event.getLastIndex() + "ROW SELECTION EVENT. " +
			// getValueAt(getSelectedRow(), 0));
			
			// Mettre un filtre pour les selections multiples
			
			// for(GraphicObject itGO: Application.getGraphicsObjects() ){
			// //Pour tous les objets graphiques
			// if( itGO.getType() == Constantes.typeVertex &
			// itGO.getName().compareTo(getValueAt(getSelectedRow(),
			// 0).toString()) == 0 ){ //Pour les noeuds
			// itGO.setSelected(true);
			// }else{
			// itGO.setSelected(false);
			// }
			// }
			// HashSet<OD> tteslesOD = Pcc.getToutesLesODs();
			//    		
			// for (OD iterOD : tteslesOD) {
			// if( iterOD.getName().compareTo(getValueAt(getSelectedRow(),
			// 0).toString()) == 0 ){
			// iterOD.setVisible(true);
			// break;
			// }else{
			// // iterOD.setVisible(false);
			// }
			// }
			// Application.panelMainDraw.repaint();
		}
	}
}