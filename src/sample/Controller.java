package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private Pane dessin_pane;

    @FXML
    private Circle cercle_int;
    @FXML
    private Label label_int;

    @FXML
    private Circle cercle_ext;
    @FXML
    private Label label_ext;

    @FXML
    private Label largeur_bucket;
    @FXML
    private Label largeur_bucket_decoupe;

    @FXML
    private ComboBox<Float> diametre_combobox;
    @FXML
    private ComboBox<Integer> bordure_combobox;
    @FXML
    private ComboBox<Integer> hauteur_combobox;
    @FXML
    private ComboBox<Integer> largeur_bandouliere_combobox;

    @FXML
    private SVGPath seau_svgpath;

    @FXML
    private Label angle1_label;

    @FXML
    private Label angle2_label;

    private ObservableList<Float> tailles;
    private ObservableList<Integer> bordures;
    private ObservableList<Integer> hauteurs;
    private ObservableList<Integer> largeurs_bandouliere;

    private BucketBody bucket;

    private float rayon;
    private float bordure;
    private int hauteur;
    private float largeur_bretelle;

    private Line axisBretelleLeft;
    private Line axisBretelleRight;
    private Line axis_x;

    private Rectangle bucket_decoupe;

    @Override
    public void initialize(URL u, ResourceBundle rb){
        cercle_ext.setStyle("-fx-stroke-dash-array: 2 12 12 2;");

        tailles = FXCollections.observableArrayList();
        tailles.addAll(20.0F, 20.5F, 21.0F, 21.5F, 22.0F);
        diametre_combobox.setItems(tailles);
        diametre_combobox.getSelectionModel().select(2);

        bordures = FXCollections.observableArrayList();
        bordures.addAll(5, 6, 7, 8, 9, 10);
        bordure_combobox.setItems(bordures);
        bordure_combobox.getSelectionModel().select(1);

        hauteurs = FXCollections.observableArrayList();
        hauteurs.addAll(15, 16, 17, 18, 19, 20, 21, 22, 23);
        hauteur_combobox.setItems(hauteurs);
        hauteur_combobox.getSelectionModel().select(4);

        largeurs_bandouliere = FXCollections.observableArrayList();
        largeurs_bandouliere.addAll(5, 6, 7, 8, 9, 10);
        largeur_bandouliere_combobox.setItems(largeurs_bandouliere);
        largeur_bandouliere_combobox.getSelectionModel().select(1);

        diametre_combobox.setOnAction(a -> redraw());
        bordure_combobox.setOnAction(a -> redraw());
        hauteur_combobox.setOnAction(a -> redraw());
        largeur_bandouliere_combobox.setOnAction(a -> redraw());

        diametre_combobox.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (! "".equals(newValue)){
                    rayon = Float.parseFloat(newValue) / 2;
                    redraw();
                }

            }
        });

        bordure_combobox.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (! "".equals(newValue)){
                    bordure = Float.parseFloat(newValue);
                    redraw();
                }

            }
        });

        hauteur_combobox.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (! "".equals(newValue)){
                    hauteur = Integer.parseInt(newValue) * 10;
                    redraw();
                }

            }
        });

        largeur_bandouliere_combobox.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                System.out.println(newValue);

                if (! "".equals(newValue)){
                    largeur_bretelle = Float.parseFloat(newValue) * 10;
                    redraw();
                }

            }
        });

        axis_x = new Line();
        axisBretelleLeft = new Line();
        axisBretelleRight = new Line();
        bucket_decoupe = new Rectangle();

        dessin_pane.getChildren().addAll(bucket_decoupe, axis_x, axisBretelleLeft, axisBretelleRight);

        //redraw();
    }

    public void redraw(){

        cercle_int.setRadius(rayon * 20);
        cercle_ext.setRadius(rayon * 20 + bordure);

        label_int.setText(String.format("diamètre : %2.2f cm\nrayon : %2.2f cm", rayon * 2, rayon));
        label_ext.setText(String.format("diamètre : %2.2f cm\nrayon : %2.2f cm", rayon * 2 + bordure * 2 / 10, rayon + bordure / 10));

        bucket = new BucketBody(77, 77 + 2 * Math.PI * rayon * 20, 255, 255 + hauteur * 2, largeur_bretelle, bordure);
        seau_svgpath.setContent(bucket.toString());

        bucket_decoupe.setSmooth(true);
        bucket_decoupe.setStroke(Color.BLACK);
        bucket_decoupe.setStrokeWidth(1);
        bucket_decoupe.setStyle("-fx-stroke-dash-array: 12 12 12 12;");
        bucket_decoupe.setFill(Color.valueOf("c9dcee"));
        bucket_decoupe.setHeight(hauteur * 2);
        bucket_decoupe.setWidth(2 * Math.PI * rayon * 20 + bordure * 2);
        bucket_decoupe.setLayoutX(77 + seau_svgpath.getLayoutX() - bordure);
        bucket_decoupe.setLayoutY(255 + seau_svgpath.getLayoutY());
        bucket_decoupe.toBack();

        largeur_bucket.setText(String.format("largeur avant marge\n= %.2f cm", 2 * Math.PI * rayon));
        largeur_bucket_decoupe.setText(String.format("largeur avec marge\n= %.2f cm", 2 * Math.PI * rayon + 2 * bordure / 10));

        axis_x.setStartX(seau_svgpath.getLayoutX() + bucket.getAxis_x());
        axis_x.setEndX(seau_svgpath.getLayoutX() + bucket.getAxis_x());
        axis_x.setStartY(seau_svgpath.getLayoutY() + 255);
        axis_x.setEndY(seau_svgpath.getLayoutY() + 255 + hauteur * 2);
        axis_x.setSmooth(true);
        axis_x.setStyle("-fx-stroke-dash-array: 12 12 12 12;");

        axisBretelleLeft.setStartX(seau_svgpath.getLayoutX() + bucket.getAxisBandouliereLeft());
        axisBretelleLeft.setEndX(seau_svgpath.getLayoutX() + bucket.getAxisBandouliereLeft());
        axisBretelleLeft.setStartY(seau_svgpath.getLayoutY() + 205);
        axisBretelleLeft.setEndY(seau_svgpath.getLayoutY() + 255 + hauteur * 2);
        axisBretelleLeft.setSmooth(true);
        axisBretelleLeft.setStyle("-fx-stroke-dash-array: 12 12 12 12;");

        axisBretelleRight.setStartX(seau_svgpath.getLayoutX() + bucket.getAxisBandouliereRight());
        axisBretelleRight.setEndX(seau_svgpath.getLayoutX() + bucket.getAxisBandouliereRight());
        axisBretelleRight.setStartY(seau_svgpath.getLayoutY() + 205);
        axisBretelleRight.setEndY(seau_svgpath.getLayoutY() + 255 + hauteur * 2);
        axisBretelleRight.setSmooth(true);
        axisBretelleRight.setStyle("-fx-stroke-dash-array: 12 12 12 12;");

        angle1_label.setText(String.format("%.2f cm" , (bucket.getAbsoluteAxisBandouliereLeft() - largeur_bretelle / 2 + bordure) / 10));
        angle2_label.setText(String.format("%.2f cm" , (bucket.getAbsoluteAxisBandouliereLeft() + largeur_bretelle / 2 + bordure) / 10));
    }

}


