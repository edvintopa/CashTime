package view;

import com.toedter.calendar.JDateChooser;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Calendar;

public class FilterPanel extends JPanel {
    private JComboBox workplaces;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    private JButton done;

    public FilterPanel(HistoryPanel history, Controller controller) {
        setLayout(null);
        setBackground(Color.BLACK);
        setOpaque(false);

        if (controller.getWorkplaces() == null) {
            String[] str = {"No workplace added"};
            workplaces = new JComboBox(str, controller);
        } else {
            workplaces = new JComboBox(controller.getWorkplaces(), controller);
        }
        workplaces.setName("history");
        workplaces.setSize(100, 20);
        workplaces.setLocation(10, 10);
        workplaces.addActionListener(workplaces);
        add(workplaces);

        startDateChooser = new JDateChooser();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        startDateChooser.setDate(cal.getTime());
        startDateChooser.setBounds(10, 40, 100, 20);
        startDateChooser.addPropertyChangeListener("date", e -> history.updateTable());
        add(startDateChooser);


        endDateChooser = new JDateChooser();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDateChooser.setDate(cal.getTime());
        endDateChooser.setBounds(10, 70, 100, 20);
        endDateChooser.addPropertyChangeListener("date", e -> history.updateTable());
        add(endDateChooser);

        Border emptyBorder = BorderFactory.createEmptyBorder();

        done = new JButton("Done");
        done.setBounds(10,100,100,20);
        done.setBackground(Color.BLACK);
        done.setForeground(Color.WHITE);
        done.setBorder(emptyBorder);
        done.addActionListener(l -> {
            history.closeFilterPanel();
        });
        add(done);
    }

    public JComboBox getWorkplacesComboBox() {
        return workplaces;
    }

    public void setWorkplacesComboBox(JComboBox workplaces){
        this.workplaces = workplaces;
    }

    public JDateChooser getStartDateChooser() {
        return startDateChooser;
    }

    public JDateChooser getEndDateChooser() {
        return endDateChooser;
    }

    public void setSelectedItem(String wpName) {
        workplaces.setSelectedItem(wpName);
    }
}
