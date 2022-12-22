import SQL.SQLDatabaseConnection;
import Tables.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame
{
    JButton oneTButton = new JButton("Автомобили");
    JButton twoTButton = new JButton("Работы");
    JButton threeTButton = new JButton("Ремонты");
    JButton fourTButton = new JButton("Маршруты");
    JButton fiveTButton = new JButton("Поездки");
    public MainMenu(int isAdmin) {
        super("Главное меню");
        setSize(500,500);
        setLocation(700, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        oneTButton.setPreferredSize(new Dimension(160,120));
        twoTButton.setPreferredSize(new Dimension(160,120));
        threeTButton.setPreferredSize(new Dimension(160,120));
        fourTButton.setPreferredSize(new Dimension(160,120));
        fiveTButton.setPreferredSize(new Dimension(160,120));

        SQLDatabaseConnection.get_cars();
        SQLDatabaseConnection.get_repair();
        SQLDatabaseConnection.get_trip();

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(oneTButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        panel.add(twoTButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(threeTButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        panel.add(fourTButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(fiveTButton, gbc);


        oneTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CarsTable(isAdmin);
            }
        });

        twoTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ServicesTable(isAdmin);
            }
        });

        threeTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RepairsTable(isAdmin);
            }
        });

        fourTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TripsTable(isAdmin);
            }
        });

        fiveTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RidesTable(isAdmin);
            }
        });



        add(panel);
        setVisible(true);
    }


}
