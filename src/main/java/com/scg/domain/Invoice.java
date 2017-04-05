package com.scg.domain;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.util.Address;
import com.scg.util.StateCode;

/**
 * Invoice encapsulates the attributes and behavior to create client invoices
 * for a given time period from time cards.  The invoicing business' name and
 * address are obtained from a properties file. The name of the property file
 * is specified by the PROP_FILE_NAME static member.
 *
 * @author Russ Moul
 */
public final class Invoice {
    /** Name of property file containing invoicing business info. */
    private static final String PROP_FILE_NAME = "/invoice.properties";

    /** Property containing the invoicing business name. */
    private static final String BUSINESS_NAME_PROP = "businessName";

    /** Property containing the invoicing business street address. */
    private static final String BUSINESS_STREET_PROP = "businessStreet";

    /** Property containing the invoicing business city. */
    private static final String BUSINESS_CITY_PROP = "businessCity";

    /** Property containing the invoicing business state. */
    private static final String BUSINESS_STATE_PROP = "businessState";

    /** Property containing the invoicing business zip or postal code. */
    private static final String BUSINESS_ZIP_PROP = "businessZip";

    /** String constant for "N/A". */
    private static final String NA = "N/A";

    /** Items per page. */
    private static final int ITEMS_PER_PAGE = 5;

    /** This class' logger. */
    private static final Logger log = LoggerFactory. getLogger(Invoice.class);

    /** Format string for the time card string representation. */
    private static final String TO_STRING_FORMAT = "Invoice for: %s, Date: %2$tb %2$td,%2$tY\n";

    /** This business' name. */
    public static final String bizName;

    /** This business' address. */
    private static final Address bizAddress;

    /**
     * Obtain the business name and address properties.
     */
    static {
        final Properties invoiceProps = new Properties();
        try (InputStream in = Invoice.class.getResourceAsStream(PROP_FILE_NAME)) {
            invoiceProps.load(in);
        } catch (final IOException e) {
            log.warn("Unable to read properties file.", e);
        }
        bizName = invoiceProps.getProperty(BUSINESS_NAME_PROP, NA);
        final String bizStreet = invoiceProps.getProperty(BUSINESS_STREET_PROP, NA);
        final String bizCity = invoiceProps.getProperty(BUSINESS_CITY_PROP, NA);
        final String bizState = invoiceProps.getProperty(BUSINESS_STATE_PROP);
        final String bizZip = invoiceProps.getProperty(BUSINESS_ZIP_PROP, NA);
        bizAddress = new Address(
                bizStreet, bizCity, StateCode.valueOf(bizState), bizZip);
    }

    /** Client for this Invoice. */
    private final ClientAccount client;

    /** Start date for this Invoice. */
    private final LocalDate startDate;

    /** Date of this Invoice. */
    private final LocalDate invoiceDate;

    /** Total hours for this Invoice. */
    private int totalHours;

    /** Total charges for this Invoice. */
    private int totalCharges;

    /** Container for line items. */
    private final List<InvoiceLineItem> lineItems;

    /**
     * Construct an Invoice for a client. The time period is set from the
     * beginning to the end of the month specified.
     *
     * @param client Client for this Invoice.
     * @param invoiceMonth Month for which this Invoice is being created.
     * @param invoiceYear Year for which this Invoice is being created.
     */
    public Invoice(final ClientAccount client, final Month invoiceMonth,
                   final int invoiceYear) {
        this.client = client;
        this.lineItems = new ArrayList<>();

        // Get today's date.
        this.invoiceDate = LocalDate.now();

        // Start date is the first of the month.
        startDate = LocalDate.of(invoiceYear, invoiceMonth, 1);
    }

    /**
     * Get the start date for this Invoice, this is the earliest date/time a
     * ConsultantTime instance may have and still be billed on this invoice.
     *
     * @return Start date.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Get the invoice month. This is the 1-based month number.
     *
     * @return the invoice month number.
     */
    public Month getInvoiceMonth() {
    	return startDate.getMonth();
    }

    /**
     * Get the client for this Invoice.
     *
     * @return the client.
     */
    public ClientAccount getClientAccount() {
        return client;
    }

    /**
     * Get the total hours for this Invoice.
     *
     * @return Total hours.
     */
    public int getTotalHours() {
        return totalHours;
    }

    /**
     * Get the total charges for this Invoice.
     *
     * @return Total charges.
     */
    public int getTotalCharges() {
        return totalCharges;
    }

    /**
     * Add an invoice line item to this Invoice.
     *
     * @param lineItem InvoiceLineItem to add.
     */
    public void addLineItem(final InvoiceLineItem lineItem) {
        lineItems.add(lineItem);
        totalHours += lineItem.getHours();
        totalCharges += lineItem.getCharge();
    }

    /**
     * Extract the billable hours for this Invoice's client from the input
     * TimeCard and add them to the line items.  Only those hours for the client
     * and month unique to this invoice will be added.
     *
     * @param timeCard the TimeCard potentially containing line items for this
     *                 Invoices client.
     */
    public void extractLineItems(final TimeCard timeCard) {
        final List<ConsultantTime> billableHoursList = timeCard.getBillableHoursForClient(client.getName());
        billableHoursList.stream().forEach((consultantTime) -> {
            final LocalDate currentDate = consultantTime.getDate();
            if (currentDate.getMonth() == startDate.getMonth()) {
                final InvoiceLineItem currentItem = new InvoiceLineItem(
                  currentDate,
                  timeCard.getConsultant(),
                  consultantTime.getSkill(),
                  consultantTime.getHours());
                addLineItem(currentItem);
            }
        });
    }

    /**
     * Create a string representation of this object, suitable for printing.
     *
     * @return string containing this invoices client name and billing start date
     */
    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, client.getName(), startDate);
    }
    
    /**
     * Create a formatted string containing the printable invoice. Includes a
     * header and footer on each page.
     *
     * @return The formatted invoice as a string.
     */
    public String toReportString() {
        final InvoiceHeader invoiceHeader = new InvoiceHeader(bizName, bizAddress, client,
                                                              invoiceDate, startDate.getMonth());
        final InvoiceFooter invoiceFooter = new InvoiceFooter(bizName);

        final StringBuilder sb = new StringBuilder();
        final Formatter formatter = new Formatter(sb, Locale.US);

        formatter.format("%s", invoiceHeader);

        for (int i = 0, itemsPrinted = 1; i < lineItems.size(); i++, itemsPrinted++) {
            final InvoiceLineItem invoiceLineItem = lineItems.get(i);
            formatter.format("%s%n", invoiceLineItem);

            if (itemsPrinted % ITEMS_PER_PAGE == 0) {
                invoiceFooter.incrementPageNumber();
                formatter.format("%s%n%s", invoiceFooter, invoiceHeader);
                
            }
        }
        invoiceFooter.incrementPageNumber();

        formatter.format("%nTotal: %50d  %,10.2f", totalHours, (double)totalCharges)
                 .format("%s", invoiceFooter);
        final String s = formatter.toString();
        formatter.close();
        return s;
    }
}