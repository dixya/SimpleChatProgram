/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.util;

import com.scg.domain.TimeCard;
import java.util.Comparator;
import static javax.swing.RowFilter.ComparisonType.BEFORE;

/**
 * Compares two TimeCard objects by ascending consultant, timecard week, totalHours, totalBillableHours and totalNonBillableHours.
 * @author dixya
 */
public class TimeCardConsultantComparator implements Comparator<TimeCard>{
    public TimeCardConsultantComparator() {
        
    }
    /**
     * Compares its two arguments for order.
     * @param firstTimeCard     the first object to be compared.
    
     * @param secondTimeCard  the second object to be compared.
     * @return integer value.
     */
    @Override
    public int compare(TimeCard firstTimeCard, TimeCard secondTimeCard){
        int consultantResult=firstTimeCard.getConsultant().compareTo(secondTimeCard.getConsultant());
        if(consultantResult!=0)
            return consultantResult ;
        int timeCardWeekResult=firstTimeCard.getWeekStartingDay().compareTo(secondTimeCard.getWeekStartingDay());
        if(timeCardWeekResult!=0)
            return timeCardWeekResult;
        
        if(firstTimeCard.getTotalHours()<secondTimeCard.getTotalHours())
            return -1; 
        if(firstTimeCard.getTotalHours()>secondTimeCard.getTotalHours())
            return 1;
            
                   
         if(firstTimeCard.getTotalBillableHours()<secondTimeCard.getTotalBillableHours())   
             return -1;
         if(firstTimeCard.getTotalBillableHours()>secondTimeCard.getTotalBillableHours())   
             return 1;
         
         if(firstTimeCard.getTotalNonBillableHours()<secondTimeCard.getTotalNonBillableHours())
             return -1;
         if(firstTimeCard.getTotalNonBillableHours()>secondTimeCard.getTotalNonBillableHours())
             return 1;
         else
             return 0;
        
        
    }
    
}
