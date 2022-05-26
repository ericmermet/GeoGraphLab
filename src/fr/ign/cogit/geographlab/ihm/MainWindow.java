/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import fr.ign.cogit.geographlab.SplashScreen;
import fr.ign.cogit.geographlab.ihm.bars.MenuBar;
import fr.ign.cogit.geographlab.ihm.bars.StatusBar;
import fr.ign.cogit.geographlab.ihm.bars.ToolBar;
import fr.ign.cogit.geographlab.ihm.console.Console;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesImages;
import fr.ign.cogit.geographlab.ihm.customdockingframes.ColorDockable;
import fr.ign.cogit.geographlab.ihm.customdockingframes.JPanelMainDrawColorDockable;
import fr.ign.cogit.geographlab.ihm.exploration.PanelExplo;
import fr.ign.cogit.geographlab.ihm.listes.PanelAgregation;
import fr.ign.cogit.geographlab.ihm.listes.PanelEdges2;
import fr.ign.cogit.geographlab.ihm.listes.PanelNodes2;
import fr.ign.cogit.geographlab.ihm.listes.PanelOD;
import fr.ign.cogit.geographlab.ihm.listes.PanelTopologicalIndicators;
import fr.ign.cogit.geographlab.ihm.outils.PanelLayer;
import fr.ign.cogit.geographlab.ihm.outils.PanelTools;
import fr.ign.cogit.geographlab.ihm.outils.legende.PanelLegend;
import fr.ign.cogit.geographlab.lang.Messages;
import fr.irstea.adret.geographlab.plugins.lang.Messages_Irstea;
import fr.irstea.adret.geographlab.plugins.mmi.bars.MenuBar_Irstea;

import javax.swing.JFrame;

import bibliothek.extension.gui.dock.theme.BubbleTheme;
import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.extension.gui.dock.theme.bubble.BubbleColorScheme;
import bibliothek.extension.gui.dock.theme.bubble.BubbleColorScheme.Distribution;
import bibliothek.gui.DockController;
import bibliothek.gui.DockFrontend;
import bibliothek.gui.Dockable;
import bibliothek.gui.dock.SplitDockStation;
import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.event.DockFrontendAdapter;
import bibliothek.gui.dock.station.split.SplitDockGrid;
import bibliothek.gui.dock.themes.ColorScheme;
import bibliothek.gui.dock.themes.NoStackTheme;

public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public HashMap<String, PanelMainDraw> viewMap;
	
	public Locale locale_FR = new Locale(Messages.getString("MainWindow.0"),Messages.getString("MainWindow.1")); //$NON-NLS-1$ //$NON-NLS-2$
	public Locale locale_EN = new Locale(Messages.getString("MainWindow.2"),Messages.getString("MainWindow.3")); //$NON-NLS-1$ //$NON-NLS-2$
	public Locale locale_US = new Locale(Messages.getString("MainWindow.4"),Messages.getString("MainWindow.5")); //$NON-NLS-1$ //$NON-NLS-2$
//	public Locale locale_default = Locale.getDefault();
	public Locale locale_default = this.locale_EN;
	
	//Initialisation du resource bundle
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(Messages.getString("MainWindow.6"), this.locale_default); //$NON-NLS-1$
//	private ResourceBundle resourceBundle_Irstea = ResourceBundle.getBundle("fr.irstea.adret.geographlab.plugins.lang", this.locale_default);
	
	public MenuBar menuBar;// = new MenuBar(this);
	public ToolBar toolBar;// = new ToolBar(this);
	public PanelTools panelTools;
	
	public PanelLayer panelLayer;
	public PanelLegend panelLegend;
	public GestionPanelDraw panelsDraw;
	
	public PanelMainDraw panelActif;
	public PanelExplo panelExplo;
	
	public Console console = new Console(this);
	
	public PanelNodes2 tabNodes;
	public PanelEdges2 tabEdges;
	public PanelTopologicalIndicators tabTopologicalIndicators;
	public PanelOD tabODs;
	public PanelAgregation panelAgreg;
	
	public StatusBar statusBar = new StatusBar(this);
	
