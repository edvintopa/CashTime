package view;

import com.toedter.calendar.JDateChooser;
import controller.Controller;
import model.Interval;
import model.Workplace;
import view.Button;
import view.JComboBox;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;


public class EconomyPanel extends JPanel {
    private Controller controller;
    private JComboBox workplaces;
    private Button backButton;
    private JLabel regularHoursLabel;
    private JLabel overTimeHoursLabel;
    private JLabel totalPayLabel;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;

    public EconomyPanel(int width, int height, Controller controller) {
        super(null);
        this.controller = controller;
        setSize(width, height); // width = 500, height = 600


        if (controller.getWorkplaces() == null) {
            String[] str = new String[1];
            str[0] = "No workplace added";
            workplaces = new view.JComboBox(str, controller);
        } else {
            workplaces = new JComboBox(controller.getWorkplaces(), controller);
        }
        workplaces.setName("economy");
        workplaces.setSize(150, 30);
        workplaces.setLocation(150, 5);
        workplaces.addActionListener(workplaces);
        this.add(workplaces);

        JLabel workplaceLabel = new JLabel("Workplace:");
        workplaceLabel.setBounds(78, 10, 80, 20);
        this.add(workplaceLabel);

        backButton = new Button("<", controller);
        backButton.setSize(45, 45);
        backButton.setLocation(5, 5);
        backButton.addActionListener(backButton);
        this.add(backButton);

        JLabel startDateLabel = new JLabel("Start date:");
        startDateLabel.setBounds(78, 50, 80, 20);
        this.add(startDateLabel);

        startDateChooser = new JDateChooser();
        startDateChooser.setBounds(150, 50, 150, 30);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        startDateChooser.setDate(cal.getTime());

        startDateChooser.getDateEditor().addPropertyChangeListener(e -> calculate());
        this.add(startDateChooser);



        JLabel endDateLabel = new JLabel("End date:");
        endDateLabel.setBounds(78, 90, 80, 20);
        this.add(endDateLabel);

        endDateChooser = new JDateChooser();
        endDateChooser.setBounds(150, 90, 150, 30);

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDateChooser.setDate(cal.getTime());

        endDateChooser.getDateEditor().addPropertyChangeListener(e -> calculate());
        this.add(endDateChooser);



        JLabel regularHoursTitleLabel = new JLabel("Regular:");
        regularHoursTitleLabel.setBounds(100, 200, 150, 30);
        regularHoursTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        regularHoursTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(regularHoursTitleLabel);
        regularHoursLabel = new JLabel();
        regularHoursLabel.setBounds(225, 200, 100, 30);
        regularHoursLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(regularHoursLabel);

        JLabel overTimeHoursTitleLabel = new JLabel("Overtime:");
        overTimeHoursTitleLabel.setBounds(100, 270, 150, 30);
        overTimeHoursTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        overTimeHoursTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(overTimeHoursTitleLabel);
        overTimeHoursLabel = new JLabel();
        overTimeHoursLabel.setBounds(225, 270, 100, 30);
        overTimeHoursLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(overTimeHoursLabel);

        JLabel totalPayTitleLabel = new JLabel("Total:");
        totalPayTitleLabel.setBounds(100, 340, 150, 30);
        totalPayTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalPayTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(totalPayTitleLabel);
        totalPayLabel = new JLabel();
        totalPayLabel.setBounds(225, 340, 200, 30);
        totalPayLabel.setFont(new Font("Arial", Font.BOLD, 20)); //
        add(totalPayLabel);

        calculate();
    }

    private void calculate() {
        if(controller.getCurrentWorkplace() != null){
            Workplace workplace = controller.getCurrentWorkplace();
            Date startDate = startDateChooser.getDate();
            Date endDate = endDateChooser.getDate();
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
            double totalPay = regularPay + overTimePay;

            regularHoursLabel.setText(String.format("%.1f hours", regularHours.toMinutes() / 60.0));
            overTimeHoursLabel.setText(String.format("%.1f hours", overTimeHours.toMinutes() / 60.0));
            totalPayLabel.setText(String.format("%.2f SEK", totalPay));
        }

    }

    public void updateWorkplaces() {
        workplaces.setModel(new DefaultComboBoxModel(controller.getWorkplaces()));
    }

    public void setSelectedWorkplace(String wpName) {
        workplaces.setSelectedItem(wpName);
        updatePage();
    }

    public void updatePage() {
        calculate();
    }

}
