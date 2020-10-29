package v1.materials.priv.abs;

/**
 * défini quelque chose comme connectable : peut se lier à un autre objet conectable
 */
public interface Connect {

    /**
     * connecte à un autre
     *
     * @param other : autre <Connect> sur lequel se connecter
     */
    void connect(Connect other);

    /**
     * déconnecte le lien
     */
    void disConnect();

    /**
     * renvoie l'autre bout du lien
     *
     * @return : objet de type Connect ou null
     */
    Connect getLink();

    /**
     * Renvoie true si le <this> est lié à l'autre <Connect> en paramètre
     *
     * @param c : le <Connect> à vérifier
     * @return : true/false
     */
    boolean isLinked(Connect c);

    /**
     * Renvoie le <Terminal> auquel appartient le <Connect>
     *
     * @return : le <Terminal> possédant le <Connect>
     */
    Terminal getParentTerm();

    /**
     * Renvoie l'index du connectable
     *
     * @return : l'index sous forme de String
     */
    String getIndex();

    /**
     * renvoie le numéro d'interface
     *
     * @return : numéro au format int
     */
    public int getIntIndex();

    /**
     * Renvoie le nom de l'interface
     * @return : String
     */
    public String getName();
}
