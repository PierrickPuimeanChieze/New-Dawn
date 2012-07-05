/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package viewerfx;

import com.newdawn.controllers.InitialisationController;
import com.newdawn.model.ships.Ship;
import com.newdawn.model.ships.Squadron;
import com.newdawn.model.ships.orders.MoveToSpaceObjectOrder;
import com.newdawn.model.ships.orders.Order;
import com.newdawn.model.system.Planet;
import com.newdawn.model.system.StellarSystem;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * * @author Pierrick Puimean-Chieze
 */
public class SollarSystemBuilder {

    public static StellarSystem getIt(InitialisationController initialisationController) throws ParserConfigurationException, SAXException, IOException {
//        File solSystemFile = new File("C:\\Users\\Pierrick\\Dropbox\\NewDawn\\ViewerFX\\srcUI\\viewerfx\\solarSystem.xml");
        InputStream solSystemInputStream = SollarSystemBuilder.class.getResourceAsStream("/solarSystem.xml");
        StellarSystem solarSystem = initialisationController.createSystem(solSystemInputStream);

        Squadron testTG = new Squadron();
        testTG.setName("testTG");
        testTG.setSpeed(1000 * 1000);


        Ship testShip = new Ship();
        testShip.setMaxSpeed(2000 * 1000);
        testShip.setTaskGroup(testTG);
        testShip.setName("testTG 1 -1");
        testTG.getShips().add(testShip);
        testTG.setPositionX(0);
        testTG.setPositionY(57950000);

        for (Planet planet : solarSystem.getPlanets()) {
            if (planet.getName().toLowerCase().equals("venus")) {
                Order testMoveOrder = new MoveToSpaceObjectOrder(planet, testTG);
                testTG.setCurrentOrder(testMoveOrder);
                testMoveOrder.applyOrder();
                solarSystem.getSquadrons().add(testTG);
                break;
            }
        }

        return solarSystem;
    }
}
