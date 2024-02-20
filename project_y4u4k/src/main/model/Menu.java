package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


// represents a menu having drinks that this bar serves
public class Menu implements Writable {

    private List<Drink> drinkList;

    //EFFECTS: construct a menu with no drinks
    public Menu() {
        this.drinkList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add the drink to the menu, log this event to the Singleton EventLog
    public void addDrink(Drink drink) {
        this.drinkList.add(drink);
        EventLog.getInstance().logEvent(new Event("New item has been added to the menu "
                 + "(" + "price: " + drink.getPrice() + "$  name: " + drink.getName() + ")"));
    }

    //REQUIRES: name has a positive length
    //EFFECTS: return the drink having the input name if it exists in the menu, otherwise return null
    public Drink findDrink(String name) {
        Drink target = null;
        for (Drink d : getDrinkList()) {
            if (d.getName().equals(name)) {
                target = d;
            }
        }
        return target;
    }

    public List<Drink> getDrinkList() {
        return this.drinkList;
    }

    @Override
    // EFFECTS: convert menu to JSON representation and return it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("drinks", drinksToJson());
        return json;
    }

    // EFFECTS: returns drinks in this menu as a JSON array
    private JSONArray drinksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Drink d : this.drinkList) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }
}
