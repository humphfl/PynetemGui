package v2.model.priv.abs;

/**
 * Enumération des types d'équipements
 */
public enum Type {



    host("docker.host", tClass.NODE),
    frr("docker.frr", tClass.NODE),
    ovs("ovs", tClass.SWITCH),
    ove("ove", tClass.SWITCH);

    private final String tpe ;
    private final tClass cls;

    private Type(String t, tClass tc) {
        this.tpe = t ;
        cls = tc;
    }

    public String tStr() {
        return  this.tpe ;
    }

    public boolean isNode(){
        return this.cls == tClass.NODE;
    }

    public boolean isSwitch(){
        return this.cls == tClass.SWITCH;
    }

    public boolean isBridge(){
        return this.cls == tClass.BRIDGE;
    }

}

/**
 * Enumération des catégorie d'équipement
 */
enum tClass{
    SWITCH,
    NODE,
    BRIDGE;
}
