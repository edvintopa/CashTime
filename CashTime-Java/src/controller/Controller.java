package controller;

import java.awt.*;
import java.io.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.toedter.calendar.JDateChooser;
import view.EconomyPanel;
import model.Interval;
import model.OverTime;
import model.Workplace;
import view.MainFrame;
import view.MainPanel;
import view.HistoryPanel;
import view.OverTimePanel;

import javax.swing.*;
/**
 * The Controller class handles the logic and interaction between the various views and models in the application.
 * It manages the clock-in/clock-out functionality, workplace management, intervals, overtime, and UI updates.
 *
 * @author Mustafa Abbas
 */
public class Controller {
    private boolean isClockedIn;
    private List<Workplace> workplaces;
    private Workplace currentWorkplace;
    private Interval currentInterval;
    private MainFrame view;
    private MainPanel mainPanel;
    private HistoryPanel historyPanel;
    private EconomyPanel economyPanel;
    private OverTimePanel overTimePanel;

    /**
     * The main method to start the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new Controller();
    }

    /**
     * Constructs a new instance of the Controller class.
     * Initializes the application by creating the necessary views and loading existing workplaces.
     * @author Mustafa Abbas, Alexandru Som
     */
    public Controller() {
        isClockedIn = false;
        workplaces = new ArrayList<>();
        createWorkplace();
        if(workplaces.size() != 0){
            currentWorkplace = workplaces.get(0);
        }
        int height = 800;
        int width = 600;

        historyPanel = new HistoryPanel(width, height, this);
        economyPanel = new EconomyPanel(width, height, this);
        mainPanel = new MainPanel(width, height, this);
        overTimePanel = new OverTimePanel(width, height, this);
        view = new MainFrame(width, height, this, mainPanel, historyPanel, economyPanel, overTimePanel);
    }

    /**
     * Checks if there are existing workplaces and loads them if available, otherwise prompts the user to create new workplaces.
     * @author Mustafa Abbas, Alexandru Som
     */
    private void createWorkplace() {
        boolean wpAvailable = checkWorkplaces();
        if(wpAvailable){
            loadWorkplaces();
        }
    }

    /**
     * Calculates the time spent in different rate schedules for the given interval and workplace.
     * This method can be customized based on the specific requirements of the application.
     *
     * @param interval  the interval for which the time spent is to be calculated
     * @param workplace the workplace for which the time spent is to be calculated
     * @author Mustafa Abbas, Alexandru Som
     */
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

    /**
     * Retrieves the names of all workplaces as an array of strings.
     *
     * @return an array of workplace names
     * @author Mustafa Abbas
     */
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

    /**
     * Retrieves the currently selected workplace.
     *
     * @return the current workplace
     * @author Mustafa Abbas
     */
    public Workplace getCurrentWorkplace() {
        return currentWorkplace;
    }

