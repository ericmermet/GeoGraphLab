/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * MainFrame_Irstea.java in fr.irstea.adret.geographlab.plugins.ihm
 * 
 */
package fr.irstea.adret.geographlab.plugins.mmi;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.test.Debug;
import fr.irstea.adret.geographlab.plugins.file.DecisionSupportOpenShape;
import fr.irstea.adret.geographlab.plugins.lang.Messages_Irstea;
import fr.irstea.adret.geographlab.plugins.mmi.constants.ConstantesImagesIrstea;
import fr.irstea.adret.geographlab.plugins.model.Indicator;
import fr.irstea.adret.geographlab.plugins.model.Operation;

import javax.swing.JCheckBox;

/**
 * @author eric
 *
 */
public class MainFrame_Irstea extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	MainWindow mainWindow;
	
	JTextField tFileXML;
	JTextField tShapeFile;
	
	private static JTextArea textArea;
	
	private static ScrollPane scrollpane = new ScrollPane();
	
	private JPanel pCheckBoxesConstraints;
	private JPanel pCheckBoxesOperations;
	
	private ArrayList<JCheckBox> listCheckBoxesConstraints = new ArrayList<JCheckBox>();
	private ArrayList<JCheckBox> listCheckBoxesOperations = new ArrayList<JCheckBox>();
	
	ArrayList<Indicator> listIndicators = new ArrayList<Indicator>();
	ArrayList<Operation> listOperations = new ArrayList<Operation>();
	
	String inFileShape;
	
	private static Scanner scanner;
	
	private static Document jdomDocument;
	static Element racine;
	
	ArrayList<Indicator> activeIndicators = new ArrayList<Indicator>();
	ArrayList<Operation> activeOperations= new ArrayList<Operation>();
	
	/**
	 * Create the panel
	 */
	public MainFrame_Irstea(MainWindow mainWindow) {
		
		this.mainWindow = mainWindow;
		
		this.setSize(1210, 575);
		this.setLocation(	ConstantesApplication.tailleFenetreX_div2- this.getWidth()/2,
							ConstantesApplication.tailleFenetreY_div2-this.getHeight()/2);
		getContentPane().setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(400, 12, 800, 526);
		getContentPane().add(textArea);
		
		scrollpane.setBounds(400, 12, 800, 526);
		scrollpane.add(textArea);
		getContentPane().add(scrollpane);
		
		JButton bCancel = new JButton("Annuler");
		bCancel.setBounds(271, 503, 117, 25);
		getContentPane().add(bCancel);
		bCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainFrame_Irstea.this.dispose();
			}
		});
		
		JButton bOk = new JButton("Ok");
		bOk.setBounds(142, 503, 117, 25);
		getContentPane().add(bOk);
		bOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DecisionSupportOpenShape dsop = new DecisionSupportOpenShape(MainFrame_Irstea.this.mainWindow, MainFrame_Irstea.this.inFileShape,
						MainFrame_Irstea.this.listIndicators, MainFrame_Irstea.this.activeOperations);
				dsop.run();
				MainFrame_Irstea.this.setVisible(false);
			}
		});
		
		JLabel lblNewLabel = new JLabel("Ouvrir XML ...");
		lblNewLabel.setBounds(12, 12, 112, 15);
		getContentPane().add(lblNewLabel);
		
		JButton bOpenXML = new JButton("...");
		bOpenXML.setBounds(130, 7, 30, 25);
		getContentPane().add(bOpenXML);
		bOpenXML.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Debug.printDebug(Messages_Irstea.getString("MenuBar_Irstea.debugTextOpenXML")); //$NON-NLS-1$
				
				JFileChooser inFileDlg = new JFileChooser(System.getProperty(Messages_Irstea.getString("MenuBar_Irstea.userDir"))); //$NON-NLS-1$
				
				inFileDlg.setFileFilter(new FileNameExtensionFilter(Messages_Irstea.getString("MenuBar_Irstea.textWindow"), Messages_Irstea.getString("MenuBar_Irstea.extensionXml"))); //$NON-NLS-1$ //$NON-NLS-2$
