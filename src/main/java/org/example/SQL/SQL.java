package org.example.SQL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.config.Resources.*;


public class SQL {
    private static final String JDBC_URL =DB_URL;
    private static final String JDBC_USER = DB_USER;
    private static final String JDBC_PASSWORD = DB_PASSWORD;
    public static List <Long> chatIdlst = new ArrayList<>();
    public static ResultSet resultSet = null;

    public SQL() throws SQLException {
    }


    public static void main(String[] args) {
        try {
            System.out.println("Connected to the database.");
            createTableStats();
            createTable();
            addStat("online");
            selectData();
            showStat();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void createTable() throws SQLException {
         Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) , " +
                "chatId BIGINT , " +
                "date DATE "+ ")";
        Statement statement = connection.createStatement();
        statement.execute(createTableSQL);
        System.out.println("Table 'users' created.");
        connection.close();
    }
    public static void createTableStats() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String createTableSQL = "CREATE TABLE IF NOT EXISTS stats (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "status VARCHAR(100) , " +
                "date DATE "+ ")";
        Statement statement = connection.createStatement();
        statement.execute(createTableSQL);
        System.out.println("Table 'stats' created.");
        connection.close();
    }

    public static String selectData() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String selectSQL = "SELECT id, name, chatId, date FROM users";
        List lst = new ArrayList();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String chatID = resultSet.getString("chatId");
            String date = resultSet.getString("date");
            lst.add ("\n"+id +" "+ name +" "+ chatID +" "+ date);
            System.out.println("ID: " + id + ", Name: " + name + ", chatId: " + chatID + " Date - " + date);
        }
        connection.close();
        return lst.toString();
    }

    public static List<Long> selectChatId() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String selectSQL = "SELECT DISTINCT chatId FROM users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);
        chatIdlst.clear();
        while (resultSet.next()) {
            Long chatID = Long.valueOf(resultSet.getString("chatId"));
            chatIdlst.add (chatID);
            System.out.println( "chatId: " + chatID);
        }
        connection.close();
        return chatIdlst;
    }

    public static void showStat () throws SQLException {
        resultSet = statement1().executeQuery("SELECT * FROM stats");
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String status = resultSet.getString("status");
            String date = resultSet.getString("date");
            System.out.println(id + " " + status + " " + date);
        }
        connect().close();

    }

    public static Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        return connection;
    }

    public static Statement statement1 () throws SQLException {
        Statement statement = connect().createStatement();
        return statement;
    }


    public static void getQuery(String str) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(str);
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String status = resultSet.getString("status");
            String date = resultSet.getString("date");
            System.out.println(id + " " + status + " " + date);
        }
        connection.close();
    }

    public static void addStat(String status) throws SQLException {
         Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String insertSQL = "INSERT INTO stats (status, date) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, status);
        preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
        int rowsAffected = preparedStatement.executeUpdate();
        connection.close();
        if (rowsAffected > 0) {
            System.out.println("Status added successfully.");
        } else {
            System.out.println("Failed to add user.");
        }
    }


    public static void addUser(String name, Long chatId) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String insertSQL = "INSERT INTO users (name, chatId, date) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setLong(2, chatId);
        preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
        int rowsAffected = preparedStatement.executeUpdate();
        connection.close();
        if (rowsAffected > 0) {
            System.out.println("User added successfully.");
        } else {
            System.out.println("Failed to add user.");
        }
    }
}