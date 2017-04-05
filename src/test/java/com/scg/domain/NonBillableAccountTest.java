package com.scg.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for NonBillableAccount class.
 */
public final class NonBillableAccountTest {
    /** NonBillableAccount instance for test. */
    private NonBillableAccount nonbillableaccount;

    /**
     * Perform test setup.
     */
    @Before
    public void setUp() {
        nonbillableaccount = NonBillableAccount.VACATION;
    }

    /**
     * Perform test tear down.
     */
    @After
    public void tearDown() {
        nonbillableaccount = null;
    }

    /**
     * Test the getName method.
     */
    @Test
    public void testGetName() {
        assertEquals("VACATION", nonbillableaccount.getName());
    }
}












///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.scg.domain;
//
//import org.junit.Assert;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// * Test for non billable account.
// * @author dixya
// */
//public class NonBillableAccountTest {
//    
//    public NonBillableAccountTest() {
//    }
//    public void testNonBillableEnum(){
////    Assert.assertEquals(NonBillableAccount.SICK_LEAVE, NonBillableAccount[0]);
////    Assert.assertEquals(NonBillableAccount.VACATION, NonBillableAccount[1]);
////    Assert.assertEquals(NonBillableAccount.BUSINESS_DEVELOPMENT, NonBillableAccount[2]);
//
//
//}
//    
//}
