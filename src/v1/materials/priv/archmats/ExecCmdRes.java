package v1.materials.priv.archmats;

/**
 * Element renvoyé par la commande execute(cmd) de la classe Arch
 */
public class ExecCmdRes<M> {

    public final Expected ex;
    public M item;

    /**
     * crée le retour de commande typé en fonction de la commande
     * @param exp : type de retour
     * @param o : objet retourné
     */
    public ExecCmdRes(Expected exp, M o) {
        this.ex = exp;
        this.item = o;

    }

    public M getItem() {
        return this.ex.getCasted(item);
    }

    public Expected whatExpected() {
        return this.ex;
    }

}