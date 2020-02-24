package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public abstract class Account {

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

    public void withdraw(double amount) { updateBalance(getBalance() - amount); }

    protected boolean canWithdraw(double amount) { return getBalance() >= amount; }

    public Double getBalance() {
        return accountData.getBalance();
    }


    private void updateBalance(Double newBalance) {
        boolean prem = accountData.isPremium();
        accountData = new AccountData(accountData.getId(), accountData.getName(), accountData.getEmail(),
                newBalance, accountData.getPin());
        if(prem)
            accountData.setPremium();
    }
}
