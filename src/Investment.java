// Super class
abstract class Investment extends Object{
    protected String symbol;
    protected String name;
    protected double quantity;
    protected double price;
    protected double bookValue;
    protected String type;
    protected int count;
    // Super Class constructor
    public Investment(String symbol, String name, double quantity, double price, double bookValue) {
        // Validation Check
        if ((symbol == null) || (name == null) || (quantity < 0) || (price < 0) || (bookValue < 0)) {
            throw new RuntimeException ("Invalid Input - Cannot create Investment!\n");
        }
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = bookValue;
    }
     
    // Abstract Methods 
    public abstract void bookValue();
    public abstract double calculateGain();
    public abstract void existingBookValue();
  
    /** 
     * @param amountToSell - first parameter
     * Performs a sell transaction and updates the current investment's book value
     */
    public void sellBookValue(double amountToSell) {
        double remainingQuantity = quantity - amountToSell;
        this.bookValue = bookValue * (remainingQuantity / quantity);
    }   
    
    /** 
     * @param newPrice - first parameter
     * Updates the investment's price
     */
    public void updateInvestmentPrice(double newPrice) {
        this.price = newPrice;
    }
    
   
    /** 
     * @param newQuantity - first parameter
     * @param newPrice - second parameter
     * Updates the investment's quantity and price
     */
    public void updateInvestment(double newQuantity, double newPrice) {
        this.quantity = newQuantity;
        this.price = newPrice;
    }
    
    /** 
     * @return String
     * Returns the symbol of an investment stock or mutual fund
     */
    // getters
    public String getSymbol() {
        if (symbol == null) {
            throw new RuntimeException ("Symbol cannot be empty");
        }
        return this.symbol;
    }

    /** 
     * @return String
     * Returns the name of an investment stock or mutual fund
     */
    public String getName() {
        if (name == null) {
            throw new RuntimeException ("Name cannot be empty");
        }
        return this.name;
    }
    
    /** 
     * @return double
     * Returns the quantity of an investment stock or mutual fund
     */
    public double getQuantity() {
        if (quantity < 0) {
            throw new RuntimeException ("Quantity cannot be negative");
        }
        return this.quantity;
    }
    
    /** 
     * @return double
     * Returns the price of an investment stock or mutual fund
     */
    public double getPrice() {
        if (price < 0) {
            throw new RuntimeException ("Quantity cannot be negative");
        }
        return this.price;
    }
    
    /** 
     * @return double
     * Returns the bookvalue of an investment stock or mutual fund
     */
    public double getBookValue() {
        if (bookValue < 0) {
            throw new RuntimeException("book value cannot be negative");
        }
        return this.bookValue;
    }
    
    /** 
     * @param otherInvestment represents the other investment beign compared
     * @return boolean
     * Returns true or false if the object is the same or not
     */
    public boolean equals(Investment otherInvestment) {
        if (otherInvestment.getSymbol().equalsIgnoreCase(symbol)) {
            return true;
        } else {
            return false;
        }
    }
    
    /** 
     * @return String
     * Returns string formatted used when outputting to files
     */
    public String printFile() {
        return "type = \"" + type + "\"\"\nsymbol = \"" + symbol + "\"\nname = \"" + name + "\"\nquantity = \"" + quantity + "\"\nprice = \"" + price + "\"\nbookValue = \"" + bookValue + "\"\n" + "\n";
    }
    
    /** 
     * @return String
     * Returns string values for each attribute of either a stock or mutual fund
     */
    public String toString() {
        return "\nSymbol: " + symbol +  " \nName: " + name +  " \nQuantity: " + quantity + "\nPrice: $"  + price +  " \nBook Value: $" + bookValue;
    }
}