package beans;

import java.util.List;

public class Graph { 

	private Arc[] tableauEdges;
	
	private final List<Sommet> sommets;
	private final List<Arc> arcs;

	public Graph(List<Sommet> vertexes, List<Arc> edges) {
	        this.sommets = vertexes;
	        this.arcs = edges;
	        
	        
	        int i = 0;
	        tableauEdges = new Arc[edges.size()];
	        for(Arc edge : edges) {
	        	tableauEdges[i] = edge;
	        	i++;
	        }
       
	}
	
	
	public List<Sommet> getSommets() {
	        return sommets;
	}

	public List<Arc> getArcs() {
	        return arcs;
	}


	public Arc[] getTableauArcs() {
		return tableauEdges;
	}

	  
	}
