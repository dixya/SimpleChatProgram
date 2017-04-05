/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.beans;

import com.scg.domain.Consultant;
import java.io.Serializable;
import java.util.EventObject;

/**
 * Event used to notify listeners of a Consultant's termination.
 * @author dixya
 */
public class TerminationEvent extends EventObject implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private final Consultant consultant;
    private final boolean voluntary;

    
    /**
     * Constructor.
     * @param source     the event source     
     * @param consultant the consultant being terminated
     * @param voluntary   was the termination voluntary.

     */
    public TerminationEvent(Object source, Consultant consultant, boolean voluntary){
        super(source);
        this.consultant=consultant;
        this.voluntary=voluntary;
    }
    /**
     * Gets the consultant that was terminated.
     * @return the consultant that was terminated
     */
    public Consultant getConsultant(){
        return consultant;
    }
    /**
     * Gets the voluntary termination status.
     * @return   true if a voluntary termination.
     */
    public boolean isVoluntary(){
       return voluntary; 
        
    }
    
}
