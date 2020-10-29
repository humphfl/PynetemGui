package v1.materials.priv.abs;

/**
 * Représente un équipement qui s'intègre dans la section [node]
 */
public  abstract class Node extends Terminal {


    //******************************************************************
    //*  CONSTRUCTOR                                                   *
    //******************************************************************

    public Node(Type t, String nm) {
        super(t, nm);
    }


    //******************************************************************
    //*  PUBLIC METHODS                                                *
    //******************************************************************



    //******************************************************************
    //*    Extends Terminal                                            *
    //******************************************************************

    /**
     * Ajoute une interface au node
     * <p>
     * crée les noms dans l'ordre : if0, if1...
     */
    @Override
    public Connect addInterface() {

        Connect if_ = new Interface(Statics.IF + getIfsCount(), this, getIfsCount());
        addIf(if_);
        return if_;

    }

    /**
     * supprime la dernière interface
     * @return : l'interface supprimée
     */
    @Override
    public Connect removeInterface() {
        Connect del = getByName(Statics.IF + (getIfsCount() - 1));
        delIf(getByName(Statics.IF + (getIfsCount() - 1)));
        return del;
    }

    /**
     * Actions à faire à la destruction de l'objet
     * suppression de toutes les interfaces
     */
    @Override
    public void destroy() {
        while(removeInterface()!=null);
    }

    /**
     * Renvoie les données à écrire dans le fichier network.ini
     *
     * @return : un String contenant les données à écrire dans le fichier de conf
     */
    @Override
    public String printConf() {
        StringBuilder conf = new StringBuilder();
        conf.append("[[").append(toString()).append("]]\n");
        conf.append("type=").append(getType().tStr()).append("\n");
        conf.append("if_numbers = ").append(getIfsCount()).append("\n");


        for (Connect c : this.getIfs()) {
            /* ifn = host.ifn*/
            conf.append(c.toString()).append(" = ");
            if (c.getLink() == null) {
                conf.append("__null__\n");
            } else {
                conf.append(c.getLink().getParentTerm().toString()).append(".").append(c.getLink().getIndex()).append("\n");
            }

        }

        return conf.toString();
    }


}
