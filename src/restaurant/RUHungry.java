package restaurant;

/**
 * RUHungry is a fictitious restaurant.
 * You will be running RUHungry for a day by seating guests,
 * taking orders, donation requests and restocking the pantry as necessary.
 * 
 * Compiling and executing:
 * 1. use the run or debug function to run the driver and test your methods
 * 
 * @author Mary Buist
 * @author Kushi Sharma
 */

public class RUHungry {

    /*
     * Instance variables
     */

    // Menu: two parallel arrays. The index in one corresponds to the same index in
    // the other.
    private String[] categoryVar; // array where containing the name of menu categories (e.g. Appetizer, Dessert).
    private MenuNode[] menuVar; // array of lists of MenuNodes where each index is a category.

    // Stock: hashtable using chaining to resolve collisions.
    private StockNode[] stockVar; // array of linked lists of StockNodes (use hashfunction to organize Nodes: id %
                                  // stockVarSize)
    private int stockVarSize;

    // Transactions: orders, donations, restock transactions are recorded
    private TransactionNode transactionVar; // refers to the first front node in linked list

    // Queue keeps track of parties that left the restaurant
    private Queue<Party> leftQueueVar;

    // Tables Information - parallel arrays
    // If tableSeats[i] has 3 seats then parties with at most 3 people can sit at
    // tables[i]
    private Party[] tables; // Parties currently occupying the tables
    private int[] tableSeats; // The number of seats at each table

    /*
     * Default constructor
     */
    public RUHungry() {
        categoryVar = null;
        menuVar = null;
        stockVar = null;
        stockVarSize = 0;
        transactionVar = null;
        leftQueueVar = null;
        tableSeats = null;
        tables = null;
    }

    /*
     * Getter and Setter methods
     */
    public MenuNode[] getMenu() {
        return menuVar;
    }

    public String[] getCategoryArray() {
        return categoryVar;
    }

    public StockNode[] getStockVar() {
        return stockVar;
    }

    public TransactionNode getFrontTransactionNode() {
        return transactionVar;
    }

    public TransactionNode resetFrontNode() {
        return transactionVar = null;
    } // method to reset the transactions for a new day

    public Queue<Party> getLeftQueueVar() {
        return leftQueueVar;
    }

    public Party[] getTables() {
        return tables;
    }

    public int[] getTableSeats() {
        return tableSeats;
    }

    /*
     * Menu methods
     */

    /**
     * 
     * This method populates the two parallel arrays menuVar and categoryVar.
     * 
     * Each index of menuVar corresponds to the same index in categoryVar (a menu
     * category like Appetizers).
     * If index 0 at categoryVar is Appetizers then menuVar at index 0 contains
     * MenuNodes of appetizer dishes.
     * 
     * 1. read the input file:
     * a) the first number corresponds to the number of categories (aka length of
     * menuVar and categoryVar)
     * b) the next line states the name of the category (populate CategoryVar as you
     * read each category name)
     * c) the next number represents how many dishes are in that category - this
     * will be the size of the linked list in menuVar for this category
     * d) the next line states the name of the dish
     * e) the first number in the next line represents how many ingredient IDs there
     * are
     * f) the next few numbers (all in the 100s) are each the ingredient ID
     * 
     * 2. As you read through the input file:
     * a) populate the categoryVar array
     * b) populate menuVar depending on which index (aka which category) you are in
     * i) make a dish object (with filled parameters -- don't worry about "price"
     * and "profit" in the dish object for right now)
     * ii) create menuNode and insert at the front of menuVar (NOTE! there will be
     * multiple menuNodes in one index)
     * 
     * @param inputFile - use menu.in file which contains all the dishes
     */