//	public TabWindow mainPanels;
//	public SplitWindow mainPanelsEtConsole;
//	public static RootWindow rootWindow;
//	public static RootWindow mainPanelsEtConsoleEnRoot;

	public DockFrontend frontend = new DockFrontend( this );
	public SplitDockStation station = new SplitDockStation();
	public SplitDockGrid grid = new SplitDockGrid();
	
	private Timer timerMemory;
	private Timer timerGc;
	Runtime runtime;
	
	List<Runnable> runOnClose = new ArrayList<Runnable>();
	
	public MainWindow(int tailleX, int tailleY) {
		
		System.out.println("Available Threads : " + ConstantesApplication.availableThreads);
		System.out.println("Available Cores : " + ConstantesApplication.availableCores);
		
		//Set Messages to default locale
		Messages.setBundle(this.locale_default);
		Messages_Irstea.setBundle(this.locale_default);
		
		this.menuBar = new MenuBar(this);
		this.toolBar = new ToolBar(this);
		
		//Creation des timer de garbage collector auto et recuperation memoire
		createTimer();
		
		//Initialisation de la fenetre principale
		initialize(tailleX, tailleY);
		
		//Compatible docking Frames
		setTitle(Messages.getString("MainWindow.7") + ConstantesApplication.version); //$NON-NLS-1$
		initWindowListener();
		
		initFrontEndDockable();
		
//		SplashScreen splash = new SplashScreen();
//		JPanel tempP = new JPanel();
//		splash.add(tempP);

	}
	
	private void createTimer() {
		int delay = 30000; // milliseconds
		int delayGc = 90000;
		
		ActionListener updateFreeMemory = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MainWindow.this.runtime = Runtime.getRuntime();
				MainWindow.this.statusBar.changeMemoryField(MainWindow.this.runtime.freeMemory() / 1000000 + Messages.getString("MainWindow.8") + MainWindow.this.runtime.totalMemory() / 1000000 + Messages.getString("MainWindow.9")); //$NON-NLS-1$ //$NON-NLS-2$
			}
		};
		
		ActionListener garbageCollector = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.gc();
			}
			
		};
		this.timerMemory = new Timer(delay, updateFreeMemory);
		this.timerMemory.start();
		this.timerGc = new Timer(delayGc, garbageCollector);
		this.timerGc.start();
	}
	
	private void initialize(int tailleX, int tailleY) {
				
		setIconImage(Toolkit.getDefaultToolkit().getImage(Messages.getString("MainWindow.10"))); //$NON-NLS-1$
		
		setSize(ConstantesApplication.tailleFenetreX, ConstantesApplication.tailleFenetreY);
		setTitle(Messages.getString("MainWindow.11")); //$NON-NLS-1$
		
		setJMenuBar(this.menuBar);
		add(this.toolBar, BorderLayout.NORTH);
		add(this.statusBar, BorderLayout.SOUTH);
		
		this.viewMap = new HashMap<String, PanelMainDraw>();
		
		this.destroyOnClose(this.frontend);
		
		this.frontend.addRoot(Messages.getString("MainWindow.12"), this.station); //$NON-NLS-1$
		
	}
	
	private void initWindowListener(){
		setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );
		addWindowListener( new WindowAdapter(){
			public void windowClosing( WindowEvent e ){
//				Ici on demande la confirmation de fermeture de l'application
				int reponse = JOptionPane.showConfirmDialog(null, Messages.getString("MainWindow.messageQuit"), Messages.getString("MainWindow.messageConfirm"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
				if (reponse == JOptionPane.YES_OPTION){
					//Sauvegarde des données et autres traitements                
//					this.writeConfInFile();
					//Quitter l'application
					dispose();
					System.exit(0);
				}
			}
			
			public void windowClosed( WindowEvent e ){
				for( Runnable onClose : MainWindow.this.runOnClose ){
					onClose.run();
				}
			}
		});
	}
	
	private void initFrontEndDockable(){
		add( MainWindow.createThemePanel( null, this.setupEclipseTheme(this)));
		
		this.destroyOnClose(this.frontend);
		
		this.add(this.station);
		this.frontend.addRoot(Messages.getString("MainWindow.15"), this.station); //$NON-NLS-1$
		
		this.frontend.setShowHideAction(true);
		
		this.station.dropTree(this.grid.toTree());
		
		this.frontend.addFrontendListener( new DockFrontendAdapter(){
			@Override
			public void shown( DockFrontend frontend, Dockable dockable ){
				//Règle le problème du update infos graphe lorsque sélection dockable
				//Attention si graphes lourds -> risque de trop de mises à jour
				// TODO vérification que le dockable est une vue d'un réseau
				MainWindow.this.panelActif.carte.getGraphe().setGrapheChange();
			}
			
			@Override
			public void hidden( DockFrontend fronend, Dockable dockable ){
//				panelsDraw.removePanel(getPanelMainDrawActif(), dockable);
			}
		});
		
	}
	
	public void runOnClose( Runnable run ){
		this.runOnClose.add( run );
	}
	
	public void destroyOnClose( final CControl control ){
		runOnClose( new Runnable(){
			public void run(){
				control.destroy();
			}
		});
	}
	
	public void destroyOnClose( final DockController controller ){
		runOnClose( new Runnable(){
			public void run(){
				controller.kill();
			}
		});
	}
	
	public void destroyOnClose( final DockFrontend frontend ){
		runOnClose( new Runnable(){
			public void run(){
				frontend.kill();	
			}
		});
	}
	
	/* This method creates a JLabel and puts it together with "example" on a JPanel. */
	public static JPanel createThemePanel( String title, Component example ){
		JPanel panel = new JPanel( new GridBagLayout() );
		Insets insets = new Insets( 5, 5, 5, 5 );
		panel.add( new JLabel( title ), new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0 ));
		panel.add( example, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1000.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 0, 0 ));
		panel.setBorder( BorderFactory.createLineBorder( Color.BLACK, 2 ));
		return panel;
	}
	
	public Component setupEclipseTheme( MainWindow frame ){

//		frontend.setRootWindow( frame );
		frame.destroyOnClose( this.frontend );
		
		/* The EclipseTheme imitates the look and feel of the famous Eclipse IDE. */
		this.frontend.getController().setTheme( new EclipseTheme() );
		
		SplitDockStation station = createStation();
		this.add(this.station);
		
		//Mise à jour des checkboxes dans les menus
		this.menuBar.getmWindowBulles().setSelected(false);
		this.menuBar.getmWindowEclipse().setSelected(true);
		
		return station;
	}
	
	public Component setupBubbleTheme( MainWindow frame ){
		
//		controller.setRootWindow( frame );
		frame.destroyOnClose( this.frontend );
		
		this.frontend.getController().setTheme( new NoStackTheme( new BubbleTheme() ));
		
		ColorScheme colors = new BubbleColorScheme( Distribution.BRG );
		
		/* Once we decided what colors to use, we put them into the property-map */
		this.frontend.getController().getProperties().set( BubbleTheme.BUBBLE_COLOR_SCHEME, colors );
		
		SplitDockStation station = createStation();
		
		//Mise à jour des checkboxes dans les menus
		this.menuBar.getmWindowBulles().setSelected(true);
		this.menuBar.getmWindowEclipse().setSelected(false);
		
		this.add(this.station);
		return station;	
	}
	
	/* Just creating a SplitDockStation with some Dockables to demonstrate the look 
	 * of a theme  */
	public SplitDockStation createStation(){
		
		this.panelAgreg = new PanelAgregation( this, Messages.getString("MainWindow.pNameAgregation"), Color.YELLOW, 2.5f ); //$NON-NLS-1$
		
		this.panelsDraw = new GestionPanelDraw(this);
		this.panelActif.carte.carteExplo.init();
		
		this.panelTools =  new PanelTools( this, Messages.getString("MainWindow.pNameTools"), Color.RED, 2.5f ); //$NON-NLS-1$
		this.console = new Console( this, Messages.getString("MainWindow.pNameConsole"), Color.BLUE, 2.5f );		 //$NON-NLS-1$
		this.tabNodes = new PanelNodes2( this, Messages.getString("MainWindow.pNameEdgeList"), Color.YELLOW, 2.5f ); //$NON-NLS-1$
		this.tabEdges = new PanelEdges2( this, Messages.getString("MainWindow.pNameNodesList"), Color.CYAN, 2.5f ); //$NON-NLS-1$
		this.tabTopologicalIndicators = new PanelTopologicalIndicators( this, Messages.getString("MainWindow.pNameTopoIndicators"), Color.ORANGE, 2.5f ); //$NON-NLS-1$
		this.panelLayer = new PanelLayer( this, Messages.getString("MainWindow.pNameLayerManager"), Color.RED, 2.5f ); //$NON-NLS-1$
		this.tabODs = new PanelOD( this, Messages.getString("MainWindow.pNameODList"), Color.MAGENTA, 2.5f ); //$NON-NLS-1$
		this.panelLegend = new PanelLegend( this, Messages.getString("MainWindow.pNameLegend"), Color.PINK, 2.5f ); //$NON-NLS-1$
		
		this.grid.addDockable( 0, 0,
				18*ConstantesApplication.tailleFenetreX/100, 50*ConstantesApplication.tailleFenetreY/100, 
				this.panelTools, this.panelLayer, this.panelLegend, this.panelAgreg );
//		panelTools.shouldFocus();
		
		this.grid.addDockable( 0, 50*ConstantesApplication.tailleFenetreY/100,
				18*ConstantesApplication.tailleFenetreX/100, 50*ConstantesApplication.tailleFenetreY/100, 
				this.tabTopologicalIndicators, this.tabNodes, this.tabEdges, this.tabODs );
		
		this.grid.addDockable( 30*ConstantesApplication.tailleFenetreX/100, 80*ConstantesApplication.tailleFenetreY/100, 
				70*ConstantesApplication.tailleFenetreX/100, 20*ConstantesApplication.tailleFenetreY/100,
				this.console, this.panelActif.panelExplo );
		
		this.station.dropTree( this.grid.toTree() );
		
		return this.station;
	}
	
	public PanelMainDraw getView(String nom) {
		return this.viewMap.get(nom);
	}
	
	public PanelMainDraw getPanelMainDrawActif() {
		return this.panelActif;
	}
	
	public void setPanelMainDrawActif(PanelMainDraw p) {
		this.panelActif = p;
	}

	/**
	 * @return the resourceBundle
	 */
	public ResourceBundle getResourceBundle() {
		return this.resourceBundle;
	}

	/**
	 * @param resourceBundle the resourceBundle to set
	 */
	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}
	
}