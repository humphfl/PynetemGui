package v2.model;

import javafx.beans.value.ChangeListener;
import v2.controller.APIModel;
import v2.fileIO.ConfRW;
import v2.model.priv.Host;
import v2.model.priv.Router;
import v2.model.priv.Switch;
import v2.model.priv.abs.Connect;
import v2.model.priv.abs.Terminal;

import java.util.ArrayList;

public class ModelArch implements APIModel {

    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************

    private final ArrayList<Terminal> arch;

    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************

    public ModelArch() {
        this.arch = new ArrayList<>();
    }


    //******************************************************************************************************************
    //*                          PUBLIC METHODS                                                                        *
    //******************************************************************************************************************
    //******************************************************************************************************************
    //*                          PRIVATE METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Récupère un Terminal via son nom
     *
     * @param name : le nom du terminal à récupérer
     * @return : le <Terminal>
     */
    private Terminal getTermByName(String name) {

        //recherche de l'élément dans la liste par stream
        return arch.stream()
                .filter(search -> name.equals(search.getNameProperty().get()))
                .findAny()
                .orElse(null);
    }

    /**
     * Supprime un <Terminal>
     * @param name : le nom du terminal
     */
    private void delTerm(String name){
        Terminal t = getTermByName(name);
        t.destroy();
        this.arch.remove(t);
    }


    //******************************************************************************************************************
    //*                          Implementations de APIModel                                                           *
    //******************************************************************************************************************

    /**
     * Ajoute un <Router> à l'architecture
     *
     * @param name : le nom du router
     */
    @Override
    public void addRouter(String name) {
        this.arch.add(new Router(name));
    }

    /**
     * Supprime un <Router> de l'architecture
     *
     * @param name : le nom du router
     */
    @Override
    public void delRouter(String name) {
        delTerm(name);
    }

    /**
     * Ajoute un <Host> à l'architecture
     *
     * @param name : le nom du host
     */
    @Override
    public void addHost(String name) {
        this.arch.add(new Host(name));
    }

    /**
     * Supprime un <Host> à l'architecture
     *
     * @param name : le nom du host
     */
    @Override
    public void delHost(String name) {
        delTerm(name);
    }

    /**
     * Ajoute un <Switch> à l'architecture
     *
     * @param name : le nom du switch
     */
    @Override
    public void addSwitch(String name) {
        this.arch.add(new Switch(name));
    }

    /**
     * Supprime un <Switch> à l'architecture
     *
     * @param name : le nom du switch
     */
    @Override
    public void delSwitch(String name) {
        delTerm(name);
    }

    /**
     * Ajoute un <Bridge> à l'architecture
     *
     * @param name : le nom du bridge
     */
    @Override
    public void addBridge(String name) {
        //TODO : créer la classe Bridge

    }

    /**
     * Supprime un <Bridge> à l'architecture
     *
     * @param name : le nom du bridge
     */
    @Override
    public void delBridge(String name) {
        delTerm(name);

    }

    /**
     * Ajoute une interface à un terminal
     *
     * @param termName : le nom du terminal à modifier
     * @return : le nom de l'interface crée
     */
    @Override
    public String addIf(String termName) {
        Terminal t = getTermByName(termName);

        if (t != null) {
            return t.addInterface().toString();
        }
        return "";
    }

    /**
     * Supprime une interface à un terminal
     *
     * @param termName : le nom du terminal à modifier
     * @return : le nom de l'interface supprimée
     */
    @Override
    public String delIf(String termName) {
        Terminal t = getTermByName(termName);

        if (t != null) {
            return t.removeInterface().toString();
        }
        return "";
    }

    /**
     * Connecte une interface à une autre
     *
     * @param term1 : le premier terminal
     * @param if1   : l'interface du premier terminal
     * @param term2 : le second terminal
     * @param if2   : l'interface du second terminal
     * @return : le lien crée
     */
    @Override
    public String connect(String term1, String if1, String term2, String if2) {
        Terminal t1 = getTermByName(term1);
        Terminal t2 = getTermByName(term2);
        if (t1 != null && t2 != null) {
            Connect if_1 = t1.getByName(if1);
            Connect if_2 = t2.getByName(if2);
            if_1.connect(if_2);
            return "[" + t1 + "." + if_1 + ";" + t2 + "." + if_2 + "]";
        }
        return "";
    }

    /**
     * Déconnecte une interface
     *
     * @param term1 : le terminal
     * @param if1   : l'interface du terminal
     * @return : le lien détruit
     */
    @Override
    public String disconnect(String term1, String if1) {
        Terminal t1 = getTermByName(term1);
        if (t1 != null) {
            Connect if_1 = t1.getByName(if1);
            if (if_1.getLink() != null) {
                Connect if_2 = if_1.getLink();
                Terminal t2 = if_2.getParentTerm();
                if_1.disConnect();
                return "[" + t1 + "." + if_1 + ";" + t2 + "." + if_2 + "]";
            }
        }
        return "";

    }

    /**
     * Ajoute un écouteur sur les changements de nom d'un Terminal
     *
     * @param termName : le nom du terminal
     * @param list     : le Listener à ajouter
     */
    @Override
    public void addNameListener(String termName, ChangeListener<String> list) {
        Terminal t = getTermByName(termName);
        if (t != null) {
            t.getNameProperty().addListener(list);
        }

    }

    /**
     * Vérifie si un équipement existe déjà
     *
     * @param name : le nom de l'équipement
     * @return : true/false
     */
    @Override
    public boolean exist(String name) {
        return this.arch.contains(getTermByName(name));
    }

    /**
     * Renvoie un String avec la conf à écrire dans le fichier network.ini
     *
     * @return : String
     */
    @Override
    public String printConf() {
        return ConfRW.createConf(this.arch);
    }

    /**
     * Renvoie la conf d'un terminal
     *
     * @param term : String du nom du terminal
     * @return : String de la conf
     */
    @Override
    public String printTermConf(String term) {
        Terminal t = getTermByName(term);
        if (t != null) {
            return t.printConf();
        }
        return "";
    }

}