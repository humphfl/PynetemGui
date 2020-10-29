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
    public boolean checkHostCreation(String name);

    /**
     * Vérifie si la création d'un <Router> est possible
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    public boolean checkRouterCreation(String name);


    /**
     * Vérifie si la création d'un <Switch> est possible
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    public boolean checkSwitchCreation(String name);

    /**
     * Vérifie si la création d'un <Host> est possible
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    public boolean checkBridgeCreation(String name);

    //******************************************************************************************************************
    //*                          2] FONCTIONS DE MODIFICATION DES TERMINAUX                                            *
    //******************************************************************************************************************


    //******************************************************************************************************************
    //*                          3] FONCTIONS D'ECOUTE                                                                 *
    //******************************************************************************************************************
}
