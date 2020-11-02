package v2.vue.items;

import javafx.geometry.Point2D;

import java.util.ArrayList;

/**
 * Classe de multiliens (quand 2 équipements sont reliés plusieurs fois)
 */
public class Multilink {


    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************

    private static final int radius = 15;
    private final ArrayList<Link> lks = new ArrayList<>();


    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************

    public Multilink() {
    }


    //******************************************************************************************************************
    //*                          GETTERS METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Renvoie la liste des liens
     *
     * @return : ArrayList<Link>
     */
    public ArrayList<Link> getLinks() {
        return this.lks;
    }
    //******************************************************************************************************************
    //*                          ABSTRACT METHODS                                                                      *
    //******************************************************************************************************************

    //******************************************************************************************************************
    //*                          PUBLIC METHODS                                                                        *
    //******************************************************************************************************************


    /**
     * Ajoute un lien à la liste
     *
     * @param lk : <Link>
     */
    public void addLink(Link lk) {
        //System.out.println("ADDLINK:" + lk);
        //System.out.println("ok?" +this.lks.contains(lk));
        if (!this.lks.contains(lk)) {//TODO methode contains appel quoi ?
            this.lks.add(lk);
            lk.setMultiLink(this);
            this.addListeners(lk);

            recalculateOffsets();
        }
    }

    /**
     * supprime un lien de la liste
     *
     * @param lk : <Link>
     * @return : le lien supprimé (pour l'enlever de l'affichage)
     */
    public Link delLink(Link lk) {
        this.lks.remove(lk);
        lk.destroy();
        recalculateOffsets();
        return lk;
    }

    public void destroy() {
        for (Link lk : this.lks) {
            lk.destroy();
        }
        lks.clear();
    }

    public String toString(){
        StringBuilder ret = new StringBuilder("MultiLink:[");
        for(Link lk : this.lks){
            ret.append(lk).append(";");
        }
        ret.append("]");
        return ret.toString();

    }

    //******************************************************************************************************************
    //*                          PRIVATE METHODS                                                                       *
    //******************************************************************************************************************


    /**
     * Recalcul les offsets de chacun des liens et les met à jour
     * place les origines et fin sur une droite perpendiculaire à la direction donnée par les objets de début et fin
     */
    private void recalculateOffsets() {
        //System.out.println("----Recalculate Offset----");
        if (this.lks.size() == 0) {
            return;
        }

        //points de ref : quelque soit le lien les début et la fin sont identiques pour un multi lien
        Point2D st = lks.get(0).getStartRef();
        Point2D ed = lks.get(0).getEndRef();


        //norme de la droite reliant les 2 points
        double norm = Math.sqrt(Math.pow(ed.getX() - st.getX(), 2) + Math.pow(ed.getY() - st.getY(), 2));

        //vecteur unitaire de la droite de base :
        Point2D vect = new Point2D((ed.getX() - st.getX()) / norm, (ed.getY() - st.getY()) / norm);

        //Vecteur unitaire normal
        Point2D pVect = new Point2D(-vect.getY(), vect.getX());

        //Placement des points sur la nouvelle droite
        double nb = lks.size();
        for (Link lk : this.lks) {
            lk.setOffset(pVect.getX() * ((lks.indexOf(lk) - nb / 2f + .5f) / nb * radius), pVect.getY() * ((lks.indexOf(lk) - nb / 2f + .5f) / nb * radius));
        }
    }

    /**
     * Ajout des écouteurs pour la mise à jour grraphique du lien en fonction des déplacements ds 2 éléments reliés
     *
     * @param lk : <Link>
     */
    private void addListeners(Link lk) {

        //recalcule les  offsets à chaque déplacement de l'un des deux points de ref
        lk.getStart().getXPos().addListener((observable, oldValue, newValue) -> recalculateOffsets());
        lk.getStart().getYPos().addListener((observable, oldValue, newValue) -> recalculateOffsets());
        lk.getEnd().getXPos().addListener((observable, oldValue, newValue) -> recalculateOffsets());
        lk.getEnd().getYPos().addListener((observable, oldValue, newValue) -> recalculateOffsets());

    }


}