    public void menu(String inputFile) {
        StdIn.setFile(inputFile); // opens the inputFile to be read

        int num = StdIn.readInt(); // count the number of categories

        // I have to intitialize array
        categoryVar = new String[num];
        menuVar = new MenuNode[num]; // linked list

        while (StdIn.hasNextLine()) { // while loop

            for (int i = 0; i < num; i++) {
                String cate = StdIn.readString();// read the name of each category
                categoryVar[i] = cate;

                int numDish = StdIn.readInt(); // count the number of dish
                for (int j = 0; j < numDish; j++) {
                    String dishName = StdIn.readLine(); // read the name of dish
                    dishName = StdIn.readLine(); // you have to read twice
                    // System.out.println(dishName);
                    int numId = StdIn.readInt();
                    int id[] = new int[numId];

                    for (int k = 0; k < numId; k++) {
                        int ID = StdIn.readInt();
                        id[k] = ID;

                    }

                    //
                    Dish dish = new Dish(cate, dishName, id);

                    MenuNode menuNode = new MenuNode(dish, menuVar[i]); // how to find front node of currentNode. set
                                                                        // next node as front node

                    menuVar[i] = menuNode;

                    // set menuNode

                    // have to know next node
                    // it matters with menuvar

                }

            }

        }

        // for(int j = 0; j < num; j++){
        // Dish dish = new Dish(c,, tableSeats); // how do you make category place
        // MenuNode menuNode = new MenuNode(dish, null);
        // menuVar[j] = menuNode;

        // }

    }

    // WRITE YOUR CODE HERE

    /**
     * Find and return the MenuNode that contains the dish with dishName in the
     * menuVar.
     * 
     * ** GIVEN METHOD **
     * ** DO NOT EDIT **
     * 
     * @param dishName - the name of the dish
     * @return the dish object corresponding to searched dish, null if dishName is
     *         not found.
     */

    public MenuNode findDish(String dishName) {

        MenuNode menuNode = null;

        // Search all categories since we don't know which category dishName is at
        for (int category = 0; category < menuVar.length; category++) {

            MenuNode ptr = menuVar[category]; // set ptr at the front (first menuNode)

            while (ptr != null) { // while loop that searches the LL of the category to find the itemOrdered
                if (ptr.getDish().getName().equalsIgnoreCase(dishName)) {
                    return ptr;
                } else {
                    ptr = ptr.getNextMenuNode();
                }
            }
        }
        return menuNode;
    }

    /**
     * Find integer that corresponds to the index in menuVar and categoryVar arrays
     * that has that category
     * 
     * ** GIVEN METHOD **
     * ** DO NOT EDIT **
     *
     * @param category - the category name
     * @return index of category in categoryVar
     */

