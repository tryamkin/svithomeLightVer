package org.example;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SQL {
    private static final String JDBC_URL = "jdbc:mysql://104.197.221.86:3306/users_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password";
    public static List <Long> chatIdlst = new ArrayList();
    public static void main(String[] args) {
        try {
            System.out.println("Connected to the database.");
            addUser("Tryam", 1326899332L);
          //  createTable();
            selectData();
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
        while (resultSet.next()) {
            Long chatID = Long.valueOf(resultSet.getString("chatId"));
            chatIdlst.add (chatID);
            System.out.println( "chatId: " + chatID);
        }
        connection.close();
        return chatIdlst;
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