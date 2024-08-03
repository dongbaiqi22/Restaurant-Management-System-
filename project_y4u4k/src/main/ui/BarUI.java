package ui;

import model.*;
import model.Menu;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.Event;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

// reference citation:
// 1. https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter Paul Carter
// 2. https://github.students.cs.ubc.ca/CPSC210/AlarmSystem Paul Carter
// 3. https://github.com/hongkailiu123/RestaurantOrdersManage Hongkai Liu
// 4. https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.git fgrund
// 5. https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase.git Paul Carter

// Bar Management System GUI
public class BarUI extends JFrame {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 360;

    private Bar myBar;
    private Table table1;
    private Table table2;
    private Table table3;
    private Menu myMenu;
    private Drink drink1;
    private Drink drink2;
    private Drink drink3;

    private static final String JSON_STORE_MENU = "./data/menu.json";
    private static final String JSON_STORE_BAR = "./data/bar.json";
    private JsonWriter jsonWriterBar;
    private JsonReader jsonReaderBar;
    private JsonWriter jsonWriterMenu;
    private JsonReader jsonReaderMenu;

    private JTextField username;
    private JTextField password;
    private JPanel logPanel;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JMenuBar menuBar;
    private JComboBox<String> tableComboBox;
    private JPanel tablesPanel;
    private Vector<String> tableVector;
    private Vector<String> orderVector;
    private JTable orderTable;
    private JComboBox orderComboBox;

    private Table currentTable;

    private Drink selectedDrink;


   // EFFECT: run the BarUI and set up the login page
    public BarUI() {
        super("Bar Management System");
        initializeGraphics();
        initializeFields();
        EventLog.getInstance().clear();
        login();
        setVisible(true);
    }

    //  MODIFIES: this
    //  EFFECTS: draws the JFrame window where the BarUI will operate
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    // MODIFIES: this
    // EFFECTS: set up three drink fields
    private void setup() {
        drink1 = new Drink(16, "Mojito");
        drink2 = new Drink(17, "Martini");
        drink3 = new Drink(15, "Old Fashion");
    }


    // MODIFIES: this
    // EFFECTS:  sets up this BarUI
    private void initializeFields() {
        myBar = new Bar();
        myMenu = new Menu();
        table1 = new Table(2, 1);
        table2 = new Table(3, 2);
        table3 = new Table(4,3);
        setup();
        myBar.addTable(table1);
        myBar.addTable(table2);
        myBar.addTable(table3);
        myMenu.addDrink(drink1);
        myMenu.addDrink(drink2);
        myMenu.addDrink(drink3);
        currentTable = myBar.getTables().get(0);
        selectedDrink = myMenu.getDrinkList().get(0);
        jsonWriterBar = new JsonWriter(JSON_STORE_BAR);
        jsonReaderBar = new JsonReader(JSON_STORE_BAR);
        jsonWriterMenu = new JsonWriter(JSON_STORE_MENU);
        jsonReaderMenu = new JsonReader(JSON_STORE_MENU);
        tableVector = new Vector<>();
        orderVector = new Vector<>();
        updateOrderVector();
        setOrderComboBox();
    }

    // MODIFIES: this
    // EFFECTS: set up orderComboBox which contains all items in the menu
    private void setOrderComboBox() {
        orderComboBox = new JComboBox<>(this.orderVector);
        orderComboBox.setSize(new Dimension(300, 30));
        orderComboBox.setBounds(360, 150, 400, 30);
        addListenerToOrderBox(orderComboBox);
    }

    // MODIFIES: this
    // EFFECTS: turn all the items information to a vector(orderVector),
    //          orderVector contains the name of items in the menu
    private void updateOrderVector() {
        List<Drink> drinks = myMenu.getDrinkList();
        Vector<String> vector = new Vector<>();
        for (Drink d : drinks) {
            String name = d.getName();
            vector.add(name);
        }
        this.orderVector = vector;
    }

