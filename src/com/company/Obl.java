package com.company;

public class Obl extends Place {
    //Поле, отвечающее за количество колес
    private int wheelsCount = 0;

    //Стандартный конструктор
    public Obl(String name, int speed, int weight, String color, int wheelsCount) {
        //Передаем параметры в конструктор родителя
        super(name, speed, weight, color);
        //Заполняем поля этого класса
        this.wheelsCount = wheelsCount;
    }

    @Override
    public Object[] getObject() {
        return new String[] {
                name, Integer.toString(size), Integer.toString(mark), country, Integer.toString(wheelsCount)
        };
    }

    @Override
    public void updatePlace(Object[] object) {
        this.name = object[0].toString();
        this.size = Integer.parseInt(object[1].toString());
        this.mark = Integer.parseInt(object[2].toString());
        this.country = object[3].toString();
        this.wheelsCount =  Integer.parseInt(object[4].toString());

        System.out.printf("Обновлено место: %s %d %d %s %d %n", name, size, mark, country, wheelsCount);
    }
}
