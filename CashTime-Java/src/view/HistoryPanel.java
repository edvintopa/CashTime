package view;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import controller.Controller;


import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;


public class HistoryPanel extends JPanel {
    private Controller controller;
    private JComboBox workplaces;
    private String[] columns;
    private JTable table;
    private Button backButton;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;

    public HistoryPanel(int width, int height, Controller controller) {
        super(null);
        this.controller = controller;
        setSize(width, height);

        //Create the workplace chooser
        if (controller.getWorkplaces() == null) {
            String[] str = new String[1];
            str[0] = "No workplace added";
            workplaces = new JComboBox(str, controller);
        } else {
            workplaces = new JComboBox(controller.getWorkplaces(), controller);
        }
        workplaces.setName("history");
        workplaces.setSize(150, 30);
        workplaces.setLocation(150, 5);
        workplaces.addActionListener(workplaces);
        this.add(workplaces);

        // Create the workplace label
        JLabel workplaceLabel = new JLabel("Workplace:");
        workplaceLabel.setBounds(78, 10, 80, 20);
        this.add(workplaceLabel);

        //Create the back button
        backButton = new Button("<", controller);
        backButton.setSize(45, 45);
        backButton.setLocation(5, 5);
        backButton.addActionListener(backButton);
        this.add(backButton);

        //Create the table data
        columns = new String[]{" ", "Date", "Start", "End", "Duration"};
        DefaultTableModel tableModel = createTableModel(controller, null,null);
        table = new JTable(tableModel);
        table.getModel().addTableModelListener(new CustomTableModelListener(controller));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(400, 400);
        scrollPane.setLocation(0, 150);
        table.setFillsViewportHeight(true);
        add(scrollPane);
        table.setAutoCreateRowSorter(true);

        // Create the start date chooser
        startDateChooser = new JDateChooser();
        startDateChooser.setBounds(150, 55, 150, 30);
        startDateChooser.addPropertyChangeListener("date", e -> updateTable());
        this.add(startDateChooser);

        // Create the "Clear" button for the start date
        JButton clearStartDateButton = new JButton("Clear");
        clearStartDateButton.setBounds(310, 55, 70, 30);
        clearStartDateButton.addActionListener(e -> startDateChooser.setDate(null));
        this.add(clearStartDateButton);

        // Create the start date label
        JLabel startDateLabel = new JLabel("From:");
        startDateLabel.setBounds(110, 60, 40, 20);
        this.add(startDateLabel);

        // Create the end date chooser
        endDateChooser = new JDateChooser();
        endDateChooser.setBounds(150, 105, 150, 30);
        endDateChooser.addPropertyChangeListener("date", e -> updateTable());
        this.add(endDateChooser);

        // Create the "Clear" button for the end date
        JButton clearEndDateButton = new JButton("Clear");
        clearEndDateButton.setBounds(310, 105, 70, 30);
        clearEndDateButton.addActionListener(e -> endDateChooser.setDate(null));
        this.add(clearEndDateButton);

        // Create the end date label
        JLabel endDateLabel = new JLabel("To:");
        endDateLabel.setBounds(124, 110, 40, 20);
        this.add(endDateLabel);
    }

    public void update() {
        workplaces.setModel(new DefaultComboBoxModel(controller.getWorkplaces()));
    }

    public void setSelectedWorkplace(String wpName) {
        workplaces.setSelectedItem(wpName);
    }

    public void updateTable() {
        LocalDate startDate = startDateChooser.getDate() != null
                ? startDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                : null;
        LocalDate endDate = endDateChooser.getDate() != null
                ? endDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                : null;

        table.setModel(createTableModel(controller, startDate, endDate));
        table.getModel().addTableModelListener(new CustomTableModelListener(controller));
    }

    private DefaultTableModel createTableModel(Controller controller, LocalDate startDate, LocalDate endDate) {
        String[][] data;

        if (controller.getCurrentWorkplace() != null) {
            data = controller.getCurrentWorkplace().getHistory();
        } else {
            String[] emptyRow = {"","", "", "", ""};
            data = new String[][]{emptyRow};
        }

        // Filter the rows based on the selected date range
        if (startDate != null || endDate != null) {
            List<String[]> filteredData = new ArrayList<>();
            for (String[] row : data) {
                LocalDate date = LocalDate.parse(row[1]);
                if ((startDate == null || !date.isBefore(startDate)) && (endDate == null || !date.isAfter(endDate))) {
                    filteredData.add(row);
                }
            }
            data = filteredData.toArray(new String[0][0]);
        }

        //Make table editable.
        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        return tableModel;
    }

    public JButton getBackButton() {
        return backButton;
    }

    class CustomTableModelListener implements TableModelListener {
        private Controller controller;

        public CustomTableModelListener(Controller controller) {
            this.controller = controller;
        }

        @Override
        public void tableChanged(TableModelEvent e) {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                // Updates interval
                String newValue = (String) table.getValueAt(row, column);
                switch (column) {
                    case 0:
                        break;
                    case 1: // Date
                        LocalDate newDate = LocalDate.parse(newValue);
                        controller.getCurrentWorkplace().getIntervals().get(row).setDate(newDate);
                        break;
                    case 2: // Start
                        LocalTime newStart = LocalTime.parse(newValue, DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
                        controller.getCurrentWorkplace().getIntervals().get(row).setStart(newStart);
                        break;
                    case 3: // End
                        LocalTime newEnd = LocalTime.parse(newValue, DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
                        controller.getCurrentWorkplace().getIntervals().get(row).setEnd(newEnd);
                        System.out.println("Changed");
                        break;
                }
                controller.getCurrentWorkplace().save();
            }
        }
    }
}
