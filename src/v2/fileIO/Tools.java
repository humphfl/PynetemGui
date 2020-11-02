package v2.fileIO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Linux ENAC :
 * Run configuration JavaFX : --module-path /usr/share/openjfx/lib --add-modules javafx.controls,javafx.fxml
 * Windows :
 * Run configuration JavaFX : --module-path  %JAVAFX_PATH% --add-modules javafx.controls,javafx.fxml
 *
 * @author loiseafl
 * @version : 3.4
 * <p>
 * version hist :
 * @3.4: ajout du --module-path pour windows dans les commentaires en début de fichier
 * @3.3: ajout de la fonction writeLBN : écriture de lignes dans un nouveau fichier avec un BufferWriter
 * ajout de la fonction writeLBA : écriture de lignes dans un fichier en mode append avec un BufferWriter
 * ajout de la fonction convertArray qui transforme un objet collection en String[]
 * <p>
 * ajout de CH_TOOLS_DEBUG et CH_TOOLS_INFO
 * @3.2: ajout de la gestion d'entiers aléatoires uniques dans la classe Singleton
 * +resetArray() : réinitialise la liste des nombres tirés
 * +genUniqueNB(): génère un entier aléatoire qui n'a pas déjà  été tiré
 * +setMinMax(int m, int M): fixe les bornes min et max pour le nombre aléatoire
 * @3.1: change LOG_LV_0 to CH_INFO
 * change LOG_LV_1 to CH_EVENTS_INFO
 * change LOG_LV_9 to CH_EVT_DEBUG
 * change LOG_LV_2 to CH_EVENTS_ERR_INFO
 */
public abstract class Tools {

    /**
     * ######################################################################
     * #               Log fcts												#
     * #######################################################################
     */

    public static boolean disp = true;
    public static boolean dispErr = true;
    public static boolean[] lstChans = {true, true, true, true, true, true, true, true, true, true, true, true};
    //									0	  1		2	  3		4	  5		6	  7		8	  9		10	  11

    //chan explicit names TODO : changer les noms
    public static int CH_INFO = 0;
    public static int CH_DEBUG = 1;
    public static int CH_EVENTS_INFO = 2;
    public static int CH_EVENTS_DEBUG = 3;
    public static int CH_FCTS_INFO = 4;
    public static int CH_FCTS_DEBUG = 5;
    public static int LOG_LV_6 = 6;
    public static int LOG_LV_7 = 7;
    public static int LOG_LV_8 = 8;
    public static int LOG_LV_9 = 9;
    public static int CH_TOOLS_INFO = 10;
    public static int CH_TOOLS_DEBUG = 11;


    /* affichage conditionnel d'un texte
     * condition : disp == true
     */
    public static void log(Object txt) {
        if (disp)
            System.out.println(txt);
    }

    /* affichage conditionnel d'un texte sur le stdErr
     * condition : dispErr == true
     */
    public static void logErr(Object txt) {
        if (dispErr)
            System.err.println(txt);
    }

    /**
     * @param txt  texte à  écrire
     * @param chan "canal" de sortie
     *             (utile pour l'activation ou la désactivation de l'affichage de certains éléments)
     */
    public static void log(Object txt, int chan) {
        chan = Math.abs(chan) % lstChans.length;

        if (lstChans[chan])
            System.out.println(Tools.formatDouble(chan, 0, 2) + ")" + txt);
    }


    /**######################################################################
     * #               I/O fcts												#
     *#######################################################################*/

    /**
     * saisie d'une entrée au clavier
     *
     * @param text : le texte à  afficher avant la saisie
     * @return
     */
    public static String userKeyIn(String text) {
        Scanner sc = Singleton.getInstance().sc;
        System.out.println(text);
        String s = sc.nextLine();
        //		sc.close();
        return s;
    }


    /**######################################################################
     * #               Object to File R/W Fcts								#
     *#######################################################################*/

    /**
     * sauvegarde d'un objet dans un fichier
     *
     * @param nomFic
     * @param o
     */
    public static void writeObj(String nomFic, Object o) {
        ObjectOutputStream os;
        try {
            os = new ObjectOutputStream(new FileOutputStream(nomFic));

            os.writeObject(o);

        } catch (FileNotFoundException e1) {
            logErr("Fichier non trouvé : " + nomFic);
            e1.printStackTrace();
            return;
        } catch (IOException e1) {
            logErr("IO Exception : " + nomFic);
            e1.printStackTrace();
            return;
        }

        try {
            os.close();
        } catch (IOException e) {
            logErr("erreur à  la fermeture");
            e.printStackTrace();
        }
    }

