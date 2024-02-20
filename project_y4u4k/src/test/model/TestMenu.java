package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestMenu {
    private Menu testMenu;
    private Drink d1;
    private Drink d2;

    @BeforeEach
    void runBefore() {
        testMenu = new Menu();
        d1 = new Drink(17, "Martini");
        d2 = new Drink(16, "Old Fashion");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testMenu.getDrinkList().size());
    }

    @Test
    void testAddDrink() {
        testMenu.addDrink(d1);
        assertEquals(1, testMenu.getDrinkList().size());
        assertEquals(d1, testMenu.getDrinkList().get(0));
        testMenu.addDrink(d2);
        assertEquals(2, testMenu.getDrinkList().size());
        assertEquals(d1, testMenu.getDrinkList().get(0));
        assertEquals(d2, testMenu.getDrinkList().get(1));
    }

    @Test
    void testAddDrinkMoreThanOnce() {
        testMenu.addDrink(d1);
        testMenu.addDrink(d2);
        assertEquals(2, testMenu.getDrinkList().size());
        assertEquals(d1, testMenu.getDrinkList().get(0));
        assertEquals(d2, testMenu.getDrinkList().get(1));
    }

    @Test //the menu is empty
    void testFindDrinkA() {
        Drink d = testMenu.findDrink("Martini");
        assertNull(d);
    }

    @Test //there is no drink having this name in the menu
    void testFindDrinkB() {
        testMenu.addDrink(d1);
        testMenu.addDrink(d2);
        Drink d = testMenu.findDrink("Coke");
        assertNull(d);
    }

    @Test //successfully found the drink
    void testFindDrinkC() {
        testMenu.addDrink(d1);
        testMenu.addDrink(d2);
        Drink d = testMenu.findDrink("Martini");
        assertEquals(d1, d);
    }

}

