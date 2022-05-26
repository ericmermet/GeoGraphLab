/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.maths;

import java.awt.Point;

public class EquationDroite {
	
	private float a = 0, b = 0, rho = 0;
	private float cosRho = 0, sinRho = 0;
	
	public EquationDroite(Point p1, Point p2) {
		this.a = new Double(new Double(Math.abs(p1.y - p2.y)) / new Double(Math.abs(p1.x - p2.x))).floatValue();
		this.b = p2.y - this.a * p2.x;
		this.rho = new Double(Math.atan(this.a)).floatValue();
		this.cosRho = new Double(Math.cos(this.rho)).floatValue();
		this.sinRho = new Double(Math.sin(this.rho)).floatValue();
	}
	
	public float getA() {
		return this.a;
	}
	
	public float getB() {
		return this.b;
	}
	
	public float getRho() {
		return this.rho;
	}
	
	public float getCosRho() {
		return this.cosRho;
	}
	
	public float getSinRho() {
		return this.sinRho;
	}
	
}
