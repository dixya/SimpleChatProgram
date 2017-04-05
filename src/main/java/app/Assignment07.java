/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Invoice;
import com.scg.persistent.DbServer;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dixya
 */
public class Assignment07 {

    private static final Logger LOG = LoggerFactory.getLogger(Assignment07.class);

    /**
     * Retrieve the list of clients and creates the invoices
     *
     * @param args
     * @throws SQLException in case of SQL error.
     * @throws IOException in case of input or output error.
     */
    public static void main(String[] args) throws SQLException, IOException {
        String db = "jdbc:derby://localhost:1527/memory:scgDb";
        String user = "student";
        String pass = "student";

        DbServer newDb = new DbServer(db, user, pass);
        List<ClientAccount> client = new ArrayList<>();
        List<Invoice> invoices = new ArrayList<>();

        client.addAll(newDb.getClients());
        Invoice invoice;
        for (ClientAccount CA : client) {
            invoice = newDb.getInvoice(CA, Month.MARCH, 2006);

            invoices.add(invoice);

        }

        PrintStream writer;
        try {
            writer = new PrintStream(new FileOutputStream("invoices.txt"));
            printInvoices(invoices, writer);
        } catch (final IOException ex) {
            LOG.error("Unable to print invoice.", ex);
        }

        printInvoices(invoices, System.out);

    }

    /**
     * Print the invoice to a PrintStream.
     *
     * @param invoices the invoices to print
     * @param out The output stream; can be System.out or a text file.
     */
    private static void printInvoices(final List<Invoice> invoices, final PrintStream out) throws IOException {

        invoices.stream().forEach((invoice) -> {
            out.println(invoice.toReportString());
        });

    }

}
