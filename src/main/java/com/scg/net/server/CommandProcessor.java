/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.server;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.net.cmd.AddClientCommand;
import com.scg.net.cmd.AddConsultantCommand;
import com.scg.net.cmd.AddTimeCardCommand;
import com.scg.net.cmd.Command;
import com.scg.net.cmd.CreateInvoicesCommand;
import com.scg.net.cmd.DisconnectCommand;
import com.scg.net.cmd.ShutdownCommand;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The command processor for the invoice server. Implements the receiver role in
 * the Command design pattern.
 *
 * @author dixya
 */
public class CommandProcessor implements Runnable {

    public Socket connection;
    public final List<ClientAccount> clientList;
    public final List<Consultant> consultantList;
    public final InvoiceServer server;
    final List<TimeCard> TimeCardList;
    private static final Logger LOG = LoggerFactory.getLogger(CommandProcessor.class);
    Invoice newInvoice;
    String outputDirectoryName;

    /**
     * Construct a CommandProcessor.
     *
     * @param connection the Socket connecting the server to the client.
     * @param clientList the ClientList to add Clients to.
     * @param consultantList the ConsultantList to add Consultants to.
     * @param server the server that created this command processor
     */
    public CommandProcessor(Socket connection, List<ClientAccount> clientList, List<Consultant> consultantList, InvoiceServer server) {
        this.connection = connection;
        this.clientList = clientList;
        this.consultantList = consultantList;
        this.server = server;
        TimeCardList = new ArrayList<>();

    }

    /**
     * Execute an AddClientCommand.
     *
     * @param command the command to execute.
     */
    public void execute(AddClientCommand command) {
        ClientAccount newAccount = command.getTarget();
        synchronized (clientList) {
            if (!clientList.contains(newAccount)) {
                clientList.add(newAccount);
            }

        }

    }

    /**
     * Execute and AddConsultantCommand.
     *
     * @param command the command to execute.
     */
    public void execute(AddConsultantCommand command) {
        Consultant consultant = command.getTarget();
        synchronized (consultantList) {
            if (!consultantList.contains(consultant)) {
                consultantList.add(consultant);
            }

        }

    }

    /**
     * Execute and AddTimeCardCommand.
     *
     * @param command the command to execute.
     */
    public void execute(AddTimeCardCommand command) {
        TimeCard timeCard = command.getTarget();
        synchronized(TimeCardList){
            if(!TimeCardList.contains(timeCard)){
                        TimeCardList.add(timeCard);

            }
        }

    }

    /**
     * Execute a CreateInvoicesCommand.
     *
     * @param command the command to execute.
     * @throws java.io.FileNotFoundException if file is not found.
     */
    public void execute(CreateInvoicesCommand command) throws FileNotFoundException {

        LocalDate date = command.getTarget();
        clientList.stream().map((CA) -> {
            newInvoice = new Invoice(CA, date.getMonth(), date.getYear());
            return CA;
        }).forEach((CA) -> {
            TimeCardList.stream().forEach((TC) -> {
                newInvoice.extractLineItems(TC);
            });
            if (newInvoice.getTotalHours() > 0) {
                //final String outFileName
                final String outFileName = String.format("%s%sInvoice.txt", CA.getName().replaceAll(" ", ""), date.getMonth());
                final File outputFile = new File(outputDirectoryName, outFileName);
                PrintStream printOutput = null;

                try {
                    printOutput = new PrintStream(new FileOutputStream(outputFile), true);
                    printOutput.println(newInvoice.toReportString());

                } catch (FileNotFoundException ex) {
                    LOG.info("Cannot open the file" + outFileName + ex);
                }

            }
        });
    }

    /**
     * Execute a DisconnectCommand.
     *
     * @param command the command to execute.
     * @throws java.io.IOException
     */
    public void execute(DisconnectCommand command) throws IOException {
        LOG.info("received disconnect command ..");
        connection.close();
    }

    /**
     * Execute a ShutdownCommand.
     *
     * @param command the command to execute.
     */
    public void execute(ShutdownCommand command) {
        try {
            server.shutdown();
           
        } catch (Exception e) {
            LOG.error("connection is not closed !!!" + e);
        }

    }

    /**
     * Set the output directory name.
     *
     * @param outputDirectoryName the output directory name.
     */
    public void setOutputDirectoryName(String outputDirectoryName) {

        this.outputDirectoryName = outputDirectoryName;
    }

    @Override
    public void run() {
        try {
            LOG.info("Connection=" + connection);

            connection.shutdownOutput();
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());

           // synchronized (in) {
                this.setOutputDirectoryName(outputDirectoryName);
                while (!connection.isClosed()) {
                    Object obj = in.readObject();
                    if (obj == null) {
                        connection.close();
                    } else if (obj instanceof Command<?>) {
                        final Command<?> command = (Command<?>) obj;
                        LOG.info("command received of type :" + command.getClass().getSimpleName());
                        command.setReceiver(this);
                        command.execute();
                    } else {
                        LOG.warn("Received object but not a command : ", obj.getClass().getSimpleName());
                    }
                }
           // }
        } catch (IOException | ClassNotFoundException ex) {
            // java.util.logging.Logger.getLogger(InvoiceServer.class.getName()).log(Level.SEVERE, null, ex);
            LOG.info("error occurred in service connection " + ex);
        }
    }
}
