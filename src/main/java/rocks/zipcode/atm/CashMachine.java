package rocks.zipcode.atm;

import rocks.zipcode.atm.bank.AccountData;
import rocks.zipcode.atm.bank.Bank;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author ZipCodeWilmington
 */
public class CashMachine {
    private String errorMessage = "";
    private final Bank bank;
    private AccountData accountData = null;
    private String specialMessage = "";

    public Bank getBank() {
        return bank;
    }

    public AccountData getCurrentUser() {
        return accountData;
    }

    public CashMachine(Bank bank) {
        this.bank = bank;
    }

    private Consumer<AccountData> update = data -> {
        accountData = data;
    };


    public void login(int id, String pin) {
        tryCall(
                () -> bank.getAccountById(id,pin),
                update
        );
    }

    public void deposit(double amount) {
        //if ((accountData != null) && (amount>0)) {
            tryCall(
                    () -> bank.deposit(accountData, amount),
                    update
            );
        //}
    }

    public void withdraw(double amount) {
        //if ((accountData != null) && (amount>0)) {
        tryCall(
                    () -> bank.withdraw(accountData, amount),
                    update
            );
        //}
    }

    public void exit() {
        if (accountData != null) {
            accountData = null;
        }
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }

    @Override
    public String toString() {
        return accountData != null ? accountData.toString() : "Try account 1 or 2 and click submit.";
    }

    private <T> void tryCall(Supplier<ActionResult<T> > action, Consumer<T> postAction) {
        try {
            ActionResult<T> result = action.get();
            if (result.isSuccess()) {
                T data = result.getData();
                specialMessage = result.getSpecialMessage();
                errorMessage = result.getErrorMessage();
                if(specialMessage != null)
                    System.out.println("Special: " + specialMessage);
                postAction.accept(data);
            } else {
                String errorMessage = result.getErrorMessage();
                throw new RuntimeException(errorMessage);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            errorMessage = e.getMessage();
        }
    }
}
