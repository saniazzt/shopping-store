import java.io.Serializable;

class Wallet implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private int balance;

    public Wallet(int initialBalance) {
        this.balance = initialBalance;
    }

    public boolean canAfford(int price) {
        int currentBalance = balance;
        return currentBalance >= price;
    }
    public void withdraw(int price) {
        this.balance -= price;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
