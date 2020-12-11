package com.company;

public abstract class Place {
    protected int id;
    protected String name;
    protected int size;
    protected int mark;
    protected String country;

    //Метод возвращающий название
    public String getName() {
        return name;
    }
    //Метод возвращающий размер
    public int getSize() {
        return size;
    }
    //Метод возвращающий оценку
    public int getMark() {
        return mark;
    }
    //Метод возвращающий данные о месте в виде объекта
    public Object[] getObject() {
        return new String[] {
                name, Integer.toString(size), Integer.toString(mark), country
        };
    }

    //Стандартный конструктор
    public Place(String name, int size, int mark, String country) {
        this.name = name;
        this.size = size;
        this.mark = mark;
        this.country = country;
    }

    public Place(int id, String name, int size, int mark, String country) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.mark = mark;
        this.country = country;
    }

    public abstract void updatePlace(Object[] object);

    public int getId() {
        return id;
    }
}
