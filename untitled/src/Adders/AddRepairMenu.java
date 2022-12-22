package Adders;

import Tables.RepairsTable;
import Validations.Validation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//https://tproger.ru/articles/java-regex-ispolzovanie-reguljarnyh-vyrazhenij-na-praktike/
//String number = number.replaceAll("^[^0-9\\+]|(?<=.)[^0-9]+", "");  String regex = "^[A-Za-z0-9+_.-]+@(.+)$";



public class AddRepairMenu extends JFrame {

    // private Matcher m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d").matcher(строка);
    JLabel label3 = new JLabel("Время:");


    public AddRepairMenu()
    {
        super("Добавление");
        setSize(500,200);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField time = new JTextField(15);

        JButton add = new JButton("Добавить замену масла");
        JButton add1 = new JButton("Добавить ходовую");
        JButton add2 = new JButton("Добавить шиномонтаж");
        JButton add3 = new JButton("Добавить замену двигателя");

        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(time, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(add, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(add1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(add2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(add3, gbc);



        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String _time = time.getText();

                    if(Validation.isNum(_time)==true)
                    {
                        String query = "Insert into Repairs (Type_of_repairs, Duration) values ('m'," + _time + ")";
                        SQL.SQLDatabaseConnection.insert_row(query);
                        setVisible(false);
                        RepairsTable.refresh();
                        JOptionPane.showMessageDialog(null,"Объект добавлен!","Готово!",JOptionPane.INFORMATION_MESSAGE);

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Неверная длительность!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
            }
        });

        add1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String _time = time.getText();

                if(Validation.isNum(_time)==true)
                {
                    String query = "Insert into Repairs (Type_of_repairs, Duration) values ('h'," + _time + ")";
                    SQL.SQLDatabaseConnection.insert_row(query);
                    setVisible(false);
                    RepairsTable.refresh();
                    JOptionPane.showMessageDialog(null,"Объект добавлен!","Готово!",JOptionPane.INFORMATION_MESSAGE);

                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Неверная длительность!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String _time = time.getText();

                if(Validation.isNum(_time)==true)
                {
                    String query = "Insert into Repairs (Type_of_repairs, Duration) values ('s'," + _time + ")";
                    SQL.SQLDatabaseConnection.insert_row(query);
                    setVisible(false);
                    RepairsTable.refresh();
                    JOptionPane.showMessageDialog(null,"Объект добавлен!","Готово!",JOptionPane.INFORMATION_MESSAGE);

                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Неверная длительность!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String _time = time.getText();

                if(Validation.isNum(_time)==true)
                {
                    String query = "Insert into Repairs (Type_of_repairs, Duration) values ('e'," + _time + ")";
                    SQL.SQLDatabaseConnection.insert_row(query);
                    setVisible(false);
                    RepairsTable.refresh();
                    JOptionPane.showMessageDialog(null,"Объект добавлен!","Готово!",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Неверная длительность!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(panel);
        setVisible(true);
    }
}



