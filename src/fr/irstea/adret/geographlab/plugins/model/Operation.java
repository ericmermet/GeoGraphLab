/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * Operation.java in fr.irstea.adret.geographlab.plugins.modele
 * 
 */
package fr.irstea.adret.geographlab.plugins.model;

import java.util.ArrayList;

import javax.swing.JCheckBox;

/**
 * @author eric
 *
 */
public class Operation {
	
	private String result;
	private String theme;
	private ArrayList<String> operands;
	private ArrayList<Double> weight;
	private String operator;
	private boolean isActive = false;
	private JCheckBox cbOperation;
	
	/**
	 * 
	 */
	public Operation(String result, String theme, ArrayList<String> operands, ArrayList<Double> weight, String operator, JCheckBox cbOperation) {
		this.result = result;
		this.theme = theme;
		this.operands = operands;
		this.weight = weight;
		this.operator = operator;
		this.cbOperation = cbOperation;
	}
	
	/**
	 * @return the result
	 */
	public String getResult() {
		return this.result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the theme
	 */
	public String getTheme() {
		return this.theme;
	}
	/**
	 * @param theme the theme to set
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}
	/**
	 * @return the operand
	 */
	public ArrayList<String> getOperands() {
		return this.operands;
	}
	/**
	 * @param operand the operand to set
	 */
	public void setOperands(ArrayList<String> operand) {
		this.operands = operand;
	}
	/**
	 * @return the weight
	 */
	public ArrayList<Double> getWeight() {
		return this.weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(ArrayList<Double> weight) {
		this.weight = weight;
	}
	
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return this.operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return this.isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
		if( this.isActive )
			this.cbOperation.setEnabled(true);
		else
			this.cbOperation.setEnabled(false);
	}
	
	public boolean isIndicatorsActived(ArrayList<Indicator> listIndicators) {
		
		int cptActive = 0;
		
		for (String operand : this.operands) {
			for (Indicator indicator : listIndicators) {
				if( operand.compareTo(indicator.getAttribute_name()) == 0 ) {
					//operand find in indicator list
					cptActive++;
				}
			}
		}
		
		if( cptActive == this.operands.size())
			return true;
		return false;
	}

}