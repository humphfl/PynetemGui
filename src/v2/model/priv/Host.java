package v2.model.priv;

import v2.model.priv.abs.Node;
import v2.model.priv.abs.Type;

/**
 * équimement de type host (docker.host)
 */
public class Host extends Node {


    //******************************************************************
    //*  CONSTRUCTOR                                                   *
    //******************************************************************

    public Host(String nm) {
        super(Type.host, nm);
    }

}
