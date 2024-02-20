package persistence;

import java.io.*;
import model.Bar;
import model.Event;
import model.EventLog;
import model.Menu;
import org.json.JSONObject;

// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a writer that writes JSON representation of menu and bar to file
public class JsonWriter {


    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of bar to file, log this event to the Singleton EventLog
    public void writeBar(Bar bar) {
        JSONObject json = bar.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Data has been saved successfully!"));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of menu to file
    public void writeMenu(Menu menu) {
        JSONObject json = menu.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }


}
