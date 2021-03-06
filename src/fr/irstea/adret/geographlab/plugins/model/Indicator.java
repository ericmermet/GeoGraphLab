/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * Indicator.java in fr.irstea.adret.geographlab.plugins.modele
 * 
 */
package fr.irstea.adret.geographlab.plugins.model;

/**
 * @author eric
 *
 */
public class Indicator {
	
	private String name;
	private String attribute_name;
	private String theme;
	private String component;
	private boolean isActive = false;
	
	/**
	 * @param component 
	 * 
	 */
	public Indicator(String name, String attribute_name, String theme, String component	 ) {
		this.name = name;
		this.attribute_name = attribute_name;
		this.theme = theme;
		this.component = component;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the attribute_name
	 */
	public String getAttribute_name() {
		return this.attribute_name;
	}
	/**
	 * @param attribute_name the attribute_name to set
	 */
	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
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
	 * @return the component
	 */
	public String getComponent() {
		return this.component;
	}
	/**
	 * @param component the component to set
	 */
	public void setComponent(String component) {
		this.component = component;
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
	}
	
}