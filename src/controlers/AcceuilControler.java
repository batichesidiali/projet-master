package controlers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import algorithmes.DjikstraAlgorithm;
import algorithmes.KruskalAlgorithm;
import beans.Arc;
import beans.Graph;
import beans.Sommet;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import model.Model;
import ui.Conteneur;
import ui.Icone;
import ui.IconeType;
import ui.Lien;
import ui.Noeud;
import util.VueUtil;

public class AcceuilControler implements Initializable{
	
	@FXML AnchorPane pan_principal;
	@FXML VBox pan_icones;
	@FXML AnchorPane pan_bas;
	@FXML Button btn_djikstra;
	@FXML Button btn_kruskal;
	
	Icone invisbleIcone = null;
	public static Noeud invisibleNoeud = null;
	public static Lien invisibleLien = null;
	
	ClipboardContent content = new ClipboardContent();
	
	private static Model model;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Label nomIcone = new Label("Capteur");
		nomIcone.setPadding(new Insets(0,0,0,10));
		Icone icon = new Icone();
		icon.setType(IconeType.CAPTEUR);
		addDragDetection(icon);
		pan_icones.getChildren().add(nomIcone);
		pan_icones.getChildren().add(icon);
		
		invisbleIcone = new Icone();
		invisbleIcone.setType(IconeType.CAPTEUR);
		pan_principal.getChildren().add(invisbleIcone);
		invisbleIcone.setOpacity(0.5);
		invisbleIcone.setVisible(false);
			
	
		Platform.runLater(()-> {
			model = new Model();
		});
	}
	
	@FXML 
	public void onDragOver(DragEvent event) {

		event.acceptTransferModes(TransferMode.ANY);
		Conteneur conteneur = (Conteneur) event.getDragboard().getContent(Conteneur.NOEUD);
		
		String source = conteneur.getParam("source");
		
		switch(source) {
		case "Icone" :
			invisbleIcone.repositionnerA(new Point2D(event.getSceneX(),event.getSceneY()));
			break;
		case "Noeud" :
			invisibleNoeud.repositionnerA(new Point2D(event.getSceneX(),event.getSceneY()));
			break;
		case "Lien" :
			invisibleLien.setEnd(new Point2D(event.getX(),event.getY()));
			break;	
		}
		
		event.consume();

	}
	
	@FXML 
	public void onDragDropped(DragEvent event) {
		
		Conteneur conteneur = (Conteneur)event.getDragboard().getContent(Conteneur.NOEUD);
		conteneur.addParam("localisation", new Point2D(event.getSceneX(),event.getSceneY()));
		
		content.put(Conteneur.NOEUD, conteneur);
	
		event.getDragboard().setContent(content);
		event.setDropCompleted(true);
	}
	
	@FXML 
	public void onDragDone(DragEvent event) {
		
		invisbleIcone.setVisible(false);
		
		Conteneur conteneur = (Conteneur)event.getDragboard().getContent(Conteneur.NOEUD);
		Point2D localisation = (Point2D)conteneur.getParam("localisation");
		Point2D nouvelleLocalisation = new Point2D(localisation.getX() - 32,
							localisation.getY() -32);
		
		String source = conteneur.getParam("source");
		
		if(source.equals("Icone")) {
				
			Noeud noeud = new Noeud();
			noeud.setType(conteneur.getParam("type"));
			
			pan_principal.getChildren().add(noeud);
		
			noeud.repositionnerA(nouvelleLocalisation);
		
			invisibleNoeud = noeud;
			
		}
		if(source.equals("Lien")) {
			
			String idDestination = conteneur.getParam("noeuddestination");
			String idSource = conteneur.getParam("noeudsource");
			
			if(idDestination == null) {
				model.supprimerArc(invisibleLien.getModel().getArc());
				pan_principal.getChildren().remove(invisibleLien);
			}else {
			
				Noeud noeudDestination = VueUtil.trouverNoeudParId(pan_principal,idDestination);
				Noeud noeudSource = VueUtil.trouverNoeudParId(pan_principal,idSource);
				
				invisibleLien.setDestination(noeudDestination);
				invisibleLien.setSource(noeudSource);
				
				invisibleLien.getModel().initArc(noeudSource.getModel().getSommet(),
						noeudDestination.getModel().getSommet(), 0);
				
				
				
			}
		}
		
	}
	
	private void addDragDetection(Icone icone) {
		
		icone.setOnDragDetected (new EventHandler <MouseEvent> () {

			@Override
			public void handle(MouseEvent event) {

				invisbleIcone.repositionnerA(new Point2D(event.getSceneX(),event.getSceneY()));
				
				Conteneur conteneur = new Conteneur();
				conteneur.addParam("type", invisbleIcone.getType());
				conteneur.addParam("source", "Icone");
				content.put(Conteneur.NOEUD,conteneur);

				invisbleIcone.startDragAndDrop (TransferMode.ANY).setContent(content);
				invisbleIcone.setVisible(true);
				invisbleIcone.setMouseTransparent(true);
				
			}
		});
	}	
	
	@FXML 
	public void onDjikstra() {
		
		Graph graphe = model.construireGraphe();
	
		DjikstraAlgorithm djikstra = new DjikstraAlgorithm(graphe);
		Sommet source = model.getSommets().get(0);
		Sommet dest = model.getSommets().get(model.getSommets().size()-1);
		
		djikstra.execute(source);
		List<Sommet> chemin = djikstra.getPath(dest);
		
		List<Sommet[]> paires = new ArrayList<Sommet[]>();
		for(int i=0;i<chemin.size()-1;i++) {
			Sommet[] tab = {chemin.get(i),chemin.get(i+1)};
			paires.add(tab);
		}
		
		List<String> liens = new ArrayList<String>();
		for(Arc e:graphe.getArcs()) {
			
			Sommet[] tab = {e.getSource(),e.getDestination()};
			boolean estDansLeChemin = paires.stream().anyMatch((paire)->paire[0].equals(tab[0]) 
					& paire[1].equals(tab[1]) );
			
			if(estDansLeChemin) {
				liens.add(model.getLien(e.getId()));
			}
			
		}		
		
		for(Node noeud : pan_principal.getChildren()) {
			
			if(noeud.getClass() == Lien.class) {
				Lien lien = (Lien)noeud;
			
				if(liens.stream().anyMatch((id)-> id.equals(lien.getId()))) {
					Line ligne_lien = lien.getLine();
					ligne_lien.setStyle("-fx-stroke: red;"
							+ "	-fx-stroke-width: 5px;");
				}
				
			}
			
		}
	}
	
	
	@FXML
	public void onKruskal() {
		
		Graph graphe = model.construireGraphe();
		
		KruskalAlgorithm algorithme = new KruskalAlgorithm();
		Arc[] arcs = algorithme.executer(graphe);
		
		for(Arc e:arcs) {
			System.out.println(e.getIdSource()+" -> "+e.getIdDestination());
		}
		
		List<Arc> liens = Arrays.asList(arcs);
		
		for(Arc e:liens) {
			System.out.println(e.getId());
			
		}
		
		for(Node noeud : pan_principal.getChildren()) {
			
			if(noeud.getClass() == Lien.class) {
				Lien lien = (Lien)noeud;
				
				if(liens.stream().anyMatch((id)-> id.equals(lien.getId()))) {
					Line ligne_lien = lien.getLine();
					ligne_lien.setVisible(false);
					ligne_lien.getStyleClass().clear();
					ligne_lien.getStyleClass().add("line-green");
				}
				
			}
		
		}
		
	}

	public static Model getModel() {
		return model;
	}
	
}
