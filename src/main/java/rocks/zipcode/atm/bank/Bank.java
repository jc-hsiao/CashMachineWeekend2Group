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


        for (int i = 0; i < 15; i++) {
            this.accounts.put(i, new PremiumAccount(new AccountData(i, premiumUserName[i], premiumUserName[i].split(" ")[0] + "@zipcode.com", 100.00, "1234")));
        }


        String[] basicUserName = {
                "Jeremy McCray", "Kevin Romero", "Khalil Crumpler", "Leila Hsiao", "Matthew Ascone",
                "Maurice Russ", "Moe Aydin", "Raheel Uppal", "Sandeep Narayana Mangalam",
                "Sandy Setiawan", "Ujjwal Shrestha", "Von Le", "Zanetta Norris", "Zeth Kane"

        };

        int counter = 0;
        for (int i = 16; i < 29; i++) {
            accounts.put(i, new BasicAccount(new AccountData(i, basicUserName[counter], basicUserName[counter].split(" ")[0] + "@zipcode.com", 100.00, "1234")));
            counter++;
        }

    }

    public ActionResult<AccountData> getAccountById(int id, String pin) {
        Account account = accounts.get(id);
        if (account != null && pin.equals(account.getAccountData().getPin())) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("Invalid login credentials");
        }
    }

    public ActionResult<AccountData> deposit(AccountData accountData, Double amount) {

        if (amount <= 0) {
            return ActionResult.fail("Withdraw failed can not except negative amount ");
        }
        Account account = accounts.get(accountData.getId());
        account.deposit(amount);

        return ActionResult.success(account.getAccountData());
    }

    public ActionResult<AccountData> withdraw(AccountData accountData, Double amount) {
        Account account = accounts.get(accountData.getId());
        if (amount <= 0) {
            return ActionResult.fail("Withdraw failed can not except negative amount ");
        } else if (account.isPremium()) {
            return ActionResult.successWithMessage("Overdraft paid!", account.getAccountData());
        } else if ((!account.isPremium()) && (accountData.getBalance() - amount <= 0) || (account.isPremium()) && (accountData.getBalance() - amount >= -100)) {
            return ActionResult.fail("Withdraw failed: " + amount + ".Account has: " + new DecimalFormat("#.00").format(account.getBalance()));
        }
        return ActionResult.fail("Withdraw failed: " + amount + ".Account has: " + new DecimalFormat("#.00").format(account.getBalance()));
    }
    }


