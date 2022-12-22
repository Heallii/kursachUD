package Tables;



import Adders.AddRideMenu;
import Objects.Ride;
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

public class RidesTable extends JFrame {
    public static DefaultTableModel tableModel;
    private JTable table1;

    private JTextField jtfFilter = new JTextField();

    private static String[] columnsHeader = new String[]{"id", "id маршрута", "Гос. Номер", "Дата начала", "Дата конца", "Вес груза"};

    public static void addRow(int trip_id, String gos_num, Date startDate, Date endDate, float weightCargo) {
        int idx = SQLDatabaseConnection.max_id("idRide", "Ride")+1;
        tableModel.addRow(new Object[]{Integer.toString(idx), trip_id, gos_num, startDate, endDate, weightCargo});
        Ride ride = new Ride(idx, trip_id, gos_num, startDate, endDate, weightCargo);
        SQLDatabaseConnection.ridesList.add(ride);
    }

    public static void refresh() {
        SQLDatabaseConnection.ridesList.clear();
        tableModel.setRowCount(0);
        fill();
    }

    public RidesTable(int isAdmin) {
        super("Поездки");
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
                new AddRideMenu();
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

                    SQLDatabaseConnection.drop_row(id, "Ride", "idRide");
                    tableModel.removeRow(idx);
                    SQLDatabaseConnection.remove_from_list(id, 3);
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
        SQLDatabaseConnection.ridesList.clear();
        tableModel.setRowCount(0);
        SQLDatabaseConnection.get_rides();
        if (SQLDatabaseConnection.ridesList.size() != 0) {
            for (Ride ride : SQLDatabaseConnection.ridesList) {
                tableModel.addRow(new Object[]{ride.getId(),ride.getTrip_id(),ride.getCar_number(),ride.getDateBegin(),ride.getDateEnd(),ride.getCargoWeight()});
            }
        }
    }

    private void save() {
        int idx_row = table1.getEditingRow();

        if (table1.isEditing() == false) {
            if (table1.getSelectedRow() != -1) {

                idx_row = table1.getSelectedRow();

                String trip_id = table1.getValueAt(idx_row,1).toString();
                String Car_num = table1.getValueAt(idx_row,2).toString();
                String DateStart = table1.getValueAt(idx_row,3).toString();
                String DateEnd = table1.getValueAt(idx_row,4).toString();
                String cargoWeight = table1.getValueAt(idx_row,5).toString();

                String t_id = table1.getValueAt(idx_row,0).toString();
                int id = Integer.parseInt(t_id);

                if((Validation.isNum(trip_id)==true)&&(Validation.isTrip(trip_id)==true))
                {
                    if(Validation.isGosNum(Car_num)==true)
                    {
                        if((Validation.isDate(DateStart)==true)&&(Validation.isDate(DateEnd)==true))
                        {
                            if((Validation.isFloatNum(cargoWeight)==true))
                            {
                                SQLDatabaseConnection.update_table("UPDATE Ride SET Trip_idTrip = " + trip_id + ", Car_number = '" + Car_num + "', Date_and_time_of_beginning_trip = '" + DateStart + "', Date_and_time_of_ending_trip = '" + DateEnd + "', Weight_of_cargo = " + cargoWeight + " WHERE idRide = " + id);
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
            else {
                JOptionPane.showMessageDialog(null, "Выберите строку!", "Ошибка!", JOptionPane.WARNING_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Завершите редактирвоание!", "Ошибка!", JOptionPane.WARNING_MESSAGE);
        }
    }
}

