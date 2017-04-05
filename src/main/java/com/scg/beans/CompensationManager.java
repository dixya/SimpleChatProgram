/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Approves or rejects compensation changes.
 *
 * @author dixya
 */
public class CompensationManager implements PropertyChangeListener, VetoableChangeListener, EventListener {
        /*logger for this class */

    private static final Logger log = LoggerFactory.getLogger(CompensationManager.class);

    /**
     * Constructor.
     */
    public CompensationManager() {

    }

    /**
     * Processes to final pay rate change.
     *
     * @param evt a change event for the payRate property
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (StaffConsultant.PAY_RATE_PROPERTY_NAME.equals(evt.getPropertyName())) {
            log.info("pay rate changed from " + evt.getOldValue() + " to " + evt.getNewValue() + " of " + evt.getPropertyName());
        }

    }

    /**
     * Rejects any raise over 5%.
     *
     * @param evt a vetoable change event for the payRate property
     * @throws PropertyVetoException if the change is vetoed
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        //if(evt.getNewValue()>0.05*(evt.getOldValue()))

        if (StaffConsultant.PAY_RATE_PROPERTY_NAME.equals(evt.getPropertyName())) {
            final int oldVal = (int) evt.getOldValue();
            final int newVal = (int) evt.getNewValue();
            if ((newVal * 100) > (oldVal * 105)) {
                if (log.isInfoEnabled()) {
                    log.info("Rejected pay rate change from " + oldVal + " to " + newVal + " for " + (StaffConsultant) (evt.getSource()));

                }
                throw new PropertyVetoException("Raise denied", evt);

            } else if (log.isInfoEnabled()) {
                log.info("Accepted pay rate change from " + oldVal + " to " + newVal + " for " + (StaffConsultant) (evt.getSource()));
            }

        }

    }

}
