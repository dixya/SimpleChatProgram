/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dixya
 */
public class ClientAccountTest {

    Name n = new Name("Coyote", "Wiley");
    Address address = new Address("32", "lynnwood", StateCode.valueOf("WA"), "98036");

    public ClientAccountTest() {
    }

    @Test
    public void testClientAccount() {
        ClientAccount clientAccount = new ClientAccount("Dixya Lamichhane", n, address);
        Assert.assertEquals(n, clientAccount.getContact()); //tests getName of clientAccount.
        Assert.assertEquals("Dixya Lamichhane", clientAccount.getName());
        Assert.assertEquals(address, clientAccount.getAddress());
    }

    @Test
    public void testGetSetClientContact() {
        Name client = new Name("Hello", "World");
        client.setFirstName("Hello");
        client.setMiddleName("Everyone");
        client.setLastName("World");
        Assert.assertEquals("World", client.getLastName());
        Assert.assertEquals("Hello", client.getFirstName());
        Assert.assertEquals("Everyone", client.getMiddleName());

    }
    @Test
    public void testClientAccountComparable(){
        Name client=new Name("Hello","World");
        int result=client.compareTo(new Name("Hello","World"));
        Assert.assertEquals(0,result);
        
    }

}
