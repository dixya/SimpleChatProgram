/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

import com.scg.util.Address;
import java.time.LocalDate;
import java.time.Month;
import java.util.Formatter;
import java.util.Locale;

/**
 *
 * @author dixya
 */
public class InvoiceHeader {
    private String businessName;
    private Address businessAddress;
    private ClientAccount client;
    private LocalDate invoiceDate;
    private Month invoiceForMonth;
    private static final String HEADER_FORMAT="%s%n%s%n%nInvoice for:%s%nInvoice For Month of:%4$tB %nInvoice Date: %5$tD%n";
    private static final String LINE_HEADER_FORMAT="Date       Consultant             Skill             Hours       Charge%n"+"------   -----------------  ------------------    ---------   --------------                                  %n";
    /**
     * Construct an InvoiceHeader.
     * @param businessName
     * @param businessAddress
     * @param client
     * @param invoiceDate
     * @param invoiceForMonth 
     */
    public InvoiceHeader(String businessName, Address businessAddress, ClientAccount client, LocalDate invoiceDate, Month invoiceForMonth){
        this.businessName=businessName;
        this.businessAddress=businessAddress;
        this.client=client;
        this.invoiceDate=invoiceDate;
        this.invoiceForMonth=invoiceForMonth;
    }
    /**
     * Print this InvoiceHeader.
     * @return string value.
     */
    @Override
    public String toString(){
        final StringBuilder sb=new StringBuilder();
        final Formatter formatter=new Formatter(sb,Locale.US);
        formatter.format(HEADER_FORMAT,businessName,businessAddress,client.getName(),invoiceForMonth,invoiceDate).format(LINE_HEADER_FORMAT);
        
        final String s=formatter.toString();
        formatter.close();
        return s;
        
        
        
        
        
        
        


        
        
    }
    
}
