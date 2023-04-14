package view;

import controller.Controller;

import javax.swing.*;

public class MainFrame extends JFrame {
    private Controller controller;


    public MainFrame(int width, int height, Controller controller, MainPanel mainPanel, HistoryPanel historyPanel){
        super("CashTime");
        this.controller = controller;
        setResizable(false);
        setSize(width,height);
        add(mainPanel);
        historyPanel.setVisible(false);
        add(historyPanel);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
