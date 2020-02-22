package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public abstract class Account {
    boolean isPremium;

    private AccountData accountData;

    public Account(AccountData accountData) {
        this.accountData = accountData;
    }

    public AccountData getAccountData() {
        return accountData;
    }

    public void deposit(int amount) {
        updateBalance(getBalance() + amount);
    }

    public boolean withdraw(int amount) {
        if (canWithdraw(amount)) {
            updateBalance(getBalance() - amount);
            return true;
        } else {
            return false;
        }
    }

    protected boolean canWithdraw(int amount) {
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
