package controller;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Interval;
import model.Workplace;
import view.MainFrame;
import view.MainPanel;
import view.HistoryPanel;

import javax.swing.*;

public class Controller {
    private boolean isClockedIn;
    private List<Workplace> workplaces;
    private Workplace currentWorkplace;
    private Interval currentInterval;
    private MainFrame view;
    private MainPanel mainPanel;
    private HistoryPanel historyPanel;


    public static void main(String[] args) {
        new Controller();
    }


    public Controller() {
        isClockedIn = false;
        workplaces = new ArrayList<>();
        createWorkplace();
        if(workplaces.size() != 0){
            currentWorkplace = workplaces.get(0);
        }

        historyPanel = new HistoryPanel(400, 600, this);
        mainPanel = new MainPanel(400, 600, this);
        view = new MainFrame(400, 600, this, mainPanel, historyPanel);
    }

    private void createWorkplace() {
        boolean wpAvailable = checkWorkplaces();
        if(wpAvailable){
            loadWorkplaces();
        }
    }

    private void calcTimeSpent(Interval interval, Workplace workplace) {
        //Duration total = interval.getDuration();
        //Duration ob1 = workplace.getRateSchedule().get(0).getTimeSpentInOBInterval(interval);
        //Duration ob2 = workplace.getRateSchedule().get(1).getTimeSpentInOBInterval(interval);
        //If there are multiple intervals to consider then, loop through them and create a list for each entry:
        /*
         * get list size
         * loop through list
         * 
         * HashMap <OBid, AmountOfTime> ?? (suggestion)
         */

        //System.out.println("Total time: " + total.toHours());
        //System.out.println("OB time 1: " + ob1.toHours());
        //System.out.println("OB time 2: " + ob2.toHours());
    }

    public String[] getWorkplaces() {
        String[] str = new String[workplaces.size()];

        if(workplaces.size()==0){
            return null;
        }

        for(int i=0; i< workplaces.size(); i++){
            str[i] = workplaces.get(i).getName();
        }
        return str;
    }

    public Workplace getCurrentWorkplace() {
        return currentWorkplace;
    }

    public void setCurrentWorkplace(String wpName, String window) {
        if(window == "main"){
            for(Workplace w : workplaces){
                if(w.getName() == wpName){
                    currentWorkplace = w;
                    historyPanel.setSelectedWorkplace(w.getName());
                    //historyPanel.update();
                    break;
                }
            }
        } else {
            for(Workplace w : workplaces){
                if(w.getName() == wpName){
                    currentWorkplace = w;
                    mainPanel.setSelectedWorkplace(w.getName());
                    break;
                }
            }
        }

    }


    public boolean isClockedIn() {
        return isClockedIn;
    }


    public void startInterval() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        currentInterval = new Interval(date, time);
        isClockedIn = true;
        mainPanel.setClockBreak(true);
    }

    public void endInterval(){
        currentInterval.setEnd(LocalTime.now());
        currentWorkplace.getIntervals().add(currentInterval);
        currentWorkplace.save();
        System.out.println(currentInterval.getDuration());
        isClockedIn = false;
        mainPanel.setClockBreak(false);
        historyPanel.update();
    }

    public void breakInterval(){
        currentInterval.setBreakTime(10);
    }


    public boolean checkWorkplaces(){
        File file = new File("workplaces.text");
        Scanner sc;
        try {
            sc = new Scanner(file);
            return true;
        } catch (FileNotFoundException e) {
            try {
                FileWriter writer = new FileWriter("workplaces.text");
                return false;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    public void loadWorkplaces(){
        File file = new File("workplaces.text");
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] str;
        int i=0;
        try {
            while(sc.hasNext()){
                String s = sc.nextLine();
                str = s.split(", ");
                String name = str[0];
                double hourlyPay = Double.parseDouble(str[1]);
                Workplace newWorkplace = new Workplace(name, hourlyPay);
                newWorkplace = newWorkplace.load();
                workplaces.add(newWorkplace);

            }
        } catch (NullPointerException n) {}
    }


    public void addWorkspace(){
        String name = JOptionPane.showInputDialog(null, "Type in your workplace:");
        int hourlyPay = Integer.parseInt(JOptionPane.showInputDialog(null, "Type in your hourly pay:"));
        if(!name.isEmpty())
        workplaces.add(new Workplace(name, hourlyPay));
        if(currentWorkplace == null){
            currentWorkplace = workplaces.get(0);
        }
        mainPanel.update();
        historyPanel.update();

        FileWriter writer;
        try {
            writer = new FileWriter("workplaces.text", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            writer.write(name + ", " + hourlyPay + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void showHistory() {
        mainPanel.setVisible(false);
        historyPanel.setVisible(true);
    }

    public void showMainPanel() {
        mainPanel.setVisible(true);
        historyPanel.setVisible(false);
    }
}
