package persistence;

import model.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;


// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// represent a reader that reads bar and menu from JSON data stored in files
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads bar from file and returns it;
    // throws IOException if an error occurs reading data from file, clear the events for setting up based on the
    // previous data
    public Bar readBar() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        Bar bar = parseBar(jsonObject);
        EventLog.getInstance().clear();
        return bar;
    }

    // EFFECTS: reads menu from file and returns it;
    // throws IOException if an error occurs reading data from file
    // clear the events for setting up based on the previous data, and log this loading event
    public Menu readMenu() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        Menu menu = parseMenu(jsonObject);
        EventLog.getInstance().clear();
        EventLog.getInstance().logEvent(new Event("Data has been loaded successfully!"));
        return menu;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses bar from JSON object and returns it
    private Bar parseBar(JSONObject jsonObject) {
        Bar bar = new Bar();
        addTablesFromData(bar, jsonObject);
        return bar;
    }

    // MODIFIES: bar
    // EFFECTS: parses tables from JSON object and adds them to bar
    private void addTablesFromData(Bar bar, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tables");
        for (Object json : jsonArray) {
            JSONObject nextTable = (JSONObject) json;
            addTableFromData(bar, nextTable);
        }
    }


    // MODIFIES: bar
    // EFFECTS: parses table from JSON object and adds it to bar
    private void addTableFromData(Bar bar, JSONObject jsonObject) {
        int capacity = jsonObject.getInt("capacity");
        boolean availability = jsonObject.getBoolean("availability");
        int tableNum = jsonObject.getInt("table number");
        Table table = new Table(capacity, tableNum);
        table.setAvailability(availability);
        addCustomersFromData(table, jsonObject);
        bar.addTable(table);
    }

    // MODIFIES: table
    // EFFECTS: parses customers from JSON object and adds them to table
    private void addCustomersFromData(Table table, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("customers");
        for (Object json : jsonArray) {
            JSONObject nextCustomer = (JSONObject) json;
            addCustomerFromData(table, nextCustomer);
        }
    }

    // MODIFIES: table
    // EFFECTS: parses customer from JSON object and adds it to table
    private void addCustomerFromData(Table table, JSONObject jsonObject) {
        Customer customer = new Customer();
        addOrdersFromData(customer, jsonObject);
        table.addCustomer(customer);
    }

    // MODIFIES: customer
    // EFFECTS: parses orders from JSON object and adds it to customer
    private void addOrdersFromData(Customer customer, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("orders");
        for (Object json : jsonArray) {
            JSONObject nextOrder = (JSONObject) json;
            addOrderFromData(customer, nextOrder);
        }

    }

    // MODIFIES: customer
    // EFFECTS: parses order from JSON object and adds it to customer
    private void addOrderFromData(Customer customer, JSONObject jsonObject) {
        int price = jsonObject.getInt("price");
        String name = jsonObject.getString("name");
        Drink order = new Drink(price, name);
        customer.addOrder(order);
    }

    // EFFECTS: parses menu from JSON object and returns it
    private Menu parseMenu(JSONObject jsonObject) {
        Menu menu = new Menu();
        addDrinksFromData(menu, jsonObject);
        return menu;
    }

    // MODIFIES: menu
    // EFFECTS: parses drinks from JSON object and adds it to menu
    private void addDrinksFromData(Menu menu, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("drinks");
        for (Object json : jsonArray) {
            JSONObject nextDrink = (JSONObject) json;
            addDrinkFromData(menu, nextDrink);
        }

    }

    // MODIFIES: menu
    // EFFECTS: parses drink from JSON object and adds it to menu
    private void addDrinkFromData(Menu menu, JSONObject jsonObject) {
        int price = jsonObject.getInt("price");
        String name = jsonObject.getString("name");
        Drink drink = new Drink(price, name);
        menu.addDrink(drink);
    }













}
