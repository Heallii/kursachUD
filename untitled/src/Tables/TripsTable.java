package Tables;

import Adders.AddRideMenu;
import Adders.AddTripMenu;
import Objects.Ride;
import Objects.Trip;
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

public class TripsTable extends JFrame {
    public static DefaultTableModel tableModel;
    private JTable table1;

    private JTextField jtfFilter = new JTextField();

    private static String[] columnsHeader = new String[]{"id", "Начальный пункт", "Расстояние", "Конечный пункт"};

    public static void addRow(String startPoint, float distance, String endPoint) {
        int idx = SQLDatabaseConnection.max_id("idTrip", "Trip")+1;
        tableModel.addRow(new Object[]{Integer.toString(idx), startPoint, distance, endPoint});
        Trip trip = new Trip(idx, startPoint, distance, endPoint);
        SQLDatabaseConnection.tripsList.add(trip);
    }

    public static void refresh() {
        SQLDatabaseConnection.tripsList.clear();
        tableModel.setRowCount(0);
        fill();
    }

    public TripsTable(int isAdmin) {
        super("Маршруты");
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
                new AddTripMenu();
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
        SQLDatabaseConnection.tripsList.clear();
        tableModel.setRowCount(0);
        SQLDatabaseConnection.get_trip();
        if (SQLDatabaseConnection.tripsList.size() != 0) {
            for (Trip trip : SQLDatabaseConnection.tripsList) {
                tableModel.addRow(new Object[]{trip.getId(), trip.getStartPoint(), trip.getDistance(),trip.getEndPoint()});
            }
        }
    }

    private void save() {
        int idx_row = table1.getEditingRow();

        if (table1.isEditing() == false) {
            if (table1.getSelectedRow() != -1) {

                idx_row = table1.getSelectedRow();

                String t_id = table1.getValueAt(idx_row,0).toString();
                int id = Integer.parseInt(t_id);

                String startPoint = table1.getValueAt(idx_row,1).toString();
                String distance = table1.getValueAt(idx_row,2).toString();
                String endPoint = table1.getValueAt(idx_row,1).toString();

                if(Validation.isLetters(Validation.editPoint(startPoint))==true)
                {
                    if(Validation.isFloatNum(distance)==true)
                    {
                        if(Validation.isLetters(Validation.editPoint(endPoint))==true)
                        {
                            SQLDatabaseConnection.update_table("UPDATE Trip SET Starting_point = '" + Validation.editPoint(startPoint) + "', Distance = " + distance + ", Ending_point = '" + Validation.editPoint(endPoint) + "' WHERE idTrip = " + id);
                        }
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
            else {
                JOptionPane.showMessageDialog(null, "Выберите строку!", "Ошибка!", JOptionPane.WARNING_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Завершите редактирвоание!", "Ошибка!", JOptionPane.WARNING_MESSAGE);
        }
    }
}

