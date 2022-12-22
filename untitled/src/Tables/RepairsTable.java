package Tables;

import Adders.AddRepairMenu;
import Objects.Repair;
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

public class RepairsTable extends JFrame {
    public static DefaultTableModel tableModel;
    private JTable table1;

    private JTextField jtfFilter = new JTextField();

    private static String[] columnsHeader = new String[]{"id", "Тип", "Время"};

    public static void addRow(String type, int time) {
        int idx = SQLDatabaseConnection.max_id("idRepairs", "Repairs") + 1;
        tableModel.addRow(new Object[]{Integer.toString(idx), type, time});
        Repair repair = new Repair(idx, type, time);
        SQLDatabaseConnection.repairsList.add(repair);
    }

    public static void refresh() {
        SQLDatabaseConnection.repairsList.clear();
        tableModel.setRowCount(0);
        fill();
    }

    public RepairsTable(int isAdmin) {
        super("Ремонты");
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
                new AddRepairMenu();
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

                    SQLDatabaseConnection.drop_row(id, "Repairs", "idRepairs");
                    tableModel.removeRow(idx);
                    SQLDatabaseConnection.remove_from_list(id, 5);
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
        SQLDatabaseConnection.repairsList.clear();
        tableModel.setRowCount(0);
        SQLDatabaseConnection.get_repair();
        if (SQLDatabaseConnection.repairsList.size() != 0) {
            for (Repair repair : SQLDatabaseConnection.repairsList) {
                tableModel.addRow(new Object[]{repair.getId(), repair.getType(), repair.getTime()});
            }
        }
    }

    private void save() {
        int idx_row = table1.getEditingRow();

        if (table1.isEditing() == false) {
            if (table1.getSelectedRow() != -1) {

                idx_row = table1.getSelectedRow();

                String type = table1.getValueAt(idx_row, 1).toString();
                char t_char = type.charAt(0);
                String time = table1.getValueAt(idx_row, 2).toString();


                String t_id = table1.getValueAt(idx_row, 0).toString();
                int id = Integer.parseInt(t_id);

                if ((t_char == 'h') || (t_char == 'm') || (t_char == 'e') || (t_char == 's')) {
                    if (Validation.isNum(time) == true) {
                        SQLDatabaseConnection.update_table("UPDATE Repairs SET Type_of_repairs = '" + type + "', Duration = " + time + " WHERE idRepairs = " + id);
                    } else {
                        JOptionPane.showMessageDialog(null, "Неверная длительность!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Неверный тип ремонта!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
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

