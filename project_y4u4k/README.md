## *Bar Managing System*



## *What will the application do?*
I am planning to design an application for bars to manage seating and billing.With this application users can customize table information based on the number and capacity of tables at their bars and create menus based on what they serve. Servers can check the availability of tables and the seating capacity of each table. With this information, servers can allocate customers to available tables efficiently, in which servers can register the group of customers into an available table and the availability of that table will automatically change and that table will carry the future order and bill information. Then, servers can use this application to take orders from each customer seated at the table by putting the order under the person who ordered it. For the payment phase, servers can choose to generate one bill with all the orders for all customers or generate individual bills for each customer.
## *Who will use it?*
This user-friendly application will cater to servers working at bars, providing them with the tools needed to improve their workflow.
## *Why is this project of interest to you?*
I am interested in designing this project because I always go to bars on weekends and I have a couple of friends working at bars, so I want to design an application for people working at bars to make their work easier and improve their efficiency.
## **User Story**:
- As a user, I want to be able to add more drinks to this menu
- As a User, I want to show the menu to customers
- As a user, I want to be able to create a new table and add it to a list of tables(Bar).
- As a user, I want to be able to find a table for my customers based on the size of their group.
- As a user, I want to be able to add my customers to some table.
- As a user, I want to be able to select a customer at a table and add new orders for that customer. (add the order to a list of orders)
- As a user, I want to be able to generate one bill that has all the orders for the table and be able to generate separate bills for each customer seated at the table.
- As a user, I want to remove the customers at some table.
- As a user, I want to be able to save my bar information (which includes table information, customers information and customers' orders) and menu information(which includes all the drinks in it). (if I so choose)
- As a user, I want to be able to load my bar information (which includes table information, customers information and customers' orders) and menu information(which includes all the drinks in it). (if I so choose)





## *Instruction for Grader*
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking take order, select the item, then you can add order to this table, order information will show up on the top half of the interface. (Before taking the order, you have to assign the customer to this table first, since you cannot take order from a table without customer)
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the "take order" button to operate "adding Xs to Y"(add order to the table).
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the "check" button the show the total due of the table.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the assign customer and input the number of customers to assign customers to the selected table, then it will show the number of customers at this table on the interface.
- You can click "update menu" in the menu bar, then you can create a new item and add to the menu
- You can click "add table" in the menu bar, then you can set the capacity of the new table, and it will be added to the bar.
- You can see my visual component on the login page.
- You can save the state of my application by click the menu called save on the interface.
- You can reload the state of my application by choose yes on login page.

## *Phase 4: Task 2*

The events I choose to log are:

- adding a new table
- adding a new item to the menu
- assigning customers into a table
- taking order for a table
- checking for a table
- saving data of the current state
- loading the previous data

Example: 

- Mon Nov 27 18:23:50 PST 2023
- Data has been loaded successfully!
- Mon Nov 27 18:24:01 PST 2023
- A customer group of 2 has been assigned to table2
- Mon Nov 27 18:24:05 PST 2023
- Coke has been added to table2
- Mon Nov 27 18:24:08 PST 2023
- Old Fashion has been added to table2
- Mon Nov 27 18:24:20 PST 2023
- New table with a capacity of 5 has been added!
- Mon Nov 27 18:24:34 PST 2023
- A customer group of 1 has been assigned to table4
- Mon Nov 27 18:24:49 PST 2023
- New item has been added to the menu (price: 5$  name: Coffee)
- Mon Nov 27 18:24:55 PST 2023
- Coffee has been added to table4
- Mon Nov 27 18:24:56 PST 2023
- Check for table4
- Mon Nov 27 18:25:00 PST 2023
- Data has been saved successfully!

The first line "Data has been loaded successfully!" is an example of logging "loading event". 
The second line "A customer group of 2 has been assigned to table2" is an example of logging "assigning customer" event.
The third line "Coke has been added to table2" is an example of logging "taking order" event.
The fifth line "New table with a capacity of 5 has been added!" is an example of logging "adding new table" event.
The seventh line "New item has been added to the menu (price: 5$  name: Coffee)" is an example of logging "adding new item to the menu" event.
The last second line "Check for table4" is an example of logging "checking" event.
The last line "Data has been saved successfully!" is an example of logging "saving" event.

## *Phase 4: Task 3*

There are two methods in BarUI, updateFrameForYes and updateFrameForNo. The tasks these two method do are very similar, both are working for updating the frame after logging in. The only difference is that updateFrameForYes works for the situation where the user want to load in the previous data while updateFrameForNo works for the situation where the user does not want to load the data. Also, the ways these two methods work are also very similar, which is just resizing the frame, setting all the panels needed and putting them on the frame.The difference is that updateFrameForYes sets these panels based on the previous data loaded into the program, while updateFrameForNo set the panel only based on initialized data. Therefore, these two methods can be refactored into only one method. What I would do is that I will firstly use two loops to check whether the tables at bar and drinks in menu are the same as the initialized data. If all of them are the same, which means the user did not choose to load previous data, then we go through the rest of the process in the way of updateFrameForNo. But if the data of the current state is not the same as the initialized state, which means the user have chosen to load the data, then we can go through the rest of the process in the way of updateFrameForYes. 

For my graphical user interface, it is built up with one frame and multiple panels that carries some components. What I did was that I put all the panels and the frame in one class, which made my BarUI class lack of cohesion. To prevent this cohesion problem, I think I should separate the panels into multiple individual classes. These panel classes will be used as fields in the class for UI. So updating the information shows up on the interface will be transformed to manipulate the field, and the UI class will be more cohesive by doing so. Then I can use an observer pattern to reduce the dependencies in the program. If observer pattern is not used here, then this panels have to have two fields, bar and menu, to keep following up the data of the current state and update the visualized data based on these two fields. Instead, an observer pattern can be used here by taking menu and bar as observable, and taking these panels as observers. Then, if any changes happened in bar or menu, we can make a call to notifyObserver method which takes bar or menu and an event as parameters to notify these panels to makes changes to visualized data on interface accordingly. The coupling of this program will be reduced a lot by using this observer pattern.

Also, in my BarApp and BarUI, both classes have three default tables and drinks. While, these two classes also have a bar which has a list of tables and menu which has a list of drinks. In the setup method, I put these three tables into the bar and three drinks into menu. However, in fact, I do not need to make these default drinks and tables as fields, which could bring me a lot of further troubles when I add remove function into program, since removing these default stuff is actually setting these fields to null, which might cause null pointer exceptions when running the program. Instead, I can just instantiate these default drinks and tables in my setup method and add them into menu and bar, which will make it easier for me to remove them and not cause any null pointer exceptions.
