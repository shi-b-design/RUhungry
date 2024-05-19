package restaurant;

import java.nio.Buffer;

/**
 * Use this class to test your Menu method. 
 * This class takes in two arguments:
 * - args[0] is the menu input file
 * - args[1] is the output file
 * 
 * This class:
 * - Reads the input and output file names from args
 * - Instantiates a new RUHungry object
 * - Calls the menu() method 
 * - Sets standard output to the output and prints the restaurant
 *   to that file
 * 
 * To run: java -cp bin restaurant.Menu menu.in menu.out
 * 
 */
public class Menu {
    public static void main(String[] args) {

	// 1. Read input files
	// Option to hardcode these values if you don't want to use the command line arguments
	   
        //String inputFile = "menu.in";
        //String outputFile = "menu.out";
        //StdOut.println("Input File" + inputFile);
        //StdOut.println("Output File" + outputFile);

        String inputFile = "menu.in"; // tell file called "menu.in"
        String outputFile = "menu.out";
        String inputFileStock = "stock.in";
        String outputFileStock = "stock.out";
        String orderInputFile = "order1.in";
        String orderOutputFile ="order1.out";
        String order2Input = "order2.in";
        String order2Output ="order2.out";
        String order3Input ="order3.in";
        String order3Output="order3.out";
        String donationInput ="donate3.in";
        String donationOutput = "donate3.out";
        String restockInput ="restock3.in";
        String restockOutput ="restock3.out";
        String transactionInput="transaction3.in";
        String transactionOutput ="transaction3.out";
        


	
        // 2. Instantiate an RUHungry object
        RUHungry rh = new RUHungry(); 
        

	// 3. Call the menu() method to read the menu
        rh.menu(inputFile);//here's the part of read
        
        rh.createStockHashTable(inputFileStock);
        rh.updatePriceAndProfit();
        readOrders(order3Input, rh);
        rh.profit();
        //readDonations(donationInput, rh); 
        //readRestock(restockInput, rh);
        //transactions(transactionInput, rh);
        //rh.updateStock("Penne Pasta", 110, 5);
        
        

        //rh.createStockHashTable(inputFileStock);
        
        

	// 4. Set output file
	// Option to remove this line if you want to print directly to the screen
        
        //StdOut.setFile(orderOutputFile);
        StdOut.setFile(order3Output);

        //StdOut.setFile(outputFileStock);

	// 5. Print restaurant
        rh.printRestaurant(); // it prints out everything


    }

    public static void readOrders (String fileName, RUHungry rh)
    {
        StdIn.setFile(fileName);
        
        int numOfOrders = StdIn.readInt();
        for (int  i = 0; i<numOfOrders; i++)
        {
                int amount = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();
                rh.order(item, amount);
        }
    
}

    public static void readDonations (String fileName, RUHungry rh)
    {
        StdIn.setFile(fileName);
        int numOfDonations = StdIn.readInt();
        for (int  i = 0; i< numOfDonations; i++)

        {

                
                int amount = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();
                rh.donation(item, amount);
        }
    }

    public static void readRestock (String fileName, RUHungry rh)
    {
        StdIn.setFile(fileName);
        int numOfRestock = Integer.parseInt(StdIn.readLine()); 
        for (int  i = 0; i<numOfRestock; i++)
        {
                int amount = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();
                rh.restock(item, amount);
        }
    }


    public static void readOrders2 (String dishName, int dishQuantity, RUHungry rh)
    {
        rh.order(dishName, dishQuantity);
    }


    public static void readDonations2 (String dishName, int dishQuantity, RUHungry rh)
    {
        
        rh.donation(dishName, dishQuantity);
        
    }


    public static void readRestock2 (String dishName, int dishQuantity, RUHungry rh)
    {
           
        rh.restock(dishName, dishQuantity);

    }

    public static void transactions (String fileName, RUHungry rh)
    {
        StdIn.setFile(fileName);
        int numOfTransactions = StdIn.readInt(); 
        StdIn.readLine();
        for (int i = 0; i<numOfTransactions; i++)
        {
                String type = StdIn.readString();
                StdIn.readChar();
                int amount = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();

                if(type.equals("order"))
                {
                        readOrders2(item, amount, rh);
                }
                else if (type.equals("donation"))
                {
                        readDonations2(item, amount, rh);
                }
                else if (type.equals("restock"))
                {
                        readRestock2(item, amount, rh);
                }
           }
        }




}
