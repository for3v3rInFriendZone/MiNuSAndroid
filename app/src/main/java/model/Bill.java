package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bill implements Serializable {

    private String name;
    private String location;
    private String issuer;
    private String date;
    private Double price;

    public Bill() {

    }

    public Bill(String name, String location, String issuer, String date, Double price) {
        this.name = name;
        this.location = location;
        this.issuer = issuer;
        this.date = date;
        this.price = price;
    }

    public static List<Bill> getItems() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return getName();
    }
}
