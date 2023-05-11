package view;

import controller.Controller;
import model.EconomyPanel;

import javax.swing.*;

public class MainFrame extends JFrame {
    private Controller controller;


    public MainFrame(int width, int height, Controller controller, MainPanel mainPanel, HistoryPanel historyPanel, EconomyPanel economyPanel){
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
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
