package v2.vue.items;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;
import v2.vue.items.abstracts.AbstractItem;

public class ContextMnu extends ContextMenu {


    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************

    private final StringProperty name = new SimpleStringProperty();
    private final BooleanProperty dispLabels = new SimpleBooleanProperty();
    private final AbstractItem anchor;


    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************


    /**
     * Create a new ContextMenu
     */
    public ContextMnu(AbstractItem anchor, String name) {
        this.anchor = anchor;
        setContextMenu();
        this.name.setValue(name);
    }


    //******************************************************************************************************************
    //*                          GETTERS METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Renvoie la propriété de l'affichege/non affichage des labels des interfaces
     *
     * @return : <BooleanProperty>
     */
    public BooleanProperty getDispLabelsProperty() {
        return this.dispLabels;
    }

    /**
     * Renvoie la propriété du nom du menu
     *
     * @return : <StringProperty>
     */
    public StringProperty getNameProperty() {
        return this.name;
    }


    //******************************************************************************************************************
    //*                          SETTERS METHODS                                                                       *
    //******************************************************************************************************************

    //******************************************************************************************************************
    //*                          PRIVATE METHODS                                                                       *
    //******************************************************************************************************************

    /**
     * Cree le menu contextuel
     */
    private void setContextMenu() {


        // Create ContextMenu
        //this.setConsumeAutoHidingEvents(false);


        // --- Item 1 : nom de l'objet (item non cliquable)
        MenuItem itName = new MenuItem(name.getValue());
        name.addListener((observable, oldValue, newValue) -> itName.setText(name.getValue()));
        itName.setDisable(true);//désactive l'item

        //ajoute les actions de l'item
        itName.setOnAction(event -> System.out.println("clik item 1"));

        //ajoute l'item au menu
        this.getItems().add(itName);

        // --- Item 2 : Separator
        this.getItems().add(new SeparatorMenuItem());


        // -- Item 3 : sous menu : liste des interfaces
        Menu ifListMenu = new Menu("Interfaces");
        ContextMenu my = this;
        InterfacesTab ifLst = new InterfacesTab(anchor) {
            @Override
            public void actionsOnCo() {
                my.hide();
            }
        };

        ifListMenu.getItems().addAll(ifLst.getItems());
        //ajoute l'item au menu
        this.getItems().add(ifListMenu);

        // --- Item 4 : Separator
        this.getItems().add(new SeparatorMenuItem());


        // --- Item 5 : check/unchek affichage des labels
        CheckBox chk = new CheckBox("afficher les interfaces");
        chk.setSelected(true);
        //ajout de l'action du menuItem
        CustomMenuItem checkItemcont = new CustomMenuItem(chk);
        checkItemcont.setHideOnClick(false);

        //ajout d'un Listener sur le checkBox pour mettre à jour le dispLabel
        chk.setOnAction(event -> dispLabels.setValue(chk.isSelected()));
        //ajout d'un listener sur le dispLabels pour mettre à jour le checkBox
        dispLabels.addListener((observable, oldValue, newValue) -> chk.setSelected(dispLabels.getValue()));

        //ajoute l'item au menu
        this.getItems().add(checkItemcont);


        // --- crée l'action lors du click droit
        anchor.setOnContextMenuRequested(event -> {
            this.show(anchor, event.getScreenX(), event.getScreenY());
            System.out.println("context!!");
        });
    }

}