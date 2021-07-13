package util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import ui.Lien;
import ui.Noeud;

public class VueUtil {
	
	public static Noeud trouverNoeudParId(AnchorPane pan,String id) {
		
		Noeud noeudATrouver = null;
		
		for(Node noeud : pan.getChildren()) {
			if(noeud.getId().equals(id)) {
				noeudATrouver = (Noeud)noeud;
			}
		}
		
		return noeudATrouver;
	}
	
	public static Lien trouverLienParId(AnchorPane pan,String id) {
		
		Lien LienATrouver = null;
		
		for(Node noeud : pan.getChildren()) {
			if(noeud.getId().equals(id)) {
				LienATrouver = (Lien)noeud;
			}
		}
		
		return LienATrouver;
	}

}