package v2.vue.items.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import v2.controller.APIController;
import v2.fileIO.Tools;
import v2.vue.items.equip.GHost;
import v2.vue.items.equip.GRouter;
import v2.vue.items.equip.GSwitch;

public class GlobalContextMenu extends ContextMenu {

    //******************************************************************************************************************
    //*                          VARIABLES                                                                             *
    //******************************************************************************************************************

    private final Group itemsRoot;
    private final FlowPane anchor;
    private final APIController controller;
    protected Point2D pos = new Point2D(0, 0);
    //******************************************************************************************************************
    //*                          CONSTRUCTEUR                                                                          *
    //******************************************************************************************************************

    /**
     * Create a new ContextMenu
     */
    public GlobalContextMenu(Group rt, FlowPane anchor, APIController ctrl) {
        super();
        this.itemsRoot = rt;

        this.anchor = anchor;
        this.controller = ctrl;

        setContextMenu();
    }
    //******************************************************************************************************************
    //*                          ABSTRACT METHODS                                                                      *
    //******************************************************************************************************************

    //******************************************************************************************************************
    //*                          GETTERS METHODS                                                                       *
    //******************************************************************************************************************

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


        // --- Item 1 : Add Host
        Button addHost = new Button("ajouter un host");
        CustomMenuItem csAddHost = new CustomMenuItem(addHost);
        addHost.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GHost ng = new GHost(controller.getName("Host_"), itemsRoot, controller);
                ng.setX(pos.getX());
                ng.setY(pos.getY());
            }
        });
        //ajoute l'item au menu
        this.getItems().add(csAddHost);

        // --- Item 2 : Add Router
        Button addRouter = new Button("ajouter un router");
        CustomMenuItem csAddRouter = new CustomMenuItem(addRouter);
        addRouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GRouter ng = new GRouter(controller.getName("Router_"), itemsRoot, controller);
                ng.setX(pos.getX());
                ng.setY(pos.getY());
            }
        });
        //ajoute l'item au menu
        this.getItems().add(csAddRouter);

        // --- Item 3 : Add Switch
        Button addSwitch = new Button("ajouter un switch");
        CustomMenuItem csAddSwitch = new CustomMenuItem(addSwitch);
        addSwitch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GSwitch ng = new GSwitch(controller.getName("Switch_"), itemsRoot, controller);
                ng.setX(pos.getX());
                ng.setY(pos.getY());
            }
        });
        //ajoute l'item au menu
        this.getItems().add(csAddSwitch);


        // --- Item 4 : Export de la conf
        Button export = new Button("export");
        CustomMenuItem csExport = new CustomMenuItem(export);
        export.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                String path = fileChooser.showSaveDialog(anchor.getScene().getWindow()).getAbsolutePath();
                Tools.writeLBA(path, new String[]{controller.getAllConf()});
            }
        });
        //ajoute l'item au menu
        this.getItems().add(csExport);


        // --- crée l'action lors du click droit
        ContextMenu mnu = this;
        anchor.setOnContextMenuRequested(event -> {
            this.show(anchor, event.getScreenX(), event.getScreenY());
            pos = new Point2D(event.getX() + itemsRoot.getBoundsInLocal().getMinX()
                    , event.getY() + itemsRoot.getBoundsInLocal().getMinY()
            );
            //System.out.println(pos);
            event.consume();
            //System.out.println("context!!");
        });

        anchor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    mnu.hide();

                }
                event.consume();
            }
        });
    }
    //******************************************************************************************************************
    //*                          PROTECTED METHODS                                                                     *
    //******************************************************************************************************************

    //******************************************************************************************************************
    //*                          PUBLIC METHODS                                                                        *
    //******************************************************************************************************************
}
