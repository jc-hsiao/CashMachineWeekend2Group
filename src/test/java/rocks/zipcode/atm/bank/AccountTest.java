package rocks.zipcode.atm.bank;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    //premium withdrawal test
    @Test
    public void canWithdrawTestPremium(){

        PremiumAccount billGates = new PremiumAccount(new AccountData(123,"William Gates","moe.money@gatesestate.com",100000000.00, "9999"));
        boolean actual = billGates.canWithdraw(1000050);
        boolean expected = true;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void canWithdrawBasic(){
        BasicAccount oliverTwist = new BasicAccount(new AccountData(123,"Ollie Spin","moe.please@billsikes.com",1.00,"0000"));
        boolean actual = oliverTwist.canWithdraw(1000050);
        boolean expected = false;
        Assert.assertEquals(expected,actual);
    }


}