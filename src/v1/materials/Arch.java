package v1.materials;

import fileIO.ConfRW;
import v1.materials.exepts.InvalidCommand;
import v1.materials.exepts.InvalidOrUsedName;
import v1.materials.priv.Host;
import v1.materials.priv.Router;
import v1.materials.priv.Switch;
import v1.materials.priv.abs.Connect;
import v1.materials.priv.abs.Node;
import v1.materials.priv.abs.Terminal;
import v1.materials.priv.archmats.ExecCmdRes;
import v1.materials.priv.archmats.Expected;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe représentant une architecture complète
 * <p>
 * Sert de conteneur aux éléments et propose quelques fonctions en plus
 */
public class Arch {


    //******************************************************************
    //*  CONSTANTES                                                    *
    //******************************************************************

    private static final String __NAMEMATCH = "^[a-zA-Z][0-9a-zA-Z_\\-]{0,20}";
    private static final String[] __CMDKEYWORDS = {"connect", "disconnect", "add", "del"};
    private static final String[] __ADDKEYWORDS = {"if", "eth", "Interface", "router", "switch", "bridge", "host"};
    private static final String[] __DELKEYWORDS = {"if", "eth", "Interface", "terminal"};


    //******************************************************************
    //*  VARIABLES                                                     *
    //******************************************************************

    // Liste des périphériques de la topo
    private final ArrayList<Terminal> materials;


    //******************************************************************
    //*  CONSTRUCTOR                                                   *
    //******************************************************************

    public Arch() {
        this.materials = new ArrayList<>();
    }


    //******************************************************************
    //*  GETTERS                                                       *
    //******************************************************************

    /**
     * Récupère un Terminal via son nom
     *
     * @param name : le nom du terminal à récupérer
     * @return : le <Terminal>
     */
    public Terminal getMatByName(String name) {

        //recherche de l'élément dans la liste par stream
        return materials.stream()
                .filter(search -> name.equals(search.toString()))
                .findAny()
                .orElse(null);
    }


    //******************************************************************
    //*  PUBLIC METHODS                                                *
    //******************************************************************

    /**
     * Ajoute un Host à la topo
     *
     * @return : le <Host> cree
     */
    public Host addHost(String name) throws InvalidOrUsedName {
        checkCreation(name);
        Host h = new Host(name);
        this.materials.add(h);
        return h;
    }

    /**
     * Ajoute un routeur à la topo
     *
     * @return : le <Router> cree
     */
    public Router addRouter(String name) throws InvalidOrUsedName {
        checkCreation(name);
        Router r = new Router(name);
        this.materials.add(r);
        return r;
    }

    /**
     * Ajoute un switch à la topo
     *
     * @return : le <Switch> cree
     */
    public Switch addSwitch(String name) throws InvalidOrUsedName {
        checkCreation(name);
        Switch sw = new Switch(name);
        this.materials.add(sw);
        return sw;

    }

    /**
     * connect t1.if t2.if : connecte deux terminaux ensemble via leur interfaces
     * connect t1.if sw : connecte un terminal sur un switch
     * disconnect t1.if : déconnecte l'interface d'un terminal
     * disconnect term : déconnecte toutes les interfaces d'un terminal
     * <p>
     * syntaxe : [node1, if1, node2, if2] : note to node
     * [node1, if1, sw] : note sur switch
     * [sw1, node1, if1] : switch to node
     *
     * @param cmd : la commande sous forme de String
     * @throws InvalidCommand : erreur de commande invalide
     */
    public ExecCmdRes execute(String cmd) throws InvalidCommand, InvalidOrUsedName {
        if (checkCommand(cmd, __CMDKEYWORDS)) {
            throw new InvalidCommand("commande inexistante !", cmd);
        }

        //séparation  des éléments de la commande
        String[] lst = cmd.split(" ");

        //on prend pas la casse de la commande en considération
        lst[0] = lst[0].toLowerCase();

        ExecCmdRes ret = null;

        switch (lst[0]) {
            case "connect" -> ret = cmdConnect(cmd);
            case "disconnect" -> ret = cmdDisconnect(cmd);
            case "del" -> ret = cmdDel(cmd);
            case "add" -> ret = cmdAdd(cmd);
        }
        return ret;
    }

    /**
     * renvoie le  contenu du fichier network.ini
     *
     * @return : String
     */
    public String printConf() {
        return ConfRW.createConf(this.materials);
    }

    //******************************************************************
    //*  PRIVATE METHODS                                               *
    //******************************************************************

