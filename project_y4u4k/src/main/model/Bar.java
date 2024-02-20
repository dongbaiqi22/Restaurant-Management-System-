package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// represents a bar having its own tables
public class Bar implements Writable {
    private List<Table> tables;

    //EFFECTS: construct a new bar with no tables
    public Bar() {
        this.tables = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add the table to this bar, log this event to the Singleton EventLog
    public void addTable(Table table) {
        this.tables.add(table);
        EventLog.getInstance().logEvent(new Event("New table with a capacity of "
                + table.getCapacity() + " has been added!"));
    }

    //REQUIRES: capacity > 0
    //EFFECTS: shows all the available tables that satisfies the capacity (in the order that they were added)
    public List<Table> filterTable(int capacity) {
        List<Table> filteredTables = new ArrayList<>();
        for (Table t : this.tables) {
            if (t.getAvailability() && t.getCapacity() >= capacity) {
                filteredTables.add(t);
            }
        }
        return filteredTables;
    }

    //REQUIRES: tableNum >= 0
    //EFFECTS: if the table has the table number exists in the bar, return this table, otherwise, return null
    public Table findTable(int tableNum) {
        Table target = null;
        for (Table t : this.tables) {
            if (t.getTableNumber() == tableNum) {
                target = t;
            }
        }
        return target;
    }

    public List<Table> getTables() {
        return this.tables;
    }

    // EFFECTS: convert bar to JSON representation and return it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tables", tablesToJson());
        return json;
    }

    // EFFECTS: returns tables in this menu as a JSON array
    private JSONArray tablesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Table t : this.tables) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


}
