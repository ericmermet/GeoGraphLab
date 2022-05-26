/**
 * GeoGraphLab - eric - Cogit / IGN 2007 - 2013
 * 
 * Messages.java in fr.ign.cogit.geographlab.ihm.bars
 * 
 */
package fr.irstea.adret.geographlab.plugins.lang;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author eric
 *
 */

public class Messages_Irstea {
	
	public static Locale locale_FR = new Locale("fr","FR");
	public static Locale locale_EN = new Locale("en","EN");
	public static Locale locale_US = new Locale("en","US");
	
	private static String BUNDLE_NAME = "fr.irstea.adret.geographlab.plugins.lang.messages"; //$NON-NLS-1$
	
	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	private Messages_Irstea() {
	}
	
	public static void setBundle(Locale locale) {
		if( locale.equals(locale_FR) ) {
			BUNDLE_NAME = "fr.irstea.adret.geographlab.plugins.lang.messages_fr_FR"; //$NON-NLS-1$
		}
		if( locale.equals(locale_EN) ) {
			BUNDLE_NAME = "fr.irstea.adret.geographlab.plugins.lang.messages_en_EN"; //$NON-NLS-1$
		}
		
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	}
	
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}