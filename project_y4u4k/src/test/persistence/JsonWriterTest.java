package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterBarInvalidFile() {
        try {
            Bar bar = new Bar();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterMenuInvalidFile() {
        try {
            Menu menu = new Menu();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBar() {
        try {
            Bar bar = new Bar();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBar.json");
            writer.open();
            writer.writeBar(bar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBar.json");
            bar = reader.readBar();
            assertEquals(0, bar.getTables().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyMenu() {
        try {
            Menu menu = new Menu();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMenu.json");
            writer.open();
            writer.writeMenu(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMenu.json");
            menu = reader.readMenu();
            assertEquals(0, menu.getDrinkList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBar() {
        try {
            Bar bar = new Bar();
            Table table1 = new Table(4, 1);
            Table table2 = new Table(5, 2);
            Customer c1 = new Customer();
            Customer c2 = new Customer();
            Customer c3 = new Customer();
            Drink d1 = new Drink(3, "Coke");
            Drink d2 = new Drink(2, "Milk");
            c1.addOrder(d1);
            c2.addOrder(d2);
            c3.addOrder(d1);
            c3.addOrder(d2);
            table1.addCustomer(c1);
            table2.addCustomer(c2);
            table2.addCustomer(c3);
            bar.addTable(table1);
            bar.addTable(table2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBar.json");
            writer.open();
            writer.writeBar(bar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBar.json");
            bar = reader.readBar();
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
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralMenu() {
        try {
            Menu menu = new Menu();
            Drink d1 = new Drink(3, "Coke");
            Drink d2 = new Drink(2, "Milk");
            menu.addDrink(d1);
            menu.addDrink(d2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMenu.json");
            writer.open();
            writer.writeMenu(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMenu.json");
            menu = reader.readMenu();
            assertEquals(2, menu.getDrinkList().size());
            assertEquals("Coke", menu.getDrinkList().get(0).getName());
            assertEquals(3, menu.getDrinkList().get(0).getPrice());
            assertEquals("Milk", menu.getDrinkList().get(1).getName());
            assertEquals(2, menu.getDrinkList().get(1).getPrice());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
