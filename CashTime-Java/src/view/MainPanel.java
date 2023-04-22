package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;


public class MainPanel extends JPanel {
    private Controller controller;
    private Button clockBreak;
    private JComboBox workplaces;


    public MainPanel(int width, int height, Controller controller){
        super(null);
        this.controller = controller;
        setSize(width,height);

        if(controller.getWorkplaces()==null){
            String[] str = new String[1];
            str[0] = "No workplace added";
            workplaces = new JComboBox(str, controller);
        } else {
            workplaces = new JComboBox(controller.getWorkplaces(), controller);
        }
        workplaces.setName("main");
        workplaces.setSize(150,30);
        workplaces.setLocation(120, 200);
        workplaces.addActionListener(workplaces);
        this.add(workplaces);

        Button clockInOut = new Button("IN/Out", controller);
        clockInOut.setSize(80,80);
        clockInOut.setLocation(80,350);
        clockInOut.addActionListener(clockInOut);
        this.add(clockInOut);

        clockBreak = new Button("Break", controller);
        clockBreak.setSize(80,80);
        clockBreak.setLocation(250,350);
        clockBreak.addActionListener(clockBreak);
        this.add(clockBreak);
        clockBreak.setVisible(false);

        Button wpAdd = new Button("+", controller);
        wpAdd.setSize(45,45);
        wpAdd.setLocation(335,5);
        wpAdd.addActionListener(wpAdd);
        this.add(wpAdd);

        Button history = new Button("History", controller);
        history.setSize(100,45);
        history.setLocation(5,5);
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
}
