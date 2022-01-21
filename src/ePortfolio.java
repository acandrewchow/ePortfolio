import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.*;
import java.util.HashMap;

public class ePortfolio {
    // Local Object Declartions and Constants
    Scanner sc = new Scanner(System.in);
    private static ArrayList<Investment> investmentList = new ArrayList<Investment>();
    private static HashMap<String,ArrayList<Integer>> map = new HashMap<String,ArrayList<Integer>>();
    final double commission = 9.99;
    final double redemptionFee = 45.00;

    // Prints the investments within the investmentlist
    public void printInvestments() {
        // traverse list to gather objets
        System.out.println(" === INVESTMENTS  ===");
        for (int i = 0; i < investmentList.size(); i++) {
            // print contents inside investment list
            System.out.println(investmentList.get(i));
            System.out.println("\n");
        }
    }

    /** 
     * @return ArrayList
     * Returns an arrayList 
     */
    // Used to copy the array list to other existing classes
    public ArrayList<Investment> getList() {
        return investmentList;
    }
    

   /** 
     * @param symbol first parameter
     * @param index second parameter
     * @param quantity  third parameter
     * @param price fourth parameter
     * This method is used for an existing stock. It calls upon
     * other methods to update the existing stock as well as the stock's book value
     */
    public void buyStock(String symbol, int index, double quantity, double price) {
        System.out.println("You bought " + quantity + " additional shares of " + symbol + " for $" + price*quantity);
        Investment stock = investmentList.get(index);
        // update total quantity with old
        quantity = quantity + stock.getQuantity();
        stock.updateInvestment(quantity, price);
        stock.existingBookValue();
    }
    
