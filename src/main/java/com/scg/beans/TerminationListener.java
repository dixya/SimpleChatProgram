/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.beans;

import java.util.EventListener;

/**
 * Interface for accepting notification of consultant terminations.
 *
 * @author dixya
 */
public interface TerminationListener extends EventListener {

    /**
     * Invoked when a consultant is involuntarily terminated.
     *
     * @param evt the termination event
     */
    void forcedTermination(TerminationEvent evt);

    /**
     * Invoked when a consultant is voluntarily terminated.
     *
     * @param evt the termination event
     */
    void voluntaryTermination(TerminationEvent evt);
}
