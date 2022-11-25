package com.nlh.minishoping;

import android.content.Context;
import android.graphics.drawable.Drawable;

// Dummy class
class ProductCart {
    public int ID;
    public int price;
    public String name;
    public Drawable image;
    public int numberOfItem;

    public ProductCart(Context context, int ID, int price, String name) {
        this.ID = ID;
        this.price = price;
        this.name = name;
        this.image = context.getDrawable(R.drawable.icon);
        this.numberOfItem = 0;
    }

    public void addItem() {
        numberOfItem++;
    }

    public void removeItem() {
        if (numberOfItem > 0) {
            numberOfItem--;
        }
    }

    public int total() {
        return numberOfItem * price;
    }

}