    /**
     * charge un objet à  partir d'un fichier (peut renvoyer null !)
     *
     * @param nomFic
     * @return Object
     */
    @SuppressWarnings("null")
    public static Object readObj(String nomFic) {
        ObjectInputStream os;
        Object rt = null;

        try {
            os = new ObjectInputStream(new FileInputStream(nomFic));

            try {
                rt = os.readObject();
            } catch (ClassNotFoundException e) {
                logErr("objet non trouvé : " + rt.getClass().toString());
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            logErr("Fichier non trouvé : " + nomFic);
            e.printStackTrace();
        } catch (IOException e) {
            logErr("IO Exception : " + nomFic);
            e.printStackTrace();
        }
        return rt;
    }


    /**######################################################################
     * #               File lines R/W Fcts									#
     *#######################################################################*/

    /**
     * lecture des lignes avec un BufferReader
     *
     * @param nomFic
     * @return String[]
     */
    public static String[] readLB(String nomFic) {
        ArrayList<String> lst = new ArrayList<String>();
        String line = "";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(nomFic));
            lst.add(reader.readLine());
            while (line != null) {
                // read next line
                lst.add(reader.readLine());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lst.toArray(new String[lst.size()]);
    }


    /**
     * lecture des lignes avec un Scanner
     *
     * @param nomFic
     * @return
     */
    public static String[] readLS(String nomFic) {
        ArrayList<String> lst = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(new File(nomFic));
            while (scanner.hasNextLine()) {
                lst.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lst.toArray(new String[lst.size()]);
    }


    /**
     * lecture des lignes avec Files
     *
     * @param nomFic
     * @return
     */
    public static String[] readLF(String nomFic) {
        String[] ret = {};
        try {
            List<String> allLines = Files.readAllLines(Paths.get(nomFic));
            ret = allLines.toArray(new String[allLines.size()]);
        } catch (IOException e) {
            logErr("fichier non trouvé !! " + nomFic);
            e.printStackTrace();
        }

        return ret;
    }


    /**
     * écriture des lignes dans un NOUVEAU fichier avec un BufferWriter
     *
     * @param nomfichier
     * @param lines[]
     */
    public static void writeLBN(String nomfichier, String[] lines) {
        BufferedWriter fw;
        try {
            fw = new BufferedWriter(new FileWriter(nomfichier));
            if (lines.length > 0) {
                for (String s : lines) {
                    fw.write(s);
                    fw.newLine();
                }
            }
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * écriture des lignes dans un fichier en mode ajout (append) avec un BufferWriter
     *
     * @param nomfichier
     * @param lines[]
     */
    public static void writeLBA(String nomfichier, String[] lines) {
        try {

            File file = new File(nomfichier);

            /* This logic is to create the file if the
             * file is not already present
             */
            if (!file.exists()) {
                file.createNewFile();
            }

            //Here true is to append the content to file
            FileWriter fw = new FileWriter(file, true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            if (lines.length > 0) {
                for (String s : lines) {
                    bw.write(s);
                    bw.newLine();
                }
            }
            //Closing BufferedWriter Stream
            bw.close();


        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**######################################################################
     * #               Mise en forme de String								#
     *#######################################################################*/

    /**
     * format 00h from int
     *
     * @param i
     * @return
     */
    public static String GenerateKey(int i) {
        return String.format("%02dh", i);
    }


    /**
     * renvoie un String du double entrée en paramètre avec s chiffres après la virgule
     *
     * @param d
     * @param s
     * @return
     */
    public static String formatDouble(double d, int s) {
        return String.format(("%." + s + "f"), d);
    }

    /**
     * renvoie un String du double entrée en paramètre avec <s> chiffres après la virgule
     *
     * @param d
     * @param s : nombre de chiffres après la virgule
     * @param z : nombre de zeros à  completer sur la partie entière
     * @return
     */
    public static String formatDouble(double d, int s, int z) {
        return String.format(("%0" + z + "." + s + "f"), d);
    }


    /**
     * transforme un objet collection en liste simple de String via le toString de chaque élément
     *
     * @param lst
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String[] convertArray(Collection lst) {
        if (lst.size() > 0) {
            ArrayList<String> s = new ArrayList<String>();
            for (Iterator iterator = lst.iterator(); iterator.hasNext(); ) {
                s.add(iterator.next().toString());
            }
            return s.toArray(new String[s.size()]);
        }
        return null;
    }

    /**######################################################################
     * #               Random fcts											#
     *#######################################################################*/

    /**
     * génère un nombre entier aléatoire (les bornes sont fixées par setMinMax)
     *
     * @return
     */
    public static int genNB() {
        return Singleton.getInstance().genUniqueNB();
    }

    /**
     * saisie des valeurs min et max pour la génération aléatoire
     *
     * @param min
     * @param max
     */
    public static void setMinMax(int min, int max) {
        Tools.log("Tools.setMinMax [" + min + ":" + max + "]", Tools.CH_TOOLS_DEBUG);
        Singleton.getInstance().setMinMax(min, max);
    }

    /**
     * réinitialise la liste des nombres déjà  générés
     */
    public static void resetNB() {
        Singleton.getInstance().resetArray();
    }
}


//#######################################################################
//#			Singleton													#
//#######################################################################

/**
 * classe Singleton contenant un Scanner System.in pour les saisies clavier
 * ce scanner reste ouvert tout le temps d'execution du programme
 * le scanner est fermé lors du finalize();
 *
 * @author loiseafl
 */
class Singleton {
    // static variable single_instance of type Singleton
    private static Singleton single_instance = null;

    // variable of type String
    public Scanner sc;
    public int cout1 = 0;
    public ArrayList<Integer> gen;//liste d'entiers déjà  tirés par random
    public int min = 0;
    public int max = 50;

    // private constructor restricted to this class itself
    private Singleton() {
        sc = new Scanner(System.in);
        resetArray();
    }

    // static method to create instance of Singleton class
    public static Singleton getInstance() {
        if (single_instance == null)
            single_instance = new Singleton();

        return single_instance;
    }

    @Override
    protected void finalize() {
        sc.close();
        single_instance = null;
        gen = null;
    }

    public void setMinMax(int m, int M) {
        this.min = m;
        this.max = M;
    }

    /**
     * reset la liste des numéros uniques déjà  tirés
     */
    public void resetArray() {
        this.gen = new ArrayList<Integer>();
    }

    /**
     * génère un nombre unique aléatoire compris entre min et max
     *
     * @return
     */
    public int genUniqueNB() {
        int r = (int) (Math.ceil(Math.random() * (max - min)) + min);
        while (this.gen.contains(r) && gen.size() < (max - min)) {
            r = (int) (Math.ceil(Math.random() * (max - min)) + min);
        }
        gen.add(r);
        Tools.log("genLST = -" + gen.toString() + "-" + " r=" + r, Tools.CH_TOOLS_DEBUG);
        return r;
    }
}