    /**
     * Sets the current workplace based on the given workplace name and window type.
     * Updates the selected workplace in all relevant panels.
     *
     * @param wpName the name of the workplace to set as current
     * @param window the type of window ('main' or 'other')
     * @author Alexandru Som, Mustafa Abbas
     */
    public void setCurrentWorkplace(String wpName, String window) {
        if(window == "main"){
            for(Workplace w : workplaces){
                if(w.getName() == wpName){
                    currentWorkplace = w;
                    historyPanel.setSelectedWorkplace(w.getName());
                    historyPanel.updateTable();
                    economyPanel.setSelectedWorkplace(w.getName());
                    overTimePanel.setSelectedWorkplace(w.getName());
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

    /**
     * Checks if the user is currently clocked in.
     *
     * @return true if clocked in, false otherwise
     * @author Mustafa Abbas
     */
    public boolean isClockedIn() {
        return isClockedIn;
    }

    /**
     * Starts a new interval and sets the clock-in time.
     * Checks if the start time conflicts with existing intervals on the same date.
     * Displays an error message if there is a conflict.
     * @author Mustafa Abbas, Alexandru Som
     */
    public void startInterval() {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime currentTime = LocalDateTime.now();

        // Check if the start time falls within existing intervals on the same date
        boolean isStartTimeConflicting = currentWorkplace.getIntervals().stream()
                .anyMatch(interval -> interval.getStart().toLocalDate().equals(currentDate)
                        && interval.getStart().toLocalTime().isBefore(currentTime.toLocalTime())
                        && interval.getEnd().toLocalTime().isAfter(currentTime.toLocalTime()));

        if (isStartTimeConflicting) {
            // Start time conflicts with existing intervals, notify the user or take appropriate action
            JOptionPane.showMessageDialog(null, "Cannot start the interval. Start time conflicts with existing intervals.");
            return;
        }


        currentInterval = new Interval(currentWorkplace.getIntervalIndex(), currentDate, currentTime);
        currentWorkplace.incrementIntervalIndex();
        isClockedIn = true;
        mainPanel.startInterval();
    }

    /**
     * Ends the current interval and sets the clock-out time.
     * Calculates the duration and interval based on the clock-in and clock-out times.
     * Saves the interval to the current workplace and updates the UI.
     * @author Mustafa Abbas
     */
    public void endInterval(){
        currentInterval.setEnd(LocalDateTime.now());
        currentInterval.calculateDuration();
        currentInterval.calculateInterval(this);
        currentWorkplace.getIntervals().add(currentInterval);
        currentWorkplace.saveWorkplaceToFile();
        isClockedIn = false;
        mainPanel.endInterval();
        historyPanel.updateTable();
    }
    /**
     * Updates the duration and intervals for all existing intervals in the current workplace.
     * @author Mustafa Abbas, Alexandru Som
     */
    public void updateInterval(){
        for(int i=0; i<currentWorkplace.getIntervals().size(); i++){
            currentWorkplace.getIntervals().get(i).calculateInterval(this);
        }
    }

    /**
     * Starts a break within the current interval.
     * @author Mustafa Abbas
     */
    public void startBreak(){
        mainPanel.startBreak();
        currentInterval.startBreak();
    }

    /**
     * Ends the current break within the current interval.
     * @author Mustafa Abbas
     */
    public void endBreak() {
        mainPanel.endBreak();
        currentInterval.endBreak();
    }

    /**
     * Checks if there are existing workplaces by attempting to read the "workplaces.text" file.
     *
     * @return true if workplaces exist, false otherwise
     * @author Mustafa Abbas, Alexandru Som
     */
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

    /**
     * Loads the workplaces from the "workplaces.text" file and adds them to the application.
     * @author Mustafa Abbas, Alexandru Som
     */
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
                newWorkplace = newWorkplace.loadWorkplaceFromFile();
                workplaces.add(newWorkplace);
            }
        } catch (NullPointerException n) {}
    }

    /**
     * Adds a new workplace based on user input.
     * Saves the workplace to a file and updates the UI.
     * @author Mustafa Abbas, Alexandru Som
     */
    public void addWorkspace(){
        String name = JOptionPane.showInputDialog(null, "Type in your workplace:");
        int hourlyPay = Integer.parseInt(JOptionPane.showInputDialog(null, "Type in your hourly pay:"));
        if(!name.isEmpty()){
            Workplace newWorkplace = new Workplace(name, hourlyPay);
            workplaces.add(newWorkplace);
            newWorkplace.saveWorkplaceToFile();
        }
        if(currentWorkplace == null){
            currentWorkplace = workplaces.get(0);
        }
        mainPanel.update();
        historyPanel.updateWorkplaces();
        economyPanel.updateWorkplaces();
        overTimePanel.updateWorkplaces();

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
    /**
     * Adds overtime based on user input.
     * Validates the input and checks for conflicts with existing overtime.
     * Saves the overtime to the current workplace and updates the economy panel.
     * @author Mustafa Abbas, Alexandru Som
     */
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
                    startDate = null;
                } else {
                    return;
                }
                break;

            case 1: // Specific overtime
                JPanel specificPanel = new JPanel(new GridLayout(4, 2));
                specificPanel.add(new JLabel("Date:"));
                JDateChooser dateChooser = new JDateChooser();
                specificPanel.add(dateChooser);
                specificPanel.add(new JLabel("Start Time:"));
                startTimeField = new JTextField("00:00");
                specificPanel.add(startTimeField);
                specificPanel.add(new JLabel("End Time:"));
                endTimeField = new JTextField("00:00");
                specificPanel.add(endTimeField);
                specificPanel.add(new JLabel("Percentage:"));
                percentageField = new JTextField("50");
                specificPanel.add(percentageField);
                int specificResult = JOptionPane.showConfirmDialog(null, specificPanel, "Specific Overtime", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (specificResult == JOptionPane.OK_OPTION) {
                    LocalDate specificDate = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalTime specificStartTime = LocalTime.parse(startTimeField.getText());
                    LocalTime specificEndTime = LocalTime.parse(endTimeField.getText());
                    percentage = Integer.parseInt(percentageField.getText());

                    if (checkSpecificOverTimeOverlap(specificDate)) {
                        JOptionPane.showMessageDialog(null, "An existing specific overtime already exists for this date.");
                        return;
                    }

                    overTimeDays = null;
                    startDate = specificDate;
                    endDate = specificDate;
                    startDateTime = specificStartTime.atDate(specificDate);
                    endDateTime = specificEndTime.atDate(specificDate);
                } else {
                    return;
                }
                break;


            default:
                return;
        }

        for (OverTime overTime : currentWorkplace.getOverTimes()) {
            if (overTime.getOverTimeDays() != null && overTime.getOverTimeDays().equals(overTimeDays)) {
                if (overTime.getStart().isBefore(LocalTime.from(endDateTime)) && overTime.getEnd().isAfter(LocalTime.from(startDateTime))) {
                    JOptionPane.showMessageDialog(null, "The new overtime overlaps with an existing overtime of the same kind.");
                    return;
                }
            }
        }

        currentWorkplace.getOverTimes().add(new OverTime(startDate, startDateTime.toLocalTime(), endDateTime.toLocalTime(), percentage, overTimeDays));
        currentWorkplace.saveWorkplaceToFile();
        economyPanel.updatePage();
    }

