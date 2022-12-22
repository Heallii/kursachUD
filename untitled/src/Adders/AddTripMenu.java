package Adders;


import SQL.SQLDatabaseConnection;
import Tables.ServicesTable;
import Tables.TripsTable;
import Validations.Validation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddTripMenu extends JFrame {
    JLabel label1 = new JLabel("Начальный пункт:");
    JLabel label2 = new JLabel("Дистаниция:");
    JLabel label3 = new JLabel("Конечный пункт:");

    public AddTripMenu()
    {
        super("Добавление");
        setSize(500,200);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField startPoint = new JTextField(15);
        JTextField distance = new JTextField(15);
        JTextField endPoint = new JTextField(15);

        JButton add = new JButton("Добавить");

        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(label1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(startPoint, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(distance, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(label3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(endPoint, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(add, gbc);



        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String _startPoint = startPoint.getText();
                if(Validation.isLetters(_startPoint)==true)
                {
                    String _distance = distance.getText();

                    if(Validation.isFloatNum(_distance)==true)
                    {
                        String _endPoint = endPoint.getText();
                        if(Validation.isLetters(_endPoint)==true)
                        {
                            String query = "Insert into Trip (Starting_point, Distance, Ending_point) values ('"+_startPoint+"',"+_distance+",'"+_endPoint+"')";
                            SQLDatabaseConnection.insert_row(query);
                            setVisible(false);
                            TripsTable.refresh();
                            JOptionPane.showMessageDialog(null,"Объект добавлен!","Готово!",JOptionPane.INFORMATION_MESSAGE);                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Неверный конечный пункт!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Неверная дистанция!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Неверный начальный пункт!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        add(panel);
        setVisible(true);
    }
}



