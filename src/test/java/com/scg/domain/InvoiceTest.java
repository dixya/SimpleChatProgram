/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dixya
 */
public class InvoiceTest {

    Name n = new Name("Coyote", "Wiley");
    Address address = new Address("32", "lynnwood", StateCode.valueOf("WA"), "98036");
    ClientAccount clientAccount = new ClientAccount("Dixya Lamichhane", n, address);
    private final Consultant newConsultant = new Consultant(new Name("Dixya", "Lamichhane"));
    private final InvoiceLineItem newItem = new InvoiceLineItem(LocalDate.of(1, Month.MARCH, 1), newConsultant, Skill.PROJECT_MANAGER, 5);
    List<InvoiceLineItem> lineItems = new ArrayList<>();
    TimeCard timeCard = new TimeCard(newConsultant, LocalDate.of(1992, Month.MARCH, 1));
    Invoice newInvoice = new Invoice(clientAccount, Month.MARCH, 1992);

    public InvoiceTest() {
    }

    @Test
    public void testInvoiceConstructor() {
        Assert.assertEquals(newInvoice.getInvoiceMonth(), Month.MARCH);
        Assert.assertEquals(newInvoice.getClientAccount(), clientAccount);
        Assert.assertEquals(newInvoice.getStartDate(), LocalDate.of(1992, Month.MARCH, 1));

    }

    @Test
    public void testAddLineItems() {
        lineItems.add(newItem);
        for (InvoiceLineItem item : lineItems) {
            assertEquals(item.getHours(), 5);
            assertEquals(item.getSkill(), Skill.PROJECT_MANAGER);
            assertEquals(item.getConsultant(), newConsultant);
        }
        //Assert.assertEquals(newInvoice.extractLineItems(timeCard),lineItems.add(newItem));

    }

    @Test
    public void testExtractLineItems() {
        List<ConsultantTime> billableHours = new ArrayList<>();
        billableHours.addAll(timeCard.getBillableHoursForClient(clientAccount.getName()));
        for (ConsultantTime ct : billableHours) {
            assertEquals(newInvoice.getTotalHours(), ct.getHours());
            assertEquals(newInvoice.getStartDate(), ct.getDate());
            assertEquals(newInvoice.getClientAccount(), ct.getAccount());
        }

    }

//    @Test
//    public void testToReportString() {
//        Properties prop = new Properties();
//        String propFileName = "invoice.properties";
//        String bizStreet = prop.getProperty("businessStreet");
//        String bizCity = prop.getProperty("businessCity");
//        String bizState = prop.getProperty("businessState");
//        String bizZip = prop.getProperty("businessZip");
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//
//        Address bizAddress = new Address(bizStreet, bizCity, StateCode.WA, bizZip);
//        InvoiceHeader header = new InvoiceHeader(prop.getProperty("businessName"), bizAddress, clientAccount, newInvoice.getStartDate(), newInvoice.getInvoiceMonth());
//
//        assertEquals(newInvoice.bizName, prop.getProperty("businessName"));
//        assertEquals(newInvoice.bizCity, bizCity);
//        assertEquals(newInvoice.bizState, bizState);
//        assertEquals(newInvoice.bizZip, bizZip);
//
//    }
}
