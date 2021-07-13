package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import beans.Arc;
import beans.Graph;
import beans.Sommet;

public class Model implements PropertyChangeListener{
	
	private List<Sommet> sommets = null;
	private List<Arc> arcs = null;
	private Graph graphe = null;
	
	private Map<Integer,String> sommetIds = new HashMap<Integer,String>();
	private Map<Integer,String> arcIds = new HashMap<Integer,String>();
	
	
	public Model() {
		sommets = new ArrayList<Sommet>();
		arcs = new ArrayList<Arc>();
	}
	
	public void supprimerSommet(Sommet sommet) {
		
		int idAsupprimer = sommet.getId();
		sommetIds.remove(idAsupprimer);
		
		Set<Integer> set = sommetIds.keySet();
		Object[] ids = set.toArray();
		
		int old = 0;
		Map<Integer,String> inter = new HashMap<Integer,String>();
		
		for(Object o:ids) {
			
			int actuel = (int)o;
			String valeur = sommetIds.get(actuel);
		
			if(actuel-old > 1) {
				old = old + 1;
			}else {
				old = actuel;
			}
			
			inter.put(old, valeur);
			
		}
		
		sommetIds.clear();
		sommetIds = inter;
		
		this.sommets.remove(sommet);
		
		reorganiserSommets();
		
	}

	public List<Sommet> getSommets() {
		return this.sommets;
	}
	
	public void supprimerArc(Arc arc) {
		
		int idAsupprimer = arc.getId();
		arcIds.remove(idAsupprimer);
		
		Set<Integer> set = arcIds.keySet();
		Object[] ids = set.toArray();
		
		int old = 0;
		Map<Integer,String> inter = new HashMap<Integer,String>();
		
		for(Object o:ids) {
			
			int actuel = (int)o;
			String valeur = arcIds.get(actuel);
			
			if(actuel - old > 1) {	
				old = old + 1;
			}else {
				old = actuel;
			}
			
			inter.put(old, valeur);
		}
		
		arcIds.clear();
		arcIds = inter;
		
		this.arcs.remove(arc);
		
		reorganiserArcs();
	}
	
	public int genererSommetId() {
		
		int dernierId = sommetIds.size()-1;
		
		if(dernierId != -1) {
			return dernierId+1;
		}else {	
			return 0;
		}
				
	}
	
	public int genererArcId() {
		
		int dernierId = arcIds.size()-1;
		
		if(dernierId != -1) {
			return dernierId+1;
		}else {	
			return 0;
		}
				
	}
	
	public void ajouterSommet(int idSommet,String idNoeud,Sommet sommet) {
		sommetIds.put(idSommet,idNoeud);
		sommets.add(sommet);
	}
	
	public void ajouterArc(int idArc,String idLien,Arc arc) {
		arcIds.put(idArc, idLien);
		arcs.add(arc);
	}
	
	public String getNoeud(int idSommet) {
		return sommetIds.get(idSommet);
		
	}
	
	public String getLien(int idArc) {
		return arcIds.get(idArc);
		
	}
	
	public void getsommetIds() {
		
	}
	
	public List<Arc> getArcs() {	
		return arcs;
	}
	
	public void getArcIds() {
		
	}
	
	public void reorganiserSommets() {
		
		for(Sommet sommet:sommets) {
			sommet.setId(sommets.indexOf(sommet));
		}
	}
	
	public void reorganiserArcs() {
		for(Arc arc:arcs) {
			arc.setId(arcs.indexOf(arc));
		}
	}
	
	public Graph construireGraphe() {
		
		this.graphe = new Graph(sommets,arcs);
		return this.graphe;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		
	}

}
