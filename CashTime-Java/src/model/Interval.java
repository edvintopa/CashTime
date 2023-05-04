package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Interval implements Serializable {
    int intervalId;
    LocalDate date;
    LocalTime start;
    LocalTime end;
    int breakTime;  // seconds

    public Interval(int intervalId, LocalDate date, LocalTime start) {
        this.intervalId = intervalId;
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
        if (start != null){
            this.start = start;
        }
    }

    public void setEnd(LocalTime end) {
        if (end != null){
            this.end = end;

        }
    }

    public void setBreakTime(int breakTime){
        this.breakTime = breakTime;
    }

    /**
     * Calculates the duration in hours and with the minutes left that could not be counted as a whole hour.
     * @return
     */
    public String getDuration() {
        long durationMinutes = Duration.between(start, end).toMinutes();
        int hours = (int) (durationMinutes / 60);
        int minutes = (int) (durationMinutes % 60);
        return hours + "h " + minutes + "m";
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getIntervalId() {
        return intervalId;
    }
}