    /** 
     * @param symbol first parameter
     * @param name second parameter
     * @param quantity  third parameter
     * @param price fourth parameter
     * This method buys a new stock by creating a new stock object and adding
     * it to the array list
     */
    public void buyStock(String symbol, String name, double quantity, double price) {
        try {
            double bookValue = quantity * price + commission;
            Stock stock = new Stock(symbol, name, quantity, price, bookValue);
            investmentList.add(stock);
            System.out.println("You bought " + quantity + " shares of " + symbol + " for $" + bookValue);
            hashMapEntry(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
     /** 
     * @param symbol first parameter
     * @param index second parameter
     * @param quantity  third parameter
     * @param price fourth parameter
     * This method is used for an existing mutual fund. It calls upon
     * other methods to update the existing mutual fund as well as the stock's book value
     */
    public void buyMutualFund(String symbol, int index, double quantity, double price) {
        System.out.println("You bought " + quantity + " additional shares of " + symbol + " for $" + price*quantity);
        Investment mutualFund = investmentList.get(index);
        // update total quantity with old
        quantity = quantity + mutualFund.getQuantity();
        mutualFund.updateInvestment(quantity, price);
        mutualFund.existingBookValue();
    }

    /** 
    * @param symbol first parameter
    * @param name second parameter
    * @param quantity  third parameter
    * @param price fourth parameter
    * This method buys a new mutual fund by creating a new mutual fund object and adding
    * it to the array list
    */
    public void buyMutualFund(String symbol, String name, double quantity, double price) {
        try {
        double bookValue = quantity * price;
        MutualFund mutualFund = new MutualFund(symbol, name, quantity, price, bookValue);
        mutualFund.existingBookValue();
        investmentList.add(mutualFund);
        System.out.println("You bought " + quantity + " units of " + symbol + " for $" + price*quantity);
        hashMapEntry(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /** 
     * @param symbol - first parameter
     * @return int
     * Returns the index where the symbol is found in the investment list
     */
    public int getInvestmentIndex(String symbol) {
        for (int i = 0; i < investmentList.size(); i++) {
            Investment currentInvestment = investmentList.get(i);
            if (currentInvestment.getSymbol().equalsIgnoreCase(symbol)) {
                // return symbol found at index
                return i;
            }
        }
        // if not found
        return -1;
    }

    /** 
     * @param name - first parameter
     * @return int
     * Returns the index where the name is found in the investment list
     */
    public int getInvestmentIndexName(String name) {
        for (int i = 0; i < investmentList.size(); i++) {
            Investment currentInvestment = investmentList.get(i);
            if (currentInvestment.getName().equalsIgnoreCase(name)) {
                // return name found at index
                return i;
            }
        }
        // if not found
        return -1;
    }

    

     /** 
     * @param symbol first parameter
     * @param index second parameter
     * @param quantity third parameter
     * @param price fourth parameter
     * This method sells a stock which is dependent on the user's input for
     * quantity, there are 3 cases
     */
    public void sellStock(String symbol, int index, double quantity, double price) {
        // receive index at current position
        Investment stock = investmentList.get(index);

        if (stock.getQuantity() < quantity) {
            System.out.println("\nCANNOT SELL STOCK, QUANTITY TOO HIGH!\n");
        }
        else if (stock.getQuantity() == quantity) {
            // update hash before investmentlist
            String name = stock.getName();
            hashMapUpdate(name);
            investmentList.remove(index);
            System.out.println("Sold all shares of " + symbol + " for $" + price*quantity);
        }
        else {
            double updatedQuantity = stock.getQuantity() - quantity;
            double payment = quantity * price - commission;
            stock.sellBookValue(quantity);
            stock.updateInvestment(updatedQuantity, price);
            // output money sold to user
            System.out.println("You have sold " + quantity + " shares of " + symbol + " at $ " + price + " each ");
            System.out.println("Money Received: $" + payment);
        }
    }

   /** 
     * @param symbol first parameter
     * @param index second parameter
     * @param quantity third parameter
     * @param price fourth parameter
     * This method sells a mutual fund which is dependent on the user's input for
     * quantity, there are 3 cases
     */
    public void sellMutualFund(String symbol, int index, double quantity, double price) {

        Investment mutualFund = investmentList.get(index);

        if (mutualFund.getQuantity() < quantity) {
            System.out.println("\nCANNOT SELL STOCK, QUANTITY TOO HIGH!\n");
        }
        // remove the investment if fully sell
        else if (mutualFund.getQuantity() == quantity) {
            String name = mutualFund.getName();
            hashMapUpdate(name);
            investmentList.remove(index);
            System.out.println("Sold all units of " + symbol + " for $" + price*quantity);
        }
        else {
            double updatedQuantity = mutualFund.getQuantity() - quantity;
            double payment = quantity * price - redemptionFee;
            mutualFund.sellBookValue(quantity);
            mutualFund.updateInvestment(updatedQuantity, price);
            // output the money gained to the user
            System.out.println("You have sold " + quantity + " units of " + symbol + " at $" + price + " each ");
            System.out.println("Money Received: $" + payment);
        }
    }
    // Updates all investements in the array list
    public void updateInvestments() {
        for (int i = 0; i < investmentList.size(); i++) {
            // traverse through the list to grab objects
            Investment investment = investmentList.get(i);
            String symbol = investment.getSymbol();
            System.out.print("Enter new price for " + symbol + ": ");
            double price = sc.nextDouble();
            investment.updateInvestmentPrice(price);
        }
    }

    /** 
     * @return double
     * returns the total gain for stocks and mutual funds
     */
    // calculate the total gains from the investments
    public double getGain() {
        double gains = 0;
        double stockGains = 0;
        double mutualGains = 0;
    
        // Loop through investment and compute gain
        for (Investment investment: investmentList) {
            if (investment instanceof Stock) {  
                stockGains = investment.calculateGain();
            }
            else if (investment instanceof MutualFund) {
                mutualGains = investment.calculateGain();
            }
        }
        gains = mutualGains + stockGains;
        System.out.printf("Total gains across all investments: $%.2f", gains);
        System.out.println("\n");
        return gains;
    }
    
    /** 
     * @param type - first parameter
     * @param symbol - second parameter
     * @param name - second parameter
     * @param quantity - third parameter
     * @param price -  fourth parameter
     * @param bookValue - fifth parameter
     * This method creates a new object or mutual fund based on the values passed from main which are parsed
     * from a textfile given at command line, In addition it also hashes indexes to each investment to a hashmap
     */
    public void loadInvestment(String type, String symbol, String name, double quantity, double price, double bookValue) {
        if (type.equalsIgnoreCase("stock")) {
            try {
            // creates new stock object and adds to investment list
            Stock stock = new Stock(symbol, name, quantity, price, bookValue);
            investmentList.add(stock);
            hashMapEntry(name);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else if (type.equalsIgnoreCase("mutualfund"))  {
            try {
            // creates new mutual fund object and adds to investment list
            MutualFund mutualFund = new MutualFund(symbol, name, quantity, price, bookValue);
            investmentList.add(mutualFund);
            hashMapEntry(name);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    /** 
     * @param args - first parameter
     * uses the command line arguments to output the corresponding file
     */
    public void outputInvestments(String args []) {
        // declare file to output, given at command line
        String fileName = args[0];
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            for (int i = 0; i < investmentList.size(); i++) {
                // receives the data inside the investment list
                Investment investments = investmentList.get(i);
                // writes to textfile
                writer.write(investments.printFile());
            }
            System.out.println("Output Successful! File contents are now located in " + args[0]);
            writer.close();
        } catch (Exception ex) {
            System.out.println("Cannot output to file");
        }
    }
   
     /** 
     * @param symbol first parameter
     * @param name second parameter
     * @param startPrice third parameter
     * @param endPrice fourth parameter
     * This method creates two new arrayLists that are used
     * to duplicate contents of the existing arraylists for investments.
     * The method will then loop through all possible combinations and filter
     * stocks and mutual funds based on the userInput. Once all searches
     * have been completed, it will print investments that
     * match the criteria entered by the user
     */
    public void search(String symbol, String name, double startPrice, double endPrice) {
        ArrayList<Investment> filteredInvestments = new ArrayList<Investment>();

        if (name.length() > 0) {
            System.out.println("=== Searching by name ===");
            // Check for invalid keywords
            if (containsInvalidKeywords(name)) {
                System.out.println("No results found.");
            }

            // Add each investment to filteredInvestments
            ArrayList<Integer> intersectingPositions = getIntersectingPositions(name);

            System.out.println("intersectingPositions: " + intersectingPositions);

            // Loop through the existing positions to add the corresponding investment to the search list
            for (int position: intersectingPositions) {
                Investment investment = investmentList.get(position);
                filteredInvestments.add(investment);
            }
        } else {
            // adds all stock and mutual fund objects into filtered array list
            for (Investment stock: investmentList) {
                filteredInvestments.add(stock);
            }
            for (Investment mutualFund: investmentList) {
                filteredInvestments.add(mutualFund);
            }
        }

        // if symbol exists
        if (symbol.length() > 0)  {
            for (Investment currentInvestment : investmentList) {
                if (!currentInvestment.getSymbol().equalsIgnoreCase(symbol)) {
                    filteredInvestments.remove(currentInvestment);
                }
            }
        }
        
        for (Investment currentInvestment : investmentList) {
            double investmentPrice = currentInvestment.getPrice();
            // Check if price is in the range
            if (investmentPrice <= startPrice || investmentPrice >= endPrice) {
                filteredInvestments.remove(currentInvestment);
            }
        }

        // Print out the filtered investments - stocks and mutual funds
        System.out.println("=== Filtered Investments ===");
        for (Investment currentInvestment: filteredInvestments) {
            System.out.println(currentInvestment);
        }   
    }

    
    /** 
     * @param name
     * @return boolean
     * checks if the map contains key words
     */
    private boolean containsInvalidKeywords(String name) {
        // parse the key words
        String [] keyWords = (name.toLowerCase().split(" "));

        for (int i = 0; i < keyWords.length; i++) {
            if (!map.containsKey(keyWords[i])) {
                return true;
            }
        }
        return false;
    }

    
    /** 
     * @param name
     * @return ArrayList<Integer>
     * returns an arraylist that holds the intersecting indexes used
     * to perform search
     */
    private ArrayList<Integer> getIntersectingPositions(String name) {
        String [] keyWords = (name.toLowerCase().split(" "));

        if (keyWords.length == 1) {
            return map.get(keyWords[0]);
        }

        // Store keyword ArrayLists
        ArrayList<ArrayList<Integer>> listOfLists = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < keyWords.length; i++) {
            listOfLists.add(map.get(keyWords[i]));
        }

        // Clone first nested list into commonItems
        ArrayList<Integer> commonItems = new ArrayList<Integer>();
        for (int position : listOfLists.get(0)) {
            commonItems.add(position);
        }
        // Create the intersection set
        for (int i = 1; i < listOfLists.size(); i++) {
            commonItems.retainAll(listOfLists.get(i));
        }
        // return the interection of common points
        return commonItems;
    }

    /** 
     * @param name - first parameter
     * This function will create an index for each investment in the investment list
     */
    public void hashMapEntry(String name) {
        // receive index in the arrayList
        int investmentIndex = getInvestmentIndexName(name);
        // tokenize words
        String [] words = (name.toLowerCase().split(" "));
        String word;
        ArrayList<Integer> entry = map.get(name);

        // Loop for the amount of keywords
        for(int i = 0; i < words.length; i++){
            // check each word
            word = words[i];
            // if exists
            if (map.containsKey(word)){
                entry = map.get(word);
                entry.add(investmentIndex);
            }
            else {
                // if it doesn't exist
                entry = new ArrayList<>();
                entry.add(investmentIndex);
                map.put(word, entry);
            }
        }
        System.out.println(" === INDEX ADDED === ");
        System.out.println(map);
    }
    
    /** 
     * @param investmentName - first parameter
     * This method will remove the investment located at the index in the investment list
     * Then, it will decrement all indices by one located after the index
     */
    public void hashMapUpdate(String investmentName) {
        // receive the position of the corresponding investment
        int investmentPosition = getInvestmentIndexName(investmentName);
    
        // Loop through all ArrayLists in the map
        for (String key: map.keySet()) {
            ArrayList<Integer> positions = map.get(key);
            
            // Remove the position of the investment for the current keyword
            int positionToRemove = positions.indexOf(investmentPosition);

            if (positionToRemove != -1) {
                positions.remove(positionToRemove);
            }
            // For every number greater than that position, decrease by 1
            for (int j = 0; j < positions.size(); j++) {
                int currentPosition = positions.get(j);
                if (currentPosition > investmentPosition) {
                    positions.set(j, currentPosition - 1);
                }
            }
            // Update the hash map with the updated ArrayList
            map.put(key, positions);
        }
        System.out.println("Removed HashMap Index!");
    }
}




