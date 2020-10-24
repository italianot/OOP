package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Company {
        //Список для хранения объектов
        protected ArrayList<Place> oblList = new ArrayList<>();
        protected ArrayList<Place> cityList = new ArrayList<>();
        //Поле для получения случайного числа
        final Random rnd = new Random();

        //Поля для объектов
        protected String name;
        protected int size;
        protected int mark;
        protected String country;
        protected int noise;
        protected int peopleCount;

        //Стандартный конструктор
        public Company() {
            //Заполним список 10 случайными объектами
            randomPlaces(5);
        }

        //Метод, добавляющий новое место в список - область
        public void addCar(String name, int speed, int mark, String country) {
            //Создаем объект класса Obl
            Obl oblTmp = new Obl(name, speed, mark, country, peopleCount);
            //Добавляем объект в список
            oblList.add(oblTmp);
            System.out.println("Место добавлено");
        }
        //Метод, добавляющий новое место в список - мегаполис
        public void addExpress(String name, int speed, int weight, String country, int peopleCount, int noise) {
            //Создаем объект класса Megapolis
            Megapolis megapolisTmp = new Megapolis(name, speed, weight, country, peopleCount, noise);
            //Добавляем объект в список
            cityList.add(megapolisTmp);
            System.out.println("Место добавлено");
        }

        public void randomPlaces(int count) {

            //Счетчики
            int oblCount = 0;
            int cityCount = 0;

            for (int i = 0; i < count; i++) {
                //С помощью случайных чисел добавим объекты
                switch(rnd.nextInt(2)) {
                    case(0):
                        oblCount++;
                        //Заполним поля
                        name = "Oblast " + oblCount;
                        size = rnd.nextInt(500) + 1200;
                        mark = rnd.nextInt(8) + 2;
                        country = rnd.nextInt(2) == 0 ? "Россия" : "США";
                        peopleCount = rnd.nextInt(1000) + 10000;

                        //Создаем объект класса obl
                        Obl oblTmp = new Obl(name, size, mark, country, peopleCount);
                        //Добавляем объект в список
                        oblList.add(oblTmp);
                        break;
                    case(1):
                        cityCount++;
                        //Заполним поля
                        name = "City " + cityCount;
                        size = rnd.nextInt(500)+ 500;
                        mark = rnd.nextInt(8) + 2;
                        country = rnd.nextInt(2) == 0 ? "Россия" : "США";
                        peopleCount = rnd.nextInt(40000) + 50000;
                        noise = rnd.nextInt(30) + 65;

                        //Создаем объект класса city
                        Megapolis megapolisTmp = new Megapolis(name, size, mark, country, peopleCount, noise);
                        //Добавляем объект в список
                        cityList.add(megapolisTmp);
                        break;
                }
            }
        }

        //Метод, возвращающий список всех мест
        public ArrayList<Place> getCarsList() {
            return oblList;
        }
        //Метод, возвращающий список всех мест
        public ArrayList<Place> getCityList() {
            return cityList;
        }
        //Метод поиска места
        public Place findPlace(String name, String type) {
            //Объект для хранения необходимого места
            Place vehicle = null;

            ArrayList<Place> list = null;

            if (type.equals("OBL")) {
                list = oblList;
            } else if (type.equals("CITY")) {
                list = cityList;
            }

            //Переберем список
            assert list != null;
            for(Place vehicleTmp : list) {
                //Получим имя, полученного объекта
                String nameTmp = vehicleTmp.getName();
                //Если имена совпали, то
                if (nameTmp.equals(name)) {
                    //Записываем полученный объект
                    vehicle = vehicleTmp;
                }
            }

            return vehicle;
        }

        public Place findPlace(String name) {
            Place vehicle = null;

            for (Place vehicleTmp : oblList) {
                //Получим имя, полученного объекта
                String nameTmp = vehicleTmp.getName();
                //Если имена совпали, то
                if (nameTmp.equals(name)) {
                    //Записываем полученный объект
                    vehicle = vehicleTmp;
                }
            }

            if (vehicle == null) {
                for (Place vehicleTmp : cityList) {
                    //Получим имя, полученного объекта
                    String nameTmp = vehicleTmp.getName();
                    //Если имена совпали, то
                    if (nameTmp.equals(name)) {
                        //Записываем полученный объект
                        vehicle = vehicleTmp;
                    }
                }
            }

            return vehicle;
        }
        //Метод удаления места
        public void deletePlace(String name, String type) {
            boolean isFind = false;
            int index = 0;

            ArrayList<Place> list = null;

            if (type.equals("OBL")) {
                list = oblList;
            } else if (type.equals("CITY")) {
                list = cityList;
            }


            //Переберем список
            assert list != null;
            for(Place vehicleTmp : list) {
                //Получим имя, полученного объекта
                String nameTmp = vehicleTmp.getName();
                //Если имена совпали, то
                if (nameTmp.equals(name)) {
                    //Получаем номер наденного объекта
                    index = list.indexOf(vehicleTmp);
                    //Ставим флаг, что найдено
                    isFind = true;
                }
            }

            if (isFind) {
                //Удалить полученный объект
                list.remove(index);
                System.out.println("Место удалено");
            } else {
                System.out.println("Место не найдено");
            }
        }

        public void updatePlace(String oldName, String name, int speed, int size, String s, int weight, String color) {
            Place vehicle = null;

            for(Place vehicleTmp : oblList) {
                //Получим имя, полученного объекта
                String nameTmp = vehicleTmp.getName();
                //Если имена совпали, то
                if (nameTmp.equals(oldName)) {
                    //Записываем полученный объект
                    vehicle = vehicleTmp;
                }
            }

            if (vehicle != null) {
                Object[] object = {name, size, weight, color, peopleCount};

                vehicle.updatePlace(object);
            }
        }

        public void updatePlace(String oldName, String name, int size, int weight, String color, int railsCount) {
            Place place = null;

            for(Place vehicleTmp : cityList) {
                //Получим имя, полученного объекта
                String nameTmp = vehicleTmp.getName();
                //Если имена совпали, то
                if (nameTmp.equals(oldName)) {
                    //Записываем полученный объект
                    place = vehicleTmp;
                }
            }

            if (place != null) {
                Object[] object = {name, size, weight, color, railsCount, noise};

                place.updatePlace(object);
            }
        }

}
