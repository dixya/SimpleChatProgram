/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scg.util;

import com.scg.domain.TimeCard;
import java.util.ArrayList;
import java.util.List;
import com.scg.domain.Consultant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 *
 * @author dixya
 */
public class TimeCardListUtil {

    static List<TimeCard> timeCard = new ArrayList<>();
    private static final int DaysInWeek=6;

    /**
     * Get a list of TimeCards for the specified consultant.
     *
     * @param timeCards the list of time cards to extract the sub set from
     * @param consultant the consultant whose TimeCards will be obtained.
     * @return list of timecards for given consultant.
     */
    public static List<TimeCard> getTimeCardsForConsultant(List<TimeCard> timeCards, Consultant consultant) {
        timeCards.stream().filter((t) -> {
            return t.getConsultant().equals( consultant);
        }).forEach((t) -> {
            timeCard.add(t);
        });
        return timeCard;
    }

    /**
     * Get a list of TimeCards that cover dates that fall within a date range.
     *
     * @param timeCards the list of time cards to extract the sub set from.
     * @param dateRange The DateRange within which the dates of the returned
     * TimeCards must fall.
     * @return list of timeCards for given date range.
     */
    public static List<TimeCard> getTimeCardsForDateRange(List<TimeCard> timeCards, DateRange dateRange) {
       // return timeCards.stream().filter(tc->dateRange.isInRange(tc.getWeekStartingDay())||(dateRange.isInRange(tc.getWeekStartingDay().plusDays(DaysInWeek))).collect(Collectors.toList()));
        List<TimeCard> timeCardWithinDateRange;
        timeCardWithinDateRange = new ArrayList<>();
        timeCards.stream().forEach((TimeCard t) -> {
            LocalDate timeCardDate = t.getWeekStartingDay();
            if (dateRange.isInRange(timeCardDate)||dateRange.isInRange(timeCardDate.plusDays(DaysInWeek))) {
                timeCardWithinDateRange.add(t);
            }
        });
        return timeCardWithinDateRange;
    }

    /**
     * Sorts this list into ascending order by consultant name..
     *
     * @param timeCards the list of time cards to sort
     */
    public static void sortByConsultantName(List<TimeCard> timeCards) {
        Comparator<TimeCard> comparator = (Comparator<TimeCard>) (TimeCard t, TimeCard t1) -> (t.getConsultant().getName()).compareTo(t1.getConsultant().getName());
        Collections.sort(timeCards, comparator); //check it should be consultantcomparator

    }

    /**
     * Sorts this list into ascending order, by the start date.
     *
     * @param timeCards the list of time cards to sort
     */
    public static void sortByStartDate(List<TimeCard> timeCards) {
        Comparator<TimeCard> comparator = (Comparator<TimeCard>) (TimeCard t, TimeCard t1) -> t.getWeekStartingDay().compareTo(t1.getWeekStartingDay());

        Collections.sort(timeCards, comparator);

    }

}
