/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * Main.java in fr.ign.cogit.geographlab.testing
 * 
 */
package fr.ign.cogit.geographlab.testing;

/**
 * @author eric
 *
 */
public class Main {
	static class JoinerThread extends Thread
	{
		public int id;
		public int result;
		@Override
		public void run()
		{
			for(int i=0; i<999999999; i++){
				Math.pow(i,2);
			}
			
			result = id;
		}
	}
	public static void main( String[] args ) throws InterruptedException
	{
		int processors = 3;
		JoinerThread threads [] = new JoinerThread[processors];
		for(int i=0;i<processors;i++){
			threads[i] = new JoinerThread();
			threads[i].id = i;
			threads[i].start();
		}
		for(int i=0;i<processors;i++){
			threads[i].join();
			System.out.println("gnaa");
		}
		
		for(int i=0;i<processors; i++){
			System.out.println( threads[i].result );
		}
	}
}