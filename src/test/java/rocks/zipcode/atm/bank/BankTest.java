package rocks.zipcode.atm.bank;

import org.junit.Assert;
import org.junit.Test;
import rocks.zipcode.atm.ActionResult;

public class BankTest {

    @Test
    public void generateIdTest(){
        Assert.assertEquals("abb",Bank.generateID("aa bb"));
    }

    @Test
    public void createAccountTest(){
        Bank zipBank = new Bank();
        ActionResult<AccountData> ar = zipBank.createAccount("Phoenix Wright", "pw_ace@attorney.gov", "1122");

        String actualName = ar.getData().getName();
        String actualEmail = ar.getData().getEmail();
        String actualPin = ar.getData().getPin();
        String actualId = ar.getData().getId();

        Assert.assertEquals("Phoenix Wright", actualName);
        Assert.assertEquals("pw_ace@attorney.gov", actualEmail);
        Assert.assertEquals("1122", actualPin);
        Assert.assertEquals("pwright",actualId );
    }

    @Test
    public void withdrawTestPremiumOverDraft() {
        Bank zipBank = new Bank();
        PremiumAccount billGates = new PremiumAccount(new AccountData("123", "William Gates", "moe.money@gatesestate.com", 1000.00, "9999"));
        zipBank.accounts.put("123", billGates);
        ActionResult<AccountData> actual = zipBank.withdraw(billGates.getAccountData(), "1099");
        Assert.assertEquals("Overdraft Warning!", actual.getSpecialMessage());
        Assert.assertEquals(billGates.getAccountData(), actual.getData());
    }
    @Test
    public void withdrawTestBasicOverDraft() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData("121", "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 1.00, "1885"));
        zipBank.accounts.put("121", oliverTwist);
        ActionResult<AccountData> actual = zipBank.withdraw(oliverTwist.getAccountData(), "1099");
        Assert.assertEquals("Withdraw failed: Balance not enough.", actual.getErrorMessage());
    }
    @Test
    public void withdrawNegativeTestBasicOverDraft() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData("121", "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
        zipBank.accounts.put("121", oliverTwist);
        ActionResult<AccountData> actual = zipBank.withdraw(oliverTwist.getAccountData(), "98");
        Assert.assertEquals("Withdraw failed: Balance not enough.", actual.getErrorMessage());
    }
    @Test
    public void withdrawNegativeTestPremiumOverDraft() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData("121", "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
        zipBank.accounts.put("121", oliverTwist);
        ActionResult<AccountData> actual = zipBank.withdraw(oliverTwist.getAccountData(), "101");
        Assert.assertEquals("Withdraw failed: Balance not enough.", actual.getErrorMessage());
    }
    @Test
    public void withdrawNegativeTest() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData("121", "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
        zipBank.accounts.put("121", oliverTwist);
        ActionResult<AccountData> actual = zipBank.withdraw(oliverTwist.getAccountData(), "-101");
        Assert.assertEquals("Withdraw failed: Negative input not allowed.", actual.getErrorMessage());
    }
    @Test
    public void depositNegativeTest() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData("121", "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
        zipBank.accounts.put("121", oliverTwist);
        ActionResult<AccountData> actual = zipBank.deposit(oliverTwist.getAccountData(), "-101");
        Assert.assertEquals("Deposit failed: Negative input not allowed.", actual.getErrorMessage());
    }
    @Test
    public  void updateTest() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData("121", "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
        zipBank.accounts.put("121",oliverTwist);
        ActionResult<AccountData> actual = zipBank.deposit(oliverTwist.getAccountData(),"100");
        Assert.assertEquals(true,actual.isSuccess());

    }
    @Test
    public  void getAccountByIdTest() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData("121", "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
        zipBank.accounts.put("121",oliverTwist);
        ActionResult<AccountData> actual =zipBank.login(oliverTwist.getAccountData().getId()+"","1885");
        Assert.assertEquals(true,actual.isSuccess());
    }
    @Test
    public  void getAccountByIdInvalidTest() {
        Bank zipBank = new Bank();
        BasicAccount oliverTwist = new BasicAccount(new AccountData("121", "Ollie Spin", "bill.sikes.sucks@canterbury.gov", 0.0, "1885"));
        zipBank.accounts.put("121",oliverTwist);
        ActionResult<AccountData> actual =zipBank.login(oliverTwist.getAccountData().getId()+"","1111");
        Assert.assertEquals("Invalid login!",actual.getErrorMessage());

    }


    }