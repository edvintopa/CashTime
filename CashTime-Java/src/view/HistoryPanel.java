package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HistoryPanel extends JPanel {
    private Controller controller;
    private JComboBox workplaces;
    private String[] columns;
    private JTable table;


    public HistoryPanel(int width, int height, Controller controller) {
        super(null);
        this.controller = controller;
        setSize(width, height);

        if(controller.getWorkplaces()==null){
            String[] str = new String[1];
            str[0] = "No workplace added";
            workplaces = new JComboBox(str, controller);
        } else {
            workplaces = new JComboBox(controller.getWorkplaces(), controller);
        }
        workplaces.setName("history");
        workplaces.setSize(150,30);
        workplaces.setLocation(10, 100);
        workplaces.addActionListener(workplaces);
        this.add(workplaces);

        Button back = new Button("<", controller);
        back.setSize(45,45);
        back.setLocation(5,5);
        back.addActionListener(back);
        this.add(back);

        columns = new String[]{"Date", "Start", "End", "Duration"};
        if(controller.getCurrentWorkplace() != null){
            table = new JTable(controller.getCurrentWorkplace().getHistory(), columns);
        } else {
            String[] s = {"", "", "", ""};
            String[][] str = {s};
            table = new JTable(str, columns);
        }
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(400,400);
        scrollPane.setLocation(0,150);
        table.setFillsViewportHeight(true);
        add(scrollPane);

    }

    public void update(){
        workplaces.setModel(new DefaultComboBoxModel(controller.getWorkplaces()));
    }



    public void setSelectedWorkplace(String wpName) {
        workplaces.setSelectedItem(wpName);
    }

    public void updateTable(){
        table.setModel(new DefaultTableModel(controller.getCurrentWorkplace().getHistory(), columns));
    }

}
