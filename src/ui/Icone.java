package ui;

import java.io.IOException;
import java.io.Serializable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

/**
 * Icone qui permet de créer un noeud dans le graphe<br> 
 * elle est manipulée par glisser déposer dans le panneau principal
 *
 * @author Elaldja.Gasmi
 *
 */
public class Icone extends AnchorPane implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@FXML AnchorPane pan_root;
	
	private IconeType type = null;
	
	/**
	 * Constructeur : crée un nouveau Objet Icone
	 */
	public Icone() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/Icone.fxml")
				);
		
		fxmlLoader.setRoot(this); 
		fxmlLoader.setController(this);
		
		try { 
			fxmlLoader.load();
        
		} catch (IOException exception) {
		    throw new RuntimeException(exception);
		}
	}
	
	@FXML
	private void initialize() {
		
	}
	
	
	public IconeType getType () {
		return type; 
	}
	
	/**
	 * Mettre le type de l'icone a créer
	 * 
	 * @param type le type de l'objet Icone<br>
	 *        Exemple : CAPTEUR
	 */
	public void setType (IconeType type) {
		
		this.type = type;
		
		getStyleClass().clear();
		getStyleClass().add("icon");
		
		//added because the cubic curve will persist into other icons
		if (this.getChildren().size() > 0)
			getChildren().clear();
		
		switch (type) {

		case CAPTEUR:
			getStyleClass().add("icon-capteur");
		break;
		
		default:
		break;
		}
	}
	
	/**
	 * Repositionner l'objet icone sur une nouveau endroit sur la fenetre
	 * 
	 * @param p le point ou l'objet sera repositionné
	 */
	public void repositionnerA (Point2D p) {

		Point2D localCoords = getParent().sceneToLocal(p);
		
		relocate ( 
				(int) (localCoords.getX() - (getBoundsInLocal().getWidth() / 2)),
				(int) (localCoords.getY() - (getBoundsInLocal().getHeight() / 2))
			);
	}
}
