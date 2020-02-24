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
    public void createAccountEmptyFieldTest(){
        Bank zipBank = new Bank();
        ActionResult<AccountData> actual = zipBank.createAccount("Phoenix Wright", "", "");
        Assert.assertEquals("Some fields are empty!", actual.getErrorMessage());
    }

    @Test
    public void loginTest(){
        Bank zipBank = new Bank();
        AccountData kris = zipBank.getAccountData("kyounger");
        ActionResult<AccountData> actual = zipBank.login(kris.getId(),kris.getPin());
        Assert.assertTrue(actual.isSuccess());
    }

    @Test
    public void loginTest2(){
        Bank zipBank = new Bank();
        AccountData kris = zipBank.getAccountData("kyounger");
        ActionResult<AccountData> actual = zipBank.login(kris.getId(),"9876");
        Assert.assertEquals("Invalid login!", actual.getErrorMessage());
    }

    @Test
    public void depositEmptyFieldTest(){
        Bank zipBank = new Bank();
        AccountData kris = zipBank.getAccountData("kyounger");
        ActionResult<AccountData> actual = zipBank.deposit( kris ,null);
        Assert.assertEquals("Deposit failed: Invalid input!", actual.getErrorMessage());
    }

    @Test
    public void withdrawEmptyFieldTest(){
        Bank zipBank = new Bank();
        AccountData kris = zipBank.getAccountData("kyounger");
        ActionResult<AccountData> actual = zipBank.withdraw( kris ,null);
        Assert.assertEquals("Withdraw failed: Invalid input!", actual.getErrorMessage());
    }


    @Test
    public void withdrawPremiumUserOverDraftTest() {
        Bank zipBank = new Bank();
        AccountData kris = zipBank.getAccountData("kyounger");
        ActionResult<AccountData> actual = zipBank.withdraw(kris, "120.5");
        Assert.assertEquals("Overdraft Warning!", actual.getSpecialMessage());
    }

    @Test
    public void withdrawOverDraftFailedTest() {
        Bank zipBank = new Bank();
        AccountData kris = zipBank.getAccountData("kyounger");
        ActionResult<AccountData> actual = zipBank.withdraw(kris, "205");
        Assert.assertEquals("Withdraw failed: Exceed overdraft limit.", actual.getErrorMessage());
    }

    @Test
    public void withdrawBasicUserOverDraftTest() {
        Bank zipBank = new Bank();
        AccountData david = zipBank.getAccountData("dcomer");
        ActionResult<AccountData> actual = zipBank.withdraw(david, "120.5");
        Assert.assertEquals("Withdraw failed: Balance not enough.", actual.getErrorMessage());
    }

    @Test
    public void withdrawNegativeInputTest() {
        Bank zipBank = new Bank();
        AccountData david = zipBank.getAccountData("dcomer");
        ActionResult<AccountData> actual = zipBank.withdraw(david, "-20");
        Assert.assertEquals("Withdraw failed: Negative input not allowed.", actual.getErrorMessage());
    }

    @Test
    public void withdrawTest() {
        Bank zipBank = new Bank();
        AccountData david = zipBank.getAccountData("dcomer");
        ActionResult<AccountData> actual = zipBank.withdraw(david, "50");
        Assert.assertTrue(actual.isSuccess());
    }

    @Test
    public void depositNegativeInputTest() {
        Bank zipBank = new Bank();
        AccountData david = zipBank.getAccountData("dcomer");
        ActionResult<AccountData> actual = zipBank.deposit(david, "-20");
        Assert.assertEquals("Deposit failed: Negative input not allowed.", actual.getErrorMessage());
    }

    @Test
    public void depositTest() {
        Bank zipBank = new Bank();
        AccountData david = zipBank.getAccountData("dcomer");
        ActionResult<AccountData> actual = zipBank.deposit(david, "200");
        Assert.assertTrue(actual.isSuccess());
    }


}