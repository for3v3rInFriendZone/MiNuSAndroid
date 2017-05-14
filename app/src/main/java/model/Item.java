package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Item implements Serializable{

    private Long id;
    private String name;
    private int quantity;
    private double price;

    public static ArrayList<Item> getItems(){
        ArrayList<Item> items = new ArrayList<Item>();

        items.add(new Item(1L, "Kafa", 1, 120.00));
        items.add(new Item(1L,"Sok", 2, 220.00));
        items.add(new Item(1L,"Cokolada", 2, 430.00));
        items.add(new Item(1L,"Voda", 4, 523.00));
        items.add(new Item(1L,"Mis", 2, 220.00));
        items.add(new Item(1L,"Knjiga", 2, 220.00));
        items.add(new Item(1L,"Torba", 2, 220.00));
        items.add(new Item(1L,"Dezodorans", 2, 220.00));

        return items;
    }

    public Long getId() {
        return id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getQuantity() {

        return quantity;
    }

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    public Item() {
    }

    public Item(Long id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (quantity != item.quantity) return false;
        if (Double.compare(item.price, price) != 0) return false;
        return name != null ? name.equals(item.name) : item.name == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + quantity;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
