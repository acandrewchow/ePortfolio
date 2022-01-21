// child class
public class Stock extends Investment {
    final double commission = 9.99;
    private String type = "stock";

    // Copy Constructor for Stocks
    public Stock(Stock copyStock) {
        super(copyStock.getSymbol(), copyStock.getName(), copyStock.getQuantity(), copyStock.getPrice(), copyStock.getBookValue());
    }
    // Stock Constructor
    public Stock (String symbol, String name, double quantity, double price, double bookValue) {
        super(symbol, name, quantity, price, bookValue);
    }
    // Methods that are unique to subclass - Stock
    @Override
    // Calculates book value of a stock
    public void bookValue() {
        this.bookValue = quantity * price + commission;
    }
    
    /** 
     * @return double
     * Returns the gain value for a stock 
     */
    // Calculates gain for stocks
    @Override
    public double calculateGain() {
        double gain = (price * quantity - commission) - bookValue;
        return gain;
    }
    @Override
    // Calculates book value for a stock
    public void existingBookValue() {
        this.bookValue = quantity * price + commission;
    }
    
    /** 
     * @return String
     * Returns the string format for a stock used for file output
     */
    @Override
    public String printFile() {
        return "type = \"" + type + "\"\"\nsymbol = \"" + symbol + "\"\nname = \"" + name + "\"\nquantity = \"" + quantity + "\"\nprice = \"" + price + "\"\nbookValue = \"" + bookValue + "\"\n" + "\n";
    }
}