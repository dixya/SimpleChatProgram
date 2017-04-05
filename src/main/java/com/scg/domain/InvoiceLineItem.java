/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.domain;

import java.time.LocalDate;
import java.util.Locale;

/**
 *
 * @author dixya
 */
public class InvoiceLineItem {
     
    public LocalDate date;
    private Consultant consultant;
    private Skill skill;
    private int hours;
    private int charge; 
    private static final String LINE_FORMAT="%1$tm/%1$td/%1$ty %2$-20s";
    
    /**
     * Construct an InvoiceLineItem.
     * @param date
     * @param consultant
     * @param skill
     * @param hours 
     */
    public InvoiceLineItem(LocalDate date, Consultant consultant, Skill skill, int hours){
        if(hours<=0)
            throw new IllegalArgumentException("InvoiceLineItem requires hours>0");
        this.date=date;
        this.consultant=consultant;
        this.skill=skill;
        this.hours=hours;
        this.charge=skill.getRate()*hours;
    }
    /**
     * Get the charge for this line item.
     * @return charge.
     */
    int getCharge(){
       //charge=skill.getRate()*hours;
        return charge;
        
    }
    /**
     * Get the consultant for this line item.
     * @return consultant.
     */
    Consultant 	getConsultant(){
        return consultant;
        
    }
    /**
     * Get the hours for this line item.
     * @return hours.
     */
    int getHours(){
        return hours;
        
    }
    /**
     * Get the skill for this line item.
     * @return skill.
     */
    Skill getSkill(){
        return skill;
        
    }
    /**
     * Print the date, consultant, skill, hours and charge for this line item.
     * @return string value.
     */
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        String firstpart=String.format(Locale.US,LINE_FORMAT,date,consultant.getName());
        sb.append(firstpart);
        sb.append(skill+"\t\t");
        sb.append(hours+"\t");
        sb.append(charge+"\n");
        return sb.toString();
       
    }
    
}
