package v1.ihm.firstTest.items;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import v1.materials.priv.abs.Connect;

public class G_Connect extends Parent {


    //******************************************************************
    //*  VARIABLES                                                     *
    //******************************************************************

    // élément du modèle
    private final Connect if_;

    // éléments visuel
    private Circle visu;

    //coordonnées absolues
    private double absoluteX;
    private double absoluteY;

    //******************************************************************
    //*  CONSTRUCTOR                                                   *
    //******************************************************************

    /**
     * Prend en paramètre un élément du modèle
     * @param If_ : <Connect>
     */
    public G_Connect(Connect If_){
        this.if_ = If_;
        createIfVisual();
    }


    //******************************************************************
    //*  GETTERS                                                       *
    //******************************************************************

    /**
     * Retourne le Connect (élément modèle)
     * @return : <Connect>
     */
    public Connect getIf(){
        return this.if_;
    }


    /**
     * renvoie la position Absolue de l'élément
     * @return : <double>
     */
    public double getAbsoluteX(){
        return this.absoluteX;
    }

    /**
     * renvoie la position Absolue de l'élément
     * @return : <double>
     */
    public double getAbsoluteY(){
        return this.absoluteY;
    }
    //******************************************************************
    //*  SETTERS                                                       *
    //******************************************************************

    /**
     * Met à jour les coordonnées absolues de l'interface
     * @param X : Abs X <double>
     * @param Y : Abs Y <double>
     */
    public void setAbsCoords(double X, double Y){
        this.absoluteX = X;
        this.absoluteY = Y;
    }

    //******************************************************************
    //*  PUBLIC METHODS                                                *
    //******************************************************************

    /**
     * Wrapping de la méthode setFill du Circle
     * @param c : la couleur <Color>
     */
    public void setFill(Color c) {
        this.visu.setFill(c);
    }

    /**
     * Wrapping de setCenterX
     * @param v : double
     */
    public void setCenterX(double v) {
        this.setTranslateX(v);
    }

    /**
     * Wrapping de setCenterY
     * @param v : double
     */
    public void setCenterY(double v) {
        this.setTranslateY(v);
    }

    //******************************************************************
    //*  PRIVATE METHODS                                               *
    //******************************************************************

    /**
     * Crée le visuel de l'interface
     *
     */
    private void createIfVisual() {
        this.visu = new Circle();
        this.visu.setRadius(5);
        this.visu.setFill(Color.BLACK);
        this.getChildren().add(visu);
    }

}
