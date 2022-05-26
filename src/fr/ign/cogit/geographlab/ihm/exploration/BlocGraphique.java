/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN
 * 2007 - 2010
 *
 * BlocGraphique.java in ihm.exploration
 * 
 */
package fr.ign.cogit.geographlab.ihm.exploration;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import fr.ign.cogit.geographlab.ihm.constantes.ConstantesExploration;

/**
 * @author mermet
 *
 */
public abstract class BlocGraphique extends Thread {

	public Thread thread;
	public int typeBloc;
	public int operation;
	//0 si carte, 1 si operation
	
	private Point pXY;
	private Rectangle emprise;
	
	private boolean selected;
	
	public Rectangle[] tabRectanglesEntreeSortie;
	
	public BlocGraphique() {
		
		this.thread = new Thread(this);
		
	}

	public void setpXY(Point pXY) {
		this.pXY = pXY;
		setEmprise();
	}

	public Point getpXY() {
		return this.pXY;
	}

	private void setEmprise() {
		if( this.typeBloc == ConstantesExploration.blocCarte)
			this.emprise = new Rectangle(getpXY().x-5,  getpXY().y-5, ConstantesExploration.widthBloc+10, ConstantesExploration.heightBloc+10);
		if( this.typeBloc == ConstantesExploration.blocOperation)
			this.emprise = new Rectangle( getpXY().x - 5,  getpXY().y - 5, ConstantesExploration.widthOperationBloc+10, ConstantesExploration.heightOperationBloc+10);
	}

	public Rectangle2D getEmprise() {
		return this.emprise;
	}

	public int getOperation() {
		return this.operation;
	}
	
	public void setOperation(int operation) {
		this.operation = operation;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	public boolean isSelected() {
		return this.selected;
	}
	
	public abstract void setRectanglesGenericLayer(Point p);
	
	public abstract void addConnecteurFilaires(ConnecteurBlocs connecteur);
	
	public abstract Rectangle[] getRectangles();
	
}
