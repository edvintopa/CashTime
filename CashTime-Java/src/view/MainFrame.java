package view;

import controller.Controller;

import javax.swing.*;

public class MainFrame extends JFrame {
    private Controller controller;


    public MainFrame(int width, int height, Controller controller, MainPanel mainPanel, HistoryPanel historyPanel, EconomyPanel economyPanel, OverTimePanel overTimePanel, Settings settings){
        super("CashTime");
        this.controller = controller;
        setResizable(false);
        setSize(width,height);
        setLocationRelativeTo(null);
        add(mainPanel);
        historyPanel.setVisible(false);
        add(historyPanel);
        economyPanel.setVisible(false);
        add(economyPanel);
        overTimePanel.setVisible(false);
        add(overTimePanel);
        setVisible(true);
        settings.setVisible(false);
        add(settings);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}