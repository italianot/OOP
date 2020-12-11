package com.company;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;

import static org.h2.message.Trace.JDBC;

public class Database {
    private static final String CON_STR = "jdbc:sqlite:company.db";
    public static Connection connection;
    public static Statement statement;
    public static ResultSet data;

    public void setConnection() throws ClassNotFoundException, SQLException {

        connection = null;
        DriverManager.registerDriver(new JDBC());
        connection = DriverManager.getConnection(CON_STR);

        System.out.println("База Подключена!");
    }

    public ArrayList<Place> read(String type) throws ClassNotFoundException, SQLException {

        ArrayList<Place> list = new ArrayList<>();

        statement = connection.createStatement();

        data = statement.executeQuery(String.format("SELECT * FROM %s", type));

        while(data.next())
        {
            int id = data.getInt("id");
            String  name = data.getString("name");
            int  size = data.getInt("size");
            int mark = data.getInt("mark");
            String country = data.getString("country");


            if (type.equals("obl")) {
                int density = data.getInt("density");

                Obl obl = new Obl(name, size, mark, country, density);
                list.add(obl);
            } else if (type.equals("city")) {
                int peopleCount = data.getInt("peopleCount");
                int noise = data.getInt("noise");

                Megapolis city = new Megapolis( name, size, mark, country, peopleCount, noise);
                list.add(city);
            }

        }

        return list;
    }

    public void close() throws ClassNotFoundException, SQLException {
        connection.close();
        statement.close();
        data.close();

        System.out.println("Соединения закрыты");
    }

    public void add(String name, int size, int mark, String country, int density) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO obl(`name`, `mark`, `size`, `country`, `density`) VALUES(?, ?, ?, ?, ?)"
        );
        statement.setObject(1, name);
        statement.setObject(2, mark);
        statement.setObject(3, size);
        statement.setObject(4, country);
        statement.setObject(5, density);
        // Выполняем запрос
        statement.execute();
    }

    public void add(String name, int size, int mark, String country, int peopleCount, int noise) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO city(`name`, `mark`, `size`, `country`, `peopleCount`, `noise`) VALUES(?, ?, ?, ?, ?, ?)"
        );
        statement.setObject(1, name);
        statement.setObject(2, mark);
        statement.setObject(3, size);
        statement.setObject(4, country);
        statement.setObject(5, peopleCount);
        statement.setObject(6, noise);
        // Выполняем запрос
        statement.execute();
    }

    public void remove(int id, String type) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                String.format("DELETE FROM %s WHERE id = ?", type)
        );
        statement.setObject(1, id);
        // Выполняем запрос
        statement.execute();
    }

    public void update(int id, String name, int size, int mark, String country, int peopleCount, int noise) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE city SET name = ?, size = ?, mark = ?, country = ?, peopleCount = ?, noise = ? WHERE id = ?"
        );
        statement.setObject(1, name);
        statement.setObject(2, size);
        statement.setObject(3, mark);
        statement.setObject(4, country);
        statement.setObject(5, peopleCount);
        statement.setObject(6, noise);
        statement.setObject(7, id);
        // Выполняем запрос
        statement.execute();
    }

    public void update(int id, String name, int size, int mark, String country, int peopleCount) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE obl SET name = ?, size = ?, mark = ?, country = ?, peopleCount = ? WHERE id = ?"
        );
        statement.setObject(1, name);
        statement.setObject(2, size);
        statement.setObject(3, mark);
        statement.setObject(4, country);
        statement.setObject(5, peopleCount);
        statement.setObject(6, id);
        // Выполняем запрос
        statement.execute();
    }
}
