/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewerfx;

import com.newdawn.gui.fleet.SpeedSelectionComponent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author Teocali
 */
public class TestClass2 extends Application {

    public static final void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(getClass().getCanonicalName());
//        SpeedSelectionComponent pane = new SpeedSelectionComponent();
//        Slider testSlider = new Slider(0, 50000, 00);
//        testSlider.setLabelFormatter(new StringConverter<Double>() {
//
//            @Override
//            public String toString(Double arg0) {
//                return Double.toString(arg0 / 1000);
//            }
//
//            @Override
//            public Double fromString(String arg0) {
//                throw new UnsupportedOperationException("Not supported yet.");
//            }
//        });
//        testSlider.setShowTickLabels(true);
//        final Scene scene = new Scene(testSlider);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }
}
