package view;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton implements ActionListener {
    private Controller controller;


    public Button(String str, Controller controller) {
        super(str);
        this.controller = controller;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();

        switch (buttonText) {
            case "Break":
                controller.breakInterval();
                break;
            case "History":
                controller.showHistoryPanel();
                break;
            case "+":
                controller.addWorkspace();
                break;
            case "<":
                controller.showMainPanel();
                break;
            default:
                if (!controller.isClockedIn()) {
                    controller.startInterval();
                } else {
                    controller.endInterval();
                }
                break;
        }

    }
}

