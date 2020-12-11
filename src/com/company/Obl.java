package com.company;

public class Obl extends Place {
    //Поле, отвечающее за плотность населения
    private int density = 0;

    //Стандартный конструктор
    public Obl(String name, int size, int mark, String country, int density) {
        //Передаем параметры в конструктор родителя
        super(name, size, mark, country);
        //Заполняем поля этого класса
        this.density = density;
    }
    public Obl(int id, String name, int size, int mark, String country, int density) {
        //Передаем параметры в конструктор родителя
        super(id, name, size, mark, country);
        //Заполняем поля этого класса
        this.density = density;
    }

    @Override
    public Object[] getObject() {
        return new String[] {
                name, Integer.toString(size), Integer.toString(mark), country, Integer.toString(density)
        };
    }

    @Override
    public void updatePlace(Object[] object) {
        this.name = object[0].toString();
        this.size = Integer.parseInt(object[1].toString());
        this.mark = Integer.parseInt(object[2].toString());
        this.country = object[3].toString();
        this.density =  Integer.parseInt(object[4].toString());

        System.out.printf("Обновлено место: %s %d %d %s %d %n", name, size, mark, country, density);
    }
}
