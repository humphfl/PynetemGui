package v2.vue.items.abstracts;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import v2.controller.APIController;
import v2.vue.items.Link;
import v2.vue.items.menu.ContextMnu;

enum ChangeEvent {
    ADDIF,
    DELIF,
    LINKADD,
    LINKDEL,
    LINKCHANGE
}

/**
 * Item abstrait.
 * contient la position globale de l'élément et les évènements de drag N drop
 */
public abstract class AbstractItem extends Parent {

    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************

    //Le panel conteneur de cet élément
    private final Group root;
    //Nom de l'item
    private final Label lblName;

    //position de l'item
    private final DoubleProperty xPos = new SimpleDoubleProperty();
    private final DoubleProperty yPos = new SimpleDoubleProperty();

    //affichage/masquage de tous les labels
    private final BooleanProperty dispLabels = new SimpleBooleanProperty();

    //Map des ifs (if ; Link)
    private final ObservableMap<String, Link> ifs = FXCollections.observableHashMap();
    //Controller
    private final APIController controller;
    //variables pour le dragNdrop
    double initX, initY;
    private Node centerRef;
    private Point2D dragAnchor = new Point2D(0f, 0f);

    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************


    public AbstractItem(String name, Group rt, APIController controller) {
        super();

        this.root = rt;
        this.lblName = new Label(name);
        this.controller = controller;

        // Ajout des divers Listeners
        setListeners();

        //Création du visuel
        createVisu();

        //Mise en place du dragNdrop
        setDragNDrop();

        //Mise en place du menu contextuel
        setContextMenu();

        root.getChildren().add(this);

    }


    //******************************************************************************************************************
    //*                          PROTECTED METHODS                                                                     *
    //******************************************************************************************************************

    /**
     * Implementation de la création de la partie visuelle de l'objet
     */
    protected void createVisu() {


        // Création du label nom :
        //lblName = new Text();
        //lblName.setFont(new Font(10));
        //label1.setTranslateY(185);
        lblName.setVisible(true);
        lblName.toFront();

        //ajoute un listener au changement de bounds (mise à jour après le show)
        lblName.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> updateLabel());
        //Ajout du listener sur le changement de nom pour garder le label centré (horizontal)
        lblName.textProperty().addListener((observable, oldValue, newValue) -> updateLabel());

