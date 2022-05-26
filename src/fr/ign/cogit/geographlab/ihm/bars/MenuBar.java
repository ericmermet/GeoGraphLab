/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.ihm.bars;


import fr.ign.cogit.geographlab.algo.ArbreCouvrantDesPCC;
import fr.ign.cogit.geographlab.algo.ArbreCouvrantPoidsMinimum;
import fr.ign.cogit.geographlab.algo.FindKShortestPath;
import fr.ign.cogit.geographlab.algo.filtres.ODFilter;
import fr.ign.cogit.geographlab.algo.geom.RegionsDeVoronoi;
import fr.ign.cogit.geographlab.algo.indicateurs.BuildDependencies;
import fr.ign.cogit.geographlab.algo.indicateurs.CentraliteDeDegre;
import fr.ign.cogit.geographlab.algo.indicateurs.CentraliteDePouvoir;
import fr.ign.cogit.geographlab.algo.indicateurs.CentraliteIntermediaire;
import fr.ign.cogit.geographlab.algo.indicateurs.CentraliteProximite;
import fr.ign.cogit.geographlab.algo.indicateurs.Circuite;
import fr.ign.cogit.geographlab.algo.indicateurs.EloignementMax;
import fr.ign.cogit.geographlab.algo.indicateurs.EloignementMin;
import fr.ign.cogit.geographlab.algo.indicateurs.EloignementMoyen;
import fr.ign.cogit.geographlab.algo.indicateurs.EloignementMoyenODSpace;
import fr.ign.cogit.geographlab.algo.indicateurs.Empan;
import fr.ign.cogit.geographlab.algo.indicateurs.KClosestFirstTree;
import fr.ign.cogit.geographlab.algo.indicateurs.KPCCSuperposes;
import fr.ign.cogit.geographlab.algo.indicateurs.ParallelKPCCSuperposes;
import fr.ign.cogit.geographlab.algo.indicateurs.RayonDistal;
import fr.ign.cogit.geographlab.algo.indicateurs.RayonProximal;
import fr.ign.cogit.geographlab.algo.maths.Barycentre;
import fr.ign.cogit.geographlab.algo.maths.TriangulationDelaunay2;
import fr.ign.cogit.geographlab.algo.topologie.ComposantesConnexes;
import fr.ign.cogit.geographlab.algo.topologie.TopologyCorrector;
import fr.ign.cogit.geographlab.algo.topologie.Triangles;
import fr.ign.cogit.geographlab.algo.traverse.SpanningTreeCentrality;
import fr.ign.cogit.geographlab.algo.traverse.SpanningTreeCentralityMultiThreads;
import fr.ign.cogit.geographlab.cheminements.APSP;
import fr.ign.cogit.geographlab.cheminements.CheminEulerien;
import fr.ign.cogit.geographlab.cheminements.CheminHamiltonien;
import fr.ign.cogit.geographlab.cheminements.Constantes;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.fichier.FileOperation;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.config.ConfigLegende;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.lang.Messages;
import fr.ign.cogit.geographlab.test.Debug;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;
import fr.irstea.adret.geographlab.plugins.mmi.bars.MenuBar_Irstea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.geotools.feature.SchemaException;

import com.sun.xml.internal.bind.v2.TODO;
import com.vividsolutions.jts.geom.Point;

import org.jgrapht.Graphs;

import bibliothek.extension.gui.dock.theme.BubbleTheme;
import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.gui.dock.themes.NoStackTheme;

