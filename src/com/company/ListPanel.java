package com.company;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class ListPanel extends JPanel {

    //Необходимые поля
    private int buttonCount = 0;
    private final JPanel tablePanel = new JPanel();
    private static final JTable oblTable = new JTable();
    private static final JTable cityTable = new JTable();
    private final JTextField oblTableFilterTextField = new JTextField();
    private final JTextField cityTableFilterTextField = new JTextField();
    private JRadioButton oblRadioButton;
    private JRadioButton cityRadioButton;
    private static int activeTable;
    private static JLabel title;

    //Констркутор
    public ListPanel() {
        initPanel();
        addElementsToPanel(this);
    }

    public static int getActiveTable() {
        return activeTable;
    }

    public static JTable getOblTable() {
        return oblTable;
    }

    public static JTable getCityTable() {
        return cityTable;
    }

    //Метод задающий стандартные настройки для панели
    private void initPanel() {
        setBackground(Color.decode("#ffffff"));
        setVisible(true);

        AppGUI.initCom();
    }
    //Метод добавляющий элементы на панель
    private void addElementsToPanel(Container pane) {
        JButton button;
        JPanel panel;
        JLabel label;
        Font font;
        ButtonGroup buttonGroup;

        //Установим метод компановки GridBagLayout
        pane.setLayout(new GridBagLayout());

        //Переменная, позволяющая манипулировать настройками layout
        GridBagConstraints c = new GridBagConstraints();

        title = new JLabel("Список мест:");

        font = new Font(null, Font.BOLD, 14);

        title.setFont(font);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20,20,20,0);
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.05;

        pane.add(title, c);

        tablePanel.setLayout(new CardLayout(0, 0));
        tablePanel.add(addTableToPanel("OBL", oblTable), "OblTable");
        tablePanel.add(addTableToPanel("CITY", cityTable), "CityTable");
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0,20,20,20);
        c.weightx = 0.8;
        c.weighty = 0.95;
        c.gridx = 0;
        c.gridy = 1;

        pane.add(tablePanel, c);

        panel = new JPanel();
        panel.setBackground(Color.decode("#ffffff"));
        panel.setLayout(new GridBagLayout());

        button = new JButton("Добавить");
        button.addActionListener(e -> {
            AddPanel.clearFields();


            if (oblRadioButton.isSelected()) {
                AddPanel.setSelected(0);
            }
            else if (cityRadioButton.isSelected()) {
                AddPanel.setSelected(1);
            }

            CardLayout cardLayout = (CardLayout) AppGUI.getCardPane().getLayout();
            cardLayout.show(AppGUI.getCardPane(), "Add");
        });
        setButtonSetting(button);
        setLayoutSetting(c);

        c.gridy = 5;

        panel.add(button, c);

        button = new JButton("Меню");
        setButtonSetting(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) AppGUI.getCardPane().getLayout();
                cardLayout.show(AppGUI.getCardPane(), "Menu");
            }
        });
        setLayoutSetting(c);

        c.gridy = 6;

        panel.add(button, c);

        //Поиск

        label = new JLabel("Фильтр поиска:");
        c.insets = new Insets(10,20,0,20);
        c.gridx = 0;
        c.gridy = 3;

        panel.add(label, c);

        c.insets = new Insets(0,20,20,20);
        c.gridx = 0;
        c.gridy = 4;
        addSortToPane();
        panel.add(oblTableFilterTextField, c);

        cityTableFilterTextField.setVisible(false);
        panel.add(cityTableFilterTextField, c);

        oblTable.addMouseListener(new PopClickListener());

        buttonGroup = new ButtonGroup();

        oblRadioButton = new JRadioButton("Области", true);
        oblRadioButton.setBackground(Color.decode("#ffffff"));
        oblRadioButton.setHorizontalAlignment(SwingConstants.LEFT);
        oblRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) tablePanel.getLayout();
                cardLayout.show(tablePanel, "OblTable");
                oblTableFilterTextField.setVisible(true);
                cityTableFilterTextField.setVisible(false);
                oblTable.addMouseListener(new PopClickListener());
                activeTable = 0;
            }
        });
        buttonGroup.add(oblRadioButton);

        label = new JLabel("Выбрать:");
        c.insets = new Insets(0,20,0,20);
        c.gridx = 0;
        c.gridy = 0;

        panel.add(label, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(0,20,0,20);
        c.gridx = 0;
        c.gridy = 1;

        panel.add(oblRadioButton, c);

        cityRadioButton = new JRadioButton("Города", false);
        cityRadioButton.setBackground(Color.decode("#ffffff"));
        cityRadioButton.setHorizontalAlignment(SwingConstants.LEFT);
        cityRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) tablePanel.getLayout();
                cardLayout.show(tablePanel, "CityTable");
                oblTableFilterTextField.setVisible(false);
                cityTableFilterTextField.setVisible(true);
                cityTable.addMouseListener(new PopClickListener());
                activeTable = 1;
            }
        });
        buttonGroup.add(cityRadioButton);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(0,20,0,20);
        c.gridx = 0;
        c.gridy = 2;

        panel.add(cityRadioButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0,0,0,0);
        c.weightx = 0.2;
        c.weighty = 0.95;
        c.gridx = 1;
        c.gridy = 1;

        pane.add(panel, c);
    }
    //Метод, задающий базовые параметры для кнопки
    private void setButtonSetting(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#858585"));
    }
    //Метод, задающий базовые параметры для кнопки
    private void setLayoutSetting(GridBagConstraints constraints) {
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.ipady = 15;
        constraints.insets = new Insets(5,20,5,20);
        constraints.gridx = 0;
        constraints.gridy = buttonCount;

        buttonCount++;
    }
    //Метод, добавляющий таблицу на панель
    private JPanel addTableToPanel(String typeName, JTable table) {

        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };

        int type = 0;

        if (typeName.equals("OBL")) type = 1; else if(typeName.equals("CITY")) type = 2;

        JPanel pane = new JPanel();

        pane.setLayout(new GridBagLayout());
        pane.setBackground(Color.decode("#858585"));

        GridBagConstraints c = new GridBagConstraints();

        Vector<Vector<String>> data = new Vector<Vector<String>>() ;
        // Вектор с заголовками столбцов
        Vector<String> header = new Vector<String>();

        ArrayList<Place> placesList = type == 1 ? AppGUI.getOblList() : AppGUI.getCityList();

        Object[] columnsHeader;
        // Заголовки столбцов
        if (type == 1) {
            columnsHeader = new String[] {"Название", "Размер, км^2",
                    "Оценка", "Страна", "Количество людей, тыс"};
        } else {
            columnsHeader = new String[] {"Название", "Размер, км^2",
                    "Оценка", "Страна", "Количество людей, тыс", "Шум, дБ"};
        }

        for (Object headerName: columnsHeader) {
            model.addColumn(headerName);
        }

        for (Place place : placesList) {

            Vector<String> row = new Vector<String>();

            Object[] objects = place.getObject();

            for (Object object : objects)
                row.add((String) object);

            model.addRow(row);
        }

        table.setModel(model);
        table.setRowHeight(30);
        table.setRowHeight(1, 20);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.setGridColor(Color.blue);
        table.setShowVerticalLines(false);

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;

        pane.add(new JScrollPane(table), c);

        return pane;
    }
    //Метод, добавляющий в таблицу строку
    public void addPlace(String name, int size, int mark, int country) {
        DefaultTableModel model = (DefaultTableModel) oblTable.getModel();
        model.addRow(new Object[]{name, size, mark, country});
    }
    //Метод, добавляющий в таблицу строку
    public void addPlace(String name, int size, int country, String mark) {
        DefaultTableModel model = (DefaultTableModel) cityTable.getModel();
        model.addRow(new Object[]{name, size, mark, country});
    }

    public void addSortToPane() {

        TableRowSorter<TableModel> oblRowSorter = new TableRowSorter<>(oblTable.getModel());
        TableRowSorter<TableModel> cityRowSorter = new TableRowSorter<>(cityTable.getModel());

        oblTable.setRowSorter(oblRowSorter);
        cityTable.setRowSorter(cityRowSorter);

        //noinspection DuplicatedCode
        oblTableFilterTextField.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = oblTableFilterTextField.getText();

                if (text.trim().length() == 0) {
                    oblRowSorter.setRowFilter(null);
                } else {
                    oblRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = oblTableFilterTextField.getText();

                if (text.trim().length() == 0) {
                    oblRowSorter.setRowFilter(null);
                } else {
                    oblRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        //noinspection DuplicatedCode
        cityTableFilterTextField.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = cityTableFilterTextField.getText();

                if (text.trim().length() == 0) {
                    cityRowSorter.setRowFilter(null);
                } else {
                    cityRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = cityTableFilterTextField.getText();

                if (text.trim().length() == 0) {
                    cityRowSorter.setRowFilter(null);
                } else {
                    cityRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    public void updateData(String oldName, String name, int size, int mark, String country, int peopleCount) {

        DefaultTableModel model = (DefaultTableModel) oblTable.getModel();

        Integer row = AddPanel.getRow();

        if (row == null) {
            Vector vector = model.getDataVector();

            for (int i = 0, vectorSize = vector.size(); i < vectorSize; i++) {
                Vector vectorTmp = (Vector) vector.get(i);
                String nameTmp = vectorTmp.get(0).toString();

                if (oldName.equals(nameTmp)) row = i;
            }
        }

        System.out.print(row);

        try {
            model.setValueAt(name, row, 0);
            model.setValueAt(size, row, 1);
            model.setValueAt(mark, row, 2);
            model.setValueAt(country, row, 3);
            model.setValueAt(peopleCount, row, 4);
        } catch (NullPointerException error) {
            System.out.println(error);
        }
    }

    public void updateData(String oldName, String name, int size, int mark, String country, int peopleCount, String expressType) {

        DefaultTableModel model = (DefaultTableModel) cityTable.getModel();

        Integer row = AddPanel.getRow();

        if (row == null) {
            Vector<Vector> vector = model.getDataVector();

            for (int i = 0, vectorSize = vector.size(); i < vectorSize; i++) {
                Vector vectorTmp = vector.get(i);
                String nameTmp = vectorTmp.get(0).toString();

                if (oldName.equals(nameTmp)) row = i;
            }
        }

        JOptionPane.showMessageDialog(null, "Поправь");

        try {
            model.setValueAt(name, row, 0);
            model.setValueAt(size, row, 1);
            model.setValueAt(mark, row, 2);
            model.setValueAt(country, row, 3);
            model.setValueAt(peopleCount, row, 4);
            model.setValueAt(expressType, row, 5);
        } catch (NullPointerException error) {
            System.out.println(error);
        }
    }
}
