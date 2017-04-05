/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs changes in benefits.
 *
 * @author dixya
 */
public class BenefitManager implements PropertyChangeListener, EventListener {
    /*logger for this class */
    private static final Logger log = LoggerFactory.getLogger(BenefitManager.class);

    /**
     * Constructor.
     */
    public BenefitManager() {

    }

    /**
     * Logs the change.
     *
     * @param pce a property change event for the sickLeaveHours or
     * vacationHours, or payRate property.
     */
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        String propertyName = pce.getPropertyName();
        int oldVal = (int) pce.getOldValue();
        int newVal = (int) pce.getNewValue();
        log.info(propertyName + "changed from" + oldVal + "to" + newVal);
    }

}
