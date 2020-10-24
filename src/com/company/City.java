package com.company;

public abstract class City extends Place {
    //Поле, отвечающее за количество людей
    protected int peopleCount = 0;

    public City(String name, int size, int weight, String color, int peopleCount) {
        //Передаем параметры в конструктор родителя
        super(name, size, weight, color);
        //Заполняем поля этого класса
        this.peopleCount = peopleCount;
    }
}