    /**
     * Effectue une vérification sur le nom d'un terminal
     * Le nom ne doit pas déjà exister et doit matcher une certaine syntaxe
     *
     * @param name : le nom à vérifier
     * @throws InvalidOrUsedName : erreur levée si le nom n'est pas correct
     */
    private void checkCreation(String name) throws InvalidOrUsedName {
        if (getMatByName(name) != null) {
            throw new InvalidOrUsedName("Le nom est déjà utilisé !", name);
        }
        if (!name.matches(Arch.__NAMEMATCH)) {
            throw new InvalidOrUsedName("Le nom n'est pas valable !", name);
        }
    }

    /**
     * Vérifie la validité d'une commande
     *
     * @param cmd : la commande
     */
    private boolean checkCommand(String cmd, String[] keywords) {
        String[] lst = cmd.split(" ");
        for (String key : keywords) {
            //System.out.println("checkCommand ==> command=[" + lst[0] + "]" + "key=[" + key + "]");
            if (lst[0].toLowerCase().equals(key.toLowerCase())) {
                //System.out.println("TRUE!!");
                return false;
            }
        }
        return true;
    }

    /**
     * Formate l'interface en ifX
     *
     * @param if_ : String de l'interface
     * @return : l'interface au bon format
     */
    private String prepIf(String if_) {

        //match une interface sur 3 chiffres max ne commencent pas par 0
        // ifx, ifxy (y!=0) ifxyz(x y != 0)
        if (if_.matches("(eth|if)?(0|[1-9]0?|[1-9][0-9]{1,2})")) {

            Pattern p = Pattern.compile("[0-9]{1,3}");
            Matcher m = p.matcher(if_);
            if (!m.find()) {
                return "";
            }
            return "if" + m.group(0);
        }
        return "";
    }


    //******************************************************************
    //*  COMMANDS METHODS                                              *
    //******************************************************************

    /**
     * Interprétation de la commande CONNECT
     *
     * @param cmd : la commande et ses paramètres
     * @throws InvalidCommand : exception levée si la commande est incorrect ou échoue
     */
    private ExecCmdRes cmdConnect(String cmd) throws InvalidCommand {
        String[] lst = cmd.split(" ");
        if (lst.length < 2 || lst.length > 3) {
            throw new InvalidCommand("CONNECT : nombre de paramètres incorrect : connect term1.if|sw term2.if|sw", cmd);
        }
        String[] t1 = lst[1].split("\\.");
        String[] t2 = lst[2].split("\\.");

        //erreur de syntaxe
        if (t1.length > 2 || t2.length > 2) {
            throw new InvalidCommand("CONNECT : Erreur de syntaxe sur l'un des paramètres", cmd);
        }
        Terminal term1 = getMatByName(t1[0]);
        Terminal term2 = getMatByName(t2[0]);
        if (term1 == null || term2 == null) {
            throw new InvalidCommand("CONNECT : l'un des terminaux passé en paramètre n'existe pas", cmd);
        }

        Connect if1 = null;
        Connect if2 = null;

        //on récupère l'interface demandée si le terminal est un Node
        if (term1.getType().isNode() && t1.length == 2) {
            t1[1] = prepIf(t1[1]);
            if1 = term1.getByName(t1[1]);
        } else if (term1.getType().isSwitch()) {
            if1 = (Connect) term1;//On caste term1 si c'est un switch
        }
        if (term2.getType().isNode() && t2.length == 2) {
            t2[1] = prepIf(t2[1]);
            if2 = term2.getByName(t2[1]);
        } else if (term2.getType().isSwitch()) {
            if2 = (Connect) term2;//On caste term2 si c'est un switch
        }

        //traitement de l'erreur si connexion de 2 switchs
        if (term1.getType().isSwitch() && term2.getType().isSwitch()) {
            throw new InvalidCommand("CONNECT : connexion de 2 switchs interdite", cmd);
        }

        if (if1 == null || if2 == null) {
            if (t1.length == 1 && if1 == null || t2.length == 1 && if2 == null) {
                throw new InvalidCommand("CONNECT : l'interface n'est pas spécifiée sur un des terminaux", cmd);
            }
            throw new InvalidCommand("CONNECT : l'interface demandée sur l'un des terminaux n'existe pas", cmd);
        }
        //On fait la connexion
        if1.connect(if2);
        Connect[] ifarr = {if1, if2};
        return new ExecCmdRes(Expected.LINK, ifarr);

    }

