package hu.garaba.webshop.service;

import hu.garaba.webshop.entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {
//    private final DataSource dataSource;

    private final EntityManager em;

    @Autowired
    public DatabaseService(EntityManager em) {
        this.em = em;
    }

    /*
    JDBC implementation
    public List<Item> fetchItems() {
        // (JPA)
        // JDBC
        // Database driver
        // MySQL, PostgreSQL,

        List<Item> result = new ArrayList<>();

        *//* We use a try-with-resources block which closes the connection object at the end of the block, so that we are
        not running out of connections. This is an "expensive" resource we shouldn't just blindly create.
         *//*
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, price FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                BigDecimal price = resultSet.getBigDecimal(3);

                result.add(new Item(id, name, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }*/

    /*
    JPA implementation
     */
    public List<Item> fetchItems() {
        Query query = em.createQuery("SELECT i FROM Item i");
        List<Item> resultList = query.getResultList();

        return resultList;
    }

    /*
    JDBC implementation
    public void insertItem(Item item) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item (name, price) VALUES (?, ?)"); // Brace yourself for SQL INJECTIONS!
//            preparedStatement.setString(1, item.name());
//            preparedStatement.setBigDecimal(2, item.price());
            int i = preparedStatement.executeUpdate();

            if (i != 1) {
                throw new RuntimeException("Insertion failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*
    JPA implementation
     */
    @Transactional
    public void insertItem(Item item) {
        em.persist(item);
    }
}
