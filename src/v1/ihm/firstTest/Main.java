package v1.ihm.firstTest;

import v1.ihm.firstTest.items.G_Router;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("Hello World");
        G_Router rt1 = new G_Router("R1", 50, 150);
        G_Router rt2 = new G_Router("R2", 200, 50);


        for(int i = 0 ; i < 12 ; i++){
            rt1.addInterface();
        }
        for(int i = 0 ; i < 8 ; i++){
            rt2.addInterface();
        }
        //rt.setVisible(true);
        root.getChildren().add(rt1);
        root.getChildren().add(rt2);

        rt1.connect(rt1.getGIfByName("if8"), rt2.getGIfByName("if4"));
        rt1.connect(rt1.getGIfByName("if7"), rt2.getGIfByName("if2"));
        //rt1.disConnect(rt1.getGIfByName("if3"));

        rt2.addInterface();
        rt2.addInterface();
        rt2.addInterface();



        rt1.getNode().getNameProperty().setValue("RT1");
        rt2.getNode().getNameProperty().setValue("RT2");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        System.out.println(rt1.getConf());
        System.out.println(rt2.getConf());

        Pattern p=Pattern.compile("[0-9]{1,3}");
        Matcher m=p.matcher("eth11");
        m.find();
        System.out.println("match=" + m.group(0));
        System.out.println("10totototo".matches("^[a-zA-Z][0-9a-zA-Z_\\-]{0,20}"));
        System.out.println("eth000".matches("(eth|if)?(0|[1-9]0?|[1-9][0-9]{1,2})"));
        System.out.println("eth010".matches("(eth|if)?(0|[1-9]0?|[1-9][0-9]{1,2})"));
        System.out.println("eth001".matches("(eth|if)?(0|[1-9]0?|[1-9][0-9]{1,2})"));
        System.out.println("eth101".matches("(eth|if)?(0|[1-9]0?|[1-9][0-9]{1,2})"));
        System.out.println("if0".matches("(eth|if)?[0-9]{1,3}"));
        System.out.println("eth".matches("(eth|if)?[0-9]{1,3}"));
        System.out.println("toto0".matches("(eth|if)?[0-9]{1,3}"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
