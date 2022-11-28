package com.nlh.minishoping;

public class Product {
    public int ID;
    public String imageLink;
    public String name;
    public String category;
    public String description;
    public int price;

    Product(int ID, String imageLink, String name, String category, String description, int price) {
        this.ID = ID;
        this.imageLink = imageLink;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
    }
}
