package Evolution;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is the  App class where the top-level object, Menu, and Scene of the game are instantiated.
 */

public class App extends Application {

    @Override
    public void start(Stage stage) {

    	//Top-level object Menu instantiated
    	Menu menu = new Menu(stage); 
    	
    	//stage title set 
    	stage.setTitle("Evolution Project");
    	//Scene object created with dimensions of the screen 
    	Scene scene = new Scene(menu.getMenu(), Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
    	
    	//Stage is set and shown
    	stage.setScene(scene);
    	stage.show();
    }

    /*
    * Here is the mainline! No need to change this.
    */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
