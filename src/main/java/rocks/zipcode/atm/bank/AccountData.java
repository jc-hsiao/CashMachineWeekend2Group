package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public final class AccountData {

    private final String id;
    private final String name;
    private final String email;
    private Double balance;
    protected final String pin;
    private boolean isPremium;


    AccountData(String id, String name, String email, Double balance, String pin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.pin = pin;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Double getBalance() {
        return balance;
    }

    public String getPin() {return pin;}

    public boolean isPremium() {
        return isPremium;
    }

    protected void setPremium() {
        isPremium = true;
    }

    @Override
    public String toString() {
        return "Account id: " + id + '\n' +
                "Name: " + name + '\n' +
                "Email: " + email + '\n' +
                "Balance: " + balance;
    }
}
