package v2.vue.items;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import v2.vue.items.abstracts.AbstractItem;

/**
 * Classe Singleton : gestion de la création des connexions entre les équipements
 */
public class ConnectionManager extends Line {

    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************

    //Instance unique
    private static final ConnectionManager INSTANCE = new ConnectionManager();

    //point de départ du lien
    private AbstractItem start;

    //état du manager
    private boolean isOnConnect = false;

    //conteneur de l'élément graphique
    private Group root;

    //labels de ébut et fin de lien
    private String startIf, endIf;


    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************

    private ConnectionManager() {

        this.setFill(Color.ORANGE);
        this.setTranslateX(0);
        this.setTranslateY(0);
        this.setVisible(false);


    }
    //******************************************************************************************************************
    //*                          ABSTRACT METHODS                                                                      *
    //******************************************************************************************************************

    //******************************************************************************************************************
    //*                          GETTERS METHODS                                                                       *
    //******************************************************************************************************************


    /**
     * Renvoie l'instance
     *
     * @return : Instance
     */
    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    /**
     * renvoie l'état du manager
     *
     * @return : <boolean>
     */
    public boolean isOnConnect() {
        return this.isOnConnect;
    }
    //******************************************************************************************************************
    //*                          SETTERS METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Change le point de start
     *
     * @param it : <AbstractItem>
     */
    public void setStart(AbstractItem it) {
        this.start = it;
        this.setStartX(it.getAbsoluteCenter().getX());
        this.setStartY(it.getAbsoluteCenter().getY());
    }

    /**
     * Passe le manager en mode création de connexion (visible)
     */
    public void setOnConnect() {
        this.isOnConnect = true;
        this.setVisible(true);
        System.out.println("OnConnect!!");
    }

    /**
     * Passe le manager en mode passif (invisible)
     */
    public void setNotOnConnect() {
        this.isOnConnect = false;
        this.setVisible(false);
        System.out.println("NotOnConnect");
    }

    /**
     * met en place le conteneur dans lequel tracer le lien
     *
     * @param r : <Group>
     */
    public void setGroup(Group r) {
        this.root = r;
    }

    /**
     * met à jour le nom du label de départ
     *
     * @param nm : <String>
     */
    public void setStartIfName(String nm) {
        this.startIf = nm;


    }

    /**
     * met à jour le nom du label de fin de lien
     *
     * @param nm : <String>
     */
    public void setEndIfName(String nm) {
        this.endIf = nm;
    }


    //******************************************************************************************************************
    //*                          PRIVATE METHODS                                                                       *
    //******************************************************************************************************************


    //******************************************************************************************************************
    //*                          PROTECTED METHODS                                                                     *
    //******************************************************************************************************************

    //******************************************************************************************************************
    //*                          PUBLIC METHODS                                                                        *
    //******************************************************************************************************************

    /**
     * Crée le lien à partir des informations qui possède le manager
     *
     * @param end : <AbstractItem> : l'autre bout du lien
     */
    public void mkLink(AbstractItem end) {

        if (end == start) {
            return;
        }

        //Si les interfaces à relier ont déjà des liens il faut les détruire

        //récupération du lien éventuel sur l'interface de l'équipement de départ
        Link lk = start.getIfsMap().get(startIf);

        if (lk != null) {
            lk.destroy();
        }
        //récupération du lien éventuel sur l'interface de l'équipement d'arrivée
        lk = end.getIfsMap().get(endIf);
        if (lk != null) {
            lk.destroy();
        }

        //Récupérer le lien AVANT la création du nouveau
        Link sameLk = start.getLink(end);

        //Création du nouveau lien
        Link newLk = new Link(start, end, startIf, endIf, root);

        //Si un lien entre les 2 équipements existe déjà
        if (sameLk != null) {

            //On récupère son multilien s'il en a un
            Multilink mlk = sameLk.getGroup();
            if (mlk == null) {
                //s'il n'a pas de multilien on le crée
                mlk = new Multilink();
                //On ajoute ce lien au multilien
                mlk.addLink(sameLk);
            }
            //On ajoute le nouveau lien au multilien
            mlk.addLink(newLk);
        }

        //On ajoute le nouveau lien à la vue
        if (!root.getChildren().contains(newLk)) {
            root.getChildren().add(newLk);

        }
    }
}
