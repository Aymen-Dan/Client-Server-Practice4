package tech;

import java.util.Set;

public class Filter {

    private Set<Integer> IDs;
    private String query;
    private Double basePrice;
    private Double finPrice;
    private Integer baseQuantity;
    private Integer finQuantity;


    /**IDs getter*/
    public Set<Integer> getIDs() {
        return IDs;
    }

    /**IDs setter*/
    public void setIDs(Set<Integer> IDs) {
        this.IDs = IDs;
    }

    /**Query getter*/
    public String getQuery() {
        return query;
    }

    /**Query setter*/
    public void setQuery(String query) {
        this.query = query;
    }

    /**Base Price getter*/
    public Double getBasePrice() {
        return basePrice;
    }

    /**Base Price setter*/
    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    /**Final Price getter*/
    public Double getFinPrice() {
        return finPrice;
    }

    /**Final Price setter*/
    public void setFinPrice(Double finPrice) {
        this.finPrice = finPrice;
    }


    /**Base Quality getter*/
    public Integer getBaseQuantity() {
        return baseQuantity;
    }

    /**Base Quality setter*/
    public void setBaseQuantity(Integer baseQuantity) {
        this.baseQuantity = baseQuantity;
    }


    /**Final Quality  getter*/
    public Integer getFinQuantity() {
        return finQuantity;
    }

    /**Final Quality setter*/
    public void setFinQuantity(Integer finQuantity) {
        this.finQuantity = finQuantity;
    }

}
