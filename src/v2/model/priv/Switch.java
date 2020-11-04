package v2.model.priv;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import v2.model.priv.abs.Connect;
import v2.model.priv.abs.Interface;
import v2.model.priv.abs.Terminal;
import v2.model.priv.abs.Type;

import java.util.Iterator;

/**
 * Représente un équipement qui s'intègre dans la section [switches]
 */
public class Switch extends Terminal {


    //******************************************************************
    //*  VARIABLES                                                     *
    //******************************************************************


    /* Liste des interfaces connectées au switch */
    private final ObservableList<Connect> links = FXCollections.observableArrayList();

    private final Interface mainIf;

    //******************************************************************
    //*  CONSTRUCTOR                                                   *
    //******************************************************************

    public Switch(String nm) {
        super(Type.ovs, nm);

        mainIf = new Interface(nm, this, 0) {

            //liste des liens
            private final ObservableList<Connect> linkList = FXCollections.observableArrayList();

            @Override
            public void connect(Connect other) {
                if (!this.linkList.contains(other)) {
                    linkList.add(other);
                    other.connect(mainIf);
                }
            }

            @Override
            public void disConnect() {
                for (Iterator<Connect> iter = this.linkList.iterator(); iter.hasNext(); ) {
                    Connect c = iter.next();
                    iter.remove();
                    c.disConnect();
                }

            }

            @Override
            public boolean isLinked(Connect c) {
                return this.linkList.contains(c);
            }

            @Override
            public String getIndex() {
                return this.getName();
            }

            @Override
            public Connect getLink() {
                return mainIf;
            }
        };
        getNameProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                mainIf.setName(getNameProperty().getValue());
            }
        });
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
    //*    Extends Terminal                                            *
    //******************************************************************

    /**
     * Le switch ne crée pas d'interfaces
     */
    @Override
    public Interface addInterface() {
        return mainIf;
    }

    /**
     * supprime la dernière interface
     *
     * @return : l'interface supprimée
     */
    @Override
    public Interface removeInterface() {
        destroy();
        return mainIf;
    }

    /**
     * Actions à faire à la destruction de l'objet
     * déconnexion de toutes les interfaces
     */
    @Override
    public void destroy() {
        mainIf.disConnect();
    }

    /**
     * Affiche la conf à écrire dans network.ini
     *
     * @return une chaine de caractère à écrire dans le fichier de conf
     */
    @Override
    public String printConf() {
        StringBuilder conf = new StringBuilder();
        conf.append("[[").append(getNameProperty().getValue()).append("]]\n");
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
    public Connect getByName(String name) {
        return this.mainIf;
    }

    @Override
    public String toString() {
        return "sw";
    }
}
