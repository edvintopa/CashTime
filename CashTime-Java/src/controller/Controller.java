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
        LocalTime end = LocalTime.of(15, 00);


        calcTimeSpent(new Interval(date, start, end), workplaces.get(0));
    }

    private void createWorkplace() {
        workplaces.add(new Workplace("ICA", 200));

        LocalDate date = LocalDate.of(2023, Month.MARCH, 29);  //onsdag 29e mars 2023
        LocalTime start = LocalTime.of(12, 00);
        LocalTime end = LocalTime.of(23, 59);

        OBInterval intervall = new OBInterval(date, start, end, 100);


        workplaces.get(0).getRateSchedule().add(intervall);
    }

    private void calcTimeSpent(Interval interval, Workplace workplace) {
        Duration total = interval.getDuration();
        Duration ob = workplace.getRateSchedule().get(0).getTimeSpentInOBInterval(interval);

        System.out.println("Total time: " + total.toHours());
        System.out.println("OB time: " + total.toHours());
    }

}
