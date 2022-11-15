package edu.school21.repositories;

import edu.school21.models.Product;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final DataSource dataSource;
    private final String SQL_FIND_BY_ID = "SELECT * FROM products WHERE id=?;";
    private final String SQL_SAVE = "INSERT INTO products (name, price) VALUES(?, ?);";
    private final String SQL_UPDATE = "UPDATE products SET name = ?, price = ? WHERE id = ?;";
    private final String SQL_DELETE = "DELETE FROM products WHERE id=?;";
    private final String SQL_FIND_ALL = "SELECT * FROM products;";

    public ProductsRepositoryJdbcImpl(EmbeddedDatabase ds) {
        dataSource = ds;
    }

    @Override
    public List<Product> findAll() {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            List<Product> listAll= new LinkedList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listAll.add(new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price")
                ));
            }
            return listAll;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            Product findProduct = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long productId = resultSet.getLong("id");
                String productName = resultSet.getString("name");
                Long productPrice = resultSet.getLong("price");
                findProduct = new Product(productId, productName, productPrice);
            }
            resultSet.close();
            if (findProduct != null) {
                return Optional.of(findProduct);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setLong(3, product.getId());
            statement.setString(1, product.getName());
            statement.setLong(2, product.getPrice());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Product product) {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SAVE)) {
            statement.setString(1, product.getName());
            statement.setLong(2, product.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            if (findById(id).isPresent()) {
                statement.setLong(1, id);
                statement.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
