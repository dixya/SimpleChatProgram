/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.net.server;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The server for creation of account invoices based on time cards sent from the
 * client.
 *
 * @author dixya
 */
public class InvoiceServer {

    private final int port;
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServer.class);

    private final List<ClientAccount> clientList;
    private final List<Consultant> consultantList;
    private final String outputDirectoryName;
    ServerSocket servSock;
    

    /**
     * Construct an InvoiceServer with a port.
     *
     * @param port The port for this server to listen on
     * @param clientList the initial list of clients
     * @param consultantList the initial list of consultants
     * @param outputDirectoryName the directory to be used for files output by
     * commands.
     */
    public InvoiceServer(int port, List<ClientAccount> clientList, List<Consultant> consultantList, String outputDirectoryName) {
        this.port = port;
        this.clientList = clientList;
        this.consultantList = consultantList;
        this.outputDirectoryName = outputDirectoryName;
        Runtime.getRuntime().addShutdownHook(new InvoiceServerShutdownHook(clientList,consultantList,outputDirectoryName));
    }

    /**
     * Run this server, establishing connections, receiving commands, and
     * sending them to the CommandProcesser.
     */
    public void run() {
        try {
            servSock = new ServerSocket(port);
            // this.servSock=servSock;

            LOG.info("InvoiceServer is running....");
            System.out.println("Server ready on port " + port + "...");

            while (servSock != null && !servSock.isClosed()) {
               // try (Socket clientSocket = servSock.accept()) { don't need to do this
               try{
                   Socket clientSocket=servSock.accept();
                    LOG.info("Got a new connection from client");
                    /**
                     * connection to command processor
                     */
                    CommandProcessor CP = new CommandProcessor(clientSocket, clientList, consultantList, this);
                    final File serverDirectory=new File(outputDirectoryName);
                    if(serverDirectory.exists() || serverDirectory.mkdirs()){
                    CP.setOutputDirectoryName(serverDirectory.getAbsolutePath());
                        Thread t=new Thread(CP);
                    t.start();
                    }else{
                        LOG.error("unable to create output directory"+serverDirectory.getAbsolutePath());
                    }
                } catch (SocketException e) {
                    LOG.info("server socket is closed", e);
                    throw e;
                }
            }
        } catch (IOException ex) {
            System.err.println("Server socket can't bind to port: " + ex);
            throw new RuntimeException(ex);
        }

    }

    /**
     * Shutdown the server.
     */
    void shutdown() {

        try {
            if (servSock != null && !servSock.isClosed()) {
                servSock.close();
                System.out.print("server socket is closed");
            }
        } catch (IOException ioex) {
            System.err.println("Error closing server socket. " + ioex);
        }
    }

   

}
