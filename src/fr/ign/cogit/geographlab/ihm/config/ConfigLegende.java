/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.config;

import javax.swing.JPanel;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import fr.ign.cogit.geographlab.lang.Messages;

public class ConfigLegende extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jButtonCancel = null;
	private JButton jButtonOk = null;
	private JLabel jLabel = null;
	private JComboBox jComboBoxLegendType = null;
	private JLabel jLabel1 = null;
	private JComboBox jComboBoxNbClasses = null;
	
	/**
	 * @param owner
	 */
	public ConfigLegende(Frame owner) {
		super(owner);
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setTitle("Configurer la legende");
		this.setContentPane(getJContentPane());
		this.setModal(true);
		this.setVisible(true);
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (this.jContentPane == null) {
			this.jLabel1 = new JLabel();
			this.jLabel1.setBounds(new Rectangle(14, 15, 131, 15));
			this.jLabel1.setName("lTextTypeDeLegende");
			this.jLabel1.setText("Type de Legende :");
			this.jLabel = new JLabel();
			this.jLabel.setBounds(new Rectangle(12, 60, 138, 23));
			this.jLabel.setName("lTextNbClasses");
			this.jLabel.setText("Nombre de classes :");
			this.jContentPane = new JPanel();
			this.jContentPane.setLayout(null);
			this.jContentPane.add(getJButtonAnnuler(), null);
			this.jContentPane.add(getJButtonOk(), null);
			this.jContentPane.add(this.jLabel, null);
			this.jContentPane.add(getJComboBox(), null);
			this.jContentPane.add(this.jLabel1, null);
			this.jContentPane.add(getJComboBox1(), null);
		}
		return this.jContentPane;
	}
	
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonAnnuler() {
		if (this.jButtonCancel == null) {
			this.jButtonCancel = new JButton();
			this.jButtonCancel.setBounds(new Rectangle(189, 143, 85, 20));
			this.jButtonCancel.setText("Annuler");
		}
		return this.jButtonCancel;
	}
	
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonOk() {
		if (this.jButtonOk == null) {
			this.jButtonOk = new JButton();
			this.jButtonOk.setBounds(new Rectangle(95, 143, 85, 20));
			this.jButtonOk.setText("Ok");
			this.jButtonOk.setName("bOk");
		}
		return this.jButtonOk;
	}
	
	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (this.jComboBoxLegendType == null) {
			
			String[] petStrings = { Messages.getString("ConfigLegende.1"), Messages.getString("ConfigLegende.2") };// "Standard Deviation",
			// "Natural ",
			// "Rayon Proximal",
			// "Rayon Distal"};
			
			this.jComboBoxLegendType = new JComboBox(petStrings);
			this.jComboBoxLegendType.setBounds(new Rectangle(166, 64, 60, 12));
			this.jComboBoxLegendType.setName("cbSelectLegendType");
		}
		return this.jComboBoxLegendType;
	}
	
	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (this.jComboBoxNbClasses == null) {
			this.jComboBoxNbClasses = new JComboBox();
			this.jComboBoxNbClasses.setBounds(new Rectangle(166, 17, 60, 13));
			this.jComboBoxNbClasses.setName("cbTypeLegende");
		}
		return this.jComboBoxNbClasses;
	}
	
}
