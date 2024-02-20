package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


// Reference: Sam, Jul 26.2021, TellerApp, java,
// https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/main/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


// Bar management application
public class BarApp {
    private static final String JSON_STORE_MENU = "./data/menu.json";
    private static final String JSON_STORE_BAR = "./data/bar.json";
    private Bar myBar;
    private Table table1;
    private Table table2;
    private Table table3;
    private Menu myMenu;
    private Drink drink1;
    private Drink drink2;
    private Drink drink3;
    private Scanner input;
    private JsonWriter jsonWriterBar;
    private JsonReader jsonReaderBar;
    private JsonWriter jsonWriterMenu;
    private JsonReader jsonReaderMenu;

    //EFFECTS: runs the bar management application
    public BarApp() {
        runBar();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runBar() {
        boolean continueProgram = true;
        String command = null;
        init();
        while (continueProgram) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                continueProgram = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye! Have a wonderful day!!!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("t")) {
            doAddTables();
        } else if (command.equals("a")) {
            doShowTables();
        } else if (command.equals("d")) {
            doAddDrinks();
        } else if (command.equals("c")) {
            doAssignCustomers();
        } else if (command.equals("o")) {
            doTakeOrders();
        } else if (command.equals("b")) {
            doBills();
        } else if (command.equals("f")) {
            doFindTable();
        } else if (command.equals("m")) {
            doMenu();
        } else if (command.equals("r")) {
            doRemoveAllCustomers();
        } else {
            saveProcessCommand(command);
        }
    }

