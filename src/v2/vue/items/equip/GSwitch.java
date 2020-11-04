package v2.vue.items.equip;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import v2.controller.APIController;
import v2.vue.items.abstracts.AbstractItem;
import v2.vue.items.abstracts.Pics;


public class GSwitch extends AbstractItem {

    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************

    private ImageView pic;

    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************
    public GSwitch(String name, Group rt, APIController ctrl) {
        super(name, rt, ctrl);
        getController().createSwitch(name);

    }


    //******************************************************************************************************************
    //*                          INHERITED METHODS                                                                     *
    //******************************************************************************************************************

    /**
     * Implementation de la création de la partie visuelle de l'objet
     * (l'appel de cette fonction se fait dans la superclasse)
     */
    @Override
    protected void createVisu() {

        pic = Pics.SWITCH.getImg();

        setCenterRef(pic);

        //Ajout des éléments
        this.getChildren().addAll(pic);

        //appel de la méthode de la classe super
        super.createVisu();


        //*********************************************
        //*  TESTS SECTION                            *
        //*********************************************


        //createTestVisu();

        //*********************************************
    }

    //******************************************************************************************************************
    //*                          GETTERS METHODS                                                                       *
    //******************************************************************************************************************

    //******************************************************************************************************************
    //*                          SETTERS METHODS                                                                       *
    //******************************************************************************************************************
    //******************************************************************************************************************
    //*                          PUBLIC METHODS                                                                        *
    //******************************************************************************************************************
    //******************************************************************************************************************
    //*                          PRIVATE METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Visu de test
     */
    private void createTestVisu() {
        //Line ln = new Line(getCenter().getX(), getCenter().getY(), getCenter().getX() + 300, getCenter().getY() + 300);

        //this.getChildren().add(ln);
        //ln.toBack();

        // Création du rectangle de bound
        Node toBound = this;
        Rectangle rect = new Rectangle(0, 0, toBound.getLayoutBounds().getWidth(), toBound.getLayoutBounds().getHeight());
        Rectangle clip = new Rectangle(1, 1, toBound.getLayoutBounds().getWidth() - 2, toBound.getLayoutBounds().getHeight() - 2);
        //System.out.println(String.format("Image size : [%03f;%03f]",toBound.getLayoutBounds().getWidth(), toBound.getLayoutBounds().getMaxY()));
        //System.out.println(toBound.getBoundsInLocal());
        Shape shape = Shape.subtract(rect, clip);
        shape.setFill(Color.BLACK);
        // Ajout du rectangle de bounds (mettre setVisible à true pour le voir)
        shape.setVisible(true);
        this.getChildren().add(shape);
        shape.toFront();

        Circle center = new Circle(5);
        center.setFill(Color.RED);
        this.getChildren().add(center);
        center.toFront();
        center.setTranslateX(getCenter().getX());
        center.setTranslateY(getCenter().getY());


    }
}
