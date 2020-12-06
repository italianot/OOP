package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AppGUI extends JFrame {

    //Объект класса-группы
    private static Company com;
    //Создаем экземпляр панелей
    private static final ListPanel listPanel = new ListPanel();
    private static final MainPanel mainPanel = new MainPanel();
    private static final JPanel cardPane = new JPanel();
    private static final AddPanel addPanel = new AddPanel();

    //Создаем toolkit для работы его методами
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    //Получаем размеры экрана
    Dimension screen = toolkit.getScreenSize();

    //Размеры фрейма
    private final int width = 1000;
    private final int height = 600;

    public AppGUI () {
        //Устанавливает заголовок окна
        super("Места в которых вы когда либо бывали");

        //Расчитываем позицию по центру
        int posX = screen.width / 2 - width / 2;
        int posY = screen.height / 2 - height / 2;

        //Устанавливаем размеры и позиционирование
        setBounds(posX, posY, width, height);
        //Установим цвет фона
        getCardPane().setBackground(Color.decode("#ffffff"));
        //Устанавливает обработчик для закрытия приложения
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Сделаем окно видимым
        setVisible(true);

        cardPane.setLayout(new CardLayout(0, 0));

        cardPane.add(mainPanel, "Menu");
        cardPane.add(listPanel, "List");
        cardPane.add(addPanel, "Add");

        cardPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        add(cardPane, BorderLayout.CENTER);

    }

    public static void initCom() {
        com = new Company();
    }

    public static Company getCom() {
        return com;
    }

    public static ArrayList<Place> getOblList() {
        return com.getOblList();
    }

    public static ArrayList<Place> getCityList() {
        return com.getCityList();
    }

    public static void addPlace(String name, int size, int mark, String country, int peopleCount, int noise) {
        com.addMegapolis(name, size,mark, country, peopleCount, noise);
        listPanel.addPlace(name, mark, peopleCount, noise);
    }

    public static void addPlace(String name, int size, int mark, String country) {
        com.addObl(name, size, mark, country);
        listPanel.addPlace(name, size, mark, country);
    }

    public static JPanel getCardPane() {
        return cardPane;
    }

    public static void deletePlace(String name, String type) {
        com.deletePlace(name, type);
    }

    public static Place findPlace(String name, String type) {
        return com.findPlace(name, type);
    }

    public static Place findPlace(String name) {
        return com.findPlace(name);
    }

    public static void updatePlace(String oldName, String name, int size, int mark, String country, int peopleCount) {
        com.updatePlace(oldName, name, size, mark, country, peopleCount);
        listPanel.updateData(oldName, name, size, mark, country, peopleCount);
    }

    public static void updatePlace(String oldName, String name, int size, int mark, String country, int peopleCount, String noise) {
        com.updatePlace(oldName, name, mark, peopleCount, noise);
        listPanel.updateData(oldName, name, size, mark, country, peopleCount, noise);
    }
}
