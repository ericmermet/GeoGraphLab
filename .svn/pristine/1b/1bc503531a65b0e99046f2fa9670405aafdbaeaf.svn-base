/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class ToolTipOnMainDraw extends JToolTip {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label;
	
	public ToolTipOnMainDraw() {
		super();
		setLayout(new BorderLayout());
		
		this.label = new JLabel();
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.label.setBorder(UIManager.getBorder("ToolTip.border"));
		
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(UIManager.getColor("ToolTip.background"));
		panel.add(this.label);
		
		add(panel);
	}
	
	public void setToolTip(String texte, Point position) {
		this.label.setText(texte);
		setVisible(true);
		setLocation(position);
	}
	
}