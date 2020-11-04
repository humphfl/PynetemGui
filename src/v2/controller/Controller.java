package v2.controller;

public class Controller implements APIController {

    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************

    APIModel model;


    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************

    public Controller(APIModel mod) {
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
     *
     * @param name : le nom du term
     * @return ; true/false
     */
    private boolean checkTerminalCreation(String name) {
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
    public boolean createHost(String name) {

        if (!checkTerminalCreation(name)) {
            model.addHost(name);
            return true;
        }
        return false;
    }

    /**
     * Vérifie si la création d'un <Router> est possible
     *
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    @Override
    public boolean createRouter(String name) {
        if (!checkTerminalCreation(name)) {
            model.addRouter(name);
            return true;
        }
        return false;
    }

    /**
     * Vérifie si la création d'un <Switch> est possible
     *
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    @Override
    public boolean createSwitch(String name) {
        if (!checkTerminalCreation(name)) {
            model.addSwitch(name);
            return true;
        }
        return false;
    }

    /**
     * Vérifie si la création d'un <Host> est possible
     *
     * @param name : le nom du nouvel équipement
     * @return : true/false
     */
    @Override
    public boolean createBridge(String name) {
        if (!checkTerminalCreation(name)) {
            model.addBridge(name);
            return true;
        }
        return false;
    }

    /**
     * Qupprime un lien d'une interface
     *
     * @param hostName : <String> le nom du Host
     * @param ifName   : <String> : le nom de l'interface
     * @return : <boolean>
     */
    @Override
    public boolean delLink(String hostName, String ifName) {

        return !model.disconnect(hostName, ifName.replace("eth", "if")).equals("");
    }

    /**
     * Crée un lien entre 2 équipements
     *
     * @param node1 : le premier
     * @param node2 : le second
     * @param if1   : l'interface du premier
     * @param if2   : l'interface du second
     * @return : <boolean>
     */
    @Override
    public boolean createLink(String node1, String if1, String node2, String if2) {
        return !model.connect(node1, if1.replace("eth", "if"), node2, if2.replace("eth", "if")).equals("");
    }

    /**
     * Supprime une interface d'un host
     *
     * @param host : l'équipement à  modifier
     * @return <boolean>
     */
    @Override
    public boolean delIf(String host) {
        //System.out.println("Controller : delIf :" + host);
        return !model.delIf(host).equals("");
    }

    /**
     * Ajoute une interface à un équipement
     *
     * @param host : l'équipement à modifier
     * @return <boolean>
     */
    @Override
    public boolean addIf(String host) {
        //System.out.println("Controller : addIf :" + host);
        return !model.addIf(host).equals("");
    }

    /**
     * Renomme le terminal
     *
     * @param term    : le terminal à renommer
     * @param newName : le nouveau nom
     * @return : le nouveau nom si le changement s'est fait correctement, l'ancien sinon
     */
    @Override
    public String rename(String term, String newName) {
        if(!model.exist(newName)){
            model.rename(term, newName);
            return newName;
        }
        return term;
    }

    /**
     * Supprime un terminal passé en paramètre
     *
     * @param name : le nom du term
     */
    @Override
    public void delTerm(String name) {
        if(model.exist(name)){
            model.delTerm(name);
        }
    }

    /**
     * Renvoie un nom préfixé et disponible
     *
     * @param prefix : le prefix à ajouter au nom
     * @return : <String> : le nom
     */
    @Override
    public String getName(String prefix) {
        int index = 0;
        while(model.exist(prefix+index)){
            index++;
        }
        return prefix+index;
    }

    /**
     * Renvoie la conf du host générée par le model
     *
     * @param host : le nom du host
     * @return : <String> la conf
     */
    @Override
    public String getHostConf(String host) {
        return model.printTermConf(host);
    }

    /**
     * Renvoie la conf globale du projet
     *
     * @return : <String>
     */
    @Override
    public String getAllConf() {
        return model.printConf();
    }


}
