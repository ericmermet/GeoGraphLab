/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.bars;

import fr.ign.cogit.geographlab.ihm.MainProgressBar;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StatusBar extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
//	private MainWindow parent;
	
	Box internalStatusBar = new Box(BoxLayout.X_AXIS);
	
	JTextField dateField = new JTextField(20);
	JTextField messageField = new JTextField(80);
	JTextField memoryField = new JTextField(20);
	JTextField positionSouris = new JTextField(20);
	
	public MainProgressBar mainProgressBar = new MainProgressBar(this);
	
	public StatusBar(MainWindow parent) {
		super();
//		this.parent = parent;
		Box.createHorizontalBox();
		initialize();
		add(this.internalStatusBar);
	}
	
	private void initialize() {
		
		this.dateField.setBackground(Color.LIGHT_GRAY);
		this.dateField.setHorizontalAlignment(0);
		this.dateField.setText(new SimpleDateFormat("EEEE dd MMMM yyyy").format(new Date()));
		
		this.messageField.setBackground(Color.LIGHT_GRAY);
		this.messageField.setHorizontalAlignment(0);
		
		this.memoryField.setBackground(Color.LIGHT_GRAY);
		this.memoryField.setHorizontalAlignment(0);
		
		this.positionSouris.setBackground(Color.LIGHT_GRAY);
		this.positionSouris.setHorizontalAlignment(0);
		
		this.mainProgressBar.setSize(ConstantesApplication.tailleFenetreX / 2 / 10, 30);
		
		//Detection multi-écrans
		GraphicsEnvironment e  = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = e.getScreenDevices();
		
		if( devices.length == 1 ) {
			this.internalStatusBar.setPreferredSize(new Dimension(ConstantesApplication.tailleFenetreX, 20));
		}
		if( devices.length == 2 ) {
			this.internalStatusBar.setPreferredSize(new Dimension(ConstantesApplication.tailleFenetreX /2, 20));
		}
		
		this.internalStatusBar.add(this.dateField);
		this.internalStatusBar.add(this.messageField);
		this.internalStatusBar.add(this.memoryField);
		this.internalStatusBar.add(this.positionSouris);
		this.internalStatusBar.add(this.mainProgressBar);
	}
	
	public void changeMessageField(String text) {
		this.messageField.setText(text);
	}
	
	public void changeProgressBar(int value, int nbOp) {
		this.mainProgressBar.setValue(value, nbOp);
	}
	
	public void changePositionSouris(int posX, int posY) {
		this.positionSouris.setText(new String(posX + "," + -posY));
	}
	
	public void changeMemoryField(String text) {
		this.memoryField.setText(text);
	}
}
