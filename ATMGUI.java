import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class BankAccount {
    String name;
    String username;
    private int pin;
    private double balance;
    private ArrayList<String> transactions;

    public BankAccount(String name, String username, int pin, double balance) {
        this.name = name;
        this.username = username;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public boolean verifyPin(int enteredPin) {
        return this.pin == enteredPin;
    }

    public void setPin(int newPin) {
        this.pin = newPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add("Deposited ₹" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        transactions.add("Withdrew ₹" + amount);
        return true;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public String getName() {
        return name;
    }
}

public class ATMGUI {
    static HashMap<String, BankAccount> users = new HashMap<>();
    static BankAccount currentUser;

    public static void main(String[] args) {
        // Predefined users
        users.put("user1", new BankAccount("Kiran", "user1", 1234, 10000));
        users.put("user2", new BankAccount("Meena", "user2", 2345, 15000));

        showLoginScreen();
    }

    public static void showLoginScreen() {
        JFrame loginFrame = new JFrame("ATM Login");
        loginFrame.setSize(350, 250);
        loginFrame.setLayout(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        JTextField userText = new JTextField();
        userText.setBounds(120, 30, 150, 25);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(30, 70, 80, 25);
        JPasswordField pinText = new JPasswordField();
        pinText.setBounds(120, 70, 150, 25);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 110, 80, 25);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(120, 150, 100, 25);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String pinStr = new String(pinText.getPassword());

            if (!users.containsKey(username)) {
                JOptionPane.showMessageDialog(loginFrame, "User not found!");
                return;
            }

            try {
                int pin = Integer.parseInt(pinStr);
                BankAccount user = users.get(username);
                if (user.verifyPin(pin)) {
                    currentUser = user;
                    loginFrame.dispose();
                    showMainMenu();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Incorrect PIN");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(loginFrame, "PIN must be a number");
            }
        });

        registerButton.addActionListener(e -> {
            loginFrame.dispose();
            showRegistrationScreen();
        });

        loginFrame.add(userLabel);
        loginFrame.add(userText);
        loginFrame.add(pinLabel);
        loginFrame.add(pinText);
        loginFrame.add(loginButton);
        loginFrame.add(registerButton);
        loginFrame.setVisible(true);
    }

    public static void showRegistrationScreen() {
        JFrame regFrame = new JFrame("User Registration");
        regFrame.setSize(400, 300);
        regFrame.setLayout(null);
        regFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        JTextField nameField = new JTextField();
        nameField.setBounds(140, 30, 200, 25);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 70, 100, 25);
        JTextField userField = new JTextField();
        userField.setBounds(140, 70, 200, 25);

        JLabel pinLabel = new JLabel("PIN (4-digits):");
        pinLabel.setBounds(30, 110, 100, 25);
        JPasswordField pinField = new JPasswordField();
        pinField.setBounds(140, 110, 200, 25);

        JLabel balLabel = new JLabel("Initial Balance:");
        balLabel.setBounds(30, 150, 100, 25);
        JTextField balField = new JTextField();
        balField.setBounds(140, 150, 200, 25);

        JButton submitBtn = new JButton("Register");
        submitBtn.setBounds(140, 190, 100, 30);

        submitBtn.addActionListener(e -> {
            String name = nameField.getText();
            String username = userField.getText();
            String pinStr = new String(pinField.getPassword());
            String balStr = balField.getText();

            if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(regFrame, "❌ Username already exists!");
                return;
            }

            try {
                int pin = Integer.parseInt(pinStr);
                double balance = Double.parseDouble(balStr);

                users.put(username, new BankAccount(name, username, pin, balance));
                JOptionPane.showMessageDialog(regFrame, "✅ Registration successful!");
                regFrame.dispose();
                showLoginScreen();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(regFrame, "⚠ Invalid PIN or balance input!");
            }
        });

        regFrame.add(nameLabel);
        regFrame.add(nameField);
        regFrame.add(userLabel);
        regFrame.add(userField);
        regFrame.add(pinLabel);
        regFrame.add(pinField);
        regFrame.add(balLabel);
        regFrame.add(balField);
        regFrame.add(submitBtn);

        regFrame.setVisible(true);
    }

    public static void showMainMenu() {
        JFrame menuFrame = new JFrame("Welcome, " + currentUser.getName());
        menuFrame.setSize(400, 350);
        menuFrame.setLayout(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel balanceLabel = new JLabel("Balance: ₹" + currentUser.getBalance());
        balanceLabel.setBounds(30, 20, 300, 25);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(30, 60, 120, 30);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(160, 60, 120, 30);

        JButton historyBtn = new JButton("Transaction History");
        historyBtn.setBounds(30, 110, 250, 30);

        JButton pinChangeBtn = new JButton("Change PIN");
        pinChangeBtn.setBounds(30, 160, 250, 30);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(30, 210, 250, 30);

        depositBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(menuFrame, "Enter amount to deposit:");
            try {
                double amt = Double.parseDouble(input);
                currentUser.deposit(amt);
                JOptionPane.showMessageDialog(menuFrame, "Deposited ₹" + amt);
                balanceLabel.setText("Balance: ₹" + currentUser.getBalance());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(menuFrame, "Invalid amount!");
            }
        });

        withdrawBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(menuFrame, "Enter amount to withdraw:");
            try {
                double amt = Double.parseDouble(input);
                if (currentUser.withdraw(amt)) {
                    JOptionPane.showMessageDialog(menuFrame, "Withdrew ₹" + amt);
                    balanceLabel.setText("Balance: ₹" + currentUser.getBalance());
                } else {
                    JOptionPane.showMessageDialog(menuFrame, "Insufficient Balance");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(menuFrame, "Invalid amount!");
            }
        });

        historyBtn.addActionListener(e -> {
            ArrayList<String> history = currentUser.getTransactions();
            StringBuilder msg = new StringBuilder();
            for (String txn : history) {
                msg.append(txn).append("\n");
            }
            JOptionPane.showMessageDialog(menuFrame, msg.length() > 0 ? msg.toString() : "No transactions yet.");
        });

        pinChangeBtn.addActionListener(e -> {
            String currentPinStr = JOptionPane.showInputDialog(menuFrame, "Enter current PIN:");
            String newPinStr = JOptionPane.showInputDialog(menuFrame, "Enter new PIN:");

            try {
                int currentPin = Integer.parseInt(currentPinStr);
                int newPin = Integer.parseInt(newPinStr);

                if (currentUser.verifyPin(currentPin)) {
                    currentUser.setPin(newPin);
                    JOptionPane.showMessageDialog(menuFrame, "✅ PIN changed successfully.");
                } else {
                    JOptionPane.showMessageDialog(menuFrame, "❌ Incorrect current PIN.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(menuFrame, "Invalid PIN input.");
            }
        });

        logoutBtn.addActionListener(e -> {
            currentUser = null;
            menuFrame.dispose();
            showLoginScreen();
        });

        menuFrame.add(balanceLabel);
        menuFrame.add(depositBtn);
        menuFrame.add(withdrawBtn);
        menuFrame.add(historyBtn);
        menuFrame.add(pinChangeBtn);
        menuFrame.add(logoutBtn);
        menuFrame.setVisible(true);
    }
}