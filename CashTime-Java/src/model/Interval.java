package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Interval implements Serializable {
    int index;
    LocalDate date;
    LocalTime start;
    LocalTime end;
    int breakTime;  // seconds

    public Interval(int index, LocalDate date, LocalTime start) {
        this.index = index;
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

    public String getDuration() {
        int hours = Math.toIntExact(Duration.between(start, end).toHours());
        int minutes = Math.toIntExact(Duration.between(start, end).toMinutes());
        return  hours + "h " + minutes + "m ";

    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getIndex() {
        return index;
    }
}
