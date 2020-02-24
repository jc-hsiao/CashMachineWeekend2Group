package rocks.zipcode.atm.bank;

import org.junit.Assert;
import org.junit.Test;
import rocks.zipcode.atm.ActionResult;

import static org.junit.Assert.*;

public class AccountDataTest {
    @Test
    public void toStringTest(){

        Bank zipBank = new Bank();
        String actual = zipBank.getAccountData("dkelly").toString();
        String expected = "Account id: dkelly\n"+
                        "Name: David Kelly\n" +
                        "Email: dkelly@zipcode.com\n" +
                        "Balance: 100.0";

        Assert.assertEquals(expected,actual);
    }

}