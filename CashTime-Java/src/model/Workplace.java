package model;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class Workplace implements Serializable{
    private String name;        //Name of workplace
    private double hourlyPay;   //Base hourly pay "grundlön"
    private List<Interval> intervals;
    private int intervalIndex;

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


    public void saveWorkplaceInDatFile() {
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

    public Workplace loadFromDatFile(){
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

    public String[][] getIntervalHistoryToString(){
        String[][] str = new String[intervals.size()][5];
        for(int i=0; i< intervals.size(); i++){
            str[i][0] = String.valueOf(intervals.get(i).getIntervalId());
            str[i][1] = intervals.get(i).date.toString();
            str[i][2] = intervals.get(i).start.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            str[i][3] = intervals.get(i).end.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            str[i][4] = String.valueOf(intervals.get(i).getDuration());
            }
        return str;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void incrementIntervalIndex() {
        this.intervalIndex++;
    }
}


