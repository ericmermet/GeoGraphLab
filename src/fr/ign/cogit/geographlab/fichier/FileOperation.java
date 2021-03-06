/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.fichier;

import fr.ign.cogit.geographlab.exploration.Espace;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.PanelMainDraw;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.test.Debug;
import fr.ign.cogit.geographlab.util.Timer;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.geotools.feature.SchemaException;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.EdgeNameProvider;
import org.jgrapht.ext.IntegerNameProvider;
import org.jgrapht.ext.VertexNameProvider;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class FileOperation {
	
	public static int nbSommets;
	public static int nbArcs;
	public static List<String> textFieldShapeAttribute = new ArrayList<String>();
	
	private PanelMainDraw parent;
	private MainWindow mainWindow;
	
	public FileOperation(MainWindow mainWindow, PanelMainDraw parent) {
		this.mainWindow = mainWindow;
		this.parent = parent;
	}
	
	public void ouvrirFichierGrapheDOT(String dir, String f) {
		String inFileDOT = f;
		String inFilePOS = f + ".pos";
		
		// Si une seule carte, on ne cree pas de nouvel onglet
		// a FAIRE
		
		String nom = "CarteParDefaut";
		if (System.getProperty("os.name").startsWith("Windows")) {
			nom = f.substring(f.lastIndexOf("\\", f.length()) + 1, f.length());
		}
		if (System.getProperty("os.name").startsWith("Linux")) {
			nom = f.substring(f.lastIndexOf("/", f.length()) + 1, f.length());
		}
		
		File fileToLoadDOT = new File(inFileDOT);
		File fileToLoadPOS = new File(inFilePOS);
		
		//		String nom = "";
		//		if (System.getProperty("os.name").startsWith("Windows")) {
		//			nom = f.substring(f.lastIndexOf("\\", f.length()) + 1, f.length());
		//		}
		//		if (System.getProperty("os.name").startsWith("Linux")) {
		//			nom = f.substring(f.lastIndexOf("/", f.length()) + 1, f.length());
		//		}
		
		
		//		URL shapeURL = new URI("file:" + f).toURL();
//		URL dotURL = fileToLoadDOT.toURI().toURL();
		
		this.parent = this.mainWindow.panelsDraw.addNewPanel(nom);
		
		this.parent.setNom(f);
		
		this.parent.setVisible(true);
		this.parent.repaint();
		
		try {
			
			// Calul du temps de chargement
			Timer.tic();
			
			nbSommets = 0;
			nbArcs = 0;
			
			// Lecture fichier DOT -> Creation du graphe dans jgraphT
			InputStream fisDOT_IS = new FileInputStream(fileToLoadDOT);
			InputStreamReader fisDOT_ISR = new InputStreamReader(fisDOT_IS);
			BufferedReader inDOTFileBuffer = new BufferedReader(fisDOT_ISR);
			StringTokenizer strTok;
			
			String ligne = null;
			String token;
			
			//Lecture des noeuds
			while ((ligne = inDOTFileBuffer.readLine()) != null) {
				token = null;
				strTok = new StringTokenizer(ligne);
				
				while (strTok.hasMoreElements()) {
					
					token = strTok.nextToken();
					// ID de l'objet
					String IDObjet = token;
					
					// Le nom du graphe
					if (token.equals("graph") || token.equals("digraph")) {
						// Structures.graphes.get(Structures.graphes.size()-1).setNom(new
						// String(strTok.nextToken()));
						this.parent.carte.getGraphe().setNom(new String(strTok.nextToken()));
						break;
					}
					
					if (token.equals("}")) {
						break;
					}
					
					if (Integer.parseInt(token) > 0) {
						String currentToken = strTok.nextToken();
						
						if (currentToken.equals("--") | currentToken.equals("->")) {
							break;
						}
						
						if (currentToken.equals("[label")) {
							// Creation des noeuds
							
//							String nomAvecGuillemets = strTok.nextToken();
							int indexD = ligne.indexOf("\"");
							int indexA = ligne.indexOf("\"", indexD + 1);
							
							int iD = Integer.parseInt(IDObjet);
							
							int posVirgule1Noeud = ligne.indexOf(",");
							int posVirgule2Noeud = ligne.indexOf(",", posVirgule1Noeud + 1);
							int posDerniereVirgule = ligne.lastIndexOf(",");
							int posAvantDerniereVirgule = ligne.lastIndexOf(",", posDerniereVirgule-1);
							int posOpenBracketNoeud = ligne.indexOf("(", posVirgule2Noeud + 1);
							int posCloseBracketNoeud = ligne.indexOf(")", posOpenBracketNoeud + 1);
							
							String vertexName;
							Noeud n;
							Color couleurNoeud;
							if (posVirgule1Noeud == -1) {
								// Ancien format
								vertexName = ligne.substring(indexD + 1, indexA);
								n = new Noeud(vertexName, iD);
								couleurNoeud = ConstantesApplication.drawingColorVertex;
							} else {
								// Nouveau format avec les couleurs
								vertexName = ligne.substring(indexD + 1, posVirgule1Noeud - 1);
								n = new Noeud(vertexName, iD);
								
								Double poidsNoeud = new Double(Double.parseDouble(ligne.substring(posVirgule1Noeud + 2, posVirgule2Noeud - 1)));
								n.setPonderation(poidsNoeud.doubleValue());
								n.setIndicateurValeur(this.parent.carte.getNomIndicateurCourant(), poidsNoeud);
								
								String colorsRGB = ligne.substring(posOpenBracketNoeud + 1, posCloseBracketNoeud);
								
								int virgule1Color = colorsRGB.indexOf(",");
								int virgule2Color = colorsRGB.indexOf(",", virgule1Color + 1);
								
								int colorRed = Integer.parseInt(colorsRGB.substring(0, virgule1Color));
								int colorGreen = Integer.parseInt(colorsRGB.substring(virgule1Color + 1, virgule2Color));
								int colorBlue = Integer.parseInt(colorsRGB.substring(virgule2Color + 1));
								
								couleurNoeud = new Color(colorRed, colorGreen, colorBlue);
							}
							
							n.getNoeudGraphique().setColor(couleurNoeud);
							
							this.parent.carte.getGraphe().addNoeud(n);
							
							nbSommets++;
							System.out.println(nbSommets + " - " + n.getNom() + " id = " + n.getID());
							// System.out.println(nbSommets +
							// " ---- "+vertexName);
							break;
						}
					}
				}
			}
			
			// Lecture fichier POS -> Positionnement des noeuds
			InputStream fisPOS_IS = new FileInputStream(fileToLoadPOS);
			InputStreamReader fisPOS_ISR = new InputStreamReader(fisPOS_IS);
			BufferedReader inPOSFileBuffer = new BufferedReader(fisPOS_ISR);
			
			// Position d'un noeud
			while ((ligne = inPOSFileBuffer.readLine()) != null) {
				// System.out.println(ligne);
				strTok = new StringTokenizer(ligne);
				token = null;
//				String XposStr, YposStr;
				int Xpos, Ypos;
				
				int indexFinNom = ligne.indexOf(":(") - 1;
				String localName = ligne.substring(0, indexFinNom);
				
				token = strTok.nextToken();
				
				Noeud monNoeud = this.parent.carte.getGraphe().getNoeud(localName);
				
				// Position en X
				int indexDebutPosX = ligne.indexOf(":(") + 3;
				int indexFinPosX = ligne.indexOf(",") - 1;
				Xpos = Integer.parseInt(ligne.substring(indexDebutPosX, indexFinPosX));
				
				// Position en Y
				int indexDebutPosY = ligne.indexOf(",") + 2;
				int indexFinPosY = ligne.indexOf(")") - 1;
				Ypos = Integer.parseInt(ligne.substring(indexDebutPosY, indexFinPosY));
				
				monNoeud.setPosition(Xpos, Ypos);
				this.parent.carte.getVueDuGraphe().addNoeud(monNoeud.getNoeudGraphique());
				
			}
			
			inDOTFileBuffer.close();
			
			// Lecture fichier DOT -> Creation du graphe dans jgraphT
			fisDOT_IS = new FileInputStream(fileToLoadDOT);
			fisDOT_ISR = new InputStreamReader(fisDOT_IS);
			inDOTFileBuffer = new BufferedReader(fisDOT_ISR);
			
			ligne = null;
			
			//Lecture des arcs
			while ((ligne = inDOTFileBuffer.readLine()) != null) {
				token = null;
				strTok = new StringTokenizer(ligne);
				
				while (strTok.hasMoreElements()) {
					
					token = strTok.nextToken();
					// ID de l'objet
//					String IDObjet = token;
					
					// Le nom du graphe
					if (token.equals("graph") || token.equals("digraph")) {
						break;
					}
					
					if (token.equals("}")) {
						break;
					}
					
					if (Integer.parseInt(token) > 0) {
						String currentToken = strTok.nextToken();
						
						if (currentToken.equals("[label")) {
							break;
						}
						
						if (currentToken.equals("--") | currentToken.equals("->")) {
							
							// Creation des arcs
							
//							int indexD = ligne.indexOf("--") + ligne.indexOf("->") + 1;
//							int indexA = ligne.indexOf("[");
							
//							String iDDebut = IDObjet;
//							String iDFin = ligne.substring(indexD + 3, indexA - 1);
//							
//							int iDD = Integer.parseInt(iDDebut);
//							int iDA = Integer.parseInt(iDFin);
							
							strTok.nextToken(); // 497
							strTok.nextToken(); // [label
							strTok.nextToken(); // =
							
							String edge1Name = strTok.nextToken();
							
							int indexDebutTexteNoeud1 = ligne.indexOf("(") + 1;
							int indexFinTexteNoeud1 = 0;
							String separator = new String(" -- ");
							if (ligne.indexOf(" -- ") != -1) {
								indexFinTexteNoeud1 = ligne.indexOf(separator);
							}
							if (ligne.indexOf(" : ") != -1) {
								indexFinTexteNoeud1 = ligne.indexOf(" : ");
								separator = new String(" : ");
							}
							
							edge1Name = ligne.substring(indexDebutTexteNoeud1, indexFinTexteNoeud1);
							
							strTok.nextToken();
							
							String edge2Name = strTok.nextToken();
							
							int indexDebutTexteNoeud2 = ligne.indexOf(separator) + 4;
							int indexFinTexteNoeud2 = ligne.indexOf(")");
							
							edge2Name = ligne.substring(indexDebutTexteNoeud2, indexFinTexteNoeud2);
							
							String testSiPoidsDansDOT = strTok.nextToken();
							
							// LE POIDS de l'arc
							double poidsArc = 1.0;
							int posVirgule = ligne.indexOf(",");
							int posVirgule2 = ligne.indexOf(",", posVirgule + 1);
							int posFinPoids = ligne.indexOf("\"", posVirgule + 1);
							if (posVirgule != -1) {
								// Le poids de l'arc EST dans le fichier DOT
								int indexDebutPoids = ligne.indexOf(",") + 1;
								int indexFinPoids;
								if (posVirgule2 != -1) {
									indexFinPoids = posVirgule2 - 1;
								} else {
									indexFinPoids = posFinPoids;
								}
								testSiPoidsDansDOT = strTok.nextToken();
								testSiPoidsDansDOT = ligne.substring(indexDebutPoids, indexFinPoids);
								// int lPoids = testSiPoidsDansDOT.length();
								poidsArc = Double.parseDouble(testSiPoidsDansDOT);
							} else {
								System.out.println("Bizarre...");
							}
							
							if (poidsArc == 1.0)
								System.out.println("Tres bizarre...");
							
							// La COULEUR de l'arc
							int posOpenBracketNoeud = ligne.indexOf("(", posVirgule2 + 1);
							int posCloseBracketNoeud = ligne.indexOf(")", posOpenBracketNoeud + 1);
							
							String colorsRGB = ligne.substring(posOpenBracketNoeud + 1, posCloseBracketNoeud);
							
							int virgule1Color = colorsRGB.indexOf(",");
							int virgule2Color = colorsRGB.indexOf(",", virgule1Color + 1);
							Color couleurArc;
							
							try {
								int colorRed = Integer.parseInt(colorsRGB.substring(0, virgule1Color));
								int colorGreen = Integer.parseInt(colorsRGB.substring(virgule1Color + 1, virgule2Color));
								int colorBlue = Integer.parseInt(colorsRGB.substring(virgule2Color + 1));
								
								couleurArc = new Color(colorRed, colorGreen, colorBlue);
							} catch (Exception err) {
								couleurArc = ConstantesApplication.drawingColorEdge;
							}
							
							// Recherche des instances des noeuds
//							Set<Noeud> allInstancesNoeuds = this.parent.carte.getGraphe().getNoeuds();
							Noeud n1 = this.parent.carte.getGraphe().getNoeud(edge1Name);
							Noeud n2 = this.parent.carte.getGraphe().getNoeud(edge2Name);
							
							if( ! this.parent.carte.getGraphe().containsVertex(n1))
								System.out.println("graph does not contain n1" + n1.getID());
							if( ! this.parent.carte.getGraphe().containsVertex(n2))
								System.out.println("graph does not contain n2" + n2.getID());
							
							Arc arcAjoute = new Arc(n1, n2);
//							Arc arcAjoute = this.parent.carte.getGraphe().addEdge(n1, n2);
							arcAjoute.setNom(edge1Name + "-" + edge2Name);
							
							arcAjoute.setPoidsDistance(poidsArc);
							arcAjoute.setPoidsTemps(poidsArc);
							arcAjoute.setIndicateurValeur(this.parent.carte.getNomIndicateurCourant(), poidsArc);
							arcAjoute.getArcGraphique().setColor(couleurArc);
							
							this.parent.carte.getGraphe().addArcPondere(arcAjoute, new Double(poidsArc));
							this.parent.carte.getVueDuGraphe().addArc(arcAjoute.getArcGraphique());
							
							System.out.println(arcAjoute.getNom() + " : " + poidsArc + "/" + couleurArc);
							System.out.println("DEBUG -> " + arcAjoute.getNom() + " : " + " poids : " + arcAjoute.getPoids() + " poids distance : " + arcAjoute.getPoidsDistance());
							
							nbArcs++;
							break;
						}
					}
					
				}
				token = null;
			}
			
			System.out.println("Nombre de noeuds = " + this.parent.carte.getGraphe().getNombreNoeuds());
			System.out.println("Nombre d'arcs = " + this.parent.carte.getGraphe().getNombreArcs());

			//			// Placement des arcs connaissant maintenant la place des noeuds
			//			for (ArcGraphique iterArc : this.parent.carte.getVueDuGraphe().getArcsGraphiques()) {
			//				iterArc.autoSetShape();
			//			}
			
			inDOTFileBuffer.close();
			inPOSFileBuffer.close();
			
			System.out.println("Temps de chargement du fichier DOT (ms) : " + Timer.tac());
			
			this.parent.carte.getGraphe().setGrapheChange();
			
			if ((this.parent.carte.getGraphe().getNoeuds().size() * this.parent.carte.getGraphe().getNoeuds().size() - 1) / 2 < 1000000)
				this.parent.carte.setEspace(new Espace(this.mainWindow, this.parent.carte.getGraphe().getToutesLesOD()));
			
		} catch (Exception err) {
			System.out.println(err);
		}
	}
	
	public void ouvrirFichierShape(String dir, String f) throws URISyntaxException, IOException {
		
		FileOperationOpenShape foos = new FileOperationOpenShape(mainWindow, parent, f);
		foos.start();
		
	}
	
	public void exportDOT(String dir, FileWriter f) {
		
		try {
			
			DOTExporter<Noeud, Arc> dotExport = new DOTExporter<Noeud, Arc>(new IntegerNameProvider<Noeud>(), new VertexNameProvider<Noeud>() {
				public String getVertexName(Noeud noeud) {
					return new String(noeud.getNom() + " , " + noeud.getPonderation() +
							" , (" + noeud.getNoeudGraphique().getColor().getRed() + "," + noeud.getNoeudGraphique().getColor().getGreen() + "," + noeud.getNoeudGraphique().getColor().getBlue() + ")" + 
							" , (" + noeud.getXPosition() + "," + noeud.getYPosition() + ")");
				}
			}, new EdgeNameProvider<Arc>() {
				public String getEdgeName(Arc arc) {
					return new String("(" + arc.getSource().getNom() + " -- " + arc.getTarget().getNom() + ")" + " , " + arc.getPoidsDistance() + " , (" + arc.getArcGraphique().getColor().getRed() + "," + arc.getArcGraphique().getColor().getGreen() + "," + arc
							.getArcGraphique().getColor().getBlue() + ")");
				}
			});
			
			BufferedWriter out = new BufferedWriter(f);
			dotExport.export(out, this.parent.carte.getGraphe());
			String newDir = dir + ".pos";
			FileWriter fstreamPos = new FileWriter(newDir);
			BufferedWriter outPos = new BufferedWriter(fstreamPos);
			
			System.out.println(this.parent.carte.getVueDuGraphe().getNoeudsGraphiques());
			
			for (NoeudGraphique noeud : this.parent.carte.getVueDuGraphe().getNoeudsGraphiques()) {
				
				switch (noeud.getType()) {
					case ConstantesApplication.typeVertex:
						
						outPos.write(noeud.getNom() + " :( " + noeud.getXPosition() + " , " + noeud.getYPosition() + " )\n");
						break;
				}
			}
			
			out.close();
			outPos.close();
			Debug.printDebug("Export OK");
			
		} catch (IOException e1) {
			Debug.printDebug("Erreur de fichier ou de repertoire pour l'export DOT");
		}
	}
	
	public void exportSVG(String dir, FileWriter f) throws UnsupportedEncodingException, SVGGraphics2DIOException {
		// DOMImplementation domImpl = GenericDOMImplementation.
		
		// Get a DOMImplementation.
		DOMImplementation domImpl =
			GenericDOMImplementation.getDOMImplementation();
		
		// Create an instance of org.w3c.dom.Document.
		String svgNS = "http://www.w3.org/2000/svg";
		Document document = domImpl.createDocument(svgNS, "svg", null);
		
		// Create an instance of the SVG Generator.
		SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
		
		// Ask the test to render into the SVG Graphics2D implementation.
		this.parent.paint(svgGenerator);
		
		// Finally, stream out SVG to the standard output using
		// UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes
		//        try {
		//        	Writer out = new OutputStreamWriter(System.out, "UTF-8");
		//            svgGenerator.stream(out, useCSS);
		//		} catch (Exception e) {
		//			System.out.println("Export SVG Error ...");
		//		}
		
		try {
			BufferedWriter out = new BufferedWriter(f);
			
			FileWriter fstreamPos = new FileWriter(dir);
			BufferedWriter outPos = new BufferedWriter(fstreamPos);
			
			//			Writer out = new OutputStreamWriter(System.out, "UTF-8");
			svgGenerator.stream(outPos, useCSS);
			
			out.close();
			outPos.close();
			Debug.printDebug("Export OK");
		}catch (Exception e) {
			System.out.println("Export SVG Error ...");
		}
		
	}
	
	//Fonctions vers Plugin IRSTEA
	public void ouvrirFichierShapePoints(String dir, String f) throws URISyntaxException, IOException {
		fr.irstea.adret.geographlab.plugins.file.FileOperation.ouvrirFichierShapePoints(this.mainWindow,dir, f);
		
	}
	
	public void ouvrirFichierShapeODPoints(String dir, String f) throws URISyntaxException, IOException {
		fr.irstea.adret.geographlab.plugins.file.FileOperation.ouvrirFichierShapeODPoints(this.mainWindow,dir, f);
		
	}
	
	public void exportShape(String dir, File f) throws IOException, SchemaException {
		fr.irstea.adret.geographlab.plugins.file.FileOperation.exportShape(this.mainWindow,dir, f);
	}
	
}