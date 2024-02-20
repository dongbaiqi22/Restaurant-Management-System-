package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDrink {
    private Drink testDrinkA;
    private Drink testDrinkB;

    @BeforeEach
    void runBefore() {
        testDrinkA = new Drink(17, "Martini");
        testDrinkB = new Drink(3, "Coke");
    }

    @Test
    void testConstructor() {
        assertEquals(17, testDrinkA.getPrice());
        assertEquals("Martini", testDrinkA.getName());
        assertEquals(3, testDrinkB.getPrice());
        assertEquals("Coke", testDrinkB.getName());
    }
}






