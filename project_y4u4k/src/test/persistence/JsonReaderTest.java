package persistence;

import model.Bar;
import model.Menu;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest{

    @Test
    void testReadBarNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Bar bar = reader.readBar();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadMenuNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Menu menu = reader.readMenu();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadBarEmptyBar() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBar.json");
        try {
            Bar bar = reader.readBar();
            assertEquals(0, bar.getTables().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReadMenuEmptyMenu() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMenu.json");
        try {
            Menu menu = reader.readMenu();
            assertEquals(0, menu.getDrinkList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBar() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBar.json");
        try {
            Bar bar = reader.readBar();
            assertEquals(2, bar.getTables().size());
            assertEquals(1, bar.getTables().get(0).getCustomers().size());
            assertEquals(2, bar.getTables().get(1).getCustomers().size());
            assertEquals(1, bar.getTables().get(0).getCustomers().get(0).getOrders().size());
            assertEquals(1, bar.getTables().get(1).getCustomers().get(0).getOrders().size());
            assertEquals(2, bar.getTables().get(1).getCustomers().get(1).getOrders().size());

            checkDrinkOrder("Coke", 3, bar.getTables().get(0).getCustomers().get(0).getOrders().get(0));

            assertEquals("Coke", bar.getTables().get(0).getCustomers().get(0).getOrders().get(0).getName());
            assertEquals(3, bar.getTables().get(0).getCustomers().get(0).getOrders().get(0).getPrice());


            checkDrinkOrder("Milk", 2, bar.getTables().get(1).getCustomers().get(0).getOrders().get(0));

            assertEquals("Milk", bar.getTables().get(1).getCustomers().get(0).getOrders().get(0).getName());
            assertEquals(2, bar.getTables().get(1).getCustomers().get(0).getOrders().get(0).getPrice());


            checkDrinkOrder("Coke", 3, bar.getTables().get(1).getCustomers().get(1).getOrders().get(0));

            assertEquals("Coke", bar.getTables().get(1).getCustomers().get(1).getOrders().get(0).getName());
            assertEquals(3, bar.getTables().get(1).getCustomers().get(1).getOrders().get(0).getPrice());


            checkDrinkOrder("Milk", 2, bar.getTables().get(1).getCustomers().get(1).getOrders().get(1));

            assertEquals("Milk", bar.getTables().get(1).getCustomers().get(1).getOrders().get(1).getName());
            assertEquals(2, bar.getTables().get(1).getCustomers().get(1).getOrders().get(1).getPrice());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMenu() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMenu.json");
        try {
            Menu menu = reader.readMenu();
            assertEquals(2, menu.getDrinkList().size());
            assertEquals("Coke", menu.getDrinkList().get(0).getName());
            assertEquals(3, menu.getDrinkList().get(0).getPrice());
            assertEquals("Milk", menu.getDrinkList().get(1).getName());
            assertEquals(2, menu.getDrinkList().get(1).getPrice());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
