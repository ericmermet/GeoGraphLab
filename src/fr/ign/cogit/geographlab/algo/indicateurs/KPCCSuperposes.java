/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.indicateurs;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jgrapht.GraphPath;

import fr.ign.cogit.geographlab.cheminements.KPCC;
import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.Carte;
import fr.ign.cogit.geographlab.graphe.Arc;
import fr.ign.cogit.geographlab.graphe.Noeud;
import fr.ign.cogit.geographlab.ihm.constantes.ConstantesApplication;
import fr.ign.cogit.geographlab.test.Debug;
import fr.ign.cogit.geographlab.visu.NoeudGraphique;
import fr.ign.cogit.geographlab.visu.ObjetGraphique;

public class KPCCSuperposes extends Thread {
	
	private Carte nouvelleCartePourNouvelleCouche;
	
	private double min = Double.MAX_VALUE;
	private double max = 0;
	int k = 2;
	
	public Thread thread;
	
	public KPCCSuperposes(Carte carte) {
		this.nouvelleCartePourNouvelleCouche = new Carte(carte, "Kpcc_Superposes", "KPCCSuperposes" + new Date().getTime());
		this.thread = new Thread(this);
		
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		}
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(0.0));
		}
		
	}
	
	@Override
	public void run() {
		int i = 0;
		int nbOperation = this.k;
		Calendar c1 = Calendar.getInstance();
		
		long debut = c1.getTimeInMillis();
		
		// Algo ici
		
		if (this.nouvelleCartePourNouvelleCouche.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects.size() == 2) {
			
			// Insertion dans le gestionnaire de couches apres test reussi des
			// deux objets
			this.nouvelleCartePourNouvelleCouche.setColorLayer(new Color(200, 0, 100));
			// this.nouvelleCartePourNouvelleCouche.parent.couchesDeCartes.ajouterUneCoucheCarte(this.nouvelleCartePourNouvelleCouche);
			
			NoeudGraphique[] noeudSel = new NoeudGraphique[2];
			
			for (ObjetGraphique objGraphique : this.nouvelleCartePourNouvelleCouche.fenetrePrincipale.getPanelMainDrawActif().listOfSelectedObjects) {
				if (objGraphique.getType() == ConstantesApplication.typeVertex)
					noeudSel[i++] = (NoeudGraphique) objGraphique;
			}
			
			 System.out.println(this.k);
			askK();
			 System.out.println(this.k);
			
			nbOperation = this.k;
			
			this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.mainProgressBar.setIndeterminate(true);

			boolean dirige = this.nouvelleCartePourNouvelleCouche.variablesDeCarte.afficheGrapheNonDirige;
				
			KPCC lesPCC = new KPCC(this.nouvelleCartePourNouvelleCouche.getGraphe(), new OD(noeudSel[0].getNoeudTopologique(), noeudSel[1].getNoeudTopologique()), 0, 1, this.k, dirige);;
				
			HashSet<Noeud> noeudsDuChemin = new HashSet<Noeud>();
			
			for (GraphPath<Noeud, Arc> chemin : lesPCC.getChemins()) {
				for (Arc iterArc : chemin.getEdgeList()) {
					int temp = iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()).intValue() + 1;
					
					this.max = Math.max(temp, this.max);
					// this.min = Math.min(temp, this.min);
					
					iterArc.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(temp));
					
					noeudsDuChemin.add(iterArc.getSource());
					noeudsDuChemin.add(iterArc.getTarget());
					
				}
				
				for (Noeud iterNoeud : noeudsDuChemin) {
					
					int tempNoeud1 = iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()).intValue();
					iterNoeud.setIndicateurValeur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant(), new Double(tempNoeud1 + 1));
					//					
					this.max = Math.max(tempNoeud1, this.max);
					// this.min = Math.min(tempNoeud1, this.min);
					//					
				}
				noeudsDuChemin.clear();
			}
			
		} else {
			JOptionPane.showMessageDialog(this.nouvelleCartePourNouvelleCouche.fenetrePrincipale, "Il faut selectionner deux noeuds comme Origine et Destination");
			return;
		}
		
		// Affichage pour debug
		for (Arc iterArc : this.nouvelleCartePourNouvelleCouche.getGraphe().getArcs()) {
			Debug.printDebug("Kppc Arc " + iterArc.getNom() + " = " + iterArc.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		for (Noeud iterNoeud : this.nouvelleCartePourNouvelleCouche.getGraphe().getNoeuds()) {
			Debug.printDebug("Kpcc Noeud " + iterNoeud.getNom() + " = " + iterNoeud.getValeurPourIndicateur(this.nouvelleCartePourNouvelleCouche.getNomIndicateurCourant()));
		}
		System.out.println(this.min + " " + this.max);
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.mainProgressBar.setIndeterminate(false);
		
		Calendar c2 = Calendar.getInstance();
		long tempsExecution = c2.getTimeInMillis() - debut;

		System.out.println("Temps d'execution (ms):" + tempsExecution);
		
		// Mise a jour de la legende et de liens couleurs - legende
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setEchelle(this.min, this.max, this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().getIntervalles());
		this.nouvelleCartePourNouvelleCouche.getLegendeDeLaCarte().setLegendesNoeudsArcsZonePourValeurs();
		
		// Activation de la vue indicateur
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelTools.viewList.setSelectedIndex(1);
		this.nouvelleCartePourNouvelleCouche.variablesDeCarte.affichageEnCours = new String("Vue Indicateur");
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.statusBar.changeProgressBar(nbOperation, nbOperation);
		
		this.nouvelleCartePourNouvelleCouche.parent.parent.panelLayer.updateLayersFromLayerControler();
		
		// this.nouvelleCartePourNouvelleCouche.parent.repaint();
	}
	
	private void askK() {
		
		final JDialog dialoglSetNom = new JDialog();
		
		dialoglSetNom.setName("Nombre k de plus courts chemins a calculer");
		dialoglSetNom.setModal(true);
		
		Point mainPanelLocation = this.nouvelleCartePourNouvelleCouche.fenetrePrincipale.getLocationOnScreen();
		int xDialogLocation = mainPanelLocation.x / 2;
		int yDialogLocation = mainPanelLocation.y / 2;
		
		dialoglSetNom.setLocation(xDialogLocation, yDialogLocation);
		dialoglSetNom.setSize(250, 100);
		dialoglSetNom.setResizable(false);
		
		JPanel panelTexte = new JPanel();
		final JTextField kText = new JTextField(this.k);
		kText.setSize(200, 25);
		panelTexte.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelTexte.add(kText);
		
		JPanel panelBoutons = new JPanel();
		JButton bOK = new JButton("Ok");
		bOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent aebOk) {
				KPCCSuperposes.this.k = Integer.parseInt(kText.getText());
				dialoglSetNom.setVisible(false);
			}
		});
		JButton bCancel = new JButton("Annuler");
		bCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent aebCancel) {
				dialoglSetNom.setVisible(false);
			}
		});
		panelBoutons.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBoutons.add(bOK);
		panelBoutons.add(bCancel);
		
		dialoglSetNom.setLayout(new FlowLayout(FlowLayout.CENTER));
		dialoglSetNom.add(panelTexte);
		dialoglSetNom.add(panelBoutons);
		
		dialoglSetNom.setVisible(true);
		
	}
}