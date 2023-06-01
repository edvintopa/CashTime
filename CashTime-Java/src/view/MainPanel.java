package view;

import controller.Controller;
import model.Interval;
import model.Workplace;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;


public class MainPanel extends JPanel {
    private Controller controller;
    private Button clockInOut;
    private Button clockBreak;
    private JComboBox workplaces;
    private Button history;
    private Button economy;
    private JLabel totalPayLabel;
    private JLabel thisMonthTotalHours;
    private int width;
    private Settings settings;


    public MainPanel(int width, int height, Controller controller){
        super(null);
        this.controller = controller;
        setSize(width,height);
        this.width = width;

        setBackground(Color.BLACK);
/*
        JLabel titleLabel = new JLabel("CashTime");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setSize(200, 50);
        titleLabel.setLocation((width - titleLabel.getWidth()) / 2, 85+150);
        this.add(titleLabel);

 */

        if(controller.getWorkplaces()==null){
            String[] str = new String[1];
            str[0] = "No workplace added";
            workplaces = new JComboBox(str, controller);
        } else {
            workplaces = new JComboBox(controller.getWorkplaces(), controller);
        }
        workplaces.setFont(new Font("Arial", Font.PLAIN, 16));
        workplaces.setName("main");
        workplaces.setSize(120, 30);
        workplaces.setLocation((width - workplaces.getWidth()) / 2, 160+150);
        workplaces.setAlignmentY(CENTER_ALIGNMENT);
        workplaces.addActionListener(workplaces);
        this.add(workplaces);

        clockInOut = new Button("IN", controller);
        clockInOut.setFont(new Font("Arial", Font.BOLD, 16));
        clockInOut.setSize(100, 50);
        clockInOut.setLocation((width - clockInOut.getWidth()) / 2, 220+150);
        clockInOut.setBorder(new RoundedBorder(10)); //10 is the radius
        clockInOut.setBackground(new Color(48, 210, 91));
        clockInOut.setForeground(new Color(255, 255, 255));
        clockInOut.addActionListener(clockInOut);
        this.add(clockInOut);

        clockBreak = new Button("Break", controller);
        clockBreak.setFont(new Font("Arial", Font.BOLD, 16));
        clockBreak.setSize(100, 50);
        clockBreak.setLocation(((width - clockInOut.getWidth()) / 2) - (clockInOut.getWidth() / 2) - 10, 220+150);
        clockBreak.setBorder(new RoundedBorder(10)); //10 is the radius
        clockBreak.setBackground(new Color(255, 216, 25));
        clockBreak.setForeground(new Color(255, 255, 255));
        clockBreak.addActionListener(clockBreak);
        this.add(clockBreak);
        clockBreak.setVisible(false);

        /*
        Button workplaceAddButton = new Button("+", controller);
        workplaceAddButton.setName("addWorkplace");
        workplaceAddButton.setFont(new Font("Arial", Font.PLAIN, 20));
        workplaceAddButton.setSize(50, 50);
        workplaceAddButton.setLocation(width - workplaceAddButton.getWidth() - 20, 20);
        workplaceAddButton.setBackground(Color.BLACK);
        workplaceAddButton.setForeground(new Color(255, 215, 0));
        workplaceAddButton.addActionListener(workplaceAddButton);
        this.add(workplaceAddButton);

         */

        history = new Button("History", controller);
        history.setFont(new Font("Arial", Font.PLAIN, 16));
        history.setSize(120, 45);
        history.setLocation((width - history.getWidth()) / 2, 500);
        history.setBorder(new RoundedBorder(10)); //10 is the radius
        history.setBackground(new Color(13, 132, 252));
        history.setForeground(new Color(255, 255, 255));
        history.addActionListener(history);
        this.add(history);

        economy = new Button("Economy", controller);
        economy.setFont(new Font("Arial", Font.PLAIN, 16));
        economy.setSize(120, 45);
        economy.setLocation(10, 150);
        economy.setBackground(Color.BLACK);
        economy.setForeground(new Color(255, 215, 0));
        economy.addActionListener(economy);
        this.add(economy);

        Button overtime = new Button("Overtimes", controller);
        overtime.setFont(new Font("Arial", Font.PLAIN, 16));
        overtime.setSize(120, 45);
        overtime.setLocation(10, 200);
        overtime.setBackground(Color.BLACK);
        overtime.setForeground(new Color(255, 215, 0));
        overtime.addActionListener(overtime);
        this.add(overtime);

/*
        Button overTimeAddButton = new Button("+", controller);
        overTimeAddButton.setName("addOverTime");
        overTimeAddButton.setFont(new Font("Arial", Font.PLAIN, 20));
        overTimeAddButton.setSize(50, 50);
        overTimeAddButton.setLocation(width - overTimeAddButton.getWidth() - 20, 90);
        overTimeAddButton.setBackground(Color.BLACK);
        overTimeAddButton.setForeground(new Color(255, 215, 0));
        overTimeAddButton.addActionListener(overTimeAddButton);
        this.add(overTimeAddButton);

 */


        //NEW CODE
        JLabel thisMonthHoursLabel = new JLabel("denna månad ");
        thisMonthHoursLabel.setBounds(10, 55, 140, 30);
        thisMonthHoursLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        thisMonthHoursLabel.setForeground(new Color(255,255,255));
        add(thisMonthHoursLabel);

        thisMonthTotalHours = new JLabel();
        thisMonthTotalHours.setBounds(90, 55, 100, 30);
        thisMonthTotalHours.setFont(new Font("Arial", Font.BOLD, 14));
        thisMonthTotalHours.setForeground(new Color(255,255,255));
        add(thisMonthTotalHours);

        totalPayLabel = new JLabel();
        totalPayLabel.setBounds(10, 25, 200, 30);
        totalPayLabel.setFont(new Font("Arial", Font.BOLD, 28));
        totalPayLabel.setForeground(new Color(255,255,255));
        add(totalPayLabel);
        updateTotalPayLabel();


        ImageIcon icon = new ImageIcon("settings.png");
        Image img = icon.getImage() ;
        Image newimg = img.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ) ;
        icon = new ImageIcon( newimg );

