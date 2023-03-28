package model;

import java.util.ArrayList;
import java.util.List;

public class RateSchedule {
    private List<Interval> intervals;

    public RateSchedule() {
        intervals = new ArrayList<>();
    }

    public void addInterval(Interval interval) {
        intervals.add(interval);
    }
}
