package Tables;


import Adders.AddCarMenu;
import Objects.Car;
import SQL.SQLDatabaseConnection;
import Validations.Validation;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarsTable extends JFrame {
    public static DefaultTableModel tableModel;
    private JTable table1;

    private JTextField jtfFilter = new JTextField();

    private static String[] columnsHeader = new String[]{"id", "Гос. номер", "Бренд", "Тип", "Цвет"};

    public static void addRow(String number, String brand, String type, String color) {
        int idx = SQLDatabaseConnection.max_id("idCar", "Car")+1;
        tableModel.addRow(new Object[]{Integer.toString(idx), number, brand, type, color});
        Car car = new Car(idx, number, brand, type, color);
        SQLDatabaseConnection.carsList.add(car);
    }

    public static void refresh() {
        SQLDatabaseConnection.carsList.clear();
        tableModel.setRowCount(0);
        fill();
    }

    public CarsTable(int isAdmin) {
        super("Автомобили");
        //setDefaultCloseOperation(EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);

        fill();

        this.table1 = new JTable(tableModel);

        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(table1);
        contents.add(new JScrollPane(table1));
        getContentPane().add(contents, "Center");

        JButton add = new JButton("Добавить");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddCarMenu();
            }
        });

        JButton save = new JButton("Сохранить строку");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        JButton remove = new JButton("Удалить");
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (table1.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите строку!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                } else {
                    int idx = table1.getSelectedRow();
                    String t_id = table1.getValueAt(idx, 0).toString();
                    int id = Integer.parseInt(t_id);

                    SQLDatabaseConnection.drop_row(id, "Car", "idCar");
                    tableModel.removeRow(idx);
                    SQLDatabaseConnection.remove_from_list(id, 2);
                    refresh();
                }
            }
        });

        JButton refresh = new JButton("Обновить");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });

        JPanel buttons = new JPanel();
        if (isAdmin == 1) {
            buttons.add(add);
            buttons.add(remove);
            buttons.add(save);
            buttons.add(refresh);
        }
        getContentPane().add(buttons, "South");

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table1.getModel());

        table1.setRowSorter(rowSorter);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Поиск:"), BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);
        getContentPane().add(panel, "North");

        jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        });
        setSize(1000, 400);
        setVisible(true);
    }


    private static void fill() {
        String temp = "";
        SQLDatabaseConnection.carsList.clear();
        tableModel.setRowCount(0);
        SQLDatabaseConnection.get_cars();
        if (SQLDatabaseConnection.carsList.size() != 0) {
            for (Car car : SQLDatabaseConnection.carsList) {
                if (car.getType()=="P")
                    temp = "Passenger";
                else
                    temp = "Cargo";
                tableModel.addRow(new Object[]{car.getId(), car.getNumber(), car.getBrand(), temp, car.getColor()});
            }
        }
    }

    private void save() {
        int idx_row = table1.getEditingRow();

        if (table1.isEditing() == false) {
            if (table1.getSelectedRow() != -1) {

                idx_row = table1.getSelectedRow();

                String number = table1.getValueAt(idx_row, 1).toString();
                String brand = table1.getValueAt(idx_row,2).toString();
                String t_type = table1.getValueAt(idx_row, 3).toString();
                String type="";
                if(t_type=="Passenger")
                    type = "P";
                else if (t_type=="Cargp")
                    type = "C";
                String color = table1.getValueAt(idx_row, 4).toString();

                String t_id = table1.getValueAt(idx_row,0).toString();
                int id = Integer.parseInt(t_id);

                if(Validation.isGosNum(number)==true)
                {
                    if((type=="P")||(type=="C"))
                    {
                            SQLDatabaseConnection.update_table("UPDATE Car SET Number = '" + number + "', Brand = '" + brand + "', Type_P_C = '" + type + "', Color = '" + Validation.editColor(color.trim()) +"' WHERE idCar = " + id);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Неверный тип!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Неверный Гос. Номер!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }

            }
            else {
                JOptionPane.showMessageDialog(null, "Выберите строку!", "Ошибка!", JOptionPane.WARNING_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Завершите редактирвоание!", "Ошибка!", JOptionPane.WARNING_MESSAGE);
        }
    }
}

