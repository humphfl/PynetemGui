package v1.materials.exepts;


/**
 * Classe exception levée lorsqu'une commande est invalide
 */
public class InvalidCommand extends Exception {

    public InvalidCommand(String s, String cmd) {
        super(s + " [cmd=" + cmd + "]");
    }
}