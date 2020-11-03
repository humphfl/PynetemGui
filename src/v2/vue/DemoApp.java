package v2.vue;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import v2.controller.APIController;
import v2.controller.Controller;
import v2.model.ModelArch;
import v2.vue.items.ConnectionManager;
import v2.vue.items.equip.GHost;
import v2.vue.items.equip.GRouter;
import v2.vue.items.equip.GSwitch;

public class DemoApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        Group root = new Group();
        ConnectionManager manager = ConnectionManager.getInstance();
        manager.setGroup(root);

        APIController ctrl = new Controller(new ModelArch());


        //Création du host 1
        GHost hs1 = new GHost("Bureau", ctrl);
        hs1.setX(0);
        hs1.setY(0);

        //création du host 2
        GHost hs2 = new GHost("Gaming", ctrl);
        hs2.setX(200);
        hs2.setY(100);

        //création du host 3
        GHost hs3 = new GHost("Master_Race", ctrl);
        hs3.setX(100);
        hs3.setY(200);

        //création du Router 1
        GRouter r1 = new GRouter("Router1", ctrl);
        r1.setX(400);
        r1.setY(300);

        //création du Switch 1
        GSwitch sw1 = new GSwitch("sw1", ctrl);
        sw1.setX(550);
        sw1.setY(120);

        Button showRun = new Button("sh run");
        showRun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(ctrl.getAllConf());
            }
        });
        showRun.setTranslateX(0);
        showRun.setTranslateY(0);
        //création du lien entre les 2 hosts
        //Link ln1 = new Link(hs1, hs2, "eth0", "eth0", root);
        //Link ln2 = new Link(hs1, hs3, "eth1", "eth0", root);

        /*Multilink mlk = new Multilink();
        for (int i = 0; i < 3; i++) {
            mlk.addLink(new Link(hs2, hs3, "eth" + (i+1), "eth" + (i+1), root));
        }

        Link lk = mlk.getLinks().remove(0);
        lk.destroy();
*/
        //root.setPadding(new Insets(20));

        root.getChildren().addAll(hs1, hs2, hs3, r1, sw1, showRun);
       /* ln1.toBack();
        ln2.toBack();*/


        FlowPane fp = new FlowPane();


        fp.getChildren().add(root);


        root.getChildren().add(manager);


        System.out.println(ctrl.getAllConf());
      /*  Rectangle rect = new Rectangle();
        rect.setFill(Color.WHITE);
        rect.setHeight(100);
        rect.setWidth(100);
        root.getChildren().add(rect);
        root.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                rect.setHeight(root.getBoundsInLocal().getHeight());
                rect.setWidth(root.getBoundsInLocal().getWidth());
                System.out.println("boundModif" + root.getBoundsInLocal().getWidth());
                rect.toBack();
            }
        });
        rect.setVisible(true);*/

        Scene scene = new Scene(fp, 900, 400);

        fp.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                //TODO fix : mauvaise position pour le endX, endY (que sur le FlowPane)
                manager.setEndX(event.getX()+root.getBoundsInLocal().getMinX());
                manager.setEndY(event.getY()+root.getBoundsInLocal().getMinY());
                manager.toBack();
                //System.out.println(String.format("fpPos=[%03.1f;%03.1f]", event.getSceneX(), event.getSceneY()));

                //System.out.println(String.format("gap=[V%03.1f;H%03.1f]", root.getTranslateX(), root.getTranslateY()));
                //System.out.println("gap="+root.getBoundsInLocal());


            }
        });

        /*root.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                //TODO fix : mauvaise position pour le endX, endY (que sur le FlowPane)
                System.out.println(String.format("RootPos=[%03.1f;%03.1f]", event.getSceneX(), event.getSceneY()));
                //System.out.println(String.format("gap=[V%03.1f;H%03.1f]", root.getTranslateX(), root.getTranslateY()));
                //System.out.println("gap="+root.getBoundsInParent());


            }
        });*/

        primaryStage.setTitle("JavaFX ImageView (o7planning.org)");
        primaryStage.setScene(scene);
        primaryStage.show();

    }



}