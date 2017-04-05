/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

/**
 * Encapsulates the concept of a billable skill.
 * @author dixya
 */

public enum Skill {
    //For now, set rate to some value for all skill
    PROJECT_MANAGER(250),
    SYSTEM_ARCHITECT(200),
    SOFTWARE_ENGINEER(150),
    SOFTWARE_TESTER(100),
    UNKNOWN_SKILL(50);
    
    private  int rate;
    
    /**
     * private constructor for skill.
     * @param rate 
     */
    private Skill(int rate) {
        this.rate = rate;
    }
    
    /**
     * Getter for the property rate
     * @return rate
     */
    public int getRate() {
        return rate;
    }
    /**
     * Returns the friendly name for this enumerated value.
     * @return name
     */
    @Override
    public String toString(){
        return name();
        
    }
}
