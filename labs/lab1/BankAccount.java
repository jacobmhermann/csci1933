public class BankAccount {
  private String name;
  private String password;
  private double balance;

  public BankAccount(String initName, String initPassword, double initBalance){
    this.name = initName;
    this.password = initPassword;
    this.balance = initBalance;
  }

  public void withdraw (String enteredPassword, double amount) {
    if (password.equals(enteredPassword) && balance >= amount) {
      balance -= amount;
    }
  }

  public void deposit(String enteredPassword, double amount) {
    if (password.equals(enteredPassword)) {
      balance += amount;
    }
  }

  public double getBalance(String enteredPassword) {
    if (password.equals(enteredPassword)) {
      return balance;
    } else {
      return -1;
    }
  }

  public boolean setPassword(String oldPassword, String newPassword) {
    if (password.equals(oldPassword)) {
      password = newPassword;
      return true;
    } else {
      return false;
    }
  }

  public static void main(String[] args) {
    BankAccount myAccount = new BankAccount("Java","CSCI1933 rules!", 100.50);
    System.out.println("My account's balance is: " + myAccount.balance);
  }

}
