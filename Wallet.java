package org.example;

class Wallet {

    private String balance;

    public Wallet(int initialBalance) {
        this.balance = Integer.toString(initialBalance);
    }

    public boolean canAfford(int price) {
        int currentBalance = Integer.parseInt(balance);
        return currentBalance >= price;
    }
    public void withdraw(int price) {
        // اینجا محاسبات مربوط به برداشت وجه از کیف پول قرار می‌گیرد
    }

    public void deposit(int price) {
        // اینجا محاسبات مربوط به واریز وجه به کیف پول قرار می‌گیرد
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
