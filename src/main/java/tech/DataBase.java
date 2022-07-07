package tech;

import storage.Group;
import storage.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataBase {

    public static final String fileName = "database.db";
    private static volatile DataBase instance;
    private final Connection connection;
    private final ProductTech productTech;
    private final GroupTech groupTech;


   /**Basic database constructor*/
    private DataBase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC not found!", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        createProductGroupsTable();
        createProductsTable();

        productTech = new ProductTech(connection);
        groupTech = new GroupTech(connection);

    }

    /**Database by name constructor*/
    private DataBase(final String fileName) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC not found!", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        createProductGroupsTable();
        createProductsTable();

        productTech = new ProductTech(connection);
        groupTech = new GroupTech(connection);

    }


   /**Database getter*/
    public static DataBase getDB() {
        DataBase localInstance = instance;
        if (localInstance == null) {
            synchronized (DataBase.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DataBase();
                }
            }
        }
        return localInstance;
    }


    /**Database getter by name*/
    public static DataBase getDB(final String fileName) {
        DataBase localInstance = instance;
        if (localInstance == null) {
            synchronized (DataBase.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DataBase(fileName);
                }
            }
        }
        return localInstance;
    }


    /**Create Products Groups Table*/
    private void createProductGroupsTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS 'categories' (" +
                            "'title' VARCHAR(200) PRIMARY KEY," +
                            "'description' VARCHAR(700) NOT NULL)"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create categories table!", e);
        }
    }

    /**Create Products Table*/
    private void createProductsTable() {
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
            throw new RuntimeException("Unable to create products table!", e);
        }
    }



    public int addGroup(final Group group) {
        return groupTech.addToGroup(group);
    }

    public Group getGroup(String title) {
        return groupTech.getProductGroup(title);
    }

    public List<Group> getGroupList(final int page, final int size) {
        return groupTech.getGroupList(page, size);
    }

    public void updateGroup(String updateColumnName, String newValue, String searchColumnName, String searchValue) {
        groupTech.Update(updateColumnName, newValue, searchColumnName, searchValue);
    }

    public void deleteGroup(final String title) {
        groupTech.Delete(title);
    }

    public void deleteAllGroup() {
        groupTech.deleteAll();
    }


    public int addProduct(final Product product) {
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
        productTech.Update(updateColumnName, newValue, searchColumnName, searchValue);
    }

    public void deleteProduct(final String title) {
        productTech.Delete(title);
    }

    public void deleteAllProducts() {
        productTech.deleteAll();
    }






    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to close connection!", e);
        }
    }
}
