/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * SplashScreen.java in fr.ign.cogit.geographlab
 * 
 */
package fr.ign.cogit.geographlab;

/**
 * @author eric
 *
 */
import javax.swing.JWindow;
import javax.swing.ImageIcon;

import fr.ign.cogit.geographlab.ihm.constantes.ConstantesImages;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class SplashScreen extends JWindow
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Image img=Toolkit.getDefaultToolkit().getImage(ConstantesImages.splashScreen);
	
	ImageIcon imgicon=new ImageIcon(this.img);
	
	public SplashScreen()
	{
		try
		{

			setSize(633,300);
			setLocationRelativeTo(null);
			setVisible(true);
			Thread.sleep(2500);
			dispose();
//			javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
//					null,"Welcome", "Welcome Screen:",
//					javax.swing.JOptionPane.DEFAULT_OPTION);
		}
		catch(Exception exception)
		{
			javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
					null,"Error"+exception.getMessage(), "Error:",
					javax.swing.JOptionPane.DEFAULT_OPTION);
		}
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(this.imgicon.getImage(),0,0,this);
	}

}
