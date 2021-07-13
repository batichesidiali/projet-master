package ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.DataFormat;
import javafx.util.Pair;

public class Conteneur implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static DataFormat NOEUD = new DataFormat("ui.Noeud");
	
	List<Pair<String,Object>> params = new ArrayList<Pair<String,Object>>();
	
	public void addParam (String cle, Object valeur) {
		params.add(new Pair<String, Object>(cle,valeur));		
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getParam (String key) {
		
		for (Pair<String, Object> data: params) {
			
			if (data.getKey().equals(key))
				return (T) data.getValue();		
		}
		
		return null;
	}
	
	public List <Pair<String, Object> > getAllParams () { 
		return params; 
	}

}
