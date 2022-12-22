package Tables;

import Adders.AddServiceMenu;
import Objects.Service;
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
import java.util.Date;

public class ServicesTable extends JFrame {
    public static DefaultTableModel tableModel;
    private JTable table1;

    private JTextField jtfFilter = new JTextField();

    private static String[] columnsHeader = new String[]{"id", "id автомобиля", "id ремонта", "Дата"};

    public static void addRow(int car_id, int repair_id, Date date) {
        int idx = SQLDatabaseConnection.max_id("idService", "Service")+1;
        tableModel.addRow(new Object[]{Integer.toString(idx), car_id, repair_id, date});
        Service service = new Service(idx, car_id, repair_id, date);
        SQLDatabaseConnection.servicesList.add(service);
    }

    public static void refresh() {
        SQLDatabaseConnection.servicesList.clear();
        tableModel.setRowCount(0);
        fill();
    }

    public ServicesTable(int isAdmin) {
        super("Работы");
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
                new AddServiceMenu();
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

                    SQLDatabaseConnection.drop_row(id, "Service", "idService");
                    tableModel.removeRow(idx);
                    SQLDatabaseConnection.remove_from_list(id, 4);
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
        SQLDatabaseConnection.servicesList.clear();
        tableModel.setRowCount(0);
        SQLDatabaseConnection.get_services();
        if (SQLDatabaseConnection.servicesList.size() != 0) {
            for (Service service : SQLDatabaseConnection.servicesList) {
                tableModel.addRow(new Object[]{service.getId(), service.getCar_id(),service.getRepair_id(),service.getDate()});
            }
        }
    }

    private void save() {
        int idx_row = table1.getEditingRow();

        if (table1.isEditing() == false) {
            if (table1.getSelectedRow() != -1) {

                idx_row = table1.getSelectedRow();

                String _id = table1.getValueAt(idx_row,0).toString();
                int id = Integer.parseInt(_id);

                String car_id = table1.getValueAt(idx_row,1).toString();
                String repair_id = table1.getValueAt(idx_row,2).toString();
                String date = table1.getValueAt(idx_row,3).toString();

                if((Validation.isNum(car_id)==true)&&(Validation.isCar(car_id)==true))
                {
                    if((Validation.isNum(repair_id)==true)&&(Validation.isRepair(repair_id)==true))
                    {
                        if (Validation.isDate(date)==true)
                        {
                            SQLDatabaseConnection.update_table("UPDATE Service SET Car_IdCar = " + car_id + ", Repairs_IdRepairs = " + repair_id + ", Date_and_time_of_repairs = '" + date + "' WHERE idService = " + id);
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
            else {
                JOptionPane.showMessageDialog(null, "Выберите строку!", "Ошибка!", JOptionPane.WARNING_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Завершите редактирвоание!", "Ошибка!", JOptionPane.WARNING_MESSAGE);
        }
    }
}

