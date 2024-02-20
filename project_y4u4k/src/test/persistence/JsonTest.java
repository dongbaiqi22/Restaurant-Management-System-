package persistence;


import model.Drink;

import static org.junit.jupiter.api.Assertions.assertEquals;


// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkDrinkOrder(String name, int price, Drink drink) {
        assertEquals(name, drink.getName());
        assertEquals(price, drink.getPrice());
    }
}
