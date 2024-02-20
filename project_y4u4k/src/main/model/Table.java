package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;


// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Represents a table having its capacity , table number, customers sitting at this table and availability
public class Table implements Writable {

    private final int capacity;
    private boolean availability;
    private List<Customer> customers;
    private final int tableNumber;

    //REQUIRES: capacity > 0, table number must not be the same as existing tables in the bar
    //EFFECTS: create a new table with given capacity and available of availability
    public Table(int capacity, int tableNumber) {
        this.capacity = capacity;
        this.availability = true;
        this.customers = new ArrayList<>();
        this.tableNumber = tableNumber;
    }

    //MODIFIES: this
    //EFFECTS: add the customer to this table, if the table is available, change the
    //         availability to not available.
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
        if (this.availability) {
            this.availability = false;
        }
    }

    //REQUIRES: there are more than 0 customers at this table
    //EFFECTS: generate one final payment for the whole table, log this event to the Singleton EventLog
    public int generateOneBill() {
        int fees = 0;
        for (Customer c : this.customers) {
            fees = fees + c.payment();
        }
        EventLog.getInstance().logEvent(new Event("Check for table" + tableNumber));
        return fees;
    }

    //REQUIRES: there are more than 0 customers at this table
    //EFFECTS: generate individual payment for each customer at this table based on the orders that they
    //         were added to the table
    public List<Integer> generateSeparateBill() {
        List<Integer> bills = new ArrayList<>();
        for (Customer c : this.customers) {
            bills.add(c.payment());
        }
        return bills;
    }

    //REQUIRES: n > 0
    //MODIFIES: this
    //EFFECTS: add n customers who have no orders to the table in one time, log this event to the Singleton EventLog
    public void addMultipleCustomers(int n) {
        int a = n;
        while (n > 0) {
            Customer c = new Customer();
            addCustomer(c);
            n = n - 1;
        }
        EventLog.getInstance().logEvent(new Event("A customer group of " + a + " has been assigned to table"
                + tableNumber));
    }

    //EFFECTS: if this table is available, return "available", otherwise, return "not available"
    public String checkAvailability() {
        if (availability) {
            return "available";
        } else {
            return "not available";
        }
    }

    // REQUIRES: this.getCustomers.size() > 0
    // MODIFIES: this
    // EFFECTS: add Drink to customer1 at this table
    //          log this event to the Singleton EventLog
    public void addOrderToTable(Drink d) {
        customers.get(0).addOrder(d);
        EventLog.getInstance().logEvent(new Event(d.getName() + " has been added to table" + tableNumber));
    }

    //MODIFIES: this
    //EFFECTS: Remove all the customers at this table, do nothing if there is no customer at this table
    public void removeAllCustomers() {
        this.customers = new ArrayList<>();
        this.availability = true;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean getAvailability() {
        return this.availability;
    }

    public List<Customer> getCustomers() {
        return customers;
    }


    public int getTableNumber() {
        return this.tableNumber;
    }

    public void setAvailability(boolean b) {
        this.availability = b;
    }



    @Override
    // EFFECTS: convert table to JSON representation and return it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("capacity", capacity);
        json.put("availability", availability);
        json.put("customers", customersToJson());
        json.put("table number", tableNumber);
        return json;
    }

    // EFFECTS: returns customers in this table as a JSON array
    private JSONArray customersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Customer c : this.customers) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
