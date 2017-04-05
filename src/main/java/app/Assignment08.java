/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

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
public class Assignment08 {

    /**
     * Instantiates an InvoiceClient, provides it with a set of timecards to
     * server the server and starts it running.
     *
     * @param args Command line parameters, not used
     * @throws Exception if anything goes awry.
     */
    public static void main(String[] args) throws Exception {
        //int port=10888;
        /**
         * Initializes list of client accounts *
         */
        final List<ClientAccount> accounts = new ArrayList<>();
        /**
         * Initializes list of consultants
         */

        final List<Consultant> consultants = new ArrayList<>();
        /**
         * Initializes list of timecards
         */

        final List<TimeCard> timeCards = new ArrayList<>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        /**
         * Constructs an instance of invoiceClient and sends timecards list to
         * the server
         */
        final InvoiceClient client = new InvoiceClient("127.0.0.1", Assignment08Server.port, timeCards);
        client.run();
        /**
         * Sends shutdown command
         */
        InvoiceClient.sendShutdown("127.0.0.1", Assignment08Server.port);

    }

}
