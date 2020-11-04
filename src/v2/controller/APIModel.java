package v2.controller;

import javafx.beans.value.ChangeListener;

public interface APIModel {

    //******************************************************************************************************************
    //*                          1] FONCTIONS D'AJOUT/SUPPRESSION DE TERMINAUX                                         *
    //******************************************************************************************************************

    /**
     * Supprime un <Terminal>
     * @param name : le nom du terminal
     */
    void delTerm(String name);

    //+------------------------------------------------------+
    //|      ROUTER                                          |
    //+------------------------------------------------------+

    /**
     * Ajoute un <Router> à l'architecture
     *
     * @param name : le nom du router
     */
    void addRouter(String name);

    /**
     * Supprime un <Router> à l'architecture
     *
     * @param name : le nom du router
     */
    void delRouter(String name);

    //+------------------------------------------------------+
    //|      HOST                                            |
    //+------------------------------------------------------+

    /**
     * Ajoute un <Host> à l'architecture
     *
     * @param name : le nom du host
     */
    void addHost(String name);

    /**
     * Supprime un <Host> à l'architecture
     *
     * @param name : le nom du host
     */
    void delHost(String name);

    //+------------------------------------------------------+
    //|      SWITCH                                          |
    //+------------------------------------------------------+

    /**
     * Ajoute un <Switch> à l'architecture
     *
     * @param name : le nom du switch
     */
    void addSwitch(String name);

    /**
     * Supprime un <Switch> à l'architecture
     *
     * @param name : le nom du switch
     */
    void delSwitch(String name);

    //+------------------------------------------------------+
    //|      BRIDGE                                          |
    //+------------------------------------------------------+

    /**
     * Ajoute un <Bridge> à l'architecture
     *
     * @param name : le nom du bridge
     */
    void addBridge(String name);

    /**
     * Supprime un <Bridge> à l'architecture
     *
     * @param name : le nom du bridge
     */
    void delBridge(String name);


    //******************************************************************************************************************
    //*                          2] FONCTIONS DE MODIFICATION DES TERMINAUX                                            *
    //******************************************************************************************************************

    /**
     * Ajoute une interface à un terminal
     *
     * @param termName : le nom du terminal à modifier
     * @return : le nom de l'interface crée
     */
    String addIf(String termName);

    /**
     * Supprime une interface à un terminal
     *
     * @param termName : le nom du terminal à modifier
     * @return : le nom de l'interface supprimée
     */
    String delIf(String termName);

    /**
     * Connecte une interface à une autre
     *
     * @param term1 : le premier terminal
     * @param if1   : l'interface du premier terminal
     * @param term2 : le second terminal
     * @param if2   : l'interface du second terminal
     * @return : le lien crée
     */
    String connect(String term1, String if1, String term2, String if2);

    /**
     * Déconnecte une interface
     *
     * @param term1 : le terminal
     * @param if1   : l'interface du terminal
     * @return : le lien détruit
     */
    String disconnect(String term1, String if1);

    /**
     * Renomme un terminal
     * @param term : le terminal à renommer
     * @param newName : le nouveau nom
     */
    void rename(String term, String newName);

    //******************************************************************************************************************
    //*                          3] FONCTIONS D'ECOUTE                                                                 *
    //******************************************************************************************************************

    /**
     * Ajoute un écouteur sur les changements de nom d'un Terminal
     *
     * @param termName : le nom du terminal
     * @param list     : le Listener à ajouter
     */
    void addNameListener(String termName, ChangeListener<String> list);

    //******************************************************************************************************************
    //*                          4] FONCTIONS DE VERIFICATION                                                          *
    //******************************************************************************************************************

    /**
     * Vérifie si un équipement éxiste déjà
     * @param name
     * @return
     */
    boolean exist(String name);

    //******************************************************************************************************************
    //*                          4] FONCTIONS D'EXPORT                                                                 *
    //******************************************************************************************************************

    /**
     * Renvoie un String avec la conf à écrire dans le fichier network.ini
     * @return : String
     */
    String printConf();

    /**
     * Renvoie la conf d'un terminal
     * @param term : String du nom du terminal
     * @return : String de la conf
     */
    String printTermConf(String term);

}
