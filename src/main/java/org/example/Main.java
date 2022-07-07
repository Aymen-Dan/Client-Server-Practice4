package org.example;
import storage.Group;
import tech.Database;
import tech.Filter;
import storage.Product;

public class Main {


    public static void main(String[] args) {


        Database db = Database.getInstance();
        db.deleteAllCategories();
        db.deleteAllProducts();

        Group group1 = new Group("1 Group Name", "Group 1 description");
        Group group2 = new Group("2 Group Name", "Group 2 description");
        Group group3 = new Group("3 Group Name", "Group 3 description");
        Group group4 = new Group("4 Group Name", "Group 4 description");
        Group group5 = new Group("5 Group Name", "Group 5 description");


        db.insertCategory(group1);
        db.insertCategory(group2);
        db.insertCategory(group3);
        db.insertCategory(group4);
        db.insertCategory(group5);


        System.out.println(db.getCategoryList(0, 10));

        System.out.println("\n<-- Changing the description of the first item -->\n");

        db.updateCategory("description", "New description", "title", group1.getgName());
        System.out.println(db.getCategoryList(0, 10));

        System.out.println("\n<-- Deleting the first group -->\n");
        db.deleteCategory(group1.getgName());
        System.out.println(db.getCategoryList(0, 10));

        System.out.println("\n<-- Deleting all groups and inserting a new group -->");
        db.deleteAllCategories();
        System.out.println(db.getCategoryList(0, 10) + "\n");

        db.insertCategory(group1);
        Product product = new Product("This is the product name", "This is the product description", 10,
                5, "productDistributor", group1.getgName());

        System.out.println("\n<-- Adding a product -->");
        db.insertProduct(product);
        System.out.println(db.getProduct(product.getpName()));
        System.out.println(db.getProductList(0, 10, new Filter()));

        System.out.println("\n<-- Changing the price of the product -->");
        db.updateProduct("price", "10000", "title", product.getpName());
        System.out.println(db.getProduct(product.getpName()));

        db.deleteProduct(product.getpName());
        System.out.println(db.getProduct(product.getpName()));

        db.deleteAllCategories();
        db.deleteAllProducts();
    }
}
