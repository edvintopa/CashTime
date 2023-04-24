package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private Controller controller;


    public MainFrame(int width, int height, Controller controller, MainPanel mainPanel, HistoryPanel historyPanel){
        super("CashTime");
        this.controller = controller;
        setResizable(false);
        setSize(width,height);
        setLocationRelativeTo(null);
        add(mainPanel);
        historyPanel.setVisible(false);
        add(historyPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
