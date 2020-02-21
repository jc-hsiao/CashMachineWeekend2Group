package rocks.zipcode.atm.bank;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    //premium withdrawal test
    @Test
    public void canWithdrawTest(){
        PremiumAccount billGates = new PremiumAccount(new AccountData(123,"William Gates","moe.money@gatesestate.com",1000000,0000));
        boolean actual = billGates.canWithdraw(1000050);
        boolean expected = true;
        Assert.assertEquals(expected,actual);
    }
}