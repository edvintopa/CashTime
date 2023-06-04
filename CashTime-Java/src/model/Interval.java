package model;

import controller.Controller;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Represents an interval of time for a specific date.
 * @author Mustafa Abbas, Alexandru Som
 * (Sidenote: every method not javadoc commented are setters and getters which are written by Mustafa Abbas)
 */
public class Interval implements Serializable {
    int index;
    private LocalDate date;
    private LocalDateTime start;
    private LocalDateTime end;
    private Duration duration; // Seconds
    private List<OverTime> overTimeList;
    private Duration regularHours = Duration.ZERO;
    private Duration overTimeHours = Duration.ZERO;
    private int overTimePercentage = 0;

    private Duration breakTime = Duration.ZERO;
    private LocalTime breakStart;
    private LocalTime breakEnd;

    /**
     * Constructs an Interval object with the specified index, date, and start time.
     *
     * @param index The index of the interval.
     * @param date  The date of the interval.
     * @param start The start time of the interval.
     */
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

    /**
     * Ends the break by recording the break end time and calculating the break duration.
     * @author Mustafa Abbas
     */
    public void endBreak() {
        breakEnd = LocalTime.now();
        calculateBreak();
    }

    /**
     * Calculates the break duration by subtracting the break start time from the break end time.
     * @author Mustafa Abbas
     */
    private void calculateBreak() {
        if (breakStart != null && breakEnd != null) {
            Duration breakDuration = Duration.between(breakStart, breakEnd);
            breakTime = breakTime.plus(breakDuration);
        }
    }

    public int getIndex() {
        return index;
    }

    /**
     * Calculates the duration, regular hours, and overtime hours for the interval based on the current workplace settings.
     * @author Mustafa Abbas
     */
    public Duration calculateDuration() {
        duration = Duration.between(start,end);
        regularHours = Duration.ofSeconds(duration.getSeconds());
        return duration;
    }

    public Duration getOverTimeHours() {
        return overTimeHours;
    }

    public Duration getRegularHours() {
        return regularHours;
    }

    /**
     * Calculates the duration, regular hours, and overtime hours for the interval based on the current workplace settings.
     *
     * @param controller The controller used to access the current workplace.
     * @author Mustafa Abbas, Alexandru Som
     */
    public void calculateInterval(Controller controller) {
        long regularSeconds = duration.getSeconds();
        regularSeconds -= breakTime.getSeconds();
        OverTime selectedOverTime = null;

        if (!controller.getCurrentWorkplace().getOverTimes().isEmpty()) {
            for (OverTime overTime : controller.getCurrentWorkplace().getOverTimes()) {
                if(overTime.getDate() != null){
                    if (overTime.getDate().equals(date)) {
                        selectedOverTime = overTime;
                        break;
                    }
                } else if (overTime.getOverTimeDays() != null && overTime.getOverTimeDays().contains(getStart().getDayOfWeek())) {
                    selectedOverTime = overTime;
                }
            }

            if (selectedOverTime != null) {
                if(!selectedOverTime.getStart().isAfter(end.toLocalTime()) && !selectedOverTime.getEnd().isBefore(start.toLocalTime())){
                    LocalTime overTimeStart = LocalTime.from(selectedOverTime.getStart().isAfter(start.toLocalTime()) ? selectedOverTime.getStart() : start);
                    LocalTime overTimeEnd = LocalTime.from(selectedOverTime.getEnd().isBefore(end.toLocalTime()) ? selectedOverTime.getEnd() : end);
                    Duration overTimeDuration = Duration.between(overTimeStart, overTimeEnd);
                    long overTimeSeconds = overTimeDuration.getSeconds();

                    regularSeconds -= overTimeSeconds;
                    this.overTimeHours = duration.minus(getRegularHours());
                    overTimePercentage = selectedOverTime.getPercentage();
                } else {
                    overTimeHours = Duration.ZERO;
                }
                this.regularHours = Duration.ofSeconds(regularSeconds);
            }
        } else {
            this.regularHours = Duration.ofSeconds(regularSeconds);
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


    public int getPercentage() {
        return overTimePercentage;
    }
}
