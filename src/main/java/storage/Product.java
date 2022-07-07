package storage;

import lombok.Builder;

@Builder
public class Product {

    private final Integer ID;

    private String pName;
    private String description;
    private double price;
    private int quantity;
    private String distributor;
    private String group;


    public Product(final Integer ID, final String pName, final String description,
                   final double price, final int quantity, final String distributor, final String group) {
        this.ID = ID;
        this.pName = pName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.distributor = distributor;
        this.group = group;
    }

    public Product(final String pName, final String description, final double price,
                   final int quantity, final String distributor, final String group) {
        this(null, pName, description, price, quantity,distributor, group);
    }




    public Integer getID() {
        return ID;
    }

    public String getpName() {
        return pName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getDistributor() {
        return distributor;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }



    @Override
    public String toString() {
        return "<-- Product ID: " + ID +
                "; Product name: " + pName +
                "; Product description: " + description +
                "; Product price: " + price +
                "; Product quantity: " + quantity +
                "; Product distributor: " + distributor +
                "; Product group: " + group + " -->\n";
    }
}
