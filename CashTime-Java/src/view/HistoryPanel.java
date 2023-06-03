package view;
import com.toedter.calendar.JDateChooser;
import controller.Controller;
import model.Workplace;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.*;
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
    private FilterPanel filterPanel;
    private JButton showFilterButton;


    public HistoryPanel(int width, int height, Controller controller) {
        super(null);
        this.controller = controller;
        setSize(width, height);
        setBackground(Color.BLACK);

        /*
        if (controller.getWorkplaces() == null) {
            String[] str = new String[1];
            str[0] = "No workplace added";
            workplaces = new JComboBox(str, controller);
        } else {
            workplaces = new JComboBox(controller.getWorkplaces(), controller);
        }
        workplaces.setName("history");
        workplaces.setSize(100, 20);
        workplaces.setLocation(150-20, 30);
        workplaces.addActionListener(workplaces);
        this.add(workplaces);


         */

        /*
        JLabel workplaceLabel = new JLabel("Filter:");
        workplaceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        workplaceLabel.setBounds(150-20, 5, 80, 20);
        workplaceLabel.setForeground(Color.WHITE);
        this.add(workplaceLabel);
         */

        filterPanel = new FilterPanel(this, controller);
        filterPanel.setSize(120, 120);
        filterPanel.setLocation(160, 5);
        filterPanel.setVisible(false);
        this.add(filterPanel);

        ImageIcon icon = new ImageIcon("filter.png");
        Image img = icon.getImage() ;
        Image newimg = img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
        icon = new ImageIcon( newimg );

        Border emptyBorder = BorderFactory.createEmptyBorder();

        showFilterButton = new JButton();
        showFilterButton.setIcon(icon);
        showFilterButton.setFont(new Font("Arial", Font.BOLD, 14));
        showFilterButton.setBounds(240, 20, 80, 30);
        showFilterButton.setBackground(Color.BLACK);
        showFilterButton.setForeground(Color.WHITE);
        showFilterButton.setHorizontalAlignment(SwingConstants.LEFT);
        showFilterButton.setBorder(BorderFactory.createEmptyBorder());
        showFilterButton.addActionListener(e -> showFilterPanel());
        this.add(showFilterButton);


        icon = new ImageIcon("back.png");
        img = icon.getImage() ;
        newimg = img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
        icon = new ImageIcon( newimg );

        emptyBorder = BorderFactory.createEmptyBorder();

        backButton = new Button("<", controller);
        backButton.setSize(30, 30);
        backButton.setLocation(5, 20);
        backButton.setBackground(Color.BLACK);
        backButton.setIcon(icon);
        backButton.setVerticalAlignment(SwingConstants.CENTER);
        backButton.setHorizontalAlignment(SwingConstants.LEFT);
        backButton.setBorder(emptyBorder);
        backButton.addActionListener(backButton);
        this.add(backButton);

        columns = new String[]{"Date", "Start", "End", "Hours", "OB"};
        DefaultTableModel tableModel = createTableModel(controller, null,null);
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(30);
        table.getColumnModel().getColumn(3).setCellRenderer(new DurationCellRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new DurationCellRenderer());
        table.getModel().addTableModelListener(new CustomTableModelListener(controller));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(width, 320);
        scrollPane.setLocation(0, 150+100);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.BLACK);
        table.setForeground(Color.WHITE);
        table.setBorder(emptyBorder);
        add(scrollPane);
        table.setAutoCreateRowSorter(true);

        /*
        startDateChooser = new JDateChooser();
        startDateChooser.setBounds(150-20, 55, 100, 20);
        startDateChooser.addPropertyChangeListener("date", e -> updateTable());
        this.add(startDateChooser);

        JButton clearStartDateButton = new JButton("Clear");
        clearStartDateButton.setBounds(230, 55, 55, 20);
        clearStartDateButton.setBackground(Color.BLACK);
        clearStartDateButton.setForeground(Color.WHITE);
        clearStartDateButton.setBorder(emptyBorder);
        clearStartDateButton.addActionListener(e -> startDateChooser.setDate(null));
        this.add(clearStartDateButton);

        JLabel startDateLabel = new JLabel("From:");
        startDateLabel.setBounds(115-20, 55, 35, 20);
        startDateLabel.setForeground(Color.WHITE);
        this.add(startDateLabel);

        endDateChooser = new JDateChooser();
        endDateChooser.setBounds(150-20, 80, 100, 20);
        endDateChooser.addPropertyChangeListener("date", e -> updateTable());
        this.add(endDateChooser);

        JButton clearEndDateButton = new JButton("Clear");
        clearEndDateButton.setBounds(230, 80, 55, 20);
        clearEndDateButton.setBackground(Color.BLACK);
        clearEndDateButton.setForeground(Color.WHITE);
        clearEndDateButton.setBorder(emptyBorder);
        clearEndDateButton.addActionListener(e -> endDateChooser.setDate(null));
        this.add(clearEndDateButton);

        JLabel endDateLabel = new JLabel("To:");
        endDateLabel.setBounds(115-20, 80, 35, 20);
        endDateLabel.setForeground(Color.WHITE);
        this.add(endDateLabel);

         */

        Button removeInterval = new Button("Remove Interval", controller);
        removeInterval.setBounds(10, 210, 130, 30);
        removeInterval.setBackground(new Color(255, 71, 63));
        removeInterval.setForeground(Color.WHITE);
        removeInterval.setBorder(new RoundedBorder(10));
        removeInterval.addActionListener(removeInterval);
        this.add(removeInterval);
    }

    public void updateWorkplaces() {
        JComboBox wp = filterPanel.getWorkplacesComboBox();
        wp.setModel(new DefaultComboBoxModel(controller.getWorkplaces()));
        filterPanel.setWorkplacesComboBox(wp);
    }

    public void setSelectedWorkplace(String wpName) {
        filterPanel.setSelectedItem(wpName);
    }

    public void updateTable() {
        LocalDate startDate = filterPanel.getStartDateChooser().getDate() != null
                ? filterPanel.getStartDateChooser().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                : null;
        LocalDate endDate = filterPanel.getEndDateChooser().getDate() != null
                ? filterPanel.getEndDateChooser().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                : null;

        table.setModel(createTableModel(controller, startDate, endDate));
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(30);
        table.getColumnModel().getColumn(2).setPreferredWidth(30);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(4).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setCellRenderer(new DurationCellRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new DurationCellRenderer());
        table.getModel().addTableModelListener(new CustomTableModelListener(controller));
    }

    private DefaultTableModel createTableModel(Controller controller, LocalDate startDate, LocalDate endDate) {
        String[][] data;

        if (controller.getCurrentWorkplace() != null) {
            controller.updateInterval();
            data = controller.getCurrentWorkplace().getHistory();
        } else {
            String[] emptyRow = {"", "", "", "", ""};
            data = new String[][]{emptyRow};
        }

        // Filter the rows based on the selected date range
        if (startDate != null || endDate != null) {
            List<String[]> filteredData = new ArrayList<>();
            for (String[] row : data) {
                LocalDate date = LocalDate.parse(row[0]);
                if ((startDate == null || !date.isBefore(startDate)) && (endDate == null || !date.isAfter(endDate))) {
                    filteredData.add(row);
                }
            }
            data = filteredData.toArray(new String[0][0]);
        }


        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3 || columnIndex == 4) {
                    return Duration.class; // Set the column class to Duration for accurate formatting
                }
                return String.class;
            }



        };

        // Create a custom cell renderer for the duration column


        // Update the duration column values to be of type Duration
        for (int row = 0; row < data.length; row++) {
            if(data[row][3] != ""){
                Duration duration = Duration.parse(data[row][3]);
                tableModel.setValueAt(duration, row, 3);
                if(data[row][4] != ""){
                    Duration ob = Duration.parse(data[row][4]);
                    tableModel.setValueAt(ob, row, 4);
                }
            }
        }



        return tableModel;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTable getTable() {
        return table;
    }

    public void showFilterPanel() {
        filterPanel.setVisible(true);
        showFilterButton.setVisible(false);
        this.repaint();
    }

    public void closeFilterPanel() {
        filterPanel.setVisible(false);
        showFilterButton.setVisible(true);

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


                String newValue = (String) table.getValueAt(row, column);
                switch (column) {
                    case 0:
                        LocalDate newDate = LocalDate.parse(newValue);
                        controller.getCurrentWorkplace().getIntervals().get(row).setDate(newDate);
                        controller.getCurrentWorkplace().getIntervals().get(row).calculateDuration();
                        controller.getCurrentWorkplace().getIntervals().get(row).calculateInterval(controller);
                        //controller.getCurrentWorkplace().getIntervals().get(row).calculateInterval(controller, newDate);
                        break;
                    case 1:
                        LocalTime newTimeValue = LocalTime.parse(newValue, DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
                        LocalDateTime newStart = LocalDateTime.of(controller.getCurrentWorkplace().getIntervals().get(row).getDate(), newTimeValue);
                        controller.getCurrentWorkplace().getIntervals().get(row).setStart(newStart);
                        controller.getCurrentWorkplace().getIntervals().get(row).calculateDuration();
                        controller.getCurrentWorkplace().getIntervals().get(row).calculateInterval(controller);
                        controller.updateInterval();
                        break;
                    case 2:
                        newTimeValue = LocalTime.parse(newValue, DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
                        LocalDateTime newEnd = LocalDateTime.of(controller.getCurrentWorkplace().getIntervals().get(row).getDate(), newTimeValue);
                        controller.getCurrentWorkplace().getIntervals().get(row).setEnd(newEnd);
                        controller.getCurrentWorkplace().getIntervals().get(row).calculateDuration();
                        controller.getCurrentWorkplace().getIntervals().get(row).calculateInterval(controller);
                        controller.updateInterval();
                        break;
                    case 3:
                        controller.getCurrentWorkplace().getIntervals().get(row).setDuration(newValue);
                        break;
                }

                controller.getCurrentWorkplace().saveWorkplaceToFile();
                updateTable();
                

            }
        }
    }


    public void updatePage(){
        table.setModel(createTableModel(controller, null, null));
    }

}
