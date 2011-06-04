/*
 * Vpc_confApp.java
 */
package vpc_conf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class Vpc_confApp
        extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup(){
        try {
            show(new Vpc_confView(this));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Vpc_confApp.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Vpc_confApp.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root){
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of Vpc_confApp
     */
    public static Vpc_confApp getApplication(){
        return Application.getInstance(Vpc_confApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args){
        launch(Vpc_confApp.class, args);
    }
}
