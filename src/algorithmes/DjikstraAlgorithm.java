package algorithmes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import beans.Arc;
import beans.Graph;
import beans.Sommet;



public class DjikstraAlgorithm {

    @SuppressWarnings("unused")
	private final List<Sommet> nodes;
    private final List<Arc> edges;
    private Set<Sommet> settledNodes;
    private Set<Sommet> unSettledNodes;
    private Map<Sommet, Sommet> predecessors;
    private Map<Sommet, Integer> distance;

    public DjikstraAlgorithm(Graph graph) {
        
        this.nodes = new ArrayList<Sommet>(graph.getSommets());
        this.edges = new ArrayList<Arc>(graph.getArcs());
    }

    public void execute(Sommet source) {
        settledNodes = new HashSet<Sommet>();
        unSettledNodes = new HashSet<Sommet>();
        distance = new HashMap<Sommet, Integer>();
        predecessors = new HashMap<Sommet, Sommet>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Sommet node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Sommet node) {
        List<Sommet> adjacentNodes = getNeighbors(node);
        for (Sommet target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Sommet node, Sommet target) {
        for (Arc edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getPoids();
            }
        }
        throw new RuntimeException();
    }

    private List<Sommet> getNeighbors(Sommet node) {
        List<Sommet> neighbors = new ArrayList<Sommet>();
        for (Arc edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Sommet getMinimum(Set<Sommet> vertexes) {
        Sommet minimum = null;
        for (Sommet vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Sommet vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Sommet destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<Sommet> getPath(Sommet target) {
        LinkedList<Sommet> path = new LinkedList<Sommet>();
        Sommet step = target;
      
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
       
        Collections.reverse(path);
        return path;
    }

}
