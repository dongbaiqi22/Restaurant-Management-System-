package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Represent a customer having a list of drinks that this customer has ordered
public class Customer implements Writable {
    private List<Drink> orders;

    //EFFECT: create a new customer with nothing ordered
    public Customer() {
        this.orders = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add order to the customer
    public void addOrder(Drink drink) {
        this.orders.add(drink);
    }

    //EFFECTS: calculate the sum of the fees of all drinks this customer ordered
    public int payment() {
        int fees = 0;
        for (Drink d: this.orders) {
            fees += d.getPrice();
        }
        return fees;
    }

    public List<Drink> getOrders() {
        return this.orders;
    }


    @Override
    // EFFECTS: convert customer to JSON representation and return it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("orders", ordersToJson());
        return json;
    }

    // EFFECTS: returns drinks in orders as a JSON array
    private JSONArray ordersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Drink d : this.orders) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }
}
