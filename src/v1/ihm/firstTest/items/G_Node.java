package v1.ihm.firstTest.items;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import v1.materials.priv.abs.Connect;
import v1.materials.priv.abs.Interface;
import v1.materials.priv.abs.Node;

public abstract class G_Node extends Parent {

    //******************************************************************
    //*  VARIABLES                                                     *
    //******************************************************************
    //Le router exploité
    private final Node nd;
    //positions X et Y du router
    private final DoubleProperty posX = new SimpleDoubleProperty();
    private final DoubleProperty posY = new SimpleDoubleProperty();

    //Liste des interfaces du node
    private final ObservableList<G_Connect> ifLst = FXCollections.observableArrayList();

    //Elements visuels
    private final Text label = new Text("");

    //******************************************************************
    //*  CONSTRUCTOR                                                   *
    //******************************************************************

    /**
     * Constructeur de l'objet
     *
     * @param nod : le Node à exploiter
     * @param x   : position X
     * @param y   : position Y
     */
    public G_Node(Node nod, double x, double y) {

        // instancie le Node
        this.nd = nod;

        // Création du visuel de base
        createMainVisual();

        // ajout des listeners
        setListeners();

        //mise à jour de la position
        getPosXProperty().setValue(x);
        getPosYProperty().setValue(y);

    }

    //******************************************************************
    //*  ABSTRACTS                                                     *
    //******************************************************************

    /**
     * méthode dans laquelle on crée le visuel de l'objet
     */
    public abstract void makeVisual();

    /**
     * méthode de mise en place des visuels des interfaces
     */
    public abstract void setIfPos();

    //******************************************************************
    //*  GETTERS                                                       *
    //******************************************************************

    /**
     * renvoie le DoubleProperty X
     *
     * @return : DoubleProperty
     */
    public DoubleProperty getPosXProperty() {
        return this.posX;
    }

    /**
     * renvoie le DoubleProperty X
     *
     * @return : DoubleProperty
     */
    public DoubleProperty getPosYProperty() {
        return this.posY;
    }

    /**
     * renvoie la map des interfaces
     *
     * @return : ObservableMap<Interface, Circle>
     */
    public ObservableList<G_Connect> getIfLst() {
        return this.ifLst;
    }

    /**
     * Renvoie le node exploité
     *
     * @return : Node
     */
    public Node getNode() {
        return this.nd;
    }


    //******************************************************************
    //*  PUBLIC METHODS                                                *
    //******************************************************************


    /**
     * Ajoute une interface :
     * Ajoute l'interface sur le node et sur la partie graphique avec un lien entre les 2 via la Map
     * Ajout aussi le listener sur l'état de l'interface (connectée ou non)
     */
    public void addInterface() {

        //création du visuel
        //Circle c = createIfVisual();

        //ajout de l'interface sur le node
        Interface if_ = (Interface) getNode().addInterface();

        //Création du visuel de l'interface
        G_Connect gIf = new G_Connect(if_);

        //ajout du listener d'état sur l'interface
        if_.getIfaceLinkProperty().addListener(new IfChange(gIf));

        //ajout de l'interface et du visuel dans la map
        this.getIfLst().add(gIf);

        //ajout du visuel au conteneur visuel
        this.getChildren().add(gIf);

        //calcul des positions
        setIfPos();
        update();
    }

    /**
     * Supprime une interface :
     * la supprime dans le node puis dans le conteneur visuel et enfin dans la map
     */
    public void removeInterface() {

        //suppression de l'interface dans le node
        Connect if_ = getNode().removeInterface();


        //TODO : recherche par stream
        //recherche de l'élément dans la liste
        G_Connect gIf = ifLst.stream()
                .filter(search -> if_ == search.getIf())
                .findAny()
                .orElse(null);

        //suppression dans le conteneur
        getChildren().remove(gIf);

        //suppression dans la map
        getIfLst().remove(gIf);

        //calcul des positions
        setIfPos();
        update();

    }

    /**
     * connect le Node à autre chose
     *
     * @param myIf    : l'interface du node à connecté
     * @param otherIf : l'autre élément sur lequel se connecter
     */
    public void connect(G_Connect myIf, G_Connect otherIf) {
        myIf.getIf().connect(otherIf.getIf());
        //TODO faire le lien ici
        G_link lnk = new G_link(myIf, otherIf);
        this.getChildren().add(lnk);
    }

