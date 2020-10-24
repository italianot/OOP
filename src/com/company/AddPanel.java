package com.company;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddPanel extends JPanel {
    //Создаем переменные для элементов
    private static JLabel[] labelArray;
    private static JTextField[] textFields;
    private static JPanel[] labelPanels;
    private static JLabel[] supportLabels;
    private static JLabel title;
    private static JRadioButton[] radioButtons;
    private static boolean isEditing = false;
    private static Place place;
    private static Integer rowEditing = null;
    private static Integer columnEditing = null;
    private static boolean isSaved = false;

    //Конструктор
    public AddPanel() {
        initPanel();
        addElementToPanel(this);
        setValidation();
    }

    public static void setCellPos(Integer row, Integer column) {
        rowEditing = row;
        columnEditing = column;
    }

    public static Integer getRow() {
        return rowEditing;
    }

    public static int getColumn() {
        return columnEditing;
    }

    public static void clearFields() {

        isEditing = false;

        for (JTextField textField : textFields) textField.setText("");

        for (JLabel label : supportLabels) {
            label.setText("");
        }

        System.out.println("Поля очищены");
    }

    public static boolean isSaved() {
        return isSaved;
    }

    public static void toggleMode() {
        if (isEditing) {
            title.setText("Редактирование:");
        } else {
            title.setText("Добавить место:");
        }
    }

    public static void setEditMode() {
        isEditing = true;
        toggleMode();
    }

    //Метод добавляющий элементы на панель
    private void addElementToPanel(Container pane) {
        JButton button;
        JLabel label;
        JPanel panel;
        ButtonGroup buttonGroup;
        Font font;

        //Переменная, позволяющая манипулировать настройками layout
        GridBagConstraints c = new GridBagConstraints();

        title = new JLabel("Добавить место:");
        font = new Font("", Font.BOLD, 14);
        title.setFont(font);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.05;
        c.insets = new Insets(10,20,0,20);

        pane.add(title, c);

        panel = new JPanel();
        panel.setBackground(Color.decode("#ffffff"));
        panel.setLayout(new GridBagLayout());

        buttonGroup = new ButtonGroup();
        radioButtons = new JRadioButton[2];

        radioButtons[0] = new JRadioButton("Область", true);
        radioButtons[0].setBackground(Color.decode("#ffffff"));
        radioButtons[0].setHorizontalAlignment(SwingConstants.LEFT);
        radioButtons[0].addActionListener(e -> {
            setActiveFileds(0);
        });
        buttonGroup.add(radioButtons[0]);

        c.weightx = 0.25;
        c.gridx = 0;
        c.gridy = 1;

        panel.add(radioButtons[0], c);

        radioButtons[1] = new JRadioButton("Города", true);
        radioButtons[1].setBackground(Color.decode("#ffffff"));
        radioButtons[1].setHorizontalAlignment(SwingConstants.LEFT);
        radioButtons[1].addActionListener(e -> {
            setActiveFileds(1);
        });
        buttonGroup.add(radioButtons[1]);

        c.weightx = 0.25;
        c.gridx = 1;
        c.gridy = 1;

        panel.add(radioButtons[1], c);

        label = new JLabel("Выберите:");
        label.setHorizontalAlignment(JLabel.LEFT);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;

        panel.add(label, c);

        String[] labelNames = new String[] {
                "Название:", "Размер, км^2:", "Оценка:", "Страна:", "Количество людей, тыс:", "Шум, дБ:"
        };
        labelArray = new JLabel[labelNames.length];
        textFields = new JTextField[labelNames.length];
        supportLabels = new JLabel[labelNames.length];
        labelPanels = new JPanel[labelNames.length];

        for (int i = 0; i < labelNames.length; i++) {
            labelArray[i] = new JLabel(labelNames[i]);
            textFields[i] = new JTextField();
            labelPanels[i] = new JPanel();
            supportLabels[i] = new JLabel();

            if (i == 5) {
                labelArray[i].setVisible(false);
                textFields[i].setVisible(false);
            }

            labelPanels[i].setLayout(new FlowLayout(FlowLayout.LEFT));
            labelPanels[i].setBackground(Color.decode("#ffffff"));

            labelPanels[i].add(labelArray[i], c);

            supportLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);
            supportLabels[i].setForeground(Color.RED);

            c.fill = GridBagConstraints.LINE_START;
            c.weightx = 1;

            labelPanels[i].setPreferredSize(new Dimension(300,20));
            labelPanels[i].add(supportLabels[i], c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.insets = new Insets(10,20,0,20);
            c.weightx = 0.5;
            c.gridx = /*0;*/(i + 2) % 2;
            c.gridy = /*i+1;*/(i + 2) - ( (i + 2) % 2 );

            panel.add(labelPanels[i], c);

            c.gridy = (i + 2) - ( (i + 2) % 2 ) + 1;
            panel.add(textFields[i], c);
        }

        button = new JButton("Сохранить");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#858585"));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener((ActionEvent e) -> {

            String msg = "Пожалуйста, заполните все поля";

            if(isEditing) {

                try {
                    if (radioButtons[0].isSelected()) {

                        String oldName = place.getName();
                        String name = textFields[0].getText();
                        int size = Integer.parseInt(textFields[1].getText());
                        int mark = Integer.parseInt(textFields[2].getText());
                        String country = textFields[3].getText();
                        int peopleCount = Integer.parseInt(textFields[4].getText());

                        if (isEmpty(oldName) || isEmpty(name) || isEmpty(country)) {
                            JOptionPane.showMessageDialog(null, msg,"Ошибка", JOptionPane.ERROR_MESSAGE);
                        } else {
                            AppGUI.updatePlace(oldName, name, size, mark, country, peopleCount);

                            CardLayout cardLayout = (CardLayout) AppGUI.getCardPane().getLayout();
                            cardLayout.show(AppGUI.getCardPane(), "List");
                        }

                    } else if(radioButtons[1].isSelected()) {
                        String oldName = place.getName();
                        String name = textFields[0].getText();
                        int size = Integer.parseInt(textFields[1].getText());
                        int mark = Integer.parseInt(textFields[2].getText());
                        String country = textFields[3].getText();
                        int peopleCount = Integer.parseInt(textFields[4].getText());
                        String expressType = textFields[5].getText();

                        if (isEmpty(oldName) || isEmpty(name) || isEmpty(country) || isEmpty(expressType)) {
                            JOptionPane.showMessageDialog(null, msg,"Ошибка", JOptionPane.ERROR_MESSAGE);
                        } else {
                            AppGUI.updatePlace(oldName, name, size, mark, country, peopleCount, expressType);

                            CardLayout cardLayout = (CardLayout) AppGUI.getCardPane().getLayout();
                            cardLayout.show(AppGUI.getCardPane(), "List");
                        }
                    }


                } catch (NumberFormatException  error){
                    JOptionPane.showMessageDialog(null, msg,"Ошибка", JOptionPane.ERROR_MESSAGE);
                    System.out.printf("Ошибка %s%n", error);
                }

            } else {

                try {

                    if (radioButtons[0].isSelected()) {

                        String name = textFields[0].getText();
                        int speed = Integer.parseInt(textFields[1].getText());
                        int weight = Integer.parseInt(textFields[2].getText());
                        String country = textFields[3].getText();

                        if (isEmpty(name) || isEmpty(country)) {
                            JOptionPane.showMessageDialog(null, msg,"Ошибка", JOptionPane.ERROR_MESSAGE);
                        } else {
                            AppGUI.addPlace(name, speed, weight, country);

                            CardLayout cardLayout = (CardLayout) AppGUI.getCardPane().getLayout();
                            cardLayout.show(AppGUI.getCardPane(), "List");
                        }

                    } else if(radioButtons[1].isSelected()) {
                        String name = textFields[0].getText();
                        int speed = Integer.parseInt(textFields[1].getText());
                        int weight = Integer.parseInt(textFields[2].getText());
                        String color = textFields[3].getText();
                        int railsCount = Integer.parseInt(textFields[4].getText());
                        int noise = Integer.parseInt(textFields[5].getText());

                        if (isEmpty(name) || isEmpty(color) ) {
                            JOptionPane.showMessageDialog(null, msg,"Ошибка", JOptionPane.ERROR_MESSAGE);
                        } else {
                            AppGUI.addPlace(name, speed, weight, color, railsCount, noise);

                            CardLayout cardLayout = (CardLayout) AppGUI.getCardPane().getLayout();
                            cardLayout.show(AppGUI.getCardPane(), "List");
                        }
                    }

                } catch (NumberFormatException  error){
                    JOptionPane.showMessageDialog(null, msg,"Ошибка", JOptionPane.ERROR_MESSAGE);
                    System.out.printf("Ошибка %s%n", error);
                }

            }

        });
        c.ipady = 15;
        c.weightx = 0.2;
        c.gridy = 8;
        c.gridx = 0;

        panel.add(button, c);

        button = new JButton("Меню");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#858585"));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> {
            if (!isEditing) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Сохранить введенные данные?", "Переход в меню", dialogButton);

                isSaved = dialogResult == JOptionPane.YES_OPTION;
            }

            CardLayout cardLayout = (CardLayout) AppGUI.getCardPane().getLayout();
            cardLayout.show(AppGUI.getCardPane(), "Menu");
        });
        c.weightx = 0.2;
        c.gridy = 8;
        c.gridx = 1;

        panel.add(button, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.95;
        c.weightx = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0,0,0,0);

        pane.add(panel, c);
    }

    private static void setActiveFileds(int index) {

        if (index > radioButtons.length) return;
        if (index == 0) {
            labelArray[4].setText("Плотность населения, чел/км^2:");// Количество людей
            labelArray[5].setVisible(false);
            textFields[5].setVisible(false);
        } else if (index == 1) {
            labelArray[4].setText("Количество людей:");
            labelArray[5].setVisible(true);
            textFields[5].setVisible(true);
        }
    }

    //Метод задающий стандартные настройки для панели
    private void initPanel() {

        setBackground(Color.decode("#ffffff"));

        setVisible(true);

        //Установим метод компоновки GridBagLayout
        setLayout(new GridBagLayout());
    }
    //
    public static void fillFields(Place place) {

        AddPanel.place = place;

        isEditing = true;

        if(place instanceof Obl) {
            radioButtons[0].setSelected(true);

            labelArray[4].setText("Плотность населения, чел/км^2:");
            labelArray[5].setVisible(false);
            textFields[5].setVisible(false);

        } else if (place instanceof Megapolis) {
            radioButtons[1].setSelected(true);

            labelArray[4].setText("Количество людей:");
            labelArray[5].setVisible(true);
            textFields[5].setVisible(true);

        }

        Object[] data = place.getObject();

        for (int i = 0; i < data.length; i++) {
            textFields[i].setText(data[i].toString());
        }
    }

    private void setValidation() {

        PlainDocument doc;

        String type = "NUM";

        doc = (PlainDocument) textFields[1].getDocument();
        doc.setDocumentFilter(new DigitFilter(type, supportLabels[1], 3));

        doc = (PlainDocument) textFields[2].getDocument();
        doc.setDocumentFilter(new DigitFilter(type, supportLabels[2], 5));

        doc = (PlainDocument) textFields[4].getDocument();
        doc.setDocumentFilter(new DigitFilter(type, supportLabels[4], 2));

        type = "WORDS";

        doc = (PlainDocument) textFields[3].getDocument();
        doc.setDocumentFilter(new DigitFilter(type, supportLabels[3], 20));
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty() || str.trim().length() == 0;
    }

    public static void setSelected(int index) {

        if (index > radioButtons.length) return;

        radioButtons[index].setSelected(true);
        setActiveFileds(index);
    }

    class DigitFilter extends DocumentFilter {
        private String DIGITS;
        private final JLabel label;
        private String msg = "";
        private final String type;
        private int limit = 10;
        private String limitMsg;

        public DigitFilter(String type, JLabel label, int limitations) {
            this.type = type;
            this.label = label;
            this.limit = limitations;

            if(this.type.equals("NUM")) {
                DIGITS = "^$|[\\d\\s^$]+";
                msg = "вводите только цифры";
                limitMsg = "вводите слишком большое число";
            } else if (this.type.equals("WORDS")) {
                DIGITS = "^$|[a-zA-Z а-яёА-ЯЁ^$]+";
                msg = "вводите только слова";
                limitMsg = "вводите слишком длинное слово";
            }
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attrs) throws BadLocationException {
            if (string.matches(DIGITS)) {

                if (isValid(offset, limit)) {
                    super.insertString(fb, offset, string, attrs);
                    label.setText("");
                } else {
                    label.setText(limitMsg);
                }
            } else {
                label.setText(msg);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
            if (string.matches(DIGITS)) {

                if (isValid(offset, limit)) {
                    super.replace(fb, offset, length, string, attrs);
                    label.setText("");
                } else {
                    label.setText(limitMsg);
                }
            } else {
                label.setText(msg);
            }
        }

        private boolean isValid(int length, int limitations) {
            return length < limitations;
        }
    }
}
