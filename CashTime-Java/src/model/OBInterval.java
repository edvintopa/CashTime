package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class OBInterval extends Interval {
    private double extraOB;

    public OBInterval(LocalDate date, LocalTime start, LocalTime end, double extraOB) {
        super(date, start, end);
        this.extraOB = extraOB;
    }

    public double getExtraOB() { // gets amount of extraOB, stored in exact amount (not % for example)
        return extraOB;
    }

    public Duration getTimeSpentInOBInterval(Interval interval) {
        Interval tempInterval = interval;
        if (interval.getStart().isBefore(this.start)) { // If starts before OB
            tempInterval.setStart(this.start);
        }
        if (interval.getEnd().isAfter(this.end)) {  // If ends after OB
            tempInterval.setStart(this.end);
        }
        return tempInterval.getDuration();
    }
}
