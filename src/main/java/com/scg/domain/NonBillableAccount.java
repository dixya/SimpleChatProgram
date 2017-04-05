/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

/**
 * Encapsulates the concept of a non-billable account, such as sick leave,
 * vacation, or business development.
 *
 * @author dixya
 */
public enum NonBillableAccount implements Account {

    SICK_LEAVE,
    VACATION,
    BUSINESS_DEVELOPMENT;

    /**
     * Getter for the name of this account.
     * @return name
     */
    @Override
    public String getName() {
        return name();
    }

    /**
     * Determines if this account is billable.
     * @return boolean value.
     */
    @Override
    public boolean isBillable() {
        return false;
    }
    
    /**
     * Returns the friendly name for this enumerated value.
     * @return name in string format.
     */
    @Override
    public String toString() {
        return this.name();//Returns the friendly name for this enumerated value.
    }

}