    /**
     * Déconnecte une interface
     *
     * @param myIf : l'interface à déconnecter
     */
    public void disConnect(G_Connect myIf) {
        myIf.getIf().disConnect();
        //Todo defaire le lien ici
    }

    /**
     * Récupère une interface à partir d'un String
     *
     * @param name : String : nom de l'interface
     * @return : <Interface>
     */
    public Connect getIfByName(String name) {
        return getNode().getByName(name);
    }

    /**
     * Renvoie les lignes du fichier de configuration à écrire
     *
     * @return : <String>
     */
    public String getConf() {
        return getNode().printConf();
    }

    /**
     * Renvoie un élément G_Connect à partir d'un String
     *
     * @param name : le nom de l'interface
     * @return : un G_Connect
     */
    public G_Connect getGIfByName(String name) {
        return getGIfByIf(getIfByName(name));
    }

    /**
     * Renvoie un élément G_Connect à partir c'un élément Connect
     * <p>
     * recherche dans la ifLst avec un stream
     *
     * @param if_ : l'interface
     * @return : le G_Connect
     */
    public G_Connect getGIfByIf(Connect if_) {
        return ifLst.stream()
                .filter(search -> if_ == search.getIf())
                .findAny()
                .orElse(null);
    }


    //******************************************************************
    //*  PRIVATE METHODS                                               *
    //******************************************************************


    /**
     * Ajoute les listeners sur le nom et la position
     */
    private void setListeners() {

        // Listener de changement de position en X
        this.posX.addListener((observable, oldValue, newValue) -> update());

        // Listener de changement de position en Y
        this.posY.addListener((observable, oldValue, newValue) -> update());

        // Listener de changement de nom : recentre le label au changement
        getNode().getNameProperty().addListener((observable, oldValue, newValue) -> {
            label.setText(getNode().toString());
            label.setTranslateX(-label.getLayoutBounds().getWidth() / 2.0f);
            label.setTranslateY(label.getLayoutBounds().getHeight() / 4.0f);
            label.toFront();
            //System.out.println("label set text : " + getNode().toString());
        });

        // Listener sur le changement du nombre d'interfaces : calcul les positions
        getIfLst().addListener((ListChangeListener<G_Connect>) change -> setIfPos());
    }

    /**
     * Création des éléments graphiques de base
     */
    private void createMainVisual() {
        label.toFront();
        label.setVisible(true);
        this.getChildren().add(label);
    }


    private void update() {
        setTranslateX(posX.getValue());
        setTranslateY(posY.getValue());
        for (G_Connect g : getIfLst()) {
            g.setAbsCoords(this.posX.getValue(), this.posY.getValue());
        }

    }
}
//**********************************************************************************************************************
//**********************************************************************************************************************
//**                                                                                                                  **
//**  CLASSES INTERNES                                                                                                **
//**                                                                                                                  **
//**********************************************************************************************************************
//**********************************************************************************************************************


    /**
     * ChangeListener à ajouter aux interface à écouter
     */
    class IfChange implements ChangeListener<Connect> {

        //éléments nécessaires au traitement
        private final G_Connect vis;

        /**
         * Constructeur prenant les éléments nécesaires au traitement
         *
         * @param vis : Element graphique contenant le visuel et l'interface
         */
        public IfChange(G_Connect vis) {
            this.vis = vis;
        }

        /**
         * This method needs to be provided by an implementation of
         * {@code ChangeListener}. It is called if the value of an
         * {@link ObservableValue} changes.
         * <p>
         * In general, it is considered bad practice to modify the observed value in
         * this method.
         *
         * @param observable The {@code ObservableValue} which value changed
         * @param oldValue   The old value
         * @param newValue   .
         */
        @Override
        public void changed(ObservableValue<? extends Connect> observable, Connect oldValue, Connect newValue) {
            if (vis.getIf().getLink() != null) {
                vis.setFill(Color.GREEN);
            } else {
                vis.setFill(Color.RED);
            }
        }
    }
