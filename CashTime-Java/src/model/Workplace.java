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

    public void saveWorkplaceToFile() {
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

    public Workplace loadWorkplaceFromFile(){
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
            str[i][0] = intervals.get(i).getStart().toLocalDate().toString();
            str[i][1] = intervals.get(i).getStart().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            str[i][2] = intervals.get(i).getEnd().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));

            Duration duration = intervals.get(i).getDuration();
            if(duration != null){
                long hours = duration.toHours();
                long minutes = duration.minusHours(hours).toMinutes();
                String durationString = String.format("PT%dH%dM", hours, minutes);
                str[i][3] = durationString;
            } else {
                str[i][3] = String.valueOf(Duration.ZERO);
            }

            Duration overTimeDuration = intervals.get(i).getOverTimeHours();
            if (overTimeDuration != null) {
                long overTimeHours = overTimeDuration.toHours();
                long overTimeMinutes = overTimeDuration.minusHours(overTimeHours).toMinutes();
                String overTimeDurationString = String.format("PT%dH%dM", overTimeHours, overTimeMinutes);
                str[i][4] = overTimeDurationString;
            } else {
                str[i][4] = "";
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