package controller;

import java.awt.*;
import java.io.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.EconomyPanel;
import model.Interval;
import model.OverTime;
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
    private EconomyPanel economyPanel;


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

        historyPanel = new HistoryPanel(500, 600, this);
        economyPanel = new EconomyPanel(500, 600, this);
        mainPanel = new MainPanel(500, 600, this);
        view = new MainFrame(500, 600, this, mainPanel, historyPanel, economyPanel);
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
                    historyPanel.updateTable();
                    economyPanel.setSelectedWorkplace(w.getName());
                    //economyPanel.update();
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
        LocalDateTime time = LocalDateTime.now();
        currentInterval = new Interval(currentWorkplace.getIntervalIndex(), date, time);
        currentWorkplace.incrementIntervalIndex();
        isClockedIn = true;
        mainPanel.setClockBreak(true);
    }

    public void endInterval(){
        currentInterval.setEnd(LocalDateTime.now());
        currentInterval.calculateDuration();
        currentInterval.calculateInterval(this);
        currentWorkplace.getIntervals().add(currentInterval);
        currentWorkplace.save();
        isClockedIn = false;
        mainPanel.setClockBreak(false);
        historyPanel.updateTable();
    }

    public void updateInterval(){
        for(int i=0; i<currentWorkplace.getIntervals().size(); i++){
            currentWorkplace.getIntervals().get(i).calculateInterval(this);
        }
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
        if(!name.isEmpty()){
            Workplace newWorkplace = new Workplace(name, hourlyPay);
            workplaces.add(newWorkplace);
            newWorkplace.save();
        }
        if(currentWorkplace == null){
            currentWorkplace = workplaces.get(0);
        }
        mainPanel.update();
        historyPanel.updateWorkplaces();
        economyPanel.updateWorkplaces();

        //Add the workplace name into the local file workplaces.text
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

    public void addOverTime() {
        Object[] options = {"Weekly Overtime", "Specific Overtime"};
        int choice = JOptionPane.showOptionDialog(null, "Choose overtime type:", "Overtime", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        int percentage = 0;
        List<DayOfWeek> overTimeDays = null;
        switch (choice) {
            case 0: // Weekly overtime
                JPanel panel = new JPanel(new GridLayout(4, 2));
                panel.add(new JLabel("Start Time:"));
                JTextField startTimeField = new JTextField("00:00");
                panel.add(startTimeField);
                panel.add(new JLabel("End Time:"));
                JTextField endTimeField = new JTextField("00:00");
                panel.add(endTimeField);
                panel.add(new JLabel("Percentage:"));
                JTextField percentageField = new JTextField("50");
                panel.add(percentageField);
                DayOfWeek[] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
                JCheckBox[] checkBoxes = new JCheckBox[7];
                for (int i = 0; i < checkBoxes.length; i++) {
                    checkBoxes[i] = new JCheckBox(daysOfWeek[i].toString());
                    panel.add(checkBoxes[i]);
                }
                int result = JOptionPane.showConfirmDialog(null, panel, "Weekly Overtime", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    LocalTime startTime = LocalTime.parse(startTimeField.getText());
                    LocalTime endTime = LocalTime.parse(endTimeField.getText());
                    percentage = Integer.parseInt(percentageField.getText());
                    overTimeDays = new ArrayList<>();
                    for (int i = 0; i < checkBoxes.length; i++) {
                        if (checkBoxes[i].isSelected()) {
                            overTimeDays.add(daysOfWeek[i]);
                        }
                    }
                    startDate = LocalDate.now();
                    endDate = startDate.plusDays(6);
                    while (!overTimeDays.contains(startDate.getDayOfWeek())) {
                        startDate = startDate.plusDays(1);
                    }
                    while (!overTimeDays.contains(endDate.getDayOfWeek())) {
                        endDate = endDate.minusDays(1);
                    }
                    startDateTime = startTime.atDate(startDate);
                    endDateTime = endTime.atDate(startDate);
                } else {
                    return;
                }
                break;

            case 1: // Specific overtime
                JPanel specificPanel = new JPanel(new GridLayout(4, 2));
                specificPanel.add(new JLabel("Start Date:"));
                JTextField startDateField = new JTextField();
                specificPanel.add(startDateField);
                specificPanel.add(new JLabel("Start Time:"));
                startTimeField = new JTextField("00:00");
                specificPanel.add(startTimeField);
                specificPanel.add(new JLabel("End Date:"));
                JTextField endDateField = new JTextField();
                specificPanel.add(endDateField);
                specificPanel.add(new JLabel("End Time:"));
                endTimeField = new JTextField("00:00");
                specificPanel.add(endTimeField);
                specificPanel.add(new JLabel("Percentage:"));
                percentageField = new JTextField("50");
                specificPanel.add(percentageField);
                int specificResult = JOptionPane.showConfirmDialog(null, specificPanel, "Specific Overtime", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (specificResult == JOptionPane.OK_OPTION) {
                    LocalDate specificStartDate = LocalDate.parse(startDateField.getText());
                    LocalTime specificStartTime = LocalTime.parse(startTimeField.getText());
                    LocalDate specificEndDate = LocalDate.parse(endDateField.getText());
                    LocalTime specificEndTime = LocalTime.parse(endTimeField.getText());
                    percentage = Integer.parseInt(percentageField.getText());
                    overTimeDays = null;
                    startDate = specificStartDate;
                    endDate = specificEndDate;
                    startDateTime = specificStartTime.atDate(specificStartDate);
                    endDateTime = specificEndTime.atDate(specificEndDate);
                } else {
                    return;
                }
                break;

            default:
                return;
        }

        for (OverTime overTime : currentWorkplace.getOverTimes()) {
            if (overTime.getOverTimeDays() != null && overTime.getOverTimeDays().equals(overTimeDays)) {
                if (overTime.getStart().isBefore(endDateTime) && overTime.getEnd().isAfter(startDateTime)) {
                    JOptionPane.showMessageDialog(null, "The new overtime overlaps with an existing overtime of the same kind.");
                    return;
                }
            }
        }

        currentWorkplace.getOverTimes().add(new OverTime(startDate, startDateTime, endDateTime, percentage, overTimeDays));
    }

    public void showHistory() {
        if(currentWorkplace!=null) updateInterval();
        mainPanel.setVisible(false);
        historyPanel.setVisible(true);
        historyPanel.updateTable();
    }

    public void showMainPanel() {
        mainPanel.setVisible(true);
        historyPanel.setVisible(false);
        economyPanel.setVisible(false);
    }

    public void showEconomy() {
        mainPanel.setVisible(false);
        economyPanel.setVisible(true);
        economyPanel.updatePage();
    }

    public void showOverTime() {

    }
}
