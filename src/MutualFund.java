// child class 
public class MutualFund extends Investment {
    final double redemptionFee = 45.00;
    private String type = "mutualfund";

    // Copy Constructor for mutual funds
    public MutualFund(Stock copyMutualFund) {
        super(copyMutualFund.getSymbol(), copyMutualFund.getName(), copyMutualFund.getQuantity(), copyMutualFund.getPrice(), copyMutualFund.getBookValue());
    }
    // Mutual Constructor
    public MutualFund (String symbol, String name, double quantity, double price, double bookValue) {
        super(symbol, name, quantity, price, bookValue);
    }
    // Methods that are unique to subclass - MutuaFund
    @Override
    // Calculates book value
    public void bookValue() {
        this.bookValue = quantity * price;
    }
    
    /** 
     * @return double
     * Returns the value for calculating gain of a mutual fund
     */
    @Override
    // Calculates the gain for mutual funds
    public double calculateGain() {
        double gain = (price * quantity - redemptionFee) - bookValue;
        return gain;
    }
    @Override
    // Calculates book value for a mutual fund
    public void existingBookValue() {
        this.bookValue = quantity * price;
    }
    
    /** 
     * @return String
     * Returns string format of a mutual fund used for file output
     */
    @Override
    public String printFile() {
        return "type = \"" + type + "\"\"\nsymbol = \"" + symbol + "\"\nname = \"" + name + "\"\nquantity = \"" + quantity + "\"\nprice = \"" + price + "\"\nbookValue = \"" + bookValue + "\"\n" + "\n";
        
    }
    
}