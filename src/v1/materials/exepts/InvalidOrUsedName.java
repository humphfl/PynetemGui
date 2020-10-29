package v1.materials.exepts;


/**
 * Classe exception levée si le nom est incorrect ou utilisé
 */
public class InvalidOrUsedName extends Exception {

    public InvalidOrUsedName(String s, String name) {
        super(s + " [name=" + name + "]");
    }
}