//				inFileDlg.setFileFilter(new FileNameExtensionFilter("Fichier HDR", "xml")); //$NON-NLS-1$ //$NON-NLS-2$
				int returnVal = inFileDlg.showOpenDialog(MainFrame_Irstea.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String browseDir = inFileDlg.getCurrentDirectory().toString();
					Debug.printDebug(browseDir);
					
					String inFile = null;
					inFile = inFileDlg.getSelectedFile().toString();
					inFileDlg.setCurrentDirectory(new File(inFile));
					//						fop.ouvrirFichierShape(browseDir, inFile);
					try {
						readXMLFile(inFile);
						MainFrame_Irstea.this.tFileXML.setText(inFile);
						
						parseXMLFile(inFile);
						
						MainFrame_Irstea.this.tFileXML.setBackground(Color.GREEN);
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (JDOMException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					Debug.printDebug(Messages_Irstea.getString("MenuBar_Irstea.debugTextDisplay")); //$NON-NLS-1$
				}
				
			}
		});
		
		this.tFileXML = new JTextField();
		this.tFileXML.setBounds(172, 10, 216, 19);
		this.tFileXML.setBackground(Color.RED);
		getContentPane().add(this.tFileXML);
		this.tFileXML.setColumns(10);
		
		JButton bOpenShape = new JButton("...");
		bOpenShape.setBounds(130, 49, 30, 25);
		getContentPane().add(bOpenShape);
		bOpenShape.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Debug.printDebug(Messages_Irstea.getString("MenuBar_Irstea.debugTextOpenSHP")); //$NON-NLS-1$
				
				JFileChooser inFileDlg = new JFileChooser(System.getProperty(Messages_Irstea.getString("MenuBar_Irstea.userDir"))); //$NON-NLS-1$
//				jfileChooser.setFileFilter(new FileNameExtensionFilter("Fichier HDR", "hdr"));
				inFileDlg.setFileFilter(new FileNameExtensionFilter(Messages_Irstea.getString("MenuBar_Irstea.textWindowShape"), Messages_Irstea.getString("MenuBar_Irstea.extensionShape"))); //$NON-NLS-1$ //$NON-NLS-2$
