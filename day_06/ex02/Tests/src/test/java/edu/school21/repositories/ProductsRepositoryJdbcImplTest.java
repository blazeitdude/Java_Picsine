package edu.school21.repositories;

import edu.school21.models.Product;
import edu.school21.numbers.NumberWorker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ProductsRepositoryJdbcImplTest {
    EmbeddedDatabase dataSource;
    ProductsRepositoryJdbcImpl repositoryJdbc;

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(0L, "CocaCola", 75L),
            new Product(1L, "Pepsi", 100L),
            new Product(2L, "Fanta", 543L),
            new Product(3L, "Maintain Dew", 3435252L),
            new Product(4L, "Sprite", 1340L),
            new Product(5L, "Buratino", 1L),
            new Product(6L, "Kvas", 9999999L)
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(3L, "Maintain Dew", 3435252L);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(1L, "Bonaqua", 50L);
    final Product EXPECTED_SAVED_PRODUCT = new Product(7L, "Lipton", 333L);

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        repositoryJdbc = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void findAll() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, repositoryJdbc.findAll());
    }

    @Test
    void findGood() throws SQLException {
        Assertions.assertAll(String.valueOf(RuntimeException.class), () -> repositoryJdbc.findAll());
    }

    @Test
    void findById() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, repositoryJdbc.findById(3L).get());
    }

    @Test
    void update() throws SQLException {
        repositoryJdbc.update(EXPECTED_UPDATED_PRODUCT);
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, repositoryJdbc.findById(1L).get());
    }

    @Test
    void updateGood() throws SQLException {
        Assertions.assertAll(String.valueOf(RuntimeException.class), () -> repositoryJdbc.update(EXPECTED_UPDATED_PRODUCT));
    }

    @Test
    void save() throws SQLException {
        repositoryJdbc.save(EXPECTED_SAVED_PRODUCT);
        Assertions.assertEquals(EXPECTED_SAVED_PRODUCT, repositoryJdbc.findById(7L).get());
    }

    @Test
    void saveGood() throws SQLException {
        Assertions.assertAll(String.valueOf(RuntimeException.class), () -> repositoryJdbc.save(EXPECTED_SAVED_PRODUCT));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 5, 6})
    void delete(long id) throws SQLException {
        repositoryJdbc.delete(id);
        Assertions.assertFalse(repositoryJdbc.findById(id).isPresent());
    }

    @AfterEach
    void close() {
        dataSource.shutdown();
    }
}
