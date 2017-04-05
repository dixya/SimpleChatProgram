/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

import com.scg.util.StateCode;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dixya
 */
public class StateCodeTest {
    //private StateCode stateCode;
    
    public StateCodeTest() {
    }

    @Test
    public void testStateCode(){
       assertEquals("WA",StateCode.WA.toString());
        
    }
}
