/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * CheckBoxesConstraintsListener.java in fr.irstea.adret.geographlab.plugins.ihm.listeners
 * 
 */
package fr.irstea.adret.geographlab.plugins.mmi.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

/**
 * @author eric
 *
 */
public class CheckBoxesConstraintsListener implements ItemListener {

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox check = (JCheckBox)e.getSource();
		String txt = check.getText();
		
	}
	
}