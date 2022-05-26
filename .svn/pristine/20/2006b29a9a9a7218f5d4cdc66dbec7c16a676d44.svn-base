/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * MenuBar_Irstea.java in fr.irstea.adret.geographlab.plugins.ihm.bars
 * 
 */
package fr.irstea.adret.geographlab.plugins.mmi.bars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.irstea.adret.geographlab.plugins.lang.Messages_Irstea;
import fr.irstea.adret.geographlab.plugins.mmi.InformationFrame;
import fr.irstea.adret.geographlab.plugins.mmi.MainFrame_Irstea;

/**
 * @author eric
 *
 */
public class MenuBar_Irstea extends JMenuBar{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainWindow mainWindow;
	
	private JMenu mDecisionHelp = new JMenu(Messages_Irstea.getString("MenuBar_Irstea.mDecisionHelp"), true); //$NON-NLS-1$
	private JMenuItem mLoadXML = new JMenuItem(Messages_Irstea.getString("MenuBar_Irstea.mLoadXML")); //$NON-NLS-1$
	private JMenuItem mHelpIrstea = new JMenuItem(Messages_Irstea.getString("MenuBar_Irstea.mHelpIrstea")); //$NON-NLS-1$
	
	/**
	 * 
	 */
	public MenuBar_Irstea(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		
//		add(this.mDecisionHelp);
		this.mDecisionHelp.add(this.mLoadXML);
		this.mDecisionHelp.add(this.mHelpIrstea);
		
		this.mLoadXML.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainFrame_Irstea frameIrstea = new MainFrame_Irstea(MenuBar_Irstea.this.mainWindow);
				frameIrstea.setVisible(true);
			}
		});
		
		this.mHelpIrstea.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InformationFrame informationFrame = new InformationFrame();
				informationFrame.setVisible(true);
			}
		});
		
	}
	
}