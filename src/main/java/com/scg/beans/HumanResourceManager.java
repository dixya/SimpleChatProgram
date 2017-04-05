/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.beans;

import com.scg.domain.Consultant;
import java.beans.PropertyVetoException;
import java.util.EventListener;
import javax.swing.event.EventListenerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for modifying the pay rate and sick leave and vacation hours of
 * staff consultants.
 *
 * @author dixya
 */
public class HumanResourceManager {

    private final EventListenerList TerminationListenerList = new EventListenerList();
    /**
     * logger for this class
     */
    private static final Logger log = LoggerFactory.getLogger(HumanResourceManager.class);

    /**
     * Constructor.
     */
    public HumanResourceManager() {

    }

    /**
     * Fires a voluntary termination event for the staff consultant.
     *
     * @param c the consultant resigning
     */
    public void acceptResignation(Consultant c) {
        TerminationEvent TE = new TerminationEvent(this, c, true);

        fireTerminationEvent(TE);
    }

    /**
     * Adds a termination listener.
     *
     * @param l the listener to add.
     */
    public void addTerminationListener(TerminationListener l) {
        TerminationListenerList.add(TerminationListener.class, l);

    }

    /**
     * Sets the pay rate for a staff consultant.
     *
     * @param c the consultant
     * @param newPayRate
     * @throws PropertyVetoException
     */
    public void adjustPayRate(StaffConsultant c, int newPayRate) throws PropertyVetoException {
        try {
            if (log.isInfoEnabled()) {
                int oldPayRate = c.getPayRate();
                log.info("changed value = " + (newPayRate - oldPayRate));
            }
            c.setPayRate(newPayRate);
            log.info("Approved pay adjustment for" + c.getName());
        } catch (final PropertyVetoException pve) {
            log.info("Denied adjustment of payrate for " + c.getName());
        }
    }

    /**
     * Sets the sick leave hours for a staff consultant.
     *
     * @param c the consultant
     * @param newSickLeaveHours the new sick leave hours for the consultant.
     */
    public void adjustSickLeaveHours(StaffConsultant c, int newSickLeaveHours) {
        //int oldSickLeaveHours=c.getSickLeaveHours();
        c.setSickLeaveHours(newSickLeaveHours);
    }

    /**
     * Sets the vacation hours for a staff consultant.
     *
     * @param c the consultant
     * @param newVacationHours the new vacation hours for the consultant
     */
    public void adjustVacationHours(StaffConsultant c, int newVacationHours) {
        //int oldVacationHours=c.getVacationHours();
        c.setVacationHours(newVacationHours);
    }

    /**
     * Removes a termination listener.
     *
     * @param l the listener to terminate.
     */
    public void removeTerminationListener(TerminationListener l) {
        TerminationListenerList.remove(TerminationListener.class, l);
    }

    /**
     * Fires an involuntary termination event for the staff consultant.
     *
     * @param c the consultant
     */
    public void terminate(Consultant c) {
        TerminationEvent TE = new TerminationEvent(this, c, false);

        fireTerminationEvent(TE);
    }

    void fireTerminationEvent(final TerminationEvent evnt) {
        TerminationListener[] listeners;
        listeners = TerminationListenerList.getListeners(TerminationListener.class);
        for (final TerminationListener listener : listeners) {
            if (evnt.isVoluntary()) {
                listener.voluntaryTermination(evnt);
            } else {
                listener.forcedTermination(evnt);
            }
        }
    }
}
