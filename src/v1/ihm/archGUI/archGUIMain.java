package v1.ihm.archGUI;

import v1.ihm.archGUI.items.PGUIMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class archGUIMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Group root = new Group();
        primaryStage.setTitle("Hello World");

        PGUIMenu mn = new PGUIMenu();

        VBox vBox = new VBox(mn);

        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
