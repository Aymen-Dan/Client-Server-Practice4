package org.example;
import storage.Group;
import tech.DataBase;
import tech.Filter;
import storage.Product;

public class Main {


    public static void main(String[] args) {


        DataBase db = DataBase.getDB();
        db.deleteAllGroup();
        db.deleteAllProducts();

        Group group1 = new Group("Group1", "Group 1 description");
        Group group2 = new Group("Group2", "Group 2 description");
        Group group3 = new Group("Group3", "Group 3 description");
        Group group4 = new Group("Group4", "Group 4 description");
        Group group5 = new Group("Group5", "Group 5 description");


        db.addGroup(group1);
        db.addGroup(group2);
        db.addGroup(group3);
        db.addGroup(group4);
        db.addGroup(group5);


        System.out.println(db.getGroupList(0, 10));

        System.out.println("\n<-- Changing the description of the first item -->\n");

        db.updateGroup("description", "New description", "title", group1.getgName());
        System.out.println(db.getGroupList(0, 10));


        System.out.println("\n<-- Deleting all groups and inserting a new group -->");
        db.deleteAllGroup();
        System.out.println(db.getGroupList(0, 10) + "\n");

        db.addGroup(group1);
        Product product = new Product("This is the product name", "This is the product description", 17,
                5, "productDistributor", group1.getgName());

        System.out.println("\n<-- Adding a product -->");
        db.addProduct(product);
        System.out.println(db.getProduct(product.getpName()));
        System.out.println(db.getProductList(0, 10, new Filter()));

        System.out.println("\n<-- Changing the price of the product -->");
        db.updateProduct("price", "12235", "title", product.getpName());
        System.out.println(db.getProduct(product.getpName()));

        db.deleteProduct(product.getpName());
        System.out.println(db.getProduct(product.getpName()));

        db.deleteAllGroup();
        db.deleteAllProducts();
    }
}
