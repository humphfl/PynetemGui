package v1.materials.priv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import v1.materials.priv.abs.*;

import java.util.Iterator;

/**
 * Représente un équipement qui s'intègre dans la section [switches]
 */
public class Switch extends Terminal implements Connect {

    /**
     * Terminal générique servant de host aux switches
     */
    public static final Terminal SW_TERM = new Terminal(Type.ovs, Statics.SWITCH) {
        @Override
        public Interface addInterface() {
            return null;
        }

        /**
         * supprime la dernière interface
         *
         * @return : l'interface supprimée
         */
        @Override
        public Interface removeInterface() {
            return null;
        }

        /**
         * Actions à faire à la destruction de l'objet
         */
        @Override
        public void destroy() {

        }

        @Override
        public String printConf() {
            return "";
        }


    };

    //******************************************************************
    //*  VARIABLES                                                     *
    //******************************************************************

    /* Liste des interfaces connectées au switch */
    private final ObservableList<Connect> links = FXCollections.observableArrayList();


    //******************************************************************
    //*  CONSTRUCTOR                                                   *
    //******************************************************************

    public Switch(String nm) {
        super(Type.ovs, nm);
    }

    /**
     * Renvoie la liste des liens sur ce switch
     *
     * @return : ObservablieList<Connect>
     */
    public ObservableList<Connect> getLinks() {
        return this.links;
    }


    //******************************************************************
    //*    Connect Implementations                                     *
    //******************************************************************

    /**
     * connecte à un autre
     *
     * @param other : autre <Connect> sur lequel se connecter
     */
    @Override
    public void connect(Connect other) {
        this.links.add(other);
        other.connect(this);
    }

    /**
     * supprime de la liste toutes les interfaces qui ne sont pas connectées au switch
     */
    @Override
    public void disConnect() {

        for (Iterator<Connect> iter = this.links.iterator(); iter.hasNext(); ) {
            Connect c = iter.next();
            c.disConnect();
            iter.remove();
        }
        //this.links.removeIf(c -> !c.isLinked(this));

    }

    /**
     * le getLink renvoie null car getLink est utilisé que pour une interface
     *
     * @return : null
     */
    @Override
    public Connect getLink() {
        return null;
    }

    /**
     * Renvoie true si le <this> est lié à l'autre <Connect> en paramètre
     *
     * @param c : le <Connect> à vérifier
     * @return : true/false
     */
    @Override
    public boolean isLinked(Connect c) {
        return this.links.contains(c);
    }

    /**
     * Renvoie le <Terminal> générique (pour le "sw." dans la conf)
     *
     * @return : le <Terminal> possédant le <Connect>
     */
    @Override
    public Terminal getParentTerm() {
        return SW_TERM;
    }


    /**
     * Renvoie l'index du connectable
     * pour un switch l'index est son nom ("sw.name" dans le ficher de conf)
     *
     * @return : le nom (String)
     */
    @Override
    public String getIndex() {
        return toString();
    }

    /**
     * renvoie le numéro d'interface
     *
     * @return : -1
     */
    @Override
    public int getIntIndex() {
        return -1;
    }

    /**
     * Renvoie le nom de l'interface
     *
     * @return : String
     */
    @Override
    public String getName() {
        return toString();
    }


    //******************************************************************
    //*    Extends Terminal                                            *
    //******************************************************************

    /**
     * Le switch ne crée pas d'interfaces
     */
    @Override
    public Interface addInterface() {
        return null;
    }

    /**
     * supprime la dernière interface
     *
     * @return : l'interface supprimée
     */
    @Override
    public Interface removeInterface() {
        return null;
    }

    /**
     * Actions à faire à la destruction de l'objet
     * déconnexion de toutes les interfaces
     */
    @Override
    public void destroy() {
        for (Connect lk : links) {
            lk.disConnect();
        }
        disConnect();
    }

    /**
     * Affiche la conf à écrire dans network.ini
     *
     * @return une chaine de caractère à écrire dans le fichier de conf
     */
    @Override
    public String printConf() {
        StringBuilder conf = new StringBuilder();
        conf.append("[[").append(toString()).append("]]\n");
        conf.append("type = ").append(getType().tStr()).append("\n");

        /* test mode */
        if (this.testMode) {
            for (Connect c : this.links) {
                /* ifn = host.ifn*/
                conf.append(c.toString()).append(" = ");
                if (c.getLink() == null) {
                    conf.append("__null__\n");
                } else {
                    conf.append(c.getLink().getParentTerm().toString()).append(".").append(c.getLink().getIndex()).append("\n");
                }

            }
        }

        return conf.toString();
    }

    @Override
    public Connect getByName(String name){
        return this;
    }


}
