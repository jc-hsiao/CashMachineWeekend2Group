package rocks.zipcode.atm.bank;

import org.junit.Assert;
import org.junit.Test;
import rocks.zipcode.atm.ActionResult;

import javax.swing.*;

import static org.junit.Assert.*;

public class BankTest {

    @Test
    public void withdrawTestPremiumOverDraft() {
        Bank zipBank = new Bank();
        PremiumAccount billGates = new PremiumAccount(new AccountData(123, "William Gates", "moe.money@gatesestate.com", 1000.00, "9999"));
        zipBank.accounts.put(123, billGates);
        ActionResult<AccountData> actual = zipBank.withdraw(billGates.getAccountData(), 1099.0);
        Assert.assertEquals("Overdraft paid!", actual.getSpecialMessage());
        Assert.assertEquals(billGates.getAccountData(), actual.getData());
    }

    @Test
    public void withdrawTestBasicOverDraft() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData(121, "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 1.00, "1885"));
        zipBank.accounts.put(121, oliverTwist);
        ActionResult<AccountData> actual = zipBank.withdraw(oliverTwist.getAccountData(), 1099.0);
        Assert.assertEquals("Withdraw failed: 1099.0.Account has: 1.00", actual.getErrorMessage());


    }


    @Test
    public void withdrawNegativeTestBasicOverDraft() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData(121, "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
        zipBank.accounts.put(121, oliverTwist);
        ActionResult<AccountData> actual = zipBank.withdraw(oliverTwist.getAccountData(), 98.0);
        Assert.assertEquals("Withdraw failed: 98.0.Account has: .00", actual.getErrorMessage());
    }

    @Test
    public void withdrawNegativeTestPremiumOverDraft() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData(121, "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
       Boolean is=oliverTwist.isPremium();
        zipBank.accounts.put(121, oliverTwist);
        ActionResult<AccountData> actual = zipBank.withdraw(oliverTwist.getAccountData(), 101.0);
        Assert.assertEquals("Withdraw failed: 101.0.Account has: .00", actual.getErrorMessage());
    }
    @Test
    public void withdrawNegativeTest() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData(121, "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
        Boolean is=oliverTwist.isPremium();
        zipBank.accounts.put(121, oliverTwist);
        ActionResult<AccountData> actual = zipBank.withdraw(oliverTwist.getAccountData(), -101.0);
        Assert.assertEquals("Withdraw failed can not except negative amount ", actual.getErrorMessage());
    }
    @Test
    public void depositNegativeTest() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData(121, "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
        Boolean is=oliverTwist.isPremium();
        zipBank.accounts.put(121, oliverTwist);
        ActionResult<AccountData> actual = zipBank.deposit(oliverTwist.getAccountData(), -101.0);
        Assert.assertEquals("Withdraw failed can not except negative amount ", actual.getErrorMessage());
    }

    @Test
    public void depositTest() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData(121, "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
        oliverTwist.setPremium(false);
        zipBank.accounts.put(121, oliverTwist);
        ActionResult<AccountData> actual = zipBank.deposit(oliverTwist.getAccountData(), 10.0);
        Assert.assertEquals("Withdraw failed can not except negative amount ", actual.getErrorMessage());
    }
}