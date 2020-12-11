package com.company;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Company {
        //Список для хранения объектов
        protected ArrayList<Place> oblList = new ArrayList<>();
        protected ArrayList<Place> cityList = new ArrayList<>();
        //Поле для получения случайного числа
        final Random rnd = new Random();
        private Database db;

        //Поля для объектов
        protected String name;
        protected int size;
        protected int mark;
        protected String country;
        protected int noise;
        protected int peopleCount;

        //Стандартный конструктор
        public Company() {
            db = new Database();

            try {
                db.setConnection();

                oblList = db.read("obl");

                cityList = db.read("city");

                db.close();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Подключение закрыто");
            }
        }

        //Метод, добавляющий новое место в список - область
        public void addObl(String name, int size, int mark, String country, int density) {
            //Создаем объект класса Obl
            Obl oblTmp = new Obl(name, size, mark, country, density);
            //Добавляем объект в список

            try {
                db.setConnection();

                db.add(name, size, mark, country, density);

                oblList.add(oblTmp);
                db.close();

                System.out.println("Место добавлено");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Подключение закрыто");
            }

        }
        //Метод, добавляющий новое место в список - мегаполис
        public void addMegapolis(String name, int size, int mark, String country, int peopleCount, int noise) {
            //Создаем объект класса Megapolis
            Megapolis megapolisTmp = new Megapolis(name, size, mark, country, peopleCount, noise);
            //Добавляем объект в список
            cityList.add(megapolisTmp);

            try {
                db.setConnection();

                db.add(name, size, mark, country, peopleCount, noise);

                cityList.add(megapolisTmp);
                db.close();

                System.out.println("Место добавлено");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Подключение закрыто");
            }
        }

        public void randomPlaces(int count) {
            String name;
            int size;
            int mark;
            String country;
            int peopleCount;
            int noise;
            //Счетчики
            int oblCount = 0;
            int cityCount = 0;

            for (int i = 0; i < count; i++) {
                //С помощью случайных чисел добавим объекты
                switch(rnd.nextInt(2)) {
                    case(0):
                        oblCount++;
                        //Заполним поля
                        name = "Область " + oblCount;
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
                        name = "Город " + cityCount;
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
                    default:
                        throw new IllegalStateException("Unexpected value: " + rnd.nextInt(2));

                }
            }
        }

        //Метод, возвращающий список всех мест
        public ArrayList<Place> getOblList() {
            return oblList;
        }
        //Метод, возвращающий список всех мест
        public ArrayList<Place> getCityList() {
            return cityList;
        }
        //Метод поиска места
        public Place findPlace(String name, String type) {
            //Объект для хранения необходимого места
            Place place = null;

            ArrayList<Place> list = null;

            if (type.equals("OBL")) {
                list = oblList;
            } else if (type.equals("CITY")) {
                list = cityList;
            }

            //Переберем список
            assert list != null;
            for(Place placeTmp : list) {
                //Получим имя, полученного объекта
                String nameTmp = placeTmp.getName();
                //Если имена совпали, то
                if (nameTmp.equals(name)) {
                    //Записываем полученный объект
                    place = placeTmp;
                }
            }

            return place;
        }

        public Place findPlace(String name) {
            Place place = null;

            for (Place placeTmp : oblList) {
                //Получим имя, полученного объекта
                String nameTmp = placeTmp.getName();
                //Если имена совпали, то
                if (nameTmp.equals(name)) {
                    //Записываем полученный объект
                    place = placeTmp;
                }
            }

            if (place == null) {
                for (Place placeTmp : cityList) {
                    //Получим имя, полученного объекта
                    String nameTmp = placeTmp.getName();
                    //Если имена совпали, то
                    if (nameTmp.equals(name)) {
                        //Записываем полученный объект
                        place = placeTmp;
                    }
                }
            }

            return place;
        }
        //Метод удаления места
        public void deletePlace(String name, String type) {
            boolean isFind = false;
            int index = 0;
            Place place = null;

            ArrayList<Place> list = null;

            if (type.equals("OBL")) {
                list = oblList;
            } else if (type.equals("CITY")) {
                list = cityList;
            }


            //Переберем список
            assert list != null;
            for(Place placeTmp : list) {
                //Получим имя, полученного объекта
                String nameTmp = placeTmp.getName();
                //Если имена совпали, то
                if (nameTmp.equals(name)) {
                    //Получаем номер наденного объекта
                    index = list.indexOf(placeTmp);
                    place = placeTmp;
                    //Ставим флаг, что найдено
                    isFind = true;
                }
            }

            if (isFind) {
                //Удалить полученный объект
                try {
                    db.setConnection();

                    int id = place.getId();
                    String placeType = place instanceof Obl ? "obl" : place instanceof City ? "city" : null;

                    db.remove(id, placeType);

                    list.remove(index);
                    db.close();

                    System.out.println("Место удалено");
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println("Подключение закрыто");
                }

            } else {
                System.out.println("Место не найдено");
            }
        }

        public void updatePlace(String oldName, String name, int size, int mark, String country) {
            Place place = null;

            for(Place placeTmp : oblList) {
                //Получим имя, полученного объекта
                String nameTmp = placeTmp.getName();
                //Если имена совпали, то
                if (nameTmp.equals(oldName)) {
                    //Записываем полученный объект
                    place = placeTmp;
                }
            }

            if (place != null) {
                Object[] object = {name, size, mark, country, peopleCount};

                try {
                    db.setConnection();

                    int id = place.getId();

                    db.update(id, name, size, mark, country, peopleCount);

                    place.updatePlace(object);

                    db.close();


                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println("Подключение закрыто");
                }
            }
        }

        public void updatePlace(String oldName, String name, int size, int mark, String country, int railsCount) {
            Place place = null;

            for(Place placeTmp : cityList) {
                //Получим имя, полученного объекта
                String nameTmp = placeTmp.getName();
                //Если имена совпали, то
                if (nameTmp.equals(oldName)) {
                    //Записываем полученный объект
                    place = placeTmp;
                }
            }

            if (place != null) {
                Object[] object = {name, size, mark, country, peopleCount, noise};

                try {
                    db.setConnection();

                    int id = place.getId();

                    db.update(id, name, size, mark, country, peopleCount, noise);

                    place.updatePlace(object);

                    db.close();


                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println("Подключение закрыто");
                }
            }
        }

}
