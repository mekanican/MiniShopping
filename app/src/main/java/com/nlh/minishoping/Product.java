package com.nlh.minishoping;

public class Product {
    public int ID;
    public String name;
    public String imageLink;
    public double price;

    Product(int ID, String name, String imageLink, double price) {
        this.ID = ID;
        this.name = name;
        this.imageLink = imageLink;
        this.price = price;
    }
}
