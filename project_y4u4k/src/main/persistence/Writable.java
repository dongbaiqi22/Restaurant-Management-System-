package persistence;

import org.json.JSONObject;


// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
