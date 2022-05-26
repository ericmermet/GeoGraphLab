/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * GeoGraphLab_DF.java in fr.ign.cogit.geographlab.ihm.exemples
 * 
 */
package fr.ign.cogit.geographlab;

import javax.swing.SwingUtilities;

import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;

/**
 * @author eric
 *
 */
public class GeoGraphLab {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// UIManager.setLookAndFeel(new LookAndFeelDockingTheme());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				MainWindow frame = new MainWindow(ConstantesApplication.tailleFenetreX, ConstantesApplication.tailleFenetreY);
				frame.setVisible( true );
				
			}
			
		});
	}
}