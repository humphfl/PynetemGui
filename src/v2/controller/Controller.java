package v2.controller;

public class Controller implements APIController{

    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************

    APIModel model;


    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************

    public Controller(APIModel mod){
        this.model = mod;
    }

    //******************************************************************************************************************
    //*                          PUBLIC METHODS                                                                        *
    //******************************************************************************************************************
    //******************************************************************************************************************
    //*                          PRIVATE METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Check si un <Terminal> existe déjà
     * @param name : le nom du term
     * @return ; true/false
     */
    private boolean checkTerminalCreation(String name){
        return model.exist(name);
    }

    //******************************************************************************************************************
    //*                          Implementations de APIController                                                      *
    //******************************************************************************************************************

    /**
     * Vérifie si la création d'un <Host> est possible
     *
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    @Override
    public boolean checkHostCreation(String name) {
        return checkTerminalCreation(name);
    }

    /**
     * Vérifie si la création d'un <Router> est possible
     *
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    @Override
    public boolean checkRouterCreation(String name) {
        return checkTerminalCreation(name);
    }

    /**
     * Vérifie si la création d'un <Switch> est possible
     *
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    @Override
    public boolean checkSwitchCreation(String name) {
        return checkTerminalCreation(name);
    }

    /**
     * Vérifie si la création d'un <Host> est possible
     *
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    @Override
    public boolean checkBridgeCreation(String name) {
        return checkTerminalCreation(name);
    }
}
