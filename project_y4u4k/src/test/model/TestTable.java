package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTable {
    private Table testTable;
    private Customer c1;
    private Customer c2;
    private Customer c3;
    private Drink d1;
    private Drink d2;
    private Drink d3;
    private Drink d4;

    @BeforeEach
    void runBefore() {
        testTable = new Table(4, 1);
        c1 = new Customer();
        c2 = new Customer();
        c3 = new Customer();
        d1 = new Drink(16, "Mojito");
        d2 = new Drink(17, "Martini");
        d3 = new Drink(15, "Old Fashion");
        d4 = new Drink(13, "Pina Colada");
    }

    @Test
    void testConstructor() {
        assertTrue(testTable.getAvailability());
        assertEquals(4, testTable.getCapacity());
        assertEquals(0,testTable.getCustomers().size());
        assertEquals(1, testTable.getTableNumber());
    }

    @Test
    void testAddCustomerOnce() {
        testTable.addCustomer(c1);
        assertEquals(1, testTable.getCustomers().size());
        assertEquals(c1, testTable.getCustomers().get(0));
        assertFalse(testTable.getAvailability());
    }

    @Test
    void testAddCustomerMoreThanOnce() {
        testTable.addCustomer(c1);
        testTable.addCustomer(c2);
        assertEquals(2, testTable.getCustomers().size());
        assertEquals(c1, testTable.getCustomers().get(0));
        assertEquals(c2, testTable.getCustomers().get(1));
        assertFalse(testTable.getAvailability());
        testTable.addCustomer(c3);
        assertEquals(3, testTable.getCustomers().size());
        assertEquals(c1, testTable.getCustomers().get(0));
        assertEquals(c2, testTable.getCustomers().get(1));
        assertEquals(c3, testTable.getCustomers().get(2));
        assertFalse(testTable.getAvailability());
    }

    @Test // All customers at this table have ordered
    void testGenerateOneBillA() {
        c1.addOrder(d1);
        c1.addOrder(d2);
        c2.addOrder(d3);
        c3.addOrder(d1);
        c3.addOrder(d4);
        testTable.addCustomer(c1);
        testTable.addCustomer(c2);
        testTable.addCustomer(c3);
        int bill = testTable.generateOneBill();
        assertEquals(77, bill);
    }

    @Test // One customer at the table has no orders
    void testGenerateOneBillB() {
        c1.addOrder(d1);
        c1.addOrder(d2);
        c3.addOrder(d1);
        c3.addOrder(d4);
        testTable.addCustomer(c1);
        testTable.addCustomer(c2);
        testTable.addCustomer(c3);
        int bill = testTable.generateOneBill();
        assertEquals(62, bill);
    }

    @Test // No customer at the table has orders
    void testGenerateOneBillC() {
        testTable.addCustomer(c1);
        testTable.addCustomer(c2);
        testTable.addCustomer(c3);
        int bill = testTable.generateOneBill();
        assertEquals(0, bill);
    }

    @Test // each customer at this table has orders
    void testGenerateSeparateBill() {
        c1.addOrder(d1);
        c1.addOrder(d2);
        c2.addOrder(d3);
        c3.addOrder(d1);
        c3.addOrder(d4);
        testTable.addCustomer(c1);
        testTable.addCustomer(c2);
        testTable.addCustomer(c3);
        List<Integer> bills = testTable.generateSeparateBill();
        assertEquals(3, bills.size());
        assertEquals(33, bills.get(0));
        assertEquals(15, bills.get(1));
        assertEquals(29, bills.get(2));
    }


    @Test // One customer at the table has no order
    void testGenerateSeparateBillB() {
        c1.addOrder(d1);
        c1.addOrder(d2);
        c3.addOrder(d1);
        c3.addOrder(d4);
        testTable.addCustomer(c1);
        testTable.addCustomer(c2);
        testTable.addCustomer(c3);
        List<Integer> bills = testTable.generateSeparateBill();
        assertEquals(3, bills.size());
        assertEquals(33, bills.get(0));
        assertEquals(0, bills.get(1));
        assertEquals(29, bills.get(2));
    }

    @Test // No customer at the table has orders
    void testGenerateSeparateBillC() {
        testTable.addCustomer(c1);
        testTable.addCustomer(c2);
        testTable.addCustomer(c3);
        List<Integer> bills = testTable.generateSeparateBill();
        assertEquals(3, bills.size());
        assertEquals(0, bills.get(0));
        assertEquals(0, bills.get(1));
        assertEquals(0, bills.get(2));
    }

    @Test
    void testAddMultipleCustomers() {
        testTable.addMultipleCustomers(3);
        assertEquals(3, testTable.getCustomers().size());
        assertFalse(testTable.getAvailability());
    }

    @Test
    void testAddMultipleCustomersB() {
        testTable.addMultipleCustomers(2);
        assertEquals(2, testTable.getCustomers().size());
        assertFalse(testTable.getAvailability());
    }

    @Test
    void testCheckAvailabilityA() {
        assertEquals("available", testTable.checkAvailability());
    }

    @Test
    void testCheckAvailabilityB() {
        testTable.addCustomer(c1);
        assertEquals("not available", testTable.checkAvailability());
    }

    @Test // there is no customers at this table, do nothing
    void testRemoveCustomersA() {
        testTable.removeAllCustomers();
        assertEquals(0, testTable.getCustomers().size());
        assertTrue(testTable.getAvailability());
    }

    @Test // there are customers at this table, remove all, and set availability to true
    void testRemoveCustomersB() {
        testTable.addCustomer(c1);
        testTable.addCustomer(c2);
        testTable.addCustomer(c3);
        testTable.removeAllCustomers();
        assertEquals(0, testTable.getCustomers().size());
        assertTrue(testTable.getAvailability());
    }

    @Test
    void testAddOrderToTable() {
        testTable.addCustomer(c1);
        testTable.addCustomer(c2);
        testTable.addOrderToTable(d1);
        assertEquals(1, c1.getOrders().size());
        assertEquals(d1, c1.getOrders().get(0));
        assertEquals(0, c2.getOrders().size());
        testTable.addOrderToTable(d2);
        assertEquals(2, c1.getOrders().size());
        assertEquals(d1, c1.getOrders().get(0));
        assertEquals(d2, c1.getOrders().get(1));
        assertEquals(0, c2.getOrders().size());
    }
}
