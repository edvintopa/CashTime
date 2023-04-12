package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.Duration;

import model.Interval;
import model.OBInterval;
import model.Workplace;
import view.MainFrame;

public class Controller {
    private boolean isClockedIn;
    private List<Workplace> workplaces;
    private Workplace currentWorkplace;
    private Interval currentInterval;
    private MainFrame view;


    public static void main(String[] args) {
        new Controller();
    }


    public Controller() {
        isClockedIn = false;
        workplaces = new ArrayList<>();
        createWorkplace();
        currentWorkplace = workplaces.get(0);
        view = new MainFrame(400, 600, this);
    }

    private void createWorkplace() {
        workplaces.add(new Workplace("ICA", 200));
        workplaces.add(new Workplace("Willis", 120));



        //workplaces.get(0).getRateSchedule().add(intervall1);
        //workplaces.get(0).getRateSchedule().add(intervall2);
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
        for(int i=0; i< workplaces.size(); i++){
            str[i] = workplaces.get(i).getName();
        }
        return str;
    }

    public Workplace getCurrentWorkplace() {
        return currentWorkplace;
    }

    public void setCurrentWorkplace(String workName) {
        for(Workplace w : workplaces){
            if(w.getName() == workName){
                currentWorkplace = w;
                break;
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
    }

    public void endInterval(){
        currentInterval.setEnd(LocalTime.now());
        currentWorkplace.getIntervals().add(currentInterval);
        System.out.println(currentInterval.getDuration().toHours());
        isClockedIn = false;
    }
}