        // Ajout du listener sur le changement d'échelle de l'image pour garder le nom sous l'image (vertical)
        centerRef.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> updateLabel());

        this.getChildren().add(lblName);

    }

    //******************************************************************************************************************
    //*                          GETTERS METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Renvoie la propriété de position sur l'axe X
     *
     * @return : Xpos (DoubleProperty)
     */
    public DoubleProperty getXPos() {
        return this.xPos;
    }

    /**
     * Renvoie la propriété de position sur l'axe y
     *
     * @return : Ypos (DoubleProperty)
     */
    public DoubleProperty getYPos() {
        return this.yPos;
    }

    /**
     * Renvoie le centre de l'objet
     *
     * @return : Point2D
     */
    public Point2D getCenter() {
        return new Point2D(centerRef.getBoundsInLocal().getCenterX(), centerRef.getBoundsInLocal().getCenterY());
    }

    /**
     * Renvoie la référence servant au centre
     *
     * @return : <Node>
     */
    public Node getCenterRef() {
        return this.centerRef;
    }

    /**
     * Ajoute la référence pour le centre
     *
     * @param n : un Node
     */
    public void setCenterRef(Node n) {
        this.centerRef = n;
    }

    /**
     * Renvoie la position absolue du centre de l'item
     *
     * @return : <Point2D>
     */
    public Point2D getAbsoluteCenter() {
        return new Point2D(getCenter().getX() + getPos().getX(), getCenter().getY() + getPos().getY());
    }

    /**
     * Renvoie la position de l'item
     *
     * @return : <Point2D>
     */
    public Point2D getPos() {
        return new Point2D(this.xPos.getValue(), this.yPos.getValue());

    }

    /**
     * Renvoie la map des interfaces
     *
     * @return : <ObservableMap>
     */
    public ObservableMap<String, Link> getIfsMap() {
        return this.ifs;
    }

    /**
     * Renvoie le label
     *
     * @return : <Text>
     */
    public Label getLblName() {
        return this.lblName;
    }

    /**
     * Renvoie le premier lien entre cet élément et un autre passé en paramètre
     *
     * @param other : l'autre élément
     * @return : <Link> ou null
     */
    public Link getLink(AbstractItem other) {
        for (Link lk : this.getIfsMap().values()) {
            if (lk != null) {
                if (lk.getStart() == other || lk.getEnd() == other) {
                    return lk;
                }
            }
        }
        return null;
    }

    /**
     * Renvoie le controller
     *
     * @return : <APIController>
     */
    public APIController getController() {
        return this.controller;
    }

    public String getName() {
        return this.lblName.getText();
    }

    //******************************************************************************************************************
    //*                          SETTERS METHODS                                                                       *
    //******************************************************************************************************************


    /**
     * Renvoie la propriétée qui définie l'affichage ou non des labels des interfaces
     *
     * @return : <BooleanProperty>
     */
    public BooleanProperty getDispLabels() {
        return this.dispLabels;
    }

    /**
     * Met à jour la position sur l'axe X
     *
     * @param x : nouvelle position
     */
    public void setX(double x) {
        this.xPos.setValue(x);
    }

    /**
     * Met à jour la position sur l'axe Y
     *
     * @param y : nouvelle position
     */
    public void setY(double y) {
        this.yPos.setValue(y);
    }

    //******************************************************************************************************************
    //*                          PUBLIC METHODS                                                                        *
    //******************************************************************************************************************

    /**
     * supprime le terminal
     */
    public void destroy() {
        for (Link k : ifs.values()) {
            if (k != null) {
                k.destroy();
            }
        }
        controller.delTerm(getName());
        if(root.getChildren().contains(this)){
            root.getChildren().remove(this);
        }

    }

    /**
     * Ajoute une interface
     */
    public void addIf() {
        //System.out.println("AbstractItem : addIf");
        this.ifs.put("eth" + this.ifs.keySet().size(), null);

    }

    /**
     * Supprime une interface
     */
    public void delIf() {
        //System.out.println("AbstractItem : delIf");
        Link lk = ifs.get("eth" + (ifs.keySet().size() - 1));
        if (lk != null) {
            //Si l'interface était reliée on supprime le lien
            lk.destroy();
        }
        this.ifs.remove("eth" + (ifs.keySet().size() - 1));
    }

    /**
     * vérifie si un lien existe entre cette interface et une autre
     *
     * @param other : l'autre itnerface
     * @return <boolean>
     */
    public boolean isLinked(AbstractItem other) {
        for (Link lk : this.getIfsMap().values()) {
            if (lk != null) {
                if (lk.getStart() == other || lk.getEnd() == other) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * renomme le terminal
     *
     * @param newName : le nouveau nom à donner
     */
    public void rename(String newName) {
        //TODO : tester
        this.lblName.setText(controller.rename(lblName.getText(), newName));

    }
    //******************************************************************************************************************
    //*                          PRIVATE METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Fonction qui intercepte tous les changements de la table des interfaces
     *
     * @param ev    : type de changement
     * @param index : entrée dans la table qui a changé
     */
    private void onIfsChange(ChangeEvent ev, String index, Link changed) {
        //TODO : c'est ici que l'on doit faire la mise à jour du modele
        switch (ev) {
            case ADDIF -> //System.out.println("ADDIF : " + index);
                    //System.out.println(controller.addIf(this.lblName.getText()));
                    controller.addIf(this.lblName.getText());
            case DELIF -> //System.out.println("DELIF : " + index);
                    //System.out.println(controller.delIf(this.lblName.getText()));
                    controller.delIf(this.lblName.getText());
            case LINKADD -> {
                //System.out.println("LINKADD : " + index);
                String h1 = changed.getStart().getLblName().getText();
                String f1 = changed.getLabs()[0].getText();
                String h2 = changed.getEnd().getLblName().getText();
                String f2 = changed.getLabs()[1].getText();

                controller.createLink(h1, f1, h2, f2);
            }
            case LINKCHANGE -> {
            }
            case LINKDEL -> {
                //System.out.println("LINKDEL : " + index);
                String h1 = changed.getStart().getLblName().getText();
                String f1 = changed.getLabs()[0].getText();
                controller.delLink(h1, f1);
            }
        }
        //System.out.println(controller.getAllConf());

    }

    /**
     * Met en place les Listeners sur les différents Observables
     */
    private void setListeners() {

        //Ajout des listeners sur la position de l'objet
        this.xPos.addListener((observable, oldValue, newValue) -> update());
        this.yPos.addListener((observable, oldValue, newValue) -> update());

        //Ajout du listener sur la map des interfaces
        AbstractItem my = this;
        ifs.addListener((MapChangeListener<String, Link>) change -> {
            //System.out.println(String.format("-----------[key:%s ; added:%s ; removed:%s]-------------", change.getKey(), change.wasAdded(), change.wasRemoved()));
            //System.out.println("added:" + change.getValueAdded());
            //System.out.println("removed:" + change.getValueRemoved());
            boolean added = change.wasAdded();
            boolean removed = change.wasRemoved();
            Link valueAdded = change.getValueAdded();
            Link valueRemoved = change.getValueRemoved();
            if (added && !removed && valueAdded == null) {
                onIfsChange(ChangeEvent.ADDIF, change.getKey(), null);
                //controller.addIf(lblName.getText());
            }
            if (!added && removed && valueRemoved == null) {
                onIfsChange(ChangeEvent.DELIF, change.getKey(), null);
                //controller.delIf(lblName.getText());
            }
            if (added && removed) {
                if (valueAdded == null && valueRemoved != null) {
                    onIfsChange(ChangeEvent.LINKDEL, change.getKey(), valueRemoved);
                    //controller.delLink(lblName.getText(), change.getKey());
                }
                if (valueRemoved == null && valueAdded != null) {
                    onIfsChange(ChangeEvent.LINKADD, change.getKey(), valueAdded);
                    //Link k = valueAdded;

                    //controller.createLink(k.getStart().getLblName().getText(), k.getLabs()[0].getText(), k.getEnd().getLblName().getText(), k.getLabs()[1].getText());
                }
                /*if(valueRemoved != null && valueAdded !=null) {
                    onIfsChange(ChangeEvent.LINKCHANGE, change.getKey());
                }*/
            }
        });


    }

    /**
     * Cree le menu contextuel de l'item
     */
    private void setContextMenu() {

        // Create ContextMenu
        ContextMnu contextMenu = new ContextMnu(this, lblName.getText());

        // Listener sur le changement de nom
        lblName.textProperty().addListener((observable, oldValue, newValue) -> contextMenu.getNameProperty().setValue(lblName.getText()));


        //1 - les changements de CE dispLabel modifient celui du contextMenu
        dispLabels.addListener((observable, oldValue, newValue) -> {
            contextMenu.getDispLabelsProperty().setValue(dispLabels.getValue());
            updateDisp();
        });
        //1 - les changements du dispLabel du contextMenu modifient celui ci
        contextMenu.getDispLabelsProperty().addListener((observable, oldValue, newValue) -> dispLabels.setValue(contextMenu.getDispLabelsProperty().getValue()));

        dispLabels.setValue(true);
    }

    /**
     * création des évènements de drag N drop de l'item
     * les modifications se font sur posX et posY
     */
    private void setDragNDrop() {
        //L'évènement sera passé au rectagle d'avant seulement
        setOnMouseClicked(Event::consume);
        setOnMouseDragged(me -> {
            double dragX = me.getSceneX() - dragAnchor.getX();
            double dragY = me.getSceneY() - dragAnchor.getY();
            //calculate new position of the circle
            double newXPosition = initX + dragX;
            double newYPosition = initY + dragY;
            setX(newXPosition);
            setY(newYPosition);
        });
        setOnMouseEntered(me -> {
            //change the z-coordinate of the circle
            //toFront();
            //System.out.println(String.format("Pos=[%04.3f;%04.3f]", xPos.getValue(), yPos.getValue()));
        });

        setOnMousePressed(me -> {

            initX = getTranslateX();
            initY = getTranslateY();
            dragAnchor = new Point2D((float) me.getSceneX(), (float) me.getSceneY());
        });
    }

    /**
     * Met à jour la position de l'objet dans l'affichage
     */
    private void update() {
        this.setTranslateX(xPos.getValue());
        this.setTranslateY(yPos.getValue());
        //updateLabel();
    }

    /**
     * Met à jour la position relative du label de nom
     */
    private void updateLabel() {

        lblName.setTranslateX(getCenter().getX() - lblName.getBoundsInLocal().getWidth() / 2);
        lblName.setTranslateY(
                centerRef.getBoundsInLocal().getHeight());// + getLblName().getBoundsInLocal().getHeight());

    }

    /**
     * met à jour la propriété d'affichage/masquage des labels dans les liens
     */
    private void updateDisp() {
        for (Link k : ifs.values()) {
            if (k != null) {
                if (k.getStart() == this) {
                    k.getDispLabStart().setValue(this.dispLabels.getValue());
                } else {
                    k.getDispLabEnd().setValue(this.dispLabels.getValue());
                }
            }
        }
    }

}