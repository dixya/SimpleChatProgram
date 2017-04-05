/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.client;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.cmd.AddClientCommand;
import com.scg.net.cmd.AddConsultantCommand;
import com.scg.net.cmd.AddTimeCardCommand;
import com.scg.net.cmd.Command;
import com.scg.net.cmd.CreateInvoicesCommand;
import com.scg.net.cmd.DisconnectCommand;
import com.scg.net.cmd.ShutdownCommand;
import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dixya
 */
public class InvoiceClient extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceClient.class);

    private final String host;
    private final int port;
    private final List<TimeCard> timeCardList;
    int invoiceYear;
    Month invoiceMonth;

   // static Object lock = new Object();

    /**
     * Construct an InvoiceClient with a host and port for the server.
     *
     * @param host address of the host.
     * @param port port number
     * @param timeCardList list of timecards
     */
    public InvoiceClient(String host, int port, List<TimeCard> timeCardList) {
        this.host = host;
        this.port = port;
        this.timeCardList = timeCardList;
    }

    /**
     * Send the command to the server to create invoices for the specified
     * month.
     *
     * @param out instance of outputstream
     * @param month invoice month
     * @param year invoice year.
     */
    public void createInvoices(ObjectOutputStream out, Month month, int year) {
        CreateInvoicesCommand command = new CreateInvoicesCommand(LocalDate.of(year, month, 1));
        //this.invoiceYear=year;
        //this.invoiceMonth=month;
        try {
            sendCommand(out, command);
        } catch (Exception e) {
            LOG.error("error creating invoices");
        }
    }

    /**
     * Runs this InvoiceClient, sending clients, consultants, and time cards to
     * the server, then sending the command to create invoices for a specified
     * month.
     *
     */
    @Override
    public void run() {
        ObjectOutputStream oos;
        try (Socket s = new Socket("127.0.0.1", port);) {
            LOG.info("server connected... port: " + port);
            s.shutdownInput();
            oos = new ObjectOutputStream(s.getOutputStream());
            sendClients(oos);
            sendConsultants(oos);
            oos.writeObject("not a known command");
            sendTimeCards(oos);
            createInvoices(oos, Month.MARCH, 2006);
            sendDisconnect(oos, s);
           // s.shutdownOutput();
           sendShutdown("127.0.0.1",port);
        } catch (Exception ex) {
            //java.util.logging.Logger.getLogger(InvoiceClient.class.getName()).log(Level.SEVERE, null, ex);
            LOG.error("Error in run method of invoiceClient" + ex);
            throw new RuntimeException(ex);
        }

    }

    /**
     * Send the clients to the server.
     *
     * @param out output file.
     * @throws java.io.IOException if error occurs while input or output.
     */
    public void sendClients(ObjectOutputStream out) throws IOException {
        AddClientCommand addClientCommand1 = new AddClientCommand(new ClientAccount("Seattle industries", new Name("Anjan", "Nepal", "Prasad"), new Address("3729", "207", StateCode.WA, "98056")));
        AddClientCommand addClientCommand2 = new AddClientCommand(new ClientAccount("Lynnwood industries", new Name("Orchid", "Care", "Center"), new Address("3700", "297", StateCode.WA, "98066")));
        AddClientCommand addClientCommand3 = new AddClientCommand(new ClientAccount("Redmond industries", new Name("Pumpkin", "Distribution", "Center"), new Address("2300", "240", StateCode.WA, "98125")));
            sendCommand(out, addClientCommand1);

            sendCommand(out, addClientCommand2);
        

            sendCommand(out, addClientCommand3);

    }

    /**
     * Send the consultants to the server.
     *
     * @param out outputstream which connects this client command to the server
     * @throws java.io.IOException if error occurs while input or output.
     */
    public void sendConsultants(ObjectOutputStream out) throws IOException {
        AddConsultantCommand addConsultantCommand1 = new AddConsultantCommand(new Consultant(new Name("Dixya", "Lamichhane", "ms")));
        sendCommand(out, addConsultantCommand1);
        AddConsultantCommand addConsultantCommand2 = new AddConsultantCommand(new Consultant(new Name("Aaron", "Sirah", "maila")));
        sendCommand(out, addConsultantCommand2);

    }

    /**
     * Send the disconnect command to the server.
     *
     * @param out outputstream which connects this client command to the server
     * @param server server socket.
     * @throws java.lang.Exception if error occurs while input or output.
     */
    public void sendDisconnect(ObjectOutputStream out, Socket server) throws Exception {
        final DisconnectCommand command = new DisconnectCommand();
        sendCommand(out, command);
        try {
            server.close();
            LOG.info("disconnect command send");
        } catch (Exception e) {
            LOG.info("server socket is not closed" + e);
            throw (e);
        }
    }

    /**
     * Send the quit command to the server.
     *
     * @param host host that is requesting shutdown.
     * @param port port that needs to get shutdown
     * @throws java.io.IOException if error occurs while input or output.
     */
    public static void sendShutdown(String host, int port) throws IOException {
        try (Socket serverSocket = new Socket(host, port);) {
            LOG.info("Server quitting at " + serverSocket.getInetAddress().getHostName() + "," + serverSocket.getInetAddress().getHostAddress() + "at port" + serverSocket.getPort());
            ObjectOutputStream oos = new ObjectOutputStream(serverSocket.getOutputStream());
            serverSocket.shutdownInput();
            ShutdownCommand command = new ShutdownCommand();
            sendCommand(oos, command);
            serverSocket.close();
            
        } catch (IOException e) {
            LOG.error("Error in shutting down" + e);
        }

    }

    /**
     * Send the time cards to the server.
     *
     * @param out outputstream which connects this client command to the server
     * @throws java.io.IOException if error occurs while input or output.
     */
    public void sendTimeCards(ObjectOutputStream out) throws IOException {
        AddTimeCardCommand addTimeCardCommand;
        for (TimeCard t : timeCardList) {
            addTimeCardCommand = new AddTimeCardCommand(t);
            sendCommand(out, addTimeCardCommand);
        }
        LOG.info("list of timeCards send from client ...");
    }

    /**
     * sends command to the server
     *
     * @param out outputstream which connects this client command to the server
     * @param command command send by the client
     * @throws IOException if error occurs while input or output.
     */

    private static void sendCommand(ObjectOutputStream out, Command<?> command) throws IOException {
        try {
            out.writeObject(command);
            out.flush();
        } catch (IOException ex) {
            LOG.error("Error in writing outputStream" + ex);
            throw (ex);
        }
    }
}
