package v2.vue;

import javafx.beans.value.ChangeListener;
import v2.controller.APIVue;

public class MainVue implements APIVue {

    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************


    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************

    //******************************************************************************************************************
    //*                          PUBLIC METHODS                                                                        *
    //******************************************************************************************************************
    //******************************************************************************************************************
    //*                          PRIVATE METHODS                                                                       *
    //******************************************************************************************************************


    //******************************************************************************************************************
    //*                          Implementations de APIVue                                                             *
    //******************************************************************************************************************

    /**
     * Ajoute un <Router> à l'architecture
     *
     * @param name : le nom du router
     */
    @Override
    public void addRouter(String name) {

    }

    /**
     * Supprime un <Router> à l'architecture
     *
     * @param name : le nom du router
     */
    @Override
    public void delRouter(String name) {

    }

    /**
     * Ajoute un <Host> à l'architecture
     *
     * @param name : le nom du host
     */
    @Override
    public void addHost(String name) {

    }

    /**
     * Supprime un <Host> à l'architecture
     *
     * @param name : le nom du host
     */
    @Override
    public void delHost(String name) {

    }

    /**
     * Ajoute un <Switch> à l'architecture
     *
     * @param name : le nom du switch
     */
    @Override
    public void addSwitch(String name) {

    }

    /**
     * Supprime un <Switch> à l'architecture
     *
     * @param name : le nom du switch
     */
    @Override
    public void delSwitch(String name) {

    }

    /**
     * Ajoute un <Bridge> à l'architecture
     *
     * @param name : le nom du bridge
     */
    @Override
    public void addBridge(String name) {

    }

    /**
     * Supprime un <Bridge> à l'architecture
     *
     * @param name : le nom du bridge
     */
    @Override
    public void delBridge(String name) {

    }

    /**
     * Ajoute une interface à un terminal
     *
     * @param termName : le nom du terminal à modifier
     * @return : le nom de l'interface crée
     */
    @Override
    public String addIf(String termName) {
        return null;
    }

    /**
     * Supprime une interface à un terminal
     *
     * @param termName : le nom du terminal à modifier
     * @return : le nom de l'interface supprimée
     */
    @Override
    public String delIf(String termName) {
        return null;
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
        return null;
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
        return null;
    }

    /**
     * Ajoute un écouteur sur les changements de nom d'un Terminal
     *
     * @param termName : le nom du terminal
     * @param list     : le Listener à ajouter
     */
    @Override
    public void addNameListener(String termName, ChangeListener<String> list) {

    }

}
