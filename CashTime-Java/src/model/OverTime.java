package model;

import java.io.Serializable;
import java.time.*;
import java.util.List;

public class OverTime implements Serializable {
    private int percentage;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private List<DayOfWeek> overTimeDays;
    /**
     * Represents the OverTime(OB) of the worplace for a specific percentage extra for the amount of money on payroll.
     * @author Mustafa Abbas
     */
    public OverTime(LocalDate date, LocalTime start, LocalTime end, int percentage) {
        this(date, start, end, percentage, null);
    }

    public OverTime(LocalDate date, LocalTime start, LocalTime end, int percentage, List<DayOfWeek> overTimeDays) {
        this.date = date;
        this.percentage = percentage;
        this.start = start;
        this.end = end;
        this.overTimeDays = overTimeDays;
    }

    public List<DayOfWeek> getOverTimeDays() {
        return overTimeDays;
    }

    public LocalDate getDate(){
        return date;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public int getPercentage() {
        return percentage;
    }

}
