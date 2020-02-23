package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public abstract class Account {
    private boolean isPremium;

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    private AccountData accountData;

    public Account(AccountData accountData) {
        this.accountData = accountData;
    }

    public AccountData getAccountData() {
        return accountData;
    }

    public void deposit(double amount) {
        updateBalance(getBalance() + amount);
    }

    public boolean withdraw(double amount) {
        if (canWithdraw(amount)) {
            updateBalance(getBalance() - amount);
            return true;
        } else {
            return false;
        }
    }

    protected boolean canWithdraw(double amount) {
        if(this.isPremium == true) {
            return getBalance() >= amount - 100;
        } else {
            return getBalance() >= amount;
        }
    }

    public Double getBalance() {
        return accountData.getBalance();
    }




    private void updateBalance(Double newBalance) {
        accountData = new AccountData(accountData.getId(), accountData.getName(), accountData.getEmail(),
                newBalance, accountData.getPin());
    }
}
