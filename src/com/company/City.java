package com.company;

public abstract class City extends Place {
    //Поле, отвечающее за количество людей
    protected int peopleCount = 0;

    public City(String name, int size, int mark, String country, int peopleCount) {
        //Передаем параметры в конструктор родителя
        super(name, size, mark, country);
        //Заполняем поля этого класса
        this.peopleCount = peopleCount;
    }
}
