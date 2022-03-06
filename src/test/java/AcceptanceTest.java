import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AcceptanceTest {
    @Test
    void acceptance_test() {
        var output =  new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        var fridge = new Fridge();
        fridge.setCurrentDate("18/10/2021");

        fridge.simulateDayOver();

        fridge.signalFridgeDoorOpened();
        fridge.signalFridgeDoorClosed();

        fridge.signalFridgeDoorOpened();
        fridge.signalFridgeDoorClosed();

        fridge.signalFridgeDoorOpened();
        fridge.scanRemovedItem("Milk");
        fridge.signalFridgeDoorClosed();

        fridge.signalFridgeDoorOpened();
        fridge.scanAddedItem("Milk", "26/10/21", "opened");
        fridge.scanAddedItem("Peppers", "23/10/21", "opened");
        fridge.signalFridgeDoorClosed();

        fridge.simulateDayOver();

        fridge.signalFridgeDoorOpened();
        fridge.scanRemovedItem("Beef");
        fridge.scanRemovedItem("Lettuce");
        fridge.signalFridgeDoorClosed();

        fridge.signalFridgeDoorOpened();
        fridge.scanAddedItem(name: "Lettuce", "22/10/21", "opened");
        fridge.signalFridgeDoorClosed();

        fridge.signalFridgeDoorOpened();
        fridge.signalFridgeDoorClosed();

        fridge.simulateDayOver();

        var finalOutput = output.toString().trim();

        var expected = "EXPIRED: Milk\n" +
                "Lettuce: 0 days remaining\n" +
                "Peppers: 1 day remaining\n" +
                "Cheese: 31 days remaining";

        assertEquals(expected, finalOutput);
    }
}
