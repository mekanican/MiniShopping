package com.nlh.minishoping;

import android.graphics.drawable.Drawable;

// Decorator Pattern applied!
public class HomeProduct {
    public HomeProduct(Product p) {
        wrapped = p;
    }

    public int getId() {
        return wrapped.ID;
    }

    public String getName() {
        return wrapped.name;
    }

    public Drawable getImage() {
        return null; // TODO:
    }

    public int getPrice() {
        return wrapped.price;
    }

    private final Product wrapped;
}
