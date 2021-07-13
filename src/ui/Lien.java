package ui;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import model.LienModel;

public class Lien extends Group implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@FXML Line ligne_lien;
	@FXML Text lbl_poids;
	
	TextField fld_poids = new TextField();
	
	private Noeud source = null;
	private Noeud destination = null;
	
	
	private LienModel model;


	public Lien() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/Lien.fxml")
				);
		
		fxmlLoader.setRoot(this); 
		fxmlLoader.setController(this);

		try { 
			fxmlLoader.load();
        
		} catch (IOException exception) {
		    throw new RuntimeException(exception);
		}
		
		setId(UUID.randomUUID().toString());
		this.model = new LienModel(getId());
	
	}
	
	@FXML
	private void initialize() {
		

		fld_poids.getStyleClass().add("fld-poids");
			
		lbl_poids.setOnMouseClicked((e) -> {
			AnchorPane parent = (AnchorPane)getParent();

			if(!parent.getChildren().contains(fld_poids)){
			parent.getChildren().add(fld_poids);
			fld_poids.setPrefWidth(30.0);
			fld_poids.setPrefHeight(15.0);
			fld_poids.setVisible(false);
			fld_poids.setLayoutX(lbl_poids.getX());
			fld_poids.setLayoutY(lbl_poids.getY()-10.0);
			fld_poids.setPromptText(lbl_poids.getText());
		
			fld_poids.setVisible(true);
			
			parent.getChildren().forEach((n)-> {
				if(!n.equals(fld_poids)){
					n.setDisable(true);
				}
			});
			}
			
		});
		
		fld_poids.setOnMouseExited((event)->{
			
			AnchorPane parent = (AnchorPane)getParent();
			
			parent.setOnMouseClicked((e2)-> {
				
			parent.getChildren().forEach((n)->{n.setDisable(false);});
			
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher matcher = pattern.matcher(fld_poids.getText());
			
			if(fld_poids.getText()!="" & matcher.matches() ) {
				model.setPoids(Integer.parseInt(fld_poids.getText()));
				lbl_poids.setText(fld_poids.getText());
			}else {
				fld_poids.setPromptText(lbl_poids.getText());
			}
			
			parent.getChildren().remove(fld_poids);
			parent.setOnMouseClicked(null);
			});
		});
		
		
		
	}

	public void setStart(Point2D p) {
		ligne_lien.setStartX(p.getX());
		ligne_lien.setStartY(p.getY());
	}
	
	public void setEnd(Point2D p) {
		ligne_lien.setEndX(p.getX());
		ligne_lien.setEndY(p.getY());
	}
	
	public void relierPositionAuNoeuds(Noeud source,Noeud destination) {
		
		ligne_lien.startXProperty().bind(source.layoutXProperty().add(32.0));
		ligne_lien.startYProperty().bind(source.layoutYProperty().add(32.0));
		
		ligne_lien.endXProperty().bind(destination.layoutXProperty().add(32.0));
		ligne_lien.endYProperty().bind(destination.layoutYProperty().add(32.0));
		

		lbl_poids.xProperty().bind(ligne_lien.startXProperty().add(ligne_lien.endXProperty()).divide(2.0));
		lbl_poids.yProperty().bind(ligne_lien.endYProperty().add(ligne_lien.startYProperty()).divide(2.0).add(15.0));
		
		
	}
	
	public Line getLine() {
		return ligne_lien;
	}
	
	public Text getText() {
		return lbl_poids;
	}

	public LienModel getModel() {
		return model;
	}

	public void setModel(LienModel model) {
		this.model = model;
	}

	public Noeud getSource() {
		return source;
	}

	public void setSource(Noeud source) {
		this.source = source;
	}

	public Noeud getDestination() {
		return destination;
	}

	public void setDestination(Noeud destination) {
		this.destination = destination;
	}

}