public class MenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	
	MainWindow parent;
	
	//MENU FILE
	private JMenu mFile = new JMenu(Messages.getString("MenuBar.mFile"), true); //$NON-NLS-1$
	private JMenuItem mNew = new JMenuItem(Messages.getString("MenuBar.mNew")); //$NON-NLS-1$
	private JMenuItem mNewTab = new JMenuItem(Messages.getString("MenuBar.mNewTab")); //$NON-NLS-1$
	private JMenu mOpen = new JMenu(Messages.getString("MenuBar.mOpen")); //$NON-NLS-1$
	private JMenuItem mOpenXLS = new JMenuItem(Messages.getString("MenuBar.mOpenXLS")); //$NON-NLS-1$
	private JMenuItem mOpenDOTPOS = new JMenuItem(Messages.getString("MenuBar.mOpenDOTPOS")); //$NON-NLS-1$
	private JMenuItem mOpenShape = new JMenuItem(Messages.getString("MenuBar.mOpenShape")); //$NON-NLS-1$
	private JMenuItem mOpenShapePoints = new JMenuItem(Messages.getString("MenuBar.mOpenShapePoints")); //$NON-NLS-1$
	private JMenuItem mOpenShapeODPoints = new JMenuItem(Messages.getString("MenuBar.mOpenShapeODPoints")); //$NON-NLS-1$
	private JMenu mExport = new JMenu(Messages.getString("MenuBar.mExport")); //$NON-NLS-1$
	private JMenuItem mExportDOT = new JMenuItem(Messages.getString("MenuBar.mExportDOT")); //$NON-NLS-1$
	private JMenuItem mExportTLP = new JMenuItem(Messages.getString("MenuBar.mExportTLP")); //$NON-NLS-1$
	private JMenuItem mExportSVG = new JMenuItem(Messages.getString("MenuBar.mExportSVG")); //$NON-NLS-1$
	private JMenuItem mExportSHP = new JMenuItem(Messages.getString("MenuBar.mExportSHP")); //$NON-NLS-1$
	private JMenuItem mQuit = new JMenuItem(Messages.getString("MenuBar.mQuit")); //$NON-NLS-1$
	
	//MENU TOOLS
	private JMenu mTools = new JMenu(Messages.getString("MenuBar.14"), true); //$NON-NLS-1$
	private JMenu mCalcIndic = new JMenu(Messages.getString("MenuBar.15"), true); //$NON-NLS-1$
	
	private JMenuItem mCalcNordSud = new JMenuItem(Messages.getString("MenuBar.17")); //$NON-NLS-1$
	private JMenuItem mFreeMemory = new JMenuItem(Messages.getString("MenuBar.18")); //$NON-NLS-1$
	
	private JMenu mMenuCentrality = new JMenu(Messages.getString("MenuBar.19"), true); //$NON-NLS-1$
	private JMenuItem mCalcBetweenessCentrality = new JMenuItem(Messages.getString("MenuBar.20")); //$NON-NLS-1$
	private JMenuItem mCalcClosenessCentrality = new JMenuItem(Messages.getString("MenuBar.21")); //$NON-NLS-1$
	private JMenuItem mCalcSpanningCentralityOneNode = new JMenuItem(Messages.getString("MenuBar.20_1")); //$NON-NLS-1$
	private JMenuItem mCalcSpanningCentralityAllNodes = new JMenuItem(Messages.getString("MenuBar.20_2")); //$NON-NLS-1$
	
	private JMenu mMenuAccessibility = new JMenu(Messages.getString("MenuBar.22"), true); //$NON-NLS-1$
	private JMenuItem mCalcAccessibilityShimbel = new JMenuItem(Messages.getString("MenuBar.23")); //$NON-NLS-1$
	private JMenu mMenuEloignement = new JMenu(Messages.getString("MenuBar.24"), true); //$NON-NLS-1$
	private JMenuItem mCalcEloignementMoyen = new JMenuItem(Messages.getString("MenuBar.25")); //$NON-NLS-1$
	private JMenuItem mCalcEloignementOD = new JMenuItem(Messages.getString("MenuBar.147")); //$NON-NLS-1$
	private JMenuItem mCalcEloignementMin = new JMenuItem(Messages.getString("MenuBar.26")); //$NON-NLS-1$
	private JMenuItem mCalcEloignementMax = new JMenuItem(Messages.getString("MenuBar.27")); //$NON-NLS-1$
	private JMenuItem mCalcCircuite = new JMenuItem(Messages.getString("MenuBar.28")); //$NON-NLS-1$
	
	private JMenu mMenuFlux = new JMenu(Messages.getString("MenuBar.29"), true); //$NON-NLS-1$
	private JMenuItem mCalcProbit = new JMenuItem(Messages.getString("MenuBar.30")); //$NON-NLS-1$
	
	private JMenu mTopologie = new JMenu(Messages.getString("MenuBar.31"), true); //$NON-NLS-1$
	private JMenuItem mCalcCentralityDegree = new JMenuItem(Messages.getString("MenuBar.32")); //$NON-NLS-1$
	private JMenuItem mCalcCentralityPouvoir = new JMenuItem(Messages.getString("MenuBar.33")); //$NON-NLS-1$
	
	private JMenu mGeometrie = new JMenu(Messages.getString("MenuBar.mGeometrie"), true); //$NON-NLS-1$
	private JMenuItem mCalcTriangles = new JMenuItem(Messages.getString("MenuBar.34")); //$NON-NLS-1$
	private JMenuItem mCalcCarres = new JMenuItem(Messages.getString("MenuBar.35")); //$NON-NLS-1$
	
	private JMenu mMenuCheminsContournements = new JMenu(Messages.getString("MenuBar.36"), true); //$NON-NLS-1$
	private JMenuItem mCalcEmpan = new JMenuItem(Messages.getString("MenuBar.37")); //$NON-NLS-1$
	private JMenuItem mCalcRayonProximal = new JMenuItem(Messages.getString("MenuBar.38")); //$NON-NLS-1$
	private JMenuItem mCalcRayonDistal = new JMenuItem(Messages.getString("MenuBar.39")); //$NON-NLS-1$
	private JMenuItem mCalcKClosestFirstTree = new JMenuItem(Messages.getString("MenuBar.40")); //$NON-NLS-1$
	
	private JMenu mMenuOhters = new JMenu(Messages.getString("MenuBar.41"), true); //$NON-NLS-1$
	private JMenuItem mCalcKPCCSuperpose = new JMenuItem(Messages.getString("MenuBar.42")); //$NON-NLS-1$
	private JMenuItem mCalcFluxPCC = new JMenuItem(Messages.getString("MenuBar.43")); //$NON-NLS-1$
	private JMenuItem mBuildDependencies = new JMenuItem(Messages.getString("MenuBar.140")); //$NON-NLS-1$
	
	private JMenu mCalcTheorieDesGraphes = new JMenu(Messages.getString("MenuBar.44"), true); //$NON-NLS-1$
	private JMenuItem mCalcCheminEulerien = new JMenuItem(Messages.getString("MenuBar.45")); //$NON-NLS-1$
	private JMenuItem mCalcCheminHamiltonien = new JMenuItem(Messages.getString("MenuBar.46")); //$NON-NLS-1$
	private JMenu mArbres = new JMenu(Messages.getString("MenuBar.47"), true); //$NON-NLS-1$
	private JMenuItem mArbreCouvrantPoidsMinimum = new JMenuItem(Messages.getString("MenuBar.48")); //$NON-NLS-1$
	private JMenuItem mArbreCouvrantDesPCC = new JMenuItem(Messages.getString("MenuBar.49")); //$NON-NLS-1$
	
	private JMenu mPreTraitements = new JMenu(Messages.getString("MenuBar.50"), true); //$NON-NLS-1$
	private JMenu mPreTraitementsNodes = new JMenu(Messages.getString("MenuBar.pretreatmentsNodes"), true); //$NON-NLS-1$
	private JMenu mPreTraitementsEdges = new JMenu(Messages.getString("MenuBar.pretreatmentsEdges"), true); //$NON-NLS-1$
	private JMenu mPreTraitementsTopology = new JMenu(Messages.getString("MenuBar.pretreatmentsTopology"), true); //$NON-NLS-1$
	private JMenuItem mRenameVertices = new JMenuItem(Messages.getString("MenuBar.51")); //$NON-NLS-1$
	private JMenuItem mAutoNameArc = new JMenuItem(Messages.getString("MenuBar.52")); //$NON-NLS-1$
	private JMenuItem mAutoPondereArcs = new JMenuItem(Messages.getString("MenuBar.53")); //$NON-NLS-1$
	private JMenuItem mAutoPondereArcsPoidsUn = new JMenuItem(Messages.getString("MenuBar.weightOne")); //$NON-NLS-1$
	private JMenuItem mInversePoids = new JMenuItem(Messages.getString("MenuBar.54")); //$NON-NLS-1$
	private JMenuItem mDeleteVerticesZeroDegree = new JMenuItem(Messages.getString("MenuBar.55")); //$NON-NLS-1$
	private JMenuItem mIdentifyCompConnex = new JMenuItem(Messages.getString("MenuBar.16")); //$NON-NLS-1$
	private JMenuItem mTopologyCorrecter = new JMenuItem(Messages.getString("MenuBar.mTopologyCorrecter")); //$NON-NLS-1$
	
	private JMenu mTriangulationMaillage = new JMenu(Messages.getString("MenuBar.56"), true); //$NON-NLS-1$
	private JMenuItem mReseauReference = new JMenuItem(Messages.getString("MenuBar.57")); //$NON-NLS-1$
	private JMenuItem mVoronoi = new JMenuItem(Messages.getString("MenuBar.58")); //$NON-NLS-1$
	
	private JMenuItem mOD = new JMenuItem(Messages.getString("MenuBar.59")); //$NON-NLS-1$
	private JMenuItem mIdentifyKPCC = new JMenuItem(Messages.getString("MenuBar.60")); //$NON-NLS-1$
	
	//MENU FILTER
	private JMenu mFilter = new JMenu(Messages.getString("MenuBar.142"), true); //$NON-NLS-1$
	private JMenuItem mFilterNSelected = new JMenuItem(Messages.getString("MenuBar.143")); //$NON-NLS-1$
	private JMenuItem mFilterSelectedAsOrigin = new JMenuItem(Messages.getString("MenuBar.144")); //$NON-NLS-1$
	private JMenuItem mFilterSelectedAsDestination = new JMenuItem(Messages.getString("MenuBar.145")); //$NON-NLS-1$
	private JMenuItem mFilterClear = new JMenuItem(Messages.getString("MenuBar.146")); //$NON-NLS-1$
	private JMenuItem mInvertODFilter = new JMenuItem(Messages.getString("MenuBar.149")); //$NON-NLS-1$
	
	//MENU CONFIG
	private JMenu mConfig = new JMenu(Messages.getString("MenuBar.61"), true); //$NON-NLS-1$
	private JMenuItem mConfigLegend = new JMenuItem(Messages.getString("MenuBar.62")); //$NON-NLS-1$
	private JMenu mConfigLang = new JMenu(Messages.getString("MenuBar.mConfigLang"));
	private JCheckBoxMenuItem mConfigLangFrancais= new JCheckBoxMenuItem(Messages.getString("MenuBar.mConfigLangFrancais"));
	private JCheckBoxMenuItem mConfigLangAnglais= new JCheckBoxMenuItem(Messages.getString("MenuBar.mConfigLangAnglais"));
	
	//MENU WINDOWS
	private JMenu mWindow = new JMenu(Messages.getString("MenuBar.63")); //$NON-NLS-1$
	private JMenuItem mWindowAlgo = new JMenuItem(Messages.getString("MenuBar.64")); //$NON-NLS-1$
	private JMenuItem mWindowLayer = new JMenuItem(Messages.getString("MenuBar.65")); //$NON-NLS-1$
	private JMenuItem mWindowNodes = new JMenuItem(Messages.getString("MenuBar.66")); //$NON-NLS-1$
	private JMenuItem mWindowEdges = new JMenuItem(Messages.getString("MenuBar.67")); //$NON-NLS-1$
	
	private JMenu mWindowLayout = new JMenu(Messages.getString("MenuBar.mWindowLayout")); //$NON-NLS-1$
	private JCheckBoxMenuItem mWindowBulles = new JCheckBoxMenuItem(Messages.getString("MenuBar.mWindowBulles")); //$NON-NLS-1$
	private JCheckBoxMenuItem mWindowEclipse = new JCheckBoxMenuItem(Messages.getString("MenuBar.mWindowEclipse")); //$NON-NLS-1$
	
	public MenuBar(MainWindow parent) {
		super();
		this.parent = parent;
		
		initialize();
	}
	
	private void initialize() {
		
		/** Placement des menus */
		
		add(this.mFile);
		add(this.mTools);
		add(this.mFilter);
		add(this.mConfig);
		add(this.mWindow);
		
		/**
		 * Menu FICHIER
		 */
		this.mFile.add(this.mNew);
		
		//		this.mFile.add(this.mNewTab);
		
		this.mFile.add(this.mOpen);
		// this.mOpen.add(this.mOpenXLS);
		//		this.mOpen.add(this.mOpenDOTPOS);
		this.mOpen.add(this.mOpenShape);
		//		this.mOpen.add(this.mOpenShapePoints);
		this.mOpen.add(this.mOpenShapeODPoints);
		
		this.mFile.add(this.mExport);
		this.mExport.add(this.mExportSHP);
		//		this.mExport.add(this.mExportDOT);
		this.mExport.add(this.mExportSVG);
		//		this.mExport.add(this.mExportTLP);
		
		this.mFile.add(this.mQuit);
		
		/**
		 * Menu OUTILS
		 */
		this.mTools.add(this.mCalcIndic);
		
		this.mCalcIndic.add(this.mMenuCentrality);
		this.mMenuCentrality.add(this.mCalcBetweenessCentrality);
		this.mMenuCentrality.add(this.mCalcSpanningCentralityOneNode);
		this.mMenuCentrality.add(this.mCalcSpanningCentralityAllNodes);
		
		this.mCalcIndic.add(this.mMenuAccessibility);
		this.mMenuAccessibility.add(this.mCalcAccessibilityShimbel);
		this.mMenuAccessibility.add(this.mMenuEloignement);
		this.mMenuEloignement.add(this.mCalcEloignementMoyen);
		this.mMenuEloignement.add(this.mCalcEloignementOD);
		this.mMenuEloignement.add(this.mCalcEloignementMin);
		this.mMenuEloignement.add(this.mCalcEloignementMax);
		this.mMenuEloignement.add(this.mCalcClosenessCentrality);
		this.mMenuEloignement.add(this.mCalcCircuite);
		
		this.mCalcIndic.add(this.mMenuFlux);
		this.mMenuFlux.add(this.mCalcProbit);
		
		this.mCalcIndic.add(this.mTopologie);
		this.mTopologie.add(this.mCalcCentralityDegree);
		this.mTopologie.add(this.mCalcCentralityPouvoir);
		this.mCalcIndic.add(this.mGeometrie);
		this.mGeometrie.add(this.mCalcTriangles);
		this.mGeometrie.add(this.mCalcCarres);
		
		this.mCalcIndic.add(this.mMenuCheminsContournements);
		this.mMenuCheminsContournements.add(this.mCalcEmpan);
		this.mMenuCheminsContournements.add(this.mCalcRayonDistal);
		this.mMenuCheminsContournements.add(this.mCalcRayonProximal);
		this.mMenuCheminsContournements.add(this.mCalcKPCCSuperpose);
		this.mMenuCheminsContournements.add(this.mCalcFluxPCC);
		
		this.mCalcIndic.add(this.mMenuOhters);
		this.mMenuOhters.add(this.mCalcKClosestFirstTree);
		this.mMenuOhters.add(this.mBuildDependencies);
		
		this.mTools.add(this.mCalcTheorieDesGraphes);
		this.mCalcTheorieDesGraphes.add(this.mCalcCheminEulerien);
		this.mCalcTheorieDesGraphes.add(this.mCalcCheminHamiltonien);
		this.mCalcTheorieDesGraphes.add(this.mArbres);
		this.mArbres.add(this.mArbreCouvrantPoidsMinimum);
		this.mArbres.add(this.mArbreCouvrantDesPCC);
		
		this.mTools.add(this.mPreTraitements);
		this.mPreTraitements.add(this.mPreTraitementsNodes);
		this.mPreTraitements.add(this.mPreTraitementsEdges);
		this.mPreTraitements.add(this.mPreTraitementsTopology);
		
		this.mPreTraitementsNodes.add(this.mRenameVertices);
		this.mPreTraitementsEdges.add(this.mAutoNameArc);
		this.mPreTraitementsEdges.add(this.mAutoPondereArcs);
		this.mPreTraitementsEdges.add(this.mAutoPondereArcsPoidsUn);
		this.mPreTraitementsEdges.add(this.mInversePoids);
		this.mPreTraitementsNodes.add(this.mDeleteVerticesZeroDegree);
		this.mPreTraitements.add(this.mCalcNordSud);
		this.mPreTraitements.add(this.mIdentifyKPCC);
		this.mPreTraitementsTopology.add(this.mIdentifyCompConnex);
		this.mPreTraitementsTopology.add(this.mTopologyCorrecter);
		
		this.mTools.add(this.mTriangulationMaillage);
		this.mTriangulationMaillage.add(this.mReseauReference);
		this.mTriangulationMaillage.add(this.mVoronoi);
		
		this.mTools.add(this.mOD);
		this.mTools.add(this.mFreeMemory);
		
		/**
		 * Menu FILTER
		 */
		this.mFilter.add(this.mFilterNSelected);
		this.mFilter.add(this.mFilterSelectedAsOrigin);
		this.mFilter.add(this.mFilterSelectedAsDestination);
		this.mFilter.add(this.mInvertODFilter);
		this.mFilter.add(this.mFilterClear);
		
		/**
		 * Menu CONFIG
		 */
		this.mConfig.add(this.mConfigLegend);
		this.mConfig.add(this.mConfigLang);
		this.mConfigLang.add(this.mConfigLangFrancais);
		if( MenuBar.this.parent.locale_default.equals(MenuBar.this.parent.locale_FR) ){
			this.mConfigLangFrancais.setSelected(true);
		}
		this.mConfigLang.add(this.mConfigLangAnglais);
		if( MenuBar.this.parent.locale_default.equals(MenuBar.this.parent.locale_EN) ){
			this.mConfigLangAnglais.setSelected(true);
		}
		
		this.mWindowAlgo.setEnabled(true);
		this.mWindow.add(this.mWindowAlgo);
		
		this.mWindowLayer.setEnabled(true);
		this.mWindow.add(this.mWindowLayer);
		
		this.mWindowNodes.setEnabled(true);
		this.mWindow.add(this.mWindowNodes);
		
		this.mWindowEdges.setEnabled(true);
		this.mWindow.add(this.mWindowEdges);
		
		this.mWindow.add(this.mWindowLayout);
		this.mWindowLayout.add(this.mWindowBulles);
		this.mWindowLayout.add(this.mWindowEclipse);
		
		initialisePluginMenu();
		
		
		/** item nouveau du menu fichier */
		this.mNew.addActionListener(new ActionListener() {
			/** Declenche a l'appui de Nouveau  */
			public void actionPerformed(ActionEvent e) {
				// GetVue active
				// (PanelMainDraw)(MainWindow.rootWindow.getFocusedView().getComponent())
				MenuBar.this.parent.getPanelMainDrawActif().carte.razCarte();
			}
		});
		
		this.mNewTab.addActionListener(new ActionListener() { 
			/** Declenche a l'appui de Nouvel Onglet */
			public void actionPerformed(ActionEvent e) {
				// Ajout au gestionnaire de panel
				MenuBar.this.parent.panelsDraw.addNewPanel();
			}
		});
		
		/** item ouvrir du menu fichier */
		this.mOpenXLS.addActionListener(new ActionListener() {
			/** Declenche a l'appui de Open XLS */
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.71")); //$NON-NLS-1$
				MenuBar.this.parent.panelActif.carte.razCarte();
				
			}
		});
		
		this.mOpenDOTPOS.addActionListener(new ActionListener() {
			/** Declenche a l'appui de Open DOT */
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.72")); //$NON-NLS-1$
				
				JFileChooser inFileDlg = new JFileChooser(System.getProperty(Messages.getString("MenuBar.73"))); //$NON-NLS-1$
				inFileDlg.setFileFilter(new FileNameExtensionFilter(Messages.getString("MenuBar.74"), Messages.getString("MenuBar.75"))); //$NON-NLS-1$ //$NON-NLS-2$
				int returnVal = inFileDlg.showOpenDialog(MenuBar.this.parent);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// MenuBar.this.parent.panelActif.carte.razCarte();
					String browseDir = inFileDlg.getCurrentDirectory().toString();
					Debug.printDebug(browseDir);
					String inFile = null;
					inFile = inFileDlg.getSelectedFile().toString();
					FileOperation fop = new FileOperation(MenuBar.this.parent, MenuBar.this.parent.getPanelMainDrawActif());
					fop.ouvrirFichierGrapheDOT(browseDir, inFile);
					
					MenuBar.this.parent.getPanelMainDrawActif().centreVue();
					
				}
			}
		});
		
		this.mOpenShape.addActionListener(new ActionListener() {
			/** Declenche a l'appui de  Open SHAPE 		 */
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.76")); //$NON-NLS-1$
				
				JFileChooser inFileDlg = new JFileChooser(System.getProperty(Messages.getString("MenuBar.77"))); //$NON-NLS-1$
				
				inFileDlg.setFileFilter(new FileNameExtensionFilter(Messages.getString("MenuBar.78"), Messages.getString("MenuBar.79"))); //$NON-NLS-1$ //$NON-NLS-2$
				int returnVal = inFileDlg.showOpenDialog(MenuBar.this.parent);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					MenuBar.this.parent.panelActif.carte.razCarte();
					String browseDir = inFileDlg.getCurrentDirectory().toString();
					Debug.printDebug(browseDir);
					
					String inFile = null;
					inFile = inFileDlg.getSelectedFile().toString();
					inFileDlg.setCurrentDirectory(new File(inFile));
					FileOperation fop = new FileOperation(MenuBar.this.parent, MenuBar.this.parent.getPanelMainDrawActif());
					try {
						fop.ouvrirFichierShape(browseDir, inFile);
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					Debug.printDebug(Messages.getString("MenuBar.80")); //$NON-NLS-1$
				}
			}
		});
		
		this.mOpenShapePoints.addActionListener(new ActionListener() {
			/** Declenche a l'appui de Open SHAPE */
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.DebugTexteOuvertureShapePoints")); //$NON-NLS-1$
				
				JFileChooser inFileDlg = new JFileChooser(System.getProperty(Messages.getString("MenuBar.77"))); //$NON-NLS-1$
				inFileDlg.setFileFilter(new FileNameExtensionFilter(Messages.getString("MenuBar.78"), Messages.getString("MenuBar.79"))); //$NON-NLS-1$ //$NON-NLS-2$
				int returnVal = inFileDlg.showOpenDialog(MenuBar.this.parent);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String browseDir = inFileDlg.getCurrentDirectory().toString();
					Debug.printDebug(browseDir);
					String inFile = null;
					inFile = inFileDlg.getSelectedFile().toString();
					FileOperation fop = new FileOperation(MenuBar.this.parent, MenuBar.this.parent.getPanelMainDrawActif());
					try {
						fop.ouvrirFichierShapePoints(browseDir, inFile);
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					//					Debug.printDebug("Affichage ?");
				}
			}
		});
		
		this.mOpenShapeODPoints.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.DebugTexteOuvertureShapeODPoints")); //$NON-NLS-1$
				
				JFileChooser inFileDlg = new JFileChooser(System.getProperty(Messages.getString("MenuBar.77"))); //$NON-NLS-1$
				inFileDlg.setFileFilter(new FileNameExtensionFilter(Messages.getString("MenuBar.78"), Messages.getString("MenuBar.79"))); //$NON-NLS-1$ //$NON-NLS-2$
				int returnVal = inFileDlg.showOpenDialog(MenuBar.this.parent);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String browseDir = inFileDlg.getCurrentDirectory().toString();
					Debug.printDebug(browseDir);
					String inFile = null;
					inFile = inFileDlg.getSelectedFile().toString();
					FileOperation fop = new FileOperation(MenuBar.this.parent, MenuBar.this.parent.getPanelMainDrawActif());
					try {
						fop.ouvrirFichierShapeODPoints(browseDir, inFile);
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					//					Debug.printDebug("Affichage ?");
				}
			}});
		
		this.mExportDOT.addActionListener(new ActionListener() {
			/** Declenche a l'appui de ExportDOT */
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.83")); //$NON-NLS-1$
				JFileChooser outFileDlg = new JFileChooser(System.getProperty(Messages.getString("MenuBar.84"))); //$NON-NLS-1$
				outFileDlg.setDialogTitle(Messages.getString("MenuBar.85")); //$NON-NLS-1$
				outFileDlg.setApproveButtonText(Messages.getString("MenuBar.86")); //$NON-NLS-1$
				int returnVal = outFileDlg.showOpenDialog(MenuBar.this.parent);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						String browseDir = new String(outFileDlg.getSelectedFile().toString());
						FileWriter fstream = new FileWriter(browseDir);
						
						FileOperation fop = new FileOperation(MenuBar.this.parent, MenuBar.this.parent.getPanelMainDrawActif());
						fop.exportDOT(browseDir, fstream);
					} catch (IOException e1) {
						Debug.printDebug(Messages.getString("MenuBar.87")); //$NON-NLS-1$
					}
				}
			}
		});
		
		this.mExportTLP.addActionListener(new ActionListener() {
			/** Declenche a l'appui de ExportTLP */
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.88")); //$NON-NLS-1$
				
			}
		});
		
		this.mExportSVG.addActionListener(new ActionListener() {
			/** Declenche a l'appui de ExportSVG */
			@Override
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.89")); //$NON-NLS-1$
				JFileChooser outFileDlg = new JFileChooser(System.getProperty(Messages.getString("MenuBar.90"))); //$NON-NLS-1$
				outFileDlg.setDialogTitle(Messages.getString("MenuBar.91")); //$NON-NLS-1$
				outFileDlg.setApproveButtonText(Messages.getString("MenuBar.92")); //$NON-NLS-1$
				int returnVal = outFileDlg.showOpenDialog(MenuBar.this.parent);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						String browseDir = new String(outFileDlg.getSelectedFile().toString());
						FileWriter fstream = new FileWriter(browseDir);
						
						FileOperation fop = new FileOperation(MenuBar.this.parent, MenuBar.this.parent.getPanelMainDrawActif());
						fop.exportSVG(browseDir, fstream);
					} catch (IOException e1) {
						Debug.printDebug(Messages.getString("MenuBar.93")); //$NON-NLS-1$
						JOptionPane.showMessageDialog(null, "ERROR : export to shapefile");
					}
				}
			}
		});
		
		this.mExportSHP.addActionListener(new ActionListener() {
			/** Declenche a l'appui de ExportSVG */
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Debug.printDebug(Messages.getString("MenuBar.94")); //$NON-NLS-1$
				JFileChooser outFileDlg = new JFileChooser(System.getProperty(Messages.getString("MenuBar.95"))); //$NON-NLS-1$
				outFileDlg.setDialogTitle(Messages.getString("MenuBar.96")); //$NON-NLS-1$
				outFileDlg.setApproveButtonText(Messages.getString("MenuBar.97")); //$NON-NLS-1$
				int returnVal = outFileDlg.showOpenDialog(MenuBar.this.parent);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						String browseDir = new String(outFileDlg.getSelectedFile().toString());
						File fstream = new File(browseDir);
						FileOperation fop = new FileOperation(MenuBar.this.parent, MenuBar.this.parent.getPanelMainDrawActif());
						fop.exportShape(browseDir, fstream);
					} catch (IOException e1) {
						Debug.printDebug(Messages.getString("MenuBar.98")); //$NON-NLS-1$
					} catch (SchemaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		/** item quitter du menu fichier */
		this.mQuit.addActionListener(new ActionListener() {
			/** Declenche a l'appui de Quitter */
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				Debug.printDebug(Messages.getString("MenuBar.99")); //$NON-NLS-1$
			}
		});
		
		this.mConfigLangFrancais.addActionListener(new ActionListener() {
			/** Configuration de la langue francaise -forcee*/
			public void actionPerformed(ActionEvent arg0) {
				MenuBar.this.parent.locale_default = MenuBar.this.parent.locale_FR;
				MenuBar.this.parent.setResourceBundle(ResourceBundle.getBundle("fr.ign.cogit.geographlab.lang.messages",
						MenuBar.this.parent.locale_FR));
				MenuBar.this.mConfigLangFrancais.setSelected(true);
				MenuBar.this.mConfigLangAnglais.setSelected(false);
				Messages.setBundle(MenuBar.this.parent.locale_default);
				MenuBar.this.repaint();
			}
		});
		
		this.mConfigLangAnglais.addActionListener(new ActionListener() {
			/** Configuration de la langue anglaise -forcee*/
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuBar.this.parent.locale_default = MenuBar.this.parent.locale_EN;
				MenuBar.this.parent.setResourceBundle(ResourceBundle.getBundle("fr.ign.cogit.geographlab.lang.messages",
						MenuBar.this.parent.locale_EN));
				MenuBar.this.mConfigLangFrancais.setSelected(false);
				MenuBar.this.mConfigLangAnglais.setSelected(true);
				Messages.setBundle(MenuBar.this.parent.locale_default);
				MenuBar.this.repaint();
			}
		});
		
		this.mOD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.100")); //$NON-NLS-1$
				APSP allShortestPath = new APSP(MenuBar.this.parent.panelActif.carte);
				allShortestPath.thread.start();
			}
		});
		
		this.mIdentifyKPCC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.101")); //$NON-NLS-1$
				FindKShortestPath trouveKPCC = new FindKShortestPath(MenuBar.this.parent.getPanelMainDrawActif().carte);
				trouveKPCC.thread.start();
			}
		});
		
		this.mReseauReference.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.102")); //$NON-NLS-1$
				TriangulationDelaunay2 trDelaunay = new TriangulationDelaunay2(MenuBar.this.parent.getPanelMainDrawActif().carte.getGraphe());
				trDelaunay.run();
				//				MenuBar.this.parent.getPanelMainDrawActif().carte.setGrapheDeReference(trDelaunay.getGrapheDelaunay());
				//				MenuBar.this.parent.getPanelMainDrawActif().carte.getGrapheDeReference().setGrapheChange();
				//				MenuBar.this.parent.getPanelMainDrawActif().carte.getVueDuGrapheDeReference().setNoeudsGraphiques(MenuBar.this.parent.getPanelMainDrawActif().carte.getGrapheDeReference());
				//				MenuBar.this.parent.getPanelMainDrawActif().carte.getVueDuGrapheDeReference().setArcsGraphiques(MenuBar.this.parent.getPanelMainDrawActif().carte.getGrapheDeReference());
			}
		});
		
		this.mVoronoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.103")); //$NON-NLS-1$
				RegionsDeVoronoi surfVoronoi = new RegionsDeVoronoi(MenuBar.this.parent.getPanelMainDrawActif().carte.getGraphe());
				surfVoronoi.run();
				//				surfVoronoi.pondereNoeudAleaRegion();
				//				MenuBar.this.parent.panelActif.carte.setPolygonesDeVoronoi(surfVoronoi.getRegionsDeVoronoi());
			}
		});
		
		this.mCalcCentralityDegree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.104")); //$NON-NLS-1$
				CentraliteDeDegre indCentraliteDeDegre = new CentraliteDeDegre(MenuBar.this.parent.getPanelMainDrawActif().carte);
				indCentraliteDeDegre.thread.start();
			}
		});
		
		this.mCalcCentralityPouvoir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.105")); //$NON-NLS-1$
				CentraliteDePouvoir indCentraliteDePouvoir = new CentraliteDePouvoir(MenuBar.this.parent.getPanelMainDrawActif().carte);
				indCentraliteDePouvoir.thread.start();
			}
		});
		
		this.mCalcClosenessCentrality.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CentraliteProximite indCentraliteDeProximite = new CentraliteProximite(MenuBar.this.parent.getPanelMainDrawActif().carte);
				indCentraliteDeProximite.thread.start();
			}
		});
		
		this.mCalcBetweenessCentrality.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.106")); //$NON-NLS-1$
				//				CentraliteIntermediaire2 indCentraliteIntermediaire = new CentraliteIntermediaire2(MenuBar.this.parent.getPanelMainDrawActif().carte);
				CentraliteIntermediaire indCentraliteIntermediaire = new CentraliteIntermediaire(MenuBar.this.parent.getPanelMainDrawActif().carte);
				indCentraliteIntermediaire.thread.start();
				// MenuBar.this.parent.panelActif.carte.setValeurs(indCentraliteIntermediaire.getValeurs());
			}
		});
		
		this.mCalcSpanningCentralityOneNode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				SpanningTreeCentrality sTree = new SpanningTreeCentrality(MenuBar.this.parent.getPanelMainDrawActif().carte);
				sTree.thread.start();
			}
		});
		
		this.mCalcSpanningCentralityAllNodes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SpanningTreeCentralityMultiThreads sTree = new SpanningTreeCentralityMultiThreads(MenuBar.this.parent.getPanelMainDrawActif().carte);
				sTree.thread.start();
			}
		});
		
		this.mCalcAccessibilityShimbel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.107"));	 //$NON-NLS-1$
			}
		});
		
		this.mCalcEloignementMoyen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.108")); //$NON-NLS-1$
				EloignementMoyen eloignementMoyen = new EloignementMoyen(MenuBar.this.parent.getPanelMainDrawActif().carte, Constantes.typePCC);
				eloignementMoyen.thread.start();
			}
		});
		
		this.mCalcEloignementOD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.148")); //$NON-NLS-1$
				EloignementMoyenODSpace eloignementMoyenOD = new EloignementMoyenODSpace(MenuBar.this.parent.getPanelMainDrawActif().carte, Constantes.typePCC);
				eloignementMoyenOD.thread.start();
			}
		});
		
		this.mCalcEloignementMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.109")); //$NON-NLS-1$
				EloignementMin eloignementMin = new EloignementMin(MenuBar.this.parent.getPanelMainDrawActif().carte, Constantes.typePCC);
				eloignementMin.thread.start();
			}
		});
		
		this.mCalcEloignementMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.110")); //$NON-NLS-1$
				EloignementMax eloignementMax = new EloignementMax(MenuBar.this.parent.getPanelMainDrawActif().carte, Constantes.typePCC);
				eloignementMax.thread.start();
			}
		});
		
		this.mCalcCircuite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.111")); //$NON-NLS-1$
				Circuite circuite = new Circuite(MenuBar.this.parent.getPanelMainDrawActif().carte);
				circuite.thread.start();
			}
		});
		
		this.mCalcEmpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.112")); //$NON-NLS-1$
				Empan empan = new Empan(MenuBar.this.parent.getPanelMainDrawActif().carte);
				empan.thread.start();
			}
		});
		
		this.mCalcRayonDistal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.113")); //$NON-NLS-1$
				RayonDistal rayonDistal = new RayonDistal(MenuBar.this.parent.getPanelMainDrawActif().carte);
				rayonDistal.thread.start();
			}
		});
		
		this.mCalcRayonProximal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.114")); //$NON-NLS-1$
				RayonProximal rayonProximal = new RayonProximal(MenuBar.this.parent.getPanelMainDrawActif().carte);
				rayonProximal.thread.start();
			}
		});
		
		this.mCalcKPCCSuperpose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.115")); //$NON-NLS-1$
				KPCCSuperposes kPCCsuperpose = new KPCCSuperposes(MenuBar.this.parent.getPanelMainDrawActif().carte);
				kPCCsuperpose.thread.start();
			}
		});
		
		this.mCalcFluxPCC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.116")); //$NON-NLS-1$
				ParallelKPCCSuperposes fluxPCC= new ParallelKPCCSuperposes(MenuBar.this.parent.getPanelMainDrawActif().carte);
				fluxPCC.thread.start();
			}
		});
		
		this.mCalcKClosestFirstTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.117")); //$NON-NLS-1$
				KClosestFirstTree kCFTree = new KClosestFirstTree(MenuBar.this.parent.getPanelMainDrawActif().carte);
				kCFTree.thread.start();
			}
		});
		
		this.mBuildDependencies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.141")); //$NON-NLS-1$
				BuildDependencies relationalDependencies = new BuildDependencies(MenuBar.this.parent.getPanelMainDrawActif().carte);
				relationalDependencies.thread.start();
			}
		});
		
		this.mCalcTriangles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.118")); //$NON-NLS-1$
				Triangles trianglesDuReseau = new Triangles(MenuBar.this.parent.getPanelMainDrawActif().carte);
				trianglesDuReseau.thread.start();
			}
		});
		
		this.mCalcCheminEulerien.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.119")); //$NON-NLS-1$
				new CheminEulerien(MenuBar.this.parent.getPanelMainDrawActif());
			}
		});
		
		this.mCalcCheminHamiltonien.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.120")); //$NON-NLS-1$
				new CheminHamiltonien(MenuBar.this.parent.getPanelMainDrawActif());
			}
		});
		
		this.mArbreCouvrantPoidsMinimum.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArbreCouvrantPoidsMinimum arbreKruskal = new ArbreCouvrantPoidsMinimum(MenuBar.this.parent.getPanelMainDrawActif().carte);
				arbreKruskal.thread.start();
			}
		});
		
		this.mArbreCouvrantDesPCC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int nbElementsSelectionnes = MenuBar.this.parent.getPanelMainDrawActif().listOfSelectedObjects.size();
				
				if (nbElementsSelectionnes == 1) {
					
					NoeudGraphique noeudSel = null;
					
					for (ObjetGraphique objGraphique : MenuBar.this.parent.getPanelMainDrawActif().listOfSelectedObjects) {
						if (objGraphique.getType() == ConstantesApplication.typeVertex)
							noeudSel = (NoeudGraphique) objGraphique;
					}
					if (noeudSel != null) {
						ArbreCouvrantDesPCC arbrePCC = new ArbreCouvrantDesPCC(MenuBar.this.parent.getPanelMainDrawActif().carte, noeudSel.getNoeudTopologique());
						arbrePCC.thread.start();
					}
				} else {
					JOptionPane.showMessageDialog(MenuBar.this.parent, Messages.getString("MenuBar.121")); //$NON-NLS-1$
					return;
				}
			}
		});
		
		this.mRenameVertices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.122")); //$NON-NLS-1$
				int cpt = 1;
				for (Noeud iterNoeud : MenuBar.this.parent.panelActif.carte.getGraphe().getNoeuds()) {
					iterNoeud.setNom(Messages.getString("MenuBar.123") + cpt++); //$NON-NLS-1$
					System.out.println(Messages.getString("MenuBar.124") + iterNoeud.getNom() + Messages.getString("MenuBar.125") + iterNoeud.getNoeudGraphique().getNom()); //$NON-NLS-1$ //$NON-NLS-2$
				}
				System.out.println(Messages.getString("MenuBar.126") + cpt + Messages.getString("MenuBar.127")); //$NON-NLS-1$ //$NON-NLS-2$
			}
		});
		
		this.mAutoNameArc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Arc iterArc : MenuBar.this.parent.panelActif.carte.getGraphe().getArcs()) {
					String nomNoeudA = iterArc.getSource().getNom();
					String nomNoeudB = iterArc.getTarget().getNom();
					iterArc.setNom(nomNoeudA + Messages.getString("MenuBar.128") + nomNoeudB); //$NON-NLS-1$
				}
				MenuBar.this.parent.panelActif.carte.getGraphe().setGrapheChange();
			}
		});
		
		this.mAutoPondereArcs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Arc iterArc : MenuBar.this.parent.panelActif.carte.getGraphe().getArcs()) {
					double longueurTroncon = Math.sqrt(Math.pow(Math.abs(iterArc.getPointSource().getY() - iterArc.getPointCible().getY()), 2.0) 
							+ Math.pow(Math.abs(iterArc.getPointSource().getX() - iterArc.getPointCible().getX()), 2.0));
					
					double echelle = 25.0;
					longueurTroncon = longueurTroncon * echelle;
					longueurTroncon /= 33;
					
					longueurTroncon *= 100;
					int intLongueurTroncon = (int) longueurTroncon;
					double doubleLongueurTroncon = intLongueurTroncon;
					longueurTroncon = new Double(doubleLongueurTroncon / 100).doubleValue();
					
					if (longueurTroncon == 1.0)
						System.out.println(Messages.getString("MenuBar.129")); //$NON-NLS-1$
					
					// MenuBar.this.parent.panelActif.carte.getGraphe().setPoidsArc(iterArc,
					// longueurTroncon);
					iterArc.setPoidsDistance(longueurTroncon);
					iterArc.setIndicateurValeur(MenuBar.this.parent.panelActif.carte.getNomIndicateurCourant(), longueurTroncon);
					MenuBar.this.parent.panelActif.carte.getGraphe().setPoidsArc(iterArc, longueurTroncon);
					System.out.println(iterArc.getArcGraphique().getNom() + Messages.getString("MenuBar.130") + iterArc.getArcGraphique().getArcTopologique().getPoids()); //$NON-NLS-1$
					System.out.println(iterArc.getArcGraphique().getNom() + Messages.getString("MenuBar.131") + iterArc.getArcGraphique().getArcTopologique().getPoidsDistance()); //$NON-NLS-1$
					
				}
				MenuBar.this.parent.panelActif.carte.getGraphe().setGrapheChange();
			}
			
		});
		
		this.mAutoPondereArcsPoidsUn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Arc iterArc : MenuBar.this.parent.panelActif.carte.getGraphe().getArcs()) {
					double longueurTroncon = 1.0;
					
					iterArc.setPoidsDistance(longueurTroncon);
					iterArc.setIndicateurValeur(MenuBar.this.parent.panelActif.carte.getNomIndicateurCourant(), longueurTroncon);
					MenuBar.this.parent.panelActif.carte.getGraphe().setPoidsArc(iterArc, longueurTroncon);
					System.out.println(iterArc.getArcGraphique().getNom() + Messages.getString("MenuBar.130") + iterArc.getArcGraphique().getArcTopologique().getPoids()); //$NON-NLS-1$
					System.out.println(iterArc.getArcGraphique().getNom() + Messages.getString("MenuBar.131") + iterArc.getArcGraphique().getArcTopologique().getPoidsDistance()); //$NON-NLS-1$
					
				}
				MenuBar.this.parent.panelActif.carte.getGraphe().setGrapheChange();
			}
			
		});
		
		this.mInversePoids.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Arc iterArc : MenuBar.this.parent.panelActif.carte.getGraphe().getArcs()) {
					double poids = iterArc.getPoids();
					MenuBar.this.parent.panelActif.carte.getGraphe().setPoidsArc(iterArc, 1 / poids);
				}
				MenuBar.this.parent.panelActif.carte.getGraphe().setGrapheChange();
			}
			
		});
		
		this.mDeleteVerticesZeroDegree.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (Noeud iterNoeud : MenuBar.this.parent.panelActif.carte.getGraphe().getNoeuds()) {
					if (Graphs.neighborListOf(MenuBar.this.parent.panelActif.carte.getGraphe(), iterNoeud).size() == 0) {
						// MenuBar.this.parent.panelActif.carte.getGraphe().delNoeud(iterNoeud);
						System.out.println(Messages.getString("MenuBar.132") + iterNoeud.getNom()); //$NON-NLS-1$
					}
				}
			}
		});
		
		this.mIdentifyCompConnex.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ComposantesConnexes cc = new ComposantesConnexes(MenuBar.this.parent.panelActif.carte);
				cc.thread.start();
			}
		});
		
		this.mTopologyCorrecter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Distance en pixel
				int distance = 3;
				TopologyCorrector tc = new TopologyCorrector(MenuBar.this.parent.panelActif.carte, distance);
				tc.thread.start();
			}
		});
		
		this.mCalcNordSud.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Set<NoeudGraphique> mesNoeuds = MenuBar.this.parent.panelActif.carte.getVueDuGraphe().getNoeudsGraphiques();
				
				Barycentre pointBarycentre = new Barycentre(mesNoeuds);
				
				Point[] pointDimensionVue = MenuBar.this.parent.panelActif.carte.getVueDuGraphe().getDimensionVue();
				
				MenuBar.this.parent.panelActif.lignePartageEstOuestVertical = new Line2D.Double(pointBarycentre.getBarycentre().getX(), pointDimensionVue[0].getY(), pointBarycentre.getBarycentre().getX(), pointDimensionVue[1].getY());
				MenuBar.this.parent.panelActif.lignePartageNordSudHorizontal = new Line2D.Double(pointDimensionVue[0].getX(), pointBarycentre.getBarycentre().getY(), pointDimensionVue[1].getX(), pointBarycentre.getBarycentre().getY());
				
			}
		});
		
		this.mFreeMemory.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.gc();
			}
		});
		
		/**
		 * Filters
		 */
		
		this.mFilterNSelected.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<Integer, OD> nouvelEspace = new HashMap<Integer, OD>();
				ODFilter.nSelected(MenuBar.this.parent, nouvelEspace);
			}
		});
		
		this.mFilterSelectedAsOrigin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<Integer, OD> nouvelEspace = new HashMap<Integer, OD>();
				ODFilter.selectedAsOrigins(MenuBar.this.parent, nouvelEspace);
			}
		});
		
		this.mFilterSelectedAsDestination.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<Integer, OD> nouvelEspace = new HashMap<Integer, OD>();
				ODFilter.selectedAsDestinations(MenuBar.this.parent, nouvelEspace);
			}
		});
		
		this.mInvertODFilter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<Integer, OD> nouvelEspace = new HashMap<Integer, OD>();
				ODFilter.inverseFilters(MenuBar.this.parent, nouvelEspace);
			}
		});
		
		this.mFilterClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<Integer, OD> nouvelEspace = new HashMap<Integer, OD>();
				ODFilter.clearFilters(MenuBar.this.parent, nouvelEspace);
			}
		});
		
		this.mConfig.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				ConfigLegende configLegende = new ConfigLegende(MenuBar.this.parent);
				configLegende.setVisible(true);
			}
			
		});
		
		this.mWindowAlgo.setEnabled(true);
		this.mWindowAlgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.136")); //$NON-NLS-1$
			}
		});
		this.mWindow.add(this.mWindowAlgo);
		
		this.mWindowLayer.setEnabled(true);
		this.mWindowLayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.137")); //$NON-NLS-1$
			}
		});
		this.mWindow.add(this.mWindowLayer);
		
		this.mWindowNodes.setEnabled(true);
		this.mWindowNodes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.138")); //$NON-NLS-1$
			}
		});
		this.mWindow.add(this.mWindowNodes);
		
		this.mWindowEdges.setEnabled(true);
		this.mWindowEdges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.printDebug(Messages.getString("MenuBar.139")); //$NON-NLS-1$
			}
		});
		this.mWindow.add(this.mWindowEdges);		
		
		this.mWindowBulles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuBar.this.parent.frontend.getController().setTheme( new NoStackTheme( new BubbleTheme() ) );
				MenuBar.this.mWindowBulles.setSelected(true);
				MenuBar.this.mWindowEclipse.setSelected(false);
			}
		});
		
		this.mWindowEclipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuBar.this.parent.frontend.getController().setTheme( new EclipseTheme() );
				MenuBar.this.mWindowBulles.setSelected(false);
				MenuBar.this.mWindowEclipse.setSelected(true);
			}
		});
	}
	
	private void initialisePluginMenu() {
		//TODO add test if plugin exists
		try {
			Class.forName("fr.irstea.adret.geographlab.plugins.mmi.bars.MenuBar_Irstea");
			add(new MenuBar_Irstea(this.parent));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this.parent, "Le plugin Irstea n'est pas installé");
		}
		
	}
	
	/**
	 * @return the mWindowBulles
	 */
	public JCheckBoxMenuItem getmWindowBulles() {
		return this.mWindowBulles;
	}
	
	/**
	 * @return the mWindowEclipse
	 */
	public JCheckBoxMenuItem getmWindowEclipse() {
		return this.mWindowEclipse;
	}
}