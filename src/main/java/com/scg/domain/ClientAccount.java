/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * Creates a new instance of ClientAccount.
 * @author dixya
 */
public class ClientAccount implements Account,Comparable<ClientAccount>,Serializable{
    //private 
    private  String name;
    private  Name contact;
    private Address address;
    public ClientAccount(String name, Name contact,Address address){
        this.name=name;
        this.contact=contact;
        this.address=address;
    }

    /**
     * Getter for the name
     * @return name of the account
     */
    @Override
    public String getName() {
                
        return name;
        
    }
    /**
     * Checks if the account is billable
     * @return boolean vlaue
     */
    @Override
    public boolean isBillable() {
        return true;
    }
    /**
     * Getter for the contact
     * @return contact
     */
    public Name getContact(){
       
        return contact;
        
    }
    
    /**
     * Setter for the contact
     * @param contact ClientContact name
     */
    public void setContact(Name contact){
         String clientContactLastName=this.contact.getLastName();
        String clientContactFirstName=this.contact.getFirstName();
        String clientContactMiddleName=this.contact.getMiddleName();
        
        this.contact=new Name(clientContactLastName,clientContactFirstName,clientContactMiddleName);
        
    }
    /**
     * Getter for the address
     * @return address
     */
    public Address getAddress(){
        return this.address;
    }
    /**
     * Setter for the address
     * 
     * @throws java.io.IOException
     */
    public void setAddress() throws IOException{
        
            Properties prop = new Properties();
            String propFileName="invoice.properties";
            InputStream inputStream=getClass().getClassLoader().getResourceAsStream(propFileName);
            try{	
            if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
        String addressStreet=prop.getProperty("businessStreet");
        String addressCity=prop.getProperty("businessCity");
        String addressState=prop.getProperty("businessState");
        String addressZip=prop.getProperty("businessZip");        
        this.address=new Address(addressStreet,addressCity,StateCode.valueOf(addressState),addressZip);

    
        		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
    
}

    @Override
    public int compareTo(ClientAccount t) {
        int nameResult=(this.getName()).compareTo(t.getName());
           if(nameResult!=0) return nameResult;
        int contactResult=(this.getContact()).compareTo(t.getContact());
            if(contactResult!=0) return contactResult;
        return address.compareTo(t.address);
        
    }

    @Override
    public String toString() {
        return "ClientAccount{" + "name=" + name + ", contact=" + contact + ", address=" + address + '}';
    }
}
