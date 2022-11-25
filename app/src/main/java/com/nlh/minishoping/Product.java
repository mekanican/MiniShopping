package com.nlh.minishoping;

public class Product {
    public int ID;
    public String name;
    public String imageLink;
    public int price;

    Product(int ID, String name, String imageLink, int price) {
        this.ID = ID;
        this.name = name;
        this.imageLink = imageLink;
        this.price = price;
    }
}
