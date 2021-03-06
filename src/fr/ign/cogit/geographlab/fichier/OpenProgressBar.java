/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * OpenProgressBar.java in fr.ign.cogit.geographlab.fichier
 * 
 */
package fr.ign.cogit.geographlab.fichier;

/**
 * @author eric
 *
 */

import java.awt.*;
import javax.swing.*;

import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;

public class OpenProgressBar extends JFrame  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static int interval = 1000;
	int i;
	JLabel label, label2;
	JProgressBar pb;
	Timer timer;
	String fichier;
	int nbElements;
	public int cpt;
	
	public OpenProgressBar(int nbElements, String fichier)  {
		super("Ouverture fichier" + fichier);

		this.nbElements = nbElements;
		
		this.setEnabled(true);
		
//		JDialog jd = new JDialog();
//		jd.setModal(true);
		
		pb = new JProgressBar(0, this.nbElements);
		pb.setValue(0);
		pb.setStringPainted(true);
		
		this.fichier = fichier;
		label = new JLabel(this.fichier);
		
		label2 = new JLabel("__");
		
		JPanel panel = new JPanel();
		panel.add(pb);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.add(panel, BorderLayout.NORTH);
		panel1.add(label, BorderLayout.CENTER);
		panel1.add(label2, BorderLayout.SOUTH);
		panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.setContentPane(panel1);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLocation(ConstantesApplication.tailleFenetreX_div2-this.getWidth()/2, ConstantesApplication.tailleFenetreY_div2-this.getHeight()/2);
		
//		jd.add(pb);
//		jd.pack();
//		jd.setVisible(true);
		
	}
	
	public void remoteBar(int el, String elements) {
		
		pb.setValue(el);
		label2.setText(elements);
		
		if( i == this.nbElements-1 ) {
			//fin
			this.dispose();
		}
	}
	
}