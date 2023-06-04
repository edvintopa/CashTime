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
            case "Overtimes List":
                controller.showOverTime();
                break;
            case "Filter":
                controller.showFilter();
                break;
            case "Remove Interval":
                controller.removeInterval();
                break;
            case "Break":
                controller.startBreak();
                break;
            case "End Break":
                controller.endBreak();
                break;
            case "History":
                controller.showHistory();
                break;
            case "Economy":
                controller.showEconomy();
                break;
            case "settings":
                controller.showSettings();
                break;
            case "Add Workplace":
                controller.addWorkspace();
                break;
            case "Add Overtime":
                if(controller.getCurrentWorkplace() == null){
                    JOptionPane.showMessageDialog(null, "No Workplace chosen!");
                    return;
                }
                controller.addOverTime();
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

