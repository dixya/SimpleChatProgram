/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import static app.Assignment09.timeCards;
import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.client.InvoiceClient;
import com.scg.util.ListFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dixya
 */
public class Assignment09 {

    /**
     * Initializes list of client accounts *
     */
    static List<ClientAccount> CA = new ArrayList<>();
    static List<Consultant> CO = new ArrayList<>();
    static List<TimeCard> timeCards = new ArrayList<>();

    /**
     * Test the Solution 09 Client.
     *
     * @param args Command line parameters - not used.
     * @throws java.lang.Exception if any occur
     */
    public static void main(String[] args) throws Exception {
        ListFactory.populateLists(CA, CO, timeCards);
        final InvoiceClient client1 = new InvoiceClient("127.0.0.1", Assignment09Server.port, timeCards);
        final InvoiceClient client2 = new InvoiceClient("127.0.0.1", Assignment09Server.port, timeCards);
        final InvoiceClient client3 = new InvoiceClient("127.0.0.1", Assignment09Server.port, timeCards);
        final InvoiceClient client4 = new InvoiceClient("127.0.0.1", Assignment09Server.port, timeCards);

        client1.start();
        client2.start();
        client3.start();
        client4.start();
        client1.join();
        client2.join();
        client3.join();
        client4.join();

        /**
         * Sends shutdown command
         */
        InvoiceClient.sendShutdown("127.0.0.1", Assignment09Server.port);

    }

}
