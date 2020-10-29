package v1.ihm.archGUI.items;

import javafx.application.Platform;
import javafx.scene.control.*;

public class PGUIMenu extends MenuBar {

    public PGUIMenu() {
        super();
        createItems();
    }


    private void createItems() {
        // File menu - new, save, exit
        //TODO actions export/import
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        fileMenu.getItems().addAll(newMenuItem, saveMenuItem,
                new SeparatorMenuItem(), exitMenuItem);

        //Menu edit
        Menu editMenu = new Menu("Edit");
        MenuItem addRouter = new MenuItem("Add Router");
        MenuItem addSwitch = new MenuItem("Add Switch");
        MenuItem addHost = new MenuItem("Add Host");

        editMenu.getItems().add(addRouter);
        editMenu.getItems().add(addSwitch);
        editMenu.getItems().add(addHost);


        this.getMenus().addAll(fileMenu, editMenu);
    }
}
