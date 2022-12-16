package com.nlh.minishoping;

public class Product {
    protected int ID;
    protected String imageLink;
    protected String name;
    protected String category;
    protected String description;
    protected int price;
    protected String sellerEmail;

    Product(int ID, String imageLink, String name, String category, String description, int price) {
        this.ID = ID;
        this.imageLink = imageLink;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;

        // 2 email, randomly
        if (SharedInfo.getInstance().getRandInt(2) == 0) {
            this.sellerEmail = "vanloc1808@gmail.com";
        } else {
            this.sellerEmail = "notabotbytheway@gmail.com";
        }
    }

    String getCategory() {
        return category;
    }
}
