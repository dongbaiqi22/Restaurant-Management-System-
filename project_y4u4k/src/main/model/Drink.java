package model;

import persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


//Represents a drink that is served by the bar having its name and price
public class Drink implements Writable {
    private final int price;
    private final String name;

    //REQUIRES: name has a positive length, price > 0
    //EFFECTS: creates a new drink, set the name and the price as given
    public Drink(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    @Override
    // EFFECTS: convert drink to JSON representation and return it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        return json;
    }
}
