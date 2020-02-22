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

        String[] premiumUserName = {
                "Aarti Kansal", "Adam Bennet", "April Howard", "Chip Fody", "Chris Farmer", "Corey Williams",
                "David Comer", "David Kelly", "Destiny Bond", "Emily Beech", "Giles Bradford", "Greg Davis",
                "Han Lin", "James Churu", "James Wilkinson"

        };


        for(int i=0; i<15 ; i++){
            accounts.put( i, new PremiumAccount(new AccountData(i, premiumUserName[i], premiumUserName[i].split(" ")[0]+"@zipcode.com", 100.00, "1234")));
        }




        String[] basicUserName = {
                "Jeremey McCray", "Kevin Romero", "Khalil Crumpler", "Leila Hsiao", "Matthew Ascone",
                "Maurice Russ", "Moe Aydin", "Raheel Uppal", "Sandeep Narayana Mangalam",
                "Sandy Setiawan", "Ujjwal Shrestha", "Von Le", "Zanetta Norris", "Zeth Kane"

        };

        for(int i=0; i<14 ; i++){
            accounts.put( i, new BasicAccount(new AccountData(i, basicUserName[i], basicUserName[i].split(" ")[0]+"@zipcode.com", 100.00, "1234")));
        }




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
        } else {
            return ActionResult.fail("Withdraw failed: " + amount + ". Account has: " + new DecimalFormat("#.00").format(account.getBalance()));
        }
    }
}
