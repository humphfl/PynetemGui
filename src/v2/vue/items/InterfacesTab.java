package v2.vue.items;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.scene.control.*;
import v2.vue.items.abstracts.AbstractItem;

import java.util.ArrayList;
import java.util.Arrays;

public class InterfacesTab {
    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************

    private final AbstractItem host;
    private final TableView<String> ifTable = new TableView<>();

    private final Button btAdd = new Button("Ajouter");
    private final Button btDel = new Button("Supprimer");
    private final CustomMenuItem addIf = new CustomMenuItem(btAdd);
    private final CustomMenuItem delIf = new CustomMenuItem(btDel);
    private final CustomMenuItem table = new CustomMenuItem(ifTable);


    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************

    public InterfacesTab(AbstractItem hst) {
        this.host = hst;

        addIf.setHideOnClick(false);
        delIf.setHideOnClick(false);
        table.setHideOnClick(false);
        createVisu();
        addListeners();
    }


    //******************************************************************************************************************
    //*                          GETTERS METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Renvoie les items du menu
     *
     * @return : <ArrayList>
     */
    public ArrayList<CustomMenuItem> getItems() {
        return new ArrayList<>(Arrays.asList(addIf, delIf, table));
    }
    //******************************************************************************************************************
    //*                          SETTERS METHODS                                                                       *
    //******************************************************************************************************************


    //******************************************************************************************************************
    //*                          PRIVATE METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Ajoute les Listeners
     */
    private void addListeners() {

        //Listener sur la map du host -> on met à jour le contenu de la table
        host.getIfsMap().addListener((MapChangeListener<String, Link>) change -> update());

        //Mise en place des actions à faire pour les bouttons ajouter et supprimer
        btAdd.setOnAction(event -> host.addIf());
        btDel.setOnAction(event -> host.delIf());

        ifTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("selected = " + newValue);
                ConnectionManager manager = ConnectionManager.getInstance();
                if(!manager.isOnConnect()){
                    manager.setOnConnect();
                    manager.setStart(host);
                    manager.setStartIfName(newValue);

                } else {
                    manager.setEndIfName(newValue);
                    manager.mkLink(host);
                    manager.setNotOnConnect();
                    //TODO gérer le multilien

                }
            }
        });
    }


    /**
     * Création des éléments visuels
     */
    private void createVisu() {

        //création des colonnes
        TableColumn<String, CheckBox> if1Check = new TableColumn<>();
        TableColumn<String, String> if1name = new TableColumn<>();
        TableColumn<String, CheckBox> if2Check = new TableColumn<>();
        TableColumn<String, String> if2name = new TableColumn<>();
        ifTable.getColumns().addAll(if1Check, if1name, if2Check, if2name);


        //Colonne 1 : checkBos de l'affichage ou non du label coté interfaces du host
        if1Check.setCellValueFactory(param -> {

            //récupération du lien lk
            final Link lk = host.getIfsMap().get(param.getValue());
            final BooleanProperty myProp;

            //récupération de la bonne propriété (en fonction de si le host est en start ou end du lien)
            if (lk != null) {
                if (lk.getStart() == host) {
                    myProp = lk.getDispLabStart();
                } else {
                    myProp = lk.getDispLabEnd();
                }

                //Création d'une checkBox
                CheckBox ck = new CheckBox();
                ck.setSelected(lk.getDispLabStart().getValue());

                //Mise en place de l'action à faire quand on coche/décoche
                ck.setOnAction(event -> {
                    //Modification de la propriété concernée
                    myProp.setValue(ck.isSelected());
                });

                //Ajout d'un Listener sur la propriété pour mettre à jour la checkBox
                myProp.addListener((observable, oldValue, newValue) -> ck.setSelected(myProp.getValue()));

                //Renvoie de la ck encapsulée
                return new SimpleObjectProperty<>(ck);
            }
            return null;
        });

        //Colonne 2 : nom de l'interface coté host
        if1name.setCellValueFactory(param -> {
            //final Link lk = host.getIfsMap().get(param.getValue());
            /*if (lk != null) {
                String st, ed;
                if (lk.getStart() == host) {
                    st = lk.getLabs()[0].getText();
                } else {
                    st = lk.getLabs()[1].getText();
                }


                return new SimpleStringProperty(st);
            }*/
            return new SimpleStringProperty(param.getValue());
        });

        //Colonne 3 :

        //Colonne 4 : nom et host de l'interface distante
        if2name.setCellValueFactory(param -> {
            //récupération du lien
            final Link lk = host.getIfsMap().get(param.getValue());

            //L'interface restera déconnectée si le lien lk est null
            String ret = "Disconnected";
            if (lk != null) {
                String  ed;

                //Récupération du bon nom de l'interface distante (en fonction de sa position start ou stop dans le lien)
                if (lk.getStart() == host) {
                    //Si le host est en start l'autre est en end => [1]
                    ed = lk.getLabs()[1].getText();
                } else {
                    //Si le host est en end l'autre est en start => [0]
                    ed = lk.getLabs()[0].getText();
                }
                ret = lk.getEnd().getLblName().getText() + "." + ed;
            }
            return new SimpleStringProperty(ret);
        });


    }

    /**
     * Mise à jour de la table
     */
    private void update() {
        ifTable.getItems().clear();
        ifTable.getItems().setAll(host.getIfsMap().keySet());
        //ifTable.getSelectionModel().clearSelection();

        //System.out.println("Table updated");

    }

    //******************************************************************************************************************
    //*                          PROTECTED METHODS                                                                     *
    //******************************************************************************************************************

    //******************************************************************************************************************
    //*                          PUBLIC METHODS                                                                        *
    //******************************************************************************************************************

    public void addInterface(String key, Link added) {
        System.out.println(this.host.getLblName().getText() + " : add action");

    }

    private void delInterface(String key, Link removed) {
        System.out.println(this.host.getLblName().getText() + " : del action");

    }

}
