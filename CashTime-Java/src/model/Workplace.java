package model;

import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class Workplace implements Serializable{
    private String name;
    private double hourlyPay;
    private List<Interval> intervals;
    private int intervalIndex;
    private List<OverTime> overTimes;

    public Workplace(String name, double pay) {
        this.name = name;
        this.hourlyPay = pay;
        this.intervals = new ArrayList<>();
        this.overTimes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getHourlyPay() {
        return hourlyPay;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }


    public void save() {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(name + ".dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Workplace load(){
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream(name + ".dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return (Workplace) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public String[][] getHistory(){
        String[][] str = new String[intervals.size()][6];
        for(int i=0; i< intervals.size(); i++){
            str[i][0] = String.valueOf(intervals.get(i).getIndex());
            str[i][1] = intervals.get(i).date.toString();
            str[i][2] = intervals.get(i).start.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            str[i][3] = intervals.get(i).end.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));

            Duration duration = intervals.get(i).getDuration();
            long hours = duration.toHours();
            long minutes = duration.minusHours(hours).toMinutes();
            String durationString = String.format("%d:%02d", hours, minutes);
            str[i][4] = durationString;

            Duration overTimeDuration = intervals.get(i).getOverTimeHours();
            if (overTimeDuration != null) {
                long overTimeHours = overTimeDuration.toHours();
                long overTimeMinutes = overTimeDuration.minusHours(overTimeHours).toMinutes();
                String overTimeDurationString = String.format("%d:%02d", overTimeHours, overTimeMinutes);
                str[i][5] = overTimeDurationString;
            } else {
                str[i][5] = "";
            }

            }
        return str;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void incrementIntervalIndex() {
        this.intervalIndex++;
    }

    public List<OverTime> getOverTimes() {
        return overTimes;
    }


    @Override
    public String toString(){
        return name;
    }
}