package Adders;

import SQL.SQLDatabaseConnection;
import Tables.RidesTable;
import Validations.Validation;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRideMenu extends JFrame {
    JLabel label1 = new JLabel("id маршрута:");
    JLabel label2 = new JLabel("Гос. номер:");
    JLabel label3 = new JLabel("Дата начала:");
    JLabel label4 = new JLabel("Дата окончания:");
    JLabel label5 = new JLabel("Вес груза:");

    public AddRideMenu()
    {
        super("Добавление");
        setSize(500,200);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField trip_id = new JTextField(15);
        JTextField gos_num = new JTextField(15);
        JTextField cargoWeight = new JTextField(15);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormatter = new DateFormatter(date);
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);

        JFormattedTextField ftfDate = new JFormattedTextField(dateFormatter);
        ftfDate.setColumns(10);
        ftfDate.setValue(new Date());

        JFormattedTextField ftfDate2 = new JFormattedTextField(dateFormatter);
        ftfDate2.setColumns(10);
        ftfDate2.setValue(new Date());

        JButton add = new JButton("Добавить");

        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(label1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(trip_id, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(gos_num, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(label3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(ftfDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(label4, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(ftfDate2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(label5, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(cargoWeight, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(add, gbc);



        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String _trip_id = trip_id.getText();

                if((Validation.isNum(_trip_id)==true)&&(Validation.isTrip(_trip_id)==true))
                {
                    String _Car_num = gos_num.getText();
                    if(Validation.isGosNum(_Car_num)==true)
                    {
                        String _DateStart = ftfDate.getText();
                        String _DateEnd = ftfDate2.getText();
                        if((Validation.isDate(_DateStart)==true)&&(Validation.isDate(_DateEnd)==true))
                        {
                            String _cargoWeight = cargoWeight.getText();
                            if((Validation.isFloatNum(_cargoWeight)==true))
                            {
                                String query = "Insert into Ride (Trip_idTrip, Car_number, Date_and_time_of_beginning_trip, Date_and_time_of_ending_trip, Weight_of_cargo) values ("+_trip_id+",'"+_Car_num+"','"+_DateStart+"','"+_DateEnd+"',"+_cargoWeight+")";
                                SQLDatabaseConnection.insert_row(query);
                                setVisible(false);
                                RidesTable.refresh();
                                JOptionPane.showMessageDialog(null,"Объект добавлен!","Готово!",JOptionPane.INFORMATION_MESSAGE);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Неверный вес!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Проверьте дату!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Неверный гос. номер!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Неверный id маршрута!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        add(panel);
        setVisible(true);
    }
}



