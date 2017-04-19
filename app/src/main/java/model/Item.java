package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dejan on 4/11/2017.
 */

public class Item implements Serializable{

    private String name;
    private int quantity;
    private double price;

    public static ArrayList<Item> getItems(){
        ArrayList<Item> items = new ArrayList<Item>();

        items.add(new Item("Kafa", 1, 120.00));
        items.add(new Item("Sok", 2, 220.00));
        items.add(new Item("Cokolada", 2, 430.00));
        items.add(new Item("Voda", 4, 523.00));
        items.add(new Item("Mis", 2, 220.00));
        items.add(new Item("Knjiga", 2, 220.00));
        items.add(new Item("Torba", 2, 220.00));
        items.add(new Item("Dezodorans", 2, 220.00));
        items.add(new Item("Solja", 2, 220.00));
        items.add(new Item("Sok od breske", 2, 220.00));
        items.add(new Item("Mlevena plazma", 2, 220.00));
        items.add(new Item("Orbit ekukaliptus", 2, 220.00));


        return items;
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

    public Item(String name, int quantity, double price) {
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
