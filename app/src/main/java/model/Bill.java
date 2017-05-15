package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bill implements Serializable {

    private Long id;
    private String name;
    private String location;
    private String issuer;
    private Long date;
    private Double price;
    private List<Item> items = new ArrayList<Item>();
    private User user;

    public Bill() {

    }

    public Bill(String name, String location, String issuer, Long date, Double price, List<Item> items, User user) {
        this.name = name;
        this.location = location;
        this.issuer = issuer;
        this.date = date;
        this.price = price;
        this.items = items;
        this.user = user;
    }

   /* public static List<Bill> getItems() {
        List<Bill> items = new ArrayList<>();
        items.add(new Bill("Racun 1", "Novi Sad", "IDEA", "22.03.2017", 890.90));
        items.add(new Bill("Racun 2", "Beograd", "Roda", "21.02.2017", 1200.00));
        items.add(new Bill("Racun 3", "Subotica", "Univerexport", "04.06.2017", 7620.43));
        items.add(new Bill("Racun 1", "Novi Sad", "IDEA", "22.03.2017", 890.90));
        items.add(new Bill("Racun 2", "Beograd", "Roda", "21.02.2017", 1200.00));
        items.add(new Bill("Racun 3", "Subotica", "Univerexport", "04.06.2017", 7620.43));
        items.add(new Bill("Racun 1", "Novi Sad", "IDEA", "22.03.2017", 890.90));
        items.add(new Bill("Racun 2", "Beograd", "Roda", "21.02.2017", 1200.00));
        items.add(new Bill("Racun 3", "Subotica", "Univerexport", "04.06.2017", 7620.43));
        items.add(new Bill("Racun 1", "Novi Sad", "IDEA", "22.03.2017", 890.90));
        items.add(new Bill("Racun 2", "Beograd", "Roda", "21.02.2017", 1200.00));
        items.add(new Bill("Racun 3", "Subotica", "Univerexport", "04.06.2017", 7620.43));
        items.add(new Bill("Racun 1", "Novi Sad", "IDEA", "22.03.2017", 890.90));
        items.add(new Bill("Racun 2", "Beograd", "Roda", "21.02.2017", 1200.00));
        items.add(new Bill("Racun 3", "Subotica", "Univerexport", "04.06.2017", 7620.43));
        items.add(new Bill("Racun 1", "Novi Sad", "IDEA", "22.03.2017", 890.90));
        items.add(new Bill("Racun 2", "Beograd", "Roda", "21.02.2017", 1200.00));
        items.add(new Bill("Racun 3", "Subotica", "Univerexport", "04.06.2017", 7620.43));
        items.add(new Bill("Racun 1", "Novi Sad", "IDEA", "22.03.2017", 890.90));
        items.add(new Bill("Racun 2", "Beograd", "Roda", "21.02.2017", 1200.00));
        items.add(new Bill("Racun 3", "Subotica", "Univerexport", "04.06.2017", 7620.43));

        return items;
    }*/

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getName();
    }
}
