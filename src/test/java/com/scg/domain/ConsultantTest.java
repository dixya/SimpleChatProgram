/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

import com.scg.util.Name;
import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dixya
 */
public class ConsultantTest {
    
    public ConsultantTest() {
    }
    @Test
    public void testConsultantTest(){
        Consultant newConsultant=new Consultant(new Name("Dixya","Lamichhane"));
        Name newName=new Name("Dixya","Lamichhane");
        assertEquals(newName.toString(), newConsultant.toString());
        
        
        
    }
}
