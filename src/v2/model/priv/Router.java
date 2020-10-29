package v2.model.priv;

import v2.model.priv.abs.Node;
import v2.model.priv.abs.Type;

/**
 * Equipement routeur (docker.frr)
 */
public class Router extends Node {


    //******************************************************************
    //*  CONSTRUCTOR                                                   *
    //******************************************************************

    public Router(String nm) {
        super(Type.frr, nm);
    }
}
