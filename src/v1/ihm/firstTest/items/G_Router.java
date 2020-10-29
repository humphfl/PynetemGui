package v1.ihm.firstTest.items;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import v1.materials.priv.Router;

/**
 * Classe graphique représentant un router
 */
public class G_Router extends G_Node {


    //éléments graphiques du routeur
    private final Circle contour = new Circle();

    /**
     * Constructeur de l'élément graphique routeur
     *
     * @param name : nom
     * @param x    : position X
     * @param y    : position Y
     */
    public G_Router(String name, double x, double y) {
        super(new Router(""), x, y);

        makeVisual();

    }
    //************************************************************************
    //*    Extends G_Node                                                    *
    //************************************************************************

    /**
     * méthode dans laquelle on crée le visuel de l'objet
     */
    @Override
    public void makeVisual() {
        this.contour.radiusProperty().setValue(40);
        this.contour.setFill(Color.LIGHTBLUE);
        this.contour.setTranslateX(0.0f);
        this.contour.setTranslateY(0.0f);
        this.contour.toBack();
        getChildren().add(contour);
    }

    /**
     * méthode de mise en place des visuels des interfaces
     */
    @Override
    public void setIfPos() {
        for (G_Connect gIf : this.getIfLst()) {
            gIf.setCenterX(this.contour.getRadius() *
                    Math.cos(gIf.getIf().getIntIndex() * 2 * Math.PI / getIfLst().size()));
            gIf.setCenterY(this.contour.getRadius() *
                    Math.sin(gIf.getIf().getIntIndex() * 2 * Math.PI / getIfLst().size()));
        }
    }

}
