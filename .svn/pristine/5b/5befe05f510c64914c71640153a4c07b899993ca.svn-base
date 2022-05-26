/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.maths;

public class Bissectrice {
	
	EquationDroite d1, d2;
	private float a = 0, b = 0, rho = 0;
	private float cosRho = 0, sinRho = 0;
	
	public Bissectrice(EquationDroite d1, EquationDroite d2) {
		this.d1 = d1;
		this.d2 = d2;
		
		float A, B, C;
		
		float a1 = this.d1.getA(), b1 = -1, c1 = this.d1.getB();
		float a2 = this.d2.getA(), b2 = -1, c2 = this.d1.getB();
		
		A = (float) (Math.sqrt((a2 * a2 + b2 * b2) / (a1 * a1 + b1 * b1)) * a1 - a2);
		B = (float) (Math.sqrt((a2 * a2 + b2 * b2) / (a1 * a1 + b1 * b1)) * b1 - b2);
		C = (float) (Math.sqrt((a2 * a2 + b2 * b2) / (a1 * a1 + b1 * b1)) * c1 - c2);
		
		this.a = A / B;
		this.b = C / B;
		
		// int tempXPointInter = new Double(
		// (d2.getB()-d1.getB())/(d1.getA()-d2.getA())).intValue();
		// int tempYPointInter = new
		// Double(d1.getA()*tempXPointInter+d1.getB()).intValue();
		//		
		// this.a = (d1.getA() + d2.getA())/2;
		// this.b = tempYPointInter - this.a * tempXPointInter;
		
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
