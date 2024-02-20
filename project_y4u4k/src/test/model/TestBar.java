package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestBar {
    private Bar testBar;
    private Table t1;
    private Table t2;
    private Table t3;
    private Table t4;

    @BeforeEach
    void runBefore() {
        testBar = new Bar();
        t1 = new Table(2, 1);
        t2 = new Table(3, 2);
        t3 = new Table(4,3);
        t4 = new Table(2,4);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testBar.getTables().size());
    }

    @Test
    void testAddTable(){
        testBar.addTable(t1);
        assertEquals(1, testBar.getTables().size());
        assertEquals(t1, testBar.getTables().get(0));
        testBar.addTable(t2);
        assertEquals(2, testBar.getTables().size());
        assertEquals(t1, testBar.getTables().get(0));
        assertEquals(t2, testBar.getTables().get(1));
        testBar.addTable(t3);
        assertEquals(3, testBar.getTables().size());
        assertEquals(t1, testBar.getTables().get(0));
        assertEquals(t2, testBar.getTables().get(1));
        assertEquals(t3, testBar.getTables().get(2));
    }

    @Test
    void testAddTableMoreThanOnce() {
        testBar.addTable(t1);
        testBar.addTable(t2);
        testBar.addTable(t3);
        assertEquals(3, testBar.getTables().size());
        assertEquals(t1, testBar.getTables().get(0));
        assertEquals(t2, testBar.getTables().get(1));
        assertEquals(t3, testBar.getTables().get(2));
    }

    @Test // no table satisfies the capacity
    void testFilterTableA() {
        testBar.addTable(t1);
        testBar.addTable(t2);
        testBar.addTable(t3);
        assertEquals(0, testBar.filterTable(5).size());
    }


    @Test // one table does not satisfy because of the capacity, one table does not satisfy because of the availability
          // one table satisfies, one table does not satisfy because of both capacity and availability
    void testFilterTableB() {
        Customer c = new Customer();
        t4.addCustomer(c);
        Customer c1 = new Customer();
        t3.addCustomer(c1);
        testBar.addTable(t1);
        testBar.addTable(t2);
        testBar.addTable(t3);
        testBar.addTable(t4);
        assertEquals(1, testBar.filterTable(3).size());
        assertEquals(t2, testBar.filterTable(3).get(0));
    }

    @Test // More than 1 table satisfy
    void testFilterTableC(){
        Customer c = new Customer();
        t4.addCustomer(c);
        testBar.addTable(t1);
        testBar.addTable(t2);
        testBar.addTable(t3);
        testBar.addTable(t4);
        List<Table> tables = testBar.filterTable(3);
        assertEquals(2, tables.size());
        assertEquals(t2, tables.get(0));
        assertEquals(t3, tables.get(1));

    }

    @Test // All tables satisfy
    void testFilterTableD() {
        testBar.addTable(t1);
        testBar.addTable(t2);
        testBar.addTable(t3);
        assertEquals(3, testBar.filterTable(2).size());
        assertEquals(t1, testBar.filterTable(2).get(0));
        assertEquals(t2, testBar.filterTable(2).get(1));
        assertEquals(t3, testBar.filterTable(2).get(2));
    }

    @Test // Table that has this table number exists
    void testFindTable() {
        testBar.addTable(t1);
        testBar.addTable(t2);
        testBar.addTable(t3);
        Table t = testBar.findTable(3);
        assertEquals(t3, t);
    }

    @Test // Table that has this table number does not exist
    void testFindTableA() {
        testBar.addTable(t1);
        testBar.addTable(t2);
        testBar.addTable(t3);
        Table t = testBar.findTable(4);
        assertNull(t);
    }
}
