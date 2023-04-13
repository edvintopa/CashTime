package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Workplace implements Serializable{
    private String name;        //Name of workplace
    private double hourlyPay;   //Base hourly pay "grundlön"
    private List<Interval> intervals;
    private String history;

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


}
