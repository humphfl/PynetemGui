package v2.model.priv.abs;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Classe représentant une interface d'un équipement
 */
public class Interface implements Connect {


    //******************************************************************
    //*  VARIABLES                                                     *
    //******************************************************************

    /* Terminal de rattachement de l'interface */
    private final Terminal parentTerm;

    /* nom de l'interface */
    private String name;

    /* numéro de l'interface */
    private final int index;

    /* interface reliée */
    private final SimpleObjectProperty<Connect> ifaceLink = new SimpleObjectProperty<>(null);


    //******************************************************************
    //*  CONSTRUCTOR                                                   *
    //******************************************************************

    public Interface(String nm, Terminal hst, int idx) {
        this.parentTerm = hst;
        this.name = nm;
        this.index = idx;
    }


    //******************************************************************
    //*  GETTERS                                                       *
    //******************************************************************

    /**
     * Renvoie le nom de l'interface
     *
     * @return : type String
     */
    public String getName() {
        return this.name;
    }

    /**
     * renvoie l'objectProperty de l'intercace connectée à celle ci
     * @return : ObjectProperty<Connect>
     */
    public ObjectProperty<Connect> getIfaceLinkProperty() {
        return this.ifaceLink;
    }
    //******************************************************************
    //*  SETTERS                                                       *
    //******************************************************************

    public void setName(String name){
        this.name = name;
    }
    //******************************************************************
    //*  PUBLIC METHODS                                                *
    //******************************************************************

    /**
     * renvoie "<HOST_NAME>.<This_name>"
     *
     * @return : type String
     */
    public String toString() {
        return this.name;
    }


    //************************************************************************
    //*    Connect Implementations                                           *
    //************************************************************************

    /**
     * connecte à un autre
     *
     * @param other : autre <Connect> sur lequel se connecter
     */
    @Override
    public void connect(Connect other) {
        if(this.ifaceLink.get() != null && this.ifaceLink.get() != other) {
            this.ifaceLink.get().disConnect();
        }
        this.ifaceLink.setValue(other);


        if (!other.isLinked(this)) {
            other.connect(this);
        }

    }

    /**
     * déconnecte le lien
     */
    @Override
    public void disConnect() {
        if (this.ifaceLink.getValue() != null) {
            Connect tmp = this.ifaceLink.getValue();
            this.ifaceLink.set(null);
            if (tmp.isLinked(this)) {
                tmp.disConnect();
            }
        }

    }

    /**
     * renvoie l'autre bout du lien
     *
     * @return : objet de type Connect ou null
     */
    @Override
    public Connect getLink() {
        return this.ifaceLink.getValue();
    }

    /**
     * Renvoie true si le <this> est lié à l'autre <Connect> en paramètre
     *
     * @param c : le <Connect> à vérifier
     * @return : true/false
     */
    @Override
    public boolean isLinked(Connect c) {
        return this.ifaceLink.getValue() == c;
    }

    /**
     * Renvoie le <Terminal> auquel appartient le <Connect>
     *
     * @return : le <Terminal> possédant le <Connect>
     */
    @Override
    public Terminal getParentTerm() {

        return this.parentTerm;
    }

    /**
     * Renvoie l'index du connectable
     *
     * @return : l'index sous forme de String
     */
    @Override
    public String getIndex() {
        return Integer.toString(this.index);
    }

    /**
     * renvoie le numéro d'interface
     *
     * @return : numéro au format int
     */
    @Override
    public int getIntIndex() {
        return this.index;
    }
}
