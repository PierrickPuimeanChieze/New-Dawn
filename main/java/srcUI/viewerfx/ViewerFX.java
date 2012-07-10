/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewerfx;

import com.newdawn.controllers.GameData;
import com.newdawn.controllers.InitialisationController;
import com.newdawn.model.system.StellarSystem;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class ViewerFX extends Application {

    private static ViewerFX currentApplication;
    private ApplicationContext sprintContainer;
    private static Log LOG = LogFactory.getLog(ViewerFX.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();

        ViewerFX.currentApplication = this;

        sprintContainer = new ClassPathXmlApplicationContext("/spring/newdawn.xml");


        InitialisationController initialisationController = sprintContainer.
                getBean(InitialisationController.class);
        final StellarSystem solarSystem = SollarSystemBuilder.getIt(initialisationController);
        final StellarSystem solarSystem2 = SollarSystem2Builder.getIt(initialisationController);
        StellarSystem testSystem = initialisationController.createSystem(getClass().
                getResourceAsStream("/testSystem.xml"));
        GameData gameData = sprintContainer.getBean(GameData.class);
        gameData.getStellarSystems().addAll(solarSystem, solarSystem2, testSystem);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane mainScreen;
            mainScreen = (AnchorPane) loader.load(getClass().
                    getResourceAsStream("/com/newdawn/gui/MainScreen.fxml"));

            final Scene scene = new Scene(mainScreen);


            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            LOG.fatal(null, ex);
            throw new RuntimeException(ex);
        }
    }

    public static ViewerFX getCurrentApplication() {
        return currentApplication;
    }

    public BeanFactory getSprintContainer() {
        return sprintContainer;
    }
}
