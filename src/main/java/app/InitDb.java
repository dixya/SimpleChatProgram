/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.persistent.DbServer;
import com.scg.util.ListFactory;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The initialize or populate the database.
 *
 * @author dixya
 */
public class InitDb {

    private static final Logger LOG = LoggerFactory.getLogger(InitDb.class);

    /**
     * Entry point.
     *
     * @param args
     * @throws Exception if anything goes awry .
     */
    //Create the Client, Consultant and TimeCard lists using the ListFactory. The contents of the lists are then added to the database using the DbServer class as appropriate.
    public static void main(String[] args) throws Exception {
        final List<ClientAccount> accounts = new ArrayList<>();
        final List<Consultant> consultants = new ArrayList<>();
        final List<TimeCard> timeCards = new ArrayList<>();
        ListFactory.populateLists(accounts, consultants, timeCards);

        Connection conn = null;
        String db = "jdbc:derby://localhost:1527/memory:scgDb";
        String user = "student";
        String pass = "student";

        DbServer newServer = new DbServer(db, user, pass);
        for (ClientAccount CA : accounts) {
            newServer.addClient(CA);
        }
        for (Consultant C : consultants) {

            newServer.addConsultant(C);
        }
        for (TimeCard TC : timeCards) {
            newServer.addTimeCard(TC);
        }

    }
}
