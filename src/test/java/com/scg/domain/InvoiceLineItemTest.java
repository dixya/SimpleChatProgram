/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

import com.scg.util.Name;
import java.time.LocalDate;
import java.time.Month;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author dixya
 */
public class InvoiceLineItemTest {
    private final Consultant newConsultant=new Consultant(new Name("Dixya","Lamichhane"));

    
    public InvoiceLineItemTest() {
        
        
    }
    @Test
    public void testInvoiceLineItemConstructor(){
        InvoiceLineItem newItem=new InvoiceLineItem(LocalDate.of(1, Month.MARCH, 1), newConsultant, Skill.PROJECT_MANAGER, 5);
        Assert.assertEquals(LocalDate.of(1,Month.MARCH,1), newItem.date);
        Assert.assertEquals(newItem.getConsultant(), newConsultant);
        Assert.assertEquals(newItem.getSkill(), Skill.PROJECT_MANAGER);
        Assert.assertEquals(5, newItem.getHours());
        
    }

    
}
