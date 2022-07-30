package br.com.alura.ecommerce;

import java.sql.*;
import java.util.UUID;

public class LocalDatabase {
    private final Connection connection;

    LocalDatabase(String name) throws SQLException {
        String url = "jdbc:sqlite:target/" + name + ".db";
        this.connection = DriverManager.getConnection(url);
    }

    // yes, this is way too generic (SQL Inject hahahahaha)
    public void createIfNotExists(String sql) {
        try {
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String statement, String ... params) throws SQLException {
        PreparedStatement prepareStatement = prepare(statement, params);

        prepareStatement.execute();
    }

    public ResultSet query(String query, String ... params) throws SQLException {
        PreparedStatement prepareStatement = prepare(query, params);

        return prepareStatement.executeQuery();
    }

    private PreparedStatement prepare(String query, String[] params) throws SQLException {
        var prepareStatement = connection.prepareStatement(query);

        for (int i = 0; i < params.length; i ++) {
            prepareStatement.setString(i+1, params[i]);
        }
        return prepareStatement;
    }

    public void close() throws SQLException {
        connection.close();
    }
}
