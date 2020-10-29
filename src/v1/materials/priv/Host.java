package v1.materials.priv;

import v1.materials.priv.abs.Node;
import v1.materials.priv.abs.Type;

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
