package v1.materials.priv;

import v1.materials.priv.abs.Node;
import v1.materials.priv.abs.Type;

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
