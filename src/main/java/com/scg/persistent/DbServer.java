/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.persistent;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.ConsultantTime;
import com.scg.domain.Invoice;
import com.scg.domain.InvoiceLineItem;
import com.scg.domain.Skill;
import com.scg.domain.TimeCard;
import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for providing a programmatic interface to store and access
 * objects in the database.
 *
 * @author dixya
 */
public class DbServer {

    private final String url;
    private final String username;
    private final String password;
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(DbServer.class);
    Connection conn = null;
    Statement stmt;

    /**
     * Constructor
     *
     * @param dbUrl url of database.
     * @param username usename of database.
     * @param password password of database.
     * @throws java.sql.SQLException
     */
    public DbServer(String dbUrl, String username, String password) throws SQLException {
        this.url = dbUrl;
        this.username = username;
        this.password = password;

    }

    private void dbConnect() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();

        } catch (SQLException e) {
            LOG.info("Error in database connection" + e);
        }
    }

    /**
     * Add a client to the database.
     *
     * @param client the client to add
     * @throws SQLException if any database operations fail
     */
    public void addClient(ClientAccount client) throws SQLException {
        /** connecting to the database */
        dbConnect();

        try {
            String query = "INSERT INTO CLIENTS(NAME,STREET,CITY,STATE,POSTAL_CODE,CONTACT_LAST_NAME,CONTACT_FIRST_NAME,CONTACT_MIDDLE_NAME) VALUES (?,?,?,?,?,?,?,?)";
            final PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress().getStreetNumber());
            statement.setString(3, client.getAddress().getCity());
            statement.setString(4, client.getAddress().getState().toString());
            statement.setString(5, client.getAddress().getPostalCode());
            statement.setString(6, client.getContact().getLastName());
            statement.setString(7, client.getContact().getFirstName());
            statement.setString(8, client.getContact().getMiddleName());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.info("Error in inserting into client table" + e);
        }
    }

    /**
     * Add a consultant to the database.
     *
     * @param consultant
     * @throws SQLException if any database operations fail.
     */
    public void addConsultant(Consultant consultant) throws SQLException {
        dbConnect();

        try {
            String query = "INSERT INTO CONSULTANTS(LAST_NAME,FIRST_NAME,MIDDLE_NAME) VALUES(?,?,?)";
            final PreparedStatement statement1 = conn.prepareStatement(query);
            statement1.setString(1, consultant.getName().getLastName());
            statement1.setString(2, consultant.getName().getFirstName());
            statement1.setString(3, consultant.getName().getMiddleName());
            statement1.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Failed consultant insertion" + e);
        }
    }

    /**
     * Get all of the clients in the database.
     *
     * @return list of all clients .
     * @throws SQLException if any database operations fail.
     */
    public List<ClientAccount> getClients() throws SQLException {
        dbConnect();

        List<ClientAccount> CA;
        CA = new ArrayList<>();
        String query = "SELECT * FROM CLIENTS";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            final StateCode state = StateCode.valueOf(rs.getString(5));
            String lname = rs.getString("CONTACT_LAST_NAME");
            String fname = rs.getString("CONTACT_FIRST_NAME");
            String mname = rs.getString("CONTACT_MIDDLE_NAME");
            int clientId = rs.getInt(1);

            Address a = new Address(rs.getString("STREET"), rs.getString("CITY"), state, rs.getString("POSTAL_CODE"));
            Name clientContact = new Name(lname, fname, mname);
            String clientName = rs.getString(2);
            CA.add(new ClientAccount(clientName, clientContact, a));
        }
        return CA;
    }

    /**
     * Get all of the consultant in the database.
     *
     * @return list of type consultant.
     * @throws SQLException if any database operations fail.
     */
    public List<Consultant> getConsultants() throws SQLException {
        dbConnect();

        List<Consultant> CO = new ArrayList<>();
        String query = "SELECT * FROM CONSULTANTS";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Name n = new Name(rs.getString("lAST_NAME"), rs.getString("FIRST_NAME"), rs.getString("MIDDLE_NAME"));
            CO.add(new Consultant(n));
        }
        return CO;

    }

    /**
     * Add a timecard to the database.
     *
     * @param timeCard
     * @throws SQLException if any database operations fail.
     */
    public void addTimeCard(TimeCard timeCard) throws SQLException {
        dbConnect();

        Name consultantName = timeCard.getConsultant().getName();
        long day = timeCard.getWeekStartingDay().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Date date = new Date(day);
        String fname = consultantName.getFirstName();
        String lname = consultantName.getLastName();

        String query1 = "SELECT ID FROM CONSULTANTS WHERE LAST_NAME='" + lname + "' AND FIRST_NAME='" + fname + "' ";
        ResultSet consIdSet = stmt.executeQuery(query1);

        if (consIdSet.next()) {
            int consId = consIdSet.getInt(1);
            consIdSet.close();
            try {
                // conn.setAutoCommit(false);
                String timeCardQuery = "INSERT INTO TIMECARDS(CONSULTANT_ID,START_DATE) VALUES(?,?)";
                final PreparedStatement pstatement = conn.prepareStatement(timeCardQuery, Statement.RETURN_GENERATED_KEYS);
                pstatement.setInt(1, consId);
                pstatement.setObject(2, date);
                pstatement.executeUpdate();
                ResultSet tSet = pstatement.getGeneratedKeys();

                while (tSet.next()) {
                    int timeCardId = tSet.getInt(1);
                    addHours(timeCardId, timeCard);
                    // tSet.close();
                }

            } catch (SQLException e) {
                LOG.error("insertion in timecard failed" + e);
            }
        }
    }

    /**
     * Adds values to the table BILLABLE_HOURS and NON_BILLABLE_HOURS.
     *
     * @param timeCardId primary key of Table TIMECARD
     * @param timeCard instance of TimeCard
     * @throws SQLException
     */
    public void addHours(int timeCardId, TimeCard timeCard) throws SQLException {
        dbConnect();

        List<ConsultantTime> CT = new ArrayList<>();
        CT.addAll(timeCard.getConsultingHours());

        for (ConsultantTime C : CT) {
            if (C.getAccount().isBillable()) {
                String clientName = C.getAccount().getName();
                long billableDate = C.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                Date billDate = new Date(billableDate);
                String skill = C.getSkill().toString();
                int hours = C.getHours();

                String consQuery = "INSERT INTO BILLABLE_HOURS(CLIENT_ID,TIMECARD_ID,DATE,SKILL,HOURS) VALUES(?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(consQuery);
                String nQuery = "SELECT ID FROM CLIENTS WHERE NAME='" + clientName + "'";
                Statement clientStatement = conn.createStatement();
                ResultSet clientSet = clientStatement.executeQuery(nQuery);
                if (clientSet.next()) {
                    int clientId = clientSet.getInt(1);
                    pstmt.setInt(1, clientId);

                }
                pstmt.setInt(2, timeCardId);
                pstmt.setObject(3, billDate);
                pstmt.setString(4, skill);
                pstmt.setInt(5, hours);
                pstmt.executeUpdate();

            } else {
                String nonBillableQuery = "INSERT INTO NON_BILLABLE_HOURS(ACCOUNT_NAME,TIMECARD_ID,DATE,HOURS) VALUES(?,?,?,?)";
                long nonBillableDate = C.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                Date nonBillDate = new Date(nonBillableDate);
                int nonBillHours = C.getHours();
                PreparedStatement pstmt1 = conn.prepareStatement(nonBillableQuery);
                pstmt1.setString(1, C.getAccount().getName());
                pstmt1.setInt(2, timeCardId);
                pstmt1.setObject(3, nonBillDate);
                pstmt1.setInt(4, nonBillHours);
                pstmt1.executeUpdate();

            }

        }

    }

    /**
     * Get clients monthly invoice.
     *
     * @param client
     * @param month invoice month
     * @param year invoice year
     * @return invoice.
     * @throws SQLException if any database operations fail.
     */
    public Invoice getInvoice(ClientAccount client, Month month, int year) throws SQLException {
        dbConnect();

        Invoice invoice = new Invoice(client, month, year);
        String invoiceSelectingQuery = "SELECT BH.DATE, CO.LAST_NAME, CO.FIRST_NAME, CO.MIDDLE_NAME, BH.SKILL, S.RATE, BH.HOURS "
          + "FROM BILLABLE_HOURS BH, CONSULTANTS CO, SKILLS S, TIMECARDS T "
          + "WHERE BH.CLIENT_ID=(SELECT DISTINCT ID FROM CLIENTS WHERE NAME=?)"
          + "AND BH.SKILL=S.NAME AND BH.TIMECARD_ID=T.ID "
          + "AND CO.ID=T.CONSULTANT_ID "
          + "AND BH.DATE>=?"
          + "AND BH.DATE< ?";
        PreparedStatement st = conn.prepareStatement(invoiceSelectingQuery);
        LocalDate startDate = invoice.getStartDate();
        LocalDate endDate = invoice.getStartDate().plusMonths(1);
        long stDate = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long edDate = endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Date dateFirst = new Date(stDate);
        Date dateLast = new Date(edDate);
        st.setString(1, client.getName());
        st.setDate(2, dateFirst);
        st.setDate(3, dateLast);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Date date = rs.getDate(1);
            LocalDate dt;
            dt = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            String lastName = rs.getString(2);
            String firstName = rs.getString(3);
            String middleName = rs.getString(4);
            Consultant consultant = new Consultant(new Name(lastName, firstName, middleName));
            String skill = rs.getString(5);
            Skill skl = Skill.valueOf(skill);
            int rate = rs.getInt(6);
            int hours = rs.getInt(7);
            InvoiceLineItem newLineItem;
            newLineItem = new InvoiceLineItem(dt, consultant, skl, hours);
            invoice.addLineItem(newLineItem);
        }
        return invoice;

    }
}
