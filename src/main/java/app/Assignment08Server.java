/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.server.CommandProcessor;
import com.scg.net.server.InvoiceServer;
import com.scg.util.ListFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dixya
 */
public class Assignment08Server {

    private static final Logger LOG = LoggerFactory.getLogger(Assignment08Server.class);
    public static int port = 10888;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        List<ClientAccount> CA = new ArrayList<>();
        List<Consultant> CO = new ArrayList<>();
        List<TimeCard> timeCards = new ArrayList<>();
        ListFactory.populateLists(CA, CO, timeCards);
        final InvoiceServer server = new InvoiceServer(port, CA, CO, "target/server");
        server.run();
        //server.shutdown();

    }

}
