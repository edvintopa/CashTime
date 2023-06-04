package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Settings extends JPanel {
    private Controller controller;
    private Button backButton;
    private JTextArea aboutLabel;
    private Button addWorkplace;
    private Button addOvertime;
    private Button overtime;
    private Button about;
    private JButton done;


    public Settings(int width, int height, Controller controller){
        super(null);
        setSize(width, height);
        setBackground(Color.BLACK);
        this.controller = controller;

        ImageIcon icon = new ImageIcon("back.png");
        Image img = icon.getImage() ;
        Image newimg = img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
        icon = new ImageIcon( newimg );

        Border emptyBorder = BorderFactory.createEmptyBorder();

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


        addWorkplace = new Button("Add Workplace", controller);
        addWorkplace.setFont(new Font("Arial", Font.PLAIN, 16));
        addWorkplace.setSize(width -19, 45);
        addWorkplace.setLocation(1, 80);
        addWorkplace.setBackground(Color.BLACK);
        addWorkplace.setForeground(new Color(255, 255, 255));
        addWorkplace.setHorizontalAlignment(SwingConstants.LEFT);
        addWorkplace.addActionListener(addWorkplace);
        this.add(addWorkplace);


        addOvertime = new Button("Add Overtime", controller);
        addOvertime.setFont(new Font("Arial", Font.PLAIN, 16));
        addOvertime.setSize(width -19, 45);
        addOvertime.setLocation(1, 125);
        addOvertime.setBackground(Color.BLACK);
        addOvertime.setForeground(new Color(255, 255, 255));
        addOvertime.setHorizontalAlignment(SwingConstants.LEFT);
        addOvertime.addActionListener(addOvertime);
        this.add(addOvertime);


        overtime = new Button("Overtimes List", controller);
        overtime.setFont(new Font("Arial", Font.PLAIN, 16));
        overtime.setSize(width -19, 45);
        overtime.setLocation(1, 170);
        overtime.setBackground(Color.BLACK);
        overtime.setForeground(new Color(255, 255, 255));
        overtime.setHorizontalAlignment(SwingConstants.LEFT);
        overtime.addActionListener(overtime);
        this.add(overtime);

        aboutLabel = new JTextArea();
        aboutLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutLabel.setSize(width -19, 410);
        aboutLabel.setLocation(1,80);
        aboutLabel.setBackground(Color.BLACK);
        aboutLabel.setForeground(new Color(255, 255, 255));
        aboutLabel.setLineWrap(true);
        aboutLabel.setWrapStyleWord(true);
        aboutLabel.setText(getAboutText());
        aboutLabel.setEditable(false);
        aboutLabel.setVisible(false);
        add(aboutLabel);

        about = new Button("About", controller);
        about.setFont(new Font("Arial", Font.PLAIN, 16));
        about.setSize(width -19, 45);
        about.setLocation(1, 215);
        about.setBackground(Color.BLACK);
        about.setForeground(new Color(255, 255, 255));
        about.setHorizontalAlignment(SwingConstants.LEFT);
        about.addActionListener(l -> {
            showAbout();
        });
        this.add(about);

        done = new JButton();
        done.setText("Done");
        done.setFont(new Font("Arial", Font.PLAIN, 16));
        done.setSize(80, 40);
        done.setLocation((width / 2) - 50, 500);
        done.setBackground(Color.BLACK);
        done.setForeground(new Color(255, 255, 255));
        done.setHorizontalAlignment(SwingConstants.CENTER);
        done.addActionListener(l -> {
            closeAbout();
        });
        done.setVisible(false);
        add(done);

    }

    public void closeAbout() {
        addWorkplace.setVisible(true);
        addOvertime.setVisible(true);
        overtime.setVisible(true);
        about.setVisible(true);
        aboutLabel.setVisible(false);
        done.setVisible(false);
    }

    private void showAbout() {
        addWorkplace.setVisible(false);
        addOvertime.setVisible(false);
        overtime.setVisible(false);
        about.setVisible(false);
        aboutLabel.setVisible(true);
        done.setVisible(true);
    }

    private String getAboutText() {
        return "Welcome to CashTime!\n\n" +
                "This free light-weight tool is designed to help you track your working hours and manage your overtime efficiently.\n\n" +
                "Key features:\n" +
                "- Clock in and out with ease\n" +
                "- Monitor your working hours and overtime\n" +
                "- Calculate your earnings based on hourly rates\n" +
                "- Add and manage multiple workplaces\n" +
                "- Set up custom overtime rules\n\n" +
                "Whether you're a freelancer, contractor, or simply want to keep track of your working hours, " +
                "our Time Tracker application is here to simplify your time management process.\n\n" +
                "For any inquiries or support, please don't hesitate to contact us. Enjoy tracking your time!";
    }



}
