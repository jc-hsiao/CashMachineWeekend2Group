//fake merge test
package rocks.zipcode.atm.bank;

import com.sun.org.apache.bcel.internal.generic.RETURN;
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

        String[] premiumUserName = {
                "Aarti Kansal", "Adam Bennet", "April Howard", "Chip Fody", "Chris Farmer", "Corey Williams",
                "David Comer", "David Kelly", "Destiny Bond", "Emily Beech", "Giles Bradford", "Greg Davis",
                "Han Lin", "James Churu", "James Wilkinson"
        };

        for (int i = 0; i < 15; i++) {
            this.accounts.put(i, new PremiumAccount(new AccountData(i, premiumUserName[i], premiumUserName[i].split(" ")[0] + "@zipcode.com", 1000.00, "1234")));
        }

        String[] basicUserName = {
                "Jeremy McCray", "Kevin Romero", "Khalil Crumpler", "Leila Hsiao", "Matthew Ascone",
                "Maurice Russ", "Moe Aydin", "Raheel Uppal", "Sandeep Narayana Mangalam",
                "Sandy Setiawan", "Ujjwal Shrestha", "Von Le", "Zanetta Norris", "Zeth Kane"
        };

        int counter = 0;
        for (int i = 15; i < 29; i++) {
            accounts.put(i, new BasicAccount(new AccountData(i, basicUserName[counter], basicUserName[counter].split(" ")[0] + "@zipcode.com", 100.00, "1234")));
            counter++;
        }

    }

    public ActionResult<AccountData> login(String id, String pin) {
        Account account;
        try {
            account = accounts.get(Integer.parseInt(id));
        }catch(Exception e){
            return ActionResult.fail("Invalid input!");
        }
        if (account != null && pin.equals(account.getAccountData().getPin())) {
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
//        } else if ( (!account.isPremium()) && (accountData.getBalance() - amount <= 0) || (account.isPremium()) && (accountData.getBalance() - amount >= -100)) {
//           return ActionResult.fail("Withdraw failed: " + amount + ".Account has: " + new DecimalFormat("#.00").format(account.getBalance()));
        }else if (account.isPremium() && (amount > account.getBalance())) {
            if(account.canWithdraw(amount)){
                account.withdraw(amount);
                return ActionResult.successWithMessage("Overdraft Warning!",account.getAccountData());
            }else{
                return ActionResult.fail("Withdraw failed: Balance not enough.");
            }
        }else if(!account.canWithdraw(amount)){
            return ActionResult.fail("Withdraw failed: Balance not enough.");
        }
        account.withdraw(amount);
        return ActionResult.success(account.getAccountData());
        //return ActionResult.fail("Withdraw failed: " + amount + ".Account has: " + new DecimalFormat("#.00").format(account.getBalance()));
    }

    public ActionResult<AccountData> createAccount(String fullName, String email, String pin) {
        AccountData accData = new AccountData(accounts.size(), fullName, email, 100.00, pin );
        accounts.put(accounts.size(), new BasicAccount(accData));
        return ActionResult.success(accData);
    }
}
