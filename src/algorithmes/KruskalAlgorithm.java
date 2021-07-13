package algorithmes;

import java.util.Arrays;

import beans.Arc;
import beans.Graph;
import beans.SousGraphe;


public class KruskalAlgorithm {
	
	  private int find(SousGraphe[] subsets, int i) {
		    if (subsets[i].parent != i)
		      subsets[i].parent = find(subsets, subsets[i].parent);
		    return subsets[i].parent;
	  }

	  private void Union(SousGraphe[] subsets, int x, int y) {
		  
		    int xroot = find(subsets, x);
		    int yroot = find(subsets, y);

		    if (subsets[xroot].rank < subsets[yroot].rank)
		      subsets[xroot].parent = yroot;
		    else if (subsets[xroot].rank > subsets[yroot].rank)
		      subsets[yroot].parent = xroot;
		    else {
		      subsets[yroot].parent = xroot;
		      subsets[xroot].rank++;
		    }
	  }

	  
	  public Arc[] executer(Graph graphe) {
		  
		  	int nbrVertexes = graphe.getSommets().size();
		  	Arc[] tableauEdges = graphe.getTableauArcs();
		  			
		    Arc result[] = new Arc[nbrVertexes];
		    int e = 0;
		    int i = 0;
		    for (i = 0; i < nbrVertexes; ++i)
		      result[i] = new Arc(0,null,null,0);

		    Arrays.sort(tableauEdges);
		    SousGraphe subsets[] = new SousGraphe[nbrVertexes];
		    for (i = 0; i < nbrVertexes; ++i)
		      subsets[i] = new SousGraphe();

		    for (int v = 0; v < nbrVertexes; ++v) {
		      subsets[v].parent = v;
		      subsets[v].rank = 0;
		    }
		    i = 0;
		    while (e < nbrVertexes -1) {
		      Arc next_edge = null;
		      next_edge = tableauEdges[i++];
		      int x = find(subsets, next_edge.getIdSource());
		      int y = find(subsets, next_edge.getIdDestination());
		      if (x != y) {
		        result[e++] = next_edge;
		        Union(subsets, x, y);
		      }
		    }
		    
		    return result;
	  }

}
