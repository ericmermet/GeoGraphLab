/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.console;

import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.ihm.customdockingframes.ColorDockable;
import fr.ign.cogit.geographlab.interpreteur.Parseur;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Console extends ColorDockable implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	public MainWindow parent;
	private String toutLeTexte = "";
	private final static String newline = "\n";
	
	private JTextField textField;
	private JTextArea textArea;
	
	private Parseur parseur;
	
	private ArrayList<String> commandesMemorisees = new ArrayList<String>();
	private int indexParcourscommandes = 0;
	
	public Console(MainWindow parent) {
		this(parent, "Console", Color.WHITE, 1.0f);
	}
	
	public Console(MainWindow parent, String nom, Color couleur, float luminosite) {
		super( nom, couleur, luminosite );
		
		this.setLayout(new GridBagLayout());
		
		this.textField = new JTextField(20);
		this.textField.addActionListener(this);
		this.textField.addKeyListener(this);
		
		this.textArea = new JTextArea(5, 20);
		this.textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(this.textArea);
		setTexte("> Console GeoGraphLab " + ConstantesApplication.version + "- taper help pour l'aide");
		
		// Add Components to this panel.
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		add(this.textField, c);
		
		this.parent = parent;
		
		this.parseur = new Parseur(parent);
	}
	
	public void setTexte(String texte) {
		this.textArea.setText(texte);
	}
	
	public String getToutLeTexte() {
		return this.toutLeTexte;
	}
	
	public void setToutLeTexte(String toutLeTexte) {
		this.toutLeTexte = toutLeTexte;
	}
	
	public void addQueueTexteInConsole(String texte) {
		this.textField.setText(this.textField.getText() + texte + " ");
	}
	
	public void addNewLine(String nouveauTexte) {
		this.toutLeTexte += nouveauTexte + "\n";
		setTexte(this.toutLeTexte);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String text = this.textField.getText();
		this.textArea.append(text + newline);
		this.textField.selectAll();
		
		Console.this.addNewLine(">" + text);
		this.commandesMemorisees.add(text);
		this.indexParcourscommandes = this.commandesMemorisees.size() - 1;
		
		// Parser -> Interpreter
		this.parseur.parseInput(text);
		
		// Make sure the new text is visible, even if there
		// was a selection in the text area.
		this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
			
			case 38:
				this.indexParcourscommandes--;
				if (this.indexParcourscommandes < 0)
					this.indexParcourscommandes = 0;
				this.textField.setText(this.commandesMemorisees.get(this.indexParcourscommandes));
				break;
			
			case 40:
				this.indexParcourscommandes++;
				if (this.indexParcourscommandes > this.commandesMemorisees.size() - 1)
					this.indexParcourscommandes = this.commandesMemorisees.size() - 1;
				this.textField.setText(this.commandesMemorisees.get(this.indexParcourscommandes));
				break;
			default:
				break;
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
}