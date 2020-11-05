package v2.vue.entrypoint;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import v2.controller.APIController;
import v2.controller.Controller;
import v2.model.ModelArch;
import v2.vue.items.ConnectionManager;
import v2.vue.items.menu.GlobalContextMenu;

public class DemoApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        //création du group ed'acceuil des éléments
        Group root = new Group();

        //ajout du groupe au connexion manager
        ConnectionManager manager = ConnectionManager.getInstance();
        manager.setGroup(root);

        //affichage du manager de co
        root.getChildren().add(manager);

        //création du controller et du modèle
        APIController ctrl = new Controller(new ModelArch());

       /* //Création du host 1
        GHost hs1 = new GHost("Bureau", root, ctrl);
        hs1.setX(0);
        hs1.setY(0);

        //création du host 2
        GHost hs2 = new GHost("Gaming", root, ctrl);
        hs2.setX(200);
        hs2.setY(100);

        //création du host 3
        GHost hs3 = new GHost("Master_Race", root, ctrl);
        hs3.setX(100);
        hs3.setY(200);

        //création du Router 1
        GRouter r1 = new GRouter("Router1", root, ctrl);
        r1.setX(400);
        r1.setY(300);

        //création du Switch 1
        GSwitch sw1 = new GSwitch("sw1", root, ctrl);
        sw1.setX(550);
        sw1.setY(120);*/

        //Création d'un bouton d'affichage de la conf
       /* Button showRun = new Button("sh run");
        showRun.setOnAction(event -> System.out.println(ctrl.getAllConf()));
        showRun.setTranslateX(0);
        showRun.setTranslateY(0);
        root.getChildren().addAll(showRun);*/


        //Création d'un flowPaner recevant le groupe
        FlowPane fp = new FlowPane();
        fp.getChildren().add(root);



        //le manager suit en permanence la position de la souris
        fp.setOnMouseMoved(event -> {

            manager.setEndX(event.getX() + root.getBoundsInLocal().getMinX());
            manager.setEndY(event.getY() + root.getBoundsInLocal().getMinY());
            manager.toBack();


        });

        GlobalContextMenu gcMenu = new GlobalContextMenu(root, fp, ctrl);



        //Création de la scène principale
        Scene scene = new Scene(fp, 900, 400);
        primaryStage.setTitle("JavaFX ImageView (o7planning.org)");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

//TODO list :
    /**
     * menu contextuel pour ajouter des terminaux
     * suppression d'un terminal (term menu contextuel)
     * menu déroulant coté gauche :
     *  - export la conf
     */

}