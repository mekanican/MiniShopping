package com.nlh.minishoping;

public class Product {
    protected int ID;
    protected String imageLink;
    protected String name;
    protected String category;
    protected String description;
    protected int price;

    Product(int ID, String imageLink, String name, String category, String description, int price) {
        this.ID = ID;
        this.imageLink = imageLink;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    String getCategory() {
        return category;
    }
}