//				inFileDlg.setFileFilter(new FileNameExtensionFilter("Fichier HDR", "shp")); //$NON-NLS-1$ //$NON-NLS-2$
				int returnVal = inFileDlg.showOpenDialog(MainFrame_Irstea.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String browseDir = inFileDlg.getCurrentDirectory().toString();
					Debug.printDebug(browseDir);
					
					String inFile = null;
					inFile = inFileDlg.getSelectedFile().toString();
					inFileDlg.setCurrentDirectory(new File(inFile));
					//						fop.ouvrirFichierShape(browseDir, inFile);
					
					//Save File Shape
					MainFrame_Irstea.this.tShapeFile.setText(inFile);
					MainFrame_Irstea.this.inFileShape = inFile;
					
					MainFrame_Irstea.this.tShapeFile.setBackground(Color.GREEN);
					
					Debug.printDebug(Messages_Irstea.getString("MenuBar_Irstea.debugTextDisplay")); //$NON-NLS-1$
				}
				
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("Ouvrir Shape ...");
		lblNewLabel_1.setBounds(12, 54, 112, 15);
		getContentPane().add(lblNewLabel_1);
		
		this.pCheckBoxesConstraints = new JPanel();
		this.pCheckBoxesConstraints.setBounds(12, 81, 376, 260);
		getContentPane().add(this.pCheckBoxesConstraints);
		this.listCheckBoxesConstraints = new ArrayList<JCheckBox>();
		
		this.tShapeFile = new JTextField();
		this.tShapeFile.setBounds(172, 52, 216, 19);
		this.tShapeFile.setBackground(Color.RED);
		getContentPane().add(this.tShapeFile);
		this.tShapeFile.setColumns(10);
		
		this.pCheckBoxesOperations = new JPanel();
		this.pCheckBoxesOperations.setBounds(12, 348, 376, 140);
		getContentPane().add(this.pCheckBoxesOperations);
		this.listCheckBoxesOperations = new ArrayList<JCheckBox>();
		
		JLabel lblLogoIrstea = new JLabel(new ImageIcon(getClass().getResource(ConstantesImagesIrstea.imgLogoIrstea_small)));
		lblLogoIrstea.setBounds(12, 492, 46, 46);
		getContentPane().add(lblLogoIrstea);

	}
	
	public static void readXMLFile(String nom) throws FileNotFoundException  //lecture du fichier
	{
		
		scanner= new Scanner(new File(nom));
		try {
			scanner= new Scanner(new File(nom));
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				textArea.append(line+ "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		scanner.close();
	}
	
	public void parseXMLFile(String inFile) throws JDOMException, IOException {
		
		SAXBuilder sxb = new SAXBuilder();
		jdomDocument = sxb.build(new File(inFile));
		racine = jdomDocument.getRootElement();
		this.listCheckBoxesConstraints.clear();
		
		for (Object iterIndicators : racine.getChild("indicators").getChildren() ) {
			
			Indicator indTemp = new Indicator(	((Element)iterIndicators).getChild("name").getValue(),
					((Element)iterIndicators).getChild("attribute_name").getValue(), 
					((Element)iterIndicators).getChild("theme").getValue(),
					((Element)iterIndicators).getChild("component").getValue());
			
			this.listIndicators.add(indTemp);
			
			JCheckBox tempJCheckBox1 = new JCheckBox(((Element)iterIndicators).getChild("name").getValue());
			tempJCheckBox1.addItemListener(new CheckBoxesConstraintsListener());
			this.listCheckBoxesConstraints.add(tempJCheckBox1);
			this.pCheckBoxesConstraints.add(tempJCheckBox1);
			
		}
		
		for (Object iterOperations : racine.getChild("operations").getChildren()) {
			
			int i=1;
			String testOperandConstraints, testWeight, operator = "";
			ArrayList<String> listOperands = new ArrayList<String>();
			ArrayList<Double> listWeight = new ArrayList<Double>();
			
			while(((Element)iterOperations).getChild("operand_"+ i) != null | ((Element)iterOperations).getChild("weight_"+ i) != null ) {
				testOperandConstraints = ((Element)iterOperations).getChild("operand_"+ i).getValue();
				listOperands.add(testOperandConstraints);
//				operator = ((Element)iterOperations).getChild("operator"+ i).getValue();
				testWeight = ((Element)iterOperations).getChild("weight_"+ i).getValue();
				listWeight.add(new Double(Double.parseDouble(testWeight)));
				i++;
			}
			
//			System.out.println(iterOperations);
			JCheckBox tempJCheckBox2 = new JCheckBox(((Element)iterOperations).getChild("result").getValue());
			tempJCheckBox2.setEnabled(false);
			tempJCheckBox2.addItemListener(new CheckBoxesConstraintsListener());
			this.listCheckBoxesOperations.add(tempJCheckBox2);
			this.pCheckBoxesOperations.add(tempJCheckBox2);
			
			Operation opeTemp = new Operation(	((Element)iterOperations).getChild("result").getValue(),
					((Element)iterOperations).getChild("theme").getValue(),
					listOperands,
					listWeight,
					operator,
					tempJCheckBox2);
			this.listOperations.add(opeTemp);
		}
		
	}
	
	/**
	 * @return the listIndicators
	 */
	public ArrayList<Indicator> getListIndicators() {
		return this.listIndicators;
	}
	
	/**
	 * @param listIndicators the listIndicators to set
	 */
	public void setListIndicators(ArrayList<Indicator> listIndicators) {
		this.listIndicators = listIndicators;
	}
	
	/**
	 * @return the listOperations
	 */
	public ArrayList<Operation> getListOperations() {
		return this.listOperations;
	}
	
	/**
	 * @param listOperations the listOperations to set
	 */
	public void setListOperations(ArrayList<Operation> listOperations) {
		this.listOperations = listOperations;
	}
	
	public class CheckBoxesConstraintsListener implements ItemListener {
		
		/* (non-Javadoc)
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		@Override
		public void itemStateChanged(ItemEvent e) {
			JCheckBox check = (JCheckBox)e.getSource();
			
			ArrayList<Indicator> activeIndicators = new ArrayList<Indicator>();
			
			for (Indicator iterIndicator : MainFrame_Irstea.this.listIndicators) {
				if( iterIndicator.isActive()) {		//if checkbox is true
					if( iterIndicator.getName().compareTo(check.getText()) == 0) { 
						iterIndicator.setActive(false);
					}
				}else {
					if( iterIndicator.getName().compareTo(check.getText()) == 0) { 
						iterIndicator.setActive(true);
					}
				}
			}

			for (Indicator indicator : MainFrame_Irstea.this.listIndicators) {
				if( indicator.isActive())
					activeIndicators.add(indicator);
			}
			
			//Chech operations and activate
			for (Operation iterOper : MainFrame_Irstea.this.listOperations) {
				
				if( iterOper.isIndicatorsActived(activeIndicators) ) {
					iterOper.setActive(true);
				}else {
					iterOper.setActive(false);
				}
			}
			
			for (Operation operation: MainFrame_Irstea.this.listOperations) {
				if( operation.isActive() & operation.getResult().compareTo(check.getText()) == 0 )
					MainFrame_Irstea.this.activeOperations.add(operation);
			}
		}
	}
}