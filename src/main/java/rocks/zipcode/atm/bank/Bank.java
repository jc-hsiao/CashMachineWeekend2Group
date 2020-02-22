//fake merge test
package rocks.zipcode.atm.bank;

import rocks.zipcode.atm.ActionResult;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    public Map<Integer, Account> accounts = new HashMap<>();

    public Bank() {
        accounts.put(1000, new BasicAccount(new AccountData(
                1000, "Example 1", "example1@gmail.com", 500.00, "1234"
        )));

        accounts.put(2000, new PremiumAccount(new AccountData(
                2000, "Example 2", "example2@gmail.com", 200.00, "1111"
        )));

        accounts.put(1, new BasicAccount(new AccountData(
                1, "Example 3", "example3@gmail.com", 10000.00, "0000"
        )));
    }

    public ActionResult<AccountData> getAccountById(int id) {
        Account account = accounts.get(id);

        if (account != null) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("No account with id: " + id + "\nTry account 1000 or 2000");
        }
    }

    public ActionResult<AccountData> deposit(AccountData accountData, int amount) {
        Account account = accounts.get(accountData.getId());
        account.deposit(amount);

        return ActionResult.success(account.getAccountData());
    }

    public ActionResult<AccountData> withdraw(AccountData accountData, int amount) {
        Account account = accounts.get(accountData.getId());
        boolean ok = account.withdraw(amount);
        if (ok && account.isPremium) {
            return ActionResult.successWithMessage("Overdraft paid!", account.getAccountData());
        } else  if (ok && !account.isPremium) {
            return ActionResult.success(account.getAccountData());
        } else{
            return ActionResult.fail("Withdraw failed: " + amount + ". Account has: " + new DecimalFormat("#.00").format(account.getBalance()));
        }
    }
}
