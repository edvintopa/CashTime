package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class Settings extends JPanel {
    private Controller controller;
    private Button backButton;

    public Settings(int width, int height, Controller controller){
        super(null);
        setSize(width, height);
        setBackground(Color.BLACK);
        this.controller = controller;


        backButton = new Button("<", controller);
        backButton.setSize(45, 45);
        backButton.setLocation(5, 5);
        backButton.addActionListener(backButton);
        this.add(backButton);


        Button addWorkplace = new Button("Add Workplace", controller);
        addWorkplace.setFont(new Font("Arial", Font.PLAIN, 16));
        addWorkplace.setSize(width -19, 45);
        addWorkplace.setLocation(1, 80);
        addWorkplace.setBackground(Color.BLACK);
        addWorkplace.setForeground(new Color(255, 255, 255));
        addWorkplace.setHorizontalAlignment(SwingConstants.LEFT);
        addWorkplace.addActionListener(addWorkplace);
        this.add(addWorkplace);


        Button addOvertime = new Button("Add Overtime", controller);
        addOvertime.setFont(new Font("Arial", Font.PLAIN, 16));
        addOvertime.setSize(width -19, 45);
        addOvertime.setLocation(1, 125);
        addOvertime.setBackground(Color.BLACK);
        addOvertime.setForeground(new Color(255, 255, 255));
        addOvertime.setHorizontalAlignment(SwingConstants.LEFT);
        addOvertime.addActionListener(addOvertime);
        this.add(addOvertime);


        Button overtime = new Button("Manage Overtimes", controller);
        overtime.setFont(new Font("Arial", Font.PLAIN, 16));
        overtime.setSize(width -19, 45);
        overtime.setLocation(1, 170);
        overtime.setBackground(Color.BLACK);
        overtime.setForeground(new Color(255, 255, 255));
        overtime.setHorizontalAlignment(SwingConstants.LEFT);
        overtime.addActionListener(overtime);
        this.add(overtime);


        Button about = new Button("About", controller);
        about.setFont(new Font("Arial", Font.PLAIN, 16));
        about.setSize(width -19, 45);
        about.setLocation(1, 215);
        about.setBackground(Color.BLACK);
        about.setForeground(new Color(255, 255, 255));
        about.setHorizontalAlignment(SwingConstants.LEFT);
        about.addActionListener(about);
        this.add(about);
    }
}
