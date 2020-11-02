package v2.vue;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import v2.vue.items.ConnectionManager;
import v2.vue.items.GHost;
import v2.vue.items.Link;
import v2.vue.items.Multilink;

public class DemoApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        Group root = new Group();
        ConnectionManager manager = ConnectionManager.getInstance();
        manager.setGroup(root);



        //Création du host 1
        GHost hs1 = new GHost("Bureau");
        hs1.setX(0);
        hs1.setY(0);

        //création du host 2
        GHost hs2 = new GHost("Gaming");
        hs2.setX(200);
        hs2.setY(100);

        //création du host 3
        GHost hs3 = new GHost("Master_Race");
        hs3.setX(100);
        hs3.setY(200);

        //création du lien entre les 2 hosts
        Link ln1 = new Link(hs1, hs2, "eth0", "eth0", root);
        Link ln2 = new Link(hs1, hs3, "eth1", "eth0", root);

        Multilink mlk = new Multilink();
        for (int i = 0; i < 3; i++) {
            mlk.addLink(new Link(hs2, hs3, "eth" + (i+1), "eth" + (i+1), root));
        }

        Link lk = mlk.getLinks().remove(0);
        lk.destroy();

        //root.setPadding(new Insets(20));

        root.getChildren().addAll(hs1, hs2, hs3);
        ln1.toBack();
        ln2.toBack();


        FlowPane fp = new FlowPane();


        fp.getChildren().add(root);


        root.getChildren().add(manager);


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