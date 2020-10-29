package v2.vue.items;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import v2.vue.items.abstracts.AbstractItem;

public class ConnectionManager extends Line {

    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************

    private static final ConnectionManager INSTANCE = new ConnectionManager();
    private AbstractItem start;
    private boolean isOnConnect = false;
    private Group root;
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

    public void setOnConnect() {
        this.isOnConnect = true;
        this.setVisible(true);
        System.out.println("OnConnect!!");
    }

    public void setNotOnConnect() {
        this.isOnConnect = false;
        this.setVisible(false);
        System.out.println("NotOnConnect");
    }

    public boolean isOnConnect() {
        return this.isOnConnect;
    }

    public void setGroup(Group r) {
        this.root = r;
    }

    public void setStartIfName(String nm) {
        this.startIf = nm;


    }

    public void setEndIfName(String nm) {
        this.endIf = nm;
    }

    public void mkLink(AbstractItem end) {

        if(end==start){
            return;
        }
        Link lk = end.getIfsMap().get(start);
        if (lk != null) {
            lk.destroy();
        }
        Link newLk = new Link(start, end, startIf, endIf, root);
        if (!root.getChildren().contains(newLk)) {
            root.getChildren().add(newLk);

        }
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
}
