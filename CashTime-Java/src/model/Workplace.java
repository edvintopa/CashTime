package model;

import java.util.ArrayList;
import java.util.List;

public class Workplace {
    private String name;        //Name of workplace
    private double hourlyPay;   //Base hourly pay "grundlön"
    private List<Interval> intervals;

    public Workplace(String name, double pay) {
        this.name = name;
        this.hourlyPay = pay;
        this.intervals = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getPay() {
        return hourlyPay;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }
}