    /**
     * Interprétation de la commande DISCONNECT
     *
     * @param cmd : la commande et ses paramètres
     * @throws InvalidCommand : exception levée si la commande est incorrect ou échoue
     */
    private ExecCmdRes cmdDisconnect(String cmd) throws InvalidCommand {
        String[] lst = cmd.split(" ");

        ExecCmdRes ret = null;

        if (lst.length != 2) {
            throw new InvalidCommand("DISCONNECT : nombre de paramètres incorrect : disconnect term1.if|sw|term", cmd);
        }
        String[] t = lst[1].split("\\.");

        //erreur de syntaxe
        if (t.length > 2) {
            throw new InvalidCommand("DISCONNECT : Erreur de syntaxe sur l'argument", cmd);
        }

        Terminal term1 = getMatByName(t[0]);

        //terminal inexistant
        if (term1 == null) {
            throw new InvalidCommand("DISCONNECT : le terminal passé en paramètre n'existe pas", cmd);
        }

        //on récupère l'interface demandée si le terminal est un Node
        if (term1.getType().isNode() && t.length == 2) {
            t[1] = prepIf(t[1]);
            if(term1.getByName(t[1])==null){
                throw new InvalidCommand("DISCONNECT : l'interface n'existe pas", cmd);
            }
            term1.getByName(t[1]).disConnect();
            ret = new ExecCmdRes(Expected.INTERFACE, term1.getByName(t[1]));
        } else if (term1.getType().isSwitch()) {
            ((Switch) term1).disConnect();//On caste term1 si c'est un switch
            ret = new ExecCmdRes(Expected.SWITCH, ((Switch) term1));

        } else if (term1.getType().isNode() && t.length == 1) {
            //cas du terminal node seul en paramètre : on déconnecte tout
            ret = new ExecCmdRes(Expected.NODE, ((Node) term1));
            for (Connect f : term1.getIfs()) {
                f.disConnect();
            }
        }
        return ret;
    }

    /**
     * Interprétation de la commande add
     * add if|router|switch|bridge [name]
     *
     * @param cmd : la commande
     * @throws InvalidCommand : erreur de commande invalide
     * @throws InvalidOrUsedName : erreur de nom invalide (pour les ajouts de terminaux)
     */
    private ExecCmdRes cmdAdd(String cmd) throws InvalidCommand, InvalidOrUsedName {
        String[] lst = cmd.split(" ");
        if (lst.length < 3) {
            throw new InvalidCommand("ADD : nombre d'arguments insuffisant", cmd);
        }
        if (lst.length > 3) {
            throw new InvalidCommand("ADD : trop d'arguments", cmd);
        }
        if (checkCommand(lst[1], __ADDKEYWORDS)) {
            throw new InvalidCommand("ADD : arguments incorrects", cmd);
        }

        lst[1] = lst[1].toLowerCase();

        ExecCmdRes ret = null;

        switch (lst[1]) {
            case "if":
            case "eth":
            case "interface":
                Terminal t = getMatByName(lst[2]);
                if(t == null){
                    throw new InvalidCommand("ADD : terminal inexistant", cmd);
                }
                if(t instanceof Switch){
                    throw new InvalidCommand("ADD IF : impossible d'ajouter une interface à un switch", cmd);
                }
                ret = new ExecCmdRes<>(Expected.INTERFACE, t.addInterface());
                break;
            case "router":
                ret = new ExecCmdRes<>(Expected.ROUTER, addRouter(lst[2]));
                break;
            case "switch":
                ret = new ExecCmdRes<>(Expected.SWITCH, addSwitch(lst[2]));
                break;
            case "bridge":
                break;
            case "host":
                ret = new ExecCmdRes<>(Expected.HOST, addHost(lst[2]));

        }
        return ret;
    }

    /**
     * Interprétation de la commande del
     * del if|router|switch|bridge [Terminal]
     *
     * @param cmd : la commande
     * @throws InvalidCommand : erreur de commande invalide
     */
    private ExecCmdRes cmdDel(String cmd) throws InvalidCommand {
        String[] lst = cmd.split(" ");
        if (lst.length < 3) {
            throw new InvalidCommand("ADD : nombre d'arguments insuffisant", cmd);
        }
        if (lst.length > 4) {
            throw new InvalidCommand("ADD : trop d'arguments", cmd);
        }
        if (checkCommand(lst[1], __DELKEYWORDS)) {
            throw new InvalidCommand("ADD : arguments incorrects", cmd);
        }

        lst[1] = lst[1].toLowerCase();

        ExecCmdRes ret = null;

        Terminal t = getMatByName(lst[2]);

        if(t == null){
            throw new InvalidCommand("ADD : terminal inexistant", cmd);
        }
        if(t instanceof Switch){
            throw new InvalidCommand("ADD IF : impossible d'ajouter une interface à un switch", cmd);
        }

        switch (lst[1]) {
            case "if", "eth", "interface" -> ret = new ExecCmdRes<>(Expected.INTERFACE, t.removeInterface());
            case "terminal" -> {
                t.destroy();
                ret = new ExecCmdRes<>(Expected.ROUTER, t);
            }
        }
        return ret;
    }
}


//**********************************************************************************************************************
//**********************************************************************************************************************
//**                                                                                                                  **
//**  CLASSES INTERNES                                                                                                **
//**                                                                                                                  **
//**********************************************************************************************************************
//**********************************************************************************************************************