    public int findCategoryIndex(String category) {
        int index = 0;
        for (int i = 0; i < categoryVar.length; i++) {
            if (category.equalsIgnoreCase(categoryVar[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    /*
     * Stockroom methods
     */

    /**
     * PICK UP LINE OF THE METHOD:
     * *can I insert myself into your life? cuz you always help me sort
     * out my problems and bring stability to my mine*
     * 
     * ***********
     * This method adds a StockNode into the stockVar hashtable.
     * 
     * 1. get the id of the given newNode and use a hash function to get the index
     * at which the
     * newNode is being inserted.
     * 
     * HASH FUNCTION: id % stockVarSize
     * 
     * 2. insert at the front of the linked list at the specific index
     * 
     * @param newNode - StockNode that needs to be inserted into StockVar
     */

    public void addStockNode(StockNode newNode) {

        // WRITE YOUR CODE HERE

        Ingredient ingredient = newNode.getIngredient();
        int ingredientId = ingredient.getID();

        int insertIndex = ingredientId % stockVarSize; // the index for a newNode in stackVar

        StockNode frontStockNode = stockVar[insertIndex];

        if (frontStockNode == null) {
            frontStockNode = newNode;
            stockVar[insertIndex] = newNode;

        }

        else {

            newNode.setNextStockNode(frontStockNode);
            frontStockNode = newNode;
            stockVar[insertIndex] = newNode;
            // frontStockNode = frontStockNode.getNextStockNode();

        }
        // stockVar[insertIndex] = newNode;

        // I need to figure out how to supposed to make linked list.

        // stockVar[insertIndex] is a StockNode now

    }

    /**
     * This method finds an ingredient from StockVar (given the ingredientID)
     * 
     * 1. find the node based upon the ingredient ID (you can go to the specific
     * index using the hash function!)
     * (a) this is an efficient search as it looks only at the linked list which the
     * key hash to
     * 2. find and return the node
     * 
     * @param ingredientID - the ID of the ingredient
     * @return the StockNode corresponding to the ingredientID, null otherwise
     */

    public StockNode findStockNode(int ingredientID) {

        // WRITE YOUR CODE HERE

        StockNode stockNode = null;

        for (int i = 0; i < stockVar.length; i++) {
            StockNode ptr = stockVar[i];

            while (ptr != null) {
                if (ptr.getIngredient().getID() == ingredientID) {
                    return ptr;
                } else {
                    ptr = ptr.getNextStockNode();
                }

            }

        }
        return stockNode;
        // update the return value
    }

    /**
     * This method is to find an ingredient from StockVar (given the ingredient
     * name).
     * 
     * ** GIVEN METHOD **
     * ** DO NOT EDIT **
     * 
     * @param ingredientName - the name of the ingredient
     * @return the specific ingredient StockNode, null otherwise
     */

    public StockNode findStockNode(String ingredientName) {

        StockNode stockNode = null;

        for (int index = 0; index < stockVar.length; index++) {

            StockNode ptr = stockVar[index];

            while (ptr != null) {
                if (ptr.getIngredient().getName().equalsIgnoreCase(ingredientName)) {
                    return ptr;
                } else {
                    ptr = ptr.getNextStockNode();
                }
            }
        }
        return stockNode;
    }

    /**
     * This method updates the stock amount of an ingredient.
     * 
     * 1. you will be given the ingredientName **OR** the ingredientID:
     * a) the ingredientName is NOT null: find the ingredient and add the given
     * stock amount to the
     * current stock amount
     * b) the ingredientID is NOT -1: find the ingredient and add the given stock
     * amount to the
     * current stock amount
     * 
     * (FOR FUTURE USE) SOMETIMES THE STOCK AMOUNT TO ADD MAY BE NEGATIVE (TO REMOVE
     * STOCK)
     * 
     * @param ingredientName   - the name of the ingredient
     * @param ingredientID     - the id of the ingredient
     * @param stockAmountToAdd - the amount to add to the current stock amount
     */

    public void updateStock(String ingredientName, int ingredientID, int stockAmountToAdd) {

        // WRITE YOUR CODE HERE

        if (ingredientName == null) {
            StockNode find = findStockNode(ingredientID);
            int findStock = find.getIngredient().getStockLevel();
            find.getIngredient().updateStockLevel(stockAmountToAdd);

        }

        else if (ingredientID == -1) { // when if statement is false, else if statement is implemented. two if
                                       // statement are implemented even if first if stetement is implemented.
            StockNode find = findStockNode(ingredientName);
            int findStock = find.getIngredient().getStockLevel();
            // int newStock = findStock + stockAmountToAdd;
            find.getIngredient().updateStockLevel(stockAmountToAdd);

        }

    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a single ‘for’ loop? cuz i only have i’s for you*
     * 
     * ***********
     * This method goes over menuVar to update the price and profit of each dish,
     * using the stockVar hashtable to lookup for ingredient's costs.
     * 
     * 1. For each dish in menuVar, add up the cost for each ingredient (found in
     * stockVar),
     * and multiply the total by 1.2 to get the final price.
     * a) update the price of each dish
     * HINT! --> you can use the methods you've previously made!
     * 
     * 2. Calculate the profit of each dish by getting the totalPrice of ingredients
     * and subtracting from
     * the price of the dish itself.
     * 
     * @return void
     */

    public void updatePriceAndProfit() {

        // WRITE YOUR CODE HERE

        for (int i = 0; i < menuVar.length; i++) {

            MenuNode ptr = menuVar[i];
            while (ptr != null) {
                Dish dish = ptr.getDish();
                int stockId[] = dish.getStockID(); // this is the array of ingredientID. There are several numbers in
                                                   // this stockID array.
                // Thus, you want to iterate through the numbers in array and find the cost of
                // each of ingredients.

                Double totalCost = 0.0;
                for (int j = 0; j < stockId.length; j++) {
                    StockNode find = findStockNode(stockId[j]);
                    Ingredient ingredient = find.getIngredient();
                    Double cost = ingredient.getCost();

                    totalCost += cost;

                }

                Double price = totalCost * 1.2;
                Double profit = (price - totalCost);

                dish.setPriceOfDish(price);
                dish.setProfit(profit);

                ptr = ptr.getNextMenuNode();

            }

        }
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a decimal? cuz the thought of you
     * always floats in my head and the two of use would make double*
     * 
     * ************
     * This method initializes and populates stockVar which is a hashtable where
     * each index contains a
     * linked list with StockNodes.
     * 
     * 1. set and read the inputFile (stock.in):
     * a) first integer (on line 1) is the size of StockVar *** update stockVarSize
     * AND create the stockVar array ***
     * b) first integer of next line represents the ingredientID
     * i) example: 101 on line 2
     * c) use StdIn.readChar() to get rid of the space between the id and the name
     * d) the string that follows is the ingredient name (NOTE! --> there are spaces
     * between certain strings)
     * i) example: Lettuce
     * e) the double on the next line corresponds to the ingredient's cost
     * i) example: 3.12 on line 3
     * f) the next integer is the stock amount for that ingredient
     * i) example: 30 on line 3
     * 
     * 2. create a Ingredient object followed by a StockNode then add to stockVar
     * HINT! --> you may use previous methods written to help you!
     * 
     * @param inputFile - the input file with the ingredients and all their
     *                  information (stock.in)
     */

    public void createStockHashTable(String inputFile) {

        StdIn.setFile(inputFile); // opens inputFile to be read by StdIn

        // WRITE YOUR CODE HERE

        stockVarSize = StdIn.readInt();

        stockVar = new StockNode[stockVarSize];
        StdIn.readLine();

        while (StdIn.hasNextLine()) {

            for (int i = 0; i < stockVarSize; i++) {

                int ingredientId = StdIn.readInt();
                // System.out.println("ingredientID "+ ingredientId);
                StdIn.readChar();

                String ingredientName = StdIn.readLine();

                // System.out.println("ingredientName "+ ingredientName);
                Double ingredientPrice = StdIn.readDouble();

                int ingredientStock = StdIn.readInt();

                Ingredient ingredient = new Ingredient(ingredientId, ingredientName, ingredientStock, ingredientPrice);

                StockNode stockNode = new StockNode(ingredient, null);

                addStockNode(stockNode);
                // addStockNode(stockNode);

                if (StdIn.readLine() == null) {
                    break;
                }
            }

        }

    }

    /*
     * Transaction methods
     */

    /**
     * This method adds a TransactionNode to the END of the transactions linked
     * list.
     * The front of the list is transactionVar.
     *
     * 1. create a new TransactionNode with the TransactionData paramenter.
     * 2. add the TransactionNode at the end of the linked list transactionVar.
     * 
     * @param data - TransactionData node to be added to transactionVar
     */

    public void addTransactionNode(TransactionData data) { // method adds new transactionNode to the end of LL

        // WRITE YOUR CODE HERE

        TransactionNode transactionNode = new TransactionNode(data, null);

        TransactionNode ptr = transactionVar;

        if (transactionVar == null) {
            transactionVar = transactionNode;
        }

        else {
            while (ptr.getNext() != null) {
                ptr = ptr.getNext();

            }

            if (ptr.getNext() == null) {
                ptr.setNext(transactionNode);
            }

        }

    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you the break command? cuz everything else stops when I see you*
     * 
     * *************
     * This method checks if there's enough in stock to prepare a dish.
     * 
     * 1. use findDish() method to find the menuNode node for dishName
     * 
     * 2. retrieve the Dish, then traverse ingredient array within the Dish
     * 
     * 3. return boolean based on whether you can sell the dish or not
     * HINT! --> once you determine you can't sell the dish, break and return
     * 
     * @param dishName       - String of dish that's being requested
     * @param numberOfDishes - int of how many of that dish is being ordered
     * @return boolean
     */

    public boolean checkDishAvailability(String dishName, int numberOfDishes) {

        // WRITE YOUR CODE HERE
        MenuNode find = findDish(dishName);
        int stockId[] = find.getDish().getStockID();
        int stockLevel = 0; // I do not know this is the proper way to check the number outside of the loop
        for (int i = 0; i < stockId.length; i++) { // you traverse the array of stockId for the dish and you want to
                                                   // know the each ingrdient stock.
            StockNode stock = findStockNode(stockId[i]);
            stockLevel = stock.getIngredient().getStockLevel();
            if ((stockLevel < numberOfDishes)) {
                return false;
            }
        }
        return true;
        // update the return value
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *if you were a while loop and I were a boolean, we could run
     * together forever because I’ll always stay true to you*
     * 
     * ***************
     * This method simulates a customer ordering a dish. Use the
     * checkDishAvailability() method to check whether the dish can be ordered.
     * If the dish cannot be prepared
     * - create a TransactionData object of type "order" where the item is the
     * dishName, the amount is the quantity being ordered, and profit is 0 (zero).
     * - then add the transaction as an UNsuccessful transaction and,
     * - simulate the customer trying to order other dishes in the same category
     * linked list:
     * - if the dish that comes right after the dishName can be prepared, great. If
     * not, try the next one and so on.
     * - you might have to traverse through the entire category searching for a dish
     * that can be prepared. If you reach the end of the list, start from the
     * beginning until you have visited EVERY dish in the category.
     * - It is possible that no dish in the entire category can be prepared.
     * - Note: the next dish the customer chooses is always the one that comes right
     * after the one that could not be prepared.
     * 
     * @param dishName - String of dish that's been ordered
     * @param quantity - int of how many of that dish has been ordered
     */

    public void order(String dishName, int quantity) {

        // WRITE YOUR CODE HERE

        // dish = dish.getNextMenuNode();
        MenuNode first = findDish(dishName);
        MenuNode dish = first;

        if (checkDishAvailability(dishName, quantity)) {
            // MenuNode dish = findDish(dishName);
            Double profit = dish.getDish().getProfit() * quantity;

            TransactionData transactionData = new TransactionData("order", dishName, quantity, profit, true);

            addTransactionNode(transactionData);

            int ingredientId[] = dish.getDish().getStockID(); // now I get the array of id of dish. So there are some
                                                              // elements in this array.
            for (int i = 0; i < ingredientId.length; i++) {
                updateStock(null, ingredientId[i], -quantity);
            }
            return;
        }

        else { // if you say else, you already mention about checkDishaAvailability(dishName,
               // quantitity) == fasle

            // MenuNode dish = findDish(dishName);
            TransactionData transactionData = new TransactionData("order", dishName, quantity, 0, false);
            addTransactionNode(transactionData);

           
            

            do {

                // String dishName1 = dish.getDish().getName();
                // TransactionData transactionData1 = new TransactionData("order", dishName1,
                // quantity, 0, false);
                // addTransactionNode(transactionData1);

                // firstNode in menuVar

                // dish = dish.getNextMenuNode();

                if (dish.getNextMenuNode() == null) {
                    int indexofFirst = findCategoryIndex(dish.getDish().getCategory());
                    dish = menuVar[indexofFirst];
                   
                    }
                    
                
    
                else {
    
                    dish = dish.getNextMenuNode();
    
                }

                

                String dishName1 = dish.getDish().getName();
                
                if (checkDishAvailability(dishName1, quantity)) {

                    // String dishName1 = dish.getDish().getName();
                    Double profit = dish.getDish().getProfit() * quantity;
                    TransactionData transactionData2 = new TransactionData("order", dishName1, quantity, profit, true);
                    addTransactionNode(transactionData2);

                    int intgredientId[] = dish.getDish().getStockID();
                    for (int i = 0; i < intgredientId.length; i++) {
                        updateStock(null, intgredientId[i], -quantity);
                    }
                    return;
                }

                else {
                    // String dishName2 = dish.getDish().getName();
                    TransactionData transactionData3 = new TransactionData("order", dishName1, quantity, 0, false);
                    addTransactionNode(transactionData3);
                }
            }while (dish != first); 
            }
            
            }

            

            
        
            // int indexofFirst = findCategoryIndex(dish.getDish().getCategory());

            // dish = dish.getNextMenuNode();

        

    

    /**
     * 
     * This method returns the total profit for the day
     *
     * The profit is computed by traversing the transaction linked list
     * (transactionVar)
     * adding up all the profits for the day
     * 
     * @return profit - double value of the total profit for the day
     */

    public double profit() {

        // WRITE YOUR CODE HERE

        TransactionNode currentTransaction = transactionVar;
        double sum = 0;
        if (transactionVar == null) {
            return 0;
        }

        else {
            while (currentTransaction != null) {
                double profit = currentTransaction.getData().getProfit();

                sum += profit;
                currentTransaction = currentTransaction.getNext();
            }
        }

        return sum; // update the return value
    }

    /**
     * This method simulates donation requests, successful or not.
     * 
     * 1. check whether the profit is > 50 and whether there's enough ingredients in
     * stock.
     * 
     * 2. add transaction to transactionVar
     * 
     * @param ingredientName - String of ingredient that's been requested
     * @param quantity       - int of how many of that ingredient has been ordered
     * @return void
     * 
     */

    public void donation(String ingredientName, int quantity) {

        StockNode stockNode = findStockNode(ingredientName);
        int stock = stockNode.getIngredient().getStockLevel();

        double profit = profit();

        if (profit > 50.0 && stock >= quantity) {

            TransactionData transactionData = new TransactionData("donation", ingredientName, quantity, 0, true);

            addTransactionNode(transactionData);
            updateStock(ingredientName, -1, -quantity);
        }

        else {

            TransactionData transactionData = new TransactionData("donation", ingredientName, quantity, 0, false);

            addTransactionNode(transactionData);

        }
    }

    // WRITE YOUR CODE HERE

    /**
     * This method simulates restock orders
     * 
     * 1. check whether the profit is sufficient to pay for the total cost of
     * ingredient
     * a) (how much each ingredient costs) * (quantity)
     * b) if there is enough profit, adjust stock and profit accordingly
     * 
     * 2. add transaction to transactionVar
     * 
     * @param ingredientName - ingredient that's been requested
     * @param quantity       - how many of that ingredient needs to be ordered
     */

    public void restock(String ingredientName, int quantity) {

        // WRITE YOUR CODE HERE

        StockNode stockNode = findStockNode(ingredientName);
        Double costRestock = stockNode.getIngredient().getCost() * quantity;

        Double profit = profit();

        if (profit >= costRestock) {

            TransactionData transactionData = new TransactionData("restock", ingredientName, quantity, -(costRestock),true);
            addTransactionNode(transactionData);
            updateStock(ingredientName, -1, quantity);
        }

        else {

            TransactionData transactionData = new TransactionData("restock", ingredientName, quantity, 0, false);
            addTransactionNode(transactionData);
        }

    }

    /*
     * Seat guests/customers methods
     */

    /**
     * Method to populate tables (which is a 1D integer array) based upon input file
     * 
     * The input file is formatted as follows:
     * - an integer t contains the number of tables
     * - t lines containing number of rows * seats per row for each table
     * 
     * @param inputFile - tables1.in (contains all the tables in the RUHungry
     *                  restaurant)
     * @return void (aka nothing)
     */

    public void createTables(String inputFile) {

        StdIn.setFile(inputFile);

        int numberOfTables = StdIn.readInt();
        tableSeats = new int[numberOfTables];
        tables = new Party[numberOfTables];

        for (int t = 0; t < numberOfTables; t++) {
            tableSeats[t] = StdIn.readInt() * StdIn.readInt();
        }
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a linked list? cuz nothing could stock up to you and
     * you’re pretty queue(te)*
     * 
     * ***************
     * This method simulates seating guests at tables. You are guaranteed to be able
     * to sit everyone from the waitingQueue eventually.
     * 
     * 1. initialize a tables array for party that are currently sitting
     * 
     * 2. initialize leftQueueVar a Party queue that represents the people that have
     * left the restaurant
     * 
     * 3. while there are parties waiting to be sat:
     * - Starting from index 0 (zero), seat the next party in the first available
     * table that fits their party.
     * - If there is no available table for the next party, kick a party out from
     * the tables array:
     * 1. starting at index 0 (zero), find the first table big enough to hold the
     * next party in line.
     * 2. remove the current party, add them to the leftQueueVar.
     * 3. seat the next party in line.
     *
     * Parallel arrays: tableSeats[i] refers to tables[i]. If tableSeats[i] is 3
     * then a party with 3 or less people can sit at tables[i].
     * tableSeats contains the number of seats per table.
     * tables contains the Party object currently at the table.
     * 
     * Note: After everyone has been seated (waitingQueue is empty), remove all the
     * parties from tables and add then to the leftQueueVar.
     * 
     * @param waitingQueue - queue containing parties waiting to be seated (each
     *                     element in queue is a Party <-- you are given this
     *                     class!)
     */

    public void seatAllGuests(Queue<Party> waitingQueue) {

        // WRITE YOUR CODE HERE

    }

    /**
     * Prints all states of the restaurant.
     * 
     * Edit this method if you wish.
     */
    public void printRestaurant() {
        // 1. Print out menu
        StdOut.println("Menu:");
        if (categoryVar != null) {
            for (int i = 0; i < categoryVar.length; i++) {
                StdOut.print(categoryVar[i] + ":");
                StdOut.println();

                MenuNode ptr = menuVar[i];
                while (ptr != null) {
                    StdOut.print(ptr.getDish().getName() + "  Price: $" +
                            ((Math.round(ptr.getDish().getPriceOfDish() * 100.0)) / 100.0) + " Profit: $"
                            + ((Math.round(ptr.getDish().getProfit() * 100.0)) / 100.0));
                    StdOut.println();

                    ptr = ptr.getNextMenuNode();
                }
                StdOut.println();
            }
        } else {
            StdOut.println("Empty - categoryVar is null.");
        }
        // 2. Print out stock
        StdOut.println("Stock:");

        if (stockVar != null) {
            for (int i = 0; i < 10; i++) {
                StdOut.println("Index " + i);
                StockNode ptr = stockVar[i];
                while (ptr != null) {
                    StdOut.print(ptr.getIngredient().getName() + "  ID: " + ptr.getIngredient().getID() + " Price: " +
                            ((Math.round(ptr.getIngredient().getCost() * 100.0)) / 100.0) + " Stock Level: "
                            + ptr.getIngredient().getStockLevel());
                    StdOut.println();

                    ptr = ptr.getNextStockNode();
                }

                StdOut.println();
            }
        } else {
            StdOut.println("Empty - stockVar is null.");
        }
        // 3. Print out transactions
        StdOut.println("Transactions:");
        if (transactionVar != null) {
            TransactionNode ptr = transactionVar;
            int successes = 0;
            int failures = 0;
            while (ptr != null) {
                String type = ptr.getData().getType();
                String item = ptr.getData().getItem();
                int amount = ptr.getData().getAmount();
                double profit = ptr.getData().getProfit();
                boolean success = ptr.getData().getSuccess();
                if (success == true) {
                    successes += 1;
                } else if (success == false) {
                    failures += 1;
                }

                StdOut.println("Type: " + type + ", Name: " + item + ", Amount: " + amount + ", Profit: $"
                        + ((Math.round(profit * 100.0)) / 100.0) + ", Was it a Success? " + success);

                ptr = ptr.getNext();
            }
            StdOut.println("Total number of successful transactions: " + successes);
            StdOut.println("Total number of unsuccessful transactions: " + failures);
            StdOut.println("Total profit remaining: $" + ((Math.round(profit() * 100.0)) / 100.0));
        } else {
            StdOut.println("Empty - transactionVar is null.");
        }
        // 4. Print out tables
        StdOut.println("Tables and Parties:");
        restaurant.Queue<Party> leftQueue = leftQueueVar;
        if (leftQueueVar != null) {
            StdOut.println(("Parties in order of leaving:"));
            int counter = 0;
            while (!leftQueue.isEmpty()) {
                Party removed = leftQueue.dequeue();
                counter += 1;
                StdOut.println(counter + ": " + removed.getName());
            }
        } else {
            StdOut.println("Empty -- leftQueueVar is empty");
        }
    }
}
