package model;

import beans.Arc;
import beans.Sommet;
import controlers.AcceuilControler;

public class LienModel {
	
	private Arc arc;
	private String idLien;
	
	public LienModel(String idLien) {
		
		this.idLien = idLien;
		int id = AcceuilControler.getModel().genererArcId();
		this.arc = new Arc(id,null,null,2);
		AcceuilControler.getModel().ajouterArc(id, idLien,arc);
	}

	public Arc getArc() {
		return arc;
	}

	public void setArc(Arc arc) {
		this.arc = arc;
	}
	
	public void initArc(Sommet source,Sommet destination,int poids) {
		arc.setSource(source);
		arc.setDestination(destination);
		arc.setPoids(poids);
	}

	public String getIdLien() {
		return idLien;
	}
	
	public void setPoids(int poids) {
		arc.setPoids(poids);
	}

}
