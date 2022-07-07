
import tech.Filter;
import tech.DataBase;
import storage.Group;
import storage.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    static DataBase db;

    @BeforeAll
    static void init() {
        db = DataBase.getDB("testDB.db");
    }



    @Test
    void test1() {
        db.updateGroup("Description", "New description!", "title", "categoryTitle");
        System.out.println(db.getGroup("categoryTitle"));
        assertEquals(("<-- Group name: categoryTitle; Group description: New description!-->\n"), db.getGroup("categoryTitle").toString());
    }


    @Test
    void test2() {
        db.addGroup(new Group("categoryToDelete", "desc"));
        db.deleteGroup("categoryToDelete");
        assertNull(db.getGroup("categoryToDelete"));
    }


    @Test
    void test5() {
        db.addProduct(new Product("orange", "fruit", 3.99, 10, "natural", "Food"));
        db.updateProduct("price", "5.99", "title", "orange");
        assertEquals(5.99, db.getProduct("orange").getPrice());
    }

    @Test
    void test6() {
        db.addGroup(new Group("Non_food", "not food goes here"));
        for (int i = 1; i < 8; i++) {
            db.addProduct(new Product("product" + i, "test product", i * 0.5 + i, i * i,"distributor",  "Non_food"));
        }
        db.getProductList(0, 10, new Filter()).forEach(System.out::println);

        Filter filter = new Filter();
        filter.setBasePrice(7.89);
        filter.setFinPrice(12.0);
        filter.setBaseQuantity(11);
        filter.setFinQuantity(40);
        filter.setQuery("product");

        String expected[] = {"product4", "product5"};

        List<Product> productList = db.getProductList(0, 10, filter);
        for (int i = 0; i < expected.length; i++)
            assertEquals(expected[i], productList.get(i).getpName());
    }

    @Test
    void test7() {
        db.addProduct(new Product("delProd0", "test", 10, 10, "producer", "Non_food"));
        db.addProduct(new Product("delProd1", "test", 10, 10, "producer", "Non_food"));
        db.deleteProduct("delProd0");
        db.deleteProduct("delProd1");
        assertNull(db.getProduct("delProd0"));
        assertNull(db.getProduct("delProd1"));
    }

    @AfterAll
    static void test8() {
        db.deleteAllGroup();
        assertEquals("[]", db.getGroupList(0, 10).toString());
        db.deleteAllProducts();
        assertEquals("[]", db.getProductList(0, 10, new Filter()).toString());
    }


}