    // EFFECTS: create a login page with a bar image, asking for the username and the password of the employee,
    //          provide the choices for whether load in previous data
    private void login() {
        logPanel = new JPanel();
        logPanel.setPreferredSize(new Dimension(1000, 1000));
        logPanel.setBackground(Color.white);
        add(logPanel);
        logPanel.setLayout(null);
        getUsername();
        getPassword();
        addLogButtons();
        addImage();
    }

    // MODIFIES: this
    // EFFECTS: create a JLabel asking for the username of the employee,
    //          create a JTextField where to type the username, then put them on logPanel(login page)
    private void getUsername() {
        JLabel askUsername = new JLabel("username: ");
        askUsername.setBounds(100, 20, 150, 30);
        logPanel.add(askUsername);

        username = new JTextField();
        username.setBounds(180, 20, 150, 30);
        logPanel.add(username);

        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: create a JLabel asking for the password of the employee,
    //          create a JTextField where to type the password, then put them on logPanel(login page)
    private void getPassword() {
        JLabel askUsername = new JLabel("password: ");
        askUsername.setBounds(100, 60, 150, 30);
        logPanel.add(askUsername);

        password = new JPasswordField();
        password.setBounds(180, 60, 150, 30);
        logPanel.add(password);

        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: create and add buttons to provide options for users to choose to log in with loading
    //          the previous data or not, then put it on the login page
    private void addLogButtons() {
        JLabel instruction = new JLabel("Do you want to log in with loading previous data or not");
        instruction.setBounds(80, 110, 500, 30);
        logPanel.add(instruction);

        JButton yesButton = new JButton("Yes");
        yesButton.setBounds(130, 145, 50, 30);
        addListenerToYesButton(yesButton);

        logPanel.add(yesButton);

        JButton noButton = new JButton("No");
        noButton.setBounds(320, 145, 50, 30);
        addListenerToNoButton(noButton);

        logPanel.add(noButton);

        revalidate();
    }


    // MODIFIES: this
    // EFFECTS:  adds the image to logPanel
    private void addImage() {
        try {
            BufferedImage image = ImageIO.read(new File("./data/barImage.png"));
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setBounds(130, 200, 264, 130);
            logPanel.add(imageLabel);
            revalidate();
            setVisible(true);
        } catch (Exception e) {
            System.out.println("Image problem");
        }
    }

    // MODIFIES: noButton
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to noButton
    private void addListenerToNoButton(JButton noButton) {


        noButton.addActionListener(new AbstractAction() {

            // MODIFIES: this
            // EFFECTS: jump into operation page without loading previous data

            @Override
            public void actionPerformed(ActionEvent e) {
                remove(logPanel);
                updateFrameForNo();
            }
        });
    }

    // MODIFIES: yesButton
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to yesButton

    private void addListenerToYesButton(JButton yesButton) {


        yesButton.addActionListener(new AbstractAction() {

            // MODIFIES: this
            // EFFECTS: jump into operation page with loading previous data

            @Override
            public void actionPerformed(ActionEvent e) {
                remove(logPanel);
                loadBarMenuGUI();
                updateFrameForYes();
                revalidate();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: resize the JFrame to width 1000, height 800, add
    //          menBar, mainPanel, controlPanel into the frame and load in previous data
    private void updateFrameForYes() {
        currentTable = myBar.getTables().get(0);
        setSize(new Dimension(1000, 800));
        setLayout(new GridLayout(2, 1));
        addWindowListenerClosing();
        setMenuBar();
        updateTableVector();
        updateOrderVector();
        setComboBox();
        setOrderComboBox();
        setTablePanel();
        String[][] data = {};
        if (!currentTable.getAvailability()) {
            List<Drink> drinks = currentTable.getCustomers().get(0).getOrders();
            data = translateOrder(drinks);
        }
        setMainPanel(data);
        setControlPanel();
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: resize the JFrame to width 1000, height 800, add
    //          menBar, mainPanel, controlPanel into the frame
    private void updateFrameForNo() {
        currentTable = myBar.getTables().get(0);
        setSize(new Dimension(1000, 800));
        setLayout(new GridLayout(2, 1));
        addWindowListenerClosing();
        setMenuBar();
        updateTableVector();
        setComboBox();
        setTablePanel();
        String[][] empty = {};
        setMainPanel(empty);
        setControlPanel();
    }

    // MODIFIES: this
    // EFFECTS: create a menuBar with three menu (table, menu, save),
    //          table has one item, add table
    //          menu has one item, update menu
    //          save has one item, save
    private void setMenuBar() {
        menuBar = new JMenuBar();
        JMenu menuTable = new JMenu("table");
        JMenu menuMenu = new JMenu("menu");
        JMenu save = new JMenu("save");
        JMenuItem updateMenu = new JMenuItem("update menu");
        JMenuItem addTable = new JMenuItem("add table");
        addListenerToAddTable(addTable);
        addListenerToUpdateMenu(updateMenu);
        JMenuItem saveData = new JMenuItem("save");
        menuMenu.add(updateMenu);
        menuTable.add(addTable);
        save.add(saveData);
        menuBar.setBorderPainted(true);
        menuBar.setPreferredSize(new Dimension(1000, 20));
        menuBar.setBounds(0, 0, 1000, 30);
        menuBar.add(menuTable);
        menuBar.add(menuMenu);
        menuBar.add(save);
        addListenerToSave(saveData);
        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: set up main panel and put it into the frame, then create a JTable with two columns items, price
    //          and information passed in, put the table into main panel
    private void setMainPanel(String[][] rows) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        String[] columnNames = {"Item", "Price"};

        orderTable = new JTable(rows, columnNames);

        JScrollPane scrollPane = new JScrollPane(orderTable);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: set controlPanel, create three buttons, "Assign Customer", "Take Orders", "Check", put them into the
    //          panel, put the control panel into the frame
    private void setControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setBackground(new Color(240, 240, 240));
        JButton assign = new JButton("Assign Customers");
        JButton takeOrder = new JButton("Take Orders");
        JButton check = new JButton("Check");
        addListenerToCheck(check);
        addListenerToAssign(assign);
        addListenerToTakeOrder(takeOrder);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.add(assign);
        buttonPanel.add(takeOrder);
        buttonPanel.add(check);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);
        controlPanel.add(tablesPanel, BorderLayout.CENTER);
        add(controlPanel);
    }

    // MODIFIES: this
    // EFFECTS: set up a comboBox which has all tables in the bar
    private void setComboBox() {
        tableComboBox = new JComboBox<>(this.tableVector);
        tableComboBox.setBounds(360, 150, 250, 30);
        addListenerToTableBox(tableComboBox);
    }

    // MODIFIES: this
    // EFFECTS: set tablesPanel, put the tableComboBox, instruction label, customer number label into it
    private void setTablePanel() {
        tablesPanel = new JPanel();
        tablesPanel.setLayout(new GridLayout(2, 1));
        tablesPanel.setPreferredSize(new Dimension(1000, 400));
        tablesPanel.setBackground(new Color(240, 240, 240));
        tablesPanel.setLayout(null);
        JLabel instruction = new JLabel("Please select a table");
        instruction.setBounds(420, 100, 300, 30);
        JLabel cusNum = new JLabel("The number of customers: "
                + String.valueOf(currentTable.getCustomers().size()));
        cusNum.setBounds(400, 200, 300, 30);
        tablesPanel.add(instruction);
        tablesPanel.add(tableComboBox);
        tablesPanel.add(cusNum);
    }

    // MODIFIES: this
    // EFFECTS: update the tableVector according to the myBar, to ensure all the tables in myBar are in the vector
    private void updateTableVector() {
        List<Table> tables = myBar.getTables();
        Vector<String> vector = new Vector<>();
        for (Table t : tables) {
            int num = t.getTableNumber();
            vector.add("Table " + num);
        }
        this.tableVector = vector;
    }

    // MODIFIES: this
    // EFFECTS: saves the bar and the menu to file
    private void saveBarMenuGUI() {
        try {
            jsonWriterBar.open();
            jsonWriterBar.writeBar(myBar);
            jsonWriterBar.close();
            jsonWriterMenu.open();
            jsonWriterMenu.writeMenu(myMenu);
            jsonWriterMenu.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_MENU + " and " + JSON_STORE_BAR);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the bar and the menu from file
    private void loadBarMenuGUI() {
        try {
            myBar = jsonReaderBar.readBar();
            myMenu = jsonReaderMenu.readMenu();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_MENU + " and " + JSON_STORE_BAR);
        }
    }

    // MODIFIES: save
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to save
    private void addListenerToSave(JMenuItem save) {
        save.addActionListener(new AbstractAction() {

            // MODIFIES: this
            // EFFECTS: save the data of current state
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBarMenuGUI();
            }
        });
    }

    // MODIFIES: check
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to check
    private void addListenerToCheck(JButton check) {
        check.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: display a JOptionPane.showConfirmDialog that shows the total due of the table
            //          and remove the customers at that table
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel checkOutPanel = new JPanel();


                String finalDue = "The total due is $" + currentTable.generateOneBill() + ".";

                JLabel due = new JLabel(finalDue);

                due.setPreferredSize(new Dimension(200, 100));

                checkOutPanel.add(due);

                int result = JOptionPane.showConfirmDialog(null, checkOutPanel,
                        "Bill", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    currentTable.removeAllCustomers();
                    updateMainPanel();
                    updateControlPanel();
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: update the components on control panel according to the current state
    private void updateControlPanel() {
        controlPanel.remove(tablesPanel);
        setTablePanel();
        controlPanel.add(tablesPanel, BorderLayout.CENTER);
        controlPanel.revalidate();
        setVisible(true);
    }



    // MODIFIES: this
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to table box
    private void addListenerToTableBox(JComboBox tableComboBox) {
        tableComboBox.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: update the current table to the selected table on comboBox, update the tablePanel and
            //          mainPanel according to the current state
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexOfTable = tableComboBox.getSelectedIndex();
                List<Table> tables = myBar.getTables();
                currentTable = tables.get(indexOfTable);
                updateTablePanelNumberOfCustomer();
                updateMainPanel();
            }
        });
    }

    // MODIFIES: Assign Customer
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to assign customer
    private void addListenerToAssign(JButton assign) {
        assign.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: display a JOptionPane.showConfirmDialog, user can input the number of customers, then
            //          assign this number of customers to the current table
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputsPanel = new JPanel();

                JLabel stringLabel = new JLabel("Number of customers: ");
                JTextField stringField = new JTextField(3);
                inputsPanel.add(stringLabel);
                inputsPanel.add(stringField);


                int result = JOptionPane.showConfirmDialog(null, inputsPanel,
                        "Enter the number of customers", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    int num = Integer.parseInt(stringField.getText());
                    currentTable.addMultipleCustomers(num);
                    updateTablePanelNumberOfCustomer();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: update the information of number of customer on tablePanel according to the current state
    private void updateTablePanelNumberOfCustomer() {

        tablesPanel.remove(2);
        tablesPanel.remove(1);
        tablesPanel.add(tableComboBox);

        JLabel cusNum = new JLabel("The number of customers: " + String.valueOf(currentTable.getCustomers().size()));
        cusNum.setBounds(400, 200, 300, 30);

        tablesPanel.add(cusNum);

        controlPanel.add(tablesPanel, BorderLayout.CENTER);

        tablesPanel.revalidate();
        tablesPanel.repaint();
        setVisible(true);
    }

    // MODIFIES: take order
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to check
    private void addListenerToTakeOrder(JButton take) {
        take.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: display a JOptionPane.showConfirmDialog with a comboBox contains the items in the menu, user
            //          can select an item and take this item for the current table
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputsPanel = new JPanel();
                inputsPanel.add(orderComboBox);
                int result = JOptionPane.showConfirmDialog(null, inputsPanel,
                        "Select an item", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    //currentTable.getCustomers().get(0).addOrder(selectedDrink);
                    currentTable.addOrderToTable(selectedDrink);
                    updateMainPanel();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: updates the order information of current table
    private void updateMainPanel() {
        String[][] orderData = {};
        mainPanel.removeAll();
        if (!currentTable.getAvailability()) {
            List<Drink> drinks = currentTable.getCustomers().get(0).getOrders();
            orderData = translateOrder(drinks);
        }
        String[] columnNames = {"Item", "Price"};
        orderTable = new JTable(orderData, columnNames);
        JScrollPane scrollPane = new JScrollPane(orderTable);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.revalidate();
    }

    // EFFECTS: turn the orders information to String[][], returns it
    private String[][] translateOrder(List<Drink> orders) {
        String[][] ordersInfo = new String[orders.size()][2];

        for (int i = 0; i < orders.size(); i++) {
            Drink d = orders.get(i);
            ordersInfo[i][0] = d.getName();
            ordersInfo[i][1] = String.valueOf(d.getPrice());
        }

        return ordersInfo;
    }

    // MODIFIES: this
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to orderBox
    private void addListenerToOrderBox(JComboBox orderComboBox) {
        orderComboBox.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: change the selectedDrink to the selection on comboBox
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexOfDrink = orderComboBox.getSelectedIndex();
                List<Drink> drinks = myMenu.getDrinkList();
                selectedDrink = drinks.get(indexOfDrink);
            }
        });
    }

    // MODIFIES: take order
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to add table
    private void addListenerToAddTable(JMenuItem addTable) {
        addTable.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: display a JOptionPane.showConfirmDialog with a textField to enter the capacity of new table,
            //          user can select an item and take this item for the current table
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputsPanel = new JPanel();

                JLabel stringLabel = new JLabel("Capacity: ");
                JTextField stringField = new JTextField(3);
                inputsPanel.add(stringLabel);
                inputsPanel.add(stringField);

                int result = JOptionPane.showConfirmDialog(null, inputsPanel,
                        "Enter the capacity of the table", JOptionPane.OK_CANCEL_OPTION);



                if (result == JOptionPane.OK_OPTION) {
                    int num = Integer.parseInt(stringField.getText());
                    Table table = new Table(num, myBar.getTables().size() + 1);
                    myBar.addTable(table);
                    updateTableVector();
                    setComboBox();
                    tablesPanel.remove(1);
                    tablesPanel.add(tableComboBox);
                    updateTablePanelNumberOfCustomer();
                }
            }
        });
    }

    // MODIFIES: take order
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed to update menu
    private void addListenerToUpdateMenu(JMenuItem updateMenu) {
        updateMenu.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: display a JOptionPane.showConfirmDialog with a textField to enter the name of the new item
            //          and the price of the item, then this new item will be added to myMenu, the orderComboBox will
            //          change accordingly
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputsPanel = new JPanel();
                inputsPanel.setLayout(new GridLayout(2, 2));
                JLabel nameLabel = new JLabel("The name of the new item: ");
                JTextField nameField = new JTextField(20);
                JLabel priceLabel = new JLabel("The price of the new item: ");
                JTextField priceField = new JTextField(20);
                inputsPanel.add(nameLabel);
                inputsPanel.add(nameField);
                inputsPanel.add(priceLabel);
                inputsPanel.add(priceField);

                int result = JOptionPane.showConfirmDialog(null, inputsPanel,
                        "Enter the capacity of the table", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    int price = Integer.parseInt(priceField.getText());
                    String name = nameField.getText();
                    addDrinkHelper(price, name);
                    updateOrderComboBoxHelper();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: make a new drink and add it to myMenu
    private void addDrinkHelper(int price, String name) {
        Drink item = new Drink(price, name);
        myMenu.addDrink(item);
    }

    // MODIFIES: this
    // EFFECTS: update the order vector based on the current menu, update the orderComboBox accordingly
    private void updateOrderComboBoxHelper() {
        updateOrderVector();
        setOrderComboBox();
    }

    // EFFECTS: print all events since this run of application
    private void printEvents() {
        for (Event next : EventLog.getInstance()) {
            if (!next.getDescription().equals("Event log cleared.")) {
                System.out.println(next.toString());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: add WindowListener with a specific implementation of windowClosing to the frame
    private void addWindowListenerClosing() {
        // EFFECTS: print all events in log to the console if the window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEvents();
            }
        });
    }
}

