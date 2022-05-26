/**
 * GeoGraphLab - Eric Mermet - Cogit / IGN 2007 - 2010
 * 
 * ${file_name} in ${package_name}
 * 
 */

package fr.ign.cogit.geographlab.algo.operations;

import java.util.Collection;

public class OperationsEnsemblistes {
	
	public static Collection<Object> intersection(Collection<Object> a, Collection<Object> b) {
		
		a.retainAll(b);
		
		return a;
	}
	
	public static Collection<Object> union(Collection<Object> a, Collection<Object> b) {
		
		a.addAll(b);
		
		return a;
		
	}
}
