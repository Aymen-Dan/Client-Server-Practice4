package tech;

import storage.Product;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductTech {

    private final Connection connection;

    public ProductTech(final Connection connection) {
        this.connection = connection;
    }


    public int addProduct(final Product product) {
        try (PreparedStatement insertStatement = connection.prepareStatement(
                "insert into 'products'('title', 'description', 'price', 'quantity', 'producer', 'category') " +
                        "values (?, ?, ?, ?, ?, ?)")) {

            if(product.getPrice() < 0 || product.getQuantity() < 0){
                return -1;
            }

            insertStatement.setString(1, product.getpName());
            insertStatement.setString(2, product.getDescription());

            insertStatement.setDouble(4, product.getPrice());
            insertStatement.setInt(3, product.getQuantity());
            insertStatement.setString(5, product.getDistributor());
            insertStatement.setString(6, product.getGroup());
            insertStatement.execute();

            final ResultSet result = insertStatement.getGeneratedKeys();
            return result.getInt("last_insert_rowid()");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert product!", e);
        }
    }



    public Product getProduct(int id) {
        Filter filter = new Filter();
        filter.setIds(Set.of(id));
        List<Product> list = getProductList(0, 1, filter);
        return list.isEmpty() ? null : list.get(0);
    }

    public Product getProduct(String title) {
        try (final Statement statement = connection.createStatement()) {

            final String    query     = "SELECT * FROM 'products' WHERE title = '" + title + "'";
            final ResultSet resultSet = statement.executeQuery(query);

            return resultSet.next() ?
                    new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("quantity"),
                            resultSet.getString("producer"),
                            resultSet.getString("category"))
                    : null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get product!", e);
        }
    }

    public List<Product> getProductList(final int page, final int size, final Filter filter) {
        try (final Statement statement = connection.createStatement()) {

            final String where = createWhereClause(filter);

            final String finalSqlQuery =
                    String.format("select * from 'products' %s limit %s offset %s", where, size, page * size);
            System.out.println(finalSqlQuery);
            final ResultSet resultSet = statement.executeQuery(finalSqlQuery);


            final List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("description"), resultSet.getInt("price"),
                        resultSet.getInt("quantity"), resultSet.getString("producer"), resultSet.getString("category")));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create list by criteria!", e);
        }
    }

    private static String createWhereClause(Filter filter) {
        final String query = Stream.of(like("title", filter.getQuery()), in("id", filter.getIds()),
                        range("price", filter.getFromPrice(), filter.getToPrice()),
                        range("quantity", filter.getFromQuantity(), filter.getToQuantity()))
                .filter(Objects::nonNull).collect(Collectors.joining(" AND "));

        final String where = query.isEmpty() ? "" : "where " + query;
        return where;
    }




    /**Like*/
    private static String like(final String fieldName, final String query) {
        return query != null ? fieldName + " LIKE  '%" + query + "%'" : null;
    }

    /**In*/
    private static String in(final String fieldName, final Collection<?> collection) {
        if (collection == null || collection.isEmpty()) return null;
        return fieldName + " IN (" + collection.stream().map(Object::toString).collect(Collectors.joining(", ")) + ")";
    }

    /**Range(fName,DOUBLE from,to)*/
    private static String range(final String fieldName, final Double from, final Double to) {
        if (from == null && to == null) return null;

        if (from != null && to == null) return fieldName + " > " + from;

        if (from == null && to != null) return fieldName + " < " + to;

        return fieldName + " BETWEEN " + from + " AND " + to;
    }

    /**Range(fName,INT from,to)*/
    private static String range(final String fieldName, final Integer from, final Integer to) {
        if (from == null && to == null) return null;

        if (from != null && to == null) return fieldName + " > " + from;

        if (from == null && to != null) return fieldName + " < " + to;

        return fieldName + " BETWEEN " + from + " AND " + to;
    }


    public void update(String updateColumnName, String newValue, String searchColumnName, String searchValue) {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "update 'products' set " + updateColumnName + " = '" + newValue + "' where " + searchColumnName +
                            " = '" + searchValue + "'");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update table!", e);
        }
    }


    public void delete(final String title) {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("delete from 'products' where title = '%s'", title));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete by title!", e);
        }
    }

    public void deleteAll() {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from 'products'");
            statement.executeUpdate("delete from sqlite_sequence where name='products'");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete all!", e);
        }
    }

}
