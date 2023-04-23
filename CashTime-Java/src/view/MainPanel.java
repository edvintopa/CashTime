package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;


public class MainPanel extends JPanel {
    private Controller controller;
    private Button clockBreak;
    private JComboBox workplaces;
    private Button history;


    public MainPanel(int width, int height, Controller controller){
        super(null);
        this.controller = controller;
        setSize(width,height);

        setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("CashTime");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setSize(200, 50);
        titleLabel.setLocation((width - titleLabel.getWidth()) / 2, 85);
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
        workplaces.setLocation((width - workplaces.getWidth()) / 2, 160);
        workplaces.addActionListener(workplaces);
        this.add(workplaces);

        Button clockInOut = new Button("IN/Out", controller);
        clockInOut.setFont(new Font("Arial", Font.PLAIN, 16));
        clockInOut.setSize(100, 100);
        clockInOut.setLocation((width - clockInOut.getWidth()) / 2, 220);
        clockInOut.setBackground(Color.BLACK);
        clockInOut.setForeground(new Color(255, 215, 0));
        clockInOut.addActionListener(clockInOut);
        this.add(clockInOut);

        clockBreak = new Button("Break", controller);
        clockBreak.setFont(new Font("Arial", Font.PLAIN, 16));
        clockBreak.setSize(100, 100);
        clockBreak.setLocation((width - clockBreak.getWidth()) / 2, 350);
        clockBreak.setBackground(Color.BLACK);
        clockBreak.setForeground(new Color(255, 215, 0));
        clockBreak.addActionListener(clockBreak);
        this.add(clockBreak);
        clockBreak.setVisible(false);

        Button wpAdd = new Button("+", controller);
        wpAdd.setFont(new Font("Arial", Font.PLAIN, 20));
        wpAdd.setSize(50, 50);
        wpAdd.setLocation(width - wpAdd.getWidth() - 20, 20);
        wpAdd.setBackground(Color.BLACK);
        wpAdd.setForeground(new Color(255, 215, 0));
        wpAdd.addActionListener(wpAdd);
        this.add(wpAdd);

        history = new Button("History", controller);
        history.setFont(new Font("Arial", Font.PLAIN, 16));
        history.setSize(100, 45);
        history.setLocation(10, 20);
        history.setBackground(Color.BLACK);
        history.setForeground(new Color(255, 215, 0));
        history.addActionListener(history);
        this.add(history);
    }


    public void setClockBreak(boolean b) {
        this.clockBreak.setVisible(b);
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
}