        Border emptyBorder = BorderFactory.createEmptyBorder();

        Button settingsButton = new Button("settings", controller);
        settingsButton.setBackground(Color.BLACK);
        settingsButton.setBounds(width-80, 20, 50,50);
        settingsButton.setIcon(icon);
        settingsButton.setVerticalAlignment(SwingConstants.CENTER);
        settingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        settingsButton.setBorder(emptyBorder);
        settingsButton.addActionListener(settingsButton);
        add(settingsButton);
    }


    public void update(){
        workplaces.setModel(new DefaultComboBoxModel(controller.getWorkplaces()));
    }

    public void setSelectedWorkplace(String wpName) {
        workplaces.setSelectedItem(wpName);
    }

    public JButton getHistoryButton() {
        return history;
    }

    public void startInterval() {
        clockInOut.setText("OUT");
        clockInOut.setBackground(new Color(255, 71, 63));
        clockInOut.setLocation(((width - clockInOut.getWidth()) / 2) + (clockInOut.getWidth() / 2) +10, 220+150);
        clockBreak.setVisible(true);
    }

    public void endInterval(){
        clockInOut.setText("IN");
        clockInOut.setBackground(new Color(48, 210, 91));
        clockInOut.setLocation((width - clockInOut.getWidth()) / 2, 220+150);
        clockBreak.setVisible(false);
        updateTotalPayLabel();
    }

    public void startBreak() {
        clockInOut.setVisible(false);
        clockBreak.setText("End Break");
    }

    public void endBreak() {
        clockInOut.setVisible(true);
        clockBreak.setText("Break");
    }

    public void updateTotalPayLabel() {
        // Get the current month's start and end dates
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.withDayOfMonth(1);
        LocalDate endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        // Convert the start and end dates to Date objects
        Date startDateObj = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateObj = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Call the calculate() method with the start and end dates
        double totalPay = calculate(startDateObj, endDateObj);

        // Update the label text
        DecimalFormat decimalFormat = new DecimalFormat("#"); // Format the total pay with two decimal places
        totalPayLabel.setText(decimalFormat.format(totalPay) + " kr");

        // Refresh the label
        totalPayLabel.repaint();
    }

    private double calculate(Date startDate, Date endDate) {
        // Your existing calculate() method implementation goes here
        double totalPay = 0;
        if(controller.getCurrentWorkplace() != null){
            Workplace workplace = controller.getCurrentWorkplace();
            double hourlyPay = workplace.getHourlyPay();
            double overTimePay = 0;

            Duration regularHours = Duration.ZERO;
            Duration overTimeHours = Duration.ZERO;
            for (Interval interval : workplace.getIntervals()) {
                if (interval.getDate().isAfter(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || interval.getDate().isEqual(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) &&
                        interval.getDate().isBefore(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || interval.getDate().isEqual(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                    regularHours = regularHours.plus(interval.getRegularHours());

                    overTimeHours = overTimeHours.plus(interval.getOverTimeHours());
                    double overTimeRate = 1 + (interval.getPercentage() / 100.0);
                    overTimePay += interval.getOverTimeHours().toMinutes() / 60.0 * hourlyPay * overTimeRate;

                }
            }

            double regularPay = regularHours.toMinutes() / 60.0 * hourlyPay;
            //double overTimePay = overTimeHours.toMinutes() / 60.0 * hourlyPay * 1.5;
            totalPay = regularPay + overTimePay;

            thisMonthTotalHours.setText(String.format("%.1f h", regularHours.toMinutes() / 60.0));
            //overTimeHoursLabel.setText(String.format("%.1f hours", overTimeHours.toMinutes() / 60.0));
            //totalPayLabel.setText(String.format("%.2f SEK", totalPay));
        }
        // Return the calculated total pay
        return totalPay;
    }
}
