package v2.vue.items;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import v2.vue.items.abstracts.AbstractItem;

public class Link extends Line {

    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************


    //affichage ou non du label
    private final BooleanProperty dispLabStart = new SimpleBooleanProperty(true);
    private final BooleanProperty dispLabEnd = new SimpleBooleanProperty(true);

    //noms à afficher pour les 2 parties du lien
    private final Label labStart, labEnd;
    private final AbstractItem start, end;
    private final Group root;
    private Point2D offset = new Point2D(0, 0);

    private Multilink group;

    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************


    /**
     * Création d'une ligne pour relier 2 périphériques réseau
     *
     * @param start : premier élément
     * @param end   : second élément
     * @param st    : nom donné au premier élément
     * @param ed    : nom donné au second élément
     */
    public Link(AbstractItem start, AbstractItem end, String st, String ed, Group root) {

        //TODO : la création d'un lien doit passer par le controller
        //création de la ligne avec les positions start/end des objets reliés. les objets sont reliés par le centre
        super();
        this.root = root;


        this.start = start;
        this.end = end;


        this.labStart = new Label(st);
        this.labEnd = new Label(ed);

        this.labStart.setVisible(true);
        this.labEnd.setVisible(true);

        this.labStart.setStyle("-fx-background-color: burlywood;");
        this.labEnd.setStyle("-fx-background-color: burlywood;");

        this.setVisible(true);

        this.toBack();


        //Mise à jour des tables d'interfaces dans les 2 bouts du lien
        updateIfLists();

        addListeners();
        addToParent();
        update();


    }

    //******************************************************************************************************************
    //*                          ABSTRACT METHODS                                                                      *
    //******************************************************************************************************************

    /**
     * Ajoute les 2 labels au parent
     */
    private void addToParent() {
        root.getChildren().addAll(labStart, labEnd, this);
    }

    /**
     * supprime les labels du parent
     */
    private void removeToParent() {
        root.getChildren().remove(this);
        root.getChildren().remove(labStart);
        root.getChildren().remove(labEnd);
    }
    //******************************************************************************************************************
    //*                          GETTERS METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Renvoie l'élément de début de lien
     *
     * @return : <absItem>
     */
    public AbstractItem getStart() {
        return this.start;
    }

    /**
     * Renvoie l'élément de fin de lien
     *
     * @return : <absItem>
     */
    public AbstractItem getEnd() {
        return this.end;
    }

    /**
     * Renvoie le point de référence du début du lien
     *
     * @return : <Point2D>
     */
    public Point2D getStartRef() {
        return start.getAbsoluteCenter();
    }

    /**
     * Renvoie le point de référence de la fin du lien
     *
     * @return : <Point2D>
     */
    public Point2D getEndRef() {
        return end.getAbsoluteCenter();
    }

    /**
     * renvoie l'offset de position
     *
     * @return : <Point2D>
     */
    public Point2D getOffset() {
        return this.offset;
    }

    /**
     * Renvoie les 2 labels du lien
     *
     * @return : <Text[]>
     */
    public Label[] getLabs() {
        return new Label[]{labStart, labEnd};
    }

    /**
     * Renvoie la propriété qui définie l'affichage ou non du label
     * @return : <BooleanProperty>
     */
    public BooleanProperty getDispLabStart(){
        return this.dispLabStart;
    }


    /**
     * Renvoie la propriété qui définie l'affichage ou non du label
     * @return : <BooleanProperty>
     */
    public BooleanProperty getDispLabEnd(){
        return this.dispLabEnd;
    }

    /**
     * Renvoie le groupe de multiliens dont ce lien est membre
     * @return : <MultiLink>
     */
    public Multilink getGroup(){
        return this.group;
    }


    //******************************************************************************************************************
    //*                          SETTERS METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Ajoute un offset de position sur le lien
     *
     * @param x : <double>
     * @param y : <double>
     */
    public void setOffset(double x, double y) {
        this.offset = new Point2D(x, y);
        update();
    }

    /**
     * défini si ce lien est membre d'un groupe de liens
     * @param ml : le groupe
     */
    public void setMultiLink(Multilink ml){
        this.group = ml;
    }

    //******************************************************************************************************************
    //*                          PRIVATE METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Ajout des écouteurs pour la mise à jour grraphique du lien en fonction des déplacements ds 2 éléments reliés
     */
    private void addListeners() {

        //Met à jour le visuel du lien à chaque changement de position des items de début et de fin
        start.getXPos().addListener((observable, oldValue, newValue) -> update());
        start.getYPos().addListener((observable, oldValue, newValue) -> update());
        end.getXPos().addListener((observable, oldValue, newValue) -> update());
        end.getYPos().addListener((observable, oldValue, newValue) -> update());

        //start.getDispLabels().addListener((observable, oldValue, newValue) -> lab1.setVisible(start.getDispLabels().getValue()));
        //end.getDispLabels().addListener((observable, oldValue, newValue) -> lab2.setVisible(end.getDispLabels().getValue()));
        dispLabStart.addListener((observable, oldValue, newValue) -> labStart.setVisible(dispLabStart.getValue()));
        dispLabEnd.addListener((observable, oldValue, newValue) -> labEnd.setVisible(dispLabEnd.getValue()));

    }

