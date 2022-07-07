package tech;

import storage.Group;
import storage.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Database {

    public static final String fileName = "database.db";

    private static volatile Database instance;
    private final Connection connection;
    private final ProductTech productTech;
    private final CategoryTech categoryTech;



    public static Database getInstance() {
        Database localInstance = instance;
        if (localInstance == null) {
            synchronized (Database.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Database();
                }
            }
        }
        return localInstance;
    }

    public static Database getInstance(final String fileName) {
        Database localInstance = instance;
        if (localInstance == null) {
            synchronized (Database.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Database(fileName);
                }
            }
        }
        return localInstance;
    }

    private Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC not found!", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        initProductGroupsTable();
        initProductsTable();
        initUserTable();

        productTech = new ProductTech(connection);
        categoryTech = new CategoryTech(connection);

    }

    private Database(final String fileName) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC not found!", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        initProductGroupsTable();
        initProductsTable();
        initUserTable();

        productTech = new ProductTech(connection);
        categoryTech = new CategoryTech(connection);

    }


    private void initProductGroupsTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS 'categories' (" +
                            "'title' VARCHAR(200) PRIMARY KEY," +
                            "'description' VARCHAR(700) NOT NULL)"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create categories table!", e);
        }
    }

    private void initProductsTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS 'products' (" +
                            "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "'title' VARCHAR(200) NOT NULL UNIQUE," +
                            "'description' VARCHAR(700) NOT NULL," +
                            "'producer' VARCHAR(200) NOT NULL," +
                            "'price' DECIMAL(10, 3) NOT NULL," +
                            "'quantity' INTEGER NOT NULL," +
                            "'category' VARCHAR(200) NOT NULL," +
                            "FOREIGN KEY(category) REFERENCES categories(title) ON UPDATE CASCADE ON DELETE CASCADE)"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create products table!", e);
        }
    }


    private void initUserTable() {
        try (final Statement statement = connection.createStatement()) {
            statement.execute("create table if not exists 'users'('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' VARCHAR(50) NOT NULL, 'password' VARCHAR(250) NOT NULL, 'role' VARCHAR(20) NOT NULL, UNIQUE (login))");
        } catch (final SQLException e) {
            throw new RuntimeException("Can't create table", e);
        }
    }


    public int insertCategory(final Group group) {
        return categoryTech.insertCategory(group);
    }

    public Group getCategory(String title) {
        return categoryTech.getCategory(title);
    }

    public List<Group> getCategoryList(final int page, final int size) {
        return categoryTech.getCategoryList(page, size);
    }

    public void updateCategory(String updateColumnName, String newValue, String searchColumnName, String searchValue) {
        categoryTech.update(updateColumnName, newValue, searchColumnName, searchValue);
    }

    public void deleteCategory(final String title) {
        categoryTech.delete(title);
    }

    public void deleteAllCategories() {
        categoryTech.deleteAll();
    }


    public int insertProduct(final Product product) {
        return productTech.addProduct(product);
    }

    public Product getProduct(int id) {
        return productTech.getProduct(id);
    }

    public Product getProduct(String title) {
        return productTech.getProduct(title);
    }

    public List<Product> getProductList(final int page, final int size, final Filter filter) {
        return productTech.getProductList(page, size, filter);
    }

    public void updateProduct(String updateColumnName, String newValue, String searchColumnName, String searchValue) {
        productTech.update(updateColumnName, newValue, searchColumnName, searchValue);
    }

    public void deleteProduct(final String title) {
        productTech.delete(title);
    }

    public void deleteAllProducts() {
        productTech.deleteAll();
    }






    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close connection!", e);
        }
    }
}
