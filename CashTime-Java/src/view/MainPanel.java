package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;


public class MainPanel extends JPanel {
    private Controller controller;
    private Button clockInOut;
    private Button clockBreak;
    private JComboBox workplaces;
    private Button history;
    private Button economy;


    public MainPanel(int width, int height, Controller controller){
        super(null);
        this.controller = controller;
        setSize(width,height);

        setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("CashTime");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setSize(200, 50);
        titleLabel.setLocation((width - titleLabel.getWidth()) / 2, 85+150);
        this.add(titleLabel);

        if(controller.getWorkplaces()==null){
            String[] str = new String[1];
            str[0] = "No workplace added";
            workplaces = new JComboBox(str, controller);
        } else {
            workplaces = new JComboBox(controller.getWorkplaces(), controller);
        }
        workplaces.setFont(new Font("Arial", Font.PLAIN, 16));
        workplaces.setName("main");
        workplaces.setSize(250, 30);
        workplaces.setLocation((width - workplaces.getWidth()) / 2, 160+150);
        workplaces.addActionListener(workplaces);
        this.add(workplaces);

        clockInOut = new Button("IN", controller);
        clockInOut.setFont(new Font("Arial", Font.PLAIN, 16));
        clockInOut.setSize(100, 100);
        clockInOut.setLocation((width - clockInOut.getWidth()) / 2, 220+150);
        clockInOut.setBackground(Color.BLACK);
        clockInOut.setForeground(new Color(255, 215, 0));
        clockInOut.addActionListener(clockInOut);
        this.add(clockInOut);

        clockBreak = new Button("Break", controller);
        clockBreak.setFont(new Font("Arial", Font.PLAIN, 16));
        clockBreak.setSize(100, 100);
        clockBreak.setLocation((width - clockBreak.getWidth()) / 2, 350+150);
        clockBreak.setBackground(Color.BLACK);
        clockBreak.setForeground(new Color(255, 215, 0));
        clockBreak.addActionListener(clockBreak);
        this.add(clockBreak);
        clockBreak.setVisible(false);

        Button workplaceAddButton = new Button("+", controller);
        workplaceAddButton.setName("addWorkplace");
        workplaceAddButton.setFont(new Font("Arial", Font.PLAIN, 20));
        workplaceAddButton.setSize(50, 50);
        workplaceAddButton.setLocation(width - workplaceAddButton.getWidth() - 20, 20);
        workplaceAddButton.setBackground(Color.BLACK);
        workplaceAddButton.setForeground(new Color(255, 215, 0));
        workplaceAddButton.addActionListener(workplaceAddButton);
        this.add(workplaceAddButton);

        history = new Button("History", controller);
        history.setFont(new Font("Arial", Font.PLAIN, 16));
        history.setSize(120, 45);
        history.setLocation(10, 75);
        history.setBackground(Color.BLACK);
        history.setForeground(new Color(255, 215, 0));
        history.addActionListener(history);
        this.add(history);

        economy = new Button("Economy", controller);
        economy.setFont(new Font("Arial", Font.PLAIN, 16));
        economy.setSize(120, 45);
        economy.setLocation(10, 20);
        economy.setBackground(Color.BLACK);
        economy.setForeground(new Color(255, 215, 0));
        economy.addActionListener(economy);
        this.add(economy);

        Button overtime = new Button("Overtimes", controller);
        overtime.setFont(new Font("Arial", Font.PLAIN, 16));
        overtime.setSize(120, 45);
        overtime.setLocation(10, 130);
        overtime.setBackground(Color.BLACK);
        overtime.setForeground(new Color(255, 215, 0));
        overtime.addActionListener(overtime);
        this.add(overtime);


        Button overTimeAddButton = new Button("+", controller);
        overTimeAddButton.setName("addOverTime");
        overTimeAddButton.setFont(new Font("Arial", Font.PLAIN, 20));
        overTimeAddButton.setSize(50, 50);
        overTimeAddButton.setLocation(width - overTimeAddButton.getWidth() - 20, 90);
        overTimeAddButton.setBackground(Color.BLACK);
        overTimeAddButton.setForeground(new Color(255, 215, 0));
        overTimeAddButton.addActionListener(overTimeAddButton);
        this.add(overTimeAddButton);

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
        clockBreak.setVisible(true);
    }

    public void endInterval(){
        clockInOut.setText("IN");
        clockBreak.setVisible(false);
    }

    public void startBreak() {
        clockInOut.setVisible(false);
        clockBreak.setText("End Break");
    }

    public void endBreak() {
        clockInOut.setVisible(true);
        clockBreak.setText("Break");
    }
}
