package Adders;

import SQL.SQLDatabaseConnection;
import Tables.CarsTable;
import Validations.Validation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//https://tproger.ru/articles/java-regex-ispolzovanie-reguljarnyh-vyrazhenij-na-praktike/
//String number = number.replaceAll("^[^0-9\\+]|(?<=.)[^0-9]+", "");  String regex = "^[A-Za-z0-9+_.-]+@(.+)$";



public class AddCarMenu extends JFrame {

    JLabel label2 = new JLabel("Гос. номер:");
    JLabel label3 = new JLabel("Бренд:");
    JLabel label4 = new JLabel("Тип:");
    JLabel label5 = new JLabel("Цвет:");

    public AddCarMenu()
    {
        super("Добавление");
        setSize(500,200);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField gos_num = new JTextField(10);
        JTextField brand = new JTextField(10);
        JTextField type = new JTextField(10);
        JTextField color = new JTextField(10);

        /*
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormatter = new DateFormatter(date);
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);

        JFormattedTextField ftfDate = new JFormattedTextField(dateFormatter);
        ftfDate.setColumns(10);
        ftfDate.setValue(new Date());
         */

        JButton add = new JButton("Добавить Легковую");
        JButton add2 = new JButton("Добавить Грузовую");

        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(gos_num, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(label3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(brand, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(label5, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(color, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(add, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(add2, gbc);


        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String number = gos_num.getText();
                String _brand = brand.getText();
                String _type = type.getText();
                String _color = color.getText();
                System.out.println(_type);

                if (Validation.isGosNum(number) == true) {
                    if (Validation.isLetters(_color)==true) {
                        String query = "Insert into Car (Number, Brand, Type_P_C, Color) values ('"+number+"','"+_brand+"','P','"+_color+"')";
                        SQLDatabaseConnection.insert_row(query);
                        setVisible(false);
                        CarsTable.refresh();
                        JOptionPane.showMessageDialog(null,"Объект добавлен!","Готово!",JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Неверный цвет!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Неверный Гос. Номер!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String number = gos_num.getText();
                String _brand = brand.getText();
                String _type = type.getText();
                String _color = color.getText();
                System.out.println(_type);

                if (Validation.isGosNum(number) == true) {
                    if (Validation.isLetters(_color)) {
                        String query = "Insert into Car (Number, Brand, Type_P_C, Color) values ('"+number+"','"+_brand+"','C','"+_color+"')";
                        SQLDatabaseConnection.insert_row(query);
                        setVisible(false);
                        CarsTable.refresh();
                        JOptionPane.showMessageDialog(null,"Объект добавлен!","Готово!",JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Неверный цвет!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Неверный Гос. Номер!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    add(panel);
    setVisible(true);
    }
}



