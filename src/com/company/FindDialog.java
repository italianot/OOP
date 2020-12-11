package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindDialog extends JDialog {
    //Создаем toolkit для работы его методами
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();
    //Получаем размеры экрана
    private final Dimension screen = toolkit.getScreenSize();
    private JTextField textField;
    private JRadioButton[] radioButtons;

    public FindDialog() {
        initDialog();
        addComponentsToPane(this);
    }

    private void initDialog() {
        //Размеры фрейма
        int width = 300;
        int height = 330;
        //Расчитываем позицию по центру
        int posX = screen.width / 2 - width / 2;
        int posY = screen.height / 2 - height / 2;

        //Устанавливаем размеры и позиционирование
        setBounds(posX, posY, width, height);

        setTitle("Поиск места");

        getContentPane().setBackground(Color.decode("#ffffff"));

        setLayout(new GridBagLayout());
    }

    public JTextField getTextField() {
        return textField;
    }

    public JRadioButton[] getRadioButtons() {
        return radioButtons;
    }

    private void addComponentsToPane(Container pane) {
        GridBagConstraints c = new GridBagConstraints();
        ButtonGroup buttonGroup = new ButtonGroup();
        JButton button;
        JLabel label;
        JPanel panel;

        label = new JLabel("Поиск по:");
        label.setHorizontalAlignment(JLabel.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.ipady = 15;
        c.insets = new Insets(0,0,0,0);
        c.gridx = 1;
        c.gridy = 0;

        pane.add(label, c);

        String[] radioButtonNames = {"Области", "Города", "Все"};

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBackground(Color.decode("#ffffff"));

        radioButtons = new JRadioButton[radioButtonNames.length];

        for (int i = 0; i < radioButtonNames.length; i++) {
            boolean selected = i == (radioButtonNames.length - 1);
            radioButtons[i] = new JRadioButton(radioButtonNames[i], selected);
            radioButtons[i].setBackground(Color.decode("#ffffff"));
            radioButtons[i].setHorizontalAlignment(SwingConstants.CENTER);
            buttonGroup.add(radioButtons[i]);
            panel.add(radioButtons[i], c);
        }

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 40;
        c.gridx = 1;
        c.gridy = 2;

        pane.add(panel, c);

        label = new JLabel("Название:");
        label.setHorizontalAlignment(JLabel.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.ipady = 15;
        c.insets = new Insets(0,0,0,0);
        c.gridx = 1;
        c.gridy = 3;

        pane.add(label, c);

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBackground(Color.decode("#ffffff"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0,0,0,0);
        c.gridx = 1;
        c.gridy = 4;
        textField = new JTextField();
        textField.setColumns(20);

        panel.add(textField);
        pane.add(panel, c);

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBackground(Color.decode("#ffffff"));
        c.insets = new Insets(0,0,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 5;

        button = new JButton("Найти");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#7ebc59"));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        button.addActionListener(new FindVehicle());

        panel.add(button);
        pane.add(panel, c);

    }

    public void showDialog() {
        setVisible(true);
    }

    private class FindVehicle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String name = getTextField().getText();

            Place place;

            if (radioButtons[0].isSelected()) {
                place = AppGUI.findPlace(name, "OBL");
            } else if (radioButtons[1].isSelected()) {
                place = AppGUI.findPlace(name, "CITY");
            } else {
                place = AppGUI.findPlace(name);
            }

            if (place == null) {
                JOptionPane.showMessageDialog(null, "Поиск не дал результатов");
            } else {
                AddPanel.setCellPos(null, null);

                AddPanel.fillFields(place);

                CardLayout cardLayout = (CardLayout) AppGUI.getCardPane().getLayout();
                cardLayout.show(AppGUI.getCardPane(), "Add");

                dispose();
            }
        }
    }
}
