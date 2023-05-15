package view;

import controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JComboBox extends javax.swing.JComboBox implements ActionListener {
    private Controller controller;


    public JComboBox(String[] workplaces, Controller controller) {
        super(workplaces);
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String workName = (String) cb.getSelectedItem();
        String source = cb.getName();

        switch (source){
            case "main":
                controller.setCurrentWorkplace(workName, "main");
                break;
            default:
                controller.setCurrentWorkplace(workName, "history");
                break;
        }

    }

}
