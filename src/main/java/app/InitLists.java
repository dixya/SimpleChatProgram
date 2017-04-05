/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.util.ListFactory;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dixya
 */
public class InitLists implements Serializable {

    /**
     * This class' logger.
     */
    private static final Logger log = LoggerFactory.getLogger(InitLists.class);
    private static final long serialVersionUID = 1L;

    /**
     * Serializes the list of clientaccount and timecard.
     *
     * @param account list of type clientaccount.
     * @param timeCard list of type timecard.
     */
    public static void serializeLists(List<ClientAccount> account, List<TimeCard> timeCard) {
        try {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ClientList.ser"))) {
                out.writeObject(account);
            }
        } catch (IOException ex) {
            log.error("Serialization of clientaccount failed", ex);
        }
        try {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("TimeCardList.ser"))) {
                out.writeObject(timeCard);
            }
        } catch (IOException ex) {
            log.error("Serialization of timecard failed", ex);
        }

    }

    /**
     * InitLists application method.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // Create lists to be populated by list factory
        final List<Consultant> consultants = new ArrayList<>();
        final List<TimeCard> timeCards = new ArrayList<>();
        final List<ClientAccount> accounts = new ArrayList<>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        //serialize the lists 
        serializeLists(accounts, timeCards);

    }

}
