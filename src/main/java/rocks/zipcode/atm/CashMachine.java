package rocks.zipcode.atm;

import rocks.zipcode.atm.bank.AccountData;
import rocks.zipcode.atm.bank.Bank;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ZipCodeWilmington
 */
public class CashMachine {
    private String errorMessage = "";
    private final Bank bank;
    private AccountData accountData = null;
    private String specialMessage = "";
    private static final Logger LOGGER = Logger.getLogger(CashMachine.class.getName());

    public Bank getBank() {
        return bank;
    }

    public String getSpecialMessage() {
        return specialMessage;
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


    public void login(String id, String pin) {
        tryCall(
                () -> bank.getAccountById(id,pin),
                update
        );
    }

    public void deposit(String amount) {
        //if ((accountData != null) && (amount>0)) {
            tryCall(
                    () -> bank.deposit(accountData, amount),
                    update
            );
        //}
    }

    public void withdraw(String amount) {
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



    private <T> void tryCall(Supplier<ActionResult<T> > action, Consumer<T> postAction) {
        try {
            ActionResult<T> result = action.get();
            if (result.isSuccess()) {
                T data = result.getData();
                specialMessage = result.getSpecialMessage();
                errorMessage = result.getErrorMessage();
                if(specialMessage != null)
                    LOGGER.log(Level.INFO,"[Special] " + specialMessage);
                postAction.accept(data);
            } else {
                String errorMessage = result.getErrorMessage();
                throw new RuntimeException(errorMessage);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING,"[Error] " + e.getMessage());
            errorMessage = e.getMessage();
        }
    }
}
