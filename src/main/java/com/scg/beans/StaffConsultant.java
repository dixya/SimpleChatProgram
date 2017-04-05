/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.beans;

import com.scg.domain.Consultant;
import com.scg.util.Name;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

/**
 * A consultant who is kept on staff (receives benefits).
 *
 * @author dixya
 */
public class StaffConsultant extends Consultant implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Pay rate property name.
     */

    static final String PAY_RATE_PROPERTY_NAME = "payRate";
    /**
     * Pay rate property name.
     */

    private static final String SICK_LEAVE_HOURS_PROPERTY_NAME = "sickLeaveHours";
    /**
     * Vacation hours property name.
     */

    private static final String VACATION_HOURS_PROPERTY_NAME = "vacationHours";
    private int payRate;
    private int sickLeave;
    private int vacation;
    /**
     * property change support to trace the change of property
     */
    PropertyChangeSupport PCS;
    /**
     * vetoable change support to trace the change of vetoable property.
     */

    VetoableChangeSupport VCS;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.payRate;
        hash = 41 * hash + this.sickLeave;
        hash = 41 * hash + this.vacation;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StaffConsultant other = (StaffConsultant) obj;
        if (this.payRate != other.payRate) {
            return false;
        }
        if (this.sickLeave != other.sickLeave) {
            return false;
        }
        if (this.vacation != other.vacation) {
            return false;
        }
        return true;
    }

    /**
     * Creates a new instance of StaffConsultant
     *
     * @param name consultant name
     * @param rate pay rate in cents
     * @param sickLeave sick leave hours
     * @param vacation vacation hours.
     */
    public StaffConsultant(Name name, int rate, int sickLeave, int vacation) {
        super(name);
        this.payRate = rate;
        this.sickLeave = sickLeave;
        this.vacation = vacation;
        PCS = new PropertyChangeSupport(this);
        VCS = new VetoableChangeSupport(this);

    }

    /**
     * Adds a payRate property change listener.
     *
     * @param l instance of PropertyChangeListener.
     */
    public void addPayRateListener(PropertyChangeListener l) {
        PCS.addPropertyChangeListener(PAY_RATE_PROPERTY_NAME, l);
    }

    /**
     * Adds a general property change listener.
     *
     * @param l instance of PropertyChangeListener.
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        //testlistener
        PCS.addPropertyChangeListener(l);

    }

    /**
     * Adds a sickLeaveHours property change listener.
     *
     * @param l instance of PropertyChangeListener.
     */
    public void addSickLeaveHoursListener(PropertyChangeListener l) {
        PCS.addPropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, l);
    }

    /**
     * Adds a vacationHours property change listener.
     *
     * @param l instance of PropertyChangeListener.
     */
    public void addVacationHoursListener(PropertyChangeListener l) {
        PCS.addPropertyChangeListener(VACATION_HOURS_PROPERTY_NAME, l);

    }

    /**
     * Adds a vetoable change listener.
     *
     * @param l instance of VetoableChangeListener
     */
    public void addVetoableChangeListener(VetoableChangeListener l) {
        VCS.addVetoableChangeListener(PAY_RATE_PROPERTY_NAME, l);
    }

    /**
     * Get the current pay rate.
     *
     * @return pay rate.
     */
    public int getPayRate() {
        return payRate;

    }

    /**
     * Get the available sick leave.
     *
     * @return sick leave hours.
     */
    public int getSickLeaveHours() {
        return sickLeave;
    }

    /**
     * Get the available vacation hours.
     *
     * @return vacation hours.
     */
    public int getVacationHours() {
        return vacation;
    }

    /**
     * Removes a payRate property change listener.
     *
     * @param l instance of PropertyChangeListener.
     */
    public void removePayRateListener(PropertyChangeListener l) {
        VCS.removeVetoableChangeListener(PAY_RATE_PROPERTY_NAME, (VetoableChangeListener) l);
    }

    /**
     * Remove a general property change listener.
     *
     * @param l instance of PropertyChangeListener.
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        PCS.removePropertyChangeListener(l);
    }

    /**
     * Removes a sickLeaveHours property change listener.
     *
     * @param l instance of PropertyChangeListener.
     */
    public void removeSickLeaveHoursListener(PropertyChangeListener l) {
        PCS.removePropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, l);
    }

    /**
     * Removes a vacationHours property change listener.
     *
     * @param l instance of PropertyChangeListener.
     */
    public void removeVacationHoursListener(PropertyChangeListener l) {
        PCS.removePropertyChangeListener(VACATION_HOURS_PROPERTY_NAME, l);
    }

    /**
     * Removes a vetoable change listener.
     *
     * @param l instance of VetoableChangeListener.
     */
    public void removeVetoableChangeListener(VetoableChangeListener l) {
        VCS.removeVetoableChangeListener(l);
    }

    /**
     * Set the pay rate.
     *
     * @param payRate
     * @throws PropertyVetoException if a veto occurs.
     */
    public synchronized void setPayRate(int payRate) throws PropertyVetoException {
        VCS.fireVetoableChange(PAY_RATE_PROPERTY_NAME, this.payRate, payRate);
        final int oldRate = this.payRate;
        this.payRate = payRate;
        PCS.firePropertyChange(PAY_RATE_PROPERTY_NAME, oldRate, payRate);

    }

    /**
     * Set the sick leave hours.
     *
     * @param sickLeaveHours
     */
    public void setSickLeaveHours(int sickLeaveHours) {
        int oldSickLeaveHours = this.sickLeave;
        this.sickLeave = sickLeaveHours;
        PCS.firePropertyChange(SICK_LEAVE_HOURS_PROPERTY_NAME, oldSickLeaveHours, sickLeave);

    }

    /**
     * Set the vacation hours.
     *
     * @param vacationHours
     */
    public void setVacationHours(int vacationHours) {
        int oldVacationHours = this.vacation;
        this.vacation = vacationHours;
        PCS.firePropertyChange(VACATION_HOURS_PROPERTY_NAME, oldVacationHours, vacation);

    }

}
