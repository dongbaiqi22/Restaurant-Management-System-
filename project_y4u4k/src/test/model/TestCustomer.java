package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCustomer {

    private Customer testCustomer;
    private Drink d1;
    private Drink d2;

    @BeforeEach
    void runBefore() {
        d1 = new Drink(17, "Martini");
        d2 = new Drink(16, "Old Fashion");
        testCustomer = new Customer();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testCustomer.getOrders().size());
    }

    @Test
    void testAddOrderOnce() {
        testCustomer.addOrder(d1);
        assertEquals(1, testCustomer.getOrders().size());
        assertEquals(d1, testCustomer.getOrders().get(0));
    }

    @Test
    void testAddOrderTwice() {
        testCustomer.addOrder(d1);
        testCustomer.addOrder(d2);
        assertEquals(2, testCustomer.getOrders().size());
        assertEquals(d1, testCustomer.getOrders().get(0));
        assertEquals(d2, testCustomer.getOrders().get(1));
    }

    @Test
    void testPaymentWithEmptyOrder() {
        assertEquals(0, testCustomer.payment());
    }

    @Test
    void testPaymentWithOrders() {
        testCustomer.addOrder(d1);
        testCustomer.addOrder(d2);
        assertEquals(33, testCustomer.payment());
    }
}
