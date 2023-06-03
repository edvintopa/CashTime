package model;

import controller.Controller;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Interval implements Serializable {
    private int index;
    private LocalDateTime start;
    private LocalDateTime end;
    private Duration duration; // Seconds
    private List<OverTime> overTimeList;
    private Duration regularHours;
    private Duration overTimeHours;
    private int overTimePercentage;

    private Duration breakTime;
    private LocalTime breakStart;
    private LocalTime breakEnd;

    public Interval(int index, LocalDateTime start) {
        this.index = index;
        this.start = start;
        this.regularHours = Duration.ZERO;
        this.overTimeHours = Duration.ZERO;
        breakTime = Duration.ZERO;
        this.overTimePercentage = 0;
    }

    public LocalDate getDate() {
        return start.toLocalDate();
    }

    public void setDate(LocalDate date) {
        LocalDateTime newStart = start.with(date);
        start = newStart;
        LocalDateTime newEnd = end.with(date);
        end = newEnd;
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
        Duration dur = Duration.ofSeconds(duration.minus(breakTime).getSeconds());
        return dur;
    }
    public void setDuration(String newDuration) {
        this.duration = Duration.parse(newDuration);
    }

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
            breakTime = Duration.ofSeconds(breakTime.plus(breakDuration).getSeconds());
        }
    }

    public int getIndex() {
        return index;
    }

    public Duration calculateDuration() {
        if(!(start.toLocalTime() == end.toLocalTime()) || !(start.getHour() == end.getHour())){
            duration = Duration.between(start,end);
        } else {
            duration = Duration.between(start,end);
        }
        regularHours = Duration.ofSeconds(duration.getSeconds()).truncatedTo(ChronoUnit.MINUTES);
        return duration;
    }

    public Duration getOverTimeHours() {
        return overTimeHours;
    }

    public Duration getRegularHours() {
        return regularHours;
    }

    public void calculateInterval(Controller controller) {
        long regularSeconds = duration.getSeconds();
        regularSeconds -= breakTime.getSeconds();
        OverTime selectedOverTime = null;

        if (!controller.getCurrentWorkplace().getOverTimes().isEmpty()) {
            for (OverTime overTime : controller.getCurrentWorkplace().getOverTimes()) {
                if(overTime.getDate() != null){
                    if (overTime.getDate().equals(start.toLocalDate())) {
                        selectedOverTime = overTime;
                        break;
                    }
                } else if (overTime.getOverTimeDays() != null && overTime.getOverTimeDays().contains(getStart().getDayOfWeek())) {
                    selectedOverTime = overTime;
                }
            }

            if (selectedOverTime != null) {
                if(!selectedOverTime.getStart().isAfter(end.toLocalTime()) && !selectedOverTime.getEnd().isBefore(start.toLocalTime()) ){
                    LocalTime overTimeStart = LocalTime.from(selectedOverTime.getStart().isAfter(start.toLocalTime()) ? selectedOverTime.getStart() : start);
                    LocalTime overTimeEnd = LocalTime.from(selectedOverTime.getEnd().isBefore(end.toLocalTime()) ? selectedOverTime.getEnd() : end);
                    Duration overTimeDuration = Duration.between(overTimeStart, overTimeEnd);
                    long overTimeSeconds = overTimeDuration.getSeconds();

                    //regularSeconds -= overTimeSeconds;

                    //this.overTimeHours = duration.minus(getRegularHours());
                    if(!(start.toLocalTime() == end.toLocalTime()) || start.isEqual(end)) {
                        overTimeHours = Duration.ofSeconds(overTimeSeconds).truncatedTo(ChronoUnit.MINUTES);
                    } else {
                        overTimeHours = Duration.ofSeconds(overTimeSeconds).truncatedTo(ChronoUnit.MINUTES);
                    }

                    overTimePercentage = selectedOverTime.getPercentage();
                } else {
                    this.regularHours = Duration.ofSeconds(regularSeconds).truncatedTo(ChronoUnit.MINUTES);
                    overTimeHours = Duration.ZERO;
                    overTimePercentage = 0;
                }

            } else {
                this.regularHours = Duration.ofSeconds(regularSeconds).truncatedTo(ChronoUnit.MINUTES);
                overTimeHours = Duration.ZERO;
                overTimePercentage = 0;
            }
        } else {
            this.regularHours = Duration.ofSeconds(regularSeconds).truncatedTo(ChronoUnit.MINUTES);
            this.overTimeHours = Duration.ZERO;
            overTimePercentage = 0;
        }
    }

/*
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

 */


    public int getPercentage() {
        return overTimePercentage;
    }
}
