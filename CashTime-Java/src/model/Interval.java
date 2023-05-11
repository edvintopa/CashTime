package model;

import controller.Controller;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Interval implements Serializable {
    int index;
    LocalDate date;
    LocalDateTime start;
    LocalDateTime end;
    Duration duration;
    int breakTime;  // seconds
    List<OverTime> overTimeList;
    private Duration regularHours;
    private Duration overTimeHours;


    public Interval(int index, LocalDate date, LocalDateTime start) {
        this.index = index;
        this.date = date;
        this.start = start;
        breakTime = 0;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }


    public LocalDateTime getStart() {
        return start;
    }
    public void setStart(LocalDateTime start) {
        this.start = start;
    }


    public LocalDateTime getEnd() {
        return end;
    }
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }


    public Duration getDuration(){
        return duration;
    }
    public void setDuration(String newDuration) {this.duration = Duration.parse(newDuration);}


    public void setBreakTime(int breakTime){
        this.breakTime = breakTime;
    }


    public int getIndex() {
        return index;
    }

    public Duration calculateDuration() {
        return duration = Duration.between(start,end);

    }

    public Duration getOverTimeHours() {
        return overTimeHours;
    }

    public Duration getRegularHours() {
        return regularHours;
    }

    public void setRegularHours(Duration ofSeconds) {
        this.regularHours = ofSeconds;
    }

    public void setOverTimeHours(Duration minus) {
        this.overTimeHours = minus;
    }

    public void calculateInterval(Controller controller){
        long regularSeconds = duration.getSeconds();
        for (OverTime overTime : controller.getCurrentWorkplace().getOverTimes()) {
            if (overTime.getOverTimeDays() != null && overTime.getOverTimeDays().contains(getStart().getDayOfWeek())) {
                LocalDateTime overTimeStart = overTime.getStart().isAfter(start) ? overTime.getStart() : start;
                LocalDateTime overTimeEnd = overTime.getEnd().isBefore(end) ? overTime.getEnd() : end;
                Duration overTimeDuration = Duration.between(overTimeStart, overTimeEnd);
                long overTimeSeconds = overTimeDuration.getSeconds();

                regularSeconds -= overTimeSeconds;
            }
        }
        this.regularHours = Duration.ofSeconds(regularSeconds);
        this.overTimeHours = duration.minus(getRegularHours());
    }

    public void calculateInterval(Controller controller, LocalDate date){
        LocalDateTime start = LocalDateTime.of(date, this.start.toLocalTime());
        LocalDateTime end = LocalDateTime.of(date, this.end.toLocalTime());

        long regularSeconds = duration.getSeconds();
        for (OverTime overTime : controller.getCurrentWorkplace().getOverTimes()) {
            if (overTime.getOverTimeDays() != null && overTime.getOverTimeDays().contains(date.getDayOfWeek())) {
                LocalDateTime overTimeStart = overTime.getStart().isAfter(start) ? overTime.getStart() : start;
                LocalDateTime overTimeEnd = overTime.getEnd().isBefore(end) ? overTime.getEnd() : end;
                Duration overTimeDuration = Duration.between(overTimeStart, overTimeEnd);
                long overTimeSeconds = overTimeDuration.getSeconds();

                regularSeconds -= overTimeSeconds;
            }
        }
        this.regularHours = Duration.ofSeconds(regularSeconds);
        this.overTimeHours = duration.minus(getRegularHours());
    }


}
