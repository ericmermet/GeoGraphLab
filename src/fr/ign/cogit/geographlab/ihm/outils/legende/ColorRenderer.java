/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.outils.legende;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class ColorRenderer extends JLabel implements TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Border unselectedBorder = null;
	Border selectedBorder = null;
	boolean isBordered = true;
	
	public ColorRenderer(boolean isBordered) {
		this.isBordered = isBordered;
		setOpaque(true); // MUST do this for background to show up.
	}
	
	public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
		Color newColor = (Color) color;
		setBackground(newColor);
		if (this.isBordered) {
			if (isSelected) {
				if (this.selectedBorder == null) {
					this.selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground());
				}
				setBorder(this.selectedBorder);
			} else {
				if (this.unselectedBorder == null) {
					this.unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground());
				}
				setBorder(this.unselectedBorder);
			}
		}
		
		// setToolTipText("RGB value: " + newColor.getRed() + ", "
		// + newColor.getGreen() + ", "
		// + newColor.getBlue());
		return this;
	}
}