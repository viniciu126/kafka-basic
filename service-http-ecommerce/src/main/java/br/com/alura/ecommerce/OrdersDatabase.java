package br.com.alura.ecommerce;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

public class OrdersDatabase implements Closeable {
    private final LocalDatabase database;

    public OrdersDatabase() throws SQLException {
        this.database = new LocalDatabase("orders_database");
        this.database.createIfNotExists("create table Orders(" +
                "uuid varchar(200) primary key,");
    }

    public boolean wasProcessed(Order order) throws SQLException {
        var results = database.query("select uuid from Order where uuid = ? limit 1", order.getId());

        return results.next();
    }

    @Override
    public void close() throws IOException {
        try {
            database.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
