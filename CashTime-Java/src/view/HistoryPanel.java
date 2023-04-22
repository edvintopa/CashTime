package view;
import controller.Controller;


import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


public class HistoryPanel extends JPanel {
    private Controller controller;
    private JComboBox workplaces;
    private String[] columns;
    private JTable table;

    public HistoryPanel(int width, int height, Controller controller) {
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
        workplaces.setLocation(10, 100);
        workplaces.addActionListener(workplaces);
        this.add(workplaces);

        Button back = new Button("<", controller);
        back.setSize(45, 45);
        back.setLocation(5, 5);
        back.addActionListener(back);
        this.add(back);

        columns = new String[]{" ", "Date", "Start", "End", "Duration"};
        DefaultTableModel tableModel = createTableModel(controller);
        table = new JTable(tableModel);
        table.getModel().addTableModelListener(new CustomTableModelListener(controller));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(400, 400);
        scrollPane.setLocation(0, 150);
        table.setFillsViewportHeight(true);
        add(scrollPane);

        table.setAutoCreateRowSorter(true);
    }

    public void update() {
        workplaces.setModel(new DefaultComboBoxModel(controller.getWorkplaces()));
    }

    public void setSelectedWorkplace(String wpName) {
        workplaces.setSelectedItem(wpName);
    }

    public void updateTable() {
        table.setModel(createTableModel(controller));
        table.getModel().addTableModelListener(new CustomTableModelListener(controller));
    }

    private DefaultTableModel createTableModel(Controller controller) {
        String[][] data;

        if (controller.getCurrentWorkplace() != null) {
            data = controller.getCurrentWorkplace().getHistory();
        } else {
            String[] emptyRow = {"","", "", "", ""};
            data = new String[][]{emptyRow};
        }
        //För att kunna ändra på table data.
        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        return tableModel;
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

                // Uppdaterar intervallet
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
