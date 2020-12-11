package com.company;

public class Megapolis extends City {
    //Поле, отвечающее за шум
    private int noise;

    //Стандартный конструктор
    public Megapolis(String name, int size, int mark, String country, int peopleCount, int noise) {
        //Передаем параметры в конструктор родителя
        super(name, size, mark, country, peopleCount);
        //Заполняем поля этого класса
        this.noise = noise;
    }

    public Megapolis(int id, String name, int size, int mark, String country, int peopleCount, int noise) {
        //Передаем параметры в конструктор родителя
        super(id, name, size, mark, country, peopleCount);
        //Заполняем поля этого класса
        this.noise = noise;
    }


    @Override
    public Object[] getObject() {
        return new String[] {
                name, Integer.toString(size), Integer.toString(mark), country, Integer.toString(peopleCount), Integer.toString(noise)
        };
    }

    //Метод для получения уровня шума
    public int getType() {
        return noise;
    }

    @Override
    public void updatePlace(Object[] object) {
        this.name = object[0].toString();
        this.size = Integer.parseInt(object[1].toString());
        this.mark = Integer.parseInt(object[2].toString());
        this.country = object[3].toString();
        this.peopleCount = Integer.parseInt(object[4].toString());
        this.noise = Integer.parseInt(object[5].toString());

        System.out.printf("Обновлено место: %s %d %d %s %d %s %n", name, size, mark, country, peopleCount, noise);
    }

}
