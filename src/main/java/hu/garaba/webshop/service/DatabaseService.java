package hu.garaba.webshop.service;

import hu.garaba.webshop.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {
    private DataSource dataSource;

    @Autowired
    public DatabaseService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Item> fetchItems() {
        // (JPA)
        // JDBC
        // Database driver
        // MySQL, PostgreSQL,

        List<Item> result = new ArrayList<>();

        /* We use a try-with-resources block which closes the connection object at the end of the block, so that we are
        not running out of connections. This is an "expensive" resource we shouldn't just blindly create.
         */
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);

                result.add(new Item(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
