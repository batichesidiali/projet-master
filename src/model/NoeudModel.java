package model;

import beans.Sommet;
import controlers.AcceuilControler;

public class NoeudModel {
	
	private Sommet sommet;
	private String idNoeud;
	
	public NoeudModel(String idNoeud) {
		
		this.idNoeud = idNoeud;
		int id = AcceuilControler.getModel().genererSommetId();
		sommet = new Sommet(id,"Sommet "+id);
		AcceuilControler.getModel().ajouterSommet(id, idNoeud,sommet);
	}
	
	public String getIdNoeud() {
		return idNoeud;
	}

	public void setIdNoeud(String idNoeud) {
		this.idNoeud = idNoeud;
	}

	public Sommet getSommet() {
		return sommet;
	}

	public void setSommet(Sommet sommet) {
		this.sommet = sommet;
	}
	
	public void initSommet(String nom) {
		sommet.setName(nom);
	}

}
