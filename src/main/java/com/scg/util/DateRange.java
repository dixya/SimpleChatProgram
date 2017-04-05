/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.util;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author dixya
 */
public class DateRange implements Serializable{

    LocalDate startingDate;
    LocalDate endingDate;
    Month month;
    int year;

    /**
     * Construct a DateRange given two dates.
     *
     * @param startDate starting date of the date range.
     * @param endDate ending date of the date range.
     */
    public DateRange(LocalDate startDate, LocalDate endDate) {
        this.startingDate = startDate;
        this.endingDate = endDate;
    }

    /**
     * Construct a DateRange for the given month.
     *
     * @param month month of the date range
     * @param year year of the date range
     */
    public DateRange(Month month, int year) {
        this.month = month;
        this.year = year;
        this.startingDate = LocalDate.of(year, month, 1);
        if ((this.year % 400 == 0) || ((this.year % 4 == 0) && (this.year % 100 != 0))) {
            if (this.month == Month.FEBRUARY) {
                this.endingDate = LocalDate.of(year, month, 29);
            } else if (this.month == Month.JANUARY || this.month == month.MARCH || this.month == month.MAY || this.month == month.JULY || this.month == month.AUGUST || this.month == month.OCTOBER || this.month == month.DECEMBER) {
                this.endingDate = LocalDate.of(year, month, 31);
            } else {
                this.endingDate = LocalDate.of(year, month, 30);
            }
        } else if (this.month == Month.FEBRUARY) {
            this.endingDate = LocalDate.of(year, month, 28);
        } else if (this.month == Month.JANUARY || this.month == Month.MARCH || this.month == month.MAY || this.month == month.JULY || this.month == month.AUGUST || this.month == month.OCTOBER || this.month == month.DECEMBER) {
            this.endingDate = LocalDate.of(year, month, 31);
        } else {
            this.endingDate = LocalDate.of(year, month, 30);
        }

    }

    /**
     * Construct a DateRange given two date strings in the correct format.
     *
     * @param start the start date for this DateRange.
     * @param end the end date for this DateRange.
     */
    public DateRange(String start, String end) {
        int startYear = Integer.parseInt(start.substring(0, 4));
        int startMonth = Integer.parseInt(start.substring(5, 7));
        int startDay = Integer.parseInt(start.substring(8, 10));
        this.startingDate = LocalDate.of(startYear, startMonth, startDay);
        int endYear = Integer.parseInt(end.substring(0, 4));
        int endMonth = Integer.parseInt(end.substring(5, 7));
        int endDay = Integer.parseInt(end.substring(8, 10));
        this.endingDate = LocalDate.of(endYear, endMonth, endDay);

    }

    /**
     * Returns the end date for this DateRange.
     *
     * @return ending date.
     */
    public LocalDate getEndDate() {
        return endingDate;

    }

    /**
     * Returns the start date for this DateRange.
     *
     * @return starting date.
     */
    public LocalDate getStartDate() {
        return startingDate;

    }

    /**
     * Returns true if the specified date is within the range start date <= date <= end date.
     * @param date     
     * m date the date to check for being within this DateRange.
     * @return true if the specified date is within this DateRange.
     */

    public boolean isInRange(LocalDate date) {
        //return !(date.isBefore(startDate)||(date.isAfter(endDate)))
        return ((date.isEqual(startingDate) || date.isAfter(startingDate)) && (date.isEqual(endingDate) || date.isBefore(endingDate)));

    }

}
