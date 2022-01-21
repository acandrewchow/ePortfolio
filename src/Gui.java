import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Gui extends JFrame implements ActionListener {
    // Object class declarations
    private static ePortfolio portfolio = new ePortfolio();
    private static ArrayList<Investment> newinvestmentList = new ArrayList<Investment>();
    private Container contentPane;
    private int counter;
    final double commission = 9.99;
    final double redemptionFee = 45.00;
 
    // Constructor
    public Gui() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prepareGui();
        newinvestmentList = portfolio.getList();
    }
    // Overload Constructor
    public Gui(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prepareGui();
        newinvestmentList = portfolio.getList();
    }
    // Prepares the Gui for the user
    private void prepareGui() {
        contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        setSize(500, 550);
        setResizable(false);
       
        // Prepare the user interface 
        JMenuBar bar = new JMenuBar();
        JMenu commands = new JMenu("Commands");
        JMenuItem buyInvestments = new JMenuItem("Buy an Investment");
        JMenuItem sellInvestments = new JMenuItem("Sell an Investment");
        JMenuItem updateInvestments = new JMenuItem("Update Investments");
        JMenuItem totalGain = new JMenuItem("Total Gain");
        JMenuItem searchInvestments = new JMenuItem("Search for an Investment");
        JMenuItem exitOption = new JMenuItem("Exit Program");
        JLabel welcome = new JLabel("<html> Welcome to ePortfolio!<br/> Choose a command from the menu to get started <br/><br/> Buy an investment <br/> Sell an investment <br/>< Update Investments <br/>< Calculate total gain <br/>< Search for Investments</html>", JLabel.CENTER);
        welcome.setFont(new Font("Verdana", Font.PLAIN, 18));

        // Adding options to the menu bar
        commands.add(buyInvestments);
        commands.add(sellInvestments);  
        commands.add(updateInvestments);
        commands.add(totalGain);
        commands.add(searchInvestments);
        commands.add(exitOption);
        
        // Add action Listeners for the constructor
        buyInvestments.addActionListener(this);
        sellInvestments.addActionListener(this);
        updateInvestments.addActionListener(this);
        totalGain.addActionListener(this);
        searchInvestments.addActionListener(this);
        exitOption.addActionListener(this);
       
        // Prepare menubar
        bar.add(commands);
        setJMenuBar(bar);
        contentPane.add(welcome);
    }
    public void showBuyWindow() {
        // Reset Pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();
        contentPane.setLayout(null);

        // Create Combo Box
        String [] investments = {"Stock",  "Mutual Fund"};
        JComboBox<String>investmentType = new JComboBox<String>(investments);
        
        // Labels
        JLabel messageTitle = new JLabel("Messages");
        JLabel windowTitle = new JLabel("Buying an Investment");
        JLabel typeLabel =  new JLabel("Type");
        JLabel symbolLabel = new JLabel("Symbol");
        JLabel nameLabel = new JLabel("Name");
        JLabel quantityLabel = new JLabel("Quantity");
        JLabel priceLabel = new JLabel("Price");

        // Text Fields
        JTextField nameField = new JTextField();
        JTextField symbolField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField();

        // Buttons
        JButton resetButton = new JButton("Reset");
        JButton buyButton = new JButton("Buy");
        JTextArea outputTextArea = new JTextArea();

        // Scrol Pane Customization
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());

        // Setting Dimensions
        typeLabel.setBounds(10, 75, 50, 50);
        investmentType.setBounds(90, 90, 150, 20);
        windowTitle.setBounds(10, 10, 200, 100);
        windowTitle.setFont(new Font("Verdana", Font.PLAIN, 18));

        symbolLabel.setBounds(10, 125, 50, 50);
        nameLabel.setBounds(10, 165, 50, 50);
        quantityLabel.setBounds(10, 205, 75, 50);
        priceLabel.setBounds(10, 245, 50, 50);

        symbolField.setBounds(90, 140, 150, 20);
        nameField.setBounds(90, 180, 150, 20);
        quantityField.setBounds(90, 220, 150, 20);
        priceField.setBounds(90, 260, 150, 20);

        outputScrollPane.setBounds(15, 325, 450, 135);
        messageTitle.setBounds(10, 280, 100, 50);
        
        resetButton.setBounds(350, 100, 100, 50);
        buyButton.setBounds(350, 200, 100, 50);

        // Adding Components
        contentPane.add(buyButton);
        contentPane.add(resetButton);
        contentPane.add(messageTitle);
        contentPane.add(outputScrollPane);
        contentPane.add(typeLabel);
        contentPane.add(windowTitle);
        contentPane.add(investmentType);
        contentPane.add(nameLabel);
        contentPane.add(nameField);
        contentPane.add(symbolLabel);
        contentPane.add(symbolField);
        contentPane.add(quantityLabel);
        contentPane.add(quantityField);
        contentPane.add(priceLabel);
        contentPane.add(priceField);

        // Buys an Investment or an existing investment
        buyButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    String symbol = symbolField.getText();
                    String name = nameField.getText();
                    String quantity = quantityField.getText();
                    String price = priceField.getText();
                    double newQuantity;
                    double newPrice;
                    String type = (String) investmentType.getSelectedItem();
                    int index = portfolio.getInvestmentIndex(symbol);
                    try {
                        if (symbol.length() == 0)  {
                            throw new Exception("Symbol cannot be empty");
                        }
                        if (name.length() == 0) {
                            throw new Exception("name cannot be empty");
                        }
                        if (quantity.length() == 0) {
                            throw new Exception("Quantity cannot be empty");
                        } else {
                            try {
                                newQuantity = Double.parseDouble(quantity);
                                if (newQuantity < 0) {
                                    throw new Exception("Quantity cannot be negative");
                                }
                            } catch (Exception e){
                                throw new Exception("Quantity should be a positive integer value");
                            }
                        }
                        if (price.length() == 0) {
                            throw new Exception("Price cannot be empty");
                        }else {
                            try {
                                newPrice = Double.parseDouble(price);
                                if (newPrice < 0) {
                                    throw new Exception("Price cannot be negative");
                                }
                            } catch (Exception e){
                                throw new Exception("Price should be a positive integer value");
                            }
                        }
                        if (type.equalsIgnoreCase("Stock")) {
                            // new stock
                            if (index == -1) {
                                portfolio.buyStock(symbol, name, newQuantity, newPrice);
                                double bookValue = newPrice * newQuantity + commission;
                                outputTextArea.append("=== Bought Investment ===\n");
                                outputTextArea.append("Type: " + type + "\n");
                                outputTextArea.append("Symbol: " + symbol + "\n");
                                outputTextArea.append("Name: " + name + "\n");
                                outputTextArea.append("Quantity: " + newQuantity + "\n");
                                outputTextArea.append("Price: $" + newPrice + "\n");
                                outputTextArea.append("Bookvalue: $" + bookValue + "\n");
                            }
                            // existing stock
                            else if (index != -1) {
                                portfolio.buyStock(symbol, index, newQuantity, newPrice);
                                Investment stock = newinvestmentList.get(index);
                                outputTextArea.append("=== Bought Investment ===\n");
                                outputTextArea.append("Bought " + newQuantity + " more shares of " + symbol + "\n");
                                outputTextArea.append("Type: " + type + "\n");
                                outputTextArea.append("Symbol: " + symbol + "\n");
                                outputTextArea.append("Name: " + name + "\n");  
                                outputTextArea.append("Quantity: " + stock.getQuantity() + "\n");
                                outputTextArea.append("Price: $" + stock.getPrice() + "\n");
                                outputTextArea.append("Bookvalue: $" + stock.getBookValue() + "\n");
                            }
                        }
                        else if (type.equalsIgnoreCase("Mutual Fund")) {
                            // new mutual fund
                            if (index == -1) {
                                double bookValue = newPrice * newQuantity;
                                portfolio.buyMutualFund(symbol, name, newQuantity, newPrice);
                                outputTextArea.append("=== Bought Investment ===\n");
                                outputTextArea.append("Type: " + type + "\n");
                                outputTextArea.append("Symbol: " + symbol + "\n");
                                outputTextArea.append("Name: " + name + "\n");
                                outputTextArea.append("Quantity: " + newQuantity + "\n");
                                outputTextArea.append("Price: $" + newPrice + "\n");
                                outputTextArea.append("Bookvalue: $" + bookValue + "\n");
                            }
                            // existing mutual fund
                            else if (index != -1) {
                                portfolio.buyMutualFund(symbol, index, newQuantity, newPrice);
                                Investment mutualFund = newinvestmentList.get(index);
                                outputTextArea.append("=== Bought Investment ===\n");
                                outputTextArea.append("Bought " + newQuantity + " more units of " + symbol + "\n");
                                outputTextArea.append("Type: " + type + "\n");
                                outputTextArea.append("Symbol: " + symbol + "\n");
                                outputTextArea.append("Name: " + name + "\n");
                                outputTextArea.append("Quantity: " + mutualFund.getQuantity() +"\n");
                                outputTextArea.append("Price: $" + mutualFund.getPrice() + "\n");
                                outputTextArea.append("Bookvalue: $" + mutualFund.getBookValue() + "\n");
                            }
                        }
                    } catch (Exception e) {
                        outputTextArea.append(e.getMessage() + " Try Again!\n");
                    }
                }
            }
        );

        // Resets all textfields on the panel
        resetButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    symbolField.setText("");
                    nameField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                }   
            }
        );
    }

    // Sells an existing stock or mutual fund
    public void showSellWindow() {
        // Reconstruct panel
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();
        contentPane.setLayout(null);

        // Labels
        JLabel messageTitle = new JLabel("Messages");
        JLabel windowTitle = new JLabel("Selling an Investment");
        JLabel symbolLabel = new JLabel("Symbol");
        JLabel quantityLabel = new JLabel("Quantity");
        JLabel priceLabel = new JLabel("Price");

        // Textfields
        JTextField symbolField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField();

        // Buttons
        JButton resetButton = new JButton("Reset");
        JButton sellButton = new JButton("Sell");
        JTextArea outputTextArea = new JTextArea();

        // Pane customization
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());

        windowTitle.setBounds(10, 10, 200, 100);
        windowTitle.setFont(new Font("Verdana", Font.PLAIN, 18));

        // Setting Dimensions
        symbolLabel.setBounds(10, 125, 50, 50);
        priceLabel.setBounds(10, 205, 50, 50);
        quantityLabel.setBounds(10, 165, 75, 50);

        symbolField.setBounds(90, 140, 150, 20);
        quantityField.setBounds(90, 180, 150, 20);
        priceField.setBounds(90, 220, 150, 20);

        outputScrollPane.setBounds(15, 325, 450, 135);
        messageTitle.setBounds(10, 280, 100, 50);

        resetButton.setBounds(350, 100, 100, 50);
        sellButton.setBounds(350, 200, 100, 50);

        // Adding components
        contentPane.add(sellButton);
        contentPane.add(resetButton);
        contentPane.add(messageTitle);
        contentPane.add(outputScrollPane);
        contentPane.add(windowTitle);
        contentPane.add(symbolLabel);
        contentPane.add(symbolField);
        contentPane.add(quantityLabel);
        contentPane.add(quantityField);
        contentPane.add(priceLabel);
        contentPane.add(priceField);

        // Resets all fields on the panel
        resetButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    symbolField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                }   
            }
        );
        
        // Peforms sell operation on an investment
        sellButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    String symbol = symbolField.getText();
                    String quantity = quantityField.getText();
                    String price = priceField.getText();
                    double newQuantity;
                    double newPrice;
                    int index = portfolio.getInvestmentIndex(symbol);
                    try {
                        if (symbol.length() == 0) {
                            throw new Exception ("Symbol cannot be empty");
                        }
                        if (quantity.length() == 0) {
                            throw new Exception ("Name cannot be empty");
                        } else {
                            try {
                                newQuantity = Double.parseDouble(quantity);
                                if (newQuantity < 0) {
                                    throw new Exception("Quantity cannot be negative");
                                }
                            } catch (Exception e) {
                                throw new Exception("Quantity must be a positive integer value");
                            }
                        }
                        if (price.length() == 0) {
                            throw new Exception ("Price cannot be empty");
                        } else {
                            try {
                                newPrice = Double.parseDouble(price);
                                if (newPrice < 0) {
                                    throw new Exception("Price cannot be negative");
                                }
                            } catch (Exception e) {
                                throw new Exception("Price must be a positive integer value");
                            }
                        }
                        Investment currentInvestment = newinvestmentList.get(index);
                        if (currentInvestment instanceof Stock) {
                            portfolio.sellStock(symbol, index, newQuantity, newPrice);
                            // cannot sell if quantity given is too high
                            if (currentInvestment.getQuantity() < newQuantity) {
                                outputTextArea.append("CANNOT SELL STOCK, QUANTITY TOO HIGH\n");
                            }
                            // fully sell
                            else if (currentInvestment.getQuantity() == newQuantity) {
                                outputTextArea.append("Sold all shares of " + symbol + " at $" + newPrice + " each \n");
                            }
                            // partial sell
                            else {
                            int parsestockQuantity = (int)(newQuantity);
                            outputTextArea.append("Sold " + parsestockQuantity + " shares of " + symbol + " at $" + newPrice + " each \n");
                            }   
                        }
                        else if (currentInvestment instanceof MutualFund) {
                            portfolio.sellMutualFund(symbol, index, newQuantity, newPrice);
                            // cannot sell if quantity given is too high
                            if (currentInvestment.getQuantity() < newQuantity) {
                                outputTextArea.append("CANNOT SELL MUTUAL FUND, QUANTITY TOO HIGH\n");
                            }
                            // fully sell
                            else if (currentInvestment.getQuantity() == newQuantity) {
                                outputTextArea.append("Sold all units of " + symbol + " at $" + newPrice + " each \n");
                            }
                            // partial sell
                            else {
                            int parsemutualfundQuantity = (int)(newQuantity);
                            outputTextArea.append("Sold " + parsemutualfundQuantity + " units of " + symbol + " at $" + newPrice + " each \n");
                            }
                        }
                    } catch (Exception e) {
                        outputTextArea.append(e.getMessage() + " Try Again!\n");
                    } 
                }   
            }
        );
    }   
    
    // Updates investments
    public void showUpdateWindow() {
        // Reconstructs panel
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();
        contentPane.setLayout(null);

        // Labels
        JLabel messageTitle = new JLabel("Messages");
        JLabel windowTitle = new JLabel("Updating Investments");
        JLabel symbolLabel = new JLabel("Symbol");
        JLabel nameLabel = new JLabel("Name");
        JLabel priceLabel = new JLabel("Price");

        // Textfields
        JTextField symbolField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();

        // Buttons
        JButton prevButton = new JButton("Prev");
        JButton nextButton = new JButton("Next");
        JButton saveButton = new JButton("Save");
        JTextArea outputTextArea = new JTextArea();

        // Pane customization
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());

        windowTitle.setBounds(10, 10, 200, 100);
        windowTitle.setFont(new Font("Verdana", Font.PLAIN, 18));

        // Setting dimensions
        symbolLabel.setBounds(10, 125, 50, 50);
        nameLabel.setBounds(10, 165, 75, 50);
        priceLabel.setBounds(10, 205, 50, 50);

        symbolField.setBounds(90, 140, 150, 20);
        nameField.setBounds(90, 180, 150, 20);
        priceField.setBounds(90, 220, 150, 20);

        nameField.setEditable(false);
        symbolField.setEditable(false);

        outputScrollPane.setBounds(15, 325, 450, 135);
        messageTitle.setBounds(10, 280, 100, 50);

        prevButton.setBounds(350, 50, 100, 50);
        nextButton.setBounds(350, 150, 100, 50);
        saveButton.setBounds(350, 250, 100, 50);

        // Adding components
        contentPane.add(prevButton);
        contentPane.add(nextButton);
        contentPane.add(saveButton);
        contentPane.add(messageTitle);
        contentPane.add(outputScrollPane);
        contentPane.add(windowTitle);
        contentPane.add(symbolLabel);
        contentPane.add(symbolField);
        contentPane.add(nameLabel);
        contentPane.add(nameField);
        contentPane.add(priceLabel);
        contentPane.add(priceField);

        // Start with the initial investment at index 0
        Investment currentInvestment = newinvestmentList.get(0);
        String symbol = currentInvestment.getSymbol();
        String name = currentInvestment.getName();
        
        // Set the initial investment attributes
        symbolField.setText(symbol);
        nameField.setText(name);

        // Iterates forward for investments in the list
        nextButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    try {
                        if (counter > newinvestmentList.size()) {
                            throw new ArrayIndexOutOfBoundsException("No more investments to update\n");
                        }
                    } catch (Exception ArrayIndexOutOfBoundsException) {
                        outputTextArea.append(ArrayIndexOutOfBoundsException.getMessage());
                    }
                    // Reset the price field
                    priceField.setText("");
                    counter++;
                    Investment currentInvestment = newinvestmentList.get(counter);
                    String symbol = currentInvestment.getSymbol();
                    String name = currentInvestment.getName();
                    symbolField.setText(symbol);
                    nameField.setText(name);
                }   
            }
        );

        // Iterates backwards for investments in the list
        prevButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    try {
                        if (counter == -1) {
                            throw new ArrayIndexOutOfBoundsException("No Previous Investment to Update\n");
                        }
                    } catch (Exception ArrayIndexOutOfBoundsException) {
                        outputTextArea.append(ArrayIndexOutOfBoundsException.getMessage());
                    }
                    // Reset the price field
                    priceField.setText("");
                    counter--;
                    Investment currentInvestment = newinvestmentList.get(counter);
                    String symbol = currentInvestment.getSymbol();
                    String name = currentInvestment.getName();
                    symbolField.setText(symbol);
                    nameField.setText(name);
                }   
            }
        );
        
        // Saves the price for the investment when clicked
        saveButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    String price = priceField.getText();
                    String symbol = symbolField.getText();
                    // receive current investment
                    int index = portfolio.getInvestmentIndex(symbol);
                    double newPrice;
                    try {
                        if (price.length() == 0) {
                            throw new Exception("Price cannot be empty!");
                        } else {
                            try {
                                newPrice = Double.parseDouble(price);
                                if (newPrice < 0) {
                                    throw new Exception("Price cannot be negative!");
                                }
                            } catch (Exception e) {
                                throw new Exception("Price must be a positive value!");
                            }
                        }
                        // Receive current investment in the list
                        Investment currentInvestment = newinvestmentList.get(index);
                        currentInvestment.updateInvestmentPrice(newPrice);
                        outputTextArea.append("The price for " + currentInvestment.getSymbol() + " is now updated to $" + newPrice + "\n");
                    } catch (Exception e) {
                        outputTextArea.append(e.getMessage() + " Try Again!\n");
                    }
                }
            }
        );
    }
    
    // Calculates total and individual gain for all investments existing in the list
    public void showGainWindow() {
        // Constructs pane
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();
        contentPane.setLayout(null);

        // Setting components
        JLabel messageTitle = new JLabel("Invidual Gains");
        JLabel windowTitle = new JLabel("Getting total gain");
        JLabel gainLabel = new JLabel("Total Gain");
        JTextField gainField = new JTextField();
        JTextArea outputTextArea = new JTextArea();
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        
        // Pane customization
        outputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());
        outputScrollPane.setBounds(15, 225, 450, 200);

        // Settign dimensions
        windowTitle.setBounds(10, 10, 200, 100);
        windowTitle.setFont(new Font("Verdana", Font.PLAIN, 18));
        messageTitle.setBounds(10, 180, 100, 50);
        gainLabel.setBounds(10, 125, 100, 50);
        gainField.setBounds(90, 140, 150, 20);

        // Adding components
        contentPane.add(windowTitle);
        contentPane.add(outputScrollPane);
        contentPane.add(messageTitle); 
        contentPane.add(gainLabel);
        contentPane.add(gainField);

        // Set the field to read-only
        gainField.setEditable(false);

        double stockGains = 0;
        double mutualGains = 0;
        double totalGains = 0;
        String newstockGains;
        String newmutualGains;

        // Loop through investment and compute gain
        for (Investment investment: newinvestmentList) {
            if (investment instanceof Stock) {
                stockGains = investment.calculateGain();
                stockGains = (investment.getPrice() * investment.getQuantity() - commission) - investment.getBookValue();
                newstockGains = Double.toString(stockGains);
                outputTextArea.append("Gain for " + investment.getSymbol() + " is $ " + newstockGains + " \n");
                totalGains+= stockGains;
            }
            else if (investment instanceof MutualFund) {
                mutualGains = investment.calculateGain();
                newmutualGains = Double.toString(mutualGains);
                outputTextArea.append("Gain for " + investment.getSymbol() + " is $ " + newmutualGains + " \n");
                totalGains+= mutualGains;
            }
        }
        String newtotalGains = Double.toString(totalGains);
        gainField.setText("$ " + newtotalGains);

    }

    public void showSearchWindow() {
        // Construct panel
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();
        contentPane.setLayout(null);
     
        // Titles
        JLabel messageTitle = new JLabel("Search Results");
        JLabel windowTitle = new JLabel("Searching Investments");

        // Labels
        JLabel symbolLabel = new JLabel("Symbol");
        JLabel keywordLabel = new JLabel("Name keywords");
        JLabel highpriceLabel = new JLabel("Low price");
        JLabel lowpriceLabel = new JLabel("High price");

        // Text Fields
        JTextField keywordField = new JTextField();
        JTextField symbolField = new JTextField();
        JTextField lowpriceField = new JTextField();
        JTextField highpriceField = new JTextField();

        // Buttons
        JButton resetButton = new JButton("Reset");
        JButton searchButton = new JButton("Search");

        JTextArea outputTextArea = new JTextArea();

        // Pane customization
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());

        // Setting dimensions
        windowTitle.setBounds(10, 10, 300, 100);
        windowTitle.setFont(new Font("Verdana", Font.PLAIN, 18));

        symbolLabel.setBounds(10, 125, 75, 50);
        keywordLabel.setBounds(10, 165, 100, 50);
        highpriceLabel.setBounds(10, 205, 75, 50);
        lowpriceLabel.setBounds(10, 245, 75, 50);

        symbolField.setBounds(110, 140, 150, 20);
        keywordField.setBounds(110, 180, 150, 20);
        highpriceField.setBounds(110, 220, 150, 20);
        lowpriceField.setBounds(110, 260, 150, 20);

        outputScrollPane.setBounds(15, 325, 450, 135);
        messageTitle.setBounds(10, 280, 100, 50);

        resetButton.setBounds(350, 100, 100, 50);
        searchButton.setBounds(350, 200, 100, 50);

        // Adding components
        contentPane.add(searchButton);
        contentPane.add(resetButton);
        contentPane.add(messageTitle);
        contentPane.add(outputScrollPane);
        contentPane.add(windowTitle);
        contentPane.add(keywordLabel);
        contentPane.add(keywordField);
        contentPane.add(symbolLabel);
        contentPane.add(symbolField);
        contentPane.add(lowpriceLabel);
        contentPane.add(lowpriceField);
        contentPane.add(highpriceLabel);
        contentPane.add(highpriceField);

        // Resets all fields when clicked
        resetButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    symbolField.setText("");
                    keywordField.setText("");
                    lowpriceField.setText("");
                    highpriceField.setText("");
                }   
            }
        );

        // Searches for existing investments in the list
        searchButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    String symbol = symbolField.getText();
                    String keyWords = keywordField.getText();
                    String lowPrice = lowpriceField.getText();
                    String highPrice = highpriceField.getText();
                    double newlowPrice;
                    double newhighPrice;
                    try {
                        // Print all investments if fields left blank
                        if ((symbol.length() == 0) && (keyWords.length() == 0) && (lowPrice.length() == 0) && (highPrice.length() == 0)) {
                            for (int i = 0 ; i < newinvestmentList.size(); i++) {
                                Investment currentInvestment = newinvestmentList.get(i);
                                outputTextArea.append(currentInvestment.toString());
                                outputTextArea.append("\n");
                            }
                        }
                        else {
                            newlowPrice = Double.parseDouble(lowPrice);
                            newhighPrice = Double.parseDouble(highPrice);
                            // check for price
                            if ((newlowPrice < 0) || (newhighPrice < 0)) {
                                    throw new Exception("Invalid price");
                            }
                            // if fields are entered correctly, perform search
                            portfolio.search(symbol, keyWords, newlowPrice, newhighPrice);
                            outputTextArea.append("=== Investment Found ==\n");
                            int index = portfolio.getInvestmentIndex(symbol);
                            Investment currentInvestment = newinvestmentList.get(index);
                            outputTextArea.append(currentInvestment.toString());
                        }
                    } catch (Exception e) {
                        outputTextArea.append(e.getMessage() + " or price" + " Try Again!\n");
                    }
                }
            }
        );
    }

    /** 
     * @param e
     * Action events performed based upon the input the user clicks
     */
    public void actionPerformed(ActionEvent e) {
        // Menubar Actions
        String option = e.getActionCommand();
            if (option.equalsIgnoreCase("Buy an Investment")) {
                showBuyWindow();
            }
            else if (option.equalsIgnoreCase("Sell an Investment")) {
                showSellWindow();
            }
            else if (option.equalsIgnoreCase("Update Investments")) {
                showUpdateWindow();
            }
            else if (option.equalsIgnoreCase("total Gain")) {
                showGainWindow();
            }
            else if (option.equalsIgnoreCase("Search for an Investment")) {
                showSearchWindow();
            }
            else if (option.equalsIgnoreCase("Exit Program")) {
                // Receive filename from command line arguments
                String [] args = Main.getArgs();
                // Output existing investments to the file given at command line
                portfolio.outputInvestments(args);
                JOptionPane.showMessageDialog(null, "Investments saved to " + args[0]);
                System.exit(0);
            }
        }
}