    /**
     * Met à jour l'affichage du lien
     */
    private void update() {
        this.toBack();

        //update de la position du lien
        this.setStartX(start.getAbsoluteCenter().getX() + offset.getX());
        this.setStartY(start.getAbsoluteCenter().getY() + offset.getY());
        this.setEndX(end.getAbsoluteCenter().getX() + offset.getX());
        this.setEndY(end.getAbsoluteCenter().getY() + offset.getY());
        /*System.out.println(String.format("LinePs=[%04.3f;%04.3f -> %04.3f;%04.3f]",
                start.getAbsoluteCenter().getX(), start.getAbsoluteCenter().getY(),
                end.getAbsoluteCenter().getX(), end.getAbsoluteCenter().getY()));*/

        //update de la position des labels
        Point2D lb1Center = new Point2D(labStart.getBoundsInLocal().getWidth() / 2.f, labStart.getBoundsInLocal().getHeight() / 2.f);
        Point2D lb2Center = new Point2D(labEnd.getBoundsInLocal().getWidth() / 2.f, labEnd.getBoundsInLocal().getHeight() / 2.f);
        //Point2D lb1Center = new Point2D(lab1.getBoundsInLocal().getHeight()/2f,lab1.getBoundsInLocal().getWidth()/2f);
        //Point2D lb2Center = new Point2D(lab2.getBoundsInLocal().getHeight()/2f,lab2.getBoundsInLocal().getWidth()/2f);

        //points de ref
        Point2D st = getStartRef();
        Point2D ed = getEndRef();

        //norme de la droite reliant les 2 points
        double norm = Math.sqrt(Math.pow(ed.getX() - st.getX(), 2) + Math.pow(ed.getY() - st.getY(), 2));

        //vecteur unitaire de la droite de base :
        Point2D vect = new Point2D(0, 0);
        if (norm != 0) {
            vect = new Point2D((ed.getX() - st.getX()) / norm, (ed.getY() - st.getY()) / norm);
        }

        double fact = 65;
        double multoff = 5;
        labStart.setTranslateX(st.getX() + vect.getX() * fact - lb1Center.getX() + offset.getX() * multoff);
        labStart.setTranslateY(st.getY() + vect.getY() * fact - lb1Center.getY() + offset.getY() * multoff);
        labEnd.setTranslateX(ed.getX() - vect.getX() * fact - lb1Center.getX() + offset.getX() * multoff);
        labEnd.setTranslateY(ed.getY() - vect.getY() * fact - lb1Center.getY() + offset.getY() * multoff);
        labStart.toFront();
        labEnd.toFront();


    }

    /**
     * Met à jour la table des interfaces sur chacun des cotés du lien
     */
    private void updateIfLists(){
        //TODO cette méthode doit être vérifiée par le controller
        start.getIfsMap().put(labStart.getText(), this);
        end.getIfsMap().put(labEnd.getText(), this);

    }
    //******************************************************************************************************************
    //*                          PUBLIC METHODS                                                                        *
    //******************************************************************************************************************

    /**
     * Actions à la destruction de l'objet
     */
    public void destroy() {
        removeToParent();
        start.getIfsMap().put(labStart.getText(), null);
        end.getIfsMap().put(labEnd.getText(), null);
        if(this.group != null){
            Multilink mlk = this.group;
            this.group = null;
            mlk.delLink(this);
        }
    }

    /**
     * Compare 2 liens. 2 liens sont identiques s'il relient les mêmes interfaces des mêmes équipements
     * @param other : l'autre lien à comparer
     * @return : <boolean>
     */
    /*
    public boolean isEqual(Link other){
        if(other == null){
            return false;
        }
        boolean hosts = (this.start == other.start || this.start == other.end)
                && (this.end == other.start || this.end == other.end);
        String t1S = this.labStart.getText();
        String t1E = this.labEnd.getText();
        String t2S = other.labStart.getText();
        String t2E = other.labEnd.getText();
        boolean ifs = (t1S.equals(t2S) || t1S.equals(t2E))
                && (t1E.equals(t2S) || t1E.equals(t2E));

        return hosts && ifs;
    }*/

    public String toString(){
        return String.format("[start:%s;stop:%s]", labStart.getText(), labEnd.getText()) + super.toString();
    }

}
