package ui;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import beans.Sommet;
import controlers.AcceuilControler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import model.Model;
import model.NoeudModel;

public class Noeud extends AnchorPane implements Serializable,Initializable{
	
	private static final long serialVersionUID = 1L;
	
	@FXML AnchorPane pan_racine;
	@FXML AnchorPane pan_gauche;
	@FXML AnchorPane pan_droite;
	@FXML AnchorPane pan_centre;
	@FXML Label bare_titre;
	@FXML Label btn_fermer;
	
	
	private Point2D position = new Point2D(0.0,0.0);
	
	private IconeType type;
	private List<String> liensIds = new ArrayList<String>();
	
	private NoeudModel model;
	
	
	public Noeud() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/Noeud.fxml")
				);
		
		fxmlLoader.setRoot(this); 
		fxmlLoader.setController(this);
		try { 
			fxmlLoader.load();
        
		} catch (IOException exception) {
		    throw new RuntimeException(exception);
		}

		setId(UUID.randomUUID().toString());
		this.setModel(new NoeudModel(getId()));
		bare_titre.setText("Capteur "+model.getSommet().getId());
	}
	
	/**
	 * Repositionner l'objet Noeud sur une nouveau endroit sur la fenetre
	 * 
	 * @param p le point ou l'objet sera repositionné
	 */
	public void repositionnerA (Point2D p) {

		Point2D localCoords = getParent().sceneToLocal(p);
		
		relocate ( 
				(int) (localCoords.getX() - position.getX()),
				(int) (localCoords.getY() - position.getY())
			);
	}
	
	@FXML
	public void onFermer() {
		
		AnchorPane parent = (AnchorPane)getParent();
		Model modelApp = AcceuilControler.getModel();
		
		Sommet sommet = model.getSommet();
	
		List<Lien> liensAsupprimer = new ArrayList<Lien>();
		
		for(Node node:parent.getChildren()) {
			if(node.getClass() == Lien.class) {
				Lien lien = (Lien)node;
				
				if(lien.getSource() == this) {
					modelApp.supprimerArc(lien.getModel().getArc());
					liensAsupprimer.add(lien);
				}
				
				if(lien.getDestination() == this) {
					modelApp.supprimerArc(lien.getModel().getArc());
					liensAsupprimer.add(lien);
				}
				
			}
		}
		
		modelApp.supprimerSommet(sommet);
		
		for(Lien lien:liensAsupprimer) {
			parent.getChildren().remove(lien);
		}
		
		parent.getChildren().remove(this);
		
	}
	
	@FXML
	public void onDragDetected(MouseEvent event) {
		
		ClipboardContent content = new ClipboardContent();
		
		Conteneur conteneur = new Conteneur();
		conteneur.addParam("source","Noeud");
		
		content.put(Conteneur.NOEUD,conteneur);
		
		bare_titre.startDragAndDrop(TransferMode.ANY).setContent(content);
		
		AcceuilControler.invisibleNoeud = this;
		position = new Point2D(event.getX(), event.getY());
		repositionnerA(
        		new Point2D(event.getSceneX(), event.getSceneY())
        		);
		
	}
	
	@FXML
	public void onPanLienDragDetected(MouseEvent event) {
		
		AnchorPane pane = (AnchorPane)event.getSource();
		AnchorPane parent = (AnchorPane)getParent();
		
		Conteneur conteneur = new Conteneur();
		
		Lien lien = new Lien();
		
		conteneur.addParam("source","Lien");
		conteneur.addParam("noeudsource",this.getId());
		conteneur.addParam("lien",lien.getId());
		
		ClipboardContent content = new ClipboardContent();
		content.put(Conteneur.NOEUD, conteneur);
		
		
		pane.startDragAndDrop(TransferMode.ANY).setContent(content);

		
		parent.getChildren().add(0,lien);
		AcceuilControler.invisibleLien = lien;
		
		
		Point2D p = new Point2D(
				getLayoutX() + (getWidth() / 2.0),
				getLayoutY() + (getHeight() / 2.0)
				);
		lien.setStart(p);
	}	
	
	@FXML
	public void onPanLienDragDropped(DragEvent event) {
		
		AnchorPane parent = (AnchorPane)getParent();
		
		Conteneur conteneur = (Conteneur)event.getDragboard().getContent(Conteneur.NOEUD);
		
		if(conteneur.getParam("noeudsource") != null) {
		
			conteneur.addParam("noeuddestination",getId());
			
			ClipboardContent content = new ClipboardContent();
			content.put(Conteneur.NOEUD, conteneur);
			
			Noeud source = null;
			
			for(Node noeud:parent.getChildren()) {
				if(noeud.getId().equals(conteneur.getParam("noeudsource"))){
					source = (Noeud)noeud;
				}
			}
			
			AcceuilControler.invisibleLien.relierPositionAuNoeuds(source,this);
			
			event.getDragboard().setContent(content);
			event.setDropCompleted(true);
		}
		
	}

	public IconeType getType() {
		return type;
	}

	public void setType(IconeType type) {
		
		this.type = type;	
		
		getStyleClass().clear();
		getStyleClass().add("noeud");
		
		switch (type) {
		
		case CAPTEUR:
			getStyleClass().add("noeud-capteur");
		break;
		}
	}

	public void CreerLien(String id) {
		liensIds.add(id);
		
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	public NoeudModel getModel() {
		return model;
	}

	public void setModel(NoeudModel model) {
		this.model = model;
	}
	
}
