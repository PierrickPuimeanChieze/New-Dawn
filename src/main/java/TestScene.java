import com.newdawn.controllers.GameData;
import com.newdawn.gui.map.system.SpecialCircle;
import com.newdawn.gui.map.system.SpecialGroup;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Created by Pierrick on 01/02/2015.
 */
public class TestScene extends Application {
    public int zoomLevel = 0;
    public int minimalRadius = 5;
    public void addZoom() {

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group mainScreen = new SpecialGroup();

        SpecialCircle test1Circle = new SpecialCircle(200, 200, 5);
        test1Circle.setFill(Color.RED);
        Circle test2Circle = new Circle(150, 150, 50);

        mainScreen.getChildren().addAll(test1Circle, test2Circle);
        final Scene scene = new Scene(mainScreen);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                switch (event.getCode()) {
                    case P:
                      zoomLevel+=1;
                        break;
                    case M:

                       zoomLevel-=1;

                        break;
                    case O:
                        zoomLevel=0;
                        break;
                    case W :
                        System.out.println(zoomLevel);
                        System.out.println(150*(1+(double)zoomLevel/100));
                }
                double newScale  = 1+(double)zoomLevel/100;
                mainScreen.setScaleX(newScale);
                mainScreen.setScaleY(newScale);
                double visibleRadius = test1Circle.initialRadius*newScale;
                if (visibleRadius<minimalRadius) {
                    test1Circle.setRadius(minimalRadius/newScale);
                } else {
                    test1Circle.setRadius(test1Circle.initialRadius);
                }
            }
        });
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void removeZoom() {
        zoomLevel-=1;
    }
}
