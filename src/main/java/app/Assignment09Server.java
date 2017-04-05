/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.server.InvoiceServer;
import com.scg.util.ListFactory;    
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dixya
 */
public class Assignment09Server {

    static int port=10888;
    /** Instantiates an InvoiceServer and starts it
     * @param args Command line parameters.
     * @throws java.lang.Exception if exception occurs.
     */
     public static void main(String[] args) throws Exception{
          List<ClientAccount> CA = new ArrayList<>();
        List<Consultant> CO = new ArrayList<>();
        List<TimeCard> timeCards = new ArrayList<>();
        ListFactory.populateLists(CA, CO, timeCards);
        final InvoiceServer server = new InvoiceServer(port, CA, CO, "target/server");
        server.run();
       // server.shutdown();
        
    }
}
