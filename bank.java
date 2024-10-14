import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Transaction {
    private String transactionType;
    private double amount;
    private Date date;

    public Transaction(String transactionType, double amount, Date date) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}

class BankAccount {
    private int accountNumber;
    private String accountName;
    protected double balance;
    private List<Transaction> transactions;

    public BankAccount(int accountNumber, String accountName) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction("Deposit", amount, new Date()));
            System.out.println("Deposited " + amount + " into account " + accountNumber);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount, new Date()));
            System.out.println("Withdrew " + amount + " from account " + accountNumber);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance");
        }
    }

    public void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder Name: " + accountName);
        System.out.println("Balance: " + balance);
    }

    public void displayTransactions() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getTransactionType() + ": " + transaction.getAmount() +
                    ", Date: " + transaction.getDate());
        }
    }
}

class SavingsAccount extends BankAccount {
    private double minBalance;

    public SavingsAccount(int accountNumber, String accountName, double minBalance) {
        super(accountNumber, accountName);
        this.minBalance = minBalance;
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount >= minBalance) {
            super.withdraw(amount);
        } else {
            System.out.println("Withdrawal failed: Insufficient balance or below minimum balance");
        }
    }

    @Override
    public void displayAccountDetails() {
        super.displayAccountDetails();
        System.out.println("Minimum Balance: " + minBalance);
    }
}

class RegisteredSavingsAccount extends SavingsAccount {
    private String registeredType;

    public RegisteredSavingsAccount(int accountNumber, String accountName, double minBalance, String registeredType) {
        super(accountNumber, accountName, minBalance);
        this.registeredType = registeredType;
    }

    @Override
    public void displayAccountDetails() {
        super.displayAccountDetails();
        System.out.println("Registered Type: " + registeredType);
    }
}

class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(int accountNumber, String accountName, double overdraftLimit) {
        super(accountNumber, accountName);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount >= -overdraftLimit) {
            super.withdraw(amount);
        } else {
            System.out.println("Withdrawal failed: Insufficient funds and overdraft limit exceeded");
        }
    }

    @Override
    public void displayAccountDetails() {
        super.displayAccountDetails();
        System.out.println("Overdraft Limit: " + overdraftLimit);
    }
}

class Loan {
    private int accountNumber;
    private double loanAmount;
    private double remainingAmount;
    private double interestRate;

    public Loan(int accountNumber, double loanAmount, double interestRate) {
        this.accountNumber = accountNumber;
        this.loanAmount = loanAmount;
        this.remainingAmount = loanAmount;
        this.interestRate = interestRate;
    }

    public boolean makePayment(double amount) {
        if (amount > 0) {
            double interestAmount = remainingAmount * (interestRate / 100);
            double totalPayment = amount + interestAmount;
            if (totalPayment <= remainingAmount) {
                remainingAmount -= totalPayment;
                System.out.println("Payment of " + amount + " made for loan " + accountNumber);
                return true;
            } else {
                System.out.println("Payment exceeds remaining loan amount");
            }
        } else {
            System.out.println("Invalid payment amount");
        }
        return false;
    }

    public void displayLoanDetails() {
        System.out.println("Loan Account Number: " + accountNumber);
        System.out.println("Loan Amount: " + loanAmount);
        System.out.println("Remaining Amount: " + remainingAmount);
        System.out.println("Interest Rate: " + interestRate + "%");
    }
}

public class bank {
    public static void main(String[] args) {
        // Test the banking system
        BankAccount bankAccount = new BankAccount(1001, "Elon Musk");
        SavingsAccount savingsAccount = new SavingsAccount(2001, "Bill Gates", 100);
        RegisteredSavingsAccount registeredSavingsAccount = new RegisteredSavingsAccount(3001, "Steve Jobs", 500, "TFSA");
        CheckingAccount checkingAccount = new CheckingAccount(4001, "Jeff Bezos", 200);

        // Test transactions
        bankAccount.deposit(1000);
        bankAccount.withdraw(500);
        bankAccount.displayTransactions();
        System.out.println();

        savingsAccount.deposit(2000);
        savingsAccount.withdraw(150);
        savingsAccount.displayTransactions();
        System.out.println();

        registeredSavingsAccount.deposit(3000);
        registeredSavingsAccount.withdraw(400);  
        registeredSavingsAccount.displayTransactions();
        System.out.println();

        checkingAccount.deposit(3000);
        checkingAccount.withdraw(3500);  
        checkingAccount.displayAccountDetails();
        System.out.println();

        Loan loan = new Loan(5001, 10000, 5);
        loan.makePayment(1500);
        loan.displayLoanDetails();
    }
}
