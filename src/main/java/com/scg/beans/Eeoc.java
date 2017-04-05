/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.beans;

import java.util.EventListener;

/**
 * The EEOC monitors SCG's terminations.
 *
 * @author dixya
 */
public class Eeoc implements TerminationListener, EventListener {
    /** counter for forced termination */
    int countForcedTermination;
     /** counter for voluntary termination */

    int countVoluntaryTermination;

    public Eeoc() {

    }

    /**
     * Simply prints a message indicating that the consultant was fired.
     *
     * @param evt the termination event
     */
    @Override
    public void forcedTermination(TerminationEvent evt) {
        System.out.println(evt.getConsultant().getName() + "fired");
        countForcedTermination++;

    }

    /**
     * Simply prints a message indicating that the consultant quit.
     *
     * @param evt the termination event
     */
    @Override
    public void voluntaryTermination(TerminationEvent evt) {

        System.out.println(evt.getConsultant().getName() + " quit");
        countVoluntaryTermination++;

    }

    /**
     * Gets the forced termination count.
     *
     * @return value of count for forced termination.
     */
    public int forcedTerminationCount() {
        return countForcedTermination;
    }

    /**
     * Gets the voluntary termination count.
     *
     * @return value of count for voluntary termination.
     */
    public int voluntaryTerminationCount() {
        return countVoluntaryTermination;
    }

}
