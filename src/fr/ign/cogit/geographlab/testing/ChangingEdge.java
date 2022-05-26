/**
 * GeoGraphLab - eric - Cogit / IGN
 * 2007 - 2013
 *
 * ChangingEdge.java in fr.ign.cogit.geographlab.testing
 * 
 */
package fr.ign.cogit.geographlab.testing;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author eric
 *
 */
public class ChangingEdge {
	
	public ChangingEdge() {
		
	}
	
	 public static void main(String [] args)
	    {
		 
		 DirectedGraph<String, DefaultEdge> g =
		            new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
			
			String VertexA = new String("A");
			String VertexB = new String("B");
			String VertexC = new String("C");
			String VertexD = new String("D");
			
			g.addVertex(VertexA);
			g.addVertex(VertexB);
			g.addVertex(VertexC);
			g.addVertex(VertexD);
			
			g.addEdge(VertexA, VertexB);
			g.addEdge(VertexB, VertexC);
			g.addEdge(VertexC, VertexD);
			g.addEdge(VertexD, VertexA);
	        
	        System.out.println("Noeuds : ");
	        for (String vertex : g.vertexSet()) {
				System.out.println(vertex + " " + vertex.hashCode());
			}
	        
	        System.out.println("Arcs : ");
	        for (DefaultEdge edge : g.edgeSet()) {
				System.out.println(edge + " " + edge.hashCode());
			}
	        
	    }
	
}