    /**
     * Checks if there is any specific overtime overlap for the given date.
     * @param specificDate The date to check for overlap.
     * @return true if there is overlap, false otherwise.
     * @author Mustafa Abbas
     */
    private boolean checkSpecificOverTimeOverlap(LocalDate specificDate) {
        for (OverTime overTime : currentWorkplace.getOverTimes()) {
            if (overTime.getOverTimeDays() == null && overTime.getDate().isEqual(specificDate)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Shows the history panel and updates the interval information.
     * @author Mustafa Abbas
     */
    public void showHistory() {
        if(currentWorkplace!=null) updateInterval();
        mainPanel.setVisible(false);
        historyPanel.setVisible(true);
        historyPanel.updateTable();
    }

    /**
     * Shows the main panel and hides other panels.
     * @author Mustafa Abbas
     */
    public void showMainPanel() {
        mainPanel.setVisible(true);
        historyPanel.setVisible(false);
        economyPanel.setVisible(false);
        overTimePanel.setVisible(false);
    }

    /**
     * Shows the economy panel.
     * @author Mustafa Abbas
     */
    public void showEconomy() {
        mainPanel.setVisible(false);
        economyPanel.setVisible(true);
        economyPanel.updatePage();
    }
    /**
     * Shows the overtime panel.
     * @author Mustafa Abbas
     */
    public void showOverTime() {
        overTimePanel.updatePage();
        mainPanel.setVisible(false);
        overTimePanel.setVisible(true);
    }


    /**
     * Removes the selected interval from the current workplace.
     * @author Mustafa Abbas
     */
    public void removeInterval() {
        int row = historyPanel.getTable().getSelectedRow();
        currentWorkplace.getIntervals().remove(row);
        historyPanel.updateTable();
        currentWorkplace.saveWorkplaceToFile();
    }


    public void removeOvertime(OverTime overtime) {

    }
}
