package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

import model.Interval;
import model.OBInterval;
import model.Workplace;

public class Controller {
    private boolean isClockedIn;
    private List<Workplace> workplaces;
    public static void main(String[] args) {
        new Controller();
    }

    public Controller() {
        isClockedIn = false;
        workplaces = new ArrayList<>();
        createWorkplace();

        LocalDate date = LocalDate.of(2023, Month.MARCH, 29);  //onsdag 29e mars 2023
        LocalTime start = LocalTime.of(10, 00);
        LocalTime end = LocalTime.of(19, 00);


        calcTimeSpent(new Interval(date, start, end), workplaces.get(0));
    }

    private void createWorkplace() {
        workplaces.add(new Workplace("ICA", 200));

        LocalDate date = LocalDate.of(2023, Month.MARCH, 29);  //onsdag 29e mars 2023

        LocalTime start1 = LocalTime.of(12, 00);
        LocalTime end1 = LocalTime.of(14, 00);
        OBInterval intervall1 = new OBInterval(date, start1, end1, 100);

        LocalTime start2 = LocalTime.of(14, 00);
        LocalTime end2 = LocalTime.of(18, 00);
        OBInterval intervall2 = new OBInterval(date, start2, end2, 100);


        workplaces.get(0).getRateSchedule().add(intervall1);
        workplaces.get(0).getRateSchedule().add(intervall2);
    }

    private void calcTimeSpent(Interval interval, Workplace workplace) {
        Duration total = interval.getDuration();
        Duration ob1 = workplace.getRateSchedule().get(0).getTimeSpentInOBInterval(interval);
        Duration ob2 = workplace.getRateSchedule().get(1).getTimeSpentInOBInterval(interval);
        //If there are multiple intervals to consider then, loop through them and create a list for each entry:
        /*
         * get list size
         * loop through list
         * 
         * HashMap <OBid, AmountOfTime> ?? (suggestion)
         */

        System.out.println("Total time: " + total.toHours());
        System.out.println("OB time 1: " + ob1.toHours());
        System.out.println("OB time 2: " + ob2.toHours());
    }

}
