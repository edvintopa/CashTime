package view;

import controller.Controller;
import model.OverTime;
import model.Workplace;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OverTimePanel extends JPanel {
    private Controller controller;
    private JComboBox workplaces;
    private Button backButton;
    private DefaultListModel<OverTime> overtimeListModel;
    private JList<OverTime> overtimeList;
    private JTextArea overtimeInfoTextArea;

    public OverTimePanel(int width, int height, Controller controller) {
        super(null);
        this.controller = controller;
        setSize(width, height);

        if (controller.getWorkplaces() == null) {
            String[] str = new String[1];
            str[0] = "No workplace added";
            workplaces = new JComboBox(str, controller);
        } else {
            workplaces = new JComboBox(controller.getWorkplaces(), controller);
        }
        workplaces.setName("history");
        workplaces.setSize(150, 30);
        workplaces.setLocation(150+50+50+50+50, 5+15);
        workplaces.addActionListener(workplaces);
        this.add(workplaces);

        JLabel workplaceLabel = new JLabel("Workplace:");
        workplaceLabel.setBounds(78+50+50+50+50, 10+15, 80, 20);
        this.add(workplaceLabel);

        backButton = new Button("<", controller);
        backButton.setSize(45, 45);
        backButton.setLocation(5, 5);
        backButton.addActionListener(backButton);
        this.add(backButton);

        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(170, 130, 100, 30);
        removeButton.addActionListener(e -> {
            OverTime selectedOverTime = overtimeList.getSelectedValue();
            if (selectedOverTime != null) {
                removeSelectedOverTime(selectedOverTime);
            }
        });
        this.add(removeButton);

        overtimeListModel = new DefaultListModel<>();
        overtimeList = new JList<>(overtimeListModel);
        overtimeList.setCellRenderer(new OverTimeListCellRenderer());
        overtimeInfoTextArea = new JTextArea();


        JScrollPane overtimeListScrollPane = new JScrollPane(overtimeList);
        overtimeListScrollPane.setBounds(0,80, 150, 170);
        this.add(overtimeListScrollPane);

        JScrollPane overtimeInfoScrollPane = new JScrollPane(overtimeInfoTextArea);
        overtimeInfoScrollPane.setBounds(0, 250, width, height-250);
        this.add(overtimeInfoScrollPane);

        overtimeList.addListSelectionListener(e -> {
            OverTime selectedOverTime = overtimeList.getSelectedValue();
            if (selectedOverTime != null) {
                updateOverTimeInfo(selectedOverTime);
            }
        });

        loadOverTimes();
    }

    private void loadOverTimes() {
        if(controller.getCurrentWorkplace() != null){
            overtimeListModel.clear();
            List<OverTime> overTimes = controller.getCurrentWorkplace().getOverTimes();
            for (OverTime overtime : overTimes) {
                overtimeListModel.addElement(overtime);
            }
        }
    }

    private void updateOverTimeInfo(OverTime overTime) {
        StringBuilder info = new StringBuilder();

        if (overTime.getDate() == null) {
            info.append("OverTime Days: ");
            List<DayOfWeek> overTimeDays = overTime.getOverTimeDays();
            if (overTimeDays != null && !overTimeDays.isEmpty()) {
                for (int i = 0; i < overTimeDays.size(); i++) {
                    info.append(overTimeDays.get(i));
                    if (i < overTimeDays.size() - 1) {
                        info.append(", ");
                    }
                }
            } else {
                info.append("None");
            }
            info.append("\n");
            info.append("Start Time: ").append(overTime.getStart()).append("\n");
            info.append("End Time: ").append(overTime.getEnd()).append("\n");
            info.append("Percentage: ").append(overTime.getPercentage()).append("%");
        } else {
            info.append("Date: ").append(overTime.getDate()).append("\n");
            info.append("Start Time: ").append(overTime.getStart()).append("\n");
            info.append("End Time: ").append(overTime.getEnd()).append("\n");
            info.append("Percentage: ").append(overTime.getPercentage()).append("%");
        }

        overtimeInfoTextArea.setText(info.toString());
    }




    private class OverTimeListCellRenderer extends JLabel implements ListCellRenderer<OverTime> {
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        public OverTimeListCellRenderer() {
            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
            setFont(getFont().deriveFont(Font.PLAIN));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends OverTime> list, OverTime value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value == null) {
                setText("");
            } else {
                if (value.getDate() == null) {
                    setText("Weekly Overtime (" + value.getPercentage() + "%)");
                } else {
                    String date = value.getDate().format(DATE_FORMATTER);
                    setText(date + " (" + value.getPercentage() + "%)");
                }
            }

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }

    public void updateWorkplaces() {
        workplaces.setModel(new DefaultComboBoxModel(controller.getWorkplaces()));
    }

    public void setSelectedWorkplace(String wpName) {
        workplaces.setSelectedItem(wpName);
        updatePage();
    }

    public void updatePage(){
        overtimeList.addListSelectionListener(e -> {
            OverTime selectedOverTime = overtimeList.getSelectedValue();
            if (selectedOverTime != null) {
                updateOverTimeInfo(selectedOverTime);
            }
        });
        loadOverTimes();
    }

    private void removeSelectedOverTime(OverTime overTime) {
        Workplace currentWorkplace = controller.getCurrentWorkplace();
        if (currentWorkplace != null) {
            currentWorkplace.getOverTimes().remove(overTime);
            currentWorkplace.saveWorkplaceToFile();
            overtimeInfoTextArea.setText("");
            updatePage();
        }
    }

}
