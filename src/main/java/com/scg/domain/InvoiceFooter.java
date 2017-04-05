/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

/**
 *
 * @author dixya
 */
public class InvoiceFooter {
    private String businessName;
    int pageNo;
    /**
     * Construct an InvoiceFooter.
     * @param businessName - name of buisness to include in footer.
     */
    public InvoiceFooter(String businessName){
        this.businessName=businessName;
        this.pageNo=0;
    }
    /**
     * Increment the current page number by one.
     */
    void incrementPageNumber(){
        pageNo=pageNo+1;
        
        
    }
    /**
     * Print the formatted footer.
     * @return string value.
     */
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("\n\n"+businessName);
        sb.append("\t\t\t\t\t\t\t\tPage:" );
        sb.append(pageNo+"\n");
        sb.append("===========================================================================================================");
        return sb.toString();
        
    }

    
}
