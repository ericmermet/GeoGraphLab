/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.outils.legende;

import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Color currentColor;
	JButton button;
	JColorChooser colorChooser;
	JDialog dialog;
	protected static final String EDIT = "edit";
	
	public ColorEditor() {
		// Set up the editor (from the table's point of view),
		// which is a button.
		// This button brings up the color chooser dialog,
		// which is the editor from the user's point of view.
		this.button = new JButton();
		this.button.setActionCommand(EDIT);
		this.button.addActionListener(this);
		this.button.setBorderPainted(false);
		
		// Set up the dialog that the button brings up.
		this.colorChooser = new JColorChooser();
		this.dialog = JColorChooser.createDialog(this.button, "Pick a Color", true, // modal
				this.colorChooser, this, // OK button handler
				null); // no CANCEL button handler
	}
	
	/**
	 * Handles events from the editor button and from the dialog's OK button.
	 */
	public void actionPerformed(ActionEvent e) {
		if (EDIT.equals(e.getActionCommand())) {
			// The user has clicked the cell, so
			// bring up the dialog.
			this.button.setBackground(this.currentColor);
			this.colorChooser.setColor(this.currentColor);
			this.dialog.setVisible(true);
			
			// Make the renderer reappear.
			fireEditingStopped();
			
		} else { // User pressed dialog's "OK" button.
			this.currentColor = this.colorChooser.getColor();
		}
	}
	
	// Implement the one CellEditor method that AbstractCellEditor doesn't.
	public Object getCellEditorValue() {
		return this.currentColor;
	}
	
	// Implement the one method defined by TableCellEditor.
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.currentColor = (Color) value;
		return this.button;
	}
}