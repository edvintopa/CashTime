package model;

import java.io.Serializable;
import java.time.*;
import java.util.List;

public class OverTime implements Serializable {
    private int percentage;
    private LocalDate date;
    private LocalTime regularStart;
    private LocalTime regularEnd;
    private LocalTime start;
    private LocalTime end;
    private List<DayOfWeek> overTimeDays;

    public OverTime(LocalDate date, LocalDateTime start, LocalDateTime end, int percentage) {
        this(date, start.toLocalTime(), end.toLocalTime(), percentage, null);
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

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

}
