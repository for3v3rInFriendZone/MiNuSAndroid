package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bill implements Serializable {

    private Long id;
    private String name;
    private String location;
    private String issuer;
    private Long date;
    private BigDecimal price;
    private List<Item> items = new ArrayList<Item>();
    private User user;

    public Bill() {
        super();
    }

    public Bill(String name, String location, String issuer, Long date, BigDecimal price, ArrayList<Item> items) {
        super();
        this.name = name;
        this.location = location;
        this.issuer = issuer;
        this.date = date;
        this.price = price;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return getName();
    }
}
