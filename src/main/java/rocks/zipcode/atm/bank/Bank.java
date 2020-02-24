//fake merge test
package rocks.zipcode.atm.bank;
import rocks.zipcode.atm.ActionResult;
import rocks.zipcode.atm.CashMachine;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    private Map<String, Account> accounts = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(CashMachine.class.getName());

    public Bank() {

        String[] studentNames = {
                "Aarti Kansal", "Adam Bennet", "April Howard", "Chip Fody", "Chris Farmer",
                "Corey Williams", "David Comer", "David Kelly", "Destiny Bond", "Emily Beech",
                "Giles Bradford", "Greg Davis", "Han Lin", "James Churu", "James Wilkinson",
                "Jeremy McCray", "Kevin Romero", "Khalil Crumpler", "Leila Hsiao", "Matthew Ascone",
                "Maurice Russ", "Moe Aydin", "Raheel Uppal", "Sandeep Mangalam", "Sandy Setiawan",
                "Ujjwal Shrestha", "Von Le", "Zanetta Norris", "Zeth Kane"
        };
        String[] instructorNames = {
                "Kris Younger", "Roberto DeDeus", "Christopher Nobles"
        };

        for(String s : studentNames) {
            String accountId = generateID(s);
            this.accounts.put(accountId, new BasicAccount(new AccountData(accountId, s, accountId + "@zipcode.com", 100.00, "1234")));
        }
        for(String i : instructorNames) {
            String accountId = generateID(i);
            this.accounts.put(accountId, new PremiumAccount(new AccountData(accountId, i, accountId + "@zipcode.com", 300.00, "1234")));
        }
    }

    public AccountData getAccountData(String Id){
        return accounts.get(Id).getAccountData();
    }

    public static String generateID(String name){
        String[] nameArray = name.split(" ");
        return (nameArray[0].charAt(0) + nameArray[1]).toLowerCase();
    }

    public ActionResult<AccountData> login(String id, String pin) {
        Account account = accounts.get(id);

        if (account != null && pin.equals(account.getAccountData().getPin())) {
            LOGGER.log(Level.INFO, "\n"+account.getAccountData().toString());
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("Invalid login!");
        }
    }

    public ActionResult<AccountData> deposit(AccountData accountData, String amountString) {
        Double amount;
        Account account = accounts.get(accountData.getId());

        try {
            amount = Double.parseDouble(amountString);
        }catch(Exception e){
            return ActionResult.fail("Deposit failed: Invalid input!");
        }

        if (amount < 0) {
            return ActionResult.fail("Deposit failed: Negative input not allowed.");
        }

        account.deposit(amount);
        return ActionResult.success(account.getAccountData());
    }

    public ActionResult<AccountData> withdraw(AccountData accountData, String amountString) {
        Account account = accounts.get(accountData.getId());
        Double amount;
        try {
            amount = Double.parseDouble(amountString);
        }catch(Exception e){
            return ActionResult.fail("Withdraw failed: Invalid input!");
        }

        if (amount < 0) {
            return ActionResult.fail("Withdraw failed: Negative input not allowed.");
       }else if (account.getAccountData().isPremium() && (amount > account.getBalance())) {
            if(account.canWithdraw(amount)){
                account.withdraw(amount);
                return ActionResult.successWithMessage("Overdraft Warning!",account.getAccountData());
            }else{
                return ActionResult.fail("Withdraw failed: Exceed overdraft limit.");
            }
        }else if(!account.canWithdraw(amount)){
            return ActionResult.fail("Withdraw failed: Balance not enough.");
        }
        account.withdraw(amount);
        return ActionResult.success(account.getAccountData());
    }

    public ActionResult<AccountData> createAccount(String fullName, String email, String pin) {
        if(fullName.isEmpty() || email.isEmpty() || pin.isEmpty()){
            return ActionResult.fail("Some fields are empty!");
        }else {
            String id = generateID(fullName);
            AccountData accData = new AccountData(id, fullName, email, 0.00, pin);
            accounts.put(id, new BasicAccount(accData));
            return ActionResult.success(accData);
        }
    }
}
