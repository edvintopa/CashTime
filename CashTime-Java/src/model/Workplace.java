package model;

import java.io.*;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Workplace implements Serializable{
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
        String[][] str = new String[intervals.size()][4];
        for(int i=0; i< intervals.size(); i++){
            str[i][0] = intervals.get(i).date.toString();
            str[i][1] = intervals.get(i).start.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            str[i][2] = intervals.get(i).end.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            str[i][3] = String.valueOf(intervals.get(i).getDuration());
            }
        return str;
    }


}
