package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Interval {
    LocalDate date;
    LocalTime start;
    LocalTime end;

    public Interval(LocalDate date, LocalTime start) {
        this.date = date;
        this.start = start;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }
}
