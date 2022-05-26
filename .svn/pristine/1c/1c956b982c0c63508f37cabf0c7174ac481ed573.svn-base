/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.listes;

import java.awt.Color;
import java.awt.GridLayout;

import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.customdockingframes.ColorDockable;
import fr.ign.cogit.geographlab.lang.Messages;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PanelTopologicalIndicators extends ColorDockable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainWindow parent;
	
	public static Object[][] dataInfosTopos = new Object[8][2];
	private static Object[] columnNames = { Messages.getString("PanelTopologicalIndicators.0"), Messages.getString("PanelTopologicalIndicators.1") }; //$NON-NLS-1$ //$NON-NLS-2$
	private static JTable tInfosTopogiques = new JTable(dataInfosTopos, columnNames);
	
	// private JButton button = new JButton("Test");
	
	public static JTable getTInfosTopogiques() {
		return tInfosTopogiques;
	}
	
	public static void setTInfosTopogiques(JTable infosTopogiques) {
		tInfosTopogiques = infosTopogiques;
	}
	
	public PanelTopologicalIndicators(MainWindow parent ) {
		this( parent, Messages.getString("PanelTopologicalIndicators.2"), Color.WHITE, 1.0f); //$NON-NLS-1$
	}
	
	public PanelTopologicalIndicators(MainWindow parent, String nom, Color couleur, float luminosite) {
		super( nom, couleur, luminosite );
		this.parent = parent;
		
		JScrollPane localPanelTableau = new JScrollPane(tInfosTopogiques);
		
		// localPanelTableau.add(tInfosTopogiques);
		
		add(localPanelTableau);
		// changeTopologicalValues();
		
		// GrapheListener gE = new GrapheListener(){
		//
		// @Override
		// public boolean grapheChange() {
		// System.out.println("graphe change alors tableau change");
		// changeTopologicalValues();
		// return false;
		// }
		// };
	}
	
}