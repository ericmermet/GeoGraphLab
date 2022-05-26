/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.exploration.events;

import fr.ign.cogit.geographlab.cheminements.OD;
import fr.ign.cogit.geographlab.exploration.listeners.EspaceListener;
import fr.ign.cogit.geographlab.ihm.MainWindow;
import fr.ign.cogit.geographlab.ihm.listes.PanelOD;

import java.util.HashMap;


public class EspaceEvent implements EspaceListener {
	
	MainWindow fenetrePrincipale;
	
	public EspaceEvent(MainWindow fenetrePrincipale) {
		this.fenetrePrincipale = fenetrePrincipale;
		
	}
	
	public boolean espaceChange() {
		// System.out.println("changement des ods");
		
		if (this.fenetrePrincipale.panelLegend != null) {
			HashMap<Integer, OD> toutesLesODs = this.fenetrePrincipale.panelActif.carte.getEspace().getEspaceDeDef();
			int nbOds = toutesLesODs.size();
			
			int i = 0;
			PanelOD.setModelDataSize(nbOds);
			if (PanelOD.parent.panelActif != null) {
				PanelOD.parent.panelActif.carte.getEspace().setEspaceDeDef(toutesLesODs);
			}
			
			for (OD iterOD : toutesLesODs.values()) {
				PanelOD.getJTable().setValueAt(iterOD.getNom(), i, 0);
				PanelOD.getJTable().setValueAt(new Double(iterOD.getPonderation()), i, 1);
				PanelOD.getJTable().setValueAt(new String("Non defini"), i, 2);
				PanelOD.getJTable().setValueAt(new Boolean(false), i, 3);
				i++;
			}
		}
		
		return true;
	}
	
}