    //MODIFIES: this
    //EFFECTS: process the rest of selections of user command
    private void saveProcessCommand(String command) {
        if (command.equals("s")) {
            saveBarMenu();
        } else if (command.equals("l")) {
            loadBarMenu();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes bar, menu, drinks and tables
    private void init() {
        myBar = new Bar();
        myMenu = new Menu();
        table1 = new Table(2, 1);
        table2 = new Table(3, 2);
        table3 = new Table(4,3);
        drink1 = new Drink(16, "Mojito");
        drink2 = new Drink(17, "Martini");
        drink3 = new Drink(15, "Old Fashion");
        myBar.addTable(table1);
        myBar.addTable(table2);
        myBar.addTable(table3);
        myMenu.addDrink(drink1);
        myMenu.addDrink(drink2);
        myMenu.addDrink(drink3);
        jsonWriterBar = new JsonWriter(JSON_STORE_BAR);
        jsonReaderBar = new JsonReader(JSON_STORE_BAR);
        jsonWriterMenu = new JsonWriter(JSON_STORE_MENU);
        jsonReaderMenu = new JsonReader(JSON_STORE_MENU);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: print out the menu
    private void doMenu() {
        for (Drink d : myMenu.getDrinkList()) {
            System.out.println(d.getName() + "---------" + "$" + d.getPrice());
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tm -> show menu");
        System.out.println("\ta -> show table information");
        System.out.println("\tt -> add tables");
        System.out.println("\td -> add drinks");
        System.out.println("\tf -> find a table");
        System.out.println("\tc -> assign customers");
        System.out.println("\to -> take orders");
        System.out.println("\tb -> bills");
        System.out.println("\tr -> remove customers");
        System.out.println("\ts -> save bar information and menu to file");
        System.out.println("\tl -> load bar information and menu from file");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: conduct an add table
    private void doAddTables() {
        Bar bar = this.myBar;
        System.out.println("Enter the capacity of the table: ");
        int capacity = input.nextInt();
        input.nextLine();
        int tableNum = myBar.getTables().size() + 1;
        Table newTable = new Table(capacity, tableNum);
        bar.addTable(newTable);
        System.out.println("A new table has been added to the bar!");
    }

    // MODIFIES: this
    // EFFECTS: conduct an add drink
    private void doAddDrinks() {
        Menu menu = this.myMenu;
        System.out.println("Enter the name of the drink: ");
        String name = input.next();
        input.nextLine();
        System.out.println("Enter the price of the drink: ");
        int price = input.nextInt();
        input.nextLine();
        Drink drink = new Drink(price, name);
        menu.addDrink(drink);
        System.out.println("A new drink has been added to the menu!");
    }

    // MODIFIES: this
    // EFFECTS: conduct an add drink
    private void doAssignCustomers() {
        System.out.println("Enter the table number of which table you want to add the customer to: ");
        int tableNum = input.nextInt();
        input.nextLine();
        Table table = myBar.findTable(tableNum);
        if (table == null) {
            System.out.println("The table number is invalid");
        } else {
            System.out.println("Enter the number of customers: ");
            int numOfCustomers = input.nextInt();
            input.nextLine();
            table.addMultipleCustomers(numOfCustomers);
            System.out.println("Customers has been added successfully!");
        }
    }


    //MODIFIES: this
    //EFFECTS: add order to some customer at some table
    private void doTakeOrders() {
        System.out.println("Enter the table number: ");
        int tableNum = input.nextInt();
        Table table = myBar.findTable(tableNum);
        if (table == null) {
            System.out.println("The table number is invalid");
        } else {
            System.out.println("Enter the seat number of the customer: ");
            int seatNum = input.nextInt();
            if (seatNum > table.getCustomers().size()) {
                System.out.println("The seat number is invalid");
            } else {
                Customer c = table.getCustomers().get(seatNum - 1);
                System.out.println("Enter the name of the drink: ");
                String drinkName = input.next();
                Drink drink = myMenu.findDrink(drinkName);
                if (drink == null) {
                    System.out.println("The drink name is invalid");
                } else {
                    c.addOrder(drink);
                    System.out.println("Order has been added");
                }
            }
        }
    }

    //EFFECTS: if input is together, then generate one bill for the whole table
    //         if input is separate, then generate individual bill for each customer

    private void doBills() {
        System.out.println("Enter the table number: ");
        int tableNum = input.nextInt();
        input.nextLine();
        Table table = myBar.getTables().get(tableNum - 1);
        if (table == null) {
            System.out.println("Table number is invalid");
        } else {
            System.out.println("Together or Separate: ");
            String instruction = input.next();
            if (instruction.equals("Together")) {
                int bill = table.generateOneBill();
                printBill(bill);
            } else if (instruction.equals("Separate")) {
                List<Integer> bills = table.generateSeparateBill();
                printBills(bills);
            } else {
                System.out.println("Invalid Command.");
            }
        }
    }

    //EFFECTS: Find available tables that has enough capacity
    private void doFindTable() {
        System.out.println("Enter the number of customers: ");
        int numOfCustomers = input.nextInt();
        input.nextLine();
        List<Table> suitableTables = myBar.filterTable(numOfCustomers);
        if (suitableTables.size() > 0) {
            printTables(suitableTables);
        } else {
            System.out.println("Sorry, there is no available table at this moment!");
        }
    }

    //EFFECTS: print tables
    private void printTables(List<Table> tables) {
        int n = tables.size();
        int i = 0;
        while (i < n) {
            int tableNum = tables.get(i).getTableNumber();
            System.out.println("table" + tableNum);
            i = i + 1;
        }
        System.out.println("are available for this group of customers at this moment.");
    }

    //EFFECTS: print bill
    private void printBill(int bill) {
        System.out.println("Your total is " + "$" + bill);
    }

    //EFFECTS: print separate bills
    private void printBills(List<Integer> bills) {
        int n = bills.size();
        int i = 0;
        while (i < n) {
            int payment = bills.get(i);
            System.out.println("The total payment for customer" + (i + 1) + " " + "is" + " " + "$" + payment);
            i = i + 1;
        }
    }

    //EFFECTS: print out all the table information
    private void doShowTables() {
        for (Table t : myBar.getTables()) {
            System.out.println("Table Number:" + t.getTableNumber() + " "
                    + "Capacity:" + t.getCapacity() + " " + "Availability:" + t.checkAvailability());
        }
    }

    //MODIFIES: this
    //EFFECTS: do remove all customers at this table
    private void doRemoveAllCustomers() {
        System.out.println("Enter the table number of which table you want to remove the customers: ");
        int tableNum = input.nextInt();
        input.nextLine();
        Table table = myBar.findTable(tableNum);
        if (table == null) {
            System.out.println("The table number is invalid");
        } else {
            table.removeAllCustomers();
            System.out.println("Customers have been removed successfully!");
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the bar and the menu to file
    private void saveBarMenu() {
        try {
            jsonWriterBar.open();
            jsonWriterBar.writeBar(myBar);
            jsonWriterBar.close();
            jsonWriterMenu.open();
            jsonWriterMenu.writeMenu(myMenu);
            jsonWriterMenu.close();
            System.out.println("Saved " + "bar information and menu"
                    + " to " + JSON_STORE_MENU + " and " + JSON_STORE_BAR);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_MENU + " and " + JSON_STORE_BAR);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the bar and the menu from file
    private void loadBarMenu() {
        try {
            myBar = jsonReaderBar.readBar();
            myMenu = jsonReaderMenu.readMenu();
            System.out.println("Loaded " + "bar information and menu" + " from "
                    + JSON_STORE_MENU + " and " + JSON_STORE_BAR);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_MENU + " and " + JSON_STORE_BAR);
        }
    }










}
