/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * InformationFrame.java in fr.irstea.adret.geographlab.plugins.mmi
 * 
 */
package fr.irstea.adret.geographlab.plugins.mmi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JLabel;

import fr.irstea.adret.geographlab.plugins.mmi.constants.ConstantesImagesIrstea;

/**
 * @author eric
 *
 */
public class InformationFrame extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InformationFrame dialog = new InformationFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public InformationFrame() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setLayout(new FlowLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		
		{
			JLabel lblLogoIrstea = new JLabel(new ImageIcon(getClass().getResource(ConstantesImagesIrstea.imgLogoIrstea_small)));
			lblLogoIrstea.setBounds(12, 492, 46, 46);
			this.contentPanel.add(lblLogoIrstea);
		}
		{
			JLabel lblGraphit = new JLabel(new ImageIcon(getClass().getResource(ConstantesImagesIrstea.imgLogoGraphit_small)));
			lblGraphit.setBounds(12, 492, 46, 46);
			this.contentPanel.add(lblGraphit);
		}
		{
			JTextArea txtrPluginDveloppPar = new JTextArea();
			txtrPluginDveloppPar.setText("Plugin développé par Graph-it pour Irstea Grenoble (2013)");
			txtrPluginDveloppPar.setEditable(false);
			this.contentPanel.add(txtrPluginDveloppPar);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						InformationFrame.this.setVisible(false);
					}
				});
				
			}
		}
		
	}
	
}