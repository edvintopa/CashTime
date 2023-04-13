package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Interval implements Serializable {
    LocalDate date;
    LocalTime start;
    LocalTime end;
    int breakTime;  // seconds

    public Interval(LocalDate date, LocalTime start) {
        this.date = date;
        this.start = start;
        breakTime = 0;
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

    public void setBreakTime(int breakTime){
        this.breakTime = breakTime;
    }

    public long getDuration() {
        return Duration.between(start, end).toSeconds() - breakTime;
    }

}
