package v2.controller;

public interface APIController {

    //******************************************************************************************************************
    //*                          1] FONCTIONS D'AJOUT/SUPPRESSION DE TERMINAUX                                         *
    //******************************************************************************************************************

    /**
     * Vérifie si la création d'un <Host> est possible
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    public boolean createHost(String name);

    /**
     * Vérifie si la création d'un <Router> est possible
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    public boolean createRouter(String name);


    /**
     * Vérifie si la création d'un <Switch> est possible
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    public boolean createSwitch(String name);

    /**
     * Vérifie si la création d'un <Host> est possible
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    public boolean createBridge(String name);

    //******************************************************************************************************************
    //*                          2] FONCTIONS DE MODIFICATION DES TERMINAUX                                            *
    //******************************************************************************************************************

    /**
     * Qupprime un lien d'une interface
     * @param hostName : <String> le nom du Host
     * @param ifName : <String> : le nom de l'interface
     * @return : <boolean>
     */
    boolean delLink(String hostName, String ifName);

    /**
     * Crée un lien entre 2 équipements
     * @param node1 : le premier
     * @param node2 : le second
     * @param if1 : l'interface du premier
     * @param if2 : l'interface du second
     * @return : <boolean>
     */
    boolean createLink(String node1, String node2, String if1, String if2);

    /**
     * Supprime une interface d'un host
     * @param host : l'équipement à  modifier
     * @return <boolean>
     */
    boolean delIf(String host);

    /**
     * Ajoute une interface à un équipement
     * @param host : l'équipement à modifier
     * @return <boolean>
     */
    boolean addIf(String host);

    /**
     * Renvoie la conf du host générée par le model
     * @param host : le nom du host
     * @return : <String> la conf
     */
    String getHostConf(String host);

    /**
     * Renvoie la conf globale du projet
     * @return : <String>
     */
    String getAllConf();

    //******************************************************************************************************************
    //*                          3] FONCTIONS D'ECOUTE                                                                 *
    //******************************************************************************************************************
}
