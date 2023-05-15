package model;

import controller.Controller;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Interval implements Serializable {
    int index;
    LocalDate date;
    LocalDateTime start;
    LocalDateTime end;
    Duration duration; // Seconds
    List<OverTime> overTimeList;
    private Duration regularHours = Duration.ZERO;
    private Duration overTimeHours = Duration.ZERO;

    private Duration breakTime = Duration.ZERO;
    private LocalTime breakStart;
    private LocalTime breakEnd;

    public Interval(int index, LocalDate date, LocalDateTime start) {
        this.index = index;
        this.date = date;
        this.start = start;
    }

    public LocalDate getDate() {
        return start.toLocalDate();
    }
    public void setDate(LocalDate date) {
        start = start.with(date);
        end = end.with(date);
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
        return duration.minus(breakTime);
    }
    public void setDuration(String newDuration) {this.duration = Duration.parse(newDuration);}

    public void startBreak(){
        breakStart = LocalTime.now();
    }

    public void endBreak() {
        breakEnd = LocalTime.now();
        calculateBreak();
    }

    private void calculateBreak() {
        if (breakStart != null && breakEnd != null) {
            Duration breakDuration = Duration.between(breakStart, breakEnd);
            breakTime = breakTime.plus(breakDuration);
        }
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
        regularSeconds -= breakTime.getSeconds();
        for (OverTime overTime : controller.getCurrentWorkplace().getOverTimes()) {
            if (overTime.getOverTimeDays() != null && overTime.getOverTimeDays().contains(getStart().getDayOfWeek())) {
                LocalTime overTimeStart = LocalTime.from(overTime.getStart().isAfter(start.toLocalTime()) ? overTime.getStart() : start);
                LocalTime overTimeEnd = LocalTime.from(overTime.getEnd().isBefore(end.toLocalTime()) ? overTime.getEnd() : end);
                Duration overTimeDuration = Duration.between(overTimeStart, overTimeEnd);
                long overTimeSeconds = overTimeDuration.getSeconds();

                regularSeconds -= overTimeSeconds;
                this.regularHours = Duration.ofSeconds(regularSeconds);
                this.overTimeHours = duration.minus(getRegularHours());
            }

        }
    }

    public void calculateInterval(Controller controller, LocalDate date){
        LocalDateTime start = LocalDateTime.of(date, this.start.toLocalTime());
        LocalDateTime end = LocalDateTime.of(date, this.end.toLocalTime());

        long regularSeconds = duration.getSeconds();
        regularSeconds -= breakTime.getSeconds();
        for (OverTime overTime : controller.getCurrentWorkplace().getOverTimes()) {
            if (overTime.getOverTimeDays() != null && overTime.getOverTimeDays().contains(date.getDayOfWeek())) {
                LocalDateTime overTimeStart = overTime.getStart().isAfter(start.toLocalTime()) ? LocalDateTime.of(date, overTime.getStart()) : start;
                LocalDateTime overTimeEnd = overTime.getEnd().isBefore(end.toLocalTime()) ? LocalDateTime.of(date, overTime.getEnd()) : end;
                Duration overTimeDuration = Duration.between(overTimeStart, overTimeEnd);
                long overTimeSeconds = overTimeDuration.getSeconds();

                if (start.isBefore(overTimeEnd) && end.isAfter(overTimeStart)) {
                    LocalDateTime intervalStart = start.isAfter(overTimeStart) ? start : overTimeStart;
                    LocalDateTime intervalEnd = end.isBefore(overTimeEnd) ? end : overTimeEnd;
                    overTimeDuration = Duration.between(intervalStart, intervalEnd);
                    overTimeSeconds = overTimeDuration.getSeconds();
                }

                regularSeconds -= overTimeSeconds;
                this.regularHours = Duration.ofSeconds(regularSeconds);
                this.overTimeHours = duration.minus(getRegularHours());
            }
        }
    }



}
