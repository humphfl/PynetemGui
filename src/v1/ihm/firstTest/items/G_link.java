package v1.ihm.firstTest.items;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

//TODO ajouter les entetes de section
/**
 * affichage graphique des connexions entre équipements
 */
public class G_link extends Line {


    // Les 2 bouts du lien
    private final G_Connect if_1;
    private final G_Connect if_2;


    public G_link(G_Connect if1, G_Connect if2) {
        super();
        this.if_1 = if1;
        this.if_2 = if2;
        System.out.println("Ifs:[" + if1.getIf() + ":" + if2.getIf() + "]");

        createVisu();
        addListeners();
        update();


    }

    private void addListeners() {
        this.if_1.translateXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                update();

            }
        });
        this.if_1.translateYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                update();
            }
        });
        this.if_2.translateXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                update();
            }
        });
        this.if_2.translateYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                update();
            }
        });
    }

    private void createVisu() {
        setFill(Color.BLACK);
        setStrokeWidth(3.0f);
    }

    private void update() {
        setStartX(if_1.getTranslateX());
        setStartY(if_1.getTranslateY());
        //setEndX(if_2.getTranslateX() + if_2.getAbsoluteX() - if_1.getAbsoluteX() - if_1.getTranslateX());
        setEndX(if_2.getTranslateX() + if_2.getAbsoluteX() - if_1.getAbsoluteX() );
        //setEndY(if_2.getTranslateY() + if_2.getAbsoluteY() - if_1.getAbsoluteY() - if_1.getTranslateY());
        setEndY(if_2.getTranslateY() + if_2.getAbsoluteY() - if_1.getAbsoluteY() );

    }


}
