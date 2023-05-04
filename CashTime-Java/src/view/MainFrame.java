package view;

import controller.Controller;

import javax.swing.*;

public class MainFrame extends JFrame {
    private Controller controller;


    public MainFrame(int width, int height, Controller controller, HomePanel homePanel, HistoryPanel historyPanel){
        super("CashTime");
        this.controller = controller;
        setResizable(false);
        setSize(width,height);
        setLocationRelativeTo(null);
        add(homePanel);
        historyPanel.setVisible(false);
        add(historyPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
