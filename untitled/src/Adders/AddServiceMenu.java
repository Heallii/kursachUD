package Adders;

import SQL.SQLDatabaseConnection;
import Tables.CarsTable;
import Tables.ServicesTable;
import Validations.Validation;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//https://tproger.ru/articles/java-regex-ispolzovanie-reguljarnyh-vyrazhenij-na-praktike/
//String number = number.replaceAll("^[^0-9\\+]|(?<=.)[^0-9]+", "");  String regex = "^[A-Za-z0-9+_.-]+@(.+)$";



public class AddServiceMenu extends JFrame {

    // private Matcher m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d").matcher(строка);
    JLabel label2 = new JLabel("id машины:");
    JLabel label3 = new JLabel("id ремонта:");
    JLabel label4 = new JLabel("Дата ремонта:");
    public AddServiceMenu()
    {
        super("Добавление");
        setSize(500,200);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField car_id = new JTextField(15);
        JTextField repair_id = new JTextField(15);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormatter = new DateFormatter(date);
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);

        JFormattedTextField ftfDate = new JFormattedTextField(dateFormatter);
        ftfDate.setColumns(10);
        ftfDate.setValue(new Date());

        JButton add = new JButton("Добавить");

        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(car_id, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(label3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(repair_id, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(label4, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(ftfDate, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(add, gbc);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String _car_id = car_id.getText();

                if((Validation.isNum(_car_id)==true)&&(Validation.isCar(_car_id)==true))
                {
                    String _repair_id = repair_id.getText();
                    if((Validation.isNum(_repair_id)==true)&&(Validation.isRepair(_repair_id)==true))
                    {
                        String _date = ftfDate.getText();
                        if (Validation.isDate(_date)==true)
                        {
                            String query = "Insert into Service (Car_idCar, Repairs_idRepairs, Date_and_time_of_repairs) values ("+_car_id+","+_repair_id+",'"+_date+"')";
                            SQLDatabaseConnection.insert_row(query);
                            setVisible(false);
                            ServicesTable.refresh();
                            JOptionPane.showMessageDialog(null,"Объект добавлен!","Готово!",JOptionPane.INFORMATION_MESSAGE);

                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Неверный id ремонта!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Неверный id автомобиля!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        add(panel);
        setVisible(true);
    }
}



