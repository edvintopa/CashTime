package model;

import java.util.ArrayList;
import java.util.List;

public class RateSchedule {
    private List<OBInterval> OBIntervals;

    public RateSchedule() {
        OBIntervals = new ArrayList<>();
    }

    public void addInterval(OBInterval interval) {
        OBIntervals.add(interval);
    }
}
