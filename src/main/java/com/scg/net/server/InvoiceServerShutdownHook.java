/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.server;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dixya
 */
public class InvoiceServerShutdownHook extends Thread {

    private final List<ClientAccount> CA;
    private final List<Consultant> CO;
    private final String outputDirectoryName;
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(InvoiceServerShutdownHook.class);

    /**
     * Construct an InvoiceServerShutDownHook.
     *
     * @param clientList the ClientList to serialize.
     * @param consultantList the ConsultantList to serialize.
     * @param outputDirectoryName name of directory to write output to
     */
    public InvoiceServerShutdownHook(List<ClientAccount> clientList, List<Consultant> consultantList, String outputDirectoryName) {
        this.CA = clientList;
        this.CO = consultantList;
        this.outputDirectoryName = outputDirectoryName;

    }

    /**
     * Called by the Runtime when a shutdown signal is received. This will write
     * the client and consultant lists to file, then shut down after
     * SHUTDOWN_DELAY_SECONDS seconds.
     *
     */
    @Override
    public void run() {
        
        File serverDirectory = new File(outputDirectoryName);
        if (serverDirectory.exists() || serverDirectory.mkdirs()) {
            File clientFile = new File(serverDirectory, "clientList.txt");
            File consultantFile = new File(serverDirectory, "consultantList.txt");

            try {
                PrintStream clientOutput = new PrintStream(new FileOutputStream(clientFile));
                PrintStream consultantOutput = new PrintStream(new FileOutputStream(consultantFile));
                LOG.info("Writing client and consultant lists into text file...");
                synchronized (CA) {
                    CA.stream().forEach((ca) -> {
                        clientOutput.println(ca);
                    });
                }
                synchronized (CO) {
                    CO.stream().forEach((co) -> {
                        consultantOutput.println(co);
                    });
                }
            } catch (IOException ex) {
                LOG.error("Error occurred in writing into file" + ex);
                try {
                    throw (ex);
                } catch (IOException ex1) {
                    Logger.getLogger(InvoiceServerShutdownHook.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        // }

        for (int i = 10; i > 0; i--) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                LOG.info("shutdown delay interrupted" + ex);
            }
            System.err.println("System is shutting down in" + i + "secs...");

        }
        //close connection
        System.out.println("Shutdown completed....");

    }
}
