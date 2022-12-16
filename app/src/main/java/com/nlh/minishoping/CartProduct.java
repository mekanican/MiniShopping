package com.nlh.minishoping;

import android.widget.ImageView;


public class CartProduct {
    private final HomeProduct wrapped;
    private int numberOfItem;

    public CartProduct(HomeProduct homeProduct) {
        wrapped = homeProduct;
        numberOfItem = 1;
    }

    public int getID() {
        return wrapped.getId();
    }

    public String getName() {
        return wrapped.getName();
    }

    public int getPrice() {
        return wrapped.getPrice();
    }

    public void getImageToImageView(ImageView imageView) {
        wrapped.getImageToImageView(imageView);
    }

    public void addItem() {
        numberOfItem++;
    }

    public void removeItem() {
        if (numberOfItem > 0) {
            numberOfItem--;
        }
    }

    public String getEmail() {
        return wrapped.getEmail();
    }

    public int getNumberOfItem() {
        return numberOfItem;
    }

    public int total() {
        return numberOfItem * wrapped.getPrice();
    }

}
