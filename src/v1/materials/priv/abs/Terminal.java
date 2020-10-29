package v1.materials.priv.abs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * classe générique représentant un matériel réseau
 */
public abstract class Terminal {


    //******************************************************************
    //*  VARIABLES                                                     *
    //******************************************************************

    private final StringProperty name;
    private final ObservableList<Connect> ifs = FXCollections.observableArrayList();
    public boolean testMode = false;
    private Type type;


    //******************************************************************
    //*  CONSTRUCTOR                                                   *
    //******************************************************************

    public Terminal(Type t, String nm) {
        setType(t);
        this.name = new SimpleStringProperty(nm);
    }

    //******************************************************************
    //*  ABSTRACT METHODS                                              *
    //******************************************************************

    /**
     * Ajoute une interface
     */
    public abstract Connect addInterface();

    /**
     * supprime la dernière interface
     * @return : l'interface supprimée
     */
    public abstract Connect removeInterface();

    /**
     * Actions à faire à la destruction de l'objet
     */
    public abstract void destroy();

    /**
     * Affiche la conf à écrire dans network.ini
     *
     * @return une chaine de caractère à écrire dans le fichier de conf
     */
    public abstract String printConf();



    //******************************************************************
    //*  GETTERS                                                       *
    //******************************************************************

    /**
     * Renvoie la propriété name
     *
     * @return : StringProperty
     */
    public StringProperty getNameProperty() {
        return this.name;
    }

    /**
     * renvoie le type de terminal
     *
     * @return : le type de terminal (enum Type)
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Défini le type de terminal
     *
     * @param t : enum:Type : le type de terminal
     */
    protected void setType(Type t) {
        this.type = t;
    }

    /**
     * renvoie la liste des interfaces
     *
     * @return : la liste observable des interfaces que possède le terminal
     */
    public ObservableList<Connect> getIfs() {
        return this.ifs;
    }

    /**
     * renvoie le nombre d'interfaces
     *
     * @return : le nombre d'interfaces, type Integer
     */
    public int getIfsCount() {
        return this.ifs.size();
    }

    //******************************************************************
    //*  SETTERS                                                       *
    //******************************************************************

    /**
     * récupère une interface à partir de son nom
     *
     * @param nm : le nom de l'interface à chercher
     * @return : l'interface ou null
     */
    public Connect getByName(String nm) {

        for (Connect if_ : this.ifs) {

            if (if_.getName().equals(nm)) {
                return if_;
            }
        }
        return null;
    }

    //******************************************************************
    //*  PROTECTED METHODS                                             *
    //******************************************************************

    /**
     * ajoute une interface à la liste
     *
     * @param if_ : l'interface à ajouter
     */
    protected void addIf(Connect if_) {
        this.ifs.add(if_);
    }

    /**
     * supprime une interface de la liste
     *
     * @param if_ : l'interface à supprimer
     */
    protected void delIf(Connect if_) {
        if (if_ != null) {

            this.ifs.remove(if_);
            if_.disConnect();

        }
    }


    //******************************************************************
    //*  PUBLIC METHODS                                                *
    //******************************************************************

    /**
     * Renvoie le nom du terminal
     *
     * @return : String du nom
     */
    public String toString() {
        return this.name.getValue();
    }

    /**
     * Renomme le terminal
     *
     * @param nm : String : le nom du terminal
     */
    public void rename(String nm) {
        this.name.setValue(nm);
    }

}
