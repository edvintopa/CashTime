package model;

import java.io.Serializable;
import java.time.*;
import java.util.List;

public class OverTime implements Serializable {
    private int percentage;
    private LocalDate date;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<DayOfWeek> overTimeDays;

    public OverTime(LocalDate date, LocalDateTime start, LocalDateTime end, int percentage) {
        this(date, start, end, percentage, null);
    }

    public OverTime(LocalDate date, LocalDateTime start, LocalDateTime end, int percentage, List<DayOfWeek> overTimeDays) {
        this.date = date;
        this.percentage = percentage;
        this.start = start;
        this.end = end;
        this.overTimeDays = overTimeDays;
    }

    public List<DayOfWeek> getOverTimeDays() {
        return overTimeDays;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

}
