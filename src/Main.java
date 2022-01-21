import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

public class Main {
    // Object declarations
    private static ePortfolio portfolio = new ePortfolio();
    private static Scanner sc = new Scanner(System.in);
    private static String[] savedArgs;
  
    /** 
     @param args - Command line strings
     used at command line
     */
    public static void main (String args []) {
        // Create a new Gui to display the menu
        Gui gui = new Gui("ePortfolio");
        gui.setVisible(true);
        savedArgs = args;
        // Loads file contents when the program is run
        if (args.length == 1) {
            // loads given file at command line
            loadFile(args);
        }
        // Keep prompting for user input
        printMenu(args);
    }
   
    // Buying a Stock
    private static void buyStock() {
        // Ask user for information
        System.out.print("Enter the stock symbol: ");
        String symbol = sc.nextLine();

        System.out.print("Enter the quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        int investmentIndex = portfolio.getInvestmentIndex(symbol);

        // if doesnt't exist 
        if (investmentIndex == -1) {
          System.out.print("Enter the stock name: ");
          String name = sc.nextLine();
          portfolio.buyStock(symbol, name, quantity, price);
        }
        else if (investmentIndex != -1) {
          // if same symbol exists
          portfolio.buyStock(symbol, investmentIndex, quantity, price);
        }
    }
    // Buying a mutual fund
    private static void buyMutualFund() {
        // Ask user for information
        System.out.print("Enter the mutual fund symbol: ");
        String symbol = sc.nextLine();

        System.out.print("Enter the quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        int investmentIndex = portfolio.getInvestmentIndex(symbol);

        // if doesn't exist
        if (investmentIndex == -1) {
          System.out.print("Enter the mutual fund name: ");
          String name = sc.nextLine();
          portfolio.buyMutualFund(symbol, name, quantity, price);
        } 
        else if (investmentIndex != -1) {
            // if same symbol exists 
            portfolio.buyMutualFund(symbol, investmentIndex, quantity, price);
        }
    }

    // Sell a stock
    private static void sellStock () {
        // Ask user for inputs
        System.out.print("Enter the stock symbol: ");
        String symbol = sc.nextLine();

        System.out.print("Enter the quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        int investmentIndex = portfolio.getInvestmentIndex(symbol);

        // check for stock availability
        if (investmentIndex == -1) {
          System.out.println("Stock doesn't exist!");
        } 
        else {
          // sell if found
          portfolio.sellStock(symbol, investmentIndex, quantity, price);
        }
    }
    // Sell a mutual fund
    private static void sellMutualFund () {
        // Ask user for inputs
        System.out.print("Enter the mutual symbol: ");
        String symbol = sc.nextLine();

        System.out.print("Enter the quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        int investmentIndex = portfolio.getInvestmentIndex(symbol);

        //  check for mutual fund availability 
        if (investmentIndex == -1) {
          System.out.println("Mutual fund Doesn't exist! ");
        } 
        else {
          // sell if found
          portfolio.sellMutualFund(symbol, investmentIndex, quantity, price);
        }
    }

    // Updates the investments given by the user
    private static void updatePortfolio() {
        portfolio.updateInvestments();
    }
    
    // Computes total gain from the investments
    private static void gainPortfolio() {
        portfolio.getGain();
     
    }
    // Performs a search with the given inputs
    public static void searchRequest() {
        
        // Ask the user for information
        System.out.print("Enter the symbol: ");
        String symbol = sc.nextLine();
  
        System.out.print("Enter the name: ");
        String name = sc.nextLine();
  
        System.out.print("Enter the price: ");
        String price = sc.nextLine();

        // set prices
        double minPrice = 0;
        double maxPrice = Double.MAX_VALUE;

        // Check for 4 specific cases for prices 
        if (price.endsWith("-")) {
            // parse from beginning up to the dash
            price = price.substring(0, price.length() - 1);
            minPrice = Double.parseDouble(price);
        }
        else if (price.startsWith("-")) {
            // parse from min value to end
            price = price.substring(1); 
            maxPrice = Double.parseDouble(price);
        }
        else if (price.contains("-")) {
            // Split the range into two variables
            String temp[] = price.split("-");
            String tempOne = temp[0];
            String tempTwo = temp[1];
            minPrice = Double.parseDouble(tempOne);
            maxPrice = Double.parseDouble(tempTwo);
        } 
        else if (!price.isEmpty()) {
            // change to default price
            minPrice = Double.parseDouble(price);
            maxPrice = Double.parseDouble(price);
        }
        // Search the portfolio given the userInputs
        portfolio.search(symbol, name.toLowerCase(), minPrice, maxPrice);
    }
    
    /** 
     * @param args 0 first parameter for command line arguments
     *  are the command line arguments given by the user at run time
     */
    public static void loadFile(String args []) {
        // temp arraylist to hold parsed lines
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        
        try {
            // Read file given argc 
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));

            // Scans contents of file
            while((line = input.readLine()) != null){

                // Remove the quotation marks
                String str = line.replace("\"","");
                // Removes content before "="
                String info = str.substring(str.indexOf("=") + 1);
                // add the elements to the array list
                lines.add(info.trim());
            }
            input.close();
        } 
        // Error check and see if file can be opened
        catch (FileNotFoundException e) {
            System.out.println("Cannot open file.");
            printMenu(args);
        }
        catch (IOException e) {
            // Continue with program if file does not exist
            System.out.println("=== Cannot open file ===");
            printMenu(args);
        }

        // Loop through ArrayList
        int index = 0;
        while (lines.size() > index) {
            // assign attributes to each variable
            String type = lines.get(index);
            index++;
            String symbol = lines.get(index);
            index++;
            String name = lines.get(index);
            index++;
            double quantity = Double.parseDouble(lines.get(index));
            index++;
            double price = Double.parseDouble(lines.get(index));
            index++;
            double bookValue = Double.parseDouble(lines.get(index));
            index++;
            // increment for new line
            index++;
            // pass the contents of the investment to the method to create the corresponding investment
            portfolio.loadInvestment(type, symbol, name, quantity, price, bookValue);
        }
    }
    /** 
     * @param args - first parameter
     * calls upon portfolio method to output file contents
     */
    public static void outputFile(String args []) {
        portfolio.outputInvestments(args);
    }

    /** 
     * @return String[]
     * Returns the command line arguments
     */
    public static String[] getArgs() {
        return savedArgs;
    }
    /** 
     * @param args - first parameter
     * command line arguments given at run time
     */
    public static void printMenu(String args []) {

        boolean counter = true;
        String menuInput = "";
        System.out.println("\t\t\t\tWelcome to your ePortfolio!");
        System.out.println("\t\t\t\tWhat would you like to do today?");
        
        while (counter) {
            // Menu options
            System.out.println("=====================================================================================================\n");
            System.out.println("\t\tBuy: own an investment or add more quantity to an existing investment");
            System.out.println("\t\tSell: reduce some quantity of an existing investment");
            System.out.println("\t\tUpdate: refresh the prices of all existing investments");
            System.out.println("\t\tgetGain: compute the total gain of portfolio");
            System.out.println("\t\tSearch: find all investments in a search request");
            System.out.println("\t\tView Portfolio: View existing investments; Stocks or Mutual Funds");
            System.out.println("\t\tOutput Portfolio: Output investments to a file");
            System.out.println("\t\tExit: Enter Quit or Q to exit the program\n");
            System.out.println("=====================================================================================================");
            
            System.out.print("Enter an option: ");
            menuInput = sc.nextLine();
          
            if (menuInput.equalsIgnoreCase("Buy")) {
                String input = "";
                System.out.print("Enter the investment: (Stock or Mutual Fund) ");
                input = sc.nextLine();

                if (input.equalsIgnoreCase("Stock") || (input.equalsIgnoreCase("Stocks"))) {
                    buyStock();
                }
                else if ((input.equalsIgnoreCase("MutualFund")) || (input.equalsIgnoreCase("Mutual")) || (input.equalsIgnoreCase("MutualFunds")) || (input.equalsIgnoreCase("Mutuals"))) {
                    buyMutualFund();
                }
            }
            else if (menuInput.equalsIgnoreCase("Sell")) {
                
                String input = "";
                System.out.print("Enter the investment: (Stock or Mutual Fund) ");
                input = sc.nextLine();

                if (input.equalsIgnoreCase("Stock") || (input.equalsIgnoreCase("Stocks"))) {
                    sellStock();
                }
                else if ((input.equalsIgnoreCase("MutualFund")) || (input.equalsIgnoreCase("Mutual")) || (input.equalsIgnoreCase("Mutual Fund"))){
                    sellMutualFund();
                }
            }   
            else if (menuInput.equalsIgnoreCase("Update") || (menuInput.equalsIgnoreCase("Updates"))) {
                updatePortfolio();
            }
            else if (menuInput.equalsIgnoreCase("getGain") || (menuInput.equalsIgnoreCase("Gain"))) {
                gainPortfolio();
            }
            else if (menuInput.equalsIgnoreCase("Search")) {
                searchRequest();
            }
            else if (menuInput.equalsIgnoreCase("output")) {
                outputFile(args);
            }
            else if ((menuInput.equalsIgnoreCase("Quit")) || (menuInput.equalsIgnoreCase("Q")) || (menuInput.equalsIgnoreCase("Exit"))) {

                System.out.println("Exiting program\n");
                System.out.println("Have a great day!");
                // output file each time user prompts to quit
                outputFile(args);
                System.exit(1);
            }
            else {
                System.out.println("Invalid Input! try again\n");
            }
        }
    }
    
